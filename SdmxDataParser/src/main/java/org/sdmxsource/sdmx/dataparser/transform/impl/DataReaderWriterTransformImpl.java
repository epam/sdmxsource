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
package org.sdmxsource.sdmx.dataparser.transform.impl;

import org.sdmxsource.sdmx.api.constants.TIME_FORMAT;
import org.sdmxsource.sdmx.api.engine.DataReaderEngine;
import org.sdmxsource.sdmx.api.engine.DataWriterEngine;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.AnnotationBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.AttributeBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DimensionBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.data.KeyValue;
import org.sdmxsource.sdmx.api.model.data.Keyable;
import org.sdmxsource.sdmx.api.model.data.Observation;
import org.sdmxsource.sdmx.api.model.header.DatasetHeaderBean;
import org.sdmxsource.sdmx.api.model.header.DatasetStructureReferenceBean;
import org.sdmxsource.sdmx.api.model.header.HeaderBean;
import org.sdmxsource.sdmx.dataparser.model.TransformOptions;
import org.sdmxsource.sdmx.dataparser.transform.DataReaderWriterTransform;
import org.sdmxsource.sdmx.sdmxbeans.model.data.KeyValueImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.data.KeyableImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.data.ObservationImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.header.DatasetStructureReferenceBeanImpl;
import org.sdmxsource.sdmx.util.date.DateUtil;
import org.sdmxsource.util.ObjectUtil;

import java.util.*;


/**
 * The type Data reader writer transform.
 */
public class DataReaderWriterTransformImpl implements DataReaderWriterTransform {

    private void pivot(DataReaderEngine dataReaderEngine, DataWriterEngine dataWriterEngine, TransformOptions options) {
        String pivotDimension = options.getPivotDimension();
        Map<String, DataWriterEngine> outputMap = new HashMap<String, DataWriterEngine>();

        DatasetHeaderBean datasetHeader = dataReaderEngine.getCurrentDatasetHeaderBean();

        String currentCrossSectionConcept = datasetHeader.getDataStructureReference().getDimensionAtObservation();
        boolean pivotIsTime = (pivotDimension.equals(DimensionBean.TIME_DIMENSION_FIXED_ID));
        boolean fromIsTimeSeries = datasetHeader.isTimeSeries();

        //Determine the series and observation attributes based on the new cross section, this is only possible if the data reader engine contains the keyFamily
        DataStructureBean keyFamily = dataReaderEngine.getDataStructure();
        List<String> seriesAttributeConcepts = null;
        List<String> obsAttributeConcepts = null;

        if (keyFamily != null) {
            //TODO what if there is no Key Family???
            seriesAttributeConcepts = getSeriesAttributes(pivotDimension, keyFamily);
            obsAttributeConcepts = getObsAttributes(pivotDimension, keyFamily);
        }

        Set<String> shortCodes = new HashSet<String>();

        Map<String, Keyable> keyMap = new HashMap<String, Keyable>();
        Map<String, Set<Observation>> obsMap = new HashMap<String, Set<Observation>>();

        while (dataReaderEngine.moveNextKeyable()) {
            Keyable key = dataReaderEngine.getCurrentKey();
            if (!key.isSeries()) {
                // Copy the Group verbatim and continue loop. There is no more useful information that can be obtained from the group
                writeKeyableToWriter(dataReaderEngine, dataWriterEngine, key, 0);
                continue;
            }
            String codeForPivot = pivotIsTime ? key.getObsTime() : key.getKeyValue(pivotDimension);

            while (dataReaderEngine.moveNextObservation()) {
                Observation obs = dataReaderEngine.getCurrentObservation();

                String currentObsCode = fromIsTimeSeries ? obs.getObsTime() : obs.getCrossSectionalValue().getCode();
                String newKeyShortCode = getKeyShortCode(key, currentObsCode, pivotDimension);
                Set<Observation> observations = obsMap.get(newKeyShortCode);
                if (!shortCodes.contains(newKeyShortCode)) {
                    shortCodes.add(newKeyShortCode);
                    keyMap.put(newKeyShortCode, createNewKey(key, pivotDimension, currentCrossSectionConcept, currentObsCode, pivotIsTime, fromIsTimeSeries, seriesAttributeConcepts));
                    observations = new HashSet<Observation>();
                    obsMap.put(newKeyShortCode, observations);
                }
                //TODO Obs Attributes
                observations.add(new ObservationImpl(key, codeForPivot, obs.getObservationValue(), obs.getAttributes()));
            }
        }
        for (String currentKey : keyMap.keySet()) {
            Keyable key = keyMap.get(currentKey);
            writeKeyableToWriter(dataWriterEngine, keyMap.get(currentKey), options.isIncludeAttributes());
            for (Observation obs : obsMap.get(currentKey)) {
                writeObsToWriter(dataWriterEngine, key, obs, options.isIncludeAttributes());
            }
        }
    }

