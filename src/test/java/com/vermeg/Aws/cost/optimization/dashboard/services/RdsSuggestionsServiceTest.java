package com.vermeg.aws.cost.optimization.dashboard.services;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.vermeg.aws.cost.optimization.dashboard.entities.AwsAccountCredentials;
import com.vermeg.aws.cost.optimization.dashboard.entities.RDSInstance;
import com.vermeg.aws.cost.optimization.dashboard.entities.RDSOptimizationSuggestion;
import com.vermeg.aws.cost.optimization.dashboard.entities.RDSSuggestionCategory;
import com.vermeg.aws.cost.optimization.dashboard.entities.SuggestionStatus;
import com.vermeg.aws.cost.optimization.dashboard.repositories.RdsOptimizationSuggestionRepository;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {RdsSuggestionsService.class})
@ExtendWith(SpringExtension.class)
class RdsSuggestionsServiceTest {
    @MockBean
    private AutoScalingService autoScalingService;

    @MockBean
    private RDSService rDSService;

    @MockBean
    private RdsOptimizationSuggestionRepository rdsOptimizationSuggestionRepository;

    @Autowired
    private RdsSuggestionsService rdsSuggestionsService;

    /**
     * Method under test: {@link RdsSuggestionsService#generateRdsSuggestions()}
     */
    @Test
    void testGenerateRdsSuggestions() {
        ArrayList<RDSOptimizationSuggestion> rdsOptimizationSuggestionList = new ArrayList<>();
        when(rdsOptimizationSuggestionRepository.saveAll(Mockito.<Iterable<RDSOptimizationSuggestion>>any()))
                .thenReturn(rdsOptimizationSuggestionList);
        when(rDSService.getAllRdsInstances()).thenReturn(new ArrayList<>());
        List<RDSOptimizationSuggestion> actualGenerateRdsSuggestionsResult = rdsSuggestionsService
                .generateRdsSuggestions();
        assertSame(rdsOptimizationSuggestionList, actualGenerateRdsSuggestionsResult);
        assertTrue(actualGenerateRdsSuggestionsResult.isEmpty());
        verify(rdsOptimizationSuggestionRepository).saveAll(Mockito.<Iterable<RDSOptimizationSuggestion>>any());
        verify(rDSService).getAllRdsInstances();
    }

    /**
     * Method under test: {@link RdsSuggestionsService#generateRdsSuggestions()}
     */
    @Test
    void testGenerateRdsSuggestions2() {
        ArrayList<RDSOptimizationSuggestion> rdsOptimizationSuggestionList = new ArrayList<>();
        when(rdsOptimizationSuggestionRepository.saveAll(Mockito.<Iterable<RDSOptimizationSuggestion>>any()))
                .thenReturn(rdsOptimizationSuggestionList);
        when(autoScalingService.isInstanceInAutoScalingGroup(Mockito.<String>any())).thenReturn(true);

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
        rdsInstance.setDbInstanceClass("Generating RDS Suggestions");
        rdsInstance.setDbInstanceIdentifier("42");
        rdsInstance.setEndpointAddress("https://config.us-east-2.amazonaws.com");
        rdsInstance.setEndpointPort(3);
        rdsInstance.setEngine("Generating RDS Suggestions");
        rdsInstance.setEngineVersion("1.0.2");
        rdsInstance.setId(1L);
        rdsInstance.setLastDowntimeTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        rdsInstance.setLastUpdateTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        rdsInstance.setLastUptimeTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        rdsInstance.setLinkedSuggestion(new HashSet<>());
        rdsInstance.setMonitoringEnabled(true);
        rdsInstance.setOperationHours("Generating RDS Suggestions");
        rdsInstance.setOwnerEmail("jane.doe@example.org");
        rdsInstance.setProductId("42");
        rdsInstance.setRegion("us-east-2");
        rdsInstance.setStatus("Generating RDS Suggestions");

        ArrayList<RDSInstance> rdsInstanceList = new ArrayList<>();
        rdsInstanceList.add(rdsInstance);
        when(rDSService.getAllRdsInstances()).thenReturn(rdsInstanceList);
        List<RDSOptimizationSuggestion> actualGenerateRdsSuggestionsResult = rdsSuggestionsService
                .generateRdsSuggestions();
        assertSame(rdsOptimizationSuggestionList, actualGenerateRdsSuggestionsResult);
        assertTrue(actualGenerateRdsSuggestionsResult.isEmpty());
        verify(rdsOptimizationSuggestionRepository).saveAll(Mockito.<Iterable<RDSOptimizationSuggestion>>any());
        verify(autoScalingService).isInstanceInAutoScalingGroup(Mockito.<String>any());
        verify(rDSService).getAllRdsInstances();
    }

