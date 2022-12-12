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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/file")
@PreAuthorize("hasRole('ROLE_USER')")
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

    @GetMapping("/getStatusFile")
    public String statusFile(@RequestParam("nameFile") String nameFile){
        try{
            return nameFile + " : " + isCheckIn(nameFile);
        }catch (Exception e){
            throw new IllegalStateException("file not found");
        }
    }

    @GetMapping("/isCheckInFile")
    public boolean isCheckIn(@RequestParam("nameFile") String nameFile) {
        try {
            return storageService.statusFile(nameFile);
        } catch (Exception e) {
            throw new IllegalStateException("not find this file : " + nameFile);
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
            //todo should add condition if this file for person
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
    public void pindingFile(@RequestParam("nameFile") String nameFile) {
        storageService.pindingFile(nameFile);
    }

    @GetMapping("/unpindingFile")
    public void unpindingFile(@RequestParam("nameFile") String nameFile) {
        storageService.unpindingFile(nameFile);
    }

    @GetMapping("/isPinding")
    public boolean isPinding(@RequestParam("nameFile") String nameFile) {
        return storageService.isPinding(nameFile);
    }

    @GetMapping("/bulk-check-in")
    public void bulkCheckIn(@RequestBody Map<String, List<String>> nameFiles) throws Exception {
        boolean allFilesIsCheckout = true;
        for (String nameFile : nameFiles.get("nameFiles")
        ) {
            if (isCheckIn(nameFile) || isPinding(nameFile)) {
                allFilesIsCheckout = false;
                break;
            }
            pindingFile(nameFile);
        }
        if (allFilesIsCheckout) {
            for (String nameFile : nameFiles.get("nameFiles")
            ) {
                bulkCheckInFile(nameFile);
                unpindingFile(nameFile);
            }
        } else {
            for (String nameFile : nameFiles.get("nameFiles")
            ) {
                unpindingFile(nameFile);
            }
        }

//        for (String fileName : nameFiles.get("nameFiles")){
//            if(isCheckIn(fileName) || isPinding(fileName)){
//                for (String nameFile : nameFiles.get("nameFiles")) {
//                    unpindingFile(nameFile);
//                }
//                return;
//            } else
//                pindingFile(fileName);
//        }
//        for (String nameFile : nameFiles.get("nameFiles")) {
//            bulkCheckInFile(nameFile);
//        }

    }

    private void bulkCheckInFile(String nameFile) throws Exception {
        Long id;
        try {
            id = getIdFile(nameFile);
            if (isCheckIn(nameFile)) {
                throw new IllegalStateException("You Can't make check in for this file beacuase another user make check in before");
            }
            changeStatusFile(nameFile, true);
            downloadFile(id);
        } catch (Exception e) {
            throw new Exception("not find file : " + nameFile);
        }
    }

    @GetMapping("/check-in")
    public ResponseEntity<?> checkInFile(@RequestParam("nameFile") String nameFile) throws Exception {
        Long id;
        try {
            id = getIdFile(nameFile);
            if (isCheckIn(nameFile) || isPinding(nameFile)) {
                throw new IllegalStateException("You Can't make check in for this file beacuase another user make check in before");
            }
            changeStatusFile(nameFile, true);
            return downloadFile(id);
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
        Long ownerId = ownerIdFile(file.getOriginalFilename());
        if (findByName(file.getOriginalFilename()) == null) {
            uploadFile(file, ownerId);
        }
        try {
            changeStatusFile(file.getOriginalFilename(), false);
        } catch (Exception e) {
            throw new IllegalStateException("\"can't change status file to check out\"");
        }
    }

    @GetMapping("/readFile")
    public void readFile(String nameFile){
        try{
            Long idFile = getIdFile(nameFile);
            downloadFile(idFile);
        }catch (Exception e){
            throw new IllegalStateException("can't get id file");
        }
    }

    @GetMapping("/files/in/group/{group_id}")
    public List<String> getFilesInGroup(@PathVariable("group_id") Long groupId){
        return fileGroupService.getFilesInGroup(groupId);
    }
}
