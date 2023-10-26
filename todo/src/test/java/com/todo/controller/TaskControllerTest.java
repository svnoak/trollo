package com.todo.controller;

import com.todo.model.Lane;
import com.todo.model.Task;
import com.todo.model.Workspace;
import com.todo.repository.TaskRepository;
import com.todo.server.ServerApplication;
import com.todo.service.LaneService;
import com.todo.service.TaskService;
import com.todo.service.WorkspaceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(classes = ServerApplication.class)
@AutoConfigureMockMvc(addFilters = false)
public class TaskControllerTest {

    private MockMvc mockMvc;
    private WorkspaceService workspaceService;
    private LaneService laneService;
    private TaskService taskService;

    private Task task;
    private Lane lane;
    private Workspace workspace;

    @Autowired
    public TaskControllerTest(MockMvc mockMvc, WorkspaceService workspaceService, LaneService laneService, TaskService taskService) {
        this.mockMvc = mockMvc;
        this.workspaceService = workspaceService;
        this.laneService = laneService;
        this.taskService = taskService;
    }

    @BeforeEach
    void setup() {
        workspace = workspaceService.createWorkspace("Test Workspace");
        lane = workspaceService.createLane("Test Lane", workspace);
        task = laneService.createTask("Test Task", "Test Description", 0, lane);
    }

    @Test
    public void testGetAllTasks() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetTaskById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks/" + task.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testCreateTask() throws Exception {
        Task task = new Task();
        task.setName("New Task");
        task.setDescription("Task Description");
        task.setPosition(1);

        ObjectMapper objectMapper = new ObjectMapper();
        String taskJson = objectMapper.writeValueAsString(task);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/tasks/workspace/" + lane.getId() + "/create")
                        .content(taskJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }
}
