package org.firstPF;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest
@Testcontainers
@DirtiesContext
public
class ApplicationTests {

	@Container
	public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest")
			.withDatabaseName("postgres")
			.withUsername("postgres")
			.withPassword("admin");

	@DynamicPropertySource
	static void configurePostgres(DynamicPropertyRegistry registry) {
		postgres.start();
		registry.add("spring.datasource.url", () -> String.format("jdbc:postgresql://localhost:%d/postgres", postgres.getFirstMappedPort()));
		registry.add("spring.datasource.username", () -> "postgres");
		registry.add("spring.datasource.password", () -> "admin");
	}

	@Test
	void contextLoads() {
	}

}