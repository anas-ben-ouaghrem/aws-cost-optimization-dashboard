package com.vermeg.aws.cost.optimization.dashboard.services;

import com.amazonaws.services.autoscaling.AmazonAutoScaling;
import com.amazonaws.services.autoscaling.model.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AutoScalingService {

    private final UtilityServices utilityServices;

    public List<AutoScalingGroup> getAllAutoScalingGroups() {
        List<AutoScalingGroup> groups = new ArrayList<>();
        for (Pair<AmazonAutoScaling,String> pair : utilityServices.getAutoscalingClientsList()) {
            AmazonAutoScaling client = pair.getLeft();
            DescribeAutoScalingGroupsResult result = client.describeAutoScalingGroups();
            groups.addAll(result.getAutoScalingGroups());
        }
        return groups;
    }

    public List<String> checkAutoscalingEnabled(List<String> instanceIds) {
        List<String> instancesWithAutoscaling = new ArrayList<>();

        for (AutoScalingGroup group : getAllAutoScalingGroups()) {
            List<String> instanceIdsInGroup = new ArrayList<>();
            for (Instance instance : group.getInstances()) {
                instanceIdsInGroup.add(instance.getInstanceId());
            }

            for (String id : instanceIds) {
                if (instanceIdsInGroup.contains(id)) {
                    instancesWithAutoscaling.add(id);
                }
            }
        }

        return instancesWithAutoscaling;
    }

    public boolean isInstanceInAutoScalingGroup(String resourceId) {
        List<AutoScalingGroup> autoScalingGroups = getAllAutoScalingGroups();
        for (AutoScalingGroup group : autoScalingGroups) {
            List<Instance> instances = group.getInstances();
            for (Instance instance : instances) {
                String instanceId = instance.getInstanceId();
                if (instanceId.equals(resourceId)) {
                    return false;
                }
            }
        }
        return true;
    }

    public void createAutoScalingGroup(AmazonAutoScaling autoScalingClient,String autoScalingGroupName, String launchConfigurationName, int minSize, int maxSize, int desiredCapacity) {
        CreateAutoScalingGroupRequest request = new CreateAutoScalingGroupRequest()
                .withAutoScalingGroupName(autoScalingGroupName)
                .withLaunchConfigurationName(launchConfigurationName)
                .withMinSize(minSize)
                .withMaxSize(maxSize)
                .withDesiredCapacity(desiredCapacity);

        autoScalingClient.createAutoScalingGroup(request);
    }

    public void createLaunchConfiguration(AmazonAutoScaling autoScalingClient,String launchConfigurationName, String instanceType, String imageId) {
        CreateLaunchConfigurationRequest request = new CreateLaunchConfigurationRequest()
                .withLaunchConfigurationName(launchConfigurationName)
                .withInstanceType(instanceType)
                .withImageId(imageId);

        autoScalingClient.createLaunchConfiguration(request);
    }
}
