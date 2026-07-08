package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Log4jVersionTest {

    @Test
    void usesSecureLog4jVersionAtRuntime() {
        assertEquals("2.17.1", LogManager.class.getPackage().getImplementationVersion());
        assertEquals("2.17.1", LoggerContext.class.getPackage().getImplementationVersion());
    }
}
