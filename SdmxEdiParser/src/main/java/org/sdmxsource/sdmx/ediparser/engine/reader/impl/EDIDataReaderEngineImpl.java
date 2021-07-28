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
package org.sdmxsource.sdmx.ediparser.engine.reader.impl;

import org.sdmxsource.sdmx.api.constants.*;
import org.sdmxsource.sdmx.api.engine.DataReaderEngine;
import org.sdmxsource.sdmx.api.exception.DataSetStructureReferenceException;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.exception.SdmxSyntaxException;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataflowBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DimensionBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.GroupBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ProvisionAgreementBean;
import org.sdmxsource.sdmx.api.model.data.KeyValue;
import org.sdmxsource.sdmx.api.model.data.Keyable;
import org.sdmxsource.sdmx.api.model.data.Observation;
import org.sdmxsource.sdmx.api.model.header.DatasetHeaderBean;
import org.sdmxsource.sdmx.api.model.header.HeaderBean;
import org.sdmxsource.sdmx.ediparser.constants.EDI_PREFIX;
import org.sdmxsource.sdmx.ediparser.constants.EDI_TIME_FORMAT;
import org.sdmxsource.sdmx.ediparser.constants.SDMX_EDI_ATTRIBUTES;
import org.sdmxsource.sdmx.ediparser.model.reader.EDIDataReader;
import org.sdmxsource.sdmx.ediparser.util.EDIUtil;
import org.sdmxsource.sdmx.sdmxbeans.model.data.KeyValueImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.data.KeyableImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.data.ObservationImpl;
import org.sdmxsource.sdmx.util.date.DateUtil;
import org.sdmxsource.util.ObjectUtil;

import javax.xml.bind.ValidationException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.*;

/**
 * The type Edi data reader engine.
 */
public class EDIDataReaderEngineImpl implements DataReaderEngine {
    /**
     * The Has next.
     */
    protected boolean hasNext = true;    //End of File
    /**
     * The Has next obs.
     */
    protected boolean hasNextObs = true; //End of Obs for current Key
    /**
     * The Is time series.
     */
    protected boolean isTimeSeries = true;  //Default is time series
    /**
     * The Cross section concept.
     */
    protected String crossSectionConcept;
    /**
     * The Current obs.
     */
    protected Observation currentObs;
    /**
     * The Current key.
     */
    protected Keyable currentKey;
    /**
     * The In foot note section.
     */
    boolean inFootNoteSection;
    //Set these to Version 1, but depending on the DSD they may be updated to version 2 concept ids
    private String obsConf = SDMX_EDI_ATTRIBUTES.OBS_CONF_V1;
    private String obsPreBreak = SDMX_EDI_ATTRIBUTES.OBS_PRE_BREAK_V1;
    private String SIBLING_GROUP = EDIUtil.getSiblingGroupId();
    private DataStructureBean dataStructureBean;
    private EDIDataReader ediDataReader;
    private String missingValue;
    private String[] dimensions;
    private String[] observations = null;
    private int currentObsPos = -1;
    private int keyablePosition = -1;
    private int seriesPosition = -1;
    private boolean hasObsStatus;
    private boolean hasObsConf;
    private boolean hasObsPreBeak;
    private List<String> obsDates;
    private EDI_TIME_FORMAT ediTimeFormat;
    //This is true when the reader has read the next key to see if it was the same as the previous
    //In this instance we do not want the user's call to moveNextKeyable() to actually do anything other then set this back to false, as the next key has already been read
    private boolean lookedAhead = false;
    private boolean hasCurrentKey = false;

    private List<KeyValue> datasetAttributes = new ArrayList<KeyValue>();


    //Date Caches
    private Map<String, String> dateMap = new HashMap<String, String>();   //EDI date String to date


    private Map<TIME_FORMAT, CachedTimeRanges> timeRangeCache = new HashMap<TIME_FORMAT, EDIDataReaderEngineImpl.CachedTimeRanges>();
    private List<EDIDataReader> ediDataReaders = new ArrayList<EDIDataReader>();
    private int currentlyProcessingReaderIndex = -1;
    private HeaderBean header;
    private SdmxBeanRetrievalManager beanRetrievalManager;
    private DataflowBean dataflowBean;

