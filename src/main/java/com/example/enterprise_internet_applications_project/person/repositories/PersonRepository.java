package com.example.enterprise_internet_applications_project.person.repositories;

import com.example.enterprise_internet_applications_project.person.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PersonRepository extends JpaRepository<Person,Long> {

}
