package com.vermeg.aws.cost.optimization.dashboard.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.importexport.model.InvalidAccessKeyIdException;
import com.vermeg.aws.cost.optimization.dashboard.entities.AwsAccountCredentials;
import com.vermeg.aws.cost.optimization.dashboard.entities.EBSOptimizationSuggestion;
import com.vermeg.aws.cost.optimization.dashboard.entities.EBSVolume;
import com.vermeg.aws.cost.optimization.dashboard.entities.EC2Instance;
import com.vermeg.aws.cost.optimization.dashboard.entities.EC2OptimizationSuggestion;
import com.vermeg.aws.cost.optimization.dashboard.entities.EIPAddress;
import com.vermeg.aws.cost.optimization.dashboard.entities.RDSInstance;
import com.vermeg.aws.cost.optimization.dashboard.entities.RDSOptimizationSuggestion;
import com.vermeg.aws.cost.optimization.dashboard.entities.S3OptimizationSuggestion;
import com.vermeg.aws.cost.optimization.dashboard.entities.StorageBucket;
import com.vermeg.aws.cost.optimization.dashboard.repositories.AwsCredentialsRepository;

import java.net.InetAddress;
import java.net.UnknownHostException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AwsCredentialsService.class})
@ExtendWith(SpringExtension.class)
class AwsCredentialsServiceTest {
    @MockBean
    private AwsCredentialsRepository awsCredentialsRepository;

    @Autowired
    private AwsCredentialsService awsCredentialsService;

    /**
     * Method under test: {@link AwsCredentialsService#createCredentials(AwsAccountCredentials)}
     */
    @Test
    void testCreateCredentials() {
        AwsAccountCredentials credentials = new AwsAccountCredentials();
        credentials.setAccessKeyId("EXAMPLEakiAIOSFODNN7");
        credentials.setAccountId("42");
        credentials.setAccountName("Dr Jane Doe");
        credentials.setDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        credentials.setEbsOptimizationSuggestions(new ArrayList<>());
        credentials.setEbsVolumes(new ArrayList<>());
        credentials.setEc2Instances(new ArrayList<>());
        credentials.setEc2OptimizationSuggestions(new ArrayList<>());
        credentials.setEipAddresses(new ArrayList<>());
        credentials.setEipOptimizationSuggestions(new ArrayList<>());
        credentials.setId(1L);
        credentials.setRdsInstances(new ArrayList<>());
        credentials.setRdsOptimizationSuggestions(new ArrayList<>());
        credentials.setS3OptimizationSuggestions(new ArrayList<>());
        credentials.setSecretAccessKey("EXAMPLEakiAIOSFODNN7");
        credentials.setStorageBuckets(new ArrayList<>());
        assertThrows(InvalidAccessKeyIdException.class, () -> awsCredentialsService.createCredentials(credentials));
    }

    /**
     * Method under test: {@link AwsCredentialsService#createCredentials(AwsAccountCredentials)}
     */
    @Test
    void testCreateCredentials2() {
        LocalDateTime date = LocalDate.of(1970, 1, 1).atStartOfDay();
        ArrayList<EC2Instance> ec2Instances = new ArrayList<>();
        ArrayList<EBSVolume> ebsVolumes = new ArrayList<>();
        ArrayList<StorageBucket> storageBuckets = new ArrayList<>();
        ArrayList<RDSInstance> rdsInstances = new ArrayList<>();
        ArrayList<EIPAddress> eipAddresses = new ArrayList<>();
        ArrayList<EC2OptimizationSuggestion> ec2OptimizationSuggestions = new ArrayList<>();
        ArrayList<EBSOptimizationSuggestion> ebsOptimizationSuggestions = new ArrayList<>();
        ArrayList<S3OptimizationSuggestion> s3OptimizationSuggestions = new ArrayList<>();
        ArrayList<RDSOptimizationSuggestion> rdsOptimizationSuggestions = new ArrayList<>();

        AwsAccountCredentials credentials = new AwsAccountCredentials(1L, "EXAMPLEakiAIOSFODNN7", "EXAMPLEakiAIOSFODNN7",
                "Dr Jane Doe", "42", date, ec2Instances, ebsVolumes, storageBuckets, rdsInstances, eipAddresses,
                ec2OptimizationSuggestions, ebsOptimizationSuggestions, s3OptimizationSuggestions, rdsOptimizationSuggestions,
                new ArrayList<>());
        credentials.setAccessKeyId("EXAMPLEakiAIOSFODNN7");
        credentials.setAccountId("42");
        credentials.setAccountName("Dr Jane Doe");
        credentials.setDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        credentials.setEbsOptimizationSuggestions(new ArrayList<>());
        credentials.setEbsVolumes(new ArrayList<>());
        credentials.setEc2Instances(new ArrayList<>());
        credentials.setEc2OptimizationSuggestions(new ArrayList<>());
        credentials.setEipAddresses(new ArrayList<>());
        credentials.setEipOptimizationSuggestions(new ArrayList<>());
        credentials.setId(1L);
        credentials.setRdsInstances(new ArrayList<>());
        credentials.setRdsOptimizationSuggestions(new ArrayList<>());
        credentials.setS3OptimizationSuggestions(new ArrayList<>());
        credentials.setSecretAccessKey("EXAMPLEakiAIOSFODNN7");
        credentials.setStorageBuckets(new ArrayList<>());
        assertThrows(InvalidAccessKeyIdException.class, () -> awsCredentialsService.createCredentials(credentials));
    }