    private String getKeyShortCode(Keyable currentKey, String promoteCode, String ignoreConcept) {
        String returnString = "";
        for (KeyValue kv : currentKey.getKey()) {
            if (kv.getConcept().equals(ignoreConcept)) {
                continue;
            }
            returnString += kv.getCode() + ":";
        }
        returnString += promoteCode;
        return returnString;
    }


    @Override
    public void copyToWriter(DataReaderEngine dataReaderEngine,
                             DataWriterEngine dataWriterEngine,
                             boolean includeObs,
                             boolean includeAttributes,
                             Integer maxObs,
                             Date dateFrom,
                             Date dateTo,
                             boolean copyHeader,
                             boolean closeWriter) {
        copyToWriter(dataReaderEngine, dataWriterEngine, null, includeObs, includeAttributes, -1, null, null, copyHeader, closeWriter);
    }

    @Override
    public void copyToWriter(DataReaderEngine dataReaderEngine, DataWriterEngine dataWriterEngine, boolean includeHeader, boolean closeOnCompletion) {
        copyToWriter(dataReaderEngine, dataWriterEngine, null, true, true, -1, null, null, includeHeader, closeOnCompletion);
    }

    @Override
    public void copyToWriter(DataReaderEngine dataReaderEngine, DataWriterEngine dataWriterEngine, String pivotDimension, boolean closeOnCompletion) {
        copyToWriter(dataReaderEngine, dataWriterEngine, pivotDimension, true, true, -1, null, null, true, closeOnCompletion);
    }

    private void copyToWriter(DataReaderEngine dataReaderEngine,
                              DataWriterEngine dataWriterEngine,
                              String pivotDimension,
                              boolean includeObs,
                              boolean includeAttributes,
                              Integer maxObs,
                              Date dateFrom,
                              Date dateTo,
                              boolean includeHeader,
                              boolean closeOnCompletion) {
        dataReaderEngine.reset();
        HeaderBean header = dataReaderEngine.getHeader();
        if (includeHeader && header != null) {
            dataWriterEngine.writeHeader(header);
        }
        try {
            DatasetHeaderBean dsHeader = null;
            List<KeyValue> attributeValues = null;
            while (dataReaderEngine.moveNextDataset()) {
                boolean startNewDataset = false;
                if (dataReaderEngine.getDatasetPosition() > 0) {
                    //CHECK PREVIOUS ATTS Vs CURRENT ATTS
                    startNewDataset = !ObjectUtil.containsAll(attributeValues, dataReaderEngine.getDatasetAttributes());
                }
                attributeValues = dataReaderEngine.getDatasetAttributes();
                dsHeader = copyDatasetToWriter(dsHeader, dataReaderEngine, dataWriterEngine, pivotDimension, includeObs, includeAttributes, maxObs,
                        dateFrom, dateTo, false, false, startNewDataset);
            }
        } finally {
            if (closeOnCompletion) {
                dataWriterEngine.close();
            }
        }
    }

