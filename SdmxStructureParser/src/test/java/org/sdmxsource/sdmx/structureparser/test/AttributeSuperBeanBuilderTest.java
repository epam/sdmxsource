package org.sdmxsource.sdmx.structureparser.test;

import org.junit.jupiter.api.Test;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.AttributeSuperBean;
import org.sdmxsource.sdmx.structureparser.builder.superbeans.impl.AttributeSuperBeanBuilder;
import org.sdmxsource.sdmx.structureretrieval.manager.InMemoryRetrievalManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.sdmxsource.sdmx.structureparser.data.DataHelper.getAttributeBean;
import static org.sdmxsource.sdmx.structureparser.data.DataHelper.getStructures;

public class AttributeSuperBeanBuilderTest {

    @Test
    public void shouldCheckAttributeSuperBeanBuilder() {
        var sut = new AttributeSuperBeanBuilder();
        SdmxBeanRetrievalManager retrievalManager = new InMemoryRetrievalManager(getStructures());
        var attributeSuperBean = sut.build(getAttributeBean(), retrievalManager, null);

        assertThat(attributeSuperBean).isInstanceOf(AttributeSuperBean.class);
    }

}
