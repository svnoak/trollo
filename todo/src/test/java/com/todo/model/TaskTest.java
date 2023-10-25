package com.todo.model;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
class TaskTest {

    @Test
    void getId() {
        Task task = new Task();
        assertNotNull(task);
        ReflectionTestUtils.setField(task, "id", 1);
        assertEquals(1, task.getId());
    }

    @Test
    void getLaneId() {
        Task task = new Task();
        assertNotNull(task);

        Workspace workspace = new Workspace();
        workspace.setName("workspace");
        ReflectionTestUtils.setField(workspace, "id", 1);

        Lane lane = new Lane();
        ReflectionTestUtils.setField(lane, "id", 1);
        lane.setLaneOrder(1);

        workspace.setLanes(List.of(lane));

        lane.setTasks(List.of(task));
        task.setLane(lane);

        assertEquals(1, task.getLaneId());
    }

    @Test
    void getTaskOrder() {
        Task task = new Task();
        assertNotNull(task);
        task.setTaskOrder(1);
        assertEquals(1, task.getTaskOrder());
    }

    @Test
    void getName() {
        Task task = new Task();
        assertNotNull(task);
        task.setName("task");
        assertEquals("task", task.getName());
    }

    @Test
    void getDescription() {
        Task task = new Task();
        assertNotNull(task);
        task.setDescription("description");
        assertEquals("description", task.getDescription());
    }

    @Test
    void setTaskOrder() {
        Task task = new Task();
        assertNotNull(task);
        assertDoesNotThrow(() -> task.setTaskOrder(1));
    }

    @Test
    void setName() {
        Task task = new Task();
        assertNotNull(task);
        assertDoesNotThrow(() -> task.setName("name"));
    }

    @Test
    void setDescription() {
        Task task = new Task();
        assertNotNull(task);
        assertDoesNotThrow(() -> task.setDescription("description"));
    }

}