package com.example.enterprise_internet_applications_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableEurekaClient
@EnableRetry
@EnableCaching
public class EnterpriseInternetApplicationsProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnterpriseInternetApplicationsProjectApplication.class, args);
    }

}
