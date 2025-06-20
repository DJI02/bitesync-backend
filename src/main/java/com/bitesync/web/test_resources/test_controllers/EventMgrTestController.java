package com.bitesync.web.test_resources.test_controllers;

import com.bitesync.web.models.Event;
import com.bitesync.web.test_resources.test_services.EventTestService;

public class EventMgrTestController {

    private static EventTestService service;

    public EventMgrTestController() {
        service = new EventTestService();
    }

    // CREATES A NEW EVENT WITH ENTERED DETAILS AND PASSES IT
    // TO THE DATABASE MANAGER TO SAVE INTO THE DATABASE.

    public static String createEvent(Event event) {
        String author = event.getAuthor();
        String name = event.getName();
        String dateTime = event.getDateAndTime();
        String description = event.getDescription();

        if(author.isEmpty() || author.length() > 128){
            System.out.println("Invalid details.");
            return "Invalid details.";
        }
        if(name.isEmpty() || name.length() > 128) {
            System.out.println("Invalid details.");
            return "Invalid details.";
        }
        if(dateTime.isEmpty() || dateTime.length() > 32) {
            System.out.println("Invalid details.");
            return "Invalid details.";
        }
        if(description.isEmpty() || description.length() > 1024) {
            System.out.println("Invalid details.");
            return "Invalid details.";
        }

        System.out.println("Success.");
        return "Event created.";
    }
}

