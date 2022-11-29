package com.example.enterprise_internet_applications_project.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "files")
public class MyFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "file")
    private List<FileGroup> fileGroups;

    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id", nullable = false, referencedColumnName = "id")
    @JsonIgnoreProperties(value = "files", allowSetters = true)
    private Person owner;

    public MyFile() {
    }

    public MyFile(String name){
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setOwner(Person owner){
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MyFile{" +
                "id=" + id +
                ", fileGroups=" + fileGroups +
                ", name='" + name + '\'' +
                ", owner=" + owner +
                '}';
    }
}