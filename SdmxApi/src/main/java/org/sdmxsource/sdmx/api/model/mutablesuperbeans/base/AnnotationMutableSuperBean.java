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
package org.sdmxsource.sdmx.api.model.mutablesuperbeans.base;

import org.sdmxsource.sdmx.api.model.mutable.base.TextTypeWrapperMutableBean;

import java.io.Serializable;
import java.util.List;


/**
 * An Annotation is a piece of information that can be held against an Annotable Object.
 * <p>
 * It can be thought of as a placeholder for any extraneous information that needs to be stored, where there is no
 * other appropriate place to store this.
 *
 * @author Matt Nelson
 */
public interface AnnotationMutableSuperBean extends Serializable {

    /**
     * Returns the title of the annotation
     *
     * @return title
     */
    String getTitle();

    /**
     * Returns the type of the annotation
     *
     * @return type
     */
    String getType();

    /**
     * Returns the URL of the annotation
     *
     * @return url
     */
    String getUrl();

    /**
     * Gets texts.
     *
     * @return the texts
     */
    List<TextTypeWrapperMutableBean> getTexts();

    /**
     * Sets texts.
     *
     * @param texts the texts
     */
    void setTexts(List<TextTypeWrapperMutableBean> texts);


}
