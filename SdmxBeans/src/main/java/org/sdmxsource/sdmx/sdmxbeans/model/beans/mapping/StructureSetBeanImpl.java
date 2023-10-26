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
package org.sdmxsource.sdmx.sdmxbeans.model.beans.mapping;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.sdmx.resources.sdmxml.schemas.v20.structure.StructureSetType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.*;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxNotImplementedException;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.mapping.*;
import org.sdmxsource.sdmx.api.model.mutable.base.StructureMapMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.mapping.*;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.MaintainableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.mapping.StructureSetMutableBeanImpl;
import org.sdmxsource.util.ObjectUtil;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * The type Structure set bean.
 */
public class StructureSetBeanImpl extends MaintainableBeanImpl implements StructureSetBean {
    private static final long serialVersionUID = 1L;
    private static Logger LOG = LoggerFactory.getLogger(StructureSetBeanImpl.class);
    private RelatedStructuresBean relatedStructures;
    private List<StructureMapBean> structureMapList = new ArrayList<StructureMapBean>();
    private List<CodelistMapBean> codelistMapList = new ArrayList<CodelistMapBean>();
    private List<CategorySchemeMapBean> categorySchemeMapList = new ArrayList<CategorySchemeMapBean>();
    private List<ConceptSchemeMapBean> conceptSchemeMapList = new ArrayList<ConceptSchemeMapBean>();
    private List<OrganisationSchemeMapBean> organisationSchemeMapList = new ArrayList<OrganisationSchemeMapBean>();


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM ITSELF, CREATES STUB BEAN //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private StructureSetBeanImpl(StructureSetBean bean, URL actualLocation, boolean isServiceUrl) {
        super(bean, actualLocation, isServiceUrl);
        LOG.debug("Stub StructureSetBean Built");
    }

    /**
     * Instantiates a new Structure set bean.
     *
     * @param structureSet the structure set
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEANS			 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public StructureSetBeanImpl(StructureSetMutableBean structureSet) {
        super(structureSet);
        LOG.debug("Building StructureSetBean from Mutable Bean");
        try {
            if (structureSet.getRelatedStructures() != null) {
                this.relatedStructures = new RelatedStructuresBeanImpl(structureSet.getRelatedStructures(), this);
            }
            if (structureSet.getStructureMapList() != null) {
                for (StructureMapMutableBean each : structureSet.getStructureMapList()) {
                    this.structureMapList.add(new StructureMapBeanImpl(each, this));
                }
            }
            if (structureSet.getCodelistMapList() != null) {
                for (CodelistMapMutableBean each : structureSet.getCodelistMapList()) {
                    this.codelistMapList.add(new CodelistMapBeanImpl(each, this));
                }
            }
            if (structureSet.getCategorySchemeMapList() != null) {
                for (CategorySchemeMapMutableBean each : structureSet.getCategorySchemeMapList()) {
                    this.categorySchemeMapList.add(new CategorySchemeMapBeanImpl(each, this));
                }
            }
            if (structureSet.getConceptSchemeMapList() != null) {
                for (ConceptSchemeMapMutableBean each : structureSet.getConceptSchemeMapList()) {
                    this.conceptSchemeMapList.add(new ConceptSchemeMapBeanImpl(each, this));
                }
            }
            if (structureSet.getOrganisationSchemeMapList() != null) {
                for (OrganisationSchemeMapMutableBean each : structureSet.getOrganisationSchemeMapList()) {
                    this.organisationSchemeMapList.add(new OrganisationSchemeMapBeanImpl(each, this));
                }
            }
        } catch (Throwable th) {
            throw new SdmxSemmanticException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this);
        }

        try {
            validate();
        } catch (SdmxSemmanticException e) {
            throw new SdmxSemmanticException(e, ExceptionCode.FAIL_VALIDATION, this);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("StructureSetBean Built " + this.getUrn());
        }
    }


    /**
     * Instantiates a new Structure set bean.
     *
     * @param structureSet the structure set
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public StructureSetBeanImpl(org.sdmx.resources.sdmxml.schemas.v21.structure.StructureSetType structureSet) {
        super(structureSet, SDMX_STRUCTURE_TYPE.STRUCTURE_SET);
        LOG.debug("Building StructureSetBean from 2.1 SDMX");
        try {
            if (structureSet.getRelatedStructureList() != null) {
                this.relatedStructures = new RelatedStructuresBeanImpl(structureSet.getRelatedStructureList(), this);
            }
            if (structureSet.getStructureMapList() != null) {
                this.structureMapList = new ArrayList<StructureMapBean>();
                for (StructureMapType each : structureSet.getStructureMapList())
                    this.structureMapList.add(new StructureMapBeanImpl(each, this));
            }
            if (structureSet.getCodelistMapList() != null) {
                this.codelistMapList = new ArrayList<CodelistMapBean>();
                for (CodelistMapType each : structureSet.getCodelistMapList())
                    this.codelistMapList.add(new CodelistMapBeanImpl(each, this));
            }
            if (structureSet.getCategorySchemeMapList() != null) {
                this.categorySchemeMapList = new ArrayList<CategorySchemeMapBean>();
                for (CategorySchemeMapType each : structureSet.getCategorySchemeMapList())
                    this.categorySchemeMapList.add(new CategorySchemeMapBeanImpl(each, this));
            }
            if (structureSet.getConceptSchemeMapList() != null) {
                this.conceptSchemeMapList = new ArrayList<ConceptSchemeMapBean>();
                for (ConceptSchemeMapType each : structureSet.getConceptSchemeMapList())
                    this.conceptSchemeMapList.add(new ConceptSchemeMapBeanImpl(each, this));
            }
            if (structureSet.getOrganisationSchemeMapList() != null) {
                this.organisationSchemeMapList = new ArrayList<OrganisationSchemeMapBean>();
                for (OrganisationSchemeMapType each : structureSet.getOrganisationSchemeMapList())
                    this.organisationSchemeMapList.add(new OrganisationSchemeMapBeanImpl(each, this));
            }
            if (ObjectUtil.validCollection(structureSet.getReportingTaxonomyMapList())) {
                throw new SdmxNotImplementedException("ReportingTaxonomyMap");
            }
        } catch (Throwable th) {
            throw new SdmxSemmanticException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this);
        }

        try {
            validate();
        } catch (SdmxSemmanticException e) {
            throw new SdmxSemmanticException(e, ExceptionCode.FAIL_VALIDATION, this);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("StructureSetBean Built " + this.getUrn());
        }
    }


    /**
     * Instantiates a new Structure set bean.
     *
     * @param bean the bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public StructureSetBeanImpl(StructureSetType bean) {
        super(bean, SDMX_STRUCTURE_TYPE.STRUCTURE_SET,
                bean.getValidTo(),
                bean.getValidFrom(),
                bean.getVersion(),
                createTertiary(bean.isSetIsFinal(), bean.getIsFinal()),
                bean.getAgencyID(),
                bean.getId(),
                bean.getUri(),
                bean.getNameList(),
                bean.getDescriptionList(),
                createTertiary(bean.isSetIsExternalReference(), bean.getIsExternalReference()),
                bean.getAnnotations());
        LOG.debug("Building StructureSetBean from 2.0 SDMX");
        try {
            if (bean.getRelatedStructures() != null) {
                this.relatedStructures = new RelatedStructuresBeanImpl(bean.getRelatedStructures(), this);
            }
            if (bean.getStructureMap() != null) {
                this.structureMapList = new ArrayList<StructureMapBean>();
                this.structureMapList.add(new StructureMapBeanImpl(bean.getStructureMap(), this));
            }
            if (bean.getCodelistMap() != null) {
                this.codelistMapList = new ArrayList<CodelistMapBean>();
                this.codelistMapList.add(new CodelistMapBeanImpl(bean.getCodelistMap(), this));
            }
            if (bean.getCategorySchemeMap() != null) {
                this.categorySchemeMapList = new ArrayList<CategorySchemeMapBean>();
                this.categorySchemeMapList.add(new CategorySchemeMapBeanImpl(bean.getCategorySchemeMap(), this));
            }
            if (bean.getConceptSchemeMap() != null) {
                this.conceptSchemeMapList = new ArrayList<ConceptSchemeMapBean>();
                this.conceptSchemeMapList.add(new ConceptSchemeMapBeanImpl(bean.getConceptSchemeMap(), this));
            }
            if (bean.getOrganisationSchemeMap() != null) {
                this.organisationSchemeMapList = new ArrayList<OrganisationSchemeMapBean>();
                this.organisationSchemeMapList.add(new OrganisationSchemeMapBeanImpl(bean.getOrganisationSchemeMap(), this));
            }
        } catch (Throwable th) {
            throw new SdmxSemmanticException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this);
        }
        try {
            validate();
        } catch (SdmxSemmanticException e) {
            throw new SdmxSemmanticException(e, ExceptionCode.FAIL_VALIDATION, this);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("StructureSetBean Built " + this.getUrn());
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////DEEP EQUALS							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean deepEquals(SDMXBean bean, boolean includeFinalProperties) {
        if (bean == null) {
            return false;
        }
        if (bean.getStructureType() == this.getStructureType()) {
            StructureSetBean that = (StructureSetBean) bean;
            if (!super.equivalent(relatedStructures, that.getRelatedStructures(), includeFinalProperties)) {
                return false;
            }
            if (!super.equivalent(structureMapList, that.getStructureMapList(), includeFinalProperties)) {
                return false;
            }
            if (!super.equivalent(codelistMapList, that.getCodelistMapList(), includeFinalProperties)) {
                return false;
            }
            if (!super.equivalent(categorySchemeMapList, that.getCategorySchemeMapList(), includeFinalProperties)) {
                return false;
            }
            if (!super.equivalent(conceptSchemeMapList, that.getConceptSchemeMapList(), includeFinalProperties)) {
                return false;
            }
            if (!super.equivalent(organisationSchemeMapList, that.getOrganisationSchemeMapList(), includeFinalProperties)) {
                return false;
            }
            return super.deepEqualsInternal(that, includeFinalProperties);
        }
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATION							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private void validate() throws SdmxSemmanticException {

    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public StructureSetBean getStub(URL actualLocation, boolean isServiceUrl) {
        return new StructureSetBeanImpl(this, actualLocation, isServiceUrl);
    }

    @Override
    public StructureSetMutableBean getMutableInstance() {
        return new StructureSetMutableBeanImpl(this);
    }

    @Override
    public RelatedStructuresBean getRelatedStructures() {
        return relatedStructures;
    }

    @Override
    public List<StructureMapBean> getStructureMapList() {
        ArrayList<StructureMapBean> list = new ArrayList<StructureMapBean>();
        for (StructureMapBean each : structureMapList)
            list.add(each);
        return list;
    }

    @Override
    public List<CodelistMapBean> getCodelistMapList() {
        ArrayList<CodelistMapBean> list = new ArrayList<CodelistMapBean>();
        for (CodelistMapBean each : codelistMapList)
            list.add(each);
        return list;
    }

    @Override
    public List<CategorySchemeMapBean> getCategorySchemeMapList() {
        ArrayList<CategorySchemeMapBean> list = new ArrayList<CategorySchemeMapBean>();
        for (CategorySchemeMapBean each : categorySchemeMapList)
            list.add(each);
        return list;
    }

    @Override
    public List<ConceptSchemeMapBean> getConceptSchemeMapList() {
        ArrayList<ConceptSchemeMapBean> list = new ArrayList<ConceptSchemeMapBean>();
        for (ConceptSchemeMapBean each : conceptSchemeMapList)
            list.add(each);
        return list;
    }

    @Override
    public List<OrganisationSchemeMapBean> getOrganisationSchemeMapList() {
        return new ArrayList<OrganisationSchemeMapBean>(organisationSchemeMapList);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////COMPOSITES		                     //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected Set<SDMXBean> getCompositesInternal() {
        Set<SDMXBean> composites = super.getCompositesInternal();
        super.addToCompositeSet(relatedStructures, composites);
        super.addToCompositeSet(structureMapList, composites);
        super.addToCompositeSet(codelistMapList, composites);
        super.addToCompositeSet(categorySchemeMapList, composites);
        super.addToCompositeSet(conceptSchemeMapList, composites);
        super.addToCompositeSet(organisationSchemeMapList, composites);
        return composites;
    }
}
