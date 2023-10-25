package com.todo.entity;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WorkspaceTest {

    @Test
    void getId() {
        Workspace workspace = new Workspace();
        assertNotNull(workspace);
        ReflectionTestUtils.setField(workspace, "id", 1);
        assertEquals(1, workspace.getId());
    }

    @Test
    void getName() {
        Workspace workspace = new Workspace();
        assertNotNull(workspace);
        workspace.setName("workspace");
        assertEquals("workspace", workspace.getName());
    }

    @Test
    void getLanes() {
        Workspace workspace = new Workspace();
        assertNotNull(workspace);

        Lane lane = new Lane();

        workspace.setLanes(List.of(lane));
        assertEquals(1, workspace.getLanes().size());
    }

    @Test
    void getUsers() {
        Workspace workspace = new Workspace();
        assertNotNull(workspace);

        User user = new User();
        user.setName("user");

        workspace.setUsers(List.of(user));
        assertEquals(1, workspace.getUsers().size());
    }

    @Test
    void setUsers(){
        Workspace workspace = new Workspace();
        assertNotNull(workspace);
        User user = new User();
        user.setName("user");
        assertDoesNotThrow(() -> workspace.setUsers(List.of(user)));
    }

    @Test
    void setName() {
        Workspace workspace = new Workspace();
        assertNotNull(workspace);
        assertDoesNotThrow(() -> workspace.setName("name"));

    }

    @Test
    void setLanes() {
        Workspace workspace = new Workspace();
        assertNotNull(workspace);
        Lane lane = new Lane();
        assertDoesNotThrow(() -> workspace.setLanes(List.of(lane)));
    }

}