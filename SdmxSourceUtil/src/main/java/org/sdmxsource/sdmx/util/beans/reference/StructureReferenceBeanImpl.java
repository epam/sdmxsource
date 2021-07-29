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

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.AgencyBean;
import org.sdmxsource.sdmx.api.model.beans.base.AgencySchemeBean;
import org.sdmxsource.sdmx.api.model.beans.base.IdentifiableBean;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.beans.reference.IdentifiableRefBean;
import org.sdmxsource.sdmx.api.model.beans.reference.MaintainableRefBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.util.beans.UrnUtil;
import org.sdmxsource.util.ObjectUtil;
import org.sdmxsource.util.VersionableUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * This bean guarantees that agencyId, MaintainableId and Version will always be non null.
 * <p>
 * If this bean is constructed with no version information then version 1.0 is assumed
 */
public class StructureReferenceBeanImpl implements StructureReferenceBean {

    private static final long serialVersionUID = -4043917683226286238L;
    /**
     * The Version.
     */
    protected String version;
    /**
     * The Hashcode.
     */
    int hashcode = -1;
    //Maintainable Reference Parameters
    private String agencyId;
    private String maintainableId;
    private SDMX_STRUCTURE_TYPE structureType;
    private SDMX_STRUCTURE_TYPE targetStructureType;
    //Child Reference (If Applicable)
    private IdentifiableRefBean childReference;

    /**
     * Instantiates a new Structure reference bean.
     */
    public StructureReferenceBeanImpl() {
        //Default Constructor
    }

    /**
     * Instantiates a new Structure reference bean.
     *
     * @param urn the urn
     */
    public StructureReferenceBeanImpl(String urn) {
        SDMX_STRUCTURE_TYPE targetStructure;
        try {
            targetStructure = UrnUtil.getIdentifiableType(urn);
        } catch (Throwable th) {
            throw new SdmxSemmanticException(th, "Illegal URN: " + urn);
        }
        UrnUtil.validateURN(urn, targetStructure);
        String[] components = UrnUtil.getUrnComponents(urn);
        this.agencyId = components[0];
        this.maintainableId = components[1];
        this.version = UrnUtil.getVersionFromUrn(urn);
        if (this.version == null) {
            this.version = MaintainableBean.DEFAULT_VERSION;
        }
        targetStructureType = targetStructure;

        if (!targetStructure.isMaintainable() && targetStructure.isIdentifiable()) {
            String[] identfiableIds = Arrays.copyOfRange(components, 2, components.length);
            childReference = new IdentifiableRefBeanImpl(this, identfiableIds, targetStructure);
            while (true) {
                targetStructure = targetStructure.getParentStructureType();
                if (targetStructure.isMaintainable()) {
                    structureType = targetStructure;
                    break;
                }
            }
        } else {
            this.structureType = targetStructure;
        }
    }

    /**
     * Instantiates a new Structure reference bean.
     *
     * @param ref           the ref
     * @param structureType the structure type
     */
    public StructureReferenceBeanImpl(MaintainableRefBean ref, SDMX_STRUCTURE_TYPE structureType) {
        if (ref != null) {
            setInformation(ref.getAgencyId(), ref.getMaintainableId(), ref.getVersion(), structureType);
        } else {
            setInformation(null, null, null, structureType);
        }

    }

    /**
     * Instantiates a new Structure reference bean.
     *
     * @param structureType the structure type
     */
    public StructureReferenceBeanImpl(SDMX_STRUCTURE_TYPE structureType) {
        setInformation(null, null, null, structureType);
    }

    /**
     * Creates a StructureReferenceBean that can be used to reference the identifiable bean
     *
     * @param identifiable the identifiable
     */
    public StructureReferenceBeanImpl(IdentifiableBean identifiable) {
        this(identifiable.getUrn());
    }


    /**
     * Instantiates a new Structure reference bean.
     *
     * @param parentMaint     the parent maint
     * @param targetStructure the target structure
     * @param identfiableIds  the identfiable ids
     */
    public StructureReferenceBeanImpl(MaintainableBean parentMaint, SDMX_STRUCTURE_TYPE targetStructure, String... identfiableIds) {
        setInformation(parentMaint.getAgencyId(), parentMaint.getId(), parentMaint.getVersion(), targetStructure, identfiableIds);
    }


