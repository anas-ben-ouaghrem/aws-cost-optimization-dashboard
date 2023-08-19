package com.vermeg.aws.cost.optimization.dashboard.services;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.redshift.model.BucketNotFoundException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.vermeg.aws.cost.optimization.dashboard.dto.S3BucketDTO;
import com.vermeg.aws.cost.optimization.dashboard.entities.AwsAccountCredentials;
import com.vermeg.aws.cost.optimization.dashboard.entities.StorageBucket;
import com.vermeg.aws.cost.optimization.dashboard.repositories.AwsCredentialsRepository;
import com.vermeg.aws.cost.optimization.dashboard.repositories.StorageBucketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3Service {

    private final UtilityServices utilityServices;
    private final StorageBucketRepository storageBucketRepository;
    private final AwsCredentialsRepository awsCredentialsRepository;

    @Cacheable("s3testAll")
    public List<S3BucketDTO> s3testAllRegions() {
        List<Pair<AmazonS3, String>> s3ClientsList = utilityServices.getS3ClientsList();

        Set<String> bucketNames = new HashSet<>(); // Store unique bucket names
        List<S3BucketDTO> s3BucketDTOList = new ArrayList<>(); // Store S3BucketDTO objects

        for (Pair<AmazonS3, String> pair : s3ClientsList) {
            AmazonS3 client = pair.getLeft();
            String accountId = pair.getRight();

            List<Bucket> buckets = client.listBuckets(); // Get list of buckets
            for (Bucket bucket : buckets) {
                String bucketName = bucket.getName();
                if (!bucketNames.contains(bucketName)) { // Check if bucket has already been processed
                    String bucketRegion = client.getBucketLocation(bucketName);
                    if (bucketRegion != null && !bucketRegion.isEmpty()) { // Check if bucket has a valid region
                        ListObjectsV2Result objectListing = client.listObjectsV2(bucketName);
                        int objectCount = objectListing.getKeyCount();
                        long size = objectListing.getObjectSummaries().parallelStream().mapToLong(S3ObjectSummary::getSize).sum();

                        Map<String, String> tags = new HashMap<>();
                        BucketTaggingConfiguration bucketTagging = client.getBucketTaggingConfiguration(bucketName);
                        if (bucketTagging != null) {
                            List<TagSet> tagSetList = bucketTagging.getAllTagSets();
                            if (tagSetList != null) {
                                for (TagSet tagSet : tagSetList) {
                                    Map<String, String> tagMap = tagSet.getAllTags();
                                    if (tagMap != null) {
                                        tags.putAll(tagMap);
                                    }
                                }
                            }
                        }

                        S3BucketDTO s3BucketDTO = S3BucketDTO.builder()
                                .associatedAccount(accountId)
                                .name(bucketName)
                                .region(bucketRegion)
                                .objectCount(objectCount)
                                .size(size)
                                .tags(tags)
                                .creationDate(bucket.getCreationDate())
                                .ownerName(bucket.getOwner().getDisplayName())
                                .build();
                        s3BucketDTOList.add(s3BucketDTO);
                        bucketNames.add(bucketName); // Add bucket name to set of processed buckets
                    }
                }
            }
        }

        return s3BucketDTOList;
    }

    public List<S3BucketDTO> s3testAllRegionsStream() {
        List<Pair<AmazonS3, String>> s3ClientsList = utilityServices.getS3ClientsList();

        Set<String> bucketNames = new HashSet<>(); // Store unique bucket names
        List<S3BucketDTO> s3BucketDTOList = new ArrayList<>(); // Store S3BucketDTO objects

        for (Pair<AmazonS3, String> pair : s3ClientsList) {
            AmazonS3 client = pair.getLeft();
            String accountId = pair.getRight();

            List<Bucket> buckets = client.listBuckets(); // Get list of buckets
            for (Bucket bucket : buckets) {
                String bucketName = bucket.getName();
                if (!bucketNames.contains(bucketName)) { // Check if bucket has already been processed
                    String bucketRegion = client.getBucketLocation(bucketName);
                    if (bucketRegion != null && !bucketRegion.isEmpty()) { // Check if bucket has a valid region
                        ListObjectsV2Result objectListing = client.listObjectsV2(bucketName);
                        int objectCount = objectListing.getKeyCount();
                        long size = objectListing.getObjectSummaries().stream().mapToLong(S3ObjectSummary::getSize).sum();

                        Map<String, String> tags = new HashMap<>();
                        BucketTaggingConfiguration bucketTagging = client.getBucketTaggingConfiguration(bucketName);
                        if (bucketTagging != null) {
                            List<TagSet> tagSetList = bucketTagging.getAllTagSets();
                            if (tagSetList != null) {
                                for (TagSet tagSet : tagSetList) {
                                    Map<String, String> tagMap = tagSet.getAllTags();
                                    if (tagMap != null) {
                                        tags.putAll(tagMap);
                                    }
                                }
                            }
                        }

                        S3BucketDTO s3BucketDTO = S3BucketDTO.builder()
                                .associatedAccount(accountId)
                                .name(bucketName)
                                .region(bucketRegion)
                                .objectCount(objectCount)
                                .size(size)
                                .tags(tags)
                                .creationDate(bucket.getCreationDate())
                                .ownerName(bucket.getOwner().getDisplayName())
                                .build();
                        s3BucketDTOList.add(s3BucketDTO);
                        bucketNames.add(bucketName); // Add bucket name to set of processed buckets
                    }
                }
            }
        }

        return s3BucketDTOList;
    }



    public List<StorageBucket> getAllStorageBuckets() {
        return storageBucketRepository.findAll();
    }

    @Cacheable("buckets")
    public List<S3BucketDTO> getAllBuckets() {
        List<Pair<AmazonS3, String>> s3ClientsList = utilityServices.getS3ClientsList();
        return s3ClientsList.parallelStream()
                .flatMap(pair -> {
                    AmazonS3 client = pair.getLeft();
                    String accountId = pair.getRight();
                    List<Bucket> buckets = client.listBuckets();
                    return buckets.stream().filter(bucket -> {
                        String bucketRegion = client.getBucketLocation(bucket.getName());
                        if (bucketRegion == null || bucketRegion.isEmpty()) {
                            log.info("Bucket with name " + bucket.getName() + " has an empty location, skipping...");
                            return false;
                        }
                        return bucketRegion.equals(client.getRegionName());
                    }).map(bucket -> {
                        ListObjectsV2Request listObjectsRequest = new ListObjectsV2Request()
                                .withBucketName(bucket.getName())
                                .withMaxKeys(1000);
                        ListObjectsV2Result objectListing;
                        List<S3ObjectSummary> objectSummaries = new ArrayList<>();
                        do {
                            objectListing = client.listObjectsV2(listObjectsRequest);
                            objectSummaries.addAll(objectListing.getObjectSummaries());
                            listObjectsRequest.setContinuationToken(objectListing.getNextContinuationToken());
                        } while (objectListing.isTruncated());
                        int objectCount = objectSummaries.size();
                        long size = 0;
                        for (S3ObjectSummary objectSummary : objectSummaries) {
                            size += objectSummary.getSize();
                        }

                        Map<String, String> tags = new HashMap<>();
                        BucketTaggingConfiguration bucketTagging = client.getBucketTaggingConfiguration(bucket.getName());
                        if (bucketTagging != null) {
                            List<TagSet> tagSetList = bucketTagging.getAllTagSets();
                            if (tagSetList != null) {
                                for (TagSet tagSet : tagSetList) {
                                    Map<String, String> tagMap = tagSet.getAllTags();
                                    if (tagMap != null) {
                                        tags.putAll(tagMap);
                                    }
                                }
                            }
                        }

                        return S3BucketDTO.builder()
                                .associatedAccount(accountId)
                                .name(bucket.getName())
                                .region(client.getRegionName())
                                .objectCount(objectCount)
                                .size(size)
                                .tags(tags)
                                .creationDate(bucket.getCreationDate())
                                .ownerName(bucket.getOwner().getDisplayName())
                                .build();
                    });
                }).collect(Collectors.toList());
    }


    public List<S3ObjectSummary> getObjectsByBucketName(String bucketName) throws BucketNotFoundException {
        List<Pair<AmazonS3, String>> s3ClientsList = utilityServices.getS3ClientsList();
        ObjectListing objectListing = null;
        List<S3ObjectSummary> objectSummaries = new ArrayList<>();
        for (Pair<AmazonS3, String> pair : s3ClientsList) {
            AmazonS3 client = pair.getLeft();
            String accountId = pair.getRight();
            if (client.doesBucketExistV2(bucketName)) {
                objectListing = client.listObjects(bucketName);
                while (true) {
                    List<S3ObjectSummary> summaries = objectListing.getObjectSummaries();
                    objectSummaries.addAll(summaries);
                    if (!objectListing.isTruncated()) {
                        break;
                    }
                    objectListing = client.listNextBatchOfObjects(objectListing);
                }
            }
        }
        if (objectSummaries.isEmpty()) {
            throw new BucketNotFoundException("Bucket not found or empty");
        }
        return objectSummaries;
    }


    public List<S3ObjectSummary> getObjectsByBucketNameStream(String bucketName) throws Exception {
        List<Pair<AmazonS3, String>> s3ClientsList = utilityServices.getS3ClientsList();

        Optional<ObjectListing> objectListing = s3ClientsList.stream()
                .map(pair -> {
                    AmazonS3 client = pair.getLeft();
                    if (client.doesBucketExistV2(bucketName)) {
                        return client.listObjects(bucketName);
                    } else {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .findFirst();

        if (objectListing.isPresent()) {
            List<S3ObjectSummary> objectSummaries = objectListing.get().getObjectSummaries();
            while (objectListing.get().isTruncated()) {
                Optional<ObjectListing> finalObjectListing = objectListing;
                objectListing = s3ClientsList.stream()
                        .map(pair -> {
                            AmazonS3 client = pair.getLeft();
                            if (client.doesBucketExistV2(bucketName)) {
                                return client.listNextBatchOfObjects(finalObjectListing.get());
                            } else {
                                return null;
                            }
                        })
                        .filter(Objects::nonNull)
                        .findFirst();
                objectSummaries.addAll(objectListing.get().getObjectSummaries());
            }
            return objectSummaries;
        } else {
            throw new Exception("Bucket not found");
        }
    }

    @Cacheable("buckets")
    public List<S3BucketDTO> getAllBucketsPool() {

        List<Pair<AmazonS3, String>> s3ClientsList = utilityServices.getS3ClientsList();

        ExecutorService executorService = Executors.newFixedThreadPool(s3ClientsList.size());

        List<CompletableFuture<List<S3BucketDTO>>> futures = s3ClientsList.stream()
                .map(pair -> CompletableFuture.supplyAsync(() -> {
                    AmazonS3 client = pair.getLeft();
                    String accountId = pair.getRight();

                    List<Bucket> buckets = client.listBuckets();
                    List<S3BucketDTO> s3BucketDTOList = new ArrayList<>();

                    for (Bucket bucket : buckets) {
                        String bucketRegion = client.getBucketLocation(bucket.getName());
                        if (bucketRegion == null || bucketRegion.isEmpty()) {
                            System.out.println("Bucket " + bucket.getName() + " has an empty location, skipping...");
                            continue;
                        }
                        if (!bucketRegion.equals(client.getRegionName())) {
                            continue;
                        }

                        ListObjectsV2Request listObjectsRequest = new ListObjectsV2Request()
                                .withBucketName(bucket.getName())
                                .withMaxKeys(1000);

                        List<S3ObjectSummary> objectSummaries = new ArrayList<>();
                        ListObjectsV2Result objectListing = null;

                        do {
                            objectListing = client.listObjectsV2(listObjectsRequest);
                            objectSummaries.addAll(objectListing.getObjectSummaries());
                            listObjectsRequest.setContinuationToken(objectListing.getNextContinuationToken());
                        } while (objectListing.isTruncated());

                        int objectCount = objectSummaries.size();
                        long size = 0;
                        for (S3ObjectSummary objectSummary : objectSummaries) {
                            size += objectSummary.getSize();
                        }

                        Map<String, String> tags = new HashMap<>();
                        BucketTaggingConfiguration bucketTagging = client.getBucketTaggingConfiguration(bucket.getName());
                        if (bucketTagging != null) {
                            List<TagSet> tagSetList = bucketTagging.getAllTagSets();
                            if (tagSetList != null) {
                                for (TagSet tagSet : tagSetList) {
                                    Map<String, String> tagMap = tagSet.getAllTags();
                                    if (tagMap != null) {
                                        tags.putAll(tagMap);
                                    }
                                }
                            }
                        }

                        S3BucketDTO s3BucketDTO = S3BucketDTO.builder()
                                .associatedAccount(accountId)
                                .name(bucket.getName())
                                .region(client.getRegionName())
                                .objectCount(objectCount)
                                .size(size)
                                .tags(tags)
                                .creationDate(bucket.getCreationDate())
                                .ownerName(bucket.getOwner().getDisplayName())
                                .build();

                        s3BucketDTOList.add(s3BucketDTO);
                    }

                    return s3BucketDTOList;
                }, executorService))
                .collect(Collectors.toList());

        List<S3BucketDTO> s3BucketDTOList = new ArrayList<>();
        for (CompletableFuture<List<S3BucketDTO>> future : futures) {
            s3BucketDTOList.addAll(future.join());
        }

        executorService.shutdown();

        return s3BucketDTOList;
    }

    public List<S3ObjectSummary> getAllObjectsByBucketName(String bucketName){
        StorageBucket storageBucket = storageBucketRepository.findByName(bucketName).orElseThrow(() -> new BucketNotFoundException(bucketName));
        AwsAccountCredentials credentials = awsCredentialsRepository.findAwsAccountCredentialsByAccountId(storageBucket.getAssociatedAccount().getAccountId());
        ObjectListing objectListing = null;
        List<S3ObjectSummary> objectSummaries = new ArrayList<>();
        AmazonS3 client = AmazonS3Client.builder()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(credentials.getAccessKeyId(), credentials.getSecretAccessKey())))
                .withRegion(storageBucket.getRegion())
                .build();
        if (client.doesBucketExistV2(bucketName)) {
            objectListing = client.listObjects(bucketName);
            while (true) {
                List<S3ObjectSummary> summaries = objectListing.getObjectSummaries();
                objectSummaries.addAll(summaries);
                if (!objectListing.isTruncated()) {
                    break;
                }
                objectListing = client.listNextBatchOfObjects(objectListing);
            }
        }
        return objectSummaries;
    }

    public List<S3ObjectSummary> getAllObjectsByBucketNameStream(String bucketName) {
        StorageBucket storageBucket = storageBucketRepository.findByName(bucketName).orElseThrow(() -> new  BucketNotFoundException(bucketName));
        AwsAccountCredentials credentials = awsCredentialsRepository.findAwsAccountCredentialsByAccountId(storageBucket.getAssociatedAccount().getAccountId());
        List<S3ObjectSummary> objectSummaries = Collections.synchronizedList(new ArrayList<>());

        AmazonS3 client = AmazonS3Client.builder()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(credentials.getAccessKeyId(), credentials.getSecretAccessKey())))
                .withRegion(storageBucket.getRegion())
                .build();

        if (client.doesBucketExistV2(bucketName)) {
            ExecutorService executorService = Executors.newFixedThreadPool(10);
            List<Callable<List<S3ObjectSummary>>> tasks = new ArrayList<>();
            ListObjectsV2Request listObjectsRequest = new ListObjectsV2Request().withBucketName(bucketName).withMaxKeys(1000);
            ListObjectsV2Result listObjectsResult;

            do {
                listObjectsResult = client.listObjectsV2(listObjectsRequest);
                List<S3ObjectSummary> summaries = listObjectsResult.getObjectSummaries();
                tasks.add(() -> summaries.parallelStream().toList());

                String token = listObjectsResult.getNextContinuationToken();
                listObjectsRequest.setContinuationToken(token);
            } while (listObjectsResult.isTruncated());

            try {
                List<Future<List<S3ObjectSummary>>> futures = executorService.invokeAll(tasks);
                for (Future<List<S3ObjectSummary>> future : futures) {
                    objectSummaries.addAll(future.get());
                }
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException("Failed to retrieve S3 object summaries", e);
            } finally {
                executorService.shutdown();
            }
        }
        return objectSummaries;
    }


    @Cacheable("buckets")
    public List<S3BucketDTO> getAllBucketsPoolV2() throws Exception {

        List<Pair<AmazonS3, String>> s3ClientsList = utilityServices.getS3ClientsList();

        ExecutorService executorService = Executors.newFixedThreadPool(Math.min(s3ClientsList.size() * 3, 12));

        List<CompletableFuture<S3BucketDTO>> futures = new ArrayList<>();

        for (Pair<AmazonS3, String> pair : s3ClientsList) {
            AmazonS3 client = pair.getLeft();
            String accountId = pair.getRight();

            ListBucketsRequest listBucketsRequest = new ListBucketsRequest();
            List<Bucket> buckets = client.listBuckets(listBucketsRequest);

            for (Bucket bucket : buckets) {
                String bucketRegion = client.getBucketLocation(bucket.getName());
                if (bucketRegion == null || bucketRegion.isEmpty()) {
                    log.info("Bucket " + bucket.getName() + " has an empty location, skipping...");
                    continue;
                }
                if (!bucketRegion.equals(client.getRegionName())) {
                    continue;
                }

                CompletableFuture<S3BucketDTO> future = CompletableFuture.supplyAsync(() -> {
                    ListObjectsV2Request listObjectsRequest = new ListObjectsV2Request()
                            .withBucketName(bucket.getName())
                            .withMaxKeys(1000);

                    List<S3ObjectSummary> objectSummaries = new ArrayList<>();
                    ListObjectsV2Result objectListing;

                    do {
                        objectListing = client.listObjectsV2(listObjectsRequest);
                        objectSummaries.addAll(objectListing.getObjectSummaries());
                        listObjectsRequest.setContinuationToken(objectListing.getNextContinuationToken());
                    } while (objectListing.isTruncated());

                    int objectCount = objectSummaries.size();
                    long size = 0;
                    for (S3ObjectSummary objectSummary : objectSummaries) {
                        size += objectSummary.getSize();
                    }

                    Map<String, String> tags = new HashMap<>();
                    BucketTaggingConfiguration bucketTagging = client.getBucketTaggingConfiguration(bucket.getName());
                    if (bucketTagging != null) {
                        List<TagSet> tagSetList = bucketTagging.getAllTagSets();
                        if (tagSetList != null) {
                            for (TagSet tagSet : tagSetList) {
                                Map<String, String> tagMap = tagSet.getAllTags();
                                if (tagMap != null) {
                                    tags.putAll(tagMap);
                                }
                            }
                        }
                    }

                    return S3BucketDTO.builder()
                            .associatedAccount(accountId)
                            .name(bucket.getName())
                            .region(client.getRegionName())
                            .objectCount(objectCount)
                            .size(size)
                            .tags(tags)
                            .creationDate(bucket.getCreationDate())
                            .ownerName(bucket.getOwner().getDisplayName())
                            .build();
                }, executorService);

                futures.add(future);
            }
        }

        List<S3BucketDTO> s3BucketDTOList = new ArrayList<>();
        for (CompletableFuture<S3BucketDTO> future : futures) {
            try {
                S3BucketDTO dto = future.get();
                s3BucketDTOList.add(dto);
            } catch (IllegalStateException e) {
                // handle exceptions
            }
        }

        executorService.shutdown();

        return s3BucketDTOList;
    }

}
