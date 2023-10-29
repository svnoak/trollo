package com.todo.model;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
class WorkspaceTest {

    @Test
    void testGetId() {
        Workspace workspace = new Workspace();
        assertNotNull(workspace);
        ReflectionTestUtils.setField(workspace, "id", 1);
        assertEquals(1, workspace.getId());
    }

    @Test
    void testGetName() {
        Workspace workspace = new Workspace();
        assertNotNull(workspace);
        workspace.setName("workspace");
        assertEquals("workspace", workspace.getName());
    }

    @Test
    void testGetLanes() {
        Workspace workspace = new Workspace();
        assertNotNull(workspace);

        Lane lane = new Lane();

        workspace.setLanes(List.of(lane));
        assertEquals(1, workspace.getLanes().size());
    }

    @Test
    void testSetName() {
        Workspace workspace = new Workspace();
        assertNotNull(workspace);
        assertDoesNotThrow(() -> workspace.setName("name"));

    }

    @Test
    void testSetLanes() {
        Workspace workspace = new Workspace();
        assertNotNull(workspace);
        Lane lane = new Lane();
        assertDoesNotThrow(() -> workspace.setLanes(List.of(lane)));
    }

}