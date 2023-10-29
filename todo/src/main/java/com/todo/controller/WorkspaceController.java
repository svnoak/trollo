package com.todo.controller;

import com.todo.dto.request.CreateWorkspaceRequest;
import com.todo.dto.response.LaneDTO;
import com.todo.dto.response.WorkspaceDTO;
import com.todo.model.Lane;
import com.todo.model.Workspace;
import com.todo.service.WorkspaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Get all workspaces")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully retrieved all workspaces"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad request"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<WorkspaceDTO>> getAllWorkspaces() {
        try {
            List<WorkspaceDTO> workspaceDTOs = workspaceService.getAllWorkspaces();
            return ResponseEntity.ok(workspaceDTOs);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{workspaceId}")
    @Operation(summary = "Get workspace by id")
    @Parameter(name = "workspaceId", description = "Id of the workspace to be retrieved", required = true)
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully retrieved workspace"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad request"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Workspace not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Workspace> getWorkspaceById(@PathVariable int workspaceId) {
        if(workspaceId < 0) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ofNullable(workspaceService.getWorkspaceById(workspaceId));
    }

    @GetMapping("/{workspaceId}/lanes")
    @Operation(summary = "Get all lanes in a workspace")
    @Parameter(name = "workspaceId", description = "Id of the workspace to get all lanes from", required = true)
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully retrieved all lanes in a workspace"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad request"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Workspace not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
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
    @Operation(summary = "Create a new workspace")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Successfully created a new workspace"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad request"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<WorkspaceDTO> createWorkspace(@RequestBody CreateWorkspaceRequest workspace) {
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
    @Operation(summary = "Delete a workspace")
    @Parameter(name = "workspaceId", description = "Id of the workspace to be deleted", required = true)
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Successfully deleted workspace"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad request"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Workspace not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
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
