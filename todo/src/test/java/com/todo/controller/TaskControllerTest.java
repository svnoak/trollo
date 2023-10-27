package com.todo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.todo.dto.CreateTaskRequest;
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
    public void testGetTaskByIdNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks/" + 1000))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testGetTaskByIdBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks/" + -1))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testCreateTask() throws Exception {

        int laneId = lane.getId();

        // Create a Task
        Task task = new Task();
        task.setName("New Task");
        task.setDescription("Task Description");
        task.setPosition(1);

        CreateTaskRequest createTaskRequest = new CreateTaskRequest(laneId, task);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestObjectJson = objectMapper.writeValueAsString(createTaskRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/tasks")
                        .content(requestObjectJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testCreateTaskNotFound () throws Exception {
        int laneId = 1000;
        // Create a Task
        Task task = new Task();
        task.setName("New Task");
        task.setDescription("Task Description");
        task.setPosition(1);

        CreateTaskRequest createTaskRequest = new CreateTaskRequest(laneId, task);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestObjectJson = objectMapper.writeValueAsString(createTaskRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/tasks")
                        .content(requestObjectJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testCreateTaskBadRequestLaneId() throws Exception {
        int laneId = -1;
        // Create a Task
        Task task = new Task();
        task.setName("New Task");
        task.setDescription("Task Description");
        task.setPosition(1);

        CreateTaskRequest createTaskRequest = new CreateTaskRequest(laneId, task);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestObjectJson = objectMapper.writeValueAsString(createTaskRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/tasks")
                        .content(requestObjectJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testCreateTaskBadRequestTaskNameEmpty() throws Exception {
        int laneId = lane.getId();
        Task task = new Task();
        CreateTaskRequest createTaskRequest = new CreateTaskRequest(laneId, task);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestObjectJson = objectMapper.writeValueAsString(createTaskRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/tasks")
                        .content(requestObjectJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testUpdateTask() throws Exception {
        task.setName("Updated Task Name");
        ObjectMapper objectMapper = new ObjectMapper();
        String updatedTaskJson = objectMapper.writeValueAsString(task);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/tasks/" + task.getId())
                        .content(updatedTaskJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testUpdateTaskPosition() throws Exception {
        int newPosition = 1;
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/tasks/" + task.getId() + "/move/" + newPosition)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testUpdateTaskPositionBadRequest() throws Exception {
        int newPosition = 1;
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/tasks/" + -1 + "/" + newPosition)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testUpdateTaskPositionNotFound() throws Exception {
        int newPosition = 1;
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/tasks/" + 1000 + "/" + newPosition)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testUpdateTaskBadRequest() throws Exception {
        task.setName("");
        ObjectMapper objectMapper = new ObjectMapper();
        String updatedTaskJson = objectMapper.writeValueAsString(task);
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/tasks/" + task.getId())
                        .content(updatedTaskJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