    private DatasetHeaderBean copyDatasetToWriter(
            DatasetHeaderBean previousDatasetHeader,
            DataReaderEngine dataReaderEngine,
            DataWriterEngine dataWriterEngine,
            String pivotDimension,
            boolean includeObs,
            boolean includeAttributes,
            Integer maxObs,
            Date dateFrom,
            Date dateTo,
            boolean includeHeader,
            boolean closeOnCompletion,
            boolean forceStartNewDataset) {
        TransformOptions options = new TransformOptions();
        options.setPivotDimension(pivotDimension);
        options.setIncludeObs(includeObs);
        options.setMaxObs(maxObs);
        options.setDateFrom(dateFrom);
        options.setDateTo(dateTo);
        options.setIncludeHeader(includeHeader);
        options.setCloseWriter(closeOnCompletion);
        options.setIncludeAttributes(includeAttributes);
        return copyDatasetToWriter(previousDatasetHeader, dataReaderEngine, dataWriterEngine, options, forceStartNewDataset);
    }

    private DatasetHeaderBean copyDatasetToWriter(
            DatasetHeaderBean previousDatasetHeader,
            DataReaderEngine dataReaderEngine,
            DataWriterEngine dataWriterEngine,
            TransformOptions transformOptions,
            boolean forceStartNewDataset) {
        try {
            if (transformOptions.isIncludeHeader() && dataReaderEngine.getHeader() != null) {
                dataWriterEngine.writeHeader(dataReaderEngine.getHeader());
            }
            DatasetHeaderBean datasetHeader = dataReaderEngine.getCurrentDatasetHeaderBean();

            boolean fromIsTimeSeries = datasetHeader.isTimeSeries();

            String dimensionAtObs = datasetHeader.getDataStructureReference().getDimensionAtObservation();
            if (transformOptions.getPivotDimension() == null || (fromIsTimeSeries && transformOptions.getPivotDimension().equals(DimensionBean.TIME_DIMENSION_FIXED_ID)) || dimensionAtObs.equals(transformOptions.getPivotDimension())) {
                if (forceStartNewDataset || (previousDatasetHeader == null || !isHeaderEqual(datasetHeader, previousDatasetHeader))) {
                    dataWriterEngine.startDataset(dataReaderEngine.getProvisionAgreement(), dataReaderEngine.getDataFlow(), dataReaderEngine.getDataStructure(), datasetHeader);
                }
                for (KeyValue kv : dataReaderEngine.getDatasetAttributes()) {
                    dataWriterEngine.writeAttributeValue(kv.getConcept(), kv.getCode());
                }
                Keyable currentKey = null;
                while (true) {
                    try {
                        if (!dataReaderEngine.moveNextKeyable()) {
                            break;
                        }
                        currentKey = dataReaderEngine.getCurrentKey();
                    } catch (Throwable th) {
                        if (currentKey == null) {
                            throw new SdmxException(th, "Error while trying read first series key");
                        }
                        throw new SdmxException(th, "Error while trying to read next series/group in the DataSet.  The last sucessfully processed key was: " + currentKey);
                    }

                    try {
                        if (transformOptions.isIncludeObs()) {
                            writeKeyableToWriter(dataReaderEngine, dataWriterEngine, currentKey, transformOptions.getMaxObs(), transformOptions.getDateFrom(), transformOptions.getDateTo(), transformOptions.isIncludeAttributes());
                        } else {
                            writeKeyableToWriter(dataWriterEngine, currentKey, transformOptions.isIncludeAttributes());
                        }
                    } catch (Throwable th) {
                        throw new SdmxException(th, "Error occurred while processing " + currentKey);
                    }
                }
                return datasetHeader;
            } else {
                //Modify the header to change the dimension at observation
                String dsId = null;
                StructureReferenceBean structureRef = null;
                String serviceURL = null;
                String structureURL = null;

                if (datasetHeader.getDataStructureReference() != null) {
                    dsId = datasetHeader.getDataStructureReference().getId();
                    structureRef = datasetHeader.getDataStructureReference().getStructureReference();
                    serviceURL = datasetHeader.getDataStructureReference().getServiceURL();
                    structureURL = datasetHeader.getDataStructureReference().getStructureURL();
                } else {
                    structureRef = dataReaderEngine.getDataStructure().asReference();
                }

                DatasetStructureReferenceBean dsStructureRef = new DatasetStructureReferenceBeanImpl(dsId, structureRef, serviceURL, structureURL, transformOptions.getPivotDimension());
                DatasetHeaderBean modifiedDatasetHeader = datasetHeader.modifyDataStructureReference(dsStructureRef);

                if (previousDatasetHeader == null || !isHeaderEqual(datasetHeader, previousDatasetHeader)) {
                    dataWriterEngine.startDataset(dataReaderEngine.getProvisionAgreement(), dataReaderEngine.getDataFlow(), dataReaderEngine.getDataStructure(), modifiedDatasetHeader);
                }
                //The data reader is pivoting on a different dimension then the one we want to pivot on - start algorithm
                //We are pivoting on a different dimension to the current pivot
                pivot(dataReaderEngine, dataWriterEngine, transformOptions);
                return modifiedDatasetHeader;
            }
        } finally {
            if (transformOptions.isCloseWriter()) {
                dataWriterEngine.close();
            }
        }
    }


