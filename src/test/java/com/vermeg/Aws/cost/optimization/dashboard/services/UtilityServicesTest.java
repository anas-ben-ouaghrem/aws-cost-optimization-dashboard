package com.vermeg.aws.cost.optimization.dashboard.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AnonymousAWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.STSSessionCredentials;
import com.amazonaws.services.autoscaling.AmazonAutoScaling;
import com.amazonaws.services.autoscaling.AmazonAutoScalingClient;
import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClient;
import com.amazonaws.services.costexplorer.AWSCostExplorer;
import com.amazonaws.services.costexplorer.AWSCostExplorerClient;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.rds.AmazonRDS;
import com.amazonaws.services.rds.AmazonRDSClient;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceAsyncClient;
import com.amazonaws.services.support.AWSSupport;
import com.amazonaws.services.support.AWSSupportClient;
import com.vermeg.aws.cost.optimization.dashboard.entities.AwsAccountCredentials;
import com.vermeg.aws.cost.optimization.dashboard.entities.EBSOptimizationSuggestion;
import com.vermeg.aws.cost.optimization.dashboard.entities.EBSVolume;
import com.vermeg.aws.cost.optimization.dashboard.entities.EC2Instance;
import com.vermeg.aws.cost.optimization.dashboard.entities.EC2OptimizationSuggestion;
import com.vermeg.aws.cost.optimization.dashboard.entities.EIPAddress;
import com.vermeg.aws.cost.optimization.dashboard.entities.EIPOptimizationSuggestion;
import com.vermeg.aws.cost.optimization.dashboard.entities.RDSInstance;
import com.vermeg.aws.cost.optimization.dashboard.entities.RDSOptimizationSuggestion;
import com.vermeg.aws.cost.optimization.dashboard.entities.S3OptimizationSuggestion;
import com.vermeg.aws.cost.optimization.dashboard.entities.StorageBucket;
import com.vermeg.aws.cost.optimization.dashboard.repositories.AwsCredentialsRepository;
import com.vermeg.aws.cost.optimization.dashboard.repositories.EBSVolumeRepository;
import com.vermeg.aws.cost.optimization.dashboard.repositories.EC2InstanceRepository;
import com.vermeg.aws.cost.optimization.dashboard.repositories.EIPAddressRepository;
import com.vermeg.aws.cost.optimization.dashboard.repositories.RDSInstanceRepository;
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

import org.apache.commons.lang3.tuple.Pair;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UtilityServices.class})
@ExtendWith(SpringExtension.class)
class UtilityServicesTest {
    @MockBean
    private AwsCredentialsRepository awsCredentialsRepository;

    @MockBean
    private AwsCredentialsService awsCredentialsService;

    @MockBean
    private EBSVolumeRepository eBSVolumeRepository;

    @MockBean
    private EC2InstanceRepository eC2InstanceRepository;

    @MockBean
    private EIPAddressRepository eIPAddressRepository;

    @MockBean
    private RDSInstanceRepository rDSInstanceRepository;

    @MockBean
    private StorageBucketRepository storageBucketRepository;

    @Autowired
    private UtilityServices utilityServices;

    /**
     * Method under test: {@link UtilityServices#getEc2ClientsListWithAccountId()}
     */
    @Test
    void testGetEc2ClientsListWithAccountId() {
        when(awsCredentialsService.getAllCredentials()).thenReturn(new ArrayList<>());
        assertTrue(utilityServices.getEc2ClientsListWithAccountId().isEmpty());
        verify(awsCredentialsService).getAllCredentials();
    }

    /**
     * Method under test: {@link UtilityServices#getEc2ClientsListWithAccountId()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetEc2ClientsListWithAccountId2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.IllegalArgumentException: Access key cannot be null.
        //       at com.amazonaws.auth.BasicAWSCredentials.<init>(BasicAWSCredentials.java:37)
        //       at com.vermeg.aws.cost.optimization.dashboard.services.UtilityServices.getEc2ClientsListWithAccountId(UtilityServices.java:71)
        //   See https://diff.blue/R013 to resolve this issue.

        ArrayList<AWSCredentials> awsCredentialsList = new ArrayList<>();
        awsCredentialsList.add(new AnonymousAWSCredentials());
        when(awsCredentialsService.getAccountId(Mockito.<String>any(), Mockito.<String>any())).thenReturn("42");
        when(awsCredentialsService.getAllCredentials()).thenReturn(awsCredentialsList);
        utilityServices.getEc2ClientsListWithAccountId();
    }

    /**
     * Method under test: {@link UtilityServices#getEc2ClientsListWithAccountId()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetEc2ClientsListWithAccountId3() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.amazonaws.auth.AWSCredentials.getAWSAccessKeyId()" because "credentials" is null
        //       at com.vermeg.aws.cost.optimization.dashboard.services.UtilityServices.getEc2ClientsListWithAccountId(UtilityServices.java:68)
        //   See https://diff.blue/R013 to resolve this issue.

        ArrayList<AWSCredentials> awsCredentialsList = new ArrayList<>();
        awsCredentialsList.add(null);
        when(awsCredentialsService.getAccountId(Mockito.<String>any(), Mockito.<String>any())).thenReturn("42");
        when(awsCredentialsService.getAllCredentials()).thenReturn(awsCredentialsList);
        utilityServices.getEc2ClientsListWithAccountId();
    }

    /**
     * Method under test: {@link UtilityServices#getEc2ClientsListWithAccountId()}
     */
    @Test
    void testGetEc2ClientsListWithAccountId4() {
        ArrayList<AWSCredentials> awsCredentialsList = new ArrayList<>();
        awsCredentialsList
                .add(new BasicAWSCredentials("EXAMPLEakiAIOSFODNN7", "EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY"));
        when(awsCredentialsService.getAccountId(Mockito.<String>any(), Mockito.<String>any())).thenReturn("42");
        when(awsCredentialsService.getAllCredentials()).thenReturn(awsCredentialsList);
        List<Pair<AmazonEC2, String>> actualEc2ClientsListWithAccountId = utilityServices
                .getEc2ClientsListWithAccountId();
        assertEquals(17, actualEc2ClientsListWithAccountId.size());
        Pair<AmazonEC2, String> getResult = actualEc2ClientsListWithAccountId.get(4);
        assertEquals("42", getResult.getValue());
        Pair<AmazonEC2, String> getResult2 = actualEc2ClientsListWithAccountId.get(11);
        assertEquals("42", getResult2.getValue());
        assertTrue(getResult2.getLeft() instanceof AmazonEC2Client);
        assertTrue(getResult.getLeft() instanceof AmazonEC2Client);
        Pair<AmazonEC2, String> getResult3 = actualEc2ClientsListWithAccountId.get(5);
        assertEquals("42", getResult3.getRight());
        assertTrue(getResult3.getLeft() instanceof AmazonEC2Client);
        Pair<AmazonEC2, String> getResult4 = actualEc2ClientsListWithAccountId.get(2);
        assertEquals("42", getResult4.getRight());
        assertTrue(getResult4.getLeft() instanceof AmazonEC2Client);
        Pair<AmazonEC2, String> getResult5 = actualEc2ClientsListWithAccountId.get(15);
        assertEquals("42", getResult5.getRight());
        assertTrue(getResult5.getLeft() instanceof AmazonEC2Client);
        Pair<AmazonEC2, String> getResult6 = actualEc2ClientsListWithAccountId.get(14);
        assertEquals("42", getResult6.getRight());
        assertTrue(getResult6.getLeft() instanceof AmazonEC2Client);
        Pair<AmazonEC2, String> getResult7 = actualEc2ClientsListWithAccountId.get(13);
        assertEquals("42", getResult7.getRight());
        assertTrue(getResult7.getLeft() instanceof AmazonEC2Client);
        Pair<AmazonEC2, String> getResult8 = actualEc2ClientsListWithAccountId.get(12);
        assertEquals("42", getResult8.getRight());
        assertTrue(getResult8.getLeft() instanceof AmazonEC2Client);
        Pair<AmazonEC2, String> getResult9 = actualEc2ClientsListWithAccountId.get(3);
        assertEquals("42", getResult9.getRight());
        Pair<AmazonEC2, String> getResult10 = actualEc2ClientsListWithAccountId.get(1);
        assertEquals("42", getResult10.getRight());
        Pair<AmazonEC2, String> getResult11 = actualEc2ClientsListWithAccountId.get(Short.SIZE);
        assertEquals("42", getResult11.getRight());
        Pair<AmazonEC2, String> getResult12 = actualEc2ClientsListWithAccountId.get(0);
        assertEquals("42", getResult12.getRight());
        assertTrue(getResult12.getLeft() instanceof AmazonEC2Client);
        assertSame(getResult11.getLeft(), getResult11.getKey());
        assertSame(getResult10.getLeft(), getResult10.getKey());
        assertSame(getResult9.getLeft(), getResult9.getKey());
        verify(awsCredentialsService).getAccountId(Mockito.<String>any(), Mockito.<String>any());
        verify(awsCredentialsService).getAllCredentials();
    }

