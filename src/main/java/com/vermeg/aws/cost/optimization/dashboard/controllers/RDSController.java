package com.vermeg.aws.cost.optimization.dashboard.controllers;

import com.vermeg.aws.cost.optimization.dashboard.entities.RDSInstance;
import com.vermeg.aws.cost.optimization.dashboard.services.RDSService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class RDSController {

    private final RDSService rdsService;

    @GetMapping("/getRDS")
    public List<RDSInstance> getRDSInstances(){
        return rdsService.getAllRdsInstances();
    }


}
