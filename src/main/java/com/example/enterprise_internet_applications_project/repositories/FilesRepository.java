package com.example.enterprise_internet_applications_project.repositories;

import com.example.enterprise_internet_applications_project.models.MyFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilesRepository extends JpaRepository<MyFile, Integer> {

}