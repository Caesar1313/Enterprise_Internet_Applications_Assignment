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
    AuthorityRepository authorityRepository;


    public void create(Authorities authorities) {
        authorityRepository.save(authorities);
    }

    public Optional<Authorities> findByPersonId(Long id){
        return authorityRepository.findByPersonId(id);
    }

    public Optional<Authorities> findUserAuthorities(String useName) {
        return authorityRepository.findByName(useName);
    }


    public void updateAuthorities(Authorities authorities) {
         authorityRepository.save(authorities);
    }

}
