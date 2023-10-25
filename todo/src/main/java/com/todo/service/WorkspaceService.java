package com.todo.service;

import com.todo.model.User;
import com.todo.model.Workspace;
import com.todo.repository.UserRepository;
import com.todo.repository.WorkspaceRepository;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkspaceService {

    private final WorkspaceRepository workspaceRepository;
    @Autowired
    public WorkspaceService(WorkspaceRepository workspaceRepository) {
        this.workspaceRepository = workspaceRepository;
    }

    public Workspace createWorkspace(String name, User user){
        Workspace workspace = new Workspace();
        workspace.setName(name);
        workspace.getUsers().add(user);

        return workspaceRepository.save(workspace);
    }

    public void deleteWorkspace(Workspace workspace){
        workspaceRepository.delete(workspace);
    }
}
