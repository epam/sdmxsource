package org.sdmxsource.sdmx.structureparser.test;

import org.junit.jupiter.api.Test;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.superbeans.conceptscheme.ConceptSchemeSuperBean;
import org.sdmxsource.sdmx.structureparser.builder.superbeans.impl.ConceptSchemeSuperBeanBuilder;
import org.sdmxsource.sdmx.structureretrieval.manager.InMemoryRetrievalManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.sdmxsource.sdmx.structureparser.data.DataHelper.getConceptScheme;
import static org.sdmxsource.sdmx.structureparser.data.DataHelper.getStructures;

public class ConceptSchemeSuperBeanBuilderTest {

    @Test
    public void shouldCheckConceptSchemeSuperBeanBuilder() {
        var sut = new ConceptSchemeSuperBeanBuilder();
        SdmxBeanRetrievalManager retrievalManager = new InMemoryRetrievalManager(getStructures());
        var conceptSchemeSuperBean = sut.build(getConceptScheme(), retrievalManager, null);

        assertThat(conceptSchemeSuperBean).isInstanceOf(ConceptSchemeSuperBean.class);
    }

}
