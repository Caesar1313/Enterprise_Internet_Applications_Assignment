package com.example.enterprise_internet_applications_project.utils.download;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileDownloadUtil {
    private Path fileToDownload;

    public Resource getFileAsResource(String fileName) throws IOException {
        Path uploadDirectory = Paths.get("Uploaded-Files");

        Files.list(uploadDirectory).forEach(file -> {
            if(file.getFileName().toString().equals(fileName)){
                fileToDownload = file;
            }
        });

        if(fileToDownload != null){
            return new UrlResource(fileToDownload.toUri());
        }

        return null;
    }
}
