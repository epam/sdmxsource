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
import org.sdmxsource.sdmx.api.constants.TEXT_TYPE;
import org.sdmxsource.sdmx.api.exception.ExceptionHandler;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.ComponentBean;
import org.sdmxsource.sdmx.api.model.beans.base.TextFormatBean;
import org.sdmxsource.sdmx.api.model.data.KeyValue;
import org.sdmxsource.sdmx.api.model.data.Keyable;
import org.sdmxsource.sdmx.api.model.data.Observation;
import org.sdmxsource.sdmx.api.model.superbeans.base.ComponentSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.codelist.CodelistSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.AttributeSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.DataStructureSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.DimensionSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.GroupSuperBean;
import org.sdmxsource.sdmx.dataparser.engine.DataValidationEngine;
import org.sdmxsource.sdmx.dataparser.model.DatasetInformation;
import org.sdmxsource.sdmx.util.beans.UrnUtil;
import org.sdmxsource.sdmx.util.date.DateUtil;
import org.sdmxsource.util.ObjectUtil;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * The type Deep data validation engine.
 */
public class DeepDataValidationEngine implements DataValidationEngine {

    /**
     * The Dsi.
     */
    protected DatasetInformation dsi;
    private Set<String> validDatesPreviouslyEncountered = new HashSet<String>();

    /**
     * Instantiates a new Deep data validation engine.
     *
     * @param dsi the dsi
     */
    public DeepDataValidationEngine(DatasetInformation dsi) {
        this.dsi = dsi;
    }

    @Override
    public void validateDataSetAttributes(List<KeyValue> key) {
        List<String> errorMessages = null;
        for (KeyValue kv : key) {
            ComponentSuperBean attribute = dsi.getDsdSuperBean().getComponentById(kv.getConcept());
            if (attribute == null) {
                errorMessages = addErrorMessage(errorMessages, "Data Structure Definition does not define an Attribute with Id '" + kv.getConcept() + "'");
            } else {
                if (attribute.getBuiltFrom().getStructureType() != SDMX_STRUCTURE_TYPE.DATA_ATTRIBUTE) {
                    errorMessages = addErrorMessage(errorMessages, "Data Structure Definition component '" + kv.getConcept() + "' is not an attribute it is a " + attribute.getBuiltFrom().getStructureType().getType());
                }
                AttributeSuperBean attr = (AttributeSuperBean) attribute;
                if (attr.getAttachmentLevel() != ATTRIBUTE_ATTACHMENT_LEVEL.DATA_SET) {
                    errorMessages = addErrorMessage(errorMessages, "Data Structure Definition attribute '" + kv.getConcept() + "' is not attached to the dataset, it is attached to the: " + attr.getAttachmentLevel());
                }
                errorMessages = validateComponent(kv, attr, errorMessages);
            }
        }
        checkErrors(errorMessages);
    }


    @Override
    public void validateKey(Keyable keyable) {
        List<String> errorMessages = null;
        Map<String, AttributeSuperBean> attributes;
        if (!keyable.isSeries()) {
            GroupSuperBean group = dsi.getDsdSuperBean().getGroup(keyable.getGroupName());
            if (group == null) {
                throw new SdmxSemmanticException("Data Structure Definition does not define a Group with id '" + keyable.getGroupName() + "'");
            }
            attributes = dsi.getGroupAttributesSuperBeans().get(group.getId());
            for (KeyValue kv : keyable.getKey()) {
                if (group.getDimensionById(kv.getConcept()) == null) {
                    errorMessages = addErrorMessage(errorMessages, "Data Structure Definition does not define Component '" + kv.getConcept() + "' in Group '" + group.getId() + "'");
                }
            }
        } else {
            if (keyable.getKey().size() != dsi.getDimSize()) {
                Set<String> reportedKeys = new HashSet<String>();
                for (KeyValue kv : keyable.getKey()) {
                    reportedKeys.add(kv.getConcept());
                }

                StringBuilder sb = new StringBuilder();
                String concat = "[Missing Keys: ";
                Set<String> actualKeys = dsi.getDimensionMap().keySet();
                for (String dsdKeyId : actualKeys) {
                    if (!reportedKeys.contains(dsdKeyId)) {
                        sb.append(concat + dsdKeyId);
                        concat = ", ";
                    }
                }
                sb.append("]");
                concat = " [Reported keys not present in DSD : ";
                for (String reportedKey : reportedKeys) {
                    if (!actualKeys.contains(reportedKey)) {
                        sb.append(concat + reportedKey);
                        concat = ", ";
                    }
                }
                sb.append("]");
                if (keyable.isSeries()) {

                }
                throw new SdmxSemmanticException("Dataset key unexpected size.  Got '" + keyable.getKey().size() + "', expected '" + dsi.getDimSize() + "' " + sb.toString());
            }
            attributes = dsi.getSeriesAttributesSuperBeans();
        }
        for (KeyValue kv : keyable.getKey()) {
            DimensionSuperBean dimension = dsi.getDimensionMapSuperBeans().get(kv.getConcept());

            if (dimension == null) {
                if (dsi.getDimensionMap().get(kv.getConcept()).isMeasureDimension()) {
                    errorMessages = addErrorMessage(errorMessages, "Measure dimension is not supported in validation. Measure dimension is: " + kv.getConcept());
                } else {
                    errorMessages = addErrorMessage(errorMessages, "DSD series key component not found : " + kv.getConcept());
                }
            } else {
                errorMessages = validateComponent(kv, dimension, errorMessages);
            }
        }

        for (KeyValue kv : keyable.getAttributes()) {
            AttributeSuperBean attribute = attributes.get(kv.getConcept());
            if (attribute == null) {
                String attach = keyable.isSeries() ? "series" : "group " + keyable.getGroupName();
                errorMessages = addErrorMessage(errorMessages, attach + " attribute '" + kv.getConcept() + "' does not exist in Data Structure");
            } else {
                errorMessages = validateComponent(kv, attribute, errorMessages);
            }
        }
        checkErrors(errorMessages);
    }

