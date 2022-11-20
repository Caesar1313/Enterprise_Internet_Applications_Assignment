package com.example.enterprise_internet_applications_project.models;


import javax.persistence.*;

@Entity
@Table(name = "person_group")
public class PersonGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id",nullable = false,referencedColumnName = "id")
    private Person person;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "group_id",nullable = false,referencedColumnName = "id")
    private Group group;


    public Person getPerson() {
        return person;
    }



    public Group getGroup() {
        return group;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "PersonGroup{" +
                "id=" + id +
                ", person=" + person +
                ", group=" + group +
                '}';
    }
}
