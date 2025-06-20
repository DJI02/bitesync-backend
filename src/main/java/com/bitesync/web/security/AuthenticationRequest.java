package com.bitesync.web.security;


//data transfer object (DTO) that plays a crucial role in your JWT authentication system. 
//This class is designed to capture and transport user credentials during the authentication process.
public class AuthenticationRequest {
    private String email;
    private String password;

    public AuthenticationRequest() {
    }

    public AuthenticationRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}