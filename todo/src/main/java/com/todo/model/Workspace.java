package com.todo.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Workspace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @OneToMany
    private List<Lane> lanes = new ArrayList<Lane>();

    @ManyToMany(mappedBy = "workspaces")
    private List<User> users = new ArrayList<User>();

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Lane> getLanes() {
        return lanes;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLanes(List<Lane> lanes) {
        this.lanes = lanes;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
