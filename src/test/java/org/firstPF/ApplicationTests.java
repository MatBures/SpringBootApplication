package org.firstPF;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = TestContainerConfig.class)
class ApplicationTests {

	@Test
	void contextLoads() {
	}

}