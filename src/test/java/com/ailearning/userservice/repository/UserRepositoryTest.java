package com.ailearning.userservice.repository;

import com.ailearning.userservice.entity.User;
import com.ailearning.userservice.enums.Role;
import com.ailearning.userservice.enums.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User createUser() {

        User user = new User();

        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUsername("john123");
        user.setEmail("john@test.com");
        user.setPassword("password123");
        user.setRole(Role.STUDENT);
        user.setAccountStatus(UserStatus.PENDING_VERIFICATION);

        return user;
    }

    @Test
    void shouldSaveUser() {

        User saved = userRepository.save(createUser());

        assertNotNull(saved.getId());
    }

    @Test
    void shouldFindUserByEmail() {

        userRepository.save(createUser());

        User user = userRepository.findByEmail("john@test.com")
                .orElse(null);

        assertNotNull(user);
    }

    @Test
    void shouldReturnTrueWhenEmailExists() {

        userRepository.save(createUser());

        boolean exists = userRepository.existsByEmail("john@test.com");

        assertTrue(exists);
    }

    @Test
    void shouldReturnTrueWhenUsernameExists() {

        userRepository.save(createUser());

        boolean exists = userRepository.existsByUsername("john123");

        assertTrue(exists);
    }

    @Test
    void shouldDeleteUser() {

        User saved = userRepository.save(createUser());

        userRepository.delete(saved);

        assertFalse(
                userRepository.findById(saved.getId()).isPresent()
        );
    }

}

