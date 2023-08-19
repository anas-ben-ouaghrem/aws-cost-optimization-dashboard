package com.vermeg.aws.cost.optimization.dashboard.repositories;

import com.vermeg.aws.cost.optimization.dashboard.entities.EC2OptimizationSuggestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Ec2OptimizationSuggestionRepository extends JpaRepository<EC2OptimizationSuggestion,Long> {
}
