package com.bitesync.web.test_resources.test_controllers;

import com.bitesync.web.models.Recipe;
import com.bitesync.web.test_resources.test_services.RecipeTestService;
import java.util.List;

public class RecipeMgrTestController {

    private static RecipeTestService service;

    public RecipeMgrTestController() {
        service = new RecipeTestService();
    }

    // CREATES A NEW RECIPE WITH ENTERED DETAILS AND PASSES IT
    // TO THE DATABASE MANAGER TO SAVE INTO THE DATABASE.
    public static String createRecipe(Recipe recipe) {
        String author = recipe.getAuthor();
        String name = recipe.getName();
        String ingredients = recipe.getIngredients();
        String instructions = recipe.getInstructions();
        List<String> tags = recipe.getTags();

        if(author.isEmpty() || author.length() > 128){
            System.out.println("Invalid details.");
            return "Invalid details.";
        }
        if(name.isEmpty() || name.length() > 128) {
            System.out.println("Invalid details.");
            return "Invalid details.";
        }
        if(ingredients.isEmpty() || ingredients.length() > 1024) {
            System.out.println("Invalid details.");
            return "Invalid details.";
        }
        if(instructions.isEmpty() || instructions.length() > 2048) {
            System.out.println("Invalid details.");
            return "Invalid details.";
        }
        for (String tag : tags) {
            if (tag.length() > 32) {
                System.out.println("Invalid details.");
                return "Invalid details.";
            }
        }

        System.out.println("Success.");
        return "Recipe created.";
    }
}