    @Override
    public void validateObs(Observation obs) {
        List<String> errorMessages = null;
        if (dsi.isTimeSeries()) {
            if (!ObjectUtil.validString(obs.getObsTime())) {
                errorMessages = addErrorMessage(errorMessages, "Observation missing time dimension for time series data");
            } else if (!validDatesPreviouslyEncountered.contains(obs.getObsTime())) {
                try {
                    // The method 'getObsAsTimeDate' is expensive, so once encountered a good date store it away
                    obs.getObsAsTimeDate();
                    validDatesPreviouslyEncountered.add(obs.getObsTime());
                } catch (Throwable t) {
                    errorMessages = addErrorMessage(errorMessages, t.getMessage());
                }
            }
        }

        DataStructureSuperBean dsdSuperBean = dsi.getDsdSuperBean();

        if (obs.getObservationValue() != null) {
            errorMessages = validateTextFormat(dsdSuperBean.getPrimaryMeasure(), obs.getObservationValue(), errorMessages);
        }
        for (KeyValue kv : obs.getAttributes()) {
            AttributeSuperBean attribute = dsi.getObsAttributesSuperBeans().get(kv.getConcept());
            if (attribute == null) {
                errorMessages = addErrorMessage(errorMessages, "Observation Attribute '" + kv.getConcept() + "' is not defined by Data Structure: " + obs.getSeriesKey().getDataStructure().getUrn().split("=")[1]);
            }
            if (!attribute.isMandatory()) {
                // Non-mandatory attributes may have 'blank' values
                if (!ObjectUtil.validString(kv.getCode())) {
                    continue;
                }
            }
            errorMessages = validateComponent(kv, attribute, errorMessages);
        }
        checkErrors(errorMessages);
    }

    private void checkErrors(List<String> errorMessages) {
        if (errorMessages != null) {
            StringBuilder sb = new StringBuilder();
            for (String currentError : errorMessages) {
                sb.append(currentError + System.getProperty("line.separator"));
            }
            throw new SdmxSemmanticException(sb.toString());
        }
    }

    /**
     * Adds an error message to the list, if the list is null then creates a new list, adds the error, and returns it.
     *
     * @param errorList
     * @return
     */
    private List<String> addErrorMessage(List<String> errorList, String error) {
        if (errorList == null) {
            errorList = new ArrayList<String>();
        }
        errorList.add(error);
        return errorList;
    }

    @Override
    public void finishedObs(ExceptionHandler exceptionHandler) {
        // Do nothing
    }


    private List<String> validateComponent(KeyValue kv, ComponentSuperBean component, List<String> errorMessages) {
        errorMessages = validateCode(kv, component, errorMessages);
        if (component.getTextFormat() != null) {
            errorMessages = validateTextFormat(component, kv.getCode(), errorMessages);
        }
        return errorMessages;
    }


