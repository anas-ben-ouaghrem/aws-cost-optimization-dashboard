package com.vermeg.aws.cost.optimization.dashboard.services;

import com.amazonaws.services.costexplorer.AWSCostExplorer;
import com.amazonaws.services.costexplorer.AWSCostExplorerClientBuilder;
import com.amazonaws.services.costexplorer.model.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CostExplorerService {

    private final AWSCostExplorer costExplorerClient;
    private static final String LINKED_ACCOUNT = "LINKED_ACCOUNT";

    public CostExplorerService() {
        costExplorerClient = AWSCostExplorerClientBuilder.defaultClient();
    }

    public GetCostAndUsageResult getCostAndUsage(String accountId, String startDate, String endDate) {
        GetCostAndUsageRequest request = new GetCostAndUsageRequest()
                .withTimePeriod(new DateInterval().withStart(startDate).withEnd(endDate))
                .withGranularity(Granularity.MONTHLY)
                .withMetrics("BlendedCost")
                .withFilter(new Expression().withDimensions(new DimensionValues().withKey(LINKED_ACCOUNT).withValues(accountId)));

        return costExplorerClient.getCostAndUsage(request);
    }

    public GetCostForecastResult getCostForecast(String accountId, String startDate, String endDate) {
        GetCostForecastRequest request = new GetCostForecastRequest()
                .withTimePeriod(new DateInterval().withStart(startDate).withEnd(endDate))
                .withMetric(Metric.BLENDED_COST)
                .withGranularity(Granularity.DAILY)
                .withFilter(new Expression().withDimensions(new DimensionValues().withKey(LINKED_ACCOUNT).withValues(accountId)));

        return costExplorerClient.getCostForecast(request);
    }

    public List<DimensionValuesWithAttributes> getDimensionValues(String dimension, String startDate, String endDate) {
        GetDimensionValuesRequest request = new GetDimensionValuesRequest()
                .withTimePeriod(new DateInterval().withStart(startDate).withEnd(endDate))
                .withDimension(dimension);

        return costExplorerClient.getDimensionValues(request).getDimensionValues();
    }

    public List<UtilizationByTime> getReservationUtilization(String accountId, String startDate, String endDate) {
        GetReservationUtilizationRequest request = new GetReservationUtilizationRequest()
                .withTimePeriod(new DateInterval().withStart(startDate).withEnd(endDate))
                .withFilter(new Expression().withDimensions(new DimensionValues().withKey(LINKED_ACCOUNT).withValues(accountId)));

        return costExplorerClient.getReservationUtilization(request).getUtilizationsByTime();
    }

    public List<CoverageByTime> getReservationCoverage(String accountId, String startDate, String endDate) {
        GetReservationCoverageRequest request = new GetReservationCoverageRequest()
                .withTimePeriod(new DateInterval().withStart(startDate).withEnd(endDate))
                .withFilter(new Expression().withDimensions(new DimensionValues().withKey(LINKED_ACCOUNT).withValues(accountId)));
        return costExplorerClient.getReservationCoverage(request).getCoveragesByTime();
    }
}
