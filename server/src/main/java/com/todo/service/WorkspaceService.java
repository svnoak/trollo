package com.todo.service;

import com.todo.dto.response.WorkspaceDTO;
import com.todo.model.Lane;
import com.todo.model.Workspace;
import com.todo.repository.LaneRepository;
import com.todo.repository.WorkspaceRepository;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Parameter;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for the Workspace model.
 */
@Hidden
@Service
public class WorkspaceService {

    private final WorkspaceRepository workspaceRepository;
    private final LaneRepository laneRepository;

    @Autowired
    public WorkspaceService(WorkspaceRepository workspaceRepository, LaneRepository laneRepository) {
        this.workspaceRepository = workspaceRepository;
        this.laneRepository = laneRepository;

    }

    /**
     * Get a workspace by id.
     * @param id The id of the workspace to be retrieved.
     * @return The workspace with the given id.
     */
    public Workspace getWorkspaceById(int id){
        return workspaceRepository.findById(id).orElse(null);
    }

    /**
     * Create a workspace.
     * @param name The name of the workspace.
     * @return The created workspace.
     */
    public Workspace createWorkspace(String name){
        Workspace workspace = new Workspace();
        workspace.setName(name);
        workspaceRepository.save(workspace);
        return workspace;
    }

    /**
     * Delete a workspace.
     * @param workspaceId The id of the workspace to be deleted.
     */
    public void deleteWorkspace(int workspaceId) throws ObjectNotFoundException{
        Workspace workspace = workspaceRepository.findById(workspaceId).orElse(null);
        if(workspace == null) throw new ObjectNotFoundException(workspaceId, "Workspace");
        workspaceRepository.delete(workspace);
    }

    /**
     * Get all lanes in a workspace.
     * @param workspace The workspace to get lanes from.
     * @return A list of all lanes in the workspace.
     */
    public List<Lane> getAllLanesInWorkspace(Workspace workspace){
        return workspace.getLanes();
    }

    /**
     * Create a lane in a workspace.
     * @param name The name of the lane.
     * @param workspaceId The id of the workspace to create the lane in.
     * @return The created lane.
     */
    public Lane createLane(String name, int workspaceId){
        Workspace workspace = workspaceRepository.findById(workspaceId).orElse(null);
        if(workspace == null) throw new ObjectNotFoundException(workspaceId, "Workspace");
        Lane lane = new Lane();
        lane.setName(name);
        lane.setWorkspace(workspace);
        lane.setPosition(workspace.getLanes().size());
        laneRepository.save(lane);

        workspace.getLanes().add(lane);
        workspaceRepository.save(workspace);
        return lane;
    }

    /**
     * Delete a lane.
     * @param lane The lane to be deleted.
     * @return The workspace the lane was deleted from.
     */
    public Workspace deleteLane(Lane lane){
        Workspace workspace = lane.getWorkspace();
        workspace.getLanes().remove(lane);
        updateLanePositions(workspace);
        workspaceRepository.save(workspace);
        laneRepository.delete(lane);
        return workspace;
    }

    /**
     * Get a lane by id.
     * @param laneId The id of the lane to be retrieved.
     * @return The lane with the given id.
     */
    public WorkspaceDTO moveLane(int laneId, int position){

        Lane lane = laneRepository.findById(laneId).orElse(null);
        if(lane == null){
            return null;
        }
        Workspace workspace = lane.getWorkspace();
        if(workspace == null){
            return null;
        }
        workspace.getLanes().remove(lane);
        lane.setPosition(position);
        workspace.getLanes().add(position, lane);
        updateLanePositions(workspace);
        workspaceRepository.save(workspace);
        return new WorkspaceDTO(workspace);
    }

    /**
     * Update a workspace.
     * @param workspace The workspace to be updated.
     * @return The updated workspace.
     */
    public Workspace update(Workspace workspace) {
        workspaceRepository.save(workspace);
        return workspace;
    }

    /**
     * Get all workspaces.
     * @return A list of all workspaces.
     */
    public List<WorkspaceDTO> getAllWorkspaces() {
        List<Workspace> workspaces = workspaceRepository.findAll();
        return workspaces.stream()
                .map(WorkspaceDTO::new)
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Update the positions of all lanes in a workspace.
     * @param workspace The workspace to update the lane positions in.
     */
    private void updateLanePositions(Workspace workspace) {
        for(int i = 0; i < workspace.getLanes().size(); i++){
            workspace.getLanes().get(i).setPosition(i);
        }
    }
}
