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
package org.sdmxsource.sdmx.dataparser.engine.impl;

import org.sdmxsource.sdmx.api.constants.ATTRIBUTE_ATTACHMENT_LEVEL;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.ExceptionHandler;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.ComponentBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.AttributeBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DimensionBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.GroupBean;
import org.sdmxsource.sdmx.api.model.data.KeyValue;
import org.sdmxsource.sdmx.api.model.data.Keyable;
import org.sdmxsource.sdmx.api.model.data.Observation;
import org.sdmxsource.sdmx.dataparser.engine.DataValidationEngine;
import org.sdmxsource.sdmx.dataparser.model.DatasetInformation;
import org.sdmxsource.util.ObjectUtil;

import java.util.List;

/**
 * The type Simple data validation engine.
 */
public class SimpleDataValidationEngine implements DataValidationEngine {

    private DatasetInformation dsi;

    /**
     * Instantiates a new Simple data validation engine.
     *
     * @param dsi the dsi
     */
    public SimpleDataValidationEngine(DatasetInformation dsi) {
        this.dsi = dsi;
    }


    @Override
    public void validateDataSetAttributes(List<KeyValue> key) {
        for (KeyValue kv : key) {
            ComponentBean component = dsi.getDsd().getComponent(kv.getConcept());
            if (component == null) {
                throw new SdmxSemmanticException("DSD component not found : " + kv.getConcept());
            }
            if (component.getStructureType() != SDMX_STRUCTURE_TYPE.DATA_ATTRIBUTE) {
                throw new SdmxSemmanticException("DSD component is not an attribute: " + kv.getConcept());
            }
            ATTRIBUTE_ATTACHMENT_LEVEL attachLevel = ((AttributeBean) component).getAttachmentLevel();

            if (attachLevel != ATTRIBUTE_ATTACHMENT_LEVEL.DATA_SET) {
                throw new SdmxSemmanticException("Attribute '" + kv.getConcept() + "' is not attached to the dataset, it is attached to '" + attachLevel + "'");
            }
        }
    }


    @Override
    public void validateKey(Keyable keyable) {
        try {
            if (!keyable.isSeries()) {
                GroupBean group = dsi.getDsd().getGroup(keyable.getGroupName());
                if (group == null) {
                    throw new SdmxSemmanticException("Data Structure Definition does not contain group: " + keyable.getGroupName());
                }
                for (KeyValue kv : keyable.getKey()) {
                    if (!group.getDimensionRefs().contains(kv.getConcept())) {
                        throw new SdmxSemmanticException("DSD group key component not found : " + kv.getConcept());
                    }
                }
            } else if (keyable.getKey().size() != dsi.getDimSize()) {
                throw new SdmxSemmanticException("Dataset key unexpected size.  Got '" + keyable.getKey().size() + "', expected '" + dsi.getDimSize() + "' ");
            }
            for (KeyValue kv : keyable.getKey()) {
                DimensionBean dimension = dsi.getDimensionMap().get(kv.getConcept());
                if (dimension == null) {
                    throw new SdmxSemmanticException("DSD series key component not found : " + kv.getConcept());
                }
            }

            for (KeyValue kv : keyable.getAttributes()) {
                AttributeBean attribute = dsi.getSeriesAttributes().get(kv.getConcept());
                if (attribute == null) {
                    throw new SdmxSemmanticException("DSD series attribute not found : " + kv.getConcept());
                }
            }
        } catch (SdmxSemmanticException e) {
            if (keyable.isSeries()) {
                throw new SdmxSemmanticException(e, "Error while processing series key: " + keyable.toString());
            }
            throw new SdmxSemmanticException(e, "Error while processing group key: " + keyable.toString());
        }
    }

    @Override
    public void validateObs(Observation obs) {
        if (dsi.isTimeSeries() && !ObjectUtil.validString(obs.getObsTime())) {
            throw new SdmxSemmanticException("Observation missing time dimension for time series data");
        }

        for (KeyValue kv : obs.getAttributes()) {
            AttributeBean attribute = dsi.getObsAttributes().get(kv.getConcept());
            if (attribute == null) {
                throw new SdmxSemmanticException("DSD observation attribute not found : " + kv.getConcept());
            }
        }
    }


    @Override
    public void finishedObs(ExceptionHandler exceptionHandler) {
        // Do nothing
    }
}