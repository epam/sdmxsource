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
package org.sdmxsource.sdmx.ediparser.engine.writer.impl;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.sdmxsource.sdmx.api.constants.*;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.exception.SdmxNotImplementedException;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.factory.WriteableDataLocationFactory;
import org.sdmxsource.sdmx.api.model.beans.base.AnnotationBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.AttributeBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataflowBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DimensionBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ProvisionAgreementBean;
import org.sdmxsource.sdmx.api.model.header.DatasetHeaderBean;
import org.sdmxsource.sdmx.api.model.header.HeaderBean;
import org.sdmxsource.sdmx.api.util.WriteableDataLocation;
import org.sdmxsource.sdmx.ediparser.constants.*;
import org.sdmxsource.sdmx.ediparser.engine.writer.EDIDataWriterEngine;
import org.sdmxsource.sdmx.ediparser.model.impl.InterchangeHeader;
import org.sdmxsource.sdmx.ediparser.model.impl.MessageIdentification;
import org.sdmxsource.sdmx.ediparser.util.EDIDataWriterUtil;
import org.sdmxsource.sdmx.ediparser.util.EDIUtil;
import org.sdmxsource.sdmx.sdmxbeans.model.header.DatasetHeaderBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.header.DatasetStructureReferenceBeanImpl;
import org.sdmxsource.sdmx.util.date.DateUtil;
import org.sdmxsource.util.ObjectUtil;

import java.io.*;
import java.util.*;


/**
 * The type Edi data writer engine.
 */
public class EDIDataWriterEngineImpl implements EDIDataWriterEngine {
    private static final Logger LOG = LoggerFactory.getLogger(EDIDataWriterEngineImpl.class);
    private static final int MAX_MISSING = 3; //The number of missing values in a row before starting a new time period
    private static int DATASET_ID = 1;
    private final WriteableDataLocationFactory writeableDataLocationFactory;
    //Set these to Version 1, but depending on the DSD they may be updated to version 2 concept ids
    private String obsConf = SDMX_EDI_ATTRIBUTES.OBS_CONF_V1;
    private String obsPreBreak = SDMX_EDI_ATTRIBUTES.OBS_PRE_BREAK_V1;
    private OutputStreamWriter writer;
    private DataStructureBean dsd;
    private HeaderBean headerBean;
    private int numLines;
    private int numMessages;
    private InterchangeHeader header;
    private MessageIdentification messageId;
    private WriteableDataLocation tmpAttributeFileDataset;
    private WriteableDataLocation tmpAttributeFileSeries;
    private WriteableDataLocation tmpAttributeFileObs;
    private OutputStreamWriter datasetAttributeWriter;
    private OutputStreamWriter seriesAttributeWriter;
    private OutputStreamWriter obsAttributeWriter;
    private String currentSeriesKey = "";
    private String currentAttributeKey = null;  //Store the last attribute key that was output so we don't keep repeating the key
    private Date currentObsStartDate;
    private Date currentObsEndDate;
    private Map<AttributeBean, AttributeKeyCreator> seriesAttributeKeyCreator = new HashMap<AttributeBean, EDIDataWriterEngineImpl.AttributeKeyCreator>();
    private Map<String, Date> dateMap = new HashMap<String, Date>();
    private Map<String, TIME_FORMAT> timeFormatMap = new HashMap<String, TIME_FORMAT>();
    private Map<String, String> dateValues = new HashMap<String, String>();
    private Map<TIME_FORMAT, DateIterations> dateIterationsMap = new HashMap<TIME_FORMAT, DateIterations>();
    private String currentObsTime;
    private String currentObsValue = EDI_CONSTANTS.MISSING_VAL;
    private String currentObsStatus = "";
    private String currentObsConf = "";
    private String currentObsPreBreak = "";
    private boolean hasAttributes;
    private boolean hasFootnoteSection;

    private TIME_FORMAT timeFormat;
    private Date currentObsDate;

    private Map<String, String> keyValues = new HashMap<String, String>(); //A list containing all the key values
    private List<String> dimensionIds = new ArrayList<String>();  //A list containing the ids of all the dimensions in order they are defined

    private POSITION currentPOS = POSITION.DATASET;

    private boolean headerWritten = false;

    private DatasetHeaderBean currentDatasetHeader;

    private int messageRefCounter;

    private boolean readerClosed;
    private Map<String, String> bufferedAttributes = new TreeMap<String, String>();

    /**
     * Instantiates a new Edi data writer engine.
     *
     * @param writeableDataLocationFactory the writeable data location factory
     * @param out                          the out
     */
    public EDIDataWriterEngineImpl(final WriteableDataLocationFactory writeableDataLocationFactory, final OutputStream out) {
        DATASET_ID++;
        this.writeableDataLocationFactory = writeableDataLocationFactory;
        this.writer = createOutputStreamWriter(out);

        this.messageRefCounter = 1;
    }

    private OutputStreamWriter createOutputStreamWriter(OutputStream os) {
        try {
            OutputStreamWriter osw = new OutputStreamWriter(os, EDI_CONSTANTS.CHARSET_ENCODING);
            return osw;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Unsupported charset encoding!", e);
        }
    }

