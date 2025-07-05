package com.bitesync.web.services;

import com.bitesync.web.models.Recipe;
import com.bitesync.web.repositories.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipes;

    public Recipe addRecipe(Recipe recipe) {
        return recipes.save(recipe);
    }

    public Recipe updateRecipe(String accountID, String recipeID, Recipe recipe) {
        Recipe auth = recipes.findById(recipeID).orElse(null);
        if(auth == null)
            return null;
        if(auth.getAuthorID().equals(accountID)) {
            recipe.setId(recipeID);
            return recipes.save(recipe);
        }
        return null;
    }

    public boolean removeRecipe(String accountID, String recipeID) {
        Recipe auth = recipes.findById(recipeID).orElse(null);
        if(auth == null)
            return false;
        if(auth.getAuthorID().equals(accountID)) {
            recipes.deleteById(recipeID);
            return true;
        }
        return false;
    }

    public Recipe getRecipe(String id) {
        return recipes.findById(id).orElse(null);
    }

    public List<Recipe> getAll() {
        return recipes.findAll();
    }
}
