package com.todo.dto.response;

import com.todo.model.Workspace;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO for the Workspace.
 */
@Hidden
public class WorkspaceDTO {

    /**
     * The id of the workspace.
     */
    @NotNull
    @PositiveOrZero
    private int id;

    /**
     * The name of the workspace.
     */
    @NotNull
    @NotEmpty
    private String name;

    /**
     * The lanes of the workspace.
     */
    private List<LaneDTO> lanes;

    /**
     * Constructor for the WorkspaceDTO.
     * @param workspace The workspace to create the DTO from.
     */
    public WorkspaceDTO(Workspace workspace) {
        this.id = workspace.getId();
        this.name = workspace.getName();
        this.lanes = workspace.getLanes()
                .stream()
                .map(LaneDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Get the id of the workspace.
     * @return The id of the workspace.
     */
    public int getId() {
        return id;
    }

    /**
     * Get the name of the workspace.
     * @return The name of the workspace.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the lanes of the workspace.
     * @return The lanes of the workspace.
     */
    public List<LaneDTO> getLanes() {
        return lanes;
    }

}
