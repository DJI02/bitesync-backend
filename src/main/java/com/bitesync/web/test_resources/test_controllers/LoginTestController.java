package com.bitesync.web.test_resources.test_controllers;

import com.bitesync.web.models.Account;
import com.bitesync.web.test_resources.test_services.AccountTestService;
import java.util.List;

public class LoginTestController {

    private static AccountTestService service;

    public LoginTestController() {
        service = new AccountTestService();
    }

    // CREATES A NEW ACCOUNT WITH ENTERED DETAILS AND PASSES IT
    // TO THE DATABASE MANAGER TO SAVE INTO THE DATABASE.
    public static String createAccount(Account account) {
        String username = account.getUsername();
        String password = account.getPassword();
        List<String> securityAnswers = account.getSecA();
        List<Integer> securityQuestions = account.getSecQ();

        if(!username.contains("@") || username.contains(" ") || username.indexOf('\t') > -1 || username.length() < 3 || username.length() > 254){
            System.out.println("Invalid Username or password.");
            return "Invalid Username or password.";
        }

        if(username.indexOf("@") != username.lastIndexOf("@")) {
            System.out.println("Invalid Username or password.");
            return "Invalid Username or password.";
        }

        if(password.length() > 128 || password.length() < 7 || password.contains(" ")) {
            System.out.println("Invalid Username or password.");
            return "Invalid Username or password.";
        }

        for(String securityAnswer : securityAnswers){
            if(securityAnswer.length() > 128 || securityAnswer.equals("")) {
                System.out.println("Invalid security response.");
                return "Invalid security response.";
            }
        }

        if(securityQuestions.isEmpty()) {
            System.out.println("Invalid security response.");
            return "Invalid security response.";
        }

        System.out.println("Success.");
        return "Registration successful.";
    }

    public static String validateAccount(Account login) {
        String Username = login.getUsername();
        String password = login.getPassword();

        if(!Username.contains("@") || Username.contains(" ") || Username.indexOf('\t') > -1 || Username.length() < 3 || Username.length() > 254){
            System.out.println("Invalid Username or password.");
            return "Invalid Username or password.";
        }

        if(Username.indexOf("@") != Username.lastIndexOf("@")) {
            System.out.println("Invalid Username or password.");
            return "Invalid Username or password.";
        }

        if(password.length() > 128 || password.length() < 7 || password.contains(" ")) {
            System.out.println("Invalid Username or password.");
            return "Invalid Username or password.";
        }

        Account account = service.getAccount(Username);

        if(account == null) {
            System.out.println("Invalid Username or password.");
            return "Invalid Username or password.";
        }

        if(!account.getPassword().equals(password)) {
            System.out.println("Invalid Username or password.");
            return "Invalid Username or password.";
        }

        System.out.println("Login successful");
        return "Login successful.";
    }
}

