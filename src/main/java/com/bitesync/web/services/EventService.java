package com.bitesync.web.services;

import com.bitesync.web.models.Account;
import com.bitesync.web.models.Event;
import com.bitesync.web.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
        if(auth.getAccountID().equals(accountID)) {
            event.setId(eventID);
            return events.save(event);
        }
        return null;
    }

    public boolean removeEvent(Account account, String eventID) {

        // SEARCH ACTIVE EVENTS.
        Event auth = events.findById(eventID).orElse(null);
        if(auth == null) {
            return false;
        }

        // VERIFY USER AUTHORIZATION.
        if(auth.getAccountID().equals(account.getId()) || account.getRole().equals("ADMIN")) {
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

    public List<Event> getAll(boolean archived) {
        return events.findAllByArchived(archived);
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

    public Event addTags(String eventID, List<String> tags) {
        Event event = events.findById(eventID).orElse(null);
        if(event == null)
            return null;
        event.updateTags(true, tags);
        return events.save(event);
    }

    public Event removeTags(String eventID, List<String> tags) {
        Event event = events.findById(eventID).orElse(null);
        if(event == null)
            return null;
        event.updateTags(false, tags);
        return events.save(event);
    }

    public boolean archiveEvent(Account account, String eventID) {
        Event auth = this.getEvent(eventID);
        if(auth == null || auth.getArchive())
            return false;
        if(auth.getAccountID().equals(account.getId()) || account.getRole().equals("ADMIN")) {
            auth.setArchive();
            events.save(auth);
            return true;
        }
        return false;
    }

    public boolean unarchiveEvent(Account account, String eventID) {
        Event auth = this.getEvent(eventID);
        if(auth == null || !auth.getArchive())
            return false;
        if(auth.getAccountID().equals(account.getId()) || account.getRole().equals("ADMIN")) {
            auth.setArchive();
            events.save(auth);
            return true;
        }
        return false;
    }

    /*
    public Event updateImage(String accountID, MultipartFile imageFile) throws IOException {
        Event event = events.findById(accountID).orElse(null);
        if(event == null)
            return null;

        String name = imageFile.getOriginalFilename();
        String type = imageFile.getContentType();
        byte[] data = imageFile.getBytes();

        if(event.setImage(name, type, data))
            return events.save(event);
        return null;
    }

     */
}