    /**
     * Method under test: {@link RdsSuggestionsService#generateRdsSuggestions()}
     */
    @Test
    void testGenerateRdsSuggestions3() {
        ArrayList<RDSOptimizationSuggestion> rdsOptimizationSuggestionList = new ArrayList<>();
        when(rdsOptimizationSuggestionRepository.saveAll(Mockito.<Iterable<RDSOptimizationSuggestion>>any()))
                .thenReturn(rdsOptimizationSuggestionList);
        when(autoScalingService.isInstanceInAutoScalingGroup(Mockito.<String>any())).thenReturn(false);

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
        rdsInstance.setDbInstanceClass("Generating RDS Suggestions");
        rdsInstance.setDbInstanceIdentifier("42");
        rdsInstance.setEndpointAddress("https://config.us-east-2.amazonaws.com");
        rdsInstance.setEndpointPort(3);
        rdsInstance.setEngine("Generating RDS Suggestions");
        rdsInstance.setEngineVersion("1.0.2");
        rdsInstance.setId(1L);
        rdsInstance.setLastDowntimeTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        rdsInstance.setLastUpdateTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        rdsInstance.setLastUptimeTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        rdsInstance.setLinkedSuggestion(new HashSet<>());
        rdsInstance.setMonitoringEnabled(true);
        rdsInstance.setOperationHours("Generating RDS Suggestions");
        rdsInstance.setOwnerEmail("jane.doe@example.org");
        rdsInstance.setProductId("42");
        rdsInstance.setRegion("us-east-2");
        rdsInstance.setStatus("Generating RDS Suggestions");

        ArrayList<RDSInstance> rdsInstanceList = new ArrayList<>();
        rdsInstanceList.add(rdsInstance);
        when(rDSService.getAllRdsInstances()).thenReturn(rdsInstanceList);
        List<RDSOptimizationSuggestion> actualGenerateRdsSuggestionsResult = rdsSuggestionsService
                .generateRdsSuggestions();
        assertSame(rdsOptimizationSuggestionList, actualGenerateRdsSuggestionsResult);
        assertTrue(actualGenerateRdsSuggestionsResult.isEmpty());
        verify(rdsOptimizationSuggestionRepository).saveAll(Mockito.<Iterable<RDSOptimizationSuggestion>>any());
        verify(autoScalingService).isInstanceInAutoScalingGroup(Mockito.<String>any());
        verify(rDSService).getAllRdsInstances();
    }

