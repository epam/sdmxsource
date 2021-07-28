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
package org.sdmxsource.sdmx.sdmxbeans.model.beans.codelist;

import org.sdmx.resources.sdmxml.schemas.v20.structure.CodelistRefType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.HierarchicalCodelistType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.HierarchyType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.IncludedCodelistReferenceType;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.CodelistRefBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.HierarchicalCodelistBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.HierarchyBean;
import org.sdmxsource.sdmx.api.model.mutable.base.HierarchyMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.codelist.HierarchicalCodelistMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.reference.CodelistRefMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.MaintainableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.codelist.HierarchicalCodelistMutableBeanImpl;
import org.sdmxsource.util.ObjectUtil;

import javax.xml.bind.ValidationException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * The type Hierarchical codelist bean.
 */
public class HierarchicalCodelistBeanImpl extends MaintainableBeanImpl implements HierarchicalCodelistBean {
    private static final long serialVersionUID = 1L;
    private List<HierarchyBean> hierarchies = new ArrayList<HierarchyBean>();
    private List<CodelistRefBean> codelistRef = new ArrayList<CodelistRefBean>();

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM ITSELF, CREATES STUB BEAN //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private HierarchicalCodelistBeanImpl(HierarchicalCodelistBean bean, URL actualLocation, boolean isServiceUrl) {
        super(bean, actualLocation, isServiceUrl);
    }

    /**
     * Instantiates a new Hierarchical codelist bean.
     *
     * @param bean the bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEANS			 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public HierarchicalCodelistBeanImpl(HierarchicalCodelistMutableBean bean) {
        super(bean);
        try {
            if (bean.getCodelistRef() != null) {
                for (CodelistRefMutableBean currentRef : bean.getCodelistRef()) {
                    codelistRef.add(new CodelistRefBeanImpl(currentRef, this));
                }
            }
            if (bean.getHierarchies() != null) {
                for (HierarchyMutableBean currentHierarchy : bean.getHierarchies()) {
                    hierarchies.add(new HierarchyBeanImpl(this, currentHierarchy));
                }
            }
        } catch (SdmxSemmanticException ex) {
            throw new SdmxSemmanticException(ex, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        } catch (Throwable th) {
            throw new SdmxException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        }
        try {
            validate();
        } catch (SdmxSemmanticException ex) {
            throw new SdmxSemmanticException(ex, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        } catch (Throwable th) {
            throw new SdmxException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        }
    }


    /**
     * Instantiates a new Hierarchical codelist bean.
     *
     * @param bean the bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public HierarchicalCodelistBeanImpl(org.sdmx.resources.sdmxml.schemas.v21.structure.HierarchicalCodelistType bean) {
        super(bean, SDMX_STRUCTURE_TYPE.HIERARCHICAL_CODELIST);

        try {
            if (bean.getIncludedCodelistList() != null) {
                for (IncludedCodelistReferenceType currentRef : bean.getIncludedCodelistList()) {
                    if (currentRef.getRef() == null)
                        codelistRef.add(new CodelistRefBeanImpl(currentRef.getURN(), currentRef.getAlias(), this));
                    else {
                        codelistRef.add(new CodelistRefBeanImpl(currentRef.getRef().getAgencyID(), currentRef.getRef().getId(), currentRef.getRef().getVersion(), currentRef.getAlias(), this));
                    }
                }
            }
            if (bean.getHierarchyList() != null) {
                for (HierarchyType currentValueHierarchy : bean.getHierarchyList()) {
                    hierarchies.add(new HierarchyBeanImpl(this, currentValueHierarchy));
                }
            }
        } catch (SdmxSemmanticException ex) {
            throw new SdmxSemmanticException(ex, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        } catch (Throwable th) {
            throw new SdmxException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        }
        try {
            validate();
        } catch (SdmxSemmanticException ex) {
            throw new SdmxSemmanticException(ex, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        } catch (Throwable th) {
            throw new SdmxException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        }
    }


    /**
     * Instantiates a new Hierarchical codelist bean.
     *
     * @param bean the bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public HierarchicalCodelistBeanImpl(HierarchicalCodelistType bean) {
        super(bean, SDMX_STRUCTURE_TYPE.HIERARCHICAL_CODELIST,
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
        try {
            if (bean.getCodelistRefList() != null) {
                for (CodelistRefType currentRef : bean.getCodelistRefList()) {
                    if (currentRef.getURN() != null) {
                        try {
                            codelistRef.add(new CodelistRefBeanImpl(currentRef.getURN(), currentRef.getAlias(), this));
                        } catch (Throwable th) {
                            throw new SdmxSemmanticException(th, "Error while trying to parse CodelistRef for " + SDMX_STRUCTURE_TYPE.HIERARCHICAL_CODELIST);
                        }
                    } else {
                        try {
                            codelistRef.add(new CodelistRefBeanImpl(currentRef.getAgencyID(), currentRef.getCodelistID(), currentRef.getVersion(), currentRef.getAlias(), this));
                        } catch (Throwable th) {
                            throw new SdmxSemmanticException(th, "Error while trying to parse CodelistRef for " + SDMX_STRUCTURE_TYPE.HIERARCHICAL_CODELIST);
                        }
                    }
                }
            }
            if (bean.getHierarchyList() != null) {
                for (org.sdmx.resources.sdmxml.schemas.v20.structure.HierarchyType currentHierarchy : bean.getHierarchyList()) {
                    hierarchies.add(new HierarchyBeanImpl(this, currentHierarchy));
                }
            }
        } catch (SdmxSemmanticException ex) {
            throw new SdmxSemmanticException(ex, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        } catch (Throwable th) {
            throw new SdmxException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        }
        try {
            validate();
        } catch (SdmxSemmanticException ex) {
            throw new SdmxSemmanticException(ex, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        } catch (Throwable th) {
            throw new SdmxException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
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
            HierarchicalCodelistBean that = (HierarchicalCodelistBean) bean;
            if (!super.equivalent(hierarchies, that.getHierarchies(), includeFinalProperties)) {
                return false;
            }
            if (!super.equivalent(codelistRef, that.getCodelistRef(), includeFinalProperties)) {
                return false;
            }
            return super.deepEqualsInternal(that, includeFinalProperties);
        }
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATION							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private void validate() throws ValidationException {
        // Ensure codelist refs have unique aliases
        if (this.codelistRef != null) {
            Set<String> codelisAlias = new HashSet<String>();
            for (CodelistRefBean currentRef : this.codelistRef) {
                if (ObjectUtil.validString(currentRef.getAlias())) {
                    if (codelisAlias.contains(currentRef.getAlias())) {
                        throw new SdmxSemmanticException(ExceptionCode.DUPLICATE_ALIAS, currentRef.getAlias());
                    }
                    codelisAlias.add(currentRef.getAlias());
                }
            }
        }
        // Ensure hierarchies and their levels have unique URNs
        if (this.hierarchies != null) {
            Set<String> hierarchyUrns = new HashSet<String>();
            for (HierarchyBean currentHierarhy : this.hierarchies) {
                try {
                    if (hierarchyUrns.contains(currentHierarhy.getUrn())) {
                        throw new SdmxSemmanticException(ExceptionCode.DUPLICATE_URN, currentHierarhy);
                    }
                    hierarchyUrns.add(currentHierarhy.getUrn());
                } catch (SdmxSemmanticException ex) {
                    throw new SdmxSemmanticException(ex, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
                } catch (Throwable th) {
                    throw new SdmxException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
                }
            }
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public HierarchicalCodelistBean getStub(URL actualLocation, boolean isServiceUrl) {
        return new HierarchicalCodelistBeanImpl(this, actualLocation, isServiceUrl);
    }

    @Override
    public HierarchicalCodelistMutableBean getMutableInstance() {
        return new HierarchicalCodelistMutableBeanImpl(this);
    }

    @Override
    public List<HierarchyBean> getHierarchies() {
        return new ArrayList<HierarchyBean>(hierarchies);
    }

    @Override
    public List<CodelistRefBean> getCodelistRef() {
        return new ArrayList<CodelistRefBean>(codelistRef);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////COMPOSITES				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected Set<SDMXBean> getCompositesInternal() {
        Set<SDMXBean> composites = super.getCompositesInternal();
        super.addToCompositeSet(hierarchies, composites);
        super.addToCompositeSet(codelistRef, composites);
        return composites;
    }
}
