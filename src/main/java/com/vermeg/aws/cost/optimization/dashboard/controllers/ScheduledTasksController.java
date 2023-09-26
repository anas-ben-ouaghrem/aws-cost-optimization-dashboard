package com.vermeg.aws.cost.optimization.dashboard.controllers;

import com.vermeg.aws.cost.optimization.dashboard.services.DataStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/scheduled-tasks")
@RequiredArgsConstructor
public class ScheduledTasksController {

    private final DataStorageService dataStorageService;


}
