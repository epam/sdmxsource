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
package org.sdmxsource.sdmx.structureparser.manager.parsing.impl;

import org.apache.log4j.Logger;
import org.sdmxsource.sdmx.api.exception.CrossReferenceException;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.exception.SdmxNotImplementedException;
import org.sdmxsource.sdmx.api.factory.ReadableDataLocationFactory;
import org.sdmxsource.sdmx.api.factory.StructureParserFactory;
import org.sdmxsource.sdmx.api.manager.parse.StructureParsingManager;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.ResolutionSettings;
import org.sdmxsource.sdmx.api.model.StructureWorkspace;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.api.util.ReadableDataLocation;
import org.sdmxsource.sdmx.structureparser.factory.SdmxStructureParserFactory;
import org.sdmxsource.sdmx.structureparser.manager.ExternalReferenceManager;
import org.sdmxsource.sdmx.structureparser.manager.impl.ExternalReferenceManagerImpl;
import org.sdmxsource.sdmx.structureparser.workspace.impl.StructureWorkspaceImpl;


/**
 * The type Structure parsing manager.
 */
public class StructureParsingManagerImpl implements StructureParsingManager {
    private final ExternalReferenceManager externalReferenceManager;
    private final StructureParserFactory[] factories;
    private Logger LOG = Logger.getLogger(StructureParsingManagerImpl.class);

    /**
     * Instantiates a new Structure parsing manager.
     *
     * @param readableDataLocationFactory the readable data location factory
     * @param factories                   the factories
     */
    public StructureParsingManagerImpl(final ReadableDataLocationFactory readableDataLocationFactory, final StructureParserFactory[] factories) {
        externalReferenceManager = new ExternalReferenceManagerImpl(readableDataLocationFactory, this);

        if (factories == null || factories.length == 0) {
            this.factories = new StructureParserFactory[]{new SdmxStructureParserFactory()};
        } else {
            this.factories = factories;
        }
    }


    @Override
    public StructureWorkspace parseStructures(ReadableDataLocation dataLocation) throws SdmxException, CrossReferenceException {
        ResolutionSettings settings = new ResolutionSettings(ResolutionSettings.RESOLVE_EXTERNAL_SETTING.DO_NOT_RESOLVE, ResolutionSettings.RESOLVE_CROSS_REFERENCES.DO_NOT_RESOLVE);
        return parseStructures(dataLocation, settings, null);
    }


    @Override
    public StructureWorkspace parseStructures(ReadableDataLocation dataLocation, ResolutionSettings settings, SdmxBeanRetrievalManager retrievalManager) {
        LOG.info("Parse structures, resolution settings: " + settings + ", retrieval manager=" + retrievalManager);
        return buildWorkspace(getSdmxBeans(dataLocation), settings, retrievalManager);
    }

    @Override
    public StructureWorkspace buildWorkspace(SdmxBeans beans, ResolutionSettings settings, SdmxBeanRetrievalManager retrievalManager) {
        if (settings.isResolveExternalReferences()) {
            externalReferenceManager.resolveExternalReferences(beans, settings.isSubstituteExternal(), settings.isLenient());
        }
        return new StructureWorkspaceImpl(beans, retrievalManager, settings.isResolveCrossReferences(), settings.isResolveAgencyReferences(), settings.getResolutionDepth());
    }


    private SdmxBeans getSdmxBeans(ReadableDataLocation sourceData) {
        for (StructureParserFactory currentFactory : getStructureParserFactory()) {
            SdmxBeans beans = currentFactory.getSdmxBeans(sourceData);
            if (beans != null) {
                return beans;
            }
        }
        throw new SdmxNotImplementedException("Can not parse structures.  Structure format is either not supported, or has an invalid syntax");
    }

    private StructureParserFactory[] getStructureParserFactory() throws IllegalArgumentException {
        return factories;
    }
}
