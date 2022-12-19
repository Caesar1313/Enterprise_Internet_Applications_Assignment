package com.example.enterprise_internet_applications_project.services;


import com.example.enterprise_internet_applications_project.models.Group;
import com.example.enterprise_internet_applications_project.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupResourceService {

    private final GroupRepository groupRepository;

    @Autowired
    public GroupResourceService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public void create(Group group){
        groupRepository.save(group);
    }


    public List<String> read(){return groupRepository.getGroups();}

    public Group findGroupById(Long groupId){
        return groupRepository.findById(groupId).get();
    }

    public Optional<Group> find(Long id){return groupRepository.findById(id);}


    public void update(Group group , Long id){
        Group group1 = groupRepository.findById(id).get();
        group1.setName(group.getName());
        groupRepository.save(group1);
    }


    public void delete(Long id){
        groupRepository.deleteById(id);
    }
}
