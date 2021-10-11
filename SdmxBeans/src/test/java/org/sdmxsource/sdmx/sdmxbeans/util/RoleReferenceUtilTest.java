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
    void shouldReturnReferenceToCommentRole() {
        StructureReferenceBean frequencyRoleReference = RoleReferenceUtil.createCommentRoleReference();

        assertEquals("SDMX_CONCEPT_ROLES", frequencyRoleReference.getMaintainableId());
        assertEquals("SDMX", frequencyRoleReference.getAgencyId());
        assertEquals("1.0", frequencyRoleReference.getVersion());
        assertEquals("COMMENT", frequencyRoleReference.getChildReference().getId());
    }

    @Test
    void shouldReturnReferenceToEntityRole() {
        StructureReferenceBean frequencyRoleReference = RoleReferenceUtil.createEntityRoleReference();

        assertEquals("SDMX_CONCEPT_ROLES", frequencyRoleReference.getMaintainableId());
        assertEquals("SDMX", frequencyRoleReference.getAgencyId());
        assertEquals("1.0", frequencyRoleReference.getVersion());
        assertEquals("ENTITY", frequencyRoleReference.getChildReference().getId());
    }

    @Test
    void shouldReturnReferenceToFlagRole() {
        StructureReferenceBean frequencyRoleReference = RoleReferenceUtil.createFlagRoleReference();

        assertEquals("SDMX_CONCEPT_ROLES", frequencyRoleReference.getMaintainableId());
        assertEquals("SDMX", frequencyRoleReference.getAgencyId());
        assertEquals("1.0", frequencyRoleReference.getVersion());
        assertEquals("FLAG", frequencyRoleReference.getChildReference().getId());
    }

    @Test
    void shouldReturnReferenceToGeoRole() {
        StructureReferenceBean frequencyRoleReference = RoleReferenceUtil.createGeoRoleReference();

        assertEquals("SDMX_CONCEPT_ROLES", frequencyRoleReference.getMaintainableId());
        assertEquals("SDMX", frequencyRoleReference.getAgencyId());
        assertEquals("1.0", frequencyRoleReference.getVersion());
        assertEquals("GEO", frequencyRoleReference.getChildReference().getId());
    }

    @Test
    void shouldReturnReferenceToOperationRole() {
        StructureReferenceBean frequencyRoleReference = RoleReferenceUtil.createOperationRoleReference();

        assertEquals("SDMX_CONCEPT_ROLES", frequencyRoleReference.getMaintainableId());
        assertEquals("SDMX", frequencyRoleReference.getAgencyId());
        assertEquals("1.0", frequencyRoleReference.getVersion());
        assertEquals("OPERATION", frequencyRoleReference.getChildReference().getId());
    }

    @Test
    void shouldReturnReferenceToVariableRole() {
        StructureReferenceBean frequencyRoleReference = RoleReferenceUtil.createVariableRoleReference();

        assertEquals("SDMX_CONCEPT_ROLES", frequencyRoleReference.getMaintainableId());
        assertEquals("SDMX", frequencyRoleReference.getAgencyId());
        assertEquals("1.0", frequencyRoleReference.getVersion());
        assertEquals("VARIABLE", frequencyRoleReference.getChildReference().getId());
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

    @Test
    void shouldCheckWhetherReferenceIsReferringToCommentRoleFalse() {
        var ref = new StructureReferenceBeanImpl("foo", "bar", "1.0", SDMX_STRUCTURE_TYPE.CONCEPT, "COMMENT");

        assertFalse(RoleReferenceUtil.isComment(ref));
    }

    @Test
    void shouldCheckWhetherReferenceIsReferringToCommentRoleTrue() {
        var ref = new StructureReferenceBeanImpl("SDMX", "SDMX_CONCEPT_ROLES", "1.0", SDMX_STRUCTURE_TYPE.CONCEPT, "COMMENT");

        assertTrue(RoleReferenceUtil.isComment(ref));
    }

    @Test
    void shouldCheckWhetherReferenceIsReferringToEntityRoleFalse() {
        var ref = new StructureReferenceBeanImpl("foo", "bar", "1.0", SDMX_STRUCTURE_TYPE.CONCEPT, "ENTITY");

        assertFalse(RoleReferenceUtil.isEntity(ref));
    }

    @Test
    void shouldCheckWhetherReferenceIsReferringToEntityRoleTrue() {
        var ref = new StructureReferenceBeanImpl("SDMX", "SDMX_CONCEPT_ROLES", "1.0", SDMX_STRUCTURE_TYPE.CONCEPT, "ENTITY");

        assertTrue(RoleReferenceUtil.isEntity(ref));
    }

    @Test
    void shouldCheckWhetherReferenceIsReferringToFlagRoleFalse() {
        var ref = new StructureReferenceBeanImpl("foo", "bar", "1.0", SDMX_STRUCTURE_TYPE.CONCEPT, "FLAG");

        assertFalse(RoleReferenceUtil.isFlag(ref));
    }

    @Test
    void shouldCheckWhetherReferenceIsReferringToFlagRoleTrue() {
        var ref = new StructureReferenceBeanImpl("SDMX", "SDMX_CONCEPT_ROLES", "1.0", SDMX_STRUCTURE_TYPE.CONCEPT, "FLAG");

        assertTrue(RoleReferenceUtil.isFlag(ref));
    }

    @Test
    void shouldCheckWhetherReferenceIsReferringToGeoRoleFalse() {
        var ref = new StructureReferenceBeanImpl("foo", "bar", "1.0", SDMX_STRUCTURE_TYPE.CONCEPT, "GEO");

        assertFalse(RoleReferenceUtil.isGeo(ref));
    }

    @Test
    void shouldCheckWhetherReferenceIsReferringToGeoRoleTrue() {
        var ref = new StructureReferenceBeanImpl("SDMX", "SDMX_CONCEPT_ROLES", "1.0", SDMX_STRUCTURE_TYPE.CONCEPT, "GEO");

        assertTrue(RoleReferenceUtil.isGeo(ref));
    }

    @Test
    void shouldCheckWhetherReferenceIsReferringToOperationRoleFalse() {
        var ref = new StructureReferenceBeanImpl("foo", "bar", "1.0", SDMX_STRUCTURE_TYPE.CONCEPT, "OPERATION");

        assertFalse(RoleReferenceUtil.isOperation(ref));
    }

    @Test
    void shouldCheckWhetherReferenceIsReferringToOperationRoleTrue() {
        var ref = new StructureReferenceBeanImpl("SDMX", "SDMX_CONCEPT_ROLES", "1.0", SDMX_STRUCTURE_TYPE.CONCEPT, "OPERATION");

        assertTrue(RoleReferenceUtil.isOperation(ref));
    }

    @Test
    void shouldCheckWhetherReferenceIsReferringToVariableRoleFalse() {
        var ref = new StructureReferenceBeanImpl("foo", "bar", "1.0", SDMX_STRUCTURE_TYPE.CONCEPT, "VARIABLE");

        assertFalse(RoleReferenceUtil.isVariable(ref));
    }

    @Test
    void shouldCheckWhetherReferenceIsReferringToVariableRoleTrue() {
        var ref = new StructureReferenceBeanImpl("SDMX", "SDMX_CONCEPT_ROLES", "1.0", SDMX_STRUCTURE_TYPE.CONCEPT, "VARIABLE");

        assertTrue(RoleReferenceUtil.isVariable(ref));
    }

    @Test
    void shouldReturnFalseWhenReferenceIsNull() {
        assertFalse(RoleReferenceUtil.isComment(null));
    }

    @Test
    void shouldReturnFalseWhenReferencePointsToMaintainable() {
        var ref = new StructureReferenceBeanImpl("SDMX", "SDMX_CONCEPT_ROLES", "1.0", SDMX_STRUCTURE_TYPE.CONCEPT_SCHEME);

        assertFalse(RoleReferenceUtil.isEntity(ref));
    }
}

