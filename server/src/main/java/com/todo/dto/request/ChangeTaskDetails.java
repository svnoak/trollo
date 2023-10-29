package com.todo.dto.request;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO for the ChangeTaskDetails.
 * This DTO is used to change the name and description of a task.
 * The name and description are optional.
 * If the name or description is not set, the old value will be used.
 * If the name or description is set to an empty string, the name or description will be removed.
 */
@Hidden
public class ChangeTaskDetails {

    /**
     * The id of the task to change.
     */
    @Schema(description = "Name of the task", example = "New Task", required = false)
    private String name;

    /**
     * The description of the task.
     */
    @Schema(description = "Description of the task", example = "This is a new task", required = false)
    private String description;

    /**
     * Constructor for the ChangeTaskDetails.
     * @param name The name of the task.
     * @param description The description of the task.
     */
    public ChangeTaskDetails(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Get the name of the task.
     * @return The name of the task.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the description of the task.
     * @return The description of the task.
     */
    public String getDescription() {
        return description;
    }

}
