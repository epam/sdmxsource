package org.sdmxsource.sdmx.api.factory;

import org.sdmxsource.sdmx.api.engine.MetadataWriterEngine;
import org.sdmxsource.sdmx.api.model.format.MetadataFormat;

/**
 * A MetadataWriterFactory operates as a plugin to a Manager which can request a MetadataWriterEngine, to which the implementation will
 * respond with an appropriate MetadataWriterEngine if it is able, otherwise it will return null
 */
public interface MetadataWriterFactory {

    /**
     * Returns a MetadataWriterEngine if the output format is understood, if it is not known then NULL is returned
     *
     * @param outputFormat the format to write the message in
     * @return metadata writer engine
     */
    MetadataWriterEngine getMetadataWriterEngine(MetadataFormat outputFormat);
}
