package com.vermeg.aws.cost.optimization.dashboard.services;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.amazonaws.services.support.AWSSupport;

import java.util.ArrayList;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {TrustedAdvisorService.class})
@ExtendWith(SpringExtension.class)
class TrustedAdvisorServiceTest {
    @Autowired
    private TrustedAdvisorService trustedAdvisorService;

    @MockBean
    private UtilityServices utilityServices;

    /**
     * Method under test: {@link TrustedAdvisorService#getAllTrustedAdvisorCheckSummaries()}
     */
    @Test
    void testGetAllTrustedAdvisorCheckSummaries() {
        when(utilityServices.getTrustedAdvisorClientsList()).thenReturn(new ArrayList<>());
        assertTrue(trustedAdvisorService.getAllTrustedAdvisorCheckSummaries().isEmpty());
        verify(utilityServices).getTrustedAdvisorClientsList();
    }

    /**
     * Method under test: {@link TrustedAdvisorService#getAllTrustedAdvisorCheckSummaries()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetAllTrustedAdvisorCheckSummaries2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.amazonaws.services.support.AWSSupport.describeTrustedAdvisorChecks(com.amazonaws.services.support.model.DescribeTrustedAdvisorChecksRequest)" because "client" is null
        //       at com.vermeg.aws.cost.optimization.dashboard.services.TrustedAdvisorService.getAllTrustedAdvisorCheckSummaries(TrustedAdvisorService.java:26)
        //   See https://diff.blue/R013 to resolve this issue.

        ArrayList<Pair<AWSSupport, String>> pairList = new ArrayList<>();
        ImmutablePair<AWSSupport, String> nullPairResult = ImmutablePair.nullPair();
        pairList.add(nullPairResult);
        when(utilityServices.getTrustedAdvisorClientsList()).thenReturn(pairList);
        trustedAdvisorService.getAllTrustedAdvisorCheckSummaries();
    }

    /**
     * Method under test: {@link TrustedAdvisorService#getAllTrustedAdvisorCheckSummaries()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetAllTrustedAdvisorCheckSummaries3() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.amazonaws.services.support.AWSSupport.describeTrustedAdvisorChecks(com.amazonaws.services.support.model.DescribeTrustedAdvisorChecksRequest)" because "client" is null
        //       at com.vermeg.aws.cost.optimization.dashboard.services.TrustedAdvisorService.getAllTrustedAdvisorCheckSummaries(TrustedAdvisorService.java:26)
        //   See https://diff.blue/R013 to resolve this issue.

        ArrayList<Pair<AWSSupport, String>> pairList = new ArrayList<>();
        ImmutablePair<AWSSupport, String> nullPairResult = ImmutablePair.nullPair();
        pairList.add(nullPairResult);
        ImmutablePair<AWSSupport, String> nullPairResult2 = ImmutablePair.nullPair();
        pairList.add(nullPairResult2);
        when(utilityServices.getTrustedAdvisorClientsList()).thenReturn(pairList);
        trustedAdvisorService.getAllTrustedAdvisorCheckSummaries();
    }
}

