package com.vermeg.aws.cost.optimization.dashboard.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity(name = "eip_optimization_suggestion")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"ip_address_id", "category"}))
public class EIPOptimizationSuggestion {

    @Id
    @SequenceGenerator(
            name = "eip_optimization_suggestion_sequence",
            sequenceName = "eip_optimization_suggestion_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "eip_optimization_suggestion_sequence"
    )
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private EIPSuggestionCategory category;

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
            name = "ip_address_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "ip_address_id_fk"
            )
    )
    private EIPAddress linkedIPAddress;

    @ManyToOne
    @JoinColumn(
            name = "associated_account_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "eip_optimization_suggestion_associated_account_id_fk"
            )
    )
    private AwsAccountCredentials associatedAccount;
}
