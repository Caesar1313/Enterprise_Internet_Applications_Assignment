package com.example.enterprise_internet_applications_project.repositories;

import com.example.enterprise_internet_applications_project.MyFile;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;

@Repository
public class FileRepository {

    private final ArrayList<MyFile> files;

    FileRepository(){
        this.files = new ArrayList<>();
    }

    public MyFile getFileByName(String name){
        for (MyFile file : files)
            if (file.getName().equals(name))
                return file;
        return null;
    }

    public void createFile(String fileName) throws IOException {
        String DESKTOP_PATH = "C:\\Users\\Lenovo\\Desktop\\";
        MyFile file = new MyFile(DESKTOP_PATH + fileName);
        file.createFile();
        files.add(file);
    }

    public String getFilesStatus(){
        StringBuilder filesStatus = new StringBuilder();
        for (MyFile file : files) {
            filesStatus.append(file.toString());
        }
        return filesStatus.toString();
    }

    public String checkinFile(String fileName){
        for (MyFile file : files) {
            if (file.getName().equals(fileName)){
                if(file.isCheckedIn())
                    return "file " + fileName + " is already checked-in. Try again after a while.";
                else{
                    file.setCheckedIn(true);
                    return "file " + fileName + " checked-in successfully!";
                }
            }
        }
        return "No such file named " + fileName + ".";
    }

    public String checkoutFile(String fileName){
        for (MyFile file : files) {
            if (file.getName().equals(fileName)){
                if(!file.isCheckedIn())
                    return "file " + fileName + " is already checked-out.";
                else{
                    file.setCheckedIn(false);
                    return "file " + fileName + " checked-out successfully!";
                }
            }
        }
        return "No such file named " + fileName + ".";
    }
}