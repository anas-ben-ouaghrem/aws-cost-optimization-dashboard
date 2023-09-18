package com.vermeg.aws.cost.optimization.dashboard.services;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.amazonaws.services.cloudwatch.model.Datapoint;
import com.vermeg.aws.cost.optimization.dashboard.entities.AwsAccountCredentials;
import com.vermeg.aws.cost.optimization.dashboard.entities.EBSOptimizationSuggestion;
import com.vermeg.aws.cost.optimization.dashboard.entities.EBSSuggestionCategory;
import com.vermeg.aws.cost.optimization.dashboard.entities.EBSVolume;
import com.vermeg.aws.cost.optimization.dashboard.entities.EC2Instance;
import com.vermeg.aws.cost.optimization.dashboard.entities.EC2OptimizationSuggestion;
import com.vermeg.aws.cost.optimization.dashboard.entities.EC2SuggestionCategory;
import com.vermeg.aws.cost.optimization.dashboard.entities.EIPAddress;
import com.vermeg.aws.cost.optimization.dashboard.entities.EIPOptimizationSuggestion;
import com.vermeg.aws.cost.optimization.dashboard.entities.EIPSuggestionCategory;
import com.vermeg.aws.cost.optimization.dashboard.entities.SuggestionStatus;
import com.vermeg.aws.cost.optimization.dashboard.repositories.EbsOptimizationSuggestionRepository;
import com.vermeg.aws.cost.optimization.dashboard.repositories.Ec2OptimizationSuggestionRepository;
import com.vermeg.aws.cost.optimization.dashboard.repositories.EipOptimizationSuggestionRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ComputeSuggestionsService.class})
@ExtendWith(SpringExtension.class)
class ComputeSuggestionsServiceTest {
    @MockBean
    private AutoScalingService autoScalingService;

    @MockBean
    private CloudWatchService cloudWatchService;

    @MockBean
    private ComputeServices computeServices;

    @Autowired
    private ComputeSuggestionsService computeSuggestionsService;

    @MockBean
    private EbsOptimizationSuggestionRepository ebsOptimizationSuggestionRepository;

    @MockBean
    private Ec2OptimizationSuggestionRepository ec2OptimizationSuggestionRepository;

    @MockBean
    private EipOptimizationSuggestionRepository eipOptimizationSuggestionRepository;

