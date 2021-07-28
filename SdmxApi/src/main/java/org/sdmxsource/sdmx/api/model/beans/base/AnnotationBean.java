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
package org.sdmxsource.sdmx.api.model.beans.base;

import java.net.URI;
import java.util.List;

/**
 * Contains annotation information
 * <p>
 * Provides for non-documentation notes and annotations to be embedded in data and structure messages.
 * It provides optional fields for providing a title, a type description, a URI, and the text of the annotation.
 * <p>
 * This is an immutable bean - this bean can not be modified
 *
 * @author Matt Nelson
 */
public interface AnnotationBean extends SDMXBean {

    /**
     * Returns the id of the annotation, this is a free text field.
     *
     * @return id id
     */
    String getId();

    /**
     * Returns the title of the annotation, this is a free text field.
     *
     * @return title title
     */
    String getTitle();

    /**
     * Returns the type of annotation, this is a free text field.
     * <p>
     * Is used to distinguish between annotations designed to support various uses.
     * The types are not enumerated, as these can be specified by the user or creator of the annotations.
     * The definitions and use of annotation types should be documented by their creator.
     *
     * @return type type
     */
    String getType();

    /**
     * This is a language-specific string which holds the text of the annotation.
     *
     * <b>NOTE</b>The list is a copy so modify the returned list will not
     * be reflected in the AnnotableBean instance.
     *
     * @return text text
     */
    List<TextTypeWrapper> getText();

    /**
     * Returns a URI - typically a URL - which points to an external resource which may contain or supplement the annotation.
     * <p>
     * If a specific behaviour is desired, an annotation type should be defined which specifies the use of this field more exactly.
     *
     * @return uri uri
     */
    URI getUri();

}
