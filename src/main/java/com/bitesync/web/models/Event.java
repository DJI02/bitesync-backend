package com.bitesync.web.models;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.data.util.Pair;

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
    private List<Pair<String, Integer>> tags;

    //private Image image;

    private boolean archived;


    public Event(String accountID, String author, String name, String dateAndTime, String description) {
        super();
        this.accountID = accountID;
        this.author = author;
        this.name = name;
        this.dateAndTime = dateAndTime;
        this.description = description;
        this.participants = new ArrayList<>();
        this.tags = new ArrayList<>();
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

    public List<Pair<String, Integer>> getTags() {
        if(this.tags == null)
            this.tags = new ArrayList<>();
        return tags;
    }

    public void setTags(List<Pair<String, Integer>> tags) {
        this.tags = tags;
    }

    public void updateTags(boolean increment, List<String> tags) {
        if(tags == null)
            return;

        if(this.tags == null)
            this.tags = new ArrayList<>();

        // CYCLE THROUGH ALL PASSED TAGS.
        for(String tag : tags) {
            // TRACK INDEX.
            int i = 0;

            if(tag != null) {
                // IF THE EVENT ALREADY CONTAINS THE TAG, UPDATE ITS QUANTITY.
                for (Pair<String, Integer> pair : this.tags) {
                    if(pair != null) {
                        if (pair.getFirst().equals(tag)) {
                            this.tags.remove(i);

                            // READ INCREMENT OR DECREMENT FLAG.
                            if (increment)
                                this.tags.add(Pair.of(tag, pair.getSecond() + 1));
                            else {
                                if (pair.getSecond() - 1 > 0)
                                    this.tags.add(Pair.of(tag, pair.getSecond() + 1));
                            }
                        }
                    }
                    i++;
                }
                // OTHERWISE, ADD THE NEW TAG TO THE EVENT WITH A QUANTITY OF 1.
                if(increment)
                    this.tags.add(Pair.of(tag, 1));
            }
        }
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
