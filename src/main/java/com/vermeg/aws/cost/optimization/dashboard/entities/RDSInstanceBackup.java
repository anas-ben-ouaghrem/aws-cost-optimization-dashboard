package com.vermeg.aws.cost.optimization.dashboard.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "rds_instance_backup")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        name = "rds_instance_backup"
)
public class RDSInstanceBackup {

    @Id
    @SequenceGenerator(
            name = "rds_instance_backup_id_seq",
            sequenceName = "rds_instance_backup_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "rds_instance_backup_id_seq"
    )
    private Long id;

    @Column(name = "associated_account", nullable = false)
    private String associatedAccount;

    @Column(name = "db_instance_identifier", nullable = false)
    private String dbInstanceIdentifier;

    @Column(name = "db_instance_class", nullable = false)
    private String dbInstanceClass;

    @Column(name = "db_instance_status", nullable = false)
    private String status;

    @Column(name = "db_instance_engine", nullable = false)
    private String engine;

    @Column(name = "db_instance_engine_version", nullable = false)
    private String engineVersion;

    @Column(name = "region", nullable = false)
    private String region;

    @Column(name = "owner_email", nullable = true)
    private String ownerEmail;

    @Column(name = "product_id", nullable = true)
    private String productId;

    @Column(name = "operation_hours", nullable = true)
    private String operationHours;

    @Column(name = "client_name", nullable = true)
    private String clientName;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "allocated_storage", nullable = false)
    private int allocatedStorage;

    @Column(name = "endpoint_address", nullable = false)
    private String endpointAddress;

    @Column(name = "endpoint_port", nullable = false)
    private int endpointPort;

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
}
