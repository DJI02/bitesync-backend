package com.bitesync.web.controllers;

import com.bitesync.web.models.Account;
import com.bitesync.web.models.Event;
import com.bitesync.web.services.AccountService;
import com.bitesync.web.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping(value = "/event-mgr", produces = "application/json")
public class EventMgrController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private EventService eventService;

    // CREATES A NEW EVENT WITH ENTERED DETAILS AND PASSES IT
    // TO THE DATABASE MANAGER TO SAVE INTO THE DATABASE.
    @PostMapping("/create")
    public ResponseEntity<String> createEvent(@RequestBody Event event) {
        if(event == null) {
            System.out.println("INVALID EVENT.");
            return new ResponseEntity<>("Invalid event.", HttpStatus.NOT_ACCEPTABLE);
        }

        String accountID = event.getAccountID();
        String author = event.getAuthor();
        String name = event.getName();
        String dateTime = event.getDateAndTime();
        String description = event.getDescription();

        if(accountID == null || accountID.isEmpty()) {
            System.out.println("INVALID ACCOUNT ID.");
            return new ResponseEntity<>("Invalid account ID.", HttpStatus.NOT_ACCEPTABLE);
        }

        if(author == null || author.isEmpty() || author.length() > 128){
            System.out.println("INVALID AUTHOR NAME.");
            return new ResponseEntity<>("Recipe created.", HttpStatus.NOT_ACCEPTABLE);
        }
        if(name == null || name.isEmpty() || name.length() > 128) {
            System.out.println("INVALID EVENT NAME.");
            return new ResponseEntity<>("Recipe created.", HttpStatus.NOT_ACCEPTABLE);
        }
        if(dateTime == null || dateTime.isEmpty() || dateTime.length() > 32) {
            System.out.println("INVALID DATE & TIME.");
            return new ResponseEntity<>("Recipe created.", HttpStatus.NOT_ACCEPTABLE);
        }
        if(description == null || description.isEmpty() || description.length() > 1024) {
            System.out.println("INVALID DESCRIPTION.");
            return new ResponseEntity<>("Recipe created.", HttpStatus.NOT_ACCEPTABLE);
        }

        String eventID = eventService.addEvent(event).getId();
        if(eventID == null || eventID.isEmpty()){
            System.out.println("FAILED TO SAVE EVENT.");
            return new ResponseEntity<>("Failed to create event.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        System.out.println("EVENT SAVED: " + eventID);
        return new ResponseEntity<>("Event created: " + eventID, HttpStatus.CREATED);
    }

    @PutMapping("/edit")
    public ResponseEntity<String> editEvent(@RequestBody Event event) {
        if(event == null) {
            System.out.println("INVALID EVENT.");
            return new ResponseEntity<>("Invalid event.", HttpStatus.NOT_ACCEPTABLE);
        }

        String accountID = event.getAccountID();
        String eventID = event.getId();
        String author = event.getAuthor();
        String name = event.getName();
        String dateTime = event.getDateAndTime();
        String description = event.getDescription();

        if(accountID == null || accountID.isEmpty()) {
            System.out.println("INVALID AUTHOR ID.");
            return new ResponseEntity<>("Invalid author ID.", HttpStatus.NOT_ACCEPTABLE);
        }

        if(eventID == null || eventID.isEmpty()) {
            System.out.println("INVALID EVENT ID.");
            return new ResponseEntity<>("Invalid event ID.", HttpStatus.NOT_ACCEPTABLE);
        }

        if(author == null || author.isEmpty() || author.length() > 128){
            System.out.println("INVALID AUTHOR NAME.");
            return new ResponseEntity<>("Recipe created.", HttpStatus.NOT_ACCEPTABLE);
        }
        if(name == null || name.isEmpty() || name.length() > 128) {
            System.out.println("INVALID EVENT NAME.");
            return new ResponseEntity<>("Recipe created.", HttpStatus.NOT_ACCEPTABLE);
        }
        if(dateTime == null || dateTime.isEmpty() || dateTime.length() > 32) {
            System.out.println("INVALID DATE & TIME.");
            return new ResponseEntity<>("Recipe created.", HttpStatus.NOT_ACCEPTABLE);
        }
        if(description == null || description.isEmpty() || description.length() > 1024) {
            System.out.println("INVALID DESCRIPTION.");
            return new ResponseEntity<>("Recipe created.", HttpStatus.NOT_ACCEPTABLE);
        }

        if(eventService.updateEvent(accountID, eventID, event) == null){
            System.out.println("FAILED TO SAVE EVENT.");
            return new ResponseEntity<>("Failed to update event.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        System.out.println("EVENT SAVED: " + eventID);
        return new ResponseEntity<>("Event updated: " + eventID, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteEvent(@RequestBody Event event) {
        if(event == null) {
            System.out.println("INVALID EVENT.");
            return new ResponseEntity<>("Invalid event.", HttpStatus.NOT_ACCEPTABLE);
        }

        String accountID = event.getAccountID();
        String eventID = event.getId();

        if(accountID == null || accountID.isEmpty()) {
            System.out.println("INVALID AUTHOR ID.");
            return new ResponseEntity<>("Invalid author ID.", HttpStatus.NOT_ACCEPTABLE);
        }

        if(eventID == null || eventID.isEmpty()) {
            System.out.println("INVALID EVENT ID.");
            return new ResponseEntity<>("Invalid event ID.", HttpStatus.NOT_ACCEPTABLE);
        }

        Account account = accountService.getAccountInfo(accountID);
        if(account == null) {
            System.out.println("ACCOUNT NOT FOUND.");
            return new ResponseEntity<>("Account not found.", HttpStatus.NOT_FOUND);
        }

        if(eventService.removeEvent(account, eventID)) {
            System.out.println("EVENT DELETED: " + eventID);
            return new ResponseEntity<>("Event removed: " + eventID, HttpStatus.OK);
        }

        System.out.println("FAILED TO DELETE EVENT: " + eventID);
        return new ResponseEntity<>("Failed to remove event: " + eventID, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/archive")
    public ResponseEntity<String> archiveEvent(@RequestBody Event event) {
        if(event == null) {
            System.out.println("INVALID EVENT.");
            return new ResponseEntity<>("Invalid event.", HttpStatus.NOT_ACCEPTABLE);
        }

        String accountID = event.getAccountID();
        String eventID = event.getId();

        if(accountID == null || accountID.isEmpty()) {
            System.out.println("INVALID AUTHOR ID.");
            return new ResponseEntity<>("Invalid author ID.", HttpStatus.NOT_ACCEPTABLE);
        }

        if(eventID == null || eventID.isEmpty()) {
            System.out.println("INVALID EVENT ID.");
            return new ResponseEntity<>("Invalid event ID.", HttpStatus.NOT_ACCEPTABLE);
        }

        Account account = accountService.getAccountInfo(accountID);
        if(account == null) {
            System.out.println("ACCOUNT NOT FOUND.");
            return new ResponseEntity<>("Account not found.", HttpStatus.NOT_FOUND);
        }

        if(eventService.archiveEvent(account, eventID)) {
            System.out.println("EVENT ARCHIVED: " + eventID);
            return new ResponseEntity<>("Event archived: " + eventID, HttpStatus.OK);
        }

        System.out.println("FAILED TO ARCHIVE EVENT: " + eventID);
        return new ResponseEntity<>("Failed to archive event: " + eventID, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/unarchive")
    public ResponseEntity<String> unarchiveEvent(@RequestBody Event event) {
        if(event == null) {
            System.out.println("INVALID EVENT.");
            return new ResponseEntity<>("Invalid event.", HttpStatus.NOT_ACCEPTABLE);
        }

        String accountID = event.getAccountID();
        String eventID = event.getId();

        if(accountID == null || accountID.isEmpty()) {
            System.out.println("INVALID ACCOUNT ID.");
            return new ResponseEntity<>("Invalid account ID.", HttpStatus.NOT_ACCEPTABLE);
        }

        if(eventID == null || eventID.isEmpty()) {
            System.out.println("INVALID EVENT ID.");
            return new ResponseEntity<>("Invalid event ID.", HttpStatus.NOT_ACCEPTABLE);
        }

        Account account = accountService.getAccountInfo(accountID);
        if(account == null) {
            System.out.println("ACCOUNT NOT FOUND.");
            return new ResponseEntity<>("Account not found.", HttpStatus.NOT_FOUND);
        }

        if(eventService.unarchiveEvent(account, eventID)) {
            System.out.println("EVENT UNARCHIVED: " + eventID);
            return new ResponseEntity<>("Event unarchived: " + eventID, HttpStatus.OK);
        }

        System.out.println("FAILED TO UNARCHIVE EVENT: " + eventID);
        return new ResponseEntity<>("Failed to unarchive event: " + eventID, HttpStatus.BAD_REQUEST);
    }

    /*
    @PostMapping("/change-image")
    public ResponseEntity<String> editImage(@RequestPart String eventID, @RequestPart MultipartFile imageFile) throws IOException {

        if(eventID == null || eventID.isEmpty()) {
            System.out.println("INVALID EVENT ID.");
            return new ResponseEntity<>("Invalid event ID,", HttpStatus.NOT_ACCEPTABLE);
        }

        if(imageFile == null) {
            System.out.println("INVALID IMAGE FILE.");
            return new ResponseEntity<>("Invalid image file: " + eventID, HttpStatus.NOT_ACCEPTABLE);
        }

        Event event = eventService.updateImage(eventID, imageFile);

        if(event == null) {
            System.out.println("FAILED TO SAVE IMAGE.");
            return new ResponseEntity<>("Failed to update image:" + eventID, HttpStatus.BAD_REQUEST);
        }

        System.out.println("IMAGE SAVED.");
        return new ResponseEntity<>("Image updated: " + eventID, HttpStatus.OK);
    }
     */
}
