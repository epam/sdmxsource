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
package org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v1;

import org.apache.log4j.Logger;
import org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.structure.AgencyType;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.beans.base.AgencyBean;


/**
 * The type Agency xml bean builder.
 */
public class AgencyXmlBeanBuilder extends AbstractBuilder {

    static {
        log = Logger.getLogger(AgencyXmlBeanBuilder.class);
    }

    /**
     * Build agency type.
     *
     * @param buildFrom the build from
     * @return the agency type
     * @throws SdmxException the sdmx exception
     */
    public AgencyType build(AgencyBean buildFrom) throws SdmxException {
        AgencyType builtObj = AgencyType.Factory.newInstance();
        if (validString(buildFrom.getId())) {
            builtObj.setId(buildFrom.getId());
        }
        if (buildFrom.getUri() != null) {
            builtObj.setUri(buildFrom.getUri().toString());
        }
        if (validCollection(buildFrom.getNames())) {
            builtObj.setNameArray(getTextType(buildFrom.getNames()));
        }
        //FUNC Missing maintenance information

        return builtObj;
    }
}
