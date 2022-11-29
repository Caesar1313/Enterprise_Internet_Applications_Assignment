package com.example.enterprise_internet_applications_project.services;


import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthUserDetailService implements UserDetailsService {


    /**
     * this method used with the other jwt security config
     *
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
        if ("eyad".equals(username)) {
            return new User("eyad", "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",
                    new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

}