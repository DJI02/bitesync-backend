package com.bitesync.web.models;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document
public class Key {

    @MongoId
    private String keyID;

    private final String role;
    private String key;

    public Key(String role, String key) {
        super();
        this.role = role;
        this.key = key;
    }

    public String getId() {
        return keyID;
    }

    public void setId(String keyID) {
        this.keyID = keyID;
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
