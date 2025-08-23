package com.bitesync.web.controllers;

import com.bitesync.web.models.Account;
import com.bitesync.web.models.Event;
import com.bitesync.web.services.AccountService;
import com.bitesync.web.services.EventService;
import com.bitesync.web.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/account-mgr", produces = "application/json")
public class AccountMgrController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private EventService eventService;

    // SERVE ACCOUNT'S NON-SENSITIVE INFORMATION.
    // (DOES NOT SERVE PASSWORD OR SECURITY ANSWERS)
    // (SERVED ACCOUNT DOES NOT SHARE THE SAME ID AS ORIGINAL ACCOUNT)
    @PostMapping("/info")
    public ResponseEntity<Account> viewAccount(@RequestParam String accountID) {
        if(accountID == null) {
            System.out.println("INVALID ACCOUNT ID.");
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        Account info = accountService.getAccountInfo(accountID);
        if(info == null) {
            System.out.println("ACCOUNT NOT FOUND");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        System.out.println("ACCOUNT FOUND");
        return new ResponseEntity<>(info, HttpStatus.OK);
    }

    // UPDATE ACCOUNT'S USERNAME.
    @PutMapping("/username")
    public ResponseEntity<String> editUsername(@RequestBody Account account) {
        if(account == null) {
            System.out.println("INVALID ACCOUNT.");
            return new ResponseEntity<>("Invalid account.", HttpStatus.NOT_ACCEPTABLE);
        }

        String accountID = account.getId();
        String username = account.getUsername();

        if(accountID == null || accountID.isEmpty()) {
            System.out.println("INVALID ACCOUNT ID.");
            return new ResponseEntity<>("Invalid account ID.", HttpStatus.NOT_ACCEPTABLE);
        }

        if(username == null || username.isEmpty()) {
            System.out.println("INVALID USERNAME.");
            return new ResponseEntity<>("Invalid username.", HttpStatus.NOT_ACCEPTABLE);
        }

        // IF THERE IS ALREADY AN ACCOUNT UNDER THIS Username.
        // SERVE THE INVALID MESSAGE.
        if(accountService.getAccount(username) != null) {
            System.out.println("INVALID USERNAME.");
            return new ResponseEntity<>("Username already exists.", HttpStatus.BAD_REQUEST);
        }

        if(accountService.updateUsername(accountID, username) == null) {
            System.out.println("FAILED TO SAVE USERNAME.");
            return new ResponseEntity<>("Failed to update account.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        System.out.println("USERNAME SAVED.");
        return new ResponseEntity<>("Account updated: " + accountID, HttpStatus.OK);
    }

    // UPDATE ACCOUNT'S PASSWORD.
    @PutMapping("/password")
    public ResponseEntity<String> editPassword(@RequestBody Account account) {
        if(account == null) {
            System.out.println("INVALID ACCOUNT.");
            return new ResponseEntity<>("Invalid account.", HttpStatus.NOT_ACCEPTABLE);
        }

        String accountID = account.getId();
        List<String> securityAnswers = account.getSecA();
        String password = account.getPassword();

        if(accountID == null || accountID.isEmpty()) {
            System.out.println("INVALID ACCOUNT ID.");
            return new ResponseEntity<>("Invalid account ID.", HttpStatus.NOT_ACCEPTABLE);
        }

        if(securityAnswers == null || securityAnswers.isEmpty()) {
            System.out.println("INVALID SECURITY RESPONSE.");
            return new ResponseEntity<>("Invalid security response.", HttpStatus.NOT_ACCEPTABLE);
        }

        for(String securityAnswer : securityAnswers){
            if(securityAnswer == null || securityAnswer.length() > 128 || securityAnswer.isEmpty()) {
                System.out.println("INVALID SECURITY RESPONSE.");
                return new ResponseEntity<>("Invalid security response.", HttpStatus.NOT_ACCEPTABLE);
            }
        }
        if(password == null || password.length() > 128 || password.length() < 7 || password.contains(" ")) {
            System.out.println("INVALID PASSWORD.");
            return new ResponseEntity<>("Invalid username or password.", HttpStatus.NOT_ACCEPTABLE);
        }

        if(accountService.updatePassword(accountID, password, securityAnswers) == null) {
            System.out.println("FAILED TO SAVE PASSWORD.");
            return new ResponseEntity<>("Failed to update account.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        System.out.println("PASSWORD SAVED.");
        return new ResponseEntity<>("Account updated: " + accountID, HttpStatus.OK);
    }

    // UPDATE ACCOUNT'S LIST OF TAGS.
    @PutMapping("/tags")
    public ResponseEntity<String> editTags(@RequestBody Account account) {
        if(account == null) {
            System.out.println("INVALID ACCOUNT.");
            return new ResponseEntity<>("Invalid account.", HttpStatus.NOT_ACCEPTABLE);
        }

        String accountID = account.getId();
        List<String> tags = account.getTags();

        if(accountID == null || accountID.isEmpty()) {
            System.out.println("INVALID ACCOUNT ID.");
            return new ResponseEntity<>("Invalid account ID.", HttpStatus.NOT_ACCEPTABLE);
        }

        if(tags == null) {
            System.out.println("INVALID TAGS.");
            return new ResponseEntity<>("Invalid tags.", HttpStatus.NOT_ACCEPTABLE);
        }

        for (String tag : tags) {
            if (tag == null || tag.length() > 32 || tag.isEmpty()) {
                System.out.println("INVALID TAGS.");
                return new ResponseEntity<>("Invalid tags.", HttpStatus.NOT_ACCEPTABLE);
            }
        }

        if(accountService.updateTags(accountID, tags) == null) {
            System.out.println("FAILED TO SAVE TAGS.");
            return new ResponseEntity<>("Failed to update account.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        System.out.println("TAGS SAVED.");
        return new ResponseEntity<>("Account updated: " + accountID, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteAccount(@RequestBody List<String> ids) {
        if(ids == null) {
            System.out.println("INVALID IDS.");
            return new ResponseEntity<>("Invalid ids.", HttpStatus.NOT_ACCEPTABLE);
        }

        String callerID = ids.get(0);
        String targetID = ids.get(1);

        if(callerID == null || callerID.isEmpty()) {
            System.out.println("INVALID CALLER ID.");
            return new ResponseEntity<>("Invalid account ID.", HttpStatus.NOT_ACCEPTABLE);
        }

        if(targetID == null || targetID.isEmpty()) {
            System.out.println("INVALID TARGET ID.");
            return new ResponseEntity<>("Invalid account ID.", HttpStatus.NOT_ACCEPTABLE);
        }

        /*
        Account account = accountService.getAccountInfo(targetID);
        if(account == null) {
            System.out.println("ACCOUNT NOT FOUND: " + targetID);
            return new ResponseEntity<>("Account not found: " + targetID, HttpStatus.NOT_FOUND);
        }

        List<Event> allEvents = eventService.getAll();
        for(Event event : allEvents) {
            if(eventService.removeParticipant(event.getId(), targetID) != null)
                eventService.removeTags(targetID, account.getTags());
        }
         */

        if(accountService.removeAccount(callerID, targetID)) {
            System.out.println("ACCOUNT DELETED: " + targetID);
            return new ResponseEntity<>("Account removed: " + targetID, HttpStatus.OK);
        }

        System.out.println("FAILED TO DELETE ACCOUNT: " + targetID);
        return new ResponseEntity<>("Failed to remove account: " + targetID, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
