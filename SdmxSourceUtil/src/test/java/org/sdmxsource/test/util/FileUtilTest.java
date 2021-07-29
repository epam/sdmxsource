package org.sdmxsource.test.util;

import org.junit.jupiter.api.Test;
import org.sdmxsource.util.io.FileUtil;

import static org.junit.jupiter.api.Assertions.*;

public class FileUtilTest {

    @Test
    public void shouldCheckFileUtils() {
        var temporaryFile = FileUtil.createTemporaryFile("test", "tst");

        assertNotNull(temporaryFile);
        assertTrue(temporaryFile.exists());

        FileUtil.deleteFile(temporaryFile.getAbsolutePath());
        assertFalse(temporaryFile.exists());
    }
}
