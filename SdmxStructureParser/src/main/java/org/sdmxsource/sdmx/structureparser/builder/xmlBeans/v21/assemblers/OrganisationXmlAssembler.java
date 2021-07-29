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
package org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v21.assemblers;

import org.sdmx.resources.sdmxml.schemas.v21.structure.ContactType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.OrganisationType;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.beans.base.ContactBean;
import org.sdmxsource.sdmx.api.model.beans.base.OrganisationBean;
import org.sdmxsource.util.ObjectUtil;

/**
 * The type Organisation xml assembler.
 */
public class OrganisationXmlAssembler extends AbstractBeanAssembler implements Assembler<OrganisationType, OrganisationBean> {

    @Override
    public void assemble(OrganisationType assembleInto, OrganisationBean assembleFrom) throws SdmxException {
        for (ContactBean currentContact : assembleFrom.getContacts()) {
            ContactType contact = assembleInto.addNewContact();

            if (ObjectUtil.validString(currentContact.getId())) {
                contact.setId(currentContact.getId());
            }
            if (ObjectUtil.validCollection(currentContact.getDepartments())) {
                contact.setDepartmentArray(getTextType(currentContact.getDepartments()));
            }
            if (ObjectUtil.validCollection(currentContact.getName())) {
                contact.setNameArray(getTextType(currentContact.getName()));
            }
            if (ObjectUtil.validCollection(currentContact.getRole())) {
                contact.setRoleArray(getTextType(currentContact.getRole()));
            }
            if (ObjectUtil.validCollection(currentContact.getTelephone())) {
                contact.getTelephoneList().addAll(currentContact.getTelephone());
            }
            if (ObjectUtil.validCollection(currentContact.getFax())) {
                contact.getFaxList().addAll(currentContact.getFax());
            }
            if (ObjectUtil.validCollection(currentContact.getEmail())) {
                contact.getEmailList().addAll(currentContact.getEmail());
            }
            if (ObjectUtil.validCollection(currentContact.getUri())) {
                contact.getURIList().addAll(currentContact.getUri());
            }
            if (ObjectUtil.validCollection(currentContact.getX400())) {
                contact.getX400List().addAll(currentContact.getX400());
            }
        }
    }
}
