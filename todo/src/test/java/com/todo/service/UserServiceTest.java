package com.todo.service;

import com.todo.model.User;
import com.todo.model.Workspace;
import com.todo.server.ServerApplication;
import jakarta.transaction.Transactional;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(classes = ServerApplication.class)
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private WorkspaceService workspaceService;

    @Test
    void getUserByEmail() {
        User user = userService.createUser("Test User", "email@me.com");
        assertNotNull(userService.getUserByEmail(user.getEmail()));
    }

    @Test
    void createUser() {
        String name = "Test User";
        String email = "user@email.com";
        assertDoesNotThrow(() ->userService.createUser(name, email));
    }

    @Test
    void createUserNoEmail(){
        String name = "Test User";
        String email = "";
        assertThrows(IllegalArgumentException.class, () -> userService.createUser(name, email));
    }

    @Test
    void createUserNoName() {
        String name = "";
        String email = "user@email.com";
        assertThrows(IllegalArgumentException.class, () -> userService.createUser(name, email));
    }

    @Test
    void createUserIllegalEmail() {
        String name = "Test User";
        String email = "useremail.com";
        assertThrows(IllegalArgumentException.class, () -> userService.createUser(name, email));
    }

    @Test
    void addWorkspaceToUser() {
        User user = userService.createUser("Test User1", "user@email.com");
        Workspace workspace = workspaceService.createWorkspace("Test Workspace", user);
        assertDoesNotThrow(() -> userService.addWorkspaceToUser(user, workspace));
    }

    @Test
    void deleteUser() {
        User user = userService.createUser("Delete User", "delete@me.com");
        assertDoesNotThrow(() -> userService.deleteUser(user));
        User deletedUser = userService.getUserById(user.getId());
        assertNull(deletedUser);
    }

    @Test
    void removeWorkspaceFromUser() {
        User user = userService.createUser("Workspace User", "workspace@email.com");
        Workspace workspace = workspaceService.createWorkspace("Test Workspace", user);
        assertDoesNotThrow(() -> userService.addWorkspaceToUser(user, workspace));
        assertDoesNotThrow(() -> userService.removeWorkspaceFromUser(user, workspace));
    }

    @Test
    void updateUser(){
        User user = userService.createUser("Test User", "update@email.com");
        user.setName("Test User 2");
        assertDoesNotThrow(() -> userService.updateUser(user));
        User updatedUser = userService.getUserById(user.getId());
        assertEquals("Test User 2", updatedUser.getName());
    }

    @Test
    void getUserById(){
        User user = userService.createUser("Test User", "id@email.com");
        assertNotNull(userService.getUserById(user.getId()));
    }
}