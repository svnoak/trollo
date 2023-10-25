package com.todo.service;

import com.todo.model.Lane;
import com.todo.model.Workspace;
import com.todo.repository.LaneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LaneService {

    private final LaneRepository laneRepository;

    @Autowired
    public LaneService(LaneRepository laneRepository) {
        this.laneRepository = laneRepository;
    }

    public Lane createLane(Workspace workspace){
        Lane lane = new Lane();
        lane.setWorkspace(workspace);
        lane.setLaneOrder(workspace.getLanes().size());
        return laneRepository.save(lane);
    }

    public void deleteLane(Lane lane){
        laneRepository.delete(lane);
    }

    public Lane getLaneById(int id){
        return laneRepository.findById(id).orElse(null);
    }

    public Lane updateLane(Lane lane){
        return laneRepository.save(lane);
    }

}
