package com.example.enterprise_internet_applications_project.controllers;

import com.example.enterprise_internet_applications_project.repositories.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping
public class PostController {

    @Autowired
    FileRepository fileRepository;

    @PostMapping("/upload/file/{fileName}")
    public String createFile(@PathVariable String fileName) throws IOException{
        fileRepository.createFile(fileName);
        return "File " + fileName + " created successfully";
    }

}
