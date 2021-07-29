package org.sdmxsource.test.util;

import org.junit.jupiter.api.Test;
import org.sdmxsource.util.io.URIUtil;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

public class UriUtilTest {

    @Test
    public void shouldCheckGetFileFromUri() {
        String fileLocation = "src/test/resources/TestFile.csv";
        try {
            File file1 = URIUtil.getFile(new URI(fileLocation));
            assertTrue(file1.exists());
            File file2 = URIUtil.getFile(new URI(fileLocation));
            assertTrue(file2.exists());
            assertEquals(file1.getName(), file2.getName());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldCheckTempUriUtil() {
        assertNotNull(URIUtil.getURIUtil());
        File tempFile = URIUtil.getFile(URIUtil.getTemporaryURI());
        assertNotNull(tempFile);
        assertTrue(tempFile.exists());
    }
}
