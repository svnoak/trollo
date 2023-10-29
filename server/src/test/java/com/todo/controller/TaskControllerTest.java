package com.todo.controller;

import com.todo.dto.request.ChangeTaskDetails;
import com.todo.dto.request.CreateTaskRequest;
import com.todo.dto.response.TaskDTO;
import com.todo.model.Lane;
import com.todo.model.Task;
import com.todo.model.Workspace;
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

    private TaskDTO task;
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
        lane = workspaceService.createLane("Test Lane", workspace.getId());
        task = laneService.createTask("Test Task", "Test Description", lane.getId());
    }

    @Test
    public void testCreateTask() throws Exception {
        CreateTaskRequest createTaskRequest = new CreateTaskRequest(lane.getId(), "Test Task", "Test Description");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/tasks")
                        .content(new ObjectMapper().writeValueAsString(createTaskRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testCreateTaskNotFound() throws Exception {
        CreateTaskRequest createTaskRequest = new CreateTaskRequest(-1, "Test Task", "Test Description");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/tasks")
                        .content(new ObjectMapper().writeValueAsString(createTaskRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
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
    public void testUpdateTaskDetails() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        String updatedTaskJson = objectMapper.writeValueAsString(task);
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/tasks/" + task.getId())
                        .content(updatedTaskJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testUpdateTaskBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/tasks/" + task.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testDeleteTask() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/tasks/" + task.getId()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testDeleteTaskNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/tasks/" + 1000))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testDeleteTaskBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/tasks/" + -1))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}
