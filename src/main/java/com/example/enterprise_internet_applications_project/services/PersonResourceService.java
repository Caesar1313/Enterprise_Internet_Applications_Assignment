package com.example.enterprise_internet_applications_project.services;


import com.example.enterprise_internet_applications_project.models.Person;
import com.example.enterprise_internet_applications_project.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonResourceService {

    @Autowired
    private  PersonRepository personRepository;





    public void create(Person person){
       personRepository.save(person);
    }


    public List<Person> read(){return personRepository.findAll();}



    public Optional<Person> find(Long id){return personRepository.findById(id);}


    public void update(Person person , Long id){
        Person person1 = personRepository.findById(id).get();
        person1.setName(person.getName());
        personRepository.save(person1);
    }


    public void delete(Long id){
        personRepository.deleteById(id);
    }


}
