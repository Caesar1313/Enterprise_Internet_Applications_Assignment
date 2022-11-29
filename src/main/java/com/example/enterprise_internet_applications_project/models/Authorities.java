package com.example.enterprise_internet_applications_project.models;

import javax.persistence.*;

@Entity
@Table(name = "authorities")
public class Authorities {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String authority;

    @OneToOne
    @JoinColumn(name = "name", referencedColumnName = "name")
    Person person;
}
