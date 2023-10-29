package com.todo.model;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Model for the Workspace.
 */
@Hidden
@Entity
public class Workspace {

    /**
     * The id of the workspace.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * The name of the workspace.
     */
    private String name;

    /**
     * The lanes of the workspace.
     */
    @OneToMany(mappedBy = "workspace", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Lane> lanes = new ArrayList<Lane>();


    /**
     * Get the id of the workspace.
     * @return The id of the workspace.
     */
    public int getId() {
        return id;
    }

    /**
     * Get the name of the workspace.
     * @return The name of the workspace.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the lanes of the workspace.
     * @return The lanes of the workspace.
     */
    public List<Lane> getLanes() {
        return lanes;
    }

    /**
     * Set the name of the workspace.
     * @param name The name of the workspace.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the lanes of the workspace.
     * @param lanes The lanes of the workspace.
     */
    public void setLanes(List<Lane> lanes) {
        this.lanes = lanes;
    }

}
