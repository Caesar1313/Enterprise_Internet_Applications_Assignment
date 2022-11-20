package com.example.enterprise_internet_applications_project.models;

import javax.persistence.*;

@Entity
@Table(name = "files")
public class MyFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    // -- attribute -- //
    private int id;
    private String name;
    private boolean isCheckIn;

    // -- constructor -- //
    public MyFile() {
    }

    public MyFile(String name) {
        this.name = name;

    }

    public MyFile(boolean isCheckIn) {
        this.isCheckIn = isCheckIn;
    }

    public MyFile(String name, boolean isCheckIn) {
        this.name = name;
        this.isCheckIn = isCheckIn;
    }

    // -- getter and setter -- //
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIsCheckIn() {
        return isCheckIn;
    }

    public void setIsCheckIn(boolean status) {
        this.isCheckIn = status;
    }

}