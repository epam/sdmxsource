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
package org.sdmxsource.sdmx.structureparser.builder.query.impl;

import org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.message.QueryMessageType;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.exception.SdmxNotImplementedException;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;

import java.util.List;


/**
 * The type Query bean builder v 1.
 */
public class QueryBeanBuilderV1 {


    /**
     * Build list.
     *
     * @param queryMessage the query message
     * @return the list
     */
    public List<StructureReferenceBean> build(QueryMessageType queryMessage) {
        //FUNC build Query from version 1.0 queryMessage
        throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "QueryMessage");

//		List<StructureReference> reutrnList = new ArrayList<StructureReference>();
//		
//		if(queryMessage.getQuery() != null) {
//			if(queryMessage.getQuery().getAgencyWhereList() != null) {
//				for(AgencyWhereType refType : queryMessage.getQuery().getAgencyWhereList()) {
//					
//				}
//			}
//		}
//		
//		
//		return reutrnList;
    }
}
