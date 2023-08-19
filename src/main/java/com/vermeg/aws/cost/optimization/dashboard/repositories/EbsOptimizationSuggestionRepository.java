package com.vermeg.aws.cost.optimization.dashboard.repositories;

import com.vermeg.aws.cost.optimization.dashboard.entities.EBSOptimizationSuggestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EbsOptimizationSuggestionRepository extends JpaRepository<EBSOptimizationSuggestion,Long> {
}
