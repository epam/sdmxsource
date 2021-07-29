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

import org.sdmx.resources.sdmxml.schemas.v21.structure.DataSetTargetType;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.constants.TEXT_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.DataSetTargetBean;
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.MetadataTargetBean;
import org.sdmxsource.sdmx.api.model.mutable.metadatastructure.DataSetTargetMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.IdentifiableBeanImpl;

import javax.xml.bind.ValidationException;


/**
 * The type Data set target bean.
 */
public class DataSetTargetBeanImpl extends IdentifiableBeanImpl implements DataSetTargetBean {
    private static final long serialVersionUID = 6991119209849428893L;
    private TEXT_TYPE textType = TEXT_TYPE.DATA_SET_REFERENCE;

    /**
     * Instantiates a new Data set target bean.
     *
     * @param bean   the bean
     * @param parent the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEANS			 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public DataSetTargetBeanImpl(DataSetTargetMutableBean bean, MetadataTargetBean parent) {
        super(bean, parent);
        try {
            this.textType = bean.getTextType();
        } catch (SdmxSemmanticException ex) {
            throw new SdmxSemmanticException(ex, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        } catch (Throwable th) {
            throw new SdmxException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        }
        try {
            validate();
        } catch (SdmxSemmanticException ex) {
            throw new SdmxSemmanticException(ex, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        } catch (Throwable th) {
            throw new SdmxException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        }
    }

    /**
     * Instantiates a new Data set target bean.
     *
     * @param datasetTargetTargetType the dataset target target type
     * @param parent                  the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    protected DataSetTargetBeanImpl(DataSetTargetType datasetTargetTargetType, MetadataTargetBean parent) {
        super(datasetTargetTargetType, SDMX_STRUCTURE_TYPE.DATASET_TARGET, parent);
        try {
            validate();
        } catch (SdmxSemmanticException ex) {
            throw new SdmxSemmanticException(ex, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        } catch (Throwable th) {
            throw new SdmxException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        }
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
            return super.deepEqualsInternal((DataSetTargetBean) bean, includeFinalProperties);
        }
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATION							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private void validate() throws ValidationException {
        setId(FIXED_ID);
    }

    @Override
    public TEXT_TYPE getTextType() {
        return textType;
    }

    @Override
    public String getId() {
        return FIXED_ID;
    }
}
