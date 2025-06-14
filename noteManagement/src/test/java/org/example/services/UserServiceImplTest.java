package org.example.services;

import org.example.dto.request.CreateUserRequest;
import org.example.dto.request.LoginRequest;
import org.example.dto.response.CreateUserResponse;
import org.example.dto.response.LoginResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserServiceImplTest {

    @Autowired
    UserService userService;

    @Test
    void testThatUserCanBeCreated(){
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("username1");
        createUserRequest.setPassword("password");
        createUserRequest.setName("sweet fanta ahmeedah");

        CreateUserResponse response = userService.userCreation(createUserRequest);
        assertEquals("account created successfully",response.getMessage());
    }
    @Test
    void testUserCreationWithEmptyFields() {
        // Empty username
        CreateUserRequest req1 = new CreateUserRequest();
        req1.setUsername("");
        req1.setPassword("pass");
        req1.setName("Name");
        assertThrows(IllegalArgumentException.class, () -> userService.userCreation(req1));

        // Empty password
        CreateUserRequest req2 = new CreateUserRequest();
        req2.setUsername("user");
        req2.setPassword("");
        req2.setName("Name");
        assertThrows(IllegalArgumentException.class, () -> userService.userCreation(req2));

        // Empty name
        CreateUserRequest req3 = new CreateUserRequest();
        req3.setUsername("user");
        req3.setPassword("pass");
        req3.setName("");
        assertThrows(IllegalArgumentException.class, () -> userService.userCreation(req3));
    }

    @Test
    void testUserCreationWithDuplicateUsername() {
        // First create a user
        CreateUserRequest req1 = new CreateUserRequest();
        req1.setUsername("duplicateUser");
        req1.setPassword("password");
        req1.setName("Name");
        userService.userCreation(req1); // assume successful creation

        // Attempt to create another with same username
        CreateUserRequest req2 = new CreateUserRequest();
        req2.setUsername("duplicateUser");
        req2.setPassword("anotherPass");
        req2.setName("Another Name");
        assertThrows(IllegalArgumentException.class, () -> userService.userCreation(req2));
    }
    @Test
    void testThatUserCanLogin(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username1");
        loginRequest.setPassword("password");
        LoginResponse loginResponse =  userService.login(loginRequest);
        assertEquals("Account logged in successfully", loginResponse.getMessage());
    }
    @Test
    void testThatUserCannotLoginWithWrongPasswordOrUsername(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("the bad user");
        loginRequest.setPassword("password");
        LoginResponse loginResponse =  userService.login(loginRequest);
        assertEquals("Invalid username or password",  loginResponse.getMessage());
    }
}