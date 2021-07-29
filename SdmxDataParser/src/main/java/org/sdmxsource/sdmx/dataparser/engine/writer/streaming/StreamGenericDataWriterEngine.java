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
import org.sdmxsource.sdmx.util.beans.DataStructureUtil;
import org.sdmxsource.sdmx.util.date.DateUtil;
import org.sdmxsource.util.ObjectUtil;

import javax.xml.stream.XMLStreamException;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Stream generic data writer engine.
 */
public class StreamGenericDataWriterEngine extends StreamDataWriterEngineBase {
    private Map<String, String> componentVals = new LinkedHashMap<>();
    private String conceptAttribute;
    private List<String> seriesKey;

    /**
     * Instantiates a new Stream generic data writer engine.
     *
     * @param schemaVersion the schema version
     * @param out           the out
     */
    public StreamGenericDataWriterEngine(SDMX_SCHEMA schemaVersion, OutputStream out) {
        super(schemaVersion, BASE_DATA_FORMAT.GENERIC, out);
        setConceptAttribute();
    }

    /**
     * In version 1.0 and 2.0 the concept attribute is named 'concept', whereas in 2.1 it is named 'id'
     */
    private void setConceptAttribute() {
        if (isTwoPointOne()) {
            conceptAttribute = "id";
        } else {
            conceptAttribute = "concept";
        }
    }

    @Override
    public void startDataset(ProvisionAgreementBean provision, DataflowBean dataflow, DataStructureBean dsd, DatasetHeaderBean header, AnnotationBean... annotations) {
        super.startDataset(provision, dataflow, dsd, header, annotations);
        seriesKey = DataStructureUtil.getSeriesKeyConcepts(dsd);
        if (isFlat && dsd.getTimeDimension() != null) {
            seriesKey.add(dsd.getTimeDimension().getId());
        }
        if (!isTwoPointOne()) {
            if (this.isCrossSectional) {
                throw new SdmxSemmanticException("A 2.0 Generic Dataset must contain Time at the observation level.");
            }
            try {
                startElement(writer, GENERIC_NS, "KeyFamilyRef");
                writer.writeCharacters(dsd.getId());
                writer.writeEndElement();
                datasetAnnotations = annotations;
            } catch (XMLStreamException e) {
                throw new RuntimeException(e);
            }
        } else {
            writeAnnotations(writer, annotations);
        }
    }

