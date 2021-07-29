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
package org.sdmxsource.sdmx.util.beans;


import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.IdentifiableBean;
import org.sdmxsource.sdmx.api.model.beans.conceptscheme.ConceptSchemeBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.util.beans.reference.CrossReferenceBeanImpl;
import org.sdmxsource.util.ObjectUtil;


/**
 * The type Concept ref util.
 */
public class ConceptRefUtil {

    /**
     * Gets concept id.
     *
     * @param sRef the s ref
     * @return the concept id
     */
    public static String getConceptId(StructureReferenceBean sRef) {
        if (sRef.getTargetReference() == SDMX_STRUCTURE_TYPE.CONCEPT) {
            return sRef.getChildReference().getId();
        }
        throw new SdmxSemmanticException("Expecting a Concept Reference got : " + sRef.getTargetReference().getType());
    }

    /**
     * Build concept ref cross reference bean.
     *
     * @param referencedFrom       the referenced from
     * @param conceptSchemeAgency  the concept scheme agency
     * @param conceptSchemeId      the concept scheme id
     * @param conceptSchemeVersion the concept scheme version
     * @param conceptAgency        the concept agency
     * @param conceptId            the concept id
     * @return the cross reference bean
     */
    public static CrossReferenceBean buildConceptRef(
            IdentifiableBean referencedFrom,
            String conceptSchemeAgency,
            String conceptSchemeId,
            String conceptSchemeVersion,
            String conceptAgency,
            String conceptId) {
        boolean isFreeStanding = false;
        if (!ObjectUtil.validString(conceptSchemeId)) {
            isFreeStanding = true;
        }
        if (ObjectUtil.validOneString(conceptId, conceptSchemeAgency, conceptSchemeId, conceptSchemeVersion)) {
            if (!ObjectUtil.validString(conceptSchemeAgency)) {
                conceptSchemeAgency = conceptAgency;
            }
            if (isFreeStanding) {
                return new CrossReferenceBeanImpl(referencedFrom, conceptSchemeAgency, ConceptSchemeBean.DEFAULT_SCHEME_ID, ConceptSchemeBean.DEFAULT_SCHEME_VERSION, SDMX_STRUCTURE_TYPE.CONCEPT, conceptId);
            }

            return new CrossReferenceBeanImpl(referencedFrom, conceptSchemeAgency, conceptSchemeId, conceptSchemeVersion, SDMX_STRUCTURE_TYPE.CONCEPT, conceptId);
        }
        //TODO Exception
        throw new IllegalArgumentException("Concept Reference missing parameters");
    }
}
