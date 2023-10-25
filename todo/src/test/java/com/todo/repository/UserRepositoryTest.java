package com.todo.repository;

import com.todo.model.User;
import com.todo.model.Workspace;
import com.todo.server.ServerApplication;
import jakarta.transaction.Transactional;
import org.hibernate.jdbc.Work;
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
        assertNotNull(user1);
        userId1 = userRepository.save(user1).getId();

        User user2 = new User();
        user2.setName("Test User 2");
        user2.setEmail("user1@email.com");
        assertNotNull(user2);
        userId2 = userRepository.save(user2).getId();
    }

    @Test
    void testCreateUser() {
        User user = new User();
        user.setName("Test User");
        user.setEmail("user2@email.com");
        assertDoesNotThrow(() -> userRepository.save(user));
    }

    @Test
    void testFindUserById() {
        User user = userRepository.findById(userId1).orElse(null);
        assertNotNull(user);
    }

    @Test
    void testFindUserByEmail() {
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

    @Test
    void findAllUsers(){
        List<User> users = userRepository.findAll();
        assertEquals(2, users.size());
    }

}
