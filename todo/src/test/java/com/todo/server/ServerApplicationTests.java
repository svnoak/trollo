package com.todo.server;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@EntityScan("com.todo.entity")
@ComponentScan(basePackages = {"com.todo.repository", "com.todo.service"})
@SpringBootTest
class ServerApplicationTests {

	@Test
	void contextLoads() {
	}

}
