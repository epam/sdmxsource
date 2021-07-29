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
package org.sdmxsource.sdmx.sdmxbeans.model.beans.mapping;

import org.sdmx.resources.sdmxml.schemas.v20.structure.OrganisationMapType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.OrganisationSchemeMapType;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.mapping.ItemMapBean;
import org.sdmxsource.sdmx.api.model.beans.mapping.OrganisationSchemeMapBean;
import org.sdmxsource.sdmx.api.model.beans.mapping.StructureSetBean;
import org.sdmxsource.sdmx.api.model.mutable.mapping.OrganisationSchemeMapMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.util.RefUtil;
import org.sdmxsource.sdmx.util.beans.reference.CrossReferenceBeanImpl;
import org.sdmxsource.util.ObjectUtil;

import javax.xml.bind.ValidationException;


/**
 * The type Organisation scheme map bean.
 */
public class OrganisationSchemeMapBeanImpl extends ItemSchemeMapBeanImpl implements OrganisationSchemeMapBean {
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new Organisation scheme map bean.
     *
     * @param orgBean the org bean
     * @param parent  the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEANS			 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public OrganisationSchemeMapBeanImpl(OrganisationSchemeMapMutableBean orgBean, StructureSetBean parent) {
        super(orgBean, SDMX_STRUCTURE_TYPE.ORGANISATION_SCHEME_MAP, parent);
        try {
            validate();
        } catch (ValidationException e) {
            throw new SdmxSemmanticException(e, ExceptionCode.FAIL_VALIDATION, this);
        }
    }

    /**
     * Instantiates a new Organisation scheme map bean.
     *
     * @param orgBean the org bean
     * @param parent  the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public OrganisationSchemeMapBeanImpl(org.sdmx.resources.sdmxml.schemas.v21.structure.OrganisationSchemeMapType orgBean, StructureSetBean parent) {
        super(orgBean, SDMX_STRUCTURE_TYPE.ORGANISATION_SCHEME_MAP, parent);

        this.sourceRef = RefUtil.createReference(this, orgBean.getSource());
        this.targetRef = RefUtil.createReference(this, orgBean.getTarget());

        // get list of code maps
        if (orgBean.getOrganisationMapList() != null) {
            for (org.sdmx.resources.sdmxml.schemas.v21.structure.OrganisationMapType orgMap : orgBean.getOrganisationMapList()) {
                ItemMapBean item = new ItemMapBeanImpl(orgMap.getSource().getRef().getId(), orgBean.getTarget().getRef().getId(), this);
                items.add(item);
            }
        }
        try {
            validate();
        } catch (ValidationException e) {
            throw new SdmxSemmanticException(e, ExceptionCode.FAIL_VALIDATION, this);
        }
    }

    /**
     * Instantiates a new Organisation scheme map bean.
     *
     * @param orgBean the org bean
     * @param parent  the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public OrganisationSchemeMapBeanImpl(OrganisationSchemeMapType orgBean, StructureSetBean parent) {
        super(orgBean, SDMX_STRUCTURE_TYPE.ORGANISATION_SCHEME_MAP,
                orgBean.getId(), null, orgBean.getNameList(),
                orgBean.getDescriptionList(), orgBean.getAnnotations(), parent);

        if (orgBean.getOrganisationSchemeRef() != null) {
            if (ObjectUtil.validString(orgBean.getOrganisationSchemeRef().getURN())) {
                this.sourceRef = new CrossReferenceBeanImpl(this, orgBean.getOrganisationSchemeRef().getURN());
            } else {
                this.sourceRef = new CrossReferenceBeanImpl(this, orgBean.getOrganisationSchemeRef().getAgencyID(),
                        orgBean.getOrganisationSchemeRef().getOrganisationSchemeID(),
                        orgBean.getOrganisationSchemeRef().getVersion(), SDMX_STRUCTURE_TYPE.ORGANISATION_UNIT_SCHEME);
            }

        }
        if (orgBean.getTargetOrganisationSchemeRef() != null) {
            if (ObjectUtil.validString(orgBean.getTargetOrganisationSchemeRef().getURN())) {
                this.sourceRef = new CrossReferenceBeanImpl(this, orgBean.getTargetOrganisationSchemeRef().getURN());
            } else {
                this.targetRef = new CrossReferenceBeanImpl(this, orgBean.getTargetOrganisationSchemeRef().getAgencyID(),
                        orgBean.getTargetOrganisationSchemeRef().getOrganisationSchemeID(),
                        orgBean.getTargetOrganisationSchemeRef().getVersion(), SDMX_STRUCTURE_TYPE.ORGANISATION_UNIT_SCHEME);
            }

        }

        // get list of code maps
        if (orgBean.getOrganisationMapList() != null) {
            for (OrganisationMapType orgMap : orgBean.getOrganisationMapList()) {
                ItemMapBean item = new ItemMapBeanImpl(orgMap.getOrganisationAlias(),
                        orgMap.getOrganisationID(),
                        orgMap.getTargetOrganisationID(), this);
                items.add(item);
            }
        }
        try {
            validate();
        } catch (ValidationException e) {
            throw new SdmxSemmanticException(e, ExceptionCode.FAIL_VALIDATION, this);
        }
    }

    /**
     * Validate.
     *
     * @throws ValidationException the validation exception
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATION							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    protected void validate() throws ValidationException {
        if (this.sourceRef == null) {
            throw new SdmxSemmanticException(ExceptionCode.BEAN_MISSING_REQUIRED_ELEMENT, this.structureType, "OrganisationSchemeRef");
        }
        if (this.targetRef == null) {
            throw new SdmxSemmanticException(ExceptionCode.BEAN_MISSING_REQUIRED_ELEMENT, this.structureType, "TargetOrganisationSchemeRef");
        }
        if (this.items == null) {
            throw new SdmxSemmanticException(ExceptionCode.BEAN_MISSING_REQUIRED_ELEMENT, this.structureType, "OrganisationMap");
        }
    }
}
