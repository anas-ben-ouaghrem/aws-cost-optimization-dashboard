package com.vermeg.aws.cost.optimization.dashboard.services;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CostExplorerService.class})
@ExtendWith(SpringExtension.class)
class CostExplorerServiceTest {
    @Autowired
    private CostExplorerService costExplorerService;

    /**
     * Method under test: {@link CostExplorerService#getCostAndUsage(String, String, String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetCostAndUsage() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy81.connect(Unknown Source)
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
            //       at com.amazonaws.services.costexplorer.AWSCostExplorerClient.doInvoke(AWSCostExplorerClient.java:2831)
            //       at com.amazonaws.services.costexplorer.AWSCostExplorerClient.invoke(AWSCostExplorerClient.java:2798)
            //       at com.amazonaws.services.costexplorer.AWSCostExplorerClient.invoke(AWSCostExplorerClient.java:2787)
            //       at com.amazonaws.services.costexplorer.AWSCostExplorerClient.executeGetCostAndUsage(AWSCostExplorerClient.java:866)
            //       at com.amazonaws.services.costexplorer.AWSCostExplorerClient.getCostAndUsage(AWSCostExplorerClient.java:835)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CostExplorerService.getCostAndUsage(CostExplorerService.java:27)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            costExplorerService.getCostAndUsage("42", "2020-03-01", "2020-03-01");
        }
    }

    /**
     * Method under test: {@link CostExplorerService#getCostForecast(String, String, String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetCostForecast() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy81.connect(Unknown Source)
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
            //       at com.amazonaws.services.costexplorer.AWSCostExplorerClient.doInvoke(AWSCostExplorerClient.java:2831)
            //       at com.amazonaws.services.costexplorer.AWSCostExplorerClient.invoke(AWSCostExplorerClient.java:2798)
            //       at com.amazonaws.services.costexplorer.AWSCostExplorerClient.invoke(AWSCostExplorerClient.java:2787)
            //       at com.amazonaws.services.costexplorer.AWSCostExplorerClient.executeGetCostForecast(AWSCostExplorerClient.java:1079)
            //       at com.amazonaws.services.costexplorer.AWSCostExplorerClient.getCostForecast(AWSCostExplorerClient.java:1048)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CostExplorerService.getCostForecast(CostExplorerService.java:37)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            costExplorerService.getCostForecast("42", "2020-03-01", "2020-03-01");
        }
    }

    /**
     * Method under test: {@link CostExplorerService#getDimensionValues(String, String, String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetDimensionValues() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy81.connect(Unknown Source)
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
            //       at com.amazonaws.services.costexplorer.AWSCostExplorerClient.doInvoke(AWSCostExplorerClient.java:2831)
            //       at com.amazonaws.services.costexplorer.AWSCostExplorerClient.invoke(AWSCostExplorerClient.java:2798)
            //       at com.amazonaws.services.costexplorer.AWSCostExplorerClient.invoke(AWSCostExplorerClient.java:2787)
            //       at com.amazonaws.services.costexplorer.AWSCostExplorerClient.executeGetDimensionValues(AWSCostExplorerClient.java:1146)
            //       at com.amazonaws.services.costexplorer.AWSCostExplorerClient.getDimensionValues(AWSCostExplorerClient.java:1115)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CostExplorerService.getDimensionValues(CostExplorerService.java:45)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            costExplorerService.getDimensionValues("Dimension", "2020-03-01", "2020-03-01");
        }
    }

    /**
     * Method under test: {@link CostExplorerService#getReservationUtilization(String, String, String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetReservationUtilization() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy81.connect(Unknown Source)
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
            //       at com.amazonaws.services.costexplorer.AWSCostExplorerClient.doInvoke(AWSCostExplorerClient.java:2831)
            //       at com.amazonaws.services.costexplorer.AWSCostExplorerClient.invoke(AWSCostExplorerClient.java:2798)
            //       at com.amazonaws.services.costexplorer.AWSCostExplorerClient.invoke(AWSCostExplorerClient.java:2787)
            //       at com.amazonaws.services.costexplorer.AWSCostExplorerClient.executeGetReservationUtilization(AWSCostExplorerClient.java:1423)
            //       at com.amazonaws.services.costexplorer.AWSCostExplorerClient.getReservationUtilization(AWSCostExplorerClient.java:1390)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CostExplorerService.getReservationUtilization(CostExplorerService.java:53)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            costExplorerService.getReservationUtilization("42", "2020-03-01", "2020-03-01");
        }
    }

    /**
     * Method under test: {@link CostExplorerService#getReservationCoverage(String, String, String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetReservationCoverage() throws UnknownHostException {
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
            //       at com.amazonaws.http.conn.$Proxy81.connect(Unknown Source)
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
            //       at com.amazonaws.services.costexplorer.AWSCostExplorerClient.doInvoke(AWSCostExplorerClient.java:2831)
            //       at com.amazonaws.services.costexplorer.AWSCostExplorerClient.invoke(AWSCostExplorerClient.java:2798)
            //       at com.amazonaws.services.costexplorer.AWSCostExplorerClient.invoke(AWSCostExplorerClient.java:2787)
            //       at com.amazonaws.services.costexplorer.AWSCostExplorerClient.executeGetReservationCoverage(AWSCostExplorerClient.java:1279)
            //       at com.amazonaws.services.costexplorer.AWSCostExplorerClient.getReservationCoverage(AWSCostExplorerClient.java:1247)
            //       at com.vermeg.aws.cost.optimization.dashboard.services.CostExplorerService.getReservationCoverage(CostExplorerService.java:60)
            //   See https://diff.blue/R013 to resolve this issue.

            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any()))
                    .thenReturn(new InetAddress[]{mock(InetAddress.class)});
            costExplorerService.getReservationCoverage("42", "2020-03-01", "2020-03-01");
        }
    }
}

