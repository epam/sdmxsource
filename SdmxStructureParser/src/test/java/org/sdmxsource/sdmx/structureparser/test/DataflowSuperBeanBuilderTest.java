package org.sdmxsource.sdmx.structureparser.test;

import org.junit.jupiter.api.Test;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.DataflowSuperBean;
import org.sdmxsource.sdmx.structureparser.builder.superbeans.impl.DataflowSuperBeanBuilder;
import org.sdmxsource.sdmx.structureretrieval.manager.InMemoryRetrievalManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.sdmxsource.sdmx.structureparser.data.DataHelper.getDataFlow;
import static org.sdmxsource.sdmx.structureparser.data.DataHelper.getStructures;

public class DataflowSuperBeanBuilderTest {

    @Test
    public void shouldCheckDataflowSuperBeanBuilder() {
        var sut = new DataflowSuperBeanBuilder();
        SdmxBeanRetrievalManager retrievalManager = new InMemoryRetrievalManager(getStructures());
        var dataflowSuperBean = sut.build(getDataFlow(), retrievalManager, null);

        assertThat(dataflowSuperBean).isInstanceOf(DataflowSuperBean.class);
    }
}
