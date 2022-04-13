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
package org.sdmxsource.util.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The type Logging util.
 */
public class LoggingUtil {

    /**
     * Debug.
     *
     * @param log     the log
     * @param message the message
     */
    public static void debug(Logger log, String message) {
        if (log != null && log.isDebugEnabled()) {
            log.debug(message);
        }
    }

    /**
     * Error.
     *
     * @param log     the log
     * @param message the message
     */
    public static void error(Logger log, String message) {
        if (log != null) {
            log.error(message);
        }
    }

    /**
     * Warn.
     *
     * @param log     the log
     * @param message the message
     */
    public static void warn(Logger log, String message) {
        if (log != null) {
            log.warn(message);
        }
    }

    /**
     * Info.
     *
     * @param log     the log
     * @param message the message
     */
    public static void info(Logger log, String message) {
        if (log != null && log.isInfoEnabled()) {
            log.info(message);
        }
    }

}
