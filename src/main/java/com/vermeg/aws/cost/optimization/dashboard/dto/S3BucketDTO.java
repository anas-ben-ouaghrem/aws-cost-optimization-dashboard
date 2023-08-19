package com.vermeg.aws.cost.optimization.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class S3BucketDTO {
    private String name;
    private Date creationDate;
    private String ownerName;
    private String region;
    private long objectCount;
    private long size;
    private Map<String, String> tags;
    private String associatedAccount;
}

