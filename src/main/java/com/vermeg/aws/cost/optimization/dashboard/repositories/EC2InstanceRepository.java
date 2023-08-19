package com.vermeg.aws.cost.optimization.dashboard.repositories;

import com.vermeg.aws.cost.optimization.dashboard.entities.EC2Instance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface EC2InstanceRepository extends JpaRepository<EC2Instance, Long> {
    Optional<EC2Instance> findByInstanceId(String instanceId);

}
