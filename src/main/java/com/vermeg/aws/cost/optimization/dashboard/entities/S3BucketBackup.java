package com.vermeg.aws.cost.optimization.dashboard.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity(name = "s3_bucket_backup")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        name = "s3_bucket_backup"
)
public class S3BucketBackup {

    @Id
    @SequenceGenerator(
            name = "s3_bucket_backup_id_seq",
            sequenceName = "s3_bucket_backup_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "s3_bucket_backup_id_seq"
    )
    private Long id;

    @Column(name = "associated_account", nullable = false)
    private String associatedAccount;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "region", nullable = false)
    private String region;

    @Column(name = "creation_date", nullable = false)
    private Date creationDate;

    @Column(name = "owner", nullable = false)
    private String owner;

    @Column(name = "object_count", nullable = false)
    private Long objectCount;

    @Column(name = "size", nullable = false)
    private Long size;

    @Column(name = "owner_email", nullable = true)
    private String ownerEmail;

    @Column(name = "purpose", nullable = true)
    private String purpose;

    @Column(name = "monitoring_enabled", nullable = true)
    private boolean monitoringEnabled;

}
