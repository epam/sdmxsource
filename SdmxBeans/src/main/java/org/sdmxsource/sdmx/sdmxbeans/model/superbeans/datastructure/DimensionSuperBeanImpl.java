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
package org.sdmxsource.sdmx.sdmxbeans.model.superbeans.datastructure;

import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DimensionBean;
import org.sdmxsource.sdmx.api.model.superbeans.codelist.CodelistSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.codelist.HierarchicalCodelistSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.conceptscheme.ConceptSchemeSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.conceptscheme.ConceptSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.DimensionSuperBean;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.base.ComponentSuperBeanImpl;

import java.util.HashSet;
import java.util.Set;


/**
 * The type Dimension super bean.
 *
 * @author Matt Nelson
 */
public class DimensionSuperBeanImpl extends ComponentSuperBeanImpl implements DimensionSuperBean {
    private static final long serialVersionUID = 1L;

    private Set<HierarchicalCodelistSuperBean> hcls;
    private boolean isFrequenyDimension;
    private boolean isMeasureDimension;
    private boolean isTimeDimension;
    private DimensionBean dimensionBean;
    private ConceptSchemeSuperBean conceptSchemeSuperBean;

    /**
     * Instantiates a new Dimension super bean.
     *
     * @param dimension              the dimension
     * @param conceptSchemeSuperBean the concept scheme super bean
     * @param concept                the concept
     */
    public DimensionSuperBeanImpl(DimensionBean dimension, ConceptSchemeSuperBean conceptSchemeSuperBean, ConceptSuperBean concept) {
        this(dimension, null, concept, null);
        this.dimensionBean = dimension;
        this.conceptSchemeSuperBean = conceptSchemeSuperBean;
    }

    /**
     * Instantiates a new Dimension super bean.
     *
     * @param dimension the dimension
     * @param codelist  the codelist
     * @param concept   the concept
     */
    public DimensionSuperBeanImpl(DimensionBean dimension, CodelistSuperBean codelist, ConceptSuperBean concept) {
        this(dimension, codelist, concept, null);
        this.dimensionBean = dimension;
    }

    /**
     * Instantiates a new Dimension super bean.
     *
     * @param dimension the dimension
     * @param codelist  the codelist
     * @param concept   the concept
     * @param hcls      the hcls
     */
    public DimensionSuperBeanImpl(DimensionBean dimension,
                                  CodelistSuperBean codelist,
                                  ConceptSuperBean concept,
                                  Set<HierarchicalCodelistSuperBean> hcls) {
        super(dimension, codelist, concept);
        this.dimensionBean = dimension;
        this.isFrequenyDimension = dimension.isFrequencyDimension();
        this.isMeasureDimension = dimension.isMeasureDimension();
        this.isTimeDimension = dimension.isTimeDimension();

        this.hcls = hcls;
        if (hcls == null) {
            this.hcls = new HashSet<HierarchicalCodelistSuperBean>();
        }
        if (concept == null) {
            throw new SdmxSemmanticException(ExceptionCode.JAVA_REQUIRED_OBJECT_NULL, ConceptSuperBean.class.getCanonicalName());
        }
    }

    /**
     * Gets hierarchical codelists.
     *
     * @return the hierarchical codelists
     */
    public Set<HierarchicalCodelistSuperBean> getHierarchicalCodelists() {
        return new HashSet<HierarchicalCodelistSuperBean>(hcls);
    }

    /**
     * Is frequency boolean.
     *
     * @return the boolean
     */
    /* (non-Javadoc)
     * @see org.sdmxsource.dataweb.external.model.Dimension#isFrequency()
     */
    public boolean isFrequency() {
        return this.isFrequenyDimension;
    }

    @Override
    public boolean isFrequencyDimension() {
        return this.isFrequenyDimension;
    }

    @Override
    public ConceptSchemeSuperBean getMeasureRepresentation() {
        return conceptSchemeSuperBean;
    }

    @Override
    public boolean isMeasureDimension() {
        return this.isMeasureDimension;
    }

    @Override
    public boolean isTimeDimension() {
        return isTimeDimension;
    }

    @Override
    public DimensionBean getBuiltFrom() {
        return dimensionBean;
    }
}
