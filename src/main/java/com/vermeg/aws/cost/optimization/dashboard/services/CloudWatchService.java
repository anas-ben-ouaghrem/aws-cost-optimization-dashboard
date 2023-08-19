package com.vermeg.aws.cost.optimization.dashboard.services;

import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CloudWatchService {

    private final UtilityServices utilityServices;
    private static final String EBS_NAMESPACE ="AWS/EBS";
    private static final String EC2_NAMESPACE ="AWS/EC2";
    private static final String RDS_NAMESPACE ="AWS/RDS";

    // EC2 Metrics
    public List<Datapoint> getCPUUtilization(String instanceId, int period, int duration) {
        return getMetricData(instanceId, "CPUUtilization", EC2_NAMESPACE, period, duration);
    }

    public List<Datapoint> getNetworkIn(String instanceId, int period, int duration) {
        return getMetricData(instanceId, "NetworkIn", EC2_NAMESPACE, period, duration);
    }

    public List<Datapoint> getNetworkOut(String instanceId, int period, int duration) {
        return getMetricData(instanceId, "NetworkOut", EC2_NAMESPACE, period, duration);
    }

    // RDS Metrics
    public List<Datapoint> getRDSCPUCreditBalance(String instanceId, int period, int duration) {
        return getMetricData(instanceId, "CPUCreditBalance", RDS_NAMESPACE, period, duration);
    }

    public List<Datapoint> getRDSCPUUtilization(String instanceId, int period, int duration) {
        return getMetricData(instanceId, "CPUUtilization", RDS_NAMESPACE, period, duration);
    }

    public List<Datapoint> getRDSFreeableMemory(String instanceId, int period, int duration) {
        return getMetricData(instanceId, "FreeableMemory", RDS_NAMESPACE, period, duration);
    }

    public List<Datapoint> getRDSFreeStorageSpace(String instanceId, int period, int duration) {
        return getMetricData(instanceId, "FreeStorageSpace", RDS_NAMESPACE, period, duration);
    }

    public List<Datapoint> getRDSReadIOPS(String instanceId, int period, int duration) {
        return getMetricData(instanceId, "ReadIOPS", RDS_NAMESPACE, period, duration);
    }

    public List<Datapoint> getRDSReadLatency(String instanceId, int period, int duration) {
        return getMetricData(instanceId, "ReadLatency", RDS_NAMESPACE, period, duration);
    }

    public List<Datapoint> getRDSReadThroughput(String instanceId, int period, int duration) {
        return getMetricData(instanceId, "ReadThroughput", RDS_NAMESPACE, period, duration);
    }

    public List<Datapoint> getRDSWriteIOPS(String instanceId, int period, int duration) {
        return getMetricData(instanceId, "WriteIOPS", RDS_NAMESPACE, period, duration);
    }

    public List<Datapoint> getRDSWriteLatency(String instanceId, int period, int duration) {
        return getMetricData(instanceId, "WriteLatency", RDS_NAMESPACE, period, duration);
    }

    public List<Datapoint> getRDSWriteThroughput(String instanceId, int period, int duration) {
        return getMetricData(instanceId, "WriteThroughput", RDS_NAMESPACE, period, duration);
    }

    // S3 Metrics

    public List<Datapoint> getS3BucketSize(String instanceId, int period, int duration) {
        return getS3MetricData(instanceId, "BucketSizeBytes",  period, duration);
    }

    public List<Datapoint> getS3NumberOfObjects(String instanceId, int period, int duration) {
        return getS3MetricData(instanceId, "NumberOfObjects",  period, duration);
    }

    // EBS Metrics

    public List<Datapoint> getEBSVolumeReadBytes(String instanceId, int period, int duration) {
        return getMetricData(instanceId, "VolumeReadBytes", EBS_NAMESPACE, period, duration);
    }

    public List<Datapoint> getEBSVolumeWriteBytes(String instanceId, int period, int duration) {
        return getMetricData(instanceId, "VolumeWriteBytes", EBS_NAMESPACE, period, duration);
    }

    public List<Datapoint> getEBSVolumeReadOps(String instanceId, int period, int duration) {
        return getMetricData(instanceId, "VolumeReadOps", EBS_NAMESPACE, period, duration);
    }

    public List<Datapoint> getEBSVolumeWriteOps(String instanceId, int period, int duration) {
        return getMetricData(instanceId, "VolumeWriteOps", EBS_NAMESPACE, period, duration);
    }

    // EIP Metrics

    public List<Datapoint> getEIPIn(String instanceId, int period, int duration) {
        return getMetricData(instanceId, "In", EC2_NAMESPACE, period, duration);
    }

    public List<Datapoint> getEIPOut(String instanceId, int period, int duration) {
        return getMetricData(instanceId, "Out", EC2_NAMESPACE, period, duration);
    }


    private List<Datapoint> getMetricData(String instanceId, String metricName, String namespace, int period, int duration) {
        GetMetricStatisticsRequest getMetricStatisticsRequest = new GetMetricStatisticsRequest()
                .withNamespace(namespace)
                .withMetricName(metricName)
                .withDimensions(new Dimension().withName("InstanceId").withValue(instanceId))
                .withStartTime(new Date(System.currentTimeMillis() - duration * 1000L))
                .withEndTime(new Date())
                .withPeriod(period)
                .withStatistics(Statistic.SampleCount, Statistic.Average, Statistic.Maximum, Statistic.Minimum);
        AmazonCloudWatch cloudWatchClient = utilityServices.getCloudWatchClient(instanceId, namespace);
        log.info("cloudWatchClient: " + cloudWatchClient);
        GetMetricStatisticsResult result = cloudWatchClient.getMetricStatistics(getMetricStatisticsRequest);
        return result.getDatapoints();
    }

    private List<Datapoint> getS3MetricData(String bucketName, String metricName, int period, int duration){
        GetMetricStatisticsRequest request = new GetMetricStatisticsRequest()
                .withNamespace("AWS/S3")
                .withMetricName(metricName)
                .withDimensions(new Dimension().withName("BucketName").withValue(bucketName))
                .withStartTime(new Date(System.currentTimeMillis() - duration * 1000L))
                .withEndTime(new Date())
                .withPeriod(period)
                .withStatistics(Statistic.SampleCount, Statistic.Average, Statistic.Sum, Statistic.Maximum, Statistic.Minimum)
                .withUnit(StandardUnit.None);
        AmazonCloudWatch cloudWatchClient = utilityServices.getCloudWatchClient(bucketName, "AWS/S3");
        GetMetricStatisticsResult result = cloudWatchClient.getMetricStatistics(request);
        return result.getDatapoints();
    }

}
