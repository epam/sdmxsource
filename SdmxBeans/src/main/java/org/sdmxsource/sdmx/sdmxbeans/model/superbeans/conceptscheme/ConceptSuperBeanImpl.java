/**
 * Copyright (c) 2013 Metadata Technology Ltd.
 * <p>
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the GNU Lesser General Public License v 3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * This file is part of the SDMX Component Library.
 * <p>
 * The SDMX Component Library is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 * <p>
 * The SDMX Component Library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser
 * General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License
 * along with The SDMX Component Library If not, see
 * http://www.gnu.org/licenses/lgpl.
 * <p>
 * Contributors:
 * Metadata Technology - initial API and implementation
 */
/**
 *
 */
package org.sdmxsource.sdmx.sdmxbeans.model.superbeans.conceptscheme;

import org.sdmxsource.sdmx.api.manager.retrieval.SdmxBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.beans.base.TextFormatBean;
import org.sdmxsource.sdmx.api.model.beans.conceptscheme.ConceptBean;
import org.sdmxsource.sdmx.api.model.superbeans.SuperBeans;
import org.sdmxsource.sdmx.api.model.superbeans.codelist.CodelistSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.conceptscheme.ConceptSuperBean;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.SuperBeansUtil;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.base.NameableSuperBeanImpl;

import java.util.Set;


/**
 * The type Concept super bean.
 *
 * @author Matt Nelson
 */
public class ConceptSuperBeanImpl extends NameableSuperBeanImpl implements ConceptSuperBean {
    private static final long serialVersionUID = 1L;
    private CodelistSuperBean representation;
    private TextFormatBean textFormat;
    private ConceptBean builtFrom;

    /**
     * Instantiates a new Concept super bean.
     *
     * @param concept          the concept
     * @param retrievalManager the retrieval manager
     * @param existingBeans    the existing beans
     */
    public ConceptSuperBeanImpl(ConceptBean concept,
                                SdmxBeanRetrievalManager retrievalManager,
                                SuperBeans existingBeans) {
        super(concept);
        if (concept.getCoreRepresentation() != null) {
            this.representation = SuperBeansUtil.buildCodelist(concept.getCoreRepresentation().getRepresentation(), retrievalManager, existingBeans);
        }
        if (concept.getCoreRepresentation() != null) {
            this.textFormat = concept.getCoreRepresentation().getTextFormat();
        }
        this.builtFrom = concept;
    }


    /**
     * Instantiates a new Concept super bean.
     *
     * @param concept        the concept
     * @param representation the representation
     */
    public ConceptSuperBeanImpl(ConceptBean concept, CodelistSuperBean representation) {
        super(concept);
        this.representation = representation;
        if (concept.getCoreRepresentation() != null) {
            this.textFormat = concept.getCoreRepresentation().getTextFormat();
        }
        this.builtFrom = concept;
    }

    @Override
    public boolean isStandAloneConcept() {
        return false;
    }

    @Override
    public CodelistSuperBean getCoreRepresentation() {
        return representation;
    }

    @Override
    public boolean hasRepresentation() {
        return representation != null;
    }

    @Override
    public TextFormatBean getTextFormat() {
        return textFormat;
    }

    @Override
    public ConceptBean getBuiltFrom() {
        return builtFrom;
    }

    @Override
    public Set<MaintainableBean> getCompositeBeans() {
        Set<MaintainableBean> returnSet = super.getCompositeBeans();
        returnSet.add(builtFrom.getMaintainableParent());
        if (representation != null) {
            returnSet.add(representation.getBuiltFrom());
        }
        return returnSet;
    }
}
