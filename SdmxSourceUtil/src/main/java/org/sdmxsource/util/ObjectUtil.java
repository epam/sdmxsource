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
package org.sdmxsource.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * Utility class providing helper methods for generic Objects.
 */
public class ObjectUtil {

    /**
     * Returns whether all of the strings are not null and have a length of greater than zero
     * after trimming the leading and trailing whitespace.
     *
     * @param strings the strings
     * @return whether all of the strings are not null and have a length of greater than zero after trimming the leading and trailing whitespace.
     */
    public static boolean validString(String... strings) {
        if (strings == null) {
            return false;
        }
        for (String str : strings) {
            if (str == null || str.length() == 0) {
                return false;
            }
            if (str.trim().length() == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns whether at least one of the strings is not null and has a length of greater than zero
     * after trimming the leading and trailing whitespace
     *
     * @param strings the strings
     * @return whether at least one of the strings is not null and has a length of greater than zero after trimming the leading and trailing whitespace
     */
    public static boolean validOneString(String... strings) {
        if (strings == null) {
            return false;
        }
        for (String str : strings) {
            if (str != null && str.length() > 0) {
                if (str.trim().length() == 0) {
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Returns whether all of the objects are not null
     *
     * @param objs the objs
     * @return whether all of the objects are not null
     */
    public static boolean validObject(Object... objs) {
        if (objs == null) {
            return false;
        }
        for (Object obj : objs) {
            if (obj == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns true if:
     * <ul>
     *   <li>Both strings are null</li>
     *   <li>Both strings are equal</li>
     * </ul>
     *
     * @param s1 the s 1
     * @param s2 the s 2
     * @return thue if both strings are equivalent
     */
    @Deprecated
    public static boolean equivalentString(String s1, String s2) {
        return equivalent(s1, s2);
    }

    /**
     * Returns true if:
     * <ul>
     *   <li>Both objects are null</li>
     *   <li>Both objects are equal</li>
     * </ul>
     *
     * @param o1 the o 1
     * @param o2 the o 2
     * @return thue if both objects are equivalent
     */
    public static boolean equivalent(Object o1, Object o2) {
        if (o1 == null && o2 == null) {
            return true;
        }
        if (o1 == null && o2 != null) {
            return false;
        }
        if (o2 == null && o1 != null) {
            return false;
        }

        return o1.equals(o2);
    }

    /**
     * Equivalent collection boolean.
     *
     * @param c1 the c 1
     * @param c2 the c 2
     * @return the boolean
     */
    public static boolean equivalentCollection(Collection<?> c1, Collection<?> c2) {
        if (!containsAll(c1, c2)) {
            return false;
        }
        Iterator<?> it1 = c1.iterator();
        Iterator<?> it2 = c2.iterator();
        while (it1.hasNext()) {
            Object obj1 = it1.next();
            Object obj2 = it2.next();
            if (!equivalent(obj1, obj2)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Contains all boolean.
     *
     * @param c1 the c 1
     * @param c2 the c 2
     * @return true if contains all boolean.
     */
    public static boolean containsAll(Collection<?> c1, Collection<?> c2) {
        if (c1 == null && c2 == null) {
            return true;
        }
        if (c2 == null && c1 != null) {
            return false;
        }
        if (c1 == null && c2 != null) {
            return false;
        }
        if (c1.size() != c2.size()) {
            return false;
        }
        if (!c1.containsAll(c2)) {
            return false;
        }
        if (!c2.containsAll(c1)) {
            return false;
        }
        return true;
    }

    /**
     * Returns true if the collection is not null and has a size greater than zero.
     *
     * @param collection the collection
     * @return true if the collection is not null and has a size greater than zero.
     */
    public static boolean validCollection(Collection<?> collection) {
        return collection != null && collection.size() > 0;
    }

    /**
     * Does the specified Iterable only contain nulls ?
     *
     * @param array The Iterable object
     * @return true if there are only nulls in the Iterable or there are no elements in the Iterable
     */
    public static boolean isAllNulls(Iterable<?> array) {
        for (Object element : array) {
            if (element != null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns true if the array is not null and has a size greater than zero.
     *
     * @param arr the array
     * @return true if the array is not null and has a size greater than zero.
     */
    public static boolean validArray(Object[] arr) {
        return arr != null && arr.length > 0;
    }

    /**
     * Returns true if the Map is not null and has a size greater than zero.
     *
     * @param map the map
     * @return true if the Map is not null and has a size greater than zero.
     */
    public static boolean validMap(Map<?, ?> map) {
        return map != null && map.size() > 0;
    }

    /**
     * Gets boolean value.
     *
     * @param str the str
     * @return the boolean value
     */
    public static boolean getBooleanValue(String str) {
        if (str.equalsIgnoreCase("true")) {
            return true;
        }
        if (str.equalsIgnoreCase("false")) {
            return false;
        }
        throw new IllegalArgumentException("Illegal value specified: " + str);
    }
}
