package com.vermeg.aws.cost.optimization.dashboard.controllers;


import com.vermeg.aws.cost.optimization.dashboard.entities.*;
import com.vermeg.aws.cost.optimization.dashboard.services.ComputeSuggestionsService;
import com.vermeg.aws.cost.optimization.dashboard.services.RdsSuggestionsService;
import com.vermeg.aws.cost.optimization.dashboard.services.S3SuggestionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/suggestions")
@RequiredArgsConstructor
@CrossOrigin("*")
public class OptimizationSuggestionsController {

    // Local Variables
    private final ComputeSuggestionsService computeSuggestionsService;
    private final S3SuggestionsService s3SuggestionsService;
    private final RdsSuggestionsService rdsSuggestionsService;

    // Endpoints
    @GetMapping("/ec2")
    public List<EC2OptimizationSuggestion> getEc2Suggestion(){
        return computeSuggestionsService.getAllEc2Suggestions();
    }
    @GetMapping("/s3")
    public List<S3OptimizationSuggestion> getS3Suggestion(){
        return s3SuggestionsService.getAllS3Suggestions();
    }

    @GetMapping("/rds")
    public List<RDSOptimizationSuggestion> getRdsSuggestion(){
        return rdsSuggestionsService.getAllRdsSuggestions();
    }

    @GetMapping("/ebs")
    public List<EBSOptimizationSuggestion> getEbsSuggestion(){
        return computeSuggestionsService.getAllEbsSuggestions();
    }

    @GetMapping("/eip")
    public List<EIPOptimizationSuggestion> getEipSuggestion(){
        return computeSuggestionsService.getAllEipSuggestions();
    }

    @PostMapping("/ec2/{instanceId}")
    public List<EC2OptimizationSuggestion> addEc2Suggestion(@PathVariable String instanceId){
        return computeSuggestionsService.generateEc2SuggestionsWithInstanceId(instanceId);
    }

    @PostMapping("/s3")
    public void addS3Suggestion(){
        s3SuggestionsService.generateS3Suggestions();
    }
}
