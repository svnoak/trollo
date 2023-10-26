package com.todo.service;

import com.todo.model.Lane;
import com.todo.model.Task;
import com.todo.repository.LaneRepository;
import com.todo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Task createTask(String name, String description, int position, Lane lane){
        Task task = new Task();
        task.setName(name);
        task.setLane(lane);
        task.setDescription(description);
        lane.getTasks().add(position, task);
        updateTaskPositions(lane);
        try {
            taskRepository.save(task);
            return task;
        } catch (Exception e){
            lane.getTasks().remove(task);
            return null;
        }
    }

    public void deleteTask(Task task){
        Lane lane = task.getLane();
        lane.getTasks().remove(task);
        try{
            laneRepository.save(lane);
        } catch (Exception ignored){
        } finally {
            taskRepository.delete(task);
        }
    }

    public Lane updateTaskPosition(Task task, int position){
        Lane lane = task.getLane();
        lane.getTasks().remove(task);
        task.setPosition(position);
        lane.getTasks().add(position, task);

        updateTaskPositions(lane);

        try{
            return laneRepository.save(lane);
        } catch (Exception e){
            return null;
        }
    }

    private void updateTaskPositions(Lane lane){
        for(int i = 0; i < lane.getTasks().size(); i++){
            lane.getTasks().get(i).setPosition(i);
        }
    }

    public List<Lane> getAllLanes() {
        try {
            return laneRepository.findAll();
        } catch (Exception e){
            return null;
        }
    }

    public Lane updateLaneName(Lane updatedLane) {
        Lane lane = laneRepository.findById(updatedLane.getId()).orElse(null);
        if (lane == null) throw new AssertionError();
        lane.setName(updatedLane.getName());
        try {
            return laneRepository.save(lane);
        } catch (Exception e){
            return null;
        }
    }
}
