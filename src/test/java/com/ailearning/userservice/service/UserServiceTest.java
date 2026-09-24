package com.ailearning.userservice.service;

import com.ailearning.userservice.dto.request.LoginRequest;
import com.ailearning.userservice.dto.request.UpdateUserRequest;
import com.ailearning.userservice.dto.response.LoginResponse;
import com.ailearning.userservice.dto.response.UserProfileResponse;
import com.ailearning.userservice.entity.User;
import com.ailearning.userservice.enums.Role;
import com.ailearning.userservice.enums.UserStatus;
import com.ailearning.userservice.exception.InvalidCredentialsException;
import com.ailearning.userservice.exception.UserNotFoundException;
import com.ailearning.userservice.repository.UserRepository;
import com.ailearning.userservice.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User createUser() {

        User user = new User();

        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUsername("john123");
        user.setEmail("john@test.com");
        user.setPassword("password123");
        user.setContactNumber("0771234567");
        user.setRole(Role.STUDENT);
        user.setAccountStatus(UserStatus.PENDING_VERIFICATION);

        return user;
    }

    @Test
    void shouldUpdateUserProfile() {

        User user = createUser();

        UpdateUserRequest request = new UpdateUserRequest();

        request.setFirstName("Johnny");
        request.setLastName("Smith");
        request.setEmail("johnny@test.com");
        request.setContactNumber("0779999999");

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(userRepository.existsByEmail("johnny@test.com"))
                .thenReturn(false);

        userService.updateUser(1L, request);

        assertEquals("Johnny", user.getFirstName());
        assertEquals("Smith", user.getLastName());
        assertEquals("johnny@test.com", user.getEmail());
        assertEquals("0779999999", user.getContactNumber());

        verify(userRepository).save(user);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistingUser() {

        UpdateUserRequest request = new UpdateUserRequest();

        request.setFirstName("Johnny");
        request.setLastName("Smith");
        request.setEmail("johnny@test.com");
        request.setContactNumber("0779999999");

        when(userRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> userService.updateUser(99L, request)
        );

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void shouldDisplayUserProfile() {

        User user = createUser();

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        UserProfileResponse response =
                userService.getUserProfile(1L);

        assertEquals(1L, response.getId());
        assertEquals("John", response.getFirstName());
        assertEquals("Doe", response.getLastName());
        assertEquals("john123", response.getUsername());
        assertEquals("john@test.com", response.getEmail());
        assertEquals("0771234567", response.getContactNumber());
        assertEquals(Role.STUDENT, response.getRole());
        assertEquals(
                UserStatus.PENDING_VERIFICATION,
                response.getAccountStatus()
        );
    }

    @Test
    void shouldThrowExceptionWhenDisplayingNonExistingUser() {

        when(userRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> userService.getUserProfile(99L)
        );
    }

    @Test
    void shouldLoginWithEmail() {

        User user = createUser();

        LoginRequest request = new LoginRequest();
        request.setEmailOrUsername("john@test.com");
        request.setPassword("password123");

        when(userRepository.findByEmail("john@test.com"))
                .thenReturn(Optional.of(user));

        LoginResponse response = userService.login(request);

        assertEquals("Login successful", response.getMessage());
    }

    @Test
    void shouldLoginWithUsername() {

        User user = createUser();

        LoginRequest request = new LoginRequest();
        request.setEmailOrUsername("john123");
        request.setPassword("password123");

        when(userRepository.findByEmail("john123"))
                .thenReturn(Optional.empty());

        when(userRepository.findByUsername("john123"))
                .thenReturn(Optional.of(user));

        LoginResponse response = userService.login(request);

        assertEquals("Login successful", response.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenPasswordIsWrong() {

        User user = createUser();

        LoginRequest request = new LoginRequest();
        request.setEmailOrUsername("john@test.com");
        request.setPassword("wrongpassword");

        when(userRepository.findByEmail("john@test.com"))
                .thenReturn(Optional.of(user));

        assertThrows(
                InvalidCredentialsException.class,
                () -> userService.login(request)
        );
    }

    @Test
    void shouldThrowExceptionWhenUserDoesNotExist() {

        LoginRequest request = new LoginRequest();
        request.setEmailOrUsername("unknown@test.com");
        request.setPassword("password123");

        when(userRepository.findByEmail("unknown@test.com"))
                .thenReturn(Optional.empty());

        when(userRepository.findByUsername("unknown@test.com"))
                .thenReturn(Optional.empty());

        assertThrows(
                InvalidCredentialsException.class,
                () -> userService.login(request)
        );
    }
}