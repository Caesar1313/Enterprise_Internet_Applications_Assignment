package com.example.enterprise_internet_applications_project.repositories;


import com.example.enterprise_internet_applications_project.models.Authorities;
import com.example.enterprise_internet_applications_project.models.FileGroup;
import com.example.enterprise_internet_applications_project.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorityRepository extends JpaRepository<Authorities,Long> {

    @Query("SELECT a FROM Authorities a WHERE a.person.name =:name")
    Optional<Authorities> findByName(@Param("name") String name);

    Optional<Authorities> findByPersonId(Long personId);
}