    private OutputStreamWriter getAttributeWriter() {
        switch (currentPOS) {
            case DATASET:
                if (datasetAttributeWriter == null) {
                    this.tmpAttributeFileDataset = writeableDataLocationFactory.getTemporaryWriteableDataLocation();
                    this.datasetAttributeWriter = createOutputStreamWriter(tmpAttributeFileDataset.getOutputStream());
                    write(datasetAttributeWriter, EDIDataWriterUtil.parseAttributeScope(ATTRIBUTE_ATTACHMENT_LEVEL.DATA_SET));
                    writeLineSeparator(datasetAttributeWriter);
                }
                return datasetAttributeWriter;
            case OBS:
                if (obsAttributeWriter == null) {
                    this.tmpAttributeFileObs = writeableDataLocationFactory.getTemporaryWriteableDataLocation();
                    this.obsAttributeWriter = createOutputStreamWriter(tmpAttributeFileObs.getOutputStream());
                    write(obsAttributeWriter, EDIDataWriterUtil.parseAttributeScope(ATTRIBUTE_ATTACHMENT_LEVEL.OBSERVATION));
                    writeLineSeparator(obsAttributeWriter);
                }

                return obsAttributeWriter;
            default:
                if (seriesAttributeWriter == null) {
                    this.tmpAttributeFileSeries = writeableDataLocationFactory.getTemporaryWriteableDataLocation();
                    this.seriesAttributeWriter = createOutputStreamWriter(tmpAttributeFileSeries.getOutputStream());
                    write(seriesAttributeWriter, EDIDataWriterUtil.parseAttributeScope(ATTRIBUTE_ATTACHMENT_LEVEL.GROUP));
                    writeLineSeparator(seriesAttributeWriter);
                }
                return seriesAttributeWriter;
        }
    }


    /**
     * Writes out the start of the message:
     * UNA:+.? '
     * UNB+[metadata such as date of preparation and receiving agency]
     * UNH+
     * NAD+MR+[sender acy]
     * NAD+MS+[rec acy]
     * DSI+[dataset id]
     * STS+3 [status code]
     * DTM [dataset preparation date]
     * IDE+5+[dsd id]
     * GIS+AR3
     * GIS+1
     *
     * @param header the header
     */
    @Override
    public void writeHeader(HeaderBean header) {
        if (headerWritten) {
            throw new SdmxException("Can not write header as message has already been started");
        }
        this.headerBean = header;
    }


    /**
     * Outputs the header info if not yet done
     */
    private void startMessage() {
        if (!headerWritten) {
            headerWritten = true;
            String senderId = "undefined";
            String receiverId = "undefined";
            Date preparedDate = new Date();

            String iref = null;
            if (headerBean != null) {
                if (headerBean.getId() != null) {
                    iref = headerBean.getId();
                }
                if (headerBean.getSender() != null) {
                    senderId = headerBean.getSender().getId();
                }
                if (headerBean.getReceiver().size() > 0) {
                    receiverId = headerBean.getReceiver().get(0).getId();
                }
                if (headerBean.getPrepared() != null) {
                    preparedDate = headerBean.getPrepared();
                }
            }

            InterchangeHeader header;
            if (iref == null) {
                header = new InterchangeHeader(senderId, receiverId, preparedDate, DATASET_ID, null);
            } else {
                header = new InterchangeHeader(senderId, receiverId, preparedDate, iref, DATASET_ID, null);
            }

            //Write UNA:+.? ' & UNB+
            writeInterchangeAdministration(header);
        }
    }


