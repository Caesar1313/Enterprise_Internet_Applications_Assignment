package com.example.enterprise_internet_applications_project.controllers;

import com.example.enterprise_internet_applications_project.models.MyFile;
import com.example.enterprise_internet_applications_project.repos.upload.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping
public class PostController {

    @PostMapping("/uploadFile")
    public ResponseEntity<MyFile> uploadFile(@RequestParam("file")MultipartFile multipartFile) throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        long size = multipartFile.getSize();

        FileUploadUtil.saveFile(fileName, multipartFile);

        MyFile file = new MyFile();
        file.setFileName(fileName);
        file.setSize(size);
        //file.setDownloadUri("/downloadFile");

        return new ResponseEntity<>(file, HttpStatus.OK);
    }
}
