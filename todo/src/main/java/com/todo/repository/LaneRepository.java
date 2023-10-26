package com.todo.repository;

import com.todo.model.Lane;
import com.todo.server.ServerApplication;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LaneRepository extends JpaRepository<Lane, Integer> {
    Lane findLaneById(Integer id);
    List<Lane> findByWorkspaceId(Integer workspaceId);

    Lane findByWorkspaceIdAndPosition(int workspaceId, int position);
}
