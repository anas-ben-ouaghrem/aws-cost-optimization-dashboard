package com.vermeg.aws.cost.optimization.dashboard.repositories;

import com.vermeg.aws.cost.optimization.dashboard.entities.EIPAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface EIPAddressRepository extends JpaRepository<EIPAddress, Long> {
    Optional<EIPAddress> findByAllocationId(String resourceId);
}
