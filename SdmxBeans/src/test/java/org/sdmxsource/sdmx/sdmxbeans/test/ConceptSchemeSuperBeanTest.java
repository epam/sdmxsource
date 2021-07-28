package org.sdmxsource.sdmx.sdmxbeans.test;

import org.junit.jupiter.api.Test;
import org.sdmxsource.sdmx.api.model.beans.conceptscheme.ConceptBean;
import org.sdmxsource.sdmx.api.model.beans.conceptscheme.ConceptSchemeBean;
import org.sdmxsource.sdmx.api.model.superbeans.codelist.CodelistSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.conceptscheme.ConceptSchemeSuperBean;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.codelist.CodelistSuperBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.conceptscheme.ConceptSchemeSuperBeanImpl;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.sdmxsource.sdmx.sdmxbeans.data.DataHelper.buildCodelist;
import static org.sdmxsource.sdmx.sdmxbeans.data.DataHelper.buildConceptScheme;

public class ConceptSchemeSuperBeanTest {

    @Test
    public void shouldCheckConceptSchemeSuperBean() {
        ConceptSchemeBean conceptSchemeBean = buildConceptScheme().get(0);
        CodelistSuperBean codelistSuperBean = new CodelistSuperBeanImpl(buildCodelist(10).getImmutableInstance());
        Map<ConceptBean, CodelistSuperBean> representations = new HashMap<>();
        conceptSchemeBean.getItems().forEach(item -> representations.put(item, codelistSuperBean));
        ConceptSchemeSuperBean conceptSchemeSuperBean = new ConceptSchemeSuperBeanImpl(conceptSchemeBean, representations);

        assertEquals(conceptSchemeSuperBean.getConcepts().size(), conceptSchemeBean.getItems().size());
    }
}
