package com.vermeg.aws.cost.optimization.dashboard.services;

import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClientBuilder;
import com.amazonaws.services.cloudwatch.model.Datapoint;
import com.amazonaws.services.cloudwatch.model.Dimension;
import com.amazonaws.services.cloudwatch.model.GetMetricStatisticsRequest;
import com.amazonaws.services.cloudwatch.model.GetMetricStatisticsResult;
import com.amazonaws.services.rds.AmazonRDS;
import com.amazonaws.services.rds.AmazonRDSClientBuilder;
import com.amazonaws.services.rds.model.DBInstance;
import com.vermeg.aws.cost.optimization.dashboard.entities.RDSOptimizationSuggestion;
import com.vermeg.aws.cost.optimization.dashboard.entities.RDSSuggestionCategory;
import com.vermeg.aws.cost.optimization.dashboard.entities.SuggestionStatus;
import com.vermeg.aws.cost.optimization.dashboard.repositories.RdsOptimizationSuggestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RdsSuggestionsService {

    // Local Variable Declaration

    private final RdsOptimizationSuggestionRepository rdsOptimizationSuggestionRepository;
    private final AutoScalingService autoscalingService;
    private final RDSService rdsService;

    // Methods Declaration

    @DependsOn("DataStorageService")
    @Scheduled(fixedDelay = 3600000L)
    public List<RDSOptimizationSuggestion> generateRdsSuggestions() {
        log.info("Generating RDS Suggestions");

        List<RDSOptimizationSuggestion> suggestions = rdsService.getAllRdsInstances().parallelStream()
                .flatMap(instance -> {
                    List<RDSOptimizationSuggestion> instanceSuggestions = new ArrayList<>();

                    if(autoscalingService.isInstanceInAutoScalingGroup(instance.getDbInstanceIdentifier())){
                        RDSOptimizationSuggestion suggestion = new RDSOptimizationSuggestion();
                        suggestion.setTitle("RDS Instance with ID: " + instance.getDbInstanceIdentifier() + " is not in an Auto Scaling Group");
                        suggestion.setDescription("The following Rds instance is not part of an autoscaling group. It will not be able to scale according to its workload thus increasing the potential of overprovisioning");
                        suggestion.setRecommendation("Consider adding the RDS Instance to an Auto Scaling Group");
                        suggestion.setStatus(SuggestionStatus.Pending);
                        suggestion.setCreatedDate(LocalDateTime.now());
                        suggestion.setCategory(RDSSuggestionCategory.NOAUTOSCALINGGROUP);
                        suggestion.setLinkedInstance(instance);
                        suggestion.setAssociatedAccount(instance.getAssociatedAccount());
                        instanceSuggestions.add(suggestion);
                    }

                    return instanceSuggestions.stream();
                })
                .toList();

        log.info("Generated " + suggestions.size() + " RDS Suggestions");

        return rdsOptimizationSuggestionRepository.saveAll(suggestions);
    }


    public List<RDSOptimizationSuggestion> getAllRdsSuggestions() {
        return rdsOptimizationSuggestionRepository.findAll();
    }

    public RDSOptimizationSuggestion getRdsSuggestionById(Long id) {
        return rdsOptimizationSuggestionRepository.findById(id).orElse(null);
    }

    public RDSOptimizationSuggestion updateRdsSuggestionStatus(SuggestionStatus status, Long id) {
        RDSOptimizationSuggestion suggestion = rdsOptimizationSuggestionRepository.findById(id).orElseThrow();
        suggestion.setStatus(status);
        return rdsOptimizationSuggestionRepository.save(suggestion);
    }

    public void deleteRdsSuggestion(Long id) {
        rdsOptimizationSuggestionRepository.deleteById(id);
    }

    public void deleteDealtWithSuggestions() {
        List<RDSOptimizationSuggestion> suggestions = rdsOptimizationSuggestionRepository.findAll();
        for (RDSOptimizationSuggestion suggestion : suggestions){
            if(suggestion.getStatus() != SuggestionStatus.Pending){
                rdsOptimizationSuggestionRepository.delete(suggestion);
            }
        }
    }


    /*
    public List<RDSOptimizationSuggestion> generateHighUtilizationRDSSuggestions() {

        List<RDSOptimizationSuggestion> suggestions = new ArrayList<>();

        AmazonRDS rdsClient = AmazonRDSClientBuilder.standard().build();
        List<DBInstance> instances = rdsClient.describeDBInstances().getDBInstances();

        for (DBInstance instance : instances) {
            double cpuUtilization = getCpuUtilization(instance.getDBInstanceIdentifier());

            if (cpuUtilization >= 80) {
                String title = "Upscale RDS instance " + instance.getDBInstanceIdentifier();
                String description = "The RDS instance " + instance.getDBInstanceIdentifier() + " is experiencing high CPU utilization of " + cpuUtilization + "%. It is recommended to upscale the instance to avoid performance issues.";
                suggestions.add(RDSOptimizationSuggestion.builder()
                        .title(title)
                        .description(description)
                        .associatedAccount(instance.getDBInstanceArn())
                        .createdDate(LocalDateTime.now())
                        .status(SuggestionStatus.Pending)
                        .build());
            }
        }

        return suggestions;
    }
    */

    private double getCpuUtilization(String instanceIdentifier) {
        final long ONE_MINUTE_IN_MILLIS = 60000;
        long start = System.currentTimeMillis() - (5 * ONE_MINUTE_IN_MILLIS);
        long end = System.currentTimeMillis();

        AmazonCloudWatch cloudWatch = AmazonCloudWatchClientBuilder.standard().build();
        GetMetricStatisticsRequest request = new GetMetricStatisticsRequest()
                .withNamespace("AWS/RDS")
                .withMetricName("CPUUtilization")
                .withDimensions(new Dimension().withName("DBInstanceIdentifier").withValue(instanceIdentifier))
                .withStartTime(new Date(start))
                .withEndTime(new Date(end))
                .withPeriod(60)
                .withStatistics("Average");

        GetMetricStatisticsResult result = cloudWatch.getMetricStatistics(request);
        List<Datapoint> datapoints = result.getDatapoints();

        if (datapoints.isEmpty()) {
            return 0.0;
        }

        Datapoint datapoint = datapoints.get(datapoints.size() - 1);
        return datapoint.getAverage();
    }

    public static class RDSUtilizationChecker {

        public static void main(String[] args) {
            String rdsInstanceName = "your-rds-instance-name";
            double maxCpuUtilization = 80.0;

            double cpuUtilization = getCpuUtilization(rdsInstanceName);

            if (cpuUtilization >= maxCpuUtilization) {
                log.info("RDS instance " + rdsInstanceName + " has high CPU utilization (" + cpuUtilization + "%)");
                // Add code here to trigger an upscale action
            } else {
                log.info("RDS instance " + rdsInstanceName + " has CPU utilization within acceptable limits (" + cpuUtilization + "%)");
            }
        }

        private static double getCpuUtilization(String rdsInstanceName) {
            String[] command = {"top", "-b", "-n", "2", "-d", "0.01", "-U", "rdsadmin", "-p", "1"};
            double cpuUtilization = 0.0;

            try {
                Process process = new ProcessBuilder(command).start();
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line = reader.readLine();
                while (line != null) {
                    if (line.contains(rdsInstanceName)) {
                        String[] tokens = line.trim().split("\\s+");
                        cpuUtilization = Double.parseDouble(tokens[8].replace(",", "."));
                        break;
                    }
                    line = reader.readLine();
                }
                process.destroy();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return cpuUtilization;
        }
    }
}
