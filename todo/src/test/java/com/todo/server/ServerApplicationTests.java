package com.todo.server;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Repository;

@EntityScan("com.todo.entity")
@SpringBootTest
class ServerApplicationTests {

	@Test
	void contextLoads() {
	}

}
