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
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxSuperBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.beans.codelist.CodelistBean;
import org.sdmxsource.sdmx.api.model.beans.conceptscheme.ConceptBean;
import org.sdmxsource.sdmx.api.model.beans.conceptscheme.ConceptSchemeBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.superbeans.SuperBeans;
import org.sdmxsource.sdmx.api.model.superbeans.codelist.CodelistSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.conceptscheme.ConceptSchemeSuperBean;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.conceptscheme.ConceptSchemeSuperBeanImpl;
import org.sdmxsource.sdmx.structureretrieval.manager.InMemorySdmxSuperBeanRetrievalManager;
import org.sdmxsource.sdmx.util.beans.container.SuperBeansImpl;

import java.util.HashMap;
import java.util.Map;


/**
 * The type Concept scheme super bean builder.
 */
public class ConceptSchemeSuperBeanBuilder extends StructureBuilderImpl<ConceptSchemeSuperBean, ConceptSchemeBean> {

    private CodelistSuperBeanBuilder codelistSuperBeanBuilder;

    /**
     * Instantiates a new Concept scheme super bean builder.
     */
    public ConceptSchemeSuperBeanBuilder() {
        this(new CodelistSuperBeanBuilder());
    }

    /**
     * Instantiates a new Concept scheme super bean builder.
     *
     * @param codelistSuperBeanBuilder the codelist super bean builder
     */
    public ConceptSchemeSuperBeanBuilder(final CodelistSuperBeanBuilder codelistSuperBeanBuilder) {
        this.codelistSuperBeanBuilder = codelistSuperBeanBuilder;
    }

    @Override
    public ConceptSchemeSuperBean build(ConceptSchemeBean buildFrom,
                                        SdmxBeanRetrievalManager retrievalManager,
                                        SuperBeans existingBeans) {
        if (existingBeans == null) {
            existingBeans = new SuperBeansImpl();
        }
        SdmxSuperBeanRetrievalManager superBeanRetrievalManager = new InMemorySdmxSuperBeanRetrievalManager(existingBeans);

        Map<ConceptBean, CodelistSuperBean> codelistMap = new HashMap<ConceptBean, CodelistSuperBean>();
        for (ConceptBean conceptBean : buildFrom.getItems()) {
            if (conceptBean.getCoreRepresentation() != null && conceptBean.getCoreRepresentation().getRepresentation() != null) {
                CrossReferenceBean codelistRef = conceptBean.getCoreRepresentation().getRepresentation();

                CodelistSuperBean codelistSuperBean = superBeanRetrievalManager.getCodelistSuperBean(codelistRef);
                if (codelistSuperBean == null) {
                    CodelistBean codelist = retrievalManager.getIdentifiableBean(codelistRef, CodelistBean.class);
                    codelistSuperBean = codelistSuperBeanBuilder.build(codelist);
                    existingBeans.addCodelist(codelistSuperBean);
                    if (codelistSuperBean == null) {
                        throw new CrossReferenceException(conceptBean.getCoreRepresentation().getRepresentation());
                    }
                }

                codelistMap.put(conceptBean, codelistSuperBean);
            }
        }
        return new ConceptSchemeSuperBeanImpl(buildFrom, codelistMap);
    }

    /**
     * Sets codelist super bean builder.
     *
     * @param codelistSuperBeanBuilder the codelist super bean builder
     */
    public void setCodelistSuperBeanBuilder(CodelistSuperBeanBuilder codelistSuperBeanBuilder) {
        this.codelistSuperBeanBuilder = codelistSuperBeanBuilder;
    }
}
