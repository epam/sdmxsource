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

import org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.common.ActionType;
import org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.message.HeaderType;
import org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.message.PartyType;
import org.sdmxsource.sdmx.api.builder.Builder;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.header.HeaderBean;
import org.sdmxsource.sdmx.api.model.header.PartyBean;
import org.sdmxsource.util.ObjectUtil;

import java.util.Calendar;


/**
 * The type Header xml beans builder.
 */
public class HeaderXmlBeansBuilder extends AbstractBuilder implements Builder<HeaderType, HeaderBean> {


    @Override
    public HeaderType build(HeaderBean buildFrom) throws SdmxException {
        HeaderType headerType = HeaderType.Factory.newInstance();

        if (buildFrom != null && ObjectUtil.validString(buildFrom.getId())) {
            headerType.setID(buildFrom.getId());
        } else {
            headerType.setID("unassigned");
        }
        if (buildFrom != null && buildFrom.getPrepared() != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(buildFrom.getPrepared());
            headerType.setPrepared(cal);
        } else {
            headerType.setPrepared(Calendar.getInstance());
        }

        if (buildFrom != null && buildFrom.getAction() != null) {
            switch (buildFrom.getAction()) {
                case APPEND:
                    headerType.setDataSetAction(ActionType.UPDATE);
                    break;
                case REPLACE:
                    headerType.setDataSetAction(ActionType.UPDATE);
                    break;
                case DELETE:
                    headerType.setDataSetAction(ActionType.DELETE);
                    break;
                default:
                    headerType.setDataSetAction(ActionType.UPDATE);
            }
        } else {
            headerType.setDataSetAction(ActionType.UPDATE);
        }

        if (buildFrom != null && buildFrom.getSender() != null) {
            setSenderInfo(headerType, buildFrom.getSender());
        } else {
            PartyType sender = headerType.addNewSender();
            sender.setId("unknown");
        }

        return headerType;
    }

    private void setSenderInfo(HeaderType headerType, PartyBean party) {
        PartyType sender = headerType.addNewSender();

        if (ObjectUtil.validString(party.getId())) {
            sender.setId(party.getId());
        }
    }
}
