package com.example.enterprise_internet_applications_project.controllers;


import com.example.enterprise_internet_applications_project.services.GroupManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/group/person")
@PreAuthorize("hasRole('ROLE_USER')")

public class GroupManagementController {

    private final GroupManagementService groupManagementService;

    @Autowired
    public GroupManagementController(GroupManagementService groupManagementService) {
        this.groupManagementService = groupManagementService;
    }

    @PostMapping
    public void addPerson(@RequestParam Long personId,@RequestParam Long groupId){
        groupManagementService.addPerson(personId, groupId);
    }

    @DeleteMapping
    public void removePerson(@RequestParam Long personId,@RequestParam Long groupId){
        groupManagementService.removePerson(personId,groupId);
    }

}
