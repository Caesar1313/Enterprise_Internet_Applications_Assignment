package com.example.enterprise_internet_applications_project.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.aspectj.lang.annotation.RequiredTypes;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "person")
public class Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String password;

    @OneToMany(mappedBy = "person")
    private List<PersonGroup> personGroups;

    @OneToMany(mappedBy = "owner")
    @JsonIgnoreProperties(value = "owner", allowSetters = true)
    private List<MyFile> files;


    @OneToOne(mappedBy = "person")
    Authorities authorities;

    @OneToOne(mappedBy = "person")
    UserIp userIp;


    public Person() {
    }

    public Person(Long id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<PersonGroup> getPersonGroups() {
        return personGroups;
    }

    public void setPersonGroups(List<PersonGroup> personGroups) {
        this.personGroups = personGroups;
    }

    public List<MyFile> getFiles() {
        return files;
    }

    public void setFiles(List<MyFile> files) {
        this.files = files;
    }

    public Authorities getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Authorities authorities) {
        this.authorities = authorities;
    }


    public UserIp getUserIp() {
        return userIp;
    }

    public void setUserIp(UserIp userIp) {
        this.userIp = userIp;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", personGroups=" + personGroups +
                ", files=" + files +
                ", authorities=" + authorities +
                '}';
    }
}
