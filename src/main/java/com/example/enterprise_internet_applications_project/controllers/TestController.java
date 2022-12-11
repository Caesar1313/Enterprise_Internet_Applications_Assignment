package com.example.enterprise_internet_applications_project.controllers;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
public class TestController {

    private final Bucket bucket;

    public TestController() {
        Bandwidth limit = Bandwidth.classic(3, Refill.greedy(3, Duration.ofMinutes(1)));
        this.bucket = Bucket4j.builder()
                .addLimit(limit)
                .build();
    }
    @GetMapping("/test")
    private String getTest(){
        if(bucket.tryConsume(1)){
            System.out.println("EYAD");
            return "test";
        }
        else return "Failed to test";


    }
}
