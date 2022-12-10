package com.example.enterprise_internet_applications_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class EnterpriseInternetApplicationsProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnterpriseInternetApplicationsProjectApplication.class, args);
    }

}
