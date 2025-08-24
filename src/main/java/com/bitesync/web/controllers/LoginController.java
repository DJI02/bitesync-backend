package com.bitesync.web.controllers;

import com.bitesync.web.models.Account;
import com.bitesync.web.security.JwtUtil;
import com.bitesync.web.services.AccountService;
import com.bitesync.web.services.SecurityService;
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
    private SecurityService security;
    
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
        if(account == null) {
            System.out.println("INVALID ACCOUNT.");
            return new ResponseEntity<>("Invalid account.", HttpStatus.NOT_ACCEPTABLE);
        }

        // VERIFY REGISTRATION KEY.
        boolean admin = false;
        String key = account.getId();

        if(key == null || !key.equals(security.getKey(false))) {
            if(key != null && key.equals(security.getKey(true)))
                admin = true;
            else {
                System.out.println("INVALID REGISTRATION KEY.");
                return new ResponseEntity<>("Invalid registration key.", HttpStatus.UNAUTHORIZED);
            }
        }

        // VERIFY VALIDITY OF ACCOUNT DETAILS.
        String username = account.getUsername();
        String password = account.getPassword();
        List<String> securityAnswers = account.getSecA();
        List<Integer> securityQuestions = account.getSecQ();

        if(username == null || username.isEmpty()) {
            System.out.println("INVALID USERNAME.");
            return new ResponseEntity<>("Invalid username or password.", HttpStatus.NOT_ACCEPTABLE);
        }

        if(password == null || password.length() > 128 || password.length() < 7 || password.contains(" ")) {
            System.out.println("INVALID PASSWORD.");
            return new ResponseEntity<>("Invalid username or password.", HttpStatus.NOT_ACCEPTABLE);
        }

        if(securityQuestions == null || securityQuestions.isEmpty() || securityAnswers == null || securityAnswers.isEmpty()) {
            System.out.println("INVALID SECURITY RESPONSE.");
            return new ResponseEntity<>("Invalid security response.", HttpStatus.NOT_ACCEPTABLE);
        }

        for(String securityAnswer : securityAnswers){
            if(securityAnswer == null || securityAnswer.length() > 128 || securityAnswer.isEmpty()) {
                System.out.println("INVALID SECURITY RESPONSE.");
                return new ResponseEntity<>("Invalid security response.", HttpStatus.NOT_ACCEPTABLE);
            }
        }

        // IF THERE IS ALREADY AN ACCOUNT UNDER THIS USERNAME.
        // SERVE THE INVALID MESSAGE.
        if(service.getAccount(username) != null) {
            System.out.println("INVALID USERNAME.");
            return new ResponseEntity<>("Username already exists.", HttpStatus.BAD_REQUEST);
        }

        // IF ADMIN KEY IS ENTERED, SET ACCOUNT TO ADMINISTRATOR.
        account = new Account(username, password, securityQuestions, securityAnswers);
        if(admin)
            account.setRole("ADMIN");

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

    @PostMapping("/change-password")
    public ResponseEntity<Account> changePassword(@RequestParam String username) {
        if(username == null || username.isEmpty()) {
            System.out.println("INVALID USERNAME.");
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        // FETCH ACCOUNT BY USERNAME.
        Account account = service.getAccount(username);

        if(account == null) {
            System.out.println("ACCOUNT NOT FOUND.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // COPY ACCOUNT ID AND SECURITY QUESTIONS INTO A MORE SECURE TEMPLATE.
        Account secure = new Account(null, null, account.getSecQ(), null);
        secure.setId(account.getId());

        System.out.println("ACCOUNT FOUND.");
        return new ResponseEntity<>(secure, HttpStatus.OK);
    }
}
