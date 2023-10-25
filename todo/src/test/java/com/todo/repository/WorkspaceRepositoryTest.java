package com.todo.repository;

import com.todo.model.Workspace;
import com.todo.server.ServerApplication;
import jakarta.transaction.Transactional;
import org.hibernate.jdbc.Work;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(classes = ServerApplication.class)
@Transactional
public class WorkspaceRepositoryTest {

    @Autowired
    private WorkspaceRepository workspaceRepository;

    @BeforeEach
    void setUp() {
        Workspace workspace = new Workspace();
        workspace.setName("Test Workspace");
        workspaceRepository.save(workspace);
    }
}
