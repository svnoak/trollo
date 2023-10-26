package com.todo.controller;


import com.todo.model.Lane;
import com.todo.model.Workspace;
import com.todo.service.LaneService;
import com.todo.service.WorkspaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lanes")
public class LaneController {

    @Autowired
    private LaneService laneService;

    @Autowired
    private WorkspaceService workspaceService;

    @GetMapping
    public ResponseEntity<List<Lane>> getAllLanes() {
        try {
            List<Lane> lanes = laneService.getAllLanes();
            return ResponseEntity.ok(lanes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lane> getLaneById(@PathVariable int id) {
        return ResponseEntity.ofNullable(laneService.getLaneById(id));
    }

    @PostMapping
    public ResponseEntity<Lane> createLane(@RequestBody Lane lane) {
        try {
            Lane createdLane = workspaceService.createLane(lane.getName(), lane.getWorkspace());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdLane);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{id}/name")
    public ResponseEntity<Lane> updateLaneName(@RequestBody int id, @RequestBody String newName) {
        Lane updatedLane = laneService.getLaneById(id);
        if (updatedLane == null) {
            return ResponseEntity.notFound().build();
        }
        try {
            Lane lane = laneService.updateLaneName(updatedLane);
            return ResponseEntity.ok(lane);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PatchMapping("/{id}/move")
    public ResponseEntity<Workspace> updateLanePosition(@PathVariable int id, @RequestBody int newPosition) {
        Lane lane = laneService.getLaneById(id);
        if (lane == null) {
            return ResponseEntity.notFound().build();
        }
        try {
            Workspace workspace = workspaceService.updateLanePosition(lane, newPosition);
            return ResponseEntity.ok(workspace);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLane(@PathVariable int id) {
        Lane lane = laneService.getLaneById(id);
        if (lane == null) {
            return ResponseEntity.notFound().build();
        }
        try {
            workspaceService.deleteLane(lane);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
