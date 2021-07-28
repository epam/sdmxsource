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
package org.sdmxsource.sdmx.sdmxbeans.model.beans.reference;

import org.sdmxsource.sdmx.api.constants.DATA_QUERY_DETAIL;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.base.SdmxDate;
import org.sdmxsource.sdmx.api.model.beans.base.DataProviderSchemeBean;
import org.sdmxsource.sdmx.api.model.beans.reference.MaintainableRefBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.query.RESTDataQuery;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.SdmxDateImpl;
import org.sdmxsource.sdmx.util.beans.reference.StructureReferenceBeanImpl;
import org.sdmxsource.util.ObjectUtil;

import java.util.*;


/**
 * The type Rest data query.
 */
public class RESTDataQueryImpl implements RESTDataQuery {
    private static final long serialVersionUID = -3735483109111105532L;
    private static final String ALL = "all";
    private StructureReferenceBean flowRef;
    private StructureReferenceBean providerRef;
    private SdmxDate startPeriod;
    private SdmxDate endPeriod;
    private SdmxDate updatedAfter;
    private Integer firstNObs;
    private Integer lastNObs;
    private boolean includeHistory;
    private DATA_QUERY_DETAIL queryDetail = DATA_QUERY_DETAIL.FULL;
    private String dimensionAtObservation;
    private List<Set<String>> queryList = new ArrayList<Set<String>>();


    /**
     * Constructs a data query off a full or partial REST URL - the URL must start before the Data segment and be complete, example input:
     * /data/IMF,PGI,1.0/156.BCA.BOP.L_B.Q?detail=full
     *
     * @param restString the rest string
     */
    public RESTDataQueryImpl(String restString) {
        this(restString, null);
    }


    /**
     * Constructs a data query off a full or partial REST URL - the URL must start before the Data segment and be complete, example input:
     * /data/IMF,PGI,1.0/156.BCA.BOP.L_B.Q?detail=full
     *
     * @param restString      the rest string
     * @param queryParameters the query parameters
     */
    public RESTDataQueryImpl(String restString, Map<String, String> queryParameters) {
        int dataIndex = restString.indexOf("data/");
        if (dataIndex < 0) {
            throw new IllegalArgumentException("Data query expected to start with 'data/'");
        }
        String queryString = restString.substring(dataIndex);

        if (queryParameters == null) {
            queryParameters = new HashMap<String, String>();
        }

        //Parse any additional parameters
        if (queryString.indexOf("?") > 0) {
            String params = queryString.substring(queryString.indexOf("?") + 1);
            queryString = queryString.substring(0, queryString.indexOf("?"));

            for (String currentParam : params.split("&")) {
                String[] param = currentParam.split("=");
                if (param.length < 2) {
                    throw new SdmxSemmanticException("Incomplete assignment in data query! Parameter at fault: " + param);
                }
                queryParameters.put(param[0], param[1]);
            }
        }

        parserQueryString(queryString.split("/"));
        parserQueryParameters(queryParameters);
    }

    /**
     * Instantiates a new Rest data query.
     *
     * @param queryString     the query string
     * @param queryParameters the query parameters
     */
    public RESTDataQueryImpl(String[] queryString,
                             Map<String, String> queryParameters) {
        parserQueryString(queryString);
        parserQueryParameters(queryParameters);
    }

    @Override
    public String getRestQuery() {
        return toString();
    }

    private void parserQueryString(String[] queryString) {
        if (queryString.length < 2) {
            throw new SdmxSemmanticException("Data query expected to contain Flow as the second argument");
        }
        setFlowRef(queryString[1]);

        if (queryString.length < 3) {
            setKey(ALL);
        } else {
            setKey(queryString[2]);
        }
        if (queryString.length > 3) {
            setProvider(queryString[3]);
        }
        if (queryString.length > 4) {
            throw new SdmxSemmanticException("Data query expected unexpected 4th argument");
        }
    }


    @Override
    public StructureReferenceBean getFlowRef() {
        return flowRef;
    }

    private void setFlowRef(String flowRef) {
        if (!flowRef.equalsIgnoreCase(ALL)) {
            this.flowRef = parseFlowRef(flowRef);
        }
    }

    @Override
    public StructureReferenceBean getProviderRef() {
        return providerRef;
    }

    @Override
    public boolean includeHistory() {
        return includeHistory;
    }

    @Override
    public SdmxDate getStartPeriod() {
        return startPeriod;
    }