    /**
     * Create a reader that can iterate over datasets containing data for many dsds
     *
     * @param header               the header
     * @param beanRetrievalManager the bean retrieval manager
     * @param ediDataReaders       the edi data readers
     */
    public EDIDataReaderEngineImpl(HeaderBean header, SdmxBeanRetrievalManager beanRetrievalManager, List<EDIDataReader> ediDataReaders) {
        this.header = header;
        this.ediDataReaders = ediDataReaders;
        this.beanRetrievalManager = beanRetrievalManager;
    }

    /**
     * Create a reader that is reading datasets conforming to only one dsd
     *
     * @param header            the header
     * @param dataflow          the dataflow
     * @param dataStructureBean the data structure bean
     * @param ediDataReaders    the edi data readers
     */
    public EDIDataReaderEngineImpl(HeaderBean header, DataflowBean dataflow, DataStructureBean dataStructureBean, List<EDIDataReader> ediDataReaders) {
        this.header = header;
        this.dataStructureBean = dataStructureBean;
        this.dataflowBean = dataflow;
        this.ediDataReaders = ediDataReaders;
    }

    /**
     * Create a reader that is reading only a single dataset
     *
     * @param header            the header
     * @param dataflowBean      the dataflow bean
     * @param dataStructureBean the data structure bean
     * @param ediDataReader     the edi data reader
     */
    public EDIDataReaderEngineImpl(HeaderBean header, DataflowBean dataflowBean, DataStructureBean dataStructureBean, EDIDataReader ediDataReader) {
        this.header = header;
        this.dataStructureBean = dataStructureBean;
        this.dataflowBean = dataflowBean;
        ediDataReaders.add(ediDataReader);
    }

    @Override
    public HeaderBean getHeader() {
        return header;
    }

//	@Override
//	public String getDataFormat() {
//		return "SDMX-EDI";
//	}

    @Override
    public DataReaderEngine createCopy() {
        List<EDIDataReader> copyReaders = new ArrayList<EDIDataReader>();
        for (EDIDataReader reader : ediDataReaders) {
            copyReaders.add(reader.createCopy());
        }
        if (beanRetrievalManager != null) {
            return new EDIDataReaderEngineImpl(header, beanRetrievalManager, copyReaders);
        }
        return new EDIDataReaderEngineImpl(header, dataflowBean, dataStructureBean, copyReaders);
    }

    @Override
    public DataStructureBean getDataStructure() {
        return dataStructureBean;
    }

    @Override
    public DataflowBean getDataFlow() {
        return dataflowBean;
    }

    @Override
    public ProvisionAgreementBean getProvisionAgreement() {
        return null;
    }

    private void obtainDataStructureInformation() {
        List<String> dimList = new ArrayList<String>();
        for (DimensionBean currentDimension : dataStructureBean.getDimensions(SDMX_STRUCTURE_TYPE.DIMENSION)) {
            dimList.add(currentDimension.getId());
        }
        for (GroupBean group : dataStructureBean.getGroups()) {
            if (group.getId().toLowerCase().startsWith("sibling")) {
                SIBLING_GROUP = group.getId();
            }
        }
        dimensions = new String[dimList.size()];
        dimList.toArray(dimensions);
        determineObsAttributes();
    }

    private void start() {
        ediDataReader = ediDataReaders.get(currentlyProcessingReaderIndex);
        ediDataReader.resetReader();
        inFootNoteSection = false;
        this.missingValue = ediDataReader.getMissingValue();
        if (beanRetrievalManager != null) {
            this.dataStructureBean = beanRetrievalManager.getMaintainableBean(DataStructureBean.class, ediDataReader.getDatasetHeaderBean().getDataStructureReference().getStructureReference().getMaintainableReference());
        }
        if (dataStructureBean == null) {
            throw new DataSetStructureReferenceException(ediDataReader.getDatasetHeaderBean().getDataStructureReference().getStructureReference());
        }

        if (dataStructureBean.getAttribute(SDMX_EDI_ATTRIBUTES.OBS_CONF_V1) == null) {
            if (dataStructureBean.getAttribute(SDMX_EDI_ATTRIBUTES.OBS_CONF_V2) != null) {
                obsConf = SDMX_EDI_ATTRIBUTES.OBS_CONF_V2;
            }
        }

        if (dataStructureBean.getAttribute(SDMX_EDI_ATTRIBUTES.OBS_PRE_BREAK_V1) == null) {
            if (dataStructureBean.getAttribute(SDMX_EDI_ATTRIBUTES.OBS_PRE_BREAK_V2) != null) {
                obsPreBreak = SDMX_EDI_ATTRIBUTES.OBS_PRE_BREAK_V2;
            }
        }

        this.datasetAttributes = ediDataReader.getDatasetAttributes();
        obtainDataStructureInformation();
    }

