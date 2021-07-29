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

import org.sdmx.resources.sdmxml.schemas.v21.common.TextType;
import org.sdmx.resources.sdmxml.schemas.v21.common.XHTMLType;
import org.sdmx.resources.sdmxml.schemas.v21.metadata.generic.ReportedAttributeType;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.base.TextTypeWrapper;
import org.sdmxsource.sdmx.api.model.metadata.ReportedAttributeBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.SDMXBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.TextTypeWrapperImpl;
import org.sdmxsource.util.ObjectUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * The type Reported attribute bean.
 */
public class ReportedAttributeBeanImpl extends SDMXBeanImpl implements ReportedAttributeBean {
    private static final long serialVersionUID = -7555198249383281384L;

    private String id;
    private String simpleValue;
    private List<TextTypeWrapper> metadataText = new ArrayList<TextTypeWrapper>();
    private List<ReportedAttributeBean> reportedAttribute = new ArrayList<ReportedAttributeBean>();


    /**
     * Instantiates a new Reported attribute bean.
     *
     * @param parent the parent
     * @param type   the type
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ReportedAttributeBeanImpl(SDMXBean parent, ReportedAttributeType type) {
        super(SDMX_STRUCTURE_TYPE.METADATA_REPORT_ATTRIBUTE, parent);
        this.id = type.getId();
        if (ObjectUtil.validCollection(type.getStructuredTextList())) {
            for (XHTMLType text : type.getStructuredTextList()) {
                metadataText.add(new TextTypeWrapperImpl(text, this));
            }
        }
        if (ObjectUtil.validCollection(type.getTextList())) {
            for (TextType text : type.getTextList()) {
                metadataText.add(new TextTypeWrapperImpl(text, this));
            }
        }
        if (type.getAttributeSet() != null) {
            for (ReportedAttributeType currentType : type.getAttributeSet().getReportedAttributeList()) {
                this.reportedAttribute.add(new ReportedAttributeBeanImpl(this, currentType));
            }
        }
        this.simpleValue = type.getValue();
        validate();
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATE							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private void validate() {
        if (!ObjectUtil.validString(id)) {
            throw new SdmxSemmanticException("Metadata Reported Attribute must have an Id");
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getSimpleValue() {
        return simpleValue;
    }

    @Override
    public boolean hasSimpleValue() {
        return simpleValue != null;
    }

    @Override
    public List<TextTypeWrapper> getMetadataText() {
        return new ArrayList<TextTypeWrapper>(metadataText);
    }

    @Override
    public List<ReportedAttributeBean> getReportedAttributes() {
        return new ArrayList<ReportedAttributeBean>(reportedAttribute);
    }

    @Override
    public boolean isPresentational() {
        return metadataText.size() == 0;
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
            ReportedAttributeBean that = (ReportedAttributeBean) bean;
            if (!ObjectUtil.equivalent(this.id, that.getId())) {
                return false;
            }
            if (!ObjectUtil.equivalent(this.simpleValue, that.getSimpleValue())) {
                return false;
            }
            if (!super.equivalent(metadataText, that.getMetadataText(), includeFinalProperties)) {
                return false;
            }
            if (!super.equivalent(reportedAttribute, that.getReportedAttributes(), includeFinalProperties)) {
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
        super.addToCompositeSet(metadataText, composites);
        super.addToCompositeSet(reportedAttribute, composites);
        return composites;
    }
}
