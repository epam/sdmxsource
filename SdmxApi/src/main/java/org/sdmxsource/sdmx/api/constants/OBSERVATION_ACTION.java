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

import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;

/**
 * Enumeration for the type of observations to be returned in a data query 2.1 request.
 */
public enum OBSERVATION_ACTION {
    /**
     * Active observations, regardless of when they were added or updated will be returned
     */
    ACTIVE("Active"),
    /**
     * Newly added observations will be returned
     */
    ADDED("Added"),
    /**
     * Only updated observations will be returned
     */
    UPDATED("Updated"),
    /**
     * Only deleted observations will be returned
     */
    DELETED("Deleted");

    String obs_action;

    OBSERVATION_ACTION(String obs_action) {
        this.obs_action = obs_action;
    }

    public static OBSERVATION_ACTION parseString(String str) {
        for (OBSERVATION_ACTION currentObsAction : OBSERVATION_ACTION.values()) {
            if (currentObsAction.obs_action.equalsIgnoreCase(str)) {
                return currentObsAction;
            }
        }
        StringBuilder sb = new StringBuilder();
        String concat = "";
        for (OBSERVATION_ACTION currentObsAction : OBSERVATION_ACTION.values()) {
            sb.append(concat + currentObsAction.obs_action);
            concat = ", ";
        }
        throw new SdmxSemmanticException("Unknown Parameter " + str + " allowed parameters: " + sb.toString());
    }

    String getObservationAction() {
        return this.obs_action;
    }
}
