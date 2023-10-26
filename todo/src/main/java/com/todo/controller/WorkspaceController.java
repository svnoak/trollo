package com.todo.controller;

import com.todo.model.Workspace;
import com.todo.service.WorkspaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/workspaces")
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    @Autowired
    public WorkspaceController(WorkspaceService workspaceService) {
        this.workspaceService = workspaceService;
    }

    @GetMapping
    public ResponseEntity<List<Workspace>> getAllWorkspaces() {
        List<Workspace> workspaces = workspaceService.getAllWorkspaces();
        return ResponseEntity.ok(workspaces);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Workspace> getWorkspaceById(@PathVariable int id) {
        return ResponseEntity.ofNullable(workspaceService.getWorkspaceById(id));
    }

    @PostMapping
    public ResponseEntity<Workspace> createWorkspace(@RequestBody Workspace workspace) {
        Workspace createdWorkspace = workspaceService.createWorkspace(workspace.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdWorkspace);
    }

    @PatchMapping("/{id}/name")
    public ResponseEntity<Workspace> updateWorkspace(@RequestBody int id, @RequestBody String newName) {
        Workspace updatedWorkspace = workspaceService.getWorkspaceById(id);
        if(updatedWorkspace == null){
            return ResponseEntity.notFound().build();
        }
        Workspace workspace = workspaceService.update(updatedWorkspace);
        if(workspace == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(workspace);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkspace(@PathVariable int id) {
        try{
            Workspace workspace = workspaceService.getWorkspaceById(id);
            workspaceService.deleteWorkspace(workspace);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
