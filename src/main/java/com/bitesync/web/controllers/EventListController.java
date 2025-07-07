package com.bitesync.web.controllers;

import com.bitesync.web.models.Event;
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
    private EventService service;

    // SERVE ALL EXISTING EVENTS.
    @GetMapping("/all")
    public ResponseEntity<List<Event>> viewEvents() {
        System.out.println("LOADING ALL EVENTS.");
        return new ResponseEntity<>(service.getAll(), HttpStatus.FOUND);
    }

    // SERVE A SPECIFIC EVENT BY ID.
    @PostMapping("/view")
    public ResponseEntity<Event> viewEvent(@RequestParam String id) {
        Event event = service.getEvent(id);

        // IF NO EVENT MATCHES THE ENTERED ID.
        if(event == null) {
            System.out.println("EVENT NOT FOUND.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // IF AN EVENT IS FOUND MATCHING THE ENTERED ID.
        System.out.println("EVENT FOUND.");
        return new ResponseEntity<>(event, HttpStatus.OK);
    }
}