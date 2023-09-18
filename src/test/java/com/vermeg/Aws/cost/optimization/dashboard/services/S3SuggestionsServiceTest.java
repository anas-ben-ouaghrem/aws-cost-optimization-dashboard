package com.vermeg.aws.cost.optimization.dashboard.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.redshift.model.BucketNotFoundException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.BucketReplicationConfiguration;
import com.amazonaws.services.s3.model.GetBucketReplicationConfigurationRequest;
import com.amazonaws.services.s3.model.Owner;
import com.amazonaws.services.s3.model.RestoreStatus;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.vermeg.aws.cost.optimization.dashboard.entities.AwsAccountCredentials;
import com.vermeg.aws.cost.optimization.dashboard.entities.S3ObjectOptimizationSuggestion;
import com.vermeg.aws.cost.optimization.dashboard.entities.S3OptimizationSuggestion;
import com.vermeg.aws.cost.optimization.dashboard.entities.S3SuggestionCategory;
import com.vermeg.aws.cost.optimization.dashboard.entities.StorageBucket;
import com.vermeg.aws.cost.optimization.dashboard.entities.SuggestionStatus;
import com.vermeg.aws.cost.optimization.dashboard.repositories.S3ObjectOptimizationSuggestionRepository;
import com.vermeg.aws.cost.optimization.dashboard.repositories.S3OptimizationSuggestionRepository;
import com.vermeg.aws.cost.optimization.dashboard.repositories.StorageBucketRepository;

import java.net.InetAddress;
import java.net.UnknownHostException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {S3SuggestionsService.class})
@ExtendWith(SpringExtension.class)
class S3SuggestionsServiceTest {
    @MockBean
    private AwsCredentialsService awsCredentialsService;

    @MockBean
    private S3ObjectOptimizationSuggestionRepository s3ObjectOptimizationSuggestionRepository;

    @MockBean
    private S3OptimizationSuggestionRepository s3OptimizationSuggestionRepository;

    @MockBean
    private S3Service s3Service;

    @Autowired
    private S3SuggestionsService s3SuggestionsService;

    @MockBean
    private StorageBucketRepository storageBucketRepository;

    /**
     * Method under test: {@link S3SuggestionsService#generateS3Suggestions()}
     */
    @Test
    void testGenerateS3Suggestions() {
        ArrayList<S3OptimizationSuggestion> s3OptimizationSuggestionList = new ArrayList<>();
        when(s3OptimizationSuggestionRepository.saveAll(Mockito.<Iterable<S3OptimizationSuggestion>>any()))
                .thenReturn(s3OptimizationSuggestionList);
        when(storageBucketRepository.findAll()).thenReturn(new ArrayList<>());
        List<S3OptimizationSuggestion> actualGenerateS3SuggestionsResult = s3SuggestionsService.generateS3Suggestions();
        assertSame(s3OptimizationSuggestionList, actualGenerateS3SuggestionsResult);
        assertTrue(actualGenerateS3SuggestionsResult.isEmpty());
        verify(s3OptimizationSuggestionRepository).saveAll(Mockito.<Iterable<S3OptimizationSuggestion>>any());
        verify(storageBucketRepository).findAll();
    }

    /**
     * Method under test: {@link S3SuggestionsService#generateS3Suggestions()}
     */
    @Test
    void testGenerateS3Suggestions2() {
        when(storageBucketRepository.findAll()).thenThrow(new BucketNotFoundException("An error occurred"));
        assertThrows(BucketNotFoundException.class, () -> s3SuggestionsService.generateS3Suggestions());
        verify(storageBucketRepository).findAll();
    }

    /**
     * Method under test: {@link S3SuggestionsService#getAllS3Suggestions()}
     */
    @Test
    void testGetAllS3Suggestions() {
        ArrayList<S3OptimizationSuggestion> s3OptimizationSuggestionList = new ArrayList<>();
        when(s3OptimizationSuggestionRepository.findAll()).thenReturn(s3OptimizationSuggestionList);
        List<S3OptimizationSuggestion> actualAllS3Suggestions = s3SuggestionsService.getAllS3Suggestions();
        assertSame(s3OptimizationSuggestionList, actualAllS3Suggestions);
        assertTrue(actualAllS3Suggestions.isEmpty());
        verify(s3OptimizationSuggestionRepository).findAll();
    }

    /**
     * Method under test: {@link S3SuggestionsService#getAllS3Suggestions()}
     */
    @Test
    void testGetAllS3Suggestions2() {
        when(s3OptimizationSuggestionRepository.findAll()).thenThrow(new BucketNotFoundException("An error occurred"));
        assertThrows(BucketNotFoundException.class, () -> s3SuggestionsService.getAllS3Suggestions());
        verify(s3OptimizationSuggestionRepository).findAll();
    }

