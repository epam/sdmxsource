package org.sdmxsource.sdmx.sdmxbeans.model;

import org.sdmxsource.sdmx.api.constants.STRUCTURE_OUTPUT_FORMAT;
import org.sdmxsource.sdmx.api.model.format.StructureFormat;

/**
 * The type Sdmx structure json format.
 */
public class SdmxStructureJsonFormat implements StructureFormat {

    @Override
    public STRUCTURE_OUTPUT_FORMAT getSdmxOutputFormat() {
        return null;
    }

    @Override
    public String getFormatAsString() {
        return "JSON_V10"; //JsonV10
    }
}
