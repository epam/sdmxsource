package org.sdmxsource.sdmx.sdmxbeans.test;

import org.junit.jupiter.api.Test;
import org.sdmxsource.sdmx.api.constants.DATA_QUERY_DETAIL;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataflowBean;
import org.sdmxsource.sdmx.api.model.data.query.DataQuery;
import org.sdmxsource.sdmx.api.model.mutable.base.RepresentationMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.datastructure.DataStructureMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.datastructure.DimensionMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.data.query.DataQueryImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.RepresentationMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.datastructure.DataStructureMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.datastructure.DimensionMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.metadatastructure.DataflowMutableBeanImpl;
import org.sdmxsource.sdmx.util.beans.reference.StructureReferenceBeanImpl;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE.CODE_LIST;
import static org.sdmxsource.sdmx.sdmxbeans.data.DataHelper.getConceptStructureReferenceBean;

public class DataQueryTest {

    @Test
    public void shouldCheckDimensionAtObservation() {
        DataStructureMutableBean dsd = new DataStructureMutableBeanImpl();
        dsd.setId("TEST");
        dsd.setAgencyId("TEST_AGENCY");
        dsd.setVersion("1.0");
        dsd.addName("en", "Test name");
        dsd.addDimension(getDimensionMutableBean());
        dsd.addPrimaryMeasure(getConceptStructureReferenceBean("OBS_VALUE"));

        var immutableDsd = dsd.getImmutableInstance();
        var dataflow = getDataflowBean(immutableDsd);

        DataQuery query = new DataQueryImpl(immutableDsd, null, DATA_QUERY_DETAIL.FULL, null, null, null, dataflow, "AllDimensions", new HashSet<>(), null, null);

        assertEquals("AllDimensions", query.dimensionAtObservation());
    }

    private DimensionMutableBean getDimensionMutableBean() {
        DimensionMutableBean dimension = new DimensionMutableBeanImpl();
        dimension.setConceptRef(getConceptStructureReferenceBean("TEST_DIM"));
        RepresentationMutableBean representationMutableBean = new RepresentationMutableBeanImpl();
        representationMutableBean.setRepresentation(new StructureReferenceBeanImpl("TEST_AGENCY", "CL_TEST", "2.0", CODE_LIST));
        dimension.setRepresentation(representationMutableBean);
        return dimension;
    }

    private DataflowBean getDataflowBean(DataStructureBean immutableDsd) {
        var dataflowMutable = new DataflowMutableBeanImpl();
        dataflowMutable.setId("TEST_DF");
        dataflowMutable.setAgencyId("TEST_AGENCY");
        dataflowMutable.setVersion("1.2");
        dataflowMutable.addName("en", "Test");
        dataflowMutable.setDataStructureRef(immutableDsd.asReference());
        return dataflowMutable.getImmutableInstance();
    }

}