    /**
     * Method under test: {@link S3SuggestionsService#getS3SuggestionById(Long)}
     */
    @Test
    void testGetS3SuggestionById() {
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

        StorageBucket linkedBucket = new StorageBucket();
        linkedBucket.setAssociatedAccount(associatedAccount2);
        linkedBucket
                .setCreationDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        linkedBucket.setCreationTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedBucket.setId(1L);
        linkedBucket.setLastUpdateTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedBucket.setMonitoringEnabled(true);
        linkedBucket.setName("bucket-name");
        linkedBucket.setObjectCount(3L);
        linkedBucket.setOwner("Owner");
        linkedBucket.setOwnerEmail("jane.doe@example.org");
        linkedBucket.setPurpose("Purpose");
        linkedBucket.setRegion("us-east-2");
        linkedBucket.setSize(3L);
        linkedBucket.setSuggestions(new HashSet<>());

        S3OptimizationSuggestion s3OptimizationSuggestion = new S3OptimizationSuggestion();
        s3OptimizationSuggestion.setAssociatedAccount(associatedAccount);
        s3OptimizationSuggestion.setCategory(S3SuggestionCategory.NOLIFECYCLEPOLICY);
        s3OptimizationSuggestion.setCreatedDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        s3OptimizationSuggestion.setDescription("The characteristics of someone or something");
        s3OptimizationSuggestion.setId(1L);
        s3OptimizationSuggestion.setLinkedBucket(linkedBucket);
        s3OptimizationSuggestion.setRecommendation("Recommendation");
        s3OptimizationSuggestion.setStatus(SuggestionStatus.Pending);
        s3OptimizationSuggestion.setTitle("Dr");
        Optional<S3OptimizationSuggestion> ofResult = Optional.of(s3OptimizationSuggestion);
        when(s3OptimizationSuggestionRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertSame(s3OptimizationSuggestion, s3SuggestionsService.getS3SuggestionById(1L));
        verify(s3OptimizationSuggestionRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link S3SuggestionsService#updateS3SuggestionStatus(SuggestionStatus, Long)}
     */
    @Test
    void testUpdateS3SuggestionStatus() {
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

        StorageBucket linkedBucket = new StorageBucket();
        linkedBucket.setAssociatedAccount(associatedAccount2);
        linkedBucket
                .setCreationDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        linkedBucket.setCreationTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedBucket.setId(1L);
        linkedBucket.setLastUpdateTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedBucket.setMonitoringEnabled(true);
        linkedBucket.setName("bucket-name");
        linkedBucket.setObjectCount(3L);
        linkedBucket.setOwner("Owner");
        linkedBucket.setOwnerEmail("jane.doe@example.org");
        linkedBucket.setPurpose("Purpose");
        linkedBucket.setRegion("us-east-2");
        linkedBucket.setSize(3L);
        linkedBucket.setSuggestions(new HashSet<>());

        S3OptimizationSuggestion s3OptimizationSuggestion = new S3OptimizationSuggestion();
        s3OptimizationSuggestion.setAssociatedAccount(associatedAccount);
        s3OptimizationSuggestion.setCategory(S3SuggestionCategory.NOLIFECYCLEPOLICY);
        s3OptimizationSuggestion.setCreatedDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        s3OptimizationSuggestion.setDescription("The characteristics of someone or something");
        s3OptimizationSuggestion.setId(1L);
        s3OptimizationSuggestion.setLinkedBucket(linkedBucket);
        s3OptimizationSuggestion.setRecommendation("Recommendation");
        s3OptimizationSuggestion.setStatus(SuggestionStatus.Pending);
        s3OptimizationSuggestion.setTitle("Dr");
        Optional<S3OptimizationSuggestion> ofResult = Optional.of(s3OptimizationSuggestion);

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

        StorageBucket linkedBucket2 = new StorageBucket();
        linkedBucket2.setAssociatedAccount(associatedAccount4);
        linkedBucket2
                .setCreationDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        linkedBucket2.setCreationTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedBucket2.setId(1L);
        linkedBucket2.setLastUpdateTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedBucket2.setMonitoringEnabled(true);
        linkedBucket2.setName("bucket-name");
        linkedBucket2.setObjectCount(3L);
        linkedBucket2.setOwner("Owner");
        linkedBucket2.setOwnerEmail("jane.doe@example.org");
        linkedBucket2.setPurpose("Purpose");
        linkedBucket2.setRegion("us-east-2");
        linkedBucket2.setSize(3L);
        linkedBucket2.setSuggestions(new HashSet<>());

        S3OptimizationSuggestion s3OptimizationSuggestion2 = new S3OptimizationSuggestion();
        s3OptimizationSuggestion2.setAssociatedAccount(associatedAccount3);
        s3OptimizationSuggestion2.setCategory(S3SuggestionCategory.NOLIFECYCLEPOLICY);
        s3OptimizationSuggestion2.setCreatedDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        s3OptimizationSuggestion2.setDescription("The characteristics of someone or something");
        s3OptimizationSuggestion2.setId(1L);
        s3OptimizationSuggestion2.setLinkedBucket(linkedBucket2);
        s3OptimizationSuggestion2.setRecommendation("Recommendation");
        s3OptimizationSuggestion2.setStatus(SuggestionStatus.Pending);
        s3OptimizationSuggestion2.setTitle("Dr");
        when(s3OptimizationSuggestionRepository.save(Mockito.<S3OptimizationSuggestion>any()))
                .thenReturn(s3OptimizationSuggestion2);
        when(s3OptimizationSuggestionRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertSame(s3OptimizationSuggestion2,
                s3SuggestionsService.updateS3SuggestionStatus(SuggestionStatus.Pending, 1L));
        verify(s3OptimizationSuggestionRepository).save(Mockito.<S3OptimizationSuggestion>any());
        verify(s3OptimizationSuggestionRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link S3SuggestionsService#updateS3SuggestionStatus(SuggestionStatus, Long)}
     */
    @Test
    void testUpdateS3SuggestionStatus2() {
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

        StorageBucket linkedBucket = new StorageBucket();
        linkedBucket.setAssociatedAccount(associatedAccount2);
        linkedBucket
                .setCreationDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        linkedBucket.setCreationTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedBucket.setId(1L);
        linkedBucket.setLastUpdateTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedBucket.setMonitoringEnabled(true);
        linkedBucket.setName("bucket-name");
        linkedBucket.setObjectCount(3L);
        linkedBucket.setOwner("Owner");
        linkedBucket.setOwnerEmail("jane.doe@example.org");
        linkedBucket.setPurpose("Purpose");
        linkedBucket.setRegion("us-east-2");
        linkedBucket.setSize(3L);
        linkedBucket.setSuggestions(new HashSet<>());

        S3OptimizationSuggestion s3OptimizationSuggestion = new S3OptimizationSuggestion();
        s3OptimizationSuggestion.setAssociatedAccount(associatedAccount);
        s3OptimizationSuggestion.setCategory(S3SuggestionCategory.NOLIFECYCLEPOLICY);
        s3OptimizationSuggestion.setCreatedDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        s3OptimizationSuggestion.setDescription("The characteristics of someone or something");
        s3OptimizationSuggestion.setId(1L);
        s3OptimizationSuggestion.setLinkedBucket(linkedBucket);
        s3OptimizationSuggestion.setRecommendation("Recommendation");
        s3OptimizationSuggestion.setStatus(SuggestionStatus.Pending);
        s3OptimizationSuggestion.setTitle("Dr");
        Optional<S3OptimizationSuggestion> ofResult = Optional.of(s3OptimizationSuggestion);
        when(s3OptimizationSuggestionRepository.save(Mockito.<S3OptimizationSuggestion>any()))
                .thenThrow(new BucketNotFoundException("An error occurred"));
        when(s3OptimizationSuggestionRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertThrows(BucketNotFoundException.class,
                () -> s3SuggestionsService.updateS3SuggestionStatus(SuggestionStatus.Pending, 1L));
        verify(s3OptimizationSuggestionRepository).save(Mockito.<S3OptimizationSuggestion>any());
        verify(s3OptimizationSuggestionRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link S3SuggestionsService#deleteS3Suggestion(Long)}
     */
    @Test
    void testDeleteS3Suggestion() {
        doNothing().when(s3OptimizationSuggestionRepository).deleteById(Mockito.<Long>any());
        s3SuggestionsService.deleteS3Suggestion(1L);
        verify(s3OptimizationSuggestionRepository).deleteById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link S3SuggestionsService#deleteS3Suggestion(Long)}
     */
    @Test
    void testDeleteS3Suggestion2() {
        doThrow(new BucketNotFoundException("An error occurred")).when(s3OptimizationSuggestionRepository)
                .deleteById(Mockito.<Long>any());
        assertThrows(BucketNotFoundException.class, () -> s3SuggestionsService.deleteS3Suggestion(1L));
        verify(s3OptimizationSuggestionRepository).deleteById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link S3SuggestionsService#deleteDealtWithSuggestions()}
     */
    @Test
    void testDeleteDealtWithSuggestions() {
        when(s3OptimizationSuggestionRepository.findAll()).thenReturn(new ArrayList<>());
        s3SuggestionsService.deleteDealtWithSuggestions();
        verify(s3OptimizationSuggestionRepository).findAll();
    }

    /**
     * Method under test: {@link S3SuggestionsService#deleteDealtWithSuggestions()}
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

        StorageBucket linkedBucket = new StorageBucket();
        linkedBucket.setAssociatedAccount(associatedAccount2);
        linkedBucket
                .setCreationDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        linkedBucket.setCreationTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedBucket.setId(1L);
        linkedBucket.setLastUpdateTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedBucket.setMonitoringEnabled(true);
        linkedBucket.setName("bucket-name");
        linkedBucket.setObjectCount(3L);
        linkedBucket.setOwner("Owner");
        linkedBucket.setOwnerEmail("jane.doe@example.org");
        linkedBucket.setPurpose("Purpose");
        linkedBucket.setRegion("us-east-2");
        linkedBucket.setSize(3L);
        linkedBucket.setSuggestions(new HashSet<>());

        S3OptimizationSuggestion s3OptimizationSuggestion = new S3OptimizationSuggestion();
        s3OptimizationSuggestion.setAssociatedAccount(associatedAccount);
        s3OptimizationSuggestion.setCategory(S3SuggestionCategory.NOLIFECYCLEPOLICY);
        s3OptimizationSuggestion.setCreatedDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        s3OptimizationSuggestion.setDescription("The characteristics of someone or something");
        s3OptimizationSuggestion.setId(1L);
        s3OptimizationSuggestion.setLinkedBucket(linkedBucket);
        s3OptimizationSuggestion.setRecommendation("Recommendation");
        s3OptimizationSuggestion.setStatus(SuggestionStatus.Pending);
        s3OptimizationSuggestion.setTitle("Dr");

        ArrayList<S3OptimizationSuggestion> s3OptimizationSuggestionList = new ArrayList<>();
        s3OptimizationSuggestionList.add(s3OptimizationSuggestion);
        when(s3OptimizationSuggestionRepository.findAll()).thenReturn(s3OptimizationSuggestionList);
        s3SuggestionsService.deleteDealtWithSuggestions();
        verify(s3OptimizationSuggestionRepository).findAll();
    }

    /**
     * Method under test: {@link S3SuggestionsService#deleteDealtWithSuggestions()}
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

        StorageBucket linkedBucket = new StorageBucket();
        linkedBucket.setAssociatedAccount(associatedAccount2);
        linkedBucket
                .setCreationDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        linkedBucket.setCreationTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedBucket.setId(1L);
        linkedBucket.setLastUpdateTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedBucket.setMonitoringEnabled(true);
        linkedBucket.setName("bucket-name");
        linkedBucket.setObjectCount(3L);
        linkedBucket.setOwner("Owner");
        linkedBucket.setOwnerEmail("jane.doe@example.org");
        linkedBucket.setPurpose("Purpose");
        linkedBucket.setRegion("us-east-2");
        linkedBucket.setSize(3L);
        linkedBucket.setSuggestions(new HashSet<>());

        S3OptimizationSuggestion s3OptimizationSuggestion = new S3OptimizationSuggestion();
        s3OptimizationSuggestion.setAssociatedAccount(associatedAccount);
        s3OptimizationSuggestion.setCategory(S3SuggestionCategory.NOLIFECYCLEPOLICY);
        s3OptimizationSuggestion.setCreatedDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        s3OptimizationSuggestion.setDescription("The characteristics of someone or something");
        s3OptimizationSuggestion.setId(1L);
        s3OptimizationSuggestion.setLinkedBucket(linkedBucket);
        s3OptimizationSuggestion.setRecommendation("Recommendation");
        s3OptimizationSuggestion.setStatus(SuggestionStatus.Pending);
        s3OptimizationSuggestion.setTitle("Dr");

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

        StorageBucket linkedBucket2 = new StorageBucket();
        linkedBucket2.setAssociatedAccount(associatedAccount4);
        linkedBucket2
                .setCreationDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        linkedBucket2.setCreationTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedBucket2.setId(2L);
        linkedBucket2.setLastUpdateTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedBucket2.setMonitoringEnabled(false);
        linkedBucket2.setName("Name");
        linkedBucket2.setObjectCount(1L);
        linkedBucket2.setOwner("com.vermeg.aws.cost.optimization.dashboard.entities.StorageBucket");
        linkedBucket2.setOwnerEmail("john.smith@example.org");
        linkedBucket2.setPurpose("com.vermeg.aws.cost.optimization.dashboard.entities.StorageBucket");
        linkedBucket2.setRegion("Region");
        linkedBucket2.setSize(1L);
        linkedBucket2.setSuggestions(new HashSet<>());

        S3OptimizationSuggestion s3OptimizationSuggestion2 = new S3OptimizationSuggestion();
        s3OptimizationSuggestion2.setAssociatedAccount(associatedAccount3);
        s3OptimizationSuggestion2.setCategory(S3SuggestionCategory.LEL);
        s3OptimizationSuggestion2.setCreatedDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        s3OptimizationSuggestion2.setDescription("Description");
        s3OptimizationSuggestion2.setId(2L);
        s3OptimizationSuggestion2.setLinkedBucket(linkedBucket2);
        s3OptimizationSuggestion2
                .setRecommendation("com.vermeg.aws.cost.optimization.dashboard.entities.S3OptimizationSuggestion");
        s3OptimizationSuggestion2.setStatus(SuggestionStatus.Ignored);
        s3OptimizationSuggestion2.setTitle("Mr");

        ArrayList<S3OptimizationSuggestion> s3OptimizationSuggestionList = new ArrayList<>();
        s3OptimizationSuggestionList.add(s3OptimizationSuggestion2);
        s3OptimizationSuggestionList.add(s3OptimizationSuggestion);
        when(s3OptimizationSuggestionRepository.findAll()).thenReturn(s3OptimizationSuggestionList);
        s3SuggestionsService.deleteDealtWithSuggestions();
        verify(s3OptimizationSuggestionRepository).findAll();
    }

    /**
     * Method under test: {@link S3SuggestionsService#deleteDealtWithSuggestions()}
     */
    @Test
    void testDeleteDealtWithSuggestions4() {
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

        StorageBucket linkedBucket = new StorageBucket();
        linkedBucket.setAssociatedAccount(associatedAccount2);
        linkedBucket
                .setCreationDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        linkedBucket.setCreationTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedBucket.setId(1L);
        linkedBucket.setLastUpdateTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedBucket.setMonitoringEnabled(true);
        linkedBucket.setName("bucket-name");
        linkedBucket.setObjectCount(3L);
        linkedBucket.setOwner("Owner");
        linkedBucket.setOwnerEmail("jane.doe@example.org");
        linkedBucket.setPurpose("Purpose");
        linkedBucket.setRegion("us-east-2");
        linkedBucket.setSize(3L);
        linkedBucket.setSuggestions(new HashSet<>());

        S3OptimizationSuggestion s3OptimizationSuggestion = new S3OptimizationSuggestion();
        s3OptimizationSuggestion.setAssociatedAccount(associatedAccount);
        s3OptimizationSuggestion.setCategory(S3SuggestionCategory.NOLIFECYCLEPOLICY);
        s3OptimizationSuggestion.setCreatedDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        s3OptimizationSuggestion.setDescription("The characteristics of someone or something");
        s3OptimizationSuggestion.setId(1L);
        s3OptimizationSuggestion.setLinkedBucket(linkedBucket);
        s3OptimizationSuggestion.setRecommendation("Recommendation");
        s3OptimizationSuggestion.setStatus(SuggestionStatus.Pending);
        s3OptimizationSuggestion.setTitle("Dr");

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

        StorageBucket linkedBucket2 = new StorageBucket();
        linkedBucket2.setAssociatedAccount(associatedAccount4);
        linkedBucket2
                .setCreationDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        linkedBucket2.setCreationTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedBucket2.setId(2L);
        linkedBucket2.setLastUpdateTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedBucket2.setMonitoringEnabled(false);
        linkedBucket2.setName("Name");
        linkedBucket2.setObjectCount(1L);
        linkedBucket2.setOwner("com.vermeg.aws.cost.optimization.dashboard.entities.StorageBucket");
        linkedBucket2.setOwnerEmail("john.smith@example.org");
        linkedBucket2.setPurpose("com.vermeg.aws.cost.optimization.dashboard.entities.StorageBucket");
        linkedBucket2.setRegion("Region");
        linkedBucket2.setSize(1L);
        linkedBucket2.setSuggestions(new HashSet<>());
        S3OptimizationSuggestion s3OptimizationSuggestion2 = mock(S3OptimizationSuggestion.class);
        when(s3OptimizationSuggestion2.getStatus()).thenReturn(SuggestionStatus.Pending);
        doNothing().when(s3OptimizationSuggestion2).setAssociatedAccount(Mockito.<AwsAccountCredentials>any());
        doNothing().when(s3OptimizationSuggestion2).setCategory(Mockito.<S3SuggestionCategory>any());
        doNothing().when(s3OptimizationSuggestion2).setCreatedDate(Mockito.<LocalDateTime>any());
        doNothing().when(s3OptimizationSuggestion2).setDescription(Mockito.<String>any());
        doNothing().when(s3OptimizationSuggestion2).setId(Mockito.<Long>any());
        doNothing().when(s3OptimizationSuggestion2).setLinkedBucket(Mockito.<StorageBucket>any());
        doNothing().when(s3OptimizationSuggestion2).setRecommendation(Mockito.<String>any());
        doNothing().when(s3OptimizationSuggestion2).setStatus(Mockito.<SuggestionStatus>any());
        doNothing().when(s3OptimizationSuggestion2).setTitle(Mockito.<String>any());
        s3OptimizationSuggestion2.setAssociatedAccount(associatedAccount3);
        s3OptimizationSuggestion2.setCategory(S3SuggestionCategory.LEL);
        s3OptimizationSuggestion2.setCreatedDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        s3OptimizationSuggestion2.setDescription("Description");
        s3OptimizationSuggestion2.setId(2L);
        s3OptimizationSuggestion2.setLinkedBucket(linkedBucket2);
        s3OptimizationSuggestion2
                .setRecommendation("com.vermeg.aws.cost.optimization.dashboard.entities.S3OptimizationSuggestion");
        s3OptimizationSuggestion2.setStatus(SuggestionStatus.Ignored);
        s3OptimizationSuggestion2.setTitle("Mr");

        ArrayList<S3OptimizationSuggestion> s3OptimizationSuggestionList = new ArrayList<>();
        s3OptimizationSuggestionList.add(s3OptimizationSuggestion2);
        s3OptimizationSuggestionList.add(s3OptimizationSuggestion);
        when(s3OptimizationSuggestionRepository.findAll()).thenReturn(s3OptimizationSuggestionList);
        s3SuggestionsService.deleteDealtWithSuggestions();
        verify(s3OptimizationSuggestionRepository).findAll();
        verify(s3OptimizationSuggestion2).getStatus();
        verify(s3OptimizationSuggestion2).setAssociatedAccount(Mockito.<AwsAccountCredentials>any());
        verify(s3OptimizationSuggestion2).setCategory(Mockito.<S3SuggestionCategory>any());
        verify(s3OptimizationSuggestion2).setCreatedDate(Mockito.<LocalDateTime>any());
        verify(s3OptimizationSuggestion2).setDescription(Mockito.<String>any());
        verify(s3OptimizationSuggestion2).setId(Mockito.<Long>any());
        verify(s3OptimizationSuggestion2).setLinkedBucket(Mockito.<StorageBucket>any());
        verify(s3OptimizationSuggestion2).setRecommendation(Mockito.<String>any());
        verify(s3OptimizationSuggestion2).setStatus(Mockito.<SuggestionStatus>any());
        verify(s3OptimizationSuggestion2).setTitle(Mockito.<String>any());
    }

    /**
     * Method under test: {@link S3SuggestionsService#deleteDealtWithSuggestions()}
     */
    @Test
    void testDeleteDealtWithSuggestions5() {
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

        StorageBucket linkedBucket = new StorageBucket();
        linkedBucket.setAssociatedAccount(associatedAccount2);
        linkedBucket
                .setCreationDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        linkedBucket.setCreationTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedBucket.setId(1L);
        linkedBucket.setLastUpdateTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedBucket.setMonitoringEnabled(true);
        linkedBucket.setName("bucket-name");
        linkedBucket.setObjectCount(3L);
        linkedBucket.setOwner("Owner");
        linkedBucket.setOwnerEmail("jane.doe@example.org");
        linkedBucket.setPurpose("Purpose");
        linkedBucket.setRegion("us-east-2");
        linkedBucket.setSize(3L);
        linkedBucket.setSuggestions(new HashSet<>());

        S3OptimizationSuggestion s3OptimizationSuggestion = new S3OptimizationSuggestion();
        s3OptimizationSuggestion.setAssociatedAccount(associatedAccount);
        s3OptimizationSuggestion.setCategory(S3SuggestionCategory.NOLIFECYCLEPOLICY);
        s3OptimizationSuggestion.setCreatedDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        s3OptimizationSuggestion.setDescription("The characteristics of someone or something");
        s3OptimizationSuggestion.setId(1L);
        s3OptimizationSuggestion.setLinkedBucket(linkedBucket);
        s3OptimizationSuggestion.setRecommendation("Recommendation");
        s3OptimizationSuggestion.setStatus(SuggestionStatus.Pending);
        s3OptimizationSuggestion.setTitle("Dr");

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

        StorageBucket linkedBucket2 = new StorageBucket();
        linkedBucket2.setAssociatedAccount(associatedAccount4);
        linkedBucket2
                .setCreationDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        linkedBucket2.setCreationTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedBucket2.setId(2L);
        linkedBucket2.setLastUpdateTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        linkedBucket2.setMonitoringEnabled(false);
        linkedBucket2.setName("Name");
        linkedBucket2.setObjectCount(1L);
        linkedBucket2.setOwner("com.vermeg.aws.cost.optimization.dashboard.entities.StorageBucket");
        linkedBucket2.setOwnerEmail("john.smith@example.org");
        linkedBucket2.setPurpose("com.vermeg.aws.cost.optimization.dashboard.entities.StorageBucket");
        linkedBucket2.setRegion("Region");
        linkedBucket2.setSize(1L);
        linkedBucket2.setSuggestions(new HashSet<>());
        S3OptimizationSuggestion s3OptimizationSuggestion2 = mock(S3OptimizationSuggestion.class);
        when(s3OptimizationSuggestion2.getStatus()).thenReturn(SuggestionStatus.Ignored);
        when(s3OptimizationSuggestion2.getCreatedDate()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        doNothing().when(s3OptimizationSuggestion2).setAssociatedAccount(Mockito.<AwsAccountCredentials>any());
        doNothing().when(s3OptimizationSuggestion2).setCategory(Mockito.<S3SuggestionCategory>any());
        doNothing().when(s3OptimizationSuggestion2).setCreatedDate(Mockito.<LocalDateTime>any());
        doNothing().when(s3OptimizationSuggestion2).setDescription(Mockito.<String>any());
        doNothing().when(s3OptimizationSuggestion2).setId(Mockito.<Long>any());
        doNothing().when(s3OptimizationSuggestion2).setLinkedBucket(Mockito.<StorageBucket>any());
        doNothing().when(s3OptimizationSuggestion2).setRecommendation(Mockito.<String>any());
        doNothing().when(s3OptimizationSuggestion2).setStatus(Mockito.<SuggestionStatus>any());
        doNothing().when(s3OptimizationSuggestion2).setTitle(Mockito.<String>any());
        s3OptimizationSuggestion2.setAssociatedAccount(associatedAccount3);
        s3OptimizationSuggestion2.setCategory(S3SuggestionCategory.LEL);
        s3OptimizationSuggestion2.setCreatedDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        s3OptimizationSuggestion2.setDescription("Description");
        s3OptimizationSuggestion2.setId(2L);
        s3OptimizationSuggestion2.setLinkedBucket(linkedBucket2);
        s3OptimizationSuggestion2
                .setRecommendation("com.vermeg.aws.cost.optimization.dashboard.entities.S3OptimizationSuggestion");
        s3OptimizationSuggestion2.setStatus(SuggestionStatus.Ignored);
        s3OptimizationSuggestion2.setTitle("Mr");

        ArrayList<S3OptimizationSuggestion> s3OptimizationSuggestionList = new ArrayList<>();
        s3OptimizationSuggestionList.add(s3OptimizationSuggestion2);
        s3OptimizationSuggestionList.add(s3OptimizationSuggestion);
        when(s3OptimizationSuggestionRepository.findAll()).thenReturn(s3OptimizationSuggestionList);
        s3SuggestionsService.deleteDealtWithSuggestions();
        verify(s3OptimizationSuggestionRepository).findAll();
        verify(s3OptimizationSuggestion2).getStatus();
        verify(s3OptimizationSuggestion2).getCreatedDate();
        verify(s3OptimizationSuggestion2).setAssociatedAccount(Mockito.<AwsAccountCredentials>any());
        verify(s3OptimizationSuggestion2).setCategory(Mockito.<S3SuggestionCategory>any());
        verify(s3OptimizationSuggestion2).setCreatedDate(Mockito.<LocalDateTime>any());
        verify(s3OptimizationSuggestion2).setDescription(Mockito.<String>any());
        verify(s3OptimizationSuggestion2).setId(Mockito.<Long>any());
        verify(s3OptimizationSuggestion2).setLinkedBucket(Mockito.<StorageBucket>any());
        verify(s3OptimizationSuggestion2).setRecommendation(Mockito.<String>any());
        verify(s3OptimizationSuggestion2).setStatus(Mockito.<SuggestionStatus>any());
        verify(s3OptimizationSuggestion2).setTitle(Mockito.<String>any());
    }

    /**
     * Method under test: {@link S3SuggestionsService#hasLifecyclePolicy(String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testHasLifecyclePolicy() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            // TODO: Complete this test.
            //   Reason: R013 No inputs found that don't throw a trivial exception.
            //   Diffblue Cover tried to run the arrange/act section, but the method under
            //   test threw
            //   java.lang.IllegalArgumentException: connect: invalid address type
            //       at java.base/java.net.Socket.checkAddress(Socket.java:691)
            //       at java.base/java.net.Socket.connect(Socket.java:621)
            //       at org.apache.http.conn.ssl.SSLConnectionSocketFactory.connectSocket(SSLConnectionSocketFactory.java:368)
            //       at com.amazonaws.http.conn.ssl.SdkTLSSocketFactory.connectSocket(SdkTLSSocketFactory.java:142)
            //       at org.apache.http.impl.conn.DefaultHttpClientConnectionOperator.connect(DefaultHttpClientConnectionOperator.java:142)
            //       at org.apache.http.impl.conn.PoolingHttpClientConnectionManager.connect(PoolingHttpClientConnectionManager.java:376)
            //       at com.amazonaws.http.conn.ClientConnectionManagerFactory$Handler.invoke(ClientConnectionManagerFactory.java:76)
            //       at com.amazonaws.http.conn.$Proxy115.connect(Unknown Source)
            //       at org.apache.http.impl.execchain.MainClientExec.establishRoute(MainClientExec.java:393)
            //       at org.apache.http.impl.execchain.MainClientExec.execute(MainClientExec.java:236)
            //       at org.apache.http.impl.execchain.ProtocolExec.execute(ProtocolExec.java:186)
            //       at org.apache.http.impl.client.InternalHttpClient.doExecute(InternalHttpClient.java:185)
            //       at org.apache.http.impl.client.CloseableHttpClient.execute(CloseableHttpClient.java:83)
            //       at org.apache.http.impl.client.CloseableHttpClient.execute(CloseableHttpClient.java:56)
            //       at com.amazonaws.http.apache.client.impl.SdkHttpClient.execute(SdkHttpClient.java:72)
            //       at com.amazonaws.http.AmazonHttpClient$RequestExecutor.executeOneRequest(AmazonHttpClient.java:1346)
            //       at com.amazonaws.http.AmazonHttpClient$RequestExecutor.executeHelper(AmazonHttpClient.java:1157)
            //       at com.amazonaws.http.AmazonHttpClient$RequestExecutor.doExecute(AmazonHttpClient.java:814)
            //       at com.amazonaws.http.AmazonHttpClient$RequestExecutor.executeWithTimer(AmazonHttpClient.java:781)
            //       at com.amazonaws.http.AmazonHttpClient$RequestExecutor.execute(AmazonHttpClient.java:755)
            //       at com.amazonaws.http.AmazonHttpClient$RequestExecutor.access$500(AmazonHttpClient.java:715)
            //       at com.amazonaws.http.AmazonHttpClient$RequestExecutionBuilderImpl.execute(AmazonHttpClient.java:697)
            //       at com.amazonaws.http.AmazonHttpClient.execute(AmazonHttpClient.java:561)
            //       at com.amazonaws.http.AmazonHttpClient.execute(AmazonHttpClient.java:541)
            //       at com.amazonaws.services.s3.AmazonS3Client.invoke(AmazonS3Client.java:5520)
            //       at com.amazonaws.services.s3.AmazonS3Client.getBucketRegionViaHeadRequest(AmazonS3Client.java:6501)
            //       at com.amazonaws.services.s3.AmazonS3Client.fetchRegionFromCache(AmazonS3Client.java:6473)
            //       at com.amazonaws.services.s3.AmazonS3Client.invoke(AmazonS3Client.java:5505)
            //       at com.amazonaws.services.s3.AmazonS3Client.invoke(AmazonS3Client.java:5467)
            //       at com.amazonaws.services.s3.AmazonS3Client.invoke(AmazonS3Client.java:5461)
            //       at com.amazonaws.services.s3.AmazonS3Client.getBucketLifecycleConfiguration(AmazonS3Client.java:2528)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.S3SuggestionsService.hasLifecyclePolicy(S3SuggestionsService.java:105)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});

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
            Optional<StorageBucket> ofResult = Optional.of(storageBucket);
            when(storageBucketRepository.findByName(Mockito.<String>any())).thenReturn(ofResult);

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
            s3SuggestionsService.hasLifecyclePolicy("s3://bucket-name/object-key");
        }
    }

    /**
     * Method under test: {@link S3SuggestionsService#hasLifecyclePolicy(String)}
     */
    @Test
    void testHasLifecyclePolicy2() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});

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
            StorageBucket storageBucket = mock(StorageBucket.class);
            when(storageBucket.getRegion()).thenThrow(new BucketNotFoundException("An error occurred"));
            when(storageBucket.getAssociatedAccount()).thenReturn(awsAccountCredentials);
            doNothing().when(storageBucket).setAssociatedAccount(Mockito.<AwsAccountCredentials>any());
            doNothing().when(storageBucket).setCreationDate(Mockito.<Date>any());
            doNothing().when(storageBucket).setCreationTimestamp(Mockito.<LocalDateTime>any());
            doNothing().when(storageBucket).setId(Mockito.<Long>any());
            doNothing().when(storageBucket).setLastUpdateTimestamp(Mockito.<LocalDateTime>any());
            doNothing().when(storageBucket).setMonitoringEnabled(anyBoolean());
            doNothing().when(storageBucket).setName(Mockito.<String>any());
            doNothing().when(storageBucket).setObjectCount(anyLong());
            doNothing().when(storageBucket).setOwner(Mockito.<String>any());
            doNothing().when(storageBucket).setOwnerEmail(Mockito.<String>any());
            doNothing().when(storageBucket).setPurpose(Mockito.<String>any());
            doNothing().when(storageBucket).setRegion(Mockito.<String>any());
            doNothing().when(storageBucket).setSize(anyLong());
            doNothing().when(storageBucket).setSuggestions(Mockito.<Set<S3OptimizationSuggestion>>any());
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
            Optional<StorageBucket> ofResult = Optional.of(storageBucket);
            when(storageBucketRepository.findByName(Mockito.<String>any())).thenReturn(ofResult);

            AwsAccountCredentials awsAccountCredentials2 = new AwsAccountCredentials();
            awsAccountCredentials2.setAccessKeyId("EXAMPLEakiAIOSFODNN7");
            awsAccountCredentials2.setAccountId("42");
            awsAccountCredentials2.setAccountName("Dr Jane Doe");
            awsAccountCredentials2.setDate(LocalDate.of(1970, 1, 1).atStartOfDay());
            awsAccountCredentials2.setEbsOptimizationSuggestions(new ArrayList<>());
            awsAccountCredentials2.setEbsVolumes(new ArrayList<>());
            awsAccountCredentials2.setEc2Instances(new ArrayList<>());
            awsAccountCredentials2.setEc2OptimizationSuggestions(new ArrayList<>());
            awsAccountCredentials2.setEipAddresses(new ArrayList<>());
            awsAccountCredentials2.setEipOptimizationSuggestions(new ArrayList<>());
            awsAccountCredentials2.setId(1L);
            awsAccountCredentials2.setRdsInstances(new ArrayList<>());
            awsAccountCredentials2.setRdsOptimizationSuggestions(new ArrayList<>());
            awsAccountCredentials2.setS3OptimizationSuggestions(new ArrayList<>());
            awsAccountCredentials2.setSecretAccessKey("EXAMPLEakiAIOSFODNN7");
            awsAccountCredentials2.setStorageBuckets(new ArrayList<>());
            when(awsCredentialsService.getCredentialsByAccountId(Mockito.<String>any())).thenReturn(awsAccountCredentials2);
            assertThrows(BucketNotFoundException.class,
                    () -> s3SuggestionsService.hasLifecyclePolicy("s3://bucket-name/object-key"));
            verify(storageBucketRepository).findByName(Mockito.<String>any());
            verify(storageBucket).getAssociatedAccount();
            verify(storageBucket).getRegion();
            verify(storageBucket).setAssociatedAccount(Mockito.<AwsAccountCredentials>any());
            verify(storageBucket).setCreationDate(Mockito.<Date>any());
            verify(storageBucket).setCreationTimestamp(Mockito.<LocalDateTime>any());
            verify(storageBucket).setId(Mockito.<Long>any());
            verify(storageBucket).setLastUpdateTimestamp(Mockito.<LocalDateTime>any());
            verify(storageBucket).setMonitoringEnabled(anyBoolean());
            verify(storageBucket).setName(Mockito.<String>any());
            verify(storageBucket).setObjectCount(anyLong());
            verify(storageBucket).setOwner(Mockito.<String>any());
            verify(storageBucket).setOwnerEmail(Mockito.<String>any());
            verify(storageBucket).setPurpose(Mockito.<String>any());
            verify(storageBucket).setRegion(Mockito.<String>any());
            verify(storageBucket).setSize(anyLong());
            verify(storageBucket).setSuggestions(Mockito.<Set<S3OptimizationSuggestion>>any());
            verify(awsCredentialsService).getCredentialsByAccountId(Mockito.<String>any());
        }
    }

    /**
     * Method under test: {@link S3SuggestionsService#getObjectsListByBucketName(String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetObjectsListByBucketName() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            // TODO: Complete this test.
            //   Reason: R013 No inputs found that don't throw a trivial exception.
            //   Diffblue Cover tried to run the arrange/act section, but the method under
            //   test threw
            //   java.lang.IllegalArgumentException: connect: invalid address type
            //       at java.base/java.net.Socket.checkAddress(Socket.java:691)
            //       at java.base/java.net.Socket.connect(Socket.java:621)
            //       at org.apache.http.conn.ssl.SSLConnectionSocketFactory.connectSocket(SSLConnectionSocketFactory.java:368)
            //       at com.amazonaws.http.conn.ssl.SdkTLSSocketFactory.connectSocket(SdkTLSSocketFactory.java:142)
            //       at org.apache.http.impl.conn.DefaultHttpClientConnectionOperator.connect(DefaultHttpClientConnectionOperator.java:142)
            //       at org.apache.http.impl.conn.PoolingHttpClientConnectionManager.connect(PoolingHttpClientConnectionManager.java:376)
            //       at com.amazonaws.http.conn.ClientConnectionManagerFactory$Handler.invoke(ClientConnectionManagerFactory.java:76)
            //       at com.amazonaws.http.conn.$Proxy115.connect(Unknown Source)
            //       at org.apache.http.impl.execchain.MainClientExec.establishRoute(MainClientExec.java:393)
            //       at org.apache.http.impl.execchain.MainClientExec.execute(MainClientExec.java:236)
            //       at org.apache.http.impl.execchain.ProtocolExec.execute(ProtocolExec.java:186)
            //       at org.apache.http.impl.client.InternalHttpClient.doExecute(InternalHttpClient.java:185)
            //       at org.apache.http.impl.client.CloseableHttpClient.execute(CloseableHttpClient.java:83)
            //       at org.apache.http.impl.client.CloseableHttpClient.execute(CloseableHttpClient.java:56)
            //       at com.amazonaws.http.apache.client.impl.SdkHttpClient.execute(SdkHttpClient.java:72)
            //       at com.amazonaws.http.AmazonHttpClient$RequestExecutor.executeOneRequest(AmazonHttpClient.java:1346)
            //       at com.amazonaws.http.AmazonHttpClient$RequestExecutor.executeHelper(AmazonHttpClient.java:1157)
            //       at com.amazonaws.http.AmazonHttpClient$RequestExecutor.doExecute(AmazonHttpClient.java:814)
            //       at com.amazonaws.http.AmazonHttpClient$RequestExecutor.executeWithTimer(AmazonHttpClient.java:781)
            //       at com.amazonaws.http.AmazonHttpClient$RequestExecutor.execute(AmazonHttpClient.java:755)
            //       at com.amazonaws.http.AmazonHttpClient$RequestExecutor.access$500(AmazonHttpClient.java:715)
            //       at com.amazonaws.http.AmazonHttpClient$RequestExecutionBuilderImpl.execute(AmazonHttpClient.java:697)
            //       at com.amazonaws.http.AmazonHttpClient.execute(AmazonHttpClient.java:561)
            //       at com.amazonaws.http.AmazonHttpClient.execute(AmazonHttpClient.java:541)
            //       at com.amazonaws.services.s3.AmazonS3Client.invoke(AmazonS3Client.java:5520)
            //       at com.amazonaws.services.s3.AmazonS3Client.invoke(AmazonS3Client.java:5467)
            //       at com.amazonaws.services.s3.AmazonS3Client.listObjects(AmazonS3Client.java:949)
            //       at com.amazonaws.services.s3.AmazonS3Client.listObjects(AmazonS3Client.java:908)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.S3SuggestionsService.getObjectsListByBucketName(S3SuggestionsService.java:119)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});

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
            Optional<StorageBucket> ofResult = Optional.of(storageBucket);
            when(storageBucketRepository.findByName(Mockito.<String>any())).thenReturn(ofResult);

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
            s3SuggestionsService.getObjectsListByBucketName("bucket-name");
        }
    }

    /**
     * Method under test: {@link S3SuggestionsService#getObjectsListByBucketName(String)}
     */
    @Test
    void testGetObjectsListByBucketName2() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});

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
            StorageBucket storageBucket = mock(StorageBucket.class);
            when(storageBucket.getRegion()).thenThrow(new BucketNotFoundException("An error occurred"));
            when(storageBucket.getAssociatedAccount()).thenReturn(awsAccountCredentials);
            doNothing().when(storageBucket).setAssociatedAccount(Mockito.<AwsAccountCredentials>any());
            doNothing().when(storageBucket).setCreationDate(Mockito.<Date>any());
            doNothing().when(storageBucket).setCreationTimestamp(Mockito.<LocalDateTime>any());
            doNothing().when(storageBucket).setId(Mockito.<Long>any());
            doNothing().when(storageBucket).setLastUpdateTimestamp(Mockito.<LocalDateTime>any());
            doNothing().when(storageBucket).setMonitoringEnabled(anyBoolean());
            doNothing().when(storageBucket).setName(Mockito.<String>any());
            doNothing().when(storageBucket).setObjectCount(anyLong());
            doNothing().when(storageBucket).setOwner(Mockito.<String>any());
            doNothing().when(storageBucket).setOwnerEmail(Mockito.<String>any());
            doNothing().when(storageBucket).setPurpose(Mockito.<String>any());
            doNothing().when(storageBucket).setRegion(Mockito.<String>any());
            doNothing().when(storageBucket).setSize(anyLong());
            doNothing().when(storageBucket).setSuggestions(Mockito.<Set<S3OptimizationSuggestion>>any());
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
            Optional<StorageBucket> ofResult = Optional.of(storageBucket);
            when(storageBucketRepository.findByName(Mockito.<String>any())).thenReturn(ofResult);

            AwsAccountCredentials awsAccountCredentials2 = new AwsAccountCredentials();
            awsAccountCredentials2.setAccessKeyId("EXAMPLEakiAIOSFODNN7");
            awsAccountCredentials2.setAccountId("42");
            awsAccountCredentials2.setAccountName("Dr Jane Doe");
            awsAccountCredentials2.setDate(LocalDate.of(1970, 1, 1).atStartOfDay());
            awsAccountCredentials2.setEbsOptimizationSuggestions(new ArrayList<>());
            awsAccountCredentials2.setEbsVolumes(new ArrayList<>());
            awsAccountCredentials2.setEc2Instances(new ArrayList<>());
            awsAccountCredentials2.setEc2OptimizationSuggestions(new ArrayList<>());
            awsAccountCredentials2.setEipAddresses(new ArrayList<>());
            awsAccountCredentials2.setEipOptimizationSuggestions(new ArrayList<>());
            awsAccountCredentials2.setId(1L);
            awsAccountCredentials2.setRdsInstances(new ArrayList<>());
            awsAccountCredentials2.setRdsOptimizationSuggestions(new ArrayList<>());
            awsAccountCredentials2.setS3OptimizationSuggestions(new ArrayList<>());
            awsAccountCredentials2.setSecretAccessKey("EXAMPLEakiAIOSFODNN7");
            awsAccountCredentials2.setStorageBuckets(new ArrayList<>());
            when(awsCredentialsService.getCredentialsByAccountId(Mockito.<String>any())).thenReturn(awsAccountCredentials2);
            assertThrows(BucketNotFoundException.class,
                    () -> s3SuggestionsService.getObjectsListByBucketName("bucket-name"));
            verify(storageBucketRepository).findByName(Mockito.<String>any());
            verify(storageBucket).getAssociatedAccount();
            verify(storageBucket).getRegion();
            verify(storageBucket).setAssociatedAccount(Mockito.<AwsAccountCredentials>any());
            verify(storageBucket).setCreationDate(Mockito.<Date>any());
            verify(storageBucket).setCreationTimestamp(Mockito.<LocalDateTime>any());
            verify(storageBucket).setId(Mockito.<Long>any());
            verify(storageBucket).setLastUpdateTimestamp(Mockito.<LocalDateTime>any());
            verify(storageBucket).setMonitoringEnabled(anyBoolean());
            verify(storageBucket).setName(Mockito.<String>any());
            verify(storageBucket).setObjectCount(anyLong());
            verify(storageBucket).setOwner(Mockito.<String>any());
            verify(storageBucket).setOwnerEmail(Mockito.<String>any());
            verify(storageBucket).setPurpose(Mockito.<String>any());
            verify(storageBucket).setRegion(Mockito.<String>any());
            verify(storageBucket).setSize(anyLong());
            verify(storageBucket).setSuggestions(Mockito.<Set<S3OptimizationSuggestion>>any());
            verify(awsCredentialsService).getCredentialsByAccountId(Mockito.<String>any());
        }
    }

    /**
     * Method under test: {@link S3SuggestionsService#getObjectsListByBucketName(String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetObjectsListByBucketName3() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            // TODO: Complete this test.
            //   Reason: R013 No inputs found that don't throw a trivial exception.
            //   Diffblue Cover tried to run the arrange/act section, but the method under
            //   test threw
            //   java.lang.IllegalArgumentException: connect: invalid address type
            //       at java.base/java.net.Socket.checkAddress(Socket.java:691)
            //       at java.base/java.net.Socket.connect(Socket.java:621)
            //       at org.apache.http.conn.ssl.SSLConnectionSocketFactory.connectSocket(SSLConnectionSocketFactory.java:368)
            //       at com.amazonaws.http.conn.ssl.SdkTLSSocketFactory.connectSocket(SdkTLSSocketFactory.java:142)
            //       at org.apache.http.impl.conn.DefaultHttpClientConnectionOperator.connect(DefaultHttpClientConnectionOperator.java:142)
            //       at org.apache.http.impl.conn.PoolingHttpClientConnectionManager.connect(PoolingHttpClientConnectionManager.java:376)
            //       at com.amazonaws.http.conn.ClientConnectionManagerFactory$Handler.invoke(ClientConnectionManagerFactory.java:76)
            //       at com.amazonaws.http.conn.$Proxy115.connect(Unknown Source)
            //       at org.apache.http.impl.execchain.MainClientExec.establishRoute(MainClientExec.java:393)
            //       at org.apache.http.impl.execchain.MainClientExec.execute(MainClientExec.java:236)
            //       at org.apache.http.impl.execchain.ProtocolExec.execute(ProtocolExec.java:186)
            //       at org.apache.http.impl.client.InternalHttpClient.doExecute(InternalHttpClient.java:185)
            //       at org.apache.http.impl.client.CloseableHttpClient.execute(CloseableHttpClient.java:83)
            //       at org.apache.http.impl.client.CloseableHttpClient.execute(CloseableHttpClient.java:56)
            //       at com.amazonaws.http.apache.client.impl.SdkHttpClient.execute(SdkHttpClient.java:72)
            //       at com.amazonaws.http.AmazonHttpClient$RequestExecutor.executeOneRequest(AmazonHttpClient.java:1346)
            //       at com.amazonaws.http.AmazonHttpClient$RequestExecutor.executeHelper(AmazonHttpClient.java:1157)
            //       at com.amazonaws.http.AmazonHttpClient$RequestExecutor.doExecute(AmazonHttpClient.java:814)
            //       at com.amazonaws.http.AmazonHttpClient$RequestExecutor.executeWithTimer(AmazonHttpClient.java:781)
            //       at com.amazonaws.http.AmazonHttpClient$RequestExecutor.execute(AmazonHttpClient.java:755)
            //       at com.amazonaws.http.AmazonHttpClient$RequestExecutor.access$500(AmazonHttpClient.java:715)
            //       at com.amazonaws.http.AmazonHttpClient$RequestExecutionBuilderImpl.execute(AmazonHttpClient.java:697)
            //       at com.amazonaws.http.AmazonHttpClient.execute(AmazonHttpClient.java:561)
            //       at com.amazonaws.http.AmazonHttpClient.execute(AmazonHttpClient.java:541)
            //       at com.amazonaws.services.s3.AmazonS3Client.invoke(AmazonS3Client.java:5520)
            //       at com.amazonaws.services.s3.AmazonS3Client.invoke(AmazonS3Client.java:5467)
            //       at com.amazonaws.services.s3.AmazonS3Client.listObjects(AmazonS3Client.java:949)
            //       at com.amazonaws.services.s3.AmazonS3Client.listObjects(AmazonS3Client.java:908)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.S3SuggestionsService.getObjectsListByBucketName(S3SuggestionsService.java:119)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});

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
            StorageBucket storageBucket = mock(StorageBucket.class);
            when(storageBucket.getRegion()).thenReturn("com.vermeg.aws.cost.optimization.dashboard.entities.StorageBucket");
            when(storageBucket.getAssociatedAccount()).thenReturn(awsAccountCredentials);
            doNothing().when(storageBucket).setAssociatedAccount(Mockito.<AwsAccountCredentials>any());
            doNothing().when(storageBucket).setCreationDate(Mockito.<Date>any());
            doNothing().when(storageBucket).setCreationTimestamp(Mockito.<LocalDateTime>any());
            doNothing().when(storageBucket).setId(Mockito.<Long>any());
            doNothing().when(storageBucket).setLastUpdateTimestamp(Mockito.<LocalDateTime>any());
            doNothing().when(storageBucket).setMonitoringEnabled(anyBoolean());
            doNothing().when(storageBucket).setName(Mockito.<String>any());
            doNothing().when(storageBucket).setObjectCount(anyLong());
            doNothing().when(storageBucket).setOwner(Mockito.<String>any());
            doNothing().when(storageBucket).setOwnerEmail(Mockito.<String>any());
            doNothing().when(storageBucket).setPurpose(Mockito.<String>any());
            doNothing().when(storageBucket).setRegion(Mockito.<String>any());
            doNothing().when(storageBucket).setSize(anyLong());
            doNothing().when(storageBucket).setSuggestions(Mockito.<Set<S3OptimizationSuggestion>>any());
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
            Optional<StorageBucket> ofResult = Optional.of(storageBucket);
            when(storageBucketRepository.findByName(Mockito.<String>any())).thenReturn(ofResult);

            AwsAccountCredentials awsAccountCredentials2 = new AwsAccountCredentials();
            awsAccountCredentials2.setAccessKeyId("EXAMPLEakiAIOSFODNN7");
            awsAccountCredentials2.setAccountId("42");
            awsAccountCredentials2.setAccountName("Dr Jane Doe");
            awsAccountCredentials2.setDate(LocalDate.of(1970, 1, 1).atStartOfDay());
            awsAccountCredentials2.setEbsOptimizationSuggestions(new ArrayList<>());
            awsAccountCredentials2.setEbsVolumes(new ArrayList<>());
            awsAccountCredentials2.setEc2Instances(new ArrayList<>());
            awsAccountCredentials2.setEc2OptimizationSuggestions(new ArrayList<>());
            awsAccountCredentials2.setEipAddresses(new ArrayList<>());
            awsAccountCredentials2.setEipOptimizationSuggestions(new ArrayList<>());
            awsAccountCredentials2.setId(1L);
            awsAccountCredentials2.setRdsInstances(new ArrayList<>());
            awsAccountCredentials2.setRdsOptimizationSuggestions(new ArrayList<>());
            awsAccountCredentials2.setS3OptimizationSuggestions(new ArrayList<>());
            awsAccountCredentials2.setSecretAccessKey("EXAMPLEakiAIOSFODNN7");
            awsAccountCredentials2.setStorageBuckets(new ArrayList<>());
            when(awsCredentialsService.getCredentialsByAccountId(Mockito.<String>any())).thenReturn(awsAccountCredentials2);
            s3SuggestionsService.getObjectsListByBucketName("bucket-name");
        }
    }

    /**
     * Method under test: {@link S3SuggestionsService#getOptimalStorageClass(S3ObjectSummary)}
     */
    @Test
    void testGetOptimalStorageClass() {
        S3ObjectSummary object = new S3ObjectSummary();
        object.setBucketName("bucket-name");
        object.setETag("E Tag");
        object.setKey("Key");
        object.setLastModified(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        object.setOwner(new Owner("42", "Display Name"));
        object.setRestoreStatus(new RestoreStatus());
        object.setSize(3L);
        object.setStorageClass("Storage Class");
        assertEquals("Storage Class", s3SuggestionsService.getOptimalStorageClass(object));
    }

    /**
     * Method under test: {@link S3SuggestionsService#getOptimalStorageClass(S3ObjectSummary)}
     */
    @Test
    void testGetOptimalStorageClass2() {
        S3ObjectSummary object = mock(S3ObjectSummary.class);
        when(object.getStorageClass()).thenReturn("Storage Class");
        when(object.getLastModified())
                .thenReturn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        doNothing().when(object).setBucketName(Mockito.<String>any());
        doNothing().when(object).setETag(Mockito.<String>any());
        doNothing().when(object).setKey(Mockito.<String>any());
        doNothing().when(object).setLastModified(Mockito.<Date>any());
        doNothing().when(object).setOwner(Mockito.<Owner>any());
        doNothing().when(object).setRestoreStatus(Mockito.<RestoreStatus>any());
        doNothing().when(object).setSize(anyLong());
        doNothing().when(object).setStorageClass(Mockito.<String>any());
        object.setBucketName("bucket-name");
        object.setETag("E Tag");
        object.setKey("Key");
        object.setLastModified(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        object.setOwner(new Owner("42", "Display Name"));
        object.setRestoreStatus(new RestoreStatus());
        object.setSize(3L);
        object.setStorageClass("Storage Class");
        assertEquals("Storage Class", s3SuggestionsService.getOptimalStorageClass(object));
        verify(object).getStorageClass();
        verify(object).getLastModified();
        verify(object).setBucketName(Mockito.<String>any());
        verify(object).setETag(Mockito.<String>any());
        verify(object).setKey(Mockito.<String>any());
        verify(object).setLastModified(Mockito.<Date>any());
        verify(object).setOwner(Mockito.<Owner>any());
        verify(object).setRestoreStatus(Mockito.<RestoreStatus>any());
        verify(object).setSize(anyLong());
        verify(object).setStorageClass(Mockito.<String>any());
    }

    /**
     * Method under test: {@link S3SuggestionsService#getOptimalStorageClass(S3ObjectSummary)}
     */
    @Test
    void testGetOptimalStorageClass3() {
        S3ObjectSummary object = mock(S3ObjectSummary.class);
        when(object.getStorageClass()).thenReturn("STANDARD");
        when(object.getLastModified())
                .thenReturn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        doNothing().when(object).setBucketName(Mockito.<String>any());
        doNothing().when(object).setETag(Mockito.<String>any());
        doNothing().when(object).setKey(Mockito.<String>any());
        doNothing().when(object).setLastModified(Mockito.<Date>any());
        doNothing().when(object).setOwner(Mockito.<Owner>any());
        doNothing().when(object).setRestoreStatus(Mockito.<RestoreStatus>any());
        doNothing().when(object).setSize(anyLong());
        doNothing().when(object).setStorageClass(Mockito.<String>any());
        object.setBucketName("bucket-name");
        object.setETag("E Tag");
        object.setKey("Key");
        object.setLastModified(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        object.setOwner(new Owner("42", "Display Name"));
        object.setRestoreStatus(new RestoreStatus());
        object.setSize(3L);
        object.setStorageClass("Storage Class");
        s3SuggestionsService.getOptimalStorageClass(object);
        verify(object).getStorageClass();
        verify(object).getLastModified();
        verify(object).setBucketName(Mockito.<String>any());
        verify(object).setETag(Mockito.<String>any());
        verify(object).setKey(Mockito.<String>any());
        verify(object).setLastModified(Mockito.<Date>any());
        verify(object).setOwner(Mockito.<Owner>any());
        verify(object).setRestoreStatus(Mockito.<RestoreStatus>any());
        verify(object).setSize(anyLong());
        verify(object).setStorageClass(Mockito.<String>any());
    }

    /**
     * Method under test: {@link S3SuggestionsService#getOptimalStorageClass(S3ObjectSummary)}
     */
    @Test
    void testGetOptimalStorageClass4() {
        S3ObjectSummary object = mock(S3ObjectSummary.class);
        when(object.getStorageClass()).thenReturn("STANDARD_IA");
        when(object.getLastModified())
                .thenReturn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        doNothing().when(object).setBucketName(Mockito.<String>any());
        doNothing().when(object).setETag(Mockito.<String>any());
        doNothing().when(object).setKey(Mockito.<String>any());
        doNothing().when(object).setLastModified(Mockito.<Date>any());
        doNothing().when(object).setOwner(Mockito.<Owner>any());
        doNothing().when(object).setRestoreStatus(Mockito.<RestoreStatus>any());
        doNothing().when(object).setSize(anyLong());
        doNothing().when(object).setStorageClass(Mockito.<String>any());
        object.setBucketName("bucket-name");
        object.setETag("E Tag");
        object.setKey("Key");
        object.setLastModified(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        object.setOwner(new Owner("42", "Display Name"));
        object.setRestoreStatus(new RestoreStatus());
        object.setSize(3L);
        object.setStorageClass("Storage Class");
        s3SuggestionsService.getOptimalStorageClass(object);
        verify(object).getStorageClass();
        verify(object).getLastModified();
        verify(object).setBucketName(Mockito.<String>any());
        verify(object).setETag(Mockito.<String>any());
        verify(object).setKey(Mockito.<String>any());
        verify(object).setLastModified(Mockito.<Date>any());
        verify(object).setOwner(Mockito.<Owner>any());
        verify(object).setRestoreStatus(Mockito.<RestoreStatus>any());
        verify(object).setSize(anyLong());
        verify(object).setStorageClass(Mockito.<String>any());
    }

    /**
     * Method under test: {@link S3SuggestionsService#getOptimalStorageClass(S3ObjectSummary)}
     */
    @Test
    void testGetOptimalStorageClass5() {
        S3ObjectSummary object = mock(S3ObjectSummary.class);
        when(object.getStorageClass()).thenReturn("GLACIER");
        when(object.getLastModified())
                .thenReturn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        doNothing().when(object).setBucketName(Mockito.<String>any());
        doNothing().when(object).setETag(Mockito.<String>any());
        doNothing().when(object).setKey(Mockito.<String>any());
        doNothing().when(object).setLastModified(Mockito.<Date>any());
        doNothing().when(object).setOwner(Mockito.<Owner>any());
        doNothing().when(object).setRestoreStatus(Mockito.<RestoreStatus>any());
        doNothing().when(object).setSize(anyLong());
        doNothing().when(object).setStorageClass(Mockito.<String>any());
        object.setBucketName("bucket-name");
        object.setETag("E Tag");
        object.setKey("Key");
        object.setLastModified(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        object.setOwner(new Owner("42", "Display Name"));
        object.setRestoreStatus(new RestoreStatus());
        object.setSize(3L);
        object.setStorageClass("Storage Class");
        s3SuggestionsService.getOptimalStorageClass(object);
        verify(object).getStorageClass();
        verify(object).getLastModified();
        verify(object).setBucketName(Mockito.<String>any());
        verify(object).setETag(Mockito.<String>any());
        verify(object).setKey(Mockito.<String>any());
        verify(object).setLastModified(Mockito.<Date>any());
        verify(object).setOwner(Mockito.<Owner>any());
        verify(object).setRestoreStatus(Mockito.<RestoreStatus>any());
        verify(object).setSize(anyLong());
        verify(object).setStorageClass(Mockito.<String>any());
    }

    /**
     * Method under test: {@link S3SuggestionsService#getOptimalStorageClass(S3ObjectSummary)}
     */
    @Test
    void testGetOptimalStorageClass6() {
        java.sql.Date date = mock(java.sql.Date.class);
        when(date.toInstant()).thenThrow(new BucketNotFoundException("An error occurred"));
        S3ObjectSummary object = mock(S3ObjectSummary.class);
        when(object.getStorageClass()).thenReturn("Storage Class");
        when(object.getLastModified()).thenReturn(date);
        doNothing().when(object).setBucketName(Mockito.<String>any());
        doNothing().when(object).setETag(Mockito.<String>any());
        doNothing().when(object).setKey(Mockito.<String>any());
        doNothing().when(object).setLastModified(Mockito.<java.util.Date>any());
        doNothing().when(object).setOwner(Mockito.<Owner>any());
        doNothing().when(object).setRestoreStatus(Mockito.<RestoreStatus>any());
        doNothing().when(object).setSize(anyLong());
        doNothing().when(object).setStorageClass(Mockito.<String>any());
        object.setBucketName("bucket-name");
        object.setETag("E Tag");
        object.setKey("Key");
        object.setLastModified(
                java.util.Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        object.setOwner(new Owner("42", "Display Name"));
        object.setRestoreStatus(new RestoreStatus());
        object.setSize(3L);
        object.setStorageClass("Storage Class");
        assertThrows(BucketNotFoundException.class, () -> s3SuggestionsService.getOptimalStorageClass(object));
        verify(object).getStorageClass();
        verify(object).getLastModified();
        verify(object).setBucketName(Mockito.<String>any());
        verify(object).setETag(Mockito.<String>any());
        verify(object).setKey(Mockito.<String>any());
        verify(object).setLastModified(Mockito.<java.util.Date>any());
        verify(object).setOwner(Mockito.<Owner>any());
        verify(object).setRestoreStatus(Mockito.<RestoreStatus>any());
        verify(object).setSize(anyLong());
        verify(object).setStorageClass(Mockito.<String>any());
        verify(date).toInstant();
    }

    /**
     * Method under test: {@link S3SuggestionsService#deleteDealtWithObjectSuggestions()}
     */
    @Test
    void testDeleteDealtWithObjectSuggestions() {
        when(s3ObjectOptimizationSuggestionRepository.findAll()).thenReturn(new ArrayList<>());
        s3SuggestionsService.deleteDealtWithObjectSuggestions();
        verify(s3ObjectOptimizationSuggestionRepository).findAll();
    }

    /**
     * Method under test: {@link S3SuggestionsService#deleteDealtWithObjectSuggestions()}
     */
    @Test
    void testDeleteDealtWithObjectSuggestions2() {
        S3ObjectOptimizationSuggestion s3ObjectOptimizationSuggestion = new S3ObjectOptimizationSuggestion();
        s3ObjectOptimizationSuggestion.setAssociatedAccount("3");
        s3ObjectOptimizationSuggestion.setBucketName("bucket-name");
        s3ObjectOptimizationSuggestion
                .setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        s3ObjectOptimizationSuggestion.setDescription("The characteristics of someone or something");
        s3ObjectOptimizationSuggestion.setId(1L);
        s3ObjectOptimizationSuggestion.setStatus(SuggestionStatus.Pending);
        s3ObjectOptimizationSuggestion.setTitle("Dr");

        ArrayList<S3ObjectOptimizationSuggestion> s3ObjectOptimizationSuggestionList = new ArrayList<>();
        s3ObjectOptimizationSuggestionList.add(s3ObjectOptimizationSuggestion);
        when(s3ObjectOptimizationSuggestionRepository.findAll()).thenReturn(s3ObjectOptimizationSuggestionList);
        s3SuggestionsService.deleteDealtWithObjectSuggestions();
        verify(s3ObjectOptimizationSuggestionRepository).findAll();
    }

    /**
     * Method under test: {@link S3SuggestionsService#deleteDealtWithObjectSuggestions()}
     */
    @Test
    void testDeleteDealtWithObjectSuggestions3() {
        S3ObjectOptimizationSuggestion s3ObjectOptimizationSuggestion = new S3ObjectOptimizationSuggestion();
        s3ObjectOptimizationSuggestion.setAssociatedAccount("3");
        s3ObjectOptimizationSuggestion.setBucketName("bucket-name");
        s3ObjectOptimizationSuggestion
                .setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        s3ObjectOptimizationSuggestion.setDescription("The characteristics of someone or something");
        s3ObjectOptimizationSuggestion.setId(1L);
        s3ObjectOptimizationSuggestion.setStatus(SuggestionStatus.Pending);
        s3ObjectOptimizationSuggestion.setTitle("Dr");

        S3ObjectOptimizationSuggestion s3ObjectOptimizationSuggestion2 = new S3ObjectOptimizationSuggestion();
        s3ObjectOptimizationSuggestion2.setAssociatedAccount("Associated Account");
        s3ObjectOptimizationSuggestion2.setBucketName("s3://bucket-name/object-key");
        s3ObjectOptimizationSuggestion2
                .setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        s3ObjectOptimizationSuggestion2.setDescription("Description");
        s3ObjectOptimizationSuggestion2.setId(2L);
        s3ObjectOptimizationSuggestion2.setStatus(SuggestionStatus.Ignored);
        s3ObjectOptimizationSuggestion2.setTitle("Mr");

        ArrayList<S3ObjectOptimizationSuggestion> s3ObjectOptimizationSuggestionList = new ArrayList<>();
        s3ObjectOptimizationSuggestionList.add(s3ObjectOptimizationSuggestion2);
        s3ObjectOptimizationSuggestionList.add(s3ObjectOptimizationSuggestion);
        when(s3ObjectOptimizationSuggestionRepository.findAll()).thenReturn(s3ObjectOptimizationSuggestionList);
        s3SuggestionsService.deleteDealtWithObjectSuggestions();
        verify(s3ObjectOptimizationSuggestionRepository).findAll();
    }

    /**
     * Method under test: {@link S3SuggestionsService#generateS3SuggestionsStream()}
     */
    @Test
    void testGenerateS3SuggestionsStream() {
        ArrayList<S3OptimizationSuggestion> s3OptimizationSuggestionList = new ArrayList<>();
        when(s3OptimizationSuggestionRepository.saveAll(Mockito.<Iterable<S3OptimizationSuggestion>>any()))
                .thenReturn(s3OptimizationSuggestionList);
        when(storageBucketRepository.findAll()).thenReturn(new ArrayList<>());
        List<S3OptimizationSuggestion> actualGenerateS3SuggestionsStreamResult = s3SuggestionsService
                .generateS3SuggestionsStream();
        assertSame(s3OptimizationSuggestionList, actualGenerateS3SuggestionsStreamResult);
        assertTrue(actualGenerateS3SuggestionsStreamResult.isEmpty());
        verify(s3OptimizationSuggestionRepository).saveAll(Mockito.<Iterable<S3OptimizationSuggestion>>any());
        verify(storageBucketRepository).findAll();
    }

    /**
     * Method under test: {@link S3SuggestionsService#generateS3SuggestionsStream()}
     */
    @Test
    void testGenerateS3SuggestionsStream2() {
        when(storageBucketRepository.findAll()).thenThrow(new BucketNotFoundException("An error occurred"));
        assertThrows(BucketNotFoundException.class, () -> s3SuggestionsService.generateS3SuggestionsStream());
        verify(storageBucketRepository).findAll();
    }

    /**
     * Method under test: {@link S3SuggestionsService#generateS3SuggestionsStream()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGenerateS3SuggestionsStream3() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            // TODO: Complete this test.
            //   Reason: R013 No inputs found that don't throw a trivial exception.
            //   Diffblue Cover tried to run the arrange/act section, but the method under
            //   test threw
            //   java.lang.IllegalArgumentException: connect: invalid address type
            //       at java.base/java.net.Socket.checkAddress(Socket.java:691)
            //       at java.base/java.net.Socket.connect(Socket.java:621)
            //       at org.apache.http.conn.ssl.SSLConnectionSocketFactory.connectSocket(SSLConnectionSocketFactory.java:368)
            //       at com.amazonaws.http.conn.ssl.SdkTLSSocketFactory.connectSocket(SdkTLSSocketFactory.java:142)
            //       at org.apache.http.impl.conn.DefaultHttpClientConnectionOperator.connect(DefaultHttpClientConnectionOperator.java:142)
            //       at org.apache.http.impl.conn.PoolingHttpClientConnectionManager.connect(PoolingHttpClientConnectionManager.java:376)
            //       at com.amazonaws.http.conn.ClientConnectionManagerFactory$Handler.invoke(ClientConnectionManagerFactory.java:76)
            //       at com.amazonaws.http.conn.$Proxy115.connect(Unknown Source)
            //       at org.apache.http.impl.execchain.MainClientExec.establishRoute(MainClientExec.java:393)
            //       at org.apache.http.impl.execchain.MainClientExec.execute(MainClientExec.java:236)
            //       at org.apache.http.impl.execchain.ProtocolExec.execute(ProtocolExec.java:186)
            //       at org.apache.http.impl.client.InternalHttpClient.doExecute(InternalHttpClient.java:185)
            //       at org.apache.http.impl.client.CloseableHttpClient.execute(CloseableHttpClient.java:83)
            //       at org.apache.http.impl.client.CloseableHttpClient.execute(CloseableHttpClient.java:56)
            //       at com.amazonaws.http.apache.client.impl.SdkHttpClient.execute(SdkHttpClient.java:72)
            //       at com.amazonaws.http.AmazonHttpClient$RequestExecutor.executeOneRequest(AmazonHttpClient.java:1346)
            //       at com.amazonaws.http.AmazonHttpClient$RequestExecutor.executeHelper(AmazonHttpClient.java:1157)
            //       at com.amazonaws.http.AmazonHttpClient$RequestExecutor.doExecute(AmazonHttpClient.java:814)
            //       at com.amazonaws.http.AmazonHttpClient$RequestExecutor.executeWithTimer(AmazonHttpClient.java:781)
            //       at com.amazonaws.http.AmazonHttpClient$RequestExecutor.execute(AmazonHttpClient.java:755)
            //       at com.amazonaws.http.AmazonHttpClient$RequestExecutor.access$500(AmazonHttpClient.java:715)
            //       at com.amazonaws.http.AmazonHttpClient$RequestExecutionBuilderImpl.execute(AmazonHttpClient.java:697)
            //       at com.amazonaws.http.AmazonHttpClient.execute(AmazonHttpClient.java:561)
            //       at com.amazonaws.http.AmazonHttpClient.execute(AmazonHttpClient.java:541)
            //       at com.amazonaws.services.s3.AmazonS3Client.invoke(AmazonS3Client.java:5520)
            //       at com.amazonaws.services.s3.AmazonS3Client.getBucketRegionViaHeadRequest(AmazonS3Client.java:6501)
            //       at com.amazonaws.services.s3.AmazonS3Client.fetchRegionFromCache(AmazonS3Client.java:6473)
            //       at com.amazonaws.services.s3.AmazonS3Client.invoke(AmazonS3Client.java:5505)
            //       at com.amazonaws.services.s3.AmazonS3Client.invoke(AmazonS3Client.java:5467)
            //       at com.amazonaws.services.s3.AmazonS3Client.invoke(AmazonS3Client.java:5461)
            //       at com.amazonaws.services.s3.AmazonS3Client.getBucketLifecycleConfiguration(AmazonS3Client.java:2528)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.S3SuggestionsService.hasLifecyclePolicy(S3SuggestionsService.java:105)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.S3SuggestionsService.lambda$generateS3SuggestionsStream$3(S3SuggestionsService.java:192)
            //       at java.base/java.util.stream.ReferencePipeline$2$1.accept(ReferencePipeline.java:178)
            //       at java.base/java.util.ArrayList$ArrayListSpliterator.forEachRemaining(ArrayList.java:1625)
            //       at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:509)
            //       at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:499)
            //       at java.base/java.util.stream.ReduceOps$ReduceTask.doLeaf(ReduceOps.java:960)
            //       at java.base/java.util.stream.ReduceOps$ReduceTask.doLeaf(ReduceOps.java:934)
            //       at java.base/java.util.stream.AbstractTask.compute(AbstractTask.java:327)
            //       at java.base/java.util.concurrent.CountedCompleter.exec(CountedCompleter.java:754)
            //       at java.base/java.util.concurrent.ForkJoinTask.doExec(ForkJoinTask.java:373)
            //       at java.base/java.util.concurrent.ForkJoinTask.invoke(ForkJoinTask.java:686)
            //       at java.base/java.util.stream.ReduceOps$ReduceOp.evaluateParallel(ReduceOps.java:927)
            //       at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:233)
            //       at java.base/java.util.stream.ReferencePipeline.collect(ReferencePipeline.java:682)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.S3SuggestionsService.generateS3SuggestionsStream(S3SuggestionsService.java:202)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            when(s3OptimizationSuggestionRepository.saveAll(Mockito.<Iterable<S3OptimizationSuggestion>>any()))
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

            ArrayList<StorageBucket> storageBucketList = new ArrayList<>();
            storageBucketList.add(storageBucket);

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

            StorageBucket storageBucket2 = new StorageBucket();
            storageBucket2.setAssociatedAccount(associatedAccount2);
            storageBucket2
                    .setCreationDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
            storageBucket2.setCreationTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
            storageBucket2.setId(1L);
            storageBucket2.setLastUpdateTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
            storageBucket2.setMonitoringEnabled(true);
            storageBucket2.setName("bucket-name");
            storageBucket2.setObjectCount(3L);
            storageBucket2.setOwner("Owner");
            storageBucket2.setOwnerEmail("jane.doe@example.org");
            storageBucket2.setPurpose("Purpose");
            storageBucket2.setRegion("us-east-2");
            storageBucket2.setSize(3L);
            storageBucket2.setSuggestions(new HashSet<>());
            Optional<StorageBucket> ofResult = Optional.of(storageBucket2);
            when(storageBucketRepository.findByName(Mockito.<String>any())).thenReturn(ofResult);
            when(storageBucketRepository.findAll()).thenReturn(storageBucketList);

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
            s3SuggestionsService.generateS3SuggestionsStream();
        }
    }

    /**
     * Method under test: {@link S3SuggestionsService#generateS3SuggestionsStream()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGenerateS3SuggestionsStream4() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            // TODO: Complete this test.
            //   Reason: R013 No inputs found that don't throw a trivial exception.
            //   Diffblue Cover tried to run the arrange/act section, but the method under
            //   test threw
            //   com.amazonaws.SdkClientException: Unable to execute HTTP request: Connection is closed
            //       at com.amazonaws.http.AmazonHttpClient$RequestExecutor.handleRetryableException(AmazonHttpClient.java:1219)
            //       at com.amazonaws.http.AmazonHttpClient$RequestExecutor.executeHelper(AmazonHttpClient.java:1165)
            //       at com.amazonaws.http.AmazonHttpClient$RequestExecutor.doExecute(AmazonHttpClient.java:814)
            //       at com.amazonaws.http.AmazonHttpClient$RequestExecutor.executeWithTimer(AmazonHttpClient.java:781)
            //       at com.amazonaws.http.AmazonHttpClient$RequestExecutor.execute(AmazonHttpClient.java:755)
            //       at com.amazonaws.http.AmazonHttpClient$RequestExecutor.access$500(AmazonHttpClient.java:715)
            //       at com.amazonaws.http.AmazonHttpClient$RequestExecutionBuilderImpl.execute(AmazonHttpClient.java:697)
            //       at com.amazonaws.http.AmazonHttpClient.execute(AmazonHttpClient.java:561)
            //       at com.amazonaws.http.AmazonHttpClient.execute(AmazonHttpClient.java:541)
            //       at com.amazonaws.services.s3.AmazonS3Client.invoke(AmazonS3Client.java:5520)
            //       at com.amazonaws.services.s3.AmazonS3Client.getBucketRegionViaHeadRequest(AmazonS3Client.java:6501)
            //       at com.amazonaws.services.s3.AmazonS3Client.fetchRegionFromCache(AmazonS3Client.java:6473)
            //       at com.amazonaws.services.s3.AmazonS3Client.invoke(AmazonS3Client.java:5505)
            //       at com.amazonaws.services.s3.AmazonS3Client.invoke(AmazonS3Client.java:5467)
            //       at com.amazonaws.services.s3.AmazonS3Client.invoke(AmazonS3Client.java:5461)
            //       at com.amazonaws.services.s3.AmazonS3Client.getBucketLifecycleConfiguration(AmazonS3Client.java:2528)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.S3SuggestionsService.hasLifecyclePolicy(S3SuggestionsService.java:105)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.S3SuggestionsService.lambda$generateS3SuggestionsStream$3(S3SuggestionsService.java:192)
            //       at java.base/java.util.stream.ReferencePipeline$2$1.accept(ReferencePipeline.java:178)
            //       at java.base/java.util.ArrayList$ArrayListSpliterator.forEachRemaining(ArrayList.java:1625)
            //       at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:509)
            //       at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:499)
            //       at java.base/java.util.stream.ReduceOps$ReduceTask.doLeaf(ReduceOps.java:960)
            //       at java.base/java.util.stream.ReduceOps$ReduceTask.doLeaf(ReduceOps.java:934)
            //       at java.base/java.util.stream.AbstractTask.compute(AbstractTask.java:327)
            //       at java.base/java.util.concurrent.CountedCompleter.exec(CountedCompleter.java:754)
            //       at java.base/java.util.concurrent.ForkJoinTask.doExec(ForkJoinTask.java:373)
            //       at java.base/java.util.concurrent.ForkJoinTask.invoke(ForkJoinTask.java:686)
            //       at java.base/java.util.stream.ReduceOps$ReduceOp.evaluateParallel(ReduceOps.java:927)
            //       at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:233)
            //       at java.base/java.util.stream.ReferencePipeline.collect(ReferencePipeline.java:682)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.S3SuggestionsService.generateS3SuggestionsStream(S3SuggestionsService.java:202)
            //   org.apache.http.ConnectionClosedException: Connection is closed
            //       at org.apache.http.impl.BHttpConnectionBase.ensureOpen(BHttpConnectionBase.java:130)
            //       at org.apache.http.impl.DefaultBHttpClientConnection.sendRequestHeader(DefaultBHttpClientConnection.java:140)
            //       at org.apache.http.impl.conn.CPoolProxy.sendRequestHeader(CPoolProxy.java:147)
            //       at org.apache.http.protocol.HttpRequestExecutor.doSendRequest(HttpRequestExecutor.java:205)
            //       at com.amazonaws.http.protocol.SdkHttpRequestExecutor.doSendRequest(SdkHttpRequestExecutor.java:63)
            //       at org.apache.http.protocol.HttpRequestExecutor.execute(HttpRequestExecutor.java:123)
            //       at org.apache.http.impl.execchain.MainClientExec.execute(MainClientExec.java:272)
            //       at org.apache.http.impl.execchain.ProtocolExec.execute(ProtocolExec.java:186)
            //       at org.apache.http.impl.client.InternalHttpClient.doExecute(InternalHttpClient.java:185)
            //       at org.apache.http.impl.client.CloseableHttpClient.execute(CloseableHttpClient.java:83)
            //       at org.apache.http.impl.client.CloseableHttpClient.execute(CloseableHttpClient.java:56)
            //       at com.amazonaws.http.apache.client.impl.SdkHttpClient.execute(SdkHttpClient.java:72)
            //       at com.amazonaws.http.AmazonHttpClient$RequestExecutor.executeOneRequest(AmazonHttpClient.java:1346)
            //       at com.amazonaws.http.AmazonHttpClient$RequestExecutor.executeHelper(AmazonHttpClient.java:1157)
            //       at com.amazonaws.http.AmazonHttpClient$RequestExecutor.doExecute(AmazonHttpClient.java:814)
            //       at com.amazonaws.http.AmazonHttpClient$RequestExecutor.executeWithTimer(AmazonHttpClient.java:781)
            //       at com.amazonaws.http.AmazonHttpClient$RequestExecutor.execute(AmazonHttpClient.java:755)
            //       at com.amazonaws.http.AmazonHttpClient$RequestExecutor.access$500(AmazonHttpClient.java:715)
            //       at com.amazonaws.http.AmazonHttpClient$RequestExecutionBuilderImpl.execute(AmazonHttpClient.java:697)
            //       at com.amazonaws.http.AmazonHttpClient.execute(AmazonHttpClient.java:561)
            //       at com.amazonaws.http.AmazonHttpClient.execute(AmazonHttpClient.java:541)
            //       at com.amazonaws.services.s3.AmazonS3Client.invoke(AmazonS3Client.java:5520)
            //       at com.amazonaws.services.s3.AmazonS3Client.getBucketRegionViaHeadRequest(AmazonS3Client.java:6501)
            //       at com.amazonaws.services.s3.AmazonS3Client.fetchRegionFromCache(AmazonS3Client.java:6473)
            //       at com.amazonaws.services.s3.AmazonS3Client.invoke(AmazonS3Client.java:5505)
            //       at com.amazonaws.services.s3.AmazonS3Client.invoke(AmazonS3Client.java:5467)
            //       at com.amazonaws.services.s3.AmazonS3Client.invoke(AmazonS3Client.java:5461)
            //       at com.amazonaws.services.s3.AmazonS3Client.getBucketLifecycleConfiguration(AmazonS3Client.java:2528)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.S3SuggestionsService.hasLifecyclePolicy(S3SuggestionsService.java:105)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.S3SuggestionsService.lambda$generateS3SuggestionsStream$3(S3SuggestionsService.java:192)
            //       at java.base/java.util.stream.ReferencePipeline$2$1.accept(ReferencePipeline.java:178)
            //       at java.base/java.util.ArrayList$ArrayListSpliterator.forEachRemaining(ArrayList.java:1625)
            //       at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:509)
            //       at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:499)
            //       at java.base/java.util.stream.ReduceOps$ReduceTask.doLeaf(ReduceOps.java:960)
            //       at java.base/java.util.stream.ReduceOps$ReduceTask.doLeaf(ReduceOps.java:934)
            //       at java.base/java.util.stream.AbstractTask.compute(AbstractTask.java:327)
            //       at java.base/java.util.concurrent.CountedCompleter.exec(CountedCompleter.java:754)
            //       at java.base/java.util.concurrent.ForkJoinTask.doExec(ForkJoinTask.java:373)
            //       at java.base/java.util.concurrent.ForkJoinTask.invoke(ForkJoinTask.java:686)
            //       at java.base/java.util.stream.ReduceOps$ReduceOp.evaluateParallel(ReduceOps.java:927)
            //       at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:233)
            //       at java.base/java.util.stream.ReferencePipeline.collect(ReferencePipeline.java:682)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.S3SuggestionsService.generateS3SuggestionsStream(S3SuggestionsService.java:202)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any())).thenReturn(new InetAddress[]{});
            when(s3OptimizationSuggestionRepository.saveAll(Mockito.<Iterable<S3OptimizationSuggestion>>any()))
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

            ArrayList<StorageBucket> storageBucketList = new ArrayList<>();
            storageBucketList.add(storageBucket);

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

            StorageBucket storageBucket2 = new StorageBucket();
            storageBucket2.setAssociatedAccount(associatedAccount2);
            storageBucket2
                    .setCreationDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
            storageBucket2.setCreationTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
            storageBucket2.setId(1L);
            storageBucket2.setLastUpdateTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
            storageBucket2.setMonitoringEnabled(true);
            storageBucket2.setName("bucket-name");
            storageBucket2.setObjectCount(3L);
            storageBucket2.setOwner("Owner");
            storageBucket2.setOwnerEmail("jane.doe@example.org");
            storageBucket2.setPurpose("Purpose");
            storageBucket2.setRegion("us-east-2");
            storageBucket2.setSize(3L);
            storageBucket2.setSuggestions(new HashSet<>());
            Optional<StorageBucket> ofResult = Optional.of(storageBucket2);
            when(storageBucketRepository.findByName(Mockito.<String>any())).thenReturn(ofResult);
            when(storageBucketRepository.findAll()).thenReturn(storageBucketList);

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
            s3SuggestionsService.generateS3SuggestionsStream();
        }
    }

    /**
     * Method under test: {@link S3SuggestionsService#generateS3SuggestionsStream()}
     */
    @Test
    void testGenerateS3SuggestionsStream5() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});

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

            ArrayList<StorageBucket> storageBucketList = new ArrayList<>();
            storageBucketList.add(storageBucket);

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
            StorageBucket storageBucket2 = mock(StorageBucket.class);
            when(storageBucket2.getRegion()).thenThrow(new BucketNotFoundException("An error occurred"));
            when(storageBucket2.getAssociatedAccount()).thenReturn(awsAccountCredentials);
            doNothing().when(storageBucket2).setAssociatedAccount(Mockito.<AwsAccountCredentials>any());
            doNothing().when(storageBucket2).setCreationDate(Mockito.<Date>any());
            doNothing().when(storageBucket2).setCreationTimestamp(Mockito.<LocalDateTime>any());
            doNothing().when(storageBucket2).setId(Mockito.<Long>any());
            doNothing().when(storageBucket2).setLastUpdateTimestamp(Mockito.<LocalDateTime>any());
            doNothing().when(storageBucket2).setMonitoringEnabled(anyBoolean());
            doNothing().when(storageBucket2).setName(Mockito.<String>any());
            doNothing().when(storageBucket2).setObjectCount(anyLong());
            doNothing().when(storageBucket2).setOwner(Mockito.<String>any());
            doNothing().when(storageBucket2).setOwnerEmail(Mockito.<String>any());
            doNothing().when(storageBucket2).setPurpose(Mockito.<String>any());
            doNothing().when(storageBucket2).setRegion(Mockito.<String>any());
            doNothing().when(storageBucket2).setSize(anyLong());
            doNothing().when(storageBucket2).setSuggestions(Mockito.<Set<S3OptimizationSuggestion>>any());
            storageBucket2.setAssociatedAccount(associatedAccount2);
            storageBucket2
                    .setCreationDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
            storageBucket2.setCreationTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
            storageBucket2.setId(1L);
            storageBucket2.setLastUpdateTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
            storageBucket2.setMonitoringEnabled(true);
            storageBucket2.setName("bucket-name");
            storageBucket2.setObjectCount(3L);
            storageBucket2.setOwner("Owner");
            storageBucket2.setOwnerEmail("jane.doe@example.org");
            storageBucket2.setPurpose("Purpose");
            storageBucket2.setRegion("us-east-2");
            storageBucket2.setSize(3L);
            storageBucket2.setSuggestions(new HashSet<>());
            Optional<StorageBucket> ofResult = Optional.of(storageBucket2);
            when(storageBucketRepository.findByName(Mockito.<String>any())).thenReturn(ofResult);
            when(storageBucketRepository.findAll()).thenReturn(storageBucketList);

            AwsAccountCredentials awsAccountCredentials2 = new AwsAccountCredentials();
            awsAccountCredentials2.setAccessKeyId("EXAMPLEakiAIOSFODNN7");
            awsAccountCredentials2.setAccountId("42");
            awsAccountCredentials2.setAccountName("Dr Jane Doe");
            awsAccountCredentials2.setDate(LocalDate.of(1970, 1, 1).atStartOfDay());
            awsAccountCredentials2.setEbsOptimizationSuggestions(new ArrayList<>());
            awsAccountCredentials2.setEbsVolumes(new ArrayList<>());
            awsAccountCredentials2.setEc2Instances(new ArrayList<>());
            awsAccountCredentials2.setEc2OptimizationSuggestions(new ArrayList<>());
            awsAccountCredentials2.setEipAddresses(new ArrayList<>());
            awsAccountCredentials2.setEipOptimizationSuggestions(new ArrayList<>());
            awsAccountCredentials2.setId(1L);
            awsAccountCredentials2.setRdsInstances(new ArrayList<>());
            awsAccountCredentials2.setRdsOptimizationSuggestions(new ArrayList<>());
            awsAccountCredentials2.setS3OptimizationSuggestions(new ArrayList<>());
            awsAccountCredentials2.setSecretAccessKey("EXAMPLEakiAIOSFODNN7");
            awsAccountCredentials2.setStorageBuckets(new ArrayList<>());
            when(awsCredentialsService.getCredentialsByAccountId(Mockito.<String>any())).thenReturn(awsAccountCredentials2);
            assertThrows(BucketNotFoundException.class, () -> s3SuggestionsService.generateS3SuggestionsStream());
            verify(storageBucketRepository).findAll();
            verify(storageBucketRepository).findByName(Mockito.<String>any());
            verify(storageBucket2).getAssociatedAccount();
            verify(storageBucket2).getRegion();
            verify(storageBucket2).setAssociatedAccount(Mockito.<AwsAccountCredentials>any());
            verify(storageBucket2).setCreationDate(Mockito.<Date>any());
            verify(storageBucket2).setCreationTimestamp(Mockito.<LocalDateTime>any());
            verify(storageBucket2).setId(Mockito.<Long>any());
            verify(storageBucket2).setLastUpdateTimestamp(Mockito.<LocalDateTime>any());
            verify(storageBucket2).setMonitoringEnabled(anyBoolean());
            verify(storageBucket2).setName(Mockito.<String>any());
            verify(storageBucket2).setObjectCount(anyLong());
            verify(storageBucket2).setOwner(Mockito.<String>any());
            verify(storageBucket2).setOwnerEmail(Mockito.<String>any());
            verify(storageBucket2).setPurpose(Mockito.<String>any());
            verify(storageBucket2).setRegion(Mockito.<String>any());
            verify(storageBucket2).setSize(anyLong());
            verify(storageBucket2).setSuggestions(Mockito.<Set<S3OptimizationSuggestion>>any());
            verify(awsCredentialsService).getCredentialsByAccountId(Mockito.<String>any());
        }
    }

    /**
     * Method under test: {@link S3SuggestionsService#generateS3SuggestionsStream()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGenerateS3SuggestionsStream6() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            // TODO: Complete this test.
            //   Reason: R013 No inputs found that don't throw a trivial exception.
            //   Diffblue Cover tried to run the arrange/act section, but the method under
            //   test threw
            //   java.lang.IllegalArgumentException: connect: invalid address type
            //       at java.base/java.net.Socket.checkAddress(Socket.java:691)
            //       at java.base/java.net.Socket.connect(Socket.java:621)
            //       at org.apache.http.conn.ssl.SSLConnectionSocketFactory.connectSocket(SSLConnectionSocketFactory.java:368)
            //       at com.amazonaws.http.conn.ssl.SdkTLSSocketFactory.connectSocket(SdkTLSSocketFactory.java:142)
            //       at org.apache.http.impl.conn.DefaultHttpClientConnectionOperator.connect(DefaultHttpClientConnectionOperator.java:142)
            //       at org.apache.http.impl.conn.PoolingHttpClientConnectionManager.connect(PoolingHttpClientConnectionManager.java:376)
            //       at com.amazonaws.http.conn.ClientConnectionManagerFactory$Handler.invoke(ClientConnectionManagerFactory.java:76)
            //       at com.amazonaws.http.conn.$Proxy115.connect(Unknown Source)
            //       at org.apache.http.impl.execchain.MainClientExec.establishRoute(MainClientExec.java:393)
            //       at org.apache.http.impl.execchain.MainClientExec.execute(MainClientExec.java:236)
            //       at org.apache.http.impl.execchain.ProtocolExec.execute(ProtocolExec.java:186)
            //       at org.apache.http.impl.client.InternalHttpClient.doExecute(InternalHttpClient.java:185)
            //       at org.apache.http.impl.client.CloseableHttpClient.execute(CloseableHttpClient.java:83)
            //       at org.apache.http.impl.client.CloseableHttpClient.execute(CloseableHttpClient.java:56)
            //       at com.amazonaws.http.apache.client.impl.SdkHttpClient.execute(SdkHttpClient.java:72)
            //       at com.amazonaws.http.AmazonHttpClient$RequestExecutor.executeOneRequest(AmazonHttpClient.java:1346)
            //       at com.amazonaws.http.AmazonHttpClient$RequestExecutor.executeHelper(AmazonHttpClient.java:1157)
            //       at com.amazonaws.http.AmazonHttpClient$RequestExecutor.doExecute(AmazonHttpClient.java:814)
            //       at com.amazonaws.http.AmazonHttpClient$RequestExecutor.executeWithTimer(AmazonHttpClient.java:781)
            //       at com.amazonaws.http.AmazonHttpClient$RequestExecutor.execute(AmazonHttpClient.java:755)
            //       at com.amazonaws.http.AmazonHttpClient$RequestExecutor.access$500(AmazonHttpClient.java:715)
            //       at com.amazonaws.http.AmazonHttpClient$RequestExecutionBuilderImpl.execute(AmazonHttpClient.java:697)
            //       at com.amazonaws.http.AmazonHttpClient.execute(AmazonHttpClient.java:561)
            //       at com.amazonaws.http.AmazonHttpClient.execute(AmazonHttpClient.java:541)
            //       at com.amazonaws.services.s3.AmazonS3Client.invoke(AmazonS3Client.java:5520)
            //       at com.amazonaws.services.s3.AmazonS3Client.getBucketRegionViaHeadRequest(AmazonS3Client.java:6501)
            //       at com.amazonaws.services.s3.AmazonS3Client.fetchRegionFromCache(AmazonS3Client.java:6473)
            //       at com.amazonaws.services.s3.AmazonS3Client.invoke(AmazonS3Client.java:5505)
            //       at com.amazonaws.services.s3.AmazonS3Client.invoke(AmazonS3Client.java:5467)
            //       at com.amazonaws.services.s3.AmazonS3Client.invoke(AmazonS3Client.java:5461)
            //       at com.amazonaws.services.s3.AmazonS3Client.getBucketLifecycleConfiguration(AmazonS3Client.java:2528)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.S3SuggestionsService.hasLifecyclePolicy(S3SuggestionsService.java:105)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.S3SuggestionsService.lambda$generateS3SuggestionsStream$3(S3SuggestionsService.java:192)
            //       at java.base/java.util.stream.ReferencePipeline$2$1.accept(ReferencePipeline.java:178)
            //       at java.base/java.util.ArrayList$ArrayListSpliterator.forEachRemaining(ArrayList.java:1625)
            //       at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:509)
            //       at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:499)
            //       at java.base/java.util.stream.ReduceOps$ReduceTask.doLeaf(ReduceOps.java:960)
            //       at java.base/java.util.stream.ReduceOps$ReduceTask.doLeaf(ReduceOps.java:934)
            //       at java.base/java.util.stream.AbstractTask.compute(AbstractTask.java:327)
            //       at java.base/java.util.concurrent.CountedCompleter.exec(CountedCompleter.java:754)
            //       at java.base/java.util.concurrent.ForkJoinTask.doExec(ForkJoinTask.java:373)
            //       at java.base/java.util.concurrent.ForkJoinTask.invoke(ForkJoinTask.java:686)
            //       at java.base/java.util.stream.ReduceOps$ReduceOp.evaluateParallel(ReduceOps.java:927)
            //       at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:233)
            //       at java.base/java.util.stream.ReferencePipeline.collect(ReferencePipeline.java:682)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.S3SuggestionsService.generateS3SuggestionsStream(S3SuggestionsService.java:202)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            when(s3OptimizationSuggestionRepository.saveAll(Mockito.<Iterable<S3OptimizationSuggestion>>any()))
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

            ArrayList<StorageBucket> storageBucketList = new ArrayList<>();
            storageBucketList.add(storageBucket);

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
            StorageBucket storageBucket2 = mock(StorageBucket.class);
            when(storageBucket2.getRegion()).thenReturn("foo");
            when(storageBucket2.getAssociatedAccount()).thenReturn(awsAccountCredentials);
            doNothing().when(storageBucket2).setAssociatedAccount(Mockito.<AwsAccountCredentials>any());
            doNothing().when(storageBucket2).setCreationDate(Mockito.<Date>any());
            doNothing().when(storageBucket2).setCreationTimestamp(Mockito.<LocalDateTime>any());
            doNothing().when(storageBucket2).setId(Mockito.<Long>any());
            doNothing().when(storageBucket2).setLastUpdateTimestamp(Mockito.<LocalDateTime>any());
            doNothing().when(storageBucket2).setMonitoringEnabled(anyBoolean());
            doNothing().when(storageBucket2).setName(Mockito.<String>any());
            doNothing().when(storageBucket2).setObjectCount(anyLong());
            doNothing().when(storageBucket2).setOwner(Mockito.<String>any());
            doNothing().when(storageBucket2).setOwnerEmail(Mockito.<String>any());
            doNothing().when(storageBucket2).setPurpose(Mockito.<String>any());
            doNothing().when(storageBucket2).setRegion(Mockito.<String>any());
            doNothing().when(storageBucket2).setSize(anyLong());
            doNothing().when(storageBucket2).setSuggestions(Mockito.<Set<S3OptimizationSuggestion>>any());
            storageBucket2.setAssociatedAccount(associatedAccount2);
            storageBucket2
                    .setCreationDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
            storageBucket2.setCreationTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
            storageBucket2.setId(1L);
            storageBucket2.setLastUpdateTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
            storageBucket2.setMonitoringEnabled(true);
            storageBucket2.setName("bucket-name");
            storageBucket2.setObjectCount(3L);
            storageBucket2.setOwner("Owner");
            storageBucket2.setOwnerEmail("jane.doe@example.org");
            storageBucket2.setPurpose("Purpose");
            storageBucket2.setRegion("us-east-2");
            storageBucket2.setSize(3L);
            storageBucket2.setSuggestions(new HashSet<>());
            Optional<StorageBucket> ofResult = Optional.of(storageBucket2);
            when(storageBucketRepository.findByName(Mockito.<String>any())).thenReturn(ofResult);
            when(storageBucketRepository.findAll()).thenReturn(storageBucketList);

            AwsAccountCredentials awsAccountCredentials2 = new AwsAccountCredentials();
            awsAccountCredentials2.setAccessKeyId("EXAMPLEakiAIOSFODNN7");
            awsAccountCredentials2.setAccountId("42");
            awsAccountCredentials2.setAccountName("Dr Jane Doe");
            awsAccountCredentials2.setDate(LocalDate.of(1970, 1, 1).atStartOfDay());
            awsAccountCredentials2.setEbsOptimizationSuggestions(new ArrayList<>());
            awsAccountCredentials2.setEbsVolumes(new ArrayList<>());
            awsAccountCredentials2.setEc2Instances(new ArrayList<>());
            awsAccountCredentials2.setEc2OptimizationSuggestions(new ArrayList<>());
            awsAccountCredentials2.setEipAddresses(new ArrayList<>());
            awsAccountCredentials2.setEipOptimizationSuggestions(new ArrayList<>());
            awsAccountCredentials2.setId(1L);
            awsAccountCredentials2.setRdsInstances(new ArrayList<>());
            awsAccountCredentials2.setRdsOptimizationSuggestions(new ArrayList<>());
            awsAccountCredentials2.setS3OptimizationSuggestions(new ArrayList<>());
            awsAccountCredentials2.setSecretAccessKey("EXAMPLEakiAIOSFODNN7");
            awsAccountCredentials2.setStorageBuckets(new ArrayList<>());
            when(awsCredentialsService.getCredentialsByAccountId(Mockito.<String>any())).thenReturn(awsAccountCredentials2);
            s3SuggestionsService.generateS3SuggestionsStream();
        }
    }

    /**
     * Method under test: {@link S3SuggestionsService#isCrossRegionReplicationEnabled(AmazonS3, String)}
     */
    @Test
    void testIsCrossRegionReplicationEnabled() {
        assertFalse(
                S3SuggestionsService.isCrossRegionReplicationEnabled(new AmazonS3Client(), "s3://bucket-name/object-key"));
        assertFalse(S3SuggestionsService.isCrossRegionReplicationEnabled(null, "s3://bucket-name/object-key"));
    }

    /**
     * Method under test: {@link S3SuggestionsService#isCrossRegionReplicationEnabled(AmazonS3, String)}
     */
    @Test
    void testIsCrossRegionReplicationEnabled2() throws SdkClientException {
        AmazonS3Client s3Client = mock(AmazonS3Client.class);
        when(s3Client.getBucketReplicationConfiguration(Mockito.<GetBucketReplicationConfigurationRequest>any()))
                .thenReturn(new BucketReplicationConfiguration());
        assertFalse(S3SuggestionsService.isCrossRegionReplicationEnabled(s3Client, "s3://bucket-name/object-key"));
        verify(s3Client).getBucketReplicationConfiguration(Mockito.<GetBucketReplicationConfigurationRequest>any());
    }

    /**
     * Method under test: {@link S3SuggestionsService#isCrossRegionReplicationEnabled(AmazonS3, String)}
     */
    @Test
    void testIsCrossRegionReplicationEnabled3() throws SdkClientException {
        AmazonS3Client s3Client = mock(AmazonS3Client.class);
        when(s3Client.getBucketReplicationConfiguration(Mockito.<GetBucketReplicationConfigurationRequest>any()))
                .thenReturn(null);
        assertFalse(S3SuggestionsService.isCrossRegionReplicationEnabled(s3Client, "s3://bucket-name/object-key"));
        verify(s3Client).getBucketReplicationConfiguration(Mockito.<GetBucketReplicationConfigurationRequest>any());
    }

    /**
     * Method under test: {@link S3SuggestionsService#isCrossRegionReplicationEnabled(AmazonS3, String)}
     */
    @Test
    void testIsCrossRegionReplicationEnabled4() throws SdkClientException {
        BucketReplicationConfiguration bucketReplicationConfiguration = mock(BucketReplicationConfiguration.class);
        when(bucketReplicationConfiguration.getRules()).thenThrow(new BucketNotFoundException("An error occurred"));
        AmazonS3Client s3Client = mock(AmazonS3Client.class);
        when(s3Client.getBucketReplicationConfiguration(Mockito.<GetBucketReplicationConfigurationRequest>any()))
                .thenReturn(bucketReplicationConfiguration);
        assertFalse(S3SuggestionsService.isCrossRegionReplicationEnabled(s3Client, "s3://bucket-name/object-key"));
        verify(s3Client).getBucketReplicationConfiguration(Mockito.<GetBucketReplicationConfigurationRequest>any());
        verify(bucketReplicationConfiguration).getRules();
    }
}

