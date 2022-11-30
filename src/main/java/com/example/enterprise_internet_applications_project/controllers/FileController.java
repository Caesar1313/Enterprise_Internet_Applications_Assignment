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
public class FileController {

    //*                 Status File             *//
    //          is true (File is check in )
    //          is false (File is check out) (Available)

    @Autowired
    private FilesService storageService;

    @PostMapping("/storeFileOnServer")
    public ResponseEntity<String> storeFileOnServer(@RequestParam("file") MultipartFile file) {
        String message;
        try {
            storageService.storeFileOnServer(file);
            message = "Store the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            message = "Could not Store the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }

    @PostMapping("/uploadFile")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        String message;
        try {
            storageService.upload(file, false);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
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

    @GetMapping("/allFiles")
    public List<MyFile> getFiles() {
        return storageService.getFiles();
    }

    @GetMapping("/findByName")
    public MyFile findByName(@RequestParam("nameFile") String nameFile) {
        return storageService.findByName(nameFile);
    }

    @GetMapping("/statusFile")
    public boolean statusFile(@RequestParam("nameFile") String nameFile) throws Exception{
        try{
            return storageService.statusFile(nameFile);
        }catch (Exception e){
            throw new Exception("not find this file : " + nameFile);
        }
    }

    @GetMapping("/changeStatusFile")
    public void changeStatusFile(@RequestParam("nameFile") String nameFile, boolean status) throws Exception {
        try {
            storageService.changeStatusFile(status, nameFile);
        } catch (Exception e) {
            throw new Exception("not find this file : " + nameFile);
        }
    }

    @DeleteMapping("/deleteFile")
    public ResponseEntity<?> deleteFileByName(@RequestParam("nameFile") String nameFile) throws Exception {
        try {
            return storageService.deleteFileByName(nameFile);
        } catch (Exception e) {
            throw new Exception("not find this file : " + nameFile);
        }
    }

    @PostMapping("/getIdFile")
    public int getIdFile(@RequestParam("nameFile") String nameFile) throws Exception {
        try {
            return storageService.getIdFile(nameFile);
        } catch (Exception e) {
            throw new Exception("not find this file : " + nameFile);
        }
    }

    @PostMapping("/bulk-check-in")
    public void bulkCheckIn(@RequestParam("nameFiles") List<String> nameFiles) throws Exception {
        for (String nameFile : nameFiles
        ) {
            checkInFile(nameFile);
        }
    }

    @GetMapping("/check-in")
    public void checkInFile(@RequestParam("nameFile") String nameFile) throws Exception {
        int id;
        try {
            id = getIdFile(nameFile);
            if (statusFile(nameFile)) {
                throw new IllegalStateException("You Can't make check in for this file beacuase another user make check in before");
            }
            changeStatusFile(nameFile, true);
            downloadFile(id);
        } catch (Exception e) {
            throw new Exception("not find file : " + nameFile);
        }
    }

    @GetMapping("/check-out")
    public void checkOutFile(@RequestParam("file") MultipartFile file) {
        uploadFile(file);
    }

}
