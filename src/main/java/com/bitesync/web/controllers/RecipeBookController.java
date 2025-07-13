package com.bitesync.web.controllers;

import com.bitesync.web.models.Account;
import com.bitesync.web.models.Recipe;
import com.bitesync.web.services.AccountService;
import com.bitesync.web.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/recipe-book", produces = "application/json")
public class RecipeBookController {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private AccountService accountService;

    // SERVE ALL EXISTING RECIPES.
    @GetMapping("/all")
    public ResponseEntity<List<Recipe>> viewRecipes() {
        System.out.println("LOADING ALL RECIPES.");
        return new ResponseEntity<>(recipeService.getAll(), HttpStatus.OK);
    }

    // SERVE A SPECIFIC RECIPE BY ID.
    @PostMapping("/view")
    public ResponseEntity<Recipe> viewRecipe(@RequestParam String recipeID) {
        Recipe recipe = recipeService.getRecipe(recipeID);

        // IF NO RECIPE MATCHES THE ENTERED ID.
        if(recipe == null) {
            System.out.println("RECIPE NOT FOUND: " + recipeID);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // IF AN RECIPE IS FOUND MATCHING THE ENTERED ID.
        System.out.println("RECIPE FOUND: " + recipeID);
        return new ResponseEntity<>(recipe, HttpStatus.OK);
    }

    // SERVE AN ACCOUNT'S LIST OF SAVED RECIPES.
    @PostMapping("/favorites")
    public ResponseEntity<List<Recipe>> viewFavorites(@RequestParam String accountID) {
        Account account = accountService.getAccountInfo(accountID);
        if(account == null) {
            System.out.println("ACCOUNT NOT FOUND: " + accountID);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<String> recipeIDs = account.getRecipes();
        List<Recipe> recipes = List.of();
        Recipe recipe;

        for(String recipeID : recipeIDs) {
            recipe = recipeService.getRecipe(recipeID);
            if(recipe == null){
                System.out.println("RECIPE NOT FOUND: " + recipeID);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            System.out.println("RECIPE FOUND: " + recipeID);
            recipes.add(recipe);
        }

        System.out.println("LOADING RECIPES.");
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }

}
