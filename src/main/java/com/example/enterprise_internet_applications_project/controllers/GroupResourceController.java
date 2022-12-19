package com.example.enterprise_internet_applications_project.controllers;


import com.example.enterprise_internet_applications_project.services.GroupResourceService;
import com.example.enterprise_internet_applications_project.models.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/group")
@PreAuthorize("hasRole('ROLE_USER')")
public class GroupResourceController {
    private final GroupResourceService groupResourceService;

    @Autowired
    public GroupResourceController(GroupResourceService groupResourceService) {
        this.groupResourceService = groupResourceService;
    }

    @PostMapping
    public void create(@RequestBody Group group){
        groupResourceService.create(group);
    }


    @GetMapping
    public List<String> read(){return groupResourceService.read();}


    @GetMapping(path = "{id}")
    public Optional<Group> find(@RequestParam Long id){return groupResourceService.find(id);}


    @PutMapping
    public void update(@RequestBody Group group,@RequestParam Long id){
        groupResourceService.update(group,id);
    }


    @DeleteMapping
    public void delete(@RequestParam Long id){
        groupResourceService.delete(id);
    }
}
