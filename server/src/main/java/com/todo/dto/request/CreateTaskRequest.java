package com.todo.dto.request;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

/**
 * DTO for the CreateTaskRequest.
 * This DTO is used to create a new task.
 * The name and description are optional.
 * If the name or description is not set, a default value will be used.
 * If the name or description is set to an empty string, the name or description will be removed.
 */
@Hidden
public class CreateTaskRequest {

    /**
     * The id of the lane to create the task in.
     */
    @Schema(description = "Id of the lane to create the task in", example = "1", required = true)
    @NotNull
    @PositiveOrZero
    private int laneId;

    /**
     * The name of the task.
     */
    @Schema(description = "Name of the task", example = "New Task", required = false)
    private String name;

    /**
     * The description of the task.
     */
    @Schema(description = "Description of the task", example = "This is a new task", required = false)
    private String description;

    /**
     * Constructor for the CreateTaskRequest.
     * @param laneId The id of the lane to create the task in.
     * @param name The name of the task.
     * @param description The description of the task.
     */
    public CreateTaskRequest(int laneId, String name, String description){
        this.laneId = laneId;
        this.name = name;
        this.description = description;
    }

    /**
     * Get the id of the lane to create the task in.
     * @return The id of the lane to create the task in.
     */
    public int getLaneId(){
        return laneId;
    }

    /**
     * Get the name of the task.
     * @return The name of the task.
     */
    public String getName(){
        return name;
    }

    /**
     * Get the description of the task.
     * @return The description of the task.
     */
    public String getDescription(){
        return description;
    }

}
