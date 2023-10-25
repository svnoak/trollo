package com.todo.service;

import com.todo.model.Lane;
import com.todo.model.User;
import com.todo.model.Workspace;
import com.todo.server.ServerApplication;
import org.hibernate.jdbc.Work;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@SpringBootTest(classes = ServerApplication.class)
public class WorkspaceServiceTest {

    @Autowired
    private WorkspaceService workspaceService;

    @Autowired
    private UserService userService;

    @Autowired
    private LaneService laneService;
    @Test
    void createWorkspace() {
        User user = userService.createUser("Test User", "em@ail.com");
        assertDoesNotThrow(() -> workspaceService.createWorkspace("Test Workspace", user));
    }

    @Test
    void createWorkspaceWithoutUser(){
        assertThrows(IllegalArgumentException.class, () -> workspaceService.createWorkspace("Test Workspace", null));
    }

    @Test
    void deleteWorkspace() {
        User user = userService.createUser("Test User", "em@ail.com");
        Workspace workspace = workspaceService.createWorkspace("Test Workspace", user);
        assertDoesNotThrow(() -> workspaceService.deleteWorkspace(workspace));
    }

    @Test
    void getWorkspaceById() {
        User user = userService.createUser("Test User", "em@ail.com");
        int workspaceId = workspaceService.createWorkspace("Test Workspace", user).getId();
        assertDoesNotThrow(() -> workspaceService.getWorkspaceById(workspaceId));
    }

    @Test
    void updateWorkspace() {
        User user = userService.createUser("Test User", "ema@il.com");
        Workspace workspace = workspaceService.createWorkspace("Test Workspace", user);
        workspace.setName("Test Workspace 2");
        assertDoesNotThrow(() -> workspaceService.updateWorkspace(workspace));
    }

    @Test
    void addLaneToWorkspace() {
        User user = userService.createUser("Test User", "email@something.com");
        Workspace workspace = workspaceService.createWorkspace("Test Workspace", user);
        Lane lane = laneService.createLane(workspace);
        assertDoesNotThrow(() -> workspaceService.addLaneToWorkspace(workspace, lane));
    }

    @Test
    void removeLaneFromWorkspace() {
        User user = userService.createUser("Test User", "email@something.com");
        Workspace workspace = workspaceService.createWorkspace("Test Workspace", user);
        Lane lane = laneService.createLane(workspace);
        assertDoesNotThrow(() -> workspaceService.addLaneToWorkspace(workspace, lane));
        assertDoesNotThrow(() -> workspaceService.removeLaneFromWorkspace(workspace, lane));
    }

    @Test
    void addUserToWorkspace() {
        User user = userService.createUser("Test User", "email@something.com");
        Workspace workspace = workspaceService.createWorkspace("Test Workspace", user);
        User newUser = userService.createUser("New User", "new@email.com");
        assertDoesNotThrow(() -> workspaceService.addUserToWorkspace(workspace, newUser));
    }
}
