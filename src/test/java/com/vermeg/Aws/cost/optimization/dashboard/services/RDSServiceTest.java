package com.vermeg.aws.cost.optimization.dashboard.services;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.amazonaws.services.rds.AmazonRDS;
import com.vermeg.aws.cost.optimization.dashboard.entities.RDSInstance;
import com.vermeg.aws.cost.optimization.dashboard.repositories.RDSInstanceRepository;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {RDSService.class})
@ExtendWith(SpringExtension.class)
class RDSServiceTest {
    @MockBean
    private RDSInstanceRepository rDSInstanceRepository;

    @Autowired
    private RDSService rDSService;

    @MockBean
    private UtilityServices utilityServices;

    /**
     * Method under test: {@link RDSService#getAllRdsInstancesWithAccountId()}
     */
    @Test
    void testGetAllRdsInstancesWithAccountId() {
        when(utilityServices.getRDSClientsListWithAccountId()).thenReturn(new ArrayList<>());
        assertTrue(rDSService.getAllRdsInstancesWithAccountId().isEmpty());
        verify(utilityServices).getRDSClientsListWithAccountId();
    }

    /**
     * Method under test: {@link RDSService#getAllRdsInstancesWithAccountId()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetAllRdsInstancesWithAccountId2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.amazonaws.services.rds.AmazonRDS.describeDBInstances()" because "client" is null
        //       at com.vermeg.aws.cost.optimization.dashboard.services.RDSService.getAllRdsInstancesWithAccountId(RDSService.java:31)
        //   See https://diff.blue/R013 to resolve this issue.

        ArrayList<Pair<AmazonRDS, String>> pairList = new ArrayList<>();
        ImmutablePair<AmazonRDS, String> nullPairResult = ImmutablePair.nullPair();
        pairList.add(nullPairResult);
        when(utilityServices.getRDSClientsListWithAccountId()).thenReturn(pairList);
        rDSService.getAllRdsInstancesWithAccountId();
    }

    /**
     * Method under test: {@link RDSService#getAllRdsInstances()}
     */
    @Test
    void testGetAllRdsInstances() {
        ArrayList<RDSInstance> rdsInstanceList = new ArrayList<>();
        when(rDSInstanceRepository.findAll()).thenReturn(rdsInstanceList);
        List<RDSInstance> actualAllRdsInstances = rDSService.getAllRdsInstances();
        assertSame(rdsInstanceList, actualAllRdsInstances);
        assertTrue(actualAllRdsInstances.isEmpty());
        verify(rDSInstanceRepository).findAll();
    }
}

