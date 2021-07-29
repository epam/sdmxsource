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
import org.sdmx.resources.sdmxml.schemas.v21.structure.ItemType;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.base.IdentifiableBean;
import org.sdmxsource.sdmx.api.model.mutable.base.ItemMutableBean;

import java.util.List;


/**
 * The type Item bean.
 */
public abstract class ItemBeanImpl extends NameableBeanImpl {
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new Item bean.
     *
     * @param bean   the bean
     * @param parent the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEAN				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ItemBeanImpl(ItemMutableBean bean, IdentifiableBean parent) {
        super(bean, parent);
    }

    /**
     * Instantiates a new Item bean.
     *
     * @param createdFrom   the created from
     * @param structureType the structure type
     * @param parent        the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ItemBeanImpl(ItemType createdFrom, SDMX_STRUCTURE_TYPE structureType, IdentifiableBean parent) {
        super(createdFrom, structureType, parent);
    }

    /**
     * Instantiates a new Item bean.
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
    public ItemBeanImpl(XmlObject createdFrom, SDMX_STRUCTURE_TYPE structureType,
                        String id,
                        String uri,
                        List<TextType> name,
                        List<TextType> description,
                        AnnotationsType annotationsType,
                        IdentifiableBean parent) {
        super(createdFrom, structureType, id, uri, name, description, annotationsType, parent);
    }

    /**
     * Instantiates a new Item bean.
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
    public ItemBeanImpl(XmlObject createdFrom,
                        SDMX_STRUCTURE_TYPE structureType,
                        String id,
                        String uri,
                        List<org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.common.TextType> name,
                        List<org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.common.TextType> description,
                        org.sdmx.resources.sdmxml.schemas.v10.xmlbeans.common.AnnotationsType annotationsType,
                        IdentifiableBean parent) {
        super(createdFrom, structureType, id, uri, name, description, annotationsType, parent);
    }
}
