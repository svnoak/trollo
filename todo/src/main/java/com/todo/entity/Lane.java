package com.todo.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Lane {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private int laneOrder;

    @OneToMany(mappedBy = "lane")
    private List<Task> tasks;

    @ManyToOne
    private Workspace workspace;

    public int getId() {
        return id;
    }

    public int getWorkspaceId() {
        return workspace.getId();
    }

    public int getLaneOrder() {
        return laneOrder;
    }

    public String getName() {
        return name;
    }

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }

    public void setLaneOrder(int laneOrder) {
        this.laneOrder = laneOrder;
    }

    public void setName(String name) {
        this.name = name;
    }
}
