package org.sdmxsource.sdmx.sdmxbeans.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sdmxsource.sdmx.api.model.beans.datastructure.PrimaryMeasureBean;
import org.sdmxsource.sdmx.api.model.mutablesuperbeans.datastructure.PrimaryMeasureMutableSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.codelist.CodelistSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.conceptscheme.ConceptSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.PrimaryMeasureSuperBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutablesuperbeans.datastructure.PrimaryMeasureMutableSuperBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.codelist.CodelistSuperBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.conceptscheme.ConceptSuperBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.datastructure.PrimaryMeasureSuperBeanImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.sdmxsource.sdmx.sdmxbeans.data.DataHelper.*;

public class PrimaryMeasureSuperBeanTest {

    private PrimaryMeasureSuperBean primaryMeasureSuperBean;
    private PrimaryMeasureBean primaryMeasure;
    private CodelistSuperBean codelist;

    @BeforeEach
    public void setup() {
        codelist = new CodelistSuperBeanImpl(buildCodelist(10).getImmutableInstance());
        primaryMeasure = buildDsd().getPrimaryMeasure();
        ConceptSuperBean concept = new ConceptSuperBeanImpl(buildConceptScheme().get(0).getItems().get(0), codelist);
        primaryMeasureSuperBean = new PrimaryMeasureSuperBeanImpl(primaryMeasure, codelist, concept);
    }

    @Test
    public void shouldCheckPrimaryMeasureSuperBean() {
        assertEquals(primaryMeasure.getId(), primaryMeasureSuperBean.getId());
        assertEquals(primaryMeasure, primaryMeasureSuperBean.getBuiltFrom());
    }

    @Test
    public void shouldCheckPrimaryMeasureMutableSuperBean() {
        PrimaryMeasureMutableSuperBean primaryMeasureMutableBean =
                new PrimaryMeasureMutableSuperBeanImpl(primaryMeasureSuperBean);

        assertEquals(primaryMeasure.getId(), primaryMeasureMutableBean.getId());
        assertEquals(codelist.getCodes().size(), primaryMeasureMutableBean.getCodelistBean().getCodes().size());
    }
}
