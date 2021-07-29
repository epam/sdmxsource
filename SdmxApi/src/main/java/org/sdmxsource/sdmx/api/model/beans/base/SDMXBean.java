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

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;

import java.io.Serializable;
import java.util.Set;


/**
 * An SDMXBean represents any SDMX structural artifact or metadata structure artifact.
 * <p>
 * All classes which inherit from SDMXBean are immutable, meaning they can not have any of their contents modified.
 * Any collections returned as a result of a method call will be copies of collections ensuring the immutability of the
 * SDMXBean is preserved.  This 'copy paradigm' is also true of composite objects returned, which are mutable, for example any object
 * of type 'java.util.Date' will be a copy of the underlying Date object contained in the SDXMBean.
 *
 * @author Matt Nelson
 */
public interface SDMXBean extends Serializable {

    /**
     * Returns the structure type of this component.
     *
     * @return structure type
     */
    SDMX_STRUCTURE_TYPE getStructureType();

    /**
     * Returns the parent that this SDMXBean belongs to
     * <p>
     * If this is a MaintainableBean, then there will be no parent to return, so will return a value of null
     *
     * @return parent parent
     */
    SDMXBean getParent();

    /**
     * Recurses up the parent hierarchy to return the first occurrence of parent of the given type that this SDMXBean belongs to
     * <p>
     * If a parent of the given type does not exist in the hierarchy, null will be returned
     *
     * @param <T>                 the type parameter
     * @param type                the type
     * @param includeThisInSearch if true then this type will be first checked to see if it is of the given type
     * @return parent parent
     */
    <T> T getParent(Class<T> type, boolean includeThisInSearch);

    /**
     * Returns a set of composite beans to this bean
     *
     * @return composites composites
     */
    Set<SDMXBean> getComposites();

    /**
     * Returns any composites of this SdmxBean of the given type
     *
     * @param <T>  the type parameter
     * @param type the type
     * @return composites composites
     */
    <T> Set<T> getComposites(Class<T> type);

    /**
     * Returns a set of cross references that are made by this bean, or by any composite bean of this bean
     *
     * @return cross references
     */
    Set<CrossReferenceBean> getCrossReferences();

    /**
     * Returns true if this SdmxBean equals the given bean in every respect (except for the validTo property of a maintainable artefact, this is not taken into consideration)
     * <p>
     * This method calls deepEquals on any SdmxBean composites.
     *
     * @param bean                   if null, then false will be returned
     * @param includeFinalProperties - if true, then this method will check every single property from this bean is  equal to the passed in bean.  If false, then this method will ignore the following properties:  <ul>    <li>Name</li>    <li>Description</li>    <li>Annotation</li>    <li>Valid From (Start Date)</li>    <li>Valid To (End Date)</li>    <li>Structure URL</li>    <li>Service URL</li>    <li>URI</li>    <li>Is External Reference</li>  </ul>
     * @return true if this SdmxBean equals the given bean in every respect (except for the validTo property of a maintainable artefact, this is not taken into consideration)
     */
    boolean deepEquals(SDMXBean bean, boolean includeFinalProperties);

}
