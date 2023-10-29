package com.todo.dto.response;

import com.todo.model.Task;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Hidden
public class TaskDTO {
    @NotNull
    @PositiveOrZero
    private int id;
    @NotNull
    @NotEmpty
    private String name;
    private String description;

    @NotNull
    @PositiveOrZero
    private int position;

    @NotNull
    @PositiveOrZero
    private int laneId;

    public TaskDTO(Task task) {
        this.id = task.getId();
        this.name = task.getName();
        this.description = task.getDescription();
        this.position = task.getPosition();
        this.laneId = task.getLane().getId();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public int getLaneId() {
        return laneId;
    }

    public String getDescription() {
        return description;
    }
}
