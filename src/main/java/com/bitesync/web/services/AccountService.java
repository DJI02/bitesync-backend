package com.bitesync.web.services;

import com.bitesync.web.models.Account;
import com.bitesync.web.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accounts;


    @Autowired
    private PasswordEncoder passwordEncoder;

    public Account addAccount(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword())); // hashes the password
        return accounts.save(account);
    }

    public void removeAccount(String id) {
        accounts.deleteById(id);
    }

    public Account getAccount(String email) {
        return accounts.findByEmail(email);
    }

    public Account getAccountInfo(String id) {
        // RECALL ACTUAL ACCOUNT FROM DATABASE
        Account account = accounts.findById(id).orElse(null);
        if(account == null)
            return null;

        // COPY NON-SENSITIVE INFO TO SECONDARY ACCOUNT OBJECT
        Account info = new Account(account.getEmail(), null, account.getSecQ(), null);
        info.setTags(account.getTags());
        return info;
    }

    public List<Account> getAll() {
        return accounts.findAll();
    }

    public Account updateUsername(String id, String username) {
        Account account = accounts.findById(id).orElse(null);
        if(account == null)
            return null;
        account.setEmail(username);
        return accounts.save(account);
    }

    public Account updatePassword(String id, String password, List<String> secA) {
        Account account = accounts.findById(id).orElse(null);
        if(account == null)
            return null;
        if(account.verifySecA(secA)){
            account.setPassword(passwordEncoder.encode(password));
            return accounts.save(account);
        }
        return null;
    }

    public Account updateTags(String id, List<String> tags) {
        Account account = accounts.findById(id).orElse(null);
        if(account == null)
            return null;
        account.setTags(tags);
        return accounts.save(account);
    }
}