    /**
     * Method under test: {@link UtilityServices#getEc2ClientsListWithAccountId()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetEc2ClientsListWithAccountId5() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy97.connect(Unknown Source)
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
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.executeGetSessionToken(AWSSecurityTokenServiceClient.java:1624)
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.getSessionToken(AWSSecurityTokenServiceClient.java:1592)
            //       at com.amazonaws.auth.STSSessionCredentials.refreshCredentials(STSSessionCredentials.java:149)
            //       at com.amazonaws.auth.STSSessionCredentials.getSessionCredentials(STSSessionCredentials.java:158)
            //       at com.amazonaws.auth.STSSessionCredentials.getAWSAccessKeyId(STSSessionCredentials.java:108)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.UtilityServices.getEc2ClientsListWithAccountId(UtilityServices.java:68)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});

            ArrayList<AWSCredentials> awsCredentialsList = new ArrayList<>();
            awsCredentialsList.add(new STSSessionCredentials(new AWSSecurityTokenServiceAsyncClient()));
            when(awsCredentialsService.getAccountId(Mockito.<String>any(), Mockito.<String>any())).thenReturn("42");
            when(awsCredentialsService.getAllCredentials()).thenReturn(awsCredentialsList);
            utilityServices.getEc2ClientsListWithAccountId();
        }
    }

    /**
     * Method under test: {@link UtilityServices#getEc2ClientsListWithAccountId()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetEc2ClientsListWithAccountId6() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy97.connect(Unknown Source)
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
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.executeGetSessionToken(AWSSecurityTokenServiceClient.java:1624)
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.getSessionToken(AWSSecurityTokenServiceClient.java:1592)
            //       at com.amazonaws.auth.STSSessionCredentials.refreshCredentials(STSSessionCredentials.java:149)
            //       at com.amazonaws.auth.STSSessionCredentials.getSessionCredentials(STSSessionCredentials.java:158)
            //       at com.amazonaws.auth.STSSessionCredentials.getAWSAccessKeyId(STSSessionCredentials.java:108)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.UtilityServices.getEc2ClientsListWithAccountId(UtilityServices.java:68)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});

            ArrayList<AWSCredentials> awsCredentialsList = new ArrayList<>();
            awsCredentialsList
                    .add(new STSSessionCredentials(new AWSSecurityTokenServiceAsyncClient(new AnonymousAWSCredentials())));
            when(awsCredentialsService.getAccountId(Mockito.<String>any(), Mockito.<String>any())).thenReturn("42");
            when(awsCredentialsService.getAllCredentials()).thenReturn(awsCredentialsList);
            utilityServices.getEc2ClientsListWithAccountId();
        }
    }

    /**
     * Method under test: {@link UtilityServices#getEc2ClientsListWithAccountId()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetEc2ClientsListWithAccountId7() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy97.connect(Unknown Source)
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
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.executeGetSessionToken(AWSSecurityTokenServiceClient.java:1624)
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.getSessionToken(AWSSecurityTokenServiceClient.java:1592)
            //       at com.amazonaws.auth.STSSessionCredentials.refreshCredentials(STSSessionCredentials.java:149)
            //       at com.amazonaws.auth.STSSessionCredentials.getSessionCredentials(STSSessionCredentials.java:158)
            //       at com.amazonaws.auth.STSSessionCredentials.getAWSAccessKeyId(STSSessionCredentials.java:108)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.UtilityServices.getEc2ClientsListWithAccountId(UtilityServices.java:68)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            BasicAWSCredentials awsCredentials = mock(BasicAWSCredentials.class);
            when(awsCredentials.getAWSAccessKeyId()).thenReturn("EXAMPLEakiAIOSFODNN7");
            when(awsCredentials.getAWSSecretKey()).thenReturn("EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY");
            STSSessionCredentials stsSessionCredentials = new STSSessionCredentials(
                    new AWSSecurityTokenServiceAsyncClient(awsCredentials));

            ArrayList<AWSCredentials> awsCredentialsList = new ArrayList<>();
            awsCredentialsList.add(stsSessionCredentials);
            when(awsCredentialsService.getAccountId(Mockito.<String>any(), Mockito.<String>any())).thenReturn("42");
            when(awsCredentialsService.getAllCredentials()).thenReturn(awsCredentialsList);
            utilityServices.getEc2ClientsListWithAccountId();
        }
    }

    /**
     * Method under test: {@link UtilityServices#getEc2ClientsListWithAccountId()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetEc2ClientsListWithAccountId8() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy97.connect(Unknown Source)
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
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.executeGetSessionToken(AWSSecurityTokenServiceClient.java:1624)
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.getSessionToken(AWSSecurityTokenServiceClient.java:1592)
            //       at com.amazonaws.auth.STSSessionCredentials.refreshCredentials(STSSessionCredentials.java:149)
            //       at com.amazonaws.auth.STSSessionCredentials.getSessionCredentials(STSSessionCredentials.java:158)
            //       at com.amazonaws.auth.STSSessionCredentials.getAWSAccessKeyId(STSSessionCredentials.java:108)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.UtilityServices.getEc2ClientsListWithAccountId(UtilityServices.java:68)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});

            ArrayList<AWSCredentials> awsCredentialsList = new ArrayList<>();
            awsCredentialsList.add(new STSSessionCredentials(new AWSSecurityTokenServiceAsyncClient()));
            when(awsCredentialsService.getAccountId(Mockito.<String>any(), Mockito.<String>any())).thenReturn("42");
            when(awsCredentialsService.getAllCredentials()).thenReturn(awsCredentialsList);
            utilityServices.getEc2ClientsListWithAccountId();
        }
    }

    /**
     * Method under test: {@link UtilityServices#getEc2ClientsListWithAccountId()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetEc2ClientsListWithAccountId9() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy97.connect(Unknown Source)
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
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.executeGetSessionToken(AWSSecurityTokenServiceClient.java:1624)
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.getSessionToken(AWSSecurityTokenServiceClient.java:1592)
            //       at com.amazonaws.auth.STSSessionCredentials.refreshCredentials(STSSessionCredentials.java:149)
            //       at com.amazonaws.auth.STSSessionCredentials.getSessionCredentials(STSSessionCredentials.java:158)
            //       at com.amazonaws.auth.STSSessionCredentials.getAWSAccessKeyId(STSSessionCredentials.java:108)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.UtilityServices.getEc2ClientsListWithAccountId(UtilityServices.java:68)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});

            ArrayList<AWSCredentials> awsCredentialsList = new ArrayList<>();
            awsCredentialsList
                    .add(new STSSessionCredentials(new AWSSecurityTokenServiceAsyncClient(new AnonymousAWSCredentials())));
            when(awsCredentialsService.getAccountId(Mockito.<String>any(), Mockito.<String>any())).thenReturn("42");
            when(awsCredentialsService.getAllCredentials()).thenReturn(awsCredentialsList);
            utilityServices.getEc2ClientsListWithAccountId();
        }
    }

    /**
     * Method under test: {@link UtilityServices#getEc2ClientsListWithAccountId()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetEc2ClientsListWithAccountId10() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy97.connect(Unknown Source)
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
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.executeGetSessionToken(AWSSecurityTokenServiceClient.java:1624)
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.getSessionToken(AWSSecurityTokenServiceClient.java:1592)
            //       at com.amazonaws.auth.STSSessionCredentials.refreshCredentials(STSSessionCredentials.java:149)
            //       at com.amazonaws.auth.STSSessionCredentials.getSessionCredentials(STSSessionCredentials.java:158)
            //       at com.amazonaws.auth.STSSessionCredentials.getAWSAccessKeyId(STSSessionCredentials.java:108)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.UtilityServices.getEc2ClientsListWithAccountId(UtilityServices.java:68)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            BasicAWSCredentials awsCredentials = mock(BasicAWSCredentials.class);
            when(awsCredentials.getAWSAccessKeyId()).thenReturn("EXAMPLEakiAIOSFODNN7");
            when(awsCredentials.getAWSSecretKey()).thenReturn("EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY");
            STSSessionCredentials stsSessionCredentials = new STSSessionCredentials(
                    new AWSSecurityTokenServiceAsyncClient(awsCredentials));

            ArrayList<AWSCredentials> awsCredentialsList = new ArrayList<>();
            awsCredentialsList.add(stsSessionCredentials);
            when(awsCredentialsService.getAccountId(Mockito.<String>any(), Mockito.<String>any())).thenReturn("42");
            when(awsCredentialsService.getAllCredentials()).thenReturn(awsCredentialsList);
            utilityServices.getEc2ClientsListWithAccountId();
        }
    }

    /**
     * Method under test: {@link UtilityServices#getRDSClientsListWithAccountId()}
     */
    @Test
    void testGetRDSClientsListWithAccountId() {
        when(awsCredentialsService.getAllCredentials()).thenReturn(new ArrayList<>());
        assertTrue(utilityServices.getRDSClientsListWithAccountId().isEmpty());
        verify(awsCredentialsService).getAllCredentials();
    }

    /**
     * Method under test: {@link UtilityServices#getRDSClientsListWithAccountId()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetRDSClientsListWithAccountId2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.IllegalArgumentException: Access key cannot be null.
        //       at com.amazonaws.auth.BasicAWSCredentials.<init>(BasicAWSCredentials.java:37)
        //       at com.vermeg.aws.cost.optimization.dashboard.services.UtilityServices.getRDSClientsListWithAccountId(UtilityServices.java:88)
        //   See https://diff.blue/R013 to resolve this issue.

        ArrayList<AWSCredentials> awsCredentialsList = new ArrayList<>();
        awsCredentialsList.add(new AnonymousAWSCredentials());
        when(awsCredentialsService.getAccountId(Mockito.<String>any(), Mockito.<String>any())).thenReturn("42");
        when(awsCredentialsService.getAllCredentials()).thenReturn(awsCredentialsList);
        utilityServices.getRDSClientsListWithAccountId();
    }

    /**
     * Method under test: {@link UtilityServices#getRDSClientsListWithAccountId()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetRDSClientsListWithAccountId3() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.amazonaws.auth.AWSCredentials.getAWSAccessKeyId()" because "credentials" is null
        //       at com.vermeg.aws.cost.optimization.dashboard.services.UtilityServices.getRDSClientsListWithAccountId(UtilityServices.java:85)
        //   See https://diff.blue/R013 to resolve this issue.

        ArrayList<AWSCredentials> awsCredentialsList = new ArrayList<>();
        awsCredentialsList.add(null);
        when(awsCredentialsService.getAccountId(Mockito.<String>any(), Mockito.<String>any())).thenReturn("42");
        when(awsCredentialsService.getAllCredentials()).thenReturn(awsCredentialsList);
        utilityServices.getRDSClientsListWithAccountId();
    }

    /**
     * Method under test: {@link UtilityServices#getRDSClientsListWithAccountId()}
     */
    @Test
    void testGetRDSClientsListWithAccountId4() {
        ArrayList<AWSCredentials> awsCredentialsList = new ArrayList<>();
        awsCredentialsList
                .add(new BasicAWSCredentials("EXAMPLEakiAIOSFODNN7", "EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY"));
        when(awsCredentialsService.getAccountId(Mockito.<String>any(), Mockito.<String>any())).thenReturn("42");
        when(awsCredentialsService.getAllCredentials()).thenReturn(awsCredentialsList);
        List<Pair<AmazonRDS, String>> actualRDSClientsListWithAccountId = utilityServices
                .getRDSClientsListWithAccountId();
        assertEquals(17, actualRDSClientsListWithAccountId.size());
        Pair<AmazonRDS, String> getResult = actualRDSClientsListWithAccountId.get(4);
        assertEquals("42", getResult.getValue());
        Pair<AmazonRDS, String> getResult2 = actualRDSClientsListWithAccountId.get(11);
        assertEquals("42", getResult2.getValue());
        assertTrue(getResult2.getLeft() instanceof AmazonRDSClient);
        assertTrue(getResult.getLeft() instanceof AmazonRDSClient);
        Pair<AmazonRDS, String> getResult3 = actualRDSClientsListWithAccountId.get(5);
        assertEquals("42", getResult3.getRight());
        assertTrue(getResult3.getLeft() instanceof AmazonRDSClient);
        Pair<AmazonRDS, String> getResult4 = actualRDSClientsListWithAccountId.get(2);
        assertEquals("42", getResult4.getRight());
        assertTrue(getResult4.getLeft() instanceof AmazonRDSClient);
        Pair<AmazonRDS, String> getResult5 = actualRDSClientsListWithAccountId.get(15);
        assertEquals("42", getResult5.getRight());
        assertTrue(getResult5.getLeft() instanceof AmazonRDSClient);
        Pair<AmazonRDS, String> getResult6 = actualRDSClientsListWithAccountId.get(14);
        assertEquals("42", getResult6.getRight());
        assertTrue(getResult6.getLeft() instanceof AmazonRDSClient);
        Pair<AmazonRDS, String> getResult7 = actualRDSClientsListWithAccountId.get(13);
        assertEquals("42", getResult7.getRight());
        assertTrue(getResult7.getLeft() instanceof AmazonRDSClient);
        Pair<AmazonRDS, String> getResult8 = actualRDSClientsListWithAccountId.get(12);
        assertEquals("42", getResult8.getRight());
        assertTrue(getResult8.getLeft() instanceof AmazonRDSClient);
        Pair<AmazonRDS, String> getResult9 = actualRDSClientsListWithAccountId.get(3);
        assertEquals("42", getResult9.getRight());
        Pair<AmazonRDS, String> getResult10 = actualRDSClientsListWithAccountId.get(1);
        assertEquals("42", getResult10.getRight());
        Pair<AmazonRDS, String> getResult11 = actualRDSClientsListWithAccountId.get(Short.SIZE);
        assertEquals("42", getResult11.getRight());
        Pair<AmazonRDS, String> getResult12 = actualRDSClientsListWithAccountId.get(0);
        assertEquals("42", getResult12.getRight());
        assertTrue(getResult12.getLeft() instanceof AmazonRDSClient);
        assertSame(getResult11.getLeft(), getResult11.getKey());
        assertSame(getResult10.getLeft(), getResult10.getKey());
        assertSame(getResult9.getLeft(), getResult9.getKey());
        verify(awsCredentialsService).getAccountId(Mockito.<String>any(), Mockito.<String>any());
        verify(awsCredentialsService).getAllCredentials();
    }

