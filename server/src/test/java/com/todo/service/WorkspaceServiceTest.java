package com.todo.service;

import com.todo.model.Lane;
import com.todo.model.Workspace;
import com.todo.server.ServerApplication;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@SpringBootTest(classes = ServerApplication.class)
public class WorkspaceServiceTest {

    private final WorkspaceService workspaceService;
    private final LaneService laneService;

    @Autowired
    public WorkspaceServiceTest(WorkspaceService workspaceService, LaneService laneService) {
        this.workspaceService = workspaceService;
        this.laneService = laneService;
    }

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
    void testMoveLane() {
        Workspace workspace = workspaceService.createWorkspace("Test Workspace");
        Lane lane1 = workspaceService.createLane("Test Lane 1", workspace.getId());
        Lane lane2 = workspaceService.createLane("Test Lane 2", workspace.getId());
        Lane lane3 = workspaceService.createLane("Test Lane 3", workspace.getId());
        assertEquals(0, lane1.getPosition());
        assertEquals(1, lane2.getPosition());
        assertEquals(2, lane3.getPosition());
        assertDoesNotThrow(() -> workspaceService.moveLane(lane1.getId(), 2));

        Lane updatedLane1 = laneService.getLaneById(lane1.getId());
        Lane updatedLane2 = laneService.getLaneById(lane2.getId());
        Lane updatedLane3 = laneService.getLaneById(lane3.getId());

        assertEquals(2, updatedLane1.getPosition());
        assertEquals(0, updatedLane2.getPosition());
        assertEquals(1, updatedLane3.getPosition());
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
