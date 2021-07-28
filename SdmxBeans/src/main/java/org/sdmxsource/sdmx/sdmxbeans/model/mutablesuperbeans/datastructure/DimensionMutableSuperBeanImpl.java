/**
 * Copyright (c) 2013 Metadata Technology Ltd.
 * <p>
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the GNU Lesser General Public License v 3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * This file is part of the SDMX Component Library.
 * <p>
 * The SDMX Component Library is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 * <p>
 * The SDMX Component Library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser
 * General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License
 * along with The SDMX Component Library If not, see
 * http://www.gnu.org/licenses/lgpl.
 * <p>
 * Contributors:
 * Metadata Technology - initial API and implementation
 */
/**
 *
 */
package org.sdmxsource.sdmx.sdmxbeans.model.mutablesuperbeans.datastructure;

import org.sdmxsource.sdmx.api.model.mutablesuperbeans.datastructure.DimensionMutableSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.DimensionSuperBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutablesuperbeans.base.ComponentMutableSuperBeanImpl;


/**
 * The type Dimension mutable super bean.
 *
 * @author Matt Nelson
 */
public class DimensionMutableSuperBeanImpl extends ComponentMutableSuperBeanImpl implements DimensionMutableSuperBean {
    private static final long serialVersionUID = 1L;
    private boolean isFrequencyDimension;
    private boolean isCountDimension;
    private boolean isEntityDimension;
    private boolean isIdentityDimension;
    private boolean isMeasureDimension;
    private boolean isNonObservationTimeDimension;

    /**
     * Instantiates a new Dimension mutable super bean.
     *
     * @param dimension the dimension
     */
    public DimensionMutableSuperBeanImpl(DimensionSuperBean dimension) {
        super(dimension);
        this.isFrequencyDimension = dimension.isFrequencyDimension();
        this.isMeasureDimension = dimension.isMeasureDimension();
    }

    /**
     * Instantiates a new Dimension mutable super bean.
     */
    public DimensionMutableSuperBeanImpl() {

    }

    /**
     * Gets frequency dimension.
     *
     * @return the frequency dimension
     */
    public boolean getFrequencyDimension() {
        return isFrequencyDimension;
    }

    /**
     * Sets frequency dimension.
     *
     * @param isFrequencyDimension the is frequency dimension
     */
    public void setFrequencyDimension(boolean isFrequencyDimension) {
        this.isFrequencyDimension = isFrequencyDimension;
    }

    /**
     * Gets count dimension.
     *
     * @return the count dimension
     */
    public boolean getCountDimension() {
        return isCountDimension;
    }

    /**
     * Sets count dimension.
     *
     * @param isCountDimension the is count dimension
     */
    public void setCountDimension(boolean isCountDimension) {
        this.isCountDimension = isCountDimension;
    }

    /**
     * Gets entity dimension.
     *
     * @return the entity dimension
     */
    public boolean getEntityDimension() {
        return isEntityDimension;
    }

    /**
     * Sets entity dimension.
     *
     * @param isEntityDimension the is entity dimension
     */
    public void setEntityDimension(boolean isEntityDimension) {
        this.isEntityDimension = isEntityDimension;
    }

    /**
     * Gets identity dimension.
     *
     * @return the identity dimension
     */
    public boolean getIdentityDimension() {
        return isIdentityDimension;
    }

    /**
     * Sets identity dimension.
     *
     * @param isIdentityDimension the is identity dimension
     */
    public void setIdentityDimension(boolean isIdentityDimension) {
        this.isIdentityDimension = isIdentityDimension;
    }

    /**
     * Gets measure dimension.
     *
     * @return the measure dimension
     */
    public boolean getMeasureDimension() {
        return isMeasureDimension;
    }

    /**
     * Sets measure dimension.
     *
     * @param isMeasureDimension the is measure dimension
     */
    public void setMeasureDimension(boolean isMeasureDimension) {
        this.isMeasureDimension = isMeasureDimension;
    }

    /**
     * Gets non observation time dimension.
     *
     * @return the non observation time dimension
     */
    public boolean getNonObservationTimeDimension() {
        return isNonObservationTimeDimension;
    }

    /**
     * Sets non observation time dimension.
     *
     * @param isNonObservationTimeDimension the is non observation time dimension
     */
    public void setNonObservationTimeDimension(boolean isNonObservationTimeDimension) {
        this.isNonObservationTimeDimension = isNonObservationTimeDimension;
    }
}
