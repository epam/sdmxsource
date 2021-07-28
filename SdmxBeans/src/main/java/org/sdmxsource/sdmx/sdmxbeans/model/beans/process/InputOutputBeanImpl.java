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
package org.sdmxsource.sdmx.sdmxbeans.model.beans.process;

import org.sdmx.resources.sdmxml.schemas.v21.structure.InputOutputType;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.IdentifiableBean;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.process.InputOutputBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.mutable.process.InputOutputMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.AnnotableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.util.RefUtil;
import org.sdmxsource.sdmx.util.beans.reference.CrossReferenceBeanImpl;
import org.sdmxsource.util.ObjectUtil;

import java.util.HashSet;
import java.util.Set;


/**
 * The type Input output bean.
 */
public class InputOutputBeanImpl extends AnnotableBeanImpl implements InputOutputBean {
    private static final long serialVersionUID = -1497837570792187509L;
    private String localId;
    private CrossReferenceBean structureReference;


    /**
     * Instantiates a new Input output bean.
     *
     * @param parent      the parent
     * @param mutableBean the mutable bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEANS			 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public InputOutputBeanImpl(IdentifiableBean parent, InputOutputMutableBean mutableBean) {
        super(mutableBean, parent);
        this.localId = mutableBean.getLocalId();
        if (mutableBean.getStructureReference() != null) {
            this.structureReference = new CrossReferenceBeanImpl(this, mutableBean.getStructureReference());
        }
        validate();
    }

    /**
     * Instantiates a new Input output bean.
     *
     * @param parent  the parent
     * @param xmlType the xml type
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public InputOutputBeanImpl(IdentifiableBean parent, InputOutputType xmlType) {
        super(xmlType, SDMX_STRUCTURE_TYPE.COMPUTATION, parent);
        this.localId = xmlType.getLocalID();
        if (xmlType.getObjectReference() != null) {
            structureReference = RefUtil.createReference(this, xmlType.getObjectReference());
        }
        validate();
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
            InputOutputBean that = (InputOutputBean) bean;
            if (!super.equivalent(structureReference, that.getStructureReference())) {
                return false;
            }
            if (!ObjectUtil.equivalent(localId, that.getLocalId())) {
                return false;
            }
            return super.deepEqualsInternal(that, includeFinalProperties);
        }
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public String getLocalId() {
        return localId;
    }

    @Override
    public CrossReferenceBean getStructureReference() {
        return structureReference;
    }

    @Override
    protected Set<CrossReferenceBean> getCrossReferenceInternal() {
        Set<CrossReferenceBean> references = new HashSet<CrossReferenceBean>();
        if (structureReference != null) {
            references.add(structureReference);
        }
        return references;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALiDATION							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private void validate() {
        if (structureReference == null) {
            throw new SdmxSemmanticException("Input / Output bean, requires an Object Reference");
        }
    }
}
