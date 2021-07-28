package org.sdmxsource.test.sdmx.util;

import org.junit.jupiter.api.Test;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.reference.IdentifiableRefBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.util.beans.reference.IdentifiableRefBeanImpl;
import org.sdmxsource.sdmx.util.beans.reference.StructureReferenceBeanImpl;

import java.util.Arrays;
import java.util.function.Predicate;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

public class IdentifiableRefBeanImplTest {

    private static String getId(SDMX_STRUCTURE_TYPE targetObj) {
        return targetObj.hasFixedId() ? targetObj.getFixedId() : "TEST";
    }

    @Test
    public void shouldCheckIdentifiableRefBeanImpl() {
        Arrays.stream(SDMX_STRUCTURE_TYPE.values())
                .filter(filterIdentifiableStructureTypes())
                .forEach(sdmxStructureType -> {
                            var result = new IdentifiableRefBeanImpl(getStructureReferenceBean(sdmxStructureType),
                                    getId(sdmxStructureType), sdmxStructureType);

                            assertEquals(result.getStructureType(), sdmxStructureType);
                            assertEquals(result.getId(), getId(sdmxStructureType));
                            assertNull(result.getChildReference());
                            assertNull(result.getParentIdentifiableReference());
                            assertEquals(result.getParentMaintainableReferece().getMaintainableStructureType(),
                                    sdmxStructureType.getParentStructureType());
                        }
                );
    }

    @Test
    public void shouldCheckIdentifiableRefBeanImplForTypeWithoutFixedId() {
        Arrays.stream(SDMX_STRUCTURE_TYPE.values())
                .filter(filterIdentifiableStructureTypesWithoutFixedId())
                .forEach(sdmxStructureType -> {
                            var ids = asList("1", "2", "A", "B");
                            var result = new IdentifiableRefBeanImpl(getStructureReferenceBean(sdmxStructureType),
                                    ids, sdmxStructureType);

                            assertEquals(result.getStructureType(), sdmxStructureType);
                            assertEquals("1", result.getId());
                            assertEquals("2", result.getChildReference().getId());
                            assertNotNull(result.getChildReference().getParentIdentifiableReference());
                            assertNull(result.getParentIdentifiableReference());
                            assertEquals(result.getParentMaintainableReferece().getMaintainableStructureType(),
                                    sdmxStructureType.getParentStructureType());
                            IdentifiableRefBean child = result;
                            for (String id : ids) {
                                assertEquals(id, child.getId());
                                child = child.getChildReference();
                            }
                        }
                );
    }

    private Predicate<SDMX_STRUCTURE_TYPE> filterIdentifiableStructureTypes() {
        return type -> type.isIdentifiable() && type.getParentStructureType() != null &&
                type.getParentStructureType().isMaintainable();
    }

    private Predicate<SDMX_STRUCTURE_TYPE> filterIdentifiableStructureTypesWithoutFixedId() {
        return type -> type.isIdentifiable() && !type.hasFixedId() && type.getParentStructureType() != null &&
                type.getParentStructureType().isMaintainable();
    }

    private StructureReferenceBean getStructureReferenceBean(SDMX_STRUCTURE_TYPE sdmxStructureType) {
        String urn = sdmxStructureType.getParentStructureType().generateUrn("AGENCY", "ID", "2.2");
        return new StructureReferenceBeanImpl(urn);
    }
}