    @Override
    public void startDataset(ProvisionAgreementBean prov, DataflowBean flow, DataStructureBean dsd, DatasetHeaderBean datasetHeader, AnnotationBean... annotations) {
        flushCurrentDataset();
        if (dsd == null) {
            throw new IllegalArgumentException("Can not start EDI dataset, data structure definition not provided");
        }
        if (!headerWritten) {
            startMessage();
        }
        currentPOS = POSITION.DATASET;
        currentDatasetHeader = datasetHeader;
        if (currentDatasetHeader == null) {
            currentDatasetHeader = new DatasetHeaderBeanImpl(dsd.getId(), DATASET_ACTION.APPEND, new DatasetStructureReferenceBeanImpl(dsd.asReference()));
        }
        this.dsd = dsd;
        dimensionIds = new ArrayList<String>();
        seriesAttributeKeyCreator = new HashMap<AttributeBean, EDIDataWriterEngineImpl.AttributeKeyCreator>();

        for (DimensionBean currentDim : dsd.getDimensions(SDMX_STRUCTURE_TYPE.DIMENSION)) {
            dimensionIds.add(currentDim.getId());
        }
        for (AttributeBean attribute : dsd.getSeriesAttributes(null)) {
            seriesAttributeKeyCreator.put(attribute, new AttributeKeyCreator(attribute));
        }
        if (dsd.getAttribute(SDMX_EDI_ATTRIBUTES.OBS_CONF_V1) == null) {
            if (dsd.getAttribute(SDMX_EDI_ATTRIBUTES.OBS_CONF_V2) != null) {
                obsConf = SDMX_EDI_ATTRIBUTES.OBS_CONF_V2;
            }
        }

        if (dsd.getAttribute(SDMX_EDI_ATTRIBUTES.OBS_PRE_BREAK_V1) == null) {
            if (dsd.getAttribute(SDMX_EDI_ATTRIBUTES.OBS_PRE_BREAK_V2) != null) {
                obsPreBreak = SDMX_EDI_ATTRIBUTES.OBS_PRE_BREAK_V2;
            }
        }

        DATASET_ID++;
        //MESSAGE ADMINISTRATION
        MessageIdentification identification = new MessageIdentification(messageRefCounter);
        messageRefCounter++;

        //Write UNH+
        writeMessageIdentification(identification);

        //write BGM+74 (data)
        writeMessageFunction(MESSSAGE_FUNCTION.STATISTICAL_DATA);

        //Write NAD+Z02
        writeStructureMaintAgency(dsd.getAgencyId());

        //Write NAD+MR
        String sender = "undefined";
        String receiver = "undefined";
        String datasetId = currentDatasetHeader.getDatasetId();
        String reportingPeriod = null;

        if (headerBean != null) {
            if (headerBean.getSender() != null) {
                sender = headerBean.getSender().getId();
            }
            if (ObjectUtil.validCollection(headerBean.getReceiver())) {
                receiver = headerBean.getReceiver().get(0).getId();
            }
            if (datasetId == null && ObjectUtil.validString(headerBean.getDatasetId())) {
                datasetId = headerBean.getDatasetId();
            }
            if (datasetId == null && ObjectUtil.validString(headerBean.getId())) {
                datasetId = headerBean.getId();
            }
            if (currentDatasetHeader.getReportingBeginDate() != null && currentDatasetHeader.getReportingEndDate() != null) {
                reportingPeriod = EDIDataWriterUtil.parseReportingPeriodDate(currentDatasetHeader.getReportingBeginDate(), currentDatasetHeader.getReportingEndDate());
            } else if (headerBean.getReportingBegin() != null && headerBean.getReportingEnd() != null) {
                reportingPeriod = EDIDataWriterUtil.parseReportingPeriodDate(headerBean.getReportingBegin(), headerBean.getReportingEnd());
            }
        }
        writeRecievingAgency(receiver);

        //Write NAD+MS
        writeSendingAgency(sender);

        //Write DSI
        writeSegment(EDIDataWriterUtil.parseDataSetIdentifier(datasetId));

        //Write STS+3 [status code]  7 = append 3 = update
        writeSegment(EDIDataWriterUtil.parseStatus(currentDatasetHeader.getAction()));

        //Write DTM [dataset preparation date]
        if (headerBean != null && headerBean.getPrepared() != null) {
            writeSegment(EDIDataWriterUtil.parsePreperationDate(headerBean.getPrepared()));
        } else {
            writeSegment(EDIDataWriterUtil.parsePreperationDate(new Date()));
        }

        if (reportingPeriod != null) {
            writeSegment(reportingPeriod);
        }

        //Write IDE+5+[dsd id]
        writeSegment(EDIDataWriterUtil.parseDataStructureIdentifier(dsd.getId()));

        //Write GIS+AR3
        writeSegment(EDIDataWriterUtil.parseMethodToSendDataSet());

        //Write GIS+1
        writeSegment(EDIDataWriterUtil.parseMissingValue());
    }

    /**
     * Sets cross section concept.
     *
     * @param concept the concept
     * @throws SdmxNotImplementedException the sdmx not implemented exception
     */
    public void setCrossSectionConcept(String concept) throws SdmxNotImplementedException {
        if (concept != null && !concept.equals(DimensionBean.TIME_DIMENSION_FIXED_ID)) {
            throw new SdmxNotImplementedException("Cross sectional datasets are not supported for EDI data");
        }
    }

    @Override
    public void startGroup(String groupId, AnnotationBean... annotations) {
        startMessage();
        flushDataLine();
        currentPOS = POSITION.GROUP;
        currentSeriesKey = "";
        keyValues.clear();

    }

    @Override
    public void writeGroupKeyValue(String conceptId, String conceptValue) {
        if (currentPOS != POSITION.GROUP) {
            throw new SdmxException("Can not write group key value, a call to the method startGroup(..) must first be made");
        }
        keyValues.put(conceptId, conceptValue);
    }

    @Override
    public void startSeries(AnnotationBean... annotations) {
        startMessage();
        flushDataLine();
        currentPOS = POSITION.SERIES;
        currentSeriesKey = "";
        keyValues.clear();
    }

    @Override
    public void writeSeriesKeyValue(String conceptId, String conceptValue) {
        if (currentPOS != POSITION.SERIES) {
            startSeries();
        }
        if (!dimensionIds.contains(conceptId)) {
            throw new SdmxException("Data Structure '" + dsd.getId() + "' does not contain dimension with id '" + conceptId + "'");
        }
        keyValues.put(conceptId, conceptValue);
    }

    /**
     * Creates the series key if it has not yet been created
     */
    private void createSeriesKey() {
        if (currentSeriesKey.length() == 0 && keyValues.size() > 0) {
            String concat = "";
            for (String currentDimension : dimensionIds) {
                String val = keyValues.get(currentDimension);
                currentSeriesKey += concat;
                if (val != null) {
                    currentSeriesKey += val;
                }
                concat = EDI_CONSTANTS.COLON;
            }
        }
    }

