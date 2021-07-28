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
 * Defines all the actions that are available in the header of a SDMX message
 *
 * @author Matt Nelson
 */
public enum DATASET_ACTION {
    /**
     * Message is of type Append
     */
    APPEND("Append"),
    /**
     * Message is of type Replace
     */
    REPLACE("Replace"),
    /**
     * Message is of type Delete
     */
    DELETE("Delete"),
    /**
     * Message is of type Information
     */
    INFORMATION("Information");

    private String action;

    private DATASET_ACTION(String action) {
        this.action = action;
    }

    /**
     * From an action (Append, Update, Replace, Delete, or Information) will return the DATASET_ACTION enum equivalent,
     * <p>
     * The input string is case insensitive, and 'update' treats as Append.
     *
     * @param action the action
     * @return action action
     */
    public static DATASET_ACTION getAction(String action) {
        if (action.equalsIgnoreCase("update")) {
            return APPEND;
        }
        for (DATASET_ACTION currentAction : DATASET_ACTION.values()) {
            if (currentAction.action.equalsIgnoreCase(action)) {
                return currentAction;
            }
        }
        String concat = "";
        StringBuilder sb = new StringBuilder();
        for (DATASET_ACTION currentArgument : DATASET_ACTION.values()) {
            sb.append(concat);
            sb.append(currentArgument.action);
            concat = ", ";
        }
        throw new RuntimeException("Unknown Dataset Action, allowed values are '" + sb.toString() + "'");
    }

    /**
     * Returns either Append,  Replace, Delete, or Information as a String.
     *
     * @return action action
     */
    public String getAction() {
        return action;
    }
}
