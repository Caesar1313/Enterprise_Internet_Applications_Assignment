package com.example.enterprise_internet_applications_project.models;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "my_group")
public class Group {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "group")
   private List<PersonGroup> personGroups;

    @OneToMany(mappedBy = "group")
    private List<FileGroup> fileGroups;

    public Group() {
    }

    public Group(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Group(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", personGroups=" + personGroups +
                ", fileGroups=" + fileGroups +
                '}';
    }
}
