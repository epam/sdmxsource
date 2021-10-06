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
import org.sdmxsource.sdmx.api.model.beans.reference.IdentifiableRefBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.mutable.datastructure.DimensionMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.ComponentMutableBeanImpl;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Dimension mutable bean.
 */
public class DimensionMutableBeanImpl extends ComponentMutableBeanImpl implements DimensionMutableBean {
    private static final long serialVersionUID = 1L;

    private static final String FREQ = "FREQ";

    private boolean measureDimension;
    private boolean frequencyDimension;
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
        this.frequencyDimension = bean.isFrequencyDimension();
        this.timeDimension = bean.isTimeDimension();
        List<CrossReferenceBean> conceptRole = bean.getConceptRole();
        if (conceptRole != null) {
            for (CrossReferenceBean currentConceptRole : conceptRole) {
                this.conceptRole.add(currentConceptRole.createMutableInstance());
                IdentifiableRefBean roleReference = currentConceptRole.getChildReference();
                checkFrequencyRole(roleReference);
            }
        }
    }

    private void checkFrequencyRole(IdentifiableRefBean roleReference) {
        if (isFrequency(roleReference)) {
            this.frequencyDimension = true;
        }
    }

    private boolean isFrequency(IdentifiableRefBean roleReference) {
        if (roleReference != null) {
            return FREQ.equals(roleReference.getId());
        }
        return false;
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
        return frequencyDimension;
    }

    @Override
    public void setFrequencyDimension(boolean bool) {
        this.frequencyDimension = bool;
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
        if (conceptRole != null) {
            for (StructureReferenceBean currentConceptRole : conceptRole) {
                IdentifiableRefBean roleReference = currentConceptRole.getChildReference();
                checkFrequencyRole(roleReference);
            }
        }
    }
}
