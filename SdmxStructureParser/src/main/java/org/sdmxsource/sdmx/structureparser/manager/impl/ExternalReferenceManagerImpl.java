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
package org.sdmxsource.sdmx.structureparser.manager.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.exception.CrossReferenceException;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.factory.ReadableDataLocationFactory;
import org.sdmxsource.sdmx.api.manager.parse.StructureParsingManager;
import org.sdmxsource.sdmx.api.model.ResolutionSettings;
import org.sdmxsource.sdmx.api.model.ResolutionSettings.RESOLVE_CROSS_REFERENCES;
import org.sdmxsource.sdmx.api.model.ResolutionSettings.RESOLVE_EXTERNAL_SETTING;
import org.sdmxsource.sdmx.api.model.StructureWorkspace;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.util.ReadableDataLocation;
import org.sdmxsource.sdmx.structureparser.manager.ExternalReferenceManager;
import org.sdmxsource.sdmx.structureparser.manager.parsing.impl.StructureParsingManagerImpl;
import org.sdmxsource.sdmx.util.beans.MaintainableUtil;
import org.sdmxsource.sdmx.util.beans.container.SdmxBeansImpl;
import org.sdmxsource.util.factory.SdmxSourceReadableDataLocationFactory;
import org.sdmxsource.util.io.URIUtil;
import org.sdmxsource.util.log.LoggingUtil;

import java.net.URI;
import java.net.URL;
import java.util.Set;

/**
 * The type External reference manager.
 */
//JAVADOC missing
public class ExternalReferenceManagerImpl implements ExternalReferenceManager {
    private static final Logger LOG = LogManager.getLogger(ExternalReferenceManagerImpl.class);
    private static final ResolutionSettings retriervalSettings = new ResolutionSettings(RESOLVE_EXTERNAL_SETTING.DO_NOT_RESOLVE, RESOLVE_CROSS_REFERENCES.DO_NOT_RESOLVE, 0);
    private final ReadableDataLocationFactory readableDataLocationFactory;
    private StructureParsingManager structureParsingManager;

    /**
     * Instantiates a new External reference manager.
     *
     * @param readableDataLocationFactory the readable data location factory
     * @param structureParsingManager     the structure parsing manager
     */
    public ExternalReferenceManagerImpl(
            final ReadableDataLocationFactory readableDataLocationFactory,
            final StructureParsingManager structureParsingManager) {
        this.readableDataLocationFactory = readableDataLocationFactory != null ? readableDataLocationFactory : new SdmxSourceReadableDataLocationFactory();
        this.structureParsingManager = structureParsingManager != null ? structureParsingManager : new StructureParsingManagerImpl(this.readableDataLocationFactory, null);
    }

    @Override
    public SdmxBeans resolveExternalReferences(SdmxBeans structures, boolean isSubstitute, boolean isLienient) throws SdmxException, CrossReferenceException {
        LOG.info("Check for External References, Substititue=" + isSubstitute + " Lienient=" + isLienient);
        SdmxBeans returnBeans = new SdmxBeansImpl();
        if (structures == null) {
            return returnBeans;
        }

        resolveBeans(structures.getAllMaintainables(), returnBeans, isLienient);

        LOG.info("Number of External References=" + returnBeans.getAllMaintainables().size());
        if (isSubstitute) {
            structures.merge(returnBeans);
        }
        return returnBeans;
    }

    private void resolveBeans(Set<? extends MaintainableBean> maintinableBeans, SdmxBeans returnBeans, boolean isLienient) {
        for (MaintainableBean currentBean : maintinableBeans) {
            if (currentBean.isExternalReference().isTrue()) {
                SdmxBeans retrievedStructures = resolve(currentBean, isLienient);
                //CHECK THERE WAS SOMETHING RETURNED
                if (retrievedStructures != null) {
                    MaintainableBean resolvedReference = MaintainableUtil.resolveReference(retrievedStructures.getCodelists(), currentBean.asReference());
                    if (resolvedReference == null) {
                        LOG.warn("External Reference " + currentBean.getStructureURL() + " does not contain structure: " + currentBean.getUrn());
                        addException(currentBean, isLienient);
                    } else {
                        //GET THE CODELIST AND ADD IT
                        returnBeans.addIdentifiable(resolvedReference);
                    }
                } else {
                    LOG.warn("Can not resolve external reference for structure " + currentBean.getUrn() + ", URL does not contain SDMX structures: " + currentBean.getStructureURL());
                }
            }
        }
    }

    private SdmxBeans resolve(MaintainableBean maintBean, boolean isLienient) {
        URI uri = null;
        try {
            URL urlLocation = maintBean.getStructureURL();
            if (urlLocation == null) {
                LOG.warn("Can not resolve external reference for structure " + maintBean.getUrn() + " Structure URL is not set ");
                addException(maintBean, isLienient);
            } else {
                ReadableDataLocation dataLocation = readableDataLocationFactory.getReadableDataLocation(urlLocation);

                StructureWorkspace sw = structureParsingManager.parseStructures(dataLocation, retriervalSettings, null);
                return sw.getStructureBeans(false);
            }
        } catch (CrossReferenceException refEx) {
            if (isLienient) {
                addException(maintBean, refEx.getMessage());
            } else {
                throw refEx;
            }
        } catch (Throwable th) {
            if (isLienient) {
                addException(maintBean, th.getMessage());
            } else {
                throw new RuntimeException(th);
            }
        } finally {
            if (uri != null) {
                URIUtil.closeUri(uri);
                URIUtil.deleteUri(uri);
            }
        }
        return null;
    }

    private void addException(MaintainableBean maintBean, boolean isLienient) {
        if (isLienient) {
            if (maintBean.getStructureURL() == null) {
                addException(maintBean, "External location not set");
            } else {
                addException(maintBean, "External location `" + maintBean.getStructureURL().toString() + "` does not contain structure : " + maintBean.getUrn());
            }
        } else {
            if (maintBean.getStructureURL() == null) {
                throw new SdmxSemmanticException(ExceptionCode.EXTERNAL_STRUCTURE_NOT_FOUND_AT_URI, maintBean.getUrn(), "NOT SET");
            }
            throw new SdmxSemmanticException(ExceptionCode.EXTERNAL_STRUCTURE_NOT_FOUND_AT_URI, maintBean.getUrn(), maintBean.getStructureURL().toString());
        }
    }

    private void addException(MaintainableBean maintBean, String message) {
        LoggingUtil.error(LOG, message);
    }

    /**
     * Sets structure parsing manager.
     *
     * @param structureParsingManager the structure parsing manager
     */
    public void setStructureParsingManager(StructureParsingManager structureParsingManager) {
        this.structureParsingManager = structureParsingManager;
    }
}
