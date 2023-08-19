package com.vermeg.aws.cost.optimization.dashboard.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity(name = "s3_object_optimization_suggestion")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "s3_object_optimization_suggestion"
)
public class S3ObjectOptimizationSuggestion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String bucketName;
    private String associatedAccount;
    private String title;
    private String description;
    private Date createdDate;
    private SuggestionStatus status;

}
