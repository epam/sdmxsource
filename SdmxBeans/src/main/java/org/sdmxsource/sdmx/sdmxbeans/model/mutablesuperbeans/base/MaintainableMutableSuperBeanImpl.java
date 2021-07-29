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
package org.sdmxsource.sdmx.sdmxbeans.model.mutablesuperbeans.base;

import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.mutablesuperbeans.base.MaintainableMutableSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.base.MaintainableSuperBean;

/**
 * The type Maintainable mutable super bean.
 */
public abstract class MaintainableMutableSuperBeanImpl extends NameableMutableSuperBeanImpl implements MaintainableMutableSuperBean {
    private static final long serialVersionUID = 1L;

    private String agencyId;
    private String version;
    private boolean isFinal;
    private boolean isStub;

    /**
     * Instantiates a new Maintainable mutable super bean.
     *
     * @param maintainable the maintainable
     */
    public MaintainableMutableSuperBeanImpl(MaintainableSuperBean maintainable) {
        super(maintainable);
        this.agencyId = maintainable.getAgencyId();
        this.version = maintainable.getVersion();
        this.isFinal = maintainable.isFinal();
    }

    /**
     * Instantiates a new Maintainable mutable super bean.
     *
     * @param maintainable the maintainable
     */
    public MaintainableMutableSuperBeanImpl(MaintainableBean maintainable) {
        super(maintainable);
        this.agencyId = maintainable.getAgencyId();
        this.version = maintainable.getVersion();
        this.isFinal = maintainable.isFinal().isTrue();
    }

    /**
     * Instantiates a new Maintainable mutable super bean.
     */
    public MaintainableMutableSuperBeanImpl() {
    }

    @Override
    public String getAgencyId() {
        return agencyId;
    }

    @Override
    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public boolean isFinal() {
        return isFinal;
    }

    /**
     * Gets final.
     *
     * @return the final
     */
    public boolean getFinal() {
        return isFinal;
    }

    @Override
    public void setFinal(boolean isFinal) {
        this.isFinal = isFinal;
    }

    /**
     * Is stub boolean.
     *
     * @return the boolean
     */
    public boolean isStub() {
        return isStub;
    }

    /**
     * Sets stub.
     *
     * @param isStub the is stub
     */
    public void setStub(boolean isStub) {
        this.isStub = isStub;
    }

}
