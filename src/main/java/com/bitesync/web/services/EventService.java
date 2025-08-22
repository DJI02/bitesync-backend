package com.bitesync.web.services;

import com.bitesync.web.models.Event;
import com.bitesync.web.repositories.ArchivedEventRepository;
import com.bitesync.web.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository events;

    @Autowired
    private ArchivedEventRepository archivedEvents;

    public Event addEvent(Event event) {
        return events.save(event);
    }

    public Event updateEvent(String accountID, String eventID, Event event) {
        Event auth = events.findById(eventID).orElse(null);
        if(auth == null)
            return null;
        if(auth.getAccountID().equals(accountID)) {
            event.setId(eventID);
            return events.save(event);
        }
        return null;
    }

    public boolean removeEvent(String accountID, String eventID) {

        // SEARCH ACTIVE EVENTS.
        Event auth = events.findById(eventID).orElse(null);
        if(auth == null) {
            return false;
        }

        // VERIFY USER AUTHORIZATION.
        if(auth.getAccountID().equals(accountID) || accountID.equals("ADMIN")) {
            events.deleteById(eventID);
            return true;
        }
        return false;
    }

    public Event getEvent(String id) {
        return events.findById(id).orElse(null);
    }

    public List<Event> getAll() {
        return events.findAll();
    }

    public Event updateParticipant(String eventId, List<String> participant) {
        Event event = events.findById(eventId).orElse(null);
        if(event == null)
            return null;

        List<List<String>> eventParticipants = event.getParticipants();
        if(eventParticipants == null)
            return null;

        String userId = participant.get(0);
        if(userId == null)
            return null;

        int i = 0;

        for(List<String> user : eventParticipants) {
            if(user.get(0).equals(userId)) {
                eventParticipants.set(i, participant);
                event.setParticipants(eventParticipants);

                return events.save(event);
            }
            i++;
        }

        eventParticipants.add(participant);
        event.setParticipants(eventParticipants);

        return events.save(event);
    }

    public Event removeParticipant(String eventID, String accountID) {
        Event event = events.findById(eventID).orElse(null);
        if(event == null)
            return null;

        List<List<String>> eventParticipants = event.getParticipants();
        if(eventParticipants == null)
            return null;

        int i = 0;

        for(List<String> user : eventParticipants) {
            if(user.get(0).equals(accountID)) {
                eventParticipants.remove(i);
                event.setParticipants(eventParticipants);

                return events.save(event);
            }
            i++;
        }

        return null;
    }
}
