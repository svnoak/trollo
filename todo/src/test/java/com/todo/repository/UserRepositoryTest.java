package com.todo.repository;

import com.todo.model.User;
import com.todo.model.Workspace;
import com.todo.server.ServerApplication;
import jakarta.transaction.Transactional;
import org.aspectj.lang.annotation.After;
import org.hibernate.jdbc.Work;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(classes = ServerApplication.class)
@Transactional
public class UserRepositoryTest {

    private int userId1;
    private int userId2;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        User user1 = new User();
        user1.setName("Test User 1");
        user1.setEmail("user@email.com");
        user1.setPassword("password");
        assertNotNull(user1);
        userId1 = userRepository.save(user1).getId();

        User user2 = new User();
        user2.setName("Test User 2");
        user2.setEmail("user1@email.com");
        assertNotNull(user2);
        userId2 = userRepository.save(user2).getId();
    }

    @AfterEach
    void cleanUp(){
        userRepository.deleteAll();
    }

    @Test
    void createUser() {
        User user = new User();
        user.setName("Test User");
        user.setEmail("user2@email.com");
        user.setPassword("password");
        assertDoesNotThrow(() -> userRepository.save(user));
    }

    @Test
    void findUserById() {
        User user = userRepository.findById(userId1).orElse(null);
        assertNotNull(user);
    }

    @Test
    void findUserByEmail() {
        User user = userRepository.findByEmail("user1@email.com");
        assertNotNull(user);
    }

    @Test
    void deleteUser() {
        User user = userRepository.findById(userId1).orElse(null);
        assertNotNull(user);
        userRepository.delete(user);
        assertNull(userRepository.findById(userId1).orElse(null));
    }

    @Test
    void updateUser(){
        User user = userRepository.findById(userId1).orElse(null);
        assertNotNull(user);
        user.setName("Updated User");
        userRepository.save(user);
        assertEquals("Updated User", Objects.requireNonNull(userRepository.findById(userId1).orElse(null)).getName());
    }

}
