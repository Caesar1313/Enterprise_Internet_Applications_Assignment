package com.example.enterprise_internet_applications_project.models;

import javax.persistence.*;

@Entity
@Table(name = "user_ip")
public class UserIp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ip;

    @OneToOne
    @JoinColumn(name = "person_id")
    Person person;

    public UserIp(Long id, String ip, Person person) {
        this.id = id;
        this.ip = ip;
        this.person = person;
    }

    public UserIp() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "UserIp{" +
                "id=" + id +
                ", ip='" + ip + '\'' +
                ", person=" + person +
                '}';
    }
}
