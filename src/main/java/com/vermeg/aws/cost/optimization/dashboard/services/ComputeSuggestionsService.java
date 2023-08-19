package com.vermeg.aws.cost.optimization.dashboard.services;

import com.amazonaws.services.cloudwatch.model.Datapoint;
import com.vermeg.aws.cost.optimization.dashboard.entities.*;
import com.vermeg.aws.cost.optimization.dashboard.repositories.EbsOptimizationSuggestionRepository;
import com.vermeg.aws.cost.optimization.dashboard.repositories.Ec2OptimizationSuggestionRepository;
import com.vermeg.aws.cost.optimization.dashboard.repositories.EipOptimizationSuggestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ComputeSuggestionsService {

    private final Ec2OptimizationSuggestionRepository ec2OptimizationSuggestionRepository;
    private final EbsOptimizationSuggestionRepository ebsOptimizationSuggestionRepository;
    private final EipOptimizationSuggestionRepository eipOptimizationSuggestionRepository;
    private final ComputeServices ec2Service;
    private final AutoScalingService autoscalingService;
    private final CloudWatchService cloudWatchService;

    // EIP Optimization Suggestions

    @DependsOn("DataStorageService")
    @Scheduled(fixedDelay = 3600000L)
    public List<EIPOptimizationSuggestion> generateEipSuggestions(){
        log.info("Generating EIP Cost Optimization Suggestions ....");
        List<EIPAddress> addresses = ec2Service.getAllAddresses();
        List<EIPOptimizationSuggestion> suggestions = new ArrayList<>();
        for (EIPAddress address : addresses){
            if(address.getInstanceId() == null) {
                EIPOptimizationSuggestion suggestion = new EIPOptimizationSuggestion();
                suggestion.setTitle("EIP with allocation ID: " + address.getAllocationId() + " not associated with a running instance");
                suggestion.setDescription("The following EIP address is not associated with a running instance");
                suggestion.setRecommendation("Associate the EIP address with an instance or consider releasing it.");
                suggestion.setStatus(SuggestionStatus.Pending);
                suggestion.setCreatedDate(LocalDateTime.now());
                suggestion.setAssociatedAccount(address.getAssociatedAccount());
                suggestion.setCategory(EIPSuggestionCategory.NORUNNINGINSTANCE);
                suggestion.setLinkedIPAddress(address);
                suggestions.add(suggestion);
            }
            if (address.getAssociationId() == null) {
                EIPOptimizationSuggestion suggestion = new EIPOptimizationSuggestion();
                suggestion.setTitle("EIP with allocation ID: " + address.getAllocationId() + " not associated with an instance");
                suggestion.setDescription("The following EIP address is not associated with an instance");
                suggestion.setRecommendation("Associate the EIP address with an instance or consider releasing it.");
                suggestion.setStatus(SuggestionStatus.Pending);
                suggestion.setCreatedDate(LocalDateTime.now());
                suggestion.setAssociatedAccount(null);
                suggestion.setCategory(EIPSuggestionCategory.NOASSOCIATEDINSTANCE);
                suggestion.setLinkedIPAddress(address);
                suggestions.add(suggestion);
            }
        }
        log.info("EIP Cost Optimization Suggestions Generation process terminated");
        return eipOptimizationSuggestionRepository.saveAll(suggestions);
    }

    public List<EIPOptimizationSuggestion> getAllEipSuggestions(){
        return eipOptimizationSuggestionRepository.findAll();
    }

    public EIPOptimizationSuggestion getEipSuggestionById(Long id){
        return eipOptimizationSuggestionRepository.findById(id).orElse(null);
    }

    public EIPOptimizationSuggestion updateEipSuggestion(SuggestionStatus status, Long id){
        EIPOptimizationSuggestion suggestion = eipOptimizationSuggestionRepository.findById(id).orElseThrow();
        suggestion.setStatus(status);
        return eipOptimizationSuggestionRepository.save(suggestion);
    }

    public void deleteEipSuggestion(Long id){
        eipOptimizationSuggestionRepository.deleteById(id);
    }

    public void deleteDealtWithEipSuggestions(){
        List<EIPOptimizationSuggestion> suggestions = eipOptimizationSuggestionRepository.findAll();
        for (EIPOptimizationSuggestion suggestion : suggestions){
            if(suggestion.getStatus() != SuggestionStatus.Pending && Duration.between(suggestion.getCreatedDate(), new Date().toInstant()).toDays() > 30){
                eipOptimizationSuggestionRepository.delete(suggestion);
            }
        }
    }

    // EBS Optimization Suggestions

    @DependsOn("DataStorageService")
    @Scheduled(fixedDelay = 3600000L)
    public List<EBSOptimizationSuggestion> generateEbsSuggestions(){
        log.info("Generating EBS Cost Optimization Suggestions ....");
        List<EBSOptimizationSuggestion> suggestions = new ArrayList<>();
        List<EBSVolume> volumes = ec2Service.getAllVolumes();
        for (EBSVolume volume : volumes){
            if(volume.getState().equals("available")){
                EBSOptimizationSuggestion suggestion = new EBSOptimizationSuggestion();
                suggestion.setTitle("EBS Volume with ID: " + volume.getVolumeId() + " is available");
                suggestion.setDescription("The following EBS volume isn't associated with any instance and might be a candidate for deletion.");
                suggestion.setRecommendation("Delete the volume or associate it with an instance if needed");
                suggestion.setStatus(SuggestionStatus.Pending);
                suggestion.setCreatedDate(new Date());
                suggestion.setAssociatedAccount(null);
                suggestions.add(suggestion);
            }
        }
        log.info("EBS Cost Optimization Suggestions Generation Process terminated. ");
        return ebsOptimizationSuggestionRepository.saveAll(suggestions);
    }



    public List<EBSOptimizationSuggestion> getAllEbsSuggestions(){
        return ebsOptimizationSuggestionRepository.findAll();
    }

    public EBSOptimizationSuggestion getEbsSuggestionById(Long id){
        return ebsOptimizationSuggestionRepository.findById(id).orElse(null);
    }

    public EBSOptimizationSuggestion updateEbsSuggestion(SuggestionStatus status, Long id){
        EBSOptimizationSuggestion suggestion = ebsOptimizationSuggestionRepository.findById(id).orElseThrow();
        suggestion.setStatus(status);
        return ebsOptimizationSuggestionRepository.save(suggestion);
    }

    public void deleteEbsSuggestion(Long id){
        ebsOptimizationSuggestionRepository.deleteById(id);
    }

    public void deleteDealtWithEbsSuggestions(){
        List<EBSOptimizationSuggestion> suggestions = ebsOptimizationSuggestionRepository.findAll();
        for (EBSOptimizationSuggestion suggestion : suggestions){
            if(suggestion.getStatus() != SuggestionStatus.Pending && Duration.between(suggestion.getCreatedDate().toInstant(), new Date().toInstant()).toDays() > 30){
                ebsOptimizationSuggestionRepository.delete(suggestion);
            }
        }
    }

    // EC2 Optimization Suggestions

    @DependsOn("DataStorageService")
    @Scheduled(fixedDelay = 3600000L)
    public List<EC2OptimizationSuggestion> generateEcAutoScaling2Suggestions() {
        List<EC2OptimizationSuggestion> suggestions = new ArrayList<>();
        List<EC2Instance> instances = ec2Service.getAllInstances();
        for (EC2Instance instance : instances) {
            // Auto Scaling Group Suggestions
            if (autoscalingService.isInstanceInAutoScalingGroup(instance.getInstanceId())) {
                EC2OptimizationSuggestion suggestion = EC2OptimizationSuggestion.builder()
                        .title("EC2 Instance with the following ID: " + instance.getInstanceId() + " not in an Auto Scaling Group")
                        .description("The following instance is not part of an autoscaling group. It will not be able to scale according to its workload thus increasing the potential of overprovisioning.")
                        .recommendation("Consider adding the instance to an Auto Scaling Group.")
                        .status(SuggestionStatus.Pending)
                        .createdDate(LocalDateTime.now())
                        .associatedAccount(instance.getAssociatedAccount())
                        .category(EC2SuggestionCategory.NOAUTOSCALINGGROUP)
                        .linkedInstance(instance)
                        .build();
                suggestions.add(suggestion);
            }
        }
        return ec2OptimizationSuggestionRepository.saveAll(suggestions);
    }


    @DependsOn("DataStorageService")
    @Scheduled(fixedDelay = 3600000L)
    public List<EC2OptimizationSuggestion> generateEc2ResourceSuggestions(){
        List<EC2OptimizationSuggestion> suggestions = new ArrayList<>();
        List<EC2Instance> instances = ec2Service.getAllInstances();
        for (EC2Instance instance : instances){
            // Cpu Utilization Suggestions
            if (instance.getMonitoring().equals("enabled")){
                if (cloudWatchService.getCPUUtilization(instance.getInstanceId(),60,3600).stream().mapToDouble(Datapoint::getAverage).allMatch(value -> value < 0.4)){
                    EC2OptimizationSuggestion suggestion = EC2OptimizationSuggestion.builder()
                            .title("EC2 Instance with ID: " + instance.getInstanceId() + " has low CPU utilization")
                            .description("The following Ec2 instance is using a low percentage of its CPU. It is then overprovisioned.")
                            .recommendation("Consider downsizing the instance or adding it to an autoscaling group.")
                            .status(SuggestionStatus.Pending)
                            .createdDate(LocalDateTime.now())
                            .associatedAccount(instance.getAssociatedAccount())
                            .category(EC2SuggestionCategory.LOWCPUUTILIZATION)
                            .linkedInstance(instance)
                            .build();
                    suggestions.add(suggestion);
                }
                // Network In Suggestions
                if (cloudWatchService.getNetworkIn(instance.getInstanceId(),60,3600).stream().mapToDouble(Datapoint::getAverage).allMatch(value -> value < 100000000)){
                    EC2OptimizationSuggestion suggestion = EC2OptimizationSuggestion.builder()
                            .title("EC2 Instance with ID: " + instance.getInstanceId() + " has low network in")
                            .description("EC2 Instance with ID: \" + instance.getInstanceId() + \" has low network in")
                            .recommendation("Consider downsizing the instance")
                            .status(SuggestionStatus.Pending)
                            .createdDate(LocalDateTime.now())
                            .associatedAccount(instance.getAssociatedAccount())
                            .category(EC2SuggestionCategory.LOWNETWORKIN)
                            .linkedInstance(instance)
                            .build();
                    suggestions.add(suggestion);
                }
                // Network Out Suggestions
                if (cloudWatchService.getNetworkOut(instance.getInstanceId(),60,3600).stream().mapToDouble(Datapoint::getAverage).allMatch(value -> value < 100000000)){
                    EC2OptimizationSuggestion suggestion = EC2OptimizationSuggestion.builder()
                            .title("EC2 Instance with ID: " + instance.getInstanceId() + " has low network out")
                            .description("EC2 Instance with ID: \" + instance.getInstanceId() + \" has low network out")
                            .recommendation("Downsize the instance")
                            .status(SuggestionStatus.Pending)
                            .createdDate(LocalDateTime.now())
                            .associatedAccount(instance.getAssociatedAccount())
                            .category(EC2SuggestionCategory.LOWNETWORKOUT)
                            .linkedInstance(instance)
                            .build();
                    suggestions.add(suggestion);
                }
            }
        }
        return ec2OptimizationSuggestionRepository.saveAll(suggestions);
    }
    public List<EC2OptimizationSuggestion> generateEc2SuggestionsWithInstanceId(String instanceId){
        EC2Instance instance = ec2Service.getInstanceById(instanceId);
        List<EC2OptimizationSuggestion> suggestions = new ArrayList<>();

        if(autoscalingService.isInstanceInAutoScalingGroup(instance.getInstanceId())){
            EC2OptimizationSuggestion suggestion = EC2OptimizationSuggestion.builder()
                    .title("EC2 Instance with ID: " + instance.getInstanceId() + " not in an Auto Scaling Group")
                    .description("Add the instance to an Auto Scaling Group")
                    .status(SuggestionStatus.Pending)
                    .createdDate(LocalDateTime.now())
                    .associatedAccount(instance.getAssociatedAccount())
                    .category(EC2SuggestionCategory.NOAUTOSCALINGGROUP)
                    .linkedInstance(instance)
                    .build();
            suggestions.add(suggestion);
        }
        // Cpu Utilization Suggestions
        if (cloudWatchService.getCPUUtilization(instance.getInstanceId(),60,3600).stream().mapToDouble(Datapoint::getAverage).allMatch(value -> value < 0.4)){
            EC2OptimizationSuggestion suggestion = EC2OptimizationSuggestion.builder()
                    .title("EC2 Instance with ID: " + instance.getInstanceId() + " has low CPU utilization")
                    .description("Downsize the instance")
                    .status(SuggestionStatus.Pending)
                    .createdDate(LocalDateTime.now())
                    .associatedAccount(instance.getAssociatedAccount())
                    .category(EC2SuggestionCategory.LOWCPUUTILIZATION)
                    .linkedInstance(instance)
                    .build();
            suggestions.add(suggestion);
        }
        // Network In Suggestions
        if (cloudWatchService.getNetworkIn(instance.getInstanceId(),60,3600).stream().mapToDouble(Datapoint::getAverage).allMatch(value -> value < 100000000)){
            EC2OptimizationSuggestion suggestion = EC2OptimizationSuggestion.builder()
                    .title("EC2 Instance with ID: " + instance.getInstanceId() + " has low network in")
                    .description("Downsize the instance")
                    .status(SuggestionStatus.Pending)
                    .createdDate(LocalDateTime.now())
                    .associatedAccount(instance.getAssociatedAccount())
                    .category(EC2SuggestionCategory.LOWNETWORKIN)
                    .linkedInstance(instance)
                    .build();
            suggestions.add(suggestion);
        }
        // Network Out Suggestions
        if (cloudWatchService.getNetworkOut(instance.getInstanceId(),60,3600).stream().mapToDouble(Datapoint::getAverage).allMatch(value -> value < 100000000)){
            EC2OptimizationSuggestion suggestion = EC2OptimizationSuggestion.builder()
                    .title("EC2 Instance with ID: " + instance.getInstanceId() + " has low network out")
                    .description("Downsize the instance")
                    .status(SuggestionStatus.Pending)
                    .createdDate(LocalDateTime.now())
                    .associatedAccount(instance.getAssociatedAccount())
                    .category(EC2SuggestionCategory.LOWNETWORKOUT)
                    .linkedInstance(instance)
                    .build();
            suggestions.add(suggestion);
        }
        return ec2OptimizationSuggestionRepository.saveAll(suggestions);
    }
    public List<EC2OptimizationSuggestion> getAllEc2Suggestions(){
        return ec2OptimizationSuggestionRepository.findAll();
    }

    public EC2OptimizationSuggestion getEc2SuggestionById(Long id){
        return ec2OptimizationSuggestionRepository.findById(id).orElse(null);
    }

    public EC2OptimizationSuggestion updateEc2Suggestion(SuggestionStatus status, Long id){
        EC2OptimizationSuggestion suggestion = ec2OptimizationSuggestionRepository.findById(id).orElseThrow();
        suggestion.setStatus(status);
        return ec2OptimizationSuggestionRepository.save(suggestion);
    }

    public void deleteEc2Suggestion(Long id){
        ec2OptimizationSuggestionRepository.deleteById(id);
    }

    public void deleteDealtWithEc2Suggestions(){
        List<EC2OptimizationSuggestion> suggestions = ec2OptimizationSuggestionRepository.findAll();
        for (EC2OptimizationSuggestion suggestion : suggestions){
            if(suggestion.getStatus() != SuggestionStatus.Pending && Duration.between(suggestion.getCreatedDate(), new Date().toInstant()).toDays() > 30){
                ec2OptimizationSuggestionRepository.delete(suggestion);
            }
        }
    }
}