    /**
     * Determines if a DSD has obs status, observation confidentiality and
     * observation pre-break attributes present as observation level attributes
     */
    private void determineObsAttributes() {
        hasObsStatus = dataStructureBean.getObservationAttribute(SDMX_EDI_ATTRIBUTES.OBS_STATUS) != null;
        hasObsConf = dataStructureBean.getObservationAttribute(obsConf) != null;
        hasObsPreBeak = dataStructureBean.getObservationAttribute(obsPreBreak) != null;
    }

    private void assertMoveNext() {
        if (!ediDataReader.moveNext()) {
            throw new SdmxSyntaxException("Unexpected end of file" + ediDataReader.getCurrentLine());
        }
    }

    @Override
    public DatasetHeaderBean getCurrentDatasetHeaderBean() {
        return ediDataReader.getDatasetHeaderBean();
    }

    /**
     * Is time series boolean.
     *
     * @return the boolean
     */
    public boolean isTimeSeries() {
        return true;
    }

    /**
     * Gets cross section concept.
     *
     * @return the cross section concept
     */
    public String getCrossSectionConcept() {
        return DimensionBean.TIME_DIMENSION_FIXED_ID;
    }

    @Override
    public int getDatasetPosition() {
        return currentlyProcessingReaderIndex;
    }

    @Override
    public int getObsPosition() {
        return currentObsPos;
    }

    @Override
    public Keyable getCurrentKey() {
        return currentKey;
    }

    @Override
    public List<KeyValue> getDatasetAttributes() {
        return new ArrayList<KeyValue>(datasetAttributes);
    }

    @Override
    public boolean moveNextDataset() {
        currentlyProcessingReaderIndex++;
        if (ediDataReaders.size() > currentlyProcessingReaderIndex) {
            ediDataReader = ediDataReaders.get(currentlyProcessingReaderIndex);
            start();
            return true;
        }
        return false;
    }

    @Override
    public boolean moveNextKeyable() {
        if (currentlyProcessingReaderIndex < 0) {
            if (!moveNextDataset()) {
                return false;
            }
        }
        if (lookedAhead == true) {
            lookedAhead = false;
            return hasCurrentKey;
        }
        try {
            while (ediDataReader.moveNext()) {
                if (ediDataReader.getLineType() == EDI_PREFIX.DATASET_DATA) {
                    processEDIDataLine();
                    hasCurrentKey = true;
                    keyablePosition++;
                    seriesPosition++;
                    return true;
                }
                if (ediDataReader.getLineType() == EDI_PREFIX.DATASET_FOOTNOTE_SECTION) {
                    inFootNoteSection = true;
                    observations = null;
                    assertMoveNext();
                    if (processEDIAttributeSegment()) {
                        hasCurrentKey = true;
                        keyablePosition++;
                        return true;
                    }
                } else if (ediDataReader.getLineType() == EDI_PREFIX.DATASET_DATAATTRIBUTE) {
                    if (!inFootNoteSection) {
                        throw new ValidationException("Can not process attributes, no foot note section declared (FNS)");
                    }
                    processEDIAttributeSegment();
                    keyablePosition++;
                    hasCurrentKey = true;
                    return true;
                }
            }
            hasCurrentKey = false;
            return false;
        } catch (Throwable th) {
            if (ediDataReader.getLineType() == null) {
                throw new SdmxException(th, "Error while processsing EDI Segment (unknown prefix):  " + ediDataReader.getCurrentLine());
            }
            throw new SdmxException(th, "Error while processsing EDI Segment:  " + ediDataReader.getLineType().getPrefix() + ediDataReader.getCurrentLine());
        }
    }

