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
package org.sdmxsource.sdmx.sdmxbeans.model.mutablesuperbeans.conceptscheme;

import org.sdmxsource.sdmx.api.model.mutablesuperbeans.conceptscheme.ConceptMutableSuperBean;
import org.sdmxsource.sdmx.api.model.mutablesuperbeans.conceptscheme.ConceptSchemeMutableSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.conceptscheme.ConceptSchemeSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.conceptscheme.ConceptSuperBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutablesuperbeans.base.MaintainableMutableSuperBeanImpl;

import java.util.HashSet;
import java.util.Set;


/**
 * The type Concept scheme mutable super bean.
 */
public class ConceptSchemeMutableSuperBeanImpl extends MaintainableMutableSuperBeanImpl implements ConceptSchemeMutableSuperBean {

    private static final long serialVersionUID = 1L;

    private Set<ConceptMutableSuperBean> concepts;

    /**
     * Instantiates a new Concept scheme mutable super bean.
     *
     * @param conceptSchemeType the concept scheme type
     */
    public ConceptSchemeMutableSuperBeanImpl(ConceptSchemeSuperBean conceptSchemeType) {
        super(conceptSchemeType);

        if (conceptSchemeType.getConcepts() != null) {
            concepts = new HashSet<ConceptMutableSuperBean>();
            for (ConceptSuperBean currentBean : conceptSchemeType.getConcepts()) {
                concepts.add(new ConceptMutableSuperBeanImpl(currentBean));
            }
        }
    }

    /**
     * Instantiates a new Concept scheme mutable super bean.
     */
    public ConceptSchemeMutableSuperBeanImpl() {
    }

    @Override
    public Set<ConceptMutableSuperBean> getConcepts() {
        return concepts;
    }

    @Override
    public void setConcepts(Set<ConceptMutableSuperBean> concepts) {
        this.concepts = concepts;
    }
}
