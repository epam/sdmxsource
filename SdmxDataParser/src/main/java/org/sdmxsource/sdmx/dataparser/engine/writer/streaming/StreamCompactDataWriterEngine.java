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

import org.sdmxsource.sdmx.api.constants.BASE_DATA_FORMAT;
import org.sdmxsource.sdmx.api.constants.SDMX_SCHEMA;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.AnnotationBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataflowBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DimensionBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ProvisionAgreementBean;
import org.sdmxsource.sdmx.api.model.header.DatasetHeaderBean;
import org.sdmxsource.sdmx.util.date.DateUtil;
import org.sdmxsource.util.ObjectUtil;

import javax.xml.stream.XMLStreamException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Stream compact data writer engine.
 */
public class StreamCompactDataWriterEngine extends StreamDataWriterEngineBase {
    private Map<String, String> componentVals = new HashMap<>();
    private String primaryMeasureConcept;
    private String timeConcept;

    /**
     * Instantiates a new Stream compact data writer engine.
     *
     * @param schemaVersion the schema version
     * @param out           the out
     */
    public StreamCompactDataWriterEngine(SDMX_SCHEMA schemaVersion, OutputStream out) {
        super(schemaVersion, BASE_DATA_FORMAT.COMPACT, out);
    }

    @Override
    public void startDataset(ProvisionAgreementBean provision, DataflowBean dataflow, DataStructureBean dsd, DatasetHeaderBean header, AnnotationBean... annotations) {
        super.startDataset(provision, dataflow, dsd, header);
        this.primaryMeasureConcept = getComponentId(dsd.getPrimaryMeasure());
        this.timeConcept = getComponentId(dsd.getTimeDimension());
        if (isTwoPointOne()) {
            writeAnnotations(writer, annotations);
        } else {
            if (this.isCrossSectional) {
                throw new SdmxSemmanticException("A 2.0 Generic Dataset must contain Time at the observation level.");
            }
            datasetAnnotations = annotations;
        }
    }

    @Override
    protected void closeDataset() throws XMLStreamException {
        if (currentPosition != null) {
            try {
                switch (currentPosition) {
                    case DATASET:
                    case DATASET_ATTRIBUTE:
                        break;
                    case OBSERVATION:
                    case OBSERVATION_ATTRIBUTE:
                        writeEndObs();
                        if (!isFlat) {
                            writeEndSeries();
                        }
                        break;
                    case SERIES_KEY_ATTRIBUTE:
                    case SERIES_KEY:
                        if (!isFlat) {
                            writeEndSeries();
                        }
                        break;
                    case GROUP:
                    case GROUP_KEY_ATTRIBUTE:
                    case GROUP_KEY:
                        writeEndGroup();
                        break;
                    default:
                        break;
                }

            } catch (XMLStreamException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }

            writeEndDataset();
        }

        writer.flush();
    }