    /**
     * Is header equal boolean.
     *
     * @param one the one
     * @param two the two
     * @return the boolean
     */
    public boolean isHeaderEqual(DatasetHeaderBean one, DatasetHeaderBean two) {
        if (!ObjectUtil.equivalent(one.getAction(), two.getAction())) {
            return false;
        }
        if (!ObjectUtil.equivalent(one.getDataProviderReference(), two.getDataProviderReference())) {
            return false;
        }
        if (!ObjectUtil.equivalent(one.getDatasetId(), two.getDatasetId())) {
            return false;
        }
        if (!isHeaderEqual(one.getDataStructureReference(), two.getDataStructureReference())) {
            return false;
        }
        if (!ObjectUtil.equivalent(one.getPublicationPeriod(), two.getPublicationPeriod())) {
            return false;
        }
        if (!ObjectUtil.equivalent(one.getPublicationYear(), two.getPublicationYear())) {
            return false;
        }
        if (!ObjectUtil.equivalent(one.getReportingBeginDate(), two.getReportingBeginDate())) {
            return false;
        }
        if (!ObjectUtil.equivalent(one.getReportingEndDate(), two.getReportingEndDate())) {
            return false;
        }
        if (!ObjectUtil.equivalent(one.getValidFrom(), two.getValidFrom())) {
            return false;
        }
        if (!ObjectUtil.equivalent(one.getValidTo(), two.getValidTo())) {
            return false;
        }
        return true;
    }

    /**
     * Checks everything but the id
     *
     * @return
     */
    private boolean isHeaderEqual(DatasetStructureReferenceBean one, DatasetStructureReferenceBean two) {
        if (one == null) {
            if (two != null) {
                return false;
            }
            return true;
        }
        if (two == null) {
            return false;
        }
        if (one.isTimeSeries() != two.isTimeSeries()) {
            return false;
        }
        if (!ObjectUtil.equivalent(one.getStructureReference(), two.getStructureReference())) {
            return false;
        }
        if (!ObjectUtil.equivalent(one.getServiceURL(), two.getServiceURL())) {
            return false;
        }
        if (!ObjectUtil.equivalent(one.getStructureURL(), two.getStructureURL())) {
            return false;
        }
        if (!ObjectUtil.equivalent(one.getDimensionAtObservation(), two.getDimensionAtObservation())) {
            return false;
        }
        return true;
    }

    @Override
    public void copyDatasetToWriter(DataReaderEngine dataReaderEngine,
                                    DataWriterEngine dataWriterEngine,
                                    String pivotDimension,
                                    boolean includeObs,
                                    Integer maxObs,
                                    Date dateFrom,
                                    Date dateTo,
                                    boolean includeHeader,
                                    boolean closeOnCompletion) {
        copyDatasetToWriter(null, dataReaderEngine, dataWriterEngine, pivotDimension, includeObs, true, maxObs, dateFrom, dateTo,
                includeHeader, closeOnCompletion, true);
    }

    private List<String> getSeriesAttributes(String pivotDimension, DataStructureBean keyFamily) {
        List<String> returnList = new ArrayList<String>();
        for (AttributeBean currentAttribute : keyFamily.getSeriesAttributes(pivotDimension)) {
            returnList.add(currentAttribute.getId());
        }
        return returnList;
    }

