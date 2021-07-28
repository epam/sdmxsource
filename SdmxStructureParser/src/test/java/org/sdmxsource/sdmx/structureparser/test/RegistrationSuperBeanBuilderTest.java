package org.sdmxsource.sdmx.structureparser.test;

import org.junit.jupiter.api.Test;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.superbeans.registry.RegistrationSuperBean;
import org.sdmxsource.sdmx.structureparser.builder.superbeans.impl.RegistrationSuperBeanBuilder;
import org.sdmxsource.sdmx.structureretrieval.manager.InMemoryRetrievalManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.sdmxsource.sdmx.structureparser.data.DataHelper.getRegistration;
import static org.sdmxsource.sdmx.structureparser.data.DataHelper.getStructures;

public class RegistrationSuperBeanBuilderTest {

    @Test
    public void shouldCheckRegistrationSuperBeanBuilder() {
        SdmxBeanRetrievalManager retrievalManager = new InMemoryRetrievalManager(getStructures("ProvisionAgreement.xml"));
        var sut = new RegistrationSuperBeanBuilder();
        var registrationSuperBean = sut.build(getRegistration(), retrievalManager, null);

        assertThat(registrationSuperBean).isInstanceOf(RegistrationSuperBean.class);
    }
}
