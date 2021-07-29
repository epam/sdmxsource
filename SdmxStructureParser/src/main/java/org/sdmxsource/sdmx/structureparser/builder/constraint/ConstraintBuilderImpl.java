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
package org.sdmxsource.sdmx.structureparser.builder.constraint;

import org.sdmxsource.sdmx.api.builder.ConstraintBuilder;
import org.sdmxsource.sdmx.api.engine.DataReaderEngine;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.DataSourceBean;
import org.sdmxsource.sdmx.api.model.beans.reference.MaintainableRefBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ContentConstraintBean;
import org.sdmxsource.sdmx.api.model.data.KeyValue;
import org.sdmxsource.sdmx.api.model.data.Keyable;
import org.sdmxsource.sdmx.api.model.data.Observation;
import org.sdmxsource.sdmx.api.model.header.DatasetHeaderBean;
import org.sdmxsource.sdmx.api.model.mutable.registry.*;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.registry.*;

import java.util.*;


/**
 * The type Constraint builder.
 */
public class ConstraintBuilderImpl implements ConstraintBuilder {

    public ContentConstraintBean buildConstraint(DataReaderEngine dre,
                                                 StructureReferenceBean attachment,
                                                 DataSourceBean dsAttachement,
                                                 boolean indexAttributes, boolean indexDataset,
                                                 boolean indexReportingPeriod, boolean indexTimeSeries,
                                                 boolean definingDataPresent, MaintainableRefBean refParams) {

        dre.reset();
        //TODO Should it check if there is more then one dataset in the datasource
        if (!dre.moveNextDataset()) {
            throw new SdmxSemmanticException("Can not index time series for registered datasource, the data retrieved from the datasource does not contain a dataset");
        }

        if (!indexAttributes && !indexDataset && !indexReportingPeriod && !indexTimeSeries) {
            return null;
        }

        DatasetHeaderBean header = dre.getCurrentDatasetHeaderBean();
        if (indexTimeSeries && !header.isTimeSeries()) {
            throw new SdmxSemmanticException("Can not index time series for registered datasource, the data retrieved from the datasource is not time series");
        }

        //Create mutable Maintainable
        ContentConstraintMutableBean mutableBean = new ContentConstraintMutableBeanImpl();
        mutableBean.setAgencyId(refParams.getAgencyId());
        mutableBean.setId(refParams.getMaintainableId());
        mutableBean.setVersion(refParams.getVersion());
        mutableBean.addName("en", "Generated Constraint");
        mutableBean.addDescription("en", "Constraint built from dataset");
        mutableBean.setIsDefiningActualDataPresent(true);
        mutableBean.setConstraintAttachment(buildAttachement(attachment, dsAttachement));

        ConstraintDataKeySetMutableBean dataKeySet = null;

        Map<String, Set<String>> cubeRegionMap = new HashMap<String, Set<String>>();
        Map<String, Set<String>> attributeMap = new HashMap<String, Set<String>>();
        Date reportFrom = null;
        Date reportTo = null;
        Set<String> processedDates = new HashSet<String>();

        while (dre.moveNextKeyable()) {
            Keyable key = dre.getCurrentKey();
            if (key.isSeries()) {
                //TODO Check if Cross Sectional and put out exception if it is
                //1. If indexing the time series store the time Series Key on the Constraint
                if (indexTimeSeries) {
                    if (dataKeySet == null) {
                        dataKeySet = new ConstraintDataKeySetMutableBeanImpl();
                        mutableBean.setIncludedSeriesKeys(dataKeySet);
                    }
                    ConstrainedDataKeyMutableBean dataKey = new ConstrainedDataKeyMutableBeanImpl();
                    dataKey.setKeyValues(key.getKey());
                    dataKeySet.addConstrainedDataKey(dataKey);
                }
                //2. If indexing the dataset, store the individual code values
                if (indexDataset) {
                    storeKeyValuesOnMap(key.getKey(), cubeRegionMap);
                }
                //3. If indexing attributes, store the individual code values for each attribute
                if (indexAttributes) {
                    storeKeyValuesOnMap(key.getAttributes(), attributeMap);
                }
            }
            if (indexAttributes || indexReportingPeriod) {
                while (dre.moveNextObservation()) {
                    Observation obs = dre.getCurrentObservation();
                    //If indexing the dates, determine the data start and end dates from the obs dates
                    //To save time, do not process the same date twice
                    if (indexReportingPeriod) {
                        if (!processedDates.contains(obs.getObsTime())) {
                            Date obsDate = obs.getObsAsTimeDate();
                            if (reportFrom == null || reportFrom.getTime() > obsDate.getTime()) {
                                reportFrom = obsDate;
                            }
                            if (reportTo == null || reportTo.getTime() < obsDate.getTime()) {
                                reportTo = obsDate;
                            }
                            processedDates.add(obs.getObsTime());
                        }
                    }
                    if (indexAttributes) {
                        storeKeyValuesOnMap(obs.getAttributes(), attributeMap);
                    }
                }
            }
        }
        if (indexAttributes || indexDataset) {
            CubeRegionMutableBean cubeRegionMutableBean = new CubeRegionMutableBeanImpl();
            mutableBean.setIncludedCubeRegion(cubeRegionMutableBean);
            if (indexAttributes) {
                createKeyValues(attributeMap, cubeRegionMutableBean.getAttributeValues());
            }
            if (indexDataset) {
                createKeyValues(cubeRegionMap, cubeRegionMutableBean.getKeyValues());
            }
        }
        if (indexReportingPeriod && reportFrom != null && reportTo != null) {
            ReferencePeriodMutableBean refPeriodMutable = new ReferencePeriodMutableBeanImpl();
            refPeriodMutable.setEndTime(reportTo);
            refPeriodMutable.setStartTime(reportFrom);
            mutableBean.setReferencePeriod(refPeriodMutable);
        }

        return mutableBean.getImmutableInstance();
    }

