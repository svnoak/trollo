package com.todo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todo.dto.response.WorkspaceDTO;
import com.todo.model.Lane;
import com.todo.model.Workspace;
import com.todo.repository.WorkspaceRepository;
import com.todo.server.ServerApplication;
import com.todo.service.WorkspaceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ServerApplication.class)
@AutoConfigureMockMvc(addFilters = false)
public class WorkspaceControllerTest {

    private final MockMvc mockMvc;
    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceService workspaceService;

    @Autowired
    public WorkspaceControllerTest(
            WorkspaceService workspaceService,
            WorkspaceRepository workspaceRepository,
            MockMvc mockMvc) {
        this.workspaceService = workspaceService;
        this.workspaceRepository = workspaceRepository;
        this.mockMvc = mockMvc;
    }

    private Workspace workspace;

    @BeforeEach
    void setup() {
        workspaceRepository.deleteAll();
        workspace = workspaceService.createWorkspace("Test workspace");
    }

    @Test
    public void testGetAllWorkspaces() throws Exception {
        List<WorkspaceDTO> workspaceDTOs = workspaceService.getAllWorkspaces();

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/workspaces"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(workspaceDTOs)));
    }

    @Test
    public void testGetWorkspaceById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/workspaces/" + workspace.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testCreateWorkspace() throws Exception {
        Workspace workspace = new Workspace();
        workspace.setName("Test Workspace");

        ObjectMapper objectMapper = new ObjectMapper();
        String workspaceJson = objectMapper.writeValueAsString(workspace);

        mockMvc.perform(post("/api/workspaces")
                .contentType(MediaType.APPLICATION_JSON)
                .content(workspaceJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testUpdateWorkspaceNoName() throws Exception {
        Workspace workspace = new Workspace();
        workspace.setName("");

        ObjectMapper objectMapper = new ObjectMapper();
        String workspaceJson = objectMapper.writeValueAsString(workspace);

        mockMvc.perform(post("/api/workspaces")
                .contentType(MediaType.APPLICATION_JSON)
                .content(workspaceJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteWorkspace() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/workspaces/" + workspace.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetAllLanesByWorkspaceId() throws Exception {
        List<Lane> lanes = workspace.getLanes();
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/workspaces/" + workspace.getId() + "/lanes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(lanes)));
    }

}
