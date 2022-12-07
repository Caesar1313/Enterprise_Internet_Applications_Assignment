package com.example.enterprise_internet_applications_project.services;

import com.example.enterprise_internet_applications_project.models.Authorities;
import com.example.enterprise_internet_applications_project.models.Person;
import com.example.enterprise_internet_applications_project.repositories.AuthorityRepository;
import com.example.enterprise_internet_applications_project.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorityService {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    AuthorityRepository authorityRepository;


    public Optional<Person> findUserDetails(String useName){return personRepository.findByName(useName);}

     public Optional<Authorities> findUserAuthorities(String useName){
        

     return authorityRepository.findByName(useName);}


}