    private void createKeyValues(Map<String, Set<String>> cubeRegionMap, List<KeyValuesMutable> populateMap) {
        for (String currentConcept : cubeRegionMap.keySet()) {
            KeyValuesMutable kvs = new KeyValuesMutableImpl();
            kvs.setId(currentConcept);
            kvs.setKeyValues(new ArrayList<String>(cubeRegionMap.get(currentConcept)));
            populateMap.add(kvs);
        }
    }

    private void storeKeyValuesOnMap(List<KeyValue> kvs, Map<String, Set<String>> cubeRegionMap) {
        for (KeyValue kv : kvs) {
            Set<String> valuesForConcept = cubeRegionMap.get(kv.getConcept());
            if (valuesForConcept == null) {
                valuesForConcept = new HashSet<String>();
                cubeRegionMap.put(kv.getConcept(), valuesForConcept);
            }
            valuesForConcept.add(kv.getCode());
        }
    }


    private ConstraintAttachmentMutableBean buildAttachement(StructureReferenceBean attachment, DataSourceBean dsAttachement) {
        ConstraintAttachmentMutableBean mutable = new ContentConstraintAttachmentMutableBeanImpl();
        //		DataSourceMutableBean datasource = dsAttachement == null ? null : dsAttachement.get;
        //		if(datasource.isSimpleDatasource()) {
        //			//HACK The schema does not allow a constraint to be attached to both a provision and a simple datasource, so here we are setting simple datasouce to false, so that
        //			//the constraint will be attached to a provision and a queryable datasource - but the queryable datasource will have the following two attributes set
        //			//isWebServiceDatasource="false" isRESTDatasource="false"
        //			//This can be inferred by a system that the datasource is simple
        //			datasource.setSimpleDatasource(false);
        //		}
        //		mutable.addDataSouces(datasource);
        mutable.addStructureReference(attachment);
        return mutable;
    }
}
