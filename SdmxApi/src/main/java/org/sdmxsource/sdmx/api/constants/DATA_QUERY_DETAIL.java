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
package org.sdmxsource.sdmx.api.constants;

import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;

/**
 * For a 2.1 REST data query, this enum contains a list of the parameters.
 * <p>
 * Detail of data query; possible options are:
 * <ul>
 *   <li>data only - Exclude attributes and groups</li>
 *   <li>series keys only - return only the series elements and dimensions that make up the series key</li>
 *   <li>full - Return everything</li>
 *   <li>no data - </li>
 * </ul>
 *
 * @author Matt Nelson
 */
public enum DATA_QUERY_DETAIL {
    /**
     * Exclude attributes and groups
     */
    DATA_ONLY("dataonly"),
    /**
     * Return only the series elements and dimensions that make up the series key
     */
    SERIES_KEYS_ONLY("serieskeysonly"),
    /**
     * Return everything
     */
    FULL("full"),
    /**
     *
     */
    NO_DATA("nodata");

    private String restParam;


    private DATA_QUERY_DETAIL(String restParam) {
        this.restParam = restParam;
    }

    public static DATA_QUERY_DETAIL parseString(String str) {
        /**
         * In SDMXQueryData.xsd the value for the detail is SeriesKeyOnly and not serieskeysonly(as in the REST Guidelines)
         * a temporary hack for this value
         */
        if (str.equals("SeriesKeyOnly")) {
            str = "serieskeysonly";
        }

        for (DATA_QUERY_DETAIL currentQueryDetail : DATA_QUERY_DETAIL.values()) {
            if (currentQueryDetail.restParam.equalsIgnoreCase(str)) {
                return currentQueryDetail;
            }
        }
        StringBuilder sb = new StringBuilder();
        String concat = "";
        for (DATA_QUERY_DETAIL currentQueryDetail : DATA_QUERY_DETAIL.values()) {
            sb.append(concat + currentQueryDetail.restParam);
            concat = ", ";
        }
        throw new SdmxSemmanticException("Unknown Parameter " + str + " allowed parameters: " + sb.toString());
    }

    public String getRestParam() {
        return restParam;
    }
}
