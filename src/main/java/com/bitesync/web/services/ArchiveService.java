package com.bitesync.web.services;

import com.bitesync.web.models.Event;
import com.bitesync.web.repositories.ArchivedEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArchiveService {

    @Autowired
    private ArchivedEventRepository archivedEvents;

    public Event getArchivedEvent(String id) {
        return archivedEvents.findById(id).orElse(null);
    }

    public List<Event> getAllArchived() {
        return archivedEvents.findAll();
    }

    public Event archiveEvent (String accountID, Event event) {
        if(event == null)
            return null;
        if(event.getAccountID().equals(accountID))
            return archivedEvents.save(event);
        return null;
    }

    public boolean unarchiveEvent (String accountID, String eventID) {

        // SEARCH ACTIVE EVENTS.
        Event auth = archivedEvents.findById(eventID).orElse(null);
        if(auth == null) {
            return false;
        }

        // VERIFY USER AUTHORIZATION.
        if(auth.getAccountID().equals(accountID) || accountID.equals("ADMIN")) {
            archivedEvents.deleteById(eventID);
            return true;
        }
        return false;
    }
}
