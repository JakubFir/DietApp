package com.example.foodgenerator.testcontainer;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TestcontainersTests extends Testcontainers{


    @Test
    public void mySqlContrinerRuns(){
        mySQLContainer.start();
        assertThat(mySQLContainer.isRunning());
        mySQLContainer.stop();
    }
    @Test
    public void backEndTestcontainer(){
        backendContainer.start();
        assertThat(backendContainer.isRunning());
        backendContainer.stop();
    }
}
