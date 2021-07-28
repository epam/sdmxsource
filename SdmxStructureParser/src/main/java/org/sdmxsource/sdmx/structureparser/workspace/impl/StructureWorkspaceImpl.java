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
package org.sdmxsource.sdmx.structureparser.workspace.impl;

import org.sdmxsource.sdmx.api.builder.SuperBeansBuilder;
import org.sdmxsource.sdmx.api.constants.SDMX_SCHEMA;
import org.sdmxsource.sdmx.api.constants.STRUCTURE_OUTPUT_FORMAT;
import org.sdmxsource.sdmx.api.manager.output.StructureWriterManager;
import org.sdmxsource.sdmx.api.manager.retrieval.IdentifiableRetrievalManager;
import org.sdmxsource.sdmx.api.model.StructureWorkspace;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.api.model.beans.base.IdentifiableBean;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.header.HeaderBean;
import org.sdmxsource.sdmx.api.model.superbeans.SuperBeans;
import org.sdmxsource.sdmx.sdmxbeans.model.SdmxStructureFormat;
import org.sdmxsource.sdmx.structureparser.builder.superbeans.impl.SuperBeansBuilderImpl;
import org.sdmxsource.sdmx.structureparser.manager.impl.StructureWriterManagerImpl;
import org.sdmxsource.sdmx.structureretrieval.engine.CrossReferenceResolverEngine;
import org.sdmxsource.sdmx.structureretrieval.engine.impl.CrossReferenceResolverEngineImpl;
import org.sdmxsource.sdmx.util.beans.MaintainableUtil;
import org.sdmxsource.sdmx.util.beans.container.SdmxBeansImpl;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * The type Structure workspace.
 */
public class StructureWorkspaceImpl implements StructureWorkspace {

    private final SuperBeansBuilder superBeansBuilder = new SuperBeansBuilderImpl();

    private final StructureWriterManager structureWritingManager = new StructureWriterManagerImpl(null);

    private HeaderBean header;
    private SdmxBeans beans;
    private Map<IdentifiableBean, Set<IdentifiableBean>> crossReferencedBeans;
    private SdmxBeans allBeans;
    private SuperBeans superBeans;

    private IdentifiableRetrievalManager retrievalManager;
    private boolean retrieveCrossRefefences;
    private boolean retrieveAgencies;
    private int resolutionDepth;

    /**
     * Creates a workspace
     *
     * @param beans
     */
    private StructureWorkspaceImpl(SdmxBeans beans,
                                   Map<IdentifiableBean, Set<IdentifiableBean>> crossReferencedBeans) {
        if (beans == null) {
            throw new IllegalArgumentException("Cannot instantiate a StructureWorkspace with beans as a null reference.");
        }
        this.beans = beans;
        this.crossReferencedBeans = crossReferencedBeans;
        init();
    }

    /**
     * Creates a workspace
     *
     * @param beans the beans
     */
    public StructureWorkspaceImpl(SdmxBeans beans) {
        this(beans, null, false, false, 0);
    }

    /**
     * Creates a workspace, gets all cross referenced objects using the retrieval manager (if it is not null)
     *
     * @param beans                   - the beans to populate the workspace with
     * @param retrievalManager        - this will be used to retrieve all the cross references if it is not null
     * @param retrieveCrossRefefences the retrieve cross refefences
     * @param retrieveAgencies        - this will also retrieve all the agencies if true
     * @param resolutionDepth         the resolution depth
     */
    public StructureWorkspaceImpl(SdmxBeans beans,
                                  IdentifiableRetrievalManager retrievalManager,
                                  boolean retrieveCrossRefefences,
                                  boolean retrieveAgencies,
                                  int resolutionDepth) {
        if (beans == null) {
            throw new IllegalArgumentException("Cannot instantiate a StructureWorkspace with beans as a null reference.");
        }
        this.beans = beans;
        this.retrievalManager = retrievalManager;
        this.retrieveCrossRefefences = retrieveCrossRefefences;
        this.retrieveAgencies = retrieveAgencies;
        this.resolutionDepth = resolutionDepth;
        init();
    }

