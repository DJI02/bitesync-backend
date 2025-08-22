package com.bitesync.web.models;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.ArrayList;
import java.util.List;

@Document
public class Event {

    @MongoId
    private String eventID;

    private final String accountID;
    private String author;
    private String name;
    private String dateAndTime;
    private String description;
    private List<List<String>> participants;
    boolean archived;

    public Event(String accountID, String author, String name, String dateAndTime, String description) {
        super();
        this.accountID = accountID;
        this.author = author;
        this.name = name;
        this.dateAndTime = dateAndTime;
        this.description = description;
        this.participants = new ArrayList<>();
        archived = false;
    }

    public String getId() {
        return eventID;
    }

    public void setId(String id) {
        this.eventID = id;
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

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<List<String>> getParticipants() {
        return participants;
    }

    public void setParticipants(List<List<String>> participants) {
        this.participants = participants;
    }

    public boolean getArchive() {
        return archived;
    }

    public void setArchive() {
        archived = !archived;
    }

    public void setArchive(boolean archived) {
        this.archived = archived;
    }
}