    /**
     * Flush the attributes if there are any.  If there are then hasAttributes is set to true
     */
    private void flushAttributes() {
        hasAttributes = false;
        for (String key : bufferedAttributes.keySet()) {
            flushAttributeValue(key, bufferedAttributes.get(key));
        }
        bufferedAttributes.clear();
    }

    @Override
    public void writeAttributeValue(String attributeId, String conceptValue) {
        // Only output attributes if not performing a delete
//		if(currentDatasetHeader.getAction() != DATASET_ACTION.DELETE) {
        bufferedAttributes.put(attributeId, conceptValue);
//		}
    }

    /**
     * Flush the attributes to a separate attribute writer.
     *
     * @param attributeId
     * @param conceptValue
     */
    private void flushAttributeValue(String attributeId, String conceptValue) {
        if (conceptValue == null) {
            conceptValue = "";
        }
        startMessage();
        if (currentPOS == POSITION.OBS) {
            //Store the observation attribute that comes in the data segment, and exit the method
            if (attributeId.equals(SDMX_EDI_ATTRIBUTES.OBS_STATUS)) {
                currentObsStatus = conceptValue;
                return;
            } else if (attributeId.equals(obsConf)) {
                currentObsConf = conceptValue;
                return;
            } else if (attributeId.equals(obsPreBreak)) {

                if (conceptValue == null || conceptValue.equals(SdmxConstants.MISSING_DATA_VALUE)) {
                    currentObsPreBreak = EDI_CONSTANTS.MISSING_VAL;
                } else {
                    currentObsPreBreak = conceptValue;
                }

                return;
            }
        }
        hasAttributes = true;
        hasFootnoteSection = true;
        //If we have not exited the method, then store the attribute in the temp attribute writer.
        //1. What is the attachment level

        if ("TIME_FORMAT".equals(attributeId)) {
            //Ignore this - The Time Format should not be output at the attribute level
            //Time format is part of the ARR segment before the first observation, the time format code indicates if it is asingle observation
            //a range, and it includes format.
            return;
        }
        AttributeBean attribute = dsd.getAttribute(attributeId);
        if (attribute == null) {
            throw new SdmxException("Data Structure '" + dsd.getId() + "' does not contain an attribute with identifier: " + attributeId);
        }

        switch (attribute.getAttachmentLevel()) {
            case DATA_SET:
                if (currentPOS != POSITION.DATASET) {
                    throw new SdmxException("Can not write a dataset attribute '" + attributeId + "' when currently processing output for " + currentPOS);
                }
                break;
            case DIMENSION_GROUP:
                if (currentPOS != POSITION.SERIES) {
                    throw new SdmxException("Can not write a series attribute '" + attributeId + "' when currently processing output for " + currentPOS);
                }
                break;
            case GROUP:
                if (currentPOS != POSITION.GROUP) {
                    throw new SdmxException("Can not write a group attribute '" + attributeId + "' when currently processing output for " + currentPOS);
                }
                break;
            case OBSERVATION:
                if (currentPOS != POSITION.OBS) {
                    throw new SdmxException("Can not write a observation attribute '" + attributeId + "' when currently processing output for " + currentPOS);
                }
                break;
        }

        boolean isCoded = attribute.hasCodedRepresentation();
        createSeriesKey();
        String outputKey = currentSeriesKey;

        int keyLength = dimensionIds.size();
        if (attribute.getAttachmentLevel() == ATTRIBUTE_ATTACHMENT_LEVEL.DIMENSION_GROUP) {
            outputKey = seriesAttributeKeyCreator.get(attribute).createSubKey();
        } else if (attribute.getAttachmentLevel() == ATTRIBUTE_ATTACHMENT_LEVEL.DATA_SET) {
            keyLength = 0;
            outputKey = "";
        } else if (currentPOS == POSITION.OBS) {
            // Convert currentObsDate into EDI format
            EDI_TIME_FORMAT ediTimeFormat = EDI_TIME_FORMAT.parseTimeFormat(timeFormat, false);
            String ediObsTime = ediTimeFormat.formatDate(currentObsDate);
            outputKey += EDI_CONSTANTS.COLON + ediObsTime + EDI_CONSTANTS.COLON + EDI_TIME_FORMAT.parseTimeFormat(timeFormat, false).getEdiValue();
            keyLength += 2;
        }

        OutputStreamWriter osw = getAttributeWriter();
        if (currentAttributeKey == null || !outputKey.equals(currentAttributeKey)) {
            write(osw, EDIDataWriterUtil.parseAttributeAttachment(keyLength, outputKey));
            writeLineSeparator(osw);
            currentAttributeKey = outputKey;
        }

        boolean writeAttr;
        if (currentDatasetHeader.getAction() == DATASET_ACTION.DELETE) {
            // For delete messages, write blank attributes (they are being deleted!)
            writeAttr = true;
        } else {
            // For all other message types, only write the attribute if it has a value
            if (conceptValue.length() > 0) {
                writeAttr = true;
            } else {
                writeAttr = false;
            }
        }

        if (writeAttr) {
            write(osw, EDIDataWriterUtil.parseAttributeIdentifier(attributeId, isCoded));
            writeLineSeparator(osw);
            List<String> values = EDIDataWriterUtil.parseAttributeValue(conceptValue, isCoded);
            for (String val : values) {
                write(osw, val);
                writeLineSeparator(osw);
            }
        }
    }

