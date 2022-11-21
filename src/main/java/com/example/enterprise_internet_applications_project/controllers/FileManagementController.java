package com.example.enterprise_internet_applications_project.controllers;

import com.example.enterprise_internet_applications_project.models.MyFile;
import com.example.enterprise_internet_applications_project.services.FileGroupService;
import com.example.enterprise_internet_applications_project.utils.download.FileDownloadUtil;
import com.example.enterprise_internet_applications_project.services.FilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("/api/file")
public class FileManagementController {


    private final FilesService storageService;
    private final FileGroupService fileGroupService;

    @Autowired
    public FileManagementController(FilesService storageService, FileGroupService fileGroupService) {
        this.storageService = storageService;
        this.fileGroupService = fileGroupService;
    }

    @PostMapping("/uploadFile")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        //System.out.println(file.getName());
        String message;
        try {
            storageService.upload(file);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }


    @GetMapping("/downloadFile/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable("id") Long id){
        MyFile fileDB = storageService.getFile(id);
        FileDownloadUtil downloadUtil = new FileDownloadUtil();

        Resource resource = null;

        try {
            resource = downloadUtil.getFileAsResource(fileDB.getName());
        } catch (IOException exception){
            return ResponseEntity.internalServerError().build();
        }

        String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);
    }


    @PostMapping("/group")
    public void addFileToGroup(@RequestParam("file_id") Long fileId,@RequestParam("group_id") Long groupId){
        fileGroupService.addFileToGroup(fileId, groupId);
    }

    @DeleteMapping("/group")
    public void removeFileFromGroup(@RequestParam("file_id") Long fileId,@RequestParam("group_id") Long groupId){
        fileGroupService.removeFileFromGroup(fileId, groupId);
    }
}
