package com.todo.service;

import com.todo.model.User;
import com.todo.model.Workspace;
import com.todo.server.ServerApplication;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
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

    @Test
    @Transactional
    void getUserByEmail() {
        User user = userService.createUser("Test User", "email@das.com", "password");
        assertNotNull(userService.getUserByEmail(user.getEmail()));
    }

    @Test
    void createUser() {
        String name = "Test User";
        String email = "user@email.com";
        String password = "password";
        assertDoesNotThrow(() ->userService.createUser(name, email, password));
    }

    @Test
    void createUserNoEmail(){
        String name = "Test User";
        String email = "";
        String password = "password";
        assertThrows(IllegalArgumentException.class, () -> userService.createUser(name, email, password));
    }

    @Test
    void createUserNoName() {
        String name = "";
        String email = "user@email.com";
        String password = "password";
        assertThrows(IllegalArgumentException.class, () -> userService.createUser(name, email, password));
    }

    @Test
    void createUserIllegalEmail() {
        String name = "Test User";
        String email = "useremail.com";
        String password = "password";
        assertThrows(IllegalArgumentException.class, () -> userService.createUser(name, email, password));
    }

    @Test
    void addWorkspaceToUser() {
        User user = userService.createUser("Test User1", "user@email.com", "password");
        Workspace workspace = userService.createWorkspace("Test Workspace", user);
        assertDoesNotThrow(() -> userService.addWorkspaceToUser(user, workspace));
    }

    @Test
    void createWorkspace() {
        User user = userService.createUser("Test User", "user@email.com", "password");
        String workspaceName = "Test Workspace";
        assertDoesNotThrow(() -> userService.createWorkspace(workspaceName, user));
    }

    @Test
    void deleteUser() {
        User user = userService.createUser("Delete User", "delete@me.com", "password");
        assertDoesNotThrow(() -> userService.deleteUser(user));
        User deletedUser = userService.getUserById(user.getId());
        assertNull(deletedUser);
    }

    @Test
    void removeWorkspaceFromUser() {
        User user = userService.createUser("Workspace User", "workspace@email.com", "password");
        Workspace workspace = userService.createWorkspace("Test Workspace", user);
        assertDoesNotThrow(() -> userService.addWorkspaceToUser(user, workspace));
        assertDoesNotThrow(() -> userService.removeWorkspaceFromUser(user, workspace));
    }

    @Test
    void updateUser(){
        User user = userService.createUser("Test User", "update@email.com", "password");
        user.setName("Test User 2");
        assertDoesNotThrow(() -> userService.updateUserDetails(user));
        User updatedUser = userService.getUserById(user.getId());
        assertEquals("Test User 2", updatedUser.getName());
    }

    @Test
    void getUserById(){
        User user = userService.createUser("Test User", "id@email.com", "password");
        assertNotNull(userService.getUserById(user.getId()));
    }
}