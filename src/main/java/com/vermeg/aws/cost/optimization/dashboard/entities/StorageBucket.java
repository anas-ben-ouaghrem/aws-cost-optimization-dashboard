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

@Entity(name = "storage_bucket")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        name = "storage_bucket",
        uniqueConstraints = @UniqueConstraint(columnNames = {"name", "region"})
)
public class StorageBucket {
    @Id
    @SequenceGenerator(
            name = "storage_bucket_id_seq",
            sequenceName = "storage_bucket_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "storage_bucket_id_seq"
    )
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "region", nullable = false)
    private String region;

    @Column(name = "creation_date", nullable = false)
    private Date creationDate;

    @Column(name = "owner", nullable = true)
    private String owner;

    @Column(name = "object_count", nullable = false)
    private long objectCount;

    @Column(name = "size", nullable = false)
    private long size;

    @Column(name = "owner_email", nullable = true)
    private String ownerEmail;

    @Column(name = "purpose", nullable = true)
    private String purpose;

    @Column(name = "monitoring", nullable = true)
    private boolean monitoringEnabled;

    @Column(name = "creation_timestamp", nullable = false)
    private LocalDateTime creationTimestamp;

    @Column(name = "last_update_timestamp", nullable = false)
    private LocalDateTime lastUpdateTimestamp;

    // Relations

    @OneToMany(mappedBy = "linkedBucket", cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<S3OptimizationSuggestion> suggestions;

    @ManyToOne
    @JoinColumn(
            name = "associated_account_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "storage_bucket_associated_account_id_fk"
            )
    )
    private AwsAccountCredentials associatedAccount;
}

