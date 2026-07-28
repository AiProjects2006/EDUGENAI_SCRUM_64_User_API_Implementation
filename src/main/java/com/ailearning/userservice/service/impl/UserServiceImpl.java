package com.ailearning.userservice.service.impl;

import com.ailearning.userservice.dto.request.RegisterUserRequest;
import com.ailearning.userservice.dto.response.RegisterUserResponse;
import com.ailearning.userservice.entity.User;
import com.ailearning.userservice.exception.EmailAlreadyExistsException;
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

}