package com.todo.controller;


import com.todo.dto.request.ChangeTaskDetails;
import com.todo.dto.request.CreateTaskRequest;
import com.todo.dto.request.MoveTaskRequest;
import com.todo.dto.response.TaskDTO;
import com.todo.service.LaneService;
import com.todo.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Task", description = "Task API")
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private TaskService taskService;
    private LaneService laneService;

    @Autowired
    public TaskController(TaskService taskService, LaneService laneService) {
        this.taskService = taskService;
        this.laneService = laneService;
    }

    @PostMapping
    @Operation(summary = "Create a new task")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Successfully created task"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad request"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Lane not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<TaskDTO> createTask(@RequestBody CreateTaskRequest task) {
        try {
            TaskDTO createdTask = laneService.createTask(task.getName(), task.getDescription(), task.getLaneId());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
        } catch (ObjectNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{taskId}")
    @Operation(summary = "Get task by id")
    @Parameter(name = "taskId", description = "Id of the task to be retrieved", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved task"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Task not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable int taskId) {
        if(taskId < 0) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ofNullable(taskService.getTaskByIdAsDTO(taskId));
    }

    @PatchMapping("/{taskId}")
    @Operation(summary = "Update task details")
    @Parameter(name = "taskId", description = "Id of the task to be updated", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated task details"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Task not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
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
    @Operation(summary = "Move task to another lane")
    @Parameter(name = "taskId", description = "Id of the task to be moved", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully moved task to another lane"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Task not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> moveTask(@PathVariable int taskId, @RequestBody MoveTaskRequest moveTaskRequest) {
        try {
            if(taskId < 0) {
                return ResponseEntity.badRequest().build();
            }
            if( moveTaskRequest == null ) {
                return ResponseEntity.badRequest().build();
            }
            laneService.moveTask(taskId, moveTaskRequest.getSourceLaneId(), moveTaskRequest.getTargetLaneId(), moveTaskRequest.getNewTaskPosition());
            return ResponseEntity.ok().build();
        } catch (ObjectNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{taskId}")
    @Operation(summary = "Delete task")
    @Parameter(name = "taskId", description = "Id of the task to be deleted", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted task"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Task not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
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
