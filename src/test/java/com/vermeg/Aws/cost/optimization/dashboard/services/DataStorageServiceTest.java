package com.vermeg.aws.cost.optimization.dashboard.services;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.vermeg.aws.cost.optimization.dashboard.dto.EbsDTO;
import com.vermeg.aws.cost.optimization.dashboard.dto.Ec2DTO;
import com.vermeg.aws.cost.optimization.dashboard.dto.EipDTO;
import com.vermeg.aws.cost.optimization.dashboard.dto.RdsDTO;
import com.vermeg.aws.cost.optimization.dashboard.dto.S3BucketDTO;
import com.vermeg.aws.cost.optimization.dashboard.entities.AwsAccountCredentials;
import com.vermeg.aws.cost.optimization.dashboard.entities.EBSVolume;
import com.vermeg.aws.cost.optimization.dashboard.entities.EC2Instance;
import com.vermeg.aws.cost.optimization.dashboard.entities.EIPAddress;
import com.vermeg.aws.cost.optimization.dashboard.entities.RDSInstance;
import com.vermeg.aws.cost.optimization.dashboard.entities.StorageBucket;
import com.vermeg.aws.cost.optimization.dashboard.repositories.EBSVolumeRepository;
import com.vermeg.aws.cost.optimization.dashboard.repositories.EC2InstanceRepository;
import com.vermeg.aws.cost.optimization.dashboard.repositories.EIPAddressRepository;
import com.vermeg.aws.cost.optimization.dashboard.repositories.EbsVolumeBackupRepository;
import com.vermeg.aws.cost.optimization.dashboard.repositories.Ec2InstanceBackupRepository;
import com.vermeg.aws.cost.optimization.dashboard.repositories.EipAddressBackupRepository;
import com.vermeg.aws.cost.optimization.dashboard.repositories.RDSInstanceRepository;
import com.vermeg.aws.cost.optimization.dashboard.repositories.RdsInstanceBackupRepository;
import com.vermeg.aws.cost.optimization.dashboard.repositories.S3BucketBackupRepository;
import com.vermeg.aws.cost.optimization.dashboard.repositories.StorageBucketRepository;

import java.time.LocalDate;
import java.time.ZoneOffset;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {DataStorageService.class})
@ExtendWith(SpringExtension.class)
class DataStorageServiceTest {
    @MockBean
    private AwsCredentialsService awsCredentialsService;

    @MockBean
    private ComputeServices computeServices;

    @Autowired
    private DataStorageService dataStorageService;

    @MockBean
    private EBSVolumeRepository eBSVolumeRepository;

    @MockBean
    private EC2InstanceRepository eC2InstanceRepository;

    @MockBean
    private EIPAddressRepository eIPAddressRepository;

    @MockBean
    private EbsVolumeBackupRepository ebsVolumeBackupRepository;

    @MockBean
    private Ec2InstanceBackupRepository ec2InstanceBackupRepository;

    @MockBean
    private EipAddressBackupRepository eipAddressBackupRepository;

    @MockBean
    private RDSInstanceRepository rDSInstanceRepository;

    @MockBean
    private RDSService rDSService;

    @MockBean
    private RdsInstanceBackupRepository rdsInstanceBackupRepository;

    @MockBean
    private S3BucketBackupRepository s3BucketBackupRepository;

    @MockBean
    private S3Service s3Service;

    @MockBean
    private StorageBucketRepository storageBucketRepository;

    /**
     * Method under test: {@link DataStorageService#storeEc2Instances()}
     */
    @Test
    void testStoreEc2Instances() {
        when(computeServices.getAllEC2InstancesWithAccountId()).thenReturn(new ArrayList<>());
        when(computeServices.getAllInstances()).thenReturn(new ArrayList<>());
        when(eC2InstanceRepository.saveAllAndFlush(Mockito.<Iterable<EC2Instance>>any())).thenReturn(new ArrayList<>());
        when(eC2InstanceRepository.findAll()).thenReturn(new ArrayList<>());
        doNothing().when(eC2InstanceRepository).deleteAll(Mockito.<Iterable<EC2Instance>>any());
        dataStorageService.storeEc2Instances();
        verify(computeServices).getAllEC2InstancesWithAccountId();
        verify(computeServices).getAllInstances();
        verify(eC2InstanceRepository).saveAllAndFlush(Mockito.<Iterable<EC2Instance>>any());
        verify(eC2InstanceRepository).findAll();
        verify(eC2InstanceRepository).deleteAll(Mockito.<Iterable<EC2Instance>>any());
    }

    /**
     * Method under test: {@link DataStorageService#storeEc2Instances()}
     */
    @Test
    void testStoreEc2Instances2() {
        AwsAccountCredentials awsAccountCredentials = new AwsAccountCredentials();
        awsAccountCredentials.setAccessKeyId("EXAMPLEakiAIOSFODNN7");
        awsAccountCredentials.setAccountId("42");
        awsAccountCredentials.setAccountName("Dr Jane Doe");
        awsAccountCredentials.setDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        awsAccountCredentials.setEbsOptimizationSuggestions(new ArrayList<>());
        awsAccountCredentials.setEbsVolumes(new ArrayList<>());
        awsAccountCredentials.setEc2Instances(new ArrayList<>());
        awsAccountCredentials.setEc2OptimizationSuggestions(new ArrayList<>());
        awsAccountCredentials.setEipAddresses(new ArrayList<>());
        awsAccountCredentials.setEipOptimizationSuggestions(new ArrayList<>());
        awsAccountCredentials.setId(1L);
        awsAccountCredentials.setRdsInstances(new ArrayList<>());
        awsAccountCredentials.setRdsOptimizationSuggestions(new ArrayList<>());
        awsAccountCredentials.setS3OptimizationSuggestions(new ArrayList<>());
        awsAccountCredentials.setSecretAccessKey("EXAMPLEakiAIOSFODNN7");
        awsAccountCredentials.setStorageBuckets(new ArrayList<>());
        when(awsCredentialsService.getCredentialsByAccountId(Mockito.<String>any())).thenReturn(awsAccountCredentials);

        ArrayList<Ec2DTO> ec2DTOList = new ArrayList<>();
        Ec2DTO.Ec2DTOBuilder stateResult = Ec2DTO.builder()
                .associatedAccount("3")
                .instanceId("42")
                .instanceType("Instance Type")
                .monitoring("Monitoring")
                .platform("Platform")
                .privateIp("Private Ip")
                .publicIp("Public Ip")
                .region("us-east-2")
                .state("MD");
        Ec2DTO buildResult = stateResult.tags(new HashMap<>()).build();
        ec2DTOList.add(buildResult);
        when(computeServices.getAllEC2InstancesWithAccountId()).thenReturn(ec2DTOList);
        when(computeServices.getAllInstances()).thenReturn(new ArrayList<>());

        AwsAccountCredentials associatedAccount = new AwsAccountCredentials();
        associatedAccount.setAccessKeyId("EXAMPLEakiAIOSFODNN7");
        associatedAccount.setAccountId("42");
        associatedAccount.setAccountName("Dr Jane Doe");
        associatedAccount.setDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        associatedAccount.setEbsOptimizationSuggestions(new ArrayList<>());
        associatedAccount.setEbsVolumes(new ArrayList<>());
        associatedAccount.setEc2Instances(new ArrayList<>());
        associatedAccount.setEc2OptimizationSuggestions(new ArrayList<>());
        associatedAccount.setEipAddresses(new ArrayList<>());
        associatedAccount.setEipOptimizationSuggestions(new ArrayList<>());
        associatedAccount.setId(1L);
        associatedAccount.setRdsInstances(new ArrayList<>());
        associatedAccount.setRdsOptimizationSuggestions(new ArrayList<>());
        associatedAccount.setS3OptimizationSuggestions(new ArrayList<>());
        associatedAccount.setSecretAccessKey("EXAMPLEakiAIOSFODNN7");
        associatedAccount.setStorageBuckets(new ArrayList<>());

        EC2Instance ec2Instance = new EC2Instance();
        ec2Instance.setAssociatedAccount(associatedAccount);
        ec2Instance.setClientName("Dr Jane Doe");
        ec2Instance.setCreationTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        ec2Instance.setEnvironmentType("Environment Type");
        ec2Instance.setId(1L);
        ec2Instance.setInstanceId("42");
        ec2Instance.setInstanceType("Instance Type");
        ec2Instance.setLastDowntimeTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        ec2Instance.setLastUpdateTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        ec2Instance.setLastUptimeTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        ec2Instance.setLinkedSuggestion(new HashSet<>());
        ec2Instance.setMonitoring("Monitoring");
        ec2Instance.setOperationHours("Operation Hours");
        ec2Instance.setOwnerEmail("jane.doe@example.org");
        ec2Instance.setPlatform("Platform");
        ec2Instance.setPrivateIp("Private Ip");
        ec2Instance.setProductId("42");
        ec2Instance.setPublicIp("Public Ip");
        ec2Instance.setRegion("us-east-2");
        ec2Instance.setState("MD");
        when(eC2InstanceRepository.saveAndFlush(Mockito.<EC2Instance>any())).thenReturn(ec2Instance);
        when(eC2InstanceRepository.saveAllAndFlush(Mockito.<Iterable<EC2Instance>>any())).thenReturn(new ArrayList<>());
        when(eC2InstanceRepository.findAll()).thenReturn(new ArrayList<>());
        doNothing().when(eC2InstanceRepository).deleteAll(Mockito.<Iterable<EC2Instance>>any());
        dataStorageService.storeEc2Instances();
        verify(awsCredentialsService).getCredentialsByAccountId(Mockito.<String>any());
        verify(computeServices).getAllEC2InstancesWithAccountId();
        verify(computeServices).getAllInstances();
        verify(eC2InstanceRepository).saveAndFlush(Mockito.<EC2Instance>any());
        verify(eC2InstanceRepository).saveAllAndFlush(Mockito.<Iterable<EC2Instance>>any());
        verify(eC2InstanceRepository).findAll();
        verify(eC2InstanceRepository).deleteAll(Mockito.<Iterable<EC2Instance>>any());
    }

