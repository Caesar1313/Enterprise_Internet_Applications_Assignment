package com.example.enterprise_internet_applications_project.controllers;

import com.example.enterprise_internet_applications_project.repositories.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class UpdateController {

    @Autowired
    FileRepository fileRepository;

    @PutMapping("/checkin/file/{fileName}")
    public String checkinFile(@PathVariable String fileName){
        return fileRepository.checkinFile(fileName);
    }

    @PutMapping("/checkout/file/{fileName}")
    public String checkoutFile(@PathVariable String fileName){
        return fileRepository.checkoutFile(fileName);
    }
}
