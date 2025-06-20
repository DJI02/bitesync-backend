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
@RequestMapping(value = "/recipe-book", produces = "application/json")
public class RecipeBookController {

    @Autowired
    private RecipeService service;

    // SERVE ALL EXISTING RECIPES.
    @GetMapping("/all")
    public ResponseEntity<List<Recipe>> viewRecipes() {
        System.out.println("LOADING ALL RECIPES.");
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    // SERVE A SPECIFIC RECIPE BY ID.
    @PostMapping("/view")
    public ResponseEntity<Recipe> viewRecipe(@RequestParam String id) {
        Recipe recipe = service.getRecipe(id);

        // IF NO RECIPE MATCHES THE ENTERED ID.
        if(recipe == null) {
            System.out.println("RECIPE NOT FOUND.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // IF AN RECIPE IS FOUND MATCHING THE ENTERED ID.
        System.out.println("RECIPE FOUND.");
        return new ResponseEntity<>(recipe, HttpStatus.OK);
    }
}
