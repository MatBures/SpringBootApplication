package org.firstPF;

import org.junit.ClassRule;
import org.testcontainers.containers.PostgreSQLContainer;

public class TestContainerConfig {

    public static PostgreSQLContainer<?> postgreSQLContainer;

    static {
        postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
                .withDatabaseName("postgres")
                .withUsername("postgres")
                .withPassword("admin");
        postgreSQLContainer.start();
    }
}