    @Override
    public void writeObservation(String obsConceptValue, String obsValue, AnnotationBean... annotations) {
        writeObservation(DimensionBean.TIME_DIMENSION_FIXED_ID, obsConceptValue, obsValue, annotations);
    }

    @Override
    public void writeObservation(String observationConceptId, String obsTime, String obsValue, AnnotationBean... annotations) {
        if (observationConceptId == null) {
            observationConceptId = DimensionBean.TIME_DIMENSION_FIXED_ID;
        }
        if (!observationConceptId.equals(DimensionBean.TIME_DIMENSION_FIXED_ID)) {
            throw new IllegalArgumentException("SDMX_EDI only supported time Series data, the observation conceptId is expected to be TIME_PERIOD, but got:" + observationConceptId);
        }
        //Step 1. Flush the previous observation - this must be performed before updating the obs date or obs value for the new observation
        flushObs();

        if (obsValue == null || obsValue.equals(SdmxConstants.MISSING_DATA_VALUE)) {
            obsValue = EDI_CONSTANTS.MISSING_VAL;
        }

        // Does it contain an exponent?
        if (obsValue.contains("E")) {
            // There may be a plus sign so this will need to be removed
            // (e.g. 7.6E+7 is represented in EDI as 7.6E7)
            if (obsValue.contains("+")) {
                obsValue = obsValue.replaceAll("\\+", "");
            }
        }

        if (obsValue.length() > 15) {
            if (EDIUtil.isTruncateLongValues()) {
                String originalValue = obsValue;
                obsValue = EDIUtil.truncate(obsValue);
                LOG.warn("Truncating EDI Observation value. Original value: " + originalValue + " truncated to: " + obsValue);
            } else {
                throw new SdmxSemmanticException("Illegal Observation value! Maximum length for an EDI observation is 15 characters. Supplied observation: " + obsValue);
            }
        }

        currentPOS = POSITION.OBS;
        currentObsDate = dateMap.get(obsTime);
        if (currentObsDate == null) {
            // The values in the dateMap MUST be in the SDMX 2.1 format, so bi-annuals must be represented with an "S"
            if (obsTime.contains("-B")) {
                obsTime = obsTime.replaceAll("-B", "-S");
            }
            currentObsDate = DateUtil.formatDate(obsTime, false);   // FOR EDI always use the END date.
            dateMap.put(obsTime, currentObsDate);
        }
        timeFormat = timeFormatMap.get(obsTime);
        if (timeFormat == null) {
            timeFormat = DateUtil.getTimeFormatOfDate(obsTime);
            timeFormatMap.put(obsTime, timeFormat);
            dateIterationsMap.put(timeFormat, new DateIterations(timeFormat));
        }

        //After flushing the last observation,
        currentObsTime = obsTime;
        currentObsValue = obsValue;
    }

    private void storeReportedDate() {
        //TODO split storage of date by time format?
        if (currentObsStartDate == null || currentObsDate.getTime() < currentObsStartDate.getTime()) {
            currentObsStartDate = currentObsDate;
        }
        if (currentObsEndDate == null || currentObsDate.getTime() > currentObsEndDate.getTime()) {
            currentObsEndDate = currentObsDate;
        }
    }

    /**
     * Flushes the observation either directly to the output location, if the time format is hourly or date time.
     * Alternatively it stores the observation in a Map for output as part of a period.
     */
    private void flushObs() {
        flushAttributes();
        if (currentObsTime != null) {
            createObsValue();
            if (timeFormat == TIME_FORMAT.DATE_TIME || timeFormat == TIME_FORMAT.HOUR) {
                //If the time format is data time or hourly, then we do not iterate time as EDI does not support time range for time periods with this level of detail
                createSeriesKey();
                EDI_TIME_FORMAT ediTimeFormat = EDI_TIME_FORMAT.parseTimeFormat(timeFormat, false);
                outputObs(ediTimeFormat.formatDate(currentObsDate), ediTimeFormat.getEdiValue(), currentObsValue);
            } else {
                String existingValue = dateValues.get(currentObsTime);

                // If there is an existing value, put out a warning
                if (ObjectUtil.validString(existingValue)) {
                    // Since existingValue is represented as <value>:<obs_conf>:<obs_value>, simply strip everything past the first colon
                    int colonPos = existingValue.indexOf(":");
                    String actualValue;
                    if (colonPos == -1) {
                        actualValue = existingValue;
                    } else {
                        actualValue = existingValue.substring(0, colonPos);
                    }

                    // Output a warning message
                    String string = Arrays.toString(keyValues.entrySet().toArray());
                    LOG.warn("Warning: For the dataset: " + dsd + " within the Series: " + string);
                    LOG.warn("  There is an existing value for the time period: " + currentObsTime + " and this value will be overwritten. Existing value: " + actualValue);
                }
                // The values in the dateValues map MUST be in the SDMX 2.1 format, so bi-annuals must be represented with an "S"
                if (currentObsTime.contains("-B")) {
                    currentObsTime = currentObsTime.replaceAll("-B", "-S");
                }
                dateValues.put(currentObsTime, currentObsValue);
                storeReportedDate();
            }
        }
        currentObsValue = EDI_CONSTANTS.MISSING_VAL;
        currentObsStatus = "";
        currentObsConf = "";
        currentObsPreBreak = "";
    }