    @Override
    public void writeStructures(SDMX_SCHEMA structureType, OutputStream out, boolean includeCrossReferences) {
        STRUCTURE_OUTPUT_FORMAT outputFormat = null;
        switch (structureType) {
            case CSV:
                outputFormat = STRUCTURE_OUTPUT_FORMAT.CSV;
                break;
            case EDI:
                outputFormat = STRUCTURE_OUTPUT_FORMAT.EDI;
                break;
            case VERSION_ONE:
                outputFormat = STRUCTURE_OUTPUT_FORMAT.SDMX_V1_STRUCTURE_DOCUMENT;
                break;
            case VERSION_TWO:
                outputFormat = STRUCTURE_OUTPUT_FORMAT.SDMX_V2_STRUCTURE_DOCUMENT;
                break;
            case VERSION_TWO_POINT_ONE:
                outputFormat = STRUCTURE_OUTPUT_FORMAT.SDMX_V21_STRUCTURE_DOCUMENT;
                break;
        }
        if (includeCrossReferences) {
            structureWritingManager.writeStructures(allBeans, new SdmxStructureFormat(outputFormat), out);
        } else {
            structureWritingManager.writeStructures(beans, new SdmxStructureFormat(outputFormat), out);
        }
    }

    private void init() {
        if (superBeansBuilder == null) {
            throw new RuntimeException("Required dependancy 'superBeansBuilder' is null, StructureWorkspaceImpl is @Configurable and requires '<context:spring-configured />' to be set, and aspectJ to be on");
        }
        if (structureWritingManager == null) {
            throw new RuntimeException("Required dependancy 'structureWritingManager' is null, StructureWorkspaceImpl is @Configurable and requires '<context:spring-configured />' to be set");
        }


        if (retrieveCrossRefefences) {
            CrossReferenceResolverEngine crossRefResolver = new CrossReferenceResolverEngineImpl();
            crossReferencedBeans = crossRefResolver.resolveReferences(beans, retrieveAgencies, resolutionDepth, retrievalManager);
        }
        this.allBeans = new SdmxBeansImpl(beans);
        if (crossReferencedBeans != null) {
            for (Set<IdentifiableBean> currentBeanSet : crossReferencedBeans.values()) {
                for (IdentifiableBean currentBean : currentBeanSet) {
                    allBeans.addIdentifiable(currentBean);
                }
            }
        }
    }

    @Override
    public HeaderBean getHeader() {
        return header;
    }

    @Override
    public Map<IdentifiableBean, Set<IdentifiableBean>> getCrossReferences() {
        return crossReferencedBeans;
    }

    @Override
    public SdmxBeans getStructureBeans(boolean includeCrossReferences) {
        if (includeCrossReferences && crossReferencedBeans != null) {
            return allBeans;
        }
        return beans;
    }

    @Override
    public SuperBeans getSuperBeans() {
        if (superBeans == null) {
            superBeans = superBeansBuilder.build(allBeans);
        }
        return superBeans;
    }

    @Override
    public StructureWorkspace getSubsetWorkspace(StructureReferenceBean... query) {
        Set<MaintainableBean> maintainablesSubset = new HashSet<MaintainableBean>();
        Map<IdentifiableBean, Set<IdentifiableBean>> crossReferencedSubset = new HashMap<IdentifiableBean, Set<IdentifiableBean>>();

        for (int i = 0; i < query.length; i++) {
            StructureReferenceBean currentQuery = query[i];
            Set<MaintainableBean> maintainableForStructure = beans.getMaintainables(currentQuery.getMaintainableStructureType());
            Set<MaintainableBean> maintainableMatches = MaintainableUtil.findMatches(maintainableForStructure, currentQuery);
            maintainablesSubset.addAll(maintainableMatches);
            for (MaintainableBean currentMatch : maintainableMatches) {
                Set<IdentifiableBean> identifiables = crossReferencedBeans == null ? new HashSet<IdentifiableBean>() : crossReferencedBeans.get(currentMatch);
                if (identifiables != null) {
                    crossReferencedSubset.put(currentMatch, identifiables);
                }
            }
        }
        SdmxBeans beansSubset = new SdmxBeansImpl(beans.getHeader(), maintainablesSubset);
        return new StructureWorkspaceImpl(beansSubset, crossReferencedSubset);
    }

    @Override
    public void mergeWorkspace(StructureWorkspace workspace) {
        allBeans = new SdmxBeansImpl(allBeans, workspace.getStructureBeans(true));
        beans = new SdmxBeansImpl(beans, workspace.getStructureBeans(false));
        superBeans = null;

        Map<IdentifiableBean, Set<IdentifiableBean>> localCrossReferencedBeans = workspace.getCrossReferences();
        if (localCrossReferencedBeans != null) {
            for (IdentifiableBean currentKey : localCrossReferencedBeans.keySet()) {
                Set<IdentifiableBean> currentSet = null;
                if (crossReferencedBeans.containsKey(currentKey)) {
                    currentSet = crossReferencedBeans.get(currentKey);
                } else {
                    currentSet = new HashSet<IdentifiableBean>();
                    crossReferencedBeans.put(currentKey, currentSet);
                }
                currentSet.addAll(localCrossReferencedBeans.get(currentKey));
            }
        }
    }
}
