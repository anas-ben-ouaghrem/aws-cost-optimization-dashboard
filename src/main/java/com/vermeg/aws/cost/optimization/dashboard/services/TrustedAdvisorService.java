package com.vermeg.aws.cost.optimization.dashboard.services;

import com.amazonaws.services.support.AWSSupport;
import com.amazonaws.services.support.model.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrustedAdvisorService {

    private final UtilityServices utilityServices;

    public List<TrustedAdvisorCheckSummary> getAllTrustedAdvisorCheckSummaries() {
        List<TrustedAdvisorCheckSummary> summaries = new ArrayList<>();
        List<Pair<AWSSupport,String>> supportClients = utilityServices.getTrustedAdvisorClientsList();
        for (Pair<AWSSupport,String> supportClient : supportClients){
            AWSSupport client = supportClient.getLeft();

            DescribeTrustedAdvisorChecksRequest request = new DescribeTrustedAdvisorChecksRequest().withLanguage("en");
            DescribeTrustedAdvisorChecksResult result = client.describeTrustedAdvisorChecks(request);

            summaries.addAll(result.getChecks()
                    .parallelStream()
                    .map(check -> {
                        DescribeTrustedAdvisorCheckSummariesRequest summariesRequest = new DescribeTrustedAdvisorCheckSummariesRequest().withCheckIds(check.getId());
                        DescribeTrustedAdvisorCheckSummariesResult summariesResult = client.describeTrustedAdvisorCheckSummaries(summariesRequest);
                        return summariesResult.getSummaries().stream().findFirst();
                    })
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .toList());
        }
        return summaries;
    }
}