    @Override
    public void startSeries(AnnotationBean... annotations) {
        super.startSeries();
        if (isFlat) {
            componentVals = new LinkedHashMap<>();

            try {
                if (currentPosition != null) {
                    switch (currentPosition) {
                        case OBSERVATION:
                            writeEndObs();
                            break;
                        case OBSERVATION_ATTRIBUTE:
                            writer.writeEndElement(); //END ATTRIBUTES
                            writeEndObs();
                            break;
                        case SERIES_KEY_ATTRIBUTE:
                        case SERIES_KEY:
                            //DO NOTHING
                            break;
                        case GROUP:
                            writeEndGroup();
                            break;
                        case GROUP_KEY_ATTRIBUTE:
                            writer.writeEndElement(); //END ATTRIBUTES
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
                if (currentPosition != null) {
                    //WE HAVE NOT YET WRITTEN ANYTHING, CHECK WHERE WE ARE AND CLOSE OFF NODES
                    switch (currentPosition) {
                        case DATASET_ATTRIBUTE:
                            writer.writeEndElement();
                            break;
                        case OBSERVATION:
                            writeEndObs();
                            writeEndSeries();
                            break;
                        case OBSERVATION_ATTRIBUTE:
                            writer.writeEndElement(); //END ATTRIBUTES
                            writeEndObs();
                            writeEndSeries();
                            break;
                        case SERIES_KEY_ATTRIBUTE:
                        case SERIES_KEY:
                            writer.writeEndElement(); //END SERIES KEY
                            writeEndSeries();
                            break;
                        case GROUP:
                            writeEndGroup();
                            break;
                        case GROUP_KEY:
                        case GROUP_KEY_ATTRIBUTE:
                            writer.writeEndElement(); //END ATTRIBUTES
                            writeEndGroup();
                            break;
                        default:
                            break;
                    }
                }
                startElement(writer, GENERIC_NS, "Series");  //WRITE THE START SERIES
                if (isTwoPointOne()) {
                    writeAnnotations(writer, annotations);
                } else {
                    seriesAnnotations = annotations;
                }
                startElement(writer, GENERIC_NS, "SeriesKey");  //WRITE THE START SERIES KEY
                currentPosition = POSITION.SERIES_KEY;
            } catch (XMLStreamException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void writeAttributeValue(String conceptId, String conceptValue) {
        conceptId = getComponentId(conceptId);
        if (conceptValue == null) {
            conceptValue = "";
        }
        try {
            switch (currentPosition) {
                case DATASET:
                    currentPosition = POSITION.DATASET_ATTRIBUTE;
                    startElement(writer, GENERIC_NS, "Attributes");
                    break;
                case DATASET_ATTRIBUTE:
                    break;
                case OBSERVATION:
                    currentPosition = POSITION.OBSERVATION_ATTRIBUTE;
                    startElement(writer, GENERIC_NS, "Attributes");
                    break;
                case SERIES_KEY:
                    if (isFlat) {
                        componentVals.put(conceptId, conceptValue);
                        return;
                    }
                    currentPosition = POSITION.SERIES_KEY_ATTRIBUTE;
                    writer.writeEndElement(); //END SERIES KEY
                    startElement(writer, GENERIC_NS, "Attributes");
                    break;
                case GROUP:
                    currentPosition = POSITION.GROUP_KEY_ATTRIBUTE;
                    startElement(writer, GENERIC_NS, "Attributes");
                    break;
                case GROUP_KEY:
                    writer.writeEndElement(); //END Group Key
                    currentPosition = POSITION.GROUP_KEY_ATTRIBUTE;
                    startElement(writer, GENERIC_NS, "Attributes");
                    break;
                case GROUP_KEY_ATTRIBUTE:
                    break;
                default:
                    break;
            }

            startElement(writer, GENERIC_NS, "Value");  //WRITE THE VALUE NODE
            writer.writeAttribute(conceptAttribute, conceptId);
            writer.writeAttribute("value", conceptValue);
            writer.writeEndElement(); //END VALUE
        } catch (XMLStreamException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeGroupKeyValue(String conceptId, String conceptValue) {
        try {
            conceptId = getComponentId(conceptId);
            if (currentPosition != POSITION.GROUP && currentPosition != POSITION.GROUP_KEY) {
                throw new IllegalArgumentException("startGroup must be called before valling writeGroupKeyValue");
            }
            currentPosition = POSITION.GROUP_KEY;
            startElement(writer, GENERIC_NS, "Value");  //WRITE THE VALUE NODE
            writer.writeAttribute(conceptAttribute, conceptId);
            writer.writeAttribute("value", conceptValue);
            writer.writeEndElement(); //END VALUE
        } catch (XMLStreamException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeSeriesKeyValue(String dimensionId, String dimensionValue) {
        dimensionId = getComponentId(dimensionId);
        if (isFlat) {
            componentVals.put(dimensionId, dimensionValue);
        } else {
            try {
                if (currentPosition != POSITION.SERIES_KEY) {
                    startSeries();
                }
                startElement(writer, GENERIC_NS, "Value");  //WRITE THE START SERIES
                writer.writeAttribute(conceptAttribute, dimensionId);
                writer.writeAttribute("value", dimensionValue);
                writer.writeEndElement(); //END VALUE

            } catch (XMLStreamException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected void closeDataset() throws XMLStreamException {
        if (currentPosition != null) {
            switch (currentPosition) {
                case DATASET_ATTRIBUTE:
                    writer.writeEndElement();
                    break;
                case OBSERVATION:
                    writeEndObs();
                    if (!isFlat) {
                        writeEndSeries();
                    }
                    break;
                case OBSERVATION_ATTRIBUTE:
                    writer.writeEndElement(); //END ATTRIBUTES
                    writeEndObs();
                    if (!isFlat) {
                        writeEndSeries();
                    }
                    break;
                case SERIES_KEY_ATTRIBUTE:
                case SERIES_KEY:
                    writer.writeEndElement(); //END SERIES KEY
                    if (!isFlat) {
                        writeEndSeries();
                    }
                    break;
                case GROUP:
                    writeEndGroup();
                    break;
                case GROUP_KEY:
                case GROUP_KEY_ATTRIBUTE:
                    writer.writeEndElement(); //END ATTRIBUTES
                    writeEndGroup();
                    break;
                case DATASET:
                    //DO NOTHING
                    break;
                default:
                    break;
            }
            writeEndDataset();
        }

        writer.flush();
    }

    @Override
    public void writeObservation(String obsConceptValue, String obsValue, AnnotationBean... annotations) {
        if (isFlat) {
            throw new IllegalArgumentException("Can not write observation, as no observation concept id was given, and this is writing a flat dataset. " +
                    "Plase use the method: writeObservation(String obsConceptId, String obsIdValue, String obsValue, AnnotationBean... annotations)");
        }
        if (isCrossSectional) {
            writeObservation(crossSectionConcept, obsConceptValue, obsValue, annotations);
        } else {
            writeObservation(DimensionBean.TIME_DIMENSION_FIXED_ID, obsConceptValue, obsValue, annotations);
        }
    }

    @Override
    public void writeObservation(String obsConceptId, String obsConceptValue, String obsValue, AnnotationBean... annotations) {
        if (DimensionBean.TIME_DIMENSION_FIXED_ID.equals(obsConceptId)) {
            if (schemaVersion == SDMX_SCHEMA.VERSION_TWO_POINT_ONE) {
                obsConceptValue = DateUtil.formatDateForSdmxVersion21(obsConceptValue);
            } else if (schemaVersion == SDMX_SCHEMA.VERSION_TWO) {
                obsConceptValue = DateUtil.formatDateForSdmxVersion2(obsConceptValue);
            } else if (schemaVersion == SDMX_SCHEMA.VERSION_ONE) {
                obsConceptValue = DateUtil.formatDateForSdmxVersion1(obsConceptValue);
            }
        }
        obsConceptId = getComponentId(obsConceptId);

        super.writeObservation(obsConceptId, obsConceptValue, obsValue, annotations);

        try {
            switch (currentPosition) {
                case OBSERVATION:
                    writeEndObs();
                    break;
                case OBSERVATION_ATTRIBUTE:
                    writer.writeEndElement(); //END ATTRIBUTES
                    writeEndObs();
                    break;
                case SERIES_KEY_ATTRIBUTE:
                    writer.writeEndElement(); //END ATTRIBUTES
                    break;
                case SERIES_KEY:
                    if (!isFlat) {
                        writer.writeEndElement(); //END SERIES KEY
                    }
                    break;
                case GROUP:
                    if (isTwoPointOne()) {
                        writer.writeEndElement(); //END GROUP
                    } else {
                        throw new SdmxSemmanticException("Attempting to write observation to group , an observation must belong to a series");
                    }
                    break;
                case GROUP_KEY:
                    if (isTwoPointOne()) {
                        writer.writeEndElement(); //END GROUP_KEY
                        writer.writeEndElement(); //END GROUP
                    } else {
                        throw new SdmxSemmanticException("Attempting to write observation to group , an observation must belong to a series");
                    }
                    break;
                case GROUP_KEY_ATTRIBUTE:
                    if (isTwoPointOne()) {
                        writer.writeEndElement(); //END GROUP_KEY_ATTRIBUTE
                        writer.writeEndElement(); //END GROUP
                    } else {
                        throw new SdmxSemmanticException("Attempting to write observation to group , an observation must belong to a series");
                    }
                    break;
                default:
                    if (!isTwoPointOne()) {
                        throw new IllegalArgumentException("An observation may only be written while inside a series");
                    }
            }
            currentPosition = POSITION.OBSERVATION;
            startElement(writer, GENERIC_NS, "Obs");  //WRITE THE OBS NODE

            if (isFlat) {
                componentVals.put(obsConceptId, obsConceptValue);
                writeAnnotations(writer, annotations);
                startElement(writer, GENERIC_NS, "ObsKey");
                for (String componentId : seriesKey) {
                    startElement(writer, GENERIC_NS, "Value");
                    writer.writeAttribute("id", componentId);
                    writer.writeAttribute("value", componentVals.get(componentId));
                    writer.writeEndElement(); //END Value
                }
                writer.writeEndElement(); //END ObsKey

            } else if (isTwoPointOne()) {
                writeAnnotations(writer, annotations);

                startElement(writer, GENERIC_NS, "ObsDimension");
                if (isCrossSectional) {
                    writer.writeAttribute("id", obsConceptId);
                }
                writer.writeAttribute("value", obsConceptValue);
                writer.writeEndElement(); //END ObsDimension
            } else {
                obsAnnotations = annotations;
                startElement(writer, GENERIC_NS, "Time");
                writer.writeCharacters(obsConceptValue);
                writer.writeEndElement(); //END Time
            }
            startElement(writer, GENERIC_NS, "ObsValue");
            if (ObjectUtil.validString(obsValue)) {
                writer.writeAttribute("value", obsValue);
            }
            writer.writeEndElement(); //END ObsValue
            if (isFlat) {
                // Write the Series Attributes as Observation Level Attributes
                // NOTE - we do not want to write the Series Keys here, so they need to be removed
                Map<String, String> componentValsClone = new LinkedHashMap<>(componentVals);
                seriesKey.forEach(componentValsClone.keySet()::remove);
                for (var entry : componentValsClone.entrySet()) {
                    writeAttributeValue(entry.getKey(), entry.getValue());
                }
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void startGroup(String groupId, AnnotationBean... annotations) {
        try {
            if (currentPosition != null) {
                //WE HAVE NOT YET WRITTEN ANYTHING, CHECK WHERE WE ARE AND CLOSE OFF NODES
                switch (currentPosition) {
                    case DATASET_ATTRIBUTE:
                        writer.writeEndElement(); //END ATTRIBUTES
                        break;
                    case OBSERVATION:
                        writeEndObs();
                        writeEndSeries();
                        break;
                    case OBSERVATION_ATTRIBUTE:
                        writer.writeEndElement(); //END ATTRIBUTES
                        writeEndObs();
                        writeEndSeries();
                        break;
                    case SERIES_KEY_ATTRIBUTE:
                    case SERIES_KEY:
                        writer.writeEndElement(); //END SERIES KEY
                        writeEndSeries();
                        break;
                    case GROUP:
                        writeEndGroup();
                        break;
                    case GROUP_KEY: {
                        writer.writeEndElement(); //END GROUP KEY
                        writeEndGroup();
                        break;
                    }
                    case GROUP_KEY_ATTRIBUTE:
                        writer.writeEndElement(); //END ATTRIBUTES
                        writeEndGroup();
                        break;
                    default:
                        break;
                }
            }
            startElement(writer, GENERIC_NS, "Group");      //WRITE THE START GROUP
            writer.writeAttribute("type", groupId);                //THE GROUP TYPE IS THE GROUP ID
            if (isTwoPointOne()) {
                writeAnnotations(writer, annotations);
            } else {
                groupAnnotations = annotations;
            }
            startElement(writer, GENERIC_NS, "GroupKey");   //WRITE THE START GROUP KEY
            currentPosition = POSITION.GROUP;
        } catch (XMLStreamException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
