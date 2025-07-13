package com.bitesync.web.models;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Document
public class Recipe {

    @MongoId
    private String recipeID;

    private final String accountID;
    private String author;
    private String name;
    private String ingredients;
    private String instructions;
    private List<String> tags;

    public Recipe(String accountID, String author, String name, String ingredients, String instructions, List<String> tags) {
        super();
        this.accountID = accountID;
        this.author = author;
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.tags = tags;
    }

    public String getId() {
        return recipeID;
    }

    public void setId(String id) {
        this.recipeID = id;
    }

    public String getAccountID() {
        return accountID;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
