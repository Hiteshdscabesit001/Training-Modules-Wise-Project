package com.security.Spring_Security.Dto;

public class LoginResponse {

    private String jwtToken;

    public LoginResponse() {
    }

    public LoginResponse(String jwtToken) {
        this.jwtToken = jwtToken;
    }




}
