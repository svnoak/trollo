package com.todo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.ArrayList;
import java.util.List;

/**
 * Model for the Lane.
 */
@Hidden
@Entity
public class Lane {

    /**
     * The id of the lane.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * The position of the lane in the workspace.
     */
    @PositiveOrZero
    @Column(name = "lane_order")
    private int position;

    /**
     * The name of the lane.
     */
    private String name;

    /**
     * The tasks of the lane.
     */
    @OneToMany(mappedBy = "lane", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Task> tasks = new ArrayList<Task>();

    /**
     * The workspace the lane belongs to.
     */
    @ManyToOne
    @NotNull
    @JsonIgnore
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;

    /**
     * Get the id of the lane.
     * @return The id of the lane.
     */
    public int getId() {
        return id;
    }

    /**
     * Get the position of the lane in the workspace.
     * @return The position of the lane in the workspace.
     */
    public int getPosition() {
        return position;
    }

    /**
     * Get the name of the lane.
     * @return The name of the lane.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the tasks of the lane.
     * @return The tasks of the lane.
     */
    public List<Task> getTasks() {
        return tasks;
    }

    /**
     * Set the position of the lane in the workspace.
     * @param position The position of the lane in the workspace.
     */
    public void setPosition(int position) {
        if(position < 0) {
            throw new IllegalArgumentException("Lane position must be positive");
        }
        this.position = position;
    }

    /**
     * Set the tasks of the lane.
     * @param tasks The tasks of the lane.
     */
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }


    /**
     * Set the workspace the lane belongs to.
     * @param workspace The workspace the lane belongs to.
     */
    public void setWorkspace(Workspace workspace) {
        if(workspace == null) {
            throw new IllegalArgumentException("Workspace must not be null");
        }
        this.workspace = workspace;
    }

    /**
     * Set the name of the lane.
     * @param name The name of the lane.
     */
    public void setName(String name) {
        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name;
    }

    /**
     * Get the workspace the lane belongs to.
     * @return The workspace the lane belongs to.
     */
    public Workspace getWorkspace() {
        return this.workspace;
    }
}
