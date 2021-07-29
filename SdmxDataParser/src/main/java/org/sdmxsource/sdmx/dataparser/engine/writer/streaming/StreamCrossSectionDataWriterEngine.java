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
package org.sdmxsource.sdmx.dataparser.engine.writer.streaming;

import org.apache.log4j.Logger;
import org.sdmxsource.sdmx.api.constants.BASE_DATA_FORMAT;
import org.sdmxsource.sdmx.api.constants.SDMX_SCHEMA;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.beans.base.AnnotationBean;
import org.sdmxsource.sdmx.api.model.beans.base.ComponentBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.CrossSectionalDataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataflowBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.PrimaryMeasureBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ProvisionAgreementBean;
import org.sdmxsource.sdmx.api.model.header.DatasetHeaderBean;

import javax.xml.stream.XMLStreamException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Stream cross section data writer engine.
 */
public class StreamCrossSectionDataWriterEngine extends StreamDataWriterEngineBase {
    /**
     * The Flush obs required.
     */
    boolean flushObsRequired = false;
    private Logger LOG = Logger.getLogger(StreamCrossSectionDataWriterEngine.class);
    private List<String> datasetComponents = new ArrayList<>();
    private List<String> groupComponents = new ArrayList<>();
    private List<String> sectionComponents = new ArrayList<>();
    private List<String> obsComponents = new ArrayList<>();
    private String currentGroupKey; //String representing the current cross sectional Group e.g. TIME:2001,FREQ:A,TIME_FORMAT:P1Y
    private String currentSectionKey; //String representing the current cross sectional Section e.g. TIME:2001,FREQ:A,TIME_FORMAT:P1Y
    private Map<String, String> componentVals = new HashMap<>(); //A map containing the current component values for the current series/obs - i.e Time=2001, FREQ=A
    private String obsValue;
    private String timeConcept;

    /**
     * Instantiates a new Stream cross section data writer engine.
     *
     * @param schemaVersion the schema version
     * @param out           the out
     */
    public StreamCrossSectionDataWriterEngine(SDMX_SCHEMA schemaVersion, OutputStream out) {
        super(schemaVersion, BASE_DATA_FORMAT.CROSS_SECTIONAL, out);
        if (schemaVersion != SDMX_SCHEMA.VERSION_ONE && schemaVersion != SDMX_SCHEMA.VERSION_TWO) {
            throw new SdmxException("Can not create a cross sectional writer in version '" + schemaVersion + "', only SDMX version 1.0 & 2.0 supported");
        }
    }

    @Override
    public void startDataset(ProvisionAgreementBean provision, DataflowBean dataflow, DataStructureBean dsd, DatasetHeaderBean header, AnnotationBean... annotations) {
        super.startDataset(provision, dataflow, dsd, header, annotations);
        this.timeConcept = getComponentId(dsd.getTimeDimension());
        if (dsd instanceof CrossSectionalDataStructureBean) {
            LOG.debug("Create Cross Sectional Data Writer with Cross Sectional DataStructure");
            CrossSectionalDataStructureBean xsDsd = (CrossSectionalDataStructureBean) dsd;

            LOG.debug("Populate Dataset Components");
            populateGroupingComponents(xsDsd.getCrossSectionalAttachDataSet(true), datasetComponents);

            LOG.debug("Populate Group Components");
            populateGroupingComponents(xsDsd.getCrossSectionalAttachGroup(true), groupComponents);

            LOG.debug("Populate Section Components");
            populateGroupingComponents(xsDsd.getCrossSectionalAttachSection(true), sectionComponents);

            LOG.debug("Populate Observation Components");
            populateGroupingComponents(xsDsd.getCrossSectionalAttachObservation(), obsComponents);
        } else {
            LOG.debug("Create Cross Sectional Data Writer with Standard DataStructure");
            LOG.debug("Populate Observation Components");
            populateGroupingComponents(dsd.getComponents(), obsComponents);
        }
        datasetAnnotations = annotations;
    }

    private void populateGroupingComponents(List<ComponentBean> components, List<String> setToPopulate) {
        for (ComponentBean currentComponent : components) {
            LOG.debug("Component: " + getComponentId(currentComponent.getId()));
            setToPopulate.add(getComponentId(currentComponent.getId()));
        }
    }

    @Override
    public void startGroup(String groupId, AnnotationBean... annotations) {
        //NOT SUPPORTED
    }

    @Override
    protected void closeDataset() throws XMLStreamException {
        flushObservation();
        writer.flush();
    }

    @Override
    public void writeGroupKeyValue(String conceptId, String conceptValue) {
        throw new UnsupportedOperationException();
    }


    @Override
    public void startSeries(AnnotationBean... annotations) {
        super.startSeries();
        flushObservation();
        componentVals = new HashMap<>();
        seriesAnnotations = annotations;
    }

