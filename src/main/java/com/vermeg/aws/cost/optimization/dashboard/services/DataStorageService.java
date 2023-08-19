package com.vermeg.aws.cost.optimization.dashboard.services;

import com.vermeg.aws.cost.optimization.dashboard.dto.*;
import com.vermeg.aws.cost.optimization.dashboard.entities.*;
import com.vermeg.aws.cost.optimization.dashboard.repositories.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DataStorageService {

    // Local variables
    private final AwsCredentialsService credentialsService;
    private final ComputeServices computeServices;
    private final S3Service s3Service;
    private final RDSService rdsService;
    private final EC2InstanceRepository ec2InstanceRepository;
    private final EBSVolumeRepository ebsVolumeRepository;
    private final EIPAddressRepository eipAddressRepository;
    private final StorageBucketRepository storageBucketRepository;
    private final RDSInstanceRepository rdsInstanceRepository;
    private final Ec2InstanceBackupRepository ec2InstanceBackupRepository;
    private final EbsVolumeBackupRepository ebsVolumeBackupRepository;
    private final EipAddressBackupRepository eipAddressBackupRepository;
    private final RdsInstanceBackupRepository rdsInstanceBackupRepository;
    private final S3BucketBackupRepository s3BucketBackupRepository;

    // Tag names
    private static final String OWNER_EMAIL = "OwnerEmail";
    private static final String CLIENT_NAME = "ClientName";
    private static final String ENVIRONMENT_TYPE = "EnvironmentType";
    private static final String OPERATION_HOURS = "OperationHours";
    private static final String PRODUCT_ID = "ProductId";

    // Methods

    @Scheduled(fixedDelay = 3600000L)
    @Transactional
    public void storeEc2Instances() {
        log.info("Storing EC2 Instances");

        List<Ec2DTO> currentInstances = computeServices.getAllEC2InstancesWithAccountId();
        List<EC2Instance> instancesInDB = computeServices.getAllInstances();

        List<EC2Instance> instancesToDelete = new ArrayList<>();
        List<Ec2DTO> instancesToAdd = new ArrayList<>();
        List<EC2Instance> instancesToUpdateInDB = new ArrayList<>();
        List<Ec2DTO> instancesToUpdate = new ArrayList<>();

        for (EC2Instance dbInstance : instancesInDB) {
            boolean found = false;
            for (Ec2DTO currentInstance : currentInstances) {
                if (dbInstance.getInstanceId().equals(currentInstance.getInstanceId())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                instancesToDelete.add(dbInstance);
            }
        }

        // Identify instances to add and update
        for (Ec2DTO currentInstance : currentInstances) {
            boolean found = false;
            for (EC2Instance dbInstance : instancesInDB) {
                if (dbInstance.getInstanceId().equals(currentInstance.getInstanceId())) {
                    found = true;
                        instancesToUpdateInDB.add(dbInstance);
                        instancesToUpdate.add(currentInstance);
                    break;
                }
            }
            if (!found) {
                instancesToAdd.add(currentInstance);
            }
        }

        this.deleteInstances(instancesToDelete);
        this.addInstances(instancesToAdd);
        this.updateInstances(instancesToUpdateInDB, instancesToUpdate);

        this.storeEc2Backup(ec2InstanceRepository.findAll());

        log.info("EC2 Instances stored");
    }

    private void updateInstances(List<EC2Instance> instancesToUpdate,List<Ec2DTO> instancesToUpdateDTO) {
        for (int i = 0; i < instancesToUpdate.size(); i++) {
            EC2Instance instance = instancesToUpdate.get(i);
            Ec2DTO instanceDTO = instancesToUpdateDTO.get(i);
            instance.setInstanceType(instanceDTO.getInstanceType());
            instance.setState(instanceDTO.getState());
            instance.setRegion(instanceDTO.getRegion());
            instance.setOwnerEmail(instanceDTO.getTags().get(OWNER_EMAIL));
            instance.setClientName(instanceDTO.getTags().get(CLIENT_NAME));
            instance.setEnvironmentType(instanceDTO.getTags().get(ENVIRONMENT_TYPE));
            instance.setOperationHours(instanceDTO.getTags().get(OPERATION_HOURS));
            instance.setProductId(instanceDTO.getTags().get(PRODUCT_ID));
            instance.setPublicIp(instanceDTO.getPublicIp());
            instance.setPrivateIp(instanceDTO.getPrivateIp());
            instance.setAssociatedAccount(credentialsService.getCredentialsByAccountId(instanceDTO.getAssociatedAccount()));
        }
        this.ec2InstanceRepository.saveAllAndFlush(instancesToUpdate);
    }

    private void addInstances(List<Ec2DTO> instancesToAdd) {
        for (Ec2DTO instance : instancesToAdd) {
            EC2Instance ec2Instance = EC2Instance.builder()
                    .instanceId(instance.getInstanceId())
                    .instanceType(instance.getInstanceType())
                    .state(instance.getState())
                    .region(instance.getRegion())
                    .ownerEmail(instance.getTags().get(OWNER_EMAIL))
                    .clientName(instance.getTags().get(CLIENT_NAME))
                    .environmentType(instance.getTags().get(ENVIRONMENT_TYPE))
                    .operationHours(instance.getTags().get(OPERATION_HOURS))
                    .productId(instance.getTags().get(PRODUCT_ID))
                    .platform(instance.getPlatform())
                    .publicIp(instance.getPublicIp())
                    .privateIp(instance.getPrivateIp())
                    .associatedAccount(credentialsService.getCredentialsByAccountId(instance.getAssociatedAccount()))
                    .creationTimestamp(LocalDateTime.now())
                    .lastUpdateTimestamp(LocalDateTime.now())
                    .lastDowntimeTimestamp(null)
                    .lastUptimeTimestamp(null)

                    .build();
            this.ec2InstanceRepository.saveAndFlush(ec2Instance);
        }
    }

    private void deleteInstances(List<EC2Instance> instancesToDelete) {
        ec2InstanceRepository.deleteAll(instancesToDelete);
    }

    @Scheduled(fixedDelay = 3600000L)
    @Transactional
    public void storeRdsInstances(){
        log.info("Storing RDS Instances");
        List<RdsDTO> currentInstances = rdsService.getAllRdsInstancesWithAccountId();
        List<RDSInstance> instancesInDB = rdsService.getAllRdsInstances();

        List<RDSInstance> instancesToDelete = new ArrayList<>();
        List<RdsDTO> instancesToAdd = new ArrayList<>();
        List<RDSInstance> instancesToUpdateInDB = new ArrayList<>();

        for (RDSInstance dbInstance : instancesInDB){
            boolean found = false;
            for (RdsDTO currentInstance : currentInstances){
                if (dbInstance.getDbInstanceIdentifier().equals(currentInstance.getDbInstanceIdentifier())){
                    found = true;
                    break;
                }
            }
            if (!found){
                instancesToDelete.add(dbInstance);
            }
        }

        // Identify instances to add and update
        for (RdsDTO currentInstance : currentInstances){
            boolean found = false;
            for (RDSInstance dbInstance : instancesInDB){
                if (dbInstance.getDbInstanceIdentifier().equals(currentInstance.getDbInstanceIdentifier())){
                    found = true;
                    instancesToUpdateInDB.add(dbInstance);
                    break;
                }
            }
            if (!found){
                instancesToAdd.add(currentInstance);
            }
        }

        this.deleteRdsInstances(instancesToDelete);
        this.addRdsInstances(instancesToAdd);
        this.updateRdsInstances(instancesToUpdateInDB, currentInstances);

        this.storeRdsBackup(rdsInstanceRepository.findAll());

        log.info("RDS Instances stored");
    }

    private void updateRdsInstances(List<RDSInstance> instancesToUpdateInDB, List<RdsDTO> currentInstances) {
        for (int i = 0; i < instancesToUpdateInDB.size(); i++){
            RDSInstance instance = instancesToUpdateInDB.get(i);
            RdsDTO instanceDTO = currentInstances.get(i);
            instance.setDbInstanceClass(instanceDTO.getDbInstanceClass());
            instance.setEngine(instanceDTO.getEngine());
            instance.setEngineVersion(instanceDTO.getEngineVersion());
            instance.setStatus(instanceDTO.getStatus());
            instance.setAllocatedStorage(instanceDTO.getAllocatedStorage());
            instance.setOwnerEmail(instanceDTO.getTags().get(OWNER_EMAIL));
            instance.setClientName(instanceDTO.getTags().get(CLIENT_NAME));
            instance.setProductId(instanceDTO.getTags().get(PRODUCT_ID));
            instance.setOperationHours(instanceDTO.getTags().get(OPERATION_HOURS));
            instance.setEndpointPort(instanceDTO.getEndpointPort());
            instance.setEndpointAddress(instanceDTO.getEndpointAddress());
            instance.setCreationDate(LocalDateTime.ofInstant(instanceDTO.getCreationDate().toInstant(), ZoneId.systemDefault()));
            instance.setRegion(instanceDTO.getRegion());
        }
        this.rdsInstanceRepository.saveAllAndFlush(instancesToUpdateInDB);
    }

    private void addRdsInstances(List<RdsDTO> instancesToAdd) {
        for (RdsDTO instance : instancesToAdd){
            RDSInstance rdsInstance = RDSInstance.builder()
                    .dbInstanceIdentifier(instance.getDbInstanceIdentifier())
                    .dbInstanceClass(instance.getDbInstanceClass())
                    .engine(instance.getEngine())
                    .engineVersion(instance.getEngineVersion())
                    .status(instance.getStatus())
                    .allocatedStorage(instance.getAllocatedStorage())
                    .ownerEmail(instance.getTags().get(OWNER_EMAIL))
                    .clientName(instance.getTags().get(CLIENT_NAME))
                    .productId(instance.getTags().get(PRODUCT_ID))
                    .operationHours(instance.getTags().get(OPERATION_HOURS))
                    .endpointPort(instance.getEndpointPort())
                    .endpointAddress(instance.getEndpointAddress())
                    .creationDate(LocalDateTime.ofInstant(instance.getCreationDate().toInstant(), ZoneId.systemDefault()))
                    .region(instance.getRegion())
                    .associatedAccount(credentialsService.getCredentialsByAccountId(instance.getAssociatedAccount()))
                    .creationTimestamp(LocalDateTime.now())
                    .lastUpdateTimestamp(LocalDateTime.now())
                    .build();
            this.rdsInstanceRepository.saveAndFlush(rdsInstance);
        }
    }

    private void deleteRdsInstances(List<RDSInstance> instancesToDelete) {
        rdsInstanceRepository.deleteAll(instancesToDelete);
    }

    @Scheduled(fixedDelay = 3600000L)
    @Transactional
    public void storeEipAddresses(){
        log.info("Storing EIP Addresses");
        List<EipDTO> currentAddresses = computeServices.getAllElasticIPsWithAccountId();
        List<EIPAddress> addressesInDb = computeServices.getAllAddresses();

        List<EIPAddress> addressesToDelete = new ArrayList<>();
        List<EipDTO> addressesToAdd = new ArrayList<>();
        List<EIPAddress> addressesToUpdateInDb = new ArrayList<>();

        for (EIPAddress dbAddress : addressesInDb){
            boolean found = false;
            for (EipDTO currentAddress : currentAddresses){
                if (dbAddress.getAllocationId().equals(currentAddress.getAllocationId())){
                    found = true;
                    break;
                }
            }
            if (!found){
                addressesToDelete.add(dbAddress);
            }
        }

        // Identify addresses to add and update
        for (EipDTO currentAddress : currentAddresses){
            boolean found = false;
            for (EIPAddress dbAddress : addressesInDb){
                if (dbAddress.getAllocationId().equals(currentAddress.getAllocationId())){
                    found = true;
                    addressesToUpdateInDb.add(dbAddress);
                    break;
                }
            }
            if (!found){
                addressesToAdd.add(currentAddress);
            }
        }

        this.deleteEipAddresses(addressesToDelete);
        this.addEipAddresses(addressesToAdd);
        this.updateEipAddresses(addressesToUpdateInDb, currentAddresses);

        this.storeEipBackup(eipAddressRepository.findAll());

        log.info("EIP Addresses stored");
    }

    private void updateEipAddresses(List<EIPAddress> addressesToUpdateInDb, List<EipDTO> currentAddresses) {
        for (int i = 0; i < addressesToUpdateInDb.size(); i++){
            EIPAddress address = addressesToUpdateInDb.get(i);
            EipDTO addressDTO = currentAddresses.get(i);
            address.setPrivateIp(addressDTO.getPrivateIp());
            address.setPublicIp(addressDTO.getPublicIp());
            address.setAssociationId(addressDTO.getAssociationId());
            address.setAssociatedAccount(credentialsService.getCredentialsByAccountId(addressDTO.getAssociatedAccount()));
        }
        this.eipAddressRepository.saveAllAndFlush(addressesToUpdateInDb);
    }

    private void addEipAddresses(List<EipDTO> addressesToAdd) {
        for (EipDTO address : addressesToAdd){
            EIPAddress eipAddress = EIPAddress.builder()
                    .allocationId(address.getAllocationId())
                    .privateIp(address.getPrivateIp())
                    .publicIp(address.getPublicIp())
                    .associationId(address.getAssociationId())
                    .associatedAccount(credentialsService.getCredentialsByAccountId(address.getAssociatedAccount()))
                    .build();
            this.eipAddressRepository.saveAndFlush(eipAddress);
        }
    }

    private void deleteEipAddresses(List<EIPAddress> addressesToDelete) {
        eipAddressRepository.deleteAll(addressesToDelete);
    }

    @Scheduled(fixedDelay = 3600000L)
    @Transactional
    public void storeEbsVolumes(){
        log.info("Storing EBS Volumes");
        List<EbsDTO> currentVolumes = computeServices.getAllEbsVolumesWithAccountId();
        List<EBSVolume> volumesInDb = computeServices.getAllVolumes();

        List<EBSVolume> volumesToDelete = new ArrayList<>();
        List<EbsDTO> volumesToAdd = new ArrayList<>();
        List<EBSVolume> volumesToUpdateInDb = new ArrayList<>();

        for (EBSVolume dbVolume : volumesInDb){
            boolean found = false;
            for (EbsDTO currentVolume : currentVolumes){
                if (dbVolume.getVolumeId().equals(currentVolume.getVolumeId())){
                    found = true;
                    break;
                }
            }
            if (!found){
                volumesToDelete.add(dbVolume);
            }
        }

        // Identify volumes to add and update
        for (EbsDTO currentVolume : currentVolumes){
            boolean found = false;
            for (EBSVolume dbVolume : volumesInDb){
                if (dbVolume.getVolumeId().equals(currentVolume.getVolumeId())){
                    found = true;
                    volumesToUpdateInDb.add(dbVolume);
                    break;
                }
            }
            if (!found){
                volumesToAdd.add(currentVolume);
            }
        }

        this.deleteEbsVolumes(volumesToDelete);
        this.addEbsVolumes(volumesToAdd);
        this.updateEbsVolumes(volumesToUpdateInDb, currentVolumes);

        this.storeEbsBackup(ebsVolumeRepository.findAll());

        log.info("EBS Volumes stored");

    }

    private void updateEbsVolumes(List<EBSVolume> volumesToUpdateInDb, List<EbsDTO> currentVolumes) {
        for (int i = 0; i < volumesToUpdateInDb.size(); i++){
            EBSVolume volume = volumesToUpdateInDb.get(i);
            EbsDTO volumeDTO = currentVolumes.get(i);
            volume.setVolumeType(volumeDTO.getVolumeType());
            volume.setSize(volumeDTO.getSize());
            volume.setState(volumeDTO.getState());
            volume.setCreationTime(volumeDTO.getCreationTime());
            volume.setOwnerEmail(volumeDTO.getTags().get(OWNER_EMAIL));
            volume.setProductId(volumeDTO.getTags().get(PRODUCT_ID));
            volume.setOperationHours(volumeDTO.getTags().get(OPERATION_HOURS));
            volume.setAssociatedAccount(credentialsService.getCredentialsByAccountId(volumeDTO.getAssociatedAccount()));
        }
        this.ebsVolumeRepository.saveAllAndFlush(volumesToUpdateInDb);
    }

    private void addEbsVolumes(List<EbsDTO> volumesToAdd) {
        for (EbsDTO volume : volumesToAdd){
            EBSVolume ebsVolume = EBSVolume.builder()
                    .instanceId(volume.getInstanceId())
                    .volumeId(volume.getVolumeId())
                    .volumeType(volume.getVolumeType())
                    .size(volume.getSize())
                    .region(volume.getRegion())
                    .state(volume.getState())
                    .creationTime(volume.getCreationTime())
                    .ownerEmail(volume.getTags().get(OWNER_EMAIL))
                    .productId(volume.getTags().get(PRODUCT_ID))
                    .operationHours(volume.getTags().get(OPERATION_HOURS))
                    .associatedAccount(credentialsService.getCredentialsByAccountId(volume.getAssociatedAccount()))
                    .lastUpdateTimestamp(LocalDateTime.now())
                    .creationTimestamp(LocalDateTime.now())
                    .build();
            this.ebsVolumeRepository.saveAndFlush(ebsVolume);
        }
    }

    private void deleteEbsVolumes(List<EBSVolume> volumesToDelete) {
        ebsVolumeRepository.deleteAll(volumesToDelete);
    }

    @Scheduled(fixedDelay = 3600000L)
    public void storeS3Buckets(){

        log.info("Storing S3 Buckets ......");
        List<S3BucketDTO> s3BucketDTOs = s3Service.s3testAllRegionsStream();

        for (S3BucketDTO s3BucketDTO : s3BucketDTOs) {
            String bucketName = s3BucketDTO.getName();

            if (!storageBucketRepository.existsByName(bucketName)) {
                StorageBucket storageBucket = StorageBucket.builder()
                        .associatedAccount(credentialsService.getCredentialsByAccountId(s3BucketDTO.getAssociatedAccount()))
                        .name(bucketName)
                        .creationDate(s3BucketDTO.getCreationDate())
                        .purpose(s3BucketDTO.getTags().get("Purpose"))
                        .ownerEmail(s3BucketDTO.getTags().get(OWNER_EMAIL))
                        .objectCount(s3BucketDTO.getObjectCount())
                        .size(s3BucketDTO.getSize())
                        .owner(s3BucketDTO.getOwnerName())
                        .region(s3BucketDTO.getRegion())
                        .creationTimestamp(LocalDateTime.now())
                        .lastUpdateTimestamp(LocalDateTime.now())
                        .build();
                storageBucketRepository.save(storageBucket);
            }
        }

        log.info("S3 Buckets stored successfully");
    }

    private void storeEc2Backup(List<EC2Instance> instanceList) {
        for (EC2Instance instance : instanceList) {
            EC2InstanceBackup backup = EC2InstanceBackup.builder()
                    .associatedAccount(instance.getAssociatedAccount().getAccountId())
                    .instanceId(instance.getInstanceId())
                    .instanceType(instance.getInstanceType())
                    .state(instance.getState())
                    .region(instance.getRegion())
                    .ownerEmail(instance.getOwnerEmail())
                    .clientName(instance.getClientName())
                    .productId(instance.getProductId())
                    .operationHours(instance.getOperationHours())
                    .publicIp(instance.getPublicIp())
                    .privateIp(instance.getPrivateIp())
                    .creationTimestamp(LocalDateTime.now())
                    .lastUptimeTimestamp(instance.getLastUptimeTimestamp())
                    .lastDowntimeTimestamp(instance.getLastDowntimeTimestamp())
                    .lastUpdateTimestamp(instance.getLastUpdateTimestamp())
                    .build();
            this.ec2InstanceBackupRepository.save(backup);
        }
    }

    private void storeEbsBackup(List<EBSVolume> volumeList){
        for (EBSVolume volume : volumeList){
            EBSVolumeBackup backup = EBSVolumeBackup.builder()
                    .instanceId(volume.getInstanceId())
                    .associatedAccount(volume.getAssociatedAccount().getAccountId())
                    .volumeId(volume.getVolumeId())
                    .volumeType(volume.getVolumeType())
                    .size(volume.getSize())
                    .region(volume.getRegion())
                    .state(volume.getState())
                    .creationTime(volume.getCreationTime())
                    .ownerEmail(volume.getOwnerEmail())
                    .productId(volume.getProductId())
                    .creationTimestamp(LocalDateTime.now())
                    .operationHours(volume.getOperationHours())
                    .environmentType(volume.getEnvironmentType())
                    .lastUpdateTimestamp(volume.getLastUpdateTimestamp())
                    .build();
            this.ebsVolumeBackupRepository.save(backup);
        }
    }

    private void storeEipBackup(List<EIPAddress> addressList){
        for (EIPAddress address : addressList) {
            EipAddressBackup backup = EipAddressBackup.builder()
                    .allocationId(address.getAllocationId())
                    .associatedAccount(address.getAssociatedAccount().getAccountId())
                    .privateIp(address.getPrivateIp())
                    .publicIp(address.getPublicIp())
                    .associationId(address.getAssociationId())
                    .build();
            this.eipAddressBackupRepository.save(backup);
        }
    }

    private void storeRdsBackup(List<RDSInstance> rdsInstanceList){
        for (RDSInstance rdsInstance : rdsInstanceList){
            RDSInstanceBackup backup = RDSInstanceBackup.builder()
                    .dbInstanceIdentifier(rdsInstance.getDbInstanceIdentifier())
                    .associatedAccount(rdsInstance.getAssociatedAccount().getAccountId())
                    .dbInstanceClass(rdsInstance.getDbInstanceClass())
                    .engine(rdsInstance.getEngine())
                    .engineVersion(rdsInstance.getEngineVersion())
                    .status(rdsInstance.getStatus())
                    .allocatedStorage(rdsInstance.getAllocatedStorage())
                    .ownerEmail(rdsInstance.getOwnerEmail())
                    .clientName(rdsInstance.getClientName())
                    .productId(rdsInstance.getProductId())
                    .operationHours(rdsInstance.getOperationHours())
                    .endpointPort(rdsInstance.getEndpointPort())
                    .endpointAddress(rdsInstance.getEndpointAddress())
                    .creationDate(rdsInstance.getCreationDate())
                    .creationTimestamp(LocalDateTime.now())
                    .lastDowntimeTimestamp(rdsInstance.getLastDowntimeTimestamp())
                    .lastUptimeTimestamp(rdsInstance.getLastUptimeTimestamp())
                    .region(rdsInstance.getRegion())
                    .build();
            this.rdsInstanceBackupRepository.save(backup);
        }
    }
}
