package com.todo.service;

import com.todo.model.Lane;
import com.todo.model.Task;
import com.todo.model.User;
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

    @Autowired
    private UserService userService;

    private User user;
    private Workspace workspace;
    private Lane lane;
    @BeforeEach
    void setUp(){
        user = userService.createUser("Test User", "e@mail.com");
        workspace = workspaceService.createWorkspace("Workspace",user);
        lane = laneService.createLane(workspace);
    }
    @Test
    void createTask() {
        assertDoesNotThrow(() -> taskService.createTask("Test Task", "Test Description",lane, lane.getTasks().size()));
    }

    @Test
    void updateTask() {
        Task task = taskService.createTask("Test Task", "Test Description",lane, lane.getTasks().size());
        task.setName("Test Task 2");
        assertDoesNotThrow(() -> taskService.updateTask(task));
    }

    @Test
    void deleteTask() {
        Task task = taskService.createTask("Test Task", "Test Description",lane, lane.getTasks().size());
        assertDoesNotThrow(() -> taskService.deleteTask(task));
    }

    @Test
    void getTaskById() {
        Task task = taskService.createTask("Test Task", "Test Description",lane, lane.getTasks().size());
        assertDoesNotThrow(() -> taskService.getTaskById(task.getId()));
    }
}
