package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

class MainSmokeTest {

    @Test
    void healthReturnsExpectedPayload() {
        Main main = new Main();

        Map<String, String> health = main.health();

        assertEquals(Map.of("status", "UP", "service", "FuzzyService"), health);
    }

    @Test
    void homeReturnsExpectedMessage() {
        Main main = new Main();

        assertEquals("FuzzyService is running", main.home());
    }

    @Test
    void userWithIgnoreDoesNotExposeSecretInJson() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        UserWithIgnore user = new UserWithIgnore("Bob", 25, "secret");

        String json = objectMapper.writeValueAsString(user);
        UserWithIgnore deserialized = objectMapper.readValue(
                "{\"name\":\"Charlie\",\"age\":40,\"secret\":\"top\"}",
                UserWithIgnore.class
        );

        assertFalse(json.contains("secret"));
        assertEquals("Charlie", deserialized.name);
        assertEquals(40, deserialized.age);
        assertNull(deserialized.secret);
    }
}
