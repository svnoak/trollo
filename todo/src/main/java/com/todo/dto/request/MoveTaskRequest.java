package com.todo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class MoveTaskRequest {

    @NotNull
    @PositiveOrZero
    @Schema(description = "Id of the lane to move the task from", example = "1", required = true)
    private int sourceLaneId;

    @NotNull
    @PositiveOrZero
    @Schema(description = "Id of the lane to move the task to", example = "2", required = true)
    private int targetLaneId;

    @NotNull
    @PositiveOrZero
    @Schema(description = "Position of the task in the new lane", example = "0", required = true)
    private int newTaskPosition;

    public MoveTaskRequest(int laneId, int targetLaneId, int newTaskPosition) {
        this.sourceLaneId = laneId;
        this.targetLaneId = targetLaneId;
        this.newTaskPosition = newTaskPosition;
    }

    public int getSourceLaneId() {
        return sourceLaneId;
    }

    public int getTargetLaneId() {
        return targetLaneId;
    }

    public int getNewTaskPosition() {
        return newTaskPosition;
    }
}
