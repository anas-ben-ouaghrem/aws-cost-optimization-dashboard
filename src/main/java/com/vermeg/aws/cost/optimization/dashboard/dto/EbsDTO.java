package com.vermeg.aws.cost.optimization.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EbsDTO {

    private Map<String, String> tags;
    private String associatedAccount;

    private String volumeId;
    private String volumeType;
    private Date creationTime;
    private String region;
    private long size;
    private String state;
    private String productId;
    private String ownerEmail;
    private String operationHours;
    private String environmentType;
    private String instanceId;
}
