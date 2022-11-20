package com.example.enterprise_internet_applications_project.services;

import java.io.IOException;

import com.example.enterprise_internet_applications_project.models.MyFile;
import com.example.enterprise_internet_applications_project.repositories.FilesRepository;
import com.example.enterprise_internet_applications_project.utils.upload.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FilesService {

    @Autowired
    private FilesRepository filesRepository;

    public void upload(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        MyFile fileDB = new MyFile(fileName);
        FileUploadUtil.saveFile(fileName, file);
        filesRepository.save(fileDB);
    }

    public MyFile getFile(int id) {
        return filesRepository.findById(id).get();
    }
}
