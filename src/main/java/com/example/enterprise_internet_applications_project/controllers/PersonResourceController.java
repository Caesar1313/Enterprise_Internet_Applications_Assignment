package com.example.enterprise_internet_applications_project.controllers;


import com.example.enterprise_internet_applications_project.services.PersonResourceService;
import com.example.enterprise_internet_applications_project.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/person")
public class PersonResourceController {

    private final PersonResourceService personResourceService;

    @Autowired
    public PersonResourceController(PersonResourceService personResourceService) {
        this.personResourceService = personResourceService;
    }


    @PostMapping
    public void create(@RequestBody Person person){
        personResourceService.create(person);
    }


    @GetMapping
    public List<Person> read(){return personResourceService.read();}


    @GetMapping(path = "{id}")
    public Optional<Person> find(@RequestParam Long id){return personResourceService.find(id);}


    @PutMapping
    public void update(@RequestBody Person person,@RequestParam Long id){
         personResourceService.update(person,id);
    }


    @DeleteMapping
    public void delete(@RequestParam Long id){
         personResourceService.delete(id);
    }



}
