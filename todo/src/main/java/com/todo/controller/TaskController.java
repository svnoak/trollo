package com.todo.controller;


import com.todo.dto.request.ChangeTaskDetails;
import com.todo.dto.request.CreateTaskRequest;
import com.todo.dto.request.MoveTaskRequest;
import com.todo.dto.response.LaneDTO;
import com.todo.dto.response.TaskDTO;
import com.todo.model.Lane;
import com.todo.model.Task;
import com.todo.service.LaneService;
import com.todo.service.TaskService;
import com.todo.service.WorkspaceService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
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

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody CreateTaskRequest task) {
        try {
        Lane lane = laneService.getLaneById(task.getLaneId());
        if (lane == null) {
            return ResponseEntity.notFound().build();
        }
            TaskDTO createdTask = laneService.createTask(task.getName(), task.getDescription(), lane.getTasks().size(), lane);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
        } catch (ObjectNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        catch (Exception e) {
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
    public ResponseEntity<TaskDTO> updateTaskDetails(@PathVariable int taskId, @RequestBody(required = false) ChangeTaskDetails taskDetails) {
        try {
            if(taskId < 0) {
                return ResponseEntity.badRequest().build();
            }
            if( taskDetails == null ) {
                return ResponseEntity.badRequest().build();
            }
            TaskDTO updatedTask = taskService.updateTaskDetails(taskId, taskDetails);
            return ResponseEntity.ofNullable(updatedTask);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("/{taskId}/move")
    public ResponseEntity<List<LaneDTO>> moveTask(@PathVariable int taskId, @RequestBody MoveTaskRequest moveTaskRequest) {
        try {
            return ResponseEntity.ofNullable(Collections.singletonList(laneService.moveTask(taskId, moveTaskRequest.getSourceLaneId(), moveTaskRequest.getTargetLaneId(), moveTaskRequest.getNewTaskPosition())));
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
        try {
            laneService.deleteTask(taskId);
            return ResponseEntity.noContent().build();
        } catch (ObjectNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
