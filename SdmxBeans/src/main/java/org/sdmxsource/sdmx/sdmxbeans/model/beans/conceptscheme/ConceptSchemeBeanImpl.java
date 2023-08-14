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
package org.sdmxsource.sdmx.sdmxbeans.model.beans.conceptscheme;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.sdmx.resources.sdmxml.schemas.v20.common.TextType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.ConceptSchemeType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.ConceptType;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.constants.TERTIARY_BOOL;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.conceptscheme.ConceptBean;
import org.sdmxsource.sdmx.api.model.beans.conceptscheme.ConceptSchemeBean;
import org.sdmxsource.sdmx.api.model.mutable.conceptscheme.ConceptMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.conceptscheme.ConceptSchemeMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.ItemSchemeBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.conceptscheme.ConceptSchemeMutableBeanImpl;
import org.sdmxsource.util.ObjectUtil;

import javax.xml.bind.ValidationException;
import java.net.URL;
import java.util.*;


/**
 * The type Concept scheme bean.
 */
public class ConceptSchemeBeanImpl extends ItemSchemeBeanImpl<ConceptBean> implements ConceptSchemeBean {
    private static final long serialVersionUID = 1L;
    private static Logger LOG = LoggerFactory.getLogger(ConceptSchemeBean.class);
    private transient Map<String, ConceptBean> itemById = new HashMap<String, ConceptBean>();

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM ITSELF, CREATES STUB BEAN //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private ConceptSchemeBeanImpl(ConceptSchemeBean bean, URL actualLocation, boolean isServiceUrl) {
        super(bean, actualLocation, isServiceUrl);
        LOG.debug("Stub ConceptSchemeBean Built");
    }


    /**
     * Instantiates a new Concept scheme bean.
     *
     * @param conceptScheme the concept scheme
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEANS			 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ConceptSchemeBeanImpl(ConceptSchemeMutableBean conceptScheme) {
        super(conceptScheme);
        LOG.debug("Building ConceptSchemeBean from Mutable Bean");
        try {
            if (conceptScheme.getItems() != null) {
                for (ConceptMutableBean concept : conceptScheme.getItems()) {
                    this.items.add(new ConceptBeanImpl(this, concept));
                }
            }
        } catch (SdmxSemmanticException ex) {
            throw new SdmxSemmanticException(ex, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        } catch (Throwable th) {
            throw new SdmxException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        }

        try {
            validate();
        } catch (ValidationException e) {
            throw new SdmxSemmanticException(e, ExceptionCode.FAIL_VALIDATION, this);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("ConceptSchemeBean Built " + this);
        }
    }


    /**
     * Instantiates a new Concept scheme bean.
     *
     * @param bean the bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ConceptSchemeBeanImpl(org.sdmx.resources.sdmxml.schemas.v21.structure.ConceptSchemeType bean) {
        super(bean, SDMX_STRUCTURE_TYPE.CONCEPT_SCHEME);
        LOG.debug("Building ConceptSchemeBean from 2.1 SDMX");
        try {
            for (org.sdmx.resources.sdmxml.schemas.v21.structure.ConceptType currentItem : bean.getConceptList()) {
                items.add(new ConceptBeanImpl(this, currentItem));
            }
        } catch (SdmxSemmanticException ex) {
            throw new SdmxSemmanticException(ex, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        } catch (Throwable th) {
            throw new SdmxException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        }

        try {
            validate();
        } catch (ValidationException e) {
            throw new SdmxSemmanticException(e, ExceptionCode.FAIL_VALIDATION, this);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("ConceptSchemeBean Built " + this);
        }
    }


    /**
     * Instantiates a new Concept scheme bean.
     *
     * @param bean the bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ConceptSchemeBeanImpl(ConceptSchemeType bean) {
        super(bean, SDMX_STRUCTURE_TYPE.CONCEPT_SCHEME,
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
        LOG.debug("Building ConceptSchemeBean from 2.0 SDMX");
        try {
            for (ConceptType currentItem : bean.getConceptList()) {
                items.add(new ConceptBeanImpl(this, currentItem));
            }
        } catch (SdmxSemmanticException ex) {
            throw new SdmxSemmanticException(ex, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        } catch (Throwable th) {
            throw new SdmxException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        }

        try {
            validate();
        } catch (ValidationException e) {
            throw new SdmxSemmanticException(e, ExceptionCode.FAIL_VALIDATION, this);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("ConceptSchemeBean Built " + this);
        }
    }

    /**
     * Default Scheme
     *
     * @param concepts the concepts
     * @param agencyId the agency id
     */
    public ConceptSchemeBeanImpl(List<ConceptType> concepts, String agencyId) {
        super(null, SDMX_STRUCTURE_TYPE.CONCEPT_SCHEME,
                null,
                null,
                DEFAULT_SCHEME_VERSION,
                TERTIARY_BOOL.FALSE,
                agencyId,
                DEFAULT_SCHEME_ID,
                null,
                getDefaultName(),
                null,
                TERTIARY_BOOL.FALSE,
                null);
        LOG.debug("Building ConceptSchemeBean from Stand Alone 2.0 Concepts");
        try {
            for (ConceptType currentItem : concepts) {
                if (!currentItem.getAgencyID().equals(this.getAgencyId())) {
                    throw new SdmxSemmanticException(ExceptionCode.FAIL_VALIDATION, "Attempting to create Default Concept Scheme from v1.0 List of concepts, and was provided with " +
                            "a concept that reference different agency reference ('" + currentItem.getAgencyID() + "') to the scheme agency ('" + this.getAgencyId() + "')");
                }
                items.add(new ConceptBeanImpl(this, currentItem));
            }
        } catch (SdmxSemmanticException ex) {
            throw new SdmxSemmanticException(ex, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        } catch (Throwable th) {
            throw new SdmxException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        }
        try {
            validate();
        } catch (ValidationException e) {
            throw new SdmxSemmanticException(e, ExceptionCode.FAIL_VALIDATION, this);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("ConceptSchemeBean Built " + this);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Default Scheme
     *
     * @param agencyId the agency id
     * @param concepts the concepts
     */
    public ConceptSchemeBeanImpl(String agencyId, List<org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.structure.ConceptType> concepts) {
        super(null, SDMX_STRUCTURE_TYPE.CONCEPT_SCHEME,
                null,
                null,
                DEFAULT_SCHEME_VERSION,
                TERTIARY_BOOL.FALSE,
                agencyId,
                DEFAULT_SCHEME_ID,
                null,
                getDefaultName(),
                null,
                TERTIARY_BOOL.FALSE,
                null);
        LOG.debug("Building ConceptSchemeBean from 1.0 SDMX");
        try {
            for (org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.structure.ConceptType currentItem : concepts) {
                if (!currentItem.getAgency().equals(this.getAgencyId())) {
                    throw new SdmxSemmanticException(ExceptionCode.FAIL_VALIDATION, "Attempting to create Default Concept Scheme from v1.0 List of concepts, and was provided with " +
                            "a concept that reference different agency reference ('" + currentItem.getAgency() + "') to the scheme agency ('" + this.getAgencyId() + "')");
                }
                items.add(new ConceptBeanImpl(this, currentItem));
            }
        } catch (SdmxSemmanticException ex) {
            throw new SdmxSemmanticException(ex, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        } catch (Throwable th) {
            throw new SdmxException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        }
        try {
            validate();
        } catch (ValidationException e) {
            throw new SdmxSemmanticException(e, ExceptionCode.FAIL_VALIDATION, this);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("ConceptSchemeBean Built " + this);
        }
    }

    private static List<TextType> getDefaultName() {
        List<TextType> returnList = new ArrayList<TextType>();
        TextType tt = TextType.Factory.newInstance();
        tt.setStringValue(DEFAULT_SCHEME_NAME);
        returnList.add(tt);
        return returnList;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////DEEP VALIDATION						 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean deepEquals(SDMXBean bean, boolean includeFinalProperties) {
        if (bean == null) {
            return false;
        }
        if (bean.getStructureType() == this.getStructureType()) {
            return super.deepEqualsInternal((ConceptSchemeBean) bean, includeFinalProperties);
        }
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATION							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private void validate() throws ValidationException {
        Set<String> urns = new HashSet<String>();
        if (this.getId().equals(DEFAULT_SCHEME_ID)) {
            if (!this.getVersion().equals(DEFAULT_SCHEME_VERSION)) {
                throw new SdmxSemmanticException(ExceptionCode.FAIL_VALIDATION, DEFAULT_SCHEME_ID + " can only be version " + DEFAULT_SCHEME_VERSION);
            }
            if (this.isFinal.isTrue()) {
                throw new SdmxSemmanticException(ExceptionCode.FAIL_VALIDATION, DEFAULT_SCHEME_ID + " can not be made final");
            }
        }
        if (items != null) {
            Map<ConceptBean, Set<ConceptBean>> parentChildMap = new HashMap<ConceptBean, Set<ConceptBean>>();

            for (ConceptBean concept : items) {
                if (urns.contains(concept.getUrn())) {
                    throw new SdmxSemmanticException(ExceptionCode.DUPLICATE_URN, concept.getUrn());
                }
                urns.add(concept.getUrn());
                try {
                    if (ObjectUtil.validString(concept.getParentConcept())) {
                        ConceptBean parent = getConceptBean(items, concept.getParentConcept());
                        Set<ConceptBean> children;
                        if (parentChildMap.containsKey(parent)) {
                            children = parentChildMap.get(parent);
                        } else {
                            children = new HashSet<ConceptBean>();
                            parentChildMap.put(parent, children);
                        }
                        children.add(concept);
                        //Check that the parent code is not directly or indirectly a child of the code it is parenting
                        recurseParentMap(parentChildMap.get(concept), parent, parentChildMap);
                    }
                } catch (SdmxSemmanticException ex) {
                    throw new SdmxSemmanticException(ex, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
                } catch (Throwable th) {
                    throw new SdmxException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
                }
            }
        }
    }

    @Override
    protected void validateId(boolean startWithIntAllowed) {
        //Not allowed to start with an integer
        super.validateId(false);
    }

    @Override
    public ConceptBean getItemById(String id) {
        if (itemById == null || itemById.size() == 0) {
            itemById = new HashMap<String, ConceptBean>();
            for (ConceptBean currentConcept : super.items) {
                itemById.put(currentConcept.getId(), currentConcept);
            }
            itemById = Collections.unmodifiableMap(itemById);
        }
        return itemById.get(id);
    }


    /**
     * Recurses the map checking the children of each child, if one of the children is the parent code, then an excetpion is thrown
     *
     * @param children
     * @param parentCode
     * @param parentChildMap
     */
    private void recurseParentMap(Set<ConceptBean> children, ConceptBean parent, Map<ConceptBean, Set<ConceptBean>> parentChildMap) {
        //If the child is also a parent
        if (children != null) {
            if (children.contains(parent)) {
                throw new SdmxSemmanticException(ExceptionCode.PARENT_RECURSIVE_LOOP, parent.getId());
            }
            for (ConceptBean currentChild : children) {
                recurseParentMap(parentChildMap.get(currentChild), parent, parentChildMap);
            }
        }
    }

    private ConceptBean getConceptBean(List<ConceptBean> concepts, String id) {
        for (ConceptBean currentBean : concepts) {
            if (currentBean.getId().equals(id)) {
                return currentBean;
            }
        }
        throw new SdmxSemmanticException(ExceptionCode.CAN_NOT_RESOLVE_PARENT, id);
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public ConceptSchemeBean getStub(URL actualLocation, boolean isServiceUrl) {
        return new ConceptSchemeBeanImpl(this, actualLocation, isServiceUrl);
    }

    @Override
    public boolean isDefaultScheme() {
        return this.getId().equals(DEFAULT_SCHEME_ID);
    }

    @Override
    public ConceptSchemeMutableBean getMutableInstance() {
        return new ConceptSchemeMutableBeanImpl(this);
    }
}
