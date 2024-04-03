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

import org.sdmxsource.sdmx.api.constants.*;
import org.sdmxsource.sdmx.api.engine.DataWriterEngine;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.*;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataflowBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DimensionBean;
import org.sdmxsource.sdmx.api.model.beans.reference.MaintainableRefBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ProvisionAgreementBean;
import org.sdmxsource.sdmx.api.model.header.DatasetHeaderBean;
import org.sdmxsource.sdmx.api.model.header.HeaderBean;
import org.sdmxsource.sdmx.api.model.header.PartyBean;
import org.sdmxsource.sdmx.util.beans.ConceptRefUtil;
import org.sdmxsource.sdmx.util.date.DateUtil;
import org.sdmxsource.util.ObjectUtil;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.OutputStream;
import java.util.*;

/**
 * This base class intended to support sequential write into output stream using single writer.
 * Multiple datasets supported only if with the identical data flow and data structure
 */
public abstract class StreamDataWriterEngineBase implements DataWriterEngine {
    /**
     * The constant XML_NS.
     */
//NAMESPACES
    protected static final String XML_NS = "http://www.w3.org/XML/1998/namespace";
    /**
     * The constant XSI_NS.
     */
    protected static final String XSI_NS = "http://www.w3.org/2001/XMLSchema-instance";
    /**
     * The Dataset annotations.
     */
    protected AnnotationBean[] datasetAnnotations;  //For version 1.0 or 2.0 writer, store the annotation until the end of the message
    /**
     * The Series annotations.
     */
    protected AnnotationBean[] seriesAnnotations;  //For version 1.0 or 2.0 writer, store the annotation until the end of the message
    /**
     * The Group annotations.
     */
    protected AnnotationBean[] groupAnnotations;  //For version 1.0 or 2.0 writer, store the annotation until the end of the message
    /**
     * The Obs annotations.
     */
    protected AnnotationBean[] obsAnnotations;  //For version 1.0 or 2.0 writer, store the annotation until the end of the message
    /**
     * The Is closed.
     */
    protected boolean isClosed = false;
    /**
     * The Prefix generic.
     */
    protected String PREFIX_GENERIC = "generic";
    /**
     * The Prefix message.
     */
    protected String PREFIX_MESSAGE = "message";
    /**
     * The Prefix common.
     */
    protected String PREFIX_COMMON = "common";
    /**
     * The Prefix cross.
     */
    protected String PREFIX_CROSS = "cross";
    /**
     * The Prefix compact.
     */
    protected String PREFIX_COMPACT = "ns";
    /**
     * The Generic ns.
     */
    protected Namespace GENERIC_NS;
    /**
     * The Cross ns.
     */
    protected Namespace CROSS_NS;
    /**
     * The Compact ns.
     */
    protected Namespace COMPACT_NS;
    /**
     * The Header written.
     */
    protected boolean headerWritten = false;
    /**
     * The Schema version.
     */
    protected SDMX_SCHEMA schemaVersion;
    /**
     * The Cross section concept.
     */
    protected String crossSectionConcept;
    /**
     * The Is cross sectional.
     */
    protected boolean isCrossSectional;
    /**
     * The Is flat.
     */
    protected boolean isFlat;
    /**
     * The Writer.
     */
//WRITER
    protected XMLStreamWriter writer = null;
    /**
     * The Concept ids.
     */
//VARIABLES & STATE
    protected List<String> conceptIds;
    /**
     * The Current group concept ids.
     */
    protected List<String> currentGroupConceptIds;
    /**
     * The Current position.
     */
    POSITION currentPosition; //WHERE ARE WE IN THE OUTPUT SDMX
    /**
     * If true, then the dimension at the observation is the measure dimension
     */
    boolean isCrossSectionalMeasure = false;
    private Namespace MESSAGE_NS;
    private Namespace COMMON_NS;
    private Namespace FOOTER_NS = new Namespace("http://www.sdmx.org/resources/sdmxml/schemas/v2_1/message/footer", "footer");
    private BASE_DATA_FORMAT dataFormat;
    //Contains the user's output stream - this is where the data should eventually be written to
    private OutputStream outputStream;
    //Contains a mapping from component id - to concept id
    private Map<String, String> componentIdMapping = new HashMap<>();
    private DataSetInfo dataSetInfo;
    private HeaderBean header;
    private List<Namespace> knownNamespaces = new ArrayList<>();

