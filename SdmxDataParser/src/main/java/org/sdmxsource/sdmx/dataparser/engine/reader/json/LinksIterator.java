package org.sdmxsource.sdmx.dataparser.engine.reader.json;

import org.sdmxsource.sdmx.dataparser.model.JsonReader;

/**
 * The type Links iterator.
 */
public class LinksIterator extends AbstractIterator {
    private String href;
    private String rel;
    private String title;

    /**
     * Instantiates a new Links iterator.
     *
     * @param jReader the j reader
     */
    public LinksIterator(JsonReader jReader) {
        super(jReader);
    }

    @Override
    public void next(String fieldName) {
        if ("href".equals(fieldName)) {
            href = jReader.getValueAsString();
        } else if ("rel".equals(fieldName)) {
            rel = jReader.getValueAsString();
        } else if ("title".equals(fieldName)) {
            title = jReader.getValueAsString();
        }
    }

    @Override
    public org.sdmxsource.sdmx.dataparser.model.JsonReader.Iterator start(String fieldName, boolean isObject) {
        //TODO Exception?
        return null;
    }
}
