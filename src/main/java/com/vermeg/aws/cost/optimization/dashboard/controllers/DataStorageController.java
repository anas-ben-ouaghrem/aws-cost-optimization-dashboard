package com.vermeg.aws.cost.optimization.dashboard.controllers;

import com.vermeg.aws.cost.optimization.dashboard.services.DataStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
@RequiredArgsConstructor
@CrossOrigin("*")
public class DataStorageController {

    private final DataStorageService dataStorageService;
}
