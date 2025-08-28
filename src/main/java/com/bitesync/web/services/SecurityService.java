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
            key = security.findByRole("ADMIN");
        else
            key = security.findByRole("USER");

        if(key != null)
            return key.getKey();
        return null;
    }

    public boolean setKey(Key key) {
        if(key == null || security.findByRole(key.getRole()) == null)
            return false;
        key.setId(security.findByRole(key.getRole()).getId());
        Key check = security.save(key);
        return check.getKey().equals(key.getKey());
    }

    public String initializeKey(Key key) {
        security.save(key);
        return key.getRole() + ": " + key.getKey();
    }
}
