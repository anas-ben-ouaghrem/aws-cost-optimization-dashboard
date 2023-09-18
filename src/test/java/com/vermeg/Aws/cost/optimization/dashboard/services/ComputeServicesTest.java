package com.vermeg.aws.cost.optimization.dashboard.services;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2AsyncClient;
import com.amazonaws.services.ec2.model.DescribeAddressesRequest;
import com.amazonaws.services.ec2.model.DescribeAddressesResult;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.DescribeVolumesRequest;
import com.amazonaws.services.ec2.model.DescribeVolumesResult;
import com.vermeg.aws.cost.optimization.dashboard.entities.AwsAccountCredentials;
import com.vermeg.aws.cost.optimization.dashboard.entities.EBSVolume;
import com.vermeg.aws.cost.optimization.dashboard.entities.EC2Instance;
import com.vermeg.aws.cost.optimization.dashboard.entities.EIPAddress;
import com.vermeg.aws.cost.optimization.dashboard.repositories.EBSVolumeRepository;
import com.vermeg.aws.cost.optimization.dashboard.repositories.EC2InstanceRepository;
import com.vermeg.aws.cost.optimization.dashboard.repositories.EIPAddressRepository;

import java.net.InetAddress;
import java.net.UnknownHostException;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.tuple.ImmutablePair;
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

@ContextConfiguration(classes = {ComputeServices.class})
@ExtendWith(SpringExtension.class)
class ComputeServicesTest {
    @Autowired
    private ComputeServices computeServices;

    @MockBean
    private EBSVolumeRepository eBSVolumeRepository;

    @MockBean
    private EC2InstanceRepository eC2InstanceRepository;

    @MockBean
    private EIPAddressRepository eIPAddressRepository;

    @MockBean
    private UtilityServices utilityServices;

    /**
     * Method under test: {@link ComputeServices#getAllEC2InstancesWithAccountId()}
     */
    @Test
    void testGetAllEC2InstancesWithAccountId() {
        when(utilityServices.getEc2ClientsListWithAccountId()).thenReturn(new ArrayList<>());
        assertTrue(computeServices.getAllEC2InstancesWithAccountId().isEmpty());
        verify(utilityServices).getEc2ClientsListWithAccountId();
    }

    /**
     * Method under test: {@link ComputeServices#getAllEC2InstancesWithAccountId()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetAllEC2InstancesWithAccountId2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.amazonaws.services.ec2.AmazonEC2.describeInstances(com.amazonaws.services.ec2.model.DescribeInstancesRequest)" because "client" is null
        //       at com.vermeg.aws.cost.optimization.dashboard.services.ComputeServices.getAllEC2InstancesWithAccountId(ComputeServices.java:44)
        //   See https://diff.blue/R013 to resolve this issue.

        ArrayList<Pair<AmazonEC2, String>> pairList = new ArrayList<>();
        ImmutablePair<AmazonEC2, String> nullPairResult = ImmutablePair.nullPair();
        pairList.add(nullPairResult);
        when(utilityServices.getEc2ClientsListWithAccountId()).thenReturn(pairList);
        computeServices.getAllEC2InstancesWithAccountId();
    }

    /**
     * Method under test: {@link ComputeServices#getAllEC2InstancesWithAccountId()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetAllEC2InstancesWithAccountId3() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy98.connect(Unknown Source)
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
            //       at com.amazonaws.services.ec2.AmazonEC2Client.doInvoke(AmazonEC2Client.java:38039)
            //       at com.amazonaws.services.ec2.AmazonEC2Client.invoke(AmazonEC2Client.java:38006)
            //       at com.amazonaws.services.ec2.AmazonEC2Client.invoke(AmazonEC2Client.java:37995)
            //       at com.amazonaws.services.ec2.AmazonEC2Client.executeDescribeInstances(AmazonEC2Client.java:17380)
            //       at com.amazonaws.services.ec2.AmazonEC2Client.describeInstances(AmazonEC2Client.java:17348)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.ComputeServices.getAllEC2InstancesWithAccountId(ComputeServices.java:44)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});

            ArrayList<Pair<AmazonEC2, String>> pairList = new ArrayList<>();
            pairList.add(new ImmutablePair<>(new AmazonEC2AsyncClient(), "Right"));
            when(utilityServices.getEc2ClientsListWithAccountId()).thenReturn(pairList);
            computeServices.getAllEC2InstancesWithAccountId();
        }
    }

    /**
     * Method under test: {@link ComputeServices#getAllEC2InstancesWithAccountId()}
     */
    @Test
    void testGetAllEC2InstancesWithAccountId4() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            AmazonEC2AsyncClient amazonEC2AsyncClient = mock(AmazonEC2AsyncClient.class);
            when(amazonEC2AsyncClient.describeInstances(Mockito.<DescribeInstancesRequest>any()))
                    .thenReturn(new DescribeInstancesResult());
            ImmutablePair<AmazonEC2, String> immutablePair = new ImmutablePair<>(amazonEC2AsyncClient, "Right");

            ArrayList<Pair<AmazonEC2, String>> pairList = new ArrayList<>();
            pairList.add(immutablePair);
            when(utilityServices.getEc2ClientsListWithAccountId()).thenReturn(pairList);
            assertTrue(computeServices.getAllEC2InstancesWithAccountId().isEmpty());
            verify(utilityServices).getEc2ClientsListWithAccountId();
            verify(amazonEC2AsyncClient).describeInstances(Mockito.<DescribeInstancesRequest>any());
        }
    }

    /**
     * Method under test: {@link ComputeServices#getAllEC2InstancesWithAccountId()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetAllEC2InstancesWithAccountId5() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            // TODO: Complete this test.
            //   Reason: R013 No inputs found that don't throw a trivial exception.
            //   Diffblue Cover tried to run the arrange/act section, but the method under
            //   test threw
            //   java.lang.NullPointerException: Cannot invoke "com.amazonaws.services.ec2.model.DescribeInstancesResult.getReservations()" because "result" is null
            //       at com.vermeg.aws.cost.optimization.dashboard.services.ComputeServices.getAllEC2InstancesWithAccountId(ComputeServices.java:46)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            AmazonEC2AsyncClient amazonEC2AsyncClient = mock(AmazonEC2AsyncClient.class);
            when(amazonEC2AsyncClient.describeInstances(Mockito.<DescribeInstancesRequest>any())).thenReturn(null);
            ImmutablePair<AmazonEC2, String> immutablePair = new ImmutablePair<>(amazonEC2AsyncClient, "Right");

            ArrayList<Pair<AmazonEC2, String>> pairList = new ArrayList<>();
            pairList.add(immutablePair);
            when(utilityServices.getEc2ClientsListWithAccountId()).thenReturn(pairList);
            computeServices.getAllEC2InstancesWithAccountId();
        }
    }

    /**
     * Method under test: {@link ComputeServices#getAllEC2InstancesWithAccountId()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetAllEC2InstancesWithAccountId6() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            // TODO: Complete this test.
            //   Reason: R013 No inputs found that don't throw a trivial exception.
            //   Diffblue Cover tried to run the arrange/act section, but the method under
            //   test threw
            //   java.lang.NullPointerException: Cannot invoke "org.apache.commons.lang3.tuple.Pair.getKey()" because "pair" is null
            //       at com.vermeg.aws.cost.optimization.dashboard.services.ComputeServices.getAllEC2InstancesWithAccountId(ComputeServices.java:40)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});

            ArrayList<Pair<AmazonEC2, String>> pairList = new ArrayList<>();
            pairList.add(null);
            when(utilityServices.getEc2ClientsListWithAccountId()).thenReturn(pairList);
            computeServices.getAllEC2InstancesWithAccountId();
        }
    }

    /**
     * Method under test: {@link ComputeServices#getAllEbsVolumesWithAccountId()}
     */
    @Test
    void testGetAllEbsVolumesWithAccountId() {
        when(utilityServices.getEc2ClientsListWithAccountId()).thenReturn(new ArrayList<>());
        assertTrue(computeServices.getAllEbsVolumesWithAccountId().isEmpty());
        verify(utilityServices).getEc2ClientsListWithAccountId();
    }

    /**
     * Method under test: {@link ComputeServices#getAllEbsVolumesWithAccountId()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetAllEbsVolumesWithAccountId2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.amazonaws.services.ec2.AmazonEC2.describeVolumes(com.amazonaws.services.ec2.model.DescribeVolumesRequest)" because "client" is null
        //       at com.vermeg.aws.cost.optimization.dashboard.services.ComputeServices.getAllEbsVolumesWithAccountId(ComputeServices.java:78)
        //   See https://diff.blue/R013 to resolve this issue.

        ArrayList<Pair<AmazonEC2, String>> pairList = new ArrayList<>();
        ImmutablePair<AmazonEC2, String> nullPairResult = ImmutablePair.nullPair();
        pairList.add(nullPairResult);
        when(utilityServices.getEc2ClientsListWithAccountId()).thenReturn(pairList);
        computeServices.getAllEbsVolumesWithAccountId();
    }

    /**
     * Method under test: {@link ComputeServices#getAllEbsVolumesWithAccountId()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetAllEbsVolumesWithAccountId3() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy98.connect(Unknown Source)
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
            //       at com.amazonaws.services.ec2.AmazonEC2Client.doInvoke(AmazonEC2Client.java:38039)
            //       at com.amazonaws.services.ec2.AmazonEC2Client.invoke(AmazonEC2Client.java:38006)
            //       at com.amazonaws.services.ec2.AmazonEC2Client.invoke(AmazonEC2Client.java:37995)
            //       at com.amazonaws.services.ec2.AmazonEC2Client.executeDescribeVolumes(AmazonEC2Client.java:22260)
            //       at com.amazonaws.services.ec2.AmazonEC2Client.describeVolumes(AmazonEC2Client.java:22228)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.ComputeServices.getAllEbsVolumesWithAccountId(ComputeServices.java:78)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});

            ArrayList<Pair<AmazonEC2, String>> pairList = new ArrayList<>();
            pairList.add(new ImmutablePair<>(new AmazonEC2AsyncClient(), "Right"));
            when(utilityServices.getEc2ClientsListWithAccountId()).thenReturn(pairList);
            computeServices.getAllEbsVolumesWithAccountId();
        }
    }

    /**
     * Method under test: {@link ComputeServices#getAllEbsVolumesWithAccountId()}
     */
    @Test
    void testGetAllEbsVolumesWithAccountId4() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            AmazonEC2AsyncClient amazonEC2AsyncClient = mock(AmazonEC2AsyncClient.class);
            when(amazonEC2AsyncClient.describeVolumes(Mockito.<DescribeVolumesRequest>any()))
                    .thenReturn(new DescribeVolumesResult());
            ImmutablePair<AmazonEC2, String> immutablePair = new ImmutablePair<>(amazonEC2AsyncClient, "Right");

            ArrayList<Pair<AmazonEC2, String>> pairList = new ArrayList<>();
            pairList.add(immutablePair);
            when(utilityServices.getEc2ClientsListWithAccountId()).thenReturn(pairList);
            assertTrue(computeServices.getAllEbsVolumesWithAccountId().isEmpty());
            verify(utilityServices).getEc2ClientsListWithAccountId();
            verify(amazonEC2AsyncClient).describeVolumes(Mockito.<DescribeVolumesRequest>any());
        }
    }

    /**
     * Method under test: {@link ComputeServices#getAllEbsVolumesWithAccountId()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetAllEbsVolumesWithAccountId5() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            // TODO: Complete this test.
            //   Reason: R013 No inputs found that don't throw a trivial exception.
            //   Diffblue Cover tried to run the arrange/act section, but the method under
            //   test threw
            //   java.lang.NullPointerException: Cannot invoke "com.amazonaws.services.ec2.model.DescribeVolumesResult.getVolumes()" because "result" is null
            //       at com.vermeg.aws.cost.optimization.dashboard.services.ComputeServices.getAllEbsVolumesWithAccountId(ComputeServices.java:80)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            AmazonEC2AsyncClient amazonEC2AsyncClient = mock(AmazonEC2AsyncClient.class);
            when(amazonEC2AsyncClient.describeVolumes(Mockito.<DescribeVolumesRequest>any())).thenReturn(null);
            ImmutablePair<AmazonEC2, String> immutablePair = new ImmutablePair<>(amazonEC2AsyncClient, "Right");

            ArrayList<Pair<AmazonEC2, String>> pairList = new ArrayList<>();
            pairList.add(immutablePair);
            when(utilityServices.getEc2ClientsListWithAccountId()).thenReturn(pairList);
            computeServices.getAllEbsVolumesWithAccountId();
        }
    }

    /**
     * Method under test: {@link ComputeServices#getAllEbsVolumesWithAccountId()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetAllEbsVolumesWithAccountId6() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            // TODO: Complete this test.
            //   Reason: R013 No inputs found that don't throw a trivial exception.
            //   Diffblue Cover tried to run the arrange/act section, but the method under
            //   test threw
            //   java.lang.NullPointerException: Cannot invoke "org.apache.commons.lang3.tuple.Pair.getKey()" because "pair" is null
            //       at com.vermeg.aws.cost.optimization.dashboard.services.ComputeServices.getAllEbsVolumesWithAccountId(ComputeServices.java:74)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});

            ArrayList<Pair<AmazonEC2, String>> pairList = new ArrayList<>();
            pairList.add(null);
            when(utilityServices.getEc2ClientsListWithAccountId()).thenReturn(pairList);
            computeServices.getAllEbsVolumesWithAccountId();
        }
    }

    /**
     * Method under test: {@link ComputeServices#getAllElasticIPsWithAccountId()}
     */
    @Test
    void testGetAllElasticIPsWithAccountId() {
        when(utilityServices.getEc2ClientsListWithAccountId()).thenReturn(new ArrayList<>());
        assertTrue(computeServices.getAllElasticIPsWithAccountId().isEmpty());
        verify(utilityServices).getEc2ClientsListWithAccountId();
    }

    /**
     * Method under test: {@link ComputeServices#getAllElasticIPsWithAccountId()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetAllElasticIPsWithAccountId2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.amazonaws.services.ec2.AmazonEC2.describeAddresses(com.amazonaws.services.ec2.model.DescribeAddressesRequest)" because "client" is null
        //       at com.vermeg.aws.cost.optimization.dashboard.services.ComputeServices.getAllElasticIPsWithAccountId(ComputeServices.java:108)
        //   See https://diff.blue/R013 to resolve this issue.

        ArrayList<Pair<AmazonEC2, String>> pairList = new ArrayList<>();
        ImmutablePair<AmazonEC2, String> nullPairResult = ImmutablePair.nullPair();
        pairList.add(nullPairResult);
        when(utilityServices.getEc2ClientsListWithAccountId()).thenReturn(pairList);
        computeServices.getAllElasticIPsWithAccountId();
    }

    /**
     * Method under test: {@link ComputeServices#getAllElasticIPsWithAccountId()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetAllElasticIPsWithAccountId3() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy98.connect(Unknown Source)
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
            //       at com.amazonaws.services.ec2.AmazonEC2Client.doInvoke(AmazonEC2Client.java:38039)
            //       at com.amazonaws.services.ec2.AmazonEC2Client.invoke(AmazonEC2Client.java:38006)
            //       at com.amazonaws.services.ec2.AmazonEC2Client.invoke(AmazonEC2Client.java:37995)
            //       at com.amazonaws.services.ec2.AmazonEC2Client.executeDescribeAddresses(AmazonEC2Client.java:14243)
            //       at com.amazonaws.services.ec2.AmazonEC2Client.describeAddresses(AmazonEC2Client.java:14211)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.ComputeServices.getAllElasticIPsWithAccountId(ComputeServices.java:108)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});

            ArrayList<Pair<AmazonEC2, String>> pairList = new ArrayList<>();
            pairList.add(new ImmutablePair<>(new AmazonEC2AsyncClient(), "Right"));
            when(utilityServices.getEc2ClientsListWithAccountId()).thenReturn(pairList);
            computeServices.getAllElasticIPsWithAccountId();
        }
    }

    /**
     * Method under test: {@link ComputeServices#getAllElasticIPsWithAccountId()}
     */
    @Test
    void testGetAllElasticIPsWithAccountId4() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            AmazonEC2AsyncClient amazonEC2AsyncClient = mock(AmazonEC2AsyncClient.class);
            when(amazonEC2AsyncClient.describeAddresses(Mockito.<DescribeAddressesRequest>any()))
                    .thenReturn(new DescribeAddressesResult());
            ImmutablePair<AmazonEC2, String> immutablePair = new ImmutablePair<>(amazonEC2AsyncClient, "Right");

            ArrayList<Pair<AmazonEC2, String>> pairList = new ArrayList<>();
            pairList.add(immutablePair);
            when(utilityServices.getEc2ClientsListWithAccountId()).thenReturn(pairList);
            assertTrue(computeServices.getAllElasticIPsWithAccountId().isEmpty());
            verify(utilityServices).getEc2ClientsListWithAccountId();
            verify(amazonEC2AsyncClient).describeAddresses(Mockito.<DescribeAddressesRequest>any());
        }
    }

    /**
     * Method under test: {@link ComputeServices#getAllElasticIPsWithAccountId()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetAllElasticIPsWithAccountId5() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            // TODO: Complete this test.
            //   Reason: R013 No inputs found that don't throw a trivial exception.
            //   Diffblue Cover tried to run the arrange/act section, but the method under
            //   test threw
            //   java.lang.NullPointerException: Cannot invoke "com.amazonaws.services.ec2.model.DescribeAddressesResult.getAddresses()" because "result" is null
            //       at com.vermeg.aws.cost.optimization.dashboard.services.ComputeServices.getAllElasticIPsWithAccountId(ComputeServices.java:110)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            AmazonEC2AsyncClient amazonEC2AsyncClient = mock(AmazonEC2AsyncClient.class);
            when(amazonEC2AsyncClient.describeAddresses(Mockito.<DescribeAddressesRequest>any())).thenReturn(null);
            ImmutablePair<AmazonEC2, String> immutablePair = new ImmutablePair<>(amazonEC2AsyncClient, "Right");

            ArrayList<Pair<AmazonEC2, String>> pairList = new ArrayList<>();
            pairList.add(immutablePair);
            when(utilityServices.getEc2ClientsListWithAccountId()).thenReturn(pairList);
            computeServices.getAllElasticIPsWithAccountId();
        }
    }

    /**
     * Method under test: {@link ComputeServices#getAllElasticIPsWithAccountId()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetAllElasticIPsWithAccountId6() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            // TODO: Complete this test.
            //   Reason: R013 No inputs found that don't throw a trivial exception.
            //   Diffblue Cover tried to run the arrange/act section, but the method under
            //   test threw
            //   java.lang.NullPointerException: Cannot invoke "org.apache.commons.lang3.tuple.Pair.getKey()" because "pair" is null
            //       at com.vermeg.aws.cost.optimization.dashboard.services.ComputeServices.getAllElasticIPsWithAccountId(ComputeServices.java:104)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});

            ArrayList<Pair<AmazonEC2, String>> pairList = new ArrayList<>();
            pairList.add(null);
            when(utilityServices.getEc2ClientsListWithAccountId()).thenReturn(pairList);
            computeServices.getAllElasticIPsWithAccountId();
        }
    }

    /**
     * Method under test: {@link ComputeServices#getAllInstances()}
     */
    @Test
    void testGetAllInstances() {
        ArrayList<EC2Instance> ec2InstanceList = new ArrayList<>();
        when(eC2InstanceRepository.findAll()).thenReturn(ec2InstanceList);
        List<EC2Instance> actualAllInstances = computeServices.getAllInstances();
        assertSame(ec2InstanceList, actualAllInstances);
        assertTrue(actualAllInstances.isEmpty());
        verify(eC2InstanceRepository).findAll();
    }

    /**
     * Method under test: {@link ComputeServices#getInstanceById(String)}
     */
    @Test
    void testGetInstanceById() {
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
        when(eC2InstanceRepository.findByInstanceId(Mockito.<String>any())).thenReturn(ofResult);
        assertSame(ec2Instance, computeServices.getInstanceById("42"));
        verify(eC2InstanceRepository).findByInstanceId(Mockito.<String>any());
    }

    /**
     * Method under test: {@link ComputeServices#getAllVolumes()}
     */
    @Test
    void testGetAllVolumes() {
        ArrayList<EBSVolume> ebsVolumeList = new ArrayList<>();
        when(eBSVolumeRepository.findAll()).thenReturn(ebsVolumeList);
        List<EBSVolume> actualAllVolumes = computeServices.getAllVolumes();
        assertSame(ebsVolumeList, actualAllVolumes);
        assertTrue(actualAllVolumes.isEmpty());
        verify(eBSVolumeRepository).findAll();
    }

    /**
     * Method under test: {@link ComputeServices#getAllAddresses()}
     */
    @Test
    void testGetAllAddresses() {
        ArrayList<EIPAddress> eipAddressList = new ArrayList<>();
        when(eIPAddressRepository.findAll()).thenReturn(eipAddressList);
        List<EIPAddress> actualAllAddresses = computeServices.getAllAddresses();
        assertSame(eipAddressList, actualAllAddresses);
        assertTrue(actualAllAddresses.isEmpty());
        verify(eIPAddressRepository).findAll();
    }

    /**
     * Method under test: {@link ComputeServices#checkEc2InstancesState()}
     */
    @Test
    void testCheckEc2InstancesState() {
        when(utilityServices.getEc2ClientsListWithAccountId()).thenReturn(new ArrayList<>());
        when(eC2InstanceRepository.saveAllAndFlush(Mockito.<Iterable<EC2Instance>>any())).thenReturn(new ArrayList<>());
        when(eC2InstanceRepository.findAll()).thenReturn(new ArrayList<>());
        computeServices.checkEc2InstancesState();
        verify(utilityServices).getEc2ClientsListWithAccountId();
        verify(eC2InstanceRepository).saveAllAndFlush(Mockito.<Iterable<EC2Instance>>any());
        verify(eC2InstanceRepository).findAll();
    }

    /**
     * Method under test: {@link ComputeServices#checkEc2InstancesState()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testCheckEc2InstancesState2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.amazonaws.services.ec2.AmazonEC2.describeInstances(com.amazonaws.services.ec2.model.DescribeInstancesRequest)" because "client" is null
        //       at com.vermeg.aws.cost.optimization.dashboard.services.ComputeServices.getAllEC2InstancesWithAccountId(ComputeServices.java:44)
        //       at com.vermeg.aws.cost.optimization.dashboard.services.ComputeServices.checkEc2InstancesState(ComputeServices.java:146)
        //   See https://diff.blue/R013 to resolve this issue.

        ArrayList<Pair<AmazonEC2, String>> pairList = new ArrayList<>();
        ImmutablePair<AmazonEC2, String> nullPairResult = ImmutablePair.nullPair();
        pairList.add(nullPairResult);
        when(utilityServices.getEc2ClientsListWithAccountId()).thenReturn(pairList);
        when(eC2InstanceRepository.saveAllAndFlush(Mockito.<Iterable<EC2Instance>>any())).thenReturn(new ArrayList<>());
        when(eC2InstanceRepository.findAll()).thenReturn(new ArrayList<>());
        computeServices.checkEc2InstancesState();
    }

    /**
     * Method under test: {@link ComputeServices#checkEc2InstancesState()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testCheckEc2InstancesState3() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.amazonaws.services.ec2.AmazonEC2.describeInstances(com.amazonaws.services.ec2.model.DescribeInstancesRequest)" because "client" is null
        //       at com.vermeg.aws.cost.optimization.dashboard.services.ComputeServices.getAllEC2InstancesWithAccountId(ComputeServices.java:44)
        //       at com.vermeg.aws.cost.optimization.dashboard.services.ComputeServices.checkEc2InstancesState(ComputeServices.java:146)
        //   See https://diff.blue/R013 to resolve this issue.

        ArrayList<Pair<AmazonEC2, String>> pairList = new ArrayList<>();
        ImmutablePair<AmazonEC2, String> nullPairResult = ImmutablePair.nullPair();
        pairList.add(nullPairResult);
        ImmutablePair<AmazonEC2, String> nullPairResult2 = ImmutablePair.nullPair();
        pairList.add(nullPairResult2);
        when(utilityServices.getEc2ClientsListWithAccountId()).thenReturn(pairList);
        when(eC2InstanceRepository.saveAllAndFlush(Mockito.<Iterable<EC2Instance>>any())).thenReturn(new ArrayList<>());
        when(eC2InstanceRepository.findAll()).thenReturn(new ArrayList<>());
        computeServices.checkEc2InstancesState();
    }

    /**
     * Method under test: {@link ComputeServices#checkEc2InstancesState()}
     */
    @Test
    void testCheckEc2InstancesState4() {
        when(utilityServices.getEc2ClientsListWithAccountId()).thenReturn(new ArrayList<>());

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
        ec2Instance.setEnvironmentType("Checking EC2 Instances state");
        ec2Instance.setId(1L);
        ec2Instance.setInstanceId("42");
        ec2Instance.setInstanceType("Checking EC2 Instances state");
        ec2Instance.setLastDowntimeTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        ec2Instance.setLastUpdateTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        ec2Instance.setLastUptimeTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        ec2Instance.setLinkedSuggestion(new HashSet<>());
        ec2Instance.setMonitoring("Checking EC2 Instances state");
        ec2Instance.setOperationHours("Checking EC2 Instances state");
        ec2Instance.setOwnerEmail("jane.doe@example.org");
        ec2Instance.setPlatform("Checking EC2 Instances state");
        ec2Instance.setPrivateIp("Checking EC2 Instances state");
        ec2Instance.setProductId("42");
        ec2Instance.setPublicIp("Checking EC2 Instances state");
        ec2Instance.setRegion("us-east-2");
        ec2Instance.setState("MD");

        ArrayList<EC2Instance> ec2InstanceList = new ArrayList<>();
        ec2InstanceList.add(ec2Instance);
        when(eC2InstanceRepository.saveAllAndFlush(Mockito.<Iterable<EC2Instance>>any())).thenReturn(new ArrayList<>());
        when(eC2InstanceRepository.findAll()).thenReturn(ec2InstanceList);
        computeServices.checkEc2InstancesState();
        verify(utilityServices).getEc2ClientsListWithAccountId();
        verify(eC2InstanceRepository).saveAllAndFlush(Mockito.<Iterable<EC2Instance>>any());
        verify(eC2InstanceRepository).findAll();
    }
}

