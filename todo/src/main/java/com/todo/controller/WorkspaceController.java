package com.todo.controller;

import com.todo.dto.response.LaneDTO;
import com.todo.dto.response.WorkspaceDTO;
import com.todo.model.Lane;
import com.todo.model.Workspace;
import com.todo.service.WorkspaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/workspaces")
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    @Autowired
    public WorkspaceController(WorkspaceService workspaceService) {
        this.workspaceService = workspaceService;
    }

    @GetMapping
    public ResponseEntity<List<WorkspaceDTO>> getAllWorkspaces() {
        try {
            List<WorkspaceDTO> workspaceDTOs = workspaceService.getAllWorkspaces();
            return ResponseEntity.ok(workspaceDTOs);
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
    public ResponseEntity<List<LaneDTO>> getAllLanesByWorkspaceId(@PathVariable  int workspaceId) {
        if(workspaceId < 0) {
            return ResponseEntity.badRequest().build();
        }
        Workspace workspace = workspaceService.getWorkspaceById(workspaceId);
        if (workspace == null) {
            return ResponseEntity.notFound().build();
        }
        List<Lane> lanes = workspaceService.getAllLanesInWorkspace(workspace);
        List<LaneDTO> laneDTOs = new ArrayList<>();
        for (Lane lane : lanes) {
            laneDTOs.add(new LaneDTO(lane));
        }
        return ResponseEntity.ok(laneDTOs);
    }

    @PostMapping
    public ResponseEntity<WorkspaceDTO> createWorkspace(@RequestBody Workspace workspace) {
        if(workspace == null || workspace.getName() == null || workspace.getName().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        try {
            Workspace createdWorkspace = workspaceService.createWorkspace(workspace.getName());
            WorkspaceDTO createdWorkspaceDTO = new WorkspaceDTO(createdWorkspace);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdWorkspaceDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{workspaceId}")
    public ResponseEntity<Void> deleteWorkspace(@PathVariable int workspaceId) {
        if(workspaceId < 0) {
            return ResponseEntity.badRequest().build();
        }
        Workspace workspace = workspaceService.getWorkspaceById(workspaceId);
        if (workspace == null) {
            return ResponseEntity.notFound().build();
        }
        try {
            workspaceService.deleteWorkspace(workspace);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
