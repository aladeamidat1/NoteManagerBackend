package org.example.services;

import org.example.dto.request.CreateUserRequest;
import org.example.dto.request.LoginRequest;
import org.example.dto.response.CreateUserResponse;
import org.example.dto.response.LoginResponse;

public interface UserService{
    CreateUserResponse userCreation(CreateUserRequest createUserRequest);

    LoginResponse login(LoginRequest loginRequest);

}