    @Override
    public Observation getCurrentObservation() {
        return currentObs;
    }

    @Override
    public int getKeyablePosition() {
        return keyablePosition;
    }

    @Override
    public int getSeriesPosition() {
        return seriesPosition;
    }

    private void processEDIDataLine() {
        currentObsPos = -1;

        //Observations becomes all of the observations for the series, unfortunately the first item in the array is the key & observation value
        //So we need to extract the first value and process it, and then replace the first value with the observation minus the key
        observations = EDIUtil.splitOnPlus(ediDataReader.getCurrentLine());
        String firstDataLine = observations[0];

        String[] dataLineSplit = EDIUtil.splitOnColon(firstDataLine);

        //Get the series key from the ARR segment.
        //Note: if this is a partial key, then it is assumed to be the deletion of the sibling group, as per the EDI Specification:

//		2520 Deletion of a group of sibling series;
//		2521 Rule: dates/periods/time ranges cannot be present in segment;
//		2522 e.g. if :BE:XXX:YYY is the key of the sibling group (second position: frequency wildcarded: for
//		2523 any frequency), then the segment
//		2524 ARR++:BE:XXX:YYY'
//		2525 implies the deletion of all series of the group (e.g. Q:BE:XXX:YYY and M:BE:XXX:YYY) and all
//		2526 their attribute values at all
        List<KeyValue> seriesKey = new ArrayList<KeyValue>();
        if (dataLineSplit.length < dimensions.length) {
            throw new SdmxSemmanticException("Reported key '" + firstDataLine + "' is less then key length '" + dimensions.length + "' ");
        }

        boolean isGroup = false;
        for (int i = 0; i < dimensions.length; i++) {
            String val = dataLineSplit[i];
            if (val.length() == 0) {
                isGroup = true;
            } else {
                seriesKey.add(new KeyValueImpl(val, dimensions[i]));
            }
        }

        //observations[0] contained both the series key (or partial key) and the first observation, we want to extract from this ONLY the observation (not the series Key)
        // and store the in the first observation location observations[0]
        observations[0] = null;
        String firstObservation = "";
        for (int i = dimensions.length + 2; i < dataLineSplit.length; i++) {
            firstObservation += dataLineSplit[i];
            if (dataLineSplit.length > (i + 1)) {
                firstObservation += ":";
            }
        }

        if (ObjectUtil.validString(firstObservation)) {
            observations[0] = firstObservation;
        } else {
            observations = null;
        }

        // At this point the observations array will have been populated. Validate it.
        validateObservation(observations);

        processObservations(dataLineSplit);

        TIME_FORMAT timeFormat = ediTimeFormat == null ? null : ediTimeFormat.getSdmxTimeFormat();
        if (isGroup) {
            this.currentKey = new KeyableImpl(dataflowBean, dataStructureBean, seriesKey, null, SIBLING_GROUP);
        } else {
            this.currentKey = new KeyableImpl(dataflowBean, dataStructureBean, seriesKey, null, timeFormat);
        }
    }

    /**
     * This method tests the array of observations being passed in and throws an exception if the String contains
     * any illegal characters. If the characters supplied are all valid, no action is performed.
     * The rules for this method come from the EDI guide, lines 2018 onwards.
     *
     * @param observations the observations
     */
    public void validateObservation(String[] observations) {
        if (observations == null) {
            return;
        }

        for (String string : observations) {
            // The values being passed in here are of the format:
            //    123:A
            // So everything after the first colon MUST be removed
            String aVal = EDIUtil.splitOnColon(string)[0];
            if (aVal.length() > 15) {
                throw new SdmxSyntaxException("Illegal observation value - it exceeds 15 characters. Observation value: " + aVal + ". Line being processed: " + ediDataReader.getCurrentLine() + " within " + currentKey);
            }

            // Ensure that the value passed in is legal.
            // Legal values are: blank; a dash (indicates the absence of a value (EDI guide line 2035)); or a legal numeric.
            // Legal numerics means catering for decimals, negatives and the use of exponent (E)
            if (!(aVal.equals("-") || aVal.equals(""))) {
                try {
                    // Use BigDecimal to test that the value is a legal numeric
                    new BigDecimal(aVal);
                } catch (NumberFormatException e) {
                    throw new SdmxSyntaxException("Illegal observation value. The observation: " + aVal + " is invalid. Observations must be valid numerics. Line being processed: " + ediDataReader.getCurrentLine());
                }
            }
        }
    }

