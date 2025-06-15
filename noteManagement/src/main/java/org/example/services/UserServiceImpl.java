package org.example.services;

import org.example.data.models.User;
import org.example.data.repository.UserRepository;
import org.example.dto.request.CreateUserRequest;
import org.example.dto.request.LoginRequest;
import org.example.dto.response.CreateUserResponse;
import org.example.dto.response.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public CreateUserResponse userCreation(CreateUserRequest createUserRequest) {
        if (!isValidUser(createUserRequest)) {
            throw new IllegalArgumentException("Invalid user data");
        }
        User user = new User();
        user.setUsername(createUserRequest.getUsername());
        user.setPassword(createUserRequest.getPassword());
        user.setName(createUserRequest.getName());
        userRepository.save(user);
        CreateUserResponse response = new CreateUserResponse();
        response.setMessage("account created successfully");
        return response;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        if (isInvalidInput(username, password)) {
            return buildResponse("Invalid username or password", "");
        }

        User user = userRepository.findByUsername(username.trim());
        if (user == null || !user.getPassword().equals(password)) {
            return buildResponse("Invalid username or password", "");
        }

        return buildResponse("Account logged in successfully", user.getId());
    }


    private boolean isInvalidInput(String username, String password) {
        return username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty();
    }

    private LoginResponse buildResponse(String message, String userId) {
        LoginResponse response = new LoginResponse();
        response.setMessage(message);
        response.setId(userId);
        return response;
    }

    private boolean isValidUser(CreateUserRequest req) {
        if (req.getUsername() == null || req.getUsername().trim().isEmpty()) return false;
        if (req.getPassword() == null || req.getPassword().trim().isEmpty()) return false;
        if (req.getName() == null || req.getName().trim().isEmpty()) return false;
        if (userRepository.existsByUsername(req.getUsername())) return false;
        return true;
    }
}
