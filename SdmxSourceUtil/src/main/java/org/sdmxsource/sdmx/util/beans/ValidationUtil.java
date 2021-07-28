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
package org.sdmxsource.sdmx.util.beans;

import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.TextTypeWrapper;
import org.sdmxsource.util.ObjectUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;


/**
 * The type Validation util.
 */
public class ValidationUtil {
    private static final Pattern idWithInt = Pattern.compile("([A-Z]|[a-z]|\\\\|@|[0-9]|_|\\$|\\-)*");
    private static final Pattern idWithNoInt = Pattern.compile("([A-Z]|[a-z])+([A-Z]|[a-z]|\\\\|@|[0-9]|_|\\$|\\-)*");

    /**
     * Validates that the locale in the text type is unique
     *
     * @param textTypes       the text types
     * @param validPatternStr the valid pattern str
     * @throws SdmxSemmanticException the sdmx semmantic exception
     */
    public static void validateTextType(List<TextTypeWrapper> textTypes, String validPatternStr) throws SdmxSemmanticException {
        Pattern validPattern = null;
        if (ObjectUtil.validString(validPatternStr)) {
            validPattern = Pattern.compile(validPatternStr);
        }
        Set<String> languages = new HashSet<String>();
        if (textTypes != null) {
            for (TextTypeWrapper currentTextType : textTypes) {
                if (validPattern != null) {
                    if (!validPattern.matcher(currentTextType.getValue()).matches()) {
                        throw new SdmxSemmanticException(currentTextType.getValue() + " invalid with respect to allowed string : " + validPattern);
                    }
                }
                if (languages.contains(currentTextType.getLocale())) {
                    throw new SdmxSemmanticException(ExceptionCode.DUPLICATE_LANGUAGE, currentTextType.getLocale());
                }
                languages.add(currentTextType.getLocale());
            }
        }
    }


    /**
     * Trims the leading and trailing whitespace from the specified id
     * <p>
     * Validates that the id starts with an alpha character (or alpha numeric if startWithIntAllowed is true).
     * <p>
     * The id may that also contains one or more of the following:
     * <ul>
     *   <li>alphabetics in either case</li>
     *   <li>a 'star' symbol (*)</li>
     *   <li>an 'at' symbol (@)</li>
     *   <li>integers (0-9)<li>
     *   <li>an underscore (_)</li>
     *   <li>a dollar ($)</li>
     *   <li>a dash (-)</li>
     * </ul>
     *
     * @param id                  The string to
     * @param startWithIntAllowed Determines whether an integer is allowed to be the first character of the string
     * @return the string
     * @throws SdmxSemmanticException if the string id not valid - empty strings or null strings are not validated
     */
    public static String cleanAndValidateId(String id, boolean startWithIntAllowed) throws SdmxSemmanticException {
        if (!ObjectUtil.validString(id)) {
            return null;
        }

        String trimedId = id.trim();
        Pattern idPattern;

        if (startWithIntAllowed) {
            idPattern = idWithInt;
        } else {
            idPattern = idWithNoInt;
        }
        if (!idPattern.matcher(trimedId).matches()) {
            if (startWithIntAllowed) {
                throw new SdmxSemmanticException(ExceptionCode.STRUCTURE_INVALID_ID, trimedId);
            }
            throw new SdmxSemmanticException(ExceptionCode.STRUCTURE_INVALID_ID_START_ALPHA, trimedId);
        }
        return trimedId;
    }
}
