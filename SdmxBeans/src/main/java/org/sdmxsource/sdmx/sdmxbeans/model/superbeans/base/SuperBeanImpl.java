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
package org.sdmxsource.sdmx.sdmxbeans.model.superbeans.base;

import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.superbeans.base.SuperBean;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The type Super bean.
 */
public abstract class SuperBeanImpl implements SuperBean {
    private static final long serialVersionUID = -4753953853772865900L;
    private SDMXBean builtFrom;

    /**
     * Instantiates a new Super bean.
     *
     * @param builtFrom the built from
     */
    public SuperBeanImpl(SDMXBean builtFrom) {
        if (builtFrom == null) {
            throw new SdmxSemmanticException("Super Bean missing the required SDMXBean that it is built from");
        }
        this.builtFrom = builtFrom;
    }

    @Override
    public SDMXBean getBuiltFrom() {
        return builtFrom;
    }

    /**
     * Add to set.
     *
     * @param returnSet  the return set
     * @param superBeans the super beans
     */
    protected void addToSet(Set<MaintainableBean> returnSet, List<? extends SuperBean> superBeans) {
        if (superBeans != null) {
            for (SuperBean currentSuperBean : superBeans) {
                returnSet.addAll(currentSuperBean.getCompositeBeans());
            }
        }
    }

    @Override
    public Set<MaintainableBean> getCompositeBeans() {
        return new HashSet<MaintainableBean>();
    }

}
