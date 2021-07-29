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

import org.sdmxsource.sdmx.api.model.beans.base.AnnotableBean;
import org.sdmxsource.sdmx.api.model.beans.base.TextTypeWrapper;

import java.util.List;


/**
 * ComputationType describes a computation in a process.
 *
 * @author Matt Nelson
 */
public interface ComputationBean extends AnnotableBean {

    /**
     * The localID attribute is an optional identification for the computation within the process.
     *
     * @return local id
     */
    String getLocalId();

    /**
     * The softwarePackage attribute holds the name of the software package that is used to perform the computation.
     *
     * @return software package
     */
    String getSoftwarePackage();

    /**
     * The softwareLanguage attribute holds the coding language that the software package used to perform the computation is written in.
     *
     * @return software language
     */
    String getSoftwareLanguage();

    /**
     * The softwareVersion attribute hold the version of the software package that is used to perform that computation.
     *
     * @return software version
     */
    String getSoftwareVersion();

    /**
     * Description describe the computation in any form desired by the user
     * (these are informational rather than machine-actionable),
     * and so may be supplied in multiple, parallel-language versions,
     *
     * @return description
     */
    List<TextTypeWrapper> getDescription();

}
