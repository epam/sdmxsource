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

import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.ResolutionSettings.RESOLVE_CROSS_REFERENCES;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.beans.reference.MaintainableRefBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.query.RESTStructureQuery;

import java.util.Set;


/**
 * Manages the retrieval of structures.
 * <p>
 * The SdmxBeanRetrievalManager contains the means to return beans by simple search criteria,
 * based on a reference structure,
 */
public interface SdmxBeanRetrievalManager extends IdentifiableRetrievalManager {


    /**
     * Get all the maintainables that match the RESTStructureQuery
     *
     * @param restquery the restquery
     * @return maintainables
     */
    SdmxBeans getMaintainables(RESTStructureQuery restquery);

    /**
     * Returns a SdmxBeans container containing all the MaintainableBeans that match
     * the query parameters as defined by the StructureReferenceBean.
     *
     * @param sRef                   The StructureReferenceBean which must not be null
     * @param resolveCrossReferences either 'do not resolve', 'resolve all' or 'resolve all excluding agencies'. If not set to 'do not resolve' then all the structures that are referenced by the resulting structures are also returned  (and also their children).  This will be equivalent to descendants for a RESTful query.
     * @return An SdmxBeans containing the appropriate Maintainable Beans.
     * @throws IllegalArgumentException if the StructureReferenceBean is null
     */
    SdmxBeans getSdmxBeans(StructureReferenceBean sRef, RESOLVE_CROSS_REFERENCES resolveCrossReferences);

    /**
     * Obtains a MaintainableBean identified by the StructureReferenceBean.
     * This is expected to only return a single MaintainableBean and will throw
     * and Exception if more then one are returned.
     * <p>
     * Null will be returned if there are no matches to the query
     *
     * @param sRef identifies the structure type, and the identifiers of the structure, can include widcarded values (null indicates a wildcard).  If version information is missing, then  latest version is assumed
     * @return maintainable bean
     * @throws SdmxException if more then one structure matches the query
     */
    MaintainableBean getMaintainableBean(StructureReferenceBean sRef);

    /**
     * Obtains a MaintainableBean identified by the StructureReferenceBean.
     * This is expected to only return a single MaintainableBean and will throw
     * and Exception if more then one are returned.
     * <p>
     * Null will be returned if there are no matches to the query
     *
     * @param sRef         identifies the structure type, and the identifiers of the structure, can include widcarded values (null indicates a wildcard).  If version information is missing, then  latest version is assumed
     * @param returnStub   if true then the stub bean will be returned instead of the full structure
     * @param returnLatest if true then the latest version is returned, regardless of whether version information is supplied
     * @return maintainable bean
     * @throws SdmxException if more then one structure matches the query
     */
    MaintainableBean getMaintainableBean(StructureReferenceBean sRef, boolean returnStub, boolean returnLatest);

    /**
     * Returns the Maintainable that is of the given type, determined by T, and matches the reference parameters in the MaintainableRef.
     *
     * @param <T>           the type parameter
     * @param structureType identifying the Class type to return
     * @param ref           contains the parameters that must match on the returned structure.   If version information is missing, then latest version is assumed
     * @return maintainable bean
     * @throws SdmxException if more then one structure matches the query
     */
    <T extends MaintainableBean> T getMaintainableBean(Class<T> structureType, MaintainableRefBean ref);

    /**
     * Returns the Maintainable that is of the given type, determined by T, and matches the reference parameters in the MaintainableRef.
     *
     * @param <T>           the type parameter
     * @param structureType identifying the Class type to return
     * @param ref           contains the parameters that must match on the returned structure.   If version information is missing, then latest version is assumed
     * @param returnStub    if true then the stub bean will be returned instead of the full structure
     * @param returnLatest  if true then the latest version is returned, regardless of whether version information is supplied
     * @return maintainable bean
     * @throws SdmxException if more then one structure matches the query
     */
    <T extends MaintainableBean> T getMaintainableBean(Class<T> structureType, MaintainableRefBean ref, boolean returnStub, boolean returnLatest);

    /**
     * Obtains a set of all MaintainableBeans of type T
     * <p>
     * An empty Set will be returned if there are no matches to the query
     *
     * @param <T>           the type parameter
     * @param structureType identifies the structure of type to return, if argument is null or Maintainable.class then all maintainables will be returned
     * @return maintainable beans
     */
    <T extends MaintainableBean> Set<T> getMaintainableBeans(Class<T> structureType);

    /**
     * Obtains a set of MaintainableBeans of type T that match the reference parameters in the MaintainableRef argument.
     * <p>
     * An empty Set will be returned if there are no matches to the query
     *
     * @param <T>           the type parameter
     * @param structureType identifies the structure type
     * @param ref           contains the identifiers of the structures to returns, can include widcarded values (null indicates a wildcard).
     * @return maintainable beans
     */
    <T extends MaintainableBean> Set<T> getMaintainableBeans(Class<T> structureType, MaintainableRefBean ref);

    /**
     * Obtains a set of MaintainableBeans of type T that match the reference parameters in the MaintainableRef argument.
     * <p>
     * An empty Set will be returned if there are no matches to the query
     *
     * @param <T>           the type parameter
     * @param structureType identifies the structure type
     * @param ref           contains the identifiers of the structures to returns, can include widcarded values (null indicates a wildcard).
     * @param returnLatest  if true then the latest version is returned, regardless of whether version information is supplied
     * @param returnStub    if true then the stub bean will be returned instead of the full structure
     * @return maintainable beans
     */
    <T extends MaintainableBean> Set<T> getMaintainableBeans(Class<T> structureType, MaintainableRefBean ref, boolean returnLatest, boolean returnStub);

}