    /**
     * Method under test: {@link UtilityServices#getRDSClientsListWithAccountId()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetRDSClientsListWithAccountId5() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy97.connect(Unknown Source)
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
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.executeGetSessionToken(AWSSecurityTokenServiceClient.java:1624)
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.getSessionToken(AWSSecurityTokenServiceClient.java:1592)
            //       at com.amazonaws.auth.STSSessionCredentials.refreshCredentials(STSSessionCredentials.java:149)
            //       at com.amazonaws.auth.STSSessionCredentials.getSessionCredentials(STSSessionCredentials.java:158)
            //       at com.amazonaws.auth.STSSessionCredentials.getAWSAccessKeyId(STSSessionCredentials.java:108)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.UtilityServices.getRDSClientsListWithAccountId(UtilityServices.java:85)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});

            ArrayList<AWSCredentials> awsCredentialsList = new ArrayList<>();
            awsCredentialsList.add(new STSSessionCredentials(new AWSSecurityTokenServiceAsyncClient()));
            when(awsCredentialsService.getAccountId(Mockito.<String>any(), Mockito.<String>any())).thenReturn("42");
            when(awsCredentialsService.getAllCredentials()).thenReturn(awsCredentialsList);
            utilityServices.getRDSClientsListWithAccountId();
        }
    }

    /**
     * Method under test: {@link UtilityServices#getRDSClientsListWithAccountId()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetRDSClientsListWithAccountId6() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy97.connect(Unknown Source)
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
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.executeGetSessionToken(AWSSecurityTokenServiceClient.java:1624)
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.getSessionToken(AWSSecurityTokenServiceClient.java:1592)
            //       at com.amazonaws.auth.STSSessionCredentials.refreshCredentials(STSSessionCredentials.java:149)
            //       at com.amazonaws.auth.STSSessionCredentials.getSessionCredentials(STSSessionCredentials.java:158)
            //       at com.amazonaws.auth.STSSessionCredentials.getAWSAccessKeyId(STSSessionCredentials.java:108)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.UtilityServices.getRDSClientsListWithAccountId(UtilityServices.java:85)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});

            ArrayList<AWSCredentials> awsCredentialsList = new ArrayList<>();
            awsCredentialsList
                    .add(new STSSessionCredentials(new AWSSecurityTokenServiceAsyncClient(new AnonymousAWSCredentials())));
            when(awsCredentialsService.getAccountId(Mockito.<String>any(), Mockito.<String>any())).thenReturn("42");
            when(awsCredentialsService.getAllCredentials()).thenReturn(awsCredentialsList);
            utilityServices.getRDSClientsListWithAccountId();
        }
    }

    /**
     * Method under test: {@link UtilityServices#getRDSClientsListWithAccountId()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetRDSClientsListWithAccountId7() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy97.connect(Unknown Source)
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
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.executeGetSessionToken(AWSSecurityTokenServiceClient.java:1624)
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.getSessionToken(AWSSecurityTokenServiceClient.java:1592)
            //       at com.amazonaws.auth.STSSessionCredentials.refreshCredentials(STSSessionCredentials.java:149)
            //       at com.amazonaws.auth.STSSessionCredentials.getSessionCredentials(STSSessionCredentials.java:158)
            //       at com.amazonaws.auth.STSSessionCredentials.getAWSAccessKeyId(STSSessionCredentials.java:108)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.UtilityServices.getRDSClientsListWithAccountId(UtilityServices.java:85)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            BasicAWSCredentials awsCredentials = mock(BasicAWSCredentials.class);
            when(awsCredentials.getAWSAccessKeyId()).thenReturn("EXAMPLEakiAIOSFODNN7");
            when(awsCredentials.getAWSSecretKey()).thenReturn("EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY");
            STSSessionCredentials stsSessionCredentials = new STSSessionCredentials(
                    new AWSSecurityTokenServiceAsyncClient(awsCredentials));

            ArrayList<AWSCredentials> awsCredentialsList = new ArrayList<>();
            awsCredentialsList.add(stsSessionCredentials);
            when(awsCredentialsService.getAccountId(Mockito.<String>any(), Mockito.<String>any())).thenReturn("42");
            when(awsCredentialsService.getAllCredentials()).thenReturn(awsCredentialsList);
            utilityServices.getRDSClientsListWithAccountId();
        }
    }

    /**
     * Method under test: {@link UtilityServices#getRDSClientsListWithAccountId()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetRDSClientsListWithAccountId8() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy97.connect(Unknown Source)
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
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.executeGetSessionToken(AWSSecurityTokenServiceClient.java:1624)
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.getSessionToken(AWSSecurityTokenServiceClient.java:1592)
            //       at com.amazonaws.auth.STSSessionCredentials.refreshCredentials(STSSessionCredentials.java:149)
            //       at com.amazonaws.auth.STSSessionCredentials.getSessionCredentials(STSSessionCredentials.java:158)
            //       at com.amazonaws.auth.STSSessionCredentials.getAWSAccessKeyId(STSSessionCredentials.java:108)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.UtilityServices.getRDSClientsListWithAccountId(UtilityServices.java:85)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});

            ArrayList<AWSCredentials> awsCredentialsList = new ArrayList<>();
            awsCredentialsList.add(new STSSessionCredentials(new AWSSecurityTokenServiceAsyncClient()));
            when(awsCredentialsService.getAccountId(Mockito.<String>any(), Mockito.<String>any())).thenReturn("42");
            when(awsCredentialsService.getAllCredentials()).thenReturn(awsCredentialsList);
            utilityServices.getRDSClientsListWithAccountId();
        }
    }

    /**
     * Method under test: {@link UtilityServices#getRDSClientsListWithAccountId()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetRDSClientsListWithAccountId9() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy97.connect(Unknown Source)
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
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.executeGetSessionToken(AWSSecurityTokenServiceClient.java:1624)
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.getSessionToken(AWSSecurityTokenServiceClient.java:1592)
            //       at com.amazonaws.auth.STSSessionCredentials.refreshCredentials(STSSessionCredentials.java:149)
            //       at com.amazonaws.auth.STSSessionCredentials.getSessionCredentials(STSSessionCredentials.java:158)
            //       at com.amazonaws.auth.STSSessionCredentials.getAWSAccessKeyId(STSSessionCredentials.java:108)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.UtilityServices.getRDSClientsListWithAccountId(UtilityServices.java:85)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});

            ArrayList<AWSCredentials> awsCredentialsList = new ArrayList<>();
            awsCredentialsList
                    .add(new STSSessionCredentials(new AWSSecurityTokenServiceAsyncClient(new AnonymousAWSCredentials())));
            when(awsCredentialsService.getAccountId(Mockito.<String>any(), Mockito.<String>any())).thenReturn("42");
            when(awsCredentialsService.getAllCredentials()).thenReturn(awsCredentialsList);
            utilityServices.getRDSClientsListWithAccountId();
        }
    }

    /**
     * Method under test: {@link UtilityServices#getRDSClientsListWithAccountId()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetRDSClientsListWithAccountId10() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy97.connect(Unknown Source)
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
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.executeGetSessionToken(AWSSecurityTokenServiceClient.java:1624)
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.getSessionToken(AWSSecurityTokenServiceClient.java:1592)
            //       at com.amazonaws.auth.STSSessionCredentials.refreshCredentials(STSSessionCredentials.java:149)
            //       at com.amazonaws.auth.STSSessionCredentials.getSessionCredentials(STSSessionCredentials.java:158)
            //       at com.amazonaws.auth.STSSessionCredentials.getAWSAccessKeyId(STSSessionCredentials.java:108)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.UtilityServices.getRDSClientsListWithAccountId(UtilityServices.java:85)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            BasicAWSCredentials awsCredentials = mock(BasicAWSCredentials.class);
            when(awsCredentials.getAWSAccessKeyId()).thenReturn("EXAMPLEakiAIOSFODNN7");
            when(awsCredentials.getAWSSecretKey()).thenReturn("EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY");
            STSSessionCredentials stsSessionCredentials = new STSSessionCredentials(
                    new AWSSecurityTokenServiceAsyncClient(awsCredentials));

            ArrayList<AWSCredentials> awsCredentialsList = new ArrayList<>();
            awsCredentialsList.add(stsSessionCredentials);
            when(awsCredentialsService.getAccountId(Mockito.<String>any(), Mockito.<String>any())).thenReturn("42");
            when(awsCredentialsService.getAllCredentials()).thenReturn(awsCredentialsList);
            utilityServices.getRDSClientsListWithAccountId();
        }
    }

    /**
     * Method under test: {@link UtilityServices#getS3ClientsList()}
     */
    @Test
    void testGetS3ClientsList() {
        when(awsCredentialsService.getAllCredentials()).thenReturn(new ArrayList<>());
        assertTrue(utilityServices.getS3ClientsList().isEmpty());
        verify(awsCredentialsService).getAllCredentials();
    }

    /**
     * Method under test: {@link UtilityServices#getS3ClientsList()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetS3ClientsList2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.IllegalArgumentException: Access key cannot be null.
        //       at com.amazonaws.auth.BasicAWSCredentials.<init>(BasicAWSCredentials.java:37)
        //       at com.vermeg.aws.cost.optimization.dashboard.services.UtilityServices.getS3ClientsList(UtilityServices.java:105)
        //   See https://diff.blue/R013 to resolve this issue.

        ArrayList<AWSCredentials> awsCredentialsList = new ArrayList<>();
        awsCredentialsList.add(new AnonymousAWSCredentials());
        when(awsCredentialsService.getAccountId(Mockito.<String>any(), Mockito.<String>any())).thenReturn("42");
        when(awsCredentialsService.getAllCredentials()).thenReturn(awsCredentialsList);
        utilityServices.getS3ClientsList();
    }

    /**
     * Method under test: {@link UtilityServices#getS3ClientsList()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetS3ClientsList3() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.amazonaws.auth.AWSCredentials.getAWSAccessKeyId()" because "credentials" is null
        //       at com.vermeg.aws.cost.optimization.dashboard.services.UtilityServices.getS3ClientsList(UtilityServices.java:102)
        //   See https://diff.blue/R013 to resolve this issue.

        ArrayList<AWSCredentials> awsCredentialsList = new ArrayList<>();
        awsCredentialsList.add(null);
        when(awsCredentialsService.getAccountId(Mockito.<String>any(), Mockito.<String>any())).thenReturn("42");
        when(awsCredentialsService.getAllCredentials()).thenReturn(awsCredentialsList);
        utilityServices.getS3ClientsList();
    }

    /**
     * Method under test: {@link UtilityServices#getS3ClientsList()}
     */
    @Test
    void testGetS3ClientsList4() {
        ArrayList<AWSCredentials> awsCredentialsList = new ArrayList<>();
        awsCredentialsList
                .add(new BasicAWSCredentials("EXAMPLEakiAIOSFODNN7", "EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY"));
        when(awsCredentialsService.getAccountId(Mockito.<String>any(), Mockito.<String>any())).thenReturn("42");
        when(awsCredentialsService.getAllCredentials()).thenReturn(awsCredentialsList);
        List<Pair<AmazonS3, String>> actualS3ClientsList = utilityServices.getS3ClientsList();
        assertEquals(17, actualS3ClientsList.size());
        Pair<AmazonS3, String> getResult = actualS3ClientsList.get(4);
        assertEquals("42", getResult.getValue());
        Pair<AmazonS3, String> getResult2 = actualS3ClientsList.get(11);
        assertEquals("42", getResult2.getValue());
        assertTrue(getResult2.getLeft() instanceof AmazonS3Client);
        assertTrue(getResult.getLeft() instanceof AmazonS3Client);
        Pair<AmazonS3, String> getResult3 = actualS3ClientsList.get(5);
        assertEquals("42", getResult3.getRight());
        assertTrue(getResult3.getLeft() instanceof AmazonS3Client);
        Pair<AmazonS3, String> getResult4 = actualS3ClientsList.get(2);
        assertEquals("42", getResult4.getRight());
        assertTrue(getResult4.getLeft() instanceof AmazonS3Client);
        Pair<AmazonS3, String> getResult5 = actualS3ClientsList.get(15);
        assertEquals("42", getResult5.getRight());
        assertTrue(getResult5.getLeft() instanceof AmazonS3Client);
        Pair<AmazonS3, String> getResult6 = actualS3ClientsList.get(14);
        assertEquals("42", getResult6.getRight());
        assertTrue(getResult6.getLeft() instanceof AmazonS3Client);
        Pair<AmazonS3, String> getResult7 = actualS3ClientsList.get(13);
        assertEquals("42", getResult7.getRight());
        assertTrue(getResult7.getLeft() instanceof AmazonS3Client);
        Pair<AmazonS3, String> getResult8 = actualS3ClientsList.get(12);
        assertEquals("42", getResult8.getRight());
        assertTrue(getResult8.getLeft() instanceof AmazonS3Client);
        Pair<AmazonS3, String> getResult9 = actualS3ClientsList.get(3);
        assertEquals("42", getResult9.getRight());
        Pair<AmazonS3, String> getResult10 = actualS3ClientsList.get(1);
        assertEquals("42", getResult10.getRight());
        Pair<AmazonS3, String> getResult11 = actualS3ClientsList.get(Short.SIZE);
        assertEquals("42", getResult11.getRight());
        Pair<AmazonS3, String> getResult12 = actualS3ClientsList.get(0);
        assertEquals("42", getResult12.getRight());
        assertTrue(getResult12.getLeft() instanceof AmazonS3Client);
        assertSame(getResult11.getLeft(), getResult11.getKey());
        assertSame(getResult10.getLeft(), getResult10.getKey());
        assertSame(getResult9.getLeft(), getResult9.getKey());
        verify(awsCredentialsService).getAccountId(Mockito.<String>any(), Mockito.<String>any());
        verify(awsCredentialsService).getAllCredentials();
    }

