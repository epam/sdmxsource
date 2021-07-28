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
import org.sdmxsource.sdmx.api.model.beans.mapping.*;
import org.sdmxsource.sdmx.api.model.mutable.base.StructureMapMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.mapping.*;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.mapping.StructureSetBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.MaintainableMutableBeanImpl;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Structure set mutable bean.
 */
public class StructureSetMutableBeanImpl extends MaintainableMutableBeanImpl implements StructureSetMutableBean {

    private static final long serialVersionUID = 1L;

    private RelatedStructuresMutableBean relatedStructures;
    private List<StructureMapMutableBean> structureMapList;
    private List<CodelistMapMutableBean> codelistMapList;
    private List<CategorySchemeMapMutableBean> categorySchemeMapList;
    private List<ConceptSchemeMapMutableBean> conceptSchemeMapList;
    private List<OrganisationSchemeMapMutableBean> organisationSchemeMapList;

    /**
     * Constructor.
     */
    public StructureSetMutableBeanImpl() {
        super(SDMX_STRUCTURE_TYPE.STRUCTURE_SET);
    }

    /**
     * Copy constructor
     *
     * @param bean immutable variant to copy from
     */
    public StructureSetMutableBeanImpl(StructureSetBean bean) {
        super(bean);
        if (bean.getRelatedStructures() != null) {
            setRelatedStructures(new RelatedStructuresMutableBeanImpl(bean.getRelatedStructures()));
        }
        for (StructureMapBean each : bean.getStructureMapList()) {
            addStructureMap(new StructureMapMutableBeanImpl(each));
        }
        for (CodelistMapBean each : bean.getCodelistMapList()) {
            addCodelistMap(new CodelistMapMutableBeanImpl(each));
        }
        for (CategorySchemeMapBean each : bean.getCategorySchemeMapList()) {
            addCategorySchemeMap(new CategorySchemeMapMutableBeanImpl(each));
        }
        for (ConceptSchemeMapBean each : bean.getConceptSchemeMapList()) {
            addConceptSchemeMap(new ConceptSchemeMapMutableBeanImpl(each));
        }
        for (OrganisationSchemeMapBean each : bean.getOrganisationSchemeMapList()) {
            addOrganisationSchemeMap(new OrganisationSchemeMapMutableBeanImpl(each));
        }
    }

    /**
     * Create immutable variant
     */
    @Override
    public StructureSetBean getImmutableInstance() {
        return new StructureSetBeanImpl(this);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public RelatedStructuresMutableBean getRelatedStructures() {
        return relatedStructures;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////SETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void setRelatedStructures(RelatedStructuresMutableBean relatedStructures) {
        this.relatedStructures = relatedStructures;
    }

    @Override
    public List<StructureMapMutableBean> getStructureMapList() {
        if (structureMapList == null) structureMapList = new ArrayList<StructureMapMutableBean>();
        return structureMapList;
    }

    @Override
    public void setStructureMapList(List<StructureMapMutableBean> structureMapList) {
        this.structureMapList = structureMapList;
    }

    @Override
    public List<CodelistMapMutableBean> getCodelistMapList() {
        if (codelistMapList == null) codelistMapList = new ArrayList<CodelistMapMutableBean>();
        return codelistMapList;
    }

    @Override
    public void setCodelistMapList(List<CodelistMapMutableBean> codelistMapList) {
        this.codelistMapList = codelistMapList;
    }

    @Override
    public List<CategorySchemeMapMutableBean> getCategorySchemeMapList() {
        if (categorySchemeMapList == null) categorySchemeMapList = new ArrayList<CategorySchemeMapMutableBean>();
        return categorySchemeMapList;
    }

    @Override
    public void setCategorySchemeMapList(List<CategorySchemeMapMutableBean> categorySchemeMapList) {
        this.categorySchemeMapList = categorySchemeMapList;
    }

    @Override
    public List<ConceptSchemeMapMutableBean> getConceptSchemeMapList() {
        if (conceptSchemeMapList == null) conceptSchemeMapList = new ArrayList<ConceptSchemeMapMutableBean>();
        return conceptSchemeMapList;
    }

    @Override
    public void setConceptSchemeMapList(List<ConceptSchemeMapMutableBean> conceptSchemeMapList) {
        this.conceptSchemeMapList = conceptSchemeMapList;
    }

    @Override
    public List<OrganisationSchemeMapMutableBean> getOrganisationSchemeMapList() {
        if (organisationSchemeMapList == null)
            organisationSchemeMapList = new ArrayList<OrganisationSchemeMapMutableBean>();
        return organisationSchemeMapList;
    }

    @Override
    public void setOrganisationSchemeMapList(List<OrganisationSchemeMapMutableBean> organisationSchemeMapList) {
        this.organisationSchemeMapList = organisationSchemeMapList;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////ADDERS 								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void addCategorySchemeMap(CategorySchemeMapMutableBean categorySchemeMap) {
        getCategorySchemeMapList().add(categorySchemeMap);
    }

    @Override
    public void addCodelistMap(CodelistMapMutableBean codelistMap) {
        getCodelistMapList().add(codelistMap);
    }

    @Override
    public void addConceptSchemeMap(ConceptSchemeMapMutableBean conceptSchemeMap) {
        getConceptSchemeMapList().add(conceptSchemeMap);
    }

    @Override
    public void addOrganisationSchemeMap(OrganisationSchemeMapMutableBean organisationSchemeMap) {
        getOrganisationSchemeMapList().add(organisationSchemeMap);
    }

    @Override
    public void addStructureMap(StructureMapMutableBean structureMap) {
        getStructureMapList().add(structureMap);
    }

}
