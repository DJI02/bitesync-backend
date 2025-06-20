package com.bitesync.web;

import com.bitesync.web.models.Account;
import com.bitesync.web.models.Recipe;
import com.bitesync.web.models.Event;
import com.bitesync.web.test_resources.test_controllers.EventMgrTestController;
import com.bitesync.web.test_resources.test_controllers.LoginTestController;
import com.bitesync.web.test_resources.test_controllers.RecipeMgrTestController;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class BITESYNCTest {
    String validEmail = "email@gmail.com";
    String validPassword = "password";
    String invalidEmail = "xx@@email.com";
    String invalidPassword = "x";
    String exceptionEmail = "X  @g ma il.com ";
    String exceptionPassword = "xx xxx xxxx";

    List<Integer> secQ = List.of(1,2,3);
    List<String> secA = List.of("Yes", "No", "Yes?");
    List<String> invalidSecA = List.of("");

    String validAuthor = "John Doe";
    String validName = "Basic Crepes";
    String validIngredients = "butter, milk, flour, eggs";
    String validInstructions = "1. Pour ingredients into bowl.\n2. Mix until smooth.";
    List<String> validTag = List.of("tree nuts");
    String invalidAuthor = "";
    String invalidName = "";
    String invalidIngredients = "";
    String invalidInstructions = "";
    List<String> invalidTag = List.of("chashews and peanuts and gluten and almonds");

    String validEventName = "Potluck 1";
    String validDateTime = "September 21st at 12:00 PM";
    String validDescription = "First Potluck of the Semester!";
    String invalidEventName = "";
    String invalidDateTime = "October 10th at Noon. See you there!";
    String invalidDescription = "";
    List<String> dummyRecipe = List.of("potatoes", "beef", "chicken", "chorus fruit", "enchanted golden apples", "cake");

    LoginTestController loginTestController = new LoginTestController();
    RecipeMgrTestController recipeMgrTestController = new RecipeMgrTestController();
    EventMgrTestController eventMgrTestController = new EventMgrTestController();

    //------------- Login test cases -------------//
    @Test
    void login_TC1() {
        //all values valid
        Account loginTC1 = new Account(validEmail, validPassword, secQ, secA);
        assertThat(loginTestController.validateAccount(loginTC1)).isEqualTo("Login successful.");
    }

    @Test
    void login_TC2() {
        //invalid password
        Account loginTC2 = new Account(validEmail, invalidPassword, secQ, secA);
        assertThat(loginTestController.validateAccount(loginTC2)).isEqualTo("Invalid email or password.");
    }

    @Test
    void login_TC3() {
        //exception password
        Account loginTC3 = new Account(validEmail, exceptionPassword, secQ, secA);
        assertThat(loginTestController.validateAccount(loginTC3)).isEqualTo("Invalid email or password.");
    }
    @Test
    void login_TC4() {
        //invalid email
        Account loginTC4 = new Account(invalidEmail, validPassword, secQ, secA);
        assertThat(loginTestController.validateAccount(loginTC4)).isEqualTo("Invalid email or password.");
    }
    @Test
    void login_TC5() {
        //exception email
        Account loginTC5 = new Account(exceptionEmail, validPassword, secQ, secA);
        assertThat(loginTestController.validateAccount(loginTC5)).isEqualTo("Invalid email or password.");
    }

    //------------- Logout -------------
    //does not need test cases because there is no significant inputs

    //------------- Create account testcases -------------
    @Test
    void createAccount_TC1() {
        //all values valid
        Account accountTC1 = new Account(validEmail, validPassword, secQ, secA);
        assertThat(loginTestController.createAccount(accountTC1)).isEqualTo("Registration successful.");

    }

    @Test
    void createAccount_TC2() {
        //invalid answer
        Account accountTC2 = new Account(validEmail, validPassword, secQ, invalidSecA);
        assertThat(loginTestController.createAccount(accountTC2)).isEqualTo("Invalid security response.");
    }
    @Test
    void createAccount_TC3() {
        //invalid password
        Account accountTC3 = new Account(validEmail, invalidPassword, secQ, secA);
        assertThat(loginTestController.createAccount(accountTC3)).isEqualTo("Invalid email or password.");
    }
    @Test
    void createAccount_TC4() {
        //exception password

        Account accountTC4 = new Account(validEmail, exceptionPassword, secQ, secA);
        assertThat(loginTestController.createAccount(accountTC4)).isEqualTo("Invalid email or password.");
    }
    @Test
    void createAccount_TC5() {
        //invalid email
        Account accountTC5 = new Account(invalidEmail, validPassword, secQ, secA);
        assertThat(loginTestController.createAccount(accountTC5)).isEqualTo("Invalid email or password.");
    }
    @Test
    void createAccount_TC6() {
        //exception email
        Account accountTC6 = new Account(exceptionEmail, validPassword, secQ, secA);
        assertThat(loginTestController.createAccount(accountTC6)).isEqualTo("Invalid email or password.");
    }

    //------------- Create Recipe Test Cases -------------
    @Test
    void createRecipe_TC1() {
        //all valid
        Recipe recipeTC1 = new Recipe(validAuthor, validName, validIngredients, validInstructions, validTag);
        assertThat(recipeMgrTestController.createRecipe(recipeTC1)).isEqualTo("Recipe created.");
    }
    @Test
    void createRecipe_TC2() {
        //tag invalid
        Recipe recipeTC2 = new Recipe(validAuthor, validName , validIngredients, validInstructions, invalidTag);
        assertThat(recipeMgrTestController.createRecipe(recipeTC2)).isEqualTo("Invalid details.");
    }
    @Test
    void createRecipe_TC3() {
        //instruction invalid
        Recipe recipeTC3 = new Recipe(validAuthor, validName , validIngredients, invalidInstructions, validTag);
        assertThat(recipeMgrTestController.createRecipe(recipeTC3)).isEqualTo("Invalid details.");
    }
    @Test
    void createRecipe_TC4() {
        //instruction invalid
        Recipe recipeTC4 = new Recipe(validAuthor, validName , invalidIngredients, validInstructions, validTag);
        assertThat(recipeMgrTestController.createRecipe(recipeTC4)).isEqualTo("Invalid details.");
    }
    @Test
    void createRecipe_TC5() {
        //name invalid
        Recipe recipeTC5 = new Recipe(validAuthor, invalidName , validIngredients, validInstructions, validTag);
        assertThat(recipeMgrTestController.createRecipe(recipeTC5)).isEqualTo("Invalid details.");
    }
    @Test
    void createRecipe_TC6() {
        //author invalid
        Recipe recipeTC6 = new Recipe(invalidAuthor, validName , validIngredients, validInstructions, validTag);
        assertThat(recipeMgrTestController.createRecipe(recipeTC6)).isEqualTo("Invalid details.");
    }

    //------------- VIEW EVENT TEST CASES -------------
    //does not need test cases because there is no significant inputs

    //------------- CREATE EVENT TEST CASES -------------
    @Test
    void createEvent_TC1() {
        //all valid
        Event eventTC1 = new Event(validAuthor, validEventName, validDateTime, validDescription, dummyRecipe);
        assertThat(eventMgrTestController.createEvent(eventTC1)).isEqualTo("Event created.");
    }
    @Test
    void createEvent_TC2() {
        //description invalid
        Event eventTC2 = new Event(validAuthor, validEventName, validDateTime, invalidDescription, dummyRecipe);
        assertThat(eventMgrTestController.createEvent(eventTC2)).isEqualTo("Invalid details.");
    }
    @Test
    void createEvent_TC3() {
        //dateTime invalid
        Event eventTC4 = new Event(validAuthor, validEventName, invalidDateTime, validDescription, dummyRecipe);
        assertThat(eventMgrTestController.createEvent(eventTC4)).isEqualTo("Invalid details.");
    }
    @Test
    void createEvent_TC4() {
        //event name invalid
        Event eventTC5 = new Event(validAuthor, invalidEventName, validDateTime, validDescription, dummyRecipe);
        assertThat(eventMgrTestController.createEvent(eventTC5)).isEqualTo("Invalid details.");
    }
    @Test
    void createEvent_TC5() {
        //author invalid
        Event eventTC6 = new Event(invalidAuthor, invalidEventName, validDateTime, validDescription, dummyRecipe);
        assertThat(eventMgrTestController.createEvent(eventTC6)).isEqualTo("Invalid details.");
    }
}
