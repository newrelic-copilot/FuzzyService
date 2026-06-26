package org.example;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainTest {
    private final Main main = new Main();

    @Test
    void healthReturnsExpectedPayload() {
        assertEquals(Map.of("status", "UP", "service", "FuzzyService"), main.health());
    }

    @Test
    void homeReturnsExpectedMessage() {
        assertEquals("FuzzyService is running", main.home());
    }
}
