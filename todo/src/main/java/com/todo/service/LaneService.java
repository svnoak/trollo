package com.todo.service;

import com.todo.dto.response.LaneDTO;
import com.todo.dto.response.TaskDTO;
import com.todo.model.Lane;
import com.todo.model.Task;
import com.todo.repository.LaneRepository;
import com.todo.repository.TaskRepository;
import io.swagger.v3.oas.annotations.Hidden;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;

@Hidden
@Service
public class LaneService {

    private final LaneRepository laneRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public LaneService(LaneRepository laneRepository, TaskRepository taskRepository) {
        this.laneRepository = laneRepository;
        this.taskRepository = taskRepository;
    }

    public Lane getLaneById(int id){
        return laneRepository.findById(id).orElse(null);
    }

    public LaneDTO getLaneByIdAsDTO(int id) throws ObjectNotFoundException{
        Lane lane = laneRepository.findById(id).orElse(null);
        if(lane == null) throw new ObjectNotFoundException(id, "Lane");
        return new LaneDTO(lane);
    }

    public List<Lane> getLanesByWorkspaceId(int workspaceId){
        return laneRepository.findByWorkspaceId(workspaceId);
    }

    public TaskDTO createTask(String name, String description, int position, Lane lane){
        Task task = new Task();
        if(name == null || name.isEmpty()) name = "New Task";
        task.setName(name);
        task.setLane(lane);
        task.setDescription(description);
        lane.getTasks().add(position, task);
        updateTaskPositions(lane);
        taskRepository.save(task);
        return new TaskDTO(task);
    }

    public void deleteTask(int taskId) throws ObjectNotFoundException {
        Task task = taskRepository.findById(taskId).orElse(null);
        if(task == null) throw new ObjectNotFoundException(taskId, "Task");
        Lane lane = task.getLane();
        lane.getTasks().remove(task);
        updateTaskPositions(lane);
        laneRepository.save(lane);
        taskRepository.delete(task);
    }

    public LaneDTO moveTask(int taskId, int sourceLaneId, int targetLaneId, int newTaskPosition){
        Task task = taskRepository.findById(taskId).orElse(null);
        Lane sourceLane = laneRepository.findById(sourceLaneId).orElse(null);
        Lane targetLane = laneRepository.findById(targetLaneId).orElse(null);
        if(task == null || sourceLane == null || targetLane == null) return null;
        sourceLane.getTasks().remove(task);
        targetLane.getTasks().add(newTaskPosition, task);
        task.setLane(targetLane);
        updateTaskPositions(sourceLane);
        updateTaskPositions(targetLane);
        laneRepository.save(sourceLane);
        laneRepository.save(targetLane);
        List<Lane> lanes = List.of(sourceLane, targetLane);
        return lanes.stream()
                .map(LaneDTO::new)
                .findFirst()
                .orElse(null);
    }

    private void updateTaskPositions(Lane lane){
        for(int i = 0; i < lane.getTasks().size(); i++){
            lane.getTasks().get(i).setPosition(i);
        }
    }

    public Lane updateLaneName(Lane updatedLane) throws ObjectNotFoundException{
        Lane lane = laneRepository.findById(updatedLane.getId()).orElse(null);
        if (lane == null) throw new ObjectNotFoundException(updatedLane.getId(), "Lane");
        lane.setName(updatedLane.getName());
        return laneRepository.save(lane);
    }
}
