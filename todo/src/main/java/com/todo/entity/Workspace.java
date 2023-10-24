package com.todo.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Workspace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @OneToMany(mappedBy = "workspace")
    private List<Lane> lanes;

    @OneToMany(mappedBy = "workspace")
    private List<Task> tasks;

    @ManyToMany
    private List<User> users;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public List<Lane> getLanes() {
        return lanes;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLanes(List<Lane> lanes) {
        this.lanes = lanes;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
