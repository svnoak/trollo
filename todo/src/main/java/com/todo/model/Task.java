package com.todo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

/**
 * Model for the Task.
 */
@Hidden
@Entity
public class Task {

    /**
     * The id of the task.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * The name of the task.
     */
    private String name;

    /**
     * The description of the task.
     */
    private String description;

    /**
     * The position of the task in the lane.
     */
    @Column(name = "task_order")
    private int position;

    /**
     * The lane the task belongs to.
     */
    @ManyToOne
    @NotNull
    @JsonIgnore
    @JoinColumn(name = "lane_id")
    private Lane lane;

    /**
     * Get the id of the task.
     * @return The id of the task.
     */
    public int getId() {
        return id;
    }

    /**
     * Get the name of the task.
     * @return The name of the task.
     */
    public Lane getLane() {
        return lane;
    }

    /**
     * Get the position of the task in the lane.
     * @return The position of the task in the lane.
     */
    public int getPosition() {
        return position;
    }

    /**
     * Get the name of the task.
     * @return The name of the task.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the description of the task.
     * @return The description of the task.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the position of the task in the lane.
     * @param position The position of the task in the lane.
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * Set the name of the task.
     * @param name The name of the task.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the description of the task.
     * @param description The description of the task.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Set the lane the task belongs to.
     * @param lane The lane the task belongs to.
     */
    public void setLane(Lane lane) {
        this.lane = lane;
    }
}
