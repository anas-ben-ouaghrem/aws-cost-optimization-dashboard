package com.vermeg.aws.cost.optimization.dashboard.controllers;

import com.vermeg.aws.cost.optimization.dashboard.entities.AwsAccountCredentials;
import com.vermeg.aws.cost.optimization.dashboard.services.AwsCredentialsService;
import com.vermeg.aws.cost.optimization.dashboard.services.DataStorageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/management/aws")
@CrossOrigin("*")
@Slf4j
public class AWSAccountController {

    private final AwsCredentialsService awsService;
    private final DataStorageService dataStorageService;

    @PostMapping("/add-account-credentials")
    public AwsAccountCredentials addCredentials(@RequestBody AwsAccountCredentials credentials) {
        return this.awsService.createCredentials(credentials);
    }

    @PutMapping("/update-account-credentials")
    public AwsAccountCredentials updateCredentials(Long credentialsId, String name, String accessKeyId, String secretAccessKey){
        return this.awsService.updateCredentials(credentialsId,name,accessKeyId,secretAccessKey);
    }

    @DeleteMapping("/delete-account-credentials/{credentialsId}")
    public void deleteCredentials(@PathVariable Long credentialsId){
        this.awsService.deleteCredentials(credentialsId);
    }


    @GetMapping("/get-account-credentials/{name}")
    public AwsAccountCredentials getCredentialsByName(@PathVariable String name){
        return this.awsService.getCredentialsByName(name);
    }

    @GetMapping("/get-account-credentials")
    public List<AwsAccountCredentials> getCredentials(){
        return this.awsService.getAllAwsCredentials();
    }

    @PostMapping("/refresh-data")
    public ResponseEntity<String> storeDataUponAccountAddCall() {
        return ResponseEntity.ok(this.storeDataUponAccountAdd());
    }

    public String storeDataUponAccountAdd() {
        log.info("AWS Account added, updating data storage .....");
        dataStorageService.storeEbsVolumes();
        dataStorageService.storeEipAddresses();
        dataStorageService.storeEc2Instances();
        dataStorageService.storeRdsInstances();
        dataStorageService.storeS3Buckets();
        log.info("Data storage updated");
        return "Account Data stored";
    }
}
