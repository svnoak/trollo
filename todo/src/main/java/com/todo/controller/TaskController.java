package com.todo.controller;


import com.todo.dto.CreateTaskRequest;
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

    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTaskById(@PathVariable int taskId) {
        if(taskId < 0) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ofNullable(taskService.getTaskById(taskId));
    }

    @PostMapping("/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable int taskId, @RequestBody Task task) {
        if(taskId < 0 || task == null || task.getName() == null || task.getName().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Task taskToUpdate = taskService.getTaskById(taskId);
        if (taskToUpdate == null) {
            return ResponseEntity.notFound().build();
        }
        try {
            taskToUpdate.setName(task.getName());
            taskToUpdate.setDescription(task.getDescription());
            taskToUpdate.setPosition(task.getPosition());
            taskToUpdate.setLane(task.getLane());
            Task updatedTask = taskService.updateTask(taskToUpdate);
            return ResponseEntity.ok(updatedTask);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Task> deleteTask(@PathVariable int taskId) {
        if(taskId < 0) {
            return ResponseEntity.badRequest().build();
        }
        Task taskToDelete = taskService.getTaskById(taskId);
        if (taskToDelete == null) {
            return ResponseEntity.notFound().build();
        }
        try {
            laneService.deleteTask(taskToDelete);
            return ResponseEntity.ok(taskToDelete);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
