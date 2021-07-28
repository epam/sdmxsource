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
package org.sdmxsource.sdmx.sdmxbeans.model.submissionresponse;

import org.sdmxsource.sdmx.api.model.ErrorList;
import org.sdmxsource.sdmx.api.model.beans.registry.RegistrationBean;
import org.sdmxsource.sdmx.api.model.submissionresponse.SubmitRegistrationResponse;

/**
 * The type Submit registration response.
 */
public class SubmitRegistrationResponseImpl implements SubmitRegistrationResponse {
    private RegistrationBean registration;
    private ErrorList errors;


    /**
     * Instantiates a new Submit registration response.
     *
     * @param registration the registration
     * @param errors       the errors
     */
    public SubmitRegistrationResponseImpl(RegistrationBean registration, ErrorList errors) {
        this.registration = registration;
        this.errors = errors;
    }

    @Override
    public RegistrationBean getRegistration() {
        return registration;
    }

    @Override
    public boolean isError() {
        return errors != null;
    }

    @Override
    public ErrorList getErrorList() {
        return errors;
    }
}
