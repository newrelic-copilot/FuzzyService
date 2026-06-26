package org.example;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainTest {

    @Test
    void healthReturnsExpectedPayload() {
        Main main = new Main();

        assertEquals(Map.of("status", "UP", "service", "FuzzyService"), main.health());
    }

    @Test
    void homeReturnsExpectedMessage() {
        Main main = new Main();

        assertEquals("FuzzyService is running", main.home());
    }
}