    /**
     * Method under test: {@link DataStorageService#storeEc2Instances()}
     */
    @Test
    void testStoreEc2Instances3() {
        AwsAccountCredentials awsAccountCredentials = new AwsAccountCredentials();
        awsAccountCredentials.setAccessKeyId("EXAMPLEakiAIOSFODNN7");
        awsAccountCredentials.setAccountId("42");
        awsAccountCredentials.setAccountName("Dr Jane Doe");
        awsAccountCredentials.setDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        awsAccountCredentials.setEbsOptimizationSuggestions(new ArrayList<>());
        awsAccountCredentials.setEbsVolumes(new ArrayList<>());
        awsAccountCredentials.setEc2Instances(new ArrayList<>());
        awsAccountCredentials.setEc2OptimizationSuggestions(new ArrayList<>());
        awsAccountCredentials.setEipAddresses(new ArrayList<>());
        awsAccountCredentials.setEipOptimizationSuggestions(new ArrayList<>());
        awsAccountCredentials.setId(1L);
        awsAccountCredentials.setRdsInstances(new ArrayList<>());
        awsAccountCredentials.setRdsOptimizationSuggestions(new ArrayList<>());
        awsAccountCredentials.setS3OptimizationSuggestions(new ArrayList<>());
        awsAccountCredentials.setSecretAccessKey("EXAMPLEakiAIOSFODNN7");
        awsAccountCredentials.setStorageBuckets(new ArrayList<>());
        when(awsCredentialsService.getCredentialsByAccountId(Mockito.<String>any())).thenReturn(awsAccountCredentials);

        ArrayList<Ec2DTO> ec2DTOList = new ArrayList<>();
        Ec2DTO.Ec2DTOBuilder stateResult = Ec2DTO.builder()
                .associatedAccount("3")
                .instanceId("42")
                .instanceType("Instance Type")
                .monitoring("Monitoring")
                .platform("Platform")
                .privateIp("Private Ip")
                .publicIp("Public Ip")
                .region("us-east-2")
                .state("MD");
        Ec2DTO buildResult = stateResult.tags(new HashMap<>()).build();
        ec2DTOList.add(buildResult);

        AwsAccountCredentials associatedAccount = new AwsAccountCredentials();
        associatedAccount.setAccessKeyId("EXAMPLEakiAIOSFODNN7");
        associatedAccount.setAccountId("42");
        associatedAccount.setAccountName("Dr Jane Doe");
        associatedAccount.setDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        associatedAccount.setEbsOptimizationSuggestions(new ArrayList<>());
        associatedAccount.setEbsVolumes(new ArrayList<>());
        associatedAccount.setEc2Instances(new ArrayList<>());
        associatedAccount.setEc2OptimizationSuggestions(new ArrayList<>());
        associatedAccount.setEipAddresses(new ArrayList<>());
        associatedAccount.setEipOptimizationSuggestions(new ArrayList<>());
        associatedAccount.setId(1L);
        associatedAccount.setRdsInstances(new ArrayList<>());
        associatedAccount.setRdsOptimizationSuggestions(new ArrayList<>());
        associatedAccount.setS3OptimizationSuggestions(new ArrayList<>());
        associatedAccount.setSecretAccessKey("EXAMPLEakiAIOSFODNN7");
        associatedAccount.setStorageBuckets(new ArrayList<>());

        EC2Instance ec2Instance = new EC2Instance();
        ec2Instance.setAssociatedAccount(associatedAccount);
        ec2Instance.setClientName("Dr Jane Doe");
        ec2Instance.setCreationTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        ec2Instance.setEnvironmentType("Storing EC2 Instances");
        ec2Instance.setId(1L);
        ec2Instance.setInstanceId("42");
        ec2Instance.setInstanceType("Storing EC2 Instances");
        ec2Instance.setLastDowntimeTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        ec2Instance.setLastUpdateTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        ec2Instance.setLastUptimeTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        ec2Instance.setLinkedSuggestion(new HashSet<>());
        ec2Instance.setMonitoring("Storing EC2 Instances");
        ec2Instance.setOperationHours("Storing EC2 Instances");
        ec2Instance.setOwnerEmail("jane.doe@example.org");
        ec2Instance.setPlatform("Storing EC2 Instances");
        ec2Instance.setPrivateIp("Storing EC2 Instances");
        ec2Instance.setProductId("42");
        ec2Instance.setPublicIp("Storing EC2 Instances");
        ec2Instance.setRegion("us-east-2");
        ec2Instance.setState("MD");

        ArrayList<EC2Instance> ec2InstanceList = new ArrayList<>();
        ec2InstanceList.add(ec2Instance);
        when(computeServices.getAllEC2InstancesWithAccountId()).thenReturn(ec2DTOList);
        when(computeServices.getAllInstances()).thenReturn(ec2InstanceList);
        when(eC2InstanceRepository.saveAllAndFlush(Mockito.<Iterable<EC2Instance>>any())).thenReturn(new ArrayList<>());
        when(eC2InstanceRepository.findAll()).thenReturn(new ArrayList<>());
        doNothing().when(eC2InstanceRepository).deleteAll(Mockito.<Iterable<EC2Instance>>any());
        dataStorageService.storeEc2Instances();
        verify(awsCredentialsService).getCredentialsByAccountId(Mockito.<String>any());
        verify(computeServices).getAllEC2InstancesWithAccountId();
        verify(computeServices).getAllInstances();
        verify(eC2InstanceRepository).saveAllAndFlush(Mockito.<Iterable<EC2Instance>>any());
        verify(eC2InstanceRepository).findAll();
        verify(eC2InstanceRepository).deleteAll(Mockito.<Iterable<EC2Instance>>any());
    }

    /**
     * Method under test: {@link DataStorageService#storeEc2Instances()}
     */
    @Test
    void testStoreEc2Instances4() {
        AwsAccountCredentials awsAccountCredentials = new AwsAccountCredentials();
        awsAccountCredentials.setAccessKeyId("EXAMPLEakiAIOSFODNN7");
        awsAccountCredentials.setAccountId("42");
        awsAccountCredentials.setAccountName("Dr Jane Doe");
        awsAccountCredentials.setDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        awsAccountCredentials.setEbsOptimizationSuggestions(new ArrayList<>());
        awsAccountCredentials.setEbsVolumes(new ArrayList<>());
        awsAccountCredentials.setEc2Instances(new ArrayList<>());
        awsAccountCredentials.setEc2OptimizationSuggestions(new ArrayList<>());
        awsAccountCredentials.setEipAddresses(new ArrayList<>());
        awsAccountCredentials.setEipOptimizationSuggestions(new ArrayList<>());
        awsAccountCredentials.setId(1L);
        awsAccountCredentials.setRdsInstances(new ArrayList<>());
        awsAccountCredentials.setRdsOptimizationSuggestions(new ArrayList<>());
        awsAccountCredentials.setS3OptimizationSuggestions(new ArrayList<>());
        awsAccountCredentials.setSecretAccessKey("EXAMPLEakiAIOSFODNN7");
        awsAccountCredentials.setStorageBuckets(new ArrayList<>());
        when(awsCredentialsService.getCredentialsByAccountId(Mockito.<String>any())).thenReturn(awsAccountCredentials);

        ArrayList<Ec2DTO> ec2DTOList = new ArrayList<>();
        Ec2DTO.Ec2DTOBuilder stateResult = Ec2DTO.builder()
                .associatedAccount("3")
                .instanceId("42")
                .instanceType("Instance Type")
                .monitoring("Monitoring")
                .platform("Platform")
                .privateIp("Private Ip")
                .publicIp("Public Ip")
                .region("us-east-2")
                .state("MD");
        Ec2DTO buildResult = stateResult.tags(new HashMap<>()).build();
        ec2DTOList.add(buildResult);

        AwsAccountCredentials associatedAccount = new AwsAccountCredentials();
        associatedAccount.setAccessKeyId("EXAMPLEakiAIOSFODNN7");
        associatedAccount.setAccountId("42");
        associatedAccount.setAccountName("Dr Jane Doe");
        associatedAccount.setDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        associatedAccount.setEbsOptimizationSuggestions(new ArrayList<>());
        associatedAccount.setEbsVolumes(new ArrayList<>());
        associatedAccount.setEc2Instances(new ArrayList<>());
        associatedAccount.setEc2OptimizationSuggestions(new ArrayList<>());
        associatedAccount.setEipAddresses(new ArrayList<>());
        associatedAccount.setEipOptimizationSuggestions(new ArrayList<>());
        associatedAccount.setId(1L);
        associatedAccount.setRdsInstances(new ArrayList<>());
        associatedAccount.setRdsOptimizationSuggestions(new ArrayList<>());
        associatedAccount.setS3OptimizationSuggestions(new ArrayList<>());
        associatedAccount.setSecretAccessKey("EXAMPLEakiAIOSFODNN7");
        associatedAccount.setStorageBuckets(new ArrayList<>());

        EC2Instance ec2Instance = new EC2Instance();
        ec2Instance.setAssociatedAccount(associatedAccount);
        ec2Instance.setClientName("Dr Jane Doe");
        ec2Instance.setCreationTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        ec2Instance.setEnvironmentType("Storing EC2 Instances");
        ec2Instance.setId(1L);
        ec2Instance.setInstanceId("42");
        ec2Instance.setInstanceType("Storing EC2 Instances");
        ec2Instance.setLastDowntimeTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        ec2Instance.setLastUpdateTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        ec2Instance.setLastUptimeTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        ec2Instance.setLinkedSuggestion(new HashSet<>());
        ec2Instance.setMonitoring("Storing EC2 Instances");
        ec2Instance.setOperationHours("Storing EC2 Instances");
        ec2Instance.setOwnerEmail("jane.doe@example.org");
        ec2Instance.setPlatform("Storing EC2 Instances");
        ec2Instance.setPrivateIp("Storing EC2 Instances");
        ec2Instance.setProductId("42");
        ec2Instance.setPublicIp("Storing EC2 Instances");
        ec2Instance.setRegion("us-east-2");
        ec2Instance.setState("MD");

        AwsAccountCredentials associatedAccount2 = new AwsAccountCredentials();
        associatedAccount2.setAccessKeyId("42");
        associatedAccount2.setAccountId("3");
        associatedAccount2.setAccountName("Mr John Smith");
        associatedAccount2.setDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        associatedAccount2.setEbsOptimizationSuggestions(new ArrayList<>());
        associatedAccount2.setEbsVolumes(new ArrayList<>());
        associatedAccount2.setEc2Instances(new ArrayList<>());
        associatedAccount2.setEc2OptimizationSuggestions(new ArrayList<>());
        associatedAccount2.setEipAddresses(new ArrayList<>());
        associatedAccount2.setEipOptimizationSuggestions(new ArrayList<>());
        associatedAccount2.setId(2L);
        associatedAccount2.setRdsInstances(new ArrayList<>());
        associatedAccount2.setRdsOptimizationSuggestions(new ArrayList<>());
        associatedAccount2.setS3OptimizationSuggestions(new ArrayList<>());
        associatedAccount2.setSecretAccessKey("Storing EC2 Instances");
        associatedAccount2.setStorageBuckets(new ArrayList<>());

        EC2Instance ec2Instance2 = new EC2Instance();
        ec2Instance2.setAssociatedAccount(associatedAccount2);
        ec2Instance2.setClientName("Mr John Smith");
        ec2Instance2.setCreationTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        ec2Instance2.setEnvironmentType("42");
        ec2Instance2.setId(2L);
        ec2Instance2.setInstanceId("Storing EC2 Instances");
        ec2Instance2.setInstanceType("42");
        ec2Instance2.setLastDowntimeTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        ec2Instance2.setLastUpdateTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        ec2Instance2.setLastUptimeTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        ec2Instance2.setLinkedSuggestion(new HashSet<>());
        ec2Instance2.setMonitoring("42");
        ec2Instance2.setOperationHours("42");
        ec2Instance2.setOwnerEmail("john.smith@example.org");
        ec2Instance2.setPlatform("42");
        ec2Instance2.setPrivateIp("42");
        ec2Instance2.setProductId("Storing EC2 Instances");
        ec2Instance2.setPublicIp("42");
        ec2Instance2.setRegion("Storing EC2 Instances");
        ec2Instance2.setState("Storing EC2 Instances");

        ArrayList<EC2Instance> ec2InstanceList = new ArrayList<>();
        ec2InstanceList.add(ec2Instance2);
        ec2InstanceList.add(ec2Instance);
        when(computeServices.getAllEC2InstancesWithAccountId()).thenReturn(ec2DTOList);
        when(computeServices.getAllInstances()).thenReturn(ec2InstanceList);
        when(eC2InstanceRepository.saveAllAndFlush(Mockito.<Iterable<EC2Instance>>any())).thenReturn(new ArrayList<>());
        when(eC2InstanceRepository.findAll()).thenReturn(new ArrayList<>());
        doNothing().when(eC2InstanceRepository).deleteAll(Mockito.<Iterable<EC2Instance>>any());
        dataStorageService.storeEc2Instances();
        verify(awsCredentialsService).getCredentialsByAccountId(Mockito.<String>any());
        verify(computeServices).getAllEC2InstancesWithAccountId();
        verify(computeServices).getAllInstances();
        verify(eC2InstanceRepository).saveAllAndFlush(Mockito.<Iterable<EC2Instance>>any());
        verify(eC2InstanceRepository).findAll();
        verify(eC2InstanceRepository).deleteAll(Mockito.<Iterable<EC2Instance>>any());
    }

