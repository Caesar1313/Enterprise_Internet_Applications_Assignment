package com.example.enterprise_internet_applications_project.controllers;


import com.example.enterprise_internet_applications_project.models.Authorities;
import com.example.enterprise_internet_applications_project.models.UserIp;
import com.example.enterprise_internet_applications_project.services.PersonResourceService;
import com.example.enterprise_internet_applications_project.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/person")
public class PersonResourceController {

    @Autowired
    private PersonResourceService personResourceService;


    @PostMapping
    public void create(@RequestBody Person person, HttpServletRequest httpServletRequest) {
        person.setUserIp(new UserIp(0L, httpServletRequest.getRemoteAddr(), person));
        personResourceService.create(person);
    }


    @GetMapping
    public List<Person> read() {
        return personResourceService.read();
    }

    @GetMapping("/all/users")
    public List<String> getAllUsers(){
        return personResourceService.getAllUsers();
    }


    @GetMapping(path = "{id}")
    public Optional<Person> find(@RequestParam long id) {
        return personResourceService.find(id);
    }


    @PutMapping
    public void update(@RequestBody Person person, @RequestParam Long id) {
        personResourceService.update(person, id);
    }

    @PutMapping("/authorities")
    public void updateAuthorities(@RequestBody String authorities, @RequestParam Long id) {
        personResourceService.updateAuthorities(authorities, id);
    }

    @DeleteMapping
    public void delete(@RequestParam Long id) {
        personResourceService.delete(id);
    }

}