    private void setStartPeriod(String startPeriod) {
        try {
            this.startPeriod = new SdmxDateImpl(startPeriod);
        } catch (NumberFormatException e) {
            throw new SdmxSemmanticException("Could not format 'startPeriod' value " + startPeriod + " as a date");
        }
    }

    @Override
    public SdmxDate getEndPeriod() {
        return endPeriod;
    }

    private void setEndPeriod(String endPeriod) {
        try {
            this.endPeriod = new SdmxDateImpl(endPeriod);
        } catch (NumberFormatException e) {
            throw new SdmxSemmanticException("Could not format 'endPeriod' value " + endPeriod + " as a date");
        }
    }

    @Override
    public SdmxDate getUpdatedAfter() {
        return updatedAfter;
    }

    @Override
    public Integer getlastNObsertations() {
        return lastNObs;
    }

    @Override
    public Integer getFirstNObservations() {
        return firstNObs;
    }

    @Override
    public DATA_QUERY_DETAIL getQueryDetail() {
        return queryDetail;
    }

    @Override
    public String getDimensionAtObservation() {
        return dimensionAtObservation;
    }

    private void setDimensionAtObservation(String dimatObs) {
        this.dimensionAtObservation = dimatObs;
    }

    @Override
    public List<Set<String>> getQueryList() {
        return queryList;
    }

    private void setProvider(String provider) {
        if (!provider.equalsIgnoreCase(ALL)) {
            this.providerRef = parseProviderRef(provider);
        }
    }

    private StructureReferenceBean parseProviderRef(String str) {
        String split[] = str.split(",");
        if (split.length > 2) {
            throw new SdmxSemmanticException("Unexpected number of data provider reference arguments (, seperated) for reference: " + str + " - expecting a maximum of two arguments (Agency Id, Id)");
        }
        if (split.length == 2) {
            return new StructureReferenceBeanImpl(split[0], DataProviderSchemeBean.FIXED_ID, DataProviderSchemeBean.FIXED_VERSION, SDMX_STRUCTURE_TYPE.DATA_PROVIDER, split[1]);
        }
        return new StructureReferenceBeanImpl(null, DataProviderSchemeBean.FIXED_ID, DataProviderSchemeBean.FIXED_VERSION, SDMX_STRUCTURE_TYPE.DATA_PROVIDER, split[0]);
    }

    private StructureReferenceBean parseFlowRef(String str) {
        String split[] = str.split(",");
        if (split.length > 3) {
            throw new SdmxSemmanticException("Unexpected number of reference arguments (, separated) for reference: " + str + " - expecting a maximum of three arguments (Agency Id, Id, and Version)");
        }
        if (split.length > 2 && split[2].equalsIgnoreCase(ALL)) {
            split[2] = null;
        }
        if (split.length > 1 && split[1].equalsIgnoreCase(ALL)) {
            split[1] = null;
        }
        if (split.length > 0 && split[0].equalsIgnoreCase(ALL)) {
            split[0] = null;
        }
        if (split.length == 3) {
            return new StructureReferenceBeanImpl(split[0], split[1], split[2], SDMX_STRUCTURE_TYPE.DATAFLOW);
        }
        if (split.length == 2) {
            return new StructureReferenceBeanImpl(split[0], split[1], null, SDMX_STRUCTURE_TYPE.DATAFLOW);
        }
        return new StructureReferenceBeanImpl(null, split[0], null, SDMX_STRUCTURE_TYPE.DATAFLOW);
    }

    private void setKey(String key) {
        if (!key.equalsIgnoreCase(ALL)) {
            String[] split = key.split("\\.", Integer.MAX_VALUE);
            for (String currentKey : split) {
                Set<String> selectionsList = new HashSet<String>();
                queryList.add(selectionsList);
                for (String currentSelection : currentKey.split("\\+")) {
                    if (ObjectUtil.validString(currentSelection)) {
                        selectionsList.add(currentSelection);
                    }
                }
            }
        }
    }

    private void setDetail(String detail) {
        if (detail != null) {
            queryDetail = DATA_QUERY_DETAIL.parseString(detail);
        }
    }

    private void setUpdatedAfterDate(String updatedAfter) {
        try {
            this.updatedAfter = new SdmxDateImpl(updatedAfter);
        } catch (NumberFormatException e) {
            throw new SdmxSemmanticException("Could not format 'updatedAfter' value " + updatedAfter + " as a date");
        }
    }