    @Override
    public void writeObservation(Date obsTime, String obsValue, TIME_FORMAT sdmxTimeFormat, AnnotationBean... annotations) {
        writeObservation(DimensionBean.TIME_DIMENSION_FIXED_ID, DateUtil.formatDate(obsTime, sdmxTimeFormat), obsValue);
    }

    /**
     * Creates the observation value which is the concatenation of OBS_VALUE, OBS_STATUS, OBS_CONF and OBS_PRE_BREAK
     */
    private void createObsValue() {
        if (currentDatasetHeader.getAction() == DATASET_ACTION.DELETE) {
            currentObsValue = "";
        }
        if (currentObsTime != null) {
            if (currentObsPreBreak.length() > 0) {
                currentObsValue += EDI_CONSTANTS.COLON + currentObsStatus + EDI_CONSTANTS.COLON + currentObsConf + EDI_CONSTANTS.COLON + currentObsPreBreak;
            } else if (currentObsConf.length() > 0) {
                currentObsValue += EDI_CONSTANTS.COLON + currentObsStatus + EDI_CONSTANTS.COLON + currentObsConf;
            } else if (currentObsStatus.length() > 0) {
                currentObsValue += EDI_CONSTANTS.COLON + currentObsStatus;
            }
        }
    }

    private void flushDataLine() {
        flushObs();
        createSeriesKey();
        if (currentObsEndDate != null && currentObsStartDate != null) {
            if (currentDatasetHeader.getAction() == DATASET_ACTION.DELETE) {
                //In a delete message output each obs on its own line
                for (String time : dateIterationsMap.get(timeFormat).getIterations(currentObsStartDate, currentObsEndDate)) {
                    String reportedValue = dateValues.get(time);
                    if (reportedValue != null) {
                        EDI_TIME_FORMAT ediTimeFormat = EDI_TIME_FORMAT.parseTimeFormat(timeFormat, false);
                        time = ediTimeFormat.formatDate(dateMap.get(time));
                        outputObs(time, ediTimeFormat.getEdiValue(), reportedValue);
                    }
                }
            } else {
                //Otherwise output each series on a single line, if possible
                List<DataGroup> dataGroupList = new ArrayList<EDIDataWriterEngineImpl.DataGroup>();
                int noVal = 0;
                int numObs = 0;
                DataGroup currentDataGroup = null;

                List<String> timeIterations = dateIterationsMap.get(timeFormat).getIterations(currentObsStartDate, currentObsEndDate);
                for (String time : timeIterations) {
                    numObs++;
                    String reportedValue = dateValues.get(time);

                    if (reportedValue == null) {
                        noVal++;
                        continue;
                    }

                    // If the number of observations is 1 then we have only just started, create and store first data group
                    if (numObs == 1 || numObs > 9999 || noVal > MAX_MISSING) {
                        currentDataGroup = new DataGroup(time);
                        dataGroupList.add(currentDataGroup);
                        noVal = 0;
                        numObs = 1;
                    }

                    if (noVal > 0) {
                        currentDataGroup.addEmptyValues(noVal);
                        noVal = 0;
                    }

                    currentDataGroup.addObservationValue(time, reportedValue);
                }

                for (DataGroup currentGroup : dataGroupList) {
                    outputObs(currentGroup.getTimePeriod(), currentGroup.getTimeRangeFormat(), currentGroup.getDataLine());
                }
            }
            //Clear all dates and date values
            currentObsEndDate = null;
            currentObsStartDate = null;
            currentObsTime = null;
            dateValues = new HashMap<String, String>();
        } else if (currentSeriesKey.length() > 0 && !hasAttributes) {
            //Only output a series key with no observations IF the series key has no attributes associated with it
            //If there are attributes, then this will be output in the FNS attribute segment
            writeSegment(EDI_PREFIX.DATASET_DATA + currentSeriesKey + EDI_CONSTANTS.END_TAG);
        }
    }

    private void outputObs(String timePeriod, String timeRange, String dataLine) {
        String segmentOutput = EDI_PREFIX.DATASET_DATA + currentSeriesKey + EDI_CONSTANTS.COLON + timePeriod + EDI_CONSTANTS.COLON + timeRange + dataLine + EDI_CONSTANTS.END_TAG;
        writeSegment(segmentOutput);
    }

    /**
     * Writes out the start of the message:
     * UNA:+.? '
     * UNB+syntax_identifier:syntax_version+ident+rec_ident+date_of_prep+interchange_ref++application_ref++++test_indicator
     *
     * @param header the header
     */
    protected void writeInterchangeAdministration(InterchangeHeader header) {
        this.header = header;
        write(writer, EDI_PREFIX.MESSAGE_START + EDI_CONSTANTS.END_TAG);
        writeLineSeparator(writer);
        write(writer, header.toString());
        writeLineSeparator(writer);
        //writer.println(EDI_PREFIX.MESSAGE_START+EDI_CONSTANTS.END_TAG);
        //writer.println(header.toString());
    }

