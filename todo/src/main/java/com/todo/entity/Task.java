package com.todo.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String description;

    private int taskOrder;
    private int workspaceId;

    @OneToOne
    private Lane lane;
    @ManyToOne
    private Workspace workspace;

    public int getId() {
        return id;
    }

    public int getLaneId() {
        return laneId;
    }

    public int getTaskOrder() {
        return taskOrder;
    }

    public int getWorkspaceId() {
        return workspaceId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setLaneId(int laneId) {
        this.laneId = laneId;
    }

    public void setTaskOrder(int taskOrder) {
        this.taskOrder = taskOrder;
    }

    public void setWorkspaceId(int workspaceId) {
        this.workspaceId = workspaceId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
