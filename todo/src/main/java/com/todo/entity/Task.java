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

    @ManyToOne
    private Lane lane;

    public int getId() {
        return id;
    }

    public int getLaneId() {
        return lane.getId();
    }

    public int getTaskOrder() {
        return taskOrder;
    }


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setTaskOrder(int taskOrder) {
        this.taskOrder = taskOrder;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLane(Lane lane) {
        this.lane = lane;
    }
}
