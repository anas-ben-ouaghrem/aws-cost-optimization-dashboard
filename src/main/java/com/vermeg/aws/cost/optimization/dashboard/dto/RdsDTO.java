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
public class RdsDTO {

    private Map<String, String> tags;
    private String associatedAccount;
    private String dbInstanceIdentifier;
    private String dbInstanceClass;
    private String status;
    private String engine;
    private String engineVersion;
    private String region;
    private Date creationDate;
    private boolean monitoringEnabled;
    private int allocatedStorage;
    private String endpointAddress;
    private int endpointPort;
}
