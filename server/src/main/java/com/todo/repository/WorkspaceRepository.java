package com.todo.repository;

import com.todo.model.Workspace;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for the Workspace model.
 */
@Hidden
@Repository
public interface WorkspaceRepository extends JpaRepository<Workspace, Integer> {

}
