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
package org.sdmxsource.util.resourceBundle;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * The type Properties to map.
 */
public class PropertiesToMap {

    /**
     * Creates a Map of key value paris from a Properties object
     *
     * @param properties the properties
     * @return map map
     */
    public static Map<String, String> createMap(Properties properties) {
        Map<String, String> returnMap = new HashMap<String, String>();
        for (Object currentKey : properties.keySet()) {
            if (currentKey instanceof String) {
                String key = (String) currentKey;
                returnMap.put(key, properties.getProperty(key));
            }
        }
        return returnMap;
    }

}
