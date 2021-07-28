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
package org.sdmxsource.sdmx.structureparser.builder.xmlBeans.registry.response.impl;

import org.apache.xmlbeans.XmlObject;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_ERROR_CODE;
import org.sdmxsource.sdmx.api.constants.SDMX_SCHEMA;
import org.sdmxsource.sdmx.api.exception.SdmxNotImplementedException;
import org.sdmxsource.sdmx.api.model.beans.registry.RegistrationBean;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.XmlBeanBuilder;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.registry.response.QueryRegistrationResponseBuilder;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.registry.response.impl.v2.QueryRegistrationResponseBuilderV2;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.registry.response.impl.v21.ErrorResponseBuilder;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.registry.response.impl.v21.QueryRegistrationResponseBuilderV2_1;

import java.util.Collection;


/**
 * Builds error and success responses for querying registrations.
 */
public class QueryRegistrationResponseBuilderImpl extends XmlBeanBuilder implements QueryRegistrationResponseBuilder {

    private final QueryRegistrationResponseBuilderV2 v2Builder;

    private final QueryRegistrationResponseBuilderV2_1 v2_1Builder = new QueryRegistrationResponseBuilderV2_1();

    private final ErrorResponseBuilder errorResponseBuilder = new ErrorResponseBuilder();

    /**
     * Instantiates a new Query registration response builder.
     */
    public QueryRegistrationResponseBuilderImpl() {
        this(QueryRegistrationResponseBuilderV2.INSTANCE);
    }

    /**
     * Instantiates a new Query registration response builder.
     *
     * @param v2Builder the v 2 builder
     */
    public QueryRegistrationResponseBuilderImpl(QueryRegistrationResponseBuilderV2 v2Builder) {
        this.v2Builder = v2Builder;
    }

    @Override
    public XmlObject buildErrorResponse(Throwable th, SDMX_SCHEMA schemaVersion) {
        XmlObject response = null;
        switch (schemaVersion) {
            case VERSION_TWO:
                response = v2Builder.buildErrorResponse(th);
                break;
            case VERSION_TWO_POINT_ONE:
                response = v2_1Builder.buildErrorResponse(th);
                break;
            default:
                throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, schemaVersion);
        }
        super.writeSchemaLocation(response, schemaVersion);
        return response;
    }

    @Override
    public XmlObject buildSuccessResponse(Collection<RegistrationBean> registrations, SDMX_SCHEMA schemaVersion) {
        XmlObject response = null;

        switch (schemaVersion) {
            case VERSION_TWO:
                response = v2Builder.buildSuccessResponse(registrations);
                break;
            case VERSION_TWO_POINT_ONE:
                if (registrations.size() == 0) {
                    response = errorResponseBuilder.buildErrorResponse(SDMX_ERROR_CODE.NO_RESULTS_FOUND);
                } else {
                    response = v2_1Builder.buildSuccessResponse(registrations);
                }
                break;
            default:
                throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, schemaVersion);
        }

        super.writeSchemaLocation(response, schemaVersion);
        return response;
    }
}