    @Override
    public void writeSeriesKeyValue(String dimensionId, String dimensionValue) {
        dimensionId = getComponentId(dimensionId);
        super.writeSeriesKeyValue(dimensionId, dimensionValue);
        componentVals.put(dimensionId, dimensionValue);
    }

    @Override
    public void writeObservation(String obsConceptId, String obsIdValue, String obsValue, AnnotationBean... annotations) {
        flushObservation();
        obsConceptId = getComponentId(obsConceptId);
        super.writeObservation(obsConceptId, obsIdValue, obsValue);
        this.obsValue = obsValue;
        componentVals.put(obsConceptId, obsIdValue);
        obsAnnotations = annotations;
        flushObsRequired = true;
    }

    @Override
    public void writeObservation(String obsConceptValue, String obsValue, AnnotationBean... annotations) {
        if (isCrossSectional) {
            writeObservation(crossSectionConcept, obsConceptValue, obsValue, annotations);
        } else {
            writeObservation(timeConcept, obsConceptValue, obsValue, annotations);
        }
    }

    /**
     * Starts a new cross sectional group if there needs to be one.  If a new group is created, a new section will also be created
     *
     * @return true if a new group was started
     * @throws XMLStreamException
     */
    private boolean startNewCrossSectionalGroupIfRequired() throws XMLStreamException {
        if (currentPosition != POSITION.OBSERVATION) {
            currentGroupKey = startNewCrossSection("Group", groupComponents);
            currentSectionKey = startNewCrossSection("Section", sectionComponents);
            return true;
        } else if (groupComponents.size() > 0) {
            String newGroupKey = getUniqueKey(groupComponents);
            if (!currentGroupKey.equals(newGroupKey)) {
                writer.writeEndElement();  //END Section
                writer.writeEndElement();  //END Group
                currentGroupKey = startNewCrossSection("Group", groupComponents);
                currentSectionKey = startNewCrossSection("Section", sectionComponents);
                return true;
            }
        }
        return false;
    }

    private void startNewCrossSectionalSectionfRequired() throws XMLStreamException {
        if (currentPosition != POSITION.OBSERVATION) {
            currentSectionKey = startNewCrossSection("Section", sectionComponents);
        } else if (groupComponents.size() > 0) {
            String newSectionKey = getUniqueKey(sectionComponents);
            if (!currentSectionKey.equals(newSectionKey)) {
                writer.writeEndElement();  //END Section
                currentSectionKey = startNewCrossSection("Section", sectionComponents);
            }
        }
    }

    /**
     * Starts a new section of the given name
     *
     * @param sectionName              the name to call the section (Group, Section, OBS_VALUE)
     * @param crossSectionComponentIds - the component ids that are attached to this section
     * @return
     * @throws XMLStreamException
     */
    private String startNewCrossSection(String sectionName, List<String> crossSectionComponentIds) throws XMLStreamException {
        //If there has not yet been a group written, then create one
        startElement(writer, CROSS_NS, sectionName);  //WRITE THE START GROUP
        for (String currentComponent : crossSectionComponentIds) {
            if (componentVals.containsKey(currentComponent)) {
                writer.writeAttribute(currentComponent, componentVals.get(currentComponent));
            }
        }
        return getUniqueKey(crossSectionComponentIds);

    }

    /**
     * Returns the unique key for the series, for the goruping components.  Example:
     * FREQ:A,TIME:2007
     *
     * @param crossSectionComponentIds
     * @return
     */
    private String getUniqueKey(List<String> crossSectionComponentIds) {
        StringBuilder sb = new StringBuilder();
        sb.append("");
        for (String currentComponent : crossSectionComponentIds) {
            sb.append(currentComponent);
            sb.append(":");
            sb.append(componentVals.get(currentComponent));
        }

        return sb.toString();
    }

    private void flushObservation() {
        if (!flushObsRequired) {
            return;
        }
        try {
            //If this is a new Grouping, start a Cross Sectional Group
            if (!startNewCrossSectionalGroupIfRequired()) {
                //If a new group wasn't created, but this is a new Section, start a new Cross Sectional Section
                startNewCrossSectionalSectionfRequired();
            }

            //write the observation
            currentPosition = POSITION.OBSERVATION;

            startElement(writer, CROSS_NS, PrimaryMeasureBean.FIXED_ID);  //WRITE THE START GROUP

            for (String currentComponent : obsComponents) {
                if (componentVals.containsKey(currentComponent)) {
                    writer.writeAttribute(currentComponent, componentVals.get(currentComponent));
                }
            }
            writer.writeAttribute("value", obsValue);

            writeAnnotations(writer, obsAnnotations);
            obsAnnotations = null;

            writer.writeEndElement();  //END OBS
            flushObsRequired = false;
        } catch (XMLStreamException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    @Override
    public void writeAttributeValue(String attributeId, String attributeValue) {
        attributeId = getComponentId(attributeId);
        super.writeAttributeValue(attributeId, attributeValue);
        if (attributeValue == null) {
            attributeValue = "";
        }
        componentVals.put(attributeId, attributeValue);
    }

}
