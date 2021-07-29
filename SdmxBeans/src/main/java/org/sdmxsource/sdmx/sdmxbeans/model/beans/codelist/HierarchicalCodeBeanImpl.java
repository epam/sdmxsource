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

import org.sdmx.resources.sdmxml.schemas.v20.structure.CodeRefType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.HierarchicalCodeType;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.constants.TIME_FORMAT;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.base.SdmxDate;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.base.SdmxStructureBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.*;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.reference.MaintainableRefBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.mutable.reference.CodeRefMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.IdentifiableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.SdmxDateImpl;
import org.sdmxsource.sdmx.sdmxbeans.util.RefUtil;
import org.sdmxsource.sdmx.util.beans.reference.CrossReferenceBeanImpl;
import org.sdmxsource.sdmx.util.beans.reference.StructureReferenceBeanImpl;
import org.sdmxsource.util.ObjectUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * CodeRefBean represents a Codelist item in a HierarchicalCodelistBean, named from the 2.0 SDMX Schema (in 2.1 it is called a HierarchicalCode).
 * It can have child CodeRefBeans.
 *
 * @author richard
 */
public class HierarchicalCodeBeanImpl extends IdentifiableBeanImpl implements HierarchicalCodeBean {
    private static final long serialVersionUID = 1L;
    private List<HierarchicalCodeBean> codeRefs = new ArrayList<HierarchicalCodeBean>();
    private CrossReferenceBean codeReference;
    private String codelistAliasRef;
    private String codeId;
    private SdmxDate validFrom;
    private SdmxDate validTo;
    private String levelRef;
    private transient LevelBean level;


    /**
     * Instantiates a new Hierarchical code bean.
     *
     * @param bean   the bean
     * @param parent the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEAN				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public HierarchicalCodeBeanImpl(CodeRefMutableBean bean, SdmxStructureBean parent) {
        super(bean, parent);
        try {
            if (bean.getCodeReference() != null) {
                this.codeReference = new CrossReferenceBeanImpl(this, bean.getCodeReference());
            }
            this.codelistAliasRef = bean.getCodelistAliasRef();
            this.codeId = bean.getCodeId();

            if (bean.getCodeRefs() != null) {
                for (CodeRefMutableBean currentCodeRef : bean.getCodeRefs()) {
                    this.codeRefs.add(new HierarchicalCodeBeanImpl(currentCodeRef, this));
                }
            }
        } catch (Throwable th) {
            throw new SdmxSemmanticException(th, "Error creating structure: " + this.toString());
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
     * Instantiates a new Hierarchical code bean.
     *
     * @param codeRef the code ref
     * @param parent  the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public HierarchicalCodeBeanImpl(HierarchicalCodeType codeRef, SdmxStructureBean parent) {
        super(codeRef, SDMX_STRUCTURE_TYPE.HIERARCHICAL_CODE, parent);
        // Either a Code ref is local or not
        if (codeRef.getCodeID() != null && codeRef.getCodeID().getRef() != null && codeRef.getCodelistAliasRef() != null) {
            // Local - has CodelistAliasRef and CodeID
            this.codeId = codeRef.getCodeID().getRef().getId();
            this.codelistAliasRef = codeRef.getCodelistAliasRef();
        }
        if (codeRef.getLevel() != null) {
            this.levelRef = RefUtil.createLocalIdReference(codeRef.getLevel());
        }
        if (codeRef.getCode() != null) {
            this.codeReference = RefUtil.createReference(this, codeRef.getCode());
        }
        if (codeRef.getValidFrom() != null) {
            validFrom = new SdmxDateImpl(codeRef.getValidFrom().getTime(), TIME_FORMAT.DATE_TIME);
        }
        if (codeRef.getValidTo() != null) {
            validTo = new SdmxDateImpl(codeRef.getValidTo().getTime(), TIME_FORMAT.DATE_TIME);
        }
        // Children
        if (codeRef.getHierarchicalCodeList() != null) {
            for (HierarchicalCodeType currentCodeRef : codeRef.getHierarchicalCodeList()) {
                this.codeRefs.add(new HierarchicalCodeBeanImpl(currentCodeRef, this));
            }
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
     * Instantiates a new Hierarchical code bean.
     *
     * @param codeRef the code ref
     * @param parent  the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public HierarchicalCodeBeanImpl(CodeRefType codeRef, SdmxStructureBean parent) {
        super(generateId(codeRef, parent), SDMX_STRUCTURE_TYPE.HIERARCHICAL_CODE, parent);
        if (ObjectUtil.validString(codeRef.getURN())) {
            this.codeReference = new CrossReferenceBeanImpl(this, codeRef.getURN());
        }
        this.codeId = codeRef.getCodeID();
        this.codelistAliasRef = codeRef.getCodelistAliasRef();
        if (codeRef.getCodeRefList() != null) {
            for (CodeRefType currentCodeRef : codeRef.getCodeRefList()) {
                this.codeRefs.add(new HierarchicalCodeBeanImpl(currentCodeRef, this));
            }
        }
        if (codeRef.getValidFrom() != null) {
            validFrom = new SdmxDateImpl(codeRef.getValidFrom().toString());
        }
        if (codeRef.getValidTo() != null) {
            validTo = new SdmxDateImpl(codeRef.getValidTo().toString());
        }
        if (codeRef.getLevelRef() != null) {
            HierarchyBean hierarchy = getParent(HierarchyBean.class, false);
            LevelBean level = hierarchy.getLevel();
            levelRef = "";
            while (!level.getId().equals(codeRef.getLevelRef())) {
                levelRef += level.getId() + ".";
                level = level.getChildLevel();
            }
            levelRef += level.getId();
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
     * Generate a code id, this is either the code alias (2.0 attribute not in 2.1) if this is not present then will use the
     * code id of the referenced code - if this id is the same as another id in the hierarchical level, then will postfix with an underscore
     * followed by an integer, starting at 2 (working it's way up until a unique id is found).
     *
     * @param codeRef
     * @param parent
     * @return
     */
    private static String generateId(CodeRefType codeRef, SDMXBean parent) {
        if (ObjectUtil.validString(codeRef.getNodeAliasID())) {
            return codeRef.getNodeAliasID();
        }
        List<HierarchicalCodeBean> currentChildren = null;
        if (parent.getStructureType() == SDMX_STRUCTURE_TYPE.HIERARCHY) {
            currentChildren = ((HierarchyBean) parent).getHierarchicalCodeBeans();
        } else {
            ((HierarchicalCodeBean) parent).getCodeRefs();
        }
        String codeId = null;
        if (ObjectUtil.validString(codeRef.getCodeID())) {
            codeId = codeRef.getCodeID();
        } else {
            StructureReferenceBean localXsRef;
            if (ObjectUtil.validString(codeRef.getURN())) {
                localXsRef = new StructureReferenceBeanImpl(codeRef.getURN());
            } else {
                throw new SdmxSemmanticException("Could not generate Hierarchical Code Id - no NodeAlias Id, Code ID, or Code URN found");
            }
            if (localXsRef.getTargetReference() != SDMX_STRUCTURE_TYPE.CODE) {
                throw new SdmxSemmanticException(ExceptionCode.REFERENCE_ERROR_UNEXPECTED_STRUCTURE, SDMX_STRUCTURE_TYPE.CODE.getType(), localXsRef.getTargetReference().getType());
            }
            codeId = localXsRef.getChildReference().getId();
        }
        //Check CodeId is unique
        String inputId = codeId;
        int i = 2;
        while (!isCodeIdUnique(currentChildren, codeId)) {
            codeId = inputId + "_" + i;
            i++;
        }
        return codeId;
    }

