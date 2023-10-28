package com.todo.dto.response;

import com.todo.model.Workspace;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.List;
import java.util.stream.Collectors;

public class WorkspaceDTO {

    @NotNull
    @PositiveOrZero
    private int id;

    @NotNull
    @NotEmpty
    private String name;
    private List<LaneDTO> lanes;

    public WorkspaceDTO(Workspace workspace) {
        this.id = workspace.getId();
        this.name = workspace.getName();
        this.lanes = workspace.getLanes()
                .stream()
                .map(LaneDTO::new)
                .collect(Collectors.toList());
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public List<LaneDTO> getLanes() {
        return lanes;
    }

}
