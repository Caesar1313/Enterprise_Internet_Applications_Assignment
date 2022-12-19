package com.example.enterprise_internet_applications_project.services;


import com.example.enterprise_internet_applications_project.models.FileGroup;
import com.example.enterprise_internet_applications_project.models.Group;
import com.example.enterprise_internet_applications_project.models.MyFile;
import com.example.enterprise_internet_applications_project.repositories.FileGroupRepository;
import com.example.enterprise_internet_applications_project.repositories.FilesRepository;
import com.example.enterprise_internet_applications_project.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class FileGroupService {

    private final FilesRepository filesRepository;
    private final GroupRepository groupRepository;
    private final FileGroupRepository fileGroupRepository;

    @Autowired
    public FileGroupService(FilesRepository filesRepository, GroupRepository groupRepository, FileGroupRepository fileGroupRepository) {
        this.filesRepository = filesRepository;
        this.groupRepository = groupRepository;
        this.fileGroupRepository = fileGroupRepository;
    }

    @CacheEvict(cacheNames = "filesInGroup", allEntries = true)
    public void addFileToGroup(Long fileId,Long groupId){
        Group group = groupRepository.findById(groupId).get();
        MyFile file = filesRepository.findById(fileId).get();
        FileGroup fileGroup = new FileGroup();

        fileGroup.setGroup(group);
        fileGroup.setFile(file);
        fileGroupRepository.save(fileGroup);
    }

    @Modifying
    @Transactional
    @CacheEvict(cacheNames = "filesInGroup", allEntries = true)
    public void removeFileFromGroup(Long fileId,Long groupId){
//        FileGroup fileGroup = fileGroupRepository.findFileInGroup(fileId, groupId);
//        fileGroupRepository.delete(fileGroup);
        fileGroupRepository.removeFileFromGroup(fileId, groupId);
    }

    @Cacheable(cacheNames = "filesInGroup", key = "#groupId")
    public List<String> getFilesInGroup(Long groupId){
        System.out.println("FETCHING FROM DB");
        return fileGroupRepository.getFileNamesInGroup(groupId);
    }

    public List<FileGroup> getAllFileGroups(){
        return fileGroupRepository.getAllFileGroups();
    }
}
