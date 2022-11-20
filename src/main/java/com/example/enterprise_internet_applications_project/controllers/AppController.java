package com.example.enterprise_internet_applications_project.controllers;

import com.example.enterprise_internet_applications_project.models.MyFile;
import com.example.enterprise_internet_applications_project.services.FilesService;
import com.example.enterprise_internet_applications_project.utils.download.FileDownloadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping
public class AppController {

    @Autowired
    private FilesService storageService;

    @PostMapping("/uploadFile")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("isCheckin") boolean isCheckin) {
        String message;
        try {
            storageService.upload(file, isCheckin);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }

    @GetMapping("/getAllFiles")
    public List<MyFile> getFiles() {
        return storageService.getFiles();
    }

    @GetMapping("/downloadFile/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable("id") int id) {
        MyFile fileDB = storageService.getFile(id);
        FileDownloadUtil downloadUtil = new FileDownloadUtil();

        Resource resource = null;

        try {
            resource = downloadUtil.getFileAsResource(fileDB.getName());
        } catch (IOException exception) {
            return ResponseEntity.internalServerError().build();
        }

        String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);
    }

    @GetMapping("/findByName")
    public MyFile findByName(@RequestParam("nameFile") String nameFile) {
        return storageService.findByName(nameFile);
    }

    @DeleteMapping("/deleteFile")
    public ResponseEntity<?> deleteFileByName(@RequestParam("nameFile") String nameFile) {
        return storageService.deleteFileByName(nameFile);
    }
}
