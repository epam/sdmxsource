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
package org.sdmxsource.sdmx.sdmxbeans.model.beans.base;

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.constants.TERTIARY_BOOL;
import org.sdmxsource.sdmx.api.model.beans.base.IdentifiableBean;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.base.SdmxStructureBean;
import org.sdmxsource.sdmx.api.model.mutable.base.MutableBean;
import org.sdmxsource.sdmx.util.beans.SDMXBeanUtil;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


/**
 * The type Sdmx structure bean.
 */
public abstract class SdmxStructureBeanImpl extends SDMXBeanImpl implements SdmxStructureBean {
    private static final long serialVersionUID = 1L;
    /**
     * The Parent.
     */
    protected SdmxStructureBean parent;

    /**
     * The Identifiable composites.
     */
    transient Set<IdentifiableBean> identifiableComposites;

    /**
     * Instantiates a new Sdmx structure bean.
     *
     * @param structureType the structure type
     * @param parent        the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM ITSELF, CREATES STUB BEAN //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    protected SdmxStructureBeanImpl(SDMX_STRUCTURE_TYPE structureType, SdmxStructureBean parent) {
        super(structureType, parent);
        this.structureType = structureType;
        this.parent = parent;
    }

    /**
     * Instantiates a new Sdmx structure bean.
     *
     * @param bean the bean
     */
    protected SdmxStructureBeanImpl(SdmxStructureBean bean) {
        super(bean);
        this.structureType = bean.getStructureType();
        this.parent = bean.getParent();
    }

    /**
     * Instantiates a new Sdmx structure bean.
     *
     * @param mutableBean the mutable bean
     * @param parent      the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEAN				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public SdmxStructureBeanImpl(MutableBean mutableBean, SdmxStructureBean parent) {
        super(mutableBean, parent);
        this.parent = parent;
    }


    /**
     * Create tertiary tertiary bool.
     *
     * @param isSet the is set
     * @param value the value
     * @return the tertiary bool
     */
    protected static TERTIARY_BOOL createTertiary(boolean isSet, boolean value) {
        return SDMXBeanUtil.createTertiary(isSet, value);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////COMPOSITES								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected Set<SDMXBean> getCompositesInternal() {
        return new HashSet<SDMXBean>();
    }

    @Override
    public SDMX_STRUCTURE_TYPE getStructureType() {
        return structureType;
    }

    @Override
    public SdmxStructureBean getParent() {
        return parent;
    }

    @Override
    public MaintainableBean getMaintainableParent() {
        if (this instanceof MaintainableBean) {
            return (MaintainableBean) this;
        }
        if (parent instanceof MaintainableBean) {
            return (MaintainableBean) parent;
        }
        return parent.getMaintainableParent();
    }


    @Override
    public final Set<IdentifiableBean> getIdentifiableComposites() {
        if (identifiableComposites == null) {
            identifiableComposites = new HashSet<IdentifiableBean>();
            for (SDMXBean currentComposite : getComposites()) {
                if (currentComposite.getStructureType().isIdentifiable() &&
                        !currentComposite.getStructureType().isMaintainable()) {
                    identifiableComposites.add((IdentifiableBean) currentComposite);
                }
            }
            identifiableComposites = Collections.unmodifiableSet(identifiableComposites);
        }
        return identifiableComposites;
    }

    @Override
    public IdentifiableBean getIdentifiableParent() {
        SDMXBean currentParent = getParent();
        while (currentParent != null) {
            if (currentParent.getStructureType().isIdentifiable()) {
                return (IdentifiableBean) currentParent;
            }
            currentParent = currentParent.getParent();
        }
        return null;
    }
}
