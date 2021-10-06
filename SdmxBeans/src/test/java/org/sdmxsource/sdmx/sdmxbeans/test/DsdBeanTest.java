package org.sdmxsource.sdmx.sdmxbeans.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.sdmxsource.sdmx.api.constants.TEXT_TYPE;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DimensionBean;
import org.sdmxsource.sdmx.api.model.mutable.base.RepresentationMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.base.TextFormatMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.datastructure.DataStructureMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.datastructure.DimensionMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.RepresentationMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.TextFormatMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.datastructure.DataStructureMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.datastructure.DimensionMutableBeanImpl;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.sdmxsource.sdmx.sdmxbeans.data.DataHelper.getConceptStructureReferenceBean;

public class DsdBeanTest {

    @ParameterizedTest
    @MethodSource("org.sdmxsource.sdmx.sdmxbeans.data.DataProvider#provideFreqDimensionBeans")
    public void shouldCheckFreqDimension(DimensionMutableBean dimension) {
        var immutable = buildDataStructureBean(dimension);

        assertNotNull(immutable.getFrequencyDimension());
    }

    @Test
    public void shouldReturnFreqDimensionWhenDimensionWithCorrespondingRolePresent() {
        var immutable = buildDataStructureBean(dimensionBean("beanId", "FREQ"));

        DimensionBean frequencyDimension = immutable.getFrequencyDimension();

        assertEquals("beanId", frequencyDimension.getId());
    }

    @Test
    public void shouldNotReturnFreqDimensionWhenCorrespondingRoleMissing() {
        var immutable = buildDataStructureBean(dimensionBean("beanId", "VAR"));

        assertNull(immutable.getFrequencyDimension());
    }

    @Test
    public void shouldCheckUnCodeTimeDimension() {
        DimensionMutableBean dimension = new DimensionMutableBeanImpl();
        dimension.setTimeDimension(true);
        dimension.setId(DimensionBean.TIME_DIMENSION_FIXED_ID);
        dimension.setConceptRef(getConceptStructureReferenceBean("TIME_PERIOD"));

        RepresentationMutableBean representationMutableBean = new RepresentationMutableBeanImpl();
        TextFormatMutableBean textFormatMutableBean = new TextFormatMutableBeanImpl();
        textFormatMutableBean.setTextType(TEXT_TYPE.TIME_PERIOD);
        representationMutableBean.setTextFormat(textFormatMutableBean);

        dimension.setRepresentation(representationMutableBean);
        var immutable = buildDataStructureBean(dimension);

        assertNotNull(immutable.getTimeDimension());
        assertFalse(immutable.getTimeDimension().hasCodedRepresentation());
        assertNotNull(immutable.getTimeDimension().getRepresentation());
        assertNotNull(immutable.getTimeDimension().getRepresentation().getTextFormat());
    }

    private DataStructureBean buildDataStructureBean(DimensionMutableBean dimension) {
        DataStructureMutableBean dsd = new DataStructureMutableBeanImpl();
        dsd.setId("TEST_DSD");
        dsd.setAgencyId("TEST");
        dsd.setVersion("1.0");
        dsd.addName("en", "TEST_DSD");
        dsd.addPrimaryMeasure(getConceptStructureReferenceBean("OBS_VALUE"));
        dsd.addDimension(dimension);
        return dsd.getImmutableInstance();
    }

    private DimensionMutableBean dimensionBean(String id, String roleId) {
        DimensionMutableBean dimension = new DimensionMutableBeanImpl();
        dimension.setId(id);
        dimension.setConceptRole(singletonList(getConceptStructureReferenceBean(roleId)));
        dimension.setConceptRef(getConceptStructureReferenceBean("FREQ"));
        return dimension;
    }

}
