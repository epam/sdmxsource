package org.sdmxsource.sdmx.structureparser.test;

import org.junit.jupiter.api.Test;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.DimensionSuperBean;
import org.sdmxsource.sdmx.structureparser.builder.superbeans.impl.DimensionSuperBeanBuilder;
import org.sdmxsource.sdmx.structureretrieval.manager.InMemoryRetrievalManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.sdmxsource.sdmx.structureparser.data.DataHelper.getDimension;
import static org.sdmxsource.sdmx.structureparser.data.DataHelper.getStructures;

public class DimensionSuperBeanBuilderTest {

    @Test
    public void shouldCheckDimensionSuperBeanBuilder() {
        var sut = new DimensionSuperBeanBuilder();
        SdmxBeanRetrievalManager retrievalManager = new InMemoryRetrievalManager(getStructures());
        var dimensionSuperBean = sut.build(getDimension(), retrievalManager, null);

        assertThat(dimensionSuperBean).isInstanceOf(DimensionSuperBean.class);
    }
}
