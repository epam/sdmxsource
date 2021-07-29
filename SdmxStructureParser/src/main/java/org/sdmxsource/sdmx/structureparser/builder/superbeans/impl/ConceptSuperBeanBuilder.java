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

import org.apache.log4j.Logger;
import org.sdmxsource.sdmx.api.exception.CrossReferenceException;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxBeanRetrievalManager;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxSuperBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.beans.codelist.CodelistBean;
import org.sdmxsource.sdmx.api.model.beans.conceptscheme.ConceptBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.superbeans.SuperBeans;
import org.sdmxsource.sdmx.api.model.superbeans.codelist.CodelistSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.conceptscheme.ConceptSuperBean;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.conceptscheme.ConceptSuperBeanImpl;
import org.sdmxsource.sdmx.structureretrieval.manager.InMemorySdmxSuperBeanRetrievalManager;
import org.sdmxsource.sdmx.util.beans.container.SuperBeansImpl;


/**
 * The type Concept super bean builder.
 */
public class ConceptSuperBeanBuilder extends StructureBuilderImpl<ConceptSuperBean, ConceptBean> {
    private static final Logger LOG = Logger.getLogger(ComponentSuperBeanBuilder.class);

    private CodelistSuperBeanBuilder codelistSuperBeanBuilder;

    /**
     * Instantiates a new Concept super bean builder.
     *
     * @param codelistSuperBeanBuilder the codelist super bean builder
     */
    public ConceptSuperBeanBuilder(final CodelistSuperBeanBuilder codelistSuperBeanBuilder) {
        this.codelistSuperBeanBuilder = codelistSuperBeanBuilder;
    }

    /**
     * Instantiates a new Concept super bean builder.
     */
    public ConceptSuperBeanBuilder() {
        this(new CodelistSuperBeanBuilder());
    }


    @Override
    public ConceptSuperBean build(ConceptBean buildFrom,
                                  SdmxBeanRetrievalManager retrievalManager,
                                  SuperBeans existingBeans) {
        if (existingBeans == null) {
            existingBeans = new SuperBeansImpl();
        }
        SdmxSuperBeanRetrievalManager superBeanRetrievalManager = new InMemorySdmxSuperBeanRetrievalManager(existingBeans);

        CodelistSuperBean codelistSuperBean = null;
        if (buildFrom.getCoreRepresentation() != null && buildFrom.getCoreRepresentation().getRepresentation() != null) {
            CrossReferenceBean codelistRef = buildFrom.getCoreRepresentation().getRepresentation();

            LOG.debug("get codelist super bean : " + codelistRef);

            codelistSuperBean = superBeanRetrievalManager.getCodelistSuperBean(codelistRef);
            if (codelistSuperBean == null) {
                LOG.debug("no existing super bean found build new : " + codelistRef);
                CodelistBean codelist = retrievalManager.getIdentifiableBean(codelistRef, CodelistBean.class);

                if (codelist == null) {
                    throw new CrossReferenceException(buildFrom.getCoreRepresentation().getRepresentation());
                }

                codelistSuperBean = codelistSuperBeanBuilder.build(codelist);

                LOG.debug("codelist super bean built");
                existingBeans.addCodelist(codelistSuperBean);
            }
        }
        return new ConceptSuperBeanImpl(buildFrom, codelistSuperBean);
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
