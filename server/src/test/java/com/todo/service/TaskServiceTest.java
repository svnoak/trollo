package com.todo.service;

import com.todo.dto.request.ChangeTaskDetails;
import com.todo.dto.response.TaskDTO;
import com.todo.model.Lane;
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
        lane = workspaceService.createLane("Lane",workspace.getId());
    }
    @Test
    void testCreateTask() {
        assertDoesNotThrow(() -> laneService.createTask("Test Task", "Test Description", lane.getId()));
    }

    @Test
    void testUpdateTask() {
        TaskDTO taskDTO = laneService.createTask("Test Task", "Test Description", lane.getId());
        ChangeTaskDetails changeTaskDetails = new ChangeTaskDetails("New Name", "");
        assertDoesNotThrow(() -> taskService.updateTaskDetails(taskDTO.getId(),changeTaskDetails ));
    }

    @Test
    void testDeleteTask() {
        TaskDTO task = laneService.createTask("Test Task", "Test Description", lane.getId());
        assertDoesNotThrow(() -> laneService.deleteTask(task.getId()));
    }

    @Test
    void testGetTaskById() {
        TaskDTO task = laneService.createTask("Test Task", "Test Description", lane.getId());
        assertDoesNotThrow(() -> taskService.getTaskById(task.getId()));
    }
}
