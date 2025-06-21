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
package org.sdmxsource.sdmx.structureretrieval.manager;

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.constants.STRUCTURE_QUERY_DETAIL;
import org.sdmxsource.sdmx.api.constants.STRUCTURE_REFERENCE_DETAIL;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.factory.ReadableDataLocationFactory;
import org.sdmxsource.sdmx.api.manager.parse.StructureParsingManager;
import org.sdmxsource.sdmx.api.model.ResolutionSettings.RESOLVE_CROSS_REFERENCES;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.beans.reference.MaintainableRefBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.query.RESTStructureQuery;
import org.sdmxsource.sdmx.api.util.ReadableDataLocation;
import org.sdmxsource.sdmx.querybuilder.builder.StructureQueryBuilderRest;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.reference.RESTStructureQueryImpl;
import org.sdmxsource.sdmx.util.beans.reference.StructureReferenceBeanImpl;
import org.sdmxsource.util.factory.SdmxSourceReadableDataLocationFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.Set;


/**
 * The type Rest sdmx bean retrieval manager.
 */
public class RESTSdmxBeanRetrievalManager extends BaseSdmxBeanRetrievalManager {
    private final String restURL;

    private final StructureQueryBuilderRest restQueryBuilder;

    private final StructureParsingManager spm;

    private final ReadableDataLocationFactory rdlFactory;

    /**
     * Instantiates a new Rest sdmx bean retrieval manager.
     *
     * @param restQueryBuilder        the rest query builder
     * @param structureParsingManager the structure parsing manager
     * @param rdlFactory              the rdl factory
     * @param restURL                 the rest url
     */
    public RESTSdmxBeanRetrievalManager(
            final StructureQueryBuilderRest restQueryBuilder,
            final StructureParsingManager structureParsingManager,
            final ReadableDataLocationFactory rdlFactory,
            final String restURL) {
        super();
        this.restQueryBuilder = Objects.requireNonNull(restQueryBuilder, "restQueryBuilder");
        this.spm = Objects.requireNonNull(structureParsingManager, "structureParsingManager");
        this.rdlFactory = rdlFactory != null ? rdlFactory : new SdmxSourceReadableDataLocationFactory();
        this.restURL = restURL;
    }

    @Override
    public SdmxBeans getMaintainables(RESTStructureQuery sQuery) {
        String restQuery = restURL + "/" + restQueryBuilder.buildStructureQuery(sQuery);
        URL restURL;
        try {
            restURL = new URL(restQuery);
        } catch (MalformedURLException e) {
            throw new SdmxException(e, "Could not open a conneciton to URL: " + restQuery);
        }
        ReadableDataLocation rdl = rdlFactory.getReadableDataLocation(restURL);
		SdmxBeans result = spm.parseStructures(rdl).getStructureBeans(false);
		rdl.close();
		return result;
    }

    @Override
    public SdmxBeans getSdmxBeans(StructureReferenceBean sRef, RESOLVE_CROSS_REFERENCES resolveCrossReferences) {
        STRUCTURE_REFERENCE_DETAIL refDetail;
        switch (resolveCrossReferences) {
            case DO_NOT_RESOLVE:
                refDetail = STRUCTURE_REFERENCE_DETAIL.NONE;
                break;
            default:
                refDetail = STRUCTURE_REFERENCE_DETAIL.DESCENDANTS;
                break;
        }
        STRUCTURE_QUERY_DETAIL queryDetail = STRUCTURE_QUERY_DETAIL.FULL;
        RESTStructureQuery query = new RESTStructureQueryImpl(queryDetail, refDetail, null, sRef, false);
        return getMaintainables(query);
    }

    @SuppressWarnings("unchecked")
    public <T extends MaintainableBean> Set<T> getMaintainableBeans(Class<T> structureType, MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        SDMX_STRUCTURE_TYPE type = SDMX_STRUCTURE_TYPE.ANY;
        if (structureType != null) {
            type = SDMX_STRUCTURE_TYPE.parseClass(structureType);
        }

        StructureReferenceBean sRef = new StructureReferenceBeanImpl(ref, type);
        STRUCTURE_REFERENCE_DETAIL refDetail = STRUCTURE_REFERENCE_DETAIL.NONE;
        STRUCTURE_QUERY_DETAIL queryDetail = returnStub ? STRUCTURE_QUERY_DETAIL.ALL_STUBS : STRUCTURE_QUERY_DETAIL.FULL;
        RESTStructureQuery query = new RESTStructureQueryImpl(queryDetail, refDetail, null, sRef, returnLatest);
        return (Set<T>) getMaintainables(query).getMaintainables(sRef.getMaintainableStructureType());
    }

    /**
     * Gets rest url.
     *
     * @return the rest url
     */
    protected String getRestURL() {
        return restURL;
    }


}
