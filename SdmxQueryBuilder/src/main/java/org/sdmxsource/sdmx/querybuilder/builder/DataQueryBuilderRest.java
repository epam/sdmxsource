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
package org.sdmxsource.sdmx.querybuilder.builder;

import org.sdmxsource.sdmx.api.builder.DataQueryBuilder;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.base.DataProviderBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataflowBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DimensionBean;
import org.sdmxsource.sdmx.api.model.data.query.DataQuery;
import org.sdmxsource.sdmx.api.model.data.query.DataQuerySelection;
import org.sdmxsource.sdmx.api.model.data.query.DataQuerySelectionGroup;
import org.sdmxsource.sdmx.util.date.DateUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * The type Data query builder rest.
 */
public class DataQueryBuilderRest implements DataQueryBuilder<String> {


    @Override
    public String buildDataQuery(DataQuery query) {
        if (query == null) {
            throw new IllegalArgumentException("DataQueryBuilderRest.buildRestDataQuery DataQuery is required, null was passed");
        }
        DataStructureBean keyFamily = query.getDataStructure();
        StringBuilder sb = new StringBuilder();
        sb.append("data/");

        if (query.getDataflow() != null) {
            DataflowBean dataflow = query.getDataflow();
            sb.append(dataflow.getAgencyId());
            sb.append(",");
            sb.append(dataflow.getId());
            sb.append(",");
            sb.append(dataflow.getVersion());
            sb.append("/");
        } else {
            throw new IllegalArgumentException("A REST query requires a dataflow to be set - no dataflow found for query");
        }
        Map<String, Set<String>> selections = new HashMap<String, Set<String>>();

        Date dateFrom = null;
        Date dateTo = null;

        if (query.hasSelections()) {
            DataQuerySelectionGroup dqsg = query.getSelectionGroups().get(0);
            if (dqsg.getDateFrom() != null) {
                dateFrom = dqsg.getDateFrom().getDate();
            }
            if (dqsg.getDateTo() != null) {
                dateTo = dqsg.getDateTo().getDate();
            }
            for (DataQuerySelection currentSelection : dqsg.getSelections()) {
                selections.put(currentSelection.getComponentId(), currentSelection.getValues());
            }
        }

        if (selections.size() == 0) {
            sb.append("all");
        } else {
            String concatPeriod = "";
            for (DimensionBean dim : keyFamily.getDimensions(SDMX_STRUCTURE_TYPE.DIMENSION)) {
                sb.append(concatPeriod);
                String conceptId = dim.getId();
                if (selections.containsKey(conceptId)) {
                    String concatPlus = "";
                    for (String currentSelection : selections.get(conceptId)) {
                        sb.append(concatPlus);
                        sb.append(currentSelection);
                        concatPlus = "+";
                    }
                }
                concatPeriod = ".";
            }
        }

        String providerAgency = null;
        String providerId = null;
        if (query.getDataProvider() != null) {
            for (DataProviderBean currentProvider : query.getDataProvider()) {
                if (providerAgency != null && !providerAgency.equals(currentProvider.getMaintainableParent().getAgencyId())) {
                    providerAgency = "*";
                } else {
                    providerAgency = currentProvider.getMaintainableParent().getAgencyId();
                }
                if (providerId != null && !providerId.equals(currentProvider.getId())) {
                    providerId = "all";
                } else {
                    providerId = currentProvider.getId();
                }
            }
        }
        if (!"all".equals(providerId) && (providerId != null)) {
            if (!"all".equals(providerAgency) && (providerAgency != null)) {
                sb.append("/" + providerAgency + "," + providerId + "/");
            } else {
                sb.append("/" + providerId + "/");
            }
        } else {
            sb.append("/all/");
        }


        boolean firstAppend = true;
        if (query.getFirstNObservations() != null) {
            appendParam(firstAppend, "firstNObservations", query.getFirstNObservations(), sb);
            firstAppend = false;
        }
        if (query.getLastNObservations() != null) {
            appendParam(firstAppend, "lastNObservations", query.getLastNObservations(), sb);
            firstAppend = false;
        }
        if (query.getDataQueryDetail() != null) {
            appendParam(firstAppend, "detail", query.getDataQueryDetail().getRestParam(), sb);
            firstAppend = false;
        }
        if (dateFrom != null) {
            appendParam(firstAppend, "startPeriod", DateUtil.formatDate(dateFrom), sb);
            firstAppend = false;
        }
        if (dateTo != null) {
            appendParam(firstAppend, "endPeriod", DateUtil.formatDate(dateTo), sb);
            firstAppend = false;
        }
        return sb.toString();
    }

    private void appendParam(boolean firstAppend, String param, Object value, StringBuilder sb) {
        if (firstAppend) {
            sb.append("?");
        } else {
            sb.append("&");
        }
        sb.append(param + "=" + value);
    }
}
