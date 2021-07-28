package org.sdmxsource.sdmx.structureparser.test;

import org.junit.jupiter.api.Test;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.PrimaryMeasureSuperBean;
import org.sdmxsource.sdmx.structureparser.builder.superbeans.impl.PrimaryMeasureSuperBeanBuilder;
import org.sdmxsource.sdmx.structureretrieval.manager.InMemoryRetrievalManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.sdmxsource.sdmx.structureparser.data.DataHelper.getDataStructure;
import static org.sdmxsource.sdmx.structureparser.data.DataHelper.getStructures;

public class PrimaryMeasureSuperBeanBuilderTest {

    @Test
    public void shouldCheckPrimaryMeasureSuperBeanBuilder() {
        SdmxBeanRetrievalManager retrievalManager = new InMemoryRetrievalManager(getStructures());
        var sut = new PrimaryMeasureSuperBeanBuilder();
        var primaryMeasureSuperBean = sut.build(getDataStructure().getPrimaryMeasure(), retrievalManager, null);

        assertThat(primaryMeasureSuperBean).isInstanceOf(PrimaryMeasureSuperBean.class);
    }
}
