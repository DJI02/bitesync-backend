package com.bitesync.web.services;

import com.bitesync.web.models.Key;
import com.bitesync.web.repositories.SecurityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    @Autowired
    private SecurityRepository security;

    public String getKey(boolean admin) {
        Key key;

        if(admin)
            key = security.findById("ADMIN").orElse(null);
        else
            key = security.findById("USER").orElse(null);

        if(key != null)
            return key.getKey();
        return null;
    }

    public boolean setKey(Key key) {
        Key check = security.save(key);
        return check.getKey().equals(key.getKey());
    }
}