    /**
     * Method under test: {@link DataStorageService#storeRdsInstances()}
     */
    @Test
    void testStoreRdsInstances() {
        when(rDSService.getAllRdsInstances()).thenReturn(new ArrayList<>());
        when(rDSService.getAllRdsInstancesWithAccountId()).thenReturn(new ArrayList<>());
        when(rDSInstanceRepository.saveAllAndFlush(Mockito.<Iterable<RDSInstance>>any())).thenReturn(new ArrayList<>());
        when(rDSInstanceRepository.findAll()).thenReturn(new ArrayList<>());
        doNothing().when(rDSInstanceRepository).deleteAll(Mockito.<Iterable<RDSInstance>>any());
        dataStorageService.storeRdsInstances();
        verify(rDSService).getAllRdsInstances();
        verify(rDSService).getAllRdsInstancesWithAccountId();
        verify(rDSInstanceRepository).saveAllAndFlush(Mockito.<Iterable<RDSInstance>>any());
        verify(rDSInstanceRepository).findAll();
        verify(rDSInstanceRepository).deleteAll(Mockito.<Iterable<RDSInstance>>any());
    }

    /**
     * Method under test: {@link DataStorageService#storeRdsInstances()}
     */
    @Test
    void testStoreRdsInstances2() {
        AwsAccountCredentials associatedAccount = new AwsAccountCredentials();
        associatedAccount.setAccessKeyId("EXAMPLEakiAIOSFODNN7");
        associatedAccount.setAccountId("42");
        associatedAccount.setAccountName("Dr Jane Doe");
        associatedAccount.setDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        associatedAccount.setEbsOptimizationSuggestions(new ArrayList<>());
        associatedAccount.setEbsVolumes(new ArrayList<>());
        associatedAccount.setEc2Instances(new ArrayList<>());
        associatedAccount.setEc2OptimizationSuggestions(new ArrayList<>());
        associatedAccount.setEipAddresses(new ArrayList<>());
        associatedAccount.setEipOptimizationSuggestions(new ArrayList<>());
        associatedAccount.setId(1L);
        associatedAccount.setRdsInstances(new ArrayList<>());
        associatedAccount.setRdsOptimizationSuggestions(new ArrayList<>());
        associatedAccount.setS3OptimizationSuggestions(new ArrayList<>());
        associatedAccount.setSecretAccessKey("EXAMPLEakiAIOSFODNN7");
        associatedAccount.setStorageBuckets(new ArrayList<>());

        RDSInstance rdsInstance = new RDSInstance();
        rdsInstance.setAllocatedStorage(1);
        rdsInstance.setAssociatedAccount(associatedAccount);
        rdsInstance.setClientName("Dr Jane Doe");
        rdsInstance.setCreationDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rdsInstance.setCreationTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        rdsInstance.setDbInstanceClass("Storing RDS Instances");
        rdsInstance.setDbInstanceIdentifier("42");
        rdsInstance.setEndpointAddress("https://config.us-east-2.amazonaws.com");
        rdsInstance.setEndpointPort(3);
        rdsInstance.setEngine("Storing RDS Instances");
        rdsInstance.setEngineVersion("1.0.2");
        rdsInstance.setId(1L);
        rdsInstance.setLastDowntimeTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        rdsInstance.setLastUpdateTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        rdsInstance.setLastUptimeTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        rdsInstance.setLinkedSuggestion(new HashSet<>());
        rdsInstance.setMonitoringEnabled(true);
        rdsInstance.setOperationHours("Storing RDS Instances");
        rdsInstance.setOwnerEmail("jane.doe@example.org");
        rdsInstance.setProductId("42");
        rdsInstance.setRegion("us-east-2");
        rdsInstance.setStatus("Storing RDS Instances");

        ArrayList<RDSInstance> rdsInstanceList = new ArrayList<>();
        rdsInstanceList.add(rdsInstance);
        when(rDSService.getAllRdsInstances()).thenReturn(rdsInstanceList);
        when(rDSService.getAllRdsInstancesWithAccountId()).thenReturn(new ArrayList<>());
        when(rDSInstanceRepository.saveAllAndFlush(Mockito.<Iterable<RDSInstance>>any())).thenReturn(new ArrayList<>());
        when(rDSInstanceRepository.findAll()).thenReturn(new ArrayList<>());
        doNothing().when(rDSInstanceRepository).deleteAll(Mockito.<Iterable<RDSInstance>>any());
        dataStorageService.storeRdsInstances();
        verify(rDSService).getAllRdsInstances();
        verify(rDSService).getAllRdsInstancesWithAccountId();
        verify(rDSInstanceRepository).saveAllAndFlush(Mockito.<Iterable<RDSInstance>>any());
        verify(rDSInstanceRepository).findAll();
        verify(rDSInstanceRepository).deleteAll(Mockito.<Iterable<RDSInstance>>any());
    }

    /**
     * Method under test: {@link DataStorageService#storeRdsInstances()}
     */
    @Test
    void testStoreRdsInstances3() {
        AwsAccountCredentials awsAccountCredentials = new AwsAccountCredentials();
        awsAccountCredentials.setAccessKeyId("EXAMPLEakiAIOSFODNN7");
        awsAccountCredentials.setAccountId("42");
        awsAccountCredentials.setAccountName("Dr Jane Doe");
        awsAccountCredentials.setDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        awsAccountCredentials.setEbsOptimizationSuggestions(new ArrayList<>());
        awsAccountCredentials.setEbsVolumes(new ArrayList<>());
        awsAccountCredentials.setEc2Instances(new ArrayList<>());
        awsAccountCredentials.setEc2OptimizationSuggestions(new ArrayList<>());
        awsAccountCredentials.setEipAddresses(new ArrayList<>());
        awsAccountCredentials.setEipOptimizationSuggestions(new ArrayList<>());
        awsAccountCredentials.setId(1L);
        awsAccountCredentials.setRdsInstances(new ArrayList<>());
        awsAccountCredentials.setRdsOptimizationSuggestions(new ArrayList<>());
        awsAccountCredentials.setS3OptimizationSuggestions(new ArrayList<>());
        awsAccountCredentials.setSecretAccessKey("EXAMPLEakiAIOSFODNN7");
        awsAccountCredentials.setStorageBuckets(new ArrayList<>());
        when(awsCredentialsService.getCredentialsByAccountId(Mockito.<String>any())).thenReturn(awsAccountCredentials);

        ArrayList<RdsDTO> rdsDTOList = new ArrayList<>();
        RdsDTO.RdsDTOBuilder associatedAccountResult = RdsDTO.builder().allocatedStorage(1).associatedAccount("3");
        RdsDTO.RdsDTOBuilder statusResult = associatedAccountResult
                .creationDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
                .dbInstanceClass("Db Instance Class")
                .dbInstanceIdentifier("42")
                .endpointAddress("https://config.us-east-2.amazonaws.com")
                .endpointPort(3)
                .engine("Engine")
                .engineVersion("1.0.2")
                .monitoringEnabled(true)
                .region("us-east-2")
                .status("Status");
        RdsDTO buildResult = statusResult.tags(new HashMap<>()).build();
        rdsDTOList.add(buildResult);
        when(rDSService.getAllRdsInstances()).thenReturn(new ArrayList<>());
        when(rDSService.getAllRdsInstancesWithAccountId()).thenReturn(rdsDTOList);

        AwsAccountCredentials associatedAccount = new AwsAccountCredentials();
        associatedAccount.setAccessKeyId("EXAMPLEakiAIOSFODNN7");
        associatedAccount.setAccountId("42");
        associatedAccount.setAccountName("Dr Jane Doe");
        associatedAccount.setDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        associatedAccount.setEbsOptimizationSuggestions(new ArrayList<>());
        associatedAccount.setEbsVolumes(new ArrayList<>());
        associatedAccount.setEc2Instances(new ArrayList<>());
        associatedAccount.setEc2OptimizationSuggestions(new ArrayList<>());
        associatedAccount.setEipAddresses(new ArrayList<>());
        associatedAccount.setEipOptimizationSuggestions(new ArrayList<>());
        associatedAccount.setId(1L);
        associatedAccount.setRdsInstances(new ArrayList<>());
        associatedAccount.setRdsOptimizationSuggestions(new ArrayList<>());
        associatedAccount.setS3OptimizationSuggestions(new ArrayList<>());
        associatedAccount.setSecretAccessKey("EXAMPLEakiAIOSFODNN7");
        associatedAccount.setStorageBuckets(new ArrayList<>());

        RDSInstance rdsInstance = new RDSInstance();
        rdsInstance.setAllocatedStorage(1);
        rdsInstance.setAssociatedAccount(associatedAccount);
        rdsInstance.setClientName("Dr Jane Doe");
        rdsInstance.setCreationDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rdsInstance.setCreationTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        rdsInstance.setDbInstanceClass("Db Instance Class");
        rdsInstance.setDbInstanceIdentifier("42");
        rdsInstance.setEndpointAddress("https://config.us-east-2.amazonaws.com");
        rdsInstance.setEndpointPort(3);
        rdsInstance.setEngine("Engine");
        rdsInstance.setEngineVersion("1.0.2");
        rdsInstance.setId(1L);
        rdsInstance.setLastDowntimeTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        rdsInstance.setLastUpdateTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        rdsInstance.setLastUptimeTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        rdsInstance.setLinkedSuggestion(new HashSet<>());
        rdsInstance.setMonitoringEnabled(true);
        rdsInstance.setOperationHours("Operation Hours");
        rdsInstance.setOwnerEmail("jane.doe@example.org");
        rdsInstance.setProductId("42");
        rdsInstance.setRegion("us-east-2");
        rdsInstance.setStatus("Status");
        when(rDSInstanceRepository.saveAndFlush(Mockito.<RDSInstance>any())).thenReturn(rdsInstance);
        when(rDSInstanceRepository.saveAllAndFlush(Mockito.<Iterable<RDSInstance>>any())).thenReturn(new ArrayList<>());
        when(rDSInstanceRepository.findAll()).thenReturn(new ArrayList<>());
        doNothing().when(rDSInstanceRepository).deleteAll(Mockito.<Iterable<RDSInstance>>any());
        dataStorageService.storeRdsInstances();
        verify(awsCredentialsService).getCredentialsByAccountId(Mockito.<String>any());
        verify(rDSService).getAllRdsInstances();
        verify(rDSService).getAllRdsInstancesWithAccountId();
        verify(rDSInstanceRepository).saveAndFlush(Mockito.<RDSInstance>any());
        verify(rDSInstanceRepository).saveAllAndFlush(Mockito.<Iterable<RDSInstance>>any());
        verify(rDSInstanceRepository).findAll();
        verify(rDSInstanceRepository).deleteAll(Mockito.<Iterable<RDSInstance>>any());
    }

