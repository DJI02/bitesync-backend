package com.bitesync.web.repositories;

import com.bitesync.web.models.Key;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityRepository extends MongoRepository<Key, String> {
    public Key findByRole(String role);
}
