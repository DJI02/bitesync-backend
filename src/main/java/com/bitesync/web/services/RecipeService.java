package com.bitesync.web.services;

import com.bitesync.web.models.Account;
import com.bitesync.web.models.Recipe;
import com.bitesync.web.repositories.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
        if(auth.getAccountID().equals(accountID)) {
            recipe.setId(recipeID);
            return recipes.save(recipe);
        }
        return null;
    }

    public boolean removeRecipe(Account account, String recipeID) {
        Recipe auth = recipes.findById(recipeID).orElse(null);
        if(auth == null)
            return false;
        if(auth.getAccountID().equals(account.getId()) || account.getRole().equals("ADMIN")) {
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

    /*
    public Recipe updateImage(String accountID, MultipartFile imageFile) throws IOException {
        Recipe recipe = recipes.findById(accountID).orElse(null);
        if(recipe == null)
            return null;

        String name = imageFile.getOriginalFilename();
        String type = imageFile.getContentType();
        byte[] data = imageFile.getBytes();

        if(recipe.setImage(name, type, data))
            return recipes.save(recipe);
        return null;
    }

     */
}
