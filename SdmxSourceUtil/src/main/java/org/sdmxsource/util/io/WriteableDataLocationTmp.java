package org.sdmxsource.util.io;

import org.sdmxsource.sdmx.api.util.WriteableDataLocation;

import java.io.OutputStream;

/**
 * The type Writeable data location tmp.
 */
public class WriteableDataLocationTmp extends ReadableDataLocationTmp implements WriteableDataLocation {

    /**
     * Instantiates a new Writeable data location tmp.
     */
    public WriteableDataLocationTmp() {
        super(URIUtil.getTemporaryURI());
        super.deleteOnClose = true;
    }

    @Override
    public OutputStream getOutputStream() {
        return URIUtil.getOutputStream(uri);
    }
}
