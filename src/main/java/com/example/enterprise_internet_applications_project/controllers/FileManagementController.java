package com.example.enterprise_internet_applications_project.controllers;

import com.example.enterprise_internet_applications_project.models.MyFile;
import com.example.enterprise_internet_applications_project.services.FileGroupService;
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
import java.util.Map;


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

    @PostMapping("/uploadFile/owner/{id}")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable("id") long ownerId) {
        String message;
        try {
            storageService.upload(file, ownerId);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }


    @GetMapping("/downloadFile/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable("id") Long id) {
        MyFile fileDB = storageService.getFile(id);
        FileDownloadUtil downloadUtil = new FileDownloadUtil();

        Resource resource = null;

        try {
            resource = downloadUtil.getFileAsResource(fileDB.getName());
            System.out.println(resource);
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

    @PostMapping("/group")
    public void addFileToGroup(@RequestParam("file_id") Long fileId, @RequestParam("group_id") Long groupId) {
        fileGroupService.addFileToGroup(fileId, groupId);
    }

    @DeleteMapping("/group")
    public void removeFileFromGroup(@RequestParam("file_id") Long fileId, @RequestParam("group_id") Long groupId) {
        fileGroupService.removeFileFromGroup(fileId, groupId);
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
    public boolean isCheckIn(@RequestParam("nameFile") String nameFile) throws Exception {
        try {
            return storageService.statusFile(nameFile);
        } catch (Exception e) {
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
    public Long getIdFile(@RequestParam("nameFile") String nameFile) throws Exception {
        try {
            return storageService.getIdFile(nameFile);
        } catch (Exception e) {
            throw new Exception("not find this file : " + nameFile);
        }
    }

    @GetMapping("/pindingFile")
    public void pindingFile(@RequestParam("nameFile") String nameFile, @RequestParam("personId") Long personId) {
        storageService.pindingFile(nameFile);
    }

    @GetMapping("/unpindingFile")
    public void unpindingFile(@RequestParam("nameFile") String nameFile, @RequestParam("personId") Long personId) {
        storageService.unpindingFile(nameFile);
    }

    @PostMapping("/isPinding")
    public boolean isPinding(@RequestParam("nameFile") String nameFile) {
        return storageService.isPinding(nameFile);
    }

    @PostMapping("/bulk-check-in")
    public void bulkCheckIn(@RequestParam("nameFiles") Map<String, List<String>> nameFiles, @RequestParam("personId") Long personId) throws Exception {
        boolean allFilesIsCheckout = true;
        for (String nameFile : nameFiles.get("nameFiles")
        ) {
            if (isCheckIn(nameFile) || isPinding(nameFile)) {
                allFilesIsCheckout = false;
                break;
            }
            pindingFile(nameFile, personId);
        }
        if (allFilesIsCheckout) {
            for (String nameFile : nameFiles.get("nameFiles")
            ) {
                checkInFile(nameFile);
            }
        } else {
            for (String nameFile : nameFiles.get("nameFiles")
            ) {
                unpindingFile(nameFile, personId);
            }
        }
    }

    @GetMapping("/check-in")
    public void checkInFile(@RequestParam("nameFile") String nameFile) throws Exception {
        Long id;
        try {
            id = getIdFile(nameFile);
            if (isCheckIn(nameFile) || isPinding(nameFile)) {
                throw new IllegalStateException("You Can't make check in for this file beacuase another user make check in before");
            }
            changeStatusFile(nameFile, true);
            downloadFile(id);
        } catch (Exception e) {
            throw new Exception("not find file : " + nameFile);
        }
    }

    @PostMapping("/getOwnerIdForFile")
    public Long ownerIdFile(String nameFile) {
        return storageService.ownerIdFile(nameFile);
    }

    @PutMapping("/check-out")
    public void checkOutFile(@RequestParam("file") MultipartFile file) {
        Long ownerId = ownerIdFile(file.getName());
        if (findByName(file.getOriginalFilename()) == null) {
            uploadFile(file, ownerId);
        }
        try {
            changeStatusFile(file.getName(), false);
        } catch (Exception e) {
            throw new IllegalStateException("\"can't change status file to check out\"");
        }
    }
}
