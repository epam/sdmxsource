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

import org.sdmxsource.sdmx.api.constants.ATTRIBUTE_ATTACHMENT_LEVEL;
import org.sdmxsource.sdmx.api.model.beans.datastructure.AttributeBean;
import org.sdmxsource.sdmx.api.model.superbeans.codelist.CodelistSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.conceptscheme.ConceptSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.AttributeSuperBean;

import java.util.List;


/**
 * The type Attribute super bean.
 */
public class AttributeSuperBeanImpl extends ComponentSuperBeanImpl implements AttributeSuperBean {
    private static final long serialVersionUID = 1L;
    private AttributeBean attributeBean;

    /**
     * Instantiates a new Attribute super bean.
     *
     * @param attributeBean    the attribute bean
     * @param codelistBean     the codelist bean
     * @param conceptSuperBean the concept super bean
     */
    public AttributeSuperBeanImpl(AttributeBean attributeBean, CodelistSuperBean codelistBean, ConceptSuperBean conceptSuperBean) {
        super(attributeBean, codelistBean, conceptSuperBean);
        this.attributeBean = attributeBean;
    }

    @Override
    public String getAssignmentStatus() {
        return attributeBean.getAssignmentStatus();
    }

    @Override
    public ATTRIBUTE_ATTACHMENT_LEVEL getAttachmentLevel() {
        return attributeBean.getAttachmentLevel();
    }

    @Override
    public List<String> getDimensionReferences() {
        return attributeBean.getDimensionReferences();
    }

    @Override
    public String getAttachmentGroup() {
        return attributeBean.getAttachmentGroup();
    }

    @Override
    public String getPrimaryMeasureReference() {
        return attributeBean.getPrimaryMeasureReference();
    }

    @Override
    public boolean isMandatory() {
        return attributeBean.isMandatory();
    }

    @Override
    public AttributeBean getBuiltFrom() {
        return attributeBean;
    }
}
