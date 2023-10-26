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

    @Autowired
    private WorkspaceService workspaceService;

    @GetMapping
    public ResponseEntity<List<Workspace>> getAllWorkspaces() {
        List<Workspace> workspaces = workspaceService.getAllWorkspaces();
        return ResponseEntity.ok(workspaces);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Workspace> getWorkspaceById(@PathVariable int id) {
        Workspace workspaceOptional = workspaceService.getWorkspaceById(id);
        if (workspaceOptional == null) {
            return ResponseEntity.notFound().build();
        }
            return ResponseEntity.ok(workspaceOptional);
    }

    @PostMapping
    public ResponseEntity<Workspace> createWorkspace(@RequestBody Workspace workspace) {
        Workspace createdWorkspace = workspaceService.createWorkspace(workspace.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdWorkspace);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Workspace> updateWorkspaceName(@RequestBody Workspace updatedWorkspace) {
        Workspace workspace = workspaceService.updateName(updatedWorkspace, updatedWorkspace.getName());
        if(workspace == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(workspace);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Workspace> deleteWorkspace(@RequestBody Workspace workspace) {
        Workspace workspaceOptional = workspaceService.deleteWorkspace(workspace);
        if (workspaceOptional == null) {
            return ResponseEntity.notFound().build();
        }
            return ResponseEntity.ok(workspaceOptional);
    }

}
