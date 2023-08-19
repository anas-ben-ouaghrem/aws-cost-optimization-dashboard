package com.vermeg.aws.cost.optimization.dashboard.repositories;

import com.vermeg.aws.cost.optimization.dashboard.entities.EBSVolume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository

public interface EBSVolumeRepository extends JpaRepository<EBSVolume, Long> {
    Optional<EBSVolume> findByVolumeId(String resourceId);
}
