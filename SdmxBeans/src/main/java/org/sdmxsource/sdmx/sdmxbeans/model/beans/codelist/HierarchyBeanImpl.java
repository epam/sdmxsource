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
import org.sdmx.resources.sdmxml.schemas.v20.structure.HierarchyType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.LevelType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.HierarchicalCodeType;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.HierarchicalCodeBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.HierarchicalCodelistBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.HierarchyBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.LevelBean;
import org.sdmxsource.sdmx.api.model.mutable.base.HierarchyMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.reference.CodeRefMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.NameableBeanImpl;
import org.sdmxsource.util.ObjectUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;


/**
 * The type Hierarchy bean.
 */
public class HierarchyBeanImpl extends NameableBeanImpl implements HierarchyBean {
    private static final long serialVersionUID = 1L;
    private List<HierarchicalCodeBean> codeRefs = new ArrayList<HierarchicalCodeBean>();
    private LevelBean level;
    private boolean hasFormalLevels;

    /**
     * Instantiates a new Hierarchy bean.
     *
     * @param parent    the parent
     * @param hierarchy the hierarchy
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEAN				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public HierarchyBeanImpl(HierarchicalCodelistBean parent, HierarchyMutableBean hierarchy) {
        super(hierarchy, parent);
        //LEVELS MUST BE SET BEFORE ANYTHING ELSE

        if (hierarchy.getHierarchicalCodeBeans() != null) {
            for (CodeRefMutableBean currentCoderef : hierarchy.getHierarchicalCodeBeans()) {
                codeRefs.add(new HierarchicalCodeBeanImpl(currentCoderef, this));
            }
        }
        this.hasFormalLevels = hierarchy.isFormalLevels();
        if (hierarchy.getChildLevel() != null) {
            this.level = new LevelBeanImpl(this, hierarchy.getChildLevel());
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
     * Instantiates a new Hierarchy bean.
     *
     * @param parent    the parent
     * @param hierarchy the hierarchy
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public HierarchyBeanImpl(HierarchicalCodelistBean parent, org.sdmx.resources.sdmxml.schemas.v21.structure.HierarchyType hierarchy) {
        super(hierarchy, SDMX_STRUCTURE_TYPE.HIERARCHY, parent);
        //LEVELS MUST BE SET BEFORE ANYTHING ELSE
        this.hasFormalLevels = hierarchy.getLeveled();

        if (hierarchy.getLevel() != null) {
            this.level = new LevelBeanImpl(this, hierarchy.getLevel());
        }
        if (hierarchy.getHierarchicalCodeList() != null) {
            for (HierarchicalCodeType currentCoderef : hierarchy.getHierarchicalCodeList()) {
                codeRefs.add(new HierarchicalCodeBeanImpl(currentCoderef, this));
            }
        }
        try {
            if (hierarchy.getLeveled()) {
                if (this.level == null) {
                    throw new SdmxSemmanticException("Hierarchy declares itself as levelled, but does not define any levels");
                }
            }
            validate();
        } catch (SdmxSemmanticException ex) {
            throw new SdmxSemmanticException(ex, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        } catch (Throwable th) {
            throw new SdmxException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        }
    }


    /**
     * Instantiates a new Hierarchy bean.
     *
     * @param parent    the parent
     * @param hierarchy the hierarchy
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public HierarchyBeanImpl(HierarchicalCodelistBean parent, HierarchyType hierarchy) {
        super(hierarchy, SDMX_STRUCTURE_TYPE.HIERARCHY, hierarchy.getId(), null, hierarchy.getNameList(), hierarchy.getDescriptionList(), hierarchy.getAnnotations(), parent);
        //LEVELS MUST BE SET BEFORE ANYTHING ELSE

        if (ObjectUtil.validCollection(hierarchy.getLevelList())) {
            TreeMap<Integer, LevelType> levelMap = new TreeMap<Integer, LevelType>();
            for (LevelType levl : hierarchy.getLevelList()) {
                levelMap.put(levl.getOrder().intValue(), levl);
            }
            List<LevelType> levelList = new ArrayList<LevelType>();
            for (int i = 1; i <= levelMap.lastKey(); i++) {
                if (levelMap.containsKey(i)) {
                    levelList.add(levelMap.get(i));
                } else {
                    LevelType defaultLevel = LevelType.Factory.newInstance();
                    defaultLevel.setId("DEFAULT");
                    defaultLevel.addNewName().setStringValue("Default");
                    levelList.add(defaultLevel);
                }
            }
            this.level = new LevelBeanImpl(this, levelList, 0);
            this.hasFormalLevels = true;
        }

        if (hierarchy.getCodeRefList() != null) {
            for (CodeRefType currentCoderef : hierarchy.getCodeRefList()) {
                codeRefs.add(new HierarchicalCodeBeanImpl(currentCoderef, this));
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

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////DEEP EQUALS							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean deepEquals(SDMXBean bean, boolean includeFinalProperties) {
        if (bean == null) {
            return false;
        }
        if (bean.getStructureType() == this.getStructureType()) {
            HierarchyBean that = (HierarchyBean) bean;
            if (!super.equivalent(codeRefs, that.getHierarchicalCodeBeans(), includeFinalProperties)) {
                return false;
            }
            if (!super.equivalent(level, that.getLevel(), includeFinalProperties)) {
                return false;
            }
            if (hasFormalLevels != that.hasFormalLevels()) {
                return false;
            }
            return super.deepEqualsInternal(that, includeFinalProperties);
        }
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATION							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private void validate() {
        if (!ObjectUtil.validCollection(codeRefs)) {
            throw new SdmxSemmanticException("Hierarchy must contain at least one hierarchical code");
        }
        if (hasFormalLevels) {
            validateHasLevel(codeRefs);
        }
    }

    private void validateHasLevel(List<HierarchicalCodeBean> codeRefs) {
        if (codeRefs != null) {
            for (HierarchicalCodeBean currentHCode : codeRefs) {
                validateHasLevel(currentHCode.getCodeRefs());
                if (currentHCode.getLevel(true) == null) {
                    throw new SdmxSemmanticException("Hierarchy indicates formal levels, but Hierarchical Code '" + currentHCode.getUrn() + "' is missing a level reference and there is no default level for it's depth in the hierarchy");
                }
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public List<HierarchicalCodeBean> getHierarchicalCodeBeans() {
        return new ArrayList<HierarchicalCodeBean>(codeRefs);
    }

    @Override
    public LevelBean getLevel() {
        return level;
    }

    @Override
    public LevelBean getLevelAtPosition(int levelPos) {
        LevelBean currentLevel = level;
        for (int i = 1; i <= levelPos; i++) {
            if (currentLevel == null) {
                return null;
            }
            if (i == levelPos) {
                return currentLevel;
            }
            currentLevel = currentLevel.getChildLevel();
        }
        return null;
    }

    @Override
    public boolean hasFormalLevels() {
        return hasFormalLevels;
    }

    @Override
    public HierarchicalCodelistBean getMaintainableParent() {
        return (HierarchicalCodelistBean) super.getMaintainableParent();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////COMPOSITES				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected Set<SDMXBean> getCompositesInternal() {
        Set<SDMXBean> composites = super.getCompositesInternal();
        super.addToCompositeSet(codeRefs, composites);
        super.addToCompositeSet(level, composites);
        return composites;
    }
}
