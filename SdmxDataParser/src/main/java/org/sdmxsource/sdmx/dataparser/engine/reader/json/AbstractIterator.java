package org.sdmxsource.sdmx.dataparser.engine.reader.json;

import org.sdmxsource.sdmx.dataparser.model.JsonReader;
import org.sdmxsource.sdmx.dataparser.model.JsonReader.Iterator;

/**
 * The type Abstract iterator.
 */
public class AbstractIterator implements JsonReader.Iterator {
    /**
     * The J reader.
     */
    JsonReader jReader;

    /**
     * Instantiates a new Abstract iterator.
     *
     * @param jReader the j reader
     */
    public AbstractIterator(JsonReader jReader) {
        this.jReader = jReader;
    }

    @Override
    public void next(String fieldName) {
    }

    @Override
    public void end(String fieldName, boolean isObject) {

    }

    @Override
    public Iterator start(String fieldName, boolean isObject) {
        return null;
    }
}
