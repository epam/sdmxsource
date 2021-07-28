package org.sdmxsource.sdmx.structureparser.test;

import org.junit.jupiter.api.Test;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.superbeans.conceptscheme.ConceptSuperBean;
import org.sdmxsource.sdmx.structureparser.builder.superbeans.impl.ConceptSuperBeanBuilder;
import org.sdmxsource.sdmx.structureretrieval.manager.InMemoryRetrievalManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.sdmxsource.sdmx.structureparser.data.DataHelper.getConceptScheme;
import static org.sdmxsource.sdmx.structureparser.data.DataHelper.getStructures;

public class ConceptSuperBeanBuilderTest {

    @Test
    public void shouldCheckConceptSuperBeanBuilder() {
        var sut = new ConceptSuperBeanBuilder();
        SdmxBeanRetrievalManager retrievalManager = new InMemoryRetrievalManager(getStructures());
        var conceptSuperBean = sut.build(getConceptScheme().getItems().get(0), retrievalManager, null);

        assertThat(conceptSuperBean).isInstanceOf(ConceptSuperBean.class);
    }


}
