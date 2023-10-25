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
        this.workspaceRepository = workspaceRepository;
        this.userRepository = userRepository;
    }

    public User createUser(String name, String email, String password) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        return userRepository.save(user);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Workspace createWorkspace(String workspaceName, User user) {
        Workspace workspace = new Workspace();
        workspace.setName(workspaceName);
        workspace.getUsers().add(user);
        workspaceRepository.save(workspace);

        user.getWorkspaces().add(workspace);
        userRepository.save(user);

        return workspace;
    }

    public Workspace addWorkspaceToUser(User user, Workspace workspace) {
        user.getWorkspaces().add(workspace);
        userRepository.save(user);

        workspace.getUsers().add(user);
        workspaceRepository.save(workspace);
        return workspace;
    }

    public User deleteUser(User user) {
        userRepository.delete(user);
        return user;
    }

    public void removeWorkspaceFromUser(User user, Workspace workspace) {
        user.getWorkspaces().remove(workspace);
        userRepository.save(user);

        workspace.getUsers().remove(user);
        workspaceRepository.save(workspace);
    }

    public User updateUserDetails(User user) {
        return userRepository.save(user);
    }

    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

}
