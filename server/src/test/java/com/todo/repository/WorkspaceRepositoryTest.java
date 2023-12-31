package com.todo.repository;

import com.todo.model.Workspace;
import com.todo.server.ServerApplication;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(classes = ServerApplication.class)
@Transactional
class WorkspaceRepositoryTest {

    @Autowired
    private WorkspaceRepository workspaceRepository;

    @Test
    void testCreateWorkspace(){
        Workspace workspace = new Workspace();
        workspace.setName("Test Workspace");
        assertDoesNotThrow(() -> workspaceRepository.save(workspace));
    }

    @Test
    void testDeleteWorkspace(){
        Workspace workspace = new Workspace();
        workspace.setName("Test Workspace");
        workspaceRepository.save(workspace);
        assertDoesNotThrow(() -> workspaceRepository.delete(workspace));
    }

    @Test
    void testUpdateWorkspace(){
        Workspace workspace = new Workspace();
        workspace.setName("Test Workspace");
        workspaceRepository.save(workspace);
        workspace.setName("Test Workspace 2");
        assertDoesNotThrow(() -> workspaceRepository.save(workspace));
    }

    @Test
    void testFindWorkspaceById() {
        Workspace workspace = new Workspace();
        workspace.setName("Test Workspace");
        int workspaceId = workspaceRepository.save(workspace).getId();
        assertNotNull(workspaceRepository.findById(workspaceId));
    }
}