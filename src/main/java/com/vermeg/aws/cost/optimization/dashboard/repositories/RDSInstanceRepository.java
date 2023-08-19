package com.vermeg.aws.cost.optimization.dashboard.repositories;

import com.vermeg.aws.cost.optimization.dashboard.entities.RDSInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface RDSInstanceRepository extends JpaRepository<RDSInstance, Long> {

    Optional<RDSInstance> findByDbInstanceIdentifier(String resourceId);
}
