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
import org.sdmxsource.sdmx.api.constants.SDMX_SCHEMA;
import org.sdmxsource.sdmx.api.exception.SdmxNotImplementedException;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.api.model.beans.registry.ProvisionAgreementBean;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.XmlBeanBuilder;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.registry.response.QueryProvisionResponseBuilder;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.registry.response.QueryStructureResponseBuilder;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.registry.response.impl.v2.QueryProvisionResponseBuilderV2;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.registry.response.impl.v21.ErrorResponseBuilder;
import org.sdmxsource.sdmx.util.beans.container.SdmxBeansImpl;

import java.util.Collection;


/**
 * Builds error and success responses for querying provisions.
 */
public class QueryProvisionResponseBuilderImpl extends XmlBeanBuilder implements QueryProvisionResponseBuilder {
    private final QueryProvisionResponseBuilderV2 v2Builder;

    private final QueryStructureResponseBuilder queryStructureResponseBuilder;

    private final ErrorResponseBuilder errorResponseBuilder2_1;

    /**
     * Instantiates a new Query provision response builder.
     */
    public QueryProvisionResponseBuilderImpl() {
        this(new ErrorResponseBuilder(), QueryProvisionResponseBuilderV2.INSTANCE, new QueryStructureResponseBuilderImpl());
    }

    /**
     * Instantiates a new Query provision response builder.
     *
     * @param errorResponseBuilder2_1       the error response builder 2 1
     * @param v2Builder                     the v 2 builder
     * @param queryStructureResponseBuilder the query structure response builder
     */
    public QueryProvisionResponseBuilderImpl(
            final ErrorResponseBuilder errorResponseBuilder2_1,
            final QueryProvisionResponseBuilderV2 v2Builder,
            final QueryStructureResponseBuilder queryStructureResponseBuilder) {
        this.v2Builder = v2Builder;
        this.queryStructureResponseBuilder = queryStructureResponseBuilder;
        this.errorResponseBuilder2_1 = errorResponseBuilder2_1;
    }

    @Override
    public XmlObject buildErrorResponse(Throwable th, SDMX_SCHEMA schemaVersion) {
        XmlObject response = null;
        switch (schemaVersion) {
            case VERSION_TWO:
                response = v2Builder.buildErrorResponse(th);
                break;
            case VERSION_TWO_POINT_ONE:
                response = errorResponseBuilder2_1.buildErrorResponse(th);
                break;
            default:
                throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, schemaVersion);
        }
        super.writeSchemaLocation(response, schemaVersion);
        return response;
    }

    @Override
    public XmlObject buildSuccessResponse(Collection<ProvisionAgreementBean> provisions, SDMX_SCHEMA schemaVersion) {
        XmlObject response = null;

        switch (schemaVersion) {
            case VERSION_TWO:
                response = v2Builder.buildSuccessResponse(provisions);
                break;
            case VERSION_TWO_POINT_ONE:
                SdmxBeans beans = new SdmxBeansImpl();
                beans.addIdentifiables(provisions);
                response = queryStructureResponseBuilder.buildSuccessResponse(beans, schemaVersion, false);
                break;
            default:
                throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, schemaVersion);
        }

        super.writeSchemaLocation(response, schemaVersion);
        return response;
    }

}
