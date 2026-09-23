package com.ailearning.userservice.service.impl;

import com.ailearning.userservice.dto.request.LoginRequest;
import com.ailearning.userservice.dto.request.RegisterUserRequest;
import com.ailearning.userservice.dto.request.UpdateUserRequest;
import com.ailearning.userservice.dto.response.LoginResponse;
import com.ailearning.userservice.dto.response.RegisterUserResponse;
import com.ailearning.userservice.dto.response.UserProfileResponse;
import com.ailearning.userservice.entity.User;
import com.ailearning.userservice.exception.EmailAlreadyExistsException;
import com.ailearning.userservice.exception.InvalidCredentialsException;
import com.ailearning.userservice.exception.UserNameAlreadyExistsException;
import com.ailearning.userservice.exception.UserNotFoundException;
import com.ailearning.userservice.repository.UserRepository;
import com.ailearning.userservice.service.UserService;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public RegisterUserResponse register(RegisterUserRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserNameAlreadyExistsException("Username already exists");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        User user = new User();

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());

        userRepository.save(user);

        return new RegisterUserResponse(
                "User registered successfully. You will receive an E-mail with admin approval",
                user.getAccountStatus()
        );
    }

    @Override
    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        userRepository.delete(user);
    }

    @Override
    public void updateUser(Long id, UpdateUserRequest request) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!user.getEmail().equals(request.getEmail())
                && userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setContactNumber(request.getContactNumber());

        userRepository.save(user);
    }

    @Override
    public UserProfileResponse getUserProfile(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return new UserProfileResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getEmail(),
                user.getContactNumber(),
                user.getRole(),
                user.getAccountStatus()
        );
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmailOrUsername())
                .orElseGet(() ->
                        userRepository.findByUsername(request.getEmailOrUsername())
                                .orElseThrow(() ->
                                        new InvalidCredentialsException("Invalid email/username or password"))
                );

        if (!user.getPassword().equals(request.getPassword())) {
            throw new InvalidCredentialsException(
                    "Invalid email/username or password"
            );
        }

        return new LoginResponse("Login successful");
    }

}