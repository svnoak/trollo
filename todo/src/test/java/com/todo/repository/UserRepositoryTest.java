package com.todo.repository;

import com.todo.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void createUser() {
        User user = new User();
        user.setName("user");
        user.setEmail("user@email.com");

        assertDoesNotThrow(() -> userRepository.save(user));
    }

    @Test
    void findUserByEmail() {
        User user = new User();
        user.setName("user");
        user.setEmail("user@email.com");
        userRepository.save(user);

        assertEquals(user, useruserRepository.findByEmail(user.getEmail()));
    }
}
