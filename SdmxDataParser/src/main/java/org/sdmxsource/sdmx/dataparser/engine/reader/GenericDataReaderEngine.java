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
package org.sdmxsource.sdmx.dataparser.engine.reader;

import org.sdmxsource.sdmx.api.constants.DATASET_POSITION;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.constants.TIME_FORMAT;
import org.sdmxsource.sdmx.api.engine.DataReaderEngine;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.exception.SdmxSyntaxException;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.AttributeBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataflowBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DimensionBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ProvisionAgreementBean;
import org.sdmxsource.sdmx.api.model.data.KeyValue;
import org.sdmxsource.sdmx.api.model.data.Keyable;
import org.sdmxsource.sdmx.api.model.data.Observation;
import org.sdmxsource.sdmx.api.model.header.DatasetStructureReferenceBean;
import org.sdmxsource.sdmx.api.util.ReadableDataLocation;
import org.sdmxsource.sdmx.sdmxbeans.model.data.KeyValueImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.data.KeyableImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.data.ObservationImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.header.DatasetHeaderBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.header.DatasetStructureReferenceBeanImpl;
import org.sdmxsource.sdmx.util.beans.DataStructureUtil;
import org.sdmxsource.sdmx.util.beans.reference.StructureReferenceBeanImpl;
import org.sdmxsource.sdmx.util.date.DateUtil;
import org.sdmxsource.sdmx.util.stax.StaxUtil;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The type Generic data reader engine.
 */
public class GenericDataReaderEngine extends AbstractSdmxDataReaderEngine {
    private static final long serialVersionUID = 8712324190069733547L;
    private Map<String, String> attributesOnDatasetNode = new HashMap<String, String>();

    /**
     * Creates a reader engine based on the data location, and the data structure to use to interpret the data
     *
     * @param dataLocation           the location of the data
     * @param dataStructureBean      the dsd to use to interpret the data
     * @param dataflowBean           the dataflow bean
     * @param provisionAgreementBean the provision agreement bean
     */
    public GenericDataReaderEngine(ReadableDataLocation dataLocation, DataStructureBean dataStructureBean, DataflowBean dataflowBean, ProvisionAgreementBean provisionAgreementBean) {
        this(dataLocation, null, dataStructureBean, dataflowBean, provisionAgreementBean);
    }

    /**
     * Creates a reader engine based on the data location, the location of available data structures that can be used to retrieve dsds, and the default dsd to use
     *
     * @param dataLocation           the location of the data
     * @param beanRetrieval          giving the ability to retrieve dsds for the datasets this reader engine is reading.  This can be null if there is only one relevent dsd - in which case the default dsd should be provided
     * @param dataStructureBean      the dsd to use if the beanRetrieval is null, or if the bean retrieval does not return the dsd for the given dataset
     * @param dataflowBean           the dataflow bean
     * @param provisionAgreementBean the provision agreement bean
     */
    public GenericDataReaderEngine(ReadableDataLocation dataLocation, SdmxBeanRetrievalManager beanRetrieval, DataStructureBean dataStructureBean, DataflowBean dataflowBean, ProvisionAgreementBean provisionAgreementBean) {
        super(dataLocation, beanRetrieval, dataStructureBean, dataflowBean, provisionAgreementBean);
        reset();
    }

    @Override
    public DataReaderEngine createCopy() {
        return new GenericDataReaderEngine(dataLocation, beanRetrieval, defaultDsd, defaultDataflow, defaultProvisionAgreement);
    }

    @Override
    public List<KeyValue> getDatasetAttributes() {
        List<KeyValue> returnList = new ArrayList<KeyValue>();
        for (AttributeBean attr : currentDsd.getDatasetAttributes()) {
            String attributeValue = attributesOnDatasetNode.get(attr.getId());
            if (attributeValue != null) {
                returnList.add(new KeyValueImpl(attributeValue, attr.getId()));
            }
        }
        return returnList;
    }

