package com.example.enterprise_internet_applications_project.models;

import javax.persistence.*;

@Entity
@Table(name = "files")
public class MyFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    // -- attribute -- //
    private Long id;
    private String name;
    private boolean isCheckIn = false;

    // -- constructor -- //
    public MyFile() {
    }

    public MyFile(String name) {
        this.name = name;
        this.isCheckIn = false;

    }

    public MyFile(boolean isCheckIn) {
        this.isCheckIn = isCheckIn;
    }

    public MyFile(String name, boolean isCheckIn) {
        this.name = name;
        this.isCheckIn = isCheckIn;
    }

    // -- getter and setter -- //
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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