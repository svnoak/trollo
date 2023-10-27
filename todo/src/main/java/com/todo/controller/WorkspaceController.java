package com.todo.controller;

import com.todo.model.Lane;
import com.todo.model.Task;
import com.todo.model.Workspace;
import com.todo.service.LaneService;
import com.todo.service.TaskService;
import com.todo.service.WorkspaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workspaces")
public class WorkspaceController {

    private final WorkspaceService workspaceService;
    private final LaneService laneService;

    @Autowired
    public WorkspaceController(WorkspaceService workspaceService, LaneService laneService) {
        this.workspaceService = workspaceService;
        this.laneService = laneService;
    }

    @GetMapping
    public ResponseEntity<List<Workspace>> getAllWorkspaces() {
        try {
            List<Workspace> workspaces = workspaceService.getAllWorkspaces();
            return ResponseEntity.ok(workspaces);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{workspaceId}")
    public ResponseEntity<Workspace> getWorkspaceById(@PathVariable int workspaceId) {
        if(workspaceId < 0) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ofNullable(workspaceService.getWorkspaceById(workspaceId));
    }

    @GetMapping("/{workspaceId}/lanes")
    public ResponseEntity<List<Lane>> getAllLanesByWorkspaceId(@PathVariable  int workspaceId) {
        if(workspaceId < 0) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(laneService.getLanesByWorkspaceId(workspaceId));
    }

    @PostMapping
    public ResponseEntity<Workspace> createWorkspace(@RequestBody Workspace workspace) {
        if(workspace == null || workspace.getName() == null || workspace.getName().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        try {
            Workspace createdWorkspace = workspaceService.createWorkspace(workspace.getName());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdWorkspace);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/{workspaceId}/lanes")
    public ResponseEntity<Lane> createLane(@PathVariable int workspaceId, @RequestBody Lane lane) {
        if(workspaceId < 0 || lane == null || lane.getName() == null || lane.getName().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Workspace workspace = workspaceService.getWorkspaceById(workspaceId);
        if (workspace == null) {
            return ResponseEntity.notFound().build();
        }
        try {
            Lane createdLane = workspaceService.createLane(lane.getName(), workspace);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdLane);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{workspaceId}")
    public ResponseEntity<Workspace> deleteWorkspace(@PathVariable int workspaceId) {
        if(workspaceId < 0) {
            return ResponseEntity.badRequest().build();
        }
        Workspace workspace = workspaceService.getWorkspaceById(workspaceId);
        if (workspace == null) {
            return ResponseEntity.notFound().build();
        }
        try {
            workspaceService.deleteWorkspace(workspace);
            return ResponseEntity.ok(workspace);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
