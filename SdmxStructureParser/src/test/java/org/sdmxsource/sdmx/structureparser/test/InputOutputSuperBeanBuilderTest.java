package org.sdmxsource.sdmx.structureparser.test;

import org.junit.jupiter.api.Test;
import org.sdmxsource.sdmx.api.manager.retrieval.IdentifiableRetrievalManager;
import org.sdmxsource.sdmx.api.model.superbeans.process.InputOutputSuperBean;
import org.sdmxsource.sdmx.structureparser.builder.superbeans.impl.InputOutputSuperBeanBuilder;
import org.sdmxsource.sdmx.structureretrieval.manager.IdentifiableRetrievalManagerImpl;
import org.sdmxsource.sdmx.structureretrieval.manager.InMemoryRetrievalManager;
import org.sdmxsource.sdmx.util.beans.container.SdmxBeansImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.sdmxsource.sdmx.structureparser.data.DataHelper.*;

public class InputOutputSuperBeanBuilderTest {

    @Test
    public void shouldCheckInputOutputSuperBeanBuilder() {
        var sdmxBeans = new SdmxBeansImpl();
        getCodelistsForHierarchical().forEach(sdmxBeans::addCodelist);
        IdentifiableRetrievalManager identifiableRetrievalManager =
                new IdentifiableRetrievalManagerImpl(new InMemoryRetrievalManager(getStructures()));
        var sut = new InputOutputSuperBeanBuilder();
        var inputOutputSuperBean = sut.build(getProcessSteps().get(0).getInput().get(0), identifiableRetrievalManager);

        assertThat(inputOutputSuperBean).isInstanceOf(InputOutputSuperBean.class);
    }
}
