package com.todo.service;

import com.todo.dto.response.TaskDTO;
import com.todo.model.Lane;
import com.todo.model.Task;
import com.todo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public TaskDTO updateTask(Task task){
        Task savedTask = taskRepository.save(task);
        return new TaskDTO(savedTask);
    }

    public TaskDTO getTaskByIdAsDTO(int id){
        Task task = taskRepository.findById(id).orElse(null);
        if(task == null){
            return null;
        }
        return new TaskDTO(task);
    }

    public Task getTaskById(int id){
        return taskRepository.findById(id).orElse(null);
    }

    public List<TaskDTO> getTasksByLaneId(int laneId) {
        List<Task> tasks = taskRepository.findByLaneId(laneId);
        if (tasks == null) {
            return null;
        }
        return tasks.stream()
                .map(TaskDTO::new)
                .toList();
    }
}
