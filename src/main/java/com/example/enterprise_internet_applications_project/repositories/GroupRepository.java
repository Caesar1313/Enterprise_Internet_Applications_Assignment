package com.example.enterprise_internet_applications_project.repositories;

import com.example.enterprise_internet_applications_project.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface GroupRepository extends JpaRepository<Group,Long> {

    @Query("SELECT g.name FROM Group AS g")
    List<String> getGroups();
}
