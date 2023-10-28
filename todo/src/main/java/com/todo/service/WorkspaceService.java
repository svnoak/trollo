package com.todo.service;

import com.todo.dto.response.WorkspaceDTO;
import com.todo.model.Lane;
import com.todo.model.Workspace;
import com.todo.repository.LaneRepository;
import com.todo.repository.WorkspaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WorkspaceService {

    private final WorkspaceRepository workspaceRepository;
    private final LaneRepository laneRepository;

    @Autowired
    public WorkspaceService(WorkspaceRepository workspaceRepository, LaneRepository laneRepository) {
        this.workspaceRepository = workspaceRepository;
        this.laneRepository = laneRepository;

    }

    public Workspace getWorkspaceById(int id){
        return workspaceRepository.findById(id).orElse(null);
    }

    public Workspace createWorkspace(String name){
        Workspace workspace = new Workspace();
        workspace.setName(name);
        workspaceRepository.save(workspace);
        return workspace;
    }

    public void deleteWorkspace(Workspace workspace){
        workspaceRepository.delete(workspace);
    }

    public List<Lane> getAllLanesInWorkspace(Workspace workspace){
        return workspace.getLanes();
    }

    public Lane createLane(String name, Workspace workspace){
        Lane lane = new Lane();
        lane.setName(name);
        lane.setWorkspace(workspace);
        lane.setPosition(workspace.getLanes().size());
        laneRepository.save(lane);

        workspace.getLanes().add(lane);
        workspaceRepository.save(workspace);
        return lane;
    }

    public Workspace deleteLane(Lane lane){
        Workspace workspace = lane.getWorkspace();
        workspace.getLanes().remove(lane);
        updateLanePositions(workspace);
        workspaceRepository.save(workspace);
        laneRepository.delete(lane);
        return workspace;
    }

    public Workspace moveLane(Lane lane, int position){
        Workspace workspace = lane.getWorkspace();
        workspace.getLanes().remove(lane);
        lane.setPosition(position);
        workspace.getLanes().add(position, lane);
        updateLanePositions(workspace);
        return workspaceRepository.save(workspace);
    }

    public Workspace update(Workspace workspace) {
        workspaceRepository.save(workspace);
        return workspace;
    }

    public List<WorkspaceDTO> getAllWorkspaces() {
        List<Workspace> workspaces = workspaceRepository.findAll();
        return workspaces.stream()
                .map(WorkspaceDTO::new)
                .collect(java.util.stream.Collectors.toList());
    }

    private void updateLanePositions(Workspace workspace) {
        for(int i = 0; i < workspace.getLanes().size(); i++){
            workspace.getLanes().get(i).setPosition(i);
        }
    }
}
