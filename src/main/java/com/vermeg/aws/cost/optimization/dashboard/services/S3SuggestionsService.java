package com.vermeg.aws.cost.optimization.dashboard.services;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.redshift.model.BucketNotFoundException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.vermeg.aws.cost.optimization.dashboard.entities.*;
import com.vermeg.aws.cost.optimization.dashboard.repositories.S3ObjectOptimizationSuggestionRepository;
import com.vermeg.aws.cost.optimization.dashboard.repositories.S3OptimizationSuggestionRepository;
import com.vermeg.aws.cost.optimization.dashboard.repositories.StorageBucketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3SuggestionsService {
    // Local variables

    private final S3OptimizationSuggestionRepository s3OptimizationSuggestionRepository;
    private final S3ObjectOptimizationSuggestionRepository s3ObjectOptimizationSuggestionRepository;
    private final StorageBucketRepository storageBucketRepository;
    private final AwsCredentialsService credentialsService;
    private final S3Service s3Service;

    // Methods

    @Scheduled(fixedDelay = 3600000L)
    public List<S3OptimizationSuggestion> generateS3Suggestions() {

        List<StorageBucket> buckets = storageBucketRepository.findAll();
        List<S3OptimizationSuggestion> suggestions = new ArrayList<>();
        for (StorageBucket bucket : buckets) {
            if (!hasLifecyclePolicy(bucket.getName())) {
                S3OptimizationSuggestion suggestion = S3OptimizationSuggestion.builder()
                        .title("Add Lifecycle Policy to Bucket: " + bucket.getName())
                        .category(S3SuggestionCategory.NOLIFECYCLEPOLICY)
                        .description("Bucket " + bucket.getName() + " does not have a lifecycle policy. This could lead to unnecessary storage costs for infrequently accessed data.")
                        .recommendation("Consider adding one to " +
                                "automatically transition objects to lower-cost storage classes or delete them when they " +
                                        "are no longer needed.")
                        .createdDate(LocalDateTime.now())
                        .status(SuggestionStatus.Pending)
                        .linkedBucket(bucket)
                        .associatedAccount(bucket.getAssociatedAccount())
                        .build();
                suggestions.add(suggestion);
            }
        }

        return s3OptimizationSuggestionRepository.saveAll(suggestions);
    }

    public List<S3OptimizationSuggestion> getAllS3Suggestions() {
        return s3OptimizationSuggestionRepository.findAll();
    }

    public S3OptimizationSuggestion getS3SuggestionById(Long id) {
        return s3OptimizationSuggestionRepository.findById(id).orElse(null);
    }

    public S3OptimizationSuggestion updateS3SuggestionStatus(SuggestionStatus status, Long id) {
        S3OptimizationSuggestion suggestion = s3OptimizationSuggestionRepository.findById(id).orElseThrow();
        suggestion.setStatus(status);
        return s3OptimizationSuggestionRepository.save(suggestion);
    }

    public void deleteS3Suggestion(Long id) {
        s3OptimizationSuggestionRepository.deleteById(id);
    }

    public void deleteDealtWithSuggestions() {
        List<S3OptimizationSuggestion> suggestions = s3OptimizationSuggestionRepository.findAll();
        for (S3OptimizationSuggestion suggestion : suggestions){
            if(suggestion.getStatus() != SuggestionStatus.Pending && Duration.between(suggestion.getCreatedDate(), LocalDateTime.now()).toDays() > 30){
                s3OptimizationSuggestionRepository.delete(suggestion);
            }
        }
    }

    public boolean hasLifecyclePolicy(String bucketName) {
        StorageBucket bucket = storageBucketRepository.findByName(bucketName).orElseThrow(() -> new RuntimeException("Bucket not found"));
        AwsAccountCredentials credentials = credentialsService.getCredentialsByAccountId(bucket.getAssociatedAccount().getAccountId());

        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(credentials.getAccessKeyId(), credentials.getSecretAccessKey())))
                .withForceGlobalBucketAccessEnabled(true)
                .withRegion((bucket.getRegion().equals("EU")) ? Regions.EU_WEST_1.getName() : bucket.getRegion())
                .build();

        GetBucketLifecycleConfigurationRequest request = new GetBucketLifecycleConfigurationRequest(bucketName);
        BucketLifecycleConfiguration configuration = s3Client.getBucketLifecycleConfiguration(request);

        return configuration != null && !configuration.getRules().isEmpty();
    }

    public List<S3ObjectSummary> getObjectsListByBucketName(String bucketName){
        StorageBucket bucket = storageBucketRepository.findByName(bucketName).orElseThrow(() -> new RuntimeException("Bucket not found"));
        AwsAccountCredentials credentials = credentialsService.getCredentialsByAccountId(bucket.getAssociatedAccount().getAccountId());

        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(credentials.getAccessKeyId(), credentials.getSecretAccessKey())))
                .withRegion(bucket.getRegion())
                .build();

        ObjectListing objectListing = s3Client.listObjects(bucketName);
        return objectListing.getObjectSummaries();
    }

    public String getOptimalStorageClass(S3ObjectSummary object) {
        Date today = new Date();
        Date lastAccessed = object.getLastModified();
        String storageClass = object.getStorageClass();
        long retentionPeriodInDays = Duration.between(lastAccessed.toInstant(), today.toInstant()).toDays();
        if (storageClass.equals("STANDARD") && retentionPeriodInDays > 30) {
            return "STANDARD_IA";
        } else if (storageClass.equals("STANDARD_IA") && retentionPeriodInDays > 60) {
            return "GLACIER";
        } else if (storageClass.equals("GLACIER") && retentionPeriodInDays > 90) {
            return "DEEP_ARCHIVE";
        } else {
            return storageClass;
        }
    }

    public void deleteDealtWithObjectSuggestions() {
        List<S3ObjectOptimizationSuggestion> suggestions = s3ObjectOptimizationSuggestionRepository.findAll();
        for (S3ObjectOptimizationSuggestion suggestion : suggestions) {
            if (suggestion.getStatus() != SuggestionStatus.Pending  && Duration.between(suggestion.getCreatedDate().toInstant(), new Date().toInstant()).toDays() > 30) {
                s3ObjectOptimizationSuggestionRepository.delete(suggestion);
            }
        }
    }

    private void createLifecyclePolicy(String bucketName){
        StorageBucket bucket = this.storageBucketRepository.findByName(bucketName).orElseThrow(()-> new BucketNotFoundException("Bucket with name : "+bucketName+" not found..."));
        AwsAccountCredentials credentials = credentialsService.getCredentialsByAccountId(bucket.getAssociatedAccount().getAccountId());

        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(credentials.getAccessKeyId(), credentials.getSecretAccessKey())))
                .withRegion(bucket.getRegion())
                .build();

        BucketLifecycleConfiguration.Rule rule1 = new BucketLifecycleConfiguration.Rule()
                .withId("Rule1")
                .withStatus(BucketLifecycleConfiguration.ENABLED)
                .withPrefix("documents/")
                .withExpirationInDays(30);

        BucketLifecycleConfiguration.Rule rule2 = new BucketLifecycleConfiguration.Rule()
                .withId("Rule2")
                .withStatus(BucketLifecycleConfiguration.ENABLED)
                .withPrefix("logs/") // Apply the rule only to objects with this prefix
                //.addTransition(new Transition().withDays(60).withStorageClass("STANDARD_IA")) // Transition to STANDARD_IA storage class after 60 days
                .withExpirationInDays(90); // Expire objects after 90 days

        // Create rule 3
        BucketLifecycleConfiguration.Rule rule3 = new BucketLifecycleConfiguration.Rule()
                .withId("Rule3")
                .withStatus(BucketLifecycleConfiguration.ENABLED)
                .withPrefix("backups/") // Apply the rule only to objects with this prefix
                //.addTransition(new Transition().withDays(30).withStorageClass("GLACIER")) // Transition to GLACIER storage class after 30 days
                .withExpirationInDays(365);

        BucketLifecycleConfiguration lifecycleConfiguration = new BucketLifecycleConfiguration()
                .withRules(rule1, rule2, rule3);

        s3Client.setBucketLifecycleConfiguration(bucketName,lifecycleConfiguration);
        log.info("Lifecycle for bucket "+bucketName+" set.");
    }

    public List<S3OptimizationSuggestion> generateS3SuggestionsStream() {
        int parallelism = Runtime.getRuntime().availableProcessors(); // Adjust the parallelism level as needed
        ForkJoinPool forkJoinPool = new ForkJoinPool(parallelism);

        List<S3OptimizationSuggestion> suggestions = storageBucketRepository.findAll()
                .parallelStream()
                .unordered()
                .filter(bucket -> !hasLifecyclePolicy(bucket.getName()))
                .map(bucket -> S3OptimizationSuggestion.builder()
                        .title("Add Lifecycle Policy to Bucket: " + bucket.getName())
                        .description("Bucket " + bucket.getName() + " does not have a lifecycle policy. Consider adding one to " +
                                "automatically transition objects to lower-cost storage classes or delete them when they " +
                                "are no longer needed.")
                        .createdDate(LocalDateTime.now())
                        .status(SuggestionStatus.Pending)
                        .associatedAccount(bucket.getAssociatedAccount())
                        .build())
                .collect(Collectors.toList());

        return s3OptimizationSuggestionRepository.saveAll(suggestions);
    }

    public static boolean isCrossRegionReplicationEnabled(AmazonS3 s3Client, String bucketName) {
        GetBucketReplicationConfigurationRequest request = new GetBucketReplicationConfigurationRequest(bucketName);
        try {
            BucketReplicationConfiguration replicationConfig = s3Client.getBucketReplicationConfiguration(request);
            return replicationConfig != null && !replicationConfig.getRules().isEmpty();
        } catch (Exception e) {
            // Handle exceptions if the bucket doesn't have replication or if an error occurs
            return false;
        }
    }
}