    /**
     * Method under test: {@link RdsSuggestionsService#generateRdsSuggestions()}
     */
    @Test
    void testGenerateRdsSuggestions4() {
        ArrayList<RDSOptimizationSuggestion> rdsOptimizationSuggestionList = new ArrayList<>();
        when(rdsOptimizationSuggestionRepository.saveAll(Mockito.<Iterable<RDSOptimizationSuggestion>>any()))
                .thenReturn(rdsOptimizationSuggestionList);
        when(autoScalingService.isInstanceInAutoScalingGroup(Mockito.<String>any())).thenReturn(true);

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
        rdsInstance.setDbInstanceClass("Generating RDS Suggestions");
        rdsInstance.setDbInstanceIdentifier("42");
        rdsInstance.setEndpointAddress("https://config.us-east-2.amazonaws.com");
        rdsInstance.setEndpointPort(3);
        rdsInstance.setEngine("Generating RDS Suggestions");
        rdsInstance.setEngineVersion("1.0.2");
        rdsInstance.setId(1L);
        rdsInstance.setLastDowntimeTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        rdsInstance.setLastUpdateTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        rdsInstance.setLastUptimeTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        rdsInstance.setLinkedSuggestion(new HashSet<>());
        rdsInstance.setMonitoringEnabled(true);
        rdsInstance.setOperationHours("Generating RDS Suggestions");
        rdsInstance.setOwnerEmail("jane.doe@example.org");
        rdsInstance.setProductId("42");
        rdsInstance.setRegion("us-east-2");
        rdsInstance.setStatus("Generating RDS Suggestions");

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
        associatedAccount2.setSecretAccessKey("Generating RDS Suggestions");
        associatedAccount2.setStorageBuckets(new ArrayList<>());

        RDSInstance rdsInstance2 = new RDSInstance();
        rdsInstance2.setAllocatedStorage(0);
        rdsInstance2.setAssociatedAccount(associatedAccount2);
        rdsInstance2.setClientName("Mr John Smith");
        rdsInstance2.setCreationDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rdsInstance2.setCreationTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        rdsInstance2.setDbInstanceClass(
                "The following Rds instance is not part of an autoscaling group. It will not be able to scale according"
                        + " to its workload thus increasing the potential of overprovisioning");
        rdsInstance2.setDbInstanceIdentifier("Generating RDS Suggestions");
        rdsInstance2.setEndpointAddress("42 Main St");
        rdsInstance2.setEndpointPort(8080);
        rdsInstance2.setEngine(
                "The following Rds instance is not part of an autoscaling group. It will not be able to scale according"
                        + " to its workload thus increasing the potential of overprovisioning");
        rdsInstance2.setEngineVersion("Generating RDS Suggestions");
        rdsInstance2.setId(2L);
        rdsInstance2.setLastDowntimeTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        rdsInstance2.setLastUpdateTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        rdsInstance2.setLastUptimeTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        rdsInstance2.setLinkedSuggestion(new HashSet<>());
        rdsInstance2.setMonitoringEnabled(false);
        rdsInstance2.setOperationHours(
                "The following Rds instance is not part of an autoscaling group. It will not be able to scale according"
                        + " to its workload thus increasing the potential of overprovisioning");
        rdsInstance2.setOwnerEmail("john.smith@example.org");
        rdsInstance2.setProductId("Generating RDS Suggestions");
        rdsInstance2.setRegion("Generating RDS Suggestions");
        rdsInstance2.setStatus(
                "The following Rds instance is not part of an autoscaling group. It will not be able to scale according"
                        + " to its workload thus increasing the potential of overprovisioning");

        ArrayList<RDSInstance> rdsInstanceList = new ArrayList<>();
        rdsInstanceList.add(rdsInstance2);
        rdsInstanceList.add(rdsInstance);
        when(rDSService.getAllRdsInstances()).thenReturn(rdsInstanceList);
        List<RDSOptimizationSuggestion> actualGenerateRdsSuggestionsResult = rdsSuggestionsService
                .generateRdsSuggestions();
        assertSame(rdsOptimizationSuggestionList, actualGenerateRdsSuggestionsResult);
        assertTrue(actualGenerateRdsSuggestionsResult.isEmpty());
        verify(rdsOptimizationSuggestionRepository).saveAll(Mockito.<Iterable<RDSOptimizationSuggestion>>any());
        verify(autoScalingService, atLeast(1)).isInstanceInAutoScalingGroup(Mockito.<String>any());
        verify(rDSService).getAllRdsInstances();
    }

    /**
     * Method under test: {@link RdsSuggestionsService#getAllRdsSuggestions()}
     */
    @Test
    void testGetAllRdsSuggestions() {
        ArrayList<RDSOptimizationSuggestion> rdsOptimizationSuggestionList = new ArrayList<>();
        when(rdsOptimizationSuggestionRepository.findAll()).thenReturn(rdsOptimizationSuggestionList);
        List<RDSOptimizationSuggestion> actualAllRdsSuggestions = rdsSuggestionsService.getAllRdsSuggestions();
        assertSame(rdsOptimizationSuggestionList, actualAllRdsSuggestions);
        assertTrue(actualAllRdsSuggestions.isEmpty());
        verify(rdsOptimizationSuggestionRepository).findAll();
    }

