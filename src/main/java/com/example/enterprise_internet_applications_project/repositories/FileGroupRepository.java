package com.example.enterprise_internet_applications_project.repositories;


import com.example.enterprise_internet_applications_project.models.FileGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface FileGroupRepository extends JpaRepository<FileGroup,Long> {

    @Query("SELECT fg FROM FileGroup fg WHERE fg.file.id = ?1 AND fg.group.id = ?2")
    FileGroup findFileInGroup(Long fileId,Long groupId);

    @Query("SELECT f.name FROM MyFile f JOIN f.fileGroups fg ON f.id = fg.file.id " +
            "WHERE fg.group.id = :id")
    List<String> getFileNamesInGroup(@Param("id") Long groupId);

    @Transactional
    @Modifying
    @Query("DELETE FROM FileGroup fg WHERE fg.file.id = :fileId AND fg.group.id = :groupId")
    void removeFileFromGroup(@Param("fileId") Long fileId, @Param("groupId") Long groupId);

    @Query("SELECT fg FROM FileGroup fg")
    List<FileGroup> getAllFileGroups();
}