    @Override
    public void startGroup(String groupId, AnnotationBean... annotations) {
        super.startGroup(groupId);
        try {

            closeSeries();

            if (isTwoPointOne()) {
                writer.writeStartElement("Group");      //WRITE THE START GROUP
                writer.writeAttribute(XSI_NS, "type", groupId);
                writeAnnotations(writer, annotations);
            } else {
                groupAnnotations = annotations;
                writer.writeStartElement(COMPACT_NS.namespacePrefix, groupId, COMPACT_NS.namespaceURL);      //WRITE
                // THE START GROUP
            }
            currentPosition = POSITION.GROUP;
        } catch (XMLStreamException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeGroupKeyValue(String conceptId, String conceptValue) {
        conceptId = getComponentId(conceptId);
        super.writeGroupKeyValue(conceptId, conceptValue);
        try {
            if (currentPosition != POSITION.GROUP) {
                throw new IllegalArgumentException("startGroup must be called before calling writeGroupKeyValue");
            }
            writer.writeAttribute(conceptId, conceptValue);
        } catch (XMLStreamException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    @Override
    public void startSeries(AnnotationBean... annotations) {
        super.startSeries();
        if (isFlat) {
            componentVals = new HashMap<>();
            try {
                if (currentPosition != null) {
                    //WE HAVE NOT YET WRITTEN ANYTHING, CHECK WHERE WE ARE AND CLOSE OFF NODES
                    switch (currentPosition) {
                        case OBSERVATION:
                        case OBSERVATION_ATTRIBUTE:
                            writeEndObs();
                            break;
                        case SERIES_KEY_ATTRIBUTE:
                        case SERIES_KEY:
                            //DO NOTHING
                            break;
                        case GROUP:
                        case GROUP_KEY:
                        case GROUP_KEY_ATTRIBUTE:
                            writeEndGroup();
                            break;
                        default:
                            break;
                    }
                }
            } catch (XMLStreamException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            currentPosition = POSITION.SERIES_KEY;
        } else {
            try {
                closeSeries();
                seriesAnnotations = annotations;
                if (isTwoPointOne()) {
                    if (isFlat) {
                        writer.writeStartElement("Obs");  //WRITE THE START SERIES (WHICH IN THIS CASE IS AN OBS AS IT
                        // IS A FLAT DATASET)
                    } else {
                        writer.writeStartElement("Series");  //WRITE THE START SERIES
                    }
                } else {
                    startElement(writer, COMPACT_NS, "Series");  //WRITE THE START SERIES
                }
                currentPosition = POSITION.SERIES_KEY;
            } catch (XMLStreamException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    private void closeSeries() throws XMLStreamException {
        if (currentPosition != null) {
            //WE HAVE NOT YET WRITTEN ANYTHING, CHECK WHERE WE ARE AND CLOSE OFF NODES
            switch (currentPosition) {
                case OBSERVATION:
                case OBSERVATION_ATTRIBUTE:
                    writeEndObs();
                    writeEndSeries();
                    break;
                case SERIES_KEY_ATTRIBUTE:
                case SERIES_KEY:
                    writeEndSeries();
                    break;
                case GROUP:
                case GROUP_KEY:
                case GROUP_KEY_ATTRIBUTE:
                    writeEndGroup();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void writeEndSeries() throws XMLStreamException {
        //IF THE VERSION IS 2.1 AND THE CURRENT POSITION IS THE SERIES, THEN OUTPUT ANY ANNOTATIONS ATTACHED TO THE
        // SERIES
        //BEFORE CLOSING IT
        if (isTwoPointOne() && (currentPosition == POSITION.SERIES_KEY || currentPosition == POSITION.SERIES_KEY_ATTRIBUTE)) {
            writeAnnotations(writer, seriesAnnotations);
            seriesAnnotations = null;
        }
        super.writeEndSeries();
    }


    @Override
    public void writeSeriesKeyValue(String dimensionId, String dimensionValue) {
        dimensionId = getComponentId(dimensionId);
        super.writeSeriesKeyValue(dimensionId, dimensionValue);
        if (isFlat) {
            componentVals.put(dimensionId, dimensionValue);
        } else {
            try {
                if (currentPosition != POSITION.SERIES_KEY) {
                    startSeries();
                }
                writer.writeAttribute(dimensionId, dimensionValue);
            } catch (XMLStreamException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }


    @Override
    public void writeObservation(String obsConceptValue, String obsValue, AnnotationBean... annotations) {
        if (isFlat) {
            throw new IllegalArgumentException("Can not write observation, as no observation concept id was given, and" +
                    " this is writing a flat dataset. " +
                    "Plase use the method: writeObservation(String obsConceptId, String obsIdValue, String obsValue, " +
                    "AnnotationBean... annotations)");
        }
        if (isCrossSectional) {
            writeObservation(crossSectionConcept, obsConceptValue, obsValue, annotations);
        } else {
            writeObservation(timeConcept, obsConceptValue, obsValue, annotations);
        }
    }


    @Override
    public void writeObservation(String obsConceptId, String obsIdValue, String obsValue,
                                 AnnotationBean... annotations) {
        if (DimensionBean.TIME_DIMENSION_FIXED_ID.equals(obsConceptId)) {
            obsConceptId = timeConcept;
            if (schemaVersion == SDMX_SCHEMA.VERSION_TWO_POINT_ONE) {
                obsIdValue = DateUtil.formatDateForSdmxVersion21(obsIdValue);
            } else if (schemaVersion == SDMX_SCHEMA.VERSION_TWO) {
                obsIdValue = DateUtil.formatDateForSdmxVersion2(obsIdValue);
            } else if (schemaVersion == SDMX_SCHEMA.VERSION_ONE) {
                obsIdValue = DateUtil.formatDateForSdmxVersion1(obsIdValue);
            }
        } else {
            obsConceptId = getComponentId(obsConceptId);
        }
        super.writeObservation(obsConceptId, obsIdValue, obsValue);

        try {
            switch (currentPosition) {
                case OBSERVATION:
                    writeEndObs();
                    break;
                case OBSERVATION_ATTRIBUTE:
                    writeEndObs();
                    break;
                case SERIES_KEY:
                    if (isTwoPointOne()) {
                        writeAnnotations(writer, seriesAnnotations);
                        seriesAnnotations = null;
                    }
                    break;
                case SERIES_KEY_ATTRIBUTE:
                    if (isTwoPointOne()) {
                        writeAnnotations(writer, seriesAnnotations);
                        seriesAnnotations = null;
                    }
                    break;
                default:
                    if (!isTwoPointOne()) {
                        throw new IllegalArgumentException("An observation may only be written while inside a series");
                    }
            }
            obsAnnotations = annotations;  //STORE THE ANNOTATIONS

            currentPosition = POSITION.OBSERVATION;
            if (isTwoPointOne()) {
                writer.writeStartElement("Obs");  //WRITE THE OBS NODE
            } else {
                startElement(writer, COMPACT_NS, "Obs");  //WRITE THE OBS NODE
            }

            if (isFlat) {
                for (var entry : componentVals.entrySet()) {
                    writer.writeAttribute(entry.getKey(), entry.getValue());
                }
            }
            if (isCrossSectionalMeasure) {
                writer.writeAttribute("xsi", XSI_NS, "type", COMPACT_NS.namespacePrefix + ":" + obsIdValue);
            } else {
                writer.writeAttribute(obsConceptId, obsIdValue);
            }

            if (ObjectUtil.validString(obsValue)) {
                writer.writeAttribute(primaryMeasureConcept, obsValue);
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void writeEndObs() throws XMLStreamException {
        if (isTwoPointOne()) {
            writeAnnotations(writer, obsAnnotations);
            obsAnnotations = null;
        }
        super.writeEndObs();
    }

    @Override
    public void writeAttributeValue(String attributeId, String attributeValue) {
        attributeId = getComponentId(attributeId);
        super.writeAttributeValue(attributeId, attributeValue);
        if (attributeValue == null) {
            attributeValue = "";
        }
        try {
            switch (currentPosition) {
                case DATASET:
                case DATASET_ATTRIBUTE:
                    currentPosition = POSITION.DATASET_ATTRIBUTE;
                    writer.writeAttribute(attributeId, attributeValue);
                    break;
                case OBSERVATION:
                case OBSERVATION_ATTRIBUTE:
                    currentPosition = POSITION.OBSERVATION_ATTRIBUTE;
                    writer.writeAttribute(attributeId, attributeValue);
                    break;
                case SERIES_KEY:
                case SERIES_KEY_ATTRIBUTE:
                    currentPosition = POSITION.SERIES_KEY_ATTRIBUTE;
                    if (isFlat) {
                        componentVals.put(attributeId, attributeValue);
                    } else {
                        writer.writeAttribute(attributeId, attributeValue);
                    }
                    break;
                case GROUP:
                case GROUP_KEY_ATTRIBUTE:
                case GROUP_KEY:
                    currentPosition = POSITION.GROUP_KEY_ATTRIBUTE;
                    writer.writeAttribute(attributeId, attributeValue);
                    break;
                default:
                    break;
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