    /**
     * Processes the observations by storing the observation dates in a list in the order in which the observations are reported.
     * <p>
     * If there are no observations to process (as this could be a delete message deleting a series, or a group of series) then the obsDates list will be empty
     *
     * @param dataLineSplit
     */
    private void processObservations(String[] dataLineSplit) {
        obsDates = new ArrayList<String>();
        if (dataLineSplit.length > dimensions.length) {
            String reportedDate = dataLineSplit[dimensions.length];
            String dateFormat = dataLineSplit[dimensions.length + 1];
            ediTimeFormat = EDI_TIME_FORMAT.parseString(dateFormat);

            // If there are no observations but we have a time range and this is a delete message,
            // then create observations with no value for each of the entries in the time range.
            if (observations == null && ediTimeFormat.isRange() &&
                    getCurrentDatasetHeaderBean().getAction() == DATASET_ACTION.DELETE) {
                Date startDate = ediTimeFormat.parseDate(reportedDate);
                Date endDate = ediTimeFormat.parseEndDate(reportedDate);
                obsDates = DateUtil.createTimeValues(startDate, endDate, ediTimeFormat.getSdmxTimeFormat());
            } else {
                if (observations != null && observations.length > 1) {
                    processObservationRange(reportedDate);
                } else {
                    processSingleObservation(reportedDate);
                }
            }
        }
    }

    private void processObservationRange(String reportedDate) {
        if (!ediTimeFormat.isRange()) {
            throw new SdmxSyntaxException("More than one observation found for a ARR segment declaring a single time period");
        }
        CachedTimeRanges timeRanges = timeRangeCache.get(ediTimeFormat.getSdmxTimeFormat());
        if (timeRanges == null) {
            timeRanges = new CachedTimeRanges();
            timeRangeCache.put(ediTimeFormat.getSdmxTimeFormat(), timeRanges);
        }

        Map<String, List<String>> timeRangeMap = timeRanges.getTimeRangeMap();
        if (timeRangeMap.containsKey(reportedDate)) {
            obsDates = timeRangeMap.get(reportedDate);
        } else {
            Date startDate = ediTimeFormat.parseDate(reportedDate);
            Date endDate = ediTimeFormat.parseEndDate(reportedDate);
            obsDates = DateUtil.createTimeValues(startDate, endDate, ediTimeFormat.getSdmxTimeFormat());
            if (obsDates.size() != observations.length) {
                throw new SdmxSyntaxException("Expecting '" + obsDates.size() + "' observations for time range '" + reportedDate + "' in format '" + ediTimeFormat + "' but got '" + observations.length + "'");
            }
            timeRangeMap.put(reportedDate, obsDates);
        }
        if (obsDates.size() != observations.length) {
            throw new SdmxSyntaxException("ARR segment decares time range requiring " + obsDates.size() + " observations, only " + observations.length + " observations reported");
        }
    }

    private void processSingleObservation(String reportedDate) {
        String mapKey = ediTimeFormat.getEdiValue() + ":" + reportedDate;
        if (dateMap.containsKey(mapKey)) {
            obsDates.add(dateMap.get(mapKey));
        } else {
            Date startDate = ediTimeFormat.parseDate(reportedDate);
            String parsedDate = DateUtil.formatDate(startDate, ediTimeFormat.getSdmxTimeFormat());
            obsDates.add(parsedDate);
            dateMap.put(mapKey, parsedDate);
        }
    }

