package com.vermeg.aws.cost.optimization.dashboard.services;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.model.*;
import com.vermeg.aws.cost.optimization.dashboard.dto.EbsDTO;
import com.vermeg.aws.cost.optimization.dashboard.dto.Ec2DTO;
import com.vermeg.aws.cost.optimization.dashboard.dto.EipDTO;
import com.vermeg.aws.cost.optimization.dashboard.entities.EBSVolume;
import com.vermeg.aws.cost.optimization.dashboard.entities.EC2Instance;
import com.vermeg.aws.cost.optimization.dashboard.entities.EIPAddress;
import com.vermeg.aws.cost.optimization.dashboard.repositories.EBSVolumeRepository;
import com.vermeg.aws.cost.optimization.dashboard.repositories.EC2InstanceRepository;
import com.vermeg.aws.cost.optimization.dashboard.repositories.EIPAddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ComputeServices {

    private final UtilityServices utilityServices;
    private final EC2InstanceRepository ec2InstanceRepository;
    private final EBSVolumeRepository ebsVolumeRepository;
    private final EIPAddressRepository eipAddressRepository;

    public List<Ec2DTO> getAllEC2InstancesWithAccountId() {
        List<org.apache.commons.lang3.tuple.Pair<AmazonEC2, String>> ec2ClientsWithAccountId = utilityServices.getEc2ClientsListWithAccountId();
        List<Ec2DTO> ec2DTOs = new ArrayList<>();

        for (Pair<AmazonEC2, String> pair : ec2ClientsWithAccountId) {
            AmazonEC2 client = pair.getKey();
            String accountId = pair.getValue();

            DescribeInstancesRequest request = new DescribeInstancesRequest();
            DescribeInstancesResult result = client.describeInstances(request);

            List<Reservation> reservations = result.getReservations();
            for (Reservation reservation : reservations) {
                List<Instance> instances = reservation.getInstances();
                for (Instance instance : instances) {
                    Ec2DTO ec2DTO = Ec2DTO.builder()
                            .instanceId(instance.getInstanceId())
                            .instanceType(instance.getInstanceType())
                            .platform(instance.getPlatformDetails())
                            .region(instance.getPlacement().getAvailabilityZone().substring(0, instance.getPlacement().getAvailabilityZone().length() - 1))
                            .state(instance.getState().getName())
                            .monitoring(instance.getMonitoring().getState().toLowerCase())
                            .publicIp(instance.getPublicIpAddress())
                            .privateIp(instance.getPrivateIpAddress())
                            .tags(instance.getTags().stream().collect(Collectors.toMap(Tag::getKey, Tag::getValue)))
                            .associatedAccount(accountId)
                            .build();
                    ec2DTOs.add(ec2DTO);
                }
            }
        }
        return ec2DTOs;
    }

    public List<EbsDTO> getAllEbsVolumesWithAccountId() {
        List<org.apache.commons.lang3.tuple.Pair<AmazonEC2, String>> ec2ClientsWithAccountId = utilityServices.getEc2ClientsListWithAccountId();
        List<EbsDTO> ebsDTOs = new ArrayList<>();

        for (Pair<AmazonEC2, String> pair : ec2ClientsWithAccountId) {
            AmazonEC2 client = pair.getKey();
            String accountId = pair.getValue();

            DescribeVolumesRequest request = new DescribeVolumesRequest();
            DescribeVolumesResult result = client.describeVolumes(request);

            List<Volume> volumes = result.getVolumes();
            for(Volume volume : volumes){
                EbsDTO ebsDTO = EbsDTO.builder()
                        .volumeId(volume.getVolumeId())
                        .volumeType(volume.getVolumeType())
                        .size(volume.getSize())
                        .creationTime(volume.getCreateTime())
                        .state(volume.getState())
                        .region(volume.getAvailabilityZone().substring(0, volume.getAvailabilityZone().length() - 1))
                        .tags(volume.getTags().stream().collect(Collectors.toMap(Tag::getKey, Tag::getValue)))
                        .associatedAccount(accountId)
                        .instanceId(volume.getAttachments().get(0).getInstanceId())
                        .build();
                ebsDTOs.add(ebsDTO);
            }
        }
        return ebsDTOs;
    }

    public List<EipDTO> getAllElasticIPsWithAccountId() {
        List<org.apache.commons.lang3.tuple.Pair<AmazonEC2, String>> ec2ClientsWithAccountId = utilityServices.getEc2ClientsListWithAccountId();
        List<EipDTO> eipDTOs = new ArrayList<>();

        for (Pair<AmazonEC2, String> pair : ec2ClientsWithAccountId) {
            AmazonEC2 client = pair.getKey();
            String accountId = pair.getValue();

            DescribeAddressesRequest request = new DescribeAddressesRequest();
            DescribeAddressesResult result = client.describeAddresses(request);

            List<Address> addresses = result.getAddresses();
            for(Address address : addresses){
                EipDTO eipDTO = EipDTO.builder()
                        .publicIp(address.getPublicIp())
                        .allocationId(address.getAllocationId())
                        .associatedAccount(accountId)
                        .associationId(address.getAssociationId())
                        .instanceId(address.getInstanceId())
                        .privateIp(address.getPrivateIpAddress())
                        .build();
                eipDTOs.add(eipDTO);
            }
        }
        return eipDTOs;
    }

    public List<EC2Instance> getAllInstances(){
        return ec2InstanceRepository.findAll();
    }

    public EC2Instance getInstanceById(String id){
        return ec2InstanceRepository.findByInstanceId(id).orElse(null);
    }

    public List<EBSVolume> getAllVolumes(){
        return ebsVolumeRepository.findAll();
    }

    public List<EIPAddress> getAllAddresses(){
        return eipAddressRepository.findAll();
    }

    @Scheduled(fixedDelay = 3600000L)
    public void checkEc2InstancesState() {
        log.info("Checking EC2 Instances state");
        List<EC2Instance> instances = ec2InstanceRepository.findAll();
        List<Ec2DTO> ec2DTOs = getAllEC2InstancesWithAccountId();

        for (EC2Instance instance : instances) {
            for (Ec2DTO ec2DTO : ec2DTOs) {
                if (instance.getInstanceId().equals(ec2DTO.getInstanceId())) {
                    String currentEntityState = instance.getState();
                    String currentDTOState = ec2DTO.getState();

                    if (!currentEntityState.equals(currentDTOState)) {
                        // States are different, update the entity
                        instance.setState(currentDTOState);
                        // Update timestamps accordingly based on the state change
                        if (currentDTOState.equals("running")) {
                            instance.setLastUptimeTimestamp(LocalDateTime.now());
                        } else if (currentDTOState.equals("stopped")) {
                            instance.setLastDowntimeTimestamp(LocalDateTime.now());
                        }
                    }
                    break;
                }
            }
        }

        // Save the updated instances back to the repository
        ec2InstanceRepository.saveAllAndFlush(instances);
        log.info("EC2 Instances state checked");
    }

}
