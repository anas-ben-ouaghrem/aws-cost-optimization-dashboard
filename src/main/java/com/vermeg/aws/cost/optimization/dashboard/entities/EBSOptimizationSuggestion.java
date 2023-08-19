package com.vermeg.aws.cost.optimization.dashboard.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity(name = "ebs_optimization_suggestion")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        name = "ebs_optimization_suggestion",
        uniqueConstraints = @UniqueConstraint(columnNames = {"volume_id", "category"})
)
public class EBSOptimizationSuggestion {
    @Id
    @SequenceGenerator(
            name = "ebs_optimization_suggestion_sequence",
            sequenceName = "ebs_optimization_suggestion_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "ebs_optimization_suggestion_sequence"
    )
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private EBSSuggestionCategory category;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "recommendation", nullable = false)
    private String recommendation;

    @Column(name = "created_date", nullable = false)
    private Date createdDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SuggestionStatus status;

    // Relations

    @ManyToOne
    @JoinColumn(
            name = "volume_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "ebs_optimization_suggestion_volume_id_fk"
            )
    )
    private EBSVolume linkedVolume;

    @ManyToOne
    @JoinColumn(
            name = "associated_account_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "ebs_optimization_suggestion_associated_account_id_fk"
            )
    )
    private AwsAccountCredentials associatedAccount;
}
