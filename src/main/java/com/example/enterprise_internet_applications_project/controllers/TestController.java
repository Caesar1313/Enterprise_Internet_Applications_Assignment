package com.example.enterprise_internet_applications_project.controllers;

import com.example.enterprise_internet_applications_project.models.Person;
import com.example.enterprise_internet_applications_project.services.PersonResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    @Autowired
    PersonResourceService personResourceService;

    @GetMapping("/security/test")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String getSecurityMethodName(){
        return "This api use JWT as security method " ;
    }

    @GetMapping("/test")
    public List<Person> getPerson(){
        return personResourceService.read();
    }
}
