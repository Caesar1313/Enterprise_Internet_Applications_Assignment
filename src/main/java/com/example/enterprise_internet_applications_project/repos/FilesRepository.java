package com.example.enterprise_internet_applications_project.repos;

import com.example.enterprise_internet_applications_project.models.MyFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FilesRepository extends JpaRepository<MyFile, Integer> {

    @Query(value = "SELECT f FROM MyFile AS f WHERE f.name = ?1")
    MyFile findByName(String nameFile);

    @Query(value = "SELECT f.isCheckIn FROM MyFile AS f WHERE f.name = ?1")
    boolean statusFile(String nameFile);

    @Transactional
    @Modifying
    @Query(value = "UPDATE MyFile AS f SET f.isCheckIn=?1 WHERE f.name=?2")
    void changeStatusFile(boolean status, String nameFile);

    @Query(value = "SELECT f.id FROM MyFile AS f WHERE f.name = ?1")
    int getIdFile(String nameFile);

    @Modifying
    @Query("DELETE FROM MyFile f WHERE f.name = ?1")
    void deleteFileByName(String nameFile);




}