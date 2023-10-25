package com.todo.repository;

import com.todo.model.User;
import com.todo.model.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);

    @Query("UPDATE User u SET u.workspaces = :workspace WHERE u.id = :userId")
    void addWorkspaceToUser(@Param("userId") int userId, @Param("workspace") Workspace workspace);
}
