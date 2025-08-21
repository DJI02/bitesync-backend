package com.bitesync.web.controllers;

import com.bitesync.web.models.Account;
import com.bitesync.web.models.Key;
import com.bitesync.web.services.AccountService;
import com.bitesync.web.services.EventService;
import com.bitesync.web.services.RecipeService;
import com.bitesync.web.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value="/admin", produces="application/json")
public class AdminController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private EventService eventService;

    @Autowired
    private SecurityService securityService;

    // SERVE ALL EXISTING ACCOUNTS.
    @PostMapping("/all")
    public ResponseEntity<List<Account>> viewAccounts(@RequestParam String id) {
        if(id.equals("ADMIN")) {
            System.out.println("LOADING ALL ACCOUNTS.");
            return new ResponseEntity<>(accountService.getAll(), HttpStatus.OK);
        }
        System.out.println("ACCESS DENIED.");
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/key")
    public ResponseEntity<String> setKey(@RequestBody List<String> entry) {
        // SEPARATE ACCOUNT ID FROM ROLE AND VALUE OF NEW KEY.
        String id = entry.get(0);
        String role = entry.get(1);
        String key = entry.get(2);

        // VERIFY ADMINISTRATOR.
        if(id != null && id.equals("ADMIN")) {
            System.out.println("ACCESS GRANTED.");

            // VERIFY VALIDITY OF NEW KEY VALUE.
            if(key != null) {

                // UPDATE KEY OF CORRESPONDING ROLE.
                if(role.equals("ADMIN")) {
                    if(!securityService.setKey(new Key(true, key))) {
                        System.out.println("FAILED TO SAVE KEY: " + key);
                        return new ResponseEntity<>("Failed to update key: " + key, HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                }
                else if(role.equals("USER")) {
                    if(!securityService.setKey(new Key(false, key))) {
                        System.out.println("FAILED TO SAVE KEY: " + key);
                        return new ResponseEntity<>("Failed to update key: " + key, HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                }
                else {
                    System.out.println("INVALID ROLE.");
                    return new ResponseEntity<>("Invalid role.", HttpStatus.BAD_REQUEST);
                }

                System.out.println(role + " KEY UPDATED.");
                return new ResponseEntity<>("Key saved: " + key, HttpStatus.OK);
            }
            System.out.println("INVALID KEY.");
            return new ResponseEntity<>("Invalid key.", HttpStatus.BAD_REQUEST);
        }
        System.out.println("ACCESS DENIED.");
        return new ResponseEntity<>("Access denied.", HttpStatus.UNAUTHORIZED);
    }
}