    @Override
    protected boolean next(boolean includeObs) {
        String nodeName = null;
        try {
            while (parser.hasNext()) {
                int event = parser.next();
                if (event == XMLStreamConstants.START_ELEMENT) {
                    nodeName = parser.getLocalName();
                    if (nodeName.equalsIgnoreCase("Dataset")) {
                        datasetPosition = DATASET_POSITION.DATASET;
                        attributesOnDatasetNode = new HashMap<String, String>();
                        this.datasetHeaderBean = new DatasetHeaderBeanImpl(parser, headerBean);
                        String dsdId = processDatasetNode();

                        DatasetStructureReferenceBean dsRef = datasetHeaderBean.getDataStructureReference();
                        StructureReferenceBean structureReference = null;
                        String id = null;
                        String serviceURL = null;
                        String structureURL = null;
                        String dimensionAtObservation = null;

                        if (dsRef != null) {
                            id = dsRef.getId();
                            serviceURL = dsRef.getServiceURL();
                            structureURL = dsRef.getStructureURL();
                            dimensionAtObservation = dsRef.getDimensionAtObservation();
                            structureReference = dsRef.getStructureReference();
                        }
                        if (structureReference == null) {
                            if (defaultDsd != null && defaultDsd.getId().equals(dsdId)) {
                                structureReference = defaultDsd.asReference();
                            } else {
                                structureReference = new StructureReferenceBeanImpl(null, dsdId, MaintainableBean.DEFAULT_VERSION, SDMX_STRUCTURE_TYPE.DSD);
                            }
                        }
                        dsRef = new DatasetStructureReferenceBeanImpl(id, structureReference, serviceURL, structureURL, dimensionAtObservation);
                        datasetHeaderBean = datasetHeaderBean.modifyDataStructureReference(dsRef);
                        return true;
                    } else if (nodeName.equals("Series")) {
                        StaxUtil.skipToEndNode(runAheadParser, "SeriesKey");  //Send Run ahead parser to the next key node
                        datasetPosition = DATASET_POSITION.SERIES;
                        return true;
                    } else if (nodeName.equals("Group")) {
                        StaxUtil.skipToEndNode(runAheadParser, "GroupKey");   //Send Run ahead parser to the next key node
                        datasetPosition = DATASET_POSITION.GROUP;
                        groupId = parser.getAttributeValue(null, "type");
                        return true;
                    } else if (nodeName.equals("Obs")) {
                        if (datasetPosition == DATASET_POSITION.SERIES || datasetPosition == DATASET_POSITION.OBSERVATION) {
                            if (includeObs) {
                                datasetPosition = DATASET_POSITION.OBSERVATION;
                                return true;
                            } else {
                                continue;
                            }
                        }
                        datasetPosition = DATASET_POSITION.OBSERAVTION_AS_SERIES;
                        return true;
                    } else if (nodeName.equals("Annotations") || nodeName.equals("Attributes")) {
                        StaxUtil.skipNode(parser);
                    } else if (nodeName.equals("KeyFamilyRef") || nodeName.equals("Time") || nodeName.equals("ObsValue") || nodeName.equals("ObsDimension") || nodeName.equals("SeriesKey") || nodeName.equals("Value") || nodeName.equals("GroupKey")) {
                        //DO NOTHING
                    } else {
                        throw new SdmxSyntaxException("Unexpected Node in XML: " + nodeName);
                    }
                } else if (event == XMLStreamConstants.END_ELEMENT) {
                    nodeName = parser.getLocalName();
                    if (nodeName.equals("Series")) {
                        datasetPosition = null;
                    } else if (nodeName.equals("Group")) {
                        datasetPosition = null;
                    }
                }
            }
        } catch (XMLStreamException ex) {
            close();
            throw new RuntimeException(ex);
        }
        hasNext = false;
        return false;
    }

    /**
     * Sends the runAheadParser ahead to see if there are any dataset attributes, will stop running ahead if it finds a series, group or end of dataset element
     *
     * @return the DSD id referenced
     * @throws XMLStreamException
     */
    private String processDatasetNode() throws XMLStreamException {
        String keyFamilyRef = null;
        if (getDatasetPosition() >= 0) {
            if (runAheadParser.getEventType() == 1 && "DataSet".equals(runAheadParser.getLocalName())) {

            } else {
                StaxUtil.skipToNode(runAheadParser, "DataSet");
            }
        }

        while (runAheadParser.hasNext()) {
            int event = runAheadParser.next();
            if (event == XMLStreamConstants.START_ELEMENT) {
                String nodeName = runAheadParser.getLocalName();
                if (nodeName.equals("Attributes")) {
                    List<KeyValue> attributes = getKeyValues("Attributes");  //We have got the series key attributes
                    for (KeyValue kv : attributes) {
                        attributesOnDatasetNode.put(kv.getConcept(), kv.getCode());
                    }
                } else if (nodeName.equals("KeyFamilyRef")) {
                    keyFamilyRef = runAheadParser.getElementText();
                } else if (nodeName.equals("Series")) {
                    return keyFamilyRef;
                } else if (nodeName.equals("Group")) {
                    return keyFamilyRef;
                } else if (nodeName.equals("Obs")) {
                    return keyFamilyRef;
                }
            } else if (event == XMLStreamConstants.END_ELEMENT) {
                String nodeName = runAheadParser.getLocalName();
                if (nodeName.equals("DataSet")) {
                    return null;
                }
            }
        }
        return null;
    }

    @Override
    protected Keyable processSeriesNode() throws XMLStreamException {
        List<KeyValue> key;
        if (noSeries) {
            key = getKeyValues("ObsKey");
        } else {
            key = getKeyValues("SeriesKey");
        }
        List<KeyValue> attributes = null;
        TIME_FORMAT timeFormat = null;
        String obsValue = null;
        String obsConcept = null;

        //Send Run ahead parser to check the next node, if it is the attributes node then process it,
        //Also move forward to the first obs and get the time format, if we hit the end series node, without finding any obs then stop
        boolean inSeries = true;
        while (runAheadParser.hasNext()) {
            int event = runAheadParser.next();
            if (event == XMLStreamConstants.START_ELEMENT) {
                String nodeName = runAheadParser.getLocalName();
                if (inSeries && nodeName.equals("Attributes")) {
                    attributes = getKeyValues("Attributes");  //We have got the series key attributes
                } else if (nodeName.equals("Time")) {
                    obsConcept = runAheadParser.getElementText();
                    break;
                } else if (nodeName.equals("Obs")) {
                    if (!super.noSeries) {
                        inSeries = false; //We are no longer in a series, so we don't want to process obs attributes here
                    }
                } else if (nodeName.equals("ObsDimension")) {
                    obsConcept = runAheadParser.getAttributeValue(null, "value");
                    break;
                } else if (nodeName.equals("ObsValue")) {
                    //We are in a No series key situation here
                    obsValue = runAheadParser.getAttributeValue(null, "value");
                }
            } else if (event == XMLStreamConstants.END_ELEMENT) {
                String nodeName = runAheadParser.getLocalName();
                if (nodeName.equals("Series")) {
                    break;
                } else if (noSeries && nodeName.equals("Obs")) {
                    break;
                }
            }
        }

        if (noSeries) {
            List<KeyValue> seriesKey = new ArrayList<KeyValue>();
            String obsTime = null;
            for (KeyValue currentKeyValue : key) {
                if (currentKeyValue.getConcept().equals(DimensionBean.TIME_DIMENSION_FIXED_ID)) {
                    obsTime = currentKeyValue.getCode();
                } else {
                    seriesKey.add(currentKeyValue);
                }
            }
            List<KeyValue> seriesAttributes = new ArrayList<KeyValue>();
            List<KeyValue> obsAttributes = new ArrayList<KeyValue>();
            List<String> observationAttributeIds = DataStructureUtil.getObservationConcepts(currentDsd);
            if (attributes != null) {
                for (KeyValue currentKv : attributes) {
                    if (observationAttributeIds.contains(currentKv.getConcept())) {
                        obsAttributes.add(currentKv);
                    } else {
                        seriesAttributes.add(currentKv);
                    }
                }
            }
            timeFormat = obsTime != null ? DateUtil.getTimeFormatOfDate(obsTime) : null;
            currentKey = new KeyableImpl(currentDataflow, currentDsd, seriesKey, seriesAttributes, timeFormat);
            currentObs = new ObservationImpl(currentKey, obsTime, obsValue, obsAttributes);
        } else {
            if (isTimeSeries()) {
                if (obsConcept != null) {
                    timeFormat = DateUtil.getTimeFormatOfDate(obsConcept);
                }
                currentKey = new KeyableImpl(currentDataflow, currentDsd, key, attributes, timeFormat);
            } else {
                List<KeyValue> seriesKey = new ArrayList<KeyValue>();
                String crossSectionTime = null;
                for (KeyValue currentKeyValue : key) {
                    if (currentKeyValue.getConcept().equals(DimensionBean.TIME_DIMENSION_FIXED_ID)) {
                        crossSectionTime = currentKeyValue.getCode();
                        timeFormat = DateUtil.getTimeFormatOfDate(crossSectionTime);
                    } else {
                        seriesKey.add(currentKeyValue);
                    }
                }
                currentKey = new KeyableImpl(currentDataflow, currentDsd, seriesKey, attributes, timeFormat, getCrossSectionConcept(), crossSectionTime);
            }
        }
        return currentKey;
    }

    @Override
    protected Keyable processGroupNode() throws XMLStreamException {
        List<KeyValue> key = getKeyValues("GroupKey");
        List<KeyValue> attributes = null;

        //Send Run ahead parser to check the next node, if it is the attributes node then process it, otherwise stop
        while (runAheadParser.hasNext()) {
            int event = runAheadParser.next();
            if (event == XMLStreamConstants.START_ELEMENT) {
                if (runAheadParser.getLocalName().equals("Attributes")) {
                    attributes = getKeyValues("Attributes");
                } else {
                    break;
                }
            }
        }
        currentKey = new KeyableImpl(currentDataflow, currentDsd, key, attributes, groupId);
        return currentKey;
    }

    @Override
    protected Observation processObsNode(XMLStreamReader parser) throws XMLStreamException {
        String obsDimension = null;
        String obsValue = null;
        List<KeyValue> attributes = null;

        while (parser.hasNext()) {
            int event = parser.next();
            if (event == XMLStreamConstants.START_ELEMENT) {
                String nodeName = parser.getLocalName();
                if (nodeName.equals("Time")) {
                    obsDimension = parser.getElementText();
                } else if (nodeName.equals("ObsDimension")) {
                    obsDimension = parser.getAttributeValue(null, "value");
                } else if (nodeName.equals("ObsValue")) {
                    obsValue = parser.getAttributeValue(null, "value");
                } else if (nodeName.equals("Attributes")) {
                    attributes = getKeyValues("Attributes");
                }
            } else if (event == XMLStreamConstants.END_ELEMENT) {
                if (parser.getLocalName().equals("Obs")) {
                    break;
                }
            }
        }
        obsDimension = getComponentId(obsDimension);

        try {
            if (isTimeSeries()) {
                return new ObservationImpl(currentKey, obsDimension, obsValue, attributes);
            }
            if (obsDimension == null) {
                throw new SdmxSemmanticException("Error while processing observation for series '" + currentKey + "' , missing required cross sectional concept value '" + getCrossSectionConcept() + "'");
            }
            KeyValue crossSection = new KeyValueImpl(obsDimension, getCrossSectionConcept());
            return new ObservationImpl(currentKey, currentKey.getObsTime(), obsValue, attributes, crossSection);
        } catch (Throwable th) {
            if (currentKey != null) {
                throw new RuntimeException("Error while processing observation for key " + currentKey, th);
            }
            throw new RuntimeException("Error while processing observation");
        }
    }

    private List<KeyValue> getKeyValues(String endElement) throws XMLStreamException {
        List<KeyValue> returnList = new ArrayList<KeyValue>();
        while (parser.hasNext()) {
            int event = parser.next();
            if (event == XMLStreamConstants.START_ELEMENT) {
                String nodeName = parser.getLocalName();
                if (nodeName.equals("Value")) {
                    String componetVal = parser.getAttributeValue(null, "value");
                    if (isTwoPointOne) {
                        returnList.add(new KeyValueImpl(componetVal, parser.getAttributeValue(null, "id")));
                    } else {
                        String componentId = getComponentId(parser.getAttributeValue(null, "concept"));
                        returnList.add(new KeyValueImpl(componetVal, componentId));
                    }
                }
            } else if (event == XMLStreamConstants.END_ELEMENT) {
                if (parser.getLocalName().equals(endElement)) {
                    break;
                }
            }
        }
        return returnList;
    }

    /**
     * The enum Move to.
     */
    public enum MOVE_TO {
        /**
         * Series move to.
         */
        SERIES,
        /**
         * Group move to.
         */
        GROUP,
        /**
         * Keyable move to.
         */
        KEYABLE;
    }
}
