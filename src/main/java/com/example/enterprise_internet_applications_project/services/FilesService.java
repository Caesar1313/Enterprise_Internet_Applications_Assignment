package com.example.enterprise_internet_applications_project.services;

import java.io.IOException;
import java.util.List;

import com.example.enterprise_internet_applications_project.models.MyFile;
import com.example.enterprise_internet_applications_project.models.Person;
import com.example.enterprise_internet_applications_project.repositories.FilesRepository;
import com.example.enterprise_internet_applications_project.repositories.PersonGroupRepository;
import com.example.enterprise_internet_applications_project.repositories.PersonRepository;
import com.example.enterprise_internet_applications_project.utils.upload.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FilesService {

    @Autowired
    private FilesRepository filesRepository;

    @Autowired
    private PersonRepository personRepository;



    public void upload(MultipartFile file, long ownerId) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        MyFile fileDB = new MyFile(fileName,false,false);
        FileUploadUtil.saveFile(fileName, file);
        fileDB.setOwner(personRepository.findById(ownerId).get());
        if (findByName(file.getOriginalFilename()) == null) {
            filesRepository.save(fileDB);
        }
    }

    public void pindingFile(String nameFile){
        filesRepository.pindingFile(nameFile);
    }

    public void unpindingFile(String nameFile){
        filesRepository.unpindingFile(nameFile);
    }

    public MyFile getFile(long id) {
        return filesRepository.findById(id).get();
    }

    public void changeStatusFile(boolean status, String nameFile,Long userId) {
        filesRepository.changeStatusFile(status,userId, nameFile);
    }

    public MyFile getFile(Long id) {
        return filesRepository.findById(id).get();
    }

    public boolean statusFile(String nameFile) {
        return filesRepository.statusFile(nameFile);
    }
    public boolean isPinding( String nameFile) {
        return filesRepository.isPinding(nameFile);
    }

    public List<String> getFiles() {
        return filesRepository.getAllFiles();
    }

    public ResponseEntity<?> deleteFileByName(String nameFile) {
        boolean statusFile = filesRepository.statusFile(nameFile);
        if (!statusFile) {
            Long id = filesRepository.getIdFile(nameFile);
            filesRepository.deleteById(id);
            return ResponseEntity.ok(HttpStatus.OK);
        }
        throw new IllegalStateException("Files is Check in by anothor user");
    }

    public MyFile findByName(String nameFile) {
        return filesRepository.findByName(nameFile);
    }

    public Long getIdFile(String nameFile) {
        return filesRepository.getIdFile(nameFile);
    }

    public Long ownerIdFile(String nameFile){return filesRepository.ownerIdFile(nameFile);}

    public Long findGroupIdByFileId(Long fileId){
        return filesRepository.findGroupIdByFileId(fileId);
    }

    public MyFile findFileById(Long fileId){
        return filesRepository.findById(fileId).get();
    }
}
