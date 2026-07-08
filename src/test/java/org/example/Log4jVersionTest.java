package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Log4jVersionTest {

    @Test
    void usesSecureLog4jVersionAtRuntime() {
        String expectedLog4jVersion = System.getProperty("expectedLog4jVersion");

        assertEquals(expectedLog4jVersion, LogManager.class.getPackage().getImplementationVersion());
        assertEquals(expectedLog4jVersion, LoggerContext.class.getPackage().getImplementationVersion());
    }
}
