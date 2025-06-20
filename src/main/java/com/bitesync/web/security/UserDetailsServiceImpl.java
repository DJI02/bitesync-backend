package com.bitesync.web.security;

import com.bitesync.web.models.Account;
import com.bitesync.web.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    //When a request comes in with a JWT token, the JwtRequestFilter extracts the username (email), 
    //then uses this service to load the corresponding user details, which are then used to validate the token.
    @Autowired
    private AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountService.getAccount(email);
        if (account == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return new User(account.getEmail(), account.getPassword(), new ArrayList<>());
    }
}