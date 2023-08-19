package com.vermeg.aws.cost.optimization.dashboard.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "rds_optimization_suggestion")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        name = "rds_optimization_suggestion",
        uniqueConstraints = @UniqueConstraint(columnNames = {"db_instance_id", "category"})
)
public class RDSOptimizationSuggestion {

    @Id
    @SequenceGenerator(
            name = "rds_optimization_suggestion_sequence",
            sequenceName = "rds_optimization_suggestion_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "rds_optimization_suggestion_sequence"
    )
    private Long id;

    @Enumerated(EnumType.STRING)
    private RDSSuggestionCategory category;
    private String title;
    private String description;
    private String recommendation;
    private LocalDateTime createdDate;
    private SuggestionStatus status;

    // Relations

    @ManyToOne
    @JoinColumn(
            name = "db_instance_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "db_instance_id_fk"
            )
    )
    private RDSInstance linkedInstance;

    @ManyToOne
    @JoinColumn(
            name = "associated_account_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "rds_optimization_suggestion_associated_account_id_fk"
            )
    )
    private AwsAccountCredentials associatedAccount;
}
