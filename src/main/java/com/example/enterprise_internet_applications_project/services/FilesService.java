package com.example.enterprise_internet_applications_project.services;

import com.example.enterprise_internet_applications_project.models.MyFile;
import com.example.enterprise_internet_applications_project.repos.FilesRepository;
import com.example.enterprise_internet_applications_project.utils.upload.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
public class FilesService {

    @Autowired
    private FilesRepository filesRepository;

    // just store file on server without make any action on database
    public void storeFileOnServer(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        FileUploadUtil.saveFile(fileName, file);
    }

    // action on database when added ( create - update )
    public void upload(MultipartFile file, boolean status) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        storeFileOnServer(file);
        if (findByName(file.getName()) == null) {
            MyFile fileDB = new MyFile(fileName, false);
            filesRepository.save(fileDB);
        } else {
            changeStatusFile(status, file.getName());
        }
    }

    void changeStatusFile(boolean status, String nameFile) {
        filesRepository.changeStatusFile(status, nameFile);
    }

    public MyFile getFile(int id) {
        return filesRepository.findById(id).get();
    }

    public boolean statusFile(String nameFile) {
        return filesRepository.statusFile(nameFile);
    }

    public List<MyFile> getFiles() {
        return filesRepository.findAll();
    }

    public ResponseEntity<?> deleteFileByName(String nameFile) {
        boolean statusFile = filesRepository.statusFile(nameFile);

        if (!statusFile) {
            int id = filesRepository.findByName(nameFile).getId();
            System.out.println(id);
            filesRepository.deleteById(id);
            return ResponseEntity.ok(HttpStatus.OK);
        }
        throw new IllegalStateException("Files is Check in by anothor user");
    }

    public MyFile findByName(String nameFile) {
        return filesRepository.findByName(nameFile);
    }

}
