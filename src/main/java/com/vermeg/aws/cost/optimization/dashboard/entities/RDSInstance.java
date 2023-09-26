package com.vermeg.aws.cost.optimization.dashboard.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Entity(name = "rds_instance")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(
        name = "rds_instance",
        uniqueConstraints = @UniqueConstraint(columnNames = {"db_instance_identifier", "region"})
)
public class RDSInstance  {
    @Id
    @SequenceGenerator(
            name = "rds_instance_id_seq",
            sequenceName = "rds_instance_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "rds_instance_id_seq"
    )
    private Long id;

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

    @Column(name = "environment_type", nullable = true)
    private String clientName;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate = LocalDateTime.now();

    @Column(name = "allocated_storage", nullable = false)
    private int allocatedStorage;

    @Column(name = "endpoint_address", nullable = false)
    private String endpointAddress;

    @Column(name = "endpoint_port", nullable = false)
    private int endpointPort;

    @Column(name = "monitoring", nullable = true)
    private boolean monitoringEnabled;

    @Column(name = "creation_timestamp", nullable = false)
    private LocalDateTime creationTimestamp = LocalDateTime.now();

    @Column(name = "last_update_timestamp", nullable = true)
    private LocalDateTime lastUpdateTimestamp;

    @Column(name = "last_downtime_timestamp", nullable = true)
    private LocalDateTime lastDowntimeTimestamp;

    @Column(name = "last_uptime_timestamp", nullable = true)
    private LocalDateTime lastUptimeTimestamp;

    // Relations

    @OneToMany(mappedBy = "linkedInstance", fetch = FetchType.LAZY, cascade ={CascadeType.REMOVE, CascadeType.PERSIST})
    @JsonIgnore
    private Set<RDSOptimizationSuggestion> linkedSuggestion;

    @ManyToOne
    @JoinColumn(
            name = "associated_account_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "rds_instance_associated_account_id_fk"
            )
    )
    private AwsAccountCredentials associatedAccount;
}
