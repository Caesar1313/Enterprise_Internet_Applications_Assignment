package com.example.enterprise_internet_applications_project;

import java.io.File;
import java.io.IOException;

public class MyFile {

    private File file;
    private boolean isCheckedIn;

    public MyFile(String fileName) {
        this.file = new File(fileName);
        this.isCheckedIn = false;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public boolean isCheckedIn() {
        return isCheckedIn;
    }

    public void setCheckedIn(boolean checkedIn) {
        isCheckedIn = checkedIn;
    }

    public String getName(){
        return file.getName();
    }

    public void createFile() throws IOException {
        file.createNewFile();
    }

    private String getFileState(){
        if(isCheckedIn)
            return "Checked-in";
        else
            return "Checked-out";
    }

    @Override
    public String toString() {
        return "file: " + getName() + " --- state: " + getFileState() + "\n";
    }
}
