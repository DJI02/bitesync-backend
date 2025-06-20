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
        String author = recipe.getAuthor();
        String name = recipe.getName();
        String ingredients = recipe.getIngredients();
        String instructions = recipe.getInstructions();
        List<String> tags = recipe.getTags();

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

    @PutMapping("/edit/{id}")
    public ResponseEntity<String> editRecipe(@PathVariable String id, @RequestBody Recipe recipe) {
        String author = recipe.getAuthor();
        String name = recipe.getName();
        String ingredients = recipe.getIngredients();
        String instructions = recipe.getInstructions();
        List<String> tags = recipe.getTags();

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

        if(service.updateRecipe(id, recipe) == null) {
            System.out.println("FAILED TO SAVE RECIPE.");
            return new ResponseEntity<>("Failed to update recipe.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        System.out.println("RECIPE SAVED.");
        return new ResponseEntity<>("Recipe updated: " + id, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRecipe(@PathVariable String id) {
        service.removeRecipe(id);

        if(service.getRecipe(id) != null) {
            System.out.println("FAILED TO DELETE RECIPE.");
            return new ResponseEntity<>("Failed to delete recipe.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        System.out.println("RECIPE DELETED.");
        return new ResponseEntity<>("Recipe removed: " + id, HttpStatus.OK);
    }
}
