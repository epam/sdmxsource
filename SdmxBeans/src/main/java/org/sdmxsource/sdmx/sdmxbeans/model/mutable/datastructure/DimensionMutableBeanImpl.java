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
package org.sdmxsource.sdmx.sdmxbeans.model.mutable.datastructure;

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DimensionBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.mutable.datastructure.DimensionMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.ComponentMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.util.RoleReferenceUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


/**
 * The type Dimension mutable bean.
 */
public class DimensionMutableBeanImpl extends ComponentMutableBeanImpl implements DimensionMutableBean {
    private static final long serialVersionUID = 1L;

    private boolean measureDimension;
    private boolean timeDimension;
    private List<StructureReferenceBean> conceptRole = new ArrayList<StructureReferenceBean>();

    /**
     * Instantiates a new Dimension mutable bean.
     */
    public DimensionMutableBeanImpl() {
        super(SDMX_STRUCTURE_TYPE.DIMENSION);
    }

    /**
     * Instantiates a new Dimension mutable bean.
     *
     * @param bean the bean
     */
    public DimensionMutableBeanImpl(DimensionBean bean) {
        super(bean);
        this.measureDimension = bean.isMeasureDimension();
        this.timeDimension = bean.isTimeDimension();
        if (bean.getConceptRole() != null) {
            for (CrossReferenceBean currentConceptRole : bean.getConceptRole()) {
                conceptRole.add(currentConceptRole.createMutableInstance());
            }
        }
    }

    @Override
    public boolean isMeasureDimension() {
        return measureDimension;
    }

    @Override
    public void setMeasureDimension(boolean bool) {
        if (bool) {
            this.structureType = SDMX_STRUCTURE_TYPE.MEASURE_DIMENSION;
        }
        this.measureDimension = bool;
    }

    @Override
    public boolean isFrequencyDimension() {
        return isFrequencyRolePresent();
    }

    private boolean isFrequencyRolePresent() {
        return Stream.ofNullable(conceptRole)
                .flatMap(List::stream)
                .anyMatch(RoleReferenceUtil::isFrequency);
    }

    @Override
    public void setFrequencyDimension(boolean isFrequency) {
        if (isFrequency && !isFrequencyDimension()) {
            List<StructureReferenceBean> updatedRoles = new ArrayList<>();
            updatedRoles.add(RoleReferenceUtil.createFrequencyRoleReference());
            if (this.conceptRole != null) updatedRoles.addAll(this.conceptRole);
            this.conceptRole = updatedRoles;
        } else if (!isFrequency && isFrequencyDimension()) {
            this.conceptRole.removeIf(RoleReferenceUtil::isFrequency);
        }
    }

    @Override
    public boolean isTimeDimension() {
        return timeDimension;
    }

    @Override
    public void setTimeDimension(boolean timeDimension) {
        if (timeDimension) {
            this.structureType = SDMX_STRUCTURE_TYPE.TIME_DIMENSION;
        }
        this.timeDimension = timeDimension;
    }

    @Override
    public List<StructureReferenceBean> getConceptRole() {
        return conceptRole;
    }

    @Override
    public void setConceptRole(List<StructureReferenceBean> conceptRole) {
        this.conceptRole = conceptRole;
    }
}
