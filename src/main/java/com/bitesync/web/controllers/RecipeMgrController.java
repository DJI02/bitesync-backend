package com.bitesync.web.controllers;

import com.bitesync.web.models.Account;
import com.bitesync.web.models.Recipe;
import com.bitesync.web.services.AccountService;
import com.bitesync.web.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/recipe-mgr", produces = "application/json")
public class RecipeMgrController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private RecipeService recipeService;

    // CREATES A NEW RECIPE WITH ENTERED DETAILS AND PASSES IT
    // TO THE DATABASE MANAGER TO SAVE INTO THE DATABASE.
    @PostMapping("/create")
    public ResponseEntity<String> createRecipe(@RequestBody Recipe recipe) {
        if(recipe == null) {
            System.out.println("INVALID RECIPE.");
            return new ResponseEntity<>("Invalid recipe.", HttpStatus.NOT_ACCEPTABLE);
        }

        String accountID = recipe.getAccountID();
        String author = recipe.getAuthor();
        String name = recipe.getName();
        String ingredients = recipe.getIngredients();
        String instructions = recipe.getInstructions();
        List<String> tags = recipe.getTags();

        if(accountID == null || accountID.isEmpty()) {
            System.out.println("INVALID AUTHOR ID.");
            return new ResponseEntity<>("Invalid author ID.", HttpStatus.NOT_ACCEPTABLE);
        }

        if(author == null || author.isEmpty() || author.length() > 128){
            System.out.println("INVALID AUTHOR.");
            return new ResponseEntity<>("Invalid author name.", HttpStatus.NOT_ACCEPTABLE);
        }
        if(name == null || name.isEmpty() || name.length() > 128) {
            System.out.println("INVALID NAME.");
            return new ResponseEntity<>("Invalid event name.", HttpStatus.NOT_ACCEPTABLE);
        }
        if(ingredients == null || ingredients.isEmpty() || ingredients.length() > 1024) {
            System.out.println("INVALID INGREDIENTS.");
            return new ResponseEntity<>("Invalid ingredients.", HttpStatus.NOT_ACCEPTABLE);
        }
        if(instructions == null || instructions.isEmpty() || instructions.length() > 2048) {
            System.out.println("INVALID INSTRUCTIONS.");
            return new ResponseEntity<>("Invalid instructions.", HttpStatus.NOT_ACCEPTABLE);
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

        String recipeID = recipeService.addRecipe(recipe).getId();
        if(recipeID == null || recipeID.isEmpty()){
            System.out.println("FAILED TO SAVE RECIPE.");
            return new ResponseEntity<>("Failed to create recipe.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        System.out.println("RECIPE SAVED: " + recipeID);
        return new ResponseEntity<>("Recipe created: " + recipeID, HttpStatus.CREATED);
    }

    @PutMapping("/edit")
    public ResponseEntity<String> editRecipe(@RequestBody Recipe recipe) {
        if(recipe == null) {
            System.out.println("INVALID RECIPE.");
            return new ResponseEntity<>("Invalid recipe.", HttpStatus.NOT_ACCEPTABLE);
        }

        String accountID = recipe.getAccountID();
        String recipeID = recipe.getId();
        String author = recipe.getAuthor();
        String name = recipe.getName();
        String ingredients = recipe.getIngredients();
        String instructions = recipe.getInstructions();
        List<String> tags = recipe.getTags();

        if(accountID == null || accountID.isEmpty()) {
            System.out.println("INVALID AUTHOR ID.");
            return new ResponseEntity<>("Invalid author ID.", HttpStatus.NOT_ACCEPTABLE);
        }

        if(recipeID == null || recipeID.isEmpty()) {
            System.out.println("INVALID RECIPE ID.");
            return new ResponseEntity<>("Invalid recipe ID.", HttpStatus.NOT_ACCEPTABLE);
        }

        if(author == null || author.isEmpty() || author.length() > 128){
            System.out.println("INVALID AUTHOR.");
            return new ResponseEntity<>("Invalid author name.", HttpStatus.NOT_ACCEPTABLE);
        }
        if(name == null || name.isEmpty() || name.length() > 128) {
            System.out.println("INVALID NAME.");
            return new ResponseEntity<>("Invalid event name.", HttpStatus.NOT_ACCEPTABLE);
        }
        if(ingredients == null || ingredients.isEmpty() || ingredients.length() > 1024) {
            System.out.println("INVALID INGREDIENTS.");
            return new ResponseEntity<>("Invalid ingredients.", HttpStatus.NOT_ACCEPTABLE);
        }
        if(instructions == null || instructions.isEmpty() || instructions.length() > 2048) {
            System.out.println("INVALID INSTRUCTIONS.");
            return new ResponseEntity<>("Invalid instructions.", HttpStatus.NOT_ACCEPTABLE);
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

        if(recipeService.updateRecipe(accountID, recipeID, recipe) == null) {
            System.out.println("FAILED TO SAVE RECIPE: " + recipeID);
            return new ResponseEntity<>("Failed to update recipe: " + recipeID, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        System.out.println("RECIPE SAVED: " + recipeID);
        return new ResponseEntity<>("Recipe updated: " + recipeID, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteRecipe(@RequestBody Recipe recipe) {
        if(recipe == null) {
            System.out.println("INVALID RECIPE.");
            return new ResponseEntity<>("Invalid recipe.", HttpStatus.NOT_ACCEPTABLE);
        }

        String accountID = recipe.getAccountID();
        String recipeID = recipe.getId();

        if(accountID == null || accountID.isEmpty()) {
            System.out.println("INVALID AUTHOR ID.");
            return new ResponseEntity<>("Invalid author ID.", HttpStatus.NOT_ACCEPTABLE);
        }

        if(recipeID == null || recipeID.isEmpty()) {
            System.out.println("INVALID RECIPE ID.");
            return new ResponseEntity<>("Invalid recipe ID.", HttpStatus.NOT_ACCEPTABLE);
        }

        Account account = accountService.getAccountInfo(accountID);
        if(account == null) {
            System.out.println("ACCOUNT NOT FOUND.");
            return new ResponseEntity<>("Account not found.", HttpStatus.NOT_FOUND);
        }

        if(recipeService.removeRecipe(account, recipeID)) {
            System.out.println("RECIPE DELETED: " + recipeID);
            return new ResponseEntity<>("Recipe removed: " + recipeID, HttpStatus.OK);
        }

        System.out.println("FAILED TO DELETE RECIPE: " + recipeID);
        return new ResponseEntity<>("Failed to delete recipe: " + recipeID, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /*
    @PostMapping("/change-image")
    public ResponseEntity<String> editImage(@RequestPart String recipeID, @RequestPart MultipartFile imageFile) throws IOException {

        if(recipeID == null || recipeID.isEmpty()) {
            System.out.println("INVALID RECIPE ID.");
            return new ResponseEntity<>("Invalid recipe ID.", HttpStatus.NOT_ACCEPTABLE);
        }

        if(imageFile == null) {
            System.out.println("INVALID IMAGE FILE.");
            return new ResponseEntity<>("Invalid image file: " + recipeID, HttpStatus.NOT_ACCEPTABLE);
        }

        Recipe recipe = recipeService.updateImage(recipeID, imageFile);

        if(recipe == null) {
            System.out.println("FAILED TO SAVE IMAGE.");
            return new ResponseEntity<>("Failed to update image:" + recipeID, HttpStatus.BAD_REQUEST);
        }

        System.out.println("IMAGE SAVED.");
        return new ResponseEntity<>("Image updated: " + recipeID, HttpStatus.OK);
    }
     */
}