    /**
     * Method under test: {@link AwsCredentialsService#getAllAwsCredentials()}
     */
    @Test
    void testGetAllAwsCredentials() {
        ArrayList<AwsAccountCredentials> awsAccountCredentialsList = new ArrayList<>();
        when(awsCredentialsRepository.findAll()).thenReturn(awsAccountCredentialsList);
        List<AwsAccountCredentials> actualAllAwsCredentials = awsCredentialsService.getAllAwsCredentials();
        assertSame(awsAccountCredentialsList, actualAllAwsCredentials);
        assertTrue(actualAllAwsCredentials.isEmpty());
        verify(awsCredentialsRepository).findAll();
    }

    /**
     * Method under test: {@link AwsCredentialsService#getAllAwsCredentials()}
     */
    @Test
    void testGetAllAwsCredentials2() {
        when(awsCredentialsRepository.findAll()).thenThrow(new InvalidAccessKeyIdException("An error occurred"));
        assertThrows(InvalidAccessKeyIdException.class, () -> awsCredentialsService.getAllAwsCredentials());
        verify(awsCredentialsRepository).findAll();
    }

    /**
     * Method under test: {@link AwsCredentialsService#updateCredentials(Long, String, String, String)}
     */
    @Test
    void testUpdateCredentials() {
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
        Optional<AwsAccountCredentials> ofResult = Optional.of(awsAccountCredentials);

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
        when(awsCredentialsRepository.save(Mockito.<AwsAccountCredentials>any())).thenReturn(awsAccountCredentials2);
        when(awsCredentialsRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertSame(awsAccountCredentials2,
                awsCredentialsService.updateCredentials(1L, "Name", "EXAMPLEakiAIOSFODNN7", "EXAMPLEakiAIOSFODNN7"));
        verify(awsCredentialsRepository).save(Mockito.<AwsAccountCredentials>any());
        verify(awsCredentialsRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link AwsCredentialsService#updateCredentials(Long, String, String, String)}
     */
    @Test
    void testUpdateCredentials2() {
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
        Optional<AwsAccountCredentials> ofResult = Optional.of(awsAccountCredentials);
        when(awsCredentialsRepository.save(Mockito.<AwsAccountCredentials>any()))
                .thenThrow(new InvalidAccessKeyIdException("An error occurred"));
        when(awsCredentialsRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertThrows(InvalidAccessKeyIdException.class,
                () -> awsCredentialsService.updateCredentials(1L, "Name", "EXAMPLEakiAIOSFODNN7", "EXAMPLEakiAIOSFODNN7"));
        verify(awsCredentialsRepository).save(Mockito.<AwsAccountCredentials>any());
        verify(awsCredentialsRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link AwsCredentialsService#updateCredentials(Long, String, String, String)}
     */
    @Test
    void testUpdateCredentials3() {
        Optional<AwsAccountCredentials> emptyResult = Optional.empty();
        when(awsCredentialsRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);
        assertThrows(RuntimeException.class,
                () -> awsCredentialsService.updateCredentials(1L, "Name", "EXAMPLEakiAIOSFODNN7", "EXAMPLEakiAIOSFODNN7"));
        verify(awsCredentialsRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link AwsCredentialsService#getCredentialsById(Long)}
     */
    @Test
    void testGetCredentialsById() {
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
        Optional<AwsAccountCredentials> ofResult = Optional.of(awsAccountCredentials);
        when(awsCredentialsRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertSame(awsAccountCredentials, awsCredentialsService.getCredentialsById(1L));
        verify(awsCredentialsRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link AwsCredentialsService#getCredentialsById(Long)}
     */
    @Test
    void testGetCredentialsById2() {
        Optional<AwsAccountCredentials> emptyResult = Optional.empty();
        when(awsCredentialsRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);
        assertThrows(RuntimeException.class, () -> awsCredentialsService.getCredentialsById(1L));
        verify(awsCredentialsRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link AwsCredentialsService#getCredentialsById(Long)}
     */
    @Test
    void testGetCredentialsById3() {
        when(awsCredentialsRepository.findById(Mockito.<Long>any()))
                .thenThrow(new InvalidAccessKeyIdException("An error occurred"));
        assertThrows(InvalidAccessKeyIdException.class, () -> awsCredentialsService.getCredentialsById(1L));
        verify(awsCredentialsRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link AwsCredentialsService#getCredentialsByName(String)}
     */
    @Test
    void testGetCredentialsByName() {
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
        when(awsCredentialsRepository.findAwsAccountCredentialsByAccountName(Mockito.<String>any()))
                .thenReturn(awsAccountCredentials);
        assertSame(awsAccountCredentials, awsCredentialsService.getCredentialsByName("Name"));
        verify(awsCredentialsRepository).findAwsAccountCredentialsByAccountName(Mockito.<String>any());
    }

    /**
     * Method under test: {@link AwsCredentialsService#getCredentialsByName(String)}
     */
    @Test
    void testGetCredentialsByName2() {
        when(awsCredentialsRepository.findAwsAccountCredentialsByAccountName(Mockito.<String>any()))
                .thenThrow(new InvalidAccessKeyIdException("An error occurred"));
        assertThrows(InvalidAccessKeyIdException.class, () -> awsCredentialsService.getCredentialsByName("Name"));
        verify(awsCredentialsRepository).findAwsAccountCredentialsByAccountName(Mockito.<String>any());
    }

    /**
     * Method under test: {@link AwsCredentialsService#getCredentialsByAccountId(String)}
     */
    @Test
    void testGetCredentialsByAccountId() {
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
        when(awsCredentialsRepository.findAwsAccountCredentialsByAccountId(Mockito.<String>any()))
                .thenReturn(awsAccountCredentials);
        assertSame(awsAccountCredentials, awsCredentialsService.getCredentialsByAccountId("3"));
        verify(awsCredentialsRepository).findAwsAccountCredentialsByAccountId(Mockito.<String>any());
    }

    /**
     * Method under test: {@link AwsCredentialsService#getCredentialsByAccountId(String)}
     */
    @Test
    void testGetCredentialsByAccountId2() {
        when(awsCredentialsRepository.findAwsAccountCredentialsByAccountId(Mockito.<String>any()))
                .thenThrow(new InvalidAccessKeyIdException("An error occurred"));
        assertThrows(InvalidAccessKeyIdException.class, () -> awsCredentialsService.getCredentialsByAccountId("3"));
        verify(awsCredentialsRepository).findAwsAccountCredentialsByAccountId(Mockito.<String>any());
    }

    /**
     * Method under test: {@link AwsCredentialsService#getAllCredentials()}
     */
    @Test
    void testGetAllCredentials() {
        when(awsCredentialsRepository.findAll()).thenReturn(new ArrayList<>());
        assertTrue(awsCredentialsService.getAllCredentials().isEmpty());
        verify(awsCredentialsRepository).findAll();
    }

    /**
     * Method under test: {@link AwsCredentialsService#getAllCredentials()}
     */
    @Test
    void testGetAllCredentials2() {
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

        ArrayList<AwsAccountCredentials> awsAccountCredentialsList = new ArrayList<>();
        awsAccountCredentialsList.add(awsAccountCredentials);
        when(awsCredentialsRepository.findAll()).thenReturn(awsAccountCredentialsList);
        List<AWSCredentials> actualAllCredentials = awsCredentialsService.getAllCredentials();
        assertEquals(1, actualAllCredentials.size());
        AWSCredentials getResult = actualAllCredentials.get(0);
        assertEquals("EXAMPLEakiAIOSFODNN7", getResult.getAWSAccessKeyId());
        assertEquals("EXAMPLEakiAIOSFODNN7", getResult.getAWSSecretKey());
        verify(awsCredentialsRepository).findAll();
    }

    /**
     * Method under test: {@link AwsCredentialsService#getAllCredentials()}
     */
    @Test
    void testGetAllCredentials3() {
        when(awsCredentialsRepository.findAll()).thenThrow(new InvalidAccessKeyIdException("An error occurred"));
        assertThrows(InvalidAccessKeyIdException.class, () -> awsCredentialsService.getAllCredentials());
        verify(awsCredentialsRepository).findAll();
    }

    /**
     * Method under test: {@link AwsCredentialsService#deleteCredentials(Long)}
     */
    @Test
    void testDeleteCredentials() {
        doNothing().when(awsCredentialsRepository).deleteById(Mockito.<Long>any());
        awsCredentialsService.deleteCredentials(1L);
        verify(awsCredentialsRepository).deleteById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link AwsCredentialsService#deleteCredentials(Long)}
     */
    @Test
    void testDeleteCredentials2() {
        doThrow(new InvalidAccessKeyIdException("An error occurred")).when(awsCredentialsRepository)
                .deleteById(Mockito.<Long>any());
        assertThrows(InvalidAccessKeyIdException.class, () -> awsCredentialsService.deleteCredentials(1L));
        verify(awsCredentialsRepository).deleteById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link AwsCredentialsService#getAwsCredentials(Long)}
     */
    @Test
    void testGetAwsCredentials() {
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
        Optional<AwsAccountCredentials> ofResult = Optional.of(awsAccountCredentials);
        when(awsCredentialsRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        AWSCredentials actualAwsCredentials = awsCredentialsService.getAwsCredentials(1L);
        assertEquals("EXAMPLEakiAIOSFODNN7", actualAwsCredentials.getAWSAccessKeyId());
        assertEquals("EXAMPLEakiAIOSFODNN7", actualAwsCredentials.getAWSSecretKey());
        verify(awsCredentialsRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link AwsCredentialsService#getAwsCredentials(Long)}
     */
    @Test
    void testGetAwsCredentials2() {
        Optional<AwsAccountCredentials> emptyResult = Optional.empty();
        when(awsCredentialsRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);
        assertThrows(IllegalArgumentException.class, () -> awsCredentialsService.getAwsCredentials(1L));
        verify(awsCredentialsRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link AwsCredentialsService#getAccountId(String, String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetAccountId() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy96.connect(Unknown Source)
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
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.doInvoke(AWSSecurityTokenServiceClient.java:1730)
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.invoke(AWSSecurityTokenServiceClient.java:1697)
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.invoke(AWSSecurityTokenServiceClient.java:1686)
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.executeGetCallerIdentity(AWSSecurityTokenServiceClient.java:1297)
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.getCallerIdentity(AWSSecurityTokenServiceClient.java:1265)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.AwsCredentialsService.getAccountId(AwsCredentialsService.java:105)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            awsCredentialsService.getAccountId("EXAMPLEakiAIOSFODNN7", "EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY");
        }
    }

    /**
     * Method under test: {@link AwsCredentialsService#getAccountId(String, String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetAccountId2() throws UnknownHostException {
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
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.doInvoke(AWSSecurityTokenServiceClient.java:1730)
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.invoke(AWSSecurityTokenServiceClient.java:1697)
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.invoke(AWSSecurityTokenServiceClient.java:1686)
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.executeGetCallerIdentity(AWSSecurityTokenServiceClient.java:1297)
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.getCallerIdentity(AWSSecurityTokenServiceClient.java:1265)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.AwsCredentialsService.getAccountId(AwsCredentialsService.java:105)
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
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.doInvoke(AWSSecurityTokenServiceClient.java:1730)
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.invoke(AWSSecurityTokenServiceClient.java:1697)
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.invoke(AWSSecurityTokenServiceClient.java:1686)
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.executeGetCallerIdentity(AWSSecurityTokenServiceClient.java:1297)
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.getCallerIdentity(AWSSecurityTokenServiceClient.java:1265)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.AwsCredentialsService.getAccountId(AwsCredentialsService.java:105)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any())).thenReturn(new InetAddress[]{});
            awsCredentialsService.getAccountId("EXAMPLEakiAIOSFODNN7", "EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY");
        }
    }

    /**
     * Method under test: {@link AwsCredentialsService#getAwsAccountAlias(String, String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetAwsAccountAlias() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy96.connect(Unknown Source)
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
            //       at com.amazonaws.services.identitymanagement.AmazonIdentityManagementClient.doInvoke(AmazonIdentityManagementClient.java:13148)
            //       at com.amazonaws.services.identitymanagement.AmazonIdentityManagementClient.invoke(AmazonIdentityManagementClient.java:13115)
            //       at com.amazonaws.services.identitymanagement.AmazonIdentityManagementClient.invoke(AmazonIdentityManagementClient.java:13104)
            //       at com.amazonaws.services.identitymanagement.AmazonIdentityManagementClient.executeListAccountAliases(AmazonIdentityManagementClient.java:6755)
            //       at com.amazonaws.services.identitymanagement.AmazonIdentityManagementClient.listAccountAliases(AmazonIdentityManagementClient.java:6723)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.AwsCredentialsService.getAwsAccountAlias(AwsCredentialsService.java:121)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            awsCredentialsService.getAwsAccountAlias("EXAMPLEakiAIOSFODNN7", "EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY");
        }
    }
}

