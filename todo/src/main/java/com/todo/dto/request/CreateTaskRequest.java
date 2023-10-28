package com.todo.dto.request;

import com.todo.model.Task;

public class CreateTaskRequest {
    private int laneId;
    private Task task;

    public CreateTaskRequest(int laneId, Task task){
        this.laneId = laneId;
        this.task = task;
    }

    public int getLaneId(){
        return laneId;
    }

    public Task getTask(){
        return task;
    }

}
