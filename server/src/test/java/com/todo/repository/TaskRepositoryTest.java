package com.todo.repository;

import com.todo.model.Lane;
import com.todo.model.Task;
import com.todo.model.Workspace;
import com.todo.server.ServerApplication;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ActiveProfiles("test")
@SpringBootTest(classes = ServerApplication.class)
@Transactional
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private LaneRepository laneRepository;

    @Autowired
    private WorkspaceRepository workspaceRepository;
    @Test
    void testCreateTask(){
        Workspace workspace = new Workspace();
        workspaceRepository.save(workspace);
        Lane lane = new Lane();
        lane.setWorkspace(workspace);
        laneRepository.save(lane);
        Task task = new Task();
        task.setLane(lane);
        assertDoesNotThrow(() -> taskRepository.save(task));
    }

    @Test
    void testGetTaskById(){
        Workspace workspace = new Workspace();
        workspaceRepository.save(workspace);
        Lane lane = new Lane();
        lane.setWorkspace(workspace);
        laneRepository.save(lane);
        Task task = new Task();
        task.setLane(lane);
        taskRepository.save(task);
        assertDoesNotThrow(() -> taskRepository.findById(task.getId()));
    }

    @Test
    void testDeleteTask(){
        Workspace workspace = new Workspace();
        workspaceRepository.save(workspace);
        Lane lane = new Lane();
        lane.setWorkspace(workspace);
        laneRepository.save(lane);
        Task task = new Task();
        task.setLane(lane);
        taskRepository.save(task);
        assertDoesNotThrow(() -> taskRepository.delete(task));
    }

}
