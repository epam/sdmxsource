package org.sdmxsource.sdmx.structureparser.test;

import org.junit.jupiter.api.Test;
import org.sdmxsource.sdmx.api.manager.retrieval.IdentifiableRetrievalManager;
import org.sdmxsource.sdmx.api.model.superbeans.process.ProcessStepSuperBean;
import org.sdmxsource.sdmx.structureparser.builder.superbeans.impl.ProcessStepSuperBeanBuilder;
import org.sdmxsource.sdmx.structureretrieval.manager.IdentifiableRetrievalManagerImpl;
import org.sdmxsource.sdmx.structureretrieval.manager.InMemoryRetrievalManager;
import org.sdmxsource.sdmx.util.beans.container.SdmxBeansImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.sdmxsource.sdmx.structureparser.data.DataHelper.*;

public class ProcessStepSuperBeanBuilderTest {

    @Test
    public void shouldCheckProcessStepSuperBeanBuilder() {
        var sdmxBeans = new SdmxBeansImpl();
        getCodelistsForHierarchical().forEach(sdmxBeans::addCodelist);
        IdentifiableRetrievalManager identifiableRetrievalManager =
                new IdentifiableRetrievalManagerImpl(new InMemoryRetrievalManager(getStructures()));
        var sut = new ProcessStepSuperBeanBuilder();
        var processStepSuperBean = sut.build(getProcessSteps().get(0), identifiableRetrievalManager);

        assertThat(processStepSuperBean).isInstanceOf(ProcessStepSuperBean.class);
    }
}
