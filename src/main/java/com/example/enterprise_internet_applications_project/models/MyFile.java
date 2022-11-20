package com.example.enterprise_internet_applications_project.models;

import javax.persistence.*;

@Entity
@Table(name = "files")
public class MyFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    public MyFile() {
    }

    public MyFile(String name){
        this.name = name;
    }

    public long getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}