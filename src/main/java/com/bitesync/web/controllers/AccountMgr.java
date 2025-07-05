package com.bitesync.web.controllers;

import com.bitesync.web.models.Account;
import com.bitesync.web.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/account-mgr", produces = "application/json")
public class AccountMgr {

    @Autowired
    private AccountService service;

    @PutMapping("/username")
    public ResponseEntity<String> editUsername(@RequestBody String id, @RequestBody String username) {
        // IF THERE IS ALREADY AN ACCOUNT UNDER THIS EMAIL.
        // SERVE THE INVALID MESSAGE.
        if(service.getAccount(username) != null) {
            System.out.println("INVALID USERNAME.");
            return new ResponseEntity<>("Username already exists.", HttpStatus.BAD_REQUEST);
        }

        if(service.updateUsername(id, username) == null) {
            System.out.println("FAILED TO SAVE USERNAME.");
            return new ResponseEntity<>("Failed to update account.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        System.out.println("USERNAME SAVED.");
        return new ResponseEntity<>("Account updated: " + id, HttpStatus.OK);
    }

    @PutMapping("/password")
    public ResponseEntity<String> editPassword(@RequestBody String id, @RequestBody List<String> securityAnswers, @RequestBody String password) {

        for(String securityAnswer : securityAnswers){
            if(securityAnswer.length() > 128 || securityAnswer.isEmpty()) {
                System.out.println("INVALID SECURITY RESPONSE.");
                return new ResponseEntity<>("Invalid security response.", HttpStatus.NOT_ACCEPTABLE);
            }
        }
        if(password.length() > 128 || password.length() < 7 || password.contains(" ")) {
            System.out.println("INVALID PASSWORD.");
            return new ResponseEntity<>("Invalid username or password.", HttpStatus.NOT_ACCEPTABLE);
        }

        if(service.updatePassword(id, password, securityAnswers) == null) {
            System.out.println("FAILED TO SAVE PASSWORD.");
            return new ResponseEntity<>("Failed to update account.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        System.out.println("PASSWORD SAVED.");
        return new ResponseEntity<>("Account updated: " + id, HttpStatus.OK);
    }

    @PutMapping("/tags")
    public ResponseEntity<String> editTags(@RequestBody String id, @RequestBody List<String> tags) {

        for (String tag : tags) {
            if (tag.length() > 32) {
                System.out.println("INVALID TAGS.");
                return new ResponseEntity<>("Invalid tags.", HttpStatus.NOT_ACCEPTABLE);
            }
        }

        if(service.updateTags(id, tags) == null) {
            System.out.println("FAILED TO SAVE TAGS.");
            return new ResponseEntity<>("Failed to update account.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        System.out.println("TAGS SAVED.");
        return new ResponseEntity<>("Account updated: " + id, HttpStatus.OK);
    }

    @GetMapping("/info")
    public ResponseEntity<Account> viewAccount(@RequestBody String id) {
        Account info = service.getAccountInfo(id);
        if(info == null) {
            System.out.println("ACCOUNT NOT FOUND");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        System.out.println("ACCOUNT FOUND");
        return new ResponseEntity<>(info, HttpStatus.OK);
    }

    // SERVE ALL EXISTING ACCOUNTS.
    @GetMapping("/all")
    public ResponseEntity<List<Account>> viewAccounts() {
        System.out.println("LOADING ALL ACCOUNTS.");
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }
}
