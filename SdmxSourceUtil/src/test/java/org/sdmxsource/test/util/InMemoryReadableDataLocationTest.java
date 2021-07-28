package org.sdmxsource.test.util;

import org.junit.jupiter.api.Test;
import org.sdmxsource.util.io.InMemoryReadableDataLocation;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InMemoryReadableDataLocationTest {

    @Test
    public void shouldCheckInMemoryReadableDataLocation() {
        String stringValue = "123456";
        var readable = new InMemoryReadableDataLocation(stringValue.getBytes(StandardCharsets.UTF_8));

        try {
            assertTrue(readable.getInputStream().readAllBytes().length > 0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertNotEquals(readable.getInputStream(), readable.getInputStream());
    }
}
