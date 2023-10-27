package com.todo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.jdbc.Work;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Lane {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @PositiveOrZero
    @Column(name = "lane_order")
    private int position;

    private String name;

    @OneToMany(mappedBy = "lane", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks = new ArrayList<Task>();

    @ManyToOne
    @NotNull
    private Workspace workspace;

    public int getId() {
        return id;
    }

    public int getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setPosition(int position) {
        if(position < 0) {
            throw new IllegalArgumentException("Lane position must be positive");
        }
        this.position = position;
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

    public void setName(String name) {
        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name;
    }

    public Workspace getWorkspace() {
        return this.workspace;
    }
}
