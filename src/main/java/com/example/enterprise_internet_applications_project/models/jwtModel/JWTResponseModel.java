package com.example.enterprise_internet_applications_project.models.jwtModel;

import java.io.Serializable;

public class JWTResponseModel implements Serializable {

    private final String jwtToken;
    private Long userId;

    public JWTResponseModel(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public JWTResponseModel(String jwtToken, Long userId) {
        this.jwtToken = jwtToken;
        this.userId = userId;
    }

    public String getJwtToken() {
        return this.jwtToken;
    }

    public Long getUserId(){
        return this.userId;
    }
}