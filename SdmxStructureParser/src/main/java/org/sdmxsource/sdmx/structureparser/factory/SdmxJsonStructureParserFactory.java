package org.sdmxsource.sdmx.structureparser.factory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.sdmxsource.sdmx.api.factory.StructureParserFactory;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.api.util.ReadableDataLocation;
import org.sdmxsource.sdmx.sdmxbeans.model.SdmxStructureJsonFormat;
import org.sdmxsource.sdmx.structureparser.builder.sdmxbeans.impl.SdmxObjectsJsonV1Builder;

import java.io.IOException;
import java.io.InputStream;

/**
 * The type Sdmx json structure parser factory.
 */
public class SdmxJsonStructureParserFactory implements StructureParserFactory {

    private final SdmxObjectsJsonV1Builder sdmxBeansJsonBuilder;

    /**
     * Instantiates a new Sdmx json structure parser factory.
     *
     * @param sdmxStructureJsonFormat the sdmx structure json format
     */
    public SdmxJsonStructureParserFactory(SdmxStructureJsonFormat sdmxStructureJsonFormat) {
        if (sdmxStructureJsonFormat == null)
            throw new IllegalArgumentException("sdmxStructureJsonFormat");

        if (sdmxStructureJsonFormat.getFormatAsString().equals("JSON_V10"))
            sdmxBeansJsonBuilder = new SdmxObjectsJsonV1Builder(sdmxStructureJsonFormat);
        else
            throw new UnsupportedOperationException();
    }

    @Override
    public SdmxBeans getSdmxBeans(ReadableDataLocation rdl) {

        try {
            try (InputStream stream = rdl.getInputStream()) {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode node = mapper.readTree(stream);
                return sdmxBeansJsonBuilder.build(node);
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
