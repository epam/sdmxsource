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
package org.sdmxsource.sdmx.api.model.superbeans.base;

import java.io.Serializable;
import java.net.URI;
import java.util.Locale;
import java.util.Map;

/**
 * An Annotation is a piece of information that can be held against an Annotable Object.
 * <p>
 * It can be thought of as a placeholder for any extraneous information that needs to be stored,
 * when there is no other appropriate place to store the information.
 *
 * @author Matt Nelson
 */
public interface AnnotationSuperBean extends Serializable {

    /**
     * Gets title.
     *
     * @return the title of the annotation.
     */
    String getTitle();

    /**
     * Gets type.
     *
     * @return the type of the annotation.
     */
    String getType();

    /**
     * Gets uri.
     *
     * @return the URI of the annotation.
     */
    URI getUri();

    /**
     * Gets text.
     *
     * @param locale Locale to obtain the text for.
     * @return the text of the annotation in the given locale.
     */
    String getText(Locale locale);

    /**
     * Gets text.
     *
     * @return the text of the annotation in the default locale.
     */
    String getText();

    /**
     * Gets texts.
     *
     * @return a Map of all the available texts for this annotation.
     */
    Map<Locale, String> getTexts();
}
