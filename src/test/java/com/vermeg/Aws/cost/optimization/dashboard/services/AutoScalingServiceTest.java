package com.vermeg.aws.cost.optimization.dashboard.services;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.amazonaws.services.autoscaling.AmazonAutoScaling;
import com.amazonaws.services.autoscaling.AmazonAutoScalingAsyncClient;
import com.amazonaws.services.autoscaling.model.CreateAutoScalingGroupRequest;
import com.amazonaws.services.autoscaling.model.CreateAutoScalingGroupResult;
import com.amazonaws.services.autoscaling.model.CreateLaunchConfigurationRequest;
import com.amazonaws.services.autoscaling.model.CreateLaunchConfigurationResult;
import com.amazonaws.services.autoscaling.model.DescribeAutoScalingGroupsResult;

import java.net.InetAddress;
import java.net.UnknownHostException;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.MutablePair;
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

@ContextConfiguration(classes = {AutoScalingService.class})
@ExtendWith(SpringExtension.class)
class AutoScalingServiceTest {
    @Autowired
    private AutoScalingService autoScalingService;

    @MockBean
    private UtilityServices utilityServices;

    /**
     * Method under test: {@link AutoScalingService#getAllAutoScalingGroups()}
     */
    @Test
    void testGetAllAutoScalingGroups() {
        when(utilityServices.getAutoscalingClientsList()).thenReturn(new ArrayList<>());
        assertTrue(autoScalingService.getAllAutoScalingGroups().isEmpty());
        verify(utilityServices).getAutoscalingClientsList();
    }

    /**
     * Method under test: {@link AutoScalingService#getAllAutoScalingGroups()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetAllAutoScalingGroups2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.amazonaws.services.autoscaling.AmazonAutoScaling.describeAutoScalingGroups()" because "client" is null
        //       at com.vermeg.aws.cost.optimization.dashboard.services.AutoScalingService.getAllAutoScalingGroups(AutoScalingService.java:22)
        //   See https://diff.blue/R013 to resolve this issue.

        ArrayList<Pair<AmazonAutoScaling, String>> pairList = new ArrayList<>();
        ImmutablePair<AmazonAutoScaling, String> nullPairResult = ImmutablePair.nullPair();
        pairList.add(nullPairResult);
        when(utilityServices.getAutoscalingClientsList()).thenReturn(pairList);
        autoScalingService.getAllAutoScalingGroups();
    }

    /**
     * Method under test: {@link AutoScalingService#getAllAutoScalingGroups()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetAllAutoScalingGroups3() throws UnknownHostException {
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
            //       at com.amazonaws.services.autoscaling.AmazonAutoScalingClient.doInvoke(AmazonAutoScalingClient.java:5394)
            //       at com.amazonaws.services.autoscaling.AmazonAutoScalingClient.invoke(AmazonAutoScalingClient.java:5361)
            //       at com.amazonaws.services.autoscaling.AmazonAutoScalingClient.invoke(AmazonAutoScalingClient.java:5350)
            //       at com.amazonaws.services.autoscaling.AmazonAutoScalingClient.executeDescribeAutoScalingGroups(AmazonAutoScalingClient.java:2023)
            //       at com.amazonaws.services.autoscaling.AmazonAutoScalingClient.describeAutoScalingGroups(AmazonAutoScalingClient.java:1991)
            //       at com.amazonaws.services.autoscaling.AmazonAutoScalingClient.describeAutoScalingGroups(AmazonAutoScalingClient.java:2035)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.AutoScalingService.getAllAutoScalingGroups(AutoScalingService.java:22)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});

            ArrayList<Pair<AmazonAutoScaling, String>> pairList = new ArrayList<>();
            pairList.add(new ImmutablePair<>(new AmazonAutoScalingAsyncClient(), "Right"));
            when(utilityServices.getAutoscalingClientsList()).thenReturn(pairList);
            autoScalingService.getAllAutoScalingGroups();
        }
    }

    /**
     * Method under test: {@link AutoScalingService#getAllAutoScalingGroups()}
     */
    @Test
    void testGetAllAutoScalingGroups4() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            AmazonAutoScalingAsyncClient amazonAutoScalingAsyncClient = mock(AmazonAutoScalingAsyncClient.class);
            when(amazonAutoScalingAsyncClient.describeAutoScalingGroups())
                    .thenReturn(new DescribeAutoScalingGroupsResult());
            ImmutablePair<AmazonAutoScaling, String> immutablePair = new ImmutablePair<>(amazonAutoScalingAsyncClient,
                    "Right");

