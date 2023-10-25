package com.todo.repository;

import com.todo.model.Lane;
import com.todo.server.ServerApplication;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LaneRepository extends JpaRepository<Lane, Integer> {
    Lane findLaneById(Integer id);

}