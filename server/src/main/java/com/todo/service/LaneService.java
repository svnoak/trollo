package com.todo.service;

import com.todo.dto.response.LaneDTO;
import com.todo.dto.response.TaskDTO;
import com.todo.model.Lane;
import com.todo.model.Task;
import com.todo.repository.LaneRepository;
import com.todo.repository.TaskRepository;
import io.swagger.v3.oas.annotations.Hidden;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for the Lane model.
 */
@Hidden
@Service
public class LaneService {

    private final LaneRepository laneRepository;
    private final TaskRepository taskRepository;

    /**
     * Constructor for the LaneService class.
     * @param laneRepository The repository for the Lane model.
     * @param taskRepository The repository for the Task model.
     */
    @Autowired
    public LaneService(LaneRepository laneRepository, TaskRepository taskRepository) {
        this.laneRepository = laneRepository;
        this.taskRepository = taskRepository;
    }

    /**
     * Create a lane.
     * @param name The name of the lane.
     * @param workspaceId The id of the workspace the lane belongs to.
     * @return The created lane.
     */
    public Lane getLaneById(int id){
        return laneRepository.findById(id).orElse(null);
    }

    /**
     * Get a lane by its id and return it as a DTO.
     * @param id The id of the lane to be retrieved.
     * @return The lane with the given id.
     * @throws ObjectNotFoundException If the lane with the given id does not exist.
     */
    public LaneDTO getLaneByIdAsDTO(int id) throws ObjectNotFoundException{
        Lane lane = laneRepository.findById(id).orElse(null);
        if(lane == null) throw new ObjectNotFoundException(id, "Lane");
        return new LaneDTO(lane);
    }

    /**
     * Get all lanes belonging to a workspace.
     * @param workspaceId The id of the workspace.
     * @return A list of all lanes belonging to the workspace.
     */
    public List<Lane> getLanesByWorkspaceId(int workspaceId){
        return laneRepository.findByWorkspaceId(workspaceId);
    }

    /**
     * Create a task.
     * @param name The name of the task.
     * @param description The description of the task.
     * @param laneId The id of the lane the task belongs to.
     * @return The created lane.
     * @throws ObjectNotFoundException If the lane with the given id does not exist.
     */
    public TaskDTO createTask(String name, String description, int laneId) throws ObjectNotFoundException{
        Lane lane = laneRepository.findById(laneId).orElse(null);
        if(lane == null) throw new ObjectNotFoundException(laneId, "Lane");
        Task task = new Task();
        if(name == null || name.isEmpty()) name = "New Task";
        task.setName(name);
        task.setLane(lane);
        task.setDescription(description);
        lane.getTasks().add(lane.getTasks().size(), task);
        updateTaskPositions(lane);
        taskRepository.save(task);
        return new TaskDTO(task);
    }

    /**
     * Delete a task.
     * @param taskId The id of the task to be deleted.
     * @throws ObjectNotFoundException If the task with the given id does not exist.
     */
    public void deleteTask(int taskId) throws ObjectNotFoundException {
        Task task = taskRepository.findById(taskId).orElse(null);
        if(task == null) throw new ObjectNotFoundException(taskId, "Task");
        Lane lane = task.getLane();
        lane.getTasks().remove(task);
        updateTaskPositions(lane);
        laneRepository.save(lane);
        taskRepository.delete(task);
    }

    /**
     * Move a task to a new position in a lane.
     * @param taskId The id of the task to be moved.
     * @param sourceLaneId The id of the lane the task is currently in.
     * @param targetLaneId The id of the lane the task should be moved to.
     * @param newTaskPosition The position the task should be moved to.
     * @throws ObjectNotFoundException If the task or one of the lanes does not exist.
     */
    public void moveTask(int taskId, int sourceLaneId, int targetLaneId, int newTaskPosition) throws ObjectNotFoundException{
        Task task = taskRepository.findById(taskId).orElse(null);
        Lane sourceLane = laneRepository.findById(sourceLaneId).orElse(null);
        Lane targetLane = laneRepository.findById(targetLaneId).orElse(null);

        if(task == null) throw new ObjectNotFoundException(taskId, "Task");
        if(sourceLane == null) throw new ObjectNotFoundException(sourceLaneId, "Lane");
        if(targetLane == null) throw new ObjectNotFoundException(targetLaneId, "Lane");

        sourceLane.getTasks().remove(task);
        targetLane.getTasks().add(newTaskPosition, task);

        task.setLane(targetLane);
        updateTaskPositions(sourceLane);
        updateTaskPositions(targetLane);

        laneRepository.save(sourceLane);
        laneRepository.save(targetLane);
    }

    /**
     * Update the positions of all lanes in a workspace.
     * @param lane The lane in which tasks are rearranged
     */
    private void updateTaskPositions(Lane lane){
        for(int i = 0; i < lane.getTasks().size(); i++){
            lane.getTasks().get(i).setPosition(i);
        }
    }

    /**
     * Update name of the Lane
     * @param updatedLane The lane with the updated name
     * @return The updated lane
     */
    public Lane updateLaneName(Lane updatedLane) throws ObjectNotFoundException{
        Lane lane = laneRepository.findById(updatedLane.getId()).orElse(null);
        if (lane == null) throw new ObjectNotFoundException(updatedLane.getId(), "Lane");
        lane.setName(updatedLane.getName());
        return laneRepository.save(lane);
    }
}
