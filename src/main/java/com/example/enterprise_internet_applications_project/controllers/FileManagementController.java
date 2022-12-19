package com.example.enterprise_internet_applications_project.controllers;

import com.example.enterprise_internet_applications_project.models.FileGroup;
import com.example.enterprise_internet_applications_project.models.Group;
import com.example.enterprise_internet_applications_project.models.MyFile;
import com.example.enterprise_internet_applications_project.models.Person;
import com.example.enterprise_internet_applications_project.services.FileGroupService;
import com.example.enterprise_internet_applications_project.services.FilesService;
import com.example.enterprise_internet_applications_project.services.PersonResourceService;
import com.example.enterprise_internet_applications_project.services.GroupResourceService;
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

    @Autowired
    private FilesService storageService;

    @Autowired
    private PersonResourceService personResourceService;

    @Autowired
    private FileGroupService fileGroupService;

    @Autowired
    private FilesService filesService;

    @Autowired
    private GroupResourceService groupResourceService;

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
    public void addFileToGroup(@RequestParam("file_id") Long fileId, @RequestParam("group_id") Long groupId, @RequestParam("userId") Long userId) {
        Person p = personResourceService.find(userId).get();
        if (p == null) {
            throw new IllegalStateException("this user is not belong to this group .. ");
        }

        boolean ok = false;
        for (int i = 0; i < p.getPersonGroups().size(); i++) {
            if(p.getPersonGroups().get(i).getGroup().getId().equals(groupId)){
                ok = true;
                break;
            }
        }
        if (!ok) {
            throw new IllegalStateException("this user is not belong to this group .. ");

        }
        FileGroup fg = new FileGroup(filesService.findFileById(fileId), groupResourceService.findGroupById(groupId));
        for (FileGroup fileGroup : fileGroupService.getAllFileGroups()) {
            if (fileGroup.equals(fg)) {
                return;
            }
        }
        fileGroupService.addFileToGroup(fileId, groupId);

    }

    @DeleteMapping("/group")
    public void removeFileFromGroup(@RequestParam("file_id") Long fileId, @RequestParam("group_id") Long groupId, @RequestParam("userId") Long userId) {
        Person p = personResourceService.find(userId).get();
        if (p == null) {
            throw new IllegalStateException("this user is not belong to this group .. ");
        }

        boolean ok = false;
        for (int i = 0; i < p.getPersonGroups().size(); i++) {
            if(p.getPersonGroups().get(i).getGroup().getId().equals(groupId)){
                ok = true;
                break;
            }
        }
        if (!ok) {
            throw new IllegalStateException("this user is not belong to this group .. ");
        }



        fileGroupService.removeFileFromGroup(fileId, groupId);
    }

    @GetMapping("/allFiles")
    public List<String> getFiles() {
        return storageService.getFiles();
    }

    @GetMapping("/findByName")
    public MyFile findByName(@RequestParam("nameFile") String nameFile) {
        return storageService.findByName(nameFile);
    }

    @GetMapping("/getStatusFile")
    public String statusFile(@RequestParam("nameFile") String nameFile) {
        try {
            return nameFile + " : " + isCheckIn(nameFile);
        } catch (Exception e) {
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
    public void changeStatusFile(@RequestParam("nameFile") String nameFile, boolean status, Long userId) throws Exception {
        try {
            storageService.changeStatusFile(status, nameFile, userId);
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
            changeStatusFile(nameFile, true, 0L);
            downloadFile(id);
        } catch (Exception e) {
            throw new Exception("not find file : " + nameFile);
        }
    }

    @GetMapping("/check-in")
    public ResponseEntity<?> checkInFile(@RequestParam("nameFile") String nameFile, @RequestParam("userId") Long userId) throws Exception {
        Long id;
        try {
            id = getIdFile(nameFile);
            if (isCheckIn(nameFile) || isPinding(nameFile)) {
                throw new IllegalStateException("You Can't make check in for this file beacuase another user make check in before");
            }


            Long pGId = personResourceService.findGroupIdByUserId(userId);
            Long fGId = storageService.findGroupIdByFileId(id);

            if (!pGId.equals(fGId)) {
                throw new IllegalStateException("You Can't make check in for this file beacuase another user make check in before");
            }

            changeStatusFile(nameFile, true, userId);
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
    public void checkOutFile(@RequestParam("file") MultipartFile file, @RequestParam("userId") Long userId) {
        Long ownerId = ownerIdFile(file.getOriginalFilename());
        MyFile myFile = findByName(file.getOriginalFilename());
        if (myFile == null) {
            uploadFile(file, ownerId);
        }

        if (!myFile.getCheckInUserId().equals(userId)) {
            throw new IllegalStateException("can not check out using different user id");
        }
        try {
            changeStatusFile(file.getOriginalFilename(), false, 0L);
        } catch (Exception e) {
            throw new IllegalStateException("\"can't change status file to check out\"");
        }
    }

    @GetMapping("/readFile")
    public void readFile(String nameFile) {
        try {
            Long idFile = getIdFile(nameFile);
            downloadFile(idFile);
        } catch (Exception e) {
            throw new IllegalStateException("can't get id file");
        }
    }

    @GetMapping("/files/in/group/{group_id}")
    public List<String> getFilesInGroup(@PathVariable("group_id") Long groupId) {
        return fileGroupService.getFilesInGroup(groupId);
    }
}
