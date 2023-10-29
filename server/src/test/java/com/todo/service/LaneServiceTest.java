package com.todo.service;

import com.todo.dto.response.TaskDTO;
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
    private TaskService taskService;
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
        assertDoesNotThrow(() -> workspaceService.createLane("Test Lane", workspace.getId()));
    }

    @Test
    void deleteLane() {
        Lane lane = workspaceService.createLane("Test Lane", workspace.getId());
        assertDoesNotThrow(() -> workspaceService.deleteLane(lane));
        assertEquals(0, workspace.getLanes().size());
    }

    @Test
    void getLaneById() {
        Lane lane = workspaceService.createLane("Test Lane", workspace.getId());
        Lane foundLane = laneService.getLaneById(lane.getId());
        assertNotNull(foundLane);
    }

    @Test
    void createTask(){
        Lane lane = workspaceService.createLane("Test Lane", workspace.getId());
        assertDoesNotThrow(() -> laneService.createTask("Test Task","Test Description", lane.getId()));
    }

    @Test
    void deleteTask(){
        Lane lane = workspaceService.createLane("Test Lane", workspace.getId());
        TaskDTO taskDTO = laneService.createTask("Test Task","Test Description", lane.getId());
        assertDoesNotThrow(() -> laneService.deleteTask(taskDTO.getId()));
        assertEquals(0, lane.getTasks().size());
    }

    @Test
    void updateTaskPosition() {
        Lane lane = workspaceService.createLane("Test Lane", workspace.getId());
        TaskDTO task1 = laneService.createTask("Test Task 1", "Test Description", lane.getId());
        TaskDTO task2 = laneService.createTask("Test Task 2", "Test Description", lane.getId());
        TaskDTO task3 = laneService.createTask("Test Task 3", "Test Description", lane.getId());
        assertEquals(0, task1.getPosition());
        assertEquals(1, task2.getPosition());
        assertEquals(2, task3.getPosition());
        assertDoesNotThrow(() -> laneService.moveTask(task1.getId(), lane.getId(), lane.getId(), 2));

        Task task1Updated = taskService.getTaskById(task1.getId());
        Task task2Updated = taskService.getTaskById(task2.getId());
        Task task3Updated = taskService.getTaskById(task3.getId());

        assertEquals(2, task1Updated.getPosition());
        assertEquals(0, task2Updated.getPosition());
        assertEquals(1, task3Updated.getPosition());
    }

}