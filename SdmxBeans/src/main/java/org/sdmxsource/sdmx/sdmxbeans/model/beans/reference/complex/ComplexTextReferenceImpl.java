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
package org.sdmxsource.sdmx.sdmxbeans.model.beans.reference.complex;

import org.sdmxsource.sdmx.api.constants.TEXT_SEARCH;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.reference.complex.ComplexTextReference;

/**
 * The type Complex text reference.
 */
public class ComplexTextReferenceImpl implements ComplexTextReference {

    private String lang;
    private TEXT_SEARCH operator;
    private String searchParam;

    /**
     * Instantiates a new Complex text reference.
     *
     * @param lang        the lang
     * @param operator    the operator
     * @param searchParam the search param
     */
    public ComplexTextReferenceImpl(String lang, TEXT_SEARCH operator, String searchParam) {
        this.lang = lang;

        if (operator == null) {
            this.operator = TEXT_SEARCH.EQUAL;
        } else {
            this.operator = operator;
        }

        if (searchParam == null || searchParam.isEmpty()) {
            throw new SdmxSemmanticException("Not provided text to search for. It should not be null or empty.");
        }

        this.searchParam = searchParam;
    }

    @Override
    public String getLanguage() {
        return lang;
    }

    @Override
    public TEXT_SEARCH getOperator() {
        return operator;
    }

    @Override
    public String getSearchParameter() {
        return searchParam;
    }

}