    private void setLastXObs(String maxObs) {
        try {
            this.lastNObs = Integer.parseInt(maxObs);
        } catch (NumberFormatException e) {
            throw new SdmxSemmanticException("Query parameter 'lastNObservations' expects an Integer, was given: " + maxObs);
        }
    }

    private void setFirstXObs(String maxObs) {
        try {
            this.firstNObs = Integer.parseInt(maxObs);
        } catch (NumberFormatException e) {
            throw new SdmxSemmanticException("Query parameter 'firstNObservations' expects an Integer, was given: " + maxObs);
        }
    }

    private void parserQueryParameters(Map<String, String> params) {
        if (params != null) {
            for (String key : params.keySet()) {
                if (key.equalsIgnoreCase("startPeriod")) {
                    setStartPeriod(params.get(key));
                } else if (key.equalsIgnoreCase("endPeriod")) {
                    setEndPeriod(params.get(key));
                } else if (key.equalsIgnoreCase("updatedAfter")) {
                    setUpdatedAfterDate(params.get(key));
                } else if (key.equalsIgnoreCase("firstNObservations")) {
                    setFirstXObs(params.get(key));
                } else if (key.equalsIgnoreCase("lastNObservations")) {
                    setLastXObs(params.get(key));
                } else if (key.equalsIgnoreCase("dimensionAtObservation")) {
                    setDimensionAtObservation(params.get(key));
                } else if (key.equalsIgnoreCase("detail")) {
                    setDetail(params.get(key));
                } else if (key.equalsIgnoreCase("includeHistory")) {
                    includeHistory = Boolean.valueOf(params.get(key));
                } else {
                    throw new SdmxSemmanticException("Unknown query parameter  '" + key +
                            "' allowed parameters [startPeriod, endPeriod, updatedAfter, firstNObservations, lastNObservations, dimensionAtObservation, detail]");
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("data/");
        if (flowRef == null) {
            sb.append(ALL);
        } else {
            MaintainableRefBean flowRefMaint = flowRef.getMaintainableReference();
            String flowAgency = flowRefMaint.getAgencyId() == null ? ALL : flowRefMaint.getAgencyId();
            String flowId = flowRefMaint.getMaintainableId() == null ? ALL : flowRefMaint.getMaintainableId();
            String flowVersion = flowRefMaint.getVersion() == null ? ALL : flowRefMaint.getVersion();
            sb.append(flowAgency + "," + flowId + "," + flowVersion);
        }
        sb.append("/");
        String concat = "";
        if (!ObjectUtil.validCollection(queryList)) {
            sb.append(ALL);
        } else {
            for (Set<String> currentQuery : queryList) {
                sb.append(concat);
                concat = "";
                for (String code : currentQuery) {
                    sb.append(concat);
                    sb.append(code);
                    concat = "+";
                }
                concat = ".";
            }
        }
        sb.append("/");
        if (providerRef == null) {
            sb.append(ALL);
        } else {
            MaintainableRefBean provRefMaint = providerRef.getMaintainableReference();
            String provAgency = provRefMaint.getAgencyId() == null ? ALL : provRefMaint.getAgencyId();
            String provId = providerRef.getChildReference() == null ? ALL : providerRef.getChildReference().getId();
            sb.append(provAgency + "," + provId);
        }
        concat = "?";
        if (startPeriod != null) {
            sb.append(concat + "startPeriod=" + startPeriod.getDateInSdmxFormat());
            concat = "&";
        }
        if (endPeriod != null) {
            sb.append(concat + "endPeriod=" + endPeriod.getDateInSdmxFormat());
            concat = "&";
        }
        if (updatedAfter != null) {
            sb.append(concat + "updatedAfter=" + updatedAfter.getDateInSdmxFormat());
            concat = "&";
        }
        if (firstNObs != null) {
            sb.append(concat + "firstNObservations=" + firstNObs);
            concat = "&";
        }
        if (lastNObs != null) {
            sb.append(concat + "lastNObservations=" + lastNObs);
            concat = "&";
        }
        if (queryDetail != null) {
            sb.append(concat + "detail=" + queryDetail.getRestParam());
            concat = "&";
        }
        if (ObjectUtil.validString(dimensionAtObservation)) {
            sb.append(concat + "dimensionAtObservation=" + dimensionAtObservation);
            concat = "&";
        }
        return sb.toString();
    }

}
