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
package org.sdmxsource.sdmx.sdmxbeans.model.mutable.mapping;

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.mapping.ComponentMapBean;
import org.sdmxsource.sdmx.api.model.mutable.mapping.ComponentMapMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.mapping.RepresentationMapRefMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.AnnotableMutableBeanImpl;


/**
 * The type Component map mutable bean.
 */
public class ComponentMapMutableBeanImpl extends AnnotableMutableBeanImpl implements ComponentMapMutableBean {
    private static final long serialVersionUID = 3882017582441362269L;
    private String mapConceptRef;
    private String mapTargetConceptRef;
    private RepresentationMapRefMutableBean repMapRef;


    /**
     * Instantiates a new Component map mutable bean.
     */
    public ComponentMapMutableBeanImpl() {
        super(SDMX_STRUCTURE_TYPE.COMPONENT_MAP);
    }

    /**
     * Instantiates a new Component map mutable bean.
     *
     * @param compMap the comp map
     */
    public ComponentMapMutableBeanImpl(ComponentMapBean compMap) {
        super(compMap);
        if (compMap.getMapConceptRef() != null) {
            mapConceptRef = compMap.getMapConceptRef();
        }
        if (compMap.getMapTargetConceptRef() != null) {
            mapTargetConceptRef = compMap.getMapTargetConceptRef();
        }
        if (compMap.getRepMapRef() != null) {
            repMapRef = new RepresentationMapRefMutableBeanImpl(compMap.getRepMapRef());
        }
    }

    @Override
    public String getMapConceptRef() {
        return mapConceptRef;
    }

    @Override
    public void setMapConceptRef(String mapConceptRef) {
        this.mapConceptRef = mapConceptRef;
    }

    @Override
    public String getMapTargetConceptRef() {
        return mapTargetConceptRef;
    }

    @Override
    public void setMapTargetConceptRef(String mapTargetConceptRef) {
        this.mapTargetConceptRef = mapTargetConceptRef;
    }

    @Override
    public RepresentationMapRefMutableBean getRepMapRef() {
        return repMapRef;
    }

    @Override
    public void setRepMapRef(RepresentationMapRefMutableBean repMapRef) {
        this.repMapRef = repMapRef;
    }
}
