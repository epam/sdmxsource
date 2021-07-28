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
package org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v2;

import org.sdmx.resources.sdmxml.schemas.v20.common.ActionType;
import org.sdmx.resources.sdmxml.schemas.v20.common.TextType;
import org.sdmx.resources.sdmxml.schemas.v20.message.ContactType;
import org.sdmx.resources.sdmxml.schemas.v20.message.HeaderType;
import org.sdmx.resources.sdmxml.schemas.v20.message.PartyType;
import org.sdmxsource.sdmx.api.builder.Builder;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.beans.base.ContactBean;
import org.sdmxsource.sdmx.api.model.beans.base.TextTypeWrapper;
import org.sdmxsource.sdmx.api.model.header.HeaderBean;
import org.sdmxsource.sdmx.api.model.header.PartyBean;
import org.sdmxsource.sdmx.util.date.DateUtil;
import org.sdmxsource.util.ObjectUtil;


/**
 * The type Structure header xml bean builder.
 */
public class StructureHeaderXmlBeanBuilder implements Builder<HeaderType, HeaderBean> {

    @Override
    public HeaderType build(HeaderBean buildFrom) throws SdmxException {
        HeaderType headerType = HeaderType.Factory.newInstance();
        if (ObjectUtil.validString(buildFrom.getId())) {
            headerType.setID(buildFrom.getId());
        }
        headerType.setTest(buildFrom.isTest());

        if (buildFrom.getAction() != null) {
            switch (buildFrom.getAction()) {
                case APPEND:
                    headerType.setDataSetAction(ActionType.APPEND);
                    break;
                case REPLACE:
                    headerType.setDataSetAction(ActionType.REPLACE);
                    break;
                case DELETE:
                    headerType.setDataSetAction(ActionType.DELETE);
                    break;
                case INFORMATION:
                    headerType.setDataSetAction(ActionType.INFORMATION);
                    break;
            }
        }

        if (buildFrom.getName() != null) {
            for (TextTypeWrapper ttw : buildFrom.getName()) {
                TextType tt = headerType.addNewName();
                tt.setLang(ttw.getLocale());
                tt.setStringValue(ttw.getValue());
            }
        }

        if (buildFrom.getSource() != null) {
            for (TextTypeWrapper ttw : buildFrom.getSource()) {
                TextType tt = headerType.addNewSource();
                tt.setLang(ttw.getLocale());
                tt.setStringValue(ttw.getValue());
            }
        }

        //ALL DATE RELATED INFO
        if (buildFrom.getExtracted() != null) {
            headerType.setExtracted(DateUtil.createCalendar(buildFrom.getExtracted()));
        }
        if (buildFrom.getPrepared() != null) {
            headerType.setPrepared(DateUtil.createCalendar(buildFrom.getPrepared()));
        }
        if (buildFrom.getReportingBegin() != null) {
            headerType.setReportingBegin(DateUtil.createCalendar(buildFrom.getReportingBegin()));
        }
        if (buildFrom.getReportingEnd() != null) {
            headerType.setReportingEnd(DateUtil.createCalendar(buildFrom.getReportingEnd()));
        }

        //SENDER
        if (buildFrom.getSender() != null) {
            PartyBean sender = buildFrom.getSender();
            PartyType senderType = headerType.addNewSender();
            if (ObjectUtil.validString(sender.getId())) {
                senderType.setId(sender.getId());
            }
            for (TextTypeWrapper ttw : sender.getName()) {
                TextType tt = senderType.addNewName();
                tt.setLang(ttw.getLocale());
                tt.setStringValue(ttw.getValue());
            }
            //CONTACT INFO
            for (ContactBean contact : sender.getContacts()) {
                buildContact(senderType.addNewContact(), contact);
            }
        }
        //RECEIVER
        if (buildFrom.getReceiver() != null) {
            for (PartyBean receiver : buildFrom.getReceiver()) {
                PartyType receiverType = headerType.addNewReceiver();
                if (ObjectUtil.validString(receiver.getId())) {
                    receiverType.setId(receiver.getId());
                }
                for (TextTypeWrapper ttw : receiver.getName()) {
                    TextType tt = receiverType.addNewName();
                    tt.setLang(ttw.getLocale());
                    tt.setStringValue(ttw.getValue());
                }
                //CONTACT INFO
                for (ContactBean contact : receiver.getContacts()) {
                    buildContact(receiverType.addNewContact(), contact);
                }
            }
        }
        if (!ObjectUtil.validCollection(headerType.getReceiverList())) {
            PartyType receiverType = headerType.addNewReceiver();
            receiverType.setId("unknown");
        }
        return headerType;
    }


    private void buildContact(ContactType contactType, ContactBean contact) {
        if (contact.getDepartments() != null) {
            for (TextTypeWrapper ttw : contact.getDepartments()) {
                TextType tt = contactType.addNewDepartment();
                tt.setLang(ttw.getLocale());
                tt.setStringValue(ttw.getValue());
            }
        }
        if (contact.getName() != null) {
            for (TextTypeWrapper ttw : contact.getName()) {
                TextType tt = contactType.addNewName();
                tt.setLang(ttw.getLocale());
                tt.setStringValue(ttw.getValue());
            }
        }
        if (contact.getRole() != null) {
            for (TextTypeWrapper ttw : contact.getRole()) {
                TextType tt = contactType.addNewRole();
                tt.setLang(ttw.getLocale());
                tt.setStringValue(ttw.getValue());
            }
        }
        if (contact.getEmail() != null) {
            for (String val : contact.getEmail()) {
                contactType.addEmail(val);
            }
        }
        if (contact.getFax() != null) {
            for (String val : contact.getFax()) {
                contactType.addFax(val);
            }
        }
        if (contact.getTelephone() != null) {
            for (String val : contact.getTelephone()) {
                contactType.addTelephone(val);
            }
        }
        if (contact.getUri() != null) {
            for (String val : contact.getUri()) {
                contactType.addURI(val);
            }
        }
        if (contact.getX400() != null) {
            for (String val : contact.getX400()) {
                contactType.addX400(val);
            }
        }
    }
}
