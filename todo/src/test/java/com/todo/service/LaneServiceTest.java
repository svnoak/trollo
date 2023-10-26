package com.todo.service;

import com.todo.model.Lane;
import com.todo.model.Task;
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
class LaneServiceTest {

    @Autowired
    private LaneService laneService;
    @Autowired
    private WorkspaceService workspaceService;

    private Workspace workspace;
    @BeforeEach
    void setUp(){
        workspace = workspaceService.createWorkspace("Test Workspace");
    }

    @Test
    void createLane() {
        assertDoesNotThrow(() -> workspaceService.createLane("Test Lane", workspace));
    }

    @Test
    void deleteLane() {
        Lane lane = workspaceService.createLane("Test Lane", workspace);
        assertDoesNotThrow(() -> workspaceService.deleteLane(lane));
        assertEquals(0, workspace.getLanes().size());
    }

    @Test
    void getLaneById() {
        Lane lane = workspaceService.createLane("Test Lane", workspace);
        assertDoesNotThrow(() -> laneService.getLaneById(lane.getId()));
    }

    @Test
    void createTask(){
        Lane lane = workspaceService.createLane("Test Lane", workspace);
        assertDoesNotThrow(() -> laneService.createTask("Test Task","Test Description", 0, lane));
    }

    @Test
    void deleteTask(){
        Lane lane = workspaceService.createLane("Test Lane", workspace);
        Task task = laneService.createTask("Test Task","Test Description", 0, lane);
        assertDoesNotThrow(() -> laneService.deleteTask(task));
        assertEquals(0, lane.getTasks().size());
    }

    @Test
    void updateTaskPosition() {
        Lane lane = workspaceService.createLane("Test Lane", workspace);
        Task task1 = laneService.createTask("Test Task 1", "Test Description", 0, lane);
        Task task2 = laneService.createTask("Test Task 2", "Test Description", 1, lane);
        Task task3 = laneService.createTask("Test Task 3", "Test Description", 2, lane);
        assertEquals(0, task1.getPosition());
        assertEquals(1, task2.getPosition());
        assertEquals(2, task3.getPosition());
        assertDoesNotThrow(() -> laneService.updateTaskPosition(task1, 2));
        assertEquals(2, task1.getPosition());
        assertEquals(0, task2.getPosition());
        assertEquals(1, task3.getPosition());
    }

    @Test
    void getLaneByWorkspaceIdAndPosition() {
        Lane lane = workspaceService.createLane("Test Lane", workspace);
        Lane foundLane = laneService.getLaneByWorkspaceAndPosition(workspace.getId(), lane.getPosition());
        assertNotNull(foundLane);
    }

}