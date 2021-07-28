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
package org.sdmxsource.sdmx.api.model.beans.process;

import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.mutable.process.ProcessMutableBean;

import java.net.URL;
import java.util.List;


/**
 * Represents an SDMX Process
 *
 * @author Matt Nelson
 */
public interface ProcessBean extends MaintainableBean {

    /**
     * Returns the child Process Steps as a copy of the underlying list.
     * <p>
     * Returns an empty list if no child process steps exist
     *
     * @return process steps
     */
    List<ProcessStepBean> getProcessSteps();

    /**
     * Returns a stub reference of itself.
     * <p>
     * A stub bean only contains Maintainable and Identifiable Attributes, not the composite Objects that are
     * contained within the Maintainable
     *
     * @param actualLocation the URL indicating where the full structure can be returned from
     * @param isServiceUrl   if true this URL will be present on the serviceURL attribute, otherwise it will be treated as a structureURL attribute
     * @return the stub
     */
    @Override
    ProcessBean getStub(URL actualLocation, boolean isServiceUrl);

    /**
     * Returns a representation of itself in a bean which can be modified, modifications to the mutable bean
     * are not reflected in the instance of the MaintainableBean.
     *
     * @return the mutable instance
     */
    @Override
    ProcessMutableBean getMutableInstance();
}
