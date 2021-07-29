/*******************************************************************************
 * Copyright (c) 2013 Metadata Technology Ltd.
 *
 * All rights reserved. This program and the accompanying materials are made 
 * available under the terms of the GNU Lesser General Public License v 3.0 
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl.html
 *
 * This file is part of the SDMX Component Library.
 *
 * The SDMX Component Library is free software: you can redistribute it and/or 
 * modify it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * The SDMX Component Library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
 * General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with The SDMX Component Library If not, see 
 * http://www.gnu.org/licenses/lgpl.
 *
 * Contributors:
 * Metadata Technology - initial API and implementation
 ******************************************************************************/
package org.sdmxsource.sdmx.sdmxbeans.util;

import org.sdmx.resources.sdmxml.schemas.v20.structure.CategoryIDType;
import org.sdmx.resources.sdmxml.schemas.v21.common.*;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.util.beans.reference.CrossReferenceBeanImpl;
import org.sdmxsource.sdmx.util.beans.reference.StructureReferenceBeanImpl;
import org.sdmxsource.util.ObjectUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Utility static methods for building SDMXBeans from v2.1+ Schemas, where common features are reused.
 */
public class RefUtil {

    /**
     * Create local id reference string.
     *
     * @param localReference the local reference
     * @return the string
     */
    public static String createLocalIdReference(LocalMetadataTargetReferenceType localReference) {
        RefBaseType ref = localReference.getRef();
        return ref.getId();
    }

    /**
     * Create local id reference string.
     *
     * @param localReference the local reference
     * @return the string
     */
    public static String createLocalIdReference(LocalLevelReferenceType localReference) {
        RefBaseType ref = localReference.getRef();
        return ref.getId();
    }

    /**
     * Create local id reference string.
     *
     * @param localReference the local reference
     * @return the string
     */
    public static String createLocalIdReference(LocalIdentifiableReferenceType localReference) {
        RefBaseType ref = localReference.getRef();
        return ref.getId();
    }

    /**
     * Create local id reference string.
     *
     * @param localReference the local reference
     * @return the string
     */
    public static String createLocalIdReference(LocalGroupKeyDescriptorReferenceType localReference) {
        RefBaseType ref = localReference.getRef();
        return ref.getId();
    }

    /**
     * Create local id reference string.
     *
     * @param localReference the local reference
     * @return the string
     */
    public static String createLocalIdReference(LocalDimensionReferenceType localReference) {
        RefBaseType ref = localReference.getRef();
        return ref.getId();
    }

    /**
     * Create local id reference string.
     *
     * @param localReference the local reference
     * @return the string
     */
    public static String createLocalIdReference(LocalPrimaryMeasureReferenceType localReference) {
        RefBaseType ref = localReference.getRef();
        return ref.getId();
    }


    /**
     * Create reference structure reference bean.
     *
     * @param objectReference the object reference
     * @return the structure reference bean
     */
    public static StructureReferenceBean createReference(ReferenceType objectReference) {
        if (objectReference.getURN() != null) {
            return new StructureReferenceBeanImpl(objectReference.getURN());
        }
        if (objectReference.getRef() == null) {
            throw new SdmxSemmanticException("Illegal Reference : ObjectReference does not contain URN or Ref");
        }
        RefBaseType ref = objectReference.getRef();
        SDMX_STRUCTURE_TYPE referencedStructure = SDMX_STRUCTURE_TYPE.parseClass(ref.getClass1().toString());
        if (!referencedStructure.getUrnPackage().equals(ref.getPackage().toString())) {
            throw new SdmxSemmanticException(referencedStructure.getType() + " is not in package " + ref.getPackage());
        }
        RefBaseType refBase = objectReference.getRef();
        boolean hasContainer = ObjectUtil.validString(refBase.getContainerID());

        String version = null;
        String maintainableId = null;
        String[] identifiableId = null;

        boolean hasIdentifiable = false;
        if (refBase.getMaintainableParentID() == null) {
            maintainableId = refBase.getId();
            version = refBase.getVersion();
        } else {
            maintainableId = refBase.getMaintainableParentID();
            hasIdentifiable = ObjectUtil.validString(refBase.getId());
            version = refBase.getMaintainableParentVersion();
        }
        if (hasIdentifiable) {
            if (hasContainer) {
                String containerId = refBase.getContainerID();
                String[] id = refBase.getId().split("\\.");

                identifiableId = new String[id.length + 1];
                identifiableId[0] = containerId;
                for (int i = 0; i < id.length; i++) {
                    identifiableId[i + 1] = id[i];
                }
            } else {
                identifiableId = refBase.getId().split("\\.");
            }
        }
        SDMX_STRUCTURE_TYPE referencedStructure2 = SDMX_STRUCTURE_TYPE.parsePackageAndClass(refBase.getPackage().toString(), refBase.getClass1().toString());
        //TODO check the array works as var args, not a single arg
        StructureReferenceBean sRef =
                new StructureReferenceBeanImpl(
                        refBase.getAgencyID(),
                        maintainableId,
                        version,
                        referencedStructure2,
                        identifiableId);
        return sRef;
    }

    /**
     * Create reference cross reference bean.
     *
     * @param referencedFrom  the referenced from
     * @param objectReference the object reference
     * @return the cross reference bean
     */
    public static CrossReferenceBean createReference(SDMXBean referencedFrom, ReferenceType objectReference) {
        return new CrossReferenceBeanImpl(referencedFrom, createReference(objectReference));
    }

    /**
     * Create category ref cross reference bean.
     *
     * @param referencedFrom the referenced from
     * @param ref            the ref
     * @return the cross reference bean
     */
    public static CrossReferenceBean createCategoryRef(SDMXBean referencedFrom, org.sdmx.resources.sdmxml.schemas.v20.structure.CategoryRefType ref) {
        if (ObjectUtil.validString(ref.getURN())) {
            return new CrossReferenceBeanImpl(referencedFrom, ref.getURN());
        }
        List<String> catId = new ArrayList<String>();
        String catSchemeAgencyId = ref.getCategorySchemeAgencyID();
        String catSchemeId = ref.getCategorySchemeID();
        String catSchemeVersion = ref.getCategorySchemeVersion();

        if (ref.getCategoryID() != null) {
            getCateogryIds(catId, ref.getCategoryID());
        }
        String[] catIds = new String[catId.size()];
        catId.toArray(catIds);
        return new CrossReferenceBeanImpl(referencedFrom, catSchemeAgencyId, catSchemeId, catSchemeVersion, SDMX_STRUCTURE_TYPE.CATEGORY, catIds);
    }

    private static void getCateogryIds(List<String> list, CategoryIDType idType) {
        list.add(idType.getID());
        if (idType.getCategoryID() != null) {
            getCateogryIds(list, idType.getCategoryID());
        }
    }
}
