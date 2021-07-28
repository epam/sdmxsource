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
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.XmlBeanBuilder;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.registry.response.QueryStructureResponseBuilder;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.registry.response.impl.v2.QueryStructureResponseBuilderV2;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.registry.response.impl.v21.ErrorResponseBuilder;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v2.StructureXmlBeanBuilder;


/**
 * Builds error and success responses for querying structures.
 */
public class QueryStructureResponseBuilderImpl extends XmlBeanBuilder implements QueryStructureResponseBuilder {

    private final QueryStructureResponseBuilderV2 v2Builder;

    private final StructureXmlBeanBuilder structv2Builder;

    private final org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v1.StructureXmlBeanBuilder structv1Builder;

    private final org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v21.StructureXmlBeanBuilder structV2_1Builder;

    private final ErrorResponseBuilder errorResponseBuilder2_1 = new ErrorResponseBuilder();

    /**
     * Instantiates a new Query structure response builder.
     */
    public QueryStructureResponseBuilderImpl() {
        this(
                new QueryStructureResponseBuilderV2(),
                new StructureXmlBeanBuilder(),
                new org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v1.StructureXmlBeanBuilder(),
                new org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v21.StructureXmlBeanBuilder());
    }

    /**
     * Instantiates a new Query structure response builder.
     *
     * @param v2Builder         the v 2 builder
     * @param structv2Builder   the structv 2 builder
     * @param structv1Builder   the structv 1 builder
     * @param structV2_1Builder the struct v 2 1 builder
     */
    public QueryStructureResponseBuilderImpl(
            final QueryStructureResponseBuilderV2 v2Builder,
            final StructureXmlBeanBuilder structv2Builder,
            final org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v1.StructureXmlBeanBuilder structv1Builder,
            final org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v21.StructureXmlBeanBuilder structV2_1Builder) {
        this.v2Builder = v2Builder;
        this.structv2Builder = structv2Builder;
        this.structv1Builder = structv1Builder;
        this.structV2_1Builder = structV2_1Builder;
    }

    @Override
    public XmlObject buildErrorResponse(Throwable th, SDMX_SCHEMA schemaVersion) {
        XmlObject response = null;
        switch (schemaVersion) {
            case VERSION_TWO_POINT_ONE:
                response = errorResponseBuilder2_1.buildErrorResponse(th);
                break;
            case VERSION_TWO:
                response = v2Builder.buildErrorResponse(th);
                break;
            default:
                throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, schemaVersion);
        }

        super.writeSchemaLocation(response, schemaVersion);
        return response;

    }

    @Override
    public XmlObject buildSuccessResponse(SdmxBeans beans, SDMX_SCHEMA schemaVersion, boolean returnAsStructureMessage) {
        XmlObject response = null;

        switch (schemaVersion) {
            case VERSION_TWO_POINT_ONE:
                if (beans.getAllMaintainables().size() == 0) {
                    response = errorResponseBuilder2_1.buildErrorResponse(SDMX_ERROR_CODE.NO_RESULTS_FOUND);
                } else {
                    response = structV2_1Builder.build(beans);
                }
                break;
            case VERSION_TWO:
                if (returnAsStructureMessage) {
                    response = structv2Builder.build(beans);
                } else {
                    response = v2Builder.buildSuccessResponse(beans);
                }
                break;

            case VERSION_ONE:
                response = structv1Builder.build(beans);
                break;

            default:
                throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, schemaVersion);
        }

        super.writeSchemaLocation(response, schemaVersion);
        return response;
    }
}
