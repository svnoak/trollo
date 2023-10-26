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
@RequestMapping("/api/tasks")
public class TaskController {

    private TaskService taskService;
    private WorkspaceService workspaceService;
    private LaneService laneService;

    @Autowired
    public TaskController(TaskService taskService, WorkspaceService workspaceService, LaneService laneService) {
        this.taskService = taskService;
        this.workspaceService = workspaceService;
        this.laneService = laneService;
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        try {
            List<Task> tasks = taskService.getAllTasks();
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable int id) {
        return ResponseEntity.ofNullable(taskService.getTaskById(id));
    }

    @PostMapping("/{laneId}/create")
    public ResponseEntity<Task> createTask(@PathVariable int laneId, @RequestBody Task task) {
        Lane lane = laneService.getLaneById(laneId);
        if(lane == null){
            return ResponseEntity.notFound().build();
        }
        try {
            Task createdTask = laneService.createTask(task.getName(), task.getDescription(), task.getPosition(), lane);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PatchMapping("/{id}/")
    public ResponseEntity<Task> updateTask(@RequestBody int id, @RequestBody Task task) {
        Task updatedTask = taskService.getTaskById(id);
        if (updatedTask == null) {
            return ResponseEntity.notFound().build();
        }
        try {
            Task updated = taskService.updateTask(updatedTask);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PatchMapping("/{id}/move")
    public ResponseEntity<Lane> updateTaskPosition(@PathVariable int id, @RequestBody int newPosition) {
        Task task = taskService.getTaskById(id);
        if (task == null) {
            return ResponseEntity.notFound().build();
        }
        try {
            Lane lane = laneService.updateTaskPosition(task, newPosition);
            return ResponseEntity.ok(lane);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLane(@PathVariable int id) {
        Task task = taskService.getTaskById(id);
        if (task == null) {
            return ResponseEntity.notFound().build();
        }
        try {
            laneService.deleteTask(task);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