    private List<String> getObsAttributes(String pivotDimension, DataStructureBean keyFamily) {
        List<String> returnList = new ArrayList<String>();
        for (AttributeBean currentAttribute : keyFamily.getObservationAttributes(pivotDimension)) {
            returnList.add(currentAttribute.getId());
        }
        return returnList;
    }

    private Keyable createNewKey(
            Keyable keyable,
            String ignoreConcept,
            String includeNewConcept,
            String newConceptValue,
            boolean movingToTimeSeries,
            boolean movingFromTimeSeries,
            List<String> seriesAttributeConcepts) {
        List<KeyValue> newKeyList = keyable.getKey();
        KeyValue removeKv = null;
        for (KeyValue kv : keyable.getKey()) {
            if (kv.getConcept().equals(ignoreConcept)) {
                removeKv = kv;
                break;
            }
        }
        List<KeyValue> newAttList = new ArrayList<KeyValue>();
        for (KeyValue currentAttr : keyable.getAttributes()) {
            if (seriesAttributeConcepts.contains(currentAttr.getConcept())) {
                newAttList.add(currentAttr);
            }
        }
        newKeyList.remove(removeKv);
        if (movingToTimeSeries) {
            newKeyList.add(new KeyValueImpl(keyable.getObsTime(), DimensionBean.TIME_DIMENSION_FIXED_ID));
        } else {
            newKeyList.add(new KeyValueImpl(newConceptValue, includeNewConcept));
        }
        return new KeyableImpl(keyable.getDataflow(), keyable.getDataStructure(), newKeyList, newAttList, null, null, null);
    }


    /**
     * Writes the keyable to the data writer engine, if includeObs is true and date from and to filter out all observation values for a key, then the key will not be written.
     */
    @Override
    public void writeKeyableToWriter(DataReaderEngine dataReaderEngine, DataWriterEngine dataWriterEngine, Keyable keyable, Integer maxObs, Date dateFrom, Date dateTo, boolean includeAttributes) {
        boolean writtenKey = false;
        boolean filteredObs = false;
        String fromDateFormatted = null;
        String toDateFormatted = null;

        boolean seriesKeysOnly = maxObs != null && maxObs == 0;
        if (keyable.isSeries() && !seriesKeysOnly) {
            //Filter out the observations from the series
            boolean hasMax = maxObs != null && maxObs > 0;
            List<Observation> obsList = new ArrayList<Observation>();
            Observation obs = null;
            while (true) {
                try {
                    if (!dataReaderEngine.moveNextObservation()) {
                        break;
                    }
                } catch (Throwable th) {
                    if (obs == null) {
                        throw new SdmxException(th, "Error occurred whilst trying to read first observation in key");
                    }
                    throw new SdmxException(th, "Error occurred whilst trying to determine if series key had another observation, last successfully processed observation: " + obs);
                }
                obs = dataReaderEngine.getCurrentObservation();
                if (dateFrom != null) {
                    if (fromDateFormatted == null) {
                        TIME_FORMAT format = obs.getObsTimeFormat();
                        fromDateFormatted = DateUtil.formatDate(dateFrom, format);
                    }
                    if (obs.getObsTime().compareTo(fromDateFormatted) < 0) {
                        filteredObs = true;
                        continue;
                    }
                }
                if (dateTo != null) {
                    if (toDateFormatted == null) {
                        TIME_FORMAT format = obs.getObsTimeFormat();
                        toDateFormatted = DateUtil.formatDate(dateTo, format);
                    }
                    if (obs.getObsTime().compareTo(toDateFormatted) > 0) {
                        filteredObs = true;
                        continue;
                    }
                }
                if (!writtenKey) {
                    writeKeyableToWriter(dataWriterEngine, keyable, includeAttributes);
                    writtenKey = true;
                }
                if (!hasMax) {
                    writeObsToWriter(dataWriterEngine, keyable, obs, includeAttributes);
                } else {
                    obsList.add(obs);
                }
            }
            //There was a limit on the number of obs, so now only write out the to 'x' results
            if (obsList.size() > 0) {
                Collections.sort(obsList);
                Collections.reverse(obsList);
                int loopCount = obsList.size() > maxObs ? maxObs : obsList.size();
                for (int i = 0; i < loopCount; i++) {
                    writeObsToWriter(dataWriterEngine, keyable, obsList.get(i), includeAttributes);
                }
            }
            if (!filteredObs && !writtenKey) {
                //If no observations were filtered and the key was not written, write the key out
                writeKeyableToWriter(dataWriterEngine, keyable, includeAttributes);
            }
        } else {
            //Either it is not a series, or observations are not to be included, either way the key needs to be written out
            writeKeyableToWriter(dataWriterEngine, keyable, includeAttributes);
        }
    }

