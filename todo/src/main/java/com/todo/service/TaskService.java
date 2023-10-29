package com.todo.service;

import com.todo.dto.request.ChangeTaskDetails;
import com.todo.dto.response.TaskDTO;
import com.todo.model.Lane;
import com.todo.model.Task;
import com.todo.repository.TaskRepository;
import io.swagger.v3.oas.annotations.Hidden;
import org.h2.mvstore.tx.TransactionStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class for the Task model.
 */
@Hidden
@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Update the positions of all tasks in a lane.
     * @param taskId The id of the task to be deleted.
     * @param taskDetails The new details of the task.
     * @return The updated task.
     */
    public TaskDTO updateTaskDetails(int taskId, ChangeTaskDetails taskDetails){
        Task task = taskRepository.findById(taskId).orElse(null);
        if(task == null){
            return null;
        }
        if(taskDetails.getName() != null){
            task.setName(taskDetails.getName());
        }
        if(taskDetails.getDescription() != null){
            task.setDescription(taskDetails.getDescription());
        }
        Task savedTask = taskRepository.save(task);
        return new TaskDTO(savedTask);
    }

    /**
     * Delete a task.
     * @param taskId The id of the task to be deleted.
     */
    public TaskDTO getTaskByIdAsDTO(int taskId){
        Task task = taskRepository.findById(taskId).orElse(null);
        if(task == null){
            return null;
        }
        return new TaskDTO(task);
    }

    /**
     * Delete a task.
     * @param taskId The id of the task to be deleted.
     */
    public Task getTaskById(int taskId){
        return taskRepository.findById(taskId).orElse(null);
    }

    /**
     * Get all tasks in a lane.
     * @param laneId The id of the lane to get tasks from.
     * @return A list of all tasks in the lane.
     */
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
