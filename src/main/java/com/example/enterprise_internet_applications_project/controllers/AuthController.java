package com.example.enterprise_internet_applications_project.controllers;


import com.example.enterprise_internet_applications_project.models.Person;
import com.example.enterprise_internet_applications_project.security.util.JWTUtil;
import com.example.enterprise_internet_applications_project.models.jwtModel.JWTRequestModel;
import com.example.enterprise_internet_applications_project.models.jwtModel.JWTResponseModel;
import com.example.enterprise_internet_applications_project.services.AuthUserDetailService;
import com.example.enterprise_internet_applications_project.services.PersonResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class AuthController {

    Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PersonResourceService personResourceService;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthUserDetailService userDetailsService;


    @GetMapping("/security/test")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String getSecurityMethodName() {
//        return httpServletRequest.getRemoteAddr();
        return "This api use JWT as security method ";
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> generateAuthenticationToken(@RequestBody JWTRequestModel jwtRequestModel)
            throws Exception {

        authenticate(jwtRequestModel.getUsername(), jwtRequestModel.getPassword());
        try {
            final UserDetails userDetails = userDetailsService
                    .loadUserByUsername(jwtRequestModel.getUsername());
            final String token = jwtUtil.generateToken(userDetails);

            Optional<Person> user = personResourceService.findUserDetails(jwtRequestModel.getUsername());
            return ResponseEntity.ok(new JWTResponseModel(token, user.get().getId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }

    private void authenticate(String username, String password) throws Exception {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
