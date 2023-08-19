package com.vermeg.aws.cost.optimization.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EipDTO {
    private String associatedAccount;

    private String allocationId;
    private String associationId;
    private String publicIp;
    private String privateIp;
    private String instanceId;
}
