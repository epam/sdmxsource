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
package org.sdmxsource.util.random;

import java.util.Date;
import java.util.Random;

/**
 * The type Random util.
 */
public class RandomUtil {
    private static final Random random = new Random(new Date().getTime());
    private static String passwordSet = "1234567890qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";

    /**
     * Generate random string string.
     *
     * @return the string
     */
    public static String generateRandomString() {
        long r1 = random.nextLong();
        long r2 = random.nextLong();
        String hash1 = Long.toHexString(r1);
        String hash2 = Long.toHexString(r2);
        return hash1 + hash2;
    }

    /**
     * Generate random password string.
     *
     * @param length the length of resulting string
     * @return the random password string
     */
    public static String generateRandomPassword(int length) {
        Random r = new Random();
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < length; i++) {
            b.append(passwordSet.charAt(r.nextInt(passwordSet.length())));
        }
        return b.toString();
    }

}
