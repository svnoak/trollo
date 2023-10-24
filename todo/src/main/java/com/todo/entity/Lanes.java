package com.todo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;

@Entity
public class Lanes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private int workspaceId;
    private int laneOrder;

    public int getId() {
        return id;
    }

    public int getWorkspaceId() {
        return workspaceId;
    }

    public int getLaneOrder() {
        return laneOrder;
    }

    public String getName() {
        return name;
    }

    public void setWorkspaceId(int workspaceId) {
        this.workspaceId = workspaceId;
    }

    public void setLaneOrder(int laneOrder) {
        this.laneOrder = laneOrder;
    }

    public void setName(String name) {
        this.name = name;
    }
}
