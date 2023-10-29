package com.todo.dto.response;

import com.todo.model.Task;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

/**
 * DTO for the Task.
 */
@Hidden
public class TaskDTO {

    /**
     * The id of the task.
     */
    @NotNull
    @PositiveOrZero
    private int id;

    /**
     * The name of the task.
     */
    @NotNull
    @NotEmpty
    private String name;

    /**
     * The description of the task.
     */
    private String description;

    /**
     * The position of the task in the lane.
     */
    @NotNull
    @PositiveOrZero
    private int position;

    /**
     * The lane the task belongs to.
     */
    @NotNull
    @PositiveOrZero
    private int laneId;

    /**
     * Constructor for the TaskDTO.
     * @param task The task to create the DTO from.
     */
    public TaskDTO(Task task) {
        this.id = task.getId();
        this.name = task.getName();
        this.description = task.getDescription();
        this.position = task.getPosition();
        this.laneId = task.getLane().getId();
    }

    /**
     * Get the id of the task.
     * @return The id of the task.
     */
    public int getId() {
        return id;
    }

    /**
     * Get the name of the task.
     * @return The name of the task.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the position of the task in the lane.
     * @return The position of the task in the lane.
     */
    public int getPosition() {
        return position;
    }

    /**
     * Get the lane the task belongs to.
     * @return The lane the task belongs to.
     */
    public int getLaneId() {
        return laneId;
    }

    /**
     * Get the description of the task.
     * @return The description of the task.
     */
    public String getDescription() {
        return description;
    }
}
