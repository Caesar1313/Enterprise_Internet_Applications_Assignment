package com.example.enterprise_internet_applications_project.models.jwtModel;

import java.io.Serializable;

public class JWTRequestModel implements Serializable {


    private String username;
    private String password;

    public JWTRequestModel() {
    }

    public JWTRequestModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "JWTRequestModel{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}