    /**
     * Method under test: {@link DataStorageService#storeRdsInstances()}
     */
    @Test
    void testStoreRdsInstances4() {
        AwsAccountCredentials awsAccountCredentials = new AwsAccountCredentials();
        awsAccountCredentials.setAccessKeyId("EXAMPLEakiAIOSFODNN7");
        awsAccountCredentials.setAccountId("42");
        awsAccountCredentials.setAccountName("Dr Jane Doe");
        awsAccountCredentials.setDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        awsAccountCredentials.setEbsOptimizationSuggestions(new ArrayList<>());
        awsAccountCredentials.setEbsVolumes(new ArrayList<>());
        awsAccountCredentials.setEc2Instances(new ArrayList<>());
        awsAccountCredentials.setEc2OptimizationSuggestions(new ArrayList<>());
        awsAccountCredentials.setEipAddresses(new ArrayList<>());
        awsAccountCredentials.setEipOptimizationSuggestions(new ArrayList<>());
        awsAccountCredentials.setId(1L);
        awsAccountCredentials.setRdsInstances(new ArrayList<>());
        awsAccountCredentials.setRdsOptimizationSuggestions(new ArrayList<>());
        awsAccountCredentials.setS3OptimizationSuggestions(new ArrayList<>());
        awsAccountCredentials.setSecretAccessKey("EXAMPLEakiAIOSFODNN7");
        awsAccountCredentials.setStorageBuckets(new ArrayList<>());
        when(awsCredentialsService.getCredentialsByAccountId(Mockito.<String>any())).thenReturn(awsAccountCredentials);

        AwsAccountCredentials associatedAccount = new AwsAccountCredentials();
        associatedAccount.setAccessKeyId("Storing RDS Instances");
        associatedAccount.setAccountId("Storing RDS Instances");
        associatedAccount.setAccountName("Prof Albert Einstein");
        associatedAccount.setDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        associatedAccount.setEbsOptimizationSuggestions(new ArrayList<>());
        associatedAccount.setEbsVolumes(new ArrayList<>());
        associatedAccount.setEc2Instances(new ArrayList<>());
        associatedAccount.setEc2OptimizationSuggestions(new ArrayList<>());
        associatedAccount.setEipAddresses(new ArrayList<>());
        associatedAccount.setEipOptimizationSuggestions(new ArrayList<>());
        associatedAccount.setId(3L);
        associatedAccount.setRdsInstances(new ArrayList<>());
        associatedAccount.setRdsOptimizationSuggestions(new ArrayList<>());
        associatedAccount.setS3OptimizationSuggestions(new ArrayList<>());
        associatedAccount.setSecretAccessKey("OwnerEmail");
        associatedAccount.setStorageBuckets(new ArrayList<>());

        RDSInstance rdsInstance = new RDSInstance();
        rdsInstance.setAllocatedStorage(-1);
        rdsInstance.setAssociatedAccount(associatedAccount);
        rdsInstance.setClientName("Prof Albert Einstein");
        rdsInstance.setCreationDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rdsInstance.setCreationTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        rdsInstance.setDbInstanceClass("ClientName");
        rdsInstance.setDbInstanceIdentifier("OwnerEmail");
        rdsInstance.setEndpointAddress("17 High St");
        rdsInstance.setEndpointPort(1);
        rdsInstance.setEngine("ClientName");
        rdsInstance.setEngineVersion("OwnerEmail");
        rdsInstance.setId(3L);
        rdsInstance.setLastDowntimeTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        rdsInstance.setLastUpdateTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        rdsInstance.setLastUptimeTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        rdsInstance.setLinkedSuggestion(new HashSet<>());
        rdsInstance.setMonitoringEnabled(true);
        rdsInstance.setOperationHours("ClientName");
        rdsInstance.setOwnerEmail("prof.einstein@example.org");
        rdsInstance.setProductId("OwnerEmail");
        rdsInstance.setRegion("OwnerEmail");
        rdsInstance.setStatus("ClientName");

        ArrayList<RDSInstance> rdsInstanceList = new ArrayList<>();
        rdsInstanceList.add(rdsInstance);

        ArrayList<RdsDTO> rdsDTOList = new ArrayList<>();
        RdsDTO.RdsDTOBuilder associatedAccountResult = RdsDTO.builder().allocatedStorage(1).associatedAccount("3");
        RdsDTO.RdsDTOBuilder statusResult = associatedAccountResult
                .creationDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
                .dbInstanceClass("Db Instance Class")
                .dbInstanceIdentifier("42")
                .endpointAddress("https://config.us-east-2.amazonaws.com")
                .endpointPort(3)
                .engine("Engine")
                .engineVersion("1.0.2")
                .monitoringEnabled(true)
                .region("us-east-2")
                .status("Status");
        RdsDTO buildResult = statusResult.tags(new HashMap<>()).build();
        rdsDTOList.add(buildResult);
        when(rDSService.getAllRdsInstances()).thenReturn(rdsInstanceList);
        when(rDSService.getAllRdsInstancesWithAccountId()).thenReturn(rdsDTOList);

        AwsAccountCredentials associatedAccount2 = new AwsAccountCredentials();
        associatedAccount2.setAccessKeyId("EXAMPLEakiAIOSFODNN7");
        associatedAccount2.setAccountId("42");
        associatedAccount2.setAccountName("Dr Jane Doe");
        associatedAccount2.setDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        associatedAccount2.setEbsOptimizationSuggestions(new ArrayList<>());
        associatedAccount2.setEbsVolumes(new ArrayList<>());
        associatedAccount2.setEc2Instances(new ArrayList<>());
        associatedAccount2.setEc2OptimizationSuggestions(new ArrayList<>());
        associatedAccount2.setEipAddresses(new ArrayList<>());
        associatedAccount2.setEipOptimizationSuggestions(new ArrayList<>());
        associatedAccount2.setId(1L);
        associatedAccount2.setRdsInstances(new ArrayList<>());
        associatedAccount2.setRdsOptimizationSuggestions(new ArrayList<>());
        associatedAccount2.setS3OptimizationSuggestions(new ArrayList<>());
        associatedAccount2.setSecretAccessKey("EXAMPLEakiAIOSFODNN7");
        associatedAccount2.setStorageBuckets(new ArrayList<>());

        RDSInstance rdsInstance2 = new RDSInstance();
        rdsInstance2.setAllocatedStorage(1);
        rdsInstance2.setAssociatedAccount(associatedAccount2);
        rdsInstance2.setClientName("Dr Jane Doe");
        rdsInstance2.setCreationDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rdsInstance2.setCreationTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        rdsInstance2.setDbInstanceClass("Db Instance Class");
        rdsInstance2.setDbInstanceIdentifier("42");
        rdsInstance2.setEndpointAddress("https://config.us-east-2.amazonaws.com");
        rdsInstance2.setEndpointPort(3);
        rdsInstance2.setEngine("Engine");
        rdsInstance2.setEngineVersion("1.0.2");
        rdsInstance2.setId(1L);
        rdsInstance2.setLastDowntimeTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        rdsInstance2.setLastUpdateTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        rdsInstance2.setLastUptimeTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        rdsInstance2.setLinkedSuggestion(new HashSet<>());
        rdsInstance2.setMonitoringEnabled(true);
        rdsInstance2.setOperationHours("Operation Hours");
        rdsInstance2.setOwnerEmail("jane.doe@example.org");
        rdsInstance2.setProductId("42");
        rdsInstance2.setRegion("us-east-2");
        rdsInstance2.setStatus("Status");
        when(rDSInstanceRepository.saveAndFlush(Mockito.<RDSInstance>any())).thenReturn(rdsInstance2);
        when(rDSInstanceRepository.saveAllAndFlush(Mockito.<Iterable<RDSInstance>>any())).thenReturn(new ArrayList<>());
        when(rDSInstanceRepository.findAll()).thenReturn(new ArrayList<>());
        doNothing().when(rDSInstanceRepository).deleteAll(Mockito.<Iterable<RDSInstance>>any());
        dataStorageService.storeRdsInstances();
        verify(awsCredentialsService).getCredentialsByAccountId(Mockito.<String>any());
        verify(rDSService).getAllRdsInstances();
        verify(rDSService).getAllRdsInstancesWithAccountId();
        verify(rDSInstanceRepository).saveAndFlush(Mockito.<RDSInstance>any());
        verify(rDSInstanceRepository).saveAllAndFlush(Mockito.<Iterable<RDSInstance>>any());
        verify(rDSInstanceRepository).findAll();
        verify(rDSInstanceRepository).deleteAll(Mockito.<Iterable<RDSInstance>>any());
    }

