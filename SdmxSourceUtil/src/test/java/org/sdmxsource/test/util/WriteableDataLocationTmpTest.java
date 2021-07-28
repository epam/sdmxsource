package org.sdmxsource.test.util;

import org.junit.jupiter.api.Test;
import org.sdmxsource.util.io.WriteableDataLocationTmp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WriteableDataLocationTmpTest {

    @Test
    public void shouldCheckWriteableDataLocationTmp() {
        byte testValue = 1;
        var writeable = new WriteableDataLocationTmp();
        OutputStream outputStream = writeable.getOutputStream();
        InputStream inputStream = writeable.getInputStream();
        try {
            outputStream.write(testValue);
            outputStream.flush();

            int readByte = inputStream.read();
            assertEquals(testValue, readByte);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
