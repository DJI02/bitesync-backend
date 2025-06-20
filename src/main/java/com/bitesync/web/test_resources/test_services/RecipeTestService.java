package com.bitesync.web.test_resources.test_services;

import com.bitesync.web.models.Recipe;

import java.util.List;
import java.util.Objects;

public class RecipeTestService {
    private List<Recipe> recipes;

    public RecipeTestService() {
        recipes = List.of(
                new Recipe("", "", "", "", List.of(""))
        );
    }

    public Recipe addRecipe(Recipe Recipe) {
        recipes.add(Recipe);
        return Recipe;
    }

    public void removeRecipe(String id) {
        recipes.removeIf(Recipe -> Objects.equals(Recipe.getId(), id));
    }

    public Recipe getRecipe(String name) {
        for(Recipe Recipe : recipes) {
            if(Objects.equals(Recipe.getName(), name))
                return Recipe;
        }
        return null;
    }

    public List<Recipe> getAll() {
        return recipes;
    }
}
