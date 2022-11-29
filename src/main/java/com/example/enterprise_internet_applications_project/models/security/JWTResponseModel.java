package com.example.enterprise_internet_applications_project.models.security;

import java.io.Serializable;

public class JWTResponseModel implements Serializable {

    private final String jwtToken;

    public JWTResponseModel(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getJwtToken() {
        return this.jwtToken;
    }
}