    private static boolean isCodeIdUnique(List<HierarchicalCodeBean> currentChildren, String codeId) {
        if (currentChildren != null) {
            for (HierarchicalCodeBean currentChild : currentChildren) {
                if (currentChild.getId().equals(codeId)) {
                    return false;
                }
            }
        }
        return true;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATION							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private void validate() {
        // Each hierarchy's coderef must have an AliasRef with CodeId, OR a URN alone.
        if (ObjectUtil.validString(codelistAliasRef)) {
            if (!ObjectUtil.validString(codeId)) {
                throw new SdmxSemmanticException(ExceptionCode.CODE_REF_MISSING_CODE_ID, this.toString());
            }
            if (codeReference != null) {
                CrossReferenceBean generatedReference = generateCodelistReference();
                if (!generatedReference.getTargetUrn().equals(codeReference.getTargetUrn())) {
                    throw new SdmxSemmanticException("Code reference was supplied both by a codelist alias ('" + codelistAliasRef + "') and code Id ('" + codeId + "'), and by a code URN ('" + codeReference.getTargetUrn() + "') - " +
                            "the two references contradict each other");
                }
            } else {
                this.codeReference = getCodeReference();
                if (codeReference == null) {
                    throw new SdmxSemmanticException("Could not resolve reference to codelist with alias : " + codelistAliasRef);
                }
            }
        } else if (codeReference != null) {
            if (ObjectUtil.validString(codeId)) {
                if (!codeReference.getChildReference().getId().equals(codeId)) {
                    throw new SdmxSemmanticException("Code id was supplied both by a code Id ('" + codeId + "'), and by a code URN ('" + codeReference.getTargetUrn() + "') - " +
                            "the two references contradict each other");
                }
            }
            if (codeReference.getTargetReference() != SDMX_STRUCTURE_TYPE.CODE) {
                throw new SdmxSemmanticException(ExceptionCode.REFERENCE_ERROR_UNEXPECTED_STRUCTURE, SDMX_STRUCTURE_TYPE.CODE.getType(), codeReference.getTargetReference().getType());
            }
            this.codeId = codeReference.getChildReference().getId();
        } else {
            throw new SdmxSemmanticException(ExceptionCode.CODE_REF_MISSING_CODE_REFERENCE, this.toString());
        }
        if (validFrom != null && validTo != null) {
            if (validFrom.isLater(validTo)) {
                throw new SdmxSemmanticException("Hierarchical Code Error - Valid from can not be after valid to");
            }
        }
        if (ObjectUtil.validString(levelRef)) {
            HierarchyBean heirarchy = getParent(HierarchyBean.class, false);
            String[] levelSplit = levelRef.split("\\.");
            level = heirarchy.getLevelAtPosition(levelSplit.length);
            if (level == null) {
                throw new SdmxSemmanticException("Hierarchical Code Error - Could not find level with id '" + levelRef + "', ensure the Level Reference is dot '.' seperated e.g. L1.L2.L3 (where L1 is the Id of the first level, L2 is the Id of the second level and L3 is the id of the third level) ");
            }

            String levelFullId = level.getFullIdPath(false);
            if (!levelFullId.equals(levelRef)) {
                throw new SdmxSemmanticException("Hierarchical Code Error - Could not find level with id '" + levelRef + "', Level id at depth '" + levelSplit.length + "' is '" + levelFullId + "', ensure the Level Reference is dot '.' seperated e.g. L1.L2.L3 (where L1 is the Id of the first level, L2 is the Id of the second level and L3 is the id of the third level) ");
            }
        } else {
            HierarchyBean heirarchy = getParent(HierarchyBean.class, false);
            if (heirarchy.hasFormalLevels()) {
                int depthOfHierarchy = getLevelInHierarchy();
                level = heirarchy.getLevelAtPosition(depthOfHierarchy);
                if (level == null) {
                    throw new SdmxSemmanticException("Hierarchical Code Error - Hierarchy states that there are formal levels, but there is no level defined for a hierarchy of depth '" + depthOfHierarchy + "'");
                }
            }
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
            HierarchicalCodeBean that = (HierarchicalCodeBean) bean;
            if (!super.equivalent(codeRefs, that.getCodeRefs(), includeFinalProperties)) {
                return false;
            }
            if (!super.equivalent(codeReference, that.getCodeReference())) {
                return false;
            }
            if (!ObjectUtil.equivalent(codeId, that.getCodeId())) {
                return false;
            }
            if (!ObjectUtil.equivalent(validFrom, that.getValidFrom())) {
                return false;
            }
            if (!ObjectUtil.equivalent(validTo, that.getValidTo())) {
                return false;
            }
            if (!ObjectUtil.equivalent(getLevel(true), that.getLevel(true))) {
                return false;
            }
            if (getLevelInHierarchy() != that.getLevelInHierarchy()) {
                return false;
            }
            return super.deepEqualsInternal(that, includeFinalProperties);
        }
        return false;
    }

    @Override
    protected Set<CrossReferenceBean> getCrossReferenceInternal() {
        Set<CrossReferenceBean> references = new HashSet<CrossReferenceBean>();
        if (codeReference != null) {
            references.add(codeReference);
        }
        return references;
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public List<HierarchicalCodeBean> getCodeRefs() {
        return new ArrayList<HierarchicalCodeBean>(codeRefs);
    }

    @Override
    public int getLevelInHierarchy() {
        return getFullIdPath(false).split("\\.").length;
    }

    @Override
    public LevelBean getLevel(boolean acceptDefault) {
        if (!ObjectUtil.validString(levelRef)) {
            if (!acceptDefault) {
                return null;
            }
            if (level == null) {
                HierarchyBean hb = getParent(HierarchyBean.class, false);
                level = hb.getLevelAtPosition(getLevelInHierarchy());
            }
        }
        return level;
    }

    @Override
    public CrossReferenceBean getCodeReference() {
        if (this.codeReference == null) {
            this.codeReference = generateCodelistReference();
        }
        return codeReference;
    }

    private CrossReferenceBean generateCodelistReference() {
        HierarchicalCodelistBean hcl = (HierarchicalCodelistBean) this.getMaintainableParent();
        if (hcl.getCodelistRef() != null) {
            for (CodelistRefBean currentCodelistRef : hcl.getCodelistRef()) {
                if (currentCodelistRef.getAlias().equals(this.getCodelistAliasRef())) {
                    MaintainableRefBean codelistRef = currentCodelistRef.getCodelistReference().getMaintainableReference();
                    return new CrossReferenceBeanImpl(this, codelistRef.getAgencyId(), codelistRef.getMaintainableId(), codelistRef.getVersion(), SDMX_STRUCTURE_TYPE.CODE, this.codeId);
                }
            }
        }
        return null;
    }

    @Override
    public String getCodelistAliasRef() {
        return codelistAliasRef;
    }

    @Override
    public String getCodeId() {
        return codeId;
    }

    @Override
    public SdmxDate getValidFrom() {
        return validFrom;
    }

    @Override
    public SdmxDate getValidTo() {
        return validTo;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////COMPOSITES				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected Set<SDMXBean> getCompositesInternal() {
        Set<SDMXBean> composites = super.getCompositesInternal();
        super.addToCompositeSet(codeRefs, composites);
        return composites;
    }
}
