/**
 * Copyright (c) 2013 Metadata Technology Ltd.
 * <p>
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the GNU Lesser General Public License v 3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * This file is part of the SDMX Component Library.
 * <p>
 * The SDMX Component Library is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 * <p>
 * The SDMX Component Library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser
 * General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License
 * along with The SDMX Component Library If not, see
 * http://www.gnu.org/licenses/lgpl.
 * <p>
 * Contributors:
 * Metadata Technology - initial API and implementation
 */
/**
 *
 */
package org.sdmxsource.sdmx.sdmxbeans.model.superbeans.base;

import org.sdmxsource.sdmx.api.model.beans.base.IdentifiableBean;
import org.sdmxsource.sdmx.api.model.superbeans.base.IdentifiableSuperBean;

/**
 * The type Identifiable super bean.
 *
 * @author Matt Nelson
 */
public abstract class IdentifiableSuperBeanImpl extends AnnotableSuperBeanImpl implements IdentifiableSuperBean {
    private static final long serialVersionUID = 1L;

    private String id;
    private String urn;

    /**
     * Instantiates a new Identifiable super bean.
     *
     * @param identifiable the identifiable
     */
    public IdentifiableSuperBeanImpl(IdentifiableBean identifiable) {
        super(identifiable);
        id = identifiable.getId();
        urn = identifiable.getUrn();
    }


    /* (non-Javadoc)
     * @see org.sdmxsource.dataweb.external.model.Identifiable#getId()
     */
    @Override
    public String getId() {
        return this.id;
    }

    /* (non-Javadoc)
     * @see org.sdmxsource.dataweb.external.model.Identifiable#getUrn()
     */
    @Override
    public String getUrn() {
        return this.urn;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof IdentifiableSuperBean) {
            IdentifiableSuperBean that = (IdentifiableSuperBean) obj;
            return this.urn.equals(that.getUrn());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.urn.hashCode();
    }

    @Override
    public String toString() {
        return this.urn;
    }
}
