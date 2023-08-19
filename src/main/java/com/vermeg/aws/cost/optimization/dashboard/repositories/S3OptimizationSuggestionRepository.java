package com.vermeg.aws.cost.optimization.dashboard.repositories;

import com.vermeg.aws.cost.optimization.dashboard.entities.S3OptimizationSuggestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface S3OptimizationSuggestionRepository extends JpaRepository<S3OptimizationSuggestion, Long> {
}