    @Override
    public void writeObsToWriter(DataWriterEngine dataWriterEngine, Keyable keyable, Observation obs, boolean includeAttributes) {
        if (obs.isCrossSection()) {
            KeyValue crossSection = obs.getCrossSectionalValue();
            if (crossSection == null) {
                throw new SdmxSemmanticException("Dataset is cross sectional, missing cross section value for observation at time '" + keyable.getObsTime());
            }
            dataWriterEngine.writeObservation(crossSection.getConcept(), crossSection.getCode(), obs.getObservationValue(), getAnnotations(obs.getAnnotations()));
        } else {
            dataWriterEngine.writeObservation(DimensionBean.TIME_DIMENSION_FIXED_ID, obs.getObsTime(), obs.getObservationValue(), getAnnotations(obs.getAnnotations()));
        }
        if (includeAttributes && obs.getAttributes() != null) {
            for (KeyValue kv : obs.getAttributes()) {
                dataWriterEngine.writeAttributeValue(kv.getConcept(), kv.getCode());
            }
        }
    }


    @Override
    public void writeKeyableToWriter(DataReaderEngine dataReaderEngine, DataWriterEngine dataWriterEngine, Keyable keyable, Integer maxObs, Date dateFrom, Date dateTo) {
        writeKeyableToWriter(dataReaderEngine, dataWriterEngine, keyable, maxObs, dateFrom, dateTo, true);
    }

    @Override
    public void writeObsToWriter(DataWriterEngine dataWriterEngine, Keyable keyable, Observation obs) {
        writeObsToWriter(dataWriterEngine, keyable, obs, true);
    }

    @Override
    public void writeKeyableToWriter(DataReaderEngine dataReaderEngine, DataWriterEngine dataWriterEngine, Keyable keyable, Integer maxObs) {
        writeKeyableToWriter(dataReaderEngine, dataWriterEngine, keyable, maxObs, null, null, true);
    }

    /**
     * Writes the keyable to the datawriter engine
     *
     * @param dataWriterEngine
     * @param keyable
     */
    private void writeKeyableToWriter(DataWriterEngine dataWriterEngine, Keyable keyable, boolean includeAttributes) {
        if (keyable.isSeries()) {
            dataWriterEngine.startSeries(getAnnotations(keyable.getAnnotations()));
            for (KeyValue kv : keyable.getKey()) {
                dataWriterEngine.writeSeriesKeyValue(kv.getConcept(), kv.getCode());
            }
            if (!keyable.isTimeSeries()) {
                if (keyable.getObsTime() == null) {
                    throw new SdmxSemmanticException("Dataset is cross sectional, cross section is missing a time");
                }
                dataWriterEngine.writeSeriesKeyValue(DimensionBean.TIME_DIMENSION_FIXED_ID, keyable.getObsTime());
            }
        } else {
            dataWriterEngine.startGroup(keyable.getGroupName());
            for (KeyValue kv : keyable.getKey()) {
                dataWriterEngine.writeGroupKeyValue(kv.getConcept(), kv.getCode());
            }
        }

        if (includeAttributes && keyable.getAttributes() != null) {
            for (KeyValue kv : keyable.getAttributes()) {
                dataWriterEngine.writeAttributeValue(kv.getConcept(), kv.getCode());
            }
        }
    }

    private AnnotationBean[] getAnnotations(List<AnnotationBean> annotationList) {
        if (annotationList.size() == 0) {
            return null;
        }
        AnnotationBean[] annotationArr = new AnnotationBean[annotationList.size()];
        for (int i = 0; i < annotationList.size(); i++) {
            annotationArr[i] = annotationList.get(i);
        }
        return annotationArr;
    }
}