    private void write(OutputStreamWriter outputStreamWriter, String value) {
        try {
            outputStreamWriter.write(value);
        } catch (IOException e) {
            throw new RuntimeException("Unable to write the string: " + value, e);
        }
    }

    private void writeLineSeparator(OutputStreamWriter outputStreamWriter) {
        try {
            outputStreamWriter.write(System.getProperty("line.separator"));
        } catch (IOException e) {
            throw new RuntimeException("Unable to write!", e);
        }
    }

    /**
     * Write message identification.
     *
     * @param messageId the message id
     */
    protected void writeMessageIdentification(MessageIdentification messageId) {
        this.messageId = messageId;
        numLines = 1;
        numMessages++;
        writeSegment(messageId.toString());
    }

    /**
     * Write message function.
     *
     * @param messagefunctionEnum the messagefunction enum
     */
    protected void writeMessageFunction(MESSSAGE_FUNCTION messagefunctionEnum) {
        writeSegment(EDI_PREFIX.MESSAGE_FUNCTION.toString() + messagefunctionEnum.getEDIString() + "'");
    }

    /**
     * Write structure maint agency.
     *
     * @param agencyId the agency id
     */
    protected void writeStructureMaintAgency(String agencyId) {
        writeSegment(EDI_PREFIX.MESSAGE_AGENCY.toString() + agencyId + "'");
    }

    /**
     * Write recieving agency.
     *
     * @param agencyId the agency id
     */
    protected void writeRecievingAgency(String agencyId) {
        writeSegment(EDI_PREFIX.RECIEVING_AGENCY.toString() + agencyId + "'");
    }

    /**
     * Write sending agency.
     *
     * @param agencyId the agency id
     */
    protected void writeSendingAgency(String agencyId) {
        writeSegment(EDI_PREFIX.SENDING_AGENCY.toString() + agencyId + "'");
    }

    /**
     * Write end message administration.
     */
    protected void writeEndMessageAdministration() {
        writeSegment(EDI_PREFIX.END_MESSAGE_ADMINISTRATION.toString() + numLines + "+" + messageId.getMessageRefNum() + "'");
    }

    /**
     * Write end message.
     */
    protected void writeEndMessage() {
        if (header != null) {
            //writer.println(EDI_PREFIX.END_MESSAGE.toString()+numMessages+"+"+header.getInterchangeRef()+"'");
            write(writer, EDI_PREFIX.END_MESSAGE.toString() + numMessages + "+" + header.getInterchangeRef() + "'");
            writeLineSeparator(writer);
        }
    }

    /**
     * Write segment.
     *
     * @param str the str
     */
    protected void writeSegment(String str) {
//		writer.println(str);
        write(writer, str);
        writeLineSeparator(writer);
        numLines++;
    }

    private void flushCurrentDataset() {
        if (currentDatasetHeader != null) {
            flushDataLine();
            if (hasFootnoteSection) {
                // Only write the footnote section FNS+ if there is some form of attribute to write!
                if (!(datasetAttributeWriter == null && seriesAttributeWriter == null && obsAttributeWriter == null)) {
                    writeSegment(EDIDataWriterUtil.parseStartAttributes());
                }
            }
            hasFootnoteSection = false;
            //Close all attribute writers
            if (datasetAttributeWriter != null) {
                try {
                    datasetAttributeWriter.close();
                } catch (IOException e) {
                    throw new RuntimeException("Unable to close: ", e);
                }
                writeAttributes(tmpAttributeFileDataset);
                datasetAttributeWriter = null;
            }
            if (seriesAttributeWriter != null) {
                try {
                    seriesAttributeWriter.close();
                } catch (IOException e) {
                    throw new RuntimeException("Unable to close: ", e);
                }
                writeAttributes(tmpAttributeFileSeries);
                seriesAttributeWriter = null;
            }
            if (obsAttributeWriter != null) {
                try {
                    obsAttributeWriter.close();
                } catch (IOException e) {
                    throw new RuntimeException("Unable to close: ", e);
                }
                writeAttributes(tmpAttributeFileObs);
                obsAttributeWriter = null;
            }

            currentSeriesKey = "";
            currentAttributeKey = null;  //Store the last attribute key that was output so we don't keep repeating the key
            currentObsStartDate = null;
            currentObsEndDate = null;
            currentObsTime = null;
            currentObsValue = EDI_CONSTANTS.MISSING_VAL;
            currentObsStatus = "";
            currentObsConf = "";
            currentObsPreBreak = "";
            keyValues.clear();
            //END MESSAGE
            writeEndMessageAdministration();
        }
    }

    @Override
    public void close(FooterMessage... footer) {
        if (readerClosed) {
            return;
        }

        if (headerWritten) {
            flushCurrentDataset();
            writeEndMessage();
            try {
                this.writer.close();
            } catch (IOException e) {
                throw new RuntimeException("Unable to close!", e);
            }
        }
        readerClosed = true;
    }

