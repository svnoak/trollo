package com.todo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Lane {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @PositiveOrZero
    private int laneOrder;

    @OneToMany(mappedBy = "lane")
    @NotNull
    private List<Task> tasks = new ArrayList<Task>();

    @ManyToOne
    @NotNull
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
        if(laneOrder < 0) {
            throw new IllegalArgumentException("Lane order must be positive");
        }
        this.laneOrder = laneOrder;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void setWorkspace(Workspace workspace) {
        if(workspace == null) {
            throw new IllegalArgumentException("Workspace must not be null");
        }
        this.workspace = workspace;
    }
}
