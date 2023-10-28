package com.todo.service;

import com.todo.model.Lane;
import com.todo.model.Task;
import com.todo.model.Workspace;
import com.todo.server.ServerApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ActiveProfiles("test")
@SpringBootTest(classes = ServerApplication.class)
public class TaskServiceTest {

    @Autowired
    private TaskService taskService;

    @Autowired
    private LaneService laneService;

    @Autowired
    private WorkspaceService workspaceService;

    private Workspace workspace;
    private Lane lane;
    @BeforeEach
    void setUp(){
        workspace = workspaceService.createWorkspace("Test Workspace");
        lane = workspaceService.createLane("Lane",workspace);
    }
    @Test
    void testCreateTask() {
        assertDoesNotThrow(() -> laneService.createTask("Test Task", "Test Description",lane.getTasks().size(), lane));
    }

    @Test
    void testUpdateTask() {
        Task task = laneService.createTask("Test Task", "Test Description", lane.getTasks().size(), lane);
        task.setName("Test Task 2");
        assertDoesNotThrow(() -> taskService.updateTask(task));
    }

    @Test
    void testDeleteTask() {
        Task task = laneService.createTask("Test Task", "Test Description", lane.getTasks().size(), lane);
        assertDoesNotThrow(() -> laneService.deleteTask(task));
    }

    @Test
    void testGetTaskById() {
        Task task = laneService.createTask("Test Task", "Test Description",lane.getTasks().size(), lane);
        assertDoesNotThrow(() -> taskService.getTaskById(task.getId()));
    }
}
