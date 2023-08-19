package com.vermeg.aws.cost.optimization.dashboard.controllers;

import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.vermeg.aws.cost.optimization.dashboard.entities.StorageBucket;
import com.vermeg.aws.cost.optimization.dashboard.services.S3Service;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@CrossOrigin("*")
public class S3Controller {
    private final S3Service s3Service;

    @GetMapping("/getS3")
    public List<StorageBucket> getS3Buckets() {
        return s3Service.getAllStorageBuckets();
    }

    @GetMapping("/getObjectsByBucket/{bucketName}")
    public List<S3ObjectSummary> getS3BucketObjects(@PathVariable String bucketName) {
        return s3Service.getAllObjectsByBucketNameStream(bucketName);
    }

}