package com.example.enterprise_internet_applications_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class EnterpriseInternetApplicationsProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnterpriseInternetApplicationsProjectApplication.class, args);
    }

}