    /**
     * Instantiates a new Structure reference bean.
     *
     * @param agencyId        the agency id
     * @param maintainableId  the maintainable id
     * @param version         the version
     * @param targetStructure the target structure
     * @param identfiableIds  the identfiable ids
     */
    public StructureReferenceBeanImpl(String agencyId, String maintainableId, String version, SDMX_STRUCTURE_TYPE targetStructure, String... identfiableIds) {
        setInformation(agencyId, maintainableId, version, targetStructure, identfiableIds);
    }

    @Override
    public String getFullId() {
        if (childReference == null) {
            return null;
        }

        String returnString = "";
        String concat = "";
        for (String currentId : getIdentifiableIds()) {
            returnString += concat + currentId;
            concat = ".";
        }
        if (targetStructureType == SDMX_STRUCTURE_TYPE.AGENCY) {
            if (agencyId != null && !agencyId.equals(AgencyBean.DEFAULT_AGENCY) && !agencyId.equals("*")) {
                returnString = agencyId + "." + returnString;
            }
        }
        return returnString;

    }

    private void setInformation(String agencyId, String maintainableId, String version, SDMX_STRUCTURE_TYPE targetStructure, String... identfiableIds) {
        this.agencyId = agencyId;
        this.maintainableId = maintainableId;
        if (ObjectUtil.validString(version)) {
            this.version = VersionableUtil.formatVersion(version);
        }
        if (targetStructure == null) {
            throw new IllegalArgumentException("Structure Type must be given for a StructureReferenceBeanImpl");
        }
        if (identfiableIds == null && !targetStructure.isMaintainable() && (targetStructure != SDMX_STRUCTURE_TYPE.ANY && targetStructure != SDMX_STRUCTURE_TYPE.ORGANISATION_SCHEME)) {
            throw new IllegalArgumentException("Can not create reference, target structure is not maintainable, and no identifiable reference parameters present");
        }
        targetStructureType = targetStructure;


        if (!targetStructure.isMaintainable() && targetStructure.isIdentifiable()) {
            if (identfiableIds != null && identfiableIds.length > 0) {
                childReference = new IdentifiableRefBeanImpl(this, identfiableIds, targetStructure);
            }
            while (true) {
                targetStructure = targetStructure.getParentStructureType();
                if (targetStructure.isMaintainable()) {
                    structureType = targetStructure;
                    break;
                }
            }
        } else {
            this.structureType = targetStructure;
        }
    }

    //GET
    @Override
    public String getTargetUrn() {
        if (targetStructureType == SDMX_STRUCTURE_TYPE.AGENCY && childReference != null && ObjectUtil.validString(childReference.getId())) {
            if (agencyId.equals(AgencySchemeBean.DEFAULT_SCHEME)) {
                return targetStructureType.getUrnPrefix() + childReference.getId();
            }
            return targetStructureType.getUrnPrefix() + agencyId + "." + childReference.getId();
        } else if (ObjectUtil.validString(agencyId, maintainableId, version)) {
            List<String> ids = new ArrayList<String>();
            if (childReference != null) {
                IdentifiableRefBean currentRef = childReference;
                while (currentRef != null) {
                    //Ignore identifiable targets that do no have the ids in the URN
                    switch (currentRef.getStructureType()) {
                        case ATTRIBUTE_DESCRIPTOR:
                        case DIMENSION_DESCRIPTOR:
                        case MEASURE_DESCRIPTOR:
                            if (targetStructureType == currentRef.getStructureType()) {
                                ids.add(currentRef.getId());
                            }
                            break;
                        default:
                            ids.add(currentRef.getId());
                    }

                    currentRef = currentRef.getChildReference();
                }
            }
            String[] arr = new String[ids.size()];
            ids.toArray(arr);
            return targetStructureType.generateUrn(agencyId, maintainableId, version, arr);
        }
        return null;
    }

