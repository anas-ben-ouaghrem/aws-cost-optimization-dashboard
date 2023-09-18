package com.vermeg.aws.cost.optimization.dashboard.utilities;

import com.vermeg.aws.cost.optimization.dashboard.services.DataStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class CredentialsAspects {

    private final DataStorageService dataStorageService;

  /*  @After("execution(* com.vermeg.aws.cost.optimization.dashboard.services.AwsCredentialsService.createCredentials(..))"+ "|| execution(* com.vermeg.aws.cost.optimization.dashboard.services.AwsCredentialsService.updateCredentials(..))")
    public void afterAdvice() throws Exception {
        log.info("Credentials update api call detected, updating data storage .....");
        dataStorageService.storeEbsVolumes();
        dataStorageService.storeEipAddresses();
        dataStorageService.storeEc2Instances();
        dataStorageService.storeRdsInstances();
        dataStorageService.storeS3Buckets();
        log.info("Data storage updated");
    }*/

}
