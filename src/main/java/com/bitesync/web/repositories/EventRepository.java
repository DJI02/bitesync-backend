package com.bitesync.web.repositories;

import com.bitesync.web.models.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends MongoRepository<Event, String> {
    Event findByName(String name);
    List<Event> findAllByArchived(Boolean archived);
}
