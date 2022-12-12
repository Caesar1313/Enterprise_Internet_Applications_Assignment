package com.example.enterprise_internet_applications_project.services;


import com.example.enterprise_internet_applications_project.models.Authorities;
import com.example.enterprise_internet_applications_project.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.ArrayList;

@Service
public class AuthUserDetailService implements UserDetailsService {


    @Autowired
    AuthorityService authorityService;

    @Autowired
    PersonResourceService personResourceService;


   /*
   this method is for the jwt config security
    */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (authorityService.findUserAuthorities(username).isPresent()
                && personResourceService.findUserDetails(username).isPresent()) {

            Authorities authorities = authorityService.findUserAuthorities(username).get();
            Person person = personResourceService.findUserDetails(username).get();

            ArrayList<GrantedAuthority> roles = new ArrayList<>();
            roles.add(new SimpleGrantedAuthority(authorities.getAuthority()));
            return new User(person.getName(), person.getPassword(), roles);
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}