    /**
     * Processes the attribute segment
     *
     * @return true if the attribute was not a dataset attribute.  If the attribtue was a dataset attribute, then it can be retrieved from the getDatasetAttribute method
     */
    private boolean processEDIAttributeSegment() {
        //TODO Process FNS+ - current line, does this need any processing?

        //Move to the attribute scope line

        if (EDIUtil.assertPrefix(ediDataReader, EDI_PREFIX.DATASET_ATTRIBUTE_SCOPE, false)) {
            int scope = Integer.parseInt(ediDataReader.getCurrentLine());
            //1 = dataset, 4=mix of dimensions, 5=observation
            if (scope == 1) {
                //This is a Dataset attribute which we want to ignore because this reader is passed all the dataset attribtues up front.
                //Move to the next line and return false to ensure this is not processed
                ediDataReader.moveNext();
                EDIUtil.assertPrefix(ediDataReader, EDI_PREFIX.DATASET_DATAATTRIBUTE, true);
                return false;
            }
        } else {
            ediDataReader.moveBackLine();
        }


        //Move to the dimension/key pointer line
        assertMoveNext();
        EDIUtil.assertPrefix(ediDataReader, EDI_PREFIX.DATASET_DATAATTRIBUTE, true);
        String currentLine = ediDataReader.getCurrentLine();
        String[] posKeySplit = EDIUtil.splitOnPlus(currentLine);

        if (posKeySplit.length != 2) {
            //TODO Exception should be caught and full line + line position put on
            throw new SdmxSyntaxException("Can not parse current line '" + currentLine + "' expecting integer+key example 5+A:B:C:D");
        }
        //TODO These two attributes gives the key of the attribute attachment, if the key is not a full key then it is a group key and the group muse be
        //determined
//		String lastDimensionPosition = posKeySplit[0];  //TODO What does this mean?
        String key = posKeySplit[1];

        String[] keySplit = EDIUtil.splitOnColon(key);
        List<KeyValue> keyableKey = new ArrayList<KeyValue>();
        if (keySplit.length < dimensions.length) {
            throw new SdmxSemmanticException("Reported attributes '" + key + "' are less then key length '" + dimensions.length + "' ");
        }
        boolean isGroup = false;
        for (int i = 0; i < dimensions.length; i++) {
            String val = keySplit[i];
            if (val.length() == 0) {
                isGroup = true;
            } else {
                keyableKey.add(new KeyValueImpl(val, dimensions[i]));
            }
        }
        List<KeyValue> attributes = processAttributeValues();
        currentObs = null;

        String obsDateAsSdmxString = null;
        if (keySplit.length > dimensions.length) {
            if (keySplit.length != dimensions.length + 2) {
                throw new SdmxSemmanticException("Reported attributes '" + key + "' unexpected information, expecting length '" + (dimensions.length + 2) + "' ");
            }
            if (isGroup) {
                throw new SdmxSemmanticException("Illegal observation level attribute reported against a wildcarded series key '" + key + "'.  Observation attributes must be reported against the full key.");
            }
            String timeFormatString = keySplit[dimensions.length + 1];
            String dateString = keySplit[dimensions.length];
            EDI_TIME_FORMAT timeFormat = EDI_TIME_FORMAT.parseString(timeFormatString);
            Date obsDate = timeFormat.parseDate(dateString);
            obsDateAsSdmxString = DateUtil.formatDate(obsDate, timeFormat.getSdmxTimeFormat());
        }
        if (isGroup) {
            if (obsDateAsSdmxString != null) {
                this.currentKey = new KeyableImpl(dataflowBean, dataStructureBean, keyableKey, null, SIBLING_GROUP);
            } else {
                this.currentKey = new KeyableImpl(dataflowBean, dataStructureBean, keyableKey, attributes, SIBLING_GROUP);
            }
        } else {
            if (obsDateAsSdmxString != null) {
                this.currentKey = new KeyableImpl(dataflowBean, dataStructureBean, keyableKey, null, null, null, null);
                currentObs = new ObservationImpl(currentKey, obsDateAsSdmxString, null, attributes);
            } else {
                this.currentKey = new KeyableImpl(dataflowBean, dataStructureBean, keyableKey, attributes, null, null, null);
            }
        }

        return true;
    }

