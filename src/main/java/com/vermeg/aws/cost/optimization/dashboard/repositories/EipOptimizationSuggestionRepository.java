package com.vermeg.aws.cost.optimization.dashboard.repositories;

import com.vermeg.aws.cost.optimization.dashboard.entities.EIPOptimizationSuggestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface EipOptimizationSuggestionRepository extends JpaRepository<EIPOptimizationSuggestion,Long> {
}
