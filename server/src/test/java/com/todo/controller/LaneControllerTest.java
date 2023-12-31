package com.todo.controller;

import com.todo.model.Lane;
import com.todo.model.Workspace;
import com.todo.repository.LaneRepository;
import com.todo.repository.WorkspaceRepository;
import com.todo.server.ServerApplication;
import com.todo.service.LaneService;
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
    private Workspace workspace;
    private Lane lane;

    @Autowired
    public LaneControllerTest(MockMvc mockMvc, WorkspaceService workspaceService, LaneService laneService, WorkspaceRepository workspaceRepository, LaneRepository laneRepository) {
        this.mockMvc = mockMvc;
        this.workspaceService = workspaceService;
        this.laneService = laneService;
        this.workspaceRepository = workspaceRepository;
        this.laneRepository = laneRepository;
    }

    @BeforeEach
    void setup() {
        workspaceRepository.deleteAll();
        laneRepository.deleteAll();

        workspace = workspaceService.createWorkspace("Test Workspace");
        lane = workspaceService.createLane("Test Lane", workspace.getId());
        laneService.createTask("Test Task", "Test Description", lane.getId());
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
    public void testCreateLane() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/lanes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Test Lane 2\",\"workspaceId\":" + workspace.getId() + "}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testCreateLaneBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/lanes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Test Lane 2\",\"workspaceId\":" + -1 + "}"))
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
    public void testMoveLane() throws Exception {
        Lane lane1 = workspaceService.createLane("Test Lane 1", workspace.getId());
        workspaceService.createLane("Test Lane 2", workspace.getId());
        workspaceService.createLane("Test Lane 3", workspace.getId());

        int newPosition = 2;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/lanes/"+ lane1.getId() +"/move/" + newPosition)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testMoveLaneNotFound() throws Exception {
        int newPosition = 2;
        mockMvc.perform(MockMvcRequestBuilders.post("/api/lanes/"+ 1000 +"/move/" + newPosition)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testMoveLaneBadRequestId() throws Exception {
        int newPosition = 2;
        mockMvc.perform(MockMvcRequestBuilders.post("/api/lanes/"+ -1 +"/move/" + newPosition)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testMoveLaneBadRequestPosition() throws Exception {
        int newPosition = -1;
        mockMvc.perform(MockMvcRequestBuilders.post("/api/lanes/"+ lane.getId() +"/move/" + newPosition)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}
