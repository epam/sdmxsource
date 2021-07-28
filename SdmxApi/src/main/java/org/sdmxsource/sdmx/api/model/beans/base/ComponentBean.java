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

import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;

/**
 * A component is a bean which contains reference to a codelist and a concept
 * <p>
 * The concept reference is mandatory and the codelist reference is optional
 *
 * @author Matt Nelson
 */
public interface ComponentBean extends IdentifiableBean {

    /**
     * Returns the concept reference, this method will not return null as concept reference is mandatory
     *
     * @return concept ref
     */
    CrossReferenceBean getConceptRef();

    /**
     * Returns the representation for this component, returns null if there is no representation
     *
     * @return representation
     */
    RepresentationBean getRepresentation();

    /**
     * Returns true if this identifier component has coded representation
     *
     * @return boolean
     */
    boolean hasCodedRepresentation();
}
