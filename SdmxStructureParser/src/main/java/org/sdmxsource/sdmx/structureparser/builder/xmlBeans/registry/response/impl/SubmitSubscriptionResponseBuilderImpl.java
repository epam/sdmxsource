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
import org.sdmxsource.sdmx.api.model.beans.registry.SubscriptionBean;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.registry.response.SubmitSubscriptionResponseBuilder;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.registry.response.impl.v21.SubmitSubscriptionResponseBuilderV2_1;

import java.util.Collection;


/**
 * Builds error and success responses for subscription submissions.
 */
public class SubmitSubscriptionResponseBuilderImpl implements SubmitSubscriptionResponseBuilder {

    private final SubmitSubscriptionResponseBuilderV2_1 v21Builder = new SubmitSubscriptionResponseBuilderV2_1();

    @Override
    public XmlObject buildErrorResponse(SubscriptionBean subscription, SDMX_SCHEMA schemaVersion, Throwable th) {
        switch (schemaVersion) {
            case VERSION_TWO_POINT_ONE:
                return v21Builder.buildErrorResponse(subscription, th);
            default:
                throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "Submit Subscitpion response in version" + schemaVersion);
        }
    }

    @Override
    public XmlObject buildSuccessResponse(Collection<SubscriptionBean> notifications, SDMX_SCHEMA schemaVersion) {
        switch (schemaVersion) {
            case VERSION_TWO_POINT_ONE:
                return v21Builder.buildSuccessResponse(notifications);
            default:
                throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "Submit Subscitpion response in version" + schemaVersion);
        }
    }
}
