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
package org.sdmxsource.sdmx.api.constants;

/**
 * Contains a high level set of SDMX Artifact types
 *
 * @author Matt Nelson
 */
public enum ARTIFACT_TYPE {
    /**
     * Relates to SDMX Data Messages
     */
    DATA,
    /**
     * Relates to SDMX Data Metadata Set Messages
     */
    METADATA,
    /**
     * Relates to SDMX Provision Messages (only relevant in SDMX v2.0)
     */
    PROVISION,
    /**
     * Relates to SDMX Registration Messages (Could be Submission or Query Response)
     */
    REGISTRATION,
    /**
     * Relates to SDMX Structure Messages including Registry Interface submissions and query structure responses
     */
    STRUCTURE,
    /**
     * Relates to SDMX Notification Messages
     */
    NOTIFICATION,
    /**
     * Relates to SDMX Subscription Messages - either submission or query response
     */
    SUBSCRIPTION;
}
