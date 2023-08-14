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

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.sdmxsource.sdmx.api.exception.CrossReferenceException;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxBeanRetrievalManager;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxSuperBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.beans.base.ComponentBean;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.CodelistBean;
import org.sdmxsource.sdmx.api.model.beans.conceptscheme.ConceptBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.superbeans.SuperBeans;
import org.sdmxsource.sdmx.api.model.superbeans.base.SuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.codelist.CodelistSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.conceptscheme.ConceptSchemeSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.conceptscheme.ConceptSuperBean;
import org.sdmxsource.sdmx.structureretrieval.manager.InMemorySdmxSuperBeanRetrievalManager;
import org.sdmxsource.sdmx.util.beans.container.SuperBeansImpl;


/**
 * The type Component super bean builder.
 *
 * @param <K> the type parameter
 * @param <V> the type parameter
 */
public abstract class ComponentSuperBeanBuilder<K extends SuperBean, V extends SDMXBean> extends StructureBuilderImpl<K, V> {
    private static final Logger LOG = LoggerFactory.getLogger(ComponentSuperBeanBuilder.class);

    private final CodelistSuperBeanBuilder codelistSuperBeanBuilder;

    private final ConceptSuperBeanBuilder conceptSuperBeanBuilder;

    /**
     * Instantiates a new Component super bean builder.
     */
    public ComponentSuperBeanBuilder() {
        this(new CodelistSuperBeanBuilder(), new ConceptSuperBeanBuilder());
    }

    /**
     * Instantiates a new Component super bean builder.
     *
     * @param codelistSuperBeanBuilder the codelist super bean builder
     * @param conceptSuperBeanBuilder  the concept super bean builder
     */
    public ComponentSuperBeanBuilder(
            final CodelistSuperBeanBuilder codelistSuperBeanBuilder,
            final ConceptSuperBeanBuilder conceptSuperBeanBuilder) {
        this.codelistSuperBeanBuilder = codelistSuperBeanBuilder;
        this.conceptSuperBeanBuilder = conceptSuperBeanBuilder;
    }

    /**
     * Gets codelist.
     *
     * @param componentBean    the component bean
     * @param retrievalManager the retrieval manager
     * @param existingBeans    the existing beans
     * @return the codelist
     */
    CodelistSuperBean getCodelist(ComponentBean componentBean,
                                  SdmxBeanRetrievalManager retrievalManager,
                                  SuperBeans existingBeans) {
        if (existingBeans == null) {
            existingBeans = new SuperBeansImpl();
        }
        SdmxSuperBeanRetrievalManager superBeanRetrievalManager = new InMemorySdmxSuperBeanRetrievalManager(existingBeans);

        if (componentBean.hasCodedRepresentation()) {
            CrossReferenceBean codelistRef = componentBean.getRepresentation().getRepresentation();

            LOG.debug("get codelist super bean : " + codelistRef);

            CodelistSuperBean codelistSuperBean = superBeanRetrievalManager.getCodelistSuperBean(codelistRef);
            if (codelistSuperBean == null) {
                CodelistBean codelistBean = retrievalManager.getIdentifiableBean(codelistRef, CodelistBean.class);

                if (codelistBean == null) {
                    throw new CrossReferenceException(componentBean.getRepresentation().getRepresentation());
                }
                LOG.debug("no existing super bean found build new : " + codelistBean.getUrn());
                codelistSuperBean = codelistSuperBeanBuilder.build(codelistBean);

                existingBeans.addCodelist(codelistSuperBean);
            }
            return codelistSuperBean;
        }
        LOG.debug("component is uncoded");
        return null;
    }

    /**
     * Gets concept.
     *
     * @param componentBean    the component bean
     * @param retrievalManager the retrieval manager
     * @param existingBeans    the existing beans
     * @return the concept
     */
    ConceptSuperBean getConcept(ComponentBean componentBean,
                                SdmxBeanRetrievalManager retrievalManager,
                                SuperBeans existingBeans) {

        if (existingBeans == null) {
            existingBeans = new SuperBeansImpl();
        }
        SdmxSuperBeanRetrievalManager superBeanRetrievalManager = new InMemorySdmxSuperBeanRetrievalManager(existingBeans);

        ConceptBean conceptBean = null;
        CrossReferenceBean conceptRef = componentBean.getConceptRef();

        LOG.debug("get concept super bean : " + conceptRef);

        String conceptId = conceptRef.getChildReference().getId();

        //Return an existing super bean if you have one
        ConceptSchemeSuperBean csSuperBean = superBeanRetrievalManager.getConceptSchemeSuperBean(conceptRef);
        if (csSuperBean != null) {
            LOG.debug("check existing concept scheme super bean : " + csSuperBean.getUrn());
            for (ConceptSuperBean concept : csSuperBean.getConcepts()) {
                if (concept.getId().equals(conceptId)) {
                    LOG.debug("existing concept super bean found");
                    return concept;
                }
            }
        }
        LOG.debug("No existing concept super bean found, build new from concept scheme bean");

        //Could not find an existing one.
        conceptBean = retrievalManager.getIdentifiableBean(conceptRef, ConceptBean.class);
        if (conceptBean == null) {
            LOG.error("Could not find concept bean to build concept super bean from : " + conceptRef);
            throw new CrossReferenceException(conceptRef);
        }
        return conceptSuperBeanBuilder.build(conceptBean, retrievalManager, existingBeans);
    }

}
