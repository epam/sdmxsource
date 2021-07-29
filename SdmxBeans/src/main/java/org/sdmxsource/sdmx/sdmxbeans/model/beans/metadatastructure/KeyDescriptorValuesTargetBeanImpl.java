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
package org.sdmxsource.sdmx.sdmxbeans.model.beans.metadatastructure;

import org.sdmx.resources.sdmxml.schemas.v21.structure.KeyDescriptorValuesTargetType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.RepresentationType;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.constants.TEXT_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.KeyDescriptorValuesTargetBean;
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.MetadataTargetBean;
import org.sdmxsource.sdmx.api.model.mutable.metadatastructure.KeyDescriptorValuesTargetMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.IdentifiableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.util.TextTypeUtil;


/**
 * The type Key descriptor values target bean.
 */
public class KeyDescriptorValuesTargetBeanImpl extends IdentifiableBeanImpl implements KeyDescriptorValuesTargetBean {
    private static final long serialVersionUID = -890998300721688045L;
    private TEXT_TYPE textType = TEXT_TYPE.KEY_VALUES;

    /**
     * Instantiates a new Key descriptor values target bean.
     *
     * @param parent the parent
     * @param bean   the bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEANS			 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public KeyDescriptorValuesTargetBeanImpl(MetadataTargetBean parent, KeyDescriptorValuesTargetMutableBean bean) {
        super(bean, parent);
        try {
            this.textType = bean.getTextType();
        } catch (SdmxSemmanticException ex) {
            throw new SdmxSemmanticException(ex, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        } catch (Throwable th) {
            throw new SdmxException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        }
        validate();
    }

    /**
     * Instantiates a new Key descriptor values target bean.
     *
     * @param target the target
     * @param parent the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    protected KeyDescriptorValuesTargetBeanImpl(KeyDescriptorValuesTargetType target, MetadataTargetBean parent) {
        super(target, SDMX_STRUCTURE_TYPE.DIMENSION_DESCRIPTOR_VALUES_TARGET, parent);
        if (target.getLocalRepresentation() != null) {
            RepresentationType repType = target.getLocalRepresentation();
            if (repType.getTextFormat() != null) {
                if (repType.getTextFormat().getTextType() != null) {
                    this.textType = TextTypeUtil.getTextType(repType.getTextFormat().getTextType());
                }
            }
        }
        validate();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////DEEP VALIDATION						 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean deepEquals(SDMXBean bean, boolean includeFinalProperties) {
        if (bean == null) {
            return false;
        }
        if (bean.getStructureType() == this.getStructureType()) {
            return super.deepEqualsInternal((KeyDescriptorValuesTargetBean) bean, includeFinalProperties);
        }
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATION							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public TEXT_TYPE getTextType() {
        return textType;
    }

    @Override
    public String getId() {
        return FIXED_ID;
    }

    private void validate() {
        setId(FIXED_ID);
    }
}
