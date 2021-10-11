package org.sdmxsource.sdmx.sdmxbeans.util;

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.reference.IdentifiableRefBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.util.beans.reference.StructureReferenceBeanImpl;

public final class RoleReferenceUtil {

    private static final String AGENCY_ID = "SDMX";
    private static final String MAINTAINABLE_ID = "SDMX_CONCEPT_ROLES";
    private static final String VERSION = "1.0";

    private RoleReferenceUtil() { }

    public static boolean isFrequency(StructureReferenceBean roleRef) {
        return isRole(roleRef, ConceptRole.FREQ);
    }

    public static boolean isComment(StructureReferenceBeanImpl roleRef) {
        return isRole(roleRef, ConceptRole.COMMENT);
    }

    public static boolean isEntity(StructureReferenceBeanImpl roleRef) {
        return isRole(roleRef, ConceptRole.ENTITY);
    }

    public static boolean isFlag(StructureReferenceBeanImpl roleRef) {
        return isRole(roleRef, ConceptRole.FLAG);
    }

    public static boolean isGeo(StructureReferenceBeanImpl roleRef) {
        return isRole(roleRef, ConceptRole.GEO);
    }

    public static boolean isOperation(StructureReferenceBeanImpl roleRef) {
        return isRole(roleRef, ConceptRole.OPERATION);
    }

    public static boolean isVariable(StructureReferenceBeanImpl roleRef) {
        return isRole(roleRef, ConceptRole.VARIABLE);
    }

    public static StructureReferenceBean createFrequencyRoleReference() {
        return getConceptRoleReference(ConceptRole.FREQ);
    }

    public static StructureReferenceBean createCommentRoleReference() {
        return getConceptRoleReference(ConceptRole.COMMENT);
    }

    public static StructureReferenceBean createEntityRoleReference() {
        return getConceptRoleReference(ConceptRole.ENTITY);
    }

    public static StructureReferenceBean createFlagRoleReference() {
        return getConceptRoleReference(ConceptRole.FLAG);
    }

    public static StructureReferenceBean createGeoRoleReference() {
        return getConceptRoleReference(ConceptRole.GEO);
    }

    public static StructureReferenceBean createOperationRoleReference() {
        return getConceptRoleReference(ConceptRole.OPERATION);
    }

    public static StructureReferenceBean createVariableRoleReference() {
        return getConceptRoleReference(ConceptRole.VARIABLE);
    }

    private static boolean isRole(StructureReferenceBean ref, ConceptRole role) {
        if (ref == null) {
            return false;
        }

        IdentifiableRefBean childRef = ref.getChildReference();
        if (childRef == null) {
            return false;
        }

        return AGENCY_ID.equals(ref.getAgencyId())
                && MAINTAINABLE_ID.equals(ref.getMaintainableId())
                && VERSION.equals(ref.getVersion())
                && role.name().equals(childRef.getId());
    }

    private static StructureReferenceBeanImpl getConceptRoleReference(ConceptRole role) {
        return new StructureReferenceBeanImpl(
                AGENCY_ID,
                MAINTAINABLE_ID,
                VERSION,
                SDMX_STRUCTURE_TYPE.CONCEPT,
                role.name()
        );
    }

    private enum ConceptRole {
        COMMENT,
        ENTITY,
        FLAG,
        FREQ,
        GEO,
        OPERATION,
        VARIABLE,
    }
}
