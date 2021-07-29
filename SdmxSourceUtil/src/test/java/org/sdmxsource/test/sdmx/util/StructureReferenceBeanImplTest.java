package org.sdmxsource.test.sdmx.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.util.beans.reference.StructureReferenceBeanImpl;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class StructureReferenceBeanImplTest {

    @ParameterizedTest
    @MethodSource("org.sdmxsource.data.DataProvider#provideStructureReferenceParams")
    public void shouldCheckStructureReference(String agency, String id, String version) {
        Arrays.stream(SDMX_STRUCTURE_TYPE.values()).forEach(sdmxStructureType -> {
            if (sdmxStructureType.isMaintainable()) {
                var cross = new StructureReferenceBeanImpl(agency, id, version, sdmxStructureType);

                assertEquals(agency, cross.getAgencyId());
                assertEquals(id, cross.getMaintainableId());
                assertEquals(getVersion(version), cross.getVersion());
            } else if (sdmxStructureType.isIdentifiable() && !sdmxStructureType.hasFixedId()) {
                var cross = new StructureReferenceBeanImpl(
                        agency, id, version, sdmxStructureType, "ENA", "DYO", "TRIA");
                assertEquals(agency, cross.getAgencyId());
                assertEquals(id, cross.getMaintainableId());
                assertEquals(getVersion(version), cross.getVersion());
                assertNotEquals(0, cross.getIdentifiableIds().length);
            }
        });
    }

    @ParameterizedTest
    @MethodSource("org.sdmxsource.data.DataProvider#provideStructureReferenceUrnParams")
    public void shouldCheckStructureReferenceWithUrn(String urn, SDMX_STRUCTURE_TYPE expectedResult,
                                                     boolean hasChildReference) {
        var impl = new StructureReferenceBeanImpl(urn);
        int identifiableIdsCount = impl.getIdentifiableIds() == null ? 0 : impl.getIdentifiableIds().length;

        assertTrue(impl.hasAgencyId());
        assertTrue(impl.hasTargetUrn());
        assertTrue(impl.hasMaintainableId());
        assertEquals(urn, impl.getTargetUrn());
        assertEquals(expectedResult, impl.getTargetStructureType());
        assertEquals(hasChildReference, impl.hasChildReference());
        assertEquals(hasChildReference, identifiableIdsCount > 0);
        assertEquals(impl.getTargetUrn(), impl.createCopy().getTargetUrn());
        assertEquals(impl.getTargetReference(), impl.createCopy().getTargetReference());
    }

    private String getVersion(String version) {
        return version == null || version.isBlank() ? null : version;
    }

}
