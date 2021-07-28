package org.sdmxsource.test.sdmx.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DimensionBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.reference.IdentifiableRefBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.util.beans.ConceptRefUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE.CONCEPT;

@ExtendWith(MockitoExtension.class)
public class ConceptRefUtilTest {

    @Mock
    private DimensionBean dimension;
    @Mock
    private StructureReferenceBean structureReferenceBean;
    @Mock
    private IdentifiableRefBean identifiableRefBean;

    @Test
    public void shouldCheckBuildConceptRef() {
        CrossReferenceBean buildConceptRef = ConceptRefUtil
                .buildConceptRef(dimension, "AGENCYTEST", "TESTCS", "1.2", "AGENCYTEST", "CAT1");

        assertNotNull(buildConceptRef);
        assertNotNull(buildConceptRef.getChildReference());
        assertEquals(CONCEPT, buildConceptRef.getChildReference().getStructureType());
        assertEquals("urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=AGENCYTEST:TESTCS(1.2).CAT1",
                buildConceptRef.getTargetUrn());
    }

    @Test
    public void shouldCheckGetConceptId() {
        String mockedIdValue = "CAT1";
        when(identifiableRefBean.getId()).thenReturn(mockedIdValue);
        when(structureReferenceBean.getChildReference()).thenReturn(identifiableRefBean);
        when(structureReferenceBean.getTargetReference()).thenReturn(CONCEPT);

        assertEquals(mockedIdValue, ConceptRefUtil.getConceptId(structureReferenceBean));
    }
}
