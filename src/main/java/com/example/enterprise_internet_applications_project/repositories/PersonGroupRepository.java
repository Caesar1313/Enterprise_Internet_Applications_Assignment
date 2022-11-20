package com.example.enterprise_internet_applications_project.repositories;


import com.example.enterprise_internet_applications_project.models.PersonGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;



@Repository
public interface PersonGroupRepository extends JpaRepository<PersonGroup,Long> {

    @Query("SELECT pg FROM PersonGroup pg WHERE pg.person.id = ?1 AND pg.group.id = ?2")
    PersonGroup findPersonInGroup(Long personId, Long groupId);
}
