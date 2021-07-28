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

import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.util.MessageResolver;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;


/**
 * The type Message decoder.
 */
public class MessageDecoder implements MessageResolver {
    private static ResourceBundleMessageSource messageSource;
    private static Set<String> baseNames = new HashSet<String>();
    private static Locale loc = new Locale("en");

    static {
        messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("exception");
        baseNames.add("exception");
    }

    /**
     * Instantiates a new Message decoder.
     */
    public MessageDecoder() {
        SdmxException.setMessageResolver(this);
    }

    /**
     * Decode message string.
     *
     * @param id   the id
     * @param args the args
     * @return the string
     */
    public static String decodeMessage(String id, Object... args) {


        if (messageSource == null) {
            return id;
        }
        return messageSource.getMessage(id, args, loc);
    }

    /**
     * Decode message default locale string.
     *
     * @param id   the id
     * @param args the args
     * @return the string
     */
    public static String decodeMessageDefaultLocale(String id, Object... args) {
        return messageSource.getMessage(id, args, loc);
    }

    /**
     * Decode message given locale string.
     *
     * @param id   the id
     * @param lang the lang
     * @param args the args
     * @return the string
     */
    public static String decodeMessageGivenLocale(String id, String lang, Object... args) {

        if (messageSource == null) {
            return id;
        }
        return messageSource.getMessage(id, args, loc);
    }

    /**
     * Gets message source.
     *
     * @return the message source
     */
    public static ResourceBundleMessageSource getMessageSource() {
        return messageSource;
    }

    /**
     * Add base name.
     *
     * @param baseName the base name
     */
    public static void addBaseName(String baseName) {
        if (MessageDecoder.messageSource == null) {
            MessageDecoder.messageSource = new ResourceBundleMessageSource();
        }
        baseNames.add(baseName);
        messageSource.setBasenames(baseNames.toArray(new String[baseNames.size()]));
        MessageDecoder.messageSource = messageSource;
    }

    @Override
    public String resolveMessage(String messageCode, Locale locale, Object... args) {
        try {
            return messageSource.getMessage(messageCode, args, locale);
        } catch (Throwable th) {
            return messageCode;
        }
    }

    /**
     * Sets basenames.
     *
     * @param basenames the basenames
     */
    public void setBasenames(Set<String> basenames) {
        for (String baseName : basenames) {
            addBaseName(baseName);
        }
    }
}
