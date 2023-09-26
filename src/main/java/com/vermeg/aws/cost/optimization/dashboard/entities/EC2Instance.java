package com.vermeg.aws.cost.optimization.dashboard.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Entity(name = "ec2_instance")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "ec2_instance",
        uniqueConstraints = @UniqueConstraint(columnNames = {"instance_id", "region"})
)
public class EC2Instance {
    @Id
    @SequenceGenerator(
            name = "ec2_instance_id_seq",
            sequenceName = "ec2_instance_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "ec2_instance_id_seq"
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

    @Column(name = "instance_name", nullable = true)
    private String clientName;

    @Column(name = "monitoring", nullable = true)
    private String monitoring;

    @Column(name = "creation_timestamp", nullable = false)
    private LocalDateTime creationTimestamp = LocalDateTime.now();

    @Column(name = "last_update_timestamp", nullable = true)
    private LocalDateTime lastUpdateTimestamp;

    @Column(name = "last_downtime_timestamp", nullable = true)
    private LocalDateTime lastDowntimeTimestamp;

    @Column(name = "last_uptime_timestamp", nullable = true)
    private LocalDateTime lastUptimeTimestamp;

    @OneToMany(mappedBy = "linkedInstance", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @JsonIgnore
    private Set<EC2OptimizationSuggestion> linkedSuggestion;

    @ManyToOne
    @JoinColumn(
            name = "associated_account",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "ec2_instance_associated_account_fk"
            )
    )
    private AwsAccountCredentials associatedAccount;
}
