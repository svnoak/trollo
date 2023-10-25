package com.todo.model;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
public class UserTest {
    @Test
    void getId() {
        User user = new User();
        assertNotNull(user);
        ReflectionTestUtils.setField(user, "id", 1);
        assertEquals(1, user.getId());
    }

    @Test
    void getEmail() {
        User user = new User();
        assertNotNull(user);
        user.setEmail("user@email.com");
        assertEquals("user@email.com", user.getEmail());
    }

    @Test
    void getName() {
        User user = new User();
        assertNotNull(user);
        user.setName("user");
        assertEquals("user", user.getName());
    }

    @Test
    void setEmail() {
        User user = new User();
        assertNotNull(user);
        assertDoesNotThrow(() -> user.setEmail("user@email.com"));
    }

    @Test
    void setEmailNull() {
        User user = new User();
        assertNotNull(user);
        assertThrows(IllegalArgumentException.class, () -> user.setEmail(null));
    }

    @Test
    void setEmailEmpty() {
        User user = new User();
        assertNotNull(user);
        assertThrows(IllegalArgumentException.class, () -> user.setEmail(""));
    }

    @Test
    void setEmailInvalid() {
        User user = new User();
        assertNotNull(user);
        assertThrows(IllegalArgumentException.class, () -> user.setEmail("invalid"));
    }

    @Test
    void setName() {
        User user = new User();
        assertNotNull(user);
        assertDoesNotThrow(() -> user.setName("name"));
    }

    @Test
    void setNameNull() {
        User user = new User();
        assertNotNull(user);
        assertThrows(IllegalArgumentException.class, () -> user.setName(null));
    }

    @Test
    void setNameEmpty() {
        User user = new User();
        assertNotNull(user);
        assertThrows(IllegalArgumentException.class, () -> user.setName(""));
    }

    @Test
    void setWorkspaces() {
        User user = new User();
        assertNotNull(user);

        Workspace workspace = new Workspace();
        List<Workspace> workspaceList = List.of(workspace);
        workspace.setUsers(List.of(user));

        assertDoesNotThrow(() -> user.setWorkspaces(workspaceList));
        assertEquals(1, workspace.getUsers().size());
    }

    @Test
    void setWorkspacesNull() {
        User user = new User();
        assertNotNull(user);
        assertThrows(IllegalArgumentException.class, () -> user.setWorkspaces(null));
    }

    @Test
    void getWorkspaces() {
        User user = new User();
        assertNotNull(user);

        Workspace workspace = new Workspace();
        List<Workspace> workspaceList = List.of(workspace);
        user.setWorkspaces(workspaceList);

        assertEquals(workspaceList, user.getWorkspaces());
    }
}
