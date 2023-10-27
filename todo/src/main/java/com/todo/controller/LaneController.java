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
    public ResponseEntity<Lane> getLaneById(@PathVariable int laneId) {
        if(laneId < 0) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ofNullable(laneService.getLaneById(laneId));
    }

    @GetMapping("/{laneId}/tasks")
    public ResponseEntity<List<Task>> getAllTasksByLaneId(@PathVariable int laneId) {
        if(laneId < 0) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(taskService.getTasksByLaneId(laneId));
    }

    @PatchMapping("/{laneId}/name")
    public ResponseEntity<Lane> updateLaneName(@PathVariable int laneId, @RequestBody String name) {
        if(laneId < 0 || name == null || name.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Lane lane = laneService.getLaneById(laneId);
        if (lane == null) {
            return ResponseEntity.notFound().build();
        }
        try {
            lane.setName(name);
            Lane updatedLane = laneService.updateLaneName(lane);
            return ResponseEntity.ok(updatedLane);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/{laneId}/tasks")
    public ResponseEntity<Task> createTask(@PathVariable int laneId, @RequestBody Task task) {
        if(laneId < 0 || task == null || task.getName() == null || task.getName().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Lane lane = laneService.getLaneById(laneId);
        if (lane == null) {
            return ResponseEntity.notFound().build();
        }
        try {
            Task createdTask = laneService.createTask(task.getName(), task.getDescription(), task.getPosition(), lane);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
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
            return ResponseEntity.ok(lane);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
