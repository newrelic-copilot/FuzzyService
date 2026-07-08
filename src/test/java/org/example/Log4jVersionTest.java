package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class Log4jVersionTest {

    @Test
    void usesSecureLog4jVersionAtRuntime() {
        String expectedLog4jVersion = System.getProperty("expectedLog4jVersion");
        String log4jApiVersion = LogManager.class.getPackage().getImplementationVersion();
        String log4jCoreVersion = LoggerContext.class.getPackage().getImplementationVersion();

        assertNotNull(expectedLog4jVersion, "expectedLog4jVersion system property must be configured by the Gradle test task");
        assertNotNull(log4jApiVersion, "Log4j API implementation version should be available from the runtime manifest");
        assertNotNull(log4jCoreVersion, "Log4j Core implementation version should be available from the runtime manifest");

        assertEquals(expectedLog4jVersion, log4jApiVersion);
        assertEquals(expectedLog4jVersion, log4jCoreVersion);
    }
}
