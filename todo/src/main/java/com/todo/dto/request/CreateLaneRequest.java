package com.todo.dto.request;

public class CreateLaneRequest {

    private int workspaceId;
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
