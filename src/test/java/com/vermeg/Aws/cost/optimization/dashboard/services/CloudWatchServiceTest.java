package com.vermeg.aws.cost.optimization.dashboard.services;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.amazonaws.services.cloudwatch.AmazonCloudWatchAsyncClient;
import com.amazonaws.services.cloudwatch.model.GetMetricStatisticsRequest;
import com.amazonaws.services.cloudwatch.model.GetMetricStatisticsResult;
import com.vermeg.aws.cost.optimization.dashboard.entities.AwsAccountCredentials;
import com.vermeg.aws.cost.optimization.dashboard.entities.EC2Instance;
import com.vermeg.aws.cost.optimization.dashboard.repositories.AwsCredentialsRepository;
import com.vermeg.aws.cost.optimization.dashboard.repositories.EBSVolumeRepository;
import com.vermeg.aws.cost.optimization.dashboard.repositories.EC2InstanceRepository;
import com.vermeg.aws.cost.optimization.dashboard.repositories.EIPAddressRepository;
import com.vermeg.aws.cost.optimization.dashboard.repositories.RDSInstanceRepository;
import com.vermeg.aws.cost.optimization.dashboard.repositories.StorageBucketRepository;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
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

@ContextConfiguration(classes = {CloudWatchService.class})
@ExtendWith(SpringExtension.class)
class CloudWatchServiceTest {
    @Autowired
    private CloudWatchService cloudWatchService;

    @MockBean
    private UtilityServices utilityServices;

