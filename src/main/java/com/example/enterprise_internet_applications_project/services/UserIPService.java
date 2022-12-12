package com.example.enterprise_internet_applications_project.services;

import com.example.enterprise_internet_applications_project.models.Authorities;
import com.example.enterprise_internet_applications_project.models.UserIp;
import com.example.enterprise_internet_applications_project.repositories.AuthorityRepository;
import com.example.enterprise_internet_applications_project.repositories.UserIPRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserIPService {


    @Autowired
    UserIPRepository userIPRepository;


    public void create(UserIp userIp){
        userIPRepository.save(userIp);
    }
}
