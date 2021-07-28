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
package org.sdmxsource.sdmx.sdmxbeans.model.beans.base;

import org.apache.xmlbeans.XmlObject;
import org.sdmx.resources.sdmxml.schemas.v20.common.AnnotationsType;
import org.sdmx.resources.sdmxml.schemas.v20.common.TextType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.OrganisationSchemeType;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.constants.TERTIARY_BOOL;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.ItemSchemeBean;
import org.sdmxsource.sdmx.api.model.beans.base.OrganisationBean;
import org.sdmxsource.sdmx.api.model.beans.base.OrganisationSchemeBean;
import org.sdmxsource.sdmx.api.model.mutable.base.ItemMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.base.ItemSchemeMutableBean;

import java.net.URL;
import java.util.List;


/**
 * The type Organisation scheme bean.
 *
 * @param <T> the type parameter
 */
public abstract class OrganisationSchemeBeanImpl<T extends OrganisationBean> extends ItemSchemeBeanImpl<T> implements OrganisationSchemeBean<T> {
    private static final long serialVersionUID = -7101534599216735610L;

    /**
     * Instantiates a new Organisation scheme bean.
     *
     * @param bean           the bean
     * @param actualLocation the actual location
     * @param isServiceUrl   the is service url
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM ITSELF, CREATES STUB BEAN //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    protected OrganisationSchemeBeanImpl(ItemSchemeBean<T> bean, URL actualLocation, boolean isServiceUrl) {
        super(bean, actualLocation, isServiceUrl);
        validateOrganisationSchemeAttributes();
    }

    /**
     * Instantiates a new Organisation scheme bean.
     *
     * @param bean the bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEAN				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    protected OrganisationSchemeBeanImpl(ItemSchemeMutableBean<? extends ItemMutableBean> bean) {
        super(bean);
        validateOrganisationSchemeAttributes();
    }


    /**
     * Instantiates a new Organisation scheme bean.
     *
     * @param createdFrom   the created from
     * @param structureType the structure type
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    protected OrganisationSchemeBeanImpl(OrganisationSchemeType createdFrom, SDMX_STRUCTURE_TYPE structureType) {
        super(createdFrom, structureType);
        validateOrganisationSchemeAttributes();
    }

    /**
     * Instantiates a new Organisation scheme bean.
     *
     * @param createdFrom         the created from
     * @param structureType       the structure type
     * @param endDate             the end date
     * @param startDate           the start date
     * @param version             the version
     * @param isFinal             the is final
     * @param agencyId            the agency id
     * @param id                  the id
     * @param uri                 the uri
     * @param name                the name
     * @param description         the description
     * @param isExternalReference the is external reference
     * @param annotationsType     the annotations type
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    protected OrganisationSchemeBeanImpl(XmlObject createdFrom, SDMX_STRUCTURE_TYPE structureType,
                                         Object endDate,
                                         Object startDate,
                                         String version,
                                         TERTIARY_BOOL isFinal,
                                         String agencyId,
                                         String id,
                                         String uri,
                                         List<TextType> name,
                                         List<TextType> description,
                                         TERTIARY_BOOL isExternalReference,
                                         AnnotationsType annotationsType) {
        super(createdFrom, structureType, endDate, startDate, version, isFinal,
                agencyId, id, uri, name, description, isExternalReference, annotationsType);
        validateOrganisationSchemeAttributes();
    }

    /**
     * Instantiates a new Organisation scheme bean.
     *
     * @param createdFrom         the created from
     * @param structureType       the structure type
     * @param version             the version
     * @param agencyId            the agency id
     * @param id                  the id
     * @param uri                 the uri
     * @param name                the name
     * @param isExternalReference the is external reference
     * @param annotationsType     the annotations type
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    protected OrganisationSchemeBeanImpl(XmlObject createdFrom,
                                         SDMX_STRUCTURE_TYPE structureType,
                                         String version,
                                         String agencyId,
                                         String id,
                                         String uri,
                                         List<org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.common.TextType> name,
                                         TERTIARY_BOOL isExternalReference,
                                         org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.common.AnnotationsType annotationsType) {
        super(createdFrom, structureType, version, agencyId, id, uri, name, isExternalReference, annotationsType);
        validateOrganisationSchemeAttributes();
    }


    /**
     * Validate organisation scheme attributes.
     *
     * @throws SdmxSemmanticException the sdmx semmantic exception
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATION							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    protected void validateOrganisationSchemeAttributes() throws SdmxSemmanticException {
        if (isFinal().isTrue()) {
            throw new SdmxSemmanticException("Organisation Schemes can not be set to final");
        }
    }
}
