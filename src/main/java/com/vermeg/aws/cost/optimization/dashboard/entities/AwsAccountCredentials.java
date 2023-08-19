package com.vermeg.aws.cost.optimization.dashboard.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "aws_account_credentials")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(
        name = "aws_account_credentials",
        uniqueConstraints = @UniqueConstraint(columnNames = {"access_key_id", "secret_access_key"})
)
public class AwsAccountCredentials {

    @Id
    @SequenceGenerator(
            name = "aws_account_credentials_id_seq",
            sequenceName = "aws_account_credentials_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "aws_account_credentials_id_seq"
    )
    private Long id;

    @Column(name = "access_key_id",nullable = false)
    private String accessKeyId;

    @Column(name = "secret_access_key",nullable = false)
    private String secretAccessKey;

    @Column(name = "account_name",nullable = false)
    private String accountName;

    @Column(name = "account_id",nullable = false)
    private String accountId;

    @Column(name = "date",nullable = false)
    private LocalDateTime date = LocalDateTime.now();

    // Relations
    @OneToMany(mappedBy = "associatedAccount", cascade = {CascadeType.REMOVE, CascadeType.PERSIST},fetch = FetchType.LAZY)
    @JsonIgnore
    private List<EC2Instance> ec2Instances;

    @OneToMany(mappedBy = "associatedAccount", cascade = {CascadeType.REMOVE, CascadeType.PERSIST},fetch = FetchType.LAZY)
    @JsonIgnore
    private List<EBSVolume> ebsVolumes;

    @OneToMany(mappedBy = "associatedAccount", cascade = {CascadeType.REMOVE, CascadeType.PERSIST},fetch = FetchType.LAZY)
    @JsonIgnore
    private List<StorageBucket> storageBuckets;

    @OneToMany(mappedBy = "associatedAccount", cascade = {CascadeType.REMOVE, CascadeType.PERSIST},fetch = FetchType.LAZY)
    @JsonIgnore
    private List<RDSInstance> rdsInstances;

    @OneToMany(mappedBy = "associatedAccount", cascade = {CascadeType.REMOVE, CascadeType.PERSIST},fetch = FetchType.LAZY)
    @JsonIgnore
    private List<EIPAddress> eipAddresses;

    @OneToMany(mappedBy = "associatedAccount", cascade = {CascadeType.REMOVE, CascadeType.PERSIST},fetch = FetchType.LAZY)
    @JsonIgnore
    private List<EC2OptimizationSuggestion> ec2OptimizationSuggestions;

    @OneToMany(mappedBy = "associatedAccount", cascade = {CascadeType.REMOVE, CascadeType.PERSIST},fetch = FetchType.LAZY)
    @JsonIgnore
    private List<EBSOptimizationSuggestion> ebsOptimizationSuggestions;

    @OneToMany(mappedBy = "associatedAccount", cascade = {CascadeType.REMOVE, CascadeType.PERSIST},fetch = FetchType.LAZY)
    @JsonIgnore
    private List<S3OptimizationSuggestion> s3OptimizationSuggestions;

    @OneToMany(mappedBy = "associatedAccount", cascade = {CascadeType.REMOVE, CascadeType.PERSIST},fetch = FetchType.LAZY)
    @JsonIgnore
    private List<RDSOptimizationSuggestion> rdsOptimizationSuggestions;

    @OneToMany(mappedBy = "associatedAccount", cascade = {CascadeType.REMOVE, CascadeType.PERSIST},fetch = FetchType.LAZY)
    @JsonIgnore
    private List<EIPOptimizationSuggestion> eipOptimizationSuggestions;

}