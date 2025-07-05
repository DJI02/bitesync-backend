package com.bitesync.web.test_resources.test_services;

import com.bitesync.web.models.Event;

import java.util.List;
import java.util.Objects;

public class EventTestService {
    private List<Event> events;

    public EventTestService() {
        events = List.of(
                new Event("", "", "", "", "", List.of(List.of("")))
        );
    }

    public Event addEvent(Event event) {
        events.add(event);
        return event;
    }

    public void removeEvent(String id) {
        events.removeIf(event -> Objects.equals(event.getId(), id));
    }

    public Event getEvent(String name) {
        for(Event event : events) {
            if(Objects.equals(event.getName(), name))
                return event;
        }
        return null;
    }

    public List<Event> getAll() {
        return events;
    }
}
