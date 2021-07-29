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

import java.util.List;
import java.util.Set;

/**
 * An AnnotableBean Bean is one which can contain annotations
 * <p>
 * This is an immutable bean - this bean can not be modified
 *
 * @author Matt Nelson
 */
public interface AnnotableBean extends SdmxStructureBean {

    /**
     * Returns the list of annotations
     * <p>
     * <b>NOTE</b>The list is a copy so modify the returned list will not
     * be reflected in the AnnotableBean instance
     *
     * @return annotations annotations
     */
    List<AnnotationBean> getAnnotations();

    /**
     * Returns true if this bean has an annotation with the given type
     *
     * @param annoationType the annoation type
     * @return boolean
     */
    boolean hasAnnotationType(String annoationType);

    /**
     * Returns the annotations with the given type, returns an empty Set is no annotations exist that have a type which
     * matches the given String
     *
     * @param type the type
     * @return annotations by type
     */
    Set<AnnotationBean> getAnnotationsByType(String type);

    /**
     * Returns the annotations with the given title, returns an empty Set is no annotations exist that have a type which
     * matches the given String
     *
     * @param title the title
     * @return annotations by title
     */
    Set<AnnotationBean> getAnnotationsByTitle(String title);

}