    /**
     * Method under test: {@link UtilityServices#getS3ClientsList()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetS3ClientsList5() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy97.connect(Unknown Source)
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
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.executeGetSessionToken(AWSSecurityTokenServiceClient.java:1624)
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.getSessionToken(AWSSecurityTokenServiceClient.java:1592)
            //       at com.amazonaws.auth.STSSessionCredentials.refreshCredentials(STSSessionCredentials.java:149)
            //       at com.amazonaws.auth.STSSessionCredentials.getSessionCredentials(STSSessionCredentials.java:158)
            //       at com.amazonaws.auth.STSSessionCredentials.getAWSAccessKeyId(STSSessionCredentials.java:108)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.UtilityServices.getS3ClientsList(UtilityServices.java:102)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});

            ArrayList<AWSCredentials> awsCredentialsList = new ArrayList<>();
            awsCredentialsList.add(new STSSessionCredentials(new AWSSecurityTokenServiceAsyncClient()));
            when(awsCredentialsService.getAccountId(Mockito.<String>any(), Mockito.<String>any())).thenReturn("42");
            when(awsCredentialsService.getAllCredentials()).thenReturn(awsCredentialsList);
            utilityServices.getS3ClientsList();
        }
    }

    /**
     * Method under test: {@link UtilityServices#getS3ClientsList()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetS3ClientsList6() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy97.connect(Unknown Source)
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
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.executeGetSessionToken(AWSSecurityTokenServiceClient.java:1624)
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.getSessionToken(AWSSecurityTokenServiceClient.java:1592)
            //       at com.amazonaws.auth.STSSessionCredentials.refreshCredentials(STSSessionCredentials.java:149)
            //       at com.amazonaws.auth.STSSessionCredentials.getSessionCredentials(STSSessionCredentials.java:158)
            //       at com.amazonaws.auth.STSSessionCredentials.getAWSAccessKeyId(STSSessionCredentials.java:108)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.UtilityServices.getS3ClientsList(UtilityServices.java:102)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});

            ArrayList<AWSCredentials> awsCredentialsList = new ArrayList<>();
            awsCredentialsList
                    .add(new STSSessionCredentials(new AWSSecurityTokenServiceAsyncClient(new AnonymousAWSCredentials())));
            when(awsCredentialsService.getAccountId(Mockito.<String>any(), Mockito.<String>any())).thenReturn("42");
            when(awsCredentialsService.getAllCredentials()).thenReturn(awsCredentialsList);
            utilityServices.getS3ClientsList();
        }
    }

    /**
     * Method under test: {@link UtilityServices#getS3ClientsList()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetS3ClientsList7() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy97.connect(Unknown Source)
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
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.executeGetSessionToken(AWSSecurityTokenServiceClient.java:1624)
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.getSessionToken(AWSSecurityTokenServiceClient.java:1592)
            //       at com.amazonaws.auth.STSSessionCredentials.refreshCredentials(STSSessionCredentials.java:149)
            //       at com.amazonaws.auth.STSSessionCredentials.getSessionCredentials(STSSessionCredentials.java:158)
            //       at com.amazonaws.auth.STSSessionCredentials.getAWSAccessKeyId(STSSessionCredentials.java:108)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.UtilityServices.getS3ClientsList(UtilityServices.java:102)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            BasicAWSCredentials awsCredentials = mock(BasicAWSCredentials.class);
            when(awsCredentials.getAWSAccessKeyId()).thenReturn("EXAMPLEakiAIOSFODNN7");
            when(awsCredentials.getAWSSecretKey()).thenReturn("EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY");
            STSSessionCredentials stsSessionCredentials = new STSSessionCredentials(
                    new AWSSecurityTokenServiceAsyncClient(awsCredentials));

            ArrayList<AWSCredentials> awsCredentialsList = new ArrayList<>();
            awsCredentialsList.add(stsSessionCredentials);
            when(awsCredentialsService.getAccountId(Mockito.<String>any(), Mockito.<String>any())).thenReturn("42");
            when(awsCredentialsService.getAllCredentials()).thenReturn(awsCredentialsList);
            utilityServices.getS3ClientsList();
        }
    }

    /**
     * Method under test: {@link UtilityServices#getS3ClientsList()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetS3ClientsList8() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy97.connect(Unknown Source)
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
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.executeGetSessionToken(AWSSecurityTokenServiceClient.java:1624)
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.getSessionToken(AWSSecurityTokenServiceClient.java:1592)
            //       at com.amazonaws.auth.STSSessionCredentials.refreshCredentials(STSSessionCredentials.java:149)
            //       at com.amazonaws.auth.STSSessionCredentials.getSessionCredentials(STSSessionCredentials.java:158)
            //       at com.amazonaws.auth.STSSessionCredentials.getAWSAccessKeyId(STSSessionCredentials.java:108)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.UtilityServices.getS3ClientsList(UtilityServices.java:102)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});

            ArrayList<AWSCredentials> awsCredentialsList = new ArrayList<>();
            awsCredentialsList.add(new STSSessionCredentials(new AWSSecurityTokenServiceAsyncClient()));
            when(awsCredentialsService.getAccountId(Mockito.<String>any(), Mockito.<String>any())).thenReturn("42");
            when(awsCredentialsService.getAllCredentials()).thenReturn(awsCredentialsList);
            utilityServices.getS3ClientsList();
        }
    }

    /**
     * Method under test: {@link UtilityServices#getS3ClientsList()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetS3ClientsList9() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy97.connect(Unknown Source)
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
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.executeGetSessionToken(AWSSecurityTokenServiceClient.java:1624)
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.getSessionToken(AWSSecurityTokenServiceClient.java:1592)
            //       at com.amazonaws.auth.STSSessionCredentials.refreshCredentials(STSSessionCredentials.java:149)
            //       at com.amazonaws.auth.STSSessionCredentials.getSessionCredentials(STSSessionCredentials.java:158)
            //       at com.amazonaws.auth.STSSessionCredentials.getAWSAccessKeyId(STSSessionCredentials.java:108)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.UtilityServices.getS3ClientsList(UtilityServices.java:102)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});

            ArrayList<AWSCredentials> awsCredentialsList = new ArrayList<>();
            awsCredentialsList
                    .add(new STSSessionCredentials(new AWSSecurityTokenServiceAsyncClient(new AnonymousAWSCredentials())));
            when(awsCredentialsService.getAccountId(Mockito.<String>any(), Mockito.<String>any())).thenReturn("42");
            when(awsCredentialsService.getAllCredentials()).thenReturn(awsCredentialsList);
            utilityServices.getS3ClientsList();
        }
    }

    /**
     * Method under test: {@link UtilityServices#getS3ClientsList()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetS3ClientsList10() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy97.connect(Unknown Source)
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
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.executeGetSessionToken(AWSSecurityTokenServiceClient.java:1624)
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.getSessionToken(AWSSecurityTokenServiceClient.java:1592)
            //       at com.amazonaws.auth.STSSessionCredentials.refreshCredentials(STSSessionCredentials.java:149)
            //       at com.amazonaws.auth.STSSessionCredentials.getSessionCredentials(STSSessionCredentials.java:158)
            //       at com.amazonaws.auth.STSSessionCredentials.getAWSAccessKeyId(STSSessionCredentials.java:108)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.UtilityServices.getS3ClientsList(UtilityServices.java:102)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            BasicAWSCredentials awsCredentials = mock(BasicAWSCredentials.class);
            when(awsCredentials.getAWSAccessKeyId()).thenReturn("EXAMPLEakiAIOSFODNN7");
            when(awsCredentials.getAWSSecretKey()).thenReturn("EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY");
            STSSessionCredentials stsSessionCredentials = new STSSessionCredentials(
                    new AWSSecurityTokenServiceAsyncClient(awsCredentials));

            ArrayList<AWSCredentials> awsCredentialsList = new ArrayList<>();
            awsCredentialsList.add(stsSessionCredentials);
            when(awsCredentialsService.getAccountId(Mockito.<String>any(), Mockito.<String>any())).thenReturn("42");
            when(awsCredentialsService.getAllCredentials()).thenReturn(awsCredentialsList);
            utilityServices.getS3ClientsList();
        }
    }

    /**
     * Method under test: {@link UtilityServices#getTrustedAdvisorClientsList()}
     */
    @Test
    void testGetTrustedAdvisorClientsList() {
        when(awsCredentialsService.getAllCredentials()).thenReturn(new ArrayList<>());
        assertTrue(utilityServices.getTrustedAdvisorClientsList().isEmpty());
        verify(awsCredentialsService).getAllCredentials();
    }

    /**
     * Method under test: {@link UtilityServices#getTrustedAdvisorClientsList()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetTrustedAdvisorClientsList2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.IllegalArgumentException: Access key cannot be null.
        //       at com.amazonaws.auth.BasicAWSCredentials.<init>(BasicAWSCredentials.java:37)
        //       at com.vermeg.aws.cost.optimization.dashboard.services.UtilityServices.getTrustedAdvisorClientsList(UtilityServices.java:123)
        //   See https://diff.blue/R013 to resolve this issue.

        ArrayList<AWSCredentials> awsCredentialsList = new ArrayList<>();
        awsCredentialsList.add(new AnonymousAWSCredentials());
        when(awsCredentialsService.getAccountId(Mockito.<String>any(), Mockito.<String>any())).thenReturn("42");
        when(awsCredentialsService.getAllCredentials()).thenReturn(awsCredentialsList);
        utilityServices.getTrustedAdvisorClientsList();
    }

    /**
     * Method under test: {@link UtilityServices#getTrustedAdvisorClientsList()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetTrustedAdvisorClientsList3() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.amazonaws.auth.AWSCredentials.getAWSAccessKeyId()" because "credentials" is null
        //       at com.vermeg.aws.cost.optimization.dashboard.services.UtilityServices.getTrustedAdvisorClientsList(UtilityServices.java:120)
        //   See https://diff.blue/R013 to resolve this issue.

        ArrayList<AWSCredentials> awsCredentialsList = new ArrayList<>();
        awsCredentialsList.add(null);
        when(awsCredentialsService.getAccountId(Mockito.<String>any(), Mockito.<String>any())).thenReturn("42");
        when(awsCredentialsService.getAllCredentials()).thenReturn(awsCredentialsList);
        utilityServices.getTrustedAdvisorClientsList();
    }

    /**
     * Method under test: {@link UtilityServices#getTrustedAdvisorClientsList()}
     */
    @Test
    void testGetTrustedAdvisorClientsList4() {
        ArrayList<AWSCredentials> awsCredentialsList = new ArrayList<>();
        awsCredentialsList
                .add(new BasicAWSCredentials("EXAMPLEakiAIOSFODNN7", "EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY"));
        when(awsCredentialsService.getAccountId(Mockito.<String>any(), Mockito.<String>any())).thenReturn("42");
        when(awsCredentialsService.getAllCredentials()).thenReturn(awsCredentialsList);
        List<Pair<AWSSupport, String>> actualTrustedAdvisorClientsList = utilityServices.getTrustedAdvisorClientsList();
        assertEquals(17, actualTrustedAdvisorClientsList.size());
        Pair<AWSSupport, String> getResult = actualTrustedAdvisorClientsList.get(4);
        assertEquals("42", getResult.getValue());
        Pair<AWSSupport, String> getResult2 = actualTrustedAdvisorClientsList.get(11);
        assertEquals("42", getResult2.getValue());
        assertTrue(getResult2.getLeft() instanceof AWSSupportClient);
        assertTrue(getResult.getLeft() instanceof AWSSupportClient);
        Pair<AWSSupport, String> getResult3 = actualTrustedAdvisorClientsList.get(5);
        assertEquals("42", getResult3.getRight());
        assertTrue(getResult3.getLeft() instanceof AWSSupportClient);
        Pair<AWSSupport, String> getResult4 = actualTrustedAdvisorClientsList.get(2);
        assertEquals("42", getResult4.getRight());
        assertTrue(getResult4.getLeft() instanceof AWSSupportClient);
        Pair<AWSSupport, String> getResult5 = actualTrustedAdvisorClientsList.get(15);
        assertEquals("42", getResult5.getRight());
        assertTrue(getResult5.getLeft() instanceof AWSSupportClient);
        Pair<AWSSupport, String> getResult6 = actualTrustedAdvisorClientsList.get(14);
        assertEquals("42", getResult6.getRight());
        assertTrue(getResult6.getLeft() instanceof AWSSupportClient);
        Pair<AWSSupport, String> getResult7 = actualTrustedAdvisorClientsList.get(13);
        assertEquals("42", getResult7.getRight());
        assertTrue(getResult7.getLeft() instanceof AWSSupportClient);
        Pair<AWSSupport, String> getResult8 = actualTrustedAdvisorClientsList.get(12);
        assertEquals("42", getResult8.getRight());
        assertTrue(getResult8.getLeft() instanceof AWSSupportClient);
        Pair<AWSSupport, String> getResult9 = actualTrustedAdvisorClientsList.get(3);
        assertEquals("42", getResult9.getRight());
        Pair<AWSSupport, String> getResult10 = actualTrustedAdvisorClientsList.get(1);
        assertEquals("42", getResult10.getRight());
        Pair<AWSSupport, String> getResult11 = actualTrustedAdvisorClientsList.get(Short.SIZE);
        assertEquals("42", getResult11.getRight());
        Pair<AWSSupport, String> getResult12 = actualTrustedAdvisorClientsList.get(0);
        assertEquals("42", getResult12.getRight());
        assertTrue(getResult12.getLeft() instanceof AWSSupportClient);
        assertSame(getResult11.getLeft(), getResult11.getKey());
        assertSame(getResult10.getLeft(), getResult10.getKey());
        assertSame(getResult9.getLeft(), getResult9.getKey());
        verify(awsCredentialsService).getAccountId(Mockito.<String>any(), Mockito.<String>any());
        verify(awsCredentialsService).getAllCredentials();
    }

