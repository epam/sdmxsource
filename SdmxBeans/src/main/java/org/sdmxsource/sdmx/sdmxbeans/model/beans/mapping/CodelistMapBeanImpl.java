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

import org.sdmx.resources.sdmxml.schemas.v20.structure.CodeMapType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.CodelistMapType;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.mapping.CodelistMapBean;
import org.sdmxsource.sdmx.api.model.beans.mapping.ItemMapBean;
import org.sdmxsource.sdmx.api.model.beans.mapping.StructureSetBean;
import org.sdmxsource.sdmx.api.model.mutable.mapping.CodelistMapMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.util.RefUtil;
import org.sdmxsource.sdmx.util.beans.reference.CrossReferenceBeanImpl;
import org.sdmxsource.util.ObjectUtil;

import javax.xml.bind.ValidationException;


/**
 * The type Codelist map bean.
 */
public class CodelistMapBeanImpl extends ItemSchemeMapBeanImpl implements CodelistMapBean {
    private static final long serialVersionUID = 1L;

    private String srcAlias;
    private String targetAlias;


    /**
     * Instantiates a new Codelist map bean.
     *
     * @param bean   the bean
     * @param parent the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEANS			 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public CodelistMapBeanImpl(CodelistMapMutableBean bean, StructureSetBean parent) {
        super(bean, SDMX_STRUCTURE_TYPE.CODE_LIST_MAP, parent);
        this.srcAlias = bean.getSrcAlias();
        this.targetAlias = bean.getTargetAlias();

        if (bean.getTargetRef() != null) {
            this.targetRef = new CrossReferenceBeanImpl(this, bean.getTargetRef());
        }
        if (bean.getSourceRef() != null) {
            this.sourceRef = new CrossReferenceBeanImpl(this, bean.getSourceRef());
        }
        try {
            validate();
        } catch (ValidationException e) {
            throw new SdmxSemmanticException(e, ExceptionCode.FAIL_VALIDATION, this);
        }
    }


    /**
     * Instantiates a new Codelist map bean.
     *
     * @param codelistMapType the codelist map type
     * @param parent          the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public CodelistMapBeanImpl(org.sdmx.resources.sdmxml.schemas.v21.structure.CodelistMapType codelistMapType, StructureSetBean parent) {
        super(codelistMapType, SDMX_STRUCTURE_TYPE.CODE_LIST_MAP, parent);
        try {
            this.sourceRef = RefUtil.createReference(this, codelistMapType.getSource());
            this.targetRef = RefUtil.createReference(this, codelistMapType.getTarget());
            if (codelistMapType.getCodeMapList() != null) {
                for (org.sdmx.resources.sdmxml.schemas.v21.structure.CodeMapType codeMap : codelistMapType.getCodeMapList()) {
                    ItemMapBean item = new ItemMapBeanImpl(codeMap.getSource().getRef().getId(), codeMap.getTarget().getRef().getId(), this);
                    items.add(item);
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
    }


    /**
     * Instantiates a new Codelist map bean.
     *
     * @param codeMapType the code map type
     * @param parent      the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public CodelistMapBeanImpl(CodelistMapType codeMapType, StructureSetBean parent) {
        super(codeMapType, SDMX_STRUCTURE_TYPE.CODE_LIST_MAP,
                codeMapType.getId(), null, codeMapType.getNameList(),
                codeMapType.getDescriptionList(), codeMapType.getAnnotations(), parent);
        try {
            if (codeMapType.getCodelistRef() != null) {
                if (ObjectUtil.validString(codeMapType.getCodelistRef().getURN())) {
                    this.sourceRef = new CrossReferenceBeanImpl(this, codeMapType.getCodelistRef().getURN());
                } else {
                    String agencyId = codeMapType.getCodelistRef().getAgencyID();
                    if (!ObjectUtil.validString(agencyId)) {
                        agencyId = this.getMaintainableParent().getAgencyId();
                    }
                    this.sourceRef = new CrossReferenceBeanImpl(this, agencyId,
                            codeMapType.getCodelistRef().getCodelistID(),
                            codeMapType.getCodelistRef().getVersion(),
                            SDMX_STRUCTURE_TYPE.CODE_LIST);
                }
            } else if (codeMapType.getHierarchicalCodelistRef() != null) {
                if (ObjectUtil.validString(codeMapType.getHierarchicalCodelistRef().getURN())) {
                    this.sourceRef = new CrossReferenceBeanImpl(this, codeMapType.getHierarchicalCodelistRef().getURN());
                } else {
                    String agencyId = codeMapType.getHierarchicalCodelistRef().getAgencyID();
                    if (!ObjectUtil.validString(agencyId)) {
                        agencyId = this.getMaintainableParent().getAgencyId();
                    }
                    this.sourceRef = new CrossReferenceBeanImpl(this, agencyId,
                            codeMapType.getHierarchicalCodelistRef().getHierarchicalCodelistID(),
                            codeMapType.getHierarchicalCodelistRef().getVersion(),
                            SDMX_STRUCTURE_TYPE.HIERARCHICAL_CODELIST);
                }
            }


            if (codeMapType.getTargetCodelistRef() != null) {
                if (ObjectUtil.validString(codeMapType.getTargetCodelistRef().getURN())) {
                    this.targetRef = new CrossReferenceBeanImpl(this, codeMapType.getTargetCodelistRef().getURN());
                } else {
                    String agencyId = codeMapType.getTargetCodelistRef().getAgencyID();
                    if (!ObjectUtil.validString(agencyId)) {
                        agencyId = this.getMaintainableParent().getAgencyId();
                    }
                    this.targetRef = new CrossReferenceBeanImpl(this, agencyId,
                            codeMapType.getTargetCodelistRef().getCodelistID(),
                            codeMapType.getTargetCodelistRef().getVersion(),
                            SDMX_STRUCTURE_TYPE.CODE_LIST);
                }
            } else if (codeMapType.getTargetHierarchicalCodelistRef() != null) {
                if (ObjectUtil.validString(codeMapType.getTargetHierarchicalCodelistRef().getURN())) {
                    this.targetRef = new CrossReferenceBeanImpl(this, codeMapType.getTargetHierarchicalCodelistRef().getURN());
                } else {
                    String agencyId = codeMapType.getTargetHierarchicalCodelistRef().getAgencyID();
                    if (!ObjectUtil.validString(agencyId)) {
                        agencyId = this.getMaintainableParent().getAgencyId();
                    }
                    this.targetRef = new CrossReferenceBeanImpl(this, agencyId,
                            codeMapType.getTargetHierarchicalCodelistRef().getHierarchicalCodelistID(),
                            codeMapType.getTargetHierarchicalCodelistRef().getVersion(),
                            SDMX_STRUCTURE_TYPE.HIERARCHICAL_CODELIST);
                }
            }
            // get list of code maps
            if (codeMapType.getCodeMapList() != null) {
                for (CodeMapType codeMap : codeMapType.getCodeMapList()) {
                    ItemMapBean item = new ItemMapBeanImpl(codeMap.getCodeAlias(),
                            codeMap.getMapCodeRef(),
                            codeMap.getMapTargetCodeRef(), this);
                    items.add(item);
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
            CodelistMapBean that = (CodelistMapBean) bean;
            if (!ObjectUtil.equivalent(srcAlias, that.getSrcAlias())) {
                return false;
            }
            if (!ObjectUtil.equivalent(srcAlias, that.getTargetAlias())) {
                return false;
            }
            return super.deepEqualsInternal(that, includeFinalProperties);
        }
        return false;
    }

    /**
     * Validate.
     *
     * @throws ValidationException the validation exception
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATION							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    protected void validate() throws ValidationException {
        if (this.sourceRef == null) {
            throw new SdmxSemmanticException(ExceptionCode.BEAN_MISSING_REQUIRED_ELEMENT, this.structureType, "Source");
        }
        if (this.targetRef == null) {
            throw new SdmxSemmanticException(ExceptionCode.BEAN_MISSING_REQUIRED_ELEMENT, this.structureType, "Target");
        }
        if (this.items == null) {
            throw new SdmxSemmanticException(ExceptionCode.BEAN_MISSING_REQUIRED_ELEMENT, this.structureType, "CodeMap");
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public String getSrcAlias() {
        return srcAlias;
    }

    @Override
    public String getTargetAlias() {
        return targetAlias;
    }
}
