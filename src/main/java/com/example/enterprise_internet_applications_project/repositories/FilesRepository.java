package com.example.enterprise_internet_applications_project.repositories;

import com.example.enterprise_internet_applications_project.models.MyFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
    @Query(value = "UPDATE MyFile AS f SET f.status=?1, f.checkInUserId=?2 WHERE f.name=?3")
    void changeStatusFile(boolean status,Long userId, String nameFile);

    @Query(value = "SELECT f.id FROM MyFile AS f WHERE f.name = ?1")
    Long getIdFile(String nameFile);

    @Modifying
    @Query("DELETE FROM MyFile f WHERE f.name = ?1")
    void deleteFileByName(String nameFile);

    @Query("SELECT p.id from Person p INNER JOIN  MyFile f ON f.owner.id = p.id WHERE f.name = ?1")
    Long ownerIdFile(String nameFile);

    @Transactional
    @Modifying
    @Query(value = "UPDATE MyFile AS f SET f.pinding=true WHERE f.name=?1")
    void pindingFile(String nameFile);

    @Transactional
    @Modifying
    @Query(value = "UPDATE MyFile AS f SET f.pinding=false WHERE f.name=?1")
    void unpindingFile(String nameFile);

    @Query(value = "SELECT f.pinding FROM MyFile f WHERE f.name = ?1")
    boolean isPinding( String nameFile);

    @Query("SELECT g.id FROM Group g JOIN " +
            "g.fileGroups fg " +
            "JOIN fg.file f " +
            "WHERE g.id = fg.group.id " +
            "AND fg.file.id =:fileId")
    Long findGroupIdByFileId(@Param("fileId")Long fileId);

    @Query("SELECT f.name FROM MyFile as f")
    List<String> getAllFiles();

}