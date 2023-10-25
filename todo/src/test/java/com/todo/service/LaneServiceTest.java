package com.todo.service;

import com.todo.model.Lane;
import com.todo.model.User;
import com.todo.model.Workspace;
import com.todo.server.ServerApplication;
import org.hibernate.jdbc.Work;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(classes = ServerApplication.class)
class LaneServiceTest {

    @Autowired
    private LaneService laneService;
    @Autowired
    private WorkspaceService workspaceService;
    @Autowired
    private UserService userService;

    private User user;
    private Workspace workspace;
    @BeforeEach
    void setUp(){
        user = userService.createUser("Test User", "e@mail.com");
        workspace = workspaceService.createWorkspace("Workspace",user);
        workspace.getLanes().add(laneService.createLane(workspace));
    }

    @Test
    void createLane() {
        assertDoesNotThrow(() ->laneService.createLane(workspace));
    }

    @Test
    void deleteLane() {
        Lane lane = laneService.createLane(workspace);
        laneService.deleteLane(lane);
        assertNull(laneService.getLaneById(lane.getId()));
    }

    @Test
    void getLaneById() {
        Lane lane = laneService.createLane(workspace);
        assertNotNull(laneService.getLaneById(lane.getId()));
    }

    @Test
    void updateLane() {
        Lane lane = laneService.createLane(workspace);
        lane.setLaneOrder(1);
        assertDoesNotThrow(() -> laneService.updateLane(lane));
    }

}