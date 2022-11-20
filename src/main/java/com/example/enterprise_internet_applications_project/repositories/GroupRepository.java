package com.example.enterprise_internet_applications_project.repositories;

import com.example.enterprise_internet_applications_project.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GroupRepository extends JpaRepository<Group,Long> {
}