    /**
     * Method under test: {@link DataStorageService#storeRdsInstances()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testStoreRdsInstances5() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "java.util.Date.toInstant()" because the return value of "com.vermeg.aws.cost.optimization.dashboard.dto.RdsDTO.getCreationDate()" is null
        //       at com.vermeg.aws.cost.optimization.dashboard.services.DataStorageService.addRdsInstances(DataStorageService.java:230)
        //       at com.vermeg.aws.cost.optimization.dashboard.services.DataStorageService.storeRdsInstances(DataStorageService.java:186)
        //   See https://diff.blue/R013 to resolve this issue.

        AwsAccountCredentials awsAccountCredentials = new AwsAccountCredentials();
        awsAccountCredentials.setAccessKeyId("EXAMPLEakiAIOSFODNN7");
        awsAccountCredentials.setAccountId("42");
        awsAccountCredentials.setAccountName("Dr Jane Doe");
        awsAccountCredentials.setDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        awsAccountCredentials.setEbsOptimizationSuggestions(new ArrayList<>());
        awsAccountCredentials.setEbsVolumes(new ArrayList<>());
        awsAccountCredentials.setEc2Instances(new ArrayList<>());
        awsAccountCredentials.setEc2OptimizationSuggestions(new ArrayList<>());
        awsAccountCredentials.setEipAddresses(new ArrayList<>());
        awsAccountCredentials.setEipOptimizationSuggestions(new ArrayList<>());
        awsAccountCredentials.setId(1L);
        awsAccountCredentials.setRdsInstances(new ArrayList<>());
        awsAccountCredentials.setRdsOptimizationSuggestions(new ArrayList<>());
        awsAccountCredentials.setS3OptimizationSuggestions(new ArrayList<>());
        awsAccountCredentials.setSecretAccessKey("EXAMPLEakiAIOSFODNN7");
        awsAccountCredentials.setStorageBuckets(new ArrayList<>());
        when(awsCredentialsService.getCredentialsByAccountId(Mockito.<String>any())).thenReturn(awsAccountCredentials);

        ArrayList<RdsDTO> rdsDTOList = new ArrayList<>();
        RdsDTO.RdsDTOBuilder statusResult = RdsDTO.builder()
                .allocatedStorage(1)
                .associatedAccount("3")
                .creationDate(null)
                .dbInstanceClass("Db Instance Class")
                .dbInstanceIdentifier("42")
                .endpointAddress("https://config.us-east-2.amazonaws.com")
                .endpointPort(3)
                .engine("Engine")
                .engineVersion("1.0.2")
                .monitoringEnabled(true)
                .region("us-east-2")
                .status("Status");
        RdsDTO buildResult = statusResult.tags(new HashMap<>()).build();
        rdsDTOList.add(buildResult);
        when(rDSService.getAllRdsInstances()).thenReturn(new ArrayList<>());
        when(rDSService.getAllRdsInstancesWithAccountId()).thenReturn(rdsDTOList);

        AwsAccountCredentials associatedAccount = new AwsAccountCredentials();
        associatedAccount.setAccessKeyId("EXAMPLEakiAIOSFODNN7");
        associatedAccount.setAccountId("42");
        associatedAccount.setAccountName("Dr Jane Doe");
        associatedAccount.setDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        associatedAccount.setEbsOptimizationSuggestions(new ArrayList<>());
        associatedAccount.setEbsVolumes(new ArrayList<>());
        associatedAccount.setEc2Instances(new ArrayList<>());
        associatedAccount.setEc2OptimizationSuggestions(new ArrayList<>());
        associatedAccount.setEipAddresses(new ArrayList<>());
        associatedAccount.setEipOptimizationSuggestions(new ArrayList<>());
        associatedAccount.setId(1L);
        associatedAccount.setRdsInstances(new ArrayList<>());
        associatedAccount.setRdsOptimizationSuggestions(new ArrayList<>());
        associatedAccount.setS3OptimizationSuggestions(new ArrayList<>());
        associatedAccount.setSecretAccessKey("EXAMPLEakiAIOSFODNN7");
        associatedAccount.setStorageBuckets(new ArrayList<>());

        RDSInstance rdsInstance = new RDSInstance();
        rdsInstance.setAllocatedStorage(1);
        rdsInstance.setAssociatedAccount(associatedAccount);
        rdsInstance.setClientName("Dr Jane Doe");
        rdsInstance.setCreationDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rdsInstance.setCreationTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        rdsInstance.setDbInstanceClass("Db Instance Class");
        rdsInstance.setDbInstanceIdentifier("42");
        rdsInstance.setEndpointAddress("https://config.us-east-2.amazonaws.com");
        rdsInstance.setEndpointPort(3);
        rdsInstance.setEngine("Engine");
        rdsInstance.setEngineVersion("1.0.2");
        rdsInstance.setId(1L);
        rdsInstance.setLastDowntimeTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        rdsInstance.setLastUpdateTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        rdsInstance.setLastUptimeTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        rdsInstance.setLinkedSuggestion(new HashSet<>());
        rdsInstance.setMonitoringEnabled(true);
        rdsInstance.setOperationHours("Operation Hours");
        rdsInstance.setOwnerEmail("jane.doe@example.org");
        rdsInstance.setProductId("42");
        rdsInstance.setRegion("us-east-2");
        rdsInstance.setStatus("Status");
        when(rDSInstanceRepository.saveAndFlush(Mockito.<RDSInstance>any())).thenReturn(rdsInstance);
        when(rDSInstanceRepository.saveAllAndFlush(Mockito.<Iterable<RDSInstance>>any())).thenReturn(new ArrayList<>());
        when(rDSInstanceRepository.findAll()).thenReturn(new ArrayList<>());
        doNothing().when(rDSInstanceRepository).deleteAll(Mockito.<Iterable<RDSInstance>>any());
        dataStorageService.storeRdsInstances();
    }

    /**
     * Method under test: {@link DataStorageService#storeEipAddresses()}
     */
    @Test
    void testStoreEipAddresses() {
        when(computeServices.getAllAddresses()).thenReturn(new ArrayList<>());
        when(computeServices.getAllElasticIPsWithAccountId()).thenReturn(new ArrayList<>());
        when(eIPAddressRepository.saveAllAndFlush(Mockito.<Iterable<EIPAddress>>any())).thenReturn(new ArrayList<>());
        when(eIPAddressRepository.findAll()).thenReturn(new ArrayList<>());
        doNothing().when(eIPAddressRepository).deleteAll(Mockito.<Iterable<EIPAddress>>any());
        dataStorageService.storeEipAddresses();
        verify(computeServices).getAllAddresses();
        verify(computeServices).getAllElasticIPsWithAccountId();
        verify(eIPAddressRepository).saveAllAndFlush(Mockito.<Iterable<EIPAddress>>any());
        verify(eIPAddressRepository).findAll();
        verify(eIPAddressRepository).deleteAll(Mockito.<Iterable<EIPAddress>>any());
    }

    /**
     * Method under test: {@link DataStorageService#storeEipAddresses()}
     */
    @Test
    void testStoreEipAddresses2() {
        AwsAccountCredentials associatedAccount = new AwsAccountCredentials();
        associatedAccount.setAccessKeyId("EXAMPLEakiAIOSFODNN7");
        associatedAccount.setAccountId("42");
        associatedAccount.setAccountName("Dr Jane Doe");
        associatedAccount.setDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        associatedAccount.setEbsOptimizationSuggestions(new ArrayList<>());
        associatedAccount.setEbsVolumes(new ArrayList<>());
        associatedAccount.setEc2Instances(new ArrayList<>());
        associatedAccount.setEc2OptimizationSuggestions(new ArrayList<>());
        associatedAccount.setEipAddresses(new ArrayList<>());
        associatedAccount.setEipOptimizationSuggestions(new ArrayList<>());
        associatedAccount.setId(1L);
        associatedAccount.setRdsInstances(new ArrayList<>());
        associatedAccount.setRdsOptimizationSuggestions(new ArrayList<>());
        associatedAccount.setS3OptimizationSuggestions(new ArrayList<>());
        associatedAccount.setSecretAccessKey("EXAMPLEakiAIOSFODNN7");
        associatedAccount.setStorageBuckets(new ArrayList<>());

        EIPAddress eipAddress = new EIPAddress();
        eipAddress.setAllocationId("42");
        eipAddress.setAssociatedAccount(associatedAccount);
        eipAddress.setAssociationId("42");
        eipAddress.setId(1L);
        eipAddress.setInstanceId("42");
        eipAddress.setLinkedSuggestion(new HashSet<>());
        eipAddress.setPrivateIp("Storing EIP Addresses");
        eipAddress.setPublicIp("Storing EIP Addresses");

        ArrayList<EIPAddress> eipAddressList = new ArrayList<>();
        eipAddressList.add(eipAddress);
        when(computeServices.getAllAddresses()).thenReturn(eipAddressList);
        when(computeServices.getAllElasticIPsWithAccountId()).thenReturn(new ArrayList<>());
        when(eIPAddressRepository.saveAllAndFlush(Mockito.<Iterable<EIPAddress>>any())).thenReturn(new ArrayList<>());
        when(eIPAddressRepository.findAll()).thenReturn(new ArrayList<>());
        doNothing().when(eIPAddressRepository).deleteAll(Mockito.<Iterable<EIPAddress>>any());
        dataStorageService.storeEipAddresses();
        verify(computeServices).getAllAddresses();
        verify(computeServices).getAllElasticIPsWithAccountId();
        verify(eIPAddressRepository).saveAllAndFlush(Mockito.<Iterable<EIPAddress>>any());
        verify(eIPAddressRepository).findAll();
        verify(eIPAddressRepository).deleteAll(Mockito.<Iterable<EIPAddress>>any());
    }

    /**
     * Method under test: {@link DataStorageService#storeEipAddresses()}
     */
    @Test
    void testStoreEipAddresses3() {
        AwsAccountCredentials awsAccountCredentials = new AwsAccountCredentials();
        awsAccountCredentials.setAccessKeyId("EXAMPLEakiAIOSFODNN7");
        awsAccountCredentials.setAccountId("42");
        awsAccountCredentials.setAccountName("Dr Jane Doe");
        awsAccountCredentials.setDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        awsAccountCredentials.setEbsOptimizationSuggestions(new ArrayList<>());
        awsAccountCredentials.setEbsVolumes(new ArrayList<>());
        awsAccountCredentials.setEc2Instances(new ArrayList<>());
        awsAccountCredentials.setEc2OptimizationSuggestions(new ArrayList<>());
        awsAccountCredentials.setEipAddresses(new ArrayList<>());
        awsAccountCredentials.setEipOptimizationSuggestions(new ArrayList<>());
        awsAccountCredentials.setId(1L);
        awsAccountCredentials.setRdsInstances(new ArrayList<>());
        awsAccountCredentials.setRdsOptimizationSuggestions(new ArrayList<>());
        awsAccountCredentials.setS3OptimizationSuggestions(new ArrayList<>());
        awsAccountCredentials.setSecretAccessKey("EXAMPLEakiAIOSFODNN7");
        awsAccountCredentials.setStorageBuckets(new ArrayList<>());
        when(awsCredentialsService.getCredentialsByAccountId(Mockito.<String>any())).thenReturn(awsAccountCredentials);

        ArrayList<EipDTO> eipDTOList = new ArrayList<>();
        EipDTO buildResult = EipDTO.builder()
                .allocationId("42")
                .associatedAccount("3")
                .associationId("42")
                .instanceId("42")
                .privateIp("Private Ip")
                .publicIp("Public Ip")
                .build();
        eipDTOList.add(buildResult);
        when(computeServices.getAllAddresses()).thenReturn(new ArrayList<>());
        when(computeServices.getAllElasticIPsWithAccountId()).thenReturn(eipDTOList);

        AwsAccountCredentials associatedAccount = new AwsAccountCredentials();
        associatedAccount.setAccessKeyId("EXAMPLEakiAIOSFODNN7");
        associatedAccount.setAccountId("42");
        associatedAccount.setAccountName("Dr Jane Doe");
        associatedAccount.setDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        associatedAccount.setEbsOptimizationSuggestions(new ArrayList<>());
        associatedAccount.setEbsVolumes(new ArrayList<>());
        associatedAccount.setEc2Instances(new ArrayList<>());
        associatedAccount.setEc2OptimizationSuggestions(new ArrayList<>());
        associatedAccount.setEipAddresses(new ArrayList<>());
        associatedAccount.setEipOptimizationSuggestions(new ArrayList<>());
        associatedAccount.setId(1L);
        associatedAccount.setRdsInstances(new ArrayList<>());
        associatedAccount.setRdsOptimizationSuggestions(new ArrayList<>());
        associatedAccount.setS3OptimizationSuggestions(new ArrayList<>());
        associatedAccount.setSecretAccessKey("EXAMPLEakiAIOSFODNN7");
        associatedAccount.setStorageBuckets(new ArrayList<>());

        EIPAddress eipAddress = new EIPAddress();
        eipAddress.setAllocationId("42");
        eipAddress.setAssociatedAccount(associatedAccount);
        eipAddress.setAssociationId("42");
        eipAddress.setId(1L);
        eipAddress.setInstanceId("42");
        eipAddress.setLinkedSuggestion(new HashSet<>());
        eipAddress.setPrivateIp("Private Ip");
        eipAddress.setPublicIp("Public Ip");
        when(eIPAddressRepository.saveAndFlush(Mockito.<EIPAddress>any())).thenReturn(eipAddress);
        when(eIPAddressRepository.saveAllAndFlush(Mockito.<Iterable<EIPAddress>>any())).thenReturn(new ArrayList<>());
        when(eIPAddressRepository.findAll()).thenReturn(new ArrayList<>());
        doNothing().when(eIPAddressRepository).deleteAll(Mockito.<Iterable<EIPAddress>>any());
        dataStorageService.storeEipAddresses();
        verify(awsCredentialsService).getCredentialsByAccountId(Mockito.<String>any());
        verify(computeServices).getAllAddresses();
        verify(computeServices).getAllElasticIPsWithAccountId();
        verify(eIPAddressRepository).saveAndFlush(Mockito.<EIPAddress>any());
        verify(eIPAddressRepository).saveAllAndFlush(Mockito.<Iterable<EIPAddress>>any());
        verify(eIPAddressRepository).findAll();
        verify(eIPAddressRepository).deleteAll(Mockito.<Iterable<EIPAddress>>any());
    }

