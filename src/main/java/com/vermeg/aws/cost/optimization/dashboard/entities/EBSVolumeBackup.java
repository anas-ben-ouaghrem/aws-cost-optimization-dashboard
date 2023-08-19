package com.vermeg.aws.cost.optimization.dashboard.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity(name = "ebs_volume_backup")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        name = "ebs_volume_backup"
)
public class EBSVolumeBackup {

    @Id
    @SequenceGenerator(
            name = "ebs_volume_backup_id_seq",
            sequenceName = "ebs_volume_backup_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "ebs_volume_backup_id_seq"
    )
    private Long id;

    @Column(name = "volume_id", nullable = false)
    private String volumeId;

    @Column(name = "volume_type", nullable = false)
    private String volumeType;

    @Column(name = "creation_time", nullable = false)
    private Date creationTime;

    @Column(name = "region", nullable = false)
    private String region;

    @Column(name = "size", nullable = false)
    private Long size;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "product_id", nullable = true)
    private String productId;

    @Column(name = "owner_email", nullable = true)
    private String ownerEmail;

    @Column(name = "operation_hours", nullable = true)
    private String operationHours;

    @Column(name = "environment_type", nullable = true)
    private String environmentType;

    @Column(name = "instance_id", nullable = true)
    private String instanceId;

    @Column(name = "creation_timestamp", nullable = false)
    private LocalDateTime creationTimestamp;

    @Column(name = "last_update_timestamp", nullable = false)
    private LocalDateTime lastUpdateTimestamp;

    @Column(name = "associated_account", nullable = false)
    private String associatedAccount;
}
