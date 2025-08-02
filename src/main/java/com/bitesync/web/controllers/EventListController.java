package com.bitesync.web.controllers;

import com.bitesync.web.models.Event;
import com.bitesync.web.services.AccountService;
import com.bitesync.web.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/event-list", produces = "application/json")
public class EventListController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private EventService eventService;

    // SERVE ALL EXISTING EVENTS.
    @GetMapping("/all")
    public ResponseEntity<List<Event>> viewEvents() {
        System.out.println("LOADING ALL EVENTS.");
        return new ResponseEntity<>(eventService.getAll(), HttpStatus.FOUND);
    }

    // SERVE A SPECIFIC EVENT BY ID.
    @PostMapping("/view")
    public ResponseEntity<Event> viewEvent(@RequestParam String eventID) {
        if(eventID == null) {
            System.out.println("INVALID EVENT ID.");
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        Event event = eventService.getEvent(eventID);

        // IF NO EVENT MATCHES THE ENTERED ID.
        if(event == null) {
            System.out.println("EVENT NOT FOUND: " + eventID);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // IF AN EVENT IS FOUND MATCHING THE ENTERED ID.
        System.out.println("EVENT FOUND: " + eventID);
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @PutMapping("/join")
    public ResponseEntity<String> joinEvent(@RequestBody List<String> participant) {
        if(participant == null || participant.isEmpty()) {
            System.out.println("INVALID PARTICIPANT.");
            return new ResponseEntity<>("Invalid participant.", HttpStatus.NOT_ACCEPTABLE);
        }

        String eventID = participant.remove(0);

        if(eventID == null || eventID.isEmpty()) {
            System.out.println("INVALID EVENT ID.");
            return new ResponseEntity<>("Invalid event ID.", HttpStatus.NOT_ACCEPTABLE);
        }

        for(String info : participant)
            if(info == null || info.isEmpty() || info.length() > 2048) {
                System.out.println("INVALID PARTICIPANT.");
                return new ResponseEntity<>("Invalid participant.", HttpStatus.NOT_ACCEPTABLE);
            }

        Event event = eventService.updateParticipants(eventID, participant);

        if(event == null) {
            System.out.println("EVENT NOT FOUND: " + eventID);
            return new ResponseEntity<>("Event not found: " + eventID, HttpStatus.NOT_FOUND);
        }

        System.out.println("EVENT SAVED: " + eventID);
        return new ResponseEntity<>("Event updated: " + eventID, HttpStatus.OK);
    }
}