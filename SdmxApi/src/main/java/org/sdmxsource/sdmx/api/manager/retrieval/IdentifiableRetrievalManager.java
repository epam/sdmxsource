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
package org.sdmxsource.sdmx.api.manager.retrieval;

import org.sdmxsource.sdmx.api.exception.CrossReferenceException;
import org.sdmxsource.sdmx.api.model.beans.base.AgencyBean;
import org.sdmxsource.sdmx.api.model.beans.base.IdentifiableBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;

import java.util.Set;

/**
 * Retrieves Identifiable structures from CrossReferenceBean
 *
 * @author Matt Nelson
 */
public interface IdentifiableRetrievalManager {

    /**
     * Returns the agency with the given Id, if the Agency is a child of another Agency (other then SDMX), then it should be a dot separated id, for
     * example DEMO.SUBDEMO
     *
     * @param id the id
     * @return agency, or null if none could be found with the supplied id
     */
    AgencyBean getAgency(String id);

    /**
     * Resolves an identifiable reference
     *
     * @param crossReferenceBean the cross reference bean
     * @return identifiable bean
     * @throws CrossReferenceException if the CrossReferenceBean could not resolve to an IdentifiableBean
     */
    IdentifiableBean getIdentifiableBean(CrossReferenceBean crossReferenceBean) throws CrossReferenceException;

    /**
     * Returns a set of identifiables that match the structure reference, which may be a full or partial reference to a maintainable or identifiable
     *
     * @param sRef the s ref
     * @return identifiable beans
     */
    Set<? extends IdentifiableBean> getIdentifiableBeans(StructureReferenceBean sRef);

    /**
     * Resolves an reference to a bean of type T, this will return the bean of the given type, throwing an exception if either the
     * bean can not be resolved or if it is not of type T
     *
     * @param <T>                the type parameter
     * @param crossReferenceBean the cross reference bean
     * @param structureType      the structure type
     * @return identifiable bean
     * @throws CrossReferenceException if the CrossReferenceBean could not resolve to an IdentifiableBean
     */
    <T> T getIdentifiableBean(CrossReferenceBean crossReferenceBean, Class<T> structureType) throws CrossReferenceException;

    /**
     * Resolves an reference to a bean of type T, this will return the bean of the given type, throwing an exception if e
     * bean is not of type T
     *
     * @param <T>                the type parameter
     * @param crossReferenceBean the cross reference bean
     * @param structureType      the structure type
     * @return identifiable bean
     * @throws CrossReferenceException the cross reference exception
     */
    <T> T getIdentifiableBean(StructureReferenceBean crossReferenceBean, Class<T> structureType) throws CrossReferenceException;
}
