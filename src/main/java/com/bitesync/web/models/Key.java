package com.bitesync.web.models;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document
public class Key {

    private final String role;
    private String key;

    public Key(Boolean admin, String key) {
        if(admin)
            role = "ADMIN";
        else
            role = "USER";
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public String getRole() {
        return role;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