    /**
     * Method under test: {@link RdsSuggestionsService#getRdsSuggestionById(Long)}
     */
    @Test
    void testGetRdsSuggestionById() {
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

        RDSInstance linkedInstance = new RDSInstance();
        linkedInstance.setAllocatedStorage(1);
        linkedInstance.setAssociatedAccount(associatedAccount2);
        linkedInstance.setClientName("Dr Jane Doe");
        linkedInstance.setCreationDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedInstance.setCreationTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedInstance.setDbInstanceClass("Db Instance Class");
        linkedInstance.setDbInstanceIdentifier("42");
        linkedInstance.setEndpointAddress("https://config.us-east-2.amazonaws.com");
        linkedInstance.setEndpointPort(3);
        linkedInstance.setEngine("Engine");
        linkedInstance.setEngineVersion("1.0.2");
        linkedInstance.setId(1L);
        linkedInstance.setLastDowntimeTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedInstance.setLastUpdateTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedInstance.setLastUptimeTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedInstance.setLinkedSuggestion(new HashSet<>());
        linkedInstance.setMonitoringEnabled(true);
        linkedInstance.setOperationHours("Operation Hours");
        linkedInstance.setOwnerEmail("jane.doe@example.org");
        linkedInstance.setProductId("42");
        linkedInstance.setRegion("us-east-2");
        linkedInstance.setStatus("Status");

        RDSOptimizationSuggestion rdsOptimizationSuggestion = new RDSOptimizationSuggestion();
        rdsOptimizationSuggestion.setAssociatedAccount(associatedAccount);
        rdsOptimizationSuggestion.setCategory(RDSSuggestionCategory.NOAUTOSCALINGGROUP);
        rdsOptimizationSuggestion.setCreatedDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rdsOptimizationSuggestion.setDescription("The characteristics of someone or something");
        rdsOptimizationSuggestion.setId(1L);
        rdsOptimizationSuggestion.setLinkedInstance(linkedInstance);
        rdsOptimizationSuggestion.setRecommendation("Recommendation");
        rdsOptimizationSuggestion.setStatus(SuggestionStatus.Pending);
        rdsOptimizationSuggestion.setTitle("Dr");
        Optional<RDSOptimizationSuggestion> ofResult = Optional.of(rdsOptimizationSuggestion);
        when(rdsOptimizationSuggestionRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertSame(rdsOptimizationSuggestion, rdsSuggestionsService.getRdsSuggestionById(1L));
        verify(rdsOptimizationSuggestionRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link RdsSuggestionsService#updateRdsSuggestionStatus(SuggestionStatus, Long)}
     */
    @Test
    void testUpdateRdsSuggestionStatus() {
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

        RDSInstance linkedInstance = new RDSInstance();
        linkedInstance.setAllocatedStorage(1);
        linkedInstance.setAssociatedAccount(associatedAccount2);
        linkedInstance.setClientName("Dr Jane Doe");
        linkedInstance.setCreationDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedInstance.setCreationTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedInstance.setDbInstanceClass("Db Instance Class");
        linkedInstance.setDbInstanceIdentifier("42");
        linkedInstance.setEndpointAddress("https://config.us-east-2.amazonaws.com");
        linkedInstance.setEndpointPort(3);
        linkedInstance.setEngine("Engine");
        linkedInstance.setEngineVersion("1.0.2");
        linkedInstance.setId(1L);
        linkedInstance.setLastDowntimeTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedInstance.setLastUpdateTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedInstance.setLastUptimeTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedInstance.setLinkedSuggestion(new HashSet<>());
        linkedInstance.setMonitoringEnabled(true);
        linkedInstance.setOperationHours("Operation Hours");
        linkedInstance.setOwnerEmail("jane.doe@example.org");
        linkedInstance.setProductId("42");
        linkedInstance.setRegion("us-east-2");
        linkedInstance.setStatus("Status");

        RDSOptimizationSuggestion rdsOptimizationSuggestion = new RDSOptimizationSuggestion();
        rdsOptimizationSuggestion.setAssociatedAccount(associatedAccount);
        rdsOptimizationSuggestion.setCategory(RDSSuggestionCategory.NOAUTOSCALINGGROUP);
        rdsOptimizationSuggestion.setCreatedDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rdsOptimizationSuggestion.setDescription("The characteristics of someone or something");
        rdsOptimizationSuggestion.setId(1L);
        rdsOptimizationSuggestion.setLinkedInstance(linkedInstance);
        rdsOptimizationSuggestion.setRecommendation("Recommendation");
        rdsOptimizationSuggestion.setStatus(SuggestionStatus.Pending);
        rdsOptimizationSuggestion.setTitle("Dr");
        Optional<RDSOptimizationSuggestion> ofResult = Optional.of(rdsOptimizationSuggestion);

        AwsAccountCredentials associatedAccount3 = new AwsAccountCredentials();
        associatedAccount3.setAccessKeyId("EXAMPLEakiAIOSFODNN7");
        associatedAccount3.setAccountId("42");
        associatedAccount3.setAccountName("Dr Jane Doe");
        associatedAccount3.setDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        associatedAccount3.setEbsOptimizationSuggestions(new ArrayList<>());
        associatedAccount3.setEbsVolumes(new ArrayList<>());
        associatedAccount3.setEc2Instances(new ArrayList<>());
        associatedAccount3.setEc2OptimizationSuggestions(new ArrayList<>());
        associatedAccount3.setEipAddresses(new ArrayList<>());
        associatedAccount3.setEipOptimizationSuggestions(new ArrayList<>());
        associatedAccount3.setId(1L);
        associatedAccount3.setRdsInstances(new ArrayList<>());
        associatedAccount3.setRdsOptimizationSuggestions(new ArrayList<>());
        associatedAccount3.setS3OptimizationSuggestions(new ArrayList<>());
        associatedAccount3.setSecretAccessKey("EXAMPLEakiAIOSFODNN7");
        associatedAccount3.setStorageBuckets(new ArrayList<>());

        AwsAccountCredentials associatedAccount4 = new AwsAccountCredentials();
        associatedAccount4.setAccessKeyId("EXAMPLEakiAIOSFODNN7");
        associatedAccount4.setAccountId("42");
        associatedAccount4.setAccountName("Dr Jane Doe");
        associatedAccount4.setDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        associatedAccount4.setEbsOptimizationSuggestions(new ArrayList<>());
        associatedAccount4.setEbsVolumes(new ArrayList<>());
        associatedAccount4.setEc2Instances(new ArrayList<>());
        associatedAccount4.setEc2OptimizationSuggestions(new ArrayList<>());
        associatedAccount4.setEipAddresses(new ArrayList<>());
        associatedAccount4.setEipOptimizationSuggestions(new ArrayList<>());
        associatedAccount4.setId(1L);
        associatedAccount4.setRdsInstances(new ArrayList<>());
        associatedAccount4.setRdsOptimizationSuggestions(new ArrayList<>());
        associatedAccount4.setS3OptimizationSuggestions(new ArrayList<>());
        associatedAccount4.setSecretAccessKey("EXAMPLEakiAIOSFODNN7");
        associatedAccount4.setStorageBuckets(new ArrayList<>());

        RDSInstance linkedInstance2 = new RDSInstance();
        linkedInstance2.setAllocatedStorage(1);
        linkedInstance2.setAssociatedAccount(associatedAccount4);
        linkedInstance2.setClientName("Dr Jane Doe");
        linkedInstance2.setCreationDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedInstance2.setCreationTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedInstance2.setDbInstanceClass("Db Instance Class");
        linkedInstance2.setDbInstanceIdentifier("42");
        linkedInstance2.setEndpointAddress("https://config.us-east-2.amazonaws.com");
        linkedInstance2.setEndpointPort(3);
        linkedInstance2.setEngine("Engine");
        linkedInstance2.setEngineVersion("1.0.2");
        linkedInstance2.setId(1L);
        linkedInstance2.setLastDowntimeTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedInstance2.setLastUpdateTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedInstance2.setLastUptimeTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedInstance2.setLinkedSuggestion(new HashSet<>());
        linkedInstance2.setMonitoringEnabled(true);
        linkedInstance2.setOperationHours("Operation Hours");
        linkedInstance2.setOwnerEmail("jane.doe@example.org");
        linkedInstance2.setProductId("42");
        linkedInstance2.setRegion("us-east-2");
        linkedInstance2.setStatus("Status");

        RDSOptimizationSuggestion rdsOptimizationSuggestion2 = new RDSOptimizationSuggestion();
        rdsOptimizationSuggestion2.setAssociatedAccount(associatedAccount3);
        rdsOptimizationSuggestion2.setCategory(RDSSuggestionCategory.NOAUTOSCALINGGROUP);
        rdsOptimizationSuggestion2.setCreatedDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rdsOptimizationSuggestion2.setDescription("The characteristics of someone or something");
        rdsOptimizationSuggestion2.setId(1L);
        rdsOptimizationSuggestion2.setLinkedInstance(linkedInstance2);
        rdsOptimizationSuggestion2.setRecommendation("Recommendation");
        rdsOptimizationSuggestion2.setStatus(SuggestionStatus.Pending);
        rdsOptimizationSuggestion2.setTitle("Dr");
        when(rdsOptimizationSuggestionRepository.save(Mockito.<RDSOptimizationSuggestion>any()))
                .thenReturn(rdsOptimizationSuggestion2);
        when(rdsOptimizationSuggestionRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertSame(rdsOptimizationSuggestion2,
                rdsSuggestionsService.updateRdsSuggestionStatus(SuggestionStatus.Pending, 1L));
        verify(rdsOptimizationSuggestionRepository).save(Mockito.<RDSOptimizationSuggestion>any());
        verify(rdsOptimizationSuggestionRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link RdsSuggestionsService#deleteRdsSuggestion(Long)}
     */
    @Test
    void testDeleteRdsSuggestion() {
        doNothing().when(rdsOptimizationSuggestionRepository).deleteById(Mockito.<Long>any());
        rdsSuggestionsService.deleteRdsSuggestion(1L);
        verify(rdsOptimizationSuggestionRepository).deleteById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link RdsSuggestionsService#deleteDealtWithSuggestions()}
     */
    @Test
    void testDeleteDealtWithSuggestions() {
        when(rdsOptimizationSuggestionRepository.findAll()).thenReturn(new ArrayList<>());
        rdsSuggestionsService.deleteDealtWithSuggestions();
        verify(rdsOptimizationSuggestionRepository).findAll();
    }

    /**
     * Method under test: {@link RdsSuggestionsService#deleteDealtWithSuggestions()}
     */
    @Test
    void testDeleteDealtWithSuggestions2() {
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

        RDSInstance linkedInstance = new RDSInstance();
        linkedInstance.setAllocatedStorage(1);
        linkedInstance.setAssociatedAccount(associatedAccount2);
        linkedInstance.setClientName("Dr Jane Doe");
        linkedInstance.setCreationDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedInstance.setCreationTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedInstance.setDbInstanceClass("Db Instance Class");
        linkedInstance.setDbInstanceIdentifier("42");
        linkedInstance.setEndpointAddress("https://config.us-east-2.amazonaws.com");
        linkedInstance.setEndpointPort(3);
        linkedInstance.setEngine("Engine");
        linkedInstance.setEngineVersion("1.0.2");
        linkedInstance.setId(1L);
        linkedInstance.setLastDowntimeTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedInstance.setLastUpdateTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedInstance.setLastUptimeTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedInstance.setLinkedSuggestion(new HashSet<>());
        linkedInstance.setMonitoringEnabled(true);
        linkedInstance.setOperationHours("Operation Hours");
        linkedInstance.setOwnerEmail("jane.doe@example.org");
        linkedInstance.setProductId("42");
        linkedInstance.setRegion("us-east-2");
        linkedInstance.setStatus("Status");

        RDSOptimizationSuggestion rdsOptimizationSuggestion = new RDSOptimizationSuggestion();
        rdsOptimizationSuggestion.setAssociatedAccount(associatedAccount);
        rdsOptimizationSuggestion.setCategory(RDSSuggestionCategory.NOAUTOSCALINGGROUP);
        rdsOptimizationSuggestion.setCreatedDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rdsOptimizationSuggestion.setDescription("The characteristics of someone or something");
        rdsOptimizationSuggestion.setId(1L);
        rdsOptimizationSuggestion.setLinkedInstance(linkedInstance);
        rdsOptimizationSuggestion.setRecommendation("Recommendation");
        rdsOptimizationSuggestion.setStatus(SuggestionStatus.Pending);
        rdsOptimizationSuggestion.setTitle("Dr");

        ArrayList<RDSOptimizationSuggestion> rdsOptimizationSuggestionList = new ArrayList<>();
        rdsOptimizationSuggestionList.add(rdsOptimizationSuggestion);
        when(rdsOptimizationSuggestionRepository.findAll()).thenReturn(rdsOptimizationSuggestionList);
        rdsSuggestionsService.deleteDealtWithSuggestions();
        verify(rdsOptimizationSuggestionRepository).findAll();
    }

    /**
     * Method under test: {@link RdsSuggestionsService#deleteDealtWithSuggestions()}
     */
    @Test
    void testDeleteDealtWithSuggestions3() {
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

        RDSInstance linkedInstance = new RDSInstance();
        linkedInstance.setAllocatedStorage(1);
        linkedInstance.setAssociatedAccount(associatedAccount2);
        linkedInstance.setClientName("Dr Jane Doe");
        linkedInstance.setCreationDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedInstance.setCreationTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedInstance.setDbInstanceClass("Db Instance Class");
        linkedInstance.setDbInstanceIdentifier("42");
        linkedInstance.setEndpointAddress("https://config.us-east-2.amazonaws.com");
        linkedInstance.setEndpointPort(3);
        linkedInstance.setEngine("Engine");
        linkedInstance.setEngineVersion("1.0.2");
        linkedInstance.setId(1L);
        linkedInstance.setLastDowntimeTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedInstance.setLastUpdateTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedInstance.setLastUptimeTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedInstance.setLinkedSuggestion(new HashSet<>());
        linkedInstance.setMonitoringEnabled(true);
        linkedInstance.setOperationHours("Operation Hours");
        linkedInstance.setOwnerEmail("jane.doe@example.org");
        linkedInstance.setProductId("42");
        linkedInstance.setRegion("us-east-2");
        linkedInstance.setStatus("Status");

        RDSOptimizationSuggestion rdsOptimizationSuggestion = new RDSOptimizationSuggestion();
        rdsOptimizationSuggestion.setAssociatedAccount(associatedAccount);
        rdsOptimizationSuggestion.setCategory(RDSSuggestionCategory.NOAUTOSCALINGGROUP);
        rdsOptimizationSuggestion.setCreatedDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rdsOptimizationSuggestion.setDescription("The characteristics of someone or something");
        rdsOptimizationSuggestion.setId(1L);
        rdsOptimizationSuggestion.setLinkedInstance(linkedInstance);
        rdsOptimizationSuggestion.setRecommendation("Recommendation");
        rdsOptimizationSuggestion.setStatus(SuggestionStatus.Pending);
        rdsOptimizationSuggestion.setTitle("Dr");

        AwsAccountCredentials associatedAccount3 = new AwsAccountCredentials();
        associatedAccount3.setAccessKeyId("42");
        associatedAccount3.setAccountId("3");
        associatedAccount3.setAccountName("Mr John Smith");
        associatedAccount3.setDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        associatedAccount3.setEbsOptimizationSuggestions(new ArrayList<>());
        associatedAccount3.setEbsVolumes(new ArrayList<>());
        associatedAccount3.setEc2Instances(new ArrayList<>());
        associatedAccount3.setEc2OptimizationSuggestions(new ArrayList<>());
        associatedAccount3.setEipAddresses(new ArrayList<>());
        associatedAccount3.setEipOptimizationSuggestions(new ArrayList<>());
        associatedAccount3.setId(2L);
        associatedAccount3.setRdsInstances(new ArrayList<>());
        associatedAccount3.setRdsOptimizationSuggestions(new ArrayList<>());
        associatedAccount3.setS3OptimizationSuggestions(new ArrayList<>());
        associatedAccount3.setSecretAccessKey("Secret Access Key");
        associatedAccount3.setStorageBuckets(new ArrayList<>());

        AwsAccountCredentials associatedAccount4 = new AwsAccountCredentials();
        associatedAccount4.setAccessKeyId("42");
        associatedAccount4.setAccountId("3");
        associatedAccount4.setAccountName("Mr John Smith");
        associatedAccount4.setDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        associatedAccount4.setEbsOptimizationSuggestions(new ArrayList<>());
        associatedAccount4.setEbsVolumes(new ArrayList<>());
        associatedAccount4.setEc2Instances(new ArrayList<>());
        associatedAccount4.setEc2OptimizationSuggestions(new ArrayList<>());
        associatedAccount4.setEipAddresses(new ArrayList<>());
        associatedAccount4.setEipOptimizationSuggestions(new ArrayList<>());
        associatedAccount4.setId(2L);
        associatedAccount4.setRdsInstances(new ArrayList<>());
        associatedAccount4.setRdsOptimizationSuggestions(new ArrayList<>());
        associatedAccount4.setS3OptimizationSuggestions(new ArrayList<>());
        associatedAccount4.setSecretAccessKey("Secret Access Key");
        associatedAccount4.setStorageBuckets(new ArrayList<>());

        RDSInstance linkedInstance2 = new RDSInstance();
        linkedInstance2.setAllocatedStorage(0);
        linkedInstance2.setAssociatedAccount(associatedAccount4);
        linkedInstance2.setClientName("Mr John Smith");
        linkedInstance2.setCreationDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedInstance2.setCreationTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedInstance2.setDbInstanceClass("com.vermeg.aws.cost.optimization.dashboard.entities.RDSInstance");
        linkedInstance2.setDbInstanceIdentifier("Db Instance Identifier");
        linkedInstance2.setEndpointAddress("42 Main St");
        linkedInstance2.setEndpointPort(8080);
        linkedInstance2.setEngine("com.vermeg.aws.cost.optimization.dashboard.entities.RDSInstance");
        linkedInstance2.setEngineVersion("Engine Version");
        linkedInstance2.setId(2L);
        linkedInstance2.setLastDowntimeTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedInstance2.setLastUpdateTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedInstance2.setLastUptimeTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedInstance2.setLinkedSuggestion(new HashSet<>());
        linkedInstance2.setMonitoringEnabled(false);
        linkedInstance2.setOperationHours("com.vermeg.aws.cost.optimization.dashboard.entities.RDSInstance");
        linkedInstance2.setOwnerEmail("john.smith@example.org");
        linkedInstance2.setProductId("Product Id");
        linkedInstance2.setRegion("Region");
        linkedInstance2.setStatus("com.vermeg.aws.cost.optimization.dashboard.entities.RDSInstance");

        RDSOptimizationSuggestion rdsOptimizationSuggestion2 = new RDSOptimizationSuggestion();
        rdsOptimizationSuggestion2.setAssociatedAccount(associatedAccount3);
        rdsOptimizationSuggestion2.setCategory(RDSSuggestionCategory.NOAUTOSCALINGGROUP);
        rdsOptimizationSuggestion2.setCreatedDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rdsOptimizationSuggestion2.setDescription("Description");
        rdsOptimizationSuggestion2.setId(2L);
        rdsOptimizationSuggestion2.setLinkedInstance(linkedInstance2);
        rdsOptimizationSuggestion2
                .setRecommendation("com.vermeg.aws.cost.optimization.dashboard.entities.RDSOptimizationSuggestion");
        rdsOptimizationSuggestion2.setStatus(SuggestionStatus.Ignored);
        rdsOptimizationSuggestion2.setTitle("Mr");

        ArrayList<RDSOptimizationSuggestion> rdsOptimizationSuggestionList = new ArrayList<>();
        rdsOptimizationSuggestionList.add(rdsOptimizationSuggestion2);
        rdsOptimizationSuggestionList.add(rdsOptimizationSuggestion);
        doNothing().when(rdsOptimizationSuggestionRepository).delete(Mockito.<RDSOptimizationSuggestion>any());
        when(rdsOptimizationSuggestionRepository.findAll()).thenReturn(rdsOptimizationSuggestionList);
        rdsSuggestionsService.deleteDealtWithSuggestions();
        verify(rdsOptimizationSuggestionRepository).findAll();
        verify(rdsOptimizationSuggestionRepository).delete(Mockito.<RDSOptimizationSuggestion>any());
    }
}

