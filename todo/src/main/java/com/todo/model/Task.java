package com.todo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Hidden
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String description;

    @Column(name = "task_order")
    private int position;

    @ManyToOne
    @NotNull
    @JsonIgnore
    @JoinColumn(name = "lane_id")
    private Lane lane;

    public int getId() {
        return id;
    }

    public Lane getLane() {
        return lane;
    }

    public int getPosition() {
        return position;
    }


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setPosition(int position) {
        this.position = position;
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