    @Override
    public String getMaintainableUrn() {
        if (ObjectUtil.validString(agencyId, maintainableId, version)) {
            return structureType.generateUrn(agencyId, maintainableId, version);
        }
        return null;
    }

    @Override
    public MaintainableRefBean getMaintainableReference() {
        return new MaintainableRefBeanImpl(agencyId, maintainableId, version);
    }

    @Override
    public SDMX_STRUCTURE_TYPE getMaintainableStructureType() {
        return structureType;
    }

    /**
     * Sets maintainable structure type.
     *
     * @param structureType the structure type
     */
    public void setMaintainableStructureType(SDMX_STRUCTURE_TYPE structureType) {
        this.structureType = structureType;
        if (!structureType.isIdentifiable()) {
            throw new SdmxException("Can not set maintainable structure type to '" + structureType + "' as it is not identifiable or maintainable ");
        }
        if (structureType != null && !structureType.isMaintainable()) {
            SDMX_STRUCTURE_TYPE targetStructure = structureType;
            while (true) {
                targetStructure = targetStructure.getParentStructureType();
                if (targetStructure.isMaintainable()) {
                    this.structureType = targetStructure;
                    break;
                }
            }
        }
    }

    @Override
    public SDMX_STRUCTURE_TYPE getTargetReference() {
        return targetStructureType;
    }

    @Override
    public IdentifiableRefBean getChildReference() {
        return childReference;
    }

    /**
     * Sets child reference.
     *
     * @param childReference the child reference
     */
    public void setChildReference(IdentifiableRefBean childReference) {
        this.childReference = childReference;
    }

    @Override
    public String[] getIdentifiableIds() {
        if (childReference == null) {
            return null;
        }
        List<String> ids = new ArrayList<String>();
        IdentifiableRefBean currentRef = childReference;
        while (currentRef != null) {
            ids.add(currentRef.getId());
            currentRef = currentRef.getChildReference();
        }
        String[] returnArray = new String[ids.size()];
        ids.toArray(returnArray);
        return returnArray;
    }

    /**
     * Gets target structure type.
     *
     * @return the target structure type
     */
    public SDMX_STRUCTURE_TYPE getTargetStructureType() {
        return targetStructureType;
    }

    /**
     * Sets target structure type.
     *
     * @param targetStructureType the target structure type
     */
    public void setTargetStructureType(SDMX_STRUCTURE_TYPE targetStructureType) {
        this.targetStructureType = targetStructureType;
        setMaintainableStructureType(targetStructureType);
    }

    @Override
    public String getAgencyId() {
        return agencyId;
    }

    /**
     * Sets agency id.
     *
     * @param agencyId the agency id
     */
//SETTERS
    public void setAgencyId(String agencyId) {
        if (ObjectUtil.validString(agencyId)) {
            this.agencyId = agencyId;
        } else {
            this.agencyId = null;
        }
    }

    @Override
    public String getMaintainableId() {
        return maintainableId;
    }

    /**
     * Sets maintainable id.
     *
     * @param maintainableId the maintainable id
     */
    public void setMaintainableId(String maintainableId) {
        if (ObjectUtil.validString(maintainableId)) {
            this.maintainableId = maintainableId;
        } else {
            this.maintainableId = null;
        }
    }

    @Override
    public String getVersion() {
        return version;
    }

    //HAS

    /**
     * Sets version.
     *
     * @param version the version
     */
    public void setVersion(String version) {
        if (ObjectUtil.validString(version)) {
            this.version = VersionableUtil.formatVersion(version);
        } else {
            this.version = null;
        }
    }

    @Override
    public boolean hasAgencyId() {
        return ObjectUtil.validString(agencyId);
    }

    @Override
    public boolean hasMaintainableId() {
        return ObjectUtil.validString(maintainableId);
    }

    @Override
    public boolean hasVersion() {
        return ObjectUtil.validString(version);
    }

    @Override
    public boolean hasChildReference() {
        return getChildReference() != null;
    }

    @Override
    public boolean hasTargetUrn() {
        return getTargetUrn() != null;
    }

    @Override
    public boolean hasMaintainableUrn() {
        return getTargetUrn() != null;
    }

