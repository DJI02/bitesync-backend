package com.bitesync.web.models;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.ArrayList;
import java.util.List;

@Document
public class Account {

    @MongoId
    private String accountID;

    private String username;
    private String password;
    private String role;

    private List<Integer> secQ;
    private List<String> secA;
    private List<String> tags;
    private List<String> recipes;

    //private Image image;

    public Account(String username, String password, List<Integer> secQ, List<String> secA) {
        super();
        this.username = username;
        this.password = password;
        this.role = "USER";
        this.secQ = secQ;
        this.secA = secA;
        this.tags = new ArrayList<>();
        this.recipes = new ArrayList<>();
    }

    public String verifyPassword(String password) {
        if(this.password.equals(password))
            return accountID;
        return null;
    }

    public boolean verifySecA(List<String> secA) {
        return this.secA.equals(secA);
    }

    public String getId() {
        return accountID;
    }

    public void setId(String id) {
        this.accountID = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        if(role == null)
            role = "USER";
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Integer> getSecQ() {
        return secQ;
    }

    public void setSecQ(List<Integer> secQ) {
        this.secQ = secQ;
    }

    public List<String> getSecA() {
        return secA;
    }

    public void setSecA(List<String> secA) {
        this.secA = secA;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getRecipes() {
        return this.recipes;
    }

    public void setRecipes(List<String> recipes) {
        this.recipes = recipes;
    }

    public boolean addRecipe(String recipeID) {
        return recipes.add(recipeID);
    }

    public boolean removeRecipe(String recipeID) {
        return recipes.remove(recipeID);
    }

    /*
    public Image getImage() {
        return image;
    }

    public boolean setImage(String name, String type, byte[] data) {
        try {
            image = new Image(name, type, data);
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

     */
}
