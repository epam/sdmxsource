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

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class providing helper methods to determine if a String is a valid version.
 */
public class VersionableUtil {

    /**
     * Returns if a version String is a valid version number for the Registry.
     *
     * @param version the String to check validity of
     * @return true if the version is valid for the registry
     */
    public static boolean validVersion(String version) {
        if (version == null || version.length() == 0) {
            return false;
        }
        try {
            verifyVersion(version);
        } catch (Throwable th) {
            return false;
        }
        return true;
    }

    /**
     * Verifies a version is in the correct format.
     * The accepted format is a positive integer followed by zero or more sub-values, where a sub-value is
     * a period (.) followed by a positive integer. There may only be 10 characters in total.
     * Examples of legal values: 1 , 1.1 , 1.2.3.4.5  123.1224.
     * <p>
     * An IllegalArgumentException is thrown if the String argument cannot be represented as a number.
     *
     * @param version The string representation of the version
     * @throws IllegalArgumentException If an illegal value was supplied
     */
    private static void verifyVersion(String version) {
        if (version == null) {
            throw new IllegalArgumentException("Null version supplied.");
        }

        if (version.equals("*")) {
            return;
        }

        Pattern p = Pattern.compile("([0-9])+(\\.([0-9])+)*");
        Matcher m = p.matcher(version);
        if (!m.matches()) {
            throw new IllegalArgumentException("Illegal in version supplied '" + version + "'");
        }

        if (version.length() > 10) {
            throw new IllegalArgumentException("Supplied version contains more characters than is allowed. The version can only be 10 characters at most.");
        }
    }

    /**
     * Formats the supplied version String.
     * TODO: Review whether this method is actually required any more.
     *
     * @param version The version string to format.
     * @return The formatted version of the input.
     */
    public static String formatVersion(String version) {
        if (version == null) {
            return null;
        }

        verifyVersion(version);
        return version;
    }

    /**
     * Increment version string.
     *
     * @param version1       the version 1
     * @param majorIncrement the major increment
     * @return the string
     */
    public static String incrementVersion(String version1, boolean majorIncrement) {
        String[] v1Comps = version1.split("\\.");
        Integer majorVersion = new Integer(v1Comps[0]);
        if (majorIncrement) {
            return ++majorVersion + ".0";
        }
        Integer minorVersion = 0;
        if (v1Comps.length > 1) {
            minorVersion = new Integer(v1Comps[1]);
        }
        minorVersion = minorVersion + 1;
        return majorVersion + "." + minorVersion;
    }

    /**
     * Returns true if version1 has a higher version then version2
     *
     * @param version1 a String representing a valid version
     * @param version2 a String representing a valid version
     * @return true if version1 has a higher version then version2
     */
    public static boolean isHigherVersion(String version1, String version2) {
        verifyVersion(version1);
        verifyVersion(version2);

        String[] v1Comps = version1.split("\\.");
        String[] v2Comps = version2.split("\\.");
        int limit = (v1Comps.length > v2Comps.length) ? v2Comps.length : v1Comps.length;

        for (int i = 0; i < limit; i++) {
            BigDecimal part1int = new BigDecimal(v1Comps[i]);
            BigDecimal part2int = new BigDecimal(v2Comps[i]);
            int compare = part1int.compareTo(part2int);
            if (compare != 0) {
                return compare > 0;
            }
        }
        return v1Comps.length > v2Comps.length;
    }
}
