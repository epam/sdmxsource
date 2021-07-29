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
package org.sdmxsource.sdmx.structureparser.builder.sdmxbeans.impl;

import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.mutable.MutableBeans;
import org.sdmxsource.sdmx.structureparser.builder.sdmxbeans.MutableBeansBuilder;
import org.sdmxsource.sdmx.util.beans.container.MutableBeansImpl;


/**
 * This Builder is realised as being able to build Mutable beans and from a collection of beans.
 */
public class MutableBeansBuilderImpl implements MutableBeansBuilder {

    /**
     * Builds Mutable beans from a collection of beans
     *
     * @param buildFrom beans to build from
     * @return Mutable bean copies
     * @throws SdmxException - If anything goes wrong during the build process
     * @since 1.0
     */
    @Override
    public MutableBeans build(SdmxBeans buildFrom) throws SdmxException {
        MutableBeans mutableBeans = new MutableBeansImpl();
        for (MaintainableBean currentMaintainable : buildFrom.getAllMaintainables()) {
            mutableBeans.addIdentifiable(currentMaintainable.getMutableInstance());
        }
        return mutableBeans;
    }
}
