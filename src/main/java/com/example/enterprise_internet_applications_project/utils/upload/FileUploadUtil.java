package com.example.enterprise_internet_applications_project.utils.upload;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUploadUtil {
    public static void saveFile(String fileName, MultipartFile multipartFile) throws IOException{
        Path uploadDirectory = Paths.get("Uploaded-Files");

        try(InputStream inputStream = multipartFile.getInputStream()){
            Path filePath = uploadDirectory.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException exception){
            throw new IOException("Error saving uploaded file: " + fileName, exception);
        }
    }
}
