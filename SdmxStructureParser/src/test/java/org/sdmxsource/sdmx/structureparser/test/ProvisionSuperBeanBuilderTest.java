package org.sdmxsource.sdmx.structureparser.test;

import org.junit.jupiter.api.Test;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.superbeans.registry.ProvisionAgreementSuperBean;
import org.sdmxsource.sdmx.structureparser.builder.superbeans.impl.ProvisionSuperBeanBuilder;
import org.sdmxsource.sdmx.structureretrieval.manager.InMemoryRetrievalManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.sdmxsource.sdmx.structureparser.data.DataHelper.getProvisionAgreement;
import static org.sdmxsource.sdmx.structureparser.data.DataHelper.getStructures;

public class ProvisionSuperBeanBuilderTest {

    @Test
    public void shouldCheckProvisionSuperBeanBuilder() {
        SdmxBeanRetrievalManager retrievalManager = new InMemoryRetrievalManager(getStructures("ProvisionAgreement.xml"));
        var sut = new ProvisionSuperBeanBuilder();
        var provisionSuperBean = sut.build(getProvisionAgreement(), retrievalManager, null);

        assertThat(provisionSuperBean).isInstanceOf(ProvisionAgreementSuperBean.class);
    }
}
