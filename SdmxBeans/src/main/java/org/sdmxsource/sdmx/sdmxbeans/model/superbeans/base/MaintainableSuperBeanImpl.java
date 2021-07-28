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
package org.sdmxsource.sdmx.sdmxbeans.model.superbeans.base;

import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.superbeans.base.MaintainableSuperBean;

/**
 * The type Maintainable super bean.
 */
public abstract class MaintainableSuperBeanImpl extends NameableSuperBeanImpl implements MaintainableSuperBean {
    private static final long serialVersionUID = 1L;

    private String agencyId;
    private String version;
    private boolean isFinal;
    private MaintainableBean builtFrom;

    /**
     * Instantiates a new Maintainable super bean.
     *
     * @param maintainable the maintainable
     */
    public MaintainableSuperBeanImpl(MaintainableBean maintainable) {
        super(maintainable);
        //TODO AgencyId should be full agency
        this.builtFrom = maintainable;
        this.agencyId = maintainable.getAgencyId();
        this.version = maintainable.getVersion();
        this.isFinal = maintainable.isFinal().isTrue();
    }

    @Override
    public boolean isFinal() {
        return isFinal;
    }

    @Override
    public String getAgencyId() {
        return agencyId;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public MaintainableBean getBuiltFrom() {
        return builtFrom;
    }
}