    /**
     * Method under test: {@link UtilityServices#getTrustedAdvisorClientsList()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetTrustedAdvisorClientsList5() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy97.connect(Unknown Source)
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
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.executeGetSessionToken(AWSSecurityTokenServiceClient.java:1624)
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.getSessionToken(AWSSecurityTokenServiceClient.java:1592)
            //       at com.amazonaws.auth.STSSessionCredentials.refreshCredentials(STSSessionCredentials.java:149)
            //       at com.amazonaws.auth.STSSessionCredentials.getSessionCredentials(STSSessionCredentials.java:158)
            //       at com.amazonaws.auth.STSSessionCredentials.getAWSAccessKeyId(STSSessionCredentials.java:108)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.UtilityServices.getTrustedAdvisorClientsList(UtilityServices.java:120)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});

            ArrayList<AWSCredentials> awsCredentialsList = new ArrayList<>();
            awsCredentialsList.add(new STSSessionCredentials(new AWSSecurityTokenServiceAsyncClient()));
            when(awsCredentialsService.getAccountId(Mockito.<String>any(), Mockito.<String>any())).thenReturn("42");
            when(awsCredentialsService.getAllCredentials()).thenReturn(awsCredentialsList);
            utilityServices.getTrustedAdvisorClientsList();
        }
    }

    /**
     * Method under test: {@link UtilityServices#getTrustedAdvisorClientsList()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetTrustedAdvisorClientsList6() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy97.connect(Unknown Source)
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
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.executeGetSessionToken(AWSSecurityTokenServiceClient.java:1624)
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.getSessionToken(AWSSecurityTokenServiceClient.java:1592)
            //       at com.amazonaws.auth.STSSessionCredentials.refreshCredentials(STSSessionCredentials.java:149)
            //       at com.amazonaws.auth.STSSessionCredentials.getSessionCredentials(STSSessionCredentials.java:158)
            //       at com.amazonaws.auth.STSSessionCredentials.getAWSAccessKeyId(STSSessionCredentials.java:108)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.UtilityServices.getTrustedAdvisorClientsList(UtilityServices.java:120)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});

            ArrayList<AWSCredentials> awsCredentialsList = new ArrayList<>();
            awsCredentialsList
                    .add(new STSSessionCredentials(new AWSSecurityTokenServiceAsyncClient(new AnonymousAWSCredentials())));
            when(awsCredentialsService.getAccountId(Mockito.<String>any(), Mockito.<String>any())).thenReturn("42");
            when(awsCredentialsService.getAllCredentials()).thenReturn(awsCredentialsList);
            utilityServices.getTrustedAdvisorClientsList();
        }
    }

    /**
     * Method under test: {@link UtilityServices#getTrustedAdvisorClientsList()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetTrustedAdvisorClientsList7() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy97.connect(Unknown Source)
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
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.executeGetSessionToken(AWSSecurityTokenServiceClient.java:1624)
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.getSessionToken(AWSSecurityTokenServiceClient.java:1592)
            //       at com.amazonaws.auth.STSSessionCredentials.refreshCredentials(STSSessionCredentials.java:149)
            //       at com.amazonaws.auth.STSSessionCredentials.getSessionCredentials(STSSessionCredentials.java:158)
            //       at com.amazonaws.auth.STSSessionCredentials.getAWSAccessKeyId(STSSessionCredentials.java:108)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.UtilityServices.getTrustedAdvisorClientsList(UtilityServices.java:120)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            BasicAWSCredentials awsCredentials = mock(BasicAWSCredentials.class);
            when(awsCredentials.getAWSAccessKeyId()).thenReturn("EXAMPLEakiAIOSFODNN7");
            when(awsCredentials.getAWSSecretKey()).thenReturn("EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY");
            STSSessionCredentials stsSessionCredentials = new STSSessionCredentials(
                    new AWSSecurityTokenServiceAsyncClient(awsCredentials));

            ArrayList<AWSCredentials> awsCredentialsList = new ArrayList<>();
            awsCredentialsList.add(stsSessionCredentials);
            when(awsCredentialsService.getAccountId(Mockito.<String>any(), Mockito.<String>any())).thenReturn("42");
            when(awsCredentialsService.getAllCredentials()).thenReturn(awsCredentialsList);
            utilityServices.getTrustedAdvisorClientsList();
        }
    }

    /**
     * Method under test: {@link UtilityServices#getTrustedAdvisorClientsList()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetTrustedAdvisorClientsList8() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy97.connect(Unknown Source)
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
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.executeGetSessionToken(AWSSecurityTokenServiceClient.java:1624)
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.getSessionToken(AWSSecurityTokenServiceClient.java:1592)
            //       at com.amazonaws.auth.STSSessionCredentials.refreshCredentials(STSSessionCredentials.java:149)
            //       at com.amazonaws.auth.STSSessionCredentials.getSessionCredentials(STSSessionCredentials.java:158)
            //       at com.amazonaws.auth.STSSessionCredentials.getAWSAccessKeyId(STSSessionCredentials.java:108)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.UtilityServices.getTrustedAdvisorClientsList(UtilityServices.java:120)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});

            ArrayList<AWSCredentials> awsCredentialsList = new ArrayList<>();
            awsCredentialsList.add(new STSSessionCredentials(new AWSSecurityTokenServiceAsyncClient()));
            when(awsCredentialsService.getAccountId(Mockito.<String>any(), Mockito.<String>any())).thenReturn("42");
            when(awsCredentialsService.getAllCredentials()).thenReturn(awsCredentialsList);
            utilityServices.getTrustedAdvisorClientsList();
        }
    }

    /**
     * Method under test: {@link UtilityServices#getTrustedAdvisorClientsList()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetTrustedAdvisorClientsList9() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy97.connect(Unknown Source)
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
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.executeGetSessionToken(AWSSecurityTokenServiceClient.java:1624)
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.getSessionToken(AWSSecurityTokenServiceClient.java:1592)
            //       at com.amazonaws.auth.STSSessionCredentials.refreshCredentials(STSSessionCredentials.java:149)
            //       at com.amazonaws.auth.STSSessionCredentials.getSessionCredentials(STSSessionCredentials.java:158)
            //       at com.amazonaws.auth.STSSessionCredentials.getAWSAccessKeyId(STSSessionCredentials.java:108)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.UtilityServices.getTrustedAdvisorClientsList(UtilityServices.java:120)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});

            ArrayList<AWSCredentials> awsCredentialsList = new ArrayList<>();
            awsCredentialsList
                    .add(new STSSessionCredentials(new AWSSecurityTokenServiceAsyncClient(new AnonymousAWSCredentials())));
            when(awsCredentialsService.getAccountId(Mockito.<String>any(), Mockito.<String>any())).thenReturn("42");
            when(awsCredentialsService.getAllCredentials()).thenReturn(awsCredentialsList);
            utilityServices.getTrustedAdvisorClientsList();
        }
    }

    /**
     * Method under test: {@link UtilityServices#getTrustedAdvisorClientsList()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetTrustedAdvisorClientsList10() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy97.connect(Unknown Source)
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
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.executeGetSessionToken(AWSSecurityTokenServiceClient.java:1624)
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.getSessionToken(AWSSecurityTokenServiceClient.java:1592)
            //       at com.amazonaws.auth.STSSessionCredentials.refreshCredentials(STSSessionCredentials.java:149)
            //       at com.amazonaws.auth.STSSessionCredentials.getSessionCredentials(STSSessionCredentials.java:158)
            //       at com.amazonaws.auth.STSSessionCredentials.getAWSAccessKeyId(STSSessionCredentials.java:108)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.UtilityServices.getTrustedAdvisorClientsList(UtilityServices.java:120)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            BasicAWSCredentials awsCredentials = mock(BasicAWSCredentials.class);
            when(awsCredentials.getAWSAccessKeyId()).thenReturn("EXAMPLEakiAIOSFODNN7");
            when(awsCredentials.getAWSSecretKey()).thenReturn("EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY");
            STSSessionCredentials stsSessionCredentials = new STSSessionCredentials(
                    new AWSSecurityTokenServiceAsyncClient(awsCredentials));

            ArrayList<AWSCredentials> awsCredentialsList = new ArrayList<>();
            awsCredentialsList.add(stsSessionCredentials);
            when(awsCredentialsService.getAccountId(Mockito.<String>any(), Mockito.<String>any())).thenReturn("42");
            when(awsCredentialsService.getAllCredentials()).thenReturn(awsCredentialsList);
            utilityServices.getTrustedAdvisorClientsList();
        }
    }

    /**
     * Method under test: {@link UtilityServices#getAutoscalingClientsList()}
     */
    @Test
    void testGetAutoscalingClientsList() {
        when(awsCredentialsService.getAllCredentials()).thenReturn(new ArrayList<>());
        assertTrue(utilityServices.getAutoscalingClientsList().isEmpty());
        verify(awsCredentialsService).getAllCredentials();
    }

    /**
     * Method under test: {@link UtilityServices#getAutoscalingClientsList()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetAutoscalingClientsList2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.IllegalArgumentException: Access key cannot be null.
        //       at com.amazonaws.auth.BasicAWSCredentials.<init>(BasicAWSCredentials.java:37)
        //       at com.vermeg.aws.cost.optimization.dashboard.services.UtilityServices.getAutoscalingClientsList(UtilityServices.java:140)
        //   See https://diff.blue/R013 to resolve this issue.

        ArrayList<AWSCredentials> awsCredentialsList = new ArrayList<>();
        awsCredentialsList.add(new AnonymousAWSCredentials());
        when(awsCredentialsService.getAccountId(Mockito.<String>any(), Mockito.<String>any())).thenReturn("42");
        when(awsCredentialsService.getAllCredentials()).thenReturn(awsCredentialsList);
        utilityServices.getAutoscalingClientsList();
    }

    /**
     * Method under test: {@link UtilityServices#getAutoscalingClientsList()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetAutoscalingClientsList3() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.amazonaws.auth.AWSCredentials.getAWSAccessKeyId()" because "credentials" is null
        //       at com.vermeg.aws.cost.optimization.dashboard.services.UtilityServices.getAutoscalingClientsList(UtilityServices.java:137)
        //   See https://diff.blue/R013 to resolve this issue.

        ArrayList<AWSCredentials> awsCredentialsList = new ArrayList<>();
        awsCredentialsList.add(null);
        when(awsCredentialsService.getAccountId(Mockito.<String>any(), Mockito.<String>any())).thenReturn("42");
        when(awsCredentialsService.getAllCredentials()).thenReturn(awsCredentialsList);
        utilityServices.getAutoscalingClientsList();
    }

    /**
     * Method under test: {@link UtilityServices#getAutoscalingClientsList()}
     */
    @Test
    void testGetAutoscalingClientsList4() {
        ArrayList<AWSCredentials> awsCredentialsList = new ArrayList<>();
        awsCredentialsList
                .add(new BasicAWSCredentials("EXAMPLEakiAIOSFODNN7", "EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY"));
        when(awsCredentialsService.getAccountId(Mockito.<String>any(), Mockito.<String>any())).thenReturn("42");
        when(awsCredentialsService.getAllCredentials()).thenReturn(awsCredentialsList);
        List<Pair<AmazonAutoScaling, String>> actualAutoscalingClientsList = utilityServices.getAutoscalingClientsList();
        assertEquals(17, actualAutoscalingClientsList.size());
        Pair<AmazonAutoScaling, String> getResult = actualAutoscalingClientsList.get(4);
        assertEquals("42", getResult.getValue());
        Pair<AmazonAutoScaling, String> getResult2 = actualAutoscalingClientsList.get(11);
        assertEquals("42", getResult2.getValue());
        assertTrue(getResult2.getLeft() instanceof AmazonAutoScalingClient);
        assertTrue(getResult.getLeft() instanceof AmazonAutoScalingClient);
        Pair<AmazonAutoScaling, String> getResult3 = actualAutoscalingClientsList.get(5);
        assertEquals("42", getResult3.getRight());
        assertTrue(getResult3.getLeft() instanceof AmazonAutoScalingClient);
        Pair<AmazonAutoScaling, String> getResult4 = actualAutoscalingClientsList.get(2);
        assertEquals("42", getResult4.getRight());
        assertTrue(getResult4.getLeft() instanceof AmazonAutoScalingClient);
        Pair<AmazonAutoScaling, String> getResult5 = actualAutoscalingClientsList.get(15);
        assertEquals("42", getResult5.getRight());
        assertTrue(getResult5.getLeft() instanceof AmazonAutoScalingClient);
        Pair<AmazonAutoScaling, String> getResult6 = actualAutoscalingClientsList.get(14);
        assertEquals("42", getResult6.getRight());
        assertTrue(getResult6.getLeft() instanceof AmazonAutoScalingClient);
        Pair<AmazonAutoScaling, String> getResult7 = actualAutoscalingClientsList.get(13);
        assertEquals("42", getResult7.getRight());
        assertTrue(getResult7.getLeft() instanceof AmazonAutoScalingClient);
        Pair<AmazonAutoScaling, String> getResult8 = actualAutoscalingClientsList.get(12);
        assertEquals("42", getResult8.getRight());
        assertTrue(getResult8.getLeft() instanceof AmazonAutoScalingClient);
        Pair<AmazonAutoScaling, String> getResult9 = actualAutoscalingClientsList.get(3);
        assertEquals("42", getResult9.getRight());
        Pair<AmazonAutoScaling, String> getResult10 = actualAutoscalingClientsList.get(1);
        assertEquals("42", getResult10.getRight());
        Pair<AmazonAutoScaling, String> getResult11 = actualAutoscalingClientsList.get(Short.SIZE);
        assertEquals("42", getResult11.getRight());
        Pair<AmazonAutoScaling, String> getResult12 = actualAutoscalingClientsList.get(0);
        assertEquals("42", getResult12.getRight());
        assertTrue(getResult12.getLeft() instanceof AmazonAutoScalingClient);
        assertSame(getResult11.getLeft(), getResult11.getKey());
        assertSame(getResult10.getLeft(), getResult10.getKey());
        assertSame(getResult9.getLeft(), getResult9.getKey());
        verify(awsCredentialsService).getAccountId(Mockito.<String>any(), Mockito.<String>any());
        verify(awsCredentialsService).getAllCredentials();
    }

