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
package org.sdmxsource.sdmx.util.exception;

import org.sdmxsource.sdmx.api.constants.ARTIFACT_TYPE;
import org.sdmxsource.sdmx.api.constants.DATASET_ACTION;
import org.sdmxsource.sdmx.api.exception.SdmxException;

/**
 * The type Parse exception.
 */
public class ParseException extends SdmxException {

    private static final long serialVersionUID = -4367630815745212302L;

    private DATASET_ACTION action;
    private boolean isQuery;
    private ARTIFACT_TYPE artifact;

    /**
     * Instantiates a new Parse exception.
     *
     * @param th       the th
     * @param action   the action
     * @param isQuery  the is query
     * @param artifact the artifact
     * @param args     the args
     */
    public ParseException(Throwable th, DATASET_ACTION action, boolean isQuery, ARTIFACT_TYPE artifact, Object... args) {
        super(th, null, args);
        this.action = action;
        this.isQuery = isQuery;
        this.artifact = artifact;
    }

    /**
     * Gets action.
     *
     * @return the action
     */
    public DATASET_ACTION getAction() {
        return action;
    }

    /**
     * Is query boolean.
     *
     * @return the boolean
     */
    public boolean isQuery() {
        return isQuery;
    }

    /**
     * Gets artifact.
     *
     * @return the artifact
     */
    public ARTIFACT_TYPE getArtifact() {
        return artifact;
    }

    @Override
    public String getErrorType() {
        return "Parse Exception";
    }
}
