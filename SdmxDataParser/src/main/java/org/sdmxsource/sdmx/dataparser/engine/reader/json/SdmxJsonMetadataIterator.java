package org.sdmxsource.sdmx.dataparser.engine.reader.json;

import org.sdmxsource.sdmx.dataparser.model.JsonReader;


/**
 * The type Sdmx json metadata iterator.
 */
public class SdmxJsonMetadataIterator extends AbstractIterator {
    private HeaderIterator headerIterator;
    private StructureIterator structureIterator;

//	private List<DataSetInformation> dataSetInformation = new ArrayList<SdmxJsonMetadataIterator.DataSetInformation>();

    /**
     * Instantiates a new Sdmx json metadata iterator.
     *
     * @param jReader the j reader
     */
    public SdmxJsonMetadataIterator(JsonReader jReader) {
        super(jReader);
    }

    @Override
    public JsonReader.Iterator start(String fieldName, boolean isObject) {
        if ("header".equals(fieldName)) {
            headerIterator = new HeaderIterator(jReader);
            return headerIterator;
        } else if ("structure".equals(fieldName)) {
            structureIterator = new StructureIterator(jReader);
            return structureIterator;
        } else if ("dataSets".equals(fieldName)) {
            if (structureIterator != null) {
                //We have everything we need to read the dataset, stop the iterator
                jReader.stopIterating();
            }
            return null;
        } else if ("erorrs".equals(fieldName)) {
            //TODO ERRROS?
        } else {
            //TODO UNKNOWN NODE
        }
        return null;
    }

    /**
     * Gets header iterator.
     *
     * @return the header iterator
     */
    public HeaderIterator getHeaderIterator() {
        return headerIterator;
    }

    /**
     * Gets structure iterator.
     *
     * @return the structure iterator
     */
    public StructureIterator getStructureIterator() {
        return structureIterator;
    }
}