    /**
     * Method under test: {@link UtilityServices#getCloudWatchClientsList()}
     */
    @Test
    void testGetCloudWatchClientsList() {
        when(awsCredentialsService.getAllCredentials()).thenReturn(new ArrayList<>());
        assertTrue(utilityServices.getCloudWatchClientsList().isEmpty());
        verify(awsCredentialsService).getAllCredentials();
    }

    /**
     * Method under test: {@link UtilityServices#getCloudWatchClientsList()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetCloudWatchClientsList2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.IllegalArgumentException: Access key cannot be null.
        //       at com.amazonaws.auth.BasicAWSCredentials.<init>(BasicAWSCredentials.java:37)
        //       at com.vermeg.aws.cost.optimization.dashboard.services.UtilityServices.getCloudWatchClientsList(UtilityServices.java:156)
        //   See https://diff.blue/R013 to resolve this issue.

        ArrayList<AWSCredentials> awsCredentialsList = new ArrayList<>();
        awsCredentialsList.add(new AnonymousAWSCredentials());
        when(awsCredentialsService.getAccountId(Mockito.<String>any(), Mockito.<String>any())).thenReturn("42");
        when(awsCredentialsService.getAllCredentials()).thenReturn(awsCredentialsList);
        utilityServices.getCloudWatchClientsList();
    }

    /**
     * Method under test: {@link UtilityServices#getCloudWatchClientsList()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetCloudWatchClientsList3() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.amazonaws.auth.AWSCredentials.getAWSAccessKeyId()" because "credentials" is null
        //       at com.vermeg.aws.cost.optimization.dashboard.services.UtilityServices.getCloudWatchClientsList(UtilityServices.java:153)
        //   See https://diff.blue/R013 to resolve this issue.

        ArrayList<AWSCredentials> awsCredentialsList = new ArrayList<>();
        awsCredentialsList.add(null);
        when(awsCredentialsService.getAccountId(Mockito.<String>any(), Mockito.<String>any())).thenReturn("42");
        when(awsCredentialsService.getAllCredentials()).thenReturn(awsCredentialsList);
        utilityServices.getCloudWatchClientsList();
    }

    /**
     * Method under test: {@link UtilityServices#getCloudWatchClientsList()}
     */
    @Test
    void testGetCloudWatchClientsList4() {
        ArrayList<AWSCredentials> awsCredentialsList = new ArrayList<>();
        awsCredentialsList
                .add(new BasicAWSCredentials("EXAMPLEakiAIOSFODNN7", "EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY"));
        when(awsCredentialsService.getAccountId(Mockito.<String>any(), Mockito.<String>any())).thenReturn("42");
        when(awsCredentialsService.getAllCredentials()).thenReturn(awsCredentialsList);
        List<Pair<AmazonCloudWatch, String>> actualCloudWatchClientsList = utilityServices.getCloudWatchClientsList();
        assertEquals(17, actualCloudWatchClientsList.size());
        Pair<AmazonCloudWatch, String> getResult = actualCloudWatchClientsList.get(4);
        assertEquals("42", getResult.getValue());
        Pair<AmazonCloudWatch, String> getResult2 = actualCloudWatchClientsList.get(11);
        assertEquals("42", getResult2.getValue());
        assertTrue(getResult2.getLeft() instanceof AmazonCloudWatchClient);
        assertTrue(getResult.getLeft() instanceof AmazonCloudWatchClient);
        Pair<AmazonCloudWatch, String> getResult3 = actualCloudWatchClientsList.get(5);
        assertEquals("42", getResult3.getRight());
        assertTrue(getResult3.getLeft() instanceof AmazonCloudWatchClient);
        Pair<AmazonCloudWatch, String> getResult4 = actualCloudWatchClientsList.get(2);
        assertEquals("42", getResult4.getRight());
        assertTrue(getResult4.getLeft() instanceof AmazonCloudWatchClient);
        Pair<AmazonCloudWatch, String> getResult5 = actualCloudWatchClientsList.get(15);
        assertEquals("42", getResult5.getRight());
        assertTrue(getResult5.getLeft() instanceof AmazonCloudWatchClient);
        Pair<AmazonCloudWatch, String> getResult6 = actualCloudWatchClientsList.get(14);
        assertEquals("42", getResult6.getRight());
        assertTrue(getResult6.getLeft() instanceof AmazonCloudWatchClient);
        Pair<AmazonCloudWatch, String> getResult7 = actualCloudWatchClientsList.get(13);
        assertEquals("42", getResult7.getRight());
        assertTrue(getResult7.getLeft() instanceof AmazonCloudWatchClient);
        Pair<AmazonCloudWatch, String> getResult8 = actualCloudWatchClientsList.get(12);
        assertEquals("42", getResult8.getRight());
        assertTrue(getResult8.getLeft() instanceof AmazonCloudWatchClient);
        Pair<AmazonCloudWatch, String> getResult9 = actualCloudWatchClientsList.get(3);
        assertEquals("42", getResult9.getRight());
        Pair<AmazonCloudWatch, String> getResult10 = actualCloudWatchClientsList.get(1);
        assertEquals("42", getResult10.getRight());
        Pair<AmazonCloudWatch, String> getResult11 = actualCloudWatchClientsList.get(Short.SIZE);
        assertEquals("42", getResult11.getRight());
        Pair<AmazonCloudWatch, String> getResult12 = actualCloudWatchClientsList.get(0);
        assertEquals("42", getResult12.getRight());
        assertTrue(getResult12.getLeft() instanceof AmazonCloudWatchClient);
        assertSame(getResult11.getLeft(), getResult11.getKey());
        assertSame(getResult10.getLeft(), getResult10.getKey());
        assertSame(getResult9.getLeft(), getResult9.getKey());
        verify(awsCredentialsService).getAccountId(Mockito.<String>any(), Mockito.<String>any());
        verify(awsCredentialsService).getAllCredentials();
    }

    /**
     * Method under test: {@link UtilityServices#getCloudWatchClientsList()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetCloudWatchClientsList5() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy97.connect(Unknown Source)
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
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.executeGetSessionToken(AWSSecurityTokenServiceClient.java:1624)
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.getSessionToken(AWSSecurityTokenServiceClient.java:1592)
            //       at com.amazonaws.auth.STSSessionCredentials.refreshCredentials(STSSessionCredentials.java:149)
            //       at com.amazonaws.auth.STSSessionCredentials.getSessionCredentials(STSSessionCredentials.java:158)
            //       at com.amazonaws.auth.STSSessionCredentials.getAWSAccessKeyId(STSSessionCredentials.java:108)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.UtilityServices.getCloudWatchClientsList(UtilityServices.java:153)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});

            ArrayList<AWSCredentials> awsCredentialsList = new ArrayList<>();
            awsCredentialsList.add(new STSSessionCredentials(new AWSSecurityTokenServiceAsyncClient()));
            when(awsCredentialsService.getAccountId(Mockito.<String>any(), Mockito.<String>any())).thenReturn("42");
            when(awsCredentialsService.getAllCredentials()).thenReturn(awsCredentialsList);
            utilityServices.getCloudWatchClientsList();
        }
    }

    /**
     * Method under test: {@link UtilityServices#getCloudWatchClientsList()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetCloudWatchClientsList6() throws UnknownHostException {
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
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.executeGetSessionToken(AWSSecurityTokenServiceClient.java:1624)
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.getSessionToken(AWSSecurityTokenServiceClient.java:1592)
            //       at com.amazonaws.auth.STSSessionCredentials.refreshCredentials(STSSessionCredentials.java:149)
            //       at com.amazonaws.auth.STSSessionCredentials.getSessionCredentials(STSSessionCredentials.java:158)
            //       at com.amazonaws.auth.STSSessionCredentials.getAWSAccessKeyId(STSSessionCredentials.java:108)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.UtilityServices.getCloudWatchClientsList(UtilityServices.java:153)
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
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.executeGetSessionToken(AWSSecurityTokenServiceClient.java:1624)
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.getSessionToken(AWSSecurityTokenServiceClient.java:1592)
            //       at com.amazonaws.auth.STSSessionCredentials.refreshCredentials(STSSessionCredentials.java:149)
            //       at com.amazonaws.auth.STSSessionCredentials.getSessionCredentials(STSSessionCredentials.java:158)
            //       at com.amazonaws.auth.STSSessionCredentials.getAWSAccessKeyId(STSSessionCredentials.java:108)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.UtilityServices.getCloudWatchClientsList(UtilityServices.java:153)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any())).thenReturn(new InetAddress[]{});

            ArrayList<AWSCredentials> awsCredentialsList = new ArrayList<>();
            awsCredentialsList.add(new STSSessionCredentials(new AWSSecurityTokenServiceAsyncClient()));
            when(awsCredentialsService.getAccountId(Mockito.<String>any(), Mockito.<String>any())).thenReturn("42");
            when(awsCredentialsService.getAllCredentials()).thenReturn(awsCredentialsList);
            utilityServices.getCloudWatchClientsList();
        }
    }

    /**
     * Method under test: {@link UtilityServices#getCloudWatchClientsList()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetCloudWatchClientsList7() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy97.connect(Unknown Source)
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
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.executeGetSessionToken(AWSSecurityTokenServiceClient.java:1624)
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.getSessionToken(AWSSecurityTokenServiceClient.java:1592)
            //       at com.amazonaws.auth.STSSessionCredentials.refreshCredentials(STSSessionCredentials.java:149)
            //       at com.amazonaws.auth.STSSessionCredentials.getSessionCredentials(STSSessionCredentials.java:158)
            //       at com.amazonaws.auth.STSSessionCredentials.getAWSAccessKeyId(STSSessionCredentials.java:108)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.UtilityServices.getCloudWatchClientsList(UtilityServices.java:153)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});

            ArrayList<AWSCredentials> awsCredentialsList = new ArrayList<>();
            awsCredentialsList
                    .add(new STSSessionCredentials(new AWSSecurityTokenServiceAsyncClient(new AnonymousAWSCredentials())));
            when(awsCredentialsService.getAccountId(Mockito.<String>any(), Mockito.<String>any())).thenReturn("42");
            when(awsCredentialsService.getAllCredentials()).thenReturn(awsCredentialsList);
            utilityServices.getCloudWatchClientsList();
        }
    }

    /**
     * Method under test: {@link UtilityServices#getCloudWatchClientsList()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetCloudWatchClientsList8() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy97.connect(Unknown Source)
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
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.executeGetSessionToken(AWSSecurityTokenServiceClient.java:1624)
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.getSessionToken(AWSSecurityTokenServiceClient.java:1592)
            //       at com.amazonaws.auth.STSSessionCredentials.refreshCredentials(STSSessionCredentials.java:149)
            //       at com.amazonaws.auth.STSSessionCredentials.getSessionCredentials(STSSessionCredentials.java:158)
            //       at com.amazonaws.auth.STSSessionCredentials.getAWSAccessKeyId(STSSessionCredentials.java:108)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.UtilityServices.getCloudWatchClientsList(UtilityServices.java:153)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            BasicAWSCredentials awsCredentials = mock(BasicAWSCredentials.class);
            when(awsCredentials.getAWSAccessKeyId()).thenReturn("EXAMPLEakiAIOSFODNN7");
            when(awsCredentials.getAWSSecretKey()).thenReturn("EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY");
            STSSessionCredentials stsSessionCredentials = new STSSessionCredentials(
                    new AWSSecurityTokenServiceAsyncClient(awsCredentials));

            ArrayList<AWSCredentials> awsCredentialsList = new ArrayList<>();
            awsCredentialsList.add(stsSessionCredentials);
            when(awsCredentialsService.getAccountId(Mockito.<String>any(), Mockito.<String>any())).thenReturn("42");
            when(awsCredentialsService.getAllCredentials()).thenReturn(awsCredentialsList);
            utilityServices.getCloudWatchClientsList();
        }
    }

    /**
     * Method under test: {@link UtilityServices#getCloudWatchClientsList()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetCloudWatchClientsList9() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy97.connect(Unknown Source)
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
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.executeGetSessionToken(AWSSecurityTokenServiceClient.java:1624)
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.getSessionToken(AWSSecurityTokenServiceClient.java:1592)
            //       at com.amazonaws.auth.STSSessionCredentials.refreshCredentials(STSSessionCredentials.java:149)
            //       at com.amazonaws.auth.STSSessionCredentials.getSessionCredentials(STSSessionCredentials.java:158)
            //       at com.amazonaws.auth.STSSessionCredentials.getAWSAccessKeyId(STSSessionCredentials.java:108)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.UtilityServices.getCloudWatchClientsList(UtilityServices.java:153)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});

            ArrayList<AWSCredentials> awsCredentialsList = new ArrayList<>();
            awsCredentialsList.add(new STSSessionCredentials(new AWSSecurityTokenServiceAsyncClient()));
            when(awsCredentialsService.getAccountId(Mockito.<String>any(), Mockito.<String>any())).thenReturn("42");
            when(awsCredentialsService.getAllCredentials()).thenReturn(awsCredentialsList);
            utilityServices.getCloudWatchClientsList();
        }
    }

    /**
     * Method under test: {@link UtilityServices#getCloudWatchClientsList()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetCloudWatchClientsList10() throws UnknownHostException {
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
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.executeGetSessionToken(AWSSecurityTokenServiceClient.java:1624)
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.getSessionToken(AWSSecurityTokenServiceClient.java:1592)
            //       at com.amazonaws.auth.STSSessionCredentials.refreshCredentials(STSSessionCredentials.java:149)
            //       at com.amazonaws.auth.STSSessionCredentials.getSessionCredentials(STSSessionCredentials.java:158)
            //       at com.amazonaws.auth.STSSessionCredentials.getAWSAccessKeyId(STSSessionCredentials.java:108)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.UtilityServices.getCloudWatchClientsList(UtilityServices.java:153)
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
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.executeGetSessionToken(AWSSecurityTokenServiceClient.java:1624)
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.getSessionToken(AWSSecurityTokenServiceClient.java:1592)
            //       at com.amazonaws.auth.STSSessionCredentials.refreshCredentials(STSSessionCredentials.java:149)
            //       at com.amazonaws.auth.STSSessionCredentials.getSessionCredentials(STSSessionCredentials.java:158)
            //       at com.amazonaws.auth.STSSessionCredentials.getAWSAccessKeyId(STSSessionCredentials.java:108)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.UtilityServices.getCloudWatchClientsList(UtilityServices.java:153)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any())).thenReturn(new InetAddress[]{});

            ArrayList<AWSCredentials> awsCredentialsList = new ArrayList<>();
            awsCredentialsList.add(new STSSessionCredentials(new AWSSecurityTokenServiceAsyncClient()));
            when(awsCredentialsService.getAccountId(Mockito.<String>any(), Mockito.<String>any())).thenReturn("42");
            when(awsCredentialsService.getAllCredentials()).thenReturn(awsCredentialsList);
            utilityServices.getCloudWatchClientsList();
        }
    }

    /**
     * Method under test: {@link UtilityServices#getCloudWatchClientsList()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetCloudWatchClientsList11() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy97.connect(Unknown Source)
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
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.executeGetSessionToken(AWSSecurityTokenServiceClient.java:1624)
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.getSessionToken(AWSSecurityTokenServiceClient.java:1592)
            //       at com.amazonaws.auth.STSSessionCredentials.refreshCredentials(STSSessionCredentials.java:149)
            //       at com.amazonaws.auth.STSSessionCredentials.getSessionCredentials(STSSessionCredentials.java:158)
            //       at com.amazonaws.auth.STSSessionCredentials.getAWSAccessKeyId(STSSessionCredentials.java:108)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.UtilityServices.getCloudWatchClientsList(UtilityServices.java:153)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});

            ArrayList<AWSCredentials> awsCredentialsList = new ArrayList<>();
            awsCredentialsList
                    .add(new STSSessionCredentials(new AWSSecurityTokenServiceAsyncClient(new AnonymousAWSCredentials())));
            when(awsCredentialsService.getAccountId(Mockito.<String>any(), Mockito.<String>any())).thenReturn("42");
            when(awsCredentialsService.getAllCredentials()).thenReturn(awsCredentialsList);
            utilityServices.getCloudWatchClientsList();
        }
    }

    /**
     * Method under test: {@link UtilityServices#getCloudWatchClientsList()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetCloudWatchClientsList12() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy97.connect(Unknown Source)
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
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.executeGetSessionToken(AWSSecurityTokenServiceClient.java:1624)
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.getSessionToken(AWSSecurityTokenServiceClient.java:1592)
            //       at com.amazonaws.auth.STSSessionCredentials.refreshCredentials(STSSessionCredentials.java:149)
            //       at com.amazonaws.auth.STSSessionCredentials.getSessionCredentials(STSSessionCredentials.java:158)
            //       at com.amazonaws.auth.STSSessionCredentials.getAWSAccessKeyId(STSSessionCredentials.java:108)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.UtilityServices.getCloudWatchClientsList(UtilityServices.java:153)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            BasicAWSCredentials awsCredentials = mock(BasicAWSCredentials.class);
            when(awsCredentials.getAWSAccessKeyId()).thenReturn("EXAMPLEakiAIOSFODNN7");
            when(awsCredentials.getAWSSecretKey()).thenReturn("EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY");
            STSSessionCredentials stsSessionCredentials = new STSSessionCredentials(
                    new AWSSecurityTokenServiceAsyncClient(awsCredentials));

            ArrayList<AWSCredentials> awsCredentialsList = new ArrayList<>();
            awsCredentialsList.add(stsSessionCredentials);
            when(awsCredentialsService.getAccountId(Mockito.<String>any(), Mockito.<String>any())).thenReturn("42");
            when(awsCredentialsService.getAllCredentials()).thenReturn(awsCredentialsList);
            utilityServices.getCloudWatchClientsList();
        }
    }

    /**
     * Method under test: {@link UtilityServices#getCloudWatchClient(String, String)}
     */
    @Test
    void testGetCloudWatchClient() {
        assertNull(utilityServices.getCloudWatchClient("42", "Namespace"));
    }

