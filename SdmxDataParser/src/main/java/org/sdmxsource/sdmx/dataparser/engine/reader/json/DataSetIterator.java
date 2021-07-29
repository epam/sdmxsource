package org.sdmxsource.sdmx.dataparser.engine.reader.json;

import org.sdmxsource.sdmx.dataparser.model.JsonReader;
import org.sdmxsource.sdmx.dataparser.model.JsonReader.Iterator;

/**
 * The type Data set iterator.
 */
public class DataSetIterator extends AbstractIterator {


    /**
     * Instantiates a new Data set iterator.
     *
     * @param jReader the j reader
     */
    public DataSetIterator(JsonReader jReader) {
        super(jReader);
    }

    @Override
    public void next(String fieldName) {

        super.next(fieldName);
    }

    @Override
    public Iterator start(String fieldName, boolean isObject) {
        // TODO Auto-generated method stub
        return super.start(fieldName, isObject);
    }


}
