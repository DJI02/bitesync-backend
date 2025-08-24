package com.bitesync.web.models;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document
public class Key {

    private final String role;
    private String key;

    public Key(String role, String key) {
        this.role = role;
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
