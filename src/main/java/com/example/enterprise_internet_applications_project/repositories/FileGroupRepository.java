package com.example.enterprise_internet_applications_project.repositories;


import com.example.enterprise_internet_applications_project.models.FileGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FileGroupRepository extends JpaRepository<FileGroup,Long> {

    @Query("SELECT fg FROM FileGroup fg WHERE fg.file.id = ?1 AND fg.group.id = ?2")
    FileGroup findFileInGroup(Long fileId,Long groupId);

}