    /**
     * Method under test: {@link DataStorageService#storeEipAddresses()}
     */
    @Test
    void testStoreEipAddresses4() {
        AwsAccountCredentials awsAccountCredentials = new AwsAccountCredentials();
        awsAccountCredentials.setAccessKeyId("EXAMPLEakiAIOSFODNN7");
        awsAccountCredentials.setAccountId("42");
        awsAccountCredentials.setAccountName("Dr Jane Doe");
        awsAccountCredentials.setDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        awsAccountCredentials.setEbsOptimizationSuggestions(new ArrayList<>());
        awsAccountCredentials.setEbsVolumes(new ArrayList<>());
        awsAccountCredentials.setEc2Instances(new ArrayList<>());
        awsAccountCredentials.setEc2OptimizationSuggestions(new ArrayList<>());
        awsAccountCredentials.setEipAddresses(new ArrayList<>());
        awsAccountCredentials.setEipOptimizationSuggestions(new ArrayList<>());
        awsAccountCredentials.setId(1L);
        awsAccountCredentials.setRdsInstances(new ArrayList<>());
        awsAccountCredentials.setRdsOptimizationSuggestions(new ArrayList<>());
        awsAccountCredentials.setS3OptimizationSuggestions(new ArrayList<>());
        awsAccountCredentials.setSecretAccessKey("EXAMPLEakiAIOSFODNN7");
        awsAccountCredentials.setStorageBuckets(new ArrayList<>());
        when(awsCredentialsService.getCredentialsByAccountId(Mockito.<String>any())).thenReturn(awsAccountCredentials);

        AwsAccountCredentials associatedAccount = new AwsAccountCredentials();
        associatedAccount.setAccessKeyId("Storing EIP Addresses");
        associatedAccount.setAccountId("Storing EIP Addresses");
        associatedAccount.setAccountName("Prof Albert Einstein");
        associatedAccount.setDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        associatedAccount.setEbsOptimizationSuggestions(new ArrayList<>());
        associatedAccount.setEbsVolumes(new ArrayList<>());
        associatedAccount.setEc2Instances(new ArrayList<>());
        associatedAccount.setEc2OptimizationSuggestions(new ArrayList<>());
        associatedAccount.setEipAddresses(new ArrayList<>());
        associatedAccount.setEipOptimizationSuggestions(new ArrayList<>());
        associatedAccount.setId(3L);
        associatedAccount.setRdsInstances(new ArrayList<>());
        associatedAccount.setRdsOptimizationSuggestions(new ArrayList<>());
        associatedAccount.setS3OptimizationSuggestions(new ArrayList<>());
        associatedAccount.setSecretAccessKey("EIP Addresses stored");
        associatedAccount.setStorageBuckets(new ArrayList<>());

        EIPAddress eipAddress = new EIPAddress();
        eipAddress.setAllocationId("EIP Addresses stored");
        eipAddress.setAssociatedAccount(associatedAccount);
        eipAddress.setAssociationId("EIP Addresses stored");
        eipAddress.setId(3L);
        eipAddress.setInstanceId("EIP Addresses stored");
        eipAddress.setLinkedSuggestion(new HashSet<>());
        eipAddress.setPrivateIp("Private Ip");
        eipAddress.setPublicIp("Public Ip");

        ArrayList<EIPAddress> eipAddressList = new ArrayList<>();
        eipAddressList.add(eipAddress);

        ArrayList<EipDTO> eipDTOList = new ArrayList<>();
        EipDTO buildResult = EipDTO.builder()
                .allocationId("42")
                .associatedAccount("3")
                .associationId("42")
                .instanceId("42")
                .privateIp("Private Ip")
                .publicIp("Public Ip")
                .build();
        eipDTOList.add(buildResult);
        when(computeServices.getAllAddresses()).thenReturn(eipAddressList);
        when(computeServices.getAllElasticIPsWithAccountId()).thenReturn(eipDTOList);

        AwsAccountCredentials associatedAccount2 = new AwsAccountCredentials();
        associatedAccount2.setAccessKeyId("EXAMPLEakiAIOSFODNN7");
        associatedAccount2.setAccountId("42");
        associatedAccount2.setAccountName("Dr Jane Doe");
        associatedAccount2.setDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        associatedAccount2.setEbsOptimizationSuggestions(new ArrayList<>());
        associatedAccount2.setEbsVolumes(new ArrayList<>());
        associatedAccount2.setEc2Instances(new ArrayList<>());
        associatedAccount2.setEc2OptimizationSuggestions(new ArrayList<>());
        associatedAccount2.setEipAddresses(new ArrayList<>());
        associatedAccount2.setEipOptimizationSuggestions(new ArrayList<>());
        associatedAccount2.setId(1L);
        associatedAccount2.setRdsInstances(new ArrayList<>());
        associatedAccount2.setRdsOptimizationSuggestions(new ArrayList<>());
        associatedAccount2.setS3OptimizationSuggestions(new ArrayList<>());
        associatedAccount2.setSecretAccessKey("EXAMPLEakiAIOSFODNN7");
        associatedAccount2.setStorageBuckets(new ArrayList<>());

        EIPAddress eipAddress2 = new EIPAddress();
        eipAddress2.setAllocationId("42");
        eipAddress2.setAssociatedAccount(associatedAccount2);
        eipAddress2.setAssociationId("42");
        eipAddress2.setId(1L);
        eipAddress2.setInstanceId("42");
        eipAddress2.setLinkedSuggestion(new HashSet<>());
        eipAddress2.setPrivateIp("Private Ip");
        eipAddress2.setPublicIp("Public Ip");
        when(eIPAddressRepository.saveAndFlush(Mockito.<EIPAddress>any())).thenReturn(eipAddress2);
        when(eIPAddressRepository.saveAllAndFlush(Mockito.<Iterable<EIPAddress>>any())).thenReturn(new ArrayList<>());
        when(eIPAddressRepository.findAll()).thenReturn(new ArrayList<>());
        doNothing().when(eIPAddressRepository).deleteAll(Mockito.<Iterable<EIPAddress>>any());
        dataStorageService.storeEipAddresses();
        verify(awsCredentialsService).getCredentialsByAccountId(Mockito.<String>any());
        verify(computeServices).getAllAddresses();
        verify(computeServices).getAllElasticIPsWithAccountId();
        verify(eIPAddressRepository).saveAndFlush(Mockito.<EIPAddress>any());
        verify(eIPAddressRepository).saveAllAndFlush(Mockito.<Iterable<EIPAddress>>any());
        verify(eIPAddressRepository).findAll();
        verify(eIPAddressRepository).deleteAll(Mockito.<Iterable<EIPAddress>>any());
    }

    /**
     * Method under test: {@link DataStorageService#storeEbsVolumes()}
     */
    @Test
    void testStoreEbsVolumes() {
        when(computeServices.getAllEbsVolumesWithAccountId()).thenReturn(new ArrayList<>());
        when(computeServices.getAllVolumes()).thenReturn(new ArrayList<>());
        when(eBSVolumeRepository.saveAllAndFlush(Mockito.<Iterable<EBSVolume>>any())).thenReturn(new ArrayList<>());
        when(eBSVolumeRepository.findAll()).thenReturn(new ArrayList<>());
        doNothing().when(eBSVolumeRepository).deleteAll(Mockito.<Iterable<EBSVolume>>any());
        dataStorageService.storeEbsVolumes();
        verify(computeServices).getAllEbsVolumesWithAccountId();
        verify(computeServices).getAllVolumes();
        verify(eBSVolumeRepository).saveAllAndFlush(Mockito.<Iterable<EBSVolume>>any());
        verify(eBSVolumeRepository).findAll();
        verify(eBSVolumeRepository).deleteAll(Mockito.<Iterable<EBSVolume>>any());
    }

