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

import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.constants.TERTIARY_BOOL;
import org.sdmxsource.sdmx.api.exception.SdmxNotImplementedException;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.mutable.base.MutableBean;
import org.sdmxsource.sdmx.util.beans.SDMXBeanUtil;

import java.util.*;


/**
 * The type Sdmx bean.
 */
public abstract class SDMXBeanImpl implements SDMXBean {
    private static final long serialVersionUID = 1L;
    /**
     * The Structure type.
     */
    protected SDMX_STRUCTURE_TYPE structureType;
    /**
     * The Parent.
     */
    protected SDMXBean parent;

    /**
     * The Composites.
     */
    transient Set<SDMXBean> composites;
    /**
     * The Cross references.
     */
    transient Set<CrossReferenceBean> crossReferences;

    /**
     * Instantiates a new Sdmx bean.
     *
     * @param structureType the structure type
     * @param parent        the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM ITSELF, CREATES STUB BEAN //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    protected SDMXBeanImpl(SDMX_STRUCTURE_TYPE structureType, SDMXBean parent) {
        this.structureType = structureType;
        this.parent = parent;
    }

    /**
     * Instantiates a new Sdmx bean.
     *
     * @param bean the bean
     */
    protected SDMXBeanImpl(SDMXBean bean) {
        if (bean == null) {
            throw new SdmxSemmanticException(ExceptionCode.JAVA_REQUIRED_OBJECT_NULL, "bean in constructor");
        }
        this.structureType = bean.getStructureType();
        this.parent = bean.getParent();
    }

    /**
     * Instantiates a new Sdmx bean.
     *
     * @param mutableBean the mutable bean
     * @param parent      the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEAN				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public SDMXBeanImpl(MutableBean mutableBean, SDMXBean parent) {
        if (mutableBean == null) {
            throw new SdmxSemmanticException(ExceptionCode.JAVA_REQUIRED_OBJECT_NULL, "bean in constructor");
        }
        this.structureType = mutableBean.getStructureType();
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

    @Override
    public SDMX_STRUCTURE_TYPE getStructureType() {
        return structureType;
    }


    @Override
    public SDMXBean getParent() {
        return parent;
    }

    /**
     * Deep equals internal boolean.
     *
     * @param bean                   the bean
     * @param includeFinalProperties the include final properties
     * @return the boolean
     */
    protected boolean deepEqualsInternal(SDMXBean bean, boolean includeFinalProperties) {
        if (bean.getStructureType() == this.getStructureType()) {
            return true;
        }
        return false;
    }


    @Override
    public boolean deepEquals(SDMXBean bean, boolean includeFinalProperties) {
        throw new SdmxNotImplementedException("Deep Equals on " + this.toString());
    }

    /**
     * Equivalent boolean.
     *
     * @param list1                  the list 1
     * @param list2                  the list 2
     * @param includeFinalProperties the include final properties
     * @return the boolean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATION							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    protected boolean equivalent(List<? extends SDMXBean> list1, List<? extends SDMXBean> list2, boolean includeFinalProperties) {
        if (list1.size() != list2.size()) {
            return false;
        }
        if (list1.size() == 0) {
            return true;
        }
        for (int i = 0; i < list2.size(); i++) {
            SDMXBean thisCurrentBean = list2.get(i);
            SDMXBean thatCurrentBean = list1.get(i);

            if (!thisCurrentBean.deepEquals(thatCurrentBean, includeFinalProperties)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Equivalent boolean.
     *
     * @param bean1                  the bean 1
     * @param bean2                  the bean 2
     * @param includeFinalProperties the include final properties
     * @return the boolean
     */
    protected boolean equivalent(SDMXBean bean1, SDMXBean bean2, boolean includeFinalProperties) {
        if (bean1 == null) {
            return bean2 == null;
        }
        if (bean2 == null) {
            return bean1 == null;
        }
        return bean1.deepEquals(bean2, includeFinalProperties);
    }

    /**
     * Equivalent boolean.
     *
     * @param crossRef1 the cross ref 1
     * @param crossRef2 the cross ref 2
     * @return the boolean
     */
    protected boolean equivalent(CrossReferenceBean crossRef1, CrossReferenceBean crossRef2) {
        if (crossRef1 == null) {
            return crossRef2 == null;
        }
        if (crossRef2 == null) {
            return crossRef1 == null;
        }
        return crossRef2.getTargetUrn().equals(crossRef1.getTargetUrn());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getParent(Class<T> type, boolean includeThisType) {
        if (parent != null) {
            if (type.isAssignableFrom(parent.getClass())) {
                T returnObj = (T) parent;
                return returnObj;
            } else {
                return parent.getParent(type, false);
            }
        }
        if (type.isAssignableFrom(this.getClass())) {
            return (T) this;
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Set<T> getComposites(Class<T> type) {
        Set<T> returnSet = new HashSet<T>();
        for (SDMXBean currentComposite : getComposites()) {
            if (type.isAssignableFrom(currentComposite.getClass())) {
                returnSet.add((T) currentComposite);
            }
        }
        return returnSet;
    }


    @Override
    public Set<SDMXBean> getComposites() {
        if (composites == null) {
            this.composites = getCompositesInternal();
        }
        composites = Collections.unmodifiableSet(composites);
        return composites;
    }

    /**
     * Gets composites internal.
     *
     * @return the composites internal
     */
    protected abstract Set<SDMXBean> getCompositesInternal();

    /**
     * Gets cross reference internal.
     *
     * @return the cross reference internal
     */
    protected Set<CrossReferenceBean> getCrossReferenceInternal() {
        return new HashSet<CrossReferenceBean>();
    }

    /**
     * Add to composite set.
     *
     * @param comp          the comp
     * @param compositesSet the composites set
     */
    protected void addToCompositeSet(Collection<? extends SDMXBean> comp, Set<SDMXBean> compositesSet) {
        for (SDMXBean composite : comp) {
            addToCompositeSet(composite, compositesSet);
        }
    }

    /**
     * Add to composite set.
     *
     * @param composite  the composite
     * @param composites the composites
     */
    protected void addToCompositeSet(SDMXBean composite, Set<SDMXBean> composites) {
        if (composite != null) {
            composites.add(composite);
        }

        //Add Bean's Composites
        if (composite != null) {
            Set<SDMXBean> getComposites = composite.getComposites();
            if (getComposites != null) {
                composites.addAll(getComposites);
            }
        }
    }

    @SuppressWarnings("all")
    public Set<CrossReferenceBean> getCrossReferences() {
        if (crossReferences != null) {
            return crossReferences;
        }
        try {
            Set<CrossReferenceBean> returnSet = getCrossReferenceInternal();
            if (returnSet == null) {
                returnSet = new HashSet<CrossReferenceBean>();
            }
            for (SDMXBean bean : getComposites()) {
                returnSet.addAll(bean.getCrossReferences());
            }
            crossReferences = returnSet;
            crossReferences = Collections.unmodifiableSet(crossReferences);
            return crossReferences;
        } catch (SecurityException e) {
            throw new RuntimeException(e);
        }
    }
}
