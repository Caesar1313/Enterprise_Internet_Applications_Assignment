package com.example.enterprise_internet_applications_project.group;


import javax.persistence.*;

@Entity
@Table(name = "my_group")
public class Group {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

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
                '}';
    }
}