    /**
     * Method under test: {@link CloudWatchService#getCPUUtilization(String, int, int)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetCPUUtilization() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            // TODO: Complete this test.
            //   Reason: R011 Sandboxing policy violation.
            //   Diffblue Cover ran code in your project that tried
            //     to access the network.
            //   Diffblue Cover's default sandboxing policy disallows this in order to prevent
            //   your code from damaging your system environment.
            //   See https://diff.blue/R011 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});

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
            AwsCredentialsRepository awsCredentialsRepository = mock(AwsCredentialsRepository.class);
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
            Optional<EC2Instance> ofResult = Optional.of(ec2Instance);
            EC2InstanceRepository ec2InstanceRepository = mock(EC2InstanceRepository.class);
            when(ec2InstanceRepository.findByInstanceId(Mockito.<String>any())).thenReturn(ofResult);
            (new CloudWatchService(new UtilityServices(new AwsCredentialsService(mock(AwsCredentialsRepository.class)),
                    awsCredentialsRepository, ec2InstanceRepository, mock(StorageBucketRepository.class),
                    mock(RDSInstanceRepository.class), mock(EBSVolumeRepository.class), mock(EIPAddressRepository.class))))
                    .getCPUUtilization("42", 1, 1);
        }
    }

    /**
     * Method under test: {@link CloudWatchService#getNetworkIn(String, int, int)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetNetworkIn() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy90.connect(Unknown Source)
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
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.doInvoke(AmazonCloudWatchClient.java:3363)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.invoke(AmazonCloudWatchClient.java:3330)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.invoke(AmazonCloudWatchClient.java:3319)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.executeGetMetricStatistics(AmazonCloudWatchClient.java:1693)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.getMetricStatistics(AmazonCloudWatchClient.java:1661)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getMetricData(CloudWatchService.java:126)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getNetworkIn(CloudWatchService.java:28)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            when(utilityServices.getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any()))
                    .thenReturn(new AmazonCloudWatchAsyncClient());
            cloudWatchService.getNetworkIn("42", 1, 1);
        }
    }

    /**
     * Method under test: {@link CloudWatchService#getNetworkIn(String, int, int)}
     */
    @Test
    void testGetNetworkIn2() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            AmazonCloudWatchAsyncClient amazonCloudWatchAsyncClient = mock(AmazonCloudWatchAsyncClient.class);
            when(amazonCloudWatchAsyncClient.getMetricStatistics(Mockito.<GetMetricStatisticsRequest>any()))
                    .thenReturn(new GetMetricStatisticsResult());
            when(utilityServices.getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any()))
                    .thenReturn(amazonCloudWatchAsyncClient);
            assertTrue(cloudWatchService.getNetworkIn("42", 1, 1).isEmpty());
            verify(utilityServices).getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any());
            verify(amazonCloudWatchAsyncClient).getMetricStatistics(Mockito.<GetMetricStatisticsRequest>any());
        }
    }

    /**
     * Method under test: {@link CloudWatchService#getNetworkIn(String, int, int)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetNetworkIn3() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            // TODO: Complete this test.
            //   Reason: R013 No inputs found that don't throw a trivial exception.
            //   Diffblue Cover tried to run the arrange/act section, but the method under
            //   test threw
            //   java.lang.NullPointerException: Cannot invoke "com.amazonaws.services.cloudwatch.model.GetMetricStatisticsResult.getDatapoints()" because "result" is null
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getMetricData(CloudWatchService.java:127)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getNetworkIn(CloudWatchService.java:28)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            AmazonCloudWatchAsyncClient amazonCloudWatchAsyncClient = mock(AmazonCloudWatchAsyncClient.class);
            when(amazonCloudWatchAsyncClient.getMetricStatistics(Mockito.<GetMetricStatisticsRequest>any()))
                    .thenReturn(null);
            when(utilityServices.getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any()))
                    .thenReturn(amazonCloudWatchAsyncClient);
            cloudWatchService.getNetworkIn("42", 1, 1);
        }
    }

    /**
     * Method under test: {@link CloudWatchService#getNetworkOut(String, int, int)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetNetworkOut() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy90.connect(Unknown Source)
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
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.doInvoke(AmazonCloudWatchClient.java:3363)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.invoke(AmazonCloudWatchClient.java:3330)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.invoke(AmazonCloudWatchClient.java:3319)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.executeGetMetricStatistics(AmazonCloudWatchClient.java:1693)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.getMetricStatistics(AmazonCloudWatchClient.java:1661)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getMetricData(CloudWatchService.java:126)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getNetworkOut(CloudWatchService.java:32)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            when(utilityServices.getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any()))
                    .thenReturn(new AmazonCloudWatchAsyncClient());
            cloudWatchService.getNetworkOut("42", 1, 1);
        }
    }

    /**
     * Method under test: {@link CloudWatchService#getNetworkOut(String, int, int)}
     */
    @Test
    void testGetNetworkOut2() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            AmazonCloudWatchAsyncClient amazonCloudWatchAsyncClient = mock(AmazonCloudWatchAsyncClient.class);
            when(amazonCloudWatchAsyncClient.getMetricStatistics(Mockito.<GetMetricStatisticsRequest>any()))
                    .thenReturn(new GetMetricStatisticsResult());
            when(utilityServices.getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any()))
                    .thenReturn(amazonCloudWatchAsyncClient);
            assertTrue(cloudWatchService.getNetworkOut("42", 1, 1).isEmpty());
            verify(utilityServices).getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any());
            verify(amazonCloudWatchAsyncClient).getMetricStatistics(Mockito.<GetMetricStatisticsRequest>any());
        }
    }

    /**
     * Method under test: {@link CloudWatchService#getNetworkOut(String, int, int)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetNetworkOut3() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            // TODO: Complete this test.
            //   Reason: R013 No inputs found that don't throw a trivial exception.
            //   Diffblue Cover tried to run the arrange/act section, but the method under
            //   test threw
            //   java.lang.NullPointerException: Cannot invoke "com.amazonaws.services.cloudwatch.model.GetMetricStatisticsResult.getDatapoints()" because "result" is null
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getMetricData(CloudWatchService.java:127)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getNetworkOut(CloudWatchService.java:32)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            AmazonCloudWatchAsyncClient amazonCloudWatchAsyncClient = mock(AmazonCloudWatchAsyncClient.class);
            when(amazonCloudWatchAsyncClient.getMetricStatistics(Mockito.<GetMetricStatisticsRequest>any()))
                    .thenReturn(null);
            when(utilityServices.getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any()))
                    .thenReturn(amazonCloudWatchAsyncClient);
            cloudWatchService.getNetworkOut("42", 1, 1);
        }
    }

    /**
     * Method under test: {@link CloudWatchService#getRDSCPUCreditBalance(String, int, int)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetRDSCPUCreditBalance() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy90.connect(Unknown Source)
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
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.doInvoke(AmazonCloudWatchClient.java:3363)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.invoke(AmazonCloudWatchClient.java:3330)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.invoke(AmazonCloudWatchClient.java:3319)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.executeGetMetricStatistics(AmazonCloudWatchClient.java:1693)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.getMetricStatistics(AmazonCloudWatchClient.java:1661)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getMetricData(CloudWatchService.java:126)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getRDSCPUCreditBalance(CloudWatchService.java:37)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            when(utilityServices.getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any()))
                    .thenReturn(new AmazonCloudWatchAsyncClient());
            cloudWatchService.getRDSCPUCreditBalance("42", 1, 1);
        }
    }

    /**
     * Method under test: {@link CloudWatchService#getRDSCPUCreditBalance(String, int, int)}
     */
    @Test
    void testGetRDSCPUCreditBalance2() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            AmazonCloudWatchAsyncClient amazonCloudWatchAsyncClient = mock(AmazonCloudWatchAsyncClient.class);
            when(amazonCloudWatchAsyncClient.getMetricStatistics(Mockito.<GetMetricStatisticsRequest>any()))
                    .thenReturn(new GetMetricStatisticsResult());
            when(utilityServices.getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any()))
                    .thenReturn(amazonCloudWatchAsyncClient);
            assertTrue(cloudWatchService.getRDSCPUCreditBalance("42", 1, 1).isEmpty());
            verify(utilityServices).getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any());
            verify(amazonCloudWatchAsyncClient).getMetricStatistics(Mockito.<GetMetricStatisticsRequest>any());
        }
    }

    /**
     * Method under test: {@link CloudWatchService#getRDSCPUCreditBalance(String, int, int)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetRDSCPUCreditBalance3() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            // TODO: Complete this test.
            //   Reason: R013 No inputs found that don't throw a trivial exception.
            //   Diffblue Cover tried to run the arrange/act section, but the method under
            //   test threw
            //   java.lang.NullPointerException: Cannot invoke "com.amazonaws.services.cloudwatch.model.GetMetricStatisticsResult.getDatapoints()" because "result" is null
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getMetricData(CloudWatchService.java:127)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getRDSCPUCreditBalance(CloudWatchService.java:37)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            AmazonCloudWatchAsyncClient amazonCloudWatchAsyncClient = mock(AmazonCloudWatchAsyncClient.class);
            when(amazonCloudWatchAsyncClient.getMetricStatistics(Mockito.<GetMetricStatisticsRequest>any()))
                    .thenReturn(null);
            when(utilityServices.getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any()))
                    .thenReturn(amazonCloudWatchAsyncClient);
            cloudWatchService.getRDSCPUCreditBalance("42", 1, 1);
        }
    }

    /**
     * Method under test: {@link CloudWatchService#getRDSCPUUtilization(String, int, int)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetRDSCPUUtilization() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy90.connect(Unknown Source)
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
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.doInvoke(AmazonCloudWatchClient.java:3363)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.invoke(AmazonCloudWatchClient.java:3330)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.invoke(AmazonCloudWatchClient.java:3319)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.executeGetMetricStatistics(AmazonCloudWatchClient.java:1693)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.getMetricStatistics(AmazonCloudWatchClient.java:1661)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getMetricData(CloudWatchService.java:126)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getRDSCPUUtilization(CloudWatchService.java:41)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            when(utilityServices.getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any()))
                    .thenReturn(new AmazonCloudWatchAsyncClient());
            cloudWatchService.getRDSCPUUtilization("42", 1, 1);
        }
    }

    /**
     * Method under test: {@link CloudWatchService#getRDSCPUUtilization(String, int, int)}
     */
    @Test
    void testGetRDSCPUUtilization2() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            AmazonCloudWatchAsyncClient amazonCloudWatchAsyncClient = mock(AmazonCloudWatchAsyncClient.class);
            when(amazonCloudWatchAsyncClient.getMetricStatistics(Mockito.<GetMetricStatisticsRequest>any()))
                    .thenReturn(new GetMetricStatisticsResult());
            when(utilityServices.getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any()))
                    .thenReturn(amazonCloudWatchAsyncClient);
            assertTrue(cloudWatchService.getRDSCPUUtilization("42", 1, 1).isEmpty());
            verify(utilityServices).getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any());
            verify(amazonCloudWatchAsyncClient).getMetricStatistics(Mockito.<GetMetricStatisticsRequest>any());
        }
    }

    /**
     * Method under test: {@link CloudWatchService#getRDSCPUUtilization(String, int, int)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetRDSCPUUtilization3() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            // TODO: Complete this test.
            //   Reason: R013 No inputs found that don't throw a trivial exception.
            //   Diffblue Cover tried to run the arrange/act section, but the method under
            //   test threw
            //   java.lang.NullPointerException: Cannot invoke "com.amazonaws.services.cloudwatch.model.GetMetricStatisticsResult.getDatapoints()" because "result" is null
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getMetricData(CloudWatchService.java:127)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getRDSCPUUtilization(CloudWatchService.java:41)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            AmazonCloudWatchAsyncClient amazonCloudWatchAsyncClient = mock(AmazonCloudWatchAsyncClient.class);
            when(amazonCloudWatchAsyncClient.getMetricStatistics(Mockito.<GetMetricStatisticsRequest>any()))
                    .thenReturn(null);
            when(utilityServices.getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any()))
                    .thenReturn(amazonCloudWatchAsyncClient);
            cloudWatchService.getRDSCPUUtilization("42", 1, 1);
        }
    }

    /**
     * Method under test: {@link CloudWatchService#getRDSFreeableMemory(String, int, int)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetRDSFreeableMemory() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy90.connect(Unknown Source)
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
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.doInvoke(AmazonCloudWatchClient.java:3363)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.invoke(AmazonCloudWatchClient.java:3330)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.invoke(AmazonCloudWatchClient.java:3319)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.executeGetMetricStatistics(AmazonCloudWatchClient.java:1693)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.getMetricStatistics(AmazonCloudWatchClient.java:1661)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getMetricData(CloudWatchService.java:126)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getRDSFreeableMemory(CloudWatchService.java:45)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            when(utilityServices.getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any()))
                    .thenReturn(new AmazonCloudWatchAsyncClient());
            cloudWatchService.getRDSFreeableMemory("42", 1, 1);
        }
    }

    /**
     * Method under test: {@link CloudWatchService#getRDSFreeableMemory(String, int, int)}
     */
    @Test
    void testGetRDSFreeableMemory2() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            AmazonCloudWatchAsyncClient amazonCloudWatchAsyncClient = mock(AmazonCloudWatchAsyncClient.class);
            when(amazonCloudWatchAsyncClient.getMetricStatistics(Mockito.<GetMetricStatisticsRequest>any()))
                    .thenReturn(new GetMetricStatisticsResult());
            when(utilityServices.getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any()))
                    .thenReturn(amazonCloudWatchAsyncClient);
            assertTrue(cloudWatchService.getRDSFreeableMemory("42", 1, 1).isEmpty());
            verify(utilityServices).getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any());
            verify(amazonCloudWatchAsyncClient).getMetricStatistics(Mockito.<GetMetricStatisticsRequest>any());
        }
    }

    /**
     * Method under test: {@link CloudWatchService#getRDSFreeableMemory(String, int, int)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetRDSFreeableMemory3() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            // TODO: Complete this test.
            //   Reason: R013 No inputs found that don't throw a trivial exception.
            //   Diffblue Cover tried to run the arrange/act section, but the method under
            //   test threw
            //   java.lang.NullPointerException: Cannot invoke "com.amazonaws.services.cloudwatch.model.GetMetricStatisticsResult.getDatapoints()" because "result" is null
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getMetricData(CloudWatchService.java:127)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getRDSFreeableMemory(CloudWatchService.java:45)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            AmazonCloudWatchAsyncClient amazonCloudWatchAsyncClient = mock(AmazonCloudWatchAsyncClient.class);
            when(amazonCloudWatchAsyncClient.getMetricStatistics(Mockito.<GetMetricStatisticsRequest>any()))
                    .thenReturn(null);
            when(utilityServices.getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any()))
                    .thenReturn(amazonCloudWatchAsyncClient);
            cloudWatchService.getRDSFreeableMemory("42", 1, 1);
        }
    }

    /**
     * Method under test: {@link CloudWatchService#getRDSFreeStorageSpace(String, int, int)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetRDSFreeStorageSpace() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy90.connect(Unknown Source)
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
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.doInvoke(AmazonCloudWatchClient.java:3363)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.invoke(AmazonCloudWatchClient.java:3330)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.invoke(AmazonCloudWatchClient.java:3319)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.executeGetMetricStatistics(AmazonCloudWatchClient.java:1693)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.getMetricStatistics(AmazonCloudWatchClient.java:1661)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getMetricData(CloudWatchService.java:126)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getRDSFreeStorageSpace(CloudWatchService.java:49)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            when(utilityServices.getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any()))
                    .thenReturn(new AmazonCloudWatchAsyncClient());
            cloudWatchService.getRDSFreeStorageSpace("42", 1, 1);
        }
    }

    /**
     * Method under test: {@link CloudWatchService#getRDSFreeStorageSpace(String, int, int)}
     */
    @Test
    void testGetRDSFreeStorageSpace2() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            AmazonCloudWatchAsyncClient amazonCloudWatchAsyncClient = mock(AmazonCloudWatchAsyncClient.class);
            when(amazonCloudWatchAsyncClient.getMetricStatistics(Mockito.<GetMetricStatisticsRequest>any()))
                    .thenReturn(new GetMetricStatisticsResult());
            when(utilityServices.getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any()))
                    .thenReturn(amazonCloudWatchAsyncClient);
            assertTrue(cloudWatchService.getRDSFreeStorageSpace("42", 1, 1).isEmpty());
            verify(utilityServices).getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any());
            verify(amazonCloudWatchAsyncClient).getMetricStatistics(Mockito.<GetMetricStatisticsRequest>any());
        }
    }

    /**
     * Method under test: {@link CloudWatchService#getRDSFreeStorageSpace(String, int, int)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetRDSFreeStorageSpace3() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            // TODO: Complete this test.
            //   Reason: R013 No inputs found that don't throw a trivial exception.
            //   Diffblue Cover tried to run the arrange/act section, but the method under
            //   test threw
            //   java.lang.NullPointerException: Cannot invoke "com.amazonaws.services.cloudwatch.model.GetMetricStatisticsResult.getDatapoints()" because "result" is null
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getMetricData(CloudWatchService.java:127)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getRDSFreeStorageSpace(CloudWatchService.java:49)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            AmazonCloudWatchAsyncClient amazonCloudWatchAsyncClient = mock(AmazonCloudWatchAsyncClient.class);
            when(amazonCloudWatchAsyncClient.getMetricStatistics(Mockito.<GetMetricStatisticsRequest>any()))
                    .thenReturn(null);
            when(utilityServices.getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any()))
                    .thenReturn(amazonCloudWatchAsyncClient);
            cloudWatchService.getRDSFreeStorageSpace("42", 1, 1);
        }
    }

    /**
     * Method under test: {@link CloudWatchService#getRDSReadIOPS(String, int, int)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetRDSReadIOPS() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy90.connect(Unknown Source)
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
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.doInvoke(AmazonCloudWatchClient.java:3363)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.invoke(AmazonCloudWatchClient.java:3330)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.invoke(AmazonCloudWatchClient.java:3319)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.executeGetMetricStatistics(AmazonCloudWatchClient.java:1693)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.getMetricStatistics(AmazonCloudWatchClient.java:1661)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getMetricData(CloudWatchService.java:126)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getRDSReadIOPS(CloudWatchService.java:53)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            when(utilityServices.getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any()))
                    .thenReturn(new AmazonCloudWatchAsyncClient());
            cloudWatchService.getRDSReadIOPS("42", 1, 1);
        }
    }

    /**
     * Method under test: {@link CloudWatchService#getRDSReadIOPS(String, int, int)}
     */
    @Test
    void testGetRDSReadIOPS2() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            AmazonCloudWatchAsyncClient amazonCloudWatchAsyncClient = mock(AmazonCloudWatchAsyncClient.class);
            when(amazonCloudWatchAsyncClient.getMetricStatistics(Mockito.<GetMetricStatisticsRequest>any()))
                    .thenReturn(new GetMetricStatisticsResult());
            when(utilityServices.getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any()))
                    .thenReturn(amazonCloudWatchAsyncClient);
            assertTrue(cloudWatchService.getRDSReadIOPS("42", 1, 1).isEmpty());
            verify(utilityServices).getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any());
            verify(amazonCloudWatchAsyncClient).getMetricStatistics(Mockito.<GetMetricStatisticsRequest>any());
        }
    }

    /**
     * Method under test: {@link CloudWatchService#getRDSReadIOPS(String, int, int)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetRDSReadIOPS3() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            // TODO: Complete this test.
            //   Reason: R013 No inputs found that don't throw a trivial exception.
            //   Diffblue Cover tried to run the arrange/act section, but the method under
            //   test threw
            //   java.lang.NullPointerException: Cannot invoke "com.amazonaws.services.cloudwatch.model.GetMetricStatisticsResult.getDatapoints()" because "result" is null
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getMetricData(CloudWatchService.java:127)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getRDSReadIOPS(CloudWatchService.java:53)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            AmazonCloudWatchAsyncClient amazonCloudWatchAsyncClient = mock(AmazonCloudWatchAsyncClient.class);
            when(amazonCloudWatchAsyncClient.getMetricStatistics(Mockito.<GetMetricStatisticsRequest>any()))
                    .thenReturn(null);
            when(utilityServices.getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any()))
                    .thenReturn(amazonCloudWatchAsyncClient);
            cloudWatchService.getRDSReadIOPS("42", 1, 1);
        }
    }

    /**
     * Method under test: {@link CloudWatchService#getRDSReadLatency(String, int, int)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetRDSReadLatency() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy90.connect(Unknown Source)
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
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.doInvoke(AmazonCloudWatchClient.java:3363)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.invoke(AmazonCloudWatchClient.java:3330)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.invoke(AmazonCloudWatchClient.java:3319)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.executeGetMetricStatistics(AmazonCloudWatchClient.java:1693)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.getMetricStatistics(AmazonCloudWatchClient.java:1661)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getMetricData(CloudWatchService.java:126)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getRDSReadLatency(CloudWatchService.java:57)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            when(utilityServices.getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any()))
                    .thenReturn(new AmazonCloudWatchAsyncClient());
            cloudWatchService.getRDSReadLatency("42", 1, 1);
        }
    }

    /**
     * Method under test: {@link CloudWatchService#getRDSReadLatency(String, int, int)}
     */
    @Test
    void testGetRDSReadLatency2() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            AmazonCloudWatchAsyncClient amazonCloudWatchAsyncClient = mock(AmazonCloudWatchAsyncClient.class);
            when(amazonCloudWatchAsyncClient.getMetricStatistics(Mockito.<GetMetricStatisticsRequest>any()))
                    .thenReturn(new GetMetricStatisticsResult());
            when(utilityServices.getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any()))
                    .thenReturn(amazonCloudWatchAsyncClient);
            assertTrue(cloudWatchService.getRDSReadLatency("42", 1, 1).isEmpty());
            verify(utilityServices).getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any());
            verify(amazonCloudWatchAsyncClient).getMetricStatistics(Mockito.<GetMetricStatisticsRequest>any());
        }
    }

    /**
     * Method under test: {@link CloudWatchService#getRDSReadLatency(String, int, int)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetRDSReadLatency3() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            // TODO: Complete this test.
            //   Reason: R013 No inputs found that don't throw a trivial exception.
            //   Diffblue Cover tried to run the arrange/act section, but the method under
            //   test threw
            //   java.lang.NullPointerException: Cannot invoke "com.amazonaws.services.cloudwatch.model.GetMetricStatisticsResult.getDatapoints()" because "result" is null
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getMetricData(CloudWatchService.java:127)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getRDSReadLatency(CloudWatchService.java:57)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            AmazonCloudWatchAsyncClient amazonCloudWatchAsyncClient = mock(AmazonCloudWatchAsyncClient.class);
            when(amazonCloudWatchAsyncClient.getMetricStatistics(Mockito.<GetMetricStatisticsRequest>any()))
                    .thenReturn(null);
            when(utilityServices.getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any()))
                    .thenReturn(amazonCloudWatchAsyncClient);
            cloudWatchService.getRDSReadLatency("42", 1, 1);
        }
    }

    /**
     * Method under test: {@link CloudWatchService#getRDSReadThroughput(String, int, int)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetRDSReadThroughput() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy90.connect(Unknown Source)
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
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.doInvoke(AmazonCloudWatchClient.java:3363)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.invoke(AmazonCloudWatchClient.java:3330)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.invoke(AmazonCloudWatchClient.java:3319)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.executeGetMetricStatistics(AmazonCloudWatchClient.java:1693)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.getMetricStatistics(AmazonCloudWatchClient.java:1661)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getMetricData(CloudWatchService.java:126)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getRDSReadThroughput(CloudWatchService.java:61)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            when(utilityServices.getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any()))
                    .thenReturn(new AmazonCloudWatchAsyncClient());
            cloudWatchService.getRDSReadThroughput("42", 1, 1);
        }
    }

    /**
     * Method under test: {@link CloudWatchService#getRDSReadThroughput(String, int, int)}
     */
    @Test
    void testGetRDSReadThroughput2() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            AmazonCloudWatchAsyncClient amazonCloudWatchAsyncClient = mock(AmazonCloudWatchAsyncClient.class);
            when(amazonCloudWatchAsyncClient.getMetricStatistics(Mockito.<GetMetricStatisticsRequest>any()))
                    .thenReturn(new GetMetricStatisticsResult());
            when(utilityServices.getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any()))
                    .thenReturn(amazonCloudWatchAsyncClient);
            assertTrue(cloudWatchService.getRDSReadThroughput("42", 1, 1).isEmpty());
            verify(utilityServices).getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any());
            verify(amazonCloudWatchAsyncClient).getMetricStatistics(Mockito.<GetMetricStatisticsRequest>any());
        }
    }

    /**
     * Method under test: {@link CloudWatchService#getRDSReadThroughput(String, int, int)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetRDSReadThroughput3() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            // TODO: Complete this test.
            //   Reason: R013 No inputs found that don't throw a trivial exception.
            //   Diffblue Cover tried to run the arrange/act section, but the method under
            //   test threw
            //   java.lang.NullPointerException: Cannot invoke "com.amazonaws.services.cloudwatch.model.GetMetricStatisticsResult.getDatapoints()" because "result" is null
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getMetricData(CloudWatchService.java:127)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getRDSReadThroughput(CloudWatchService.java:61)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            AmazonCloudWatchAsyncClient amazonCloudWatchAsyncClient = mock(AmazonCloudWatchAsyncClient.class);
            when(amazonCloudWatchAsyncClient.getMetricStatistics(Mockito.<GetMetricStatisticsRequest>any()))
                    .thenReturn(null);
            when(utilityServices.getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any()))
                    .thenReturn(amazonCloudWatchAsyncClient);
            cloudWatchService.getRDSReadThroughput("42", 1, 1);
        }
    }

    /**
     * Method under test: {@link CloudWatchService#getRDSWriteIOPS(String, int, int)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetRDSWriteIOPS() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy90.connect(Unknown Source)
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
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.doInvoke(AmazonCloudWatchClient.java:3363)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.invoke(AmazonCloudWatchClient.java:3330)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.invoke(AmazonCloudWatchClient.java:3319)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.executeGetMetricStatistics(AmazonCloudWatchClient.java:1693)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.getMetricStatistics(AmazonCloudWatchClient.java:1661)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getMetricData(CloudWatchService.java:126)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getRDSWriteIOPS(CloudWatchService.java:65)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            when(utilityServices.getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any()))
                    .thenReturn(new AmazonCloudWatchAsyncClient());
            cloudWatchService.getRDSWriteIOPS("42", 19088743, 19088743);
        }
    }

    /**
     * Method under test: {@link CloudWatchService#getRDSWriteIOPS(String, int, int)}
     */
    @Test
    void testGetRDSWriteIOPS2() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            AmazonCloudWatchAsyncClient amazonCloudWatchAsyncClient = mock(AmazonCloudWatchAsyncClient.class);
            when(amazonCloudWatchAsyncClient.getMetricStatistics(Mockito.<GetMetricStatisticsRequest>any()))
                    .thenReturn(new GetMetricStatisticsResult());
            when(utilityServices.getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any()))
                    .thenReturn(amazonCloudWatchAsyncClient);
            assertTrue(cloudWatchService.getRDSWriteIOPS("42", 19088743, 19088743).isEmpty());
            verify(utilityServices).getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any());
            verify(amazonCloudWatchAsyncClient).getMetricStatistics(Mockito.<GetMetricStatisticsRequest>any());
        }
    }

    /**
     * Method under test: {@link CloudWatchService#getRDSWriteIOPS(String, int, int)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetRDSWriteIOPS3() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            // TODO: Complete this test.
            //   Reason: R013 No inputs found that don't throw a trivial exception.
            //   Diffblue Cover tried to run the arrange/act section, but the method under
            //   test threw
            //   java.lang.NullPointerException: Cannot invoke "com.amazonaws.services.cloudwatch.model.GetMetricStatisticsResult.getDatapoints()" because "result" is null
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getMetricData(CloudWatchService.java:127)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getRDSWriteIOPS(CloudWatchService.java:65)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            AmazonCloudWatchAsyncClient amazonCloudWatchAsyncClient = mock(AmazonCloudWatchAsyncClient.class);
            when(amazonCloudWatchAsyncClient.getMetricStatistics(Mockito.<GetMetricStatisticsRequest>any()))
                    .thenReturn(null);
            when(utilityServices.getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any()))
                    .thenReturn(amazonCloudWatchAsyncClient);
            cloudWatchService.getRDSWriteIOPS("42", 19088743, 19088743);
        }
    }

    /**
     * Method under test: {@link CloudWatchService#getRDSWriteLatency(String, int, int)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetRDSWriteLatency() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy90.connect(Unknown Source)
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
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.doInvoke(AmazonCloudWatchClient.java:3363)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.invoke(AmazonCloudWatchClient.java:3330)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.invoke(AmazonCloudWatchClient.java:3319)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.executeGetMetricStatistics(AmazonCloudWatchClient.java:1693)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.getMetricStatistics(AmazonCloudWatchClient.java:1661)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getMetricData(CloudWatchService.java:126)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getRDSWriteLatency(CloudWatchService.java:69)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            when(utilityServices.getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any()))
                    .thenReturn(new AmazonCloudWatchAsyncClient());
            cloudWatchService.getRDSWriteLatency("42", 19088743, 19088743);
        }
    }

    /**
     * Method under test: {@link CloudWatchService#getRDSWriteLatency(String, int, int)}
     */
    @Test
    void testGetRDSWriteLatency2() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            AmazonCloudWatchAsyncClient amazonCloudWatchAsyncClient = mock(AmazonCloudWatchAsyncClient.class);
            when(amazonCloudWatchAsyncClient.getMetricStatistics(Mockito.<GetMetricStatisticsRequest>any()))
                    .thenReturn(new GetMetricStatisticsResult());
            when(utilityServices.getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any()))
                    .thenReturn(amazonCloudWatchAsyncClient);
            assertTrue(cloudWatchService.getRDSWriteLatency("42", 19088743, 19088743).isEmpty());
            verify(utilityServices).getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any());
            verify(amazonCloudWatchAsyncClient).getMetricStatistics(Mockito.<GetMetricStatisticsRequest>any());
        }
    }

    /**
     * Method under test: {@link CloudWatchService#getRDSWriteLatency(String, int, int)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetRDSWriteLatency3() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            // TODO: Complete this test.
            //   Reason: R013 No inputs found that don't throw a trivial exception.
            //   Diffblue Cover tried to run the arrange/act section, but the method under
            //   test threw
            //   java.lang.NullPointerException: Cannot invoke "com.amazonaws.services.cloudwatch.model.GetMetricStatisticsResult.getDatapoints()" because "result" is null
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getMetricData(CloudWatchService.java:127)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getRDSWriteLatency(CloudWatchService.java:69)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            AmazonCloudWatchAsyncClient amazonCloudWatchAsyncClient = mock(AmazonCloudWatchAsyncClient.class);
            when(amazonCloudWatchAsyncClient.getMetricStatistics(Mockito.<GetMetricStatisticsRequest>any()))
                    .thenReturn(null);
            when(utilityServices.getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any()))
                    .thenReturn(amazonCloudWatchAsyncClient);
            cloudWatchService.getRDSWriteLatency("42", 19088743, 19088743);
        }
    }

    /**
     * Method under test: {@link CloudWatchService#getRDSWriteThroughput(String, int, int)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetRDSWriteThroughput() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy90.connect(Unknown Source)
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
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.doInvoke(AmazonCloudWatchClient.java:3363)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.invoke(AmazonCloudWatchClient.java:3330)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.invoke(AmazonCloudWatchClient.java:3319)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.executeGetMetricStatistics(AmazonCloudWatchClient.java:1693)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.getMetricStatistics(AmazonCloudWatchClient.java:1661)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getMetricData(CloudWatchService.java:126)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getRDSWriteThroughput(CloudWatchService.java:73)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            when(utilityServices.getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any()))
                    .thenReturn(new AmazonCloudWatchAsyncClient());
            cloudWatchService.getRDSWriteThroughput("42", 19088743, 19088743);
        }
    }

    /**
     * Method under test: {@link CloudWatchService#getRDSWriteThroughput(String, int, int)}
     */
    @Test
    void testGetRDSWriteThroughput2() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            AmazonCloudWatchAsyncClient amazonCloudWatchAsyncClient = mock(AmazonCloudWatchAsyncClient.class);
            when(amazonCloudWatchAsyncClient.getMetricStatistics(Mockito.<GetMetricStatisticsRequest>any()))
                    .thenReturn(new GetMetricStatisticsResult());
            when(utilityServices.getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any()))
                    .thenReturn(amazonCloudWatchAsyncClient);
            assertTrue(cloudWatchService.getRDSWriteThroughput("42", 19088743, 19088743).isEmpty());
            verify(utilityServices).getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any());
            verify(amazonCloudWatchAsyncClient).getMetricStatistics(Mockito.<GetMetricStatisticsRequest>any());
        }
    }

    /**
     * Method under test: {@link CloudWatchService#getRDSWriteThroughput(String, int, int)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetRDSWriteThroughput3() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            // TODO: Complete this test.
            //   Reason: R013 No inputs found that don't throw a trivial exception.
            //   Diffblue Cover tried to run the arrange/act section, but the method under
            //   test threw
            //   java.lang.NullPointerException: Cannot invoke "com.amazonaws.services.cloudwatch.model.GetMetricStatisticsResult.getDatapoints()" because "result" is null
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getMetricData(CloudWatchService.java:127)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getRDSWriteThroughput(CloudWatchService.java:73)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            AmazonCloudWatchAsyncClient amazonCloudWatchAsyncClient = mock(AmazonCloudWatchAsyncClient.class);
            when(amazonCloudWatchAsyncClient.getMetricStatistics(Mockito.<GetMetricStatisticsRequest>any()))
                    .thenReturn(null);
            when(utilityServices.getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any()))
                    .thenReturn(amazonCloudWatchAsyncClient);
            cloudWatchService.getRDSWriteThroughput("42", 19088743, 19088743);
        }
    }

    /**
     * Method under test: {@link CloudWatchService#getS3BucketSize(String, int, int)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetS3BucketSize() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy90.connect(Unknown Source)
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
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.doInvoke(AmazonCloudWatchClient.java:3363)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.invoke(AmazonCloudWatchClient.java:3330)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.invoke(AmazonCloudWatchClient.java:3319)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.executeGetMetricStatistics(AmazonCloudWatchClient.java:1693)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.getMetricStatistics(AmazonCloudWatchClient.java:1661)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getS3MetricData(CloudWatchService.java:141)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getS3BucketSize(CloudWatchService.java:79)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            when(utilityServices.getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any()))
                    .thenReturn(new AmazonCloudWatchAsyncClient());
            cloudWatchService.getS3BucketSize("42", 1, 1);
        }
    }

    /**
     * Method under test: {@link CloudWatchService#getS3BucketSize(String, int, int)}
     */
    @Test
    void testGetS3BucketSize2() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            AmazonCloudWatchAsyncClient amazonCloudWatchAsyncClient = mock(AmazonCloudWatchAsyncClient.class);
            when(amazonCloudWatchAsyncClient.getMetricStatistics(Mockito.<GetMetricStatisticsRequest>any()))
                    .thenReturn(new GetMetricStatisticsResult());
            when(utilityServices.getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any()))
                    .thenReturn(amazonCloudWatchAsyncClient);
            assertTrue(cloudWatchService.getS3BucketSize("42", 1, 1).isEmpty());
            verify(utilityServices).getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any());
            verify(amazonCloudWatchAsyncClient).getMetricStatistics(Mockito.<GetMetricStatisticsRequest>any());
        }
    }

    /**
     * Method under test: {@link CloudWatchService#getS3BucketSize(String, int, int)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetS3BucketSize3() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            // TODO: Complete this test.
            //   Reason: R013 No inputs found that don't throw a trivial exception.
            //   Diffblue Cover tried to run the arrange/act section, but the method under
            //   test threw
            //   java.lang.NullPointerException: Cannot invoke "com.amazonaws.services.cloudwatch.model.GetMetricStatisticsResult.getDatapoints()" because "result" is null
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getS3MetricData(CloudWatchService.java:142)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getS3BucketSize(CloudWatchService.java:79)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            AmazonCloudWatchAsyncClient amazonCloudWatchAsyncClient = mock(AmazonCloudWatchAsyncClient.class);
            when(amazonCloudWatchAsyncClient.getMetricStatistics(Mockito.<GetMetricStatisticsRequest>any()))
                    .thenReturn(null);
            when(utilityServices.getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any()))
                    .thenReturn(amazonCloudWatchAsyncClient);
            cloudWatchService.getS3BucketSize("42", 1, 1);
        }
    }

    /**
     * Method under test: {@link CloudWatchService#getS3NumberOfObjects(String, int, int)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetS3NumberOfObjects() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy90.connect(Unknown Source)
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
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.doInvoke(AmazonCloudWatchClient.java:3363)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.invoke(AmazonCloudWatchClient.java:3330)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.invoke(AmazonCloudWatchClient.java:3319)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.executeGetMetricStatistics(AmazonCloudWatchClient.java:1693)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.getMetricStatistics(AmazonCloudWatchClient.java:1661)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getS3MetricData(CloudWatchService.java:141)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getS3NumberOfObjects(CloudWatchService.java:83)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            when(utilityServices.getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any()))
                    .thenReturn(new AmazonCloudWatchAsyncClient());
            cloudWatchService.getS3NumberOfObjects("42", 1, 1);
        }
    }

    /**
     * Method under test: {@link CloudWatchService#getS3NumberOfObjects(String, int, int)}
     */
    @Test
    void testGetS3NumberOfObjects2() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            AmazonCloudWatchAsyncClient amazonCloudWatchAsyncClient = mock(AmazonCloudWatchAsyncClient.class);
            when(amazonCloudWatchAsyncClient.getMetricStatistics(Mockito.<GetMetricStatisticsRequest>any()))
                    .thenReturn(new GetMetricStatisticsResult());
            when(utilityServices.getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any()))
                    .thenReturn(amazonCloudWatchAsyncClient);
            assertTrue(cloudWatchService.getS3NumberOfObjects("42", 1, 1).isEmpty());
            verify(utilityServices).getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any());
            verify(amazonCloudWatchAsyncClient).getMetricStatistics(Mockito.<GetMetricStatisticsRequest>any());
        }
    }

    /**
     * Method under test: {@link CloudWatchService#getS3NumberOfObjects(String, int, int)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetS3NumberOfObjects3() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            // TODO: Complete this test.
            //   Reason: R013 No inputs found that don't throw a trivial exception.
            //   Diffblue Cover tried to run the arrange/act section, but the method under
            //   test threw
            //   java.lang.NullPointerException: Cannot invoke "com.amazonaws.services.cloudwatch.model.GetMetricStatisticsResult.getDatapoints()" because "result" is null
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getS3MetricData(CloudWatchService.java:142)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getS3NumberOfObjects(CloudWatchService.java:83)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            AmazonCloudWatchAsyncClient amazonCloudWatchAsyncClient = mock(AmazonCloudWatchAsyncClient.class);
            when(amazonCloudWatchAsyncClient.getMetricStatistics(Mockito.<GetMetricStatisticsRequest>any()))
                    .thenReturn(null);
            when(utilityServices.getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any()))
                    .thenReturn(amazonCloudWatchAsyncClient);
            cloudWatchService.getS3NumberOfObjects("42", 1, 1);
        }
    }

    /**
     * Method under test: {@link CloudWatchService#getEBSVolumeReadBytes(String, int, int)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetEBSVolumeReadBytes() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy90.connect(Unknown Source)
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
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.doInvoke(AmazonCloudWatchClient.java:3363)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.invoke(AmazonCloudWatchClient.java:3330)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.invoke(AmazonCloudWatchClient.java:3319)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.executeGetMetricStatistics(AmazonCloudWatchClient.java:1693)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.getMetricStatistics(AmazonCloudWatchClient.java:1661)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getMetricData(CloudWatchService.java:126)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getEBSVolumeReadBytes(CloudWatchService.java:89)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            when(utilityServices.getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any()))
                    .thenReturn(new AmazonCloudWatchAsyncClient());
            cloudWatchService.getEBSVolumeReadBytes("42", 1, 1);
        }
    }

    /**
     * Method under test: {@link CloudWatchService#getEBSVolumeReadBytes(String, int, int)}
     */
    @Test
    void testGetEBSVolumeReadBytes2() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            AmazonCloudWatchAsyncClient amazonCloudWatchAsyncClient = mock(AmazonCloudWatchAsyncClient.class);
            when(amazonCloudWatchAsyncClient.getMetricStatistics(Mockito.<GetMetricStatisticsRequest>any()))
                    .thenReturn(new GetMetricStatisticsResult());
            when(utilityServices.getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any()))
                    .thenReturn(amazonCloudWatchAsyncClient);
            assertTrue(cloudWatchService.getEBSVolumeReadBytes("42", 1, 1).isEmpty());
            verify(utilityServices).getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any());
            verify(amazonCloudWatchAsyncClient).getMetricStatistics(Mockito.<GetMetricStatisticsRequest>any());
        }
    }

    /**
     * Method under test: {@link CloudWatchService#getEBSVolumeReadBytes(String, int, int)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetEBSVolumeReadBytes3() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            // TODO: Complete this test.
            //   Reason: R013 No inputs found that don't throw a trivial exception.
            //   Diffblue Cover tried to run the arrange/act section, but the method under
            //   test threw
            //   java.lang.NullPointerException: Cannot invoke "com.amazonaws.services.cloudwatch.model.GetMetricStatisticsResult.getDatapoints()" because "result" is null
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getMetricData(CloudWatchService.java:127)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getEBSVolumeReadBytes(CloudWatchService.java:89)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            AmazonCloudWatchAsyncClient amazonCloudWatchAsyncClient = mock(AmazonCloudWatchAsyncClient.class);
            when(amazonCloudWatchAsyncClient.getMetricStatistics(Mockito.<GetMetricStatisticsRequest>any()))
                    .thenReturn(null);
            when(utilityServices.getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any()))
                    .thenReturn(amazonCloudWatchAsyncClient);
            cloudWatchService.getEBSVolumeReadBytes("42", 1, 1);
        }
    }

    /**
     * Method under test: {@link CloudWatchService#getEBSVolumeWriteBytes(String, int, int)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetEBSVolumeWriteBytes() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy90.connect(Unknown Source)
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
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.doInvoke(AmazonCloudWatchClient.java:3363)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.invoke(AmazonCloudWatchClient.java:3330)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.invoke(AmazonCloudWatchClient.java:3319)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.executeGetMetricStatistics(AmazonCloudWatchClient.java:1693)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.getMetricStatistics(AmazonCloudWatchClient.java:1661)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getMetricData(CloudWatchService.java:126)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getEBSVolumeWriteBytes(CloudWatchService.java:93)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            when(utilityServices.getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any()))
                    .thenReturn(new AmazonCloudWatchAsyncClient());
            cloudWatchService.getEBSVolumeWriteBytes("42", 19088743, 19088743);
        }
    }

    /**
     * Method under test: {@link CloudWatchService#getEBSVolumeWriteBytes(String, int, int)}
     */
    @Test
    void testGetEBSVolumeWriteBytes2() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            AmazonCloudWatchAsyncClient amazonCloudWatchAsyncClient = mock(AmazonCloudWatchAsyncClient.class);
            when(amazonCloudWatchAsyncClient.getMetricStatistics(Mockito.<GetMetricStatisticsRequest>any()))
                    .thenReturn(new GetMetricStatisticsResult());
            when(utilityServices.getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any()))
                    .thenReturn(amazonCloudWatchAsyncClient);
            assertTrue(cloudWatchService.getEBSVolumeWriteBytes("42", 19088743, 19088743).isEmpty());
            verify(utilityServices).getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any());
            verify(amazonCloudWatchAsyncClient).getMetricStatistics(Mockito.<GetMetricStatisticsRequest>any());
        }
    }

    /**
     * Method under test: {@link CloudWatchService#getEBSVolumeWriteBytes(String, int, int)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetEBSVolumeWriteBytes3() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            // TODO: Complete this test.
            //   Reason: R013 No inputs found that don't throw a trivial exception.
            //   Diffblue Cover tried to run the arrange/act section, but the method under
            //   test threw
            //   java.lang.NullPointerException: Cannot invoke "com.amazonaws.services.cloudwatch.model.GetMetricStatisticsResult.getDatapoints()" because "result" is null
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getMetricData(CloudWatchService.java:127)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getEBSVolumeWriteBytes(CloudWatchService.java:93)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            AmazonCloudWatchAsyncClient amazonCloudWatchAsyncClient = mock(AmazonCloudWatchAsyncClient.class);
            when(amazonCloudWatchAsyncClient.getMetricStatistics(Mockito.<GetMetricStatisticsRequest>any()))
                    .thenReturn(null);
            when(utilityServices.getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any()))
                    .thenReturn(amazonCloudWatchAsyncClient);
            cloudWatchService.getEBSVolumeWriteBytes("42", 19088743, 19088743);
        }
    }

    /**
     * Method under test: {@link CloudWatchService#getEBSVolumeReadOps(String, int, int)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetEBSVolumeReadOps() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy90.connect(Unknown Source)
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
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.doInvoke(AmazonCloudWatchClient.java:3363)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.invoke(AmazonCloudWatchClient.java:3330)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.invoke(AmazonCloudWatchClient.java:3319)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.executeGetMetricStatistics(AmazonCloudWatchClient.java:1693)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.getMetricStatistics(AmazonCloudWatchClient.java:1661)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getMetricData(CloudWatchService.java:126)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getEBSVolumeReadOps(CloudWatchService.java:97)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            when(utilityServices.getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any()))
                    .thenReturn(new AmazonCloudWatchAsyncClient());
            cloudWatchService.getEBSVolumeReadOps("42", 1, 1);
        }
    }

    /**
     * Method under test: {@link CloudWatchService#getEBSVolumeReadOps(String, int, int)}
     */
    @Test
    void testGetEBSVolumeReadOps2() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            AmazonCloudWatchAsyncClient amazonCloudWatchAsyncClient = mock(AmazonCloudWatchAsyncClient.class);
            when(amazonCloudWatchAsyncClient.getMetricStatistics(Mockito.<GetMetricStatisticsRequest>any()))
                    .thenReturn(new GetMetricStatisticsResult());
            when(utilityServices.getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any()))
                    .thenReturn(amazonCloudWatchAsyncClient);
            assertTrue(cloudWatchService.getEBSVolumeReadOps("42", 1, 1).isEmpty());
            verify(utilityServices).getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any());
            verify(amazonCloudWatchAsyncClient).getMetricStatistics(Mockito.<GetMetricStatisticsRequest>any());
        }
    }

    /**
     * Method under test: {@link CloudWatchService#getEBSVolumeReadOps(String, int, int)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetEBSVolumeReadOps3() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            // TODO: Complete this test.
            //   Reason: R013 No inputs found that don't throw a trivial exception.
            //   Diffblue Cover tried to run the arrange/act section, but the method under
            //   test threw
            //   java.lang.NullPointerException: Cannot invoke "com.amazonaws.services.cloudwatch.model.GetMetricStatisticsResult.getDatapoints()" because "result" is null
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getMetricData(CloudWatchService.java:127)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getEBSVolumeReadOps(CloudWatchService.java:97)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            AmazonCloudWatchAsyncClient amazonCloudWatchAsyncClient = mock(AmazonCloudWatchAsyncClient.class);
            when(amazonCloudWatchAsyncClient.getMetricStatistics(Mockito.<GetMetricStatisticsRequest>any()))
                    .thenReturn(null);
            when(utilityServices.getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any()))
                    .thenReturn(amazonCloudWatchAsyncClient);
            cloudWatchService.getEBSVolumeReadOps("42", 1, 1);
        }
    }

    /**
     * Method under test: {@link CloudWatchService#getEBSVolumeWriteOps(String, int, int)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetEBSVolumeWriteOps() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy90.connect(Unknown Source)
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
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.doInvoke(AmazonCloudWatchClient.java:3363)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.invoke(AmazonCloudWatchClient.java:3330)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.invoke(AmazonCloudWatchClient.java:3319)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.executeGetMetricStatistics(AmazonCloudWatchClient.java:1693)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.getMetricStatistics(AmazonCloudWatchClient.java:1661)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getMetricData(CloudWatchService.java:126)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getEBSVolumeWriteOps(CloudWatchService.java:101)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            when(utilityServices.getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any()))
                    .thenReturn(new AmazonCloudWatchAsyncClient());
            cloudWatchService.getEBSVolumeWriteOps("42", 19088743, 19088743);
        }
    }

    /**
     * Method under test: {@link CloudWatchService#getEBSVolumeWriteOps(String, int, int)}
     */
    @Test
    void testGetEBSVolumeWriteOps2() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            AmazonCloudWatchAsyncClient amazonCloudWatchAsyncClient = mock(AmazonCloudWatchAsyncClient.class);
            when(amazonCloudWatchAsyncClient.getMetricStatistics(Mockito.<GetMetricStatisticsRequest>any()))
                    .thenReturn(new GetMetricStatisticsResult());
            when(utilityServices.getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any()))
                    .thenReturn(amazonCloudWatchAsyncClient);
            assertTrue(cloudWatchService.getEBSVolumeWriteOps("42", 19088743, 19088743).isEmpty());
            verify(utilityServices).getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any());
            verify(amazonCloudWatchAsyncClient).getMetricStatistics(Mockito.<GetMetricStatisticsRequest>any());
        }
    }

    /**
     * Method under test: {@link CloudWatchService#getEBSVolumeWriteOps(String, int, int)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetEBSVolumeWriteOps3() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            // TODO: Complete this test.
            //   Reason: R013 No inputs found that don't throw a trivial exception.
            //   Diffblue Cover tried to run the arrange/act section, but the method under
            //   test threw
            //   java.lang.NullPointerException: Cannot invoke "com.amazonaws.services.cloudwatch.model.GetMetricStatisticsResult.getDatapoints()" because "result" is null
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getMetricData(CloudWatchService.java:127)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getEBSVolumeWriteOps(CloudWatchService.java:101)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            AmazonCloudWatchAsyncClient amazonCloudWatchAsyncClient = mock(AmazonCloudWatchAsyncClient.class);
            when(amazonCloudWatchAsyncClient.getMetricStatistics(Mockito.<GetMetricStatisticsRequest>any()))
                    .thenReturn(null);
            when(utilityServices.getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any()))
                    .thenReturn(amazonCloudWatchAsyncClient);
            cloudWatchService.getEBSVolumeWriteOps("42", 19088743, 19088743);
        }
    }

    /**
     * Method under test: {@link CloudWatchService#getEIPIn(String, int, int)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetEIPIn() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy90.connect(Unknown Source)
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
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.doInvoke(AmazonCloudWatchClient.java:3363)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.invoke(AmazonCloudWatchClient.java:3330)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.invoke(AmazonCloudWatchClient.java:3319)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.executeGetMetricStatistics(AmazonCloudWatchClient.java:1693)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.getMetricStatistics(AmazonCloudWatchClient.java:1661)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getMetricData(CloudWatchService.java:126)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getEIPIn(CloudWatchService.java:107)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            when(utilityServices.getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any()))
                    .thenReturn(new AmazonCloudWatchAsyncClient());
            cloudWatchService.getEIPIn("42", 1, 1);
        }
    }

    /**
     * Method under test: {@link CloudWatchService#getEIPIn(String, int, int)}
     */
    @Test
    void testGetEIPIn2() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            AmazonCloudWatchAsyncClient amazonCloudWatchAsyncClient = mock(AmazonCloudWatchAsyncClient.class);
            when(amazonCloudWatchAsyncClient.getMetricStatistics(Mockito.<GetMetricStatisticsRequest>any()))
                    .thenReturn(new GetMetricStatisticsResult());
            when(utilityServices.getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any()))
                    .thenReturn(amazonCloudWatchAsyncClient);
            assertTrue(cloudWatchService.getEIPIn("42", 1, 1).isEmpty());
            verify(utilityServices).getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any());
            verify(amazonCloudWatchAsyncClient).getMetricStatistics(Mockito.<GetMetricStatisticsRequest>any());
        }
    }

    /**
     * Method under test: {@link CloudWatchService#getEIPIn(String, int, int)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetEIPIn3() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            // TODO: Complete this test.
            //   Reason: R013 No inputs found that don't throw a trivial exception.
            //   Diffblue Cover tried to run the arrange/act section, but the method under
            //   test threw
            //   java.lang.NullPointerException: Cannot invoke "com.amazonaws.services.cloudwatch.model.GetMetricStatisticsResult.getDatapoints()" because "result" is null
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getMetricData(CloudWatchService.java:127)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getEIPIn(CloudWatchService.java:107)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            AmazonCloudWatchAsyncClient amazonCloudWatchAsyncClient = mock(AmazonCloudWatchAsyncClient.class);
            when(amazonCloudWatchAsyncClient.getMetricStatistics(Mockito.<GetMetricStatisticsRequest>any()))
                    .thenReturn(null);
            when(utilityServices.getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any()))
                    .thenReturn(amazonCloudWatchAsyncClient);
            cloudWatchService.getEIPIn("42", 1, 1);
        }
    }

    /**
     * Method under test: {@link CloudWatchService#getEIPOut(String, int, int)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetEIPOut() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy90.connect(Unknown Source)
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
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.doInvoke(AmazonCloudWatchClient.java:3363)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.invoke(AmazonCloudWatchClient.java:3330)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.invoke(AmazonCloudWatchClient.java:3319)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.executeGetMetricStatistics(AmazonCloudWatchClient.java:1693)
            //       at com.amazonaws.services.cloudwatch.AmazonCloudWatchClient.getMetricStatistics(AmazonCloudWatchClient.java:1661)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getMetricData(CloudWatchService.java:126)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getEIPOut(CloudWatchService.java:111)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            when(utilityServices.getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any()))
                    .thenReturn(new AmazonCloudWatchAsyncClient());
            cloudWatchService.getEIPOut("42", 1, 1);
        }
    }

    /**
     * Method under test: {@link CloudWatchService#getEIPOut(String, int, int)}
     */
    @Test
    void testGetEIPOut2() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            AmazonCloudWatchAsyncClient amazonCloudWatchAsyncClient = mock(AmazonCloudWatchAsyncClient.class);
            when(amazonCloudWatchAsyncClient.getMetricStatistics(Mockito.<GetMetricStatisticsRequest>any()))
                    .thenReturn(new GetMetricStatisticsResult());
            when(utilityServices.getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any()))
                    .thenReturn(amazonCloudWatchAsyncClient);
            assertTrue(cloudWatchService.getEIPOut("42", 1, 1).isEmpty());
            verify(utilityServices).getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any());
            verify(amazonCloudWatchAsyncClient).getMetricStatistics(Mockito.<GetMetricStatisticsRequest>any());
        }
    }

    /**
     * Method under test: {@link CloudWatchService#getEIPOut(String, int, int)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetEIPOut3() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            // TODO: Complete this test.
            //   Reason: R013 No inputs found that don't throw a trivial exception.
            //   Diffblue Cover tried to run the arrange/act section, but the method under
            //   test threw
            //   java.lang.NullPointerException: Cannot invoke "com.amazonaws.services.cloudwatch.model.GetMetricStatisticsResult.getDatapoints()" because "result" is null
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getMetricData(CloudWatchService.java:127)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService.getEIPOut(CloudWatchService.java:111)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            AmazonCloudWatchAsyncClient amazonCloudWatchAsyncClient = mock(AmazonCloudWatchAsyncClient.class);
            when(amazonCloudWatchAsyncClient.getMetricStatistics(Mockito.<GetMetricStatisticsRequest>any()))
                    .thenReturn(null);
            when(utilityServices.getCloudWatchClient(Mockito.<String>any(), Mockito.<String>any()))
                    .thenReturn(amazonCloudWatchAsyncClient);
            cloudWatchService.getEIPOut("42", 1, 1);
        }
    }
}

