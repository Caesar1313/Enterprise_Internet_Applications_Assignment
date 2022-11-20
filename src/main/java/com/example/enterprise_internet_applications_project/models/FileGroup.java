package com.example.enterprise_internet_applications_project.models;


import javax.persistence.*;

@Entity(name = "fileGroup")
@Table(name ="file_group")
public class FileGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "group_id",nullable = false,referencedColumnName = "id")
    private Group group;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "file_id",nullable = false,referencedColumnName = "id")
    private MyFile file;
}
