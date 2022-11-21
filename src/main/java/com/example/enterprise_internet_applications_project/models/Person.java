package com.example.enterprise_internet_applications_project.models;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "person")
public class Person {


@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "person")
    private List<PersonGroup> personGroups;

    @OneToMany(mappedBy = "owner")
    private List<MyFile> files;


    public Person() {
    }

    public Person(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Person(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
