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

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
    void getWorkspaceById() {
        User user = userService.createUser("Test User", "em@ail.com", "password");
        int workspaceId = userService.createWorkspace("Test Workspace", user).getId();
        assertDoesNotThrow(() -> workspaceService.getWorkspaceById(workspaceId));
    }

    @Test
    void updateWorkspaceName() {
        User user = userService.createUser("Test User", "ema@il.com", "password");
        Workspace workspace = userService.createWorkspace("Test Workspace", user);
        assertDoesNotThrow(() -> workspaceService.updateName(workspace, "New name"));
    }

    @Test
    void addWorkspaceToUser() {
        User user = userService.createUser("Test User", "email@something.com", "password");
        Workspace workspace = userService.createWorkspace("Test Workspace", user);
        User newUser = userService.createUser("New User", "new@email.com", "password");
        assertDoesNotThrow(() -> userService.addWorkspaceToUser(newUser, workspace));
    }

    @Test
    void updateLanePosition() {
        User user = userService.createUser("Test User", "email@something.com", "password");
        Workspace workspace = userService.createWorkspace("Test Workspace", user);
        Lane lane1 = workspaceService.createLane("Test Lane 1", workspace);
        Lane lane2 = workspaceService.createLane("Test Lane 2", workspace);
        Lane lane3 = workspaceService.createLane("Test Lane 3", workspace);
        assertEquals(0, lane1.getPosition());
        assertEquals(1, lane2.getPosition());
        assertEquals(2, lane3.getPosition());
        assertDoesNotThrow(() -> workspaceService.updateLanePosition(lane1, 2));
        assertEquals(2, lane1.getPosition());
        assertEquals(0, lane2.getPosition());
        assertEquals(1, lane3.getPosition());
    }
}
