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
import org.sdmxsource.sdmx.api.constants.TO_VALUE;
import org.sdmxsource.sdmx.api.model.beans.mapping.RepresentationMapRefBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.mutable.base.TextFormatMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.mapping.RepresentationMapRefMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.MutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.TextFormatMutableBeanImpl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * The type Representation map ref mutable bean.
 */
public class RepresentationMapRefMutableBeanImpl extends MutableBeanImpl implements RepresentationMapRefMutableBean {
    private static final long serialVersionUID = -860429690903269870L;
    private StructureReferenceBean codelistMap;
    private TextFormatMutableBean toTextFormat;
    private TO_VALUE toValueType;
    private Map<String, Set<String>> valueMappings = new HashMap<String, Set<String>>();

    /**
     * Instantiates a new Representation map ref mutable bean.
     */
    public RepresentationMapRefMutableBeanImpl() {
        super(SDMX_STRUCTURE_TYPE.REPRESENTATION_MAP);
    }

    /**
     * Instantiates a new Representation map ref mutable bean.
     *
     * @param refBean the ref bean
     */
    public RepresentationMapRefMutableBeanImpl(RepresentationMapRefBean refBean) {
        super(refBean);
        if (refBean.getCodelistMap() != null) {
            this.codelistMap = refBean.getCodelistMap().createMutableInstance();
        }
        if (refBean.getToTextFormat() != null) {
            this.toTextFormat = new TextFormatMutableBeanImpl(refBean.getToTextFormat());
        }
        this.toValueType = refBean.getToValueType();
        this.valueMappings = refBean.getValueMappings();
    }


    @Override
    public void addMapping(String componentId, String componentValue) {
        Set<String> mappings = valueMappings.get(componentId);
        if (mappings == null) {
            mappings = new HashSet<String>();
            valueMappings.put(componentId, mappings);
        }
        mappings.add(componentValue);
    }

    @Override
    public StructureReferenceBean getCodelistMap() {
        return codelistMap;
    }

    @Override
    public void setCodelistMap(StructureReferenceBean codelistMap) {
        this.codelistMap = codelistMap;
    }

    @Override
    public TextFormatMutableBean getToTextFormat() {
        return toTextFormat;
    }

    @Override
    public void setToTextFormat(TextFormatMutableBean toTextFormat) {
        this.toTextFormat = toTextFormat;
    }

    @Override
    public TO_VALUE getToValueType() {
        return toValueType;
    }

    @Override
    public void setToValueType(TO_VALUE toValueType) {
        this.toValueType = toValueType;
    }

    @Override
    public Map<String, Set<String>> getValueMappings() {
        return valueMappings;
    }

    @Override
    public void setValueMappings(Map<String, Set<String>> valueMappings) {
        this.valueMappings = valueMappings;
    }
}