    /**
     * Method under test: {@link DataStorageService#storeEbsVolumes()}
     */
    @Test
    void testStoreEbsVolumes2() {
        AwsAccountCredentials awsAccountCredentials = new AwsAccountCredentials();
        awsAccountCredentials.setAccessKeyId("EXAMPLEakiAIOSFODNN7");
        awsAccountCredentials.setAccountId("42");
        awsAccountCredentials.setAccountName("Dr Jane Doe");
        awsAccountCredentials.setDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        awsAccountCredentials.setEbsOptimizationSuggestions(new ArrayList<>());
        awsAccountCredentials.setEbsVolumes(new ArrayList<>());
        awsAccountCredentials.setEc2Instances(new ArrayList<>());
        awsAccountCredentials.setEc2OptimizationSuggestions(new ArrayList<>());
        awsAccountCredentials.setEipAddresses(new ArrayList<>());
        awsAccountCredentials.setEipOptimizationSuggestions(new ArrayList<>());
        awsAccountCredentials.setId(1L);
        awsAccountCredentials.setRdsInstances(new ArrayList<>());
        awsAccountCredentials.setRdsOptimizationSuggestions(new ArrayList<>());
        awsAccountCredentials.setS3OptimizationSuggestions(new ArrayList<>());
        awsAccountCredentials.setSecretAccessKey("EXAMPLEakiAIOSFODNN7");
        awsAccountCredentials.setStorageBuckets(new ArrayList<>());
        when(awsCredentialsService.getCredentialsByAccountId(Mockito.<String>any())).thenReturn(awsAccountCredentials);

        ArrayList<EbsDTO> ebsDTOList = new ArrayList<>();
        EbsDTO.EbsDTOBuilder associatedAccountResult = EbsDTO.builder().associatedAccount("3");
        EbsDTO.EbsDTOBuilder stateResult = associatedAccountResult
                .creationTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
                .environmentType("Environment Type")
                .instanceId("42")
                .operationHours("Operation Hours")
                .ownerEmail("jane.doe@example.org")
                .productId("42")
                .region("us-east-2")
                .size(3L)
                .state("MD");
        EbsDTO buildResult = stateResult.tags(new HashMap<>()).volumeId("42").volumeType("Volume Type").build();
        ebsDTOList.add(buildResult);
        when(computeServices.getAllEbsVolumesWithAccountId()).thenReturn(ebsDTOList);
        when(computeServices.getAllVolumes()).thenReturn(new ArrayList<>());

        AwsAccountCredentials associatedAccount = new AwsAccountCredentials();
        associatedAccount.setAccessKeyId("EXAMPLEakiAIOSFODNN7");
        associatedAccount.setAccountId("42");
        associatedAccount.setAccountName("Dr Jane Doe");
        associatedAccount.setDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        associatedAccount.setEbsOptimizationSuggestions(new ArrayList<>());
        associatedAccount.setEbsVolumes(new ArrayList<>());
        associatedAccount.setEc2Instances(new ArrayList<>());
        associatedAccount.setEc2OptimizationSuggestions(new ArrayList<>());
        associatedAccount.setEipAddresses(new ArrayList<>());
        associatedAccount.setEipOptimizationSuggestions(new ArrayList<>());
        associatedAccount.setId(1L);
        associatedAccount.setRdsInstances(new ArrayList<>());
        associatedAccount.setRdsOptimizationSuggestions(new ArrayList<>());
        associatedAccount.setS3OptimizationSuggestions(new ArrayList<>());
        associatedAccount.setSecretAccessKey("EXAMPLEakiAIOSFODNN7");
        associatedAccount.setStorageBuckets(new ArrayList<>());

        EBSVolume ebsVolume = new EBSVolume();
        ebsVolume.setAssociatedAccount(associatedAccount);
        ebsVolume.setCreationTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        ebsVolume.setCreationTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        ebsVolume.setEnvironmentType("Environment Type");
        ebsVolume.setId(1L);
        ebsVolume.setInstanceId("42");
        ebsVolume.setLastUpdateTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        ebsVolume.setOperationHours("Operation Hours");
        ebsVolume.setOwnerEmail("jane.doe@example.org");
        ebsVolume.setProductId("42");
        ebsVolume.setRegion("us-east-2");
        ebsVolume.setSize(3L);
        ebsVolume.setState("MD");
        ebsVolume.setSuggestions(new HashSet<>());
        ebsVolume.setVolumeId("42");
        ebsVolume.setVolumeType("Volume Type");
        when(eBSVolumeRepository.saveAndFlush(Mockito.<EBSVolume>any())).thenReturn(ebsVolume);
        when(eBSVolumeRepository.saveAllAndFlush(Mockito.<Iterable<EBSVolume>>any())).thenReturn(new ArrayList<>());
        when(eBSVolumeRepository.findAll()).thenReturn(new ArrayList<>());
        doNothing().when(eBSVolumeRepository).deleteAll(Mockito.<Iterable<EBSVolume>>any());
        dataStorageService.storeEbsVolumes();
        verify(awsCredentialsService).getCredentialsByAccountId(Mockito.<String>any());
        verify(computeServices).getAllEbsVolumesWithAccountId();
        verify(computeServices).getAllVolumes();
        verify(eBSVolumeRepository).saveAndFlush(Mockito.<EBSVolume>any());
        verify(eBSVolumeRepository).saveAllAndFlush(Mockito.<Iterable<EBSVolume>>any());
        verify(eBSVolumeRepository).findAll();
        verify(eBSVolumeRepository).deleteAll(Mockito.<Iterable<EBSVolume>>any());
    }

    /**
     * Method under test: {@link DataStorageService#storeEbsVolumes()}
     */
    @Test
    void testStoreEbsVolumes3() {
        AwsAccountCredentials awsAccountCredentials = new AwsAccountCredentials();
        awsAccountCredentials.setAccessKeyId("EXAMPLEakiAIOSFODNN7");
        awsAccountCredentials.setAccountId("42");
        awsAccountCredentials.setAccountName("Dr Jane Doe");
        awsAccountCredentials.setDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        awsAccountCredentials.setEbsOptimizationSuggestions(new ArrayList<>());
        awsAccountCredentials.setEbsVolumes(new ArrayList<>());
        awsAccountCredentials.setEc2Instances(new ArrayList<>());
        awsAccountCredentials.setEc2OptimizationSuggestions(new ArrayList<>());
        awsAccountCredentials.setEipAddresses(new ArrayList<>());
        awsAccountCredentials.setEipOptimizationSuggestions(new ArrayList<>());
        awsAccountCredentials.setId(1L);
        awsAccountCredentials.setRdsInstances(new ArrayList<>());
        awsAccountCredentials.setRdsOptimizationSuggestions(new ArrayList<>());
        awsAccountCredentials.setS3OptimizationSuggestions(new ArrayList<>());
        awsAccountCredentials.setSecretAccessKey("EXAMPLEakiAIOSFODNN7");
        awsAccountCredentials.setStorageBuckets(new ArrayList<>());
        when(awsCredentialsService.getCredentialsByAccountId(Mockito.<String>any())).thenReturn(awsAccountCredentials);

        ArrayList<EbsDTO> ebsDTOList = new ArrayList<>();
        EbsDTO.EbsDTOBuilder associatedAccountResult = EbsDTO.builder().associatedAccount("3");
        EbsDTO.EbsDTOBuilder stateResult = associatedAccountResult
                .creationTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
                .environmentType("Environment Type")
                .instanceId("42")
                .operationHours("Operation Hours")
                .ownerEmail("jane.doe@example.org")
                .productId("42")
                .region("us-east-2")
                .size(3L)
                .state("MD");
        EbsDTO buildResult = stateResult.tags(new HashMap<>()).volumeId("42").volumeType("Volume Type").build();
        ebsDTOList.add(buildResult);

        AwsAccountCredentials associatedAccount = new AwsAccountCredentials();
        associatedAccount.setAccessKeyId("EXAMPLEakiAIOSFODNN7");
        associatedAccount.setAccountId("42");
        associatedAccount.setAccountName("Dr Jane Doe");
        associatedAccount.setDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        associatedAccount.setEbsOptimizationSuggestions(new ArrayList<>());
        associatedAccount.setEbsVolumes(new ArrayList<>());
        associatedAccount.setEc2Instances(new ArrayList<>());
        associatedAccount.setEc2OptimizationSuggestions(new ArrayList<>());
        associatedAccount.setEipAddresses(new ArrayList<>());
        associatedAccount.setEipOptimizationSuggestions(new ArrayList<>());
        associatedAccount.setId(1L);
        associatedAccount.setRdsInstances(new ArrayList<>());
        associatedAccount.setRdsOptimizationSuggestions(new ArrayList<>());
        associatedAccount.setS3OptimizationSuggestions(new ArrayList<>());
        associatedAccount.setSecretAccessKey("EXAMPLEakiAIOSFODNN7");
        associatedAccount.setStorageBuckets(new ArrayList<>());

        EBSVolume ebsVolume = new EBSVolume();
        ebsVolume.setAssociatedAccount(associatedAccount);
        ebsVolume.setCreationTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        ebsVolume.setCreationTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        ebsVolume.setEnvironmentType("Storing EBS Volumes");
        ebsVolume.setId(1L);
        ebsVolume.setInstanceId("42");
        ebsVolume.setLastUpdateTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        ebsVolume.setOperationHours("Storing EBS Volumes");
        ebsVolume.setOwnerEmail("jane.doe@example.org");
        ebsVolume.setProductId("42");
        ebsVolume.setRegion("us-east-2");
        ebsVolume.setSize(3L);
        ebsVolume.setState("MD");
        ebsVolume.setSuggestions(new HashSet<>());
        ebsVolume.setVolumeId("42");
        ebsVolume.setVolumeType("Storing EBS Volumes");

        ArrayList<EBSVolume> ebsVolumeList = new ArrayList<>();
        ebsVolumeList.add(ebsVolume);
        when(computeServices.getAllEbsVolumesWithAccountId()).thenReturn(ebsDTOList);
        when(computeServices.getAllVolumes()).thenReturn(ebsVolumeList);
        when(eBSVolumeRepository.saveAllAndFlush(Mockito.<Iterable<EBSVolume>>any())).thenReturn(new ArrayList<>());
        when(eBSVolumeRepository.findAll()).thenReturn(new ArrayList<>());
        doNothing().when(eBSVolumeRepository).deleteAll(Mockito.<Iterable<EBSVolume>>any());
        dataStorageService.storeEbsVolumes();
        verify(awsCredentialsService).getCredentialsByAccountId(Mockito.<String>any());
        verify(computeServices).getAllEbsVolumesWithAccountId();
        verify(computeServices).getAllVolumes();
        verify(eBSVolumeRepository).saveAllAndFlush(Mockito.<Iterable<EBSVolume>>any());
        verify(eBSVolumeRepository).findAll();
        verify(eBSVolumeRepository).deleteAll(Mockito.<Iterable<EBSVolume>>any());
    }

