package com.todo.dto.request;

import com.todo.model.Task;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class CreateTaskRequest {

    @Schema(description = "Id of the lane to create the task in", example = "1", required = true)
    @NotNull
    @PositiveOrZero
    private int laneId;

    @Schema(description = "Name of the task", example = "New Task", required = false)
    private String name;

    @Schema(description = "Description of the task", example = "This is a new task", required = false)
    private String description;

    public CreateTaskRequest(int laneId, String name, String description){
        this.laneId = laneId;
        this.name = name;
        this.description = description;
    }

    public int getLaneId(){
        return laneId;
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

}
