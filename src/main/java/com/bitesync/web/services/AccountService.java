package com.bitesync.web.services;

import com.bitesync.web.models.Account;
import com.bitesync.web.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
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

    public boolean removeAccount(String callerID, String deleteID) {
        Account account = accounts.findById(callerID).orElse(null);
        if(account == null)
            return false;
        if(callerID.equals(deleteID) || account.getRole().equals("ADMIN")) {
            accounts.deleteById(deleteID);
            return true;
        }
        return false;
    }

    public Account getAccount(String username) {
        return accounts.findByUsername(username);
    }

    public Account getAccountInfo(String id) {
        // RECALL ACTUAL ACCOUNT FROM DATABASE
        Account account = accounts.findById(id).orElse(null);
        if(account == null)
            return null;

        // COPY NON-SENSITIVE INFO TO SECONDARY ACCOUNT OBJECT
        Account info = new Account(account.getUsername(), null, account.getSecQ(), null);
        info.setId(account.getId());
        info.setTags(account.getTags());
        info.setRecipes(account.getRecipes());
        info.setRole(account.getRole());
        return info;
    }

    public List<Account> getAll() {
        List<Account> all = accounts.findAll();
        List<Account> allInfo = new ArrayList<>();

        for(Account account : all) {
            if(account != null)
                allInfo.add(this.getAccountInfo(account.getId()));
        }

        return allInfo;
    }

    public Account updateUsername(String id, String username) {
        Account account = accounts.findById(id).orElse(null);
        if(account == null)
            return null;
        account.setUsername(username);
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

    public Account addRecipe(String accountID, String recipeID) {
        Account account = accounts.findById(accountID).orElse(null);
        if(account == null)
            return null;
        if(!account.addRecipe(recipeID))
            return null;
        return accounts.save(account);
    }

    public Account removeRecipe(String accountID, String recipeID) {
        Account account = accounts.findById(accountID).orElse(null);
        if(account == null)
            return null;
        if(!account.removeRecipe(recipeID))
            return null;
        return accounts.save(account);
    }

    /*
    public Account updateImage(String accountID, MultipartFile imageFile) throws IOException {
        Account account = accounts.findById(accountID).orElse(null);
        if(account == null)
            return null;

        String name = imageFile.getOriginalFilename();
        String type = imageFile.getContentType();
        byte[] data = imageFile.getBytes();

        if(name == null || type == null || type.isEmpty() || data == null)
            return null;

        if(account.setImage(name, type, data))
            return accounts.save(account);
        return null;
    }

     */
}
