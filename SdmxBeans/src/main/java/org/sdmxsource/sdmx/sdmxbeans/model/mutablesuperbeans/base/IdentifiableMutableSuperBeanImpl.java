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
package org.sdmxsource.sdmx.sdmxbeans.model.mutablesuperbeans.base;

import org.sdmxsource.sdmx.api.model.beans.base.IdentifiableBean;
import org.sdmxsource.sdmx.api.model.mutablesuperbeans.base.IdentifiableMutableSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.base.IdentifiableSuperBean;

/**
 * The type Identifiable mutable super bean.
 *
 * @author Matt Nelson
 */
public class IdentifiableMutableSuperBeanImpl extends AnnotableMutableSuperBeanImpl implements IdentifiableMutableSuperBean {
    private static final long serialVersionUID = 1L;
    private String id;
    private String urn;

    /**
     * Instantiates a new Identifiable mutable super bean.
     *
     * @param identifiable the identifiable
     */
    public IdentifiableMutableSuperBeanImpl(IdentifiableSuperBean identifiable) {
        super(identifiable);
        this.id = identifiable.getId();
        this.urn = identifiable.getUrn();
    }

    /**
     * Instantiates a new Identifiable mutable super bean.
     *
     * @param identifiable the identifiable
     */
    public IdentifiableMutableSuperBeanImpl(IdentifiableBean identifiable) {
        super(identifiable);
        this.id = identifiable.getId();
        this.urn = identifiable.getUrn();
    }

    /**
     * Instantiates a new Identifiable mutable super bean.
     */
    public IdentifiableMutableSuperBeanImpl() {
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
    public String getUrn() {
        return urn;
    }

    @Override
    public void setUrn(String urn) {
        this.urn = urn;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof IdentifiableMutableSuperBeanImpl) {
            IdentifiableMutableSuperBeanImpl that = (IdentifiableMutableSuperBeanImpl) obj;
            if (this.getUrn() == null || that.getUrn() == null) {
                return false;
            }
            return this.getUrn().equals(that.getUrn());
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (getUrn() == null) {
            return super.hashCode();
        }
        return this.getUrn().hashCode();
    }
}
