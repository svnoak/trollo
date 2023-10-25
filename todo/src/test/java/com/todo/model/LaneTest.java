package com.todo.model;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
class LaneTest {

    @Test
    void getId() {
        Lane lane = new Lane();
        assertNotNull(lane);
        ReflectionTestUtils.setField(lane, "id", 1);
        assertEquals(1, lane.getId());
    }

    @Test
    void getWorkspaceId() {
        Lane lane = new Lane();
        assertNotNull(lane);

        Workspace workspace = new Workspace();
        workspace.setName("workspace");
        ReflectionTestUtils.setField(workspace, "id", 1);

        lane.setWorkspace(workspace);

        assertEquals(1, lane.getWorkspace().getId());
    }

    @Test
    void getLaneOrder() {
        Lane lane = new Lane();
        assertNotNull(lane);
        lane.setPosition(1);
        assertEquals(1, lane.getPosition());
    }

    @Test
    void getTasks() {
        Lane lane = new Lane();
        assertNotNull(lane);

        Task task = new Task();
        task.setName("task");

        lane.setTasks(List.of(task));
        assertEquals(1, lane.getTasks().size());
    }

    @Test
    void setLaneOrder() {
        Lane lane = new Lane();
        assertNotNull(lane);
        assertDoesNotThrow(() -> lane.setPosition(1));
    }

    @Test
    void setLaneOrderNegative() {
        Lane lane = new Lane();
        assertNotNull(lane);
        assertThrows(IllegalArgumentException.class, () -> lane.setPosition(-1));
    }

    @Test
    void setTasks() {
        Lane lane = new Lane();
        assertNotNull(lane);
        Task task = new Task();
        task.setName("task");
        assertDoesNotThrow(() -> lane.setTasks(List.of(task)));
    }

}