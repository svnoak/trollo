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

    public Task updateTask(Task task){
        return taskRepository.save(task);
    }

    public Task getTaskById(int id){
        return taskRepository.findById(id).orElse(null);
    }


}
