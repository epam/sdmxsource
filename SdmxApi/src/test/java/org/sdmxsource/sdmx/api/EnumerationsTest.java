package org.sdmxsource.sdmx.api;

import org.junit.jupiter.api.Test;
import org.sdmxsource.sdmx.api.constants.BASE_DATA_FORMAT;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EnumerationsTest {

    @Test
    public void shouldCheckBaseDataFormat() {
        Arrays.stream(BASE_DATA_FORMAT.values()).forEach(item -> {
            assertNotNull(item);
            String rootNode = item.getRootNode();
            assertTrue(rootNode == null || rootNode.length() > 0);
        });
    }
}