    /**
     * Method under test: {@link ComputeSuggestionsService#deleteEipSuggestion(Long)}
     */
    @Test
    void testDeleteEipSuggestion() {
        doNothing().when(eipOptimizationSuggestionRepository).deleteById(Mockito.<Long>any());
        computeSuggestionsService.deleteEipSuggestion(1L);
        verify(eipOptimizationSuggestionRepository).deleteById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link ComputeSuggestionsService#deleteDealtWithEipSuggestions()}
     */
    @Test
    void testDeleteDealtWithEipSuggestions() {
        when(eipOptimizationSuggestionRepository.findAll()).thenReturn(new ArrayList<>());
        computeSuggestionsService.deleteDealtWithEipSuggestions();
        verify(eipOptimizationSuggestionRepository).findAll();
    }

    /**
     * Method under test: {@link ComputeSuggestionsService#deleteDealtWithEipSuggestions()}
     */
    @Test
    void testDeleteDealtWithEipSuggestions2() {
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

        EIPAddress linkedIPAddress = new EIPAddress();
        linkedIPAddress.setAllocationId("42");
        linkedIPAddress.setAssociatedAccount(associatedAccount2);
        linkedIPAddress.setAssociationId("42");
        linkedIPAddress.setId(1L);
        linkedIPAddress.setInstanceId("42");
        linkedIPAddress.setLinkedSuggestion(new HashSet<>());
        linkedIPAddress.setPrivateIp("Private Ip");
        linkedIPAddress.setPublicIp("Public Ip");

        EIPOptimizationSuggestion eipOptimizationSuggestion = new EIPOptimizationSuggestion();
        eipOptimizationSuggestion.setAssociatedAccount(associatedAccount);
        eipOptimizationSuggestion.setCategory(EIPSuggestionCategory.NORUNNINGINSTANCE);
        eipOptimizationSuggestion.setCreatedDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        eipOptimizationSuggestion.setDescription("The characteristics of someone or something");
        eipOptimizationSuggestion.setId(1L);
        eipOptimizationSuggestion.setLinkedIPAddress(linkedIPAddress);
        eipOptimizationSuggestion.setRecommendation("Recommendation");
        eipOptimizationSuggestion.setStatus(SuggestionStatus.Pending);
        eipOptimizationSuggestion.setTitle("Dr");

        ArrayList<EIPOptimizationSuggestion> eipOptimizationSuggestionList = new ArrayList<>();
        eipOptimizationSuggestionList.add(eipOptimizationSuggestion);
        when(eipOptimizationSuggestionRepository.findAll()).thenReturn(eipOptimizationSuggestionList);
        computeSuggestionsService.deleteDealtWithEipSuggestions();
        verify(eipOptimizationSuggestionRepository).findAll();
    }

    /**
     * Method under test: {@link ComputeSuggestionsService#deleteDealtWithEipSuggestions()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testDeleteDealtWithEipSuggestions3() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.time.DateTimeException: Unable to obtain LocalDateTime from TemporalAccessor: 2023-09-13T10:56:53.841Z of type java.time.Instant
        //       at java.base/java.time.LocalDateTime.from(LocalDateTime.java:463)
        //       at java.base/java.time.LocalDateTime.until(LocalDateTime.java:1677)
        //       at java.base/java.time.Duration.between(Duration.java:492)
        //       at com.vermeg.aws.cost.optimization.dashboard.services.ComputeSuggestionsService.deleteDealtWithEipSuggestions(ComputeSuggestionsService.java:91)
        //   java.time.DateTimeException: Unable to obtain LocalDate from TemporalAccessor: 2023-09-13T10:56:53.841Z of type java.time.Instant
        //       at java.base/java.time.LocalDate.from(LocalDate.java:398)
        //       at java.base/java.time.LocalDateTime.from(LocalDateTime.java:458)
        //       at java.base/java.time.LocalDateTime.until(LocalDateTime.java:1677)
        //       at java.base/java.time.Duration.between(Duration.java:492)
        //       at com.vermeg.aws.cost.optimization.dashboard.services.ComputeSuggestionsService.deleteDealtWithEipSuggestions(ComputeSuggestionsService.java:91)
        //   See https://diff.blue/R013 to resolve this issue.

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

        EIPAddress linkedIPAddress = new EIPAddress();
        linkedIPAddress.setAllocationId("42");
        linkedIPAddress.setAssociatedAccount(associatedAccount2);
        linkedIPAddress.setAssociationId("42");
        linkedIPAddress.setId(1L);
        linkedIPAddress.setInstanceId("42");
        linkedIPAddress.setLinkedSuggestion(new HashSet<>());
        linkedIPAddress.setPrivateIp("Private Ip");
        linkedIPAddress.setPublicIp("Public Ip");

        EIPOptimizationSuggestion eipOptimizationSuggestion = new EIPOptimizationSuggestion();
        eipOptimizationSuggestion.setAssociatedAccount(associatedAccount);
        eipOptimizationSuggestion.setCategory(EIPSuggestionCategory.NORUNNINGINSTANCE);
        eipOptimizationSuggestion.setCreatedDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        eipOptimizationSuggestion.setDescription("The characteristics of someone or something");
        eipOptimizationSuggestion.setId(1L);
        eipOptimizationSuggestion.setLinkedIPAddress(linkedIPAddress);
        eipOptimizationSuggestion.setRecommendation("Recommendation");
        eipOptimizationSuggestion.setStatus(SuggestionStatus.Pending);
        eipOptimizationSuggestion.setTitle("Dr");

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

        EIPAddress linkedIPAddress2 = new EIPAddress();
        linkedIPAddress2.setAllocationId("Allocation Id");
        linkedIPAddress2.setAssociatedAccount(associatedAccount4);
        linkedIPAddress2.setAssociationId("Association Id");
        linkedIPAddress2.setId(2L);
        linkedIPAddress2.setInstanceId("Instance Id");
        linkedIPAddress2.setLinkedSuggestion(new HashSet<>());
        linkedIPAddress2.setPrivateIp("com.vermeg.aws.cost.optimization.dashboard.entities.EIPAddress");
        linkedIPAddress2.setPublicIp("com.vermeg.aws.cost.optimization.dashboard.entities.EIPAddress");

        EIPOptimizationSuggestion eipOptimizationSuggestion2 = new EIPOptimizationSuggestion();
        eipOptimizationSuggestion2.setAssociatedAccount(associatedAccount3);
        eipOptimizationSuggestion2.setCategory(EIPSuggestionCategory.NOASSOCIATEDINSTANCE);
        eipOptimizationSuggestion2.setCreatedDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        eipOptimizationSuggestion2.setDescription("Description");
        eipOptimizationSuggestion2.setId(2L);
        eipOptimizationSuggestion2.setLinkedIPAddress(linkedIPAddress2);
        eipOptimizationSuggestion2
                .setRecommendation("com.vermeg.aws.cost.optimization.dashboard.entities.EIPOptimizationSuggestion");
        eipOptimizationSuggestion2.setStatus(SuggestionStatus.Ignored);
        eipOptimizationSuggestion2.setTitle("Mr");

        ArrayList<EIPOptimizationSuggestion> eipOptimizationSuggestionList = new ArrayList<>();
        eipOptimizationSuggestionList.add(eipOptimizationSuggestion2);
        eipOptimizationSuggestionList.add(eipOptimizationSuggestion);
        when(eipOptimizationSuggestionRepository.findAll()).thenReturn(eipOptimizationSuggestionList);
        computeSuggestionsService.deleteDealtWithEipSuggestions();
    }

    /**
     * Method under test: {@link ComputeSuggestionsService#deleteDealtWithEipSuggestions()}
     */
    @Test
    void testDeleteDealtWithEipSuggestions4() {
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

        EIPAddress linkedIPAddress = new EIPAddress();
        linkedIPAddress.setAllocationId("42");
        linkedIPAddress.setAssociatedAccount(associatedAccount2);
        linkedIPAddress.setAssociationId("42");
        linkedIPAddress.setId(1L);
        linkedIPAddress.setInstanceId("42");
        linkedIPAddress.setLinkedSuggestion(new HashSet<>());
        linkedIPAddress.setPrivateIp("Private Ip");
        linkedIPAddress.setPublicIp("Public Ip");
        EIPOptimizationSuggestion eipOptimizationSuggestion = mock(EIPOptimizationSuggestion.class);
        when(eipOptimizationSuggestion.getStatus()).thenReturn(SuggestionStatus.Pending);
        doNothing().when(eipOptimizationSuggestion).setAssociatedAccount(Mockito.<AwsAccountCredentials>any());
        doNothing().when(eipOptimizationSuggestion).setCategory(Mockito.<EIPSuggestionCategory>any());
        doNothing().when(eipOptimizationSuggestion).setCreatedDate(Mockito.<LocalDateTime>any());
        doNothing().when(eipOptimizationSuggestion).setDescription(Mockito.<String>any());
        doNothing().when(eipOptimizationSuggestion).setId(Mockito.<Long>any());
        doNothing().when(eipOptimizationSuggestion).setLinkedIPAddress(Mockito.<EIPAddress>any());
        doNothing().when(eipOptimizationSuggestion).setRecommendation(Mockito.<String>any());
        doNothing().when(eipOptimizationSuggestion).setStatus(Mockito.<SuggestionStatus>any());
        doNothing().when(eipOptimizationSuggestion).setTitle(Mockito.<String>any());
        eipOptimizationSuggestion.setAssociatedAccount(associatedAccount);
        eipOptimizationSuggestion.setCategory(EIPSuggestionCategory.NORUNNINGINSTANCE);
        eipOptimizationSuggestion.setCreatedDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        eipOptimizationSuggestion.setDescription("The characteristics of someone or something");
        eipOptimizationSuggestion.setId(1L);
        eipOptimizationSuggestion.setLinkedIPAddress(linkedIPAddress);
        eipOptimizationSuggestion.setRecommendation("Recommendation");
        eipOptimizationSuggestion.setStatus(SuggestionStatus.Pending);
        eipOptimizationSuggestion.setTitle("Dr");

        ArrayList<EIPOptimizationSuggestion> eipOptimizationSuggestionList = new ArrayList<>();
        eipOptimizationSuggestionList.add(eipOptimizationSuggestion);
        when(eipOptimizationSuggestionRepository.findAll()).thenReturn(eipOptimizationSuggestionList);
        computeSuggestionsService.deleteDealtWithEipSuggestions();
        verify(eipOptimizationSuggestionRepository).findAll();
        verify(eipOptimizationSuggestion).getStatus();
        verify(eipOptimizationSuggestion).setAssociatedAccount(Mockito.<AwsAccountCredentials>any());
        verify(eipOptimizationSuggestion).setCategory(Mockito.<EIPSuggestionCategory>any());
        verify(eipOptimizationSuggestion).setCreatedDate(Mockito.<LocalDateTime>any());
        verify(eipOptimizationSuggestion).setDescription(Mockito.<String>any());
        verify(eipOptimizationSuggestion).setId(Mockito.<Long>any());
        verify(eipOptimizationSuggestion).setLinkedIPAddress(Mockito.<EIPAddress>any());
        verify(eipOptimizationSuggestion).setRecommendation(Mockito.<String>any());
        verify(eipOptimizationSuggestion).setStatus(Mockito.<SuggestionStatus>any());
        verify(eipOptimizationSuggestion).setTitle(Mockito.<String>any());
    }

    /**
     * Method under test: {@link ComputeSuggestionsService#deleteDealtWithEipSuggestions()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testDeleteDealtWithEipSuggestions5() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.time.DateTimeException: Unable to obtain LocalDateTime from TemporalAccessor: 2023-09-13T10:57:22.221Z of type java.time.Instant
        //       at java.base/java.time.LocalDateTime.from(LocalDateTime.java:463)
        //       at java.base/java.time.LocalDateTime.until(LocalDateTime.java:1677)
        //       at java.base/java.time.Duration.between(Duration.java:492)
        //       at com.vermeg.aws.cost.optimization.dashboard.services.ComputeSuggestionsService.deleteDealtWithEipSuggestions(ComputeSuggestionsService.java:91)
        //   java.time.DateTimeException: Unable to obtain LocalDate from TemporalAccessor: 2023-09-13T10:57:22.221Z of type java.time.Instant
        //       at java.base/java.time.LocalDate.from(LocalDate.java:398)
        //       at java.base/java.time.LocalDateTime.from(LocalDateTime.java:458)
        //       at java.base/java.time.LocalDateTime.until(LocalDateTime.java:1677)
        //       at java.base/java.time.Duration.between(Duration.java:492)
        //       at com.vermeg.aws.cost.optimization.dashboard.services.ComputeSuggestionsService.deleteDealtWithEipSuggestions(ComputeSuggestionsService.java:91)
        //   See https://diff.blue/R013 to resolve this issue.

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

        EIPAddress linkedIPAddress = new EIPAddress();
        linkedIPAddress.setAllocationId("42");
        linkedIPAddress.setAssociatedAccount(associatedAccount2);
        linkedIPAddress.setAssociationId("42");
        linkedIPAddress.setId(1L);
        linkedIPAddress.setInstanceId("42");
        linkedIPAddress.setLinkedSuggestion(new HashSet<>());
        linkedIPAddress.setPrivateIp("Private Ip");
        linkedIPAddress.setPublicIp("Public Ip");
        EIPOptimizationSuggestion eipOptimizationSuggestion = mock(EIPOptimizationSuggestion.class);
        when(eipOptimizationSuggestion.getStatus()).thenReturn(SuggestionStatus.Ignored);
        when(eipOptimizationSuggestion.getCreatedDate()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        doNothing().when(eipOptimizationSuggestion).setAssociatedAccount(Mockito.<AwsAccountCredentials>any());
        doNothing().when(eipOptimizationSuggestion).setCategory(Mockito.<EIPSuggestionCategory>any());
        doNothing().when(eipOptimizationSuggestion).setCreatedDate(Mockito.<LocalDateTime>any());
        doNothing().when(eipOptimizationSuggestion).setDescription(Mockito.<String>any());
        doNothing().when(eipOptimizationSuggestion).setId(Mockito.<Long>any());
        doNothing().when(eipOptimizationSuggestion).setLinkedIPAddress(Mockito.<EIPAddress>any());
        doNothing().when(eipOptimizationSuggestion).setRecommendation(Mockito.<String>any());
        doNothing().when(eipOptimizationSuggestion).setStatus(Mockito.<SuggestionStatus>any());
        doNothing().when(eipOptimizationSuggestion).setTitle(Mockito.<String>any());
        eipOptimizationSuggestion.setAssociatedAccount(associatedAccount);
        eipOptimizationSuggestion.setCategory(EIPSuggestionCategory.NORUNNINGINSTANCE);
        eipOptimizationSuggestion.setCreatedDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        eipOptimizationSuggestion.setDescription("The characteristics of someone or something");
        eipOptimizationSuggestion.setId(1L);
        eipOptimizationSuggestion.setLinkedIPAddress(linkedIPAddress);
        eipOptimizationSuggestion.setRecommendation("Recommendation");
        eipOptimizationSuggestion.setStatus(SuggestionStatus.Pending);
        eipOptimizationSuggestion.setTitle("Dr");

        ArrayList<EIPOptimizationSuggestion> eipOptimizationSuggestionList = new ArrayList<>();
        eipOptimizationSuggestionList.add(eipOptimizationSuggestion);
        when(eipOptimizationSuggestionRepository.findAll()).thenReturn(eipOptimizationSuggestionList);
        computeSuggestionsService.deleteDealtWithEipSuggestions();
    }

    /**
     * Method under test: {@link ComputeSuggestionsService#generateEbsSuggestions()}
     */
    @Test
    void testGenerateEbsSuggestions() {
        ArrayList<EBSOptimizationSuggestion> ebsOptimizationSuggestionList = new ArrayList<>();
        when(ebsOptimizationSuggestionRepository.saveAll(Mockito.<Iterable<EBSOptimizationSuggestion>>any()))
                .thenReturn(ebsOptimizationSuggestionList);
        when(computeServices.getAllVolumes()).thenReturn(new ArrayList<>());
        List<EBSOptimizationSuggestion> actualGenerateEbsSuggestionsResult = computeSuggestionsService
                .generateEbsSuggestions();
        assertSame(ebsOptimizationSuggestionList, actualGenerateEbsSuggestionsResult);
        assertTrue(actualGenerateEbsSuggestionsResult.isEmpty());
        verify(ebsOptimizationSuggestionRepository).saveAll(Mockito.<Iterable<EBSOptimizationSuggestion>>any());
        verify(computeServices).getAllVolumes();
    }

    /**
     * Method under test: {@link ComputeSuggestionsService#generateEbsSuggestions()}
     */
    @Test
    void testGenerateEbsSuggestions2() {
        ArrayList<EBSOptimizationSuggestion> ebsOptimizationSuggestionList = new ArrayList<>();
        when(ebsOptimizationSuggestionRepository.saveAll(Mockito.<Iterable<EBSOptimizationSuggestion>>any()))
                .thenReturn(ebsOptimizationSuggestionList);

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
        ebsVolume.setEnvironmentType("Generating EBS Cost Optimization Suggestions ....");
        ebsVolume.setId(1L);
        ebsVolume.setInstanceId("42");
        ebsVolume.setLastUpdateTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        ebsVolume.setOperationHours("Generating EBS Cost Optimization Suggestions ....");
        ebsVolume.setOwnerEmail("jane.doe@example.org");
        ebsVolume.setProductId("42");
        ebsVolume.setRegion("us-east-2");
        ebsVolume.setSize(3L);
        ebsVolume.setState("MD");
        ebsVolume.setSuggestions(new HashSet<>());
        ebsVolume.setVolumeId("42");
        ebsVolume.setVolumeType("Generating EBS Cost Optimization Suggestions ....");

        ArrayList<EBSVolume> ebsVolumeList = new ArrayList<>();
        ebsVolumeList.add(ebsVolume);
        when(computeServices.getAllVolumes()).thenReturn(ebsVolumeList);
        List<EBSOptimizationSuggestion> actualGenerateEbsSuggestionsResult = computeSuggestionsService
                .generateEbsSuggestions();
        assertSame(ebsOptimizationSuggestionList, actualGenerateEbsSuggestionsResult);
        assertTrue(actualGenerateEbsSuggestionsResult.isEmpty());
        verify(ebsOptimizationSuggestionRepository).saveAll(Mockito.<Iterable<EBSOptimizationSuggestion>>any());
        verify(computeServices).getAllVolumes();
    }

    /**
     * Method under test: {@link ComputeSuggestionsService#deleteEbsSuggestion(Long)}
     */
    @Test
    void testDeleteEbsSuggestion() {
        doNothing().when(ebsOptimizationSuggestionRepository).deleteById(Mockito.<Long>any());
        computeSuggestionsService.deleteEbsSuggestion(1L);
        verify(ebsOptimizationSuggestionRepository).deleteById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link ComputeSuggestionsService#deleteDealtWithEbsSuggestions()}
     */
    @Test
    void testDeleteDealtWithEbsSuggestions() {
        when(ebsOptimizationSuggestionRepository.findAll()).thenReturn(new ArrayList<>());
        computeSuggestionsService.deleteDealtWithEbsSuggestions();
        verify(ebsOptimizationSuggestionRepository).findAll();
    }

    /**
     * Method under test: {@link ComputeSuggestionsService#deleteDealtWithEbsSuggestions()}
     */
    @Test
    void testDeleteDealtWithEbsSuggestions2() {
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

        EBSVolume linkedVolume = new EBSVolume();
        linkedVolume.setAssociatedAccount(associatedAccount2);
        linkedVolume
                .setCreationTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        linkedVolume.setCreationTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedVolume.setEnvironmentType("Environment Type");
        linkedVolume.setId(1L);
        linkedVolume.setInstanceId("42");
        linkedVolume.setLastUpdateTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedVolume.setOperationHours("Operation Hours");
        linkedVolume.setOwnerEmail("jane.doe@example.org");
        linkedVolume.setProductId("42");
        linkedVolume.setRegion("us-east-2");
        linkedVolume.setSize(3L);
        linkedVolume.setState("MD");
        linkedVolume.setSuggestions(new HashSet<>());
        linkedVolume.setVolumeId("42");
        linkedVolume.setVolumeType("Volume Type");

        EBSOptimizationSuggestion ebsOptimizationSuggestion = new EBSOptimizationSuggestion();
        ebsOptimizationSuggestion.setAssociatedAccount(associatedAccount);
        ebsOptimizationSuggestion.setCategory(EBSSuggestionCategory.OLDERGENERATION);
        ebsOptimizationSuggestion
                .setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        ebsOptimizationSuggestion.setDescription("The characteristics of someone or something");
        ebsOptimizationSuggestion.setId(1L);
        ebsOptimizationSuggestion.setLinkedVolume(linkedVolume);
        ebsOptimizationSuggestion.setRecommendation("Recommendation");
        ebsOptimizationSuggestion.setStatus(SuggestionStatus.Pending);
        ebsOptimizationSuggestion.setTitle("Dr");

        ArrayList<EBSOptimizationSuggestion> ebsOptimizationSuggestionList = new ArrayList<>();
        ebsOptimizationSuggestionList.add(ebsOptimizationSuggestion);
        when(ebsOptimizationSuggestionRepository.findAll()).thenReturn(ebsOptimizationSuggestionList);
        computeSuggestionsService.deleteDealtWithEbsSuggestions();
        verify(ebsOptimizationSuggestionRepository).findAll();
    }

    /**
     * Method under test: {@link ComputeSuggestionsService#deleteDealtWithEbsSuggestions()}
     */
    @Test
    void testDeleteDealtWithEbsSuggestions3() {
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

        EBSVolume linkedVolume = new EBSVolume();
        linkedVolume.setAssociatedAccount(associatedAccount2);
        linkedVolume
                .setCreationTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        linkedVolume.setCreationTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedVolume.setEnvironmentType("Environment Type");
        linkedVolume.setId(1L);
        linkedVolume.setInstanceId("42");
        linkedVolume.setLastUpdateTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedVolume.setOperationHours("Operation Hours");
        linkedVolume.setOwnerEmail("jane.doe@example.org");
        linkedVolume.setProductId("42");
        linkedVolume.setRegion("us-east-2");
        linkedVolume.setSize(3L);
        linkedVolume.setState("MD");
        linkedVolume.setSuggestions(new HashSet<>());
        linkedVolume.setVolumeId("42");
        linkedVolume.setVolumeType("Volume Type");

        EBSOptimizationSuggestion ebsOptimizationSuggestion = new EBSOptimizationSuggestion();
        ebsOptimizationSuggestion.setAssociatedAccount(associatedAccount);
        ebsOptimizationSuggestion.setCategory(EBSSuggestionCategory.OLDERGENERATION);
        ebsOptimizationSuggestion
                .setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        ebsOptimizationSuggestion.setDescription("The characteristics of someone or something");
        ebsOptimizationSuggestion.setId(1L);
        ebsOptimizationSuggestion.setLinkedVolume(linkedVolume);
        ebsOptimizationSuggestion.setRecommendation("Recommendation");
        ebsOptimizationSuggestion.setStatus(SuggestionStatus.Pending);
        ebsOptimizationSuggestion.setTitle("Dr");

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

        EBSVolume linkedVolume2 = new EBSVolume();
        linkedVolume2.setAssociatedAccount(associatedAccount4);
        linkedVolume2
                .setCreationTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        linkedVolume2.setCreationTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedVolume2.setEnvironmentType("com.vermeg.aws.cost.optimization.dashboard.entities.EBSVolume");
        linkedVolume2.setId(2L);
        linkedVolume2.setInstanceId("Instance Id");
        linkedVolume2.setLastUpdateTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedVolume2.setOperationHours("com.vermeg.aws.cost.optimization.dashboard.entities.EBSVolume");
        linkedVolume2.setOwnerEmail("john.smith@example.org");
        linkedVolume2.setProductId("Product Id");
        linkedVolume2.setRegion("Region");
        linkedVolume2.setSize(1L);
        linkedVolume2.setState("State");
        linkedVolume2.setSuggestions(new HashSet<>());
        linkedVolume2.setVolumeId("Volume Id");
        linkedVolume2.setVolumeType("com.vermeg.aws.cost.optimization.dashboard.entities.EBSVolume");

        EBSOptimizationSuggestion ebsOptimizationSuggestion2 = new EBSOptimizationSuggestion();
        ebsOptimizationSuggestion2.setAssociatedAccount(associatedAccount3);
        ebsOptimizationSuggestion2.setCategory(EBSSuggestionCategory.LEL);
        ebsOptimizationSuggestion2
                .setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        ebsOptimizationSuggestion2.setDescription("Description");
        ebsOptimizationSuggestion2.setId(2L);
        ebsOptimizationSuggestion2.setLinkedVolume(linkedVolume2);
        ebsOptimizationSuggestion2
                .setRecommendation("com.vermeg.aws.cost.optimization.dashboard.entities.EBSOptimizationSuggestion");
        ebsOptimizationSuggestion2.setStatus(SuggestionStatus.Ignored);
        ebsOptimizationSuggestion2.setTitle("Mr");

        ArrayList<EBSOptimizationSuggestion> ebsOptimizationSuggestionList = new ArrayList<>();
        ebsOptimizationSuggestionList.add(ebsOptimizationSuggestion2);
        ebsOptimizationSuggestionList.add(ebsOptimizationSuggestion);
        when(ebsOptimizationSuggestionRepository.findAll()).thenReturn(ebsOptimizationSuggestionList);
        computeSuggestionsService.deleteDealtWithEbsSuggestions();
        verify(ebsOptimizationSuggestionRepository).findAll();
    }

    /**
     * Method under test: {@link ComputeSuggestionsService#deleteDealtWithEbsSuggestions()}
     */
    @Test
    void testDeleteDealtWithEbsSuggestions4() {
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

        EBSVolume linkedVolume = new EBSVolume();
        linkedVolume.setAssociatedAccount(associatedAccount2);
        linkedVolume
                .setCreationTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        linkedVolume.setCreationTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedVolume.setEnvironmentType("Environment Type");
        linkedVolume.setId(1L);
        linkedVolume.setInstanceId("42");
        linkedVolume.setLastUpdateTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedVolume.setOperationHours("Operation Hours");
        linkedVolume.setOwnerEmail("jane.doe@example.org");
        linkedVolume.setProductId("42");
        linkedVolume.setRegion("us-east-2");
        linkedVolume.setSize(3L);
        linkedVolume.setState("MD");
        linkedVolume.setSuggestions(new HashSet<>());
        linkedVolume.setVolumeId("42");
        linkedVolume.setVolumeType("Volume Type");

        EBSOptimizationSuggestion ebsOptimizationSuggestion = new EBSOptimizationSuggestion();
        ebsOptimizationSuggestion.setAssociatedAccount(associatedAccount);
        ebsOptimizationSuggestion.setCategory(EBSSuggestionCategory.OLDERGENERATION);
        ebsOptimizationSuggestion
                .setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        ebsOptimizationSuggestion.setDescription("The characteristics of someone or something");
        ebsOptimizationSuggestion.setId(1L);
        ebsOptimizationSuggestion.setLinkedVolume(linkedVolume);
        ebsOptimizationSuggestion.setRecommendation("Recommendation");
        ebsOptimizationSuggestion.setStatus(SuggestionStatus.Pending);
        ebsOptimizationSuggestion.setTitle("Dr");

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

        EBSVolume linkedVolume2 = new EBSVolume();
        linkedVolume2.setAssociatedAccount(associatedAccount4);
        linkedVolume2
                .setCreationTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        linkedVolume2.setCreationTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedVolume2.setEnvironmentType("com.vermeg.aws.cost.optimization.dashboard.entities.EBSVolume");
        linkedVolume2.setId(2L);
        linkedVolume2.setInstanceId("Instance Id");
        linkedVolume2.setLastUpdateTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedVolume2.setOperationHours("com.vermeg.aws.cost.optimization.dashboard.entities.EBSVolume");
        linkedVolume2.setOwnerEmail("john.smith@example.org");
        linkedVolume2.setProductId("Product Id");
        linkedVolume2.setRegion("Region");
        linkedVolume2.setSize(1L);
        linkedVolume2.setState("State");
        linkedVolume2.setSuggestions(new HashSet<>());
        linkedVolume2.setVolumeId("Volume Id");
        linkedVolume2.setVolumeType("com.vermeg.aws.cost.optimization.dashboard.entities.EBSVolume");
        EBSOptimizationSuggestion ebsOptimizationSuggestion2 = mock(EBSOptimizationSuggestion.class);
        when(ebsOptimizationSuggestion2.getStatus()).thenReturn(SuggestionStatus.Pending);
        doNothing().when(ebsOptimizationSuggestion2).setAssociatedAccount(Mockito.<AwsAccountCredentials>any());
        doNothing().when(ebsOptimizationSuggestion2).setCategory(Mockito.<EBSSuggestionCategory>any());
        doNothing().when(ebsOptimizationSuggestion2).setCreatedDate(Mockito.<Date>any());
        doNothing().when(ebsOptimizationSuggestion2).setDescription(Mockito.<String>any());
        doNothing().when(ebsOptimizationSuggestion2).setId(Mockito.<Long>any());
        doNothing().when(ebsOptimizationSuggestion2).setLinkedVolume(Mockito.<EBSVolume>any());
        doNothing().when(ebsOptimizationSuggestion2).setRecommendation(Mockito.<String>any());
        doNothing().when(ebsOptimizationSuggestion2).setStatus(Mockito.<SuggestionStatus>any());
        doNothing().when(ebsOptimizationSuggestion2).setTitle(Mockito.<String>any());
        ebsOptimizationSuggestion2.setAssociatedAccount(associatedAccount3);
        ebsOptimizationSuggestion2.setCategory(EBSSuggestionCategory.LEL);
        ebsOptimizationSuggestion2
                .setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        ebsOptimizationSuggestion2.setDescription("Description");
        ebsOptimizationSuggestion2.setId(2L);
        ebsOptimizationSuggestion2.setLinkedVolume(linkedVolume2);
        ebsOptimizationSuggestion2
                .setRecommendation("com.vermeg.aws.cost.optimization.dashboard.entities.EBSOptimizationSuggestion");
        ebsOptimizationSuggestion2.setStatus(SuggestionStatus.Ignored);
        ebsOptimizationSuggestion2.setTitle("Mr");

        ArrayList<EBSOptimizationSuggestion> ebsOptimizationSuggestionList = new ArrayList<>();
        ebsOptimizationSuggestionList.add(ebsOptimizationSuggestion2);
        ebsOptimizationSuggestionList.add(ebsOptimizationSuggestion);
        when(ebsOptimizationSuggestionRepository.findAll()).thenReturn(ebsOptimizationSuggestionList);
        computeSuggestionsService.deleteDealtWithEbsSuggestions();
        verify(ebsOptimizationSuggestionRepository).findAll();
        verify(ebsOptimizationSuggestion2).getStatus();
        verify(ebsOptimizationSuggestion2).setAssociatedAccount(Mockito.<AwsAccountCredentials>any());
        verify(ebsOptimizationSuggestion2).setCategory(Mockito.<EBSSuggestionCategory>any());
        verify(ebsOptimizationSuggestion2).setCreatedDate(Mockito.<Date>any());
        verify(ebsOptimizationSuggestion2).setDescription(Mockito.<String>any());
        verify(ebsOptimizationSuggestion2).setId(Mockito.<Long>any());
        verify(ebsOptimizationSuggestion2).setLinkedVolume(Mockito.<EBSVolume>any());
        verify(ebsOptimizationSuggestion2).setRecommendation(Mockito.<String>any());
        verify(ebsOptimizationSuggestion2).setStatus(Mockito.<SuggestionStatus>any());
        verify(ebsOptimizationSuggestion2).setTitle(Mockito.<String>any());
    }

    /**
     * Method under test: {@link ComputeSuggestionsService#deleteDealtWithEbsSuggestions()}
     */
    @Test
    void testDeleteDealtWithEbsSuggestions5() {
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

        EBSVolume linkedVolume = new EBSVolume();
        linkedVolume.setAssociatedAccount(associatedAccount2);
        linkedVolume
                .setCreationTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        linkedVolume.setCreationTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedVolume.setEnvironmentType("Environment Type");
        linkedVolume.setId(1L);
        linkedVolume.setInstanceId("42");
        linkedVolume.setLastUpdateTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedVolume.setOperationHours("Operation Hours");
        linkedVolume.setOwnerEmail("jane.doe@example.org");
        linkedVolume.setProductId("42");
        linkedVolume.setRegion("us-east-2");
        linkedVolume.setSize(3L);
        linkedVolume.setState("MD");
        linkedVolume.setSuggestions(new HashSet<>());
        linkedVolume.setVolumeId("42");
        linkedVolume.setVolumeType("Volume Type");

        EBSOptimizationSuggestion ebsOptimizationSuggestion = new EBSOptimizationSuggestion();
        ebsOptimizationSuggestion.setAssociatedAccount(associatedAccount);
        ebsOptimizationSuggestion.setCategory(EBSSuggestionCategory.OLDERGENERATION);
        ebsOptimizationSuggestion
                .setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        ebsOptimizationSuggestion.setDescription("The characteristics of someone or something");
        ebsOptimizationSuggestion.setId(1L);
        ebsOptimizationSuggestion.setLinkedVolume(linkedVolume);
        ebsOptimizationSuggestion.setRecommendation("Recommendation");
        ebsOptimizationSuggestion.setStatus(SuggestionStatus.Pending);
        ebsOptimizationSuggestion.setTitle("Dr");

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

        EBSVolume linkedVolume2 = new EBSVolume();
        linkedVolume2.setAssociatedAccount(associatedAccount4);
        linkedVolume2
                .setCreationTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        linkedVolume2.setCreationTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedVolume2.setEnvironmentType("com.vermeg.aws.cost.optimization.dashboard.entities.EBSVolume");
        linkedVolume2.setId(2L);
        linkedVolume2.setInstanceId("Instance Id");
        linkedVolume2.setLastUpdateTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedVolume2.setOperationHours("com.vermeg.aws.cost.optimization.dashboard.entities.EBSVolume");
        linkedVolume2.setOwnerEmail("john.smith@example.org");
        linkedVolume2.setProductId("Product Id");
        linkedVolume2.setRegion("Region");
        linkedVolume2.setSize(1L);
        linkedVolume2.setState("State");
        linkedVolume2.setSuggestions(new HashSet<>());
        linkedVolume2.setVolumeId("Volume Id");
        linkedVolume2.setVolumeType("com.vermeg.aws.cost.optimization.dashboard.entities.EBSVolume");
        EBSOptimizationSuggestion ebsOptimizationSuggestion2 = mock(EBSOptimizationSuggestion.class);
        when(ebsOptimizationSuggestion2.getStatus()).thenReturn(SuggestionStatus.Ignored);
        when(ebsOptimizationSuggestion2.getCreatedDate())
                .thenReturn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        doNothing().when(ebsOptimizationSuggestion2).setAssociatedAccount(Mockito.<AwsAccountCredentials>any());
        doNothing().when(ebsOptimizationSuggestion2).setCategory(Mockito.<EBSSuggestionCategory>any());
        doNothing().when(ebsOptimizationSuggestion2).setCreatedDate(Mockito.<Date>any());
        doNothing().when(ebsOptimizationSuggestion2).setDescription(Mockito.<String>any());
        doNothing().when(ebsOptimizationSuggestion2).setId(Mockito.<Long>any());
        doNothing().when(ebsOptimizationSuggestion2).setLinkedVolume(Mockito.<EBSVolume>any());
        doNothing().when(ebsOptimizationSuggestion2).setRecommendation(Mockito.<String>any());
        doNothing().when(ebsOptimizationSuggestion2).setStatus(Mockito.<SuggestionStatus>any());
        doNothing().when(ebsOptimizationSuggestion2).setTitle(Mockito.<String>any());
        ebsOptimizationSuggestion2.setAssociatedAccount(associatedAccount3);
        ebsOptimizationSuggestion2.setCategory(EBSSuggestionCategory.LEL);
        ebsOptimizationSuggestion2
                .setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        ebsOptimizationSuggestion2.setDescription("Description");
        ebsOptimizationSuggestion2.setId(2L);
        ebsOptimizationSuggestion2.setLinkedVolume(linkedVolume2);
        ebsOptimizationSuggestion2
                .setRecommendation("com.vermeg.aws.cost.optimization.dashboard.entities.EBSOptimizationSuggestion");
        ebsOptimizationSuggestion2.setStatus(SuggestionStatus.Ignored);
        ebsOptimizationSuggestion2.setTitle("Mr");

        ArrayList<EBSOptimizationSuggestion> ebsOptimizationSuggestionList = new ArrayList<>();
        ebsOptimizationSuggestionList.add(ebsOptimizationSuggestion2);
        ebsOptimizationSuggestionList.add(ebsOptimizationSuggestion);
        when(ebsOptimizationSuggestionRepository.findAll()).thenReturn(ebsOptimizationSuggestionList);
        computeSuggestionsService.deleteDealtWithEbsSuggestions();
        verify(ebsOptimizationSuggestionRepository).findAll();
        verify(ebsOptimizationSuggestion2).getStatus();
        verify(ebsOptimizationSuggestion2).getCreatedDate();
        verify(ebsOptimizationSuggestion2).setAssociatedAccount(Mockito.<AwsAccountCredentials>any());
        verify(ebsOptimizationSuggestion2).setCategory(Mockito.<EBSSuggestionCategory>any());
        verify(ebsOptimizationSuggestion2).setCreatedDate(Mockito.<Date>any());
        verify(ebsOptimizationSuggestion2).setDescription(Mockito.<String>any());
        verify(ebsOptimizationSuggestion2).setId(Mockito.<Long>any());
        verify(ebsOptimizationSuggestion2).setLinkedVolume(Mockito.<EBSVolume>any());
        verify(ebsOptimizationSuggestion2).setRecommendation(Mockito.<String>any());
        verify(ebsOptimizationSuggestion2).setStatus(Mockito.<SuggestionStatus>any());
        verify(ebsOptimizationSuggestion2).setTitle(Mockito.<String>any());
    }

    /**
     * Method under test: {@link ComputeSuggestionsService#generateEcAutoScaling2Suggestions()}
     */
    @Test
    void testGenerateEcAutoScaling2Suggestions() {
        ArrayList<EC2OptimizationSuggestion> ec2OptimizationSuggestionList = new ArrayList<>();
        when(ec2OptimizationSuggestionRepository.saveAll(Mockito.<Iterable<EC2OptimizationSuggestion>>any()))
                .thenReturn(ec2OptimizationSuggestionList);
        when(computeServices.getAllInstances()).thenReturn(new ArrayList<>());
        List<EC2OptimizationSuggestion> actualGenerateEcAutoScaling2SuggestionsResult = computeSuggestionsService
                .generateEcAutoScaling2Suggestions();
        assertSame(ec2OptimizationSuggestionList, actualGenerateEcAutoScaling2SuggestionsResult);
        assertTrue(actualGenerateEcAutoScaling2SuggestionsResult.isEmpty());
        verify(ec2OptimizationSuggestionRepository).saveAll(Mockito.<Iterable<EC2OptimizationSuggestion>>any());
        verify(computeServices).getAllInstances();
    }

    /**
     * Method under test: {@link ComputeSuggestionsService#generateEcAutoScaling2Suggestions()}
     */
    @Test
    void testGenerateEcAutoScaling2Suggestions2() {
        ArrayList<EC2OptimizationSuggestion> ec2OptimizationSuggestionList = new ArrayList<>();
        when(ec2OptimizationSuggestionRepository.saveAll(Mockito.<Iterable<EC2OptimizationSuggestion>>any()))
                .thenReturn(ec2OptimizationSuggestionList);

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

        ArrayList<EC2Instance> ec2InstanceList = new ArrayList<>();
        ec2InstanceList.add(ec2Instance);
        when(computeServices.getAllInstances()).thenReturn(ec2InstanceList);
        when(autoScalingService.isInstanceInAutoScalingGroup(Mockito.<String>any())).thenReturn(true);
        List<EC2OptimizationSuggestion> actualGenerateEcAutoScaling2SuggestionsResult = computeSuggestionsService
                .generateEcAutoScaling2Suggestions();
        assertSame(ec2OptimizationSuggestionList, actualGenerateEcAutoScaling2SuggestionsResult);
        assertTrue(actualGenerateEcAutoScaling2SuggestionsResult.isEmpty());
        verify(ec2OptimizationSuggestionRepository).saveAll(Mockito.<Iterable<EC2OptimizationSuggestion>>any());
        verify(computeServices).getAllInstances();
        verify(autoScalingService).isInstanceInAutoScalingGroup(Mockito.<String>any());
    }

    /**
     * Method under test: {@link ComputeSuggestionsService#generateEcAutoScaling2Suggestions()}
     */
    @Test
    void testGenerateEcAutoScaling2Suggestions3() {
        ArrayList<EC2OptimizationSuggestion> ec2OptimizationSuggestionList = new ArrayList<>();
        when(ec2OptimizationSuggestionRepository.saveAll(Mockito.<Iterable<EC2OptimizationSuggestion>>any()))
                .thenReturn(ec2OptimizationSuggestionList);

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

        ArrayList<EC2Instance> ec2InstanceList = new ArrayList<>();
        ec2InstanceList.add(ec2Instance);
        when(computeServices.getAllInstances()).thenReturn(ec2InstanceList);
        when(autoScalingService.isInstanceInAutoScalingGroup(Mockito.<String>any())).thenReturn(false);
        List<EC2OptimizationSuggestion> actualGenerateEcAutoScaling2SuggestionsResult = computeSuggestionsService
                .generateEcAutoScaling2Suggestions();
        assertSame(ec2OptimizationSuggestionList, actualGenerateEcAutoScaling2SuggestionsResult);
        assertTrue(actualGenerateEcAutoScaling2SuggestionsResult.isEmpty());
        verify(ec2OptimizationSuggestionRepository).saveAll(Mockito.<Iterable<EC2OptimizationSuggestion>>any());
        verify(computeServices).getAllInstances();
        verify(autoScalingService).isInstanceInAutoScalingGroup(Mockito.<String>any());
    }

    /**
     * Method under test: {@link ComputeSuggestionsService#generateEc2ResourceSuggestions()}
     */
    @Test
    void testGenerateEc2ResourceSuggestions() {
        ArrayList<EC2OptimizationSuggestion> ec2OptimizationSuggestionList = new ArrayList<>();
        when(ec2OptimizationSuggestionRepository.saveAll(Mockito.<Iterable<EC2OptimizationSuggestion>>any()))
                .thenReturn(ec2OptimizationSuggestionList);
        when(computeServices.getAllInstances()).thenReturn(new ArrayList<>());
        List<EC2OptimizationSuggestion> actualGenerateEc2ResourceSuggestionsResult = computeSuggestionsService
                .generateEc2ResourceSuggestions();
        assertSame(ec2OptimizationSuggestionList, actualGenerateEc2ResourceSuggestionsResult);
        assertTrue(actualGenerateEc2ResourceSuggestionsResult.isEmpty());
        verify(ec2OptimizationSuggestionRepository).saveAll(Mockito.<Iterable<EC2OptimizationSuggestion>>any());
        verify(computeServices).getAllInstances();
    }

    /**
     * Method under test: {@link ComputeSuggestionsService#generateEc2ResourceSuggestions()}
     */
    @Test
    void testGenerateEc2ResourceSuggestions2() {
        ArrayList<EC2OptimizationSuggestion> ec2OptimizationSuggestionList = new ArrayList<>();
        when(ec2OptimizationSuggestionRepository.saveAll(Mockito.<Iterable<EC2OptimizationSuggestion>>any()))
                .thenReturn(ec2OptimizationSuggestionList);

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

        ArrayList<EC2Instance> ec2InstanceList = new ArrayList<>();
        ec2InstanceList.add(ec2Instance);
        when(computeServices.getAllInstances()).thenReturn(ec2InstanceList);
        List<EC2OptimizationSuggestion> actualGenerateEc2ResourceSuggestionsResult = computeSuggestionsService
                .generateEc2ResourceSuggestions();
        assertSame(ec2OptimizationSuggestionList, actualGenerateEc2ResourceSuggestionsResult);
        assertTrue(actualGenerateEc2ResourceSuggestionsResult.isEmpty());
        verify(ec2OptimizationSuggestionRepository).saveAll(Mockito.<Iterable<EC2OptimizationSuggestion>>any());
        verify(computeServices).getAllInstances();
    }

    /**
     * Method under test: {@link ComputeSuggestionsService#generateEc2SuggestionsWithInstanceId(String)}
     */
    @Test
    void testGenerateEc2SuggestionsWithInstanceId() {
        ArrayList<EC2OptimizationSuggestion> ec2OptimizationSuggestionList = new ArrayList<>();
        when(ec2OptimizationSuggestionRepository.saveAll(Mockito.<Iterable<EC2OptimizationSuggestion>>any()))
                .thenReturn(ec2OptimizationSuggestionList);

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
        when(computeServices.getInstanceById(Mockito.<String>any())).thenReturn(ec2Instance);
        when(autoScalingService.isInstanceInAutoScalingGroup(Mockito.<String>any())).thenReturn(true);
        when(cloudWatchService.getCPUUtilization(Mockito.<String>any(), anyInt(), anyInt()))
                .thenReturn(new ArrayList<>());
        when(cloudWatchService.getNetworkIn(Mockito.<String>any(), anyInt(), anyInt())).thenReturn(new ArrayList<>());
        when(cloudWatchService.getNetworkOut(Mockito.<String>any(), anyInt(), anyInt())).thenReturn(new ArrayList<>());
        List<EC2OptimizationSuggestion> actualGenerateEc2SuggestionsWithInstanceIdResult = computeSuggestionsService
                .generateEc2SuggestionsWithInstanceId("42");
        assertSame(ec2OptimizationSuggestionList, actualGenerateEc2SuggestionsWithInstanceIdResult);
        assertTrue(actualGenerateEc2SuggestionsWithInstanceIdResult.isEmpty());
        verify(ec2OptimizationSuggestionRepository).saveAll(Mockito.<Iterable<EC2OptimizationSuggestion>>any());
        verify(computeServices).getInstanceById(Mockito.<String>any());
        verify(autoScalingService).isInstanceInAutoScalingGroup(Mockito.<String>any());
        verify(cloudWatchService).getCPUUtilization(Mockito.<String>any(), anyInt(), anyInt());
        verify(cloudWatchService).getNetworkIn(Mockito.<String>any(), anyInt(), anyInt());
        verify(cloudWatchService).getNetworkOut(Mockito.<String>any(), anyInt(), anyInt());
    }

    /**
     * Method under test: {@link ComputeSuggestionsService#generateEc2SuggestionsWithInstanceId(String)}
     */
    @Test
    void testGenerateEc2SuggestionsWithInstanceId2() {
        ArrayList<EC2OptimizationSuggestion> ec2OptimizationSuggestionList = new ArrayList<>();
        when(ec2OptimizationSuggestionRepository.saveAll(Mockito.<Iterable<EC2OptimizationSuggestion>>any()))
                .thenReturn(ec2OptimizationSuggestionList);

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
        when(computeServices.getInstanceById(Mockito.<String>any())).thenReturn(ec2Instance);
        when(autoScalingService.isInstanceInAutoScalingGroup(Mockito.<String>any())).thenReturn(false);
        when(cloudWatchService.getCPUUtilization(Mockito.<String>any(), anyInt(), anyInt()))
                .thenReturn(new ArrayList<>());
        when(cloudWatchService.getNetworkIn(Mockito.<String>any(), anyInt(), anyInt())).thenReturn(new ArrayList<>());
        when(cloudWatchService.getNetworkOut(Mockito.<String>any(), anyInt(), anyInt())).thenReturn(new ArrayList<>());
        List<EC2OptimizationSuggestion> actualGenerateEc2SuggestionsWithInstanceIdResult = computeSuggestionsService
                .generateEc2SuggestionsWithInstanceId("42");
        assertSame(ec2OptimizationSuggestionList, actualGenerateEc2SuggestionsWithInstanceIdResult);
        assertTrue(actualGenerateEc2SuggestionsWithInstanceIdResult.isEmpty());
        verify(ec2OptimizationSuggestionRepository).saveAll(Mockito.<Iterable<EC2OptimizationSuggestion>>any());
        verify(computeServices).getInstanceById(Mockito.<String>any());
        verify(autoScalingService).isInstanceInAutoScalingGroup(Mockito.<String>any());
        verify(cloudWatchService).getCPUUtilization(Mockito.<String>any(), anyInt(), anyInt());
        verify(cloudWatchService).getNetworkIn(Mockito.<String>any(), anyInt(), anyInt());
        verify(cloudWatchService).getNetworkOut(Mockito.<String>any(), anyInt(), anyInt());
    }

    /**
     * Method under test: {@link ComputeSuggestionsService#generateEc2SuggestionsWithInstanceId(String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGenerateEc2SuggestionsWithInstanceId3() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException
        //       at java.base/java.util.stream.ReferencePipeline$6$1.accept(ReferencePipeline.java:248)
        //       at java.base/java.util.ArrayList$ArrayListSpliterator.tryAdvance(ArrayList.java:1602)
        //       at java.base/java.util.stream.ReferencePipeline.forEachWithCancel(ReferencePipeline.java:129)
        //       at java.base/java.util.stream.AbstractPipeline.copyIntoWithCancel(AbstractPipeline.java:527)
        //       at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:513)
        //       at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:499)
        //       at java.base/java.util.stream.MatchOps$MatchOp.evaluateSequential(MatchOps.java:230)
        //       at java.base/java.util.stream.MatchOps$MatchOp.evaluateSequential(MatchOps.java:196)
        //       at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
        //       at java.base/java.util.stream.DoublePipeline.allMatch(DoublePipeline.java:551)
        //       at com.vermeg.aws.cost.optimization.dashboard.services.ComputeSuggestionsService.generateEc2SuggestionsWithInstanceId(ComputeSuggestionsService.java:247)
        //   See https://diff.blue/R013 to resolve this issue.

        when(ec2OptimizationSuggestionRepository.saveAll(Mockito.<Iterable<EC2OptimizationSuggestion>>any()))
                .thenReturn(new ArrayList<>());

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
        when(computeServices.getInstanceById(Mockito.<String>any())).thenReturn(ec2Instance);
        when(autoScalingService.isInstanceInAutoScalingGroup(Mockito.<String>any())).thenReturn(true);

        ArrayList<Datapoint> datapointList = new ArrayList<>();
        datapointList.add(new Datapoint());
        when(cloudWatchService.getCPUUtilization(Mockito.<String>any(), anyInt(), anyInt())).thenReturn(datapointList);
        when(cloudWatchService.getNetworkIn(Mockito.<String>any(), anyInt(), anyInt())).thenReturn(new ArrayList<>());
        when(cloudWatchService.getNetworkOut(Mockito.<String>any(), anyInt(), anyInt())).thenReturn(new ArrayList<>());
        computeSuggestionsService.generateEc2SuggestionsWithInstanceId("42");
    }

    /**
     * Method under test: {@link ComputeSuggestionsService#generateEc2SuggestionsWithInstanceId(String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGenerateEc2SuggestionsWithInstanceId4() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException
        //       at java.base/java.util.stream.ReferencePipeline$6$1.accept(ReferencePipeline.java:248)
        //       at java.base/java.util.ArrayList$ArrayListSpliterator.tryAdvance(ArrayList.java:1602)
        //       at java.base/java.util.stream.ReferencePipeline.forEachWithCancel(ReferencePipeline.java:129)
        //       at java.base/java.util.stream.AbstractPipeline.copyIntoWithCancel(AbstractPipeline.java:527)
        //       at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:513)
        //       at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:499)
        //       at java.base/java.util.stream.MatchOps$MatchOp.evaluateSequential(MatchOps.java:230)
        //       at java.base/java.util.stream.MatchOps$MatchOp.evaluateSequential(MatchOps.java:196)
        //       at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
        //       at java.base/java.util.stream.DoublePipeline.allMatch(DoublePipeline.java:551)
        //       at com.vermeg.aws.cost.optimization.dashboard.services.ComputeSuggestionsService.generateEc2SuggestionsWithInstanceId(ComputeSuggestionsService.java:260)
        //   See https://diff.blue/R013 to resolve this issue.

        when(ec2OptimizationSuggestionRepository.saveAll(Mockito.<Iterable<EC2OptimizationSuggestion>>any()))
                .thenReturn(new ArrayList<>());

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
        when(computeServices.getInstanceById(Mockito.<String>any())).thenReturn(ec2Instance);
        when(autoScalingService.isInstanceInAutoScalingGroup(Mockito.<String>any())).thenReturn(true);

        ArrayList<Datapoint> datapointList = new ArrayList<>();
        datapointList.add(new Datapoint());
        when(cloudWatchService.getCPUUtilization(Mockito.<String>any(), anyInt(), anyInt()))
                .thenReturn(new ArrayList<>());
        when(cloudWatchService.getNetworkIn(Mockito.<String>any(), anyInt(), anyInt())).thenReturn(datapointList);
        when(cloudWatchService.getNetworkOut(Mockito.<String>any(), anyInt(), anyInt())).thenReturn(new ArrayList<>());
        computeSuggestionsService.generateEc2SuggestionsWithInstanceId("42");
    }

    /**
     * Method under test: {@link ComputeSuggestionsService#generateEc2SuggestionsWithInstanceId(String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGenerateEc2SuggestionsWithInstanceId5() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException
        //       at java.base/java.util.stream.ReferencePipeline$6$1.accept(ReferencePipeline.java:248)
        //       at java.base/java.util.ArrayList$ArrayListSpliterator.tryAdvance(ArrayList.java:1602)
        //       at java.base/java.util.stream.ReferencePipeline.forEachWithCancel(ReferencePipeline.java:129)
        //       at java.base/java.util.stream.AbstractPipeline.copyIntoWithCancel(AbstractPipeline.java:527)
        //       at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:513)
        //       at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:499)
        //       at java.base/java.util.stream.MatchOps$MatchOp.evaluateSequential(MatchOps.java:230)
        //       at java.base/java.util.stream.MatchOps$MatchOp.evaluateSequential(MatchOps.java:196)
        //       at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
        //       at java.base/java.util.stream.DoublePipeline.allMatch(DoublePipeline.java:551)
        //       at com.vermeg.aws.cost.optimization.dashboard.services.ComputeSuggestionsService.generateEc2SuggestionsWithInstanceId(ComputeSuggestionsService.java:273)
        //   See https://diff.blue/R013 to resolve this issue.

        when(ec2OptimizationSuggestionRepository.saveAll(Mockito.<Iterable<EC2OptimizationSuggestion>>any()))
                .thenReturn(new ArrayList<>());

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
        when(computeServices.getInstanceById(Mockito.<String>any())).thenReturn(ec2Instance);
        when(autoScalingService.isInstanceInAutoScalingGroup(Mockito.<String>any())).thenReturn(true);

        ArrayList<Datapoint> datapointList = new ArrayList<>();
        datapointList.add(new Datapoint());
        when(cloudWatchService.getCPUUtilization(Mockito.<String>any(), anyInt(), anyInt()))
                .thenReturn(new ArrayList<>());
        when(cloudWatchService.getNetworkIn(Mockito.<String>any(), anyInt(), anyInt())).thenReturn(new ArrayList<>());
        when(cloudWatchService.getNetworkOut(Mockito.<String>any(), anyInt(), anyInt())).thenReturn(datapointList);
        computeSuggestionsService.generateEc2SuggestionsWithInstanceId("42");
    }

    /**
     * Method under test: {@link ComputeSuggestionsService#deleteEc2Suggestion(Long)}
     */
    @Test
    void testDeleteEc2Suggestion() {
        doNothing().when(ec2OptimizationSuggestionRepository).deleteById(Mockito.<Long>any());
        computeSuggestionsService.deleteEc2Suggestion(1L);
        verify(ec2OptimizationSuggestionRepository).deleteById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link ComputeSuggestionsService#deleteDealtWithEc2Suggestions()}
     */
    @Test
    void testDeleteDealtWithEc2Suggestions() {
        when(ec2OptimizationSuggestionRepository.findAll()).thenReturn(new ArrayList<>());
        computeSuggestionsService.deleteDealtWithEc2Suggestions();
        verify(ec2OptimizationSuggestionRepository).findAll();
    }

    /**
     * Method under test: {@link ComputeSuggestionsService#deleteDealtWithEc2Suggestions()}
     */
    @Test
    void testDeleteDealtWithEc2Suggestions2() {
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

        EC2Instance linkedInstance = new EC2Instance();
        linkedInstance.setAssociatedAccount(associatedAccount2);
        linkedInstance.setClientName("Dr Jane Doe");
        linkedInstance.setCreationTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedInstance.setEnvironmentType("Environment Type");
        linkedInstance.setId(1L);
        linkedInstance.setInstanceId("42");
        linkedInstance.setInstanceType("Instance Type");
        linkedInstance.setLastDowntimeTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedInstance.setLastUpdateTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedInstance.setLastUptimeTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedInstance.setLinkedSuggestion(new HashSet<>());
        linkedInstance.setMonitoring("Monitoring");
        linkedInstance.setOperationHours("Operation Hours");
        linkedInstance.setOwnerEmail("jane.doe@example.org");
        linkedInstance.setPlatform("Platform");
        linkedInstance.setPrivateIp("Private Ip");
        linkedInstance.setProductId("42");
        linkedInstance.setPublicIp("Public Ip");
        linkedInstance.setRegion("us-east-2");
        linkedInstance.setState("MD");

        EC2OptimizationSuggestion ec2OptimizationSuggestion = new EC2OptimizationSuggestion();
        ec2OptimizationSuggestion.setAssociatedAccount(associatedAccount);
        ec2OptimizationSuggestion.setCategory(EC2SuggestionCategory.LOWNETWORKIN);
        ec2OptimizationSuggestion.setCreatedDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        ec2OptimizationSuggestion.setDescription("The characteristics of someone or something");
        ec2OptimizationSuggestion.setId(1L);
        ec2OptimizationSuggestion.setLinkedInstance(linkedInstance);
        ec2OptimizationSuggestion.setRecommendation("Recommendation");
        ec2OptimizationSuggestion.setStatus(SuggestionStatus.Pending);
        ec2OptimizationSuggestion.setTitle("Dr");

        ArrayList<EC2OptimizationSuggestion> ec2OptimizationSuggestionList = new ArrayList<>();
        ec2OptimizationSuggestionList.add(ec2OptimizationSuggestion);
        when(ec2OptimizationSuggestionRepository.findAll()).thenReturn(ec2OptimizationSuggestionList);
        computeSuggestionsService.deleteDealtWithEc2Suggestions();
        verify(ec2OptimizationSuggestionRepository).findAll();
    }

    /**
     * Method under test: {@link ComputeSuggestionsService#deleteDealtWithEc2Suggestions()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testDeleteDealtWithEc2Suggestions3() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.time.DateTimeException: Unable to obtain LocalDateTime from TemporalAccessor: 2023-09-13T10:52:51.644Z of type java.time.Instant
        //       at java.base/java.time.LocalDateTime.from(LocalDateTime.java:463)
        //       at java.base/java.time.LocalDateTime.until(LocalDateTime.java:1677)
        //       at java.base/java.time.Duration.between(Duration.java:492)
        //       at com.vermeg.aws.cost.optimization.dashboard.services.ComputeSuggestionsService.deleteDealtWithEc2Suggestions(ComputeSuggestionsService.java:308)
        //   java.time.DateTimeException: Unable to obtain LocalDate from TemporalAccessor: 2023-09-13T10:52:51.644Z of type java.time.Instant
        //       at java.base/java.time.LocalDate.from(LocalDate.java:398)
        //       at java.base/java.time.LocalDateTime.from(LocalDateTime.java:458)
        //       at java.base/java.time.LocalDateTime.until(LocalDateTime.java:1677)
        //       at java.base/java.time.Duration.between(Duration.java:492)
        //       at com.vermeg.aws.cost.optimization.dashboard.services.ComputeSuggestionsService.deleteDealtWithEc2Suggestions(ComputeSuggestionsService.java:308)
        //   See https://diff.blue/R013 to resolve this issue.

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

        EC2Instance linkedInstance = new EC2Instance();
        linkedInstance.setAssociatedAccount(associatedAccount2);
        linkedInstance.setClientName("Dr Jane Doe");
        linkedInstance.setCreationTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedInstance.setEnvironmentType("Environment Type");
        linkedInstance.setId(1L);
        linkedInstance.setInstanceId("42");
        linkedInstance.setInstanceType("Instance Type");
        linkedInstance.setLastDowntimeTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedInstance.setLastUpdateTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedInstance.setLastUptimeTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedInstance.setLinkedSuggestion(new HashSet<>());
        linkedInstance.setMonitoring("Monitoring");
        linkedInstance.setOperationHours("Operation Hours");
        linkedInstance.setOwnerEmail("jane.doe@example.org");
        linkedInstance.setPlatform("Platform");
        linkedInstance.setPrivateIp("Private Ip");
        linkedInstance.setProductId("42");
        linkedInstance.setPublicIp("Public Ip");
        linkedInstance.setRegion("us-east-2");
        linkedInstance.setState("MD");

        EC2OptimizationSuggestion ec2OptimizationSuggestion = new EC2OptimizationSuggestion();
        ec2OptimizationSuggestion.setAssociatedAccount(associatedAccount);
        ec2OptimizationSuggestion.setCategory(EC2SuggestionCategory.LOWNETWORKIN);
        ec2OptimizationSuggestion.setCreatedDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        ec2OptimizationSuggestion.setDescription("The characteristics of someone or something");
        ec2OptimizationSuggestion.setId(1L);
        ec2OptimizationSuggestion.setLinkedInstance(linkedInstance);
        ec2OptimizationSuggestion.setRecommendation("Recommendation");
        ec2OptimizationSuggestion.setStatus(SuggestionStatus.Pending);
        ec2OptimizationSuggestion.setTitle("Dr");

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

        EC2Instance linkedInstance2 = new EC2Instance();
        linkedInstance2.setAssociatedAccount(associatedAccount4);
        linkedInstance2.setClientName("Mr John Smith");
        linkedInstance2.setCreationTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedInstance2.setEnvironmentType("com.vermeg.aws.cost.optimization.dashboard.entities.EC2Instance");
        linkedInstance2.setId(2L);
        linkedInstance2.setInstanceId("Instance Id");
        linkedInstance2.setInstanceType("com.vermeg.aws.cost.optimization.dashboard.entities.EC2Instance");
        linkedInstance2.setLastDowntimeTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedInstance2.setLastUpdateTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedInstance2.setLastUptimeTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedInstance2.setLinkedSuggestion(new HashSet<>());
        linkedInstance2.setMonitoring("com.vermeg.aws.cost.optimization.dashboard.entities.EC2Instance");
        linkedInstance2.setOperationHours("com.vermeg.aws.cost.optimization.dashboard.entities.EC2Instance");
        linkedInstance2.setOwnerEmail("john.smith@example.org");
        linkedInstance2.setPlatform("com.vermeg.aws.cost.optimization.dashboard.entities.EC2Instance");
        linkedInstance2.setPrivateIp("com.vermeg.aws.cost.optimization.dashboard.entities.EC2Instance");
        linkedInstance2.setProductId("Product Id");
        linkedInstance2.setPublicIp("com.vermeg.aws.cost.optimization.dashboard.entities.EC2Instance");
        linkedInstance2.setRegion("Region");
        linkedInstance2.setState("State");

        EC2OptimizationSuggestion ec2OptimizationSuggestion2 = new EC2OptimizationSuggestion();
        ec2OptimizationSuggestion2.setAssociatedAccount(associatedAccount3);
        ec2OptimizationSuggestion2.setCategory(EC2SuggestionCategory.LOWNETWORKOUT);
        ec2OptimizationSuggestion2.setCreatedDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        ec2OptimizationSuggestion2.setDescription("Description");
        ec2OptimizationSuggestion2.setId(2L);
        ec2OptimizationSuggestion2.setLinkedInstance(linkedInstance2);
        ec2OptimizationSuggestion2
                .setRecommendation("com.vermeg.aws.cost.optimization.dashboard.entities.EC2OptimizationSuggestion");
        ec2OptimizationSuggestion2.setStatus(SuggestionStatus.Ignored);
        ec2OptimizationSuggestion2.setTitle("Mr");

        ArrayList<EC2OptimizationSuggestion> ec2OptimizationSuggestionList = new ArrayList<>();
        ec2OptimizationSuggestionList.add(ec2OptimizationSuggestion2);
        ec2OptimizationSuggestionList.add(ec2OptimizationSuggestion);
        when(ec2OptimizationSuggestionRepository.findAll()).thenReturn(ec2OptimizationSuggestionList);
        computeSuggestionsService.deleteDealtWithEc2Suggestions();
    }

    /**
     * Method under test: {@link ComputeSuggestionsService#deleteDealtWithEc2Suggestions()}
     */
    @Test
    void testDeleteDealtWithEc2Suggestions4() {
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

        EC2Instance linkedInstance = new EC2Instance();
        linkedInstance.setAssociatedAccount(associatedAccount2);
        linkedInstance.setClientName("Dr Jane Doe");
        linkedInstance.setCreationTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedInstance.setEnvironmentType("Environment Type");
        linkedInstance.setId(1L);
        linkedInstance.setInstanceId("42");
        linkedInstance.setInstanceType("Instance Type");
        linkedInstance.setLastDowntimeTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedInstance.setLastUpdateTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedInstance.setLastUptimeTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedInstance.setLinkedSuggestion(new HashSet<>());
        linkedInstance.setMonitoring("Monitoring");
        linkedInstance.setOperationHours("Operation Hours");
        linkedInstance.setOwnerEmail("jane.doe@example.org");
        linkedInstance.setPlatform("Platform");
        linkedInstance.setPrivateIp("Private Ip");
        linkedInstance.setProductId("42");
        linkedInstance.setPublicIp("Public Ip");
        linkedInstance.setRegion("us-east-2");
        linkedInstance.setState("MD");
        EC2OptimizationSuggestion ec2OptimizationSuggestion = mock(EC2OptimizationSuggestion.class);
        when(ec2OptimizationSuggestion.getStatus()).thenReturn(SuggestionStatus.Pending);
        doNothing().when(ec2OptimizationSuggestion).setAssociatedAccount(Mockito.<AwsAccountCredentials>any());
        doNothing().when(ec2OptimizationSuggestion).setCategory(Mockito.<EC2SuggestionCategory>any());
        doNothing().when(ec2OptimizationSuggestion).setCreatedDate(Mockito.<LocalDateTime>any());
        doNothing().when(ec2OptimizationSuggestion).setDescription(Mockito.<String>any());
        doNothing().when(ec2OptimizationSuggestion).setId(Mockito.<Long>any());
        doNothing().when(ec2OptimizationSuggestion).setLinkedInstance(Mockito.<EC2Instance>any());
        doNothing().when(ec2OptimizationSuggestion).setRecommendation(Mockito.<String>any());
        doNothing().when(ec2OptimizationSuggestion).setStatus(Mockito.<SuggestionStatus>any());
        doNothing().when(ec2OptimizationSuggestion).setTitle(Mockito.<String>any());
        ec2OptimizationSuggestion.setAssociatedAccount(associatedAccount);
        ec2OptimizationSuggestion.setCategory(EC2SuggestionCategory.LOWNETWORKIN);
        ec2OptimizationSuggestion.setCreatedDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        ec2OptimizationSuggestion.setDescription("The characteristics of someone or something");
        ec2OptimizationSuggestion.setId(1L);
        ec2OptimizationSuggestion.setLinkedInstance(linkedInstance);
        ec2OptimizationSuggestion.setRecommendation("Recommendation");
        ec2OptimizationSuggestion.setStatus(SuggestionStatus.Pending);
        ec2OptimizationSuggestion.setTitle("Dr");

        ArrayList<EC2OptimizationSuggestion> ec2OptimizationSuggestionList = new ArrayList<>();
        ec2OptimizationSuggestionList.add(ec2OptimizationSuggestion);
        when(ec2OptimizationSuggestionRepository.findAll()).thenReturn(ec2OptimizationSuggestionList);
        computeSuggestionsService.deleteDealtWithEc2Suggestions();
        verify(ec2OptimizationSuggestionRepository).findAll();
        verify(ec2OptimizationSuggestion).getStatus();
        verify(ec2OptimizationSuggestion).setAssociatedAccount(Mockito.<AwsAccountCredentials>any());
        verify(ec2OptimizationSuggestion).setCategory(Mockito.<EC2SuggestionCategory>any());
        verify(ec2OptimizationSuggestion).setCreatedDate(Mockito.<LocalDateTime>any());
        verify(ec2OptimizationSuggestion).setDescription(Mockito.<String>any());
        verify(ec2OptimizationSuggestion).setId(Mockito.<Long>any());
        verify(ec2OptimizationSuggestion).setLinkedInstance(Mockito.<EC2Instance>any());
        verify(ec2OptimizationSuggestion).setRecommendation(Mockito.<String>any());
        verify(ec2OptimizationSuggestion).setStatus(Mockito.<SuggestionStatus>any());
        verify(ec2OptimizationSuggestion).setTitle(Mockito.<String>any());
    }

    /**
     * Method under test: {@link ComputeSuggestionsService#deleteDealtWithEc2Suggestions()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testDeleteDealtWithEc2Suggestions5() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.time.DateTimeException: Unable to obtain LocalDateTime from TemporalAccessor: 2023-09-13T10:53:26.159Z of type java.time.Instant
        //       at java.base/java.time.LocalDateTime.from(LocalDateTime.java:463)
        //       at java.base/java.time.LocalDateTime.until(LocalDateTime.java:1677)
        //       at java.base/java.time.Duration.between(Duration.java:492)
        //       at com.vermeg.aws.cost.optimization.dashboard.services.ComputeSuggestionsService.deleteDealtWithEc2Suggestions(ComputeSuggestionsService.java:308)
        //   java.time.DateTimeException: Unable to obtain LocalDate from TemporalAccessor: 2023-09-13T10:53:26.159Z of type java.time.Instant
        //       at java.base/java.time.LocalDate.from(LocalDate.java:398)
        //       at java.base/java.time.LocalDateTime.from(LocalDateTime.java:458)
        //       at java.base/java.time.LocalDateTime.until(LocalDateTime.java:1677)
        //       at java.base/java.time.Duration.between(Duration.java:492)
        //       at com.vermeg.aws.cost.optimization.dashboard.services.ComputeSuggestionsService.deleteDealtWithEc2Suggestions(ComputeSuggestionsService.java:308)
        //   See https://diff.blue/R013 to resolve this issue.

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

        EC2Instance linkedInstance = new EC2Instance();
        linkedInstance.setAssociatedAccount(associatedAccount2);
        linkedInstance.setClientName("Dr Jane Doe");
        linkedInstance.setCreationTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedInstance.setEnvironmentType("Environment Type");
        linkedInstance.setId(1L);
        linkedInstance.setInstanceId("42");
        linkedInstance.setInstanceType("Instance Type");
        linkedInstance.setLastDowntimeTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedInstance.setLastUpdateTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedInstance.setLastUptimeTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedInstance.setLinkedSuggestion(new HashSet<>());
        linkedInstance.setMonitoring("Monitoring");
        linkedInstance.setOperationHours("Operation Hours");
        linkedInstance.setOwnerEmail("jane.doe@example.org");
        linkedInstance.setPlatform("Platform");
        linkedInstance.setPrivateIp("Private Ip");
        linkedInstance.setProductId("42");
        linkedInstance.setPublicIp("Public Ip");
        linkedInstance.setRegion("us-east-2");
        linkedInstance.setState("MD");
        EC2OptimizationSuggestion ec2OptimizationSuggestion = mock(EC2OptimizationSuggestion.class);
        when(ec2OptimizationSuggestion.getStatus()).thenReturn(SuggestionStatus.Ignored);
        when(ec2OptimizationSuggestion.getCreatedDate()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        doNothing().when(ec2OptimizationSuggestion).setAssociatedAccount(Mockito.<AwsAccountCredentials>any());
        doNothing().when(ec2OptimizationSuggestion).setCategory(Mockito.<EC2SuggestionCategory>any());
        doNothing().when(ec2OptimizationSuggestion).setCreatedDate(Mockito.<LocalDateTime>any());
        doNothing().when(ec2OptimizationSuggestion).setDescription(Mockito.<String>any());
        doNothing().when(ec2OptimizationSuggestion).setId(Mockito.<Long>any());
        doNothing().when(ec2OptimizationSuggestion).setLinkedInstance(Mockito.<EC2Instance>any());
        doNothing().when(ec2OptimizationSuggestion).setRecommendation(Mockito.<String>any());
        doNothing().when(ec2OptimizationSuggestion).setStatus(Mockito.<SuggestionStatus>any());
        doNothing().when(ec2OptimizationSuggestion).setTitle(Mockito.<String>any());
        ec2OptimizationSuggestion.setAssociatedAccount(associatedAccount);
        ec2OptimizationSuggestion.setCategory(EC2SuggestionCategory.LOWNETWORKIN);
        ec2OptimizationSuggestion.setCreatedDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        ec2OptimizationSuggestion.setDescription("The characteristics of someone or something");
        ec2OptimizationSuggestion.setId(1L);
        ec2OptimizationSuggestion.setLinkedInstance(linkedInstance);
        ec2OptimizationSuggestion.setRecommendation("Recommendation");
        ec2OptimizationSuggestion.setStatus(SuggestionStatus.Pending);
        ec2OptimizationSuggestion.setTitle("Dr");

        ArrayList<EC2OptimizationSuggestion> ec2OptimizationSuggestionList = new ArrayList<>();
        ec2OptimizationSuggestionList.add(ec2OptimizationSuggestion);
        when(ec2OptimizationSuggestionRepository.findAll()).thenReturn(ec2OptimizationSuggestionList);
        computeSuggestionsService.deleteDealtWithEc2Suggestions();
    }
}

