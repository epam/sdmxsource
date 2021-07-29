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
package org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v21;

import org.sdmx.resources.sdmxml.schemas.v21.common.DataType;
import org.sdmx.resources.sdmxml.schemas.v21.common.DataType.Enum;
import org.sdmxsource.sdmx.api.builder.Builder;
import org.sdmxsource.sdmx.api.constants.TEXT_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxException;


/**
 * The type Data type builder.
 */
public class DataTypeBuilder implements Builder<DataType.Enum, TEXT_TYPE> {

    @Override
    public Enum build(TEXT_TYPE buildFrom) throws SdmxException {
        switch (buildFrom) {
            case ALPHA:
                return DataType.ALPHA;
            case ALPHA_NUMERIC:
                return DataType.ALPHA_NUMERIC;
            case ATTACHMENT_CONSTRAINT_REFERENCE:
                return DataType.ATTACHMENT_CONSTRAINT_REFERENCE;
            case STRING:
                return DataType.STRING;
            case BIG_INTEGER:
                return DataType.BIG_INTEGER;
            case INTEGER:
                return DataType.INTEGER;
            case LONG:
                return DataType.LONG;
            case SHORT:
                return DataType.SHORT;
            case DECIMAL:
                return DataType.DECIMAL;
            case FLOAT:
                return DataType.FLOAT;
            case DOUBLE:
                return DataType.DOUBLE;
            case BOOLEAN:
                return DataType.BOOLEAN;
            case DATE_TIME:
                return DataType.DATE_TIME;
            case TIME:
                return DataType.TIME;
            case YEAR:
                return DataType.GREGORIAN_YEAR;
            case MONTH:
                return DataType.MONTH;
            case DAY:
                return DataType.DAY;
            case MONTH_DAY:
                return DataType.MONTH_DAY;
            case NUMERIC:
                return DataType.NUMERIC;
            case YEAR_MONTH:
                return DataType.GREGORIAN_YEAR_MONTH;
            case DURATION:
                return DataType.DURATION;
            case URI:
                return DataType.URI;
            case TIMESPAN:
                return DataType.GREGORIAN_TIME_PERIOD;
            case COUNT:
                return DataType.COUNT;
            case INCLUSIVE_VALUE_RANGE:
                return DataType.INCLUSIVE_VALUE_RANGE;
            case EXCLUSIVE_VALUE_RANGE:
                return DataType.EXCLUSIVE_VALUE_RANGE;
            case INCREMENTAL:
                return DataType.INCREMENTAL;
            case OBSERVATIONAL_TIME_PERIOD:
                return DataType.OBSERVATIONAL_TIME_PERIOD;
            case IDENTIFIABLE_REFERENCE:
                return DataType.IDENTIFIABLE_REFERENCE;
            case DATE:
                return DataType.DATE_TIME;
            case KEY_VALUES:
                return DataType.KEY_VALUES;
            case BASIC_TIME_PERIOD:
                return DataType.BASIC_TIME_PERIOD;
            case DATA_SET_REFERENCE:
                return DataType.DATA_SET_REFERENCE;
            case GREGORIAN_DAY:
                return DataType.GREGORIAN_DAY;
            case GREGORIAN_TIME_PERIOD:
                return DataType.GREGORIAN_TIME_PERIOD;
            case GREGORIAN_YEAR:
                return DataType.GREGORIAN_YEAR;
            case GREGORIAN_YEAR_MONTH:
                return DataType.GREGORIAN_YEAR_MONTH;
            case REPORTING_DAY:
                return DataType.REPORTING_DAY;
            case REPORTING_MONTH:
                return DataType.REPORTING_MONTH;
            case REPORTING_QUARTER:
                return DataType.REPORTING_QUARTER;
            case REPORTING_SEMESTER:
                return DataType.REPORTING_SEMESTER;
            case REPORTING_TIME_PERIOD:
                return DataType.REPORTING_TIME_PERIOD;
            case REPORTING_TRIMESTER:
                return DataType.REPORTING_TRIMESTER;
            case REPORTING_WEEK:
                return DataType.REPORTING_WEEK;
            case REPORTING_YEAR:
                return DataType.REPORTING_YEAR;
            case STANDARD_TIME_PERIOD:
                return DataType.STANDARD_TIME_PERIOD;
            case TIME_PERIOD:
                return DataType.TIME_RANGE;
            case TIMES_RANGE:
                return DataType.TIME_RANGE;
            case XHTML:
                return DataType.XHTML;
        }
        return null;
    }

}
