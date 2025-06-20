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

    public Recipe updateRecipe(String id, Recipe recipe) {
        recipe.setId(id);
        return recipes.save(recipe);
    }

    public void removeRecipe(String id) {
        recipes.deleteById(id);
    }

    public Recipe getRecipe(String id) {
        return recipes.findById(id).orElse(null);
    }

    public List<Recipe> getAll() {
        return recipes.findAll();
    }
}