    private List<KeyValue> processAttributeValues() {
        List<KeyValue> returnList = new ArrayList<KeyValue>();
        while (true) {
            //Move to the coded (IDE+Z10)/uncoded(IDE+Z11) attribute line
            assertMoveNext();
            if (ediDataReader.getLineType() != EDI_PREFIX.DATASET_ATTRIBUTE_CODED
                    && ediDataReader.getLineType() != EDI_PREFIX.DATASET_ATTRIBUTE_UNCODED) {
                ediDataReader.moveBackLine();

                // Sort the attributes into their natural order based on the KeyValue's concept.
                Collections.sort(returnList, new Comparator<KeyValue>() {
                    @Override
                    public int compare(KeyValue o1, KeyValue o2) {
                        return o1.getConcept().compareTo(o2.getConcept());
                    }
                });

                return returnList;
            }
            String attributeConceptId = ediDataReader.getCurrentLine();
            String attributeValue = null;
            if (ediDataReader.getLineType() == EDI_PREFIX.DATASET_ATTRIBUTE_CODED) {
                //Move to the code value Line
                assertMoveNext();
                //If the current line is the attribute value then store it, otherwise
                //
                if (EDIUtil.assertPrefix(ediDataReader, EDI_PREFIX.CODE_VALUE, false)) {
                    attributeValue = ediDataReader.getCurrentLine();
                } else {
                    ediDataReader.moveBackLine();
                }
                checkAttributeValueValidity(attributeConceptId, attributeValue, EDI_PREFIX.DATASET_ATTRIBUTE_CODED);

            } else if (ediDataReader.getLineType() == EDI_PREFIX.DATASET_ATTRIBUTE_UNCODED) {
                String compositeValue = "";
                while (true) {
                    // Move to the next line and see if it is FREE TEXT
                    assertMoveNext();
                    if (EDIUtil.assertPrefix(ediDataReader, EDI_PREFIX.STRING, false)) {
                        compositeValue += ediDataReader.parseTextString();
                    } else {
                        break;
                    }
                }
                checkAttributeValueValidity(attributeConceptId, compositeValue, EDI_PREFIX.DATASET_ATTRIBUTE_UNCODED);

                attributeValue = compositeValue;
                ediDataReader.moveBackLine();
            }
            returnList.add(new KeyValueImpl(attributeValue, attributeConceptId));
        }
    }

    private void checkAttributeValueValidity(String attribute, String value, EDI_PREFIX type) {
        DATASET_ACTION action = null;
        if (getCurrentDatasetHeaderBean() != null) {
            action = getCurrentDatasetHeaderBean().getAction();
        }
        if (action == null && header != null && header.getAction() != null) {
            action = header.getAction();
        }
        // If the action is NOT delete then the value must be legal.
        // Spaces are considered legal values
        if (action != DATASET_ACTION.DELETE) {
            if (value == null || value.equals("")) {
                if (type.equals(EDI_PREFIX.DATASET_ATTRIBUTE_UNCODED)) {
                    throw new SdmxSyntaxException("Processing Uncoded Dataset Attribute (IDE+Z11) encountered illegal empty value for attribute: " + attribute);
                } else {
                    throw new SdmxSyntaxException("Processing Coded Dataset Attribute (IDE+Z10) encountered illegal empty code for attribute: " + attribute);
                }
            }
        }
    }

    @Override
    public boolean moveNextObservation() {
        if (inFootNoteSection) {
            //	return false;
        }
        processEDIObservation();
        return currentObs != null;
    }

