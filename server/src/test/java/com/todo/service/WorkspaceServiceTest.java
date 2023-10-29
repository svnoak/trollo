package com.todo.service;

import com.todo.model.Lane;
import com.todo.model.Workspace;
import com.todo.server.ServerApplication;
import org.hibernate.ObjectNotFoundException;
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
    private LaneService laneService;

    @Test
    void testGetWorkspaceById() {
        int workspaceId = workspaceService.createWorkspace("Test Workspace").getId();
        assertDoesNotThrow(() -> workspaceService.getWorkspaceById(workspaceId));
    }

    @Test
    void testUpdateWorkspace() {
        Workspace workspace = workspaceService.createWorkspace("Test Workspace");
        assertDoesNotThrow(() -> workspaceService.update(workspace));
    }

    @Test
    void testMoveLan() {
        Workspace workspace = workspaceService.createWorkspace("Test Workspace");
        Lane lane1 = workspaceService.createLane("Test Lane 1", workspace.getId());
        Lane lane2 = workspaceService.createLane("Test Lane 2", workspace.getId());
        Lane lane3 = workspaceService.createLane("Test Lane 3", workspace.getId());
        assertEquals(0, lane1.getPosition());
        assertEquals(1, lane2.getPosition());
        assertEquals(2, lane3.getPosition());
        assertDoesNotThrow(() -> workspaceService.moveLane(lane1.getId(), 2));
        assertEquals(2, lane1.getPosition());
        assertEquals(0, lane2.getPosition());
        assertEquals(1, lane3.getPosition());
    }

    @Test
    void testDeleteWorkspace() {
        Workspace workspace = workspaceService.createWorkspace("Test Workspace");
        assertDoesNotThrow(() -> workspaceService.deleteWorkspace(workspace.getId()));
    }

    @Test
    void testDeleteWorkspaceNotFound() {
        assertThrows(ObjectNotFoundException.class, () -> workspaceService.deleteWorkspace(-1));
    }
}
