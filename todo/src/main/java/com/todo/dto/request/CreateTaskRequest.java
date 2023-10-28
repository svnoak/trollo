package com.todo.dto.request;

import com.todo.model.Task;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class CreateTaskRequest {

    @NotNull
    @PositiveOrZero
    private int laneId;
    private String name;
    private String description;

    public CreateTaskRequest(int laneId, String name, String description){
        this.laneId = laneId;
        this.name = name;
        this.description = description;
    }

    public int getLaneId(){
        return laneId;
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

}