    private List<String> validateTextFormat(ComponentSuperBean componentSb, String value, List<String> errorMessages) {
        TextFormatBean textFormat = componentSb.getTextFormat();
        if (textFormat == null) {
            return errorMessages;
        }
        ComponentBean component = componentSb.getBuiltFrom();
        if (textFormat.getTextType() != null) {
            errorMessages = validateTextType(component, textFormat.getTextType(), value, errorMessages);
        }

        if (textFormat.getDecimals() != null) {
            try {
                Double valAsDouble = Double.parseDouble(value);
                int num = getNumberOfDecimalPlace(valAsDouble);
                if (num > textFormat.getDecimals().intValue()) {
                    errorMessages = addErrorMessage(component, value, "' exceeds the number of allowed decimal places of " + textFormat.getDecimals().toString(), errorMessages);
                }
            } catch (NumberFormatException e) {
                errorMessages = addErrorMessage(component, value, "' is expected to be numerical", errorMessages);
            }
        }
        if (textFormat.getMinLength() != null) {
            if (value.length() < textFormat.getMinLength().intValue()) {
                errorMessages = addErrorMessage(component, value, "' is shorter then the minimum allowed length of '" + textFormat.getMinLength().toString() + "'", errorMessages);
            }
        }
        if (textFormat.getMaxLength() != null) {
            if (value.length() > textFormat.getMaxLength().intValue()) {
                errorMessages = addErrorMessage(component, value, "' is greater then the maximum allowed length of '" + textFormat.getMaxLength().toString() + "'", errorMessages);
            }
        }
        if (textFormat.getStartValue() != null) {
            BigDecimal valueAsDecimal = parseBigDecimal(value);
            if (valueAsDecimal.compareTo(textFormat.getStartValue()) < 0) {
                errorMessages = addErrorMessage(component, value, "' is less then the specified start value of " + textFormat.getStartValue().toPlainString(), errorMessages);
            }
            if (textFormat.getInterval() != null) {
                if (valueAsDecimal.subtract(textFormat.getStartValue()).remainder(textFormat.getInterval()).compareTo(BigDecimal.ZERO) != 0) {
                    errorMessages = addErrorMessage(component, value, "' do not fit the allowed intervals starting from " + textFormat.getStartValue().toPlainString() + " and increasing by " + textFormat.getInterval().toPlainString(), errorMessages);
                }
            }
        }
        if (textFormat.getEndValue() != null) {
            if (parseBigDecimal(value).compareTo(textFormat.getEndValue()) > 0) {
                errorMessages = addErrorMessage(component, value, "' is greater then the specified end value of " + textFormat.getEndValue().toPlainString(), errorMessages);
            }
        }
        if (textFormat.getMinValue() != null) {
            if (parseBigDecimal(value).compareTo(textFormat.getMinValue()) < 0) {
                errorMessages = addErrorMessage(component, value, "' is less then the specified min value of " + textFormat.getMinValue().toPlainString(), errorMessages);
            }
        }
        if (textFormat.getMaxValue() != null) {
            if (parseBigDecimal(value).compareTo(textFormat.getMaxValue()) > 0) {
                errorMessages = addErrorMessage(component, value, "' is greater then the specified end value of " + textFormat.getMaxValue().toPlainString(), errorMessages);
            }
        }
        if (textFormat.getStartTime() != null) {
            try {
                Date dte = DateUtil.formatDate(value, true);
                if (dte.before(textFormat.getStartTime().getDate())) {
                    errorMessages = addErrorMessage(component, value, "' is before the allowed start time of " + textFormat.getStartTime().getDateInSdmxFormat(), errorMessages);
                }
            } catch (NumberFormatException e) {
                errorMessages = addErrorMessage(component, value, "' is not of expected type 'Date'", errorMessages);
            }
        }
        if (textFormat.getEndTime() != null) {
            try {
                Date dte = DateUtil.formatDate(value, true);
                if (dte.after(textFormat.getEndTime().getDate())) {
                    errorMessages = addErrorMessage(component, value, "' is after the allowed start time of " + textFormat.getStartTime().getDateInSdmxFormat(), errorMessages);
                }
            } catch (NumberFormatException e) {
                errorMessages = addErrorMessage(component, value, "' is not of expected type 'Date'", errorMessages);
            }
        }

        return errorMessages;
    }

    private List<String> addErrorMessage(ComponentBean component, String reportedValue, String errorMessage, List<String> errorMessages) {
        String componentType = component.getStructureType().getType();
        String msg = componentType + " '" + component.getId() + "' is reporting value '" + reportedValue + "' which " + errorMessage;
        errorMessages = addErrorMessage(errorMessages, msg);
        return errorMessages;
    }

