package org.sdmxsource.util.io;

import java.io.IOException;
import java.io.OutputStream;

/**
 * An output stream that does nothing to the data which is written
 */
public class DummyOutputStream extends OutputStream {

    @Override
    public void write(int b) throws IOException {
        //Do Nothing
    }

}
