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
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.IdentifiableBean;
import org.sdmxsource.sdmx.api.model.beans.reference.IdentifiableRefBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.util.ObjectUtil;

import java.util.Collection;

/**
 * The type Identifiable ref bean.
 */
public class IdentifiableRefBeanImpl implements IdentifiableRefBean {
    private static final long serialVersionUID = -4595600685019168190L;
    private String id;
    private StructureReferenceBean maintainableParent;
    private SDMX_STRUCTURE_TYPE structureType;
    private IdentifiableRefBean childReference;
    private IdentifiableRefBean parentReference;

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////CONSTRUCTORS 						 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Instantiates a new Identifiable ref bean.
     */
    public IdentifiableRefBeanImpl() {
    }

    private IdentifiableRefBeanImpl(IdentifiableRefBean parentReference, String id[], int currentDepth, SDMX_STRUCTURE_TYPE targetStructureType) {
        this.parentReference = parentReference;
        this.id = id[currentDepth];
        setStructureType(targetStructureType, currentDepth);
        currentDepth++;
        if (currentDepth < id.length) {
            this.childReference = new IdentifiableRefBeanImpl(this, id, currentDepth, targetStructureType);
        }
        validate();
    }

    /**
     * Instantiates a new Identifiable ref bean.
     *
     * @param maintainableParent  the maintainable parent
     * @param id                  the id
     * @param targetStructureType the target structure type
     */
    public IdentifiableRefBeanImpl(StructureReferenceBean maintainableParent, String id, SDMX_STRUCTURE_TYPE targetStructureType) {
        this.structureType = targetStructureType;
        this.maintainableParent = maintainableParent;
        this.id = id;
        validate();
    }

    /**
     * Instantiates a new Identifiable ref bean.
     *
     * @param maintainableParent  the maintainable parent
     * @param idArr               the id arr
     * @param targetStructureType the target structure type
     */
    IdentifiableRefBeanImpl(StructureReferenceBean maintainableParent, String idArr[], SDMX_STRUCTURE_TYPE targetStructureType) {
        setStructureType(targetStructureType, 0);
        this.maintainableParent = maintainableParent;
        if (structureType.hasFixedId()) {
            if (!idArr[0].equals(structureType.getFixedId())) {
                //Change the id array by inserting a new fixed id
                String[] tempArray = new String[idArr.length + 1];
                tempArray[0] = structureType.getFixedId();
                for (int i = 0; i < idArr.length; i++) {
                    tempArray[i + 1] = idArr[i];
                }

                idArr = tempArray;
            }
        }
        this.id = idArr[0];
        if (idArr.length > 1) {
            this.childReference = new IdentifiableRefBeanImpl(this, idArr, 1, targetStructureType);
        }
        validate();
    }

    /**
     * Instantiates a new Identifiable ref bean.
     *
     * @param maintainableParent  the maintainable parent
     * @param ids                 the ids
     * @param targetStructureType the target structure type
     */
    public IdentifiableRefBeanImpl(StructureReferenceBean maintainableParent, Collection<String> ids, SDMX_STRUCTURE_TYPE targetStructureType) {
        setStructureType(targetStructureType, 0);
        this.maintainableParent = maintainableParent;
        String[] idArr = new String[ids.size()];
        ids.toArray(idArr);
        this.id = idArr[0];
        if (idArr.length > 1) {
            this.childReference = new IdentifiableRefBeanImpl(this, idArr, 1, targetStructureType);
        }
        validate();
    }

    private void setStructureType(SDMX_STRUCTURE_TYPE targetStructureType, int currentDepth) {
        if (currentDepth < targetStructureType.getNestedDepth()) {
            SDMX_STRUCTURE_TYPE parentStructure = targetStructureType.getParentStructureType();
            while (parentStructure != null) {
                if (parentStructure.getNestedDepth() == currentDepth) {
                    this.structureType = parentStructure;
                    break;
                }
                parentStructure = parentStructure.getParentStructureType();
            }
        } else {
            this.structureType = targetStructureType;
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATION							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private void validate() throws SdmxSemmanticException {
        if (structureType.hasFixedId() && ObjectUtil.validString(id)) {
            if (!this.id.equals(structureType.getFixedId())) {
                throw new SdmxSemmanticException(structureType.getType() + " has a fixed id of '" + structureType.getFixedId() + "'.  Identifiable reference can not set this to '" + id + "'");
            }
        }
    }

    @Override
    public String getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
//FUNC Breaks immutablility but required for flex
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public SDMX_STRUCTURE_TYPE getStructureType() {
        return structureType;
    }

    /**
     * Sets structure type.
     *
     * @param structureType the structure type
     */
    public void setStructureType(SDMX_STRUCTURE_TYPE structureType) {
        this.structureType = structureType;
    }

    @Override
    public IdentifiableRefBean getChildReference() {
        return childReference;
    }

    /**
     * Sets child reference.
     *
     * @param ref the ref
     */
    public void setChildReference(IdentifiableRefBean ref) {
        this.childReference = ref;
    }

    @Override
    public IdentifiableRefBean getParentIdentifiableReference() {
        return parentReference;
    }

    @Override
    public StructureReferenceBean getParentMaintainableReferece() {
        if (maintainableParent != null) {
            return maintainableParent;
        }
        return childReference.getParentMaintainableReferece();
    }

    @Override
    public int hashCode() {
        return (this.structureType.getType() + toString()).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof IdentifiableRefBean) {
            IdentifiableRefBean that = (IdentifiableRefBean) obj;
            if (this.getStructureType() == that.getStructureType()) {
                if (ObjectUtil.equivalent(this.getId(), that.getId())) {
                    if (ObjectUtil.equivalent(this.getChildReference(), that.getChildReference())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public IdentifiableBean getMatch(IdentifiableBean reference) {
        if (this.structureType != reference.getStructureType()) {
            return null;
        }

        if (ObjectUtil.validString(getId())) {
            if (getId().equals(reference.getId())) {
                return reference;
            }
            return null;
        }

        if (this.getChildReference() != null) {
            for (IdentifiableBean currentComposite : reference.getIdentifiableComposites()) {
                if (this.getChildReference().getMatch(currentComposite) != null) {
                    return currentComposite;
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        String concatString = "";
        if (this.getChildReference() != null) {
            concatString = "." + this.getChildReference().toString();
        }
        return this.getId() + concatString;
    }
}
