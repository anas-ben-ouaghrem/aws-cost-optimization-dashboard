package com.vermeg.aws.cost.optimization.dashboard.controllers;

import com.amazonaws.services.cloudwatch.model.Datapoint;
import com.vermeg.aws.cost.optimization.dashboard.services.CloudWatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cloudwatch")
@CrossOrigin("*")
public class CloudWatchController {

    private final CloudWatchService cloudWatchService;
    // EC2
    @GetMapping("/getEC2CPUUtilization")
    public List<Datapoint> getEC2CPUUtilization(String instanceId, int period, int duration) {
        return cloudWatchService.getCPUUtilization(instanceId, period, duration);
    }

    @GetMapping("/getEC2NetworkIn")
    public List<Datapoint> getEC2NetworkIn(String instanceId, int period, int duration) {
        return cloudWatchService.getNetworkIn(instanceId, period, duration);
    }

    @GetMapping("/getEC2NetworkOut")
    public List<Datapoint> getEC2NetworkOut(String instanceId, int period, int duration) {
        return cloudWatchService.getNetworkOut(instanceId, period, duration);
    }

    // RDS

    @GetMapping("/getRDSCPUUtilization")
    public List<Datapoint> getRDSCPUUtilization(String instanceId, int period, int duration) {
        return cloudWatchService.getRDSCPUUtilization(instanceId, period, duration);
    }

    @GetMapping("/getRDSFreeableMemory")
    public List<Datapoint> getRDSFreeableMemory(String instanceId, int period, int duration) {
        return cloudWatchService.getRDSFreeableMemory(instanceId, period, duration);
    }

    @GetMapping("/getRDSFreeStorageSpace")
    public List<Datapoint> getRDSFreeStorageSpace(String instanceId, int period, int duration) {
        return cloudWatchService.getRDSFreeStorageSpace(instanceId, period, duration);
    }

    @GetMapping("/getRDSCPUCreditBalance")
    public List<Datapoint> getRDSCPUCreditBalance(String instanceId, int period, int duration) {
        return cloudWatchService.getRDSCPUCreditBalance(instanceId, period, duration);
    }

    @GetMapping("/getRDSReadIOPS")
    public List<Datapoint> getRDSReadIOPS(String instanceId, int period, int duration) {
        return cloudWatchService.getRDSReadIOPS(instanceId, period, duration);
    }

    @GetMapping("/getRDSWriteIOPS")
    public List<Datapoint> getRDSWriteIOPS(String instanceId, int period, int duration) {
        return cloudWatchService.getRDSWriteIOPS(instanceId, period, duration);
    }

    @GetMapping("/getRDSReadLatency")
    public List<Datapoint> getRDSReadLatency(String instanceId, int period, int duration) {
        return cloudWatchService.getRDSReadLatency(instanceId, period, duration);
    }

    @GetMapping("/getRDSWriteLatency")
    public List<Datapoint> getRDSWriteLatency(String instanceId, int period, int duration) {
        return cloudWatchService.getRDSWriteLatency(instanceId, period, duration);
    }

    @GetMapping("/getRDSReadThroughput")
    public List<Datapoint> getRDSReadThroughput(String instanceId, int period, int duration) {
        return cloudWatchService.getRDSReadThroughput(instanceId, period, duration);
    }

    @GetMapping("/getRDSWriteThroughput")
    public List<Datapoint> getRDSWriteThroughput(String instanceId, int period, int duration) {
        return cloudWatchService.getRDSWriteThroughput(instanceId, period, duration);
    }

    // S3

    @GetMapping("/getS3NumberOfObjects")
    public List<Datapoint> getS3NumberOfObjects(String instanceId, int period, int duration) {
        return cloudWatchService.getS3NumberOfObjects(instanceId, period, duration);
    }

    @GetMapping("/getS3BucketSizeBytes")
    public List<Datapoint> getS3BucketSizeBytes(String instanceId, int period, int duration) {
        return cloudWatchService.getS3BucketSize(instanceId, period, duration);
    }

    // EBS
    @GetMapping("/getEBSVolumeReadOps")
    public List<Datapoint> getEBSVolumeReadOps(String instanceId, int period, int duration) {
        return cloudWatchService.getEBSVolumeReadOps(instanceId, period, duration);
    }

    @GetMapping("/getEBSVolumeWriteOps")
    public List<Datapoint> getEBSVolumeWriteOps(String instanceId, int period, int duration) {
        return cloudWatchService.getEBSVolumeWriteOps(instanceId, period, duration);
    }

    @GetMapping("/getEBSVolumeReadBytes")
    public List<Datapoint> getEBSVolumeReadBytes(String instanceId, int period, int duration) {
        return cloudWatchService.getEBSVolumeReadBytes(instanceId, period, duration);
    }

    @GetMapping("/getEBSVolumeWriteBytes")
    public List<Datapoint> getEBSVolumeWriteBytes(String instanceId, int period, int duration) {
        return cloudWatchService.getEBSVolumeWriteBytes(instanceId, period, duration);
    }

    // EIP
    @GetMapping("/getEIPIn")
    public List<Datapoint> getEIPs(String instanceId, int period, int duration) {
        return cloudWatchService.getEIPIn(instanceId, period, duration);
    }

    @GetMapping("/getEIPOut")
    public List<Datapoint> getEIPOut(String instanceId, int period, int duration) {
        return cloudWatchService.getEIPOut(instanceId, period, duration);
    }





}
