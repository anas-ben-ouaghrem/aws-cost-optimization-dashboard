package com.vermeg.aws.cost.optimization.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ec2DTO {

    private Map<String, String> tags;
    private String associatedAccount;

    private String instanceId;
    private String instanceType;
    private String platform;
    private String region;
    private String state;
    private String monitoring;
    private String publicIp;
    private String privateIp;


}
