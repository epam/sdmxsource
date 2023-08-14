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
package org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v21.assemblers;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.apache.xmlbeans.GDuration;
import org.sdmx.resources.sdmxml.schemas.v21.structure.TextFormatType;
import org.sdmxsource.sdmx.api.constants.TEXT_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.beans.base.TextFormatBean;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v21.DataTypeBuilder;


/**
 * The type Text format assembler.
 */
public class TextFormatAssembler implements Assembler<TextFormatType, TextFormatBean> {
    private final Logger log = LoggerFactory.getLogger(TextFormatAssembler.class);

    private final DataTypeBuilder dataTypeBuilder = new DataTypeBuilder();

    @Override
    public void assemble(TextFormatType assembleInto, TextFormatBean assembleFrom) throws SdmxException {
        TEXT_TYPE textType = assembleFrom.getTextType();
        if (textType != null) {
            try {
                assembleInto.setTextType(dataTypeBuilder.build(textType));
            } catch (Throwable th) {
                assembleInto.unsetTextType();
                log.warn("SDMX 2.1 - Unable to set TextType '" + textType + "'  on " + assembleFrom.getParent().toString());
            }
        }
        if (assembleFrom.getSequence().isSet()) {
            try {
                assembleInto.setIsSequence(assembleFrom.getSequence().isTrue());
            } catch (Throwable th) {
                assembleInto.unsetIsSequence();
                log.warn("SDMX 2.1 - Unable to set IsSequence '" + assembleFrom.getSequence() + "'  on " + assembleFrom.getParent().toString());
            }
        }
        if (assembleFrom.getMinLength() != null) {
            try {
                assembleInto.setMinLength(assembleFrom.getMinLength());
            } catch (Throwable th) {
                assembleInto.unsetMinLength();
                log.warn("SDMX 2.1 - Unable to set MinLength '" + assembleFrom.getMinLength() + "' on " + assembleFrom.getParent().toString());
            }
        }
        if (assembleFrom.getMaxLength() != null) {
            try {
                assembleInto.setMaxLength(assembleFrom.getMaxLength());
            } catch (Throwable th) {
                assembleInto.unsetMaxLength();
                log.warn("SDMX 2.1 - Unable to set MaxLength '" + assembleFrom.getMaxLength() + "'  on " + assembleFrom.getParent().toString());
            }
        }
        if (assembleFrom.getMinValue() != null) {
            try {
                assembleInto.setMinValue(assembleFrom.getMinValue());
            } catch (Throwable th) {
                assembleInto.unsetMinValue();
                log.warn("SDMX 2.1 - Unable to set MinValue '" + assembleFrom.getMinValue() + "' on " + assembleFrom.getParent().toString());
            }
        }
        if (assembleFrom.getMaxValue() != null) {
            try {
                assembleInto.setMaxValue(assembleFrom.getMaxValue());
            } catch (Throwable th) {
                assembleInto.unsetMaxValue();
                log.warn("SDMX 2.1 - Unable to set MaxValue '" + assembleFrom.getMaxValue() + "'  on " + assembleFrom.getParent().toString());
            }
        }
        if (assembleFrom.getStartValue() != null) {
            try {
                assembleInto.setStartValue(assembleFrom.getStartValue());
            } catch (Throwable th) {
                assembleInto.unsetStartValue();
                log.warn("SDMX 2.1 - Unable to set StartValue '" + assembleFrom.getStartValue() + "'  on " + assembleFrom.getParent().toString());
            }
        }
        if (assembleFrom.getEndValue() != null) {
            try {
                assembleInto.setEndValue(assembleFrom.getEndValue());
            } catch (Throwable th) {
                assembleInto.unsetEndValue();
                log.warn("SDMX 2.1 - Unable to set EndValue '" + assembleFrom.getEndValue() + "'  on " + assembleFrom.getParent().toString());
            }
        }
        if (assembleFrom.getInterval() != null) {
            try {
                assembleInto.setInterval(assembleFrom.getInterval());
            } catch (Throwable th) {
                assembleInto.unsetInterval();
                log.warn("SDMX 2.1 - Unable to set Interval '" + assembleFrom.getInterval() + "'  on " + assembleFrom.getParent().toString());
            }
        }
        if (assembleFrom.getTimeInterval() != null) {
            try {
                assembleInto.setTimeInterval(new GDuration(assembleFrom.getTimeInterval()));
            } catch (Throwable th) {
                assembleInto.unsetTimeInterval();
                log.warn("SDMX 2.1 - Unable to set TimeInterval '" + assembleFrom.getTimeInterval() + "'  on " + assembleFrom.getParent().toString());
            }
        }
        if (assembleFrom.getDecimals() != null) {
            try {
                assembleInto.setDecimals(assembleFrom.getDecimals());
            } catch (Throwable th) {
                assembleInto.unsetDecimals();
                log.warn("SDMX 2.1 - Unable to set Decimals '" + assembleFrom.getDecimals() + "'  on " + assembleFrom.getParent().toString());
            }
        }
        if (assembleFrom.getPattern() != null) {
            try {
                assembleInto.setPattern(assembleFrom.getPattern());
            } catch (Throwable th) {
                assembleInto.unsetPattern();
                log.warn("SDMX 2.1 - Unable to set Pattern '" + assembleFrom.getPattern() + "'  on " + assembleFrom.getParent().toString());
            }
        }
        if (assembleFrom.getStartTime() != null) {
            try {
                assembleInto.setStartTime(assembleFrom.getStartTime().getDateInSdmxFormat());
            } catch (Throwable th) {
                assembleInto.unsetStartTime();
                log.warn("SDMX 2.1 - Unable to set StartTime '" + assembleFrom.getStartTime().getDateInSdmxFormat() + "'  on " + assembleFrom.getParent().toString());
            }
        }
        if (assembleFrom.getEndTime() != null) {
            try {
                //FUNC 2.1 end time
            } catch (Throwable th) {
                assembleInto.unsetEndTime();
                log.warn("SDMX 2.1 - Unable to set EndTime '" + assembleFrom.getEndTime().getDateInSdmxFormat() + "'  on " + assembleFrom.getParent().toString());
            }
        }
    }
}
