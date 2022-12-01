package com.example.enterprise_internet_applications_project.repositories;

import com.example.enterprise_internet_applications_project.models.MyFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface FilesRepository extends JpaRepository<MyFile, Long> {


    @Query("SELECT p.name , p.id FROM MyFile p WHERE p.id =? 1")
    MyFile findFile(Long id);

    @Query(value = "SELECT f FROM MyFile AS f WHERE f.name = ?1")
    MyFile findByName(String nameFile);

    @Query(value = "SELECT f.status FROM MyFile AS f WHERE f.name = ?1")
    boolean statusFile(String nameFile);

    @Transactional
    @Modifying
    @Query(value = "UPDATE MyFile AS f SET f.status=?1 WHERE f.name=?2")
    void changeStatusFile(boolean status, String nameFile);

    @Query(value = "SELECT f.id FROM MyFile AS f WHERE f.name = ?1")
    Long getIdFile(String nameFile);

    @Modifying
    @Query("DELETE FROM MyFile f WHERE f.name = ?1")
    void deleteFileByName(String nameFile);

}