package com.vermeg.aws.cost.optimization.dashboard.controllers;

import com.amazonaws.services.support.model.TrustedAdvisorCheckSummary;
import com.vermeg.aws.cost.optimization.dashboard.services.TrustedAdvisorService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class TrustedAdvisorController {

    private final TrustedAdvisorService trustedAdvisorService;

    @GetMapping("get-trusted-advisor-summaries/")
    public List<TrustedAdvisorCheckSummary> getTrustedAdvisorCheckSummaries(){
        return trustedAdvisorService.getAllTrustedAdvisorCheckSummaries();
    }
}
