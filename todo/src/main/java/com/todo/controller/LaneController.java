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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<LaneDTO> getLaneById(@PathVariable int laneId) {
        if(laneId < 0) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ofNullable(laneService.getLaneByIdAsDTO(laneId));
    }

    @GetMapping("/{laneId}/tasks")
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

    @PostMapping("/{laneId}/move")
    public ResponseEntity<WorkspaceDTO> moveLane(@PathVariable int laneId, @RequestBody int newPosition) {
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
