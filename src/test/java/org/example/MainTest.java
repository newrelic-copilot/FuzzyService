package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MainTest {

    @Test
    void healthReportsServiceStatus() {
        Main main = new Main();

        assertEquals("UP", main.health().get("status"));
        assertEquals("FuzzyService", main.health().get("service"));
        assertEquals("FuzzyService is running", main.home());
    }

    @Test
    void performComplexOperationsCompletesWithoutException() {
        Main main = new Main();

        assertDoesNotThrow(main::performComplexOperations);
    }
}
