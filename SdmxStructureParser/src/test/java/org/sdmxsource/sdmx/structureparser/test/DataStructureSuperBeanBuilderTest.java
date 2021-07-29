package org.sdmxsource.sdmx.structureparser.test;

import org.junit.jupiter.api.Test;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.DataStructureSuperBean;
import org.sdmxsource.sdmx.structureparser.builder.superbeans.impl.DataStructureSuperBeanBuilder;
import org.sdmxsource.sdmx.structureretrieval.manager.InMemoryRetrievalManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.sdmxsource.sdmx.structureparser.data.DataHelper.getDataStructure;
import static org.sdmxsource.sdmx.structureparser.data.DataHelper.getStructures;

public class DataStructureSuperBeanBuilderTest {

    @Test
    public void shouldCheckDataStructureSuperBeanBuilder() {
        var sut = new DataStructureSuperBeanBuilder();
        SdmxBeanRetrievalManager retrievalManager = new InMemoryRetrievalManager(getStructures());
        var dataStructureSuperBean = sut.build(getDataStructure(), retrievalManager, null);

        assertThat(dataStructureSuperBean).isInstanceOf(DataStructureSuperBean.class);
    }

}
