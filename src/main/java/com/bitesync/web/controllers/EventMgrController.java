package com.bitesync.web.controllers;

import com.bitesync.web.models.Event;
import com.bitesync.web.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/event-mgr", produces = "application/json")
public class EventMgrController {

    @Autowired
    private EventService service;

    // CREATES A NEW EVENT WITH ENTERED DETAILS AND PASSES IT
    // TO THE DATABASE MANAGER TO SAVE INTO THE DATABASE.
    @PostMapping("/create")
    public ResponseEntity<String> createEvent(@RequestBody Event event) {
        String accountID = event.getAccountID();
        String author = event.getAuthor();
        String name = event.getName();
        String dateTime = event.getDateAndTime();
        String description = event.getDescription();

        if(accountID.isEmpty()) {
            System.out.println("INVALID AUTHOR ID.");
            return new ResponseEntity<>("Invalid author ID.", HttpStatus.NOT_ACCEPTABLE);
        }

        if(author.isEmpty() || author.length() > 128){
            System.out.println("INVALID AUTHOR NAME.");
            return new ResponseEntity<>("Recipe created.", HttpStatus.NOT_ACCEPTABLE);
        }
        if(name.isEmpty() || name.length() > 128) {
            System.out.println("INVALID EVENT NAME.");
            return new ResponseEntity<>("Recipe created.", HttpStatus.NOT_ACCEPTABLE);
        }
        if(dateTime.isEmpty() || dateTime.length() > 32) {
            System.out.println("INVALID DATE & TIME.");
            return new ResponseEntity<>("Recipe created.", HttpStatus.NOT_ACCEPTABLE);
        }
        if(description.isEmpty() || description.length() > 1024) {
            System.out.println("INVALID DESCRIPTION.");
            return new ResponseEntity<>("Recipe created.", HttpStatus.NOT_ACCEPTABLE);
        }

        String id = service.addEvent(event).getId();
        if(id.isEmpty()){
            System.out.println("FAILED TO SAVE EVENT.");
            return new ResponseEntity<>("Failed to create event.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        System.out.println("EVENT SAVED.");
        return new ResponseEntity<>("Event created: " + id, HttpStatus.CREATED);
    }

    @PutMapping("/edit")
    public ResponseEntity<String> editEvent(@RequestBody Event event) {
        String accountID = event.getAccountID();
        String eventID = event.getId();
        String author = event.getAuthor();
        String name = event.getName();
        String dateTime = event.getDateAndTime();
        String description = event.getDescription();

        if(accountID.isEmpty()) {
            System.out.println("INVALID AUTHOR ID.");
            return new ResponseEntity<>("Invalid author ID.", HttpStatus.NOT_ACCEPTABLE);
        }

        if(author.isEmpty() || author.length() > 128){
            System.out.println("INVALID AUTHOR NAME.");
            return new ResponseEntity<>("Recipe created.", HttpStatus.NOT_ACCEPTABLE);
        }
        if(name.isEmpty() || name.length() > 128) {
            System.out.println("INVALID EVENT NAME.");
            return new ResponseEntity<>("Recipe created.", HttpStatus.NOT_ACCEPTABLE);
        }
        if(dateTime.isEmpty() || dateTime.length() > 32) {
            System.out.println("INVALID DATE & TIME.");
            return new ResponseEntity<>("Recipe created.", HttpStatus.NOT_ACCEPTABLE);
        }
        if(description.isEmpty() || description.length() > 1024) {
            System.out.println("INVALID DESCRIPTION.");
            return new ResponseEntity<>("Recipe created.", HttpStatus.NOT_ACCEPTABLE);
        }

        if(service.updateEvent(accountID, eventID, event) == null){
            System.out.println("FAILED TO SAVE EVENT.");
            return new ResponseEntity<>("Failed to update event.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        System.out.println("EVENT SAVED.");
        return new ResponseEntity<>("Event updated: " + eventID, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteEvent(@RequestBody Event event) {
        String accountID = event.getAccountID();
        String eventID = event.getId();

        if(service.removeEvent(accountID, eventID)) {
            System.out.println("EVENT DELETED.");
            return new ResponseEntity<>("Event removed: " + eventID, HttpStatus.OK);
        }

        System.out.println("FAILED TO DELETE EVENT.");
        return new ResponseEntity<>("Failed to remove event.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
