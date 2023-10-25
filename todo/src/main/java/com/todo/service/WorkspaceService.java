package com.todo.service;

import com.todo.model.Lane;
import com.todo.model.Workspace;
import com.todo.repository.LaneRepository;
import com.todo.repository.WorkspaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Workspace deleteWorkspace(Workspace workspace){
        workspaceRepository.delete(workspace);
        return workspace;
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

    public void deleteLane(Lane lane){
        Workspace workspace = lane.getWorkspace();
        workspace.getLanes().remove(lane);
        workspaceRepository.save(workspace);
        laneRepository.delete(lane);
    }

    public Workspace updateLanePosition(Lane lane, int position){
        Workspace workspace = lane.getWorkspace();
        workspace.getLanes().remove(lane);
        lane.setPosition(position);
        workspace.getLanes().add(position, lane);

        for(int i = 0; i < workspace.getLanes().size(); i++){
            workspace.getLanes().get(i).setPosition(i);
        }

        return workspaceRepository.save(workspace);
    }

    public void updateName(Workspace workspace, String name) {
        workspace.setName(name);
        workspaceRepository.save(workspace);
    }
}
