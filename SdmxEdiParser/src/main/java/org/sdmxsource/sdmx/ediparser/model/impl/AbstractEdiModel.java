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
package org.sdmxsource.sdmx.ediparser.model.impl;


import org.sdmxsource.sdmx.util.date.DateUtil;

import java.text.DateFormat;
import java.util.Date;

/**
 * The type Abstract edi model.
 */
public class AbstractEdiModel {
    /**
     * The Plus.
     */
    static final String PLUS = "+";
    /**
     * The Colon.
     */
    static final String COLON = ":";
    /**
     * The End tag.
     */
    static final String END_TAG = "'";

    /**
     * Prepend zeros string.
     *
     * @param value        the value
     * @param targetLength the target length
     * @return the string
     */
    String prependZeros(int value, int targetLength) {
        String refStr = Integer.toString(value);
        String prep = "";

        for (int i = refStr.length(); i < targetLength; i++) {
            prep += "0";
        }
        return prep + refStr;
    }

    /**
     * Format date string.
     *
     * @param date the date
     * @return the string
     */
    String formatDate(Date date) {
        DateFormat df = DateUtil.getDateFormatter("yyMMdd:HHmm");
        return df.format(date);
    }
}
