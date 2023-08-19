package com.vermeg.aws.cost.optimization.dashboard.controllers;

import com.vermeg.aws.cost.optimization.dashboard.entities.AwsAccountCredentials;
import com.vermeg.aws.cost.optimization.dashboard.services.AwsCredentialsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@CrossOrigin("*")
public class AWSAccountController {

    private final AwsCredentialsService awsService;

    @PostMapping("add-account-credentials")
    public AwsAccountCredentials addCredentials(@RequestBody AwsAccountCredentials credentials) {
        return this.awsService.createCredentials(credentials);
    }

    @PutMapping("update-account-credentials")
    public AwsAccountCredentials updateCredentials(Long credentialsId, String name, String accessKeyId, String secretAccessKey){
        return this.awsService.updateCredentials(credentialsId,name,accessKeyId,secretAccessKey);
    }

    @DeleteMapping("delete-account-credentials/{credentialsId}")
    public void deleteCredentials(@PathVariable Long credentialsId){
        this.awsService.deleteCredentials(credentialsId);
    }


    @GetMapping("get-account-credentials/{name}")
    public AwsAccountCredentials getCredentialsByName(@PathVariable String name){
        return this.awsService.getCredentialsByName(name);
    }

    @GetMapping("get-account-credentials")
    public List<AwsAccountCredentials> getCredentials(){
        return this.awsService.getAllAwsCredentials();
    }

}