    private List<String> validateTextType(ComponentBean component, TEXT_TYPE textType, String value, List<String> errorMessages) {
        switch (textType) {
            case DOUBLE:
                try {
                    Double.parseDouble(value);
                } catch (NumberFormatException e) {
                    errorMessages = addErrorMessage(component, value, "' is not of expected type 'Double'", errorMessages);
                }
                break;
            case BIG_INTEGER:
                try {
                    new BigInteger(value);
                } catch (NumberFormatException e) {
                    errorMessages = addErrorMessage(component, value, "' is not of expected type 'Big Integer'", errorMessages);
                }
                break;
            case INTEGER:
                try {
                    new Integer(value);
                } catch (NumberFormatException e) {
                    errorMessages = addErrorMessage(component, value, "' is not of expected type 'Integer'", errorMessages);
                }
                break;
            case LONG:
                try {
                    new Long(value);
                } catch (NumberFormatException e) {
                    errorMessages = addErrorMessage(component, value, "' is not of expected type 'Long'", errorMessages);
                }
                break;

            case SHORT:
                try {
                    new Short(value);
                } catch (NumberFormatException e) {
                    errorMessages = addErrorMessage(component, value, "' is not of expected type 'Short'", errorMessages);
                }
                break;
            case DECIMAL:
                try {
                    new BigDecimal(value);
                } catch (NumberFormatException e) {
                    errorMessages = addErrorMessage(component, value, "' is not of expected type 'Decimal'", errorMessages);
                }
                break;
            case FLOAT:
                try {
                    new Float(value);
                } catch (NumberFormatException e) {
                    errorMessages = addErrorMessage(component, value, "' is not of expected type 'Float'", errorMessages);
                }
                break;
            case BOOLEAN:
                if (!value.equalsIgnoreCase("true") && !value.equalsIgnoreCase("false")) {
                    errorMessages = addErrorMessage(component, value, "' is not of expected type 'Boolean'", errorMessages);
                }
                break;
            case DATE:
                try {
                    DateUtil.getTimeFormatOfDate(value);
                } catch (Throwable e) {
                    errorMessages = addErrorMessage(component, value, "' is not of expected type 'Date'", errorMessages);
                }
                break;
            case DATE_TIME:
                try {
                    DateUtil.getTimeFormatOfDate(value);
                } catch (Throwable e) {
                    errorMessages = addErrorMessage(component, value, "' is not of expected type 'Date'", errorMessages);
                }
                break;
            case YEAR:
                try {
                    if (value.length() != 4) {
                        errorMessages = addErrorMessage(component, value, "' is not of expected type 'Year', expected year length of 4", errorMessages);
                    }
                    new Integer(value);
                } catch (Throwable e) {
                    errorMessages = addErrorMessage(component, value, "' is not of expected type 'Year'", errorMessages);
                }
                break;
            case TIME:
                try {
                    DateUtil.getTimeFormatOfDate(value);
                } catch (Throwable e) {
                    errorMessages = addErrorMessage(component, value, "' is not of expected type 'Date'", errorMessages);
                }
                break;
            case IDENTIFIABLE_REFERENCE:
                try {
                    SDMX_STRUCTURE_TYPE targetStructure = UrnUtil.getIdentifiableType(value);
                    UrnUtil.validateURN(value, targetStructure);
                } catch (Throwable e) {
                    errorMessages = addErrorMessage(component, value, "' is not of expected type 'Identifiable Reference' a valid URN is expected", errorMessages);
                }
                break;
            case URI:
                try {
                    new URI(value);
                } catch (URISyntaxException e) {
                    errorMessages = addErrorMessage(component, value, "is not of expected type 'URI'", errorMessages);
                }
                break;
        }
        return errorMessages;
    }


    /**
     * Turn string into BigDecimal, to try and process it against other rules
     *
     * @param value
     * @return
     */
    private BigDecimal parseBigDecimal(String value) {
        try {
            return new BigDecimal(value);
        } catch (NumberFormatException e) {
            throw new SdmxSemmanticException("Value '" + value + "' expected to be numerical");
        }
    }

    private int getNumberOfDecimalPlace(double value) {
        //For whole numbers like 0
        if (Math.round(value) == value) return 0;
        final String s = Double.toString(value);
        final int index = s.indexOf('.');
        if (index < 0) {
            return 0;
        }
        return s.length() - 1 - index;
    }

    private List<String> validateCode(KeyValue kv, ComponentSuperBean component, List<String> errorMessages) {
        CodelistSuperBean codelist = component.getCodelist(true);
        if (codelist != null) {
            if (!ObjectUtil.validString(kv.getCode())) {
                errorMessages = addErrorMessage(component.getBuiltFrom(), "", " is not a valid representation in referenced Codelist '" + UrnUtil.getUrnPostfix(codelist.getUrn()) + "'", errorMessages);
            } else {
                if (codelist.getCodeByValue(kv.getCode()) == null) {
                    errorMessages = addErrorMessage(component.getBuiltFrom(), kv.getCode(), " is not a valid representation in referenced Codelist '" + UrnUtil.getUrnPostfix(codelist.getUrn()) + "'", errorMessages);
                }
            }
        }
        return errorMessages;
    }
}
