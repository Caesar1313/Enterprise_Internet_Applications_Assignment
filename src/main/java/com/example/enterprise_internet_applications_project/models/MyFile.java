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
    @JsonIgnoreProperties(value = "person", allowSetters = true)
    private List<FileGroup> fileGroups;

    private String name;
    private boolean status;
    private boolean pinding;

    private Long checkInUserId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id", nullable = false, referencedColumnName = "id")
    @JsonIgnoreProperties(value = "files", allowSetters = true)
    private Person owner;

    public MyFile() {
    }

    public MyFile(String name){
        this.name = name;
    }
    public MyFile(String name,boolean status){
        this.name = name;
        this.status = status;
    }
    public MyFile(String name,boolean status,boolean pinding){
        this.name = name;
        this.status = status;
        this.pinding = pinding;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<FileGroup> getFileGroups() {
        return fileGroups;
    }

    public void setFileGroups(List<FileGroup> fileGroups) {
        this.fileGroups = fileGroups;
    }

    public Long getCheckInUserId() {
        return checkInUserId;
    }

    public void setCheckInUserId(Long checkInUserId) {
        this.checkInUserId = checkInUserId;
    }

    public Person getOwner() {
        return owner;
    }

    public boolean isPinding() {
        return pinding;
    }

    public void setPinding(boolean pending) {
        this.pinding = pending;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
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
                ", status='" + status + '\''+
                ", owner=" + owner +
                '}';
    }
}