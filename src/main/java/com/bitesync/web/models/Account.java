package com.bitesync.web.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Document
public class Account {

    @MongoId
    private String accountID;

    private String email;
    private String password;
    private List<Integer> secQ;
    private List<String> secA;
    private List<String> tags;

    public Account(String email, String password, List<Integer> secQ, List<String> secA) {
        super();
        this.email = email;
        this.password = password;
        this.secQ = secQ;
        this.secA = secA;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
