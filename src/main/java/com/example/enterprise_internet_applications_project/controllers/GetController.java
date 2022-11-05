package com.example.enterprise_internet_applications_project.controllers;

import com.example.enterprise_internet_applications_project.repositories.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetController {

    @Autowired
    FileRepository fileRepository;

    @GetMapping("/get/statistics")
    public String getFilesStatus(){
        return fileRepository.getFilesStatus();
    }
}
