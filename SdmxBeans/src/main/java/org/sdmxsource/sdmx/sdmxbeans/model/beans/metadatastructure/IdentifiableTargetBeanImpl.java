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

import org.sdmx.resources.sdmxml.schemas.v21.common.ObjectTypeCodelistType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.IdentifiableObjectTargetType;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.constants.TEXT_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.IdentifiableTargetBean;
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.MetadataTargetBean;
import org.sdmxsource.sdmx.api.model.mutable.base.RepresentationMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.base.TextFormatMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.metadatastructure.IdentifiableTargetMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.ComponentBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.RepresentationBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.RepresentationMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.TextFormatMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.util.XmlBeansEnumUtil;

import javax.xml.bind.ValidationException;


/**
 * The type Identifiable target bean.
 */
public class IdentifiableTargetBeanImpl extends ComponentBeanImpl implements IdentifiableTargetBean {
    private static final long serialVersionUID = 1936328008391577535L;
    private SDMX_STRUCTURE_TYPE referencedStructureType;

    /**
     * Instantiates a new Identifiable target bean.
     *
     * @param bean   the bean
     * @param parent the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEANS			 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public IdentifiableTargetBeanImpl(IdentifiableTargetMutableBean bean, MetadataTargetBean parent) {
        super(bean, parent);
        this.referencedStructureType = bean.getReferencedStructureType();
        try {
            validate();
        } catch (ValidationException e) {
            throw new SdmxSemmanticException(e, ExceptionCode.FAIL_VALIDATION, this);
        }
    }

    /**
     * Instantiates a new Identifiable target bean.
     *
     * @param identifiableTargetType the identifiable target type
     * @param parent                 the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    protected IdentifiableTargetBeanImpl(IdentifiableObjectTargetType identifiableTargetType, MetadataTargetBean parent) {
        super(identifiableTargetType, SDMX_STRUCTURE_TYPE.IDENTIFIABLE_OBJECT_TARGET, parent);
        referencedStructureType = XmlBeansEnumUtil.getSdmxStructureType(identifiableTargetType.getObjectType());
        try {
            validate();
        } catch (ValidationException e) {
            throw new SdmxSemmanticException(e, ExceptionCode.FAIL_VALIDATION, this);
        }
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
            IdentifiableTargetBean that = (IdentifiableTargetBean) bean;
            if (referencedStructureType != that.getReferencedStructureType()) {
                return false;
            }
            return super.deepEqualsInternal(that, includeFinalProperties);
        }
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATION							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private void validate() throws ValidationException {
        // Sanity Check, make sure we can build both ways
        ObjectTypeCodelistType.Enum objIdEnum = XmlBeansEnumUtil.build(referencedStructureType);
        referencedStructureType = XmlBeansEnumUtil.getSdmxStructureType(objIdEnum);

        if (localRepresentation == null) {
            // Create a Local Representation if one doesn't exist. The schema insists on there being an Identifiable Reference
            TextFormatMutableBean textFormatMutable = new TextFormatMutableBeanImpl();
            textFormatMutable.setTextType(TEXT_TYPE.IDENTIFIABLE_REFERENCE);
            RepresentationMutableBean representationMutable = new RepresentationMutableBeanImpl();
            representationMutable.setTextFormat(textFormatMutable);
            localRepresentation = new RepresentationBeanImpl(representationMutable, this);
            return;
        }

        if (localRepresentation.getRepresentation() == null) {
            // If both Representation Ref and TextFormat are null then construct a new Local Representation with a TextFormat
            if (localRepresentation.getTextFormat() == null || localRepresentation.getTextFormat().getTextType() != TEXT_TYPE.IDENTIFIABLE_REFERENCE) {
                TextFormatMutableBean textFormatMutable = new TextFormatMutableBeanImpl();
                textFormatMutable.setTextType(TEXT_TYPE.IDENTIFIABLE_REFERENCE);
                RepresentationMutableBean representationMutable = new RepresentationMutableBeanImpl();
                representationMutable.setTextFormat(textFormatMutable);
                localRepresentation = new RepresentationBeanImpl(representationMutable, this);
            }
        } else {
            if (localRepresentation.getTextFormat() != null) {
                throw new SdmxSemmanticException("Can not have both TextFormat and Representation set on IdentifiableObjectTarget");
            }
        }

        if (referencedStructureType == null) {
            throw new SdmxSemmanticException("Identifiable target is missing Target Structure Type (objectType)");
        }
    }

    @Override
    protected void validateComponetReference() {
        //DO NOTHING
    }

    @Override
    public SDMX_STRUCTURE_TYPE getReferencedStructureType() {
        return referencedStructureType;
    }
}
