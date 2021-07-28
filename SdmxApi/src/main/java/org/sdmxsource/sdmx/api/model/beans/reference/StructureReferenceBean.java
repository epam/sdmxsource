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
package org.sdmxsource.sdmx.api.model.beans.reference;

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.base.IdentifiableBean;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;

import java.io.Serializable;


/**
 * A StructureReferenceBean is used to reference an identifiable artifact using a combination of reference parameters.
 * <p>
 * If all reference parameters are present then the reference is for a single identifiable item, if any are missing, then this represents
 * a wildcard parameter / or ALL.
 * <p>
 * SDMX_STRUCTURE_TYPE is a mandatory reference parameter, all others are optional
 *
 * @author Matt Nelson
 */
public interface StructureReferenceBean extends Serializable, MaintainableRefBean {

    /**
     * Creates a copy of this bean
     *
     * @return structure reference bean
     */
    StructureReferenceBean createCopy();

    /**
     * Returns the SDMX Structure that is being referenced at the top level (maintainable level) by this reference bean.
     * <p>
     * Any child references will further refine the structure type that is being referenced.
     *
     * @return maintainable structure type
     */
    SDMX_STRUCTURE_TYPE getMaintainableStructureType();

    /**
     * Returns the SDMX Structure that is being referenced by this reference bean.
     *
     * @return target reference
     */
    SDMX_STRUCTURE_TYPE getTargetReference();

    /**
     * Returns the child artifact that is being referenced, returns null if there is no child artifact
     *
     * @return child reference
     */
    IdentifiableRefBean getChildReference();

    /**
     * Returns a string array of any child ids (obtained from getChildReference()), returns null if getChildReference() is null
     *
     * @return identifiable ids
     */
    String[] getIdentifiableIds();

    /**
     * Returns the full id of the identifiable reference.  This is a dot '.' separated id which consists of the parent identifiable ids and the target.
     * If the referenced structure is a Sub-Agency then this will include the parent Agency ids, and the id of the target agency.  If this reference
     * is referencing a maintainable then null will be returned.  If there is only one child identifiable, then the id of that identifable will be returned, with no '.' seperators.
     *
     * @return full id
     */
    String getFullId();

    /**
     * Returns the reference parameters as a maintainable reference
     *
     * @return maintainable reference
     */
    MaintainableRefBean getMaintainableReference();

    /**
     * Returns the maintainable id attribute
     *
     * @return the agency id
     */
    String getAgencyId();

    /**
     * Returns true if there is an agency Id set
     *
     * @return has agency id
     */
    boolean hasAgencyId();

    /**
     * Returns the maintainable id attribute
     *
     * @return the maintainable id
     */
    String getMaintainableId();

    /**
     * Returns true if there is a maintainable id set
     *
     * @return the maintainable id
     */
    boolean hasMaintainableId();

    /**
     * Returns the version attribute
     *
     * @return the version id
     */
    String getVersion();

    /**
     * Returns true if there is a value for version set
     *
     * @return has version
     */
    boolean hasVersion();

    /**
     * Returns true if getChildReference returns a value
     *
     * @return has child reference
     */
    boolean hasChildReference();

    /**
     * Returns the URN of the target structure that is being referenced, returns null if there is no URN available
     *
     * @return the target urn
     */
    String getTargetUrn();

    /**
     * Returns true if getTargetUrn returns a value
     *
     * @return has the target urn
     */
    boolean hasTargetUrn();

    /**
     * Returns the URN of the maintainable structure that is being referenced, returns null if there is no URN available
     *
     * @return the maintainable urn
     */
    String getMaintainableUrn();

    /**
     * Returns true if getMaintainableUrn returns a value
     *
     * @return has the maintainable urn
     */
    boolean hasMaintainableUrn();

    /**
     * Returns true if this reference matches the MaintainableBean, or one of its indentifiable composites.
     * <p>
     * This bean does not require all reference parameters to be set, this method will return true if all the parameters that are set match
     * the bean passed in.  If this reference is referencing an Identifiable composite, then the maintainable's identifiable composites will also be
     * checked to determine if this is a match.
     *
     * @param reference the reference
     * @return is match
     */
    boolean isMatch(MaintainableBean reference);

    /**
     * Returns the matched Identifiable Bean from this reference, returns the original Maintainable if this is a Maintainable reference that matches the maintainable.  Returns null if no match
     * is found
     *
     * @param maintainbleBean the maintainble bean
     * @return the match
     */
    IdentifiableBean getMatch(MaintainableBean maintainbleBean);


}
