package com.vermeg.aws.cost.optimization.dashboard.services;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.autoscaling.AmazonAutoScaling;
import com.amazonaws.services.autoscaling.AmazonAutoScalingClientBuilder;
import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClientBuilder;
import com.amazonaws.services.costexplorer.AWSCostExplorer;
import com.amazonaws.services.costexplorer.AWSCostExplorerClientBuilder;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.rds.AmazonRDS;
import com.amazonaws.services.rds.AmazonRDSClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.support.AWSSupport;
import com.amazonaws.services.support.AWSSupportClientBuilder;
import com.vermeg.aws.cost.optimization.dashboard.entities.AwsAccountCredentials;
import com.vermeg.aws.cost.optimization.dashboard.repositories.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UtilityServices {

    private final AwsCredentialsService awsCredentialsService;
    private final AwsCredentialsRepository awsCredentialsRepository;
    private final EC2InstanceRepository ec2InstanceRepository;
    private final StorageBucketRepository storageBucketRepository;
    private final RDSInstanceRepository rdsInstanceRepository;
    private final EBSVolumeRepository ebsVolumeRepository;
    private final EIPAddressRepository eipAddressRepository;
    private static final Regions[] awsRegions = {
            Regions.AP_SOUTH_1,
            Regions.EU_NORTH_1,
            Regions.EU_WEST_3,
            Regions.EU_WEST_2,
            Regions.EU_WEST_1,
            Regions.AP_NORTHEAST_3,
            Regions.AP_NORTHEAST_2,
            Regions.AP_NORTHEAST_1,
            Regions.CA_CENTRAL_1,
            Regions.SA_EAST_1,
            Regions.AP_SOUTHEAST_1,
            Regions.AP_SOUTHEAST_2,
            Regions.EU_CENTRAL_1,
            Regions.US_EAST_1,
            Regions.US_EAST_2,
            Regions.US_WEST_1,
            Regions.US_WEST_2,
    };

    public List<Pair<AmazonEC2, String>> getEc2ClientsListWithAccountId() {
        List<Pair<AmazonEC2, String>> ec2ClientsListWithAccountId = new ArrayList<>();
        for (AWSCredentials credentials : this.awsCredentialsService.getAllCredentials()){
            String accountId = awsCredentialsService.getAccountId(credentials.getAWSAccessKeyId(), credentials.getAWSSecretKey());
            for (Regions region : awsRegions) {
                AmazonEC2 client = AmazonEC2ClientBuilder.standard()
                        .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(credentials.getAWSAccessKeyId(),credentials.getAWSSecretKey())))
                        .withRegion(region)
                        .build();
                ec2ClientsListWithAccountId.add(new ImmutablePair<>(client, accountId));
            }
        }
        return ec2ClientsListWithAccountId;
    }

    @Cacheable("rds-clients")
    public List<Pair<AmazonRDS, String>> getRDSClientsListWithAccountId () {
        List<Pair<AmazonRDS, String>> rdsClientsList = new ArrayList<>();

        for (AWSCredentials credentials : this.awsCredentialsService.getAllCredentials()){
            String accountId = awsCredentialsService.getAccountId(credentials.getAWSAccessKeyId(), credentials.getAWSSecretKey());
            for (Regions region : awsRegions) {
                AmazonRDS client = AmazonRDSClientBuilder.standard()
                        .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(credentials.getAWSAccessKeyId(),credentials.getAWSSecretKey())))
                        .withRegion(region)
                        .build();
                rdsClientsList.add(new ImmutablePair<>(client, accountId));
            }
        }
        return rdsClientsList;
    }

    @Cacheable("s3-clients")
    public List<Pair<AmazonS3, String>> getS3ClientsList() {
        List<Pair<AmazonS3, String>> s3ClientsList = new ArrayList<>();

        for (AWSCredentials credentials : this.awsCredentialsService.getAllCredentials()) {
            String accountId = awsCredentialsService.getAccountId(credentials.getAWSAccessKeyId(), credentials.getAWSSecretKey());
            for (Regions region : awsRegions) {
                AmazonS3 client = AmazonS3ClientBuilder.standard()
                        .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(credentials.getAWSAccessKeyId(), credentials.getAWSSecretKey())))
                        .withRegion(region)
                        .withForceGlobalBucketAccessEnabled(true)
                        .build();
                s3ClientsList.add(new ImmutablePair<>(client, accountId));
            }
        }
        return s3ClientsList;
    }

    @Cacheable("trusted-advisor-clients")
    public List<Pair<AWSSupport,String>> getTrustedAdvisorClientsList() {
        List<Pair<AWSSupport, String>> trustedAdvisorClientsList = new ArrayList<>();

        for (AWSCredentials credentials : this.awsCredentialsService.getAllCredentials()) {
            String accountId = awsCredentialsService.getAccountId(credentials.getAWSAccessKeyId(), credentials.getAWSSecretKey());
            for (Regions region : awsRegions) {
                AWSSupport client = AWSSupportClientBuilder.standard()
                        .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(credentials.getAWSAccessKeyId(), credentials.getAWSSecretKey())))
                        .withRegion(region)
                        .build();
                trustedAdvisorClientsList.add(new ImmutablePair<>(client, accountId));
            }
        }
        return trustedAdvisorClientsList;
    }

    @Cacheable("asg-clients")
    public List<Pair<AmazonAutoScaling,String>> getAutoscalingClientsList(){
        List<Pair<AmazonAutoScaling, String>> autoscalingClientsList = new ArrayList<>();

        for (AWSCredentials credentials : this.awsCredentialsService.getAllCredentials()) {
            String accountId = awsCredentialsService.getAccountId(credentials.getAWSAccessKeyId(), credentials.getAWSSecretKey());
            for (Regions region : awsRegions) {
                AmazonAutoScaling client = AmazonAutoScalingClientBuilder.standard()
                        .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(credentials.getAWSAccessKeyId(), credentials.getAWSSecretKey())))
                        .withRegion(region)
                        .build();
                autoscalingClientsList.add(new ImmutablePair<>(client, accountId));
            }
        }
        return autoscalingClientsList;
    }

    List<Pair<AmazonCloudWatch,String>> getCloudWatchClientsList() {
        List<Pair<AmazonCloudWatch, String>> cloudWatchClientsList = new ArrayList<>();

        for (AWSCredentials credentials : this.awsCredentialsService.getAllCredentials()) {
            String accountId = awsCredentialsService.getAccountId(credentials.getAWSAccessKeyId(), credentials.getAWSSecretKey());
            for (Regions region : awsRegions) {
                AmazonCloudWatch client = AmazonCloudWatchClientBuilder.standard()
                        .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(credentials.getAWSAccessKeyId(), credentials.getAWSSecretKey())))
                        .withRegion(region)
                        .build();
                cloudWatchClientsList.add(new ImmutablePair<>(client, accountId));
            }
        }
        return cloudWatchClientsList;
    }

    public AmazonCloudWatch getCloudWatchClient(String resourceId, String namespace) {
        String accountId = null;
        String region = null;

        switch (namespace) {
            case "AWS/EC2" -> {
                if (ec2InstanceRepository.findByInstanceId(resourceId).isPresent()) {
                    accountId = ec2InstanceRepository.findByInstanceId(resourceId).get().getAssociatedAccount().getAccountId();
                    region = ec2InstanceRepository.findByInstanceId(resourceId).get().getRegion();
                }
                else if (eipAddressRepository.findByAllocationId(resourceId).isPresent()) {
                    accountId = eipAddressRepository.findByAllocationId(resourceId).get().getAssociatedAccount().getAccountId();
                    region = "eu-west-1";
                }

            }
            case "AWS/EBS" -> {
                if (ebsVolumeRepository.findByVolumeId(resourceId).isPresent()) {
                    accountId = ebsVolumeRepository.findByVolumeId(resourceId).get().getAssociatedAccount().getAccountId();
                    region = ebsVolumeRepository.findByVolumeId(resourceId).get().getRegion();
                }
            }
            case "AWS/RDS" -> {
                if (rdsInstanceRepository.findByDbInstanceIdentifier(resourceId).isPresent()) {
                    accountId = rdsInstanceRepository.findByDbInstanceIdentifier(resourceId).get().getAssociatedAccount().getAccountId();
                    region = rdsInstanceRepository.findByDbInstanceIdentifier(resourceId).get().getRegion();
                }
            }
            case "AWS/S3" -> {
                if (storageBucketRepository.findByName(resourceId).isPresent()) {
                    accountId = storageBucketRepository.findByName(resourceId).get().getAssociatedAccount().getAccountId();
                    region = storageBucketRepository.findByName(resourceId).get().getRegion();
                }
            }
            default -> {
                log.error("Namespace not found");
                return null;
            }
        }
        AwsAccountCredentials credentials = this.awsCredentialsRepository.findAwsAccountCredentialsByAccountId(accountId);
        return AmazonCloudWatchClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(credentials.getAccessKeyId(), credentials.getSecretAccessKey())))
                .withRegion(region)
                .build();
    }

    public List<AWSCostExplorer> getCostExplorerClientsList() {
        List<AWSCostExplorer> costExplorerClientsList = new ArrayList<>();

        for (AWSCredentials credentials : this.awsCredentialsService.getAllCredentials()) {
            AWSCostExplorer client = AWSCostExplorerClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(credentials.getAWSAccessKeyId(), credentials.getAWSSecretKey())))
                    .withRegion(Regions.DEFAULT_REGION)
                    .build();
            costExplorerClientsList.add(client);
        }
        return costExplorerClientsList;
    }
}
