package com.todo.controller;

import com.todo.dto.response.TaskDTO;
import com.todo.model.Lane;
import com.todo.model.Task;
import com.todo.model.Workspace;
import com.todo.repository.LaneRepository;
import com.todo.repository.TaskRepository;
import com.todo.repository.WorkspaceRepository;
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

@SpringBootTest(classes = ServerApplication.class)
@AutoConfigureMockMvc(addFilters = false)
public class LaneControllerTest {

    private MockMvc mockMvc;
    private WorkspaceService workspaceService;
    private WorkspaceRepository workspaceRepository;
    private LaneService laneService;
    private LaneRepository laneRepository;
    private TaskService taskService;
    private TaskRepository taskRepository;

    private Workspace workspace;
    private Lane lane;
    private TaskDTO task;

    @Autowired
    public LaneControllerTest(MockMvc mockMvc, WorkspaceService workspaceService, LaneService laneService, TaskService taskService, WorkspaceRepository workspaceRepository, LaneRepository laneRepository, TaskRepository taskRepository) {
        this.mockMvc = mockMvc;
        this.workspaceService = workspaceService;
        this.laneService = laneService;
        this.taskService = taskService;
        this.workspaceRepository = workspaceRepository;
        this.laneRepository = laneRepository;
        this.taskRepository = taskRepository;
    }

    @BeforeEach
    void setup() {
        workspaceRepository.deleteAll();
        laneRepository.deleteAll();
        taskRepository.deleteAll();

        workspace = workspaceService.createWorkspace("Test Workspace");
        lane = workspaceService.createLane("Test Lane", workspace);
        task = laneService.createTask("Test Task", "Test Description", 0, lane);
    }

    @Test
    public void testGetLaneById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/lanes/" + lane.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetAllTasksByLaneId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/lanes/" + lane.getId() + "/tasks"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testDeleteLane() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/lanes/" + lane.getId()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testDeleteLaneNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/lanes/" + 1000))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testDeleteLaneBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/lanes/" + -1))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testUpdateLaneName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/lanes/" + lane.getId() + "/name")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Updated Name\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testUpdateLaneNameBadRequest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/lanes/" + lane.getId() + "/name")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testUpdateLaneNameNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/lanes/" + 1000 + "/name")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Updated Name\"}"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testUpdateLaneNameBadRequestId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/lanes/" + -1 + "/name")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Updated Name\"}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testCreateTask() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/lanes/" + lane.getId() + "/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Test Task\",\"description\":\"Test Description\",\"position\":0}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testCreateTaskBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/lanes/" + lane.getId() + "/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"\",\"description\":\"Test Description\",\"position\":0}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testCreateTaskNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/lanes/" + 1000 + "/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Test Task\",\"description\":\"Test Description\",\"position\":0}"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testCreateTaskBadRequestId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/lanes/" + -1 + "/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Test Task\",\"description\":\"Test Description\",\"position\":0}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testMoveTask() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/lanes/" + lane.getId() + "/tasks/" + task.getId() + "/move")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"position\":1}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

}
