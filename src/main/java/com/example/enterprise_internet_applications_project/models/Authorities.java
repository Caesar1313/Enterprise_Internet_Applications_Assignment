package com.example.enterprise_internet_applications_project.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "authorities")
public class Authorities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String authority;

    @OneToOne
    @JoinColumn(name = "name")
    Person person;

    public Authorities() {
    }

    public Authorities(Long id, String authority) {
        this.id = id;
        this.authority = authority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "Authorities{" +
                "id=" + id +
                ", authority='" + authority + '\'' +
                ", person=" + person +
                '}';
    }
}
