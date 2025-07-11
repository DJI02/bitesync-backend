package com.bitesync.web.controllers;

import com.bitesync.web.models.Recipe;
import com.bitesync.web.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/recipe-mgr", produces = "application/json")
public class RecipeMgrController {

    @Autowired
    private RecipeService service;

    // CREATES A NEW RECIPE WITH ENTERED DETAILS AND PASSES IT
    // TO THE DATABASE MANAGER TO SAVE INTO THE DATABASE.
    @PostMapping("/create")
    public ResponseEntity<String> createRecipe(@RequestBody Recipe recipe) {
        String accountID = recipe.getAccountID();
        String author = recipe.getAuthor();
        String name = recipe.getName();
        String ingredients = recipe.getIngredients();
        String instructions = recipe.getInstructions();
        List<String> tags = recipe.getTags();

        if(accountID.isEmpty()) {
            System.out.println("INVALID AUTHOR ID.");
            return new ResponseEntity<>("Invalid author ID.", HttpStatus.NOT_ACCEPTABLE);
        }

        if(author.isEmpty() || author.length() > 128){
            System.out.println("INVALID AUTHOR.");
            return new ResponseEntity<>("Invalid author name.", HttpStatus.NOT_ACCEPTABLE);
        }
        if(name.isEmpty() || name.length() > 128) {
            System.out.println("INVALID NAME.");
            return new ResponseEntity<>("Invalid event name.", HttpStatus.NOT_ACCEPTABLE);
        }
        if(ingredients.isEmpty() || ingredients.length() > 1024) {
            System.out.println("INVALID INGREDIENTS.");
            return new ResponseEntity<>("Invalid ingredients.", HttpStatus.NOT_ACCEPTABLE);
        }
        if(instructions.isEmpty() || instructions.length() > 2048) {
            System.out.println("INVALID INSTRUCTIONS.");
            return new ResponseEntity<>("Invalid instructions.", HttpStatus.NOT_ACCEPTABLE);
        }
        for (String tag : tags) {
            if (tag.length() > 32) {
                System.out.println("INVALID TAGS.");
                return new ResponseEntity<>("Invalid tags.", HttpStatus.NOT_ACCEPTABLE);
            }
        }

        String id = service.addRecipe(recipe).getId();
        if(id.isEmpty()){
            System.out.println("FAILED TO SAVE RECIPE.");
            return new ResponseEntity<>("Failed to create recipe.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        System.out.println("RECIPE SAVED.");
        return new ResponseEntity<>("Recipe created: " + id, HttpStatus.CREATED);
    }

    @PutMapping("/edit")
    public ResponseEntity<String> editRecipe(@RequestBody Recipe recipe) {
        String accountID = recipe.getAccountID();
        String recipeID = recipe.getId();
        String author = recipe.getAuthor();
        String name = recipe.getName();
        String ingredients = recipe.getIngredients();
        String instructions = recipe.getInstructions();
        List<String> tags = recipe.getTags();

        if(accountID.isEmpty()) {
            System.out.println("INVALID AUTHOR ID.");
            return new ResponseEntity<>("Invalid author ID.", HttpStatus.NOT_ACCEPTABLE);
        }

        if(author.isEmpty() || author.length() > 128){
            System.out.println("INVALID AUTHOR.");
            return new ResponseEntity<>("Invalid author name.", HttpStatus.NOT_ACCEPTABLE);
        }
        if(name.isEmpty() || name.length() > 128) {
            System.out.println("INVALID NAME.");
            return new ResponseEntity<>("Invalid event name.", HttpStatus.NOT_ACCEPTABLE);
        }
        if(ingredients.isEmpty() || ingredients.length() > 1024) {
            System.out.println("INVALID INGREDIENTS.");
            return new ResponseEntity<>("Invalid ingredients.", HttpStatus.NOT_ACCEPTABLE);
        }
        if(instructions.isEmpty() || instructions.length() > 2048) {
            System.out.println("INVALID INSTRUCTIONS.");
            return new ResponseEntity<>("Invalid instructions.", HttpStatus.NOT_ACCEPTABLE);
        }
        for (String tag : tags) {
            if (tag.length() > 32) {
                System.out.println("INVALID TAGS.");
                return new ResponseEntity<>("Invalid tags.", HttpStatus.NOT_ACCEPTABLE);
            }
        }

        if(service.updateRecipe(accountID, recipeID, recipe) == null) {
            System.out.println("FAILED TO SAVE RECIPE.");
            return new ResponseEntity<>("Failed to update recipe.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        System.out.println("RECIPE SAVED.");
        return new ResponseEntity<>("Recipe updated: " + recipeID, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteRecipe(@RequestBody Recipe recipe) {
        String accountID = recipe.getAccountID();
        String recipeID = recipe.getId();

        if(service.removeRecipe(accountID, recipeID)) {
            System.out.println("RECIPE DELETED.");
            return new ResponseEntity<>("Recipe removed: " + recipeID, HttpStatus.OK);
        }

        System.out.println("FAILED TO DELETE RECIPE.");
        return new ResponseEntity<>("Failed to delete recipe.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
