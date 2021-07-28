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
package org.sdmxsource.sdmx.sdmxbeans.model.superbeans.conceptscheme;

import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.beans.conceptscheme.ConceptBean;
import org.sdmxsource.sdmx.api.model.beans.conceptscheme.ConceptSchemeBean;
import org.sdmxsource.sdmx.api.model.superbeans.codelist.CodelistSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.conceptscheme.ConceptSchemeSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.conceptscheme.ConceptSuperBean;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.base.MaintainableSuperBeanImpl;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * The type Concept scheme super bean.
 */
public class ConceptSchemeSuperBeanImpl extends MaintainableSuperBeanImpl implements ConceptSchemeSuperBean {
    private static final long serialVersionUID = 1L;

    private Set<ConceptSuperBean> concepts;
    private ConceptSchemeBean conceptSchemeBean;

    /**
     * Instantiates a new Concept scheme super bean.
     *
     * @param conceptSchemeType the concept scheme type
     * @param representations   the representations
     */
    public ConceptSchemeSuperBeanImpl(ConceptSchemeBean conceptSchemeType, Map<ConceptBean, CodelistSuperBean> representations) {
        super(conceptSchemeType);
        if (conceptSchemeType == null) {
            throw new SdmxSemmanticException(ExceptionCode.JAVA_REQUIRED_OBJECT_NULL, ConceptSchemeBean.class.getCanonicalName());
        }
        this.conceptSchemeBean = conceptSchemeType;
        concepts = new HashSet<ConceptSuperBean>();
        if (conceptSchemeType.getItems() != null) {
            for (ConceptBean currentConcept : conceptSchemeType.getItems()) {
                ConceptSuperBean concept = new ConceptSuperBeanImpl(currentConcept, representations.get(currentConcept));
                concepts.add(concept);
            }
        }
    }

    @Override
    public ConceptSchemeBean getBuiltFrom() {
        return conceptSchemeBean;
    }

    @Override
    public Set<MaintainableBean> getCompositeBeans() {
        Set<MaintainableBean> returnSet = super.getCompositeBeans();
        returnSet.add(conceptSchemeBean);
        for (ConceptSuperBean concept : concepts) {
            returnSet.addAll(concept.getCompositeBeans());
        }
        return returnSet;
    }

    @Override
    public Set<ConceptSuperBean> getConcepts() {
        return new HashSet<ConceptSuperBean>(concepts);
    }
}
