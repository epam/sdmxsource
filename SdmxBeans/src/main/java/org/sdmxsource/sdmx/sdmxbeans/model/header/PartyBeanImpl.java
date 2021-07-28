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
package org.sdmxsource.sdmx.sdmxbeans.model.header;

import org.sdmx.resources.sdmxml.schemas.v21.common.TextType;
import org.sdmx.resources.sdmxml.schemas.v21.message.ContactType;
import org.sdmx.resources.sdmxml.schemas.v21.message.PartyType;
import org.sdmx.resources.sdmxml.schemas.v21.message.SenderType;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.ContactBean;
import org.sdmxsource.sdmx.api.model.beans.base.TextTypeWrapper;
import org.sdmxsource.sdmx.api.model.header.PartyBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.ContactBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.TextTypeWrapperImpl;
import org.sdmxsource.util.ObjectUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


/**
 * The type Party bean.
 */
public class PartyBeanImpl implements PartyBean, Serializable {
    private static final long serialVersionUID = -4454791463810290878L;

    private List<TextTypeWrapper> name = new ArrayList<TextTypeWrapper>();
    private String id;
    private List<ContactBean> contacts = new ArrayList<ContactBean>();
    private String timeZone;

    /**
     * Instantiates a new Party bean.
     *
     * @param name     the name
     * @param id       the id
     * @param contacts the contacts
     * @param timeZone the time zone
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM ATTRIBUTES			   ////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public PartyBeanImpl(List<TextTypeWrapper> name, String id, List<ContactBean> contacts, String timeZone) {
        if (name != null) {
            this.name = new ArrayList<TextTypeWrapper>(name);
        }
        this.id = id;
        if (contacts != null) {
            this.contacts = new ArrayList<ContactBean>(contacts);
        }
        this.timeZone = timeZone;
        validate();
    }


    /**
     * Instantiates a new Party bean.
     *
     * @param partyType the party type
     */
/////////////////////////////////////////////////////////////////////////////////////////////////
    //////////BUILD FROM V2.1 SCHEMA		/////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////
    public PartyBeanImpl(PartyType partyType) {
        id = partyType.getId();
        if (ObjectUtil.validCollection(partyType.getNameList())) {
            for (TextType tt : partyType.getNameList()) {
                name.add(new TextTypeWrapperImpl(tt, null));
            }
        }
        if (ObjectUtil.validCollection(partyType.getContactList())) {
            for (ContactType contactType : partyType.getContactList()) {
                contacts.add(new ContactBeanImpl(contactType));
            }
        }
        validate();
    }

    /**
     * Instantiates a new Party bean.
     *
     * @param senderType the sender type
     */
    public PartyBeanImpl(SenderType senderType) {
        id = senderType.getId();
        if (ObjectUtil.validCollection(senderType.getNameList())) {
            for (TextType tt : senderType.getNameList()) {
                name.add(new TextTypeWrapperImpl(tt, null));
            }
        }
        if (ObjectUtil.validCollection(senderType.getContactList())) {
            for (ContactType contactType : senderType.getContactList()) {
                contacts.add(new ContactBeanImpl(contactType));
            }
        }
        timeZone = senderType.getTimezone();
        validate();
    }


    /**
     * Instantiates a new Party bean.
     *
     * @param partyType the party type
     */
/////////////////////////////////////////////////////////////////////////////////////////////////
    //////////BUILD FROM V2.0 SCHEMA		/////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////
    public PartyBeanImpl(org.sdmx.resources.sdmxml.schemas.v20.message.PartyType partyType) {
        id = partyType.getId();
        if (ObjectUtil.validCollection(partyType.getNameList())) {
            for (org.sdmx.resources.sdmxml.schemas.v20.common.TextType tt : partyType.getNameList()) {
                name.add(new TextTypeWrapperImpl(tt, null));
            }
        }
        if (ObjectUtil.validCollection(partyType.getContactList())) {
            for (org.sdmx.resources.sdmxml.schemas.v20.message.ContactType contactType : partyType.getContactList()) {
                contacts.add(new ContactBeanImpl(contactType));
            }
        }
        validate();
    }

    /**
     * Instantiates a new Party bean.
     *
     * @param partyType the party type
     */
/////////////////////////////////////////////////////////////////////////////////////////////////
    //////////BUILD FROM V1.0 SCHEMA		/////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////
    public PartyBeanImpl(org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.message.PartyType partyType) {
        id = partyType.getId();
        if (ObjectUtil.validCollection(partyType.getNameList())) {
            for (org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.common.TextType tt : partyType.getNameList()) {
                name.add(new TextTypeWrapperImpl(tt, null));
            }
        }
        if (ObjectUtil.validCollection(partyType.getContactList())) {
            for (org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.message.ContactType contactType : partyType.getContactList()) {
                contacts.add(new ContactBeanImpl(contactType));
            }
        }
        validate();
    }

    /**
     * Validate.
     *
     * @throws SdmxSemmanticException the sdmx semmantic exception
     */
/////////////////////////////////////////////////////////////////////////////////////////////////
    //////////VALIDATE						/////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////
    public void validate() throws SdmxSemmanticException {
        if (!ObjectUtil.validString(id)) {
            throw new SdmxSemmanticException("Party missing mandatory id");
        }
        if (timeZone != null) {
            Pattern idPattern = Pattern.compile("(\\+|\\-)(14:00|((0[0-9]|1[0-3]):[0-5][0-9]))");
            if (!idPattern.matcher(timeZone).matches()) {
                throw new SdmxSemmanticException("Time zone '" + timeZone + "' is in an invalid format. please ensure the format matches the patttern (\\+|\\-)(14:00|((0[0-9]|1[0-3]):[0-5][0-9]) example +12:30");
            }
        }
    }


    //////////GETTERS						/////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public List<TextTypeWrapper> getName() {
        return new ArrayList<TextTypeWrapper>(name);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public List<ContactBean> getContacts() {
        return new ArrayList<ContactBean>(contacts);
    }

    @Override
    public String getTimeZone() {
        return timeZone;
    }
}
