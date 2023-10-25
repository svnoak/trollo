package com.todo.repository;

import com.todo.model.Task;
import com.todo.server.ServerApplication;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ActiveProfiles("test")
@SpringBootTest(classes = ServerApplication.class)
@Transactional
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void createTask(){
        assertDoesNotThrow(() -> taskRepository.save(new Task()));
    }

    @Test
    void getTaskById(){
        Task task = new Task();
        taskRepository.save(task);
        assertDoesNotThrow(() -> taskRepository.findById(task.getId()));
    }

    @Test
    void deleteTask(){
        Task task = new Task();
        taskRepository.save(task);
        assertDoesNotThrow(() -> taskRepository.delete(task));
    }

}
