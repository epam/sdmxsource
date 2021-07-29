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
package org.sdmxsource.sdmx.api.model.submissionresponse;

import org.sdmxsource.sdmx.api.model.ErrorList;
import org.sdmxsource.sdmx.api.model.beans.registry.RegistrationBean;

/**
 * Response from a SDMX Registry for a Registration submission
 */
public interface SubmitRegistrationResponse {

    /**
     * Gets registration.
     *
     * @return the registration
     */
    RegistrationBean getRegistration();

    /**
     * Returns true if getErrorList() returns a not null ErrorList, and the returned ErrorList is of type error (not warning)
     *
     * @return error
     */
    boolean isError();

    /**
     * Returns the list of errors, returns null if there were no errors for this response (if it was a success)
     *
     * @return error list
     */
    ErrorList getErrorList();

}
