package com.example.enterprise_internet_applications_project.services;


import com.example.enterprise_internet_applications_project.models.Authorities;
import com.example.enterprise_internet_applications_project.models.Person;
import com.example.enterprise_internet_applications_project.models.UserIp;
import com.example.enterprise_internet_applications_project.repositories.AuthorityRepository;
import com.example.enterprise_internet_applications_project.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonResourceService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private UserIPService userIPService;

    public void create(Person person) {
        String password = person.getPassword();
        person.setPassword(new BCryptPasswordEncoder().encode(password));
        String roleName = person.getAuthorities().getAuthority();
        UserIp userIp = person.getUserIp();
        person.setAuthorities(null);
        person.setUserIp(null);
        Person p = personRepository.save(person);
        authorityService.create(new Authorities(0L, roleName, p));
        userIPService.create(userIp);
    }


    public Optional<Person> findByGroupId(Long groupId){
        return personRepository.findByGroupId(groupId);
    }
    public Optional<Person> findUserDetails(String useName) {
        return personRepository.findByName(useName);
    }

    public List<Person> read() {
        return personRepository.findAll();
    }


    public Optional<Person> find(Long id) {
        return personRepository.findById(id);
    }

    public Long findGroupIdByUserId(Long userId){
        return personRepository.findGroupIdByUserId(userId);
    }


    public void update(Person person, Long id) {
        Person person1 = personRepository.findById(id).get();
        person1.setName(person.getName());
        personRepository.save(person1);
    }

    public void updateAuthorities(String authorities, Long id) {
        Authorities a = authorityService.findByPersonId(id).get();
        a.setAuthority(authorities);
        authorityService.updateAuthorities(a);
    }

    public void delete(Long id) {
        personRepository.deleteById(id);
    }

    public List<String> getAllUsers() {
        return personRepository.getAllUsers();
    }
}
