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
import java.util.Optional;

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
        Lane lane = laneService.getLaneById(id);
        if(lane == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lane);
    }

    @PostMapping
    public ResponseEntity<Lane> createLane(@RequestBody Lane lane) {
        if(lane.getWorkspace() == null){
            return ResponseEntity.badRequest().build();
        }
        try {
            Lane createdLane = workspaceService.createLane(lane.getName(), lane.getWorkspace());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdLane);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{id}/name")
    public ResponseEntity<Lane> updateLaneName(@RequestBody Lane updatedLane) {
        try{
            Lane lane = laneService.updateLaneName(updatedLane);
            return ResponseEntity.ok(lane);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/move")
    public ResponseEntity<Workspace> updateLanePosition(@RequestBody Lane updatedLane) {
        try{
            Workspace workspace = workspaceService.updateLanePosition(updatedLane, updatedLane.getPosition());
            return ResponseEntity.ok(workspace);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Workspace> deleteLane(@PathVariable int id) {
        Lane lane = laneService.getLaneById(id);
        if(lane == null){
            return ResponseEntity.notFound().build();
        }
        try {
            Workspace updatedWorkspace = workspaceService.deleteLane(lane);
            return ResponseEntity.ok(updatedWorkspace);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
