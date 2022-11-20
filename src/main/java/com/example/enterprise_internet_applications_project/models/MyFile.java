package com.example.enterprise_internet_applications_project.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "files")
public class MyFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "file")
    private List<FileGroup> fileGroups;

    private String name;

    public MyFile() {
    }

    public MyFile(String name){
        this.name = name;
    }

    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}