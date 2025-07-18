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

    //When a request comes in with a JWT token, the JwtRequestFilter extracts the username (Username), 
    //then uses this service to load the corresponding user details, which are then used to validate the token.
    @Autowired
    private AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountService.getAccount(username);
        if (account == null) {
            throw new UsernameNotFoundException("User not found with Username: " + username);
        }
        return new User(account.getUsername(), account.getPassword(), new ArrayList<>());
    }
}