            ArrayList<Pair<AmazonAutoScaling, String>> pairList = new ArrayList<>();
            pairList.add(immutablePair);
            when(utilityServices.getAutoscalingClientsList()).thenReturn(pairList);
            assertTrue(autoScalingService.getAllAutoScalingGroups().isEmpty());
            verify(utilityServices).getAutoscalingClientsList();
            verify(amazonAutoScalingAsyncClient).describeAutoScalingGroups();
        }
    }

    /**
     * Method under test: {@link AutoScalingService#getAllAutoScalingGroups()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetAllAutoScalingGroups5() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            // TODO: Complete this test.
            //   Reason: R013 No inputs found that don't throw a trivial exception.
            //   Diffblue Cover tried to run the arrange/act section, but the method under
            //   test threw
            //   java.lang.NullPointerException: Cannot invoke "com.amazonaws.services.autoscaling.model.DescribeAutoScalingGroupsResult.getAutoScalingGroups()" because "result" is null
            //       at com.vermeg.aws.cost.optimization.dashboard.services.AutoScalingService.getAllAutoScalingGroups(AutoScalingService.java:23)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            AmazonAutoScalingAsyncClient amazonAutoScalingAsyncClient = mock(AmazonAutoScalingAsyncClient.class);
            when(amazonAutoScalingAsyncClient.describeAutoScalingGroups()).thenReturn(null);
            ImmutablePair<AmazonAutoScaling, String> immutablePair = new ImmutablePair<>(amazonAutoScalingAsyncClient,
                    "Right");

            ArrayList<Pair<AmazonAutoScaling, String>> pairList = new ArrayList<>();
            pairList.add(immutablePair);
            when(utilityServices.getAutoscalingClientsList()).thenReturn(pairList);
            autoScalingService.getAllAutoScalingGroups();
        }
    }

    /**
     * Method under test: {@link AutoScalingService#getAllAutoScalingGroups()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetAllAutoScalingGroups6() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            // TODO: Complete this test.
            //   Reason: R013 No inputs found that don't throw a trivial exception.
            //   Diffblue Cover tried to run the arrange/act section, but the method under
            //   test threw
            //   java.lang.NullPointerException: Cannot invoke "org.apache.commons.lang3.tuple.Pair.getLeft()" because "pair" is null
            //       at com.vermeg.aws.cost.optimization.dashboard.services.AutoScalingService.getAllAutoScalingGroups(AutoScalingService.java:21)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});

            ArrayList<Pair<AmazonAutoScaling, String>> pairList = new ArrayList<>();
            pairList.add(null);
            when(utilityServices.getAutoscalingClientsList()).thenReturn(pairList);
            autoScalingService.getAllAutoScalingGroups();
        }
    }

    /**
     * Method under test: {@link AutoScalingService#getAllAutoScalingGroups()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetAllAutoScalingGroups7() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            // TODO: Complete this test.
            //   Reason: R013 No inputs found that don't throw a trivial exception.
            //   Diffblue Cover tried to run the arrange/act section, but the method under
            //   test threw
            //   java.lang.NullPointerException: Cannot invoke "com.amazonaws.services.autoscaling.AmazonAutoScaling.describeAutoScalingGroups()" because "client" is null
            //       at com.vermeg.aws.cost.optimization.dashboard.services.AutoScalingService.getAllAutoScalingGroups(AutoScalingService.java:22)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});

            ArrayList<Pair<AmazonAutoScaling, String>> pairList = new ArrayList<>();
            pairList.add(new MutablePair<>());
            when(utilityServices.getAutoscalingClientsList()).thenReturn(pairList);
            autoScalingService.getAllAutoScalingGroups();
        }
    }

    /**
     * Method under test: {@link AutoScalingService#checkAutoscalingEnabled(List)}
     */
    @Test
    void testCheckAutoscalingEnabled() {
        when(utilityServices.getAutoscalingClientsList()).thenReturn(new ArrayList<>());
        assertTrue(autoScalingService.checkAutoscalingEnabled(new ArrayList<>()).isEmpty());
        verify(utilityServices).getAutoscalingClientsList();
    }

    /**
     * Method under test: {@link AutoScalingService#checkAutoscalingEnabled(List)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testCheckAutoscalingEnabled2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.amazonaws.services.autoscaling.AmazonAutoScaling.describeAutoScalingGroups()" because "client" is null
        //       at com.vermeg.aws.cost.optimization.dashboard.services.AutoScalingService.getAllAutoScalingGroups(AutoScalingService.java:22)
        //       at com.vermeg.aws.cost.optimization.dashboard.services.AutoScalingService.checkAutoscalingEnabled(AutoScalingService.java:31)
        //   See https://diff.blue/R013 to resolve this issue.

        ArrayList<Pair<AmazonAutoScaling, String>> pairList = new ArrayList<>();
        ImmutablePair<AmazonAutoScaling, String> nullPairResult = ImmutablePair.nullPair();
        pairList.add(nullPairResult);
        when(utilityServices.getAutoscalingClientsList()).thenReturn(pairList);
        autoScalingService.checkAutoscalingEnabled(new ArrayList<>());
    }

    /**
     * Method under test: {@link AutoScalingService#checkAutoscalingEnabled(List)}
     */
    @Test
    void testCheckAutoscalingEnabled3() {
        when(utilityServices.getAutoscalingClientsList()).thenReturn(new ArrayList<>());

        ArrayList<String> instanceIds = new ArrayList<>();
        instanceIds.add("foo");
        assertTrue(autoScalingService.checkAutoscalingEnabled(instanceIds).isEmpty());
        verify(utilityServices).getAutoscalingClientsList();
    }

    /**
     * Method under test: {@link AutoScalingService#checkAutoscalingEnabled(List)}
     */
    @Test
    void testCheckAutoscalingEnabled4() {
        when(utilityServices.getAutoscalingClientsList()).thenReturn(new ArrayList<>());

        ArrayList<String> instanceIds = new ArrayList<>();
        instanceIds.add("42");
        instanceIds.add("foo");
        assertTrue(autoScalingService.checkAutoscalingEnabled(instanceIds).isEmpty());
        verify(utilityServices).getAutoscalingClientsList();
    }

    /**
     * Method under test: {@link AutoScalingService#isInstanceInAutoScalingGroup(String)}
     */
    @Test
    void testIsInstanceInAutoScalingGroup() {
        when(utilityServices.getAutoscalingClientsList()).thenReturn(new ArrayList<>());
        assertTrue(autoScalingService.isInstanceInAutoScalingGroup("42"));
        verify(utilityServices).getAutoscalingClientsList();
    }

    /**
     * Method under test: {@link AutoScalingService#isInstanceInAutoScalingGroup(String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testIsInstanceInAutoScalingGroup2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.amazonaws.services.autoscaling.AmazonAutoScaling.describeAutoScalingGroups()" because "client" is null
        //       at com.vermeg.aws.cost.optimization.dashboard.services.AutoScalingService.getAllAutoScalingGroups(AutoScalingService.java:22)
        //       at com.vermeg.aws.cost.optimization.dashboard.services.AutoScalingService.isInstanceInAutoScalingGroup(AutoScalingService.java:48)
        //   See https://diff.blue/R013 to resolve this issue.

        ArrayList<Pair<AmazonAutoScaling, String>> pairList = new ArrayList<>();
        ImmutablePair<AmazonAutoScaling, String> nullPairResult = ImmutablePair.nullPair();
        pairList.add(nullPairResult);
        when(utilityServices.getAutoscalingClientsList()).thenReturn(pairList);
        autoScalingService.isInstanceInAutoScalingGroup("42");
    }

    /**
     * Method under test: {@link AutoScalingService#isInstanceInAutoScalingGroup(String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testIsInstanceInAutoScalingGroup3() throws UnknownHostException {
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
            //       at com.amazonaws.services.autoscaling.AmazonAutoScalingClient.doInvoke(AmazonAutoScalingClient.java:5394)
            //       at com.amazonaws.services.autoscaling.AmazonAutoScalingClient.invoke(AmazonAutoScalingClient.java:5361)
            //       at com.amazonaws.services.autoscaling.AmazonAutoScalingClient.invoke(AmazonAutoScalingClient.java:5350)
            //       at com.amazonaws.services.autoscaling.AmazonAutoScalingClient.executeDescribeAutoScalingGroups(AmazonAutoScalingClient.java:2023)
            //       at com.amazonaws.services.autoscaling.AmazonAutoScalingClient.describeAutoScalingGroups(AmazonAutoScalingClient.java:1991)
            //       at com.amazonaws.services.autoscaling.AmazonAutoScalingClient.describeAutoScalingGroups(AmazonAutoScalingClient.java:2035)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.AutoScalingService.getAllAutoScalingGroups(AutoScalingService.java:22)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.AutoScalingService.isInstanceInAutoScalingGroup(AutoScalingService.java:48)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});

            ArrayList<Pair<AmazonAutoScaling, String>> pairList = new ArrayList<>();
            pairList.add(new ImmutablePair<>(new AmazonAutoScalingAsyncClient(), "Right"));
            when(utilityServices.getAutoscalingClientsList()).thenReturn(pairList);
            autoScalingService.isInstanceInAutoScalingGroup("42");
        }
    }

    /**
     * Method under test: {@link AutoScalingService#isInstanceInAutoScalingGroup(String)}
     */
    @Test
    void testIsInstanceInAutoScalingGroup4() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            AmazonAutoScalingAsyncClient amazonAutoScalingAsyncClient = mock(AmazonAutoScalingAsyncClient.class);
            when(amazonAutoScalingAsyncClient.describeAutoScalingGroups())
                    .thenReturn(new DescribeAutoScalingGroupsResult());
            ImmutablePair<AmazonAutoScaling, String> immutablePair = new ImmutablePair<>(amazonAutoScalingAsyncClient,
                    "Right");

            ArrayList<Pair<AmazonAutoScaling, String>> pairList = new ArrayList<>();
            pairList.add(immutablePair);
            when(utilityServices.getAutoscalingClientsList()).thenReturn(pairList);
            assertTrue(autoScalingService.isInstanceInAutoScalingGroup("42"));
            verify(utilityServices).getAutoscalingClientsList();
            verify(amazonAutoScalingAsyncClient).describeAutoScalingGroups();
            assertTrue(autoScalingService.getAllAutoScalingGroups().isEmpty());
        }
    }

    /**
     * Method under test: {@link AutoScalingService#isInstanceInAutoScalingGroup(String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testIsInstanceInAutoScalingGroup5() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            // TODO: Complete this test.
            //   Reason: R013 No inputs found that don't throw a trivial exception.
            //   Diffblue Cover tried to run the arrange/act section, but the method under
            //   test threw
            //   java.lang.NullPointerException: Cannot invoke "com.amazonaws.services.autoscaling.model.DescribeAutoScalingGroupsResult.getAutoScalingGroups()" because "result" is null
            //       at com.vermeg.aws.cost.optimization.dashboard.services.AutoScalingService.getAllAutoScalingGroups(AutoScalingService.java:23)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.AutoScalingService.isInstanceInAutoScalingGroup(AutoScalingService.java:48)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            AmazonAutoScalingAsyncClient amazonAutoScalingAsyncClient = mock(AmazonAutoScalingAsyncClient.class);
            when(amazonAutoScalingAsyncClient.describeAutoScalingGroups()).thenReturn(null);
            ImmutablePair<AmazonAutoScaling, String> immutablePair = new ImmutablePair<>(amazonAutoScalingAsyncClient,
                    "Right");

            ArrayList<Pair<AmazonAutoScaling, String>> pairList = new ArrayList<>();
            pairList.add(immutablePair);
            when(utilityServices.getAutoscalingClientsList()).thenReturn(pairList);
            autoScalingService.isInstanceInAutoScalingGroup("42");
        }
    }

    /**
     * Method under test: {@link AutoScalingService#isInstanceInAutoScalingGroup(String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testIsInstanceInAutoScalingGroup6() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            // TODO: Complete this test.
            //   Reason: R013 No inputs found that don't throw a trivial exception.
            //   Diffblue Cover tried to run the arrange/act section, but the method under
            //   test threw
            //   java.lang.NullPointerException: Cannot invoke "org.apache.commons.lang3.tuple.Pair.getLeft()" because "pair" is null
            //       at com.vermeg.aws.cost.optimization.dashboard.services.AutoScalingService.getAllAutoScalingGroups(AutoScalingService.java:21)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.AutoScalingService.isInstanceInAutoScalingGroup(AutoScalingService.java:48)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});

            ArrayList<Pair<AmazonAutoScaling, String>> pairList = new ArrayList<>();
            pairList.add(null);
            when(utilityServices.getAutoscalingClientsList()).thenReturn(pairList);
            autoScalingService.isInstanceInAutoScalingGroup("42");
        }
    }

    /**
     * Method under test: {@link AutoScalingService#isInstanceInAutoScalingGroup(String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testIsInstanceInAutoScalingGroup7() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            // TODO: Complete this test.
            //   Reason: R013 No inputs found that don't throw a trivial exception.
            //   Diffblue Cover tried to run the arrange/act section, but the method under
            //   test threw
            //   java.lang.NullPointerException: Cannot invoke "com.amazonaws.services.autoscaling.AmazonAutoScaling.describeAutoScalingGroups()" because "client" is null
            //       at com.vermeg.aws.cost.optimization.dashboard.services.AutoScalingService.getAllAutoScalingGroups(AutoScalingService.java:22)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.AutoScalingService.isInstanceInAutoScalingGroup(AutoScalingService.java:48)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});

            ArrayList<Pair<AmazonAutoScaling, String>> pairList = new ArrayList<>();
            pairList.add(new MutablePair<>());
            when(utilityServices.getAutoscalingClientsList()).thenReturn(pairList);
            autoScalingService.isInstanceInAutoScalingGroup("42");
        }
    }

    /**
     * Method under test: {@link AutoScalingService#createAutoScalingGroup(AmazonAutoScaling, String, String, int, int, int)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testCreateAutoScalingGroup() throws UnknownHostException {
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
            //       at com.amazonaws.services.autoscaling.AmazonAutoScalingClient.doInvoke(AmazonAutoScalingClient.java:5394)
            //       at com.amazonaws.services.autoscaling.AmazonAutoScalingClient.invoke(AmazonAutoScalingClient.java:5361)
            //       at com.amazonaws.services.autoscaling.AmazonAutoScalingClient.invoke(AmazonAutoScalingClient.java:5350)
            //       at com.amazonaws.services.autoscaling.AmazonAutoScalingClient.executeCreateAutoScalingGroup(AmazonAutoScalingClient.java:1100)
            //       at com.amazonaws.services.autoscaling.AmazonAutoScalingClient.createAutoScalingGroup(AmazonAutoScalingClient.java:1068)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.AutoScalingService.createAutoScalingGroup(AutoScalingService.java:69)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            autoScalingService.createAutoScalingGroup(new AmazonAutoScalingAsyncClient(), "Auto Scaling Group Name",
                    "Launch Configuration Name", 1, 3, 1);
        }
    }

    /**
     * Method under test: {@link AutoScalingService#createAutoScalingGroup(AmazonAutoScaling, String, String, int, int, int)}
     */
    @Test
    void testCreateAutoScalingGroup2() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            AmazonAutoScalingAsyncClient autoScalingClient = mock(AmazonAutoScalingAsyncClient.class);
            when(autoScalingClient.createAutoScalingGroup(Mockito.<CreateAutoScalingGroupRequest>any()))
                    .thenReturn(new CreateAutoScalingGroupResult());
            autoScalingService.createAutoScalingGroup(autoScalingClient, "Auto Scaling Group Name",
                    "Launch Configuration Name", 1, 3, 1);
            verify(autoScalingClient).createAutoScalingGroup(Mockito.<CreateAutoScalingGroupRequest>any());
        }
    }

    /**
     * Method under test: {@link AutoScalingService#createLaunchConfiguration(AmazonAutoScaling, String, String, String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testCreateLaunchConfiguration() throws UnknownHostException {
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
            //       at com.amazonaws.services.autoscaling.AmazonAutoScalingClient.doInvoke(AmazonAutoScalingClient.java:5394)
            //       at com.amazonaws.services.autoscaling.AmazonAutoScalingClient.invoke(AmazonAutoScalingClient.java:5361)
            //       at com.amazonaws.services.autoscaling.AmazonAutoScalingClient.invoke(AmazonAutoScalingClient.java:5350)
            //       at com.amazonaws.services.autoscaling.AmazonAutoScalingClient.executeCreateLaunchConfiguration(AmazonAutoScalingClient.java:1186)
            //       at com.amazonaws.services.autoscaling.AmazonAutoScalingClient.createLaunchConfiguration(AmazonAutoScalingClient.java:1154)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.AutoScalingService.createLaunchConfiguration(AutoScalingService.java:78)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            autoScalingService.createLaunchConfiguration(new AmazonAutoScalingAsyncClient(), "Launch Configuration Name",
                    "Instance Type", "42");
        }
    }

    /**
     * Method under test: {@link AutoScalingService#createLaunchConfiguration(AmazonAutoScaling, String, String, String)}
     */
    @Test
    void testCreateLaunchConfiguration2() throws UnknownHostException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            AmazonAutoScalingAsyncClient autoScalingClient = mock(AmazonAutoScalingAsyncClient.class);
            when(autoScalingClient.createLaunchConfiguration(Mockito.<CreateLaunchConfigurationRequest>any()))
                    .thenReturn(new CreateLaunchConfigurationResult());
            autoScalingService.createLaunchConfiguration(autoScalingClient, "Launch Configuration Name", "Instance Type",
                    "42");
            verify(autoScalingClient).createLaunchConfiguration(Mockito.<CreateLaunchConfigurationRequest>any());
        }
    }
}

