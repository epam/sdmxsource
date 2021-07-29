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

/**
 * Contains an enumerated list of the SDMX standard error codes, along with an english representation of the error
 *
 * @author Matt Nelson
 */
public enum SDMX_ERROR_CODE {
    /**
     * The No results found.
     */
    NO_RESULTS_FOUND(100, "No Results Found", 404),
    /**
     * Unauthorised sdmx error code.
     */
    UNAUTHORISED(110, "Unauthorized", 401),
    /**
     * The Response too large.
     */
    RESPONSE_TOO_LARGE(130, "ResponseToo Large", 413),
    /**
     * The Syntax error.
     */
    SYNTAX_ERROR(140, "Syntax Error", 400),
    /**
     * The Semantic error.
     */
    SEMANTIC_ERROR(150, "Semantic Error", 400),
    /**
     * The Internal server error.
     */
    INTERNAL_SERVER_ERROR(500, "Internal Server Error", 500),
    /**
     * The Not implemented.
     */
    NOT_IMPLEMENTED(501, "Not Implemented", 501),
    /**
     * The Service unavailable.
     */
    SERVICE_UNAVAILABLE(503, "Service Unavailable", 503),
    /**
     * The Response size exceeds service limit.
     */
    RESPONSE_SIZE_EXCEEDS_SERVICE_LIMIT(510, "Response size exceeds service limit", 413);

    private Integer clientErrorCode;
    private String errorString;
    private Integer httpRestErrorCode;

    private SDMX_ERROR_CODE(Integer clientErrorCode, String errorString, int httpRestErrorCode) {
        this.clientErrorCode = clientErrorCode;
        this.errorString = errorString;
        this.httpRestErrorCode = httpRestErrorCode;
    }

    /**
     * Parse client code sdmx error code.
     *
     * @param code the code
     * @return the sdmx error code
     */
    public static SDMX_ERROR_CODE parseClientCode(int code) {
        for (SDMX_ERROR_CODE currentCode : values()) {
            if (currentCode.getClientErrorCode() == code) {
                return currentCode;
            }
        }
        return null;
    }

    /**
     * Returns the SDMX error code as an Integer
     *
     * @return client error code
     */
    public Integer getClientErrorCode() {
        return clientErrorCode;
    }

    /**
     * Returns a English representation of the error code
     *
     * @return error string
     */
    public String getErrorString() {
        return errorString;
    }

    /**
     * Returns the HHTP error code used by the REST interface which corresponds to the SDMX error.
     * This is defined in the SDMX Web Services Guidelines:
     * http://sdmx.org/wp-content/uploads/2012/05/SDMX_2_1-SECTION_07_WebServicesGuidelines_May2012.pdf
     *
     * @return http rest error code
     */
    public Integer getHttpRestErrorCode() {
        return httpRestErrorCode;
    }
}
