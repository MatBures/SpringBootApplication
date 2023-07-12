package org.firstPF;

import org.junit.ClassRule;
import org.testcontainers.containers.PostgreSQLContainer;

public class TestContainerConfig {

    @ClassRule
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");


}