    private void processEDIObservation() {
        while (true) {
            currentObsPos++;
            currentObs = null;
            if (observations == null) {
                if (obsDates != null && (obsDates.size() > currentObsPos)) {
                    //Observations is null, but this does not mean that there was not a reported date, or dates.
                    //In this case, the observation is just a date, with no reported value.  This can be the case in delete messages
                    String obsDate = obsDates.size() > currentObsPos ? obsDates.get(currentObsPos) : null;
                    currentObs = new ObservationImpl(currentKey, obsDate, null, null);
                }
            } else if (observations.length > currentObsPos) {
                String currentObsLine = observations[currentObsPos];

                //1. Set up variables
                String obsDate = obsDates.size() > currentObsPos ? obsDates.get(currentObsPos) : null;
                String obsVal = null;
                List<KeyValue> attributes = new ArrayList<KeyValue>();

                // If there is no observation, and no date, don't output anything.
                if (!ObjectUtil.validString(currentObsLine)) {
                    if (!ObjectUtil.validString(obsDate)) {
                        currentObsPos++;
                        continue;
                    }
                }

                String[] obsArr = EDIUtil.splitOnColon(currentObsLine);
                //0 = OBS_VALUE
                //1 = OBS_STATUS
                //2 = OBS_CONF
                //3 = OBS_PRE_BREAK
                obsVal = obsArr[0];

                boolean obsStatusPresent = obsArr.length > 1 && ObjectUtil.validString(obsArr[1]);
                boolean obsConfPresent = obsArr.length > 2 && ObjectUtil.validString(obsArr[2]);
                boolean obsPreBreakPresent = obsArr.length > 3 && ObjectUtil.validString(obsArr[3]);

                if (obsStatusPresent) {
                    if (hasObsStatus) {
                        attributes.add(new KeyValueImpl(obsArr[1], SDMX_EDI_ATTRIBUTES.OBS_STATUS));
                    } else {
                        throw new IllegalArgumentException("No observation attribute '" + SDMX_EDI_ATTRIBUTES.OBS_STATUS + "' present on DSD, but the data contains a value for this attribute");
                    }
                } else {
                    // If there is no OBS_STATUS and no OBS_VALUE skip to the next entry
                    if (!ObjectUtil.validString(obsVal)) {
                        continue;
                    }
                }
                if (obsConfPresent) {
                    if (hasObsConf) {
                        attributes.add(new KeyValueImpl(obsArr[2], obsConf));
                    } else {
                        throw new IllegalArgumentException("No observation attribute '" + obsConf + "' present on DSD, but the data contains a value for this attribute");
                    }
                }
                if (obsPreBreakPresent) {
                    if (hasObsPreBeak) {
                        String obsAttr = obsArr[3].equals(missingValue) ? SdmxConstants.MISSING_DATA_VALUE : obsArr[3];
                        attributes.add(new KeyValueImpl(obsAttr, obsPreBreak));
                    } else {
                        throw new IllegalArgumentException("No observation attribute '" + obsPreBreak + "' present on DSD, but the data contains a value for this attribute");
                    }
                }

                if (obsVal == null) {
                    obsVal = "";
                }
                if (obsVal.equals(missingValue)) {
                    obsVal = SdmxConstants.MISSING_DATA_VALUE;
                }
                currentObs = new ObservationImpl(currentKey, obsDate, obsVal, attributes);
            } else {
                //Check if the next key is the same as the last key
                Keyable prevKey = currentKey;
                if (moveNextKeyable()) {
                    if (inFootNoteSection) {
                        lookedAhead = true;
                        break;
                    }
                    if (currentKey.equals(prevKey)) {
                        continue;
                    }
                    lookedAhead = true;
                }
            }

            break;
        }
    }

    @Override
    public void reset() {
        currentlyProcessingReaderIndex = -1;
        ediDataReader = null;
        currentObsPos = -1;
        keyablePosition = -1;
        seriesPosition = -1;
        currentKey = null;
        currentObs = null;
    }

    /**
     * Gets data type.
     *
     * @return the data type
     */
    public DATA_TYPE getDataType() {
        return DATA_TYPE.EDI_TS;
    }

    @Override
    public void copyToOutputStream(OutputStream outputStream) {
        ediDataReader.copyToStream(outputStream);
    }

    @Override
    public void close() {
        for (EDIDataReader dataReader : ediDataReaders) {
            dataReader.close();
        }
    }

    private class CachedTimeRanges {
        private Map<String, List<String>> timeRangeMap = new HashMap<String, List<String>>();  //Edi Time period map to dates

        /**
         * Gets time range map.
         *
         * @return the time range map
         */
        public Map<String, List<String>> getTimeRangeMap() {
            return timeRangeMap;
        }
    }
}
