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

import org.sdmxsource.sdmx.api.constants.ATTRIBUTE_ATTACHMENT_LEVEL;
import org.sdmxsource.sdmx.api.constants.DATASET_ACTION;
import org.sdmxsource.sdmx.api.constants.DATASET_POSITION;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.constants.TIME_FORMAT;
import org.sdmxsource.sdmx.api.engine.DataReaderEngine;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.exception.SdmxNotImplementedException;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.exception.SdmxSyntaxException;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.beans.base.ComponentBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.*;
import org.sdmxsource.sdmx.api.model.beans.registry.ProvisionAgreementBean;
import org.sdmxsource.sdmx.api.model.data.KeyValue;
import org.sdmxsource.sdmx.api.model.data.Keyable;
import org.sdmxsource.sdmx.api.model.data.Observation;
import org.sdmxsource.sdmx.api.util.ReadableDataLocation;
import org.sdmxsource.sdmx.sdmxbeans.model.data.KeyValueImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.data.KeyableImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.data.ObservationImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.header.DatasetHeaderBeanImpl;
import org.sdmxsource.sdmx.util.beans.ConceptRefUtil;
import org.sdmxsource.sdmx.util.date.DateUtil;
import org.sdmxsource.sdmx.util.stax.StaxUtil;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.util.*;
import java.util.stream.Collectors;


/**
 * The type Compact data reader engine.
 */
public class CompactDataReaderEngine extends AbstractSdmxDataReaderEngine {
    private static final long serialVersionUID = 7750528288236102625L;
    private Map<String, String> rolledUpAttributes = new HashMap<String, String>();
    private Map<String, String> keyValues = new HashMap<String, String>();
    private Map<String, String> attributeValues = new HashMap<String, String>();

    private List<String> groups = new ArrayList<String>();

    //CONCEPTS
    private List<String> dimensionConcepts = new ArrayList<String>();
    private Map<String, List<String>> groupConcepts = new HashMap<String, List<String>>();

    private String primaryMeasureConcept;
    private String timeConcept;

    //ATTRIBUTES
    private Set<String> datasetAttributes = new HashSet<String>();
    private Set<String> seriesAttributes = new HashSet<String>();
    private Set<String> observationAttributes = new HashSet<String>();
    private Map<String, Set<String>> groupAttributeConcepts = new HashMap<String, Set<String>>();

    //OBS INFO
    private String obsTime = null;
    private String obsValue = null;
    private List<KeyValue> attributes = new ArrayList<KeyValue>();
    private KeyValue crossSection = null;
    private Map<String, String> attributesOnDatasetNode = new HashMap<String, String>();

    /**
     * Creates a reader engine based on the data location, and the data structure to use to interpret the data
     *
     * @param dataLocation           the location of the data
     * @param dataStructureBean      the dsd to use to interpret the data
     * @param dataflowBean           the dataflow bean
     * @param provisionAgreementBean the provision agreement bean
     */
    public CompactDataReaderEngine(ReadableDataLocation dataLocation, DataStructureBean dataStructureBean, DataflowBean dataflowBean, ProvisionAgreementBean provisionAgreementBean) {
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
    public CompactDataReaderEngine(ReadableDataLocation dataLocation, SdmxBeanRetrievalManager beanRetrieval, DataStructureBean dataStructureBean,
                                   DataflowBean dataflowBean, ProvisionAgreementBean provisionAgreementBean) {
        super(dataLocation, beanRetrieval, dataStructureBean, dataflowBean, provisionAgreementBean);
        reset();
    }

    @Override
    public DataReaderEngine createCopy() {
        return new CompactDataReaderEngine(dataLocation.copy(), beanRetrieval, defaultDsd, defaultDataflow, defaultProvisionAgreement);
    }

    private void populateGroupConcepts(GroupBean groupBean, List<String> groups) {
        for (String dimensionId : groupBean.getDimensionRefs()) {
            groups.add(dimensionId);
        }
    }

    private String getComponentId(ComponentBean component) {
        if (component == null) {
            return null;
        }
        if (isTwoPointOne) {
            return component.getId();
        }
        return ConceptRefUtil.getConceptId(component.getConceptRef());
    }

    @Override
    protected boolean next(boolean includeObs) throws XMLStreamException {
        while (parser.hasNext()) {
            int event = parser.next();
            if (event == XMLStreamConstants.START_ELEMENT) {
                String nodeName = parser.getLocalName();
                if (nodeName.equals("DataSet")) {
                    datasetPosition = DATASET_POSITION.DATASET;
                    processDataSetNode();
                    return true;
                } else if (nodeName.equals("Series")) {
                    StaxUtil.jumpToNode(runAheadParser, "Series", null);
                    datasetPosition = DATASET_POSITION.SERIES;
                    return true;
                } else if (!isTwoPointOne && groups.contains(nodeName)) {
                    datasetPosition = DATASET_POSITION.GROUP;
                    groupId = nodeName;
                    return true;
                } else if (isTwoPointOne && nodeName.equals("Group")) {
                    datasetPosition = DATASET_POSITION.GROUP;
                    groupId = parser.getAttributeValue("xsi", "type");
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
                } else if (nodeName.equals("Annotations")) {
                    StaxUtil.skipNode(parser);
                } else {
                    throw new SdmxSyntaxException("Unexpected Node in XML: " + nodeName);
                }
            } else if (event == XMLStreamConstants.END_ELEMENT) {
                String nodeName = parser.getLocalName();
                if (nodeName.equals("Series")) {
                    datasetPosition = null;
                } else if (groups.contains(nodeName)) {
                    datasetPosition = null;
                } else if (nodeName.equals("Group")) {
                    datasetPosition = null;
                }
            }
        }

        datasetPosition = null;
        hasNext = false;
        return false;
    }

    private void processDataSetNode() {
        rolledUpAttributes = new HashMap<String, String>();
        keyValues = new HashMap<String, String>();
        attributeValues = new HashMap<String, String>();
        attributesOnDatasetNode = new HashMap<String, String>();

        this.datasetHeaderBean = new DatasetHeaderBeanImpl(parser, headerBean);
        for (int i = 0; i < parser.getAttributeCount(); i++) {
            String attributeId = parser.getAttributeLocalName(i);
            attributeId = getComponentId(attributeId);
            attributesOnDatasetNode.put(attributeId, parser.getAttributeValue(i));
        }
    }

    private void setDimensionAtObservation(String dimensionAtObservation) {
        observationAttributes = new HashSet<String>();
        seriesAttributes = new HashSet<String>();
        for (AttributeBean attributeBean : currentDsd.getSeriesAttributes(dimensionAtObservation)) {
            seriesAttributes.add(getComponentId(attributeBean));
        }

        //Create a list of observation attribute concepts
        for (AttributeBean attributeBean : currentDsd.getObservationAttributes(dimensionAtObservation)) {
            observationAttributes.add(getComponentId(attributeBean));
        }
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
    protected void setCurrentDsd(DataStructureBean dsd) {
        super.setCurrentDsd(dsd);

        //reset all maps
        dimensionConcepts = new ArrayList<String>();
        datasetAttributes = new HashSet<String>();
        seriesAttributes = new HashSet<String>();
        observationAttributes = new HashSet<String>();
        groups = new ArrayList<String>();
        groupConcepts = new HashMap<String, List<String>>();

        if (datasetHeaderBean.getDataStructureReference() != null) {
            setDimensionAtObservation(datasetHeaderBean.getDataStructureReference().getDimensionAtObservation());
        } else {
            setDimensionAtObservation(DimensionBean.TIME_DIMENSION_FIXED_ID);
        }

        //Roll up any attribute values
        for (String attributeId : attributesOnDatasetNode.keySet()) {
            ComponentBean component = dsd.getComponent(attributeId);
            if (component != null) {
                rolledUpAttributes.put(attributeId, attributesOnDatasetNode.get(attributeId));
            }
        }

        //Create a list of dimension concepts
        for (DimensionBean dimensionBean : dsd.getDimensions(SDMX_STRUCTURE_TYPE.DIMENSION, SDMX_STRUCTURE_TYPE.MEASURE_DIMENSION)) {
            dimensionConcepts.add(dimensionBean.getId());
        }

        //Create a list of dataset attribute concepts
        for (AttributeBean attributeBean : dsd.getDatasetAttributes()) {
            datasetAttributes.add(attributeBean.getId());
        }

        //Create a list of dimension group attribute concepts
        for (AttributeBean attributeBean : dsd.getDimensionGroupAttributes()) {
            seriesAttributes.add(attributeBean.getId());
        }

        //Create a list of observation attribute concepts
        for (AttributeBean attributeBean : dsd.getObservationAttributes()) {
            observationAttributes.add(attributeBean.getId());
        }
        primaryMeasureConcept = dsd.getPrimaryMeasure().getId();
        if (dsd.getTimeDimension() == null) {
            throw new SdmxNotImplementedException("The DSD: " + dsd.getId() + " has no time dimension. This is unsupported!");
        }
        timeConcept = dsd.getTimeDimension().getId();

        for (GroupBean groupBean : dsd.getGroups()) {
            String groupId = groupBean.getId();
            groups.add(groupId);

            Set<String> groupAttributes = new HashSet<String>();
            for (AttributeBean attributeBean : dsd.getGroupAttributes(groupId, true)) {
                groupAttributes.add(attributeBean.getId());
            }
            groupAttributeConcepts.put(groupId, groupAttributes);

            List<String> groups = new ArrayList<String>();
            populateGroupConcepts(groupBean, groups);
            groupConcepts.put(groupId, groups);
        }

        dsd.getAttributes().stream()
            .filter(attr -> attr.getAttachmentLevel().equals(ATTRIBUTE_ATTACHMENT_LEVEL.DIMENSION_GROUP))
            .forEach(attr -> {
                var dimRefs = attr.getDimensionReferences();
                var joiner = new StringJoiner(".");

                dimRefs.forEach(joiner::add);
                String id = joiner.toString();

                groupConcepts.put(id, dimRefs);

                var attributeConcepts = dsd.getAttributeList().getAttributes().stream()
                    .map(AttributeBean::getId)
                    .collect(Collectors.toSet());

                groupAttributeConcepts.put(id, attributeConcepts);
            });
    }

    @Override
    protected Keyable processSeriesNode() {
        //Clear values
        keyValues.clear();
        attributeValues.clear();
        TIME_FORMAT timeFormat = null;
        String timeValue = null;
        Set<String> unknownConcepts = new HashSet<String>();
        for (int i = 0; i < parser.getAttributeCount(); i++) {
            String attributeId = getComponentId(parser.getAttributeLocalName(i));
            String attributeValue = parser.getAttributeValue(i);
            if (attributeId.equals(timeConcept)) {
                timeValue = attributeValue;
                timeFormat = DateUtil.getTimeFormatOfDate(timeValue);
            } else if (dimensionConcepts.contains(attributeId)) {
                keyValues.put(attributeId, attributeValue);
            } else if (seriesAttributes.contains(attributeId)) {
                attributeValues.put(attributeId, attributeValue);
            } else if (datasetPosition == DATASET_POSITION.OBSERAVTION_AS_SERIES) {
                //This attribute was not found as a series level attribute, it could still be an observation level attribute
                //But this is only allowed if we are processing this series node as a FLAT obs node which is both an obs and key
                //In which case this attributeName could be an observation attribute, the primary measure, or the time concept.
                if (!observationAttributes.contains(attributeId)
                        && !attributeId.equals(primaryMeasureConcept)
                        && !attributeId.equals(timeConcept)) {
                    unknownConcepts.add(attributeId);
                }
            } else {
                unknownConcepts.add(attributeId);
            }
        }


        //System.out.println(keyValues);
        List<KeyValue> key = new ArrayList<KeyValue>();
        for (String dimensionConcept : dimensionConcepts) {
            String conceptValue = null;
            if (keyValues.containsKey(dimensionConcept)) {
                conceptValue = keyValues.get(dimensionConcept);
            } else {
                conceptValue = rolledUpAttributes.get(dimensionConcept);
            }
            if (conceptValue == null && datasetHeaderBean.getAction() != DATASET_ACTION.DELETE) {
                if (!isTimeSeries() && getCrossSectionConcept().equals(dimensionConcept)) {
                    //This is expected
                } else {
                    throw new SdmxSemmanticException("Missing series key value for concept: " + dimensionConcept);
                }
            } else {
                KeyValue kv = new KeyValueImpl(conceptValue, dimensionConcept);
                key.add(kv);
            }
        }

        if (unknownConcepts.size() > 0) {
            StringBuilder sb = new StringBuilder();
            String concat = "";
            for (KeyValue kv : key) {
                sb.append(concat + kv.getCode());
                concat = ", ";
            }
            concat = "";
            String series = sb.toString();
            sb = new StringBuilder();
            for (String unknownconcept : unknownConcepts) {
                sb.append(concat + unknownconcept);
                concat = ", ";
            }
            throw new SdmxSemmanticException("Unknown concept(s) '" + sb.toString() + "' reported for series : " + series);
        }

        List<KeyValue> attributes = new ArrayList<KeyValue>();
        try {
            processAttributes(seriesAttributes, attributes);
        } catch (SdmxException e) {
            throw new SdmxException(e, "Error while procesing series attributes");
        }


        //Clear values
        keyValues.clear();
        attributeValues.clear();

        if (isTimeSeries()) {
            if (datasetPosition == DATASET_POSITION.SERIES) {
                try {
                    while (runAheadParser.hasNext()) {
                        int event = runAheadParser.next();
                        if (event == XMLStreamConstants.START_ELEMENT) {
                            if (runAheadParser.getLocalName().equals("Obs")) {
                                processObservation(runAheadParser);
                                timeFormat = DateUtil.getTimeFormatOfDate(obsTime);
                                break;
                            }
                        } else if (event == XMLStreamConstants.END_ELEMENT) {
                            if (runAheadParser.getLocalName().equals("Series")) {
                                break;
                            }
                        }
                    }
                } catch (XMLStreamException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        if (isTimeSeries()) {
            currentKey = new KeyableImpl(currentDataflow, currentDsd, key, attributes, timeFormat);
        } else {
            currentKey = new KeyableImpl(currentDataflow, currentDsd, key, attributes, timeFormat, getCrossSectionConcept(), timeValue);
        }
        if (datasetPosition == DATASET_POSITION.OBSERAVTION_AS_SERIES) {
            currentObs = processObsNode(parser);
            timeFormat = currentObs.getObsTimeFormat();
        }

        attributeValues.clear();
        return currentKey;
    }

    @Override
    protected Keyable processGroupNode() {
        //Clear values
        keyValues.clear();
        attributeValues.clear();

        for (int i = 0; i < parser.getAttributeCount(); i++) {
            String attributeName = getComponentId(parser.getAttributeLocalName(i));
            String attributeValue = parser.getAttributeValue(i);
            String namespace = parser.getAttributePrefix(i);
            if (namespace != null && namespace.equals("xsi") && attributeName.equals("type")) {
                if (attributeValue.contains(":")) {
                    groupId = attributeValue.split(":")[1];
                } else {
                    groupId = attributeValue;
                }
            } else {
                if (dimensionConcepts.contains(attributeName)) {
                    keyValues.put(attributeName, attributeValue);
                } else {
                    attributeValues.put(attributeName, attributeValue);
                }
            }
        }
        List<KeyValue> key = new ArrayList<KeyValue>();
        List<KeyValue> attributes = new ArrayList<KeyValue>();

        if (!groupConcepts.containsKey(groupId)) {
            throw new SdmxSemmanticException("Data Structure '" + currentDsd + "' does not contain group '" + groupId + "'");
        }
        for (String groupConcept : groupConcepts.get(groupId)) {
            String conceptValue = null;
            if (keyValues.containsKey(groupConcept)) {
                conceptValue = keyValues.get(groupConcept);
            } else {
                conceptValue = rolledUpAttributes.get(groupConcept);
            }
            if (conceptValue == null) {
                throw new IllegalArgumentException("No value found in data for group '" + groupId + "' and concept '" + groupConcept + "'.  ");
            }
            KeyValue kv = new KeyValueImpl(conceptValue, groupConcept);
            key.add(kv);
        }

        try {
            processAttributes(groupAttributeConcepts.get(groupId), attributes);
        } catch (SdmxSemmanticException e) {
            throw new SdmxSemmanticException(e, "Error while procesing group attributes for group '" + groupId + "' ");
        }


        //Clear values
        keyValues.clear();
        attributeValues.clear();

        currentKey = new KeyableImpl(currentDataflow, currentDsd, key, attributes, groupId);
        return currentKey;
    }

    @Override
    protected Observation processObsNode(XMLStreamReader parser) {
        clearObsInformation();
        processObservation(parser);
        try {
            if (isTimeSeries()) {
                return new ObservationImpl(currentKey, obsTime, obsValue, attributes);
            }
            return new ObservationImpl(currentKey, currentKey.getObsTime(), obsValue, attributes, crossSection);
        } catch (Throwable th) {
            if (currentKey != null) {
                throw new SdmxSemmanticException(th, "Error while processing observation for key " + currentKey);
            }
            throw new SdmxSemmanticException(th, "Error while processing observation");
        } finally {
            attributes.clear();
        }
    }

    private void processObservation(XMLStreamReader parser) {
        clearObsInformation();

        for (int i = 0; i < parser.getAttributeCount(); i++) {
            String attributeId = getComponentId(parser.getAttributeLocalName(i));
            String attributeValue = parser.getAttributeValue(i);
            if (!isTimeSeries() && attributeId.equals(getCrossSectionConcept())) {
                crossSection = new KeyValueImpl(attributeValue, attributeId);
            } else if (observationAttributes.contains(attributeId)) {
                attributeValues.put(attributeId, attributeValue);
            } else if (attributeId.equals(primaryMeasureConcept)) {
                obsValue = attributeValue;
            } else if (attributeId.equals(timeConcept)) {
                obsTime = attributeValue;
            }
        }
        try {
            processAttributes(observationAttributes, attributes);
        } catch (SdmxSemmanticException e) {
            throw new SdmxSemmanticException(e, "Error while procesing observation attributes");
        }
        try {
            if (!isTimeSeries() && crossSection == null) {
                throw new SdmxSemmanticException("Error while processing observation for series '" + currentKey + "' , missing required concept '" + getCrossSectionConcept() + "'");
            }
        } catch (Throwable th) {
            if (currentKey != null) {
                throw new SdmxSemmanticException(th, "Error while processing observation for key " + currentKey);
            }
            throw new SdmxSemmanticException(th, "Error while processing observation");
        }
        //Clear values
        attributeValues.clear();
    }

    private void clearObsInformation() {
        //Clear values
        attributeValues.clear();

        //Clear the current Obs information
        obsTime = null;
        obsValue = null;
        attributes = new ArrayList<KeyValue>();
        crossSection = null;
    }

    private void processAttributes(Set<String> attributeConcepts, List<KeyValue> attributes) {
        for (String attributeConcept : attributeConcepts) {
            String conceptValue = null;
            if (attributeValues.containsKey(attributeConcept)) {
                conceptValue = attributeValues.get(attributeConcept);
            } else {
                conceptValue = rolledUpAttributes.get(attributeConcept);
            }
            if (conceptValue != null) {
                KeyValue kv = new KeyValueImpl(conceptValue, attributeConcept);
                attributes.add(kv);
            }
        }
        if (attributeValues.keySet().size() != attributes.size()) {
            for (String attribute : attributeValues.keySet()) {
                if (!attributeConcepts.contains(attribute)) {
                    throw new SdmxSemmanticException("Unknown attribute '" + attribute + "' reported in the data.  This attribute is not defined by the data structure definition");
                }
            }
        }
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
