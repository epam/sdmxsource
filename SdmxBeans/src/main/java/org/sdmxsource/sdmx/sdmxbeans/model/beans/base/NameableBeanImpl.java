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
import org.sdmx.resources.sdmxml.schemas.v21.structure.NameableType;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.IdentifiableBean;
import org.sdmxsource.sdmx.api.model.beans.base.NameableBean;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.base.TextTypeWrapper;
import org.sdmxsource.sdmx.api.model.mutable.base.NameableMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.base.TextTypeWrapperMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.util.TextTypeUtil;
import org.sdmxsource.sdmx.util.beans.ValidationUtil;
import org.sdmxsource.util.ObjectUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * The type Nameable bean.
 */
public abstract class NameableBeanImpl extends IdentifiableBeanImpl implements NameableBean {
    private static final long serialVersionUID = 1L;

    /**
     * The Name.
     */
    protected List<TextTypeWrapper> name = new ArrayList<TextTypeWrapper>();
    /**
     * The Description.
     */
    List<TextTypeWrapper> description = new ArrayList<TextTypeWrapper>();


    /**
     * Instantiates a new Nameable bean.
     *
     * @param bean the bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM ITSELF, CREATES STUB BEAN //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    protected NameableBeanImpl(NameableBean bean) {
        super(bean);
        this.name = bean.getNames();
        validateNameableAttributes();
    }

    /**
     * Instantiates a new Nameable bean.
     *
     * @param bean   the bean
     * @param parent the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEAN				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    protected NameableBeanImpl(NameableMutableBean bean,
                               IdentifiableBean parent) {
        super(bean, parent);

        if (bean.getNames() != null) {
            for (TextTypeWrapperMutableBean mutable : bean.getNames()) {
                if (ObjectUtil.validString(mutable.getValue())) {
                    name.add(new TextTypeWrapperImpl(mutable, this));
                }
            }
        }
        if (bean.getDescriptions() != null) {
            for (TextTypeWrapperMutableBean mutable : bean.getDescriptions()) {
                if (ObjectUtil.validString(mutable.getValue())) {
                    description.add(new TextTypeWrapperImpl(mutable, this));
                }
            }
        }
        validateNameableAttributes();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM READER                    //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    //	public NameableBeanImpl(SDMX_STRUCTURE_TYPE structure, SdmxReader reader, IdentifiableBean parent) {
    //		super(structure, reader, parent);
    //
    //		String maintainableNode = reader.getCurrentElement();
    //
    //
    //		while(reader.peek().equals("Name")) {
    //			reader.moveNextElement();
    //			String lang = reader.getAttributeValue("lang", false);
    //			name.add(new TextTypeWrapperImpl(lang, reader.getCurrentElementValue(), this));
    //		}
    //		while(reader.peek().equals("Description")) {
    //			reader.moveNextElement();
    //			String lang = reader.getAttributeValue("lang", false);
    //			description.add(new TextTypeWrapperImpl(lang, reader.getCurrentElementValue(), this));
    //		}
    //
    //		if(!reader.getCurrentElement().equals(maintainableNode)) {
    //			reader.moveBackToElement(maintainableNode);
    //		}
    //		validateNameableAttributes();
    //	}

    /**
     * Instantiates a new Nameable bean.
     *
     * @param createdFrom   the created from
     * @param structureType the structure type
     * @param parent        the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public NameableBeanImpl(NameableType createdFrom, SDMX_STRUCTURE_TYPE structureType, IdentifiableBean parent) {
        super(createdFrom, structureType, parent);
        this.name = TextTypeUtil.wrapTextTypeV21(createdFrom.getNameList(), this);
        this.description = TextTypeUtil.wrapTextTypeV21(createdFrom.getDescriptionList(), this);
        validateNameableAttributes();
    }

    /**
     * Instantiates a new Nameable bean.
     *
     * @param createdFrom     the created from
     * @param structureType   the structure type
     * @param id              the id
     * @param uri             the uri
     * @param name            the name
     * @param description     the description
     * @param annotationsType the annotations type
     * @param parent          the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public NameableBeanImpl(XmlObject createdFrom, SDMX_STRUCTURE_TYPE structureType,
                            String id,
                            String uri,
                            List<TextType> name,
                            List<TextType> description,
                            AnnotationsType annotationsType,
                            IdentifiableBean parent) {
        super(createdFrom, structureType, id, uri, annotationsType, parent);
        this.name = TextTypeUtil.wrapTextTypeV2(name, this);
        this.description = TextTypeUtil.wrapTextTypeV2(description, this);
        validateNameableAttributes();
    }

    /**
     * Instantiates a new Nameable bean.
     *
     * @param createdFrom     the created from
     * @param structureType   the structure type
     * @param id              the id
     * @param uri             the uri
     * @param name            the name
     * @param description     the description
     * @param annotationsType the annotations type
     * @param parent          the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public NameableBeanImpl(XmlObject createdFrom,
                            SDMX_STRUCTURE_TYPE structureType,
                            String id,
                            String uri,
                            List<org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.common.TextType> name,
                            List<org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.common.TextType> description,
                            org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.common.AnnotationsType annotationsType,
                            IdentifiableBean parent) {
        super(createdFrom, structureType, id, uri, annotationsType, parent);
        this.name = TextTypeUtil.wrapTextTypeV1(name, this);
        this.description = TextTypeUtil.wrapTextTypeV1(description, this);
        validateNameableAttributes();
    }

    /**
     * Validate nameable attributes.
     *
     * @throws SdmxSemmanticException the sdmx semmantic exception
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATION							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    protected void validateNameableAttributes() throws SdmxSemmanticException {
        if (this.structureType != SDMX_STRUCTURE_TYPE.SUBSCRIPTION &&
                this.structureType != SDMX_STRUCTURE_TYPE.REGISTRATION) {
            if (this.name == null || this.name.size() == 0) {
                throw new SdmxSemmanticException(ExceptionCode.STRUCTURE_IDENTIFIABLE_MISSING_NAME, this.structureType + "  " + this.getId());
            }
            ValidationUtil.validateTextType(this.name, "");
            ValidationUtil.validateTextType(this.description, null);
        }
    }

    /**
     * Deep equals internal boolean.
     *
     * @param bean                   the bean
     * @param includeFinalProperties the include final properties
     * @return the boolean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////DEEP EQUALS							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    protected boolean deepEqualsInternal(NameableBean bean, boolean includeFinalProperties) {
        if (bean == null) {
            return false;
        }
        //If the user has choosen not to include final properties, then ignore the name and description
        if (includeFinalProperties) {
            if (!super.equivalent(name, bean.getNames(), includeFinalProperties)) {
                return false;
            }
            if (!super.equivalent(description, bean.getDescriptions(), includeFinalProperties)) {
                return false;
            }
        }
        return super.deepEqualsInternal(bean, includeFinalProperties);
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////COMPOSITES								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected Set<SDMXBean> getCompositesInternal() {
        Set<SDMXBean> composites = super.getCompositesInternal();
        super.addToCompositeSet(name, composites);
        super.addToCompositeSet(description, composites);
        return composites;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public List<TextTypeWrapper> getNames() {
        return new ArrayList<TextTypeWrapper>(name);
    }

    @Override
    public List<TextTypeWrapper> getAllTextTypes() {
        List<TextTypeWrapper> returnList = super.getAllTextTypes();
        returnList.addAll(name);
        returnList.addAll(description);
        return returnList;
    }

    @Override
    public String getName() {
        //HACK This does not work properly
        TextTypeWrapper ttw = TextTypeUtil.getDefaultLocale(name);
        return (ttw == null) ? null : ttw.getValue();
    }

    @Override
    public List<TextTypeWrapper> getDescriptions() {
        return new ArrayList<TextTypeWrapper>(description);
    }

    @Override
    public String getDescription() {
        //HACK This does not work properly
        TextTypeWrapper ttw = TextTypeUtil.getDefaultLocale(description);
        return (ttw == null) ? null : ttw.getValue();
    }
}
