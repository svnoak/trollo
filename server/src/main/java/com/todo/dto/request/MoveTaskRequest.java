package com.todo.dto.request;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

/**
 * DTO for the MoveTaskRequest.
 * This DTO is used to move a task from one lane to another.
 * The sourceLaneId, targetLaneId and newTaskPosition are required.
 * SourceLaneId and targetLaneId can be the same.
 */
@Hidden
public class MoveTaskRequest {

    /**
     * The id of the lane to move the task from.
     */
    @NotNull
    @PositiveOrZero
    @Schema(description = "Id of the lane to move the task from", example = "1", required = true)
    private int sourceLaneId;

    /**
     * The id of the lane to move the task to.
     */
    @NotNull
    @PositiveOrZero
    @Schema(description = "Id of the lane to move the task to", example = "2", required = true)
    private int targetLaneId;

    /**
     * The position of the task in the new lane.
     */
    @NotNull
    @PositiveOrZero
    @Schema(description = "Position of the task in the new lane", example = "0", required = true)
    private int newTaskPosition;

    /**
     * Constructor for the MoveTaskRequest.
     * @param laneId The id of the lane to move the task from.
     * @param targetLaneId The id of the lane to move the task to.
     * @param newTaskPosition The position of the task in the new lane.
     */
    public MoveTaskRequest(int laneId, int targetLaneId, int newTaskPosition) {
        this.sourceLaneId = laneId;
        this.targetLaneId = targetLaneId;
        this.newTaskPosition = newTaskPosition;
    }

    /**
     * Get the id of the lane to move the task from.
     * @return The id of the lane to move the task from.
     */
    public int getSourceLaneId() {
        return sourceLaneId;
    }

    /**
     * Get the id of the lane to move the task to.
     * @return The id of the lane to move the task to.
     */
    public int getTargetLaneId() {
        return targetLaneId;
    }

    /**
     * Get the position of the task in the new lane.
     * @return The position of the task in the new lane.
     */
    public int getNewTaskPosition() {
        return newTaskPosition;
    }
}
