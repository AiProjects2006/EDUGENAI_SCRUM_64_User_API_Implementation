package com.ailearning.userservice.controller;

import com.ailearning.userservice.dto.request.RegisterUserRequest;
import com.ailearning.userservice.dto.response.RegisterUserResponse;
import com.ailearning.userservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    public RegisterUserResponse register(
            @Valid @RequestBody RegisterUserRequest request) {

        return userService.register(request);
    }

    @Operation(summary = "Delete user by ID")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

}
