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
package org.sdmxsource.sdmx.structureparser.builder.xmlBeans.registry.response.impl.v1;

import org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.message.HeaderType;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.HeaderHelper;

import java.util.Calendar;


/**
 * The type V 1 helper.
 */
public class V1Helper {

    private static int REF = 1;

    /**
     * Sets header.
     *
     * @param header the header
     * @param beans  the beans
     */
    public static void setHeader(HeaderType header, SdmxBeans beans) {
        header.setID("IDREF" + REF);
        REF++;
        header.setTest(false);
        header.setPrepared(Calendar.getInstance());

        String senderId;
        if (beans != null && beans.getHeader() != null && beans.getHeader().getSender() != null) {
            // Get header information from the supplied beans
            senderId = beans.getHeader().getSender().getId();
        } else {
            // Get header info from HeaderHelper
            senderId = HeaderHelper.INSTANCE.getSenderId();
        }
        header.addNewSender().setId(senderId);
    }
}
