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

@Entity(name = "ebs_volume")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        name = "ebs_volume",
        uniqueConstraints = @UniqueConstraint(columnNames = {"volume_id", "region"})
)
public class EBSVolume {
    @Id
    @SequenceGenerator(
            name = "ebs_volume_id_seq",
            sequenceName = "ebs_volume_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "ebs_volume_id_seq"
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

    @Column(name = "creation_timestamp", nullable = true)
    private LocalDateTime creationTimestamp = LocalDateTime.now();

    @Column(name = "last_update_timestamp", nullable = true)
    private LocalDateTime lastUpdateTimestamp;

    // Relations

    @OneToMany(mappedBy = "linkedVolume", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<EBSOptimizationSuggestion> suggestions;

    @ManyToOne
    @JoinColumn(
            name = "associated_account_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "ebs_volume_associated_account_id_fk"
            )
    )
    private AwsAccountCredentials associatedAccount;
}
