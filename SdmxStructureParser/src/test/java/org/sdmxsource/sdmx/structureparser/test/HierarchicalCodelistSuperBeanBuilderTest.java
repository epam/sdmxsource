package org.sdmxsource.sdmx.structureparser.test;

import org.junit.jupiter.api.Test;
import org.sdmxsource.sdmx.api.manager.retrieval.IdentifiableRetrievalManager;
import org.sdmxsource.sdmx.api.model.superbeans.codelist.HierarchicalCodelistSuperBean;
import org.sdmxsource.sdmx.structureparser.builder.superbeans.impl.HierarchicalCodelistSuperBeanBuilder;
import org.sdmxsource.sdmx.structureretrieval.manager.IdentifiableRetrievalManagerImpl;
import org.sdmxsource.sdmx.structureretrieval.manager.InMemoryRetrievalManager;
import org.sdmxsource.sdmx.util.beans.container.SdmxBeansImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.sdmxsource.sdmx.structureparser.data.DataHelper.getCodelistsForHierarchical;
import static org.sdmxsource.sdmx.structureparser.data.DataHelper.getHierarchicalCodelist;

public class HierarchicalCodelistSuperBeanBuilderTest {

    @Test
    public void shouldCheckHierarchicalCodelistSuperBeanBuilder() {
        var sdmxBeans = new SdmxBeansImpl();
        getCodelistsForHierarchical().forEach(sdmxBeans::addCodelist);
        IdentifiableRetrievalManager identifiableRetrievalManager = new IdentifiableRetrievalManagerImpl(new InMemoryRetrievalManager(sdmxBeans));
        var sut = new HierarchicalCodelistSuperBeanBuilder();
        var hierarchicalCodelistSuperBean = sut.build(getHierarchicalCodelist(), identifiableRetrievalManager);

        assertThat(hierarchicalCodelistSuperBean).isInstanceOf(HierarchicalCodelistSuperBean.class);
    }
}
