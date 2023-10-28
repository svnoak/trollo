package com.todo.dto.request;

public class MoveLaneRequest {

    private int laneId;
    private int position;

    public MoveLaneRequest(int laneId, int position) {
        this.laneId = laneId;
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public int getLaneId() {
        return laneId;
    }
}
