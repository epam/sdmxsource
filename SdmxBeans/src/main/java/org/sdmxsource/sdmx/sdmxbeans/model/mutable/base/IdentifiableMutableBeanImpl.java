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
package org.sdmxsource.sdmx.sdmxbeans.model.mutable.base;

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.SdmxReader;
import org.sdmxsource.sdmx.api.model.beans.base.IdentifiableBean;
import org.sdmxsource.sdmx.api.model.mutable.base.IdentifiableMutableBean;

/**
 * The type Identifiable mutable bean.
 */
public abstract class IdentifiableMutableBeanImpl extends AnnotableMutableBeanImpl implements IdentifiableMutableBean {
    private static final long serialVersionUID = 1L;
    private String id;
    private String uri;
    private String urn;

    /**
     * Instantiates a new Identifiable mutable bean.
     *
     * @param structureType the structure type
     */
    public IdentifiableMutableBeanImpl(SDMX_STRUCTURE_TYPE structureType) {
        super(structureType);
    }

    /**
     * Instantiates a new Identifiable mutable bean.
     *
     * @param bean the bean
     */
    public IdentifiableMutableBeanImpl(IdentifiableBean bean) {
        super(bean);
        setId(bean.getId());
        if (bean.getUri() != null) {
            this.uri = bean.getUri().toString();
        }
        setUrn(bean.getUrn());
    }

    /**
     * Build identifiable attributes.
     *
     * @param reader the reader
     */
    protected void buildIdentifiableAttributes(SdmxReader reader) {
        id = reader.getAttributeValue("id", true);
        if (reader.getAttributeValue("uri", false) != null) {
            setUri(reader.getAttributeValue("uri", false));
        }
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getUri() {
        return uri;
    }

    @Override
    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public String getUrn() {
        return urn;
    }

    /**
     * Sets urn.
     *
     * @param urn the urn
     */
    public void setUrn(String urn) {
        this.urn = urn;
    }

}
