package com.vermeg.aws.cost.optimization.dashboard.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity(name = "ec2_optimization_suggestion")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        name = "ec2_optimization_suggestion",
        uniqueConstraints = @UniqueConstraint(columnNames = {"instance_id", "category"})
)
public class EC2OptimizationSuggestion {
    @Id
    @SequenceGenerator(
            name = "ec2_optimization_suggestion_sequence",
            sequenceName = "ec2_optimization_suggestion_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "ec2_optimization_suggestion_sequence"
    )
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private EC2SuggestionCategory category;

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
            name = "instance_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "ec2_optimization_suggestion_ec2_instance_id_fk"
            )
    )
    private EC2Instance linkedInstance;

    @ManyToOne
    @JoinColumn(
            name = "associated_account",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "ec2_optimization_suggestion_associated_account_fk"
            )
    )
    private AwsAccountCredentials associatedAccount;
}
