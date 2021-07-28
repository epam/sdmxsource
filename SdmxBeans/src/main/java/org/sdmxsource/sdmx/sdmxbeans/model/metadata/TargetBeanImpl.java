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
package org.sdmxsource.sdmx.sdmxbeans.model.metadata;

import org.sdmx.resources.sdmxml.schemas.v21.metadata.generic.ReferenceValueType;
import org.sdmx.resources.sdmxml.schemas.v21.metadata.generic.TargetType;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.metadata.MetadataReportBean;
import org.sdmxsource.sdmx.api.model.metadata.ReferenceValueBean;
import org.sdmxsource.sdmx.api.model.metadata.TargetBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.SDMXBeanImpl;
import org.sdmxsource.util.ObjectUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * The type Target bean.
 */
public class TargetBeanImpl extends SDMXBeanImpl implements TargetBean {
    private static final long serialVersionUID = 5981262118675314991L;
    private String id;
    private List<ReferenceValueBean> referenceValues = new ArrayList<ReferenceValueBean>();

    /**
     * Instantiates a new Target bean.
     *
     * @param parent the parent
     * @param type   the type
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public TargetBeanImpl(MetadataReportBean parent, TargetType type) {
        super(SDMX_STRUCTURE_TYPE.METADATA_REPORT_TARGET, parent);
        this.id = type.getId();
        if (ObjectUtil.validCollection(type.getReferenceValueList())) {
            for (ReferenceValueType refValue : type.getReferenceValueList()) {
                referenceValues.add(new ReferenceValueBeanImpl(this, refValue));
            }
        }
        validate();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATE							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private void validate() {
        if (!ObjectUtil.validString(id)) {
            throw new SdmxSemmanticException("Metadata Target must have an Id");
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public List<ReferenceValueBean> getReferenceValues() {
        return new ArrayList<ReferenceValueBean>(referenceValues);
    }

    @Override
    public String getId() {
        return id;
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
            TargetBean that = (TargetBean) bean;
            if (!ObjectUtil.equivalent(this.id, that.getId())) {
                return false;
            }
            if (!super.equivalent(referenceValues, that.getReferenceValues(), includeFinalProperties)) {
                return false;
            }
            return super.deepEqualsInternal(that, includeFinalProperties);
        }
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////COMPOSITES		                     //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected Set<SDMXBean> getCompositesInternal() {
        Set<SDMXBean> composites = new HashSet<SDMXBean>();
        super.addToCompositeSet(referenceValues, composites);
        return composites;
    }
}
