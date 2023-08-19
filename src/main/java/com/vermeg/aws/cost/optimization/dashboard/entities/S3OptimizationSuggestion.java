package com.vermeg.aws.cost.optimization.dashboard.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "s3_optimization_suggestion")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        name = "s3_optimization_suggestion",
        uniqueConstraints = @UniqueConstraint(columnNames = {"bucket", "category"})
)
public class S3OptimizationSuggestion {
    @Id
    @SequenceGenerator(
            name = "s3_optimization_suggestion_sequence",
            sequenceName = "s3_optimization_suggestion_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "s3_optimization_suggestion_sequence"
    )
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private S3SuggestionCategory category;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "recommendation", nullable = false)
    private String recommendation;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SuggestionStatus status;

    // Relations

    @ManyToOne
    @JoinColumn(
            name = "bucket",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "bucket_fk"
            )
    )
    private StorageBucket linkedBucket;

    @ManyToOne
    @JoinColumn(
            name = "associated_account_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "s3_optimization_suggestion_associated_account_id_fk"
            )
    )
    private AwsAccountCredentials associatedAccount;
}
