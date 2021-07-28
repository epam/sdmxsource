package org.sdmxsource.sdmx.structureretrieval;

import org.junit.jupiter.api.Test;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.api.model.beans.base.IdentifiableBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.mutable.datastructure.AttributeMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.datastructure.DimensionMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.datastructure.DataStructureMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.datastructure.DimensionMutableBeanImpl;
import org.sdmxsource.sdmx.structureretrieval.engine.CrossReferenceResolverEngine;
import org.sdmxsource.sdmx.structureretrieval.engine.impl.CrossReferenceResolverEngineImpl;
import org.sdmxsource.sdmx.structureretrieval.manager.InMemoryRetrievalManager;
import org.sdmxsource.sdmx.util.beans.container.SdmxBeansImpl;
import org.sdmxsource.sdmx.util.beans.reference.StructureReferenceBeanImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.sdmxsource.sdmx.api.constants.ATTRIBUTE_ATTACHMENT_LEVEL.DIMENSION_GROUP;
import static org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE.CODE_LIST;
import static org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE.CONCEPT;

public class CrossReferenceResolverEngineTest {

    private final static String FREQ = "FREQ";
    private final static String ADJUSTMENT = "ADJUSTMENT";
    private final static String STS_ACTIVITY = "STS_ACTIVITY";

    @Test
    public void shouldCheckGetMissingCrossReferences() {
        CrossReferenceResolverEngine crossReferenceResolverEngine = new CrossReferenceResolverEngineImpl();
        InMemoryRetrievalManager retrievalManager = new InMemoryRetrievalManager();
        var dataStructureBean = buildDsd();
        SdmxBeans sdmxBeans = new SdmxBeansImpl(dataStructureBean);
        Map<IdentifiableBean, Set<CrossReferenceBean>> missingCrossRef = crossReferenceResolverEngine
                .getMissingCrossReferences(sdmxBeans, 10, retrievalManager);

        assertFalse(missingCrossRef.isEmpty());
        List<String> keyUrnList = missingCrossRef.keySet()
                .stream()
                .map(IdentifiableBean::getUrn)
                .collect(toList());
        assertTrue(keyUrnList.contains(dataStructureBean.getPrimaryMeasure().getUrn()));
        assertTrue(keyUrnList.contains(dataStructureBean.getTimeDimension().getUrn()));
    }

    private DataStructureBean buildDsd() {
        var dsdMutableObject = dsdMutableObject();
        dsdMutableObject.addDimension(getConceptStructureReferenceBean(FREQ),
                getCodeListStructureReferenceBean("SDMX", "CL_FREQ"));
        dsdMutableObject.addDimension(getConceptStructureReferenceBean(ADJUSTMENT),
                getCodeListStructureReferenceBean("SDMX", "CL_ADJUSTMENT"));
        dsdMutableObject.addDimension(getConceptStructureReferenceBean(STS_ACTIVITY),
                getCodeListStructureReferenceBean("STS", "CL_STS_ACTIVITY"));

        DimensionMutableBean dimensionMutableBean = new DimensionMutableBeanImpl();
        dimensionMutableBean.setConceptRef(getConceptStructureReferenceBean("TIME_PERIOD"));
        dimensionMutableBean.setTimeDimension(true);
        dsdMutableObject.addDimension(dimensionMutableBean);
        dsdMutableObject.addPrimaryMeasure(getConceptStructureReferenceBean("OBS_VALUE"));

        AttributeMutableBean attributeMutableObject = dsdMutableObject.addAttribute(
                getConceptStructureReferenceBean("DECIMALS"),
                getCodeListStructureReferenceBean("STS", "CL_DECIMALS"));
        attributeMutableObject.setAttachmentLevel(DIMENSION_GROUP);
        attributeMutableObject.setDimensionReferences(Arrays.asList(FREQ, ADJUSTMENT, STS_ACTIVITY));
        attributeMutableObject.setAssignmentStatus("Mandatory");
        return dsdMutableObject.getImmutableInstance();
    }

    private DataStructureMutableBeanImpl dsdMutableObject() {
        var dsdMutableObject = new DataStructureMutableBeanImpl();
        dsdMutableObject.setAgencyId("TEST");
        dsdMutableObject.setId("TEST_DSD");
        dsdMutableObject.setVersion("1.0");
        dsdMutableObject.addName("en", "Test data");
        return dsdMutableObject;
    }

    private StructureReferenceBeanImpl getConceptStructureReferenceBean(String identfiableId) {
        return new StructureReferenceBeanImpl("TEST", "TEST_CS", "1.0", CONCEPT, identfiableId);
    }

    private StructureReferenceBeanImpl getCodeListStructureReferenceBean(String agencyId, String maintainableId) {
        return new StructureReferenceBeanImpl(agencyId, maintainableId, "1.0", CODE_LIST);
    }

}
