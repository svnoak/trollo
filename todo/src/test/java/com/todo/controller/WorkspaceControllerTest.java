package com.todo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todo.model.Workspace;
import com.todo.repository.WorkspaceRepository;
import com.todo.server.ServerApplication;
import com.todo.service.WorkspaceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@SpringBootTest(classes = ServerApplication.class)
@AutoConfigureMockMvc(addFilters = false)
public class WorkspaceControllerTest {

    private MockMvc mockMvc;
    private WorkspaceRepository workspaceRepository;

    @Autowired
    public WorkspaceControllerTest(WorkspaceRepository workspaceRepository, MockMvc mockMvc) {
        this.workspaceRepository = workspaceRepository;
        this.mockMvc = mockMvc;
    }

    private Workspace workspace;

    @BeforeEach
    void setup() {
        workspaceRepository.deleteAll();
        Workspace testWorkspace = new Workspace();
        testWorkspace.setName("Test Workspace");
        workspace = workspaceRepository.save(testWorkspace);
    }

    @Test
    public void testGetAllWorkspaces() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/workspaces"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetWorkspaceById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/workspaces/" + workspace.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testCreateWorkspace() throws Exception {
        Workspace workspace = new Workspace();
        workspace.setName("Test Workspace");

        ObjectMapper objectMapper = new ObjectMapper();
        String workspaceJson = objectMapper.writeValueAsString(workspace);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/workspaces")
                .contentType(MediaType.APPLICATION_JSON)
                .content(workspaceJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testDeleteWorkspace() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/workspaces/" + workspace.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