    /**
     * Instantiates a new Stream data writer engine base.
     *
     * @param schemaVersion the schema version
     * @param dataFormat    the data format
     * @param out           the out
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////CONSTRUCTOR							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    protected StreamDataWriterEngineBase(SDMX_SCHEMA schemaVersion, BASE_DATA_FORMAT dataFormat, OutputStream out) {
        this.outputStream = out; //Store the user's output stream

        this.schemaVersion = schemaVersion;
        this.dataFormat = dataFormat;

        createNamespaces();
    }

    /**
     * Writes annotations to the writer, if the argument is null, then thie method will return
     *
     * @param annotationWriter to write the annotations to
     * @param annotations      the annotations
     */
    void writeAnnotations(XMLStreamWriter annotationWriter, AnnotationBean... annotations) {
        try {
            if (annotations != null && annotations.length > 0) {
                if (isTwoPointOne()) {
                    startElement(annotationWriter, COMMON_NS, "Annotations");
                } else if (dataFormat == BASE_DATA_FORMAT.GENERIC) {
                    startElement(annotationWriter, GENERIC_NS, "Annotations");
                } else {
                    startElement(annotationWriter, COMPACT_NS, "Annotations");
                }
                for (AnnotationBean currentAnnotation : annotations) {
                    startElement(annotationWriter, COMMON_NS, "Annotation");
                    if (isTwoPointOne() && ObjectUtil.validString(currentAnnotation.getId())) {
                        annotationWriter.writeAttribute("id", currentAnnotation.getId());
                    }
                    if (ObjectUtil.validString(currentAnnotation.getTitle())) {
                        startElement(annotationWriter, COMMON_NS, "AnnotationTitle");
                        annotationWriter.writeCharacters(currentAnnotation.getTitle());
                        annotationWriter.writeEndElement(); //END AnnotationTitle
                    }
                    if (ObjectUtil.validString(currentAnnotation.getType())) {
                        startElement(annotationWriter, COMMON_NS, "AnnotationType");
                        annotationWriter.writeCharacters(currentAnnotation.getType());
                        annotationWriter.writeEndElement(); //END AnnotationType
                    }
                    if (currentAnnotation.getUri() != null) {
                        startElement(annotationWriter, COMMON_NS, "AnnotationURL");
                        annotationWriter.writeCharacters(currentAnnotation.getUri().toString());
                        annotationWriter.writeEndElement(); //END AnnotationURL
                    }
                    if (ObjectUtil.validCollection(currentAnnotation.getText())) {
                        for (TextTypeWrapper currentText : currentAnnotation.getText()) {
                            startElement(annotationWriter, COMMON_NS, "AnnotationText");
                            annotationWriter.writeAttribute("xml", XML_NS, "lang", currentText.getLocale());
                            annotationWriter.writeCharacters(currentText.getValue());
                            annotationWriter.writeEndElement(); //END AnnotationText
                        }
                    }
                    annotationWriter.writeEndElement(); //END ANNOTATION
                }
                annotationWriter.writeEndElement(); //END ANNOTATIONS
            }
        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////INTERNAL METHODS					 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private void createNamespaces() {
        COMPACT_NS = new Namespace("http://www.sdmxfusion.com/ns/compact", PREFIX_COMPACT);

        if (schemaVersion == SDMX_SCHEMA.VERSION_TWO_POINT_ONE) {
            GENERIC_NS = new Namespace(SdmxConstants.GENERIC_NS_2_1, PREFIX_GENERIC);
            MESSAGE_NS = new Namespace(SdmxConstants.MESSAGE_NS_2_1, PREFIX_MESSAGE);
            COMMON_NS = new Namespace(SdmxConstants.COMMON_NS_2_1, PREFIX_COMMON);
        }
        if (schemaVersion == SDMX_SCHEMA.VERSION_TWO) {
            GENERIC_NS = new Namespace(SdmxConstants.GENERIC_NS_2_0, PREFIX_GENERIC);
            MESSAGE_NS = new Namespace(SdmxConstants.MESSAGE_NS_2_0, PREFIX_MESSAGE);
            COMMON_NS = new Namespace(SdmxConstants.COMMON_NS_2_0, PREFIX_COMMON);
            if (dataFormat == BASE_DATA_FORMAT.CROSS_SECTIONAL) {
                CROSS_NS = new Namespace(SdmxConstants.CROSS_NS_2_0, PREFIX_CROSS);
            }
        }
        if (schemaVersion == SDMX_SCHEMA.VERSION_ONE) {
            GENERIC_NS = new Namespace(SdmxConstants.GENERIC_NS_1_0, PREFIX_GENERIC);
            MESSAGE_NS = new Namespace(SdmxConstants.MESSAGE_NS_1_0, PREFIX_MESSAGE);
            COMMON_NS = new Namespace(SdmxConstants.COMMON_NS_1_0, PREFIX_COMMON);
            if (dataFormat == BASE_DATA_FORMAT.CROSS_SECTIONAL) {
                CROSS_NS = new Namespace(SdmxConstants.CROSS_NS_1_0, PREFIX_CROSS);
            }
        }
    }

    private void writeNameSpace(Namespace ns) throws XMLStreamException {
        if (ns != null) {
            writer.writeNamespace(ns.namespacePrefix, ns.namespaceURL);
        }
    }

    private XMLStreamWriter createWriter(OutputStream writerOut) {
        try {
            XMLOutputFactory xmlOutputfactory = XMLOutputFactory.newInstance();
            XMLStreamWriter xmlStreamWriter = xmlOutputfactory.createXMLStreamWriter(writerOut, "UTF-8");
            xmlStreamWriter.writeStartDocument();
            xmlStreamWriter.flush();
            return xmlStreamWriter;
        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Is two point one boolean.
     *
     * @return the boolean
     */
    protected boolean isTwoPointOne() {
        return schemaVersion == SDMX_SCHEMA.VERSION_TWO_POINT_ONE;
    }

    /**
     * Start element.
     *
     * @param streamWriter the stream writer
     * @param ns           the ns
     * @param elementName  the element name
     * @throws XMLStreamException the xml stream exception
     */
    protected void startElement(XMLStreamWriter streamWriter, Namespace ns, String elementName) throws XMLStreamException {
        streamWriter.writeStartElement(ns.namespacePrefix, elementName, ns.namespaceURL);
    }

    private String getStructureRef(MaintainableBean maint) {
        return maint.getAgencyId() + "_" + maint.getId() + "_" + maint.getVersion().replaceAll("\\.", "_");
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////INTERFACE METHOD IMPLEMENTATIONS	 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void startDataset(ProvisionAgreementBean provision, DataflowBean dataflowBean, DataStructureBean dsd, DatasetHeaderBean header, AnnotationBean... annotationMutableBean) {
        checkClosed();
        if (dsd == null) {
            throw new IllegalArgumentException("Can not start dataset, no DataStructure provided");
        }
        //TODO Nothing is done with the dataflow - it can be used to output on the header of the message

        try {
            if (this.headerWritten) {
                if (!dataSetInfo.dataflow.deepEquals(dataflowBean, true)
                        || !dataSetInfo.dsd.deepEquals(dsd, true)) {
                    throw new IllegalArgumentException("Datasets with different structure not supported.");
                }
            } else {
                initEngine(provision, dataflowBean, dsd, header);
                writeMessageTag();
                writeHeader();
            }

            closeDataset();

            dataSetInfo = new DataSetInfo(dataSetInfo.provision, dataSetInfo.dataflow, dataSetInfo.dsd, header);

            writeDatasetHeader(dataSetInfo);
            currentPosition = POSITION.DATASET;

        } catch (Throwable e) {
            dataSetInfo = null;
            isClosed = true; //Close the writer engine
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeHeader(HeaderBean header) {
        checkClosed();
        this.header = header;
    }

    /**
     * Returns the id that should be output for a component Id.  For a 1.0 and 2.0 data message this is the Id of the Concept.
     * For a 2.1 data message, this is the id of the component (and will just be returned unchanged).
     *
     * @param componentId the component id
     * @return component id
     */
    protected String getComponentId(String componentId) {
        if (isTwoPointOne()) {
            return componentId;
        }
        if (componentIdMapping.containsKey(componentId)) {
            return componentIdMapping.get(componentId);
        }
        return componentId;
    }

    @Override
    public void writeObservation(Date obsTime, String obsValue, TIME_FORMAT sdmxTimeFormat, AnnotationBean... annotations) {
        checkClosed();
        writeObservation(DimensionBean.TIME_DIMENSION_FIXED_ID, DateUtil.formatDate(obsTime, sdmxTimeFormat), obsValue, annotations);
    }

    /**
     * Gets component id.
     *
     * @param component the component
     * @return the component id
     */
    protected String getComponentId(ComponentBean component) {
        if (component == null) {
            return null;
        }
        if (isTwoPointOne()) {
            return component.getId();
        }
        return ConceptRefUtil.getConceptId(component.getConceptRef());
    }

    @Override
    public void startGroup(String groupId, AnnotationBean... annotationBean) {
        checkClosed();
        if (currentPosition == null) {
            throw new IllegalArgumentException("Can not startGroup, no call has been made to startDataset");
        }

    }

    @Override
    public void writeGroupKeyValue(String id, String value) {
        checkClosed();
        if (currentPosition == null) {
            throw new IllegalArgumentException("Can not writeGroupKeyValue, no call has been made to startDataset");
        }
        if (currentPosition != POSITION.GROUP && currentPosition != POSITION.GROUP_KEY && currentPosition != POSITION.GROUP_KEY_ATTRIBUTE) {
            throw new IllegalArgumentException("Can not writeGroupKeyValue, not in a group");
        }
    }

    @Override
    public void startSeries(AnnotationBean... annotationMutableBean) {
        checkClosed();
        if (currentPosition == null) {
            throw new IllegalArgumentException("Can not startSeries, no call has been made to startDataset");
        }
    }

    @Override
    public void writeSeriesKeyValue(String id, String value) {
        checkClosed();
        if (currentPosition == null) {
            throw new IllegalArgumentException("Can not writeSeriesKeyValue, no call has been made to startDataset");
        }
    }

    @Override
    public void writeAttributeValue(String id, String value) {
        checkClosed();
        if (currentPosition == null) {
            throw new IllegalArgumentException("Can not writeAttributeValue, no call has been made to startDataset");
        }
    }

    @Override
    public void writeObservation(String obsConceptId, String obsIdValue, String obsValue, AnnotationBean... annotationBean) {
        checkClosed();
        if (obsConceptId == null) {
            if (isCrossSectional) {
                throw new SdmxSemmanticException("Error while writing observation, a value for cross sectional concept '" + crossSectionConcept + "' expected and not given");
            }
            throw new SdmxSemmanticException("Error while writing observation, an observation time was expected and not given");
        }

        if (currentPosition == null) {
            throw new IllegalArgumentException("Can not writeObservation, no call has been made to startDataset");
        }
    }

    /**
     * Write dataset attribute.
     *
     * @param id    the id
     * @param value the value
     */
    public void writeDatasetAttribute(String id, String value) {
        checkClosed();
        dataSetInfo.datasetAttributes.put(id, value);
    }

    /**
     * Write end obs.
     *
     * @throws XMLStreamException the xml stream exception
     */
    protected void writeEndObs() throws XMLStreamException {
        if (!isTwoPointOne()) {
            writeAnnotations(writer, obsAnnotations);
            obsAnnotations = null;
        }
        writer.writeEndElement(); //END OBS
    }

    /**
     * Write end series.
     *
     * @throws XMLStreamException the xml stream exception
     */
    protected void writeEndSeries() throws XMLStreamException {
        if (!isTwoPointOne()) {
            writeAnnotations(writer, seriesAnnotations);
            seriesAnnotations = null;
        }
        writer.writeEndElement(); //END SERIES
        currentPosition = POSITION.DATASET; //Position is back to dataset
    }

    /**
     * Write end group.
     *
     * @throws XMLStreamException the xml stream exception
     */
    protected void writeEndGroup() throws XMLStreamException {
        if (!isTwoPointOne()) {
            writeAnnotations(writer, groupAnnotations);
            groupAnnotations = null;
        }

        writer.writeEndElement(); //END GROUP
        currentPosition = POSITION.DATASET; //Position is back to dataset
    }

    /**
     * Write end dataset.
     *
     * @throws XMLStreamException the xml stream exception
     */
    protected void writeEndDataset() throws XMLStreamException {
        if (!isTwoPointOne()) {
            writeAnnotations(writer, datasetAnnotations);
            groupAnnotations = null;
        }

        writer.writeEndElement(); //END DATASET
        currentPosition = null;
    }

    /**
     * Close dataset.
     *
     * @throws XMLStreamException the xml stream exception
     */
    protected abstract void closeDataset() throws XMLStreamException;

    @Override
    public void close(FooterMessage... footer) {
        if (isClosed) {
            return;
        }

        try {

            closeDataset();

            dataSetInfo = null;
            isClosed = true; //Close the writer engine
            if (footer != null && footer.length > 0 && isTwoPointOne()) {
                startElement(writer, FOOTER_NS, "Footer");
                for (FooterMessage currentFooter : footer) {
                    startElement(writer, FOOTER_NS, "Message");
                    writer.writeAttribute("code", currentFooter.getCode());
                    if (currentFooter.getSeverity() != null) {
                        writer.writeAttribute("severity", currentFooter.getSeverity().toString());
                    }
                    for (TextTypeWrapper ttw : currentFooter.getFooterText()) {
                        startElement(writer, COMMON_NS, "Text");
                        if (ttw.getLocale() != null) {
                            writer.writeAttribute("xml", XML_NS, "lang", ttw.getLocale());
                        }
                        if (ttw.getValue() != null) {
                            writer.writeCharacters(ttw.getValue());
                        }
                        writer.writeEndElement(); //End Text
                    }
                    writer.writeEndElement(); //End Message
                }
                writer.writeEndElement(); //End footer
            }
            writer.writeEndDocument();
            writer.flush();
            writer.close();
        } catch (Throwable e) {
            System.err.println("Error trying to close resources");
            e.printStackTrace();
        }
    }

    /**
     * Check closed.
     */
    protected void checkClosed() {
        if (isClosed) {
            throw new RuntimeException("Data Write has already been closed and can not have any more information written to it");
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////CLOSE WRITER						 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    private void initEngine(ProvisionAgreementBean provision, DataflowBean dataflowBean, DataStructureBean dsd,
                            DatasetHeaderBean header) {
        componentIdMapping = new HashMap<>();
        for (ComponentBean currentComponent : dsd.getComponents()) {
            componentIdMapping.put(currentComponent.getId(), getComponentId(currentComponent));
        }

        dataSetInfo = new DataSetInfo(provision, dataflowBean, dsd, header);
        if (dataSetInfo.dataNamespace != null) {
            knownNamespaces.add(dataSetInfo.dataNamespace);
        }
        this.crossSectionConcept = dataSetInfo.getDimensionAtObservation();
        this.isFlat = crossSectionConcept.equals(DIMENSION_AT_OBSERVATION.ALL.getVal());
        this.isCrossSectional = !crossSectionConcept.equals(DimensionBean.TIME_DIMENSION_FIXED_ID);
        if (!isFlat) {
            DimensionBean dimension = dsd.getDimension(crossSectionConcept);
            if (dimension == null) {
                throw new IllegalArgumentException("Can not start dataset.  The dimension at observation has been set to '" + crossSectionConcept + "', but this dimenison does not exist in the datastructure '" + dsd.getUrn() + "'");
            }
            this.isCrossSectionalMeasure = dimension.isMeasureDimension();
        }

        writer = createWriter(outputStream);
    }

    private void writeMessageTag() throws XMLStreamException {
        //Write the start Node
        /**
         * / TODO MessageGroup support
         */
        if (isTwoPointOne()) {
            if (dataFormat == BASE_DATA_FORMAT.COMPACT) {
                startElement(writer, MESSAGE_NS, "StructureSpecificData");
                writer.writeNamespace("ss", SdmxConstants.STRUCTURE_SPECIFIC_NS_2_1);
            } else if (dataFormat == BASE_DATA_FORMAT.GENERIC) {
                startElement(writer, MESSAGE_NS, "GenericData");
            }

            writeNameSpace(FOOTER_NS);
        } else {
            startElement(writer, MESSAGE_NS, dataFormat.getRootNode());
        }

        Set<String> nameSpacePrefixWritten = new HashSet<>();
        //Write the Namespaces
        if (dataFormat == BASE_DATA_FORMAT.GENERIC) {
            writeNameSpace(GENERIC_NS);
        } else {
            if (!nameSpacePrefixWritten.contains(dataSetInfo.dataNamespace.namespacePrefix)) {
                writeNameSpace(dataSetInfo.dataNamespace);
                nameSpacePrefixWritten.add(dataSetInfo.dataNamespace.namespacePrefix);
            }
            if (!nameSpacePrefixWritten.contains(dataSetInfo.datasetNamespace.namespacePrefix)) {
                writeNameSpace(dataSetInfo.datasetNamespace);
                nameSpacePrefixWritten.add(dataSetInfo.datasetNamespace.namespacePrefix);
            }
        }
        if (!nameSpacePrefixWritten.contains(COMMON_NS.namespacePrefix)) {
            writeNameSpace(COMMON_NS);
        }
        if (!nameSpacePrefixWritten.contains(MESSAGE_NS.namespacePrefix)) {
            writeNameSpace(MESSAGE_NS);
        }
        writer.writeNamespace("xsi", XSI_NS);
        writer.writeNamespace("xml", XML_NS);

    }

    /**
     * Writes out the dataset header to the writer
     *
     * @param dataSetInfo contains base information about dataset
     * @throws XMLStreamException the xml stream exception
     */
    protected void writeDatasetHeader(DataSetInfo dataSetInfo) throws XMLStreamException {

        Namespace datasetNamespace = this.dataSetInfo.datasetNamespace;
        if (!isTwoPointOne() && dataFormat == BASE_DATA_FORMAT.GENERIC) {
            //For a MessageGroup the dataset namespace is the underlying dataformat, otherwise for 2.0 Generic data it is message
            datasetNamespace = GENERIC_NS;
        }
        startElement(writer, datasetNamespace, "DataSet");
        if (dataFormat == BASE_DATA_FORMAT.COMPACT) {
            for (String attributeName : this.dataSetInfo.datasetAttributes.keySet()) {
                writer.writeAttribute(attributeName, dataSetInfo.datasetAttributes.get(attributeName));
            }
        }

        if (isTwoPointOne()) {
            //TODO Will this namespace be written multiple times?
            //			writer.writeNamespace("structurespec", SdmxConstants.STRUCTURE_SPECIFIC_NS_2_1);
            if (dataFormat == BASE_DATA_FORMAT.COMPACT) {
                writer.writeAttribute("ss", SdmxConstants.STRUCTURE_SPECIFIC_NS_2_1, "dataScope", "DataStructure");
                writer.writeAttribute("xsi", XSI_NS, "type", COMPACT_NS.namespacePrefix + ":DataSetType");
                writer.writeAttribute("ss", SdmxConstants.STRUCTURE_SPECIFIC_NS_2_1, "structureRef", getStructureRef(dataSetInfo.getStructureForData()));
            } else {
                writer.writeAttribute("structureRef", getStructureRef(dataSetInfo.getStructureForData()));
            }
        }
        if (dataSetInfo.headerBean != null) {
            DatasetHeaderBean datasetHeader = dataSetInfo.headerBean;
            if (ObjectUtil.validString(datasetHeader.getPublicationPeriod())) {
                writer.writeAttribute("publicationPeriod", datasetHeader.getPublicationPeriod());
            }
            if (datasetHeader.getPublicationYear() > 0) {
                writer.writeAttribute("publicationYear", Integer.toString(datasetHeader.getPublicationYear()));
            }
            if (datasetHeader.getValidFrom() != null) {
                writer.writeAttribute("validFromDate", DateUtil.formatDate(datasetHeader.getValidFrom()));
            }
            if (datasetHeader.getValidTo() != null) {
                writer.writeAttribute("validToDate", DateUtil.formatDate(datasetHeader.getValidTo()));
            }
            //Write out the dataset id, action, reporting begin and end dates
            if (datasetHeader.getAction() != null) {
                writer.writeAttribute("action", datasetHeader.getAction().getAction());
            }
            if (ObjectUtil.validString(datasetHeader.getDatasetId())) {
                if (isTwoPointOne()) {
                    writer.writeAttribute("setID", datasetHeader.getDatasetId());
                } else {
                    writer.writeAttribute("datasetID", datasetHeader.getDatasetId());
                }
            }
            if (!isTwoPointOne()) {
                writer.writeAttribute("keyFamilyURI", dataSetInfo.dsd.getUrn());
            }
            if (datasetHeader.getReportingBeginDate() != null) {
                writer.writeAttribute("reportingBeginDate", DateUtil.formatDate(datasetHeader.getReportingBeginDate()));
            }
            if (datasetHeader.getReportingEndDate() != null) {
                writer.writeAttribute("reportingEndDate", DateUtil.formatDate(datasetHeader.getReportingEndDate()));
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////WRITE HEADER						 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Writes the header to the writer
     */
    private void writeHeader() {
        try {
            startElement(writer, MESSAGE_NS, "Header");  //START HEADER
            //ID
            startElement(writer, MESSAGE_NS, "ID");
            if (header != null && ObjectUtil.validString(header.getId())) {
                writer.writeCharacters(header.getId());
            } else {
                writer.writeCharacters("DS" + Long.toString(new Date().getTime()));
            }
            writer.writeEndElement();

            //Test
            startElement(writer, MESSAGE_NS, "Test");
            if (header != null) {
                writer.writeCharacters(Boolean.valueOf(header.isTest()).toString());
            } else {
                writer.writeCharacters("false");
            }
            writer.writeEndElement();

            if (!isTwoPointOne() && header != null && header.getName() != null) {
                //In 2.0 the name goes in this location
                writeTextTypes(MESSAGE_NS, "Name", header.getName());
            }

            //Prepared
            startElement(writer, MESSAGE_NS, "Prepared");
            if (header != null && header.getPrepared() != null) {
                writer.writeCharacters(DateUtil.formatDate(header.getPrepared()));
            } else {
                writer.writeCharacters(DateUtil.formatDate(new Date()));
            }
            writer.writeEndElement();

            startElement(writer, MESSAGE_NS, "Sender");
            if (header != null && header.getSender() != null) {
                writeParty(header.getSender(), isTwoPointOne()); //Only 2.1 has the concept of timeZone in the Sender
            } else {
                writer.writeAttribute("id", "MetadataTechnology");
            }
            writer.writeEndElement();

            if (header != null && header.getReceiver() != null) {
                writeReceiver(header.getReceiver());
            }
            if (isTwoPointOne() && header != null && header.getName() != null) {
                //In 2.1 the name goes in this location
                writeTextTypes(COMMON_NS, "Name", header.getName());
            }

            MaintainableBean currentStructure = dataSetInfo.getStructureForData();

            if (isTwoPointOne()) {
                //Start Structure
                startElement(writer, MESSAGE_NS, "Structure");

                writer.writeAttribute("structureID", getStructureRef(currentStructure));


                if (dataFormat == BASE_DATA_FORMAT.COMPACT) {
                    writer.writeAttribute("namespace", COMPACT_NS.namespaceURL);
                }
                if (crossSectionConcept != null) {
                    writer.writeAttribute("dimensionAtObservation", crossSectionConcept);
                } else {
                    writer.writeAttribute("dimensionAtObservation", DimensionBean.TIME_DIMENSION_FIXED_ID);
                }

                //Start Structure (in this case Structure refers to DSD)
                if (currentStructure instanceof ProvisionAgreementBean) {
                    startElement(writer, COMMON_NS, "ProvisionAgrement");
                } else if (currentStructure instanceof DataflowBean) {
                    startElement(writer, COMMON_NS, "StructureUsage");
                } else {
                    startElement(writer, COMMON_NS, "Structure");
                }


                //Start Ref
                writer.writeStartElement("Ref");


                writer.writeAttribute("agencyID", currentStructure.getAgencyId());
                writer.writeAttribute("id", currentStructure.getId());
                writer.writeAttribute("version", currentStructure.getVersion());
                //End Ref
                writer.writeEndElement();

                //End Structure
                writer.writeEndElement();

                //End Structure
                writer.writeEndElement();
            } else {
                DataStructureBean dsd = dataSetInfo.dsd;
                StructureReferenceBean dsdRef = dsd.asReference();
                // KeyFamilyRef
                startElement(writer, MESSAGE_NS, "KeyFamilyRef");
                writer.writeCharacters(dsdRef.getMaintainableReference().getMaintainableId());
                writer.writeEndElement();

                // KeyFamilyAgency
                startElement(writer, MESSAGE_NS, "KeyFamilyAgency");
                writer.writeCharacters(dsdRef.getMaintainableReference().getAgencyId());
                writer.writeEndElement();
            }
            //The following are all optional, and only output if the header is not null
            if (header != null) {
                if (isTwoPointOne() && header.getDataProviderReference() != null) {
                    MaintainableRefBean ref = header.getDataProviderReference().getMaintainableReference();
                    if (ObjectUtil.validString(ref.getAgencyId(), ref.getMaintainableId())) {
                        //Start DataProvider
                        startElement(writer, MESSAGE_NS, "DataProvider");

                        //Start Ref
                        writer.writeStartElement("Ref");
                        writer.writeAttribute("agencyID", ref.getAgencyId());
                        writer.writeAttribute("id", ref.getMaintainableId());
                        if (ObjectUtil.validString(ref.getVersion())) {
                            writer.writeAttribute("version", ref.getVersion());
                        } else {
                            writer.writeAttribute("version", MaintainableBean.DEFAULT_VERSION);
                        }
                        //End Ref
                        writer.writeEndElement();

                        //End DataProvider
                        writer.writeEndElement();

                    }
                }
                if (!isTwoPointOne() && header.hasAdditionalAttribute(HeaderBean.DATASET_AGENCY)) {
                    startElement(writer, MESSAGE_NS, "DataSetAgency");
                    writer.writeCharacters(header.getAdditionalAttribute(HeaderBean.DATASET_AGENCY));
                    writer.writeEndElement();
                }
                String datasetId = getDatasetIdForHeader();
                String datasetAction = getDatasetActionForHeader();

                if (isTwoPointOne()) {
                    writeDatasetAction(datasetAction);
                    writeDatasetId(datasetId);
                } else {
                    writeDatasetId(datasetId);
                    writeDatasetAction(datasetAction);
                }

                if (header.getExtracted() != null) {
                    startElement(writer, MESSAGE_NS, "Extracted");
                    writer.writeCharacters(DateUtil.formatDate(header.getExtracted()));
                    writer.writeEndElement();
                }
                String reportingBeginDate = getReportingBeginForHeader();
                if (reportingBeginDate != null) {
                    startElement(writer, MESSAGE_NS, "ReportingBegin");
                    writer.writeCharacters(reportingBeginDate);
                    writer.writeEndElement();
                }

                String reportingEndDate = getReportingEndForHeader();
                if (reportingEndDate != null) {
                    startElement(writer, MESSAGE_NS, "ReportingEnd");
                    writer.writeCharacters(reportingEndDate);
                    writer.writeEndElement();
                }
                if (isTwoPointOne() && header.getEmbargoDate() != null) {
                    startElement(writer, MESSAGE_NS, "EmbargoDate");
                    writer.writeCharacters(DateUtil.formatDate(header.getEmbargoDate()));
                    writer.writeEndElement();
                }
                writeTextTypes(MESSAGE_NS, "Source", header.getSource());
            }

            writer.writeEndElement(); //END HEADER
            headerWritten = true;
        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean writeDatasetId(String datasetId) throws XMLStreamException {
        if (ObjectUtil.validString(datasetId)) {
            startElement(writer, MESSAGE_NS, "DataSetID");
            writer.writeCharacters(datasetId);
            writer.writeEndElement();
            return true;
        }
        return false;
    }

    private boolean writeDatasetAction(String datasetAction) throws XMLStreamException {
        if (ObjectUtil.validString(datasetAction)) {
            startElement(writer, MESSAGE_NS, "DataSetAction");
            writer.writeCharacters(datasetAction);
            writer.writeEndElement();
            return true;
        }
        return false;
    }

    /**
     * Returns the dataset id for the header.
     * If the header does not contain this information, obtain the information from the datasets.
     * If there are no datasets, null will be returned.
     * If there is only one dataset, use that datasets information (if available).
     * If there is more than one dataset, all datasets will be inspected. If they all have the same id, that value
     * will be returned otherwise null will be returned.
     *
     * @return returns the dataset id or null if the id could not be determined.
     */
    private String getDatasetIdForHeader() {
        if (ObjectUtil.validString(header.getDatasetId())) {
            return header.getDatasetId();
        }

        if (dataSetInfo == null) {
            return null;
        }

        DatasetHeaderBean primaryHeaderBean = dataSetInfo.headerBean;
        if (primaryHeaderBean == null) {
            return null;
        }

        return primaryHeaderBean.getDatasetId();
    }

    /**
     * Returns the dataset action for the header.
     * If the header does not contain this information, obtain the information from the datasets.
     * If there are no datasets, null will be returned.
     * If there is only one dataset, use that datasets information (if available).
     * If there is more than one dataset, all datasets will be inspected. If they all have the same action, that value
     * will be returned otherwise null will be returned.
     *
     * @return returns the dataset action or null if the action could not be determined.
     */
    private String getDatasetActionForHeader() {
        if (header.getAction() != null) {
            return header.getAction().getAction();
        }

        if (dataSetInfo == null) {
            return null;
        }

        DatasetHeaderBean primaryHeaderBean = dataSetInfo.headerBean;
        if (primaryHeaderBean == null || primaryHeaderBean.getAction() == null) {
            return null;
        }

        return primaryHeaderBean.getAction().getAction();
    }

    /**
     * Returns the reporting begin date for the header.
     * If the header does not contain this information, obtain the information from the datasets.
     * If there are no datasets, null will be returned.
     * If there is only one dataset, use that datasets information (if available).
     * If there is more than one dataset, all datasets will be inspected. If they all have the same reporting begin date, that value
     * will be returned otherwise null will be returned.
     *
     * @return returns the dataset action or null if the reporting begin date could not be determined.
     */
    private String getReportingBeginForHeader() {
        if (header.getReportingBegin() != null) {
            return DateUtil.formatDate(header.getReportingBegin());
        }

        if (dataSetInfo == null) {
            return null;
        }

        DatasetHeaderBean primaryHeaderBean = dataSetInfo.headerBean;
        if (primaryHeaderBean == null || primaryHeaderBean.getReportingBeginDate() == null) {
            return null;
        }

        return DateUtil.formatDate(primaryHeaderBean.getReportingBeginDate());
    }

    /**
     * Returns the reporting end date for the header.
     * If the header does not contain this information, obtain the information from the datasets.
     * If there are no datasets, null will be returned.
     * If there is only one dataset, use that datasets information (if available).
     * If there is more than one dataset, all datasets will be inspected. If they all have the same reporting end date, that value
     * will be returned otherwise null will be returned.
     *
     * @return returns the dataset action or null if the reporting end date could not be determined.
     */
    private String getReportingEndForHeader() {
        if (header.getReportingEnd() != null) {
            return DateUtil.formatDate(header.getReportingEnd());
        }

        if (dataSetInfo == null) {
            return null;
        }

        DatasetHeaderBean primaryHeaderBean = dataSetInfo.headerBean;
        if (primaryHeaderBean == null || primaryHeaderBean.getReportingEndDate() == null) {
            return null;
        }

        return DateUtil.formatDate(primaryHeaderBean.getReportingEndDate());
    }

    private void writeReceiver(List<PartyBean> receivers) throws XMLStreamException {
        if (receivers != null) {
            for (PartyBean currentReceiver : receivers) {
                startElement(writer, MESSAGE_NS, "Receiver");
                writeParty(currentReceiver, false);
                writer.writeEndElement();
            }
        }
    }

    private void writeParty(PartyBean party, boolean includeTimeZone) throws XMLStreamException {
        if (ObjectUtil.validString(party.getId())) {
            writer.writeAttribute("id", party.getId());
            if (party.getName() != null) {
                Namespace nameNamespace = schemaVersion == SDMX_SCHEMA.VERSION_TWO_POINT_ONE ? COMMON_NS : MESSAGE_NS;
                writeTextTypes(nameNamespace, "Name", party.getName());
            }
            if (party.getContacts() != null) {
                writeContacts(party.getContacts());
            }
            if (includeTimeZone && ObjectUtil.validString(party.getTimeZone())) {
                startElement(writer, MESSAGE_NS, "Timezone");
                writer.writeCharacters(party.getTimeZone());
                writer.writeEndElement();
            }
        } else {
            writer.writeAttribute("id", "unknown");
        }
    }

    private void writeTextTypes(Namespace ns, String elementName, List<TextTypeWrapper> textTypes) throws XMLStreamException {
        if (textTypes != null) {
            for (TextTypeWrapper tt : textTypes) {
                startElement(writer, ns, elementName);
                writer.writeAttribute("xml", XML_NS, "lang", tt.getLocale());
                writer.writeCharacters(tt.getValue());
                writer.writeEndElement();
            }
        }
    }

    private void writeListContents(Namespace nameSpace, String elementName, List<String> list) throws XMLStreamException {
        if (list != null) {
            for (String value : list) {
                if (ObjectUtil.validString(value)) {
                    startElement(writer, nameSpace, elementName);
                    writer.writeCharacters(value);
                    writer.writeEndElement();
                }
            }
        }
    }

    private void writeContacts(List<ContactBean> contacts) throws XMLStreamException {
        if (contacts != null) {
            for (ContactBean currentContact : contacts) {
                startElement(writer, MESSAGE_NS, "Contact");
                writeContact(currentContact);
                writer.writeEndElement();
            }
        }
    }

    private void writeContact(ContactBean contact) throws XMLStreamException {
        Namespace nameNamespace = schemaVersion == SDMX_SCHEMA.VERSION_TWO_POINT_ONE ? COMMON_NS : MESSAGE_NS;
        writeTextTypes(nameNamespace, "Name", contact.getName());
        writeTextTypes(MESSAGE_NS, "Department", contact.getDepartments());
        writeTextTypes(MESSAGE_NS, "Role", contact.getRole());
        writeListContents(MESSAGE_NS, "Telephone", contact.getTelephone());
        writeListContents(MESSAGE_NS, "Fax", contact.getFax());
        writeListContents(MESSAGE_NS, "X400", contact.getX400());
        writeListContents(MESSAGE_NS, "URI", contact.getUri());
        writeListContents(MESSAGE_NS, "Email", contact.getEmail());
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
         * Dataset attribute position.
         */
        DATASET_ATTRIBUTE,
        /**
         * Series key position.
         */
        SERIES_KEY,
        /**
         * Series key attribute position.
         */
        SERIES_KEY_ATTRIBUTE,
        /**
         * Group position.
         */
        GROUP,
        /**
         * Group key position.
         */
        GROUP_KEY,
        /**
         * Group key attribute position.
         */
        GROUP_KEY_ATTRIBUTE,
        /**
         * Observation position.
         */
        OBSERVATION,
        /**
         * Observation attribute position.
         */
        OBSERVATION_ATTRIBUTE;
    }

    /**
     * The type Namespace.
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////INNER CLASSES						 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    class Namespace {
        /**
         * The Namespace url.
         */
        protected String namespaceURL;
        /**
         * The Namespace prefix.
         */
        protected String namespacePrefix;

        /**
         * Instantiates a new Namespace.
         *
         * @param namespaceURL    the namespace url
         * @param namespacePrefix the namespace prefix
         */
        public Namespace(String namespaceURL, String namespacePrefix) {
            this.namespaceURL = namespaceURL;
            this.namespacePrefix = namespacePrefix;
        }
    }

    /**
     * Contains base information about dataset
     */
    private class DataSetInfo {
        private DatasetHeaderBean headerBean;
        private ProvisionAgreementBean provision;
        private DataflowBean dataflow;
        private DataStructureBean dsd;
        private Namespace datasetNamespace;  //Namespace for the dataset
        private Namespace dataNamespace;     //Namespace for the data contents
        private String dimensionAtObservation;
        private Map<String, String> datasetAttributes = new HashMap<>();

        /**
         * Instantiates a new Data set info.
         *
         * @param provision  the provision
         * @param dataflow   the dataflow
         * @param dsd        the dsd
         * @param headerBean the header bean
         */
        public DataSetInfo(
                ProvisionAgreementBean provision,
                DataflowBean dataflow,
                DataStructureBean dsd,
                DatasetHeaderBean headerBean) {

            this.provision = provision;
            this.dataflow = dataflow;
            this.dsd = dsd;
            this.headerBean = headerBean;
            setDimensionAtObservation();

            MaintainableBean constrainable = getStructureForData();
            if (dataFormat == BASE_DATA_FORMAT.COMPACT) {
                dataNamespace = generateStructureNameSpace(constrainable, 1);
                if (isTwoPointOne()) {
                    datasetNamespace = MESSAGE_NS;
                } else {
                    datasetNamespace = dataNamespace;
                }
                COMPACT_NS = dataNamespace;
            } else if (dataFormat == BASE_DATA_FORMAT.CROSS_SECTIONAL) {
                datasetNamespace = MESSAGE_NS;
                dataNamespace = generateStructureNameSpace(constrainable, 1);
                CROSS_NS = dataNamespace;
            } else {
                datasetNamespace = MESSAGE_NS;
            }

        }

        /**
         * Returns either the dataflow if it exists, or data structure
         *
         * @return structure for data
         */
        public MaintainableBean getStructureForData() {
            if (provision != null) {
                return provision;
            } else if (dataflow != null) {
                return dataflow;
            }
            return dsd;
        }

        /**
         * Gets dimension at observation.
         *
         * @return the dimension at observation
         */
        public String getDimensionAtObservation() {
            return dimensionAtObservation;
        }

        private void setDimensionAtObservation() {
            if (headerBean == null || headerBean.getDataStructureReference() == null || !ObjectUtil.validString(headerBean.getDataStructureReference().getDimensionAtObservation())) {
                this.dimensionAtObservation = DimensionBean.TIME_DIMENSION_FIXED_ID;
            } else {
                this.dimensionAtObservation = headerBean.getDataStructureReference().getDimensionAtObservation();
            }
        }

        private Namespace getNamespaceIfKnown(List<Namespace> knownNamespaces, String namespaceUrl) {
            for (Namespace currentNamespace : knownNamespaces) {
                if (currentNamespace.namespaceURL.equals(namespaceUrl)) {
                    return currentNamespace;
                }
            }
            return null;
        }

        /**
         * Writes out the namespace
         *
         * @param bean     either a dataflow, data strucutre, or a provision agreement
         * @param nsNumber
         * @return
         */
        private Namespace generateStructureNameSpace(MaintainableBean bean, int nsNumber) {
            String ssNamespaceUrl = bean.getUrn() + ":ObsLevelDim:" + getDimensionAtObservation();

            Namespace dataNamespace = getNamespaceIfKnown(knownNamespaces, ssNamespaceUrl);
            if (dataNamespace == null) {
                dataNamespace = new Namespace(ssNamespaceUrl, "ns" + nsNumber);
            }
            return dataNamespace;
        }
    }


}
