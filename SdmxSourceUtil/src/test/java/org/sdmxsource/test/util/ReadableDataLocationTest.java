package org.sdmxsource.test.util;

import org.junit.jupiter.api.Test;
import org.sdmxsource.util.io.ReadableDataLocationTmp;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReadableDataLocationTest {

    @Test
    public void shouldCheckReadableDataLocationTmp() {
        String file = "src/test/resources/TestFile.csv";
        var readable = new ReadableDataLocationTmp(file);
        try {
            assertTrue(readable.getInputStream().readAllBytes().length > 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertNotEquals(readable.getInputStream(), readable.getInputStream());
    }
}
