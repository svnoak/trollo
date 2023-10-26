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
        mockMvc.perform(MockMvcRequestBuilders.get("/api/workspaces")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetWorkspaceById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/workspaces/" + workspace.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testCreateWorkspace() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/workspaces")
                        .content("{\"name\": \"Test Workspace\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testUpdateWorkspace() throws Exception {
        workspace.setName("Updated Workspace Name");

        ObjectMapper objectMapper = new ObjectMapper();
        String updatedWorkspaceJson = objectMapper.writeValueAsString(workspace);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/workspaces" + workspace.getId())
                        .content(updatedWorkspaceJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDeleteWorkspace() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/workspaces/" + workspace.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
