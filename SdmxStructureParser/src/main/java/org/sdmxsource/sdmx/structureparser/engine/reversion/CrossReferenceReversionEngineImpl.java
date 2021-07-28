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
package org.sdmxsource.sdmx.structureparser.engine.reversion;

import org.sdmxsource.sdmx.api.exception.CrossReferenceException;
import org.sdmxsource.sdmx.api.manager.retrieval.IdentifiableRetrievalManager;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.api.model.beans.base.IdentifiableBean;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.beans.categoryscheme.CategorisationBean;
import org.sdmxsource.sdmx.api.model.beans.categoryscheme.ReportingTaxonomyBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.HierarchicalCodelistBean;
import org.sdmxsource.sdmx.api.model.beans.conceptscheme.ConceptSchemeBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataflowBean;
import org.sdmxsource.sdmx.api.model.beans.mapping.StructureSetBean;
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.MetadataFlowBean;
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.MetadataStructureDefinitionBean;
import org.sdmxsource.sdmx.api.model.beans.process.ProcessBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ProvisionAgreementBean;
import org.sdmxsource.sdmx.structureparser.engine.*;
import org.sdmxsource.sdmx.structureretrieval.engine.CrossReferenceResolverEngine;
import org.sdmxsource.sdmx.structureretrieval.engine.impl.CrossReferenceResolverEngineImpl;

import java.util.Map;
import java.util.Set;


/**
 * The type Cross reference reversion engine.
 */
public class CrossReferenceReversionEngineImpl implements CrossReferenceReversionEngine {

    private final CategorisationCrossReferenceUpdaterEngine categorisationCrossReferenceUpdaterEngine = new CategorisationCrossReferenceUpdaterEngineImpl();

    private final ConceptSchemeCrossReferenceUpdaterEngine conceptSchemeCrossReferenceUpdaterEngine = new ConceptSchemeCrossReferenceUpdaterEngineImpl();

    private final DataflowCrossReferenceUpdaterEngine dataflowCrossReferenceUpdaterEngine = new DataflowCrossReferenceUpdaterEngineImpl();

    private final DataStructureCrossReferenceUpdaterEngine dataStructureCrossReferenceUpdaterEngine = new DataStructureCrossReferenceUpdaterEngineImpl();

    private final HierarchicCodelistCrossReferenceUpdaterEngine hierarchicCodelistCrossReferenceUpdaterEngine = new HierarchicCodelistCrossReferenceUpdaterEngineImpl();

    private final MetadataflowCrossReferenceUpdaterEngine metadataflowCrossReferenceUpdaterEngine = new MetadataflowCrossReferenceUpdaterEngineImpl();

    private final MetadataStructureCrossReferenceUpdaterEngine metadataStructureCrossReferenceUpdaterEngine = new MetadataStructureCrossReferenceUpdaterEngineImpl();

    private final ProcessCrossReferenceUpdater processCrossReferenceUpdater = new ProcessCrossReferenceUpdaterImpl();

    private final ProvisionCrossReferenceUpdaterEngine provisionCrossReferenceUpdaterEngine = new ProvisionCrossReferenceUpdaterEngineImpl();

    private final StructureSetCrossReferenceUpdaterEngine structureSetCrossReferenceUpdaterEngine = new StructureSetCrossReferenceUpdaterEngineImpl();

    private final ReportingTaxonomyBeanCrossReferenceUpdaterEngine reportingTaxonomyBeanCrossReferenceUpdaterEngine = new ReportingTaxonomyBeanCrossReferenceUpdaterEngineImpl();

    /**
     * Resolve references map.
     *
     * @param structures       the structures
     * @param resolveAgencies  the resolve agencies
     * @param resolutionDepth  the resolution depth
     * @param retrievalManager the retrieval manager
     * @return the map
     * @throws CrossReferenceException the cross reference exception
     */
    public Map<IdentifiableBean, Set<IdentifiableBean>> resolveReferences(SdmxBeans structures,
                                                                          boolean resolveAgencies,
                                                                          int resolutionDepth,
                                                                          IdentifiableRetrievalManager retrievalManager) throws CrossReferenceException {
        CrossReferenceResolverEngine crossRefResolver = new CrossReferenceResolverEngineImpl();
        return crossRefResolver.resolveReferences(structures, resolveAgencies, resolutionDepth, retrievalManager);
    }

    @Override
    public MaintainableBean udpateReferences(MaintainableBean maintianable, Map<StructureReferenceBean, StructureReferenceBean> updateReferences, String newVersionNumber) {
        switch (maintianable.getStructureType()) {
            case ATTACHMENT_CONSTRAINT:
                break;
            case CATEGORISATION:
                return categorisationCrossReferenceUpdaterEngine.updateReferences((CategorisationBean) maintianable, updateReferences, newVersionNumber);
            case CONCEPT_SCHEME:
                return conceptSchemeCrossReferenceUpdaterEngine.updateReferences((ConceptSchemeBean) maintianable, updateReferences, newVersionNumber);
            case CONTENT_CONSTRAINT:
                break;
            case DATAFLOW:
                return dataflowCrossReferenceUpdaterEngine.updateReferences((DataflowBean) maintianable, updateReferences, newVersionNumber);
            case DSD:
                return dataStructureCrossReferenceUpdaterEngine.updateReferences((DataStructureBean) maintianable, updateReferences, newVersionNumber);
            case HIERARCHICAL_CODELIST:
                return hierarchicCodelistCrossReferenceUpdaterEngine.updateReferences((HierarchicalCodelistBean) maintianable, updateReferences, newVersionNumber);
            case METADATA_FLOW:
                return metadataflowCrossReferenceUpdaterEngine.updateReferences((MetadataFlowBean) maintianable, updateReferences, newVersionNumber);
            case MSD:
                return metadataStructureCrossReferenceUpdaterEngine.updateReferences((MetadataStructureDefinitionBean) maintianable, updateReferences, newVersionNumber);
            case PROCESS:
                return processCrossReferenceUpdater.updateReferences((ProcessBean) maintianable, updateReferences, newVersionNumber);
            case PROVISION_AGREEMENT:
                return provisionCrossReferenceUpdaterEngine.updateReferences((ProvisionAgreementBean) maintianable, updateReferences, newVersionNumber);
            case REPORTING_TAXONOMY:
                return reportingTaxonomyBeanCrossReferenceUpdaterEngine.updateReferences((ReportingTaxonomyBean) maintianable, updateReferences, newVersionNumber);
            case STRUCTURE_SET:
                return structureSetCrossReferenceUpdaterEngine.updateReferences((StructureSetBean) maintianable, updateReferences, newVersionNumber);
        }
        return maintianable;
    }
}
