package org.sdmxsource.sdmx.sdmxbeans.util;


import org.junit.jupiter.api.Test;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.util.beans.reference.StructureReferenceBeanImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RoleReferenceUtilTest {

    @Test
    void shouldReturnReferenceToFrequencyRole() {
        StructureReferenceBean frequencyRoleReference = RoleReferenceUtil.createFrequencyRoleReference();

        assertEquals("SDMX_CONCEPT_ROLES", frequencyRoleReference.getMaintainableId());
        assertEquals("SDMX", frequencyRoleReference.getAgencyId());
        assertEquals("1.0", frequencyRoleReference.getVersion());
        assertEquals("FREQ", frequencyRoleReference.getChildReference().getId());
    }

    @Test
    void shouldCheckWhetherReferenceIsReferringToFrequencyRoleFalse() {
        var ref = new StructureReferenceBeanImpl("foo", "bar", "1.0", SDMX_STRUCTURE_TYPE.CONCEPT, "FREQ");

        assertFalse(RoleReferenceUtil.isFrequency(ref));
    }

    @Test
    void shouldCheckWhetherReferenceIsReferringToFrequencyRoleTrue() {
        var ref = new StructureReferenceBeanImpl("SDMX", "SDMX_CONCEPT_ROLES", "1.0", SDMX_STRUCTURE_TYPE.CONCEPT, "FREQ");

        assertTrue(RoleReferenceUtil.isFrequency(ref));
    }
}