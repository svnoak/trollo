package com.todo.dto.response;

import com.todo.model.Lane;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.List;

public class LaneDTO {

    @NotNull
    @PositiveOrZero
    private int id;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @PositiveOrZero
    private int position;

    @NotNull
    @PositiveOrZero
    private int workspaceId;

    private List<TaskDTO> tasks;
    public LaneDTO(Lane lane){
        this.id = lane.getId();
        this.name = lane.getName();
        this.position = lane.getPosition();
        this.workspaceId = lane.getWorkspace().getId();
        this.tasks = lane.getTasks()
                .stream()
                .map(TaskDTO::new)
                .collect(java.util.stream.Collectors.toList());
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

    public int getWorkspaceId() {
        return workspaceId;
    }
}
