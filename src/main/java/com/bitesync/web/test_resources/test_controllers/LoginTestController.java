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
        String email = account.getEmail();
        String password = account.getPassword();
        List<String> securityAnswers = account.getSecA();
        List<Integer> securityQuestions = account.getSecQ();

        if(!email.contains("@") || email.contains(" ") || email.indexOf('\t') > -1 || email.length() < 3 || email.length() > 254){
            System.out.println("Invalid email or password.");
            return "Invalid email or password.";
        }

        if(email.indexOf("@") != email.lastIndexOf("@")) {
            System.out.println("Invalid email or password.");
            return "Invalid email or password.";
        }

        if(password.length() > 128 || password.length() < 7 || password.contains(" ")) {
            System.out.println("Invalid email or password.");
            return "Invalid email or password.";
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
        String email = login.getEmail();
        String password = login.getPassword();

        if(!email.contains("@") || email.contains(" ") || email.indexOf('\t') > -1 || email.length() < 3 || email.length() > 254){
            System.out.println("Invalid email or password.");
            return "Invalid email or password.";
        }

        if(email.indexOf("@") != email.lastIndexOf("@")) {
            System.out.println("Invalid email or password.");
            return "Invalid email or password.";
        }

        if(password.length() > 128 || password.length() < 7 || password.contains(" ")) {
            System.out.println("Invalid email or password.");
            return "Invalid email or password.";
        }

        Account account = service.getAccount(email);

        if(account == null) {
            System.out.println("Invalid email or password.");
            return "Invalid email or password.";
        }

        if(!account.getPassword().equals(password)) {
            System.out.println("Invalid email or password.");
            return "Invalid email or password.";
        }

        System.out.println("Login successful");
        return "Login successful.";
    }
}

