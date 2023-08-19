package com.vermeg.aws.cost.optimization.dashboard.services;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClientBuilder;
import com.amazonaws.services.identitymanagement.model.ListAccountAliasesRequest;
import com.amazonaws.services.identitymanagement.model.ListAccountAliasesResult;
import com.amazonaws.services.importexport.model.InvalidAccessKeyIdException;
import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClientBuilder;
import com.amazonaws.services.securitytoken.model.GetCallerIdentityRequest;
import com.amazonaws.services.securitytoken.model.GetCallerIdentityResult;
import com.vermeg.aws.cost.optimization.dashboard.entities.AwsAccountCredentials;
import com.vermeg.aws.cost.optimization.dashboard.repositories.AwsCredentialsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AwsCredentialsService {

    private final AwsCredentialsRepository awsCredentialsRepository;
    private static final String ERROR_MESSAGE = "Invalid AWS credentials ID";

    public AwsAccountCredentials createCredentials(AwsAccountCredentials credentials) {
        credentials.setDate(LocalDateTime.now());
        try {
            credentials.setAccountId(getAccountId(credentials.getAccessKeyId(), credentials.getSecretAccessKey()));
            credentials.setAccountName(getAwsAccountAlias(credentials.getAccessKeyId(), credentials.getSecretAccessKey()));
            return awsCredentialsRepository.save(credentials);
        } catch (Exception e) {
            throw new InvalidAccessKeyIdException("Invalid AWS credentials");
        }
    }

    public List<AwsAccountCredentials> getAllAwsCredentials() {
        return awsCredentialsRepository.findAll();
    }

    public AwsAccountCredentials updateCredentials(Long credentialsId, String name, String accessKeyID, String secretAccessKey){
        AwsAccountCredentials credentials = awsCredentialsRepository.findById(credentialsId).orElseThrow( () -> new RuntimeException(ERROR_MESSAGE));
        credentials.setAccountName(name);
        credentials.setAccessKeyId(accessKeyID);
        credentials.setSecretAccessKey(secretAccessKey);
        return awsCredentialsRepository.save(credentials);
    }

    public AwsAccountCredentials getCredentialsById(Long credentialsId){
        return awsCredentialsRepository.findById(credentialsId).orElseThrow( () -> new RuntimeException(ERROR_MESSAGE));
    }

    public AwsAccountCredentials getCredentialsByName(String name){
        return awsCredentialsRepository.findAwsAccountCredentialsByAccountName(name);
    }

    public AwsAccountCredentials getCredentialsByAccountId(String accountID){
        return awsCredentialsRepository.findAwsAccountCredentialsByAccountId(accountID);
    }

    public List<AWSCredentials> getAllCredentials(){
        List<AwsAccountCredentials> credentials = awsCredentialsRepository.findAll();
        List<AWSCredentials> result = new ArrayList<>();
        for (AwsAccountCredentials credential : credentials){
            result.add(new BasicAWSCredentials(credential.getAccessKeyId(), credential.getSecretAccessKey()));
        }
        return result;
    }

    public void deleteCredentials(Long credentialsID){
        awsCredentialsRepository.deleteById(credentialsID);
    }

    public AWSCredentials getAwsCredentials(Long awsCredentialsId){
        AwsAccountCredentials credentials = awsCredentialsRepository.findById(awsCredentialsId).orElse(null);
        if (credentials == null) {
            // Credentials not found in the database
            throw new IllegalArgumentException(ERROR_MESSAGE);
        }
        return new BasicAWSCredentials(credentials.getAccessKeyId(), credentials.getSecretAccessKey());
    }



    public  String getAccountId(String accessKey, String secretKey) {

        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

        AWSSecurityTokenService sts = AWSSecurityTokenServiceClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.DEFAULT_REGION)
                .build();

        GetCallerIdentityRequest request = new GetCallerIdentityRequest();

        GetCallerIdentityResult result = sts.getCallerIdentity(request);

        return result.getAccount();
    }

    public String getAwsAccountAlias(String accessKey, String secretKey) {

        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

        AmazonIdentityManagement iamClient = AmazonIdentityManagementClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();

        ListAccountAliasesRequest request = new ListAccountAliasesRequest();

        ListAccountAliasesResult result = iamClient.listAccountAliases(request);

        if (!result.getAccountAliases().isEmpty()) {
            return result.getAccountAliases().get(0);
        } else {
            return "No Alias Found";
        }
    }
}
