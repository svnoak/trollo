package com.todo.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class MoveTaskRequest {

    @NotNull
    @PositiveOrZero
    private int sourceLaneId;

    @NotNull
    @PositiveOrZero
    private int targetLaneId;

    @NotNull
    @PositiveOrZero
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
