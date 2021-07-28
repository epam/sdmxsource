package org.sdmxsource.sdmx.structureparser.test;

import org.junit.jupiter.api.Test;
import org.sdmxsource.sdmx.api.manager.retrieval.IdentifiableRetrievalManager;
import org.sdmxsource.sdmx.api.model.superbeans.process.ProcessSuperBean;
import org.sdmxsource.sdmx.structureparser.builder.superbeans.impl.ProcessSuperBeanBuilder;
import org.sdmxsource.sdmx.structureretrieval.manager.IdentifiableRetrievalManagerImpl;
import org.sdmxsource.sdmx.structureretrieval.manager.InMemoryRetrievalManager;
import org.sdmxsource.sdmx.util.beans.container.SdmxBeansImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.sdmxsource.sdmx.structureparser.data.DataHelper.*;

public class ProcessSuperBeanBuilderTest {

    @Test
    public void shouldCheckProcessSuperBeanBuilder() {
        var sdmxBeans = new SdmxBeansImpl();
        getCodelistsForHierarchical().forEach(sdmxBeans::addCodelist);
        IdentifiableRetrievalManager identifiableRetrievalManager =
                new IdentifiableRetrievalManagerImpl(new InMemoryRetrievalManager(getStructures()));
        var sut = new ProcessSuperBeanBuilder();
        var processSuperBean = sut.build(getProcess(), identifiableRetrievalManager);

        assertThat(processSuperBean).isInstanceOf(ProcessSuperBean.class);
    }
}
