package org.sdmxsource.sdmx.sdmxbeans.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sdmxsource.sdmx.api.model.beans.conceptscheme.ConceptBean;
import org.sdmxsource.sdmx.api.model.superbeans.codelist.CodelistSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.conceptscheme.ConceptSuperBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutablesuperbeans.conceptscheme.ConceptMutableSuperBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.codelist.CodelistSuperBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.conceptscheme.ConceptSuperBeanImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.sdmxsource.sdmx.sdmxbeans.data.DataHelper.buildCodelist;
import static org.sdmxsource.sdmx.sdmxbeans.data.DataHelper.buildConceptScheme;

public class ConceptSuperBeanTest {

    private ConceptBean conceptBean;
    private ConceptSuperBean conceptSuperBean;

    @BeforeEach
    public void setup() {
        CodelistSuperBean codelistSuperBean = new CodelistSuperBeanImpl(buildCodelist(10).getImmutableInstance());
        conceptBean = buildConceptScheme().get(0).getItems().get(0);
        conceptSuperBean = new ConceptSuperBeanImpl(conceptBean, codelistSuperBean);
    }

    @Test
    public void shouldCheckConceptSuperBean() {
        assertEquals(conceptSuperBean.getUrn(), conceptBean.getUrn());
    }

    @Test
    public void shouldCheckConceptMutableSuperBean() {
        var conceptMutableSuperBean = new ConceptMutableSuperBeanImpl(conceptSuperBean);

        assertEquals(conceptMutableSuperBean.getId(), conceptBean.getId());
    }

}
