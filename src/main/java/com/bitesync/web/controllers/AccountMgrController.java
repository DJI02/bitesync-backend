package com.bitesync.web.controllers;

import com.bitesync.web.models.Account;
import com.bitesync.web.services.AccountService;
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

    // SERVE ACCOUNT'S NON-SENSITIVE INFORMATION.
    // (DOES NOT SERVE PASSWORD OR SECURITY ANSWERS)
    // (SERVED ACCOUNT DOES NOT SHARE THE SAME ID AS ORIGINAL ACCOUNT)
    @PostMapping("/info")
    public ResponseEntity<Account> viewAccount(@RequestParam String accountID) {
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
        String accountID = account.getId();
        String username = account.getEmail();

        // IF THERE IS ALREADY AN ACCOUNT UNDER THIS EMAIL.
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
        String accountID = account.getId();
        List<String> securityAnswers = account.getSecA();
        String password = account.getPassword();

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
        String accountID = account.getId();
        List<String> tags = account.getTags();

        for (String tag : tags) {
            if (tag.length() > 32) {
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

    // UPDATE ACCOUNT'S LIST OF SAVED RECIPES.
    @PutMapping("/favorites")
    public ResponseEntity<String> editFavorites(@RequestBody Account account) {
        String accountID = account.getId();
        List<String> recipes = account.getRecipes();

        for (String recipe : recipes) {
            if (recipeService.getRecipe(recipe) == null) {
                System.out.println("RECIPE NOT FOUND.");
                return new ResponseEntity<>("Recipe not found.", HttpStatus.NOT_ACCEPTABLE);
            }
        }

        if(accountService.updateRecipes(accountID, recipes) == null) {
            System.out.println("FAILED TO SAVE RECIPES.");
            return new ResponseEntity<>("Failed to update account.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        System.out.println("RECIPES SAVED.");
        return new ResponseEntity<>("Account updated: " + accountID, HttpStatus.OK);
    }

    // SERVE ALL EXISTING ACCOUNTS.
    @GetMapping("/all")
    public ResponseEntity<List<Account>> viewAccounts() {
        System.out.println("LOADING ALL ACCOUNTS.");
        return new ResponseEntity<>(accountService.getAll(), HttpStatus.OK);
    }
}
