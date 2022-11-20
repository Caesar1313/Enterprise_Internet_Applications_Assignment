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
import java.util.Optional;

@Service
public class FilesService {

    @Autowired
    private FilesRepository filesRepository;

    public void upload(MultipartFile file, boolean isCheckIn) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        MyFile fileDB = new MyFile(fileName, isCheckIn);
        FileUploadUtil.saveFile(fileName, file);
        filesRepository.save(fileDB);
    }

    public MyFile getFile(int id) {
        return filesRepository.findById(id).get();
    }

    public List<MyFile> getFiles() {
        return filesRepository.findAll();
    }

    public ResponseEntity<?> deleteFileByName(String nameFile) {
        Optional<Boolean> optionalB = filesRepository.checkStatusFile(nameFile);
        Boolean fileIsCheckIn = optionalB.orElse(false);
        if (!fileIsCheckIn) {
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
