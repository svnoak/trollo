package com.todo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Entity
public class Lane {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
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

    public List<Task> getTasks() {
        return tasks;
    }

    public void setLaneOrder(int laneOrder) {
        this.laneOrder = laneOrder;
    }


    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
