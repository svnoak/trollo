package com.todo.service;

import com.todo.model.User;
import com.todo.model.Workspace;
import com.todo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }
}
