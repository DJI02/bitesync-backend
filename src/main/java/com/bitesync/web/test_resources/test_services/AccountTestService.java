package com.bitesync.web.test_resources.test_services;

import com.bitesync.web.models.Account;
import com.bitesync.web.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

public class AccountTestService {

    private List<Account> accounts;

    public AccountTestService() {
        accounts = List.of(
                new Account("test1", "test4321", List.of(1,2,3), List.of("y", "n", "?")),
                new Account("email@gmail.com", "password", List.of(1,2,3), List.of("Yes", "No", "Yes?")),
                new Account("test2", "test1234", List.of(1,2,3), List.of("y", "n", "?"))
        );
    }

    public Account addAccount(Account account) {
        accounts.add(account);
        return account;
    }

    public void removeAccount(String id) {
        accounts.removeIf(account -> Objects.equals(account.getId(), id));
    }

    public Account getAccount(String email) {
        for(Account account : accounts) {
            if(Objects.equals(account.getEmail(), email))
                return account;
        }
        return null;
    }

    public List<Account> getAll() {
        return accounts;
    }
}
