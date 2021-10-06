package org.sdmxsource.sdmx.sdmxbeans.util;

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.reference.IdentifiableRefBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.util.beans.reference.StructureReferenceBeanImpl;

public final class RoleReferenceUtil {

    private static final String AGENCY_ID = "SDMX";
    private static final String MAINTAINABLE_ID = "SDMX_CONCEPT_ROLES";
    private static final String VERSION = "1.0";

    private RoleReferenceUtil() { }

    public static boolean isFrequency(StructureReferenceBean roleRef) {
        if (roleRef == null) {
            return false;
        }

        IdentifiableRefBean role = roleRef.getChildReference();
        if (role== null) {
            return false;
        }

        return ConceptRole.FREQ.name().equals(role.getId());
    }

    public static StructureReferenceBean createFrequencyRoleReference() {
        return getStructureReferenceBean(ConceptRole.FREQ);
    }

    private static StructureReferenceBeanImpl getStructureReferenceBean(ConceptRole role) {
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
