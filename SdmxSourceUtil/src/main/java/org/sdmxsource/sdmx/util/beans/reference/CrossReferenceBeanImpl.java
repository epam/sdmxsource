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
package org.sdmxsource.sdmx.util.beans.reference;

import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxNotImplementedException;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.IdentifiableBean;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.reference.IdentifiableRefBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.util.ObjectUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Cross reference bean.
 */
public class CrossReferenceBeanImpl extends StructureReferenceBeanImpl implements CrossReferenceBean {
    private static final long serialVersionUID = -1103372297741893400L;
    private SDMXBean referencedFrom;

    /**
     * Instantiates a new Cross reference bean.
     *
     * @param referencedFrom the referenced from
     * @param sRefBean       the s ref bean
     */
//FUNC this constructor does not account the sRef having the incorrect SDMX_STRUCTURE_TYPE (i.e from a mutable bean)
    public CrossReferenceBeanImpl(SDMXBean referencedFrom, StructureReferenceBean sRefBean) {
        super(sRefBean.getMaintainableReference().getAgencyId(),
                sRefBean.getMaintainableReference().getMaintainableId(),
                sRefBean.getMaintainableReference().getVersion(),
                sRefBean.getTargetReference(),
                sRefBean.getIdentifiableIds());
        this.referencedFrom = referencedFrom;
        validateReference();
    }

    /**
     * Instantiates a new Cross reference bean.
     *
     * @param referencedFrom the referenced from
     * @param agencyId       the agency id
     * @param maintainableId the maintainable id
     * @param version        the version
     * @param structureType  the structure type
     */
    public CrossReferenceBeanImpl(SDMXBean referencedFrom, String agencyId, String maintainableId, String version, SDMX_STRUCTURE_TYPE structureType) {
        super(agencyId, maintainableId, version, structureType);
        this.referencedFrom = referencedFrom;
        validateReference();
    }

    /**
     * Instantiates a new Cross reference bean.
     *
     * @param referencedFrom  the referenced from
     * @param agencyId        the agency id
     * @param maintainableId  the maintainable id
     * @param version         the version
     * @param structureType   the structure type
     * @param identifiableIds the identifiable ids
     */
    public CrossReferenceBeanImpl(SDMXBean referencedFrom, String agencyId, String maintainableId, String version, SDMX_STRUCTURE_TYPE structureType, String... identifiableIds) {
        super(agencyId, maintainableId, version, structureType, identifiableIds);
        this.referencedFrom = referencedFrom;
        validateReference();
    }

    /**
     * Instantiates a new Cross reference bean.
     *
     * @param referencedFrom the referenced from
     * @param urn            the urn
     */
    public CrossReferenceBeanImpl(SDMXBean referencedFrom, String urn) {
        super(urn);
        this.referencedFrom = referencedFrom;
        validateReference();
    }

    /**
     * Get identifiable ids string [ ].
     *
     * @param ref the ref
     * @return the string [ ]
     */
    public static String[] getIdentifiableIds(IdentifiableRefBean ref) {
        if (ref == null) {
            return null;
        }
        List<String> ids = new ArrayList<String>();
        while (ref.getChildReference() != null) {
            ids.add(ref.getChildReference().getId());
        }
        String[] returnArray = new String[ids.size()];
        ids.toArray(returnArray);
        return returnArray;
    }

    @Override
    public SDMXBean getReferencedFrom() {
        return referencedFrom;
    }

    @Override
    public void setAgencyId(String agencyId) {
        throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "CrossReferenceBeanImpl.setAgencyId - CrossReferenceBeanImpl is immutbale");
    }

    @Override
    public void setVersion(String version) {
        throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "CrossReferenceBeanImpl.setVersion - CrossReferenceBeanImpl is immutbale");
    }


    @Override
    public void setChildReference(IdentifiableRefBean childReference) {
        throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "CrossReferenceBeanImpl.setChildReference - CrossReferenceBeanImpl is immutbale");
    }

    @Override
    public void setTargetStructureType(SDMX_STRUCTURE_TYPE targetStructureType) {
        throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "CrossReferenceBeanImpl.setTargetStructureType - CrossReferenceBeanImpl is immutbale");
    }

    @Override
    public void setMaintainableStructureType(SDMX_STRUCTURE_TYPE structureType) {
        throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "CrossReferenceBeanImpl.setMaintainableStructureType - CrossReferenceBeanImpl is immutbale");
    }

    @Override
    public void setMaintainableId(String maintainableId) {
        throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "CrossReferenceBeanImpl.setMaintainableId - CrossReferenceBeanImpl is immutbale");
    }

    @Override
    public StructureReferenceBean createMutableInstance() {
        return new StructureReferenceBeanImpl(getTargetUrn());
    }


    @Override
    public boolean isMatch(IdentifiableBean identifiableBean) {
        if (identifiableBean.getStructureType() == this.getTargetReference()) {
            return this.getTargetUrn().equals(identifiableBean.getUrn());
        }
        return false;
    }

    private void validateReference() {
        if (referencedFrom == null) {
            throw new SdmxSemmanticException(ExceptionCode.BEAN_MISSING_REQUIRED_ELEMENT, "referencedFrom");
        }
        if (!ObjectUtil.validString(getMaintainableReference().getAgencyId())) {
            throw new SdmxSemmanticException(ExceptionCode.BEAN_INCOMPLETE_REFERENCE, "Agency Id");
        }
        if (!ObjectUtil.validString(getMaintainableReference().getMaintainableId())) {
            throw new SdmxSemmanticException(ExceptionCode.BEAN_INCOMPLETE_REFERENCE, "Maintainable Id");
        }
        if (!ObjectUtil.validString(getMaintainableReference().getVersion())) {
            this.version = "1.0";
        }
        if (!getTargetReference().isMaintainable()) {
            if (getChildReference() == null) {
                throw new SdmxSemmanticException("Reference to " + getTargetReference().getType() + " missing identifiable parameters");
            }
        }
    }

    @Override
    public String toString() {
        return "Cross Reference : " + getTargetUrn();
    }
}
