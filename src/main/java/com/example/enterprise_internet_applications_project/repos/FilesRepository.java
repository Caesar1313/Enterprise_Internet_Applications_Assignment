package com.example.enterprise_internet_applications_project.repos;

import com.example.enterprise_internet_applications_project.models.MyFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FilesRepository extends JpaRepository<MyFile, Integer> {

    @Query(value = "SELECT f FROM MyFile as f WHERE f.name = ?1")
    MyFile findByName(String nameFile);

    @Query(value = "SELECT f.isCheckIn FROM MyFile as f WHERE f.name = ?1")
    Optional<Boolean> checkStatusFile(String nameFile);

    @Modifying
    @Query("DELETE FROM MyFile f WHERE f.name = ?1")
    ResponseEntity<?> deleteFileByName(String nameFile);


}