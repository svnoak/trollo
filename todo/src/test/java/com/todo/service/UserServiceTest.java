package com.todo.service;

import com.todo.model.User;
import com.todo.model.Workspace;
import com.todo.server.ServerApplication;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(classes = ServerApplication.class)
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private WorkspaceService workspaceService;

    private User user;
    @BeforeEach
    void setUp(){
        String name = "Test User1";
        String email = "user1@email.com";
        user = userService.createUser(name, email);
    }

    @Test
    void createUser() {
        String name = "Test User";
        String email = "user@email.com";
        assertDoesNotThrow(() ->userService.createUser(name, email));
    }

    @Test
    void getUserByEmail() {
        assertNotNull(userService.getUserByEmail(user.getEmail()));
    }

    @Test
    void addWorkspaceToUser() {
        Workspace workspace = workspaceService.createWorkspace("Test Workspace", user);
        assertDoesNotThrow(() -> userService.addWorkspaceToUser(user, workspace));
    }

    @Test
    void deleteUser() {
        assertDoesNotThrow(() -> userService.deleteUser(user));
    }

    @Test
    void removeWorkspaceFromUser() {
        Workspace workspace = workspaceService.createWorkspace("Test Workspace", user);
        assertDoesNotThrow(() -> userService.addWorkspaceToUser(user, workspace));
        assertDoesNotThrow(() -> userService.removeWorkspaceFromUser(user, workspace));
    }

    @Test
    void updateUser(){
        user.setName("Test User 2");
        assertDoesNotThrow(() -> userService.updateUser(user));
    }

    @Test
    void getUserById(){
        assertNotNull(userService.getUserById(user.getId()));
    }
}