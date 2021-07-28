package org.sdmxsource.sdmx.sdmxbeans.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DimensionBean;
import org.sdmxsource.sdmx.api.model.superbeans.codelist.CodelistSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.conceptscheme.ConceptSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.DimensionSuperBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutablesuperbeans.datastructure.DimensionMutableSuperBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.codelist.CodelistSuperBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.conceptscheme.ConceptSuperBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.datastructure.DimensionSuperBeanImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.sdmxsource.sdmx.sdmxbeans.data.DataHelper.*;

public class DimensionSuperBeanTest {

    private DimensionSuperBean dimensionSuperBean;
    private DimensionBean dimension;

    @BeforeEach
    public void setup() {
        CodelistSuperBean codelist = new CodelistSuperBeanImpl(buildCodelist(10).getImmutableInstance());
        dimension = buildDsd().getDimensionList().getDimensions().get(0);
        ConceptSuperBean concept = new ConceptSuperBeanImpl(buildConceptScheme().get(0).getItems().get(0), codelist);
        dimensionSuperBean = new DimensionSuperBeanImpl(dimension, codelist, concept, null);
    }

    @Test
    public void shouldCheckDimensionSuperBean() {
        assertEquals(dimensionSuperBean.isTimeDimension(), dimension.isTimeDimension());
        assertEquals(dimensionSuperBean.isFrequencyDimension(), dimension.isFrequencyDimension());
        assertEquals(dimensionSuperBean.isMeasureDimension(), dimension.isMeasureDimension());
    }

    @Test
    public void shouldCheckDimensionMutableSuperBean() {
        var dimensionMutableSuperBean = new DimensionMutableSuperBeanImpl(dimensionSuperBean);

        assertEquals(dimensionMutableSuperBean.getMeasureDimension(), dimension.isMeasureDimension());
        assertEquals(dimensionMutableSuperBean.getFrequencyDimension(), dimension.isFrequencyDimension());
    }
}