    /**
     * Method under test: {@link UtilityServices#getCloudWatchClient(String, String)}
     */
    @Test
    void testGetCloudWatchClient2() {
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
        Optional<EBSVolume> ofResult = Optional.of(ebsVolume);
        when(eBSVolumeRepository.findByVolumeId(Mockito.<String>any())).thenReturn(ofResult);
        assertEquals("monitoring",
                ((AmazonCloudWatchClient) utilityServices.getCloudWatchClient("42", "AWS/EBS")).getEndpointPrefix());
        assertNull(
                ((AmazonCloudWatchClient) utilityServices.getCloudWatchClient("42", "AWS/EBS")).getRequestMetricsCollector());
        assertEquals("monitoring",
                ((AmazonCloudWatchClient) utilityServices.getCloudWatchClient("42", "AWS/EBS")).getServiceName());
        assertNull(((AmazonCloudWatchClient) utilityServices.getCloudWatchClient("42", "AWS/EBS")).getSignerOverride());
        verify(awsCredentialsRepository).findAwsAccountCredentialsByAccountId(Mockito.<String>any());
        verify(eBSVolumeRepository, atLeast(1)).findByVolumeId(Mockito.<String>any());
    }

    /**
     * Method under test: {@link UtilityServices#getCloudWatchClient(String, String)}
     */
    @Test
    void testGetCloudWatchClient3() {
        AwsAccountCredentials awsAccountCredentials = mock(AwsAccountCredentials.class);
        when(awsAccountCredentials.getAccessKeyId()).thenReturn("EXAMPLEakiAIOSFODNN7");
        when(awsAccountCredentials.getSecretAccessKey()).thenReturn("EXAMPLEakiAIOSFODNN7");
        doNothing().when(awsAccountCredentials).setAccessKeyId(Mockito.<String>any());
        doNothing().when(awsAccountCredentials).setAccountId(Mockito.<String>any());
        doNothing().when(awsAccountCredentials).setAccountName(Mockito.<String>any());
        doNothing().when(awsAccountCredentials).setDate(Mockito.<LocalDateTime>any());
        doNothing().when(awsAccountCredentials)
                .setEbsOptimizationSuggestions(Mockito.<List<EBSOptimizationSuggestion>>any());
        doNothing().when(awsAccountCredentials).setEbsVolumes(Mockito.<List<EBSVolume>>any());
        doNothing().when(awsAccountCredentials).setEc2Instances(Mockito.<List<EC2Instance>>any());
        doNothing().when(awsAccountCredentials)
                .setEc2OptimizationSuggestions(Mockito.<List<EC2OptimizationSuggestion>>any());
        doNothing().when(awsAccountCredentials).setEipAddresses(Mockito.<List<EIPAddress>>any());
        doNothing().when(awsAccountCredentials)
                .setEipOptimizationSuggestions(Mockito.<List<EIPOptimizationSuggestion>>any());
        doNothing().when(awsAccountCredentials).setId(Mockito.<Long>any());
        doNothing().when(awsAccountCredentials).setRdsInstances(Mockito.<List<RDSInstance>>any());
        doNothing().when(awsAccountCredentials)
                .setRdsOptimizationSuggestions(Mockito.<List<RDSOptimizationSuggestion>>any());
        doNothing().when(awsAccountCredentials)
                .setS3OptimizationSuggestions(Mockito.<List<S3OptimizationSuggestion>>any());
        doNothing().when(awsAccountCredentials).setSecretAccessKey(Mockito.<String>any());
        doNothing().when(awsAccountCredentials).setStorageBuckets(Mockito.<List<StorageBucket>>any());
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
        EBSVolume ebsVolume = mock(EBSVolume.class);
        when(ebsVolume.getRegion()).thenReturn("foo");
        when(ebsVolume.getAssociatedAccount()).thenReturn(awsAccountCredentials2);
        doNothing().when(ebsVolume).setAssociatedAccount(Mockito.<AwsAccountCredentials>any());
        doNothing().when(ebsVolume).setCreationTime(Mockito.<Date>any());
        doNothing().when(ebsVolume).setCreationTimestamp(Mockito.<LocalDateTime>any());
        doNothing().when(ebsVolume).setEnvironmentType(Mockito.<String>any());
        doNothing().when(ebsVolume).setId(Mockito.<Long>any());
        doNothing().when(ebsVolume).setInstanceId(Mockito.<String>any());
        doNothing().when(ebsVolume).setLastUpdateTimestamp(Mockito.<LocalDateTime>any());
        doNothing().when(ebsVolume).setOperationHours(Mockito.<String>any());
        doNothing().when(ebsVolume).setOwnerEmail(Mockito.<String>any());
        doNothing().when(ebsVolume).setProductId(Mockito.<String>any());
        doNothing().when(ebsVolume).setRegion(Mockito.<String>any());
        doNothing().when(ebsVolume).setSize(Mockito.<Long>any());
        doNothing().when(ebsVolume).setState(Mockito.<String>any());
        doNothing().when(ebsVolume).setSuggestions(Mockito.<Set<EBSOptimizationSuggestion>>any());
        doNothing().when(ebsVolume).setVolumeId(Mockito.<String>any());
        doNothing().when(ebsVolume).setVolumeType(Mockito.<String>any());
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
        Optional<EBSVolume> ofResult = Optional.of(ebsVolume);
        when(eBSVolumeRepository.findByVolumeId(Mockito.<String>any())).thenReturn(ofResult);
        assertEquals("monitoring",
                ((AmazonCloudWatchClient) utilityServices.getCloudWatchClient("42", "AWS/EBS")).getEndpointPrefix());
        assertNull(
                ((AmazonCloudWatchClient) utilityServices.getCloudWatchClient("42", "AWS/EBS")).getRequestMetricsCollector());
        assertEquals("monitoring",
                ((AmazonCloudWatchClient) utilityServices.getCloudWatchClient("42", "AWS/EBS")).getServiceName());
        assertNull(((AmazonCloudWatchClient) utilityServices.getCloudWatchClient("42", "AWS/EBS")).getSignerOverride());
        verify(awsCredentialsRepository).findAwsAccountCredentialsByAccountId(Mockito.<String>any());
        verify(awsAccountCredentials).getAccessKeyId();
        verify(awsAccountCredentials).getSecretAccessKey();
        verify(awsAccountCredentials).setAccessKeyId(Mockito.<String>any());
        verify(awsAccountCredentials).setAccountId(Mockito.<String>any());
        verify(awsAccountCredentials).setAccountName(Mockito.<String>any());
        verify(awsAccountCredentials).setDate(Mockito.<LocalDateTime>any());
        verify(awsAccountCredentials).setEbsOptimizationSuggestions(Mockito.<List<EBSOptimizationSuggestion>>any());
        verify(awsAccountCredentials).setEbsVolumes(Mockito.<List<EBSVolume>>any());
        verify(awsAccountCredentials).setEc2Instances(Mockito.<List<EC2Instance>>any());
        verify(awsAccountCredentials).setEc2OptimizationSuggestions(Mockito.<List<EC2OptimizationSuggestion>>any());
        verify(awsAccountCredentials).setEipAddresses(Mockito.<List<EIPAddress>>any());
        verify(awsAccountCredentials).setEipOptimizationSuggestions(Mockito.<List<EIPOptimizationSuggestion>>any());
        verify(awsAccountCredentials).setId(Mockito.<Long>any());
        verify(awsAccountCredentials).setRdsInstances(Mockito.<List<RDSInstance>>any());
        verify(awsAccountCredentials).setRdsOptimizationSuggestions(Mockito.<List<RDSOptimizationSuggestion>>any());
        verify(awsAccountCredentials).setS3OptimizationSuggestions(Mockito.<List<S3OptimizationSuggestion>>any());
        verify(awsAccountCredentials).setSecretAccessKey(Mockito.<String>any());
        verify(awsAccountCredentials).setStorageBuckets(Mockito.<List<StorageBucket>>any());
        verify(eBSVolumeRepository, atLeast(1)).findByVolumeId(Mockito.<String>any());
        verify(ebsVolume).getAssociatedAccount();
        verify(ebsVolume).getRegion();
        verify(ebsVolume).setAssociatedAccount(Mockito.<AwsAccountCredentials>any());
        verify(ebsVolume).setCreationTime(Mockito.<Date>any());
        verify(ebsVolume).setCreationTimestamp(Mockito.<LocalDateTime>any());
        verify(ebsVolume).setEnvironmentType(Mockito.<String>any());
        verify(ebsVolume).setId(Mockito.<Long>any());
        verify(ebsVolume).setInstanceId(Mockito.<String>any());
        verify(ebsVolume).setLastUpdateTimestamp(Mockito.<LocalDateTime>any());
        verify(ebsVolume).setOperationHours(Mockito.<String>any());
        verify(ebsVolume).setOwnerEmail(Mockito.<String>any());
        verify(ebsVolume).setProductId(Mockito.<String>any());
        verify(ebsVolume).setRegion(Mockito.<String>any());
        verify(ebsVolume).setSize(Mockito.<Long>any());
        verify(ebsVolume).setState(Mockito.<String>any());
        verify(ebsVolume).setSuggestions(Mockito.<Set<EBSOptimizationSuggestion>>any());
        verify(ebsVolume).setVolumeId(Mockito.<String>any());
        verify(ebsVolume).setVolumeType(Mockito.<String>any());
    }

