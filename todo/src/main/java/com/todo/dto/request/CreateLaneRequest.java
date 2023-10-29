package com.todo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public class CreateLaneRequest {

    @NotNull
    @Schema(description = "Id of the workspace to create the lane in", example = "1", required = true)
    private int workspaceId;

    @Schema(description = "Name of the lane", example = "New Lane", required = false)
    private String name;

    public CreateLaneRequest(int workspaceId, String name){
        this.workspaceId = workspaceId;
        this.name = name;
    }

    public int getWorkspaceId(){
        return workspaceId;
    }

    public String getName(){
        return name;
    }
}
