package com.todo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class ChangeTaskDetails {

    @Schema(description = "Name of the task", example = "New Task", required = false)
    private String name;

    @Schema(description = "Description of the task", example = "This is a new task", required = false)
    private String description;

    public ChangeTaskDetails(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}
