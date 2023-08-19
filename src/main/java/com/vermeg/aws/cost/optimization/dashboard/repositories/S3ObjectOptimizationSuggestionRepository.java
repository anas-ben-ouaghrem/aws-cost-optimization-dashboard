package com.vermeg.aws.cost.optimization.dashboard.repositories;

import com.vermeg.aws.cost.optimization.dashboard.entities.S3ObjectOptimizationSuggestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface S3ObjectOptimizationSuggestionRepository extends JpaRepository<S3ObjectOptimizationSuggestion, Long> {
}
