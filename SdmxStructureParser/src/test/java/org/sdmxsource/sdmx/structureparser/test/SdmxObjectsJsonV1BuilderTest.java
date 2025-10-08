package org.sdmxsource.sdmx.structureparser.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.api.util.ReadableDataLocation;
import org.sdmxsource.sdmx.sdmxbeans.model.SdmxStructureJsonFormat;
import org.sdmxsource.sdmx.structureparser.builder.sdmxbeans.impl.SdmxObjectsJsonV1Builder;
import org.sdmxsource.util.io.ReadableDataLocationTmp;

public class SdmxObjectsJsonV1BuilderTest {

    private final SdmxObjectsJsonV1Builder sdmxObjectsJsonV1Builder = new SdmxObjectsJsonV1Builder(new SdmxStructureJsonFormat());

    @Test
    public void shouldBuildSdmxBeans() {
        ReadableDataLocation fileReadableDataLocation = new ReadableDataLocationTmp("src/test/resources/json/data-contentconstraint.json");
        SdmxBeans beans;

        try (InputStream stream = fileReadableDataLocation.getInputStream()) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(stream);
            beans = sdmxObjectsJsonV1Builder.build(node);
            beans.getDataflows();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assertThat(beans.getContentConstraintBeans()).isNotEmpty();
        assertThat(beans.getContentConstraintBeans().size()).isEqualTo(1);

        var contentConstraint = beans.getContentConstraintBeans().stream().findFirst().get();
        assertThat(contentConstraint.getAnnotations().size()).isEqualTo(1);
    }

    @Test
    public void shouldBuildSdmxBeans_dataStructure() {
        ReadableDataLocation fileReadableDataLocation = new ReadableDataLocationTmp("src/test/resources/json/datastructure.json");
        SdmxBeans beans;

        try (InputStream stream = fileReadableDataLocation.getInputStream()) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(stream);
            beans = sdmxObjectsJsonV1Builder.build(node);
            beans.getDataflows();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assertThat(beans.getDataStructures()).isNotEmpty();
        assertThat(beans.getDataStructures().size()).isEqualTo(1);
    }
}
