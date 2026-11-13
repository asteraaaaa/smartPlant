package com.csc3700.smartplant;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {"smartplant.simulator.enabled=false", "smartplant.scheduler.enabled=false"})
class SmartPlantApplicationTests {

    @Test
    void contextLoads() {
    }

}
