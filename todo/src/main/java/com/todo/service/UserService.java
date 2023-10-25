package com.todo.service;

import com.todo.model.User;
import com.todo.model.Workspace;
import com.todo.repository.UserRepository;
import com.todo.repository.WorkspaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final WorkspaceRepository workspaceRepository;

    @Autowired
    public UserService(UserRepository userRepository, WorkspaceRepository workspaceRepository) {
        this.userRepository = userRepository;
        this.workspaceRepository = workspaceRepository;
    }

    public User createUser(String name, String email) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        return userRepository.save(user);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Workspace addWorkspaceToUser(User user, Workspace workspace) {
        user.getWorkspaces().add(workspace);
        userRepository.save(user);
        return workspace;
    }

    public User deleteUser(User user) {
        userRepository.delete(user);
        return user;
    }

    public void removeWorkspaceFromUser(User user, Workspace workspace) {
        user.getWorkspaces().remove(workspace);
        userRepository.save(user);
    }
}
