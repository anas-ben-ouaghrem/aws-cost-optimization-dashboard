package com.vermeg.aws.cost.optimization.dashboard.services;

import com.amazonaws.services.rds.AmazonRDS;
import com.amazonaws.services.rds.model.DBInstance;
import com.amazonaws.services.rds.model.Tag;
import com.vermeg.aws.cost.optimization.dashboard.dto.RdsDTO;
import com.vermeg.aws.cost.optimization.dashboard.entities.RDSInstance;
import com.vermeg.aws.cost.optimization.dashboard.repositories.RDSInstanceRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RDSService {

    private final UtilityServices utilityServices;
    private final RDSInstanceRepository rdsInstanceRepository;

    public List<RdsDTO> getAllRdsInstancesWithAccountId(){
        List<Pair<AmazonRDS, String>> rdsClientsList = utilityServices.getRDSClientsListWithAccountId();
        List<RdsDTO> rdsDTOList = new ArrayList<>();

        for (Pair<AmazonRDS, String> pair : rdsClientsList) {
            AmazonRDS client = pair.getLeft();
            String accountId = pair.getRight();
            for (DBInstance dbInstance : client.describeDBInstances().getDBInstances()) {
                RdsDTO rdsDTO = new RdsDTO();
                rdsDTO.setAssociatedAccount(accountId);
                rdsDTO.setDbInstanceIdentifier(dbInstance.getDBInstanceIdentifier());
                rdsDTO.setDbInstanceClass(dbInstance.getDBInstanceClass());
                rdsDTO.setStatus(dbInstance.getDBInstanceStatus());
                rdsDTO.setEngine(dbInstance.getEngine());
                rdsDTO.setEngineVersion(dbInstance.getEngineVersion());
                rdsDTO.setAllocatedStorage(dbInstance.getAllocatedStorage());
                rdsDTO.setRegion(dbInstance.getAvailabilityZone());
                rdsDTO.setEndpointAddress(dbInstance.getEndpoint().getAddress());
                rdsDTO.setEndpointPort(dbInstance.getEndpoint().getPort());
                rdsDTO.setCreationDate(dbInstance.getInstanceCreateTime());
                rdsDTO.setTags(dbInstance.getTagList().stream().collect(Collectors.toMap(Tag::getKey, Tag::getValue)));
                rdsDTOList.add(rdsDTO);
            }
        }
        return rdsDTOList;
    }

    public List<RDSInstance> getAllRdsInstances(){
        return rdsInstanceRepository.findAll();
    }
}
