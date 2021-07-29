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

import java.util.Set;


/**
 * An Annotable Object is one which can hold annotations against it.
 *
 * @author Matt Nelson
 */
public interface AnnotableSuperBean extends SuperBean {

    /**
     * Returns a list of annotations that exist for this Annotable Object
     *
     * @return annotations
     */
    Set<AnnotationSuperBean> getAnnotations();

    /**
     * Returns an annotation with the given type, this returns null if no annotation exists with
     * the given type
     *
     * @param type the type
     * @return annotation by type
     */
    AnnotationSuperBean getAnnotationByType(String type);

    /**
     * Returns an annotations with the given title, this returns null if no annotation exists with
     * the given type
     *
     * @param title the title
     * @return annotation by title
     */
    Set<AnnotationSuperBean> getAnnotationByTitle(String title);

    /**
     * Returns an annotations with the given url, this returns null if no annotation exists with
     * the given type
     *
     * @param url the url
     * @return annotation by url
     */
    Set<AnnotationSuperBean> getAnnotationByUrl(String url);


    /**
     * Returns true if annotations exist for this Annotable Object
     *
     * @return boolean
     */
    boolean hasAnnotations();

}