    /**
     * Method under test: {@link UtilityServices#getCostExplorerClientsList()}
     */
    @Test
    void testGetCostExplorerClientsList() {
        when(awsCredentialsService.getAllCredentials()).thenReturn(new ArrayList<>());
        assertTrue(utilityServices.getCostExplorerClientsList().isEmpty());
        verify(awsCredentialsService).getAllCredentials();
    }

    /**
     * Method under test: {@link UtilityServices#getCostExplorerClientsList()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetCostExplorerClientsList2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.IllegalArgumentException: Access key cannot be null.
        //       at com.amazonaws.auth.BasicAWSCredentials.<init>(BasicAWSCredentials.java:37)
        //       at com.vermeg.aws.cost.optimization.dashboard.services.UtilityServices.getCostExplorerClientsList(UtilityServices.java:216)
        //   See https://diff.blue/R013 to resolve this issue.

        ArrayList<AWSCredentials> awsCredentialsList = new ArrayList<>();
        awsCredentialsList.add(new AnonymousAWSCredentials());
        when(awsCredentialsService.getAllCredentials()).thenReturn(awsCredentialsList);
        utilityServices.getCostExplorerClientsList();
    }

    /**
     * Method under test: {@link UtilityServices#getCostExplorerClientsList()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetCostExplorerClientsList3() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.amazonaws.auth.AWSCredentials.getAWSAccessKeyId()" because "credentials" is null
        //       at com.vermeg.aws.cost.optimization.dashboard.services.UtilityServices.getCostExplorerClientsList(UtilityServices.java:216)
        //   See https://diff.blue/R013 to resolve this issue.

        ArrayList<AWSCredentials> awsCredentialsList = new ArrayList<>();
        awsCredentialsList.add(null);
        when(awsCredentialsService.getAllCredentials()).thenReturn(awsCredentialsList);
        utilityServices.getCostExplorerClientsList();
    }

    /**
     * Method under test: {@link UtilityServices#getCostExplorerClientsList()}
     */
    @Test
    void testGetCostExplorerClientsList4() {
        ArrayList<AWSCredentials> awsCredentialsList = new ArrayList<>();
        awsCredentialsList
                .add(new BasicAWSCredentials("EXAMPLEakiAIOSFODNN7", "EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY"));
        when(awsCredentialsService.getAllCredentials()).thenReturn(awsCredentialsList);
        List<AWSCostExplorer> actualCostExplorerClientsList = utilityServices.getCostExplorerClientsList();
        assertEquals(1, actualCostExplorerClientsList.size());
        assertEquals("ce", ((AWSCostExplorerClient) actualCostExplorerClientsList.get(0)).getEndpointPrefix());
        assertNull(((AWSCostExplorerClient) actualCostExplorerClientsList.get(0)).getRequestMetricsCollector());
        assertEquals("ce", ((AWSCostExplorerClient) actualCostExplorerClientsList.get(0)).getServiceName());
        assertNull(((AWSCostExplorerClient) actualCostExplorerClientsList.get(0)).getSignerOverride());
        verify(awsCredentialsService).getAllCredentials();
    }

    /**
     * Method under test: {@link UtilityServices#getCostExplorerClientsList()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetCostExplorerClientsList5() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy97.connect(Unknown Source)
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
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.executeGetSessionToken(AWSSecurityTokenServiceClient.java:1624)
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.getSessionToken(AWSSecurityTokenServiceClient.java:1592)
            //       at com.amazonaws.auth.STSSessionCredentials.refreshCredentials(STSSessionCredentials.java:149)
            //       at com.amazonaws.auth.STSSessionCredentials.getSessionCredentials(STSSessionCredentials.java:158)
            //       at com.amazonaws.auth.STSSessionCredentials.getAWSAccessKeyId(STSSessionCredentials.java:108)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.UtilityServices.getCostExplorerClientsList(UtilityServices.java:216)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});

            ArrayList<AWSCredentials> awsCredentialsList = new ArrayList<>();
            awsCredentialsList.add(new STSSessionCredentials(new AWSSecurityTokenServiceAsyncClient()));
            when(awsCredentialsService.getAllCredentials()).thenReturn(awsCredentialsList);
            utilityServices.getCostExplorerClientsList();
        }
    }

    /**
     * Method under test: {@link UtilityServices#getCostExplorerClientsList()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetCostExplorerClientsList6() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy97.connect(Unknown Source)
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
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.executeGetSessionToken(AWSSecurityTokenServiceClient.java:1624)
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.getSessionToken(AWSSecurityTokenServiceClient.java:1592)
            //       at com.amazonaws.auth.STSSessionCredentials.refreshCredentials(STSSessionCredentials.java:149)
            //       at com.amazonaws.auth.STSSessionCredentials.getSessionCredentials(STSSessionCredentials.java:158)
            //       at com.amazonaws.auth.STSSessionCredentials.getAWSAccessKeyId(STSSessionCredentials.java:108)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.UtilityServices.getCostExplorerClientsList(UtilityServices.java:216)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});

            ArrayList<AWSCredentials> awsCredentialsList = new ArrayList<>();
            awsCredentialsList
                    .add(new STSSessionCredentials(new AWSSecurityTokenServiceAsyncClient(new AnonymousAWSCredentials())));
            when(awsCredentialsService.getAllCredentials()).thenReturn(awsCredentialsList);
            utilityServices.getCostExplorerClientsList();
        }
    }

    /**
     * Method under test: {@link UtilityServices#getCostExplorerClientsList()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetCostExplorerClientsList7() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy97.connect(Unknown Source)
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
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.executeGetSessionToken(AWSSecurityTokenServiceClient.java:1624)
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.getSessionToken(AWSSecurityTokenServiceClient.java:1592)
            //       at com.amazonaws.auth.STSSessionCredentials.refreshCredentials(STSSessionCredentials.java:149)
            //       at com.amazonaws.auth.STSSessionCredentials.getSessionCredentials(STSSessionCredentials.java:158)
            //       at com.amazonaws.auth.STSSessionCredentials.getAWSAccessKeyId(STSSessionCredentials.java:108)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.UtilityServices.getCostExplorerClientsList(UtilityServices.java:216)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            BasicAWSCredentials awsCredentials = mock(BasicAWSCredentials.class);
            when(awsCredentials.getAWSAccessKeyId()).thenReturn("EXAMPLEakiAIOSFODNN7");
            when(awsCredentials.getAWSSecretKey()).thenReturn("EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY");
            STSSessionCredentials stsSessionCredentials = new STSSessionCredentials(
                    new AWSSecurityTokenServiceAsyncClient(awsCredentials));

            ArrayList<AWSCredentials> awsCredentialsList = new ArrayList<>();
            awsCredentialsList.add(stsSessionCredentials);
            when(awsCredentialsService.getAllCredentials()).thenReturn(awsCredentialsList);
            utilityServices.getCostExplorerClientsList();
        }
    }

    /**
     * Method under test: {@link UtilityServices#getCostExplorerClientsList()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetCostExplorerClientsList8() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy97.connect(Unknown Source)
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
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.executeGetSessionToken(AWSSecurityTokenServiceClient.java:1624)
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.getSessionToken(AWSSecurityTokenServiceClient.java:1592)
            //       at com.amazonaws.auth.STSSessionCredentials.refreshCredentials(STSSessionCredentials.java:149)
            //       at com.amazonaws.auth.STSSessionCredentials.getSessionCredentials(STSSessionCredentials.java:158)
            //       at com.amazonaws.auth.STSSessionCredentials.getAWSAccessKeyId(STSSessionCredentials.java:108)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.UtilityServices.getCostExplorerClientsList(UtilityServices.java:216)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});

            ArrayList<AWSCredentials> awsCredentialsList = new ArrayList<>();
            awsCredentialsList.add(new STSSessionCredentials(new AWSSecurityTokenServiceAsyncClient()));
            when(awsCredentialsService.getAllCredentials()).thenReturn(awsCredentialsList);
            utilityServices.getCostExplorerClientsList();
        }
    }

    /**
     * Method under test: {@link UtilityServices#getCostExplorerClientsList()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetCostExplorerClientsList9() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy97.connect(Unknown Source)
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
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.executeGetSessionToken(AWSSecurityTokenServiceClient.java:1624)
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.getSessionToken(AWSSecurityTokenServiceClient.java:1592)
            //       at com.amazonaws.auth.STSSessionCredentials.refreshCredentials(STSSessionCredentials.java:149)
            //       at com.amazonaws.auth.STSSessionCredentials.getSessionCredentials(STSSessionCredentials.java:158)
            //       at com.amazonaws.auth.STSSessionCredentials.getAWSAccessKeyId(STSSessionCredentials.java:108)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.UtilityServices.getCostExplorerClientsList(UtilityServices.java:216)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});

            ArrayList<AWSCredentials> awsCredentialsList = new ArrayList<>();
            awsCredentialsList
                    .add(new STSSessionCredentials(new AWSSecurityTokenServiceAsyncClient(new AnonymousAWSCredentials())));
            when(awsCredentialsService.getAllCredentials()).thenReturn(awsCredentialsList);
            utilityServices.getCostExplorerClientsList();
        }
    }

    /**
     * Method under test: {@link UtilityServices#getCostExplorerClientsList()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetCostExplorerClientsList10() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy97.connect(Unknown Source)
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
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.executeGetSessionToken(AWSSecurityTokenServiceClient.java:1624)
            //       at com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient.getSessionToken(AWSSecurityTokenServiceClient.java:1592)
            //       at com.amazonaws.auth.STSSessionCredentials.refreshCredentials(STSSessionCredentials.java:149)
            //       at com.amazonaws.auth.STSSessionCredentials.getSessionCredentials(STSSessionCredentials.java:158)
            //       at com.amazonaws.auth.STSSessionCredentials.getAWSAccessKeyId(STSSessionCredentials.java:108)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.UtilityServices.getCostExplorerClientsList(UtilityServices.java:216)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            BasicAWSCredentials awsCredentials = mock(BasicAWSCredentials.class);
            when(awsCredentials.getAWSAccessKeyId()).thenReturn("EXAMPLEakiAIOSFODNN7");
            when(awsCredentials.getAWSSecretKey()).thenReturn("EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY");
            STSSessionCredentials stsSessionCredentials = new STSSessionCredentials(
                    new AWSSecurityTokenServiceAsyncClient(awsCredentials));

            ArrayList<AWSCredentials> awsCredentialsList = new ArrayList<>();
            awsCredentialsList.add(stsSessionCredentials);
            when(awsCredentialsService.getAllCredentials()).thenReturn(awsCredentialsList);
            utilityServices.getCostExplorerClientsList();
        }
    }
}

