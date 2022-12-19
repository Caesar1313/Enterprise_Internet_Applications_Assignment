package com.example.enterprise_internet_applications_project.repositories;

import com.example.enterprise_internet_applications_project.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> findByName(String name);

    @Query("SELECT p FROM Person p JOIN p.personGroups pg WHERE pg.id =:groupId ")
    Optional<Person> findByGroupId(@Param("groupId") Long groupId);


    @Query("SELECT g.id FROM Group g JOIN g.personGroups pg " +
            "JOIN pg.person p " +
            "WHERE g.id = pg.group.id " +
            "AND pg.person.id =:userId")
    Long findGroupIdByUserId(@Param("userId")Long userId);

    @Query("SELECT p.name FROM Person as p")
    List<String> getAllUsers();
}
