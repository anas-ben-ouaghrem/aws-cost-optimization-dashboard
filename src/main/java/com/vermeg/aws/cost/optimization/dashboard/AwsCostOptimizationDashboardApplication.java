package com.vermeg.aws.cost.optimization.dashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AwsCostOptimizationDashboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(AwsCostOptimizationDashboardApplication.class, args);
	}

}
