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
package org.sdmxsource.sdmx.structureparser.builder.superbeans.impl;

import org.sdmxsource.sdmx.api.exception.CrossReferenceException;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.beans.conceptscheme.ConceptSchemeBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DimensionBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.superbeans.SuperBeans;
import org.sdmxsource.sdmx.api.model.superbeans.codelist.CodelistSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.conceptscheme.ConceptSchemeSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.conceptscheme.ConceptSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.DimensionSuperBean;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.datastructure.DimensionSuperBeanImpl;
import org.sdmxsource.sdmx.util.beans.container.SuperBeansImpl;


/**
 * The type Dimension super bean builder.
 */
public class DimensionSuperBeanBuilder extends ComponentSuperBeanBuilder<DimensionSuperBean, DimensionBean> {

    private final ConceptSchemeSuperBeanBuilder conceptSchemeSuperBeanBuilder;

    /**
     * Instantiates a new Dimension super bean builder.
     */
    public DimensionSuperBeanBuilder() {
        this(new ConceptSchemeSuperBeanBuilder());
    }

    /**
     * Instantiates a new Dimension super bean builder.
     *
     * @param conceptSchemeSuperBeanBuilder the concept scheme super bean builder
     */
    public DimensionSuperBeanBuilder(ConceptSchemeSuperBeanBuilder conceptSchemeSuperBeanBuilder) {
        this(new CodelistSuperBeanBuilder(), new ConceptSuperBeanBuilder(), conceptSchemeSuperBeanBuilder);
    }

    /**
     * Instantiates a new Dimension super bean builder.
     *
     * @param codelistSuperBeanBuilder      the codelist super bean builder
     * @param conceptSuperBeanBuilder       the concept super bean builder
     * @param conceptSchemeSuperBeanBuilder the concept scheme super bean builder
     */
    public DimensionSuperBeanBuilder(
            final CodelistSuperBeanBuilder codelistSuperBeanBuilder,
            final ConceptSuperBeanBuilder conceptSuperBeanBuilder,
            final ConceptSchemeSuperBeanBuilder conceptSchemeSuperBeanBuilder) {
        super(codelistSuperBeanBuilder, conceptSuperBeanBuilder);
        this.conceptSchemeSuperBeanBuilder = conceptSchemeSuperBeanBuilder;
    }

    @Override
    public DimensionSuperBean build(DimensionBean buildFrom,
                                    SdmxBeanRetrievalManager retrievalManager,
                                    SuperBeans existingBeans) {
        if (existingBeans == null) {
            existingBeans = new SuperBeansImpl();
        }
        ConceptSuperBean concept = getConcept(buildFrom, retrievalManager, existingBeans);
        if (buildFrom.isMeasureDimension()) {
            CrossReferenceBean conceptSchemeRef = buildFrom.getRepresentation().getRepresentation();
            ConceptSchemeBean conceptSchemeBean = retrievalManager.getMaintainableBean(ConceptSchemeBean.class, conceptSchemeRef);
            if (conceptSchemeBean == null) {
                throw new CrossReferenceException(conceptSchemeRef);
            }
            ConceptSchemeSuperBean csSb = conceptSchemeSuperBeanBuilder.build(conceptSchemeBean, retrievalManager, existingBeans);
            return new DimensionSuperBeanImpl(buildFrom, csSb, concept);
        } else {
            CodelistSuperBean codelist = getCodelist(buildFrom, retrievalManager, existingBeans);
            return new DimensionSuperBeanImpl(buildFrom, codelist, concept);
        }
    }
}
