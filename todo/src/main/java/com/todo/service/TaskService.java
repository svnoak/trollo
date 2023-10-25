package com.todo.service;

import com.todo.model.Lane;
import com.todo.model.Task;
import com.todo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(String name, String description, Lane lane, int taskOrder){
        Task task = new Task();
        task.setName(name);
        task.setDescription(description);
        task.setLane(lane);
        task.setTaskOrder(taskOrder);
        return taskRepository.save(task);
    }

    public Task updateTask(Task task){
        return taskRepository.save(task);
    }

    public void deleteTask(Task task){
        taskRepository.delete(task);
    }

    public Task getTaskById(int id){
        return taskRepository.findById(id).orElse(null);
    }

    public void moveTaskToLane(Task task, Lane lane){
        task.setLane(lane);
        taskRepository.save(task);
    }

    public void moveTaskToPosition(Task task, int position){
        task.setTaskOrder(position);
        taskRepository.save(task);
    }

    public void moveTaskToLaneAndPosition(Task task, Lane lane, int position){
        task.setLane(lane);
        task.setTaskOrder(position);
        taskRepository.save(task);
    }
}
