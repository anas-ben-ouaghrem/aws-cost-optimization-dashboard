package com.vermeg.aws.cost.optimization.dashboard.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "ec2_instance_backup")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        name = "ec2_instance_backup"
)
public class EC2InstanceBackup {

    @Id
    @SequenceGenerator(
            name = "ec2_instance_backup_id_seq",
            sequenceName = "ec2_instance_backup_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "ec2_instance_backup_id_seq"
    )
    private Long id;

    @Column(name = "instance_id", nullable = false)
    private String instanceId;

    @Column(name = "instance_type", nullable = false)
    private String instanceType;

    @Column(name = "platform", nullable = true)
    private String platform;

    @Column(name = "region", nullable = false)
    private String region;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "public_ip", nullable = true)
    private String publicIp;

    @Column(name = "private_ip", nullable = true)
    private String privateIp;

    @Column(name = "product_id", nullable = true)
    private String productId;

    @Column(name = "owner_email", nullable = true)
    private String ownerEmail;

    @Column(name = "environment_type", nullable = true)
    private String environmentType;

    @Column(name = "operation_hours", nullable = true)
    private String operationHours;

    @Column(name = "client_name", nullable = true)
    private String clientName;

    @Column(name = "monitoring", nullable = true)
    private boolean monitoringEnabled;

    @Column(name = "creation_timestamp", nullable = false)
    private LocalDateTime creationTimestamp;

    @Column(name = "last_update_timestamp", nullable = true)
    private LocalDateTime lastUpdateTimestamp;

    @Column(name = "last_downtime_timestamp", nullable = true)
    private LocalDateTime lastDowntimeTimestamp;

    @Column(name = "last_uptime_timestamp", nullable = true)
    private LocalDateTime lastUptimeTimestamp;

    @Column(name = "associated_account", nullable = false)
    private String associatedAccount;
}
