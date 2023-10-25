package com.todo.repository;

import com.todo.model.Lane;
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
class LaneRepositoryTest {

    @Autowired
    private LaneRepository laneRepository;

    @Test
    void createLane(){
        Lane lane = new Lane();
        assertDoesNotThrow(() -> laneRepository.save(lane));
    }

    @Test
    void getLaneById(){
        Lane lane = new Lane();
        laneRepository.save(lane);
        assertDoesNotThrow(() -> laneRepository.findById(lane.getId()));
    }

    @Test
    void deleteLane(){
        Lane lane = new Lane();
        laneRepository.save(lane);
        assertDoesNotThrow(() -> laneRepository.delete(lane));
    }

}