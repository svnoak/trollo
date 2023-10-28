package com.todo.controller;


import com.todo.dto.request.MoveTaskRequest;
import com.todo.dto.response.TaskDTO;
import com.todo.model.Lane;
import com.todo.model.Task;
import com.todo.service.LaneService;
import com.todo.service.TaskService;
import com.todo.service.WorkspaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO task) {
        try {
        Lane lane = laneService.getLaneById(task.getLaneId());
        if (lane == null) {
            return ResponseEntity.notFound().build();
        }
            TaskDTO createdTask = laneService.createTask(task.getName(), task.getDescription(), task.getPosition(), lane);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable int taskId) {
        if(taskId < 0) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ofNullable(taskService.getTaskByIdAsDTO(taskId));
    }

    @PatchMapping("/{taskId}")
    public ResponseEntity<TaskDTO> updateTaskDetails(@PathVariable int taskId, @RequestBody TaskDTO task) {
        try {
        Task taskToUpdate = taskService.getTaskById(taskId);
        if (taskToUpdate == null) {
            return ResponseEntity.notFound().build();
        }
            taskToUpdate.setName(task.getName());
            taskToUpdate.setDescription(task.getDescription());
            TaskDTO updatedTask = taskService.updateTask(taskToUpdate);
            return ResponseEntity.ok(updatedTask);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("/{taskId}/move")
    public ResponseEntity<Task> moveTask(@PathVariable int taskId, @RequestBody MoveTaskRequest moveTaskRequest) {
        try {
            Lane sourceLane = laneService.getLaneById(moveTaskRequest.getSourceLaneId());
            Lane targetLane = laneService.getLaneById(moveTaskRequest.getTargetLaneId());
            Task task = taskService.getTaskById(taskId);
            int newTaskPosition = moveTaskRequest.getNewTaskPosition();
            if (sourceLane == null || targetLane == null || task == null) {
                return ResponseEntity.notFound().build();
            }
            laneService.moveTask(task, sourceLane, targetLane, newTaskPosition);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable int taskId) {
        if(taskId < 0) {
            return ResponseEntity.badRequest().build();
        }
        Task taskToDelete = taskService.getTaskById(taskId);
        if (taskToDelete == null) {
            return ResponseEntity.notFound().build();
        }
        try {
            laneService.deleteTask(taskToDelete);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