    @Override
    public StructureReferenceBean createCopy() {
        return new StructureReferenceBeanImpl(agencyId, maintainableId, version, targetStructureType, getIdentifiableIds());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof StructureReferenceBean) {

            StructureReferenceBean that = (StructureReferenceBean) obj;
            MaintainableRefBean thatMRef = that.getMaintainableReference();
            MaintainableRefBean thisMRef = getMaintainableReference();
            return
                    ObjectUtil.equivalent(thatMRef.getMaintainableId(), thisMRef.getMaintainableId()) &&
                            ObjectUtil.equivalent(thatMRef.getAgencyId(), thisMRef.getAgencyId()) &&
                            ObjectUtil.equivalent(thatMRef.getVersion(), thisMRef.getVersion()) &&
                            ObjectUtil.equivalent(this.getChildReference(), that.getChildReference());
        }
        return false;
    }

    @Override
    public boolean isMatch(MaintainableBean maintainbleBean) {
        return getMatch(maintainbleBean) != null;
    }

    @Override
    public IdentifiableBean getMatch(MaintainableBean maintainbleBean) {
        if (getMaintainableStructureType() == maintainbleBean.getStructureType()) {

            if (ObjectUtil.validString(getAgencyId())) {
                // If the AgencyId has the wildcard character then we will use Java's matches method to see if the value matches
                // otherwise just use the equals method. Only use 'matches' when the wildcard is present to avoid any other side-effects
                // of RegEx (e.g. the dot character)
                if (getAgencyId().contains("*")) {
                    String y = getAgencyId().replaceAll("\\*", ".*");
                    if (!maintainbleBean.getAgencyId().matches(y)) {
                        return null;
                    }
                } else {
                    if (!getAgencyId().equals(maintainbleBean.getAgencyId())) {
                        return null;
                    }
                }
            }

            if (ObjectUtil.validString(getMaintainableId())) {
                // If the AgencyId has the wildcard character then we will use Java's matches method to see if the value matches
                // otherwise just use the equals method. Only use 'matches' when the wildcard is present to avoid any other side-effects
                // of RegEx (e.g. the dot character)
                if (getMaintainableId().contains("*")) {
                    String y = getMaintainableId().replaceAll("\\*", ".*");
                    if (!maintainbleBean.getId().matches(y)) {
                        return null;
                    }
                } else {
                    if (!getMaintainableId().equals(maintainbleBean.getId())) {
                        return null;
                    }
                }
            }

            if (ObjectUtil.validString(getVersion())) {
                if (!getVersion().equals("*")) {
                    if (!getVersion().equals(maintainbleBean.getVersion())) {
                        return null;
                    }
                }
            }

            if (this.getChildReference() != null) {
                for (IdentifiableBean currentComposite : maintainbleBean.getIdentifiableComposites()) {
                    IdentifiableBean matchedIdentifiable = this.getChildReference().getMatch(currentComposite);
                    if (matchedIdentifiable != null) {
                        return matchedIdentifiable;
                    }
                }
            } else {
                return maintainbleBean;
            }
        }
        return null;
    }

    @Override
    public int hashCode() {
        if (hashcode < 0) {
            String concat = "";
            if (childReference != null) {
                concat = " - Identifiable Reference " + childReference.toString();
            }
            MaintainableRefBean mRef = getMaintainableReference();
            String str = "Target: " + getTargetReference() +
                    "Agency Id: " + mRef.getAgencyId() + " - " +
                    "Maintainable Id: " + mRef.getMaintainableId() + " - " +
                    "Version: " + mRef.getVersion() + concat;

            hashcode = str.hashCode();
        }
        return hashcode;
    }

    @Override
    public String toString() {
        String concat = "";
        if (childReference != null) {
            concat = " - Identifiable Reference: " + childReference.toString();
        }
        MaintainableRefBean mRef = getMaintainableReference();
        return
                "Target: " + getTargetReference() + " - " +
                        "Agency Id: " + mRef.getAgencyId() + " - " +
                        "Maintainable Id: " + mRef.getMaintainableId() + " - " +
                        "Version: " + mRef.getVersion() + concat;
    }
}
