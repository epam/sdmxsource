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
package org.sdmxsource.sdmx.sdmxbeans.model.mutable.mapping;

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.mapping.RelatedStructuresBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.mutable.mapping.RelatedStructuresMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.MutableBeanImpl;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Related structures mutable bean.
 */
public class RelatedStructuresMutableBeanImpl extends MutableBeanImpl implements RelatedStructuresMutableBean {
    private static final long serialVersionUID = 1L;

    private List<StructureReferenceBean> keyFamilyRef;
    private List<StructureReferenceBean> metadataStructureRef;
    private List<StructureReferenceBean> conceptSchemeRef;
    private List<StructureReferenceBean> categorySchemeRef;
    private List<StructureReferenceBean> orgSchemeRef;
    private List<StructureReferenceBean> hierCodelistRef;

    /**
     * Instantiates a new Related structures mutable bean.
     */
    public RelatedStructuresMutableBeanImpl() {
        super(SDMX_STRUCTURE_TYPE.RELATED_STRUCTURES);
    }

    /**
     * Instantiates a new Related structures mutable bean.
     *
     * @param relStrucType the rel struc type
     */
    public RelatedStructuresMutableBeanImpl(RelatedStructuresBean relStrucType) {
        super(relStrucType);
        this.keyFamilyRef = convertList(relStrucType.getDataStructureRef());
        this.metadataStructureRef = convertList(relStrucType.getMetadataStructureRef());
        this.conceptSchemeRef = convertList(relStrucType.getConceptSchemeRef());
        this.categorySchemeRef = convertList(relStrucType.getCategorySchemeRef());
        this.orgSchemeRef = convertList(relStrucType.getOrgSchemeRef());
        this.hierCodelistRef = convertList(relStrucType.getHierCodelistRef());
    }

    private List<StructureReferenceBean> convertList(List<CrossReferenceBean> inputList) {
        List<StructureReferenceBean> returnList = new ArrayList<StructureReferenceBean>();
        if (inputList != null) {
            for (CrossReferenceBean currentCrossReference : inputList) {
                returnList.add(currentCrossReference.createMutableInstance());
            }
        }
        return returnList;
    }

    @Override
    public List<StructureReferenceBean> getDataStructureRef() {
        return keyFamilyRef;
    }

    @Override
    public void setDataStructureRef(List<StructureReferenceBean> keyFamilyRef) {
        this.keyFamilyRef = keyFamilyRef;
    }

    @Override
    public List<StructureReferenceBean> getMetadataStructureRef() {
        return metadataStructureRef;
    }

    @Override
    public void setMetadataStructureRef(List<StructureReferenceBean> metadataStructureRef) {
        this.metadataStructureRef = metadataStructureRef;
    }

    @Override
    public List<StructureReferenceBean> getConceptSchemeRef() {
        return conceptSchemeRef;
    }

    @Override
    public void setConceptSchemeRef(List<StructureReferenceBean> conceptSchemeRef) {
        this.conceptSchemeRef = conceptSchemeRef;
    }

    @Override
    public List<StructureReferenceBean> getCategorySchemeRef() {
        return categorySchemeRef;
    }

    @Override
    public void setCategorySchemeRef(List<StructureReferenceBean> categorySchemeRef) {
        this.categorySchemeRef = categorySchemeRef;
    }

    @Override
    public List<StructureReferenceBean> getOrgSchemeRef() {
        return orgSchemeRef;
    }

    @Override
    public void setOrgSchemeRef(List<StructureReferenceBean> orgSchemeRef) {
        this.orgSchemeRef = orgSchemeRef;
    }

    @Override
    public List<StructureReferenceBean> getHierCodelistRef() {
        return hierCodelistRef;
    }

    @Override
    public void setHierCodelistRef(List<StructureReferenceBean> hierCodelistRef) {
        this.hierCodelistRef = hierCodelistRef;
    }
}
