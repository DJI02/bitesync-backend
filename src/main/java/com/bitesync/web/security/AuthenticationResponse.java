package com.bitesync.web.security;

//data transfer object (DTO) that serves as the response payload
// when a user successfully authenticates in your JWT-based authentication system.
public class AuthenticationResponse {
    private final String jwt;
    private final String userId;

    public AuthenticationResponse(String jwt, String userId) {
        this.jwt = jwt;
        this.userId = userId;
    }

    public String getJwt() {
        return jwt;
    }
    
    public String getUserId() {
        return userId;
    }
}