    private void writeAttributes(WriteableDataLocation attributeFile) {
        BufferedReader br = null;
        if (attributeFile != null) {
            try {
                br = new BufferedReader(new InputStreamReader(attributeFile.getInputStream(), EDI_CONSTANTS.CHARSET_ENCODING));

                String currentLine = br.readLine();
                while (currentLine != null) {
                    writeSegment(currentLine);
                    currentLine = br.readLine();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                attributeFile.close();
            }
        }
    }

    /**
     * The enum Position.
     */
    enum POSITION {
        /**
         * Dataset position.
         */
        DATASET,
        /**
         * Group position.
         */
        GROUP,
        /**
         * Series position.
         */
        SERIES,
        /**
         * Obs position.
         */
        OBS
    }

    /**
     * Class contains the start and end period and a single line containing all the observation values
     */
    private class DataGroup {
        private StringBuilder segmentSb = new StringBuilder();

        //Start & end period in SDMX date format
        private String startPeriod;
        private String endPeriod;
        private String prepend = EDI_CONSTANTS.COLON;

        /**
         * Instantiates a new Data group.
         *
         * @param startPeriod the start period
         */
        public DataGroup(String startPeriod) {
            this.startPeriod = startPeriod;
            this.endPeriod = startPeriod;
        }

        /**
         * Gets time period.
         *
         * @return the time period
         */
        public String getTimePeriod() {
            Date startDate = dateMap.get(startPeriod);
            Date endDate = dateMap.get(endPeriod);
            EDI_TIME_FORMAT ediTimeFormat = EDI_TIME_FORMAT.parseTimeFormat(timeFormat, false);


            String dateToOutput = ediTimeFormat.formatDate(startDate);
            if (isRange()) {
                dateToOutput += ediTimeFormat.formatDate(endDate);
            }
            return dateToOutput;
        }

        /**
         * Gets data line.
         *
         * @return the data line
         */
        public String getDataLine() {
            return segmentSb.toString();
        }

        /**
         * Gets time range format.
         *
         * @return the time range format
         */
        public String getTimeRangeFormat() {
            return EDI_TIME_FORMAT.parseTimeFormat(timeFormat, isRange()).getEdiValue();
        }

        private boolean isRange() {
            return !startPeriod.equals(endPeriod);
        }

        /**
         * Add empty values.
         *
         * @param numVals the num vals
         */
        public void addEmptyValues(int numVals) {
            for (int i = 0; i < numVals; i++) {
                segmentSb.append(EDI_CONSTANTS.PLUS);
            }
        }

        /**
         * Add observation value.
         *
         * @param date  the date
         * @param value the value
         */
        public void addObservationValue(String date, String value) {
            endPeriod = date;
            segmentSb.append(prepend + value);
            prepend = EDI_CONSTANTS.PLUS;
        }
    }

    /**
     * Returns sub keys for the current series key, based on the attribute attachment level
     */
    private class AttributeKeyCreator {
        private boolean[] keyPos;

        /**
         * Instantiates a new Attribute key creator.
         *
         * @param attribute the attribute
         */
        public AttributeKeyCreator(AttributeBean attribute) {

            keyPos = new boolean[dimensionIds.size()];

            for (String currentRef : attribute.getDimensionReferences()) {
                keyPos[dimensionIds.indexOf(currentRef)] = true;
            }
        }

        /**
         * Create sub key string.
         *
         * @return the string
         */
        public String createSubKey() {
            String[] keySplit = currentSeriesKey.split(EDI_CONSTANTS.COLON);

            StringBuilder sb = new StringBuilder();
            String concat = "";
            for (int i = 0; i < keyPos.length; i++) {
                sb.append(concat);
                if (keyPos[i]) {
                    sb.append(keySplit[i]);
                }
                concat = EDI_CONSTANTS.COLON;
            }
            return sb.toString();
        }
    }

    /**
     * Class contains date iterations for a given Time Format
     */
    private class DateIterations {
        private Date from;
        private Date to;
        private TIME_FORMAT format;

        private List<String> datesIterated;

        /**
         * Instantiates a new Date iterations.
         *
         * @param format the format
         */
        public DateIterations(TIME_FORMAT format) {
            this.format = format;
        }

        /**
         * Returns a list of all the dates that lie between the from and to dates inclusive
         *
         * @param from
         * @param to
         * @return
         */
        private List<String> getIterations(Date from, Date to) {
            if (this.from == null) {
                datesIterated = DateUtil.createTimeValues(from, to, format);
                this.from = from;
                this.to = to;
            }

            if (from.before(this.from)) {
                List<String> timeValues = DateUtil.createTimeValues(from, this.from, format);
                timeValues.remove(timeValues.size() - 1);  //Remove the last date as this will duplicate what we have
                datesIterated.addAll(0, timeValues);
                this.from = from;
            } else if (to.after(this.to)) {
                List<String> timeValues = DateUtil.createTimeValues(this.to, to, format);
                timeValues.remove(0);  //Remove the first date as this will duplicate what we have
                datesIterated.addAll(timeValues);
                this.to = to;
            }

            String dateFromStr = DateUtil.formatDate(from, format);
            String dateToStr = DateUtil.formatDate(to, format);

            int idxFrom = datesIterated.indexOf(dateFromStr);
            int idxTo = datesIterated.indexOf(dateToStr) + 1;

            return datesIterated.subList(idxFrom, idxTo);
        }
    }
}
