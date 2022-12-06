package com.example.enterprise_internet_applications_project.services;


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


    /**
     * this method used with the other jwt security config
     */
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return new User("eyad","eyad",new ArrayList<>());
//    }


   /*
   this method is for the jwt config security
    */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if ("username".equals(username)) {
            ArrayList<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("USER"));
            return new User("username", "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",
                    authorities);
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

}