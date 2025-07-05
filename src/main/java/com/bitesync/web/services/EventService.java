package com.bitesync.web.services;

import com.bitesync.web.models.Event;
import com.bitesync.web.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository events;

    public Event addEvent(Event event) {
        return events.save(event);
    }

    public Event updateEvent(String accountID, String eventID, Event event) {
        Event auth = events.findById(eventID).orElse(null);
        if(auth == null)
            return null;
        if(auth.getAuthorID().equals(accountID)) {
            event.setId(eventID);
            return events.save(event);
        }
        return null;
    }

    public boolean removeEvent(String accountID, String eventID) {
        Event auth = events.findById(eventID).orElse(null);
        if(auth == null)
            return false;
        if(auth.getAuthorID().equals(accountID)) {
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

    public Event addParticipant(String eventId, List<String> participant) {
        Event event = events.findById(eventId).orElse(null);
        if(event == null)
            return null;

        List<List<String>> eventParticipants = event.getRecipes();
        String userId = participant.get(0);

        for(List<String> user : eventParticipants) {
            if(user.get(0).equals(userId))
                return null;
        }

        eventParticipants.add(participant);
        event.setRecipes(eventParticipants);
        event.setId(eventId);
        return events.save(event);
    }

    public Event updateRecipes(String eventId, List<String> participant) {
        Event event = events.findById(eventId).orElse(null);
        if(event == null)
            return null;

        List<List<String>> eventRecipes = event.getRecipes();
        String userId = participant.get(0);
        int i = 0;

        for(List<String> user : eventRecipes) {
            if(user.get(0).equals(userId)) {
                eventRecipes.set(i, participant);
                event.setRecipes(eventRecipes);
                return events.save(event);
            }
            i++;
        }
        return null;
    }
}
