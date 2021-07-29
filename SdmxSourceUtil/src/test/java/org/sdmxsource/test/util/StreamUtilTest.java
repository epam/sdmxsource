package org.sdmxsource.test.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.sdmxsource.util.io.FileUtil;
import org.sdmxsource.util.io.StreamUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StreamUtilTest {

    private final static String fileLocation = "src/test/resources/TestFile.csv";

    @Test
    public void shouldCheckCloseStream() {
        InputStream stream = FileUtil.readFileAsStream(fileLocation);
        try {
            assertNotEquals(0, stream.readAllBytes().length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        StreamUtil.closeStream(stream);
        assertThrows(IOException.class, stream::readAllBytes);

    }

    @ParameterizedTest
    @MethodSource("org.sdmxsource.data.DataProvider#provideStreamUtilParams")
    public void shouldCheckCopyFirstXLines(String fileLocation, int lines, int expectedResult) {
        InputStream stream = FileUtil.readFileAsStream(fileLocation);
        List<String> copyFirstXLines = StreamUtil.copyFirstXLines(stream, lines);

        assertNotNull(copyFirstXLines);
        assertEquals(expectedResult, copyFirstXLines.size());
    }

    @Test
    public void shouldCheckCopyStream() {
        File file = new File(fileLocation);
        InputStream stream = FileUtil.readFileAsStream(fileLocation);
        var outputFile = FileUtil.createTemporaryFile("test-out", ".csv");

        StreamUtil.copyStream(stream, FileUtil.getOutputStream(outputFile.getAbsolutePath()));

        assertEquals(file.length(), outputFile.length());
    }

    @Test
    public void shouldCheckToByteArray() {
        File file = new File(fileLocation);
        InputStream stream = FileUtil.readFileAsStream(fileLocation);

        byte[] byteArray = StreamUtil.toByteArray(stream);
        assertNotEquals(0, byteArray.length);
        assertEquals(file.length(), byteArray.length);
    }
}
