package com.vermeg.aws.cost.optimization.dashboard.controllers;


import com.vermeg.aws.cost.optimization.dashboard.entities.EBSVolume;
import com.vermeg.aws.cost.optimization.dashboard.entities.EC2Instance;
import com.vermeg.aws.cost.optimization.dashboard.entities.EIPAddress;
import com.vermeg.aws.cost.optimization.dashboard.services.ComputeServices;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class ComputeController {

    private final ComputeServices computeServices;

    @GetMapping("/getEc2")
    public List<EC2Instance> getEc2Instances(){
        return computeServices.getAllInstances();
    }

    @GetMapping("/getEbs")
    public List<EBSVolume> getEbsVolumes(){
        return computeServices.getAllVolumes();
    }

    @GetMapping("/getEip")
    public List<EIPAddress> getEipAddresses(){
        return computeServices.getAllAddresses();
    }
}
