package com.vermeg.aws.cost.optimization.dashboard.controllers;

import com.amazonaws.services.costexplorer.model.*;
import com.vermeg.aws.cost.optimization.dashboard.services.CostExplorerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cost-explorer")
@RequiredArgsConstructor
public class CostExplorerController {

    private final CostExplorerService costExplorerService;

    @GetMapping("get-cost")
    public GetCostAndUsageResult getCostAndUsageResult(String accountId, String startDate, String endDate){
        return costExplorerService.getCostAndUsage(accountId, startDate, endDate);
    }

    @GetMapping("get-cost-forecast")
    public GetCostForecastResult getCostForecast(String accountId, String startDate, String endDate){
        return costExplorerService.getCostForecast(accountId, startDate, endDate);
    }

    @GetMapping("get-dimension-values")
    public List<DimensionValuesWithAttributes> getDimensionValues(String dimension, String startDate, String endDate) {
        return costExplorerService.getDimensionValues(dimension,startDate,endDate);
    }

    @GetMapping("get-utilization-by-time")
    public List<UtilizationByTime> getReservationUtilization(String accountId, String startDate, String endDate) {
        return costExplorerService.getReservationUtilization(accountId, startDate, endDate);
    }

    @GetMapping("get-coverage-by-time")
    public List<CoverageByTime> getReservationCoverage(String accountId, String startDate, String endDate) {
        return costExplorerService.getReservationCoverage(accountId, startDate, endDate);
    }
}
