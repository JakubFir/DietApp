package com.example.foodgenerator.testcontainer;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;


@org.testcontainers.junit.jupiter.Testcontainers
public class Testcontainers {

    protected static Network sharedNetwork = Network.newNetwork();

    @Container
    @ServiceConnection
    public static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:5.7")
            .withDatabaseName("FoodDB")
            .withUsername("root")
            .withPassword("password")
            .withExposedPorts(3306)
            .withNetwork(sharedNetwork);


    @Container
    protected static GenericContainer<?> backendContainer =
            new GenericContainer<>(DockerImageName.parse("diet"))
                    .withExposedPorts(8080)
                    .withNetwork(mySQLContainer.getNetwork())
                    .dependsOn(mySQLContainer)
                    .withEnv("spring.datasource.url", "jdbc:mysql://" + mySQLContainer.getNetworkAliases().iterator().next() + ":" + "3306/FoodDB")
                    .withEnv("spring.datasource.username", "root")
                    .withEnv("spring.datasource.password", "password")
                    .withEnv("server.port", "8080");
}
