package com.todo.dto.request;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/**
 * DTO for the CreateLaneRequest.
 * This DTO is used to create a lane.
 * The name is optional.
 * If the name is not set, a default name will be used.
 */
@Hidden
public class CreateLaneRequest {

    /**
     * The id of the workspace to create the lane in.
     */
    @NotNull
    @Schema(description = "Id of the workspace to create the lane in", example = "1", required = true)
    private int workspaceId;

    /**
     * The name of the lane.
     */
    @Schema(description = "Name of the lane", example = "New Lane", required = false)
    private String name;

    /**
     * Constructor for the CreateLaneRequest.
     * @param workspaceId The id of the workspace to create the lane in.
     * @param name The name of the lane.
     */
    public CreateLaneRequest(int workspaceId, String name){
        this.workspaceId = workspaceId;
        this.name = name;
    }

    /**
     * Get the id of the workspace to create the lane in.
     * @return The id of the workspace to create the lane in.
     */
    public int getWorkspaceId(){
        return workspaceId;
    }

    /**
     * Get the name of the lane.
     * @return The name of the lane.
     */
    public String getName(){
        return name;
    }
}
