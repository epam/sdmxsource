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

import org.sdmx.resources.sdmxml.schemas.v21.structure.LevelType;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.base.IdentifiableBean;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.base.TextFormatBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.LevelBean;
import org.sdmxsource.sdmx.api.model.mutable.codelist.LevelMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.NameableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.TextFormatBeanImpl;

import java.util.List;
import java.util.Set;


/**
 * The type Level bean.
 */
public class LevelBeanImpl extends NameableBeanImpl implements LevelBean {
    private static final long serialVersionUID = -4441829506005430291L;
    private LevelBean childLevel;
    private TextFormatBean textFormatBean;


    /**
     * Instantiates a new Level bean.
     *
     * @param parent       the parent
     * @param levelMutable the level mutable
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEAN				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public LevelBeanImpl(IdentifiableBean parent, LevelMutableBean levelMutable) {
        super(levelMutable, parent);
        if (levelMutable.getCodingFormat() != null) {
            this.textFormatBean = new TextFormatBeanImpl(levelMutable.getCodingFormat(), this);
        }
        if (levelMutable.getChildLevel() != null) {
            this.childLevel = new LevelBeanImpl(this, levelMutable.getChildLevel());
        }
    }


    /**
     * Instantiates a new Level bean.
     *
     * @param parent the parent
     * @param levels the levels
     * @param pos    the pos
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public LevelBeanImpl(IdentifiableBean parent, List<org.sdmx.resources.sdmxml.schemas.v20.structure.LevelType> levels, int pos) {
        super(getLevel(levels, pos), SDMX_STRUCTURE_TYPE.LEVEL, getLevel(levels, pos).getId(), null, getLevel(levels, pos).getNameList(), getLevel(levels, pos).getDescriptionList(), getLevel(levels, pos).getAnnotations(), parent);
        org.sdmx.resources.sdmxml.schemas.v20.structure.LevelType level = getLevel(levels, pos);
        if (level.getCodingType() != null) {
            this.textFormatBean = new TextFormatBeanImpl(level.getCodingType(), this);
        }
        if (levels.size() > pos + 1) {
            this.childLevel = new LevelBeanImpl(this, levels, ++pos);
        }
    }

    /**
     * Instantiates a new Level bean.
     *
     * @param parent the parent
     * @param level  the level
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public LevelBeanImpl(IdentifiableBean parent, LevelType level) {
        super(level, SDMX_STRUCTURE_TYPE.LEVEL, parent);
        if (level.getCodingFormat() != null) {
            this.textFormatBean = new TextFormatBeanImpl(level.getCodingFormat(), this);
        }
        if (level.getLevel() != null) {
            this.childLevel = new LevelBeanImpl(this, level.getLevel());
        }
    }

    private static org.sdmx.resources.sdmxml.schemas.v20.structure.LevelType getLevel(List<org.sdmx.resources.sdmxml.schemas.v20.structure.LevelType> levels, int pos) {
        return levels.get(pos);
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
            LevelBean that = (LevelBean) bean;
            if (!super.equivalent(childLevel, that.getChildLevel(), includeFinalProperties)) {
                return false;
            }
            if (!super.equivalent(textFormatBean, that.getCodingFormat(), includeFinalProperties)) {
                return false;
            }
            return super.deepEqualsInternal(that, includeFinalProperties);
        }
        return false;
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public TextFormatBean getCodingFormat() {
        return textFormatBean;
    }

    @Override
    public LevelBean getChildLevel() {
        return childLevel;
    }

    @Override
    public boolean hasChild() {
        return childLevel != null;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////COMPOSITES				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected Set<SDMXBean> getCompositesInternal() {
        Set<SDMXBean> composites = super.getCompositesInternal();
        super.addToCompositeSet(childLevel, composites);
        super.addToCompositeSet(textFormatBean, composites);
        return composites;
    }
}
