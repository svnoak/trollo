package com.todo.controller;

import com.todo.dto.request.CreateLaneRequest;
import com.todo.dto.response.LaneDTO;
import com.todo.dto.response.TaskDTO;
import com.todo.dto.response.WorkspaceDTO;
import com.todo.model.Lane;
import com.todo.model.Workspace;
import com.todo.service.LaneService;
import com.todo.service.TaskService;
import com.todo.service.WorkspaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Lane API", description = "Lane API")
@RestController
@RequestMapping("/api/lanes")
public class LaneController {

    private LaneService laneService;
    private WorkspaceService workspaceService;
    private TaskService taskService;

    @Autowired
    public LaneController(LaneService laneService, WorkspaceService workspaceService, TaskService taskService) {
        this.laneService = laneService;
        this.workspaceService = workspaceService;
        this.taskService = taskService;
    }

    @GetMapping("/{laneId}")
    @Operation(summary = "Get lane by id")
    @Parameter(name = "laneId", description = "Id of the lane to be retrieved", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved lane"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Lane not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<LaneDTO> getLaneById(@PathVariable int laneId) {
        if(laneId < 0) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ofNullable(laneService.getLaneByIdAsDTO(laneId));
    }

    @GetMapping("/{laneId}/tasks")
    @Operation(summary = "Get all tasks in a lane")
    @Parameter(name = "laneId", description = "Id of the lane to get tasks from", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved tasks"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Lane not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<TaskDTO>> getAllTasksByLaneId(@PathVariable int laneId) {
        if(laneId < 0) {
            return ResponseEntity.badRequest().build();
        }
        List<TaskDTO> tasks = taskService.getTasksByLaneId(laneId);
        if (tasks == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tasks);
    }

    @PostMapping
    @Operation(summary = "Create a new lane")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created lane"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Workspace not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<LaneDTO> createLane(@RequestBody CreateLaneRequest createLaneRequest) {
        int workspaceId = createLaneRequest.getWorkspaceId();
        String laneName = createLaneRequest.getName();
        if(workspaceId < 0 || laneName == null || laneName.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Workspace workspace = workspaceService.getWorkspaceById(workspaceId);
        if (workspace == null) {
            return ResponseEntity.notFound().build();
        }
        try {
            Lane createdLane = workspaceService.createLane(laneName, workspace);
            LaneDTO createdLaneDTO = new LaneDTO(createdLane);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdLaneDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PatchMapping("/{laneId}/name")
    @Operation(summary = "Update lane name")
    @Parameter(name = "laneId", description = "Id of the lane to be updated", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated lane name"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Lane not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<LaneDTO> updateLaneName(@PathVariable int laneId, @RequestBody(required = false) String name) {
        if(laneId < 0 || name == null || name.isEmpty() || name.isBlank() || name.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Lane lane = laneService.getLaneById(laneId);
        if (lane == null) {
            return ResponseEntity.notFound().build();
        }
        try {
            lane.setName(name);
            Lane updatedLane = laneService.updateLaneName(lane);
            LaneDTO updatedLaneDTO = new LaneDTO(updatedLane);
            return ResponseEntity.ok(updatedLaneDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{laneId}")
    @Operation(summary = "Delete lane")
    @Parameter(name = "laneId", description = "Id of the lane to be deleted", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted lane"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Lane not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Lane> deleteLane(@PathVariable int laneId) {
        if(laneId < 0) {
            return ResponseEntity.badRequest().build();
        }
        Lane lane = laneService.getLaneById(laneId);
        if (lane == null) {
            return ResponseEntity.notFound().build();
        }
        try {
            workspaceService.deleteLane(lane);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/{laneId}/move/{newPosition}")
    @Operation(summary = "Move lane to another position")
    @Parameter(name = "laneId", description = "Id of the lane to be moved", required = true)
    @Parameter(name = "newPosition", description = "New position of the lane", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully moved lane to another position"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Lane not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<WorkspaceDTO> moveLane(@PathVariable int laneId, @PathVariable int newPosition) {
        if(laneId < 0 || newPosition < 0) {
            return ResponseEntity.badRequest().build();
        }
        try {
            WorkspaceDTO updatedWorkspace = workspaceService.moveLane(laneId, newPosition);
            return ResponseEntity.ofNullable(updatedWorkspace);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