    /**
     * Method under test: {@link DataStorageService#storeEbsVolumes()}
     */
    @Test
    void testStoreEbsVolumes4() {
        AwsAccountCredentials awsAccountCredentials = new AwsAccountCredentials();
        awsAccountCredentials.setAccessKeyId("EXAMPLEakiAIOSFODNN7");
        awsAccountCredentials.setAccountId("42");
        awsAccountCredentials.setAccountName("Dr Jane Doe");
        awsAccountCredentials.setDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        awsAccountCredentials.setEbsOptimizationSuggestions(new ArrayList<>());
        awsAccountCredentials.setEbsVolumes(new ArrayList<>());
        awsAccountCredentials.setEc2Instances(new ArrayList<>());
        awsAccountCredentials.setEc2OptimizationSuggestions(new ArrayList<>());
        awsAccountCredentials.setEipAddresses(new ArrayList<>());
        awsAccountCredentials.setEipOptimizationSuggestions(new ArrayList<>());
        awsAccountCredentials.setId(1L);
        awsAccountCredentials.setRdsInstances(new ArrayList<>());
        awsAccountCredentials.setRdsOptimizationSuggestions(new ArrayList<>());
        awsAccountCredentials.setS3OptimizationSuggestions(new ArrayList<>());
        awsAccountCredentials.setSecretAccessKey("EXAMPLEakiAIOSFODNN7");
        awsAccountCredentials.setStorageBuckets(new ArrayList<>());
        when(awsCredentialsService.getCredentialsByAccountId(Mockito.<String>any())).thenReturn(awsAccountCredentials);

        ArrayList<EbsDTO> ebsDTOList = new ArrayList<>();
        EbsDTO.EbsDTOBuilder associatedAccountResult = EbsDTO.builder().associatedAccount("3");
        EbsDTO.EbsDTOBuilder stateResult = associatedAccountResult
                .creationTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
                .environmentType("Environment Type")
                .instanceId("42")
                .operationHours("Operation Hours")
                .ownerEmail("jane.doe@example.org")
                .productId("42")
                .region("us-east-2")
                .size(3L)
                .state("MD");
        EbsDTO buildResult = stateResult.tags(new HashMap<>()).volumeId("42").volumeType("Volume Type").build();
        ebsDTOList.add(buildResult);

        AwsAccountCredentials associatedAccount = new AwsAccountCredentials();
        associatedAccount.setAccessKeyId("EXAMPLEakiAIOSFODNN7");
        associatedAccount.setAccountId("42");
        associatedAccount.setAccountName("Dr Jane Doe");
        associatedAccount.setDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        associatedAccount.setEbsOptimizationSuggestions(new ArrayList<>());
        associatedAccount.setEbsVolumes(new ArrayList<>());
        associatedAccount.setEc2Instances(new ArrayList<>());
        associatedAccount.setEc2OptimizationSuggestions(new ArrayList<>());
        associatedAccount.setEipAddresses(new ArrayList<>());
        associatedAccount.setEipOptimizationSuggestions(new ArrayList<>());
        associatedAccount.setId(1L);
        associatedAccount.setRdsInstances(new ArrayList<>());
        associatedAccount.setRdsOptimizationSuggestions(new ArrayList<>());
        associatedAccount.setS3OptimizationSuggestions(new ArrayList<>());
        associatedAccount.setSecretAccessKey("EXAMPLEakiAIOSFODNN7");
        associatedAccount.setStorageBuckets(new ArrayList<>());

        EBSVolume ebsVolume = new EBSVolume();
        ebsVolume.setAssociatedAccount(associatedAccount);
        ebsVolume.setCreationTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        ebsVolume.setCreationTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        ebsVolume.setEnvironmentType("Storing EBS Volumes");
        ebsVolume.setId(1L);
        ebsVolume.setInstanceId("42");
        ebsVolume.setLastUpdateTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        ebsVolume.setOperationHours("Storing EBS Volumes");
        ebsVolume.setOwnerEmail("jane.doe@example.org");
        ebsVolume.setProductId("42");
        ebsVolume.setRegion("us-east-2");
        ebsVolume.setSize(3L);
        ebsVolume.setState("MD");
        ebsVolume.setSuggestions(new HashSet<>());
        ebsVolume.setVolumeId("42");
        ebsVolume.setVolumeType("Storing EBS Volumes");

        AwsAccountCredentials associatedAccount2 = new AwsAccountCredentials();
        associatedAccount2.setAccessKeyId("42");
        associatedAccount2.setAccountId("3");
        associatedAccount2.setAccountName("Mr John Smith");
        associatedAccount2.setDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        associatedAccount2.setEbsOptimizationSuggestions(new ArrayList<>());
        associatedAccount2.setEbsVolumes(new ArrayList<>());
        associatedAccount2.setEc2Instances(new ArrayList<>());
        associatedAccount2.setEc2OptimizationSuggestions(new ArrayList<>());
        associatedAccount2.setEipAddresses(new ArrayList<>());
        associatedAccount2.setEipOptimizationSuggestions(new ArrayList<>());
        associatedAccount2.setId(2L);
        associatedAccount2.setRdsInstances(new ArrayList<>());
        associatedAccount2.setRdsOptimizationSuggestions(new ArrayList<>());
        associatedAccount2.setS3OptimizationSuggestions(new ArrayList<>());
        associatedAccount2.setSecretAccessKey("Storing EBS Volumes");
        associatedAccount2.setStorageBuckets(new ArrayList<>());

        EBSVolume ebsVolume2 = new EBSVolume();
        ebsVolume2.setAssociatedAccount(associatedAccount2);
        ebsVolume2.setCreationTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        ebsVolume2.setCreationTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        ebsVolume2.setEnvironmentType("42");
        ebsVolume2.setId(2L);
        ebsVolume2.setInstanceId("Storing EBS Volumes");
        ebsVolume2.setLastUpdateTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        ebsVolume2.setOperationHours("42");
        ebsVolume2.setOwnerEmail("john.smith@example.org");
        ebsVolume2.setProductId("Storing EBS Volumes");
        ebsVolume2.setRegion("Storing EBS Volumes");
        ebsVolume2.setSize(1L);
        ebsVolume2.setState("Storing EBS Volumes");
        ebsVolume2.setSuggestions(new HashSet<>());
        ebsVolume2.setVolumeId("Storing EBS Volumes");
        ebsVolume2.setVolumeType("42");

        ArrayList<EBSVolume> ebsVolumeList = new ArrayList<>();
        ebsVolumeList.add(ebsVolume2);
        ebsVolumeList.add(ebsVolume);
        when(computeServices.getAllEbsVolumesWithAccountId()).thenReturn(ebsDTOList);
        when(computeServices.getAllVolumes()).thenReturn(ebsVolumeList);
        when(eBSVolumeRepository.saveAllAndFlush(Mockito.<Iterable<EBSVolume>>any())).thenReturn(new ArrayList<>());
        when(eBSVolumeRepository.findAll()).thenReturn(new ArrayList<>());
        doNothing().when(eBSVolumeRepository).deleteAll(Mockito.<Iterable<EBSVolume>>any());
        dataStorageService.storeEbsVolumes();
        verify(awsCredentialsService).getCredentialsByAccountId(Mockito.<String>any());
        verify(computeServices).getAllEbsVolumesWithAccountId();
        verify(computeServices).getAllVolumes();
        verify(eBSVolumeRepository).saveAllAndFlush(Mockito.<Iterable<EBSVolume>>any());
        verify(eBSVolumeRepository).findAll();
        verify(eBSVolumeRepository).deleteAll(Mockito.<Iterable<EBSVolume>>any());
    }

    /**
     * Method under test: {@link DataStorageService#storeS3Buckets()}
     */
    @Test
    void testStoreS3Buckets() {
        when(s3Service.s3testAllRegionsStream()).thenReturn(new ArrayList<>());
        dataStorageService.storeS3Buckets();
        verify(s3Service).s3testAllRegionsStream();
    }

    /**
     * Method under test: {@link DataStorageService#storeS3Buckets()}
     */
    @Test
    void testStoreS3Buckets2() {
        ArrayList<S3BucketDTO> s3BucketDTOList = new ArrayList<>();
        S3BucketDTO.S3BucketDTOBuilder associatedAccountResult = S3BucketDTO.builder().associatedAccount("3");
        S3BucketDTO.S3BucketDTOBuilder sizeResult = associatedAccountResult
                .creationDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
                .name("bucket-name")
                .objectCount(3L)
                .ownerName("bucket-name")
                .region("us-east-2")
                .size(3L);
        S3BucketDTO buildResult = sizeResult.tags(new HashMap<>()).build();
        s3BucketDTOList.add(buildResult);
        when(s3Service.s3testAllRegionsStream()).thenReturn(s3BucketDTOList);
        when(storageBucketRepository.existsByName(Mockito.<String>any())).thenReturn(true);
        dataStorageService.storeS3Buckets();
        verify(s3Service).s3testAllRegionsStream();
        verify(storageBucketRepository).existsByName(Mockito.<String>any());
    }

    /**
     * Method under test: {@link DataStorageService#storeS3Buckets()}
     */
    @Test
    void testStoreS3Buckets3() {
        AwsAccountCredentials awsAccountCredentials = new AwsAccountCredentials();
        awsAccountCredentials.setAccessKeyId("EXAMPLEakiAIOSFODNN7");
        awsAccountCredentials.setAccountId("42");
        awsAccountCredentials.setAccountName("Dr Jane Doe");
        awsAccountCredentials.setDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        awsAccountCredentials.setEbsOptimizationSuggestions(new ArrayList<>());
        awsAccountCredentials.setEbsVolumes(new ArrayList<>());
        awsAccountCredentials.setEc2Instances(new ArrayList<>());
        awsAccountCredentials.setEc2OptimizationSuggestions(new ArrayList<>());
        awsAccountCredentials.setEipAddresses(new ArrayList<>());
        awsAccountCredentials.setEipOptimizationSuggestions(new ArrayList<>());
        awsAccountCredentials.setId(1L);
        awsAccountCredentials.setRdsInstances(new ArrayList<>());
        awsAccountCredentials.setRdsOptimizationSuggestions(new ArrayList<>());
        awsAccountCredentials.setS3OptimizationSuggestions(new ArrayList<>());
        awsAccountCredentials.setSecretAccessKey("EXAMPLEakiAIOSFODNN7");
        awsAccountCredentials.setStorageBuckets(new ArrayList<>());
        when(awsCredentialsService.getCredentialsByAccountId(Mockito.<String>any())).thenReturn(awsAccountCredentials);

        ArrayList<S3BucketDTO> s3BucketDTOList = new ArrayList<>();
        S3BucketDTO.S3BucketDTOBuilder associatedAccountResult = S3BucketDTO.builder().associatedAccount("3");
        S3BucketDTO.S3BucketDTOBuilder sizeResult = associatedAccountResult
                .creationDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
                .name("bucket-name")
                .objectCount(3L)
                .ownerName("bucket-name")
                .region("us-east-2")
                .size(3L);
        S3BucketDTO buildResult = sizeResult.tags(new HashMap<>()).build();
        s3BucketDTOList.add(buildResult);
        when(s3Service.s3testAllRegionsStream()).thenReturn(s3BucketDTOList);

        AwsAccountCredentials associatedAccount = new AwsAccountCredentials();
        associatedAccount.setAccessKeyId("EXAMPLEakiAIOSFODNN7");
        associatedAccount.setAccountId("42");
        associatedAccount.setAccountName("Dr Jane Doe");
        associatedAccount.setDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        associatedAccount.setEbsOptimizationSuggestions(new ArrayList<>());
        associatedAccount.setEbsVolumes(new ArrayList<>());
        associatedAccount.setEc2Instances(new ArrayList<>());
        associatedAccount.setEc2OptimizationSuggestions(new ArrayList<>());
        associatedAccount.setEipAddresses(new ArrayList<>());
        associatedAccount.setEipOptimizationSuggestions(new ArrayList<>());
        associatedAccount.setId(1L);
        associatedAccount.setRdsInstances(new ArrayList<>());
        associatedAccount.setRdsOptimizationSuggestions(new ArrayList<>());
        associatedAccount.setS3OptimizationSuggestions(new ArrayList<>());
        associatedAccount.setSecretAccessKey("EXAMPLEakiAIOSFODNN7");
        associatedAccount.setStorageBuckets(new ArrayList<>());

        StorageBucket storageBucket = new StorageBucket();
        storageBucket.setAssociatedAccount(associatedAccount);
        storageBucket
                .setCreationDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        storageBucket.setCreationTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        storageBucket.setId(1L);
        storageBucket.setLastUpdateTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        storageBucket.setMonitoringEnabled(true);
        storageBucket.setName("bucket-name");
        storageBucket.setObjectCount(3L);
        storageBucket.setOwner("Owner");
        storageBucket.setOwnerEmail("jane.doe@example.org");
        storageBucket.setPurpose("Purpose");
        storageBucket.setRegion("us-east-2");
        storageBucket.setSize(3L);
        storageBucket.setSuggestions(new HashSet<>());
        when(storageBucketRepository.existsByName(Mockito.<String>any())).thenReturn(false);
        when(storageBucketRepository.save(Mockito.<StorageBucket>any())).thenReturn(storageBucket);
        dataStorageService.storeS3Buckets();
        verify(awsCredentialsService).getCredentialsByAccountId(Mockito.<String>any());
        verify(s3Service).s3testAllRegionsStream();
        verify(storageBucketRepository).existsByName(Mockito.<String>any());
        verify(storageBucketRepository).save(Mockito.<StorageBucket>any());
    }
}

