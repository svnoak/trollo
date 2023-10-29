package com.todo.repository;

import com.todo.model.Lane;
import com.todo.model.Workspace;
import com.todo.server.ServerApplication;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@SpringBootTest(classes = ServerApplication.class)
@Transactional
class LaneRepositoryTest {

    @Autowired
    private LaneRepository laneRepository;

    @Autowired
    private WorkspaceRepository workspaceRepository;

    @Test
    void testCreateLane(){
        Workspace workspace = new Workspace();
        workspaceRepository.save(workspace);

        Lane lane = new Lane();
        lane.setWorkspace(workspace);
        assertDoesNotThrow(() -> laneRepository.save(lane));
    }

    @Test
    void testCreateLaneWithoutWorkspace(){
        Lane lane = new Lane();
        assertThrows(Exception.class, () -> laneRepository.save(lane));
    }

    @Test
    void testGetLaneById(){
        Workspace workspace = new Workspace();
        workspaceRepository.save(workspace);

        Lane lane = new Lane();
        lane.setWorkspace(workspace);
        laneRepository.save(lane);
        assertDoesNotThrow(() -> laneRepository.findById(lane.getId()));
    }

    @Test
    void testDeleteLane(){
        Workspace workspace = new Workspace();
        workspaceRepository.save(workspace);

        Lane lane = new Lane();
        lane.setWorkspace(workspace);
        laneRepository.save(lane);
        assertDoesNotThrow(() -> laneRepository.delete(lane));
    }

}