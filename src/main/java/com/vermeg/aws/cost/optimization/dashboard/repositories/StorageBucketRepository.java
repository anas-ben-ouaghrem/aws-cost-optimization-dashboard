package com.vermeg.aws.cost.optimization.dashboard.repositories;

import com.vermeg.aws.cost.optimization.dashboard.entities.StorageBucket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface StorageBucketRepository extends JpaRepository<StorageBucket, Long> {
    Optional<StorageBucket> findByName(String bucketName);

    boolean existsByName(String bucketName);
}
