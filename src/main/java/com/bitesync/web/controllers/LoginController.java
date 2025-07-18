package com.bitesync.web.controllers;

import com.bitesync.web.models.Account;
import com.bitesync.web.security.JwtUtil;
import com.bitesync.web.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.sound.midi.SysexMessage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(produces = "application/json")
public class LoginController {

    @Autowired
    private AccountService service;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    private JwtUtil jwtUtil;

    // CREATES A NEW ACCOUNT WITH ENTERED DETAILS AND PASSES IT
    // TO THE DATABASE MANAGER TO SAVE INTO THE DATABASE.
    @PostMapping("/register")
    public ResponseEntity<String> createAccount(@RequestBody Account account) {
        String username = account.getUsername();
        String password = account.getPassword();
        List<String> securityAnswers = account.getSecA();
        List<Integer> securityQuestions = account.getSecQ();

        if(password.length() > 128 || password.length() < 7 || password.contains(" ")) {
            System.out.println("INVALID PASSWORD.");
            return new ResponseEntity<>("Invalid username or password.", HttpStatus.NOT_ACCEPTABLE);
        }
        for(String securityAnswer : securityAnswers){
            if(securityAnswer.length() > 128 || securityAnswer.isEmpty()) {
                System.out.println("INVALID SECURITY RESPONSE.");
                return new ResponseEntity<>("Invalid security response.", HttpStatus.NOT_ACCEPTABLE);
            }
        }
        if(securityQuestions.isEmpty()) {
            System.out.println("INVALID SECURITY RESPONSE.");
            return new ResponseEntity<>("Invalid security response.", HttpStatus.NOT_ACCEPTABLE);
        }

        // IF THERE IS ALREADY AN ACCOUNT UNDER THIS USERNAME.
        // SERVE THE INVALID MESSAGE.
        if(service.getAccount(username) != null) {
            System.out.println("INVALID USERNAME.");
            return new ResponseEntity<>("Username already exists.", HttpStatus.BAD_REQUEST);
        }

        // OTHERWISE, AN ACCOUNT IS SUCCESSFULLY CREATED.
        try {
            String id = service.addAccount(account).getId();
            if(id.isEmpty()){
                System.out.println("FAILED TO SAVE ACCOUNT.");
                return new ResponseEntity<>("Failed to create account.", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            System.out.println("ACCOUNT SAVED.");
            return new ResponseEntity<>("Registration successful: " + id, HttpStatus.CREATED);
        } catch(Exception e) {
            System.out.println("REGISTRATION FAILED.");
            return new ResponseEntity<>("Registration failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> validateAccount(@RequestBody Account login) {
        try {
            // Authenticate the user
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword())
            );
            
            // If authentication is successful, generate JWT token
            final UserDetails userDetails = userDetailsService.loadUserByUsername(login.getUsername());
            final String jwt = jwtUtil.generateToken(userDetails);
            
            // Return the token along with user ID
            Account account = service.getAccount(login.getUsername());
            Map<String, String> response = new HashMap<>();
            response.put("token", jwt);
            response.put("userId", account.getId());
            
            System.out.println("ACCESS GRANTED.");
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
            
        } catch (BadCredentialsException e) {
            System.out.println("ACCESS DENIED.");
            return new ResponseEntity<>("Invalid Username or password.", HttpStatus.UNAUTHORIZED);
        }
    }
}
