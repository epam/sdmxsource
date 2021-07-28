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
package org.sdmxsource.sdmx.dataparser.engine.writer;

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
import org.sdmxsource.sdmx.api.util.ReadableDataLocation;
import org.sdmxsource.sdmx.api.util.WriteableDataLocation;
import org.sdmxsource.sdmx.util.beans.ConceptRefUtil;
import org.sdmxsource.sdmx.util.date.DateUtil;
import org.sdmxsource.sdmx.util.stax.StaxUtil;
import org.sdmxsource.util.ObjectUtil;
import org.sdmxsource.util.io.OverflowWriteableDataLocation;
import org.sdmxsource.util.io.StreamUtil;

import javax.xml.stream.*;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;


/**
 * The type Sdmx data writer engine.
 */
public abstract class SdmxDataWriterEngine implements DataWriterEngine {
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
     * The Series writer.
     */
    protected XMLStreamWriter seriesWriter = null;
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

    //private WriteableDataLocation tmpDataLocation;
    private Namespace MESSAGE_NS;
    private Namespace COMMON_NS;
    private Namespace FOOTER_NS = new Namespace("http://www.sdmx.org/resources/sdmxml/schemas/v2_1/message/footer", "footer");
    private BASE_DATA_FORMAT dataFormat;
    //The current series output stream that is being written to
    private OutputStream seriesOut;
    //The current output stream that is being written to
    private OutputStream out;
    //Contains the user's output stream - this is where the data should eventually be written to
    private OutputStream finalOutputStream;
    //Contains a mapping from component id - to concept id
    private Map<String, String> componentIdMapping = new HashMap<String, String>();
    private List<DataSetOutputStreams> outputDatasetLocations = new ArrayList<SdmxDataWriterEngine.DataSetOutputStreams>();
    private DataSetOutputStreams currentDatasetOutputLocations;
    private HeaderBean header;
    private List<Namespace> knownNamespaces = new ArrayList<SdmxDataWriterEngine.Namespace>();

    /**
     * Instantiates a new Sdmx data writer engine.
     *
     * @param schemaVersion the schema version
     * @param dataFormat    the data format
     * @param out           the out
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////CONSTRUCTOR							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public SdmxDataWriterEngine(SDMX_SCHEMA schemaVersion, BASE_DATA_FORMAT dataFormat, OutputStream out) {
        this.finalOutputStream = out; //Store the user's output stream

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


        componentIdMapping = new HashMap<String, String>();
        for (ComponentBean currentComponent : dsd.getComponents()) {
            componentIdMapping.put(currentComponent.getId(), getComponentId(currentComponent));
        }

        closeCurrentWriter();
        currentPosition = POSITION.DATASET;

        currentDatasetOutputLocations = new DataSetOutputStreams(provision, dataflowBean, dsd, header, knownNamespaces, outputDatasetLocations.size() + 1);
        if (currentDatasetOutputLocations.dataNamespace != null) {
            knownNamespaces.add(currentDatasetOutputLocations.dataNamespace);
        }
        this.crossSectionConcept = currentDatasetOutputLocations.getDimensionAtObservation();


        this.isFlat = crossSectionConcept.equals(DIMENSION_AT_OBSERVATION.ALL.getVal());
        this.isCrossSectional = !crossSectionConcept.equals(DimensionBean.TIME_DIMENSION_FIXED_ID);

        if (!isFlat) {
            DimensionBean dimension = dsd.getDimension(crossSectionConcept);
            if (dimension == null) {
                throw new IllegalArgumentException("Can not start dataset.  The dimension at observation has been set to '" + crossSectionConcept + "', but this dimenison does not exist in the datastructure '" + dsd.getUrn() + "'");
            }
            this.isCrossSectionalMeasure = dimension.isMeasureDimension();
        }

        this.out = currentDatasetOutputLocations.outputStream;
        this.seriesOut = currentDatasetOutputLocations.seriesOutLocation.getOutputStream();

        outputDatasetLocations.add(currentDatasetOutputLocations);
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
//		if(!ObjectUtil.validString(obsValue)) {
//			obsValue = SdmxConstants.MISSING_DATA_VALUE;
//		}
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

    //	public void setCrossSectionConcept(String concept) throws UnsupportedException {
    //		if(concept == null) {
    //			throw new ValidationException("Can not set the cross section concept to null");
    //		}
    //		if(!concept.equals(DimensionBean.TIME_DIMENSION_FIXED_ID) && this.schemaVersion != SDMX_SCHEMA.VERSION_TWO_POINT_ONE) {
    //			throw new UnsupportedException("Compact Cross sectional datasets not supported for version " + schemaVersion.toString());
    //		}
    //		this.crossSectionConcept = concept;
    //		isCrossSectional = true;
    //	}

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
        currentDatasetOutputLocations.datasetAttributes.put(id, value);
    }

    /**
     * Write end obs.
     *
     * @throws XMLStreamException the xml stream exception
     */
    protected void writeEndObs() throws XMLStreamException {
        if (!isTwoPointOne()) {
            writeAnnotations(seriesWriter, obsAnnotations);
            obsAnnotations = null;
        }
        seriesWriter.writeEndElement(); //END OBS
    }

    /**
     * Write end series.
     *
     * @throws XMLStreamException the xml stream exception
     */
    protected void writeEndSeries() throws XMLStreamException {
        if (!isTwoPointOne()) {
            writeAnnotations(seriesWriter, seriesAnnotations);
            seriesAnnotations = null;
        }
        seriesWriter.writeEndElement(); //END SERIES
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
     * Close group writer.
     *
     * @throws Exception the exception
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////CLOSE WRITER						 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    protected abstract void closeGroupWriter() throws Exception;

    @Override
    public void close(FooterMessage... footer) {
        if (isClosed) {
            return;
        }
        isClosed = true;

        //1. Close the current writer
        closeCurrentWriter();

        //2. Flush the output to the master output stream
        try {
            flushOutput();
        } finally {
            try {
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
                if (out != null) {
                    out.flush();
                    out.close();
                }
            } catch (Throwable e) {
                System.err.println("Error trying to close resources");
                e.printStackTrace();
            }
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

    /**
     * Closes the current writer if there is one
     */
    private void closeCurrentWriter() {
        if (currentDatasetOutputLocations != null) {
            DataStructureBean dsd = currentDatasetOutputLocations.dsd;
            try {
                if (isTwoPointOne() && dsd.getGroups().size() > 0) {
                    //Copy all the series to the master writer containing the groups
                    seriesWriter.writeEndDocument();
                    seriesWriter.flush();
                    seriesWriter.close();

                    closeGroupWriter();
                    copyToOutputStream(currentDatasetOutputLocations.seriesOutLocation);
                    StreamUtil.closeStream(seriesOut);

                } else {
                    writeAnnotations(writer, datasetAnnotations);
                    datasetAnnotations = null;
                }
                writer.writeEndDocument();
                writer.flush();
                writer.close();
                if (out != null) {
                    out.flush();
                    out.close();
                }
            } catch (Throwable e) {
                currentDatasetOutputLocations.out.close();
                currentDatasetOutputLocations.seriesOutLocation.close();
                currentDatasetOutputLocations = null;
                isClosed = true; //Close the writer engine
                throw new RuntimeException(e);
            } finally {
                currentPosition = null;
            }
        }
    }

    /**
     * Copies the contents of the ReadableDataLocation to the current writer, closes the ReadableDataLocation on completion
     *
     * @param rdl closed on completion
     * @throws XMLStreamException
     */
    private void copyToOutputStream(ReadableDataLocation rdl) throws XMLStreamException {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        InputStream is = rdl.getInputStream();
        XMLStreamReader parser = factory.createXMLStreamReader(is, "UTF-8");
        try {
            while (parser.hasNext()) {
                int event = parser.next();
                writer.flush();
                if (event == XMLStreamConstants.START_ELEMENT) {
                    StaxUtil.copyNode(parser, true, false, false, writer);
                }
            }
        } finally {
            parser.close();
            StreamUtil.closeStream(is);
            rdl.close();
        }
    }

    /**
     * Writes out all the data to the final outputstream
     *
     * @param out
     * @return
     */
    private void flushOutput() {
        try {
            XMLOutputFactory xmlOutputfactory = XMLOutputFactory.newInstance();

            writer = xmlOutputfactory.createXMLStreamWriter(finalOutputStream, "UTF-8");
            writer.writeStartDocument();

            boolean multipleDatasets = outputDatasetLocations.size() > 1;

            //Write the start Node
            if (isTwoPointOne()) {
                if (dataFormat == BASE_DATA_FORMAT.COMPACT) {
                    startElement(writer, MESSAGE_NS, "StructureSpecificData");
                    writer.writeNamespace("ss", SdmxConstants.STRUCTURE_SPECIFIC_NS_2_1);
                } else if (dataFormat == BASE_DATA_FORMAT.GENERIC) {
                    startElement(writer, MESSAGE_NS, "GenericData");
                }

                writeNameSpace(FOOTER_NS);
            } else if (multipleDatasets) {
                startElement(writer, MESSAGE_NS, "MessageGroup");
            } else {
                startElement(writer, MESSAGE_NS, dataFormat.getRootNode());
            }

            Set<String> nameSpacePrefixWritten = new HashSet<String>();
            //Write the Namespaces
            if (dataFormat == BASE_DATA_FORMAT.GENERIC) {
                writeNameSpace(GENERIC_NS);
            } else {
                for (DataSetOutputStreams dsOut : outputDatasetLocations) {
                    if (!nameSpacePrefixWritten.contains(dsOut.dataNamespace.namespacePrefix)) {
                        writeNameSpace(dsOut.dataNamespace);
                        nameSpacePrefixWritten.add(dsOut.dataNamespace.namespacePrefix);
                    }
                    if (!nameSpacePrefixWritten.contains(dsOut.datasetNamespace.namespacePrefix)) {
                        writeNameSpace(dsOut.datasetNamespace);
                        nameSpacePrefixWritten.add(dsOut.datasetNamespace.namespacePrefix);
                    }
                }
                //				writer.writeNamespace(COMPACT_NS.namespacePrefix, COMPACT_NS.namespaceURL);
                //				if(isTwoPointOne()) {
                //					writer.writeNamespace("structurespec", SdmxConstants.STRUCTURE_SPECIFIC_NS_2_1);
                //				}
            }
            if (!nameSpacePrefixWritten.contains(COMMON_NS.namespacePrefix)) {
                writeNameSpace(COMMON_NS);
            }
            if (!nameSpacePrefixWritten.contains(MESSAGE_NS.namespacePrefix)) {
                writeNameSpace(MESSAGE_NS);
            }
            //			writer.setDefaultNamespace(datasetNs);
            writer.writeNamespace("xsi", XSI_NS);
            writer.writeNamespace("xml", XML_NS);

            writeHeader();

            for (DataSetOutputStreams dsOut : outputDatasetLocations) {
                Namespace datasetNamespace = dsOut.datasetNamespace;
                if (multipleDatasets && !isTwoPointOne() && dataFormat == BASE_DATA_FORMAT.GENERIC) {
                    //For a MessageGroup the dataset namespace is the underlying dataformat, otherwise for 2.0 Generic data it is message
                    datasetNamespace = GENERIC_NS;
                }
                startElement(writer, datasetNamespace, "DataSet");
                if (dataFormat == BASE_DATA_FORMAT.COMPACT) {
                    for (String attributeName : dsOut.datasetAttributes.keySet()) {
                        writer.writeAttribute(attributeName, dsOut.datasetAttributes.get(attributeName));
                    }
                }
                writeDatasetHeader(dsOut, multipleDatasets);

                copyToOutputStream(dsOut.out);

                //writer.writeEndElement(); //End Dataset
            }


        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Writes out the dataset header to the writer
     *
     * @param datasetHeader       the header to write, if null will not write anything
     * @param hasMultipleDatasets if there are multiple datasets, then it will include attributes such as action and dataset id, which will normally get written to the messsage header when dealing with a sinlge dataset
     * @throws XMLStreamException
     */
    private void writeDatasetHeader(DataSetOutputStreams dsOut, boolean hasMultipleDatasets) throws XMLStreamException {
        if (isTwoPointOne()) {
            //TODO Will this namespace be written multiple times?
            //			writer.writeNamespace("structurespec", SdmxConstants.STRUCTURE_SPECIFIC_NS_2_1);
            if (dataFormat == BASE_DATA_FORMAT.COMPACT) {
                writer.writeAttribute("ss", SdmxConstants.STRUCTURE_SPECIFIC_NS_2_1, "dataScope", "DataStructure");
                writer.writeAttribute("xsi", XSI_NS, "type", COMPACT_NS.namespacePrefix + ":DataSetType");
                writer.writeAttribute("ss", SdmxConstants.STRUCTURE_SPECIFIC_NS_2_1, "structureRef", getStructureRef(dsOut.getStructureForData()));
            } else {
                writer.writeAttribute("structureRef", getStructureRef(dsOut.getStructureForData()));
            }
        }
        if (dsOut.headerBean != null) {
            DatasetHeaderBean datasetHeader = dsOut.headerBean;
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
            if (hasMultipleDatasets) {
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
                    writer.writeAttribute("keyFamilyURI", dsOut.dsd.getUrn());
                }
                if (datasetHeader.getReportingBeginDate() != null) {
                    writer.writeAttribute("reportingBeginDate", DateUtil.formatDate(datasetHeader.getReportingBeginDate()));
                }
                if (datasetHeader.getReportingEndDate() != null) {
                    writer.writeAttribute("reportingEndDate", DateUtil.formatDate(datasetHeader.getReportingEndDate()));
                }
            }
        }
    }

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
                writer.writeCharacters(new Boolean(header.isTest()).toString());
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
            Set<MaintainableBean> constrainables = new HashSet<MaintainableBean>();
            Set<DataStructureBean> dsds = new HashSet<DataStructureBean>();
            for (DataSetOutputStreams os : outputDatasetLocations) {
                constrainables.add(os.getStructureForData());
                dsds.add(os.dsd);
            }

            if (isTwoPointOne()) {
                for (MaintainableBean currentStructure : constrainables) {
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
                }
            } else if (dsds.size() == 1) {
                DataStructureBean dsd = (DataStructureBean) dsds.toArray()[0];
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

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////WRITE HEADER						 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////

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

        if (outputDatasetLocations.size() == 0) {
            return null;
        }

        DatasetHeaderBean primaryHeaderBean = outputDatasetLocations.get(0).headerBean;
        if (primaryHeaderBean == null) {
            return null;
        }

        if (outputDatasetLocations.size() == 1) {
            return primaryHeaderBean.getDatasetId();
        }

        String primaryId = primaryHeaderBean.getDatasetId();
        for (int i = 1; i < outputDatasetLocations.size(); i++) {
            DatasetHeaderBean headerBean = outputDatasetLocations.get(i).headerBean;
            if (headerBean == null) {
                return null;
            }
            if (!primaryId.equals(headerBean.getDatasetId())) {
                return null;
            }
        }
        return primaryId;
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

        if (outputDatasetLocations.size() == 0) {
            return null;
        }

        DatasetHeaderBean primaryHeaderBean = outputDatasetLocations.get(0).headerBean;
        if (primaryHeaderBean == null || primaryHeaderBean.getAction() == null) {
            return null;
        }

        if (outputDatasetLocations.size() == 1) {
            return primaryHeaderBean.getAction().getAction();
        }

        DATASET_ACTION primaryAction = primaryHeaderBean.getAction();
        for (int i = 1; i < outputDatasetLocations.size(); i++) {
            DatasetHeaderBean headerBean = outputDatasetLocations.get(i).headerBean;
            if (headerBean == null || headerBean.getAction() == null) {
                return null;
            }
            if (!primaryAction.equals(headerBean.getAction())) {
                return null;
            }
        }
        return primaryAction.getAction();
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

        if (outputDatasetLocations.size() == 0) {
            return null;
        }

        DatasetHeaderBean primaryHeaderBean = outputDatasetLocations.get(0).headerBean;
        if (primaryHeaderBean == null || primaryHeaderBean.getReportingBeginDate() == null) {
            return null;
        }

        if (outputDatasetLocations.size() == 1) {
            return DateUtil.formatDate(primaryHeaderBean.getReportingBeginDate());
        }

        Date primaryDate = primaryHeaderBean.getReportingBeginDate();
        for (int i = 1; i < outputDatasetLocations.size(); i++) {
            DatasetHeaderBean headerBean = outputDatasetLocations.get(i).headerBean;
            if (headerBean == null || headerBean.getReportingBeginDate() == null) {
                return null;
            }
            if (!primaryDate.equals(headerBean.getReportingBeginDate())) {
                return null;
            }
        }
        return DateUtil.formatDate(primaryDate);
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

        if (outputDatasetLocations.size() == 0) {
            return null;
        }

        DatasetHeaderBean primaryHeaderBean = outputDatasetLocations.get(0).headerBean;
        if (primaryHeaderBean == null || primaryHeaderBean.getReportingEndDate() == null) {
            return null;
        }

        if (outputDatasetLocations.size() == 1) {
            return DateUtil.formatDate(primaryHeaderBean.getReportingEndDate());
        }

        Date primaryDate = primaryHeaderBean.getReportingEndDate();
        for (int i = 1; i < outputDatasetLocations.size(); i++) {
            DatasetHeaderBean headerBean = outputDatasetLocations.get(i).headerBean;
            if (headerBean == null || headerBean.getReportingEndDate() == null) {
                return null;
            }
            if (!primaryDate.equals(headerBean.getReportingEndDate())) {
                return null;
            }
        }
        return DateUtil.formatDate(primaryDate);
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
     * Contains output streams for each dataset that has been written
     */
    private class DataSetOutputStreams {
        private WriteableDataLocation seriesOutLocation;
        private WriteableDataLocation out;
        private OutputStream outputStream;
        private DatasetHeaderBean headerBean;
        private ProvisionAgreementBean provision;
        private DataflowBean dataflow;
        private DataStructureBean dsd;
        private Namespace datasetNamespace;  //Namespace for the dataset
        private Namespace dataNamespace;     //Namespace for the data contents
        private String dimensionAtObservation;
        private Map<String, String> datasetAttributes = new HashMap<String, String>();

        /**
         * Instantiates a new Data set output streams.
         *
         * @param provision       the provision
         * @param dataflow        the dataflow
         * @param dsd             the dsd
         * @param headerBean      the header bean
         * @param knownNamespaces the known namespaces
         * @param writerNumber    the writer number
         */
        public DataSetOutputStreams(
                ProvisionAgreementBean provision,
                DataflowBean dataflow,
                DataStructureBean dsd,
                DatasetHeaderBean headerBean,
                List<Namespace> knownNamespaces,
                int writerNumber) {
            this.provision = provision;
            this.dataflow = dataflow;
            this.dsd = dsd;
            out = new OverflowWriteableDataLocation();
            outputStream = out.getOutputStream();

            this.headerBean = headerBean;
            setDimensionAtObservation();
            writer = createWriter(outputStream, knownNamespaces, writerNumber);
            try {
                writer.flush();
            } catch (XMLStreamException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (isTwoPointOne() && dsd.getGroups().size() > 0) {
                seriesOutLocation = new OverflowWriteableDataLocation();
                seriesWriter = createWriter(seriesOutLocation.getOutputStream(), knownNamespaces, writerNumber);
            } else {
                seriesWriter = writer;
                seriesOutLocation = out;
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

        private XMLStreamWriter createWriter(OutputStream writerOut, List<Namespace> knownNamespaces, int writerNumber) {
            try {
                XMLOutputFactory xmlOutputfactory = XMLOutputFactory.newInstance();
                XMLStreamWriter writer = xmlOutputfactory.createXMLStreamWriter(writerOut, "UTF-8");
                writer.writeStartDocument();
                MaintainableBean constrainable = getStructureForData();

                if (dataFormat == BASE_DATA_FORMAT.COMPACT) {
                    dataNamespace = generateStructureNameSpace(constrainable, writerNumber);
                    if (isTwoPointOne()) {
                        datasetNamespace = MESSAGE_NS;
                    } else {
                        datasetNamespace = dataNamespace;
                    }
                    COMPACT_NS = dataNamespace;
                } else if (dataFormat == BASE_DATA_FORMAT.CROSS_SECTIONAL) {
                    datasetNamespace = MESSAGE_NS;
                    dataNamespace = generateStructureNameSpace(constrainable, writerNumber);
                    CROSS_NS = dataNamespace;
                } else {
                    datasetNamespace = MESSAGE_NS;
                }

                startElement(writer, datasetNamespace, "DataSet");
                if (isTwoPointOne()) {
                    writer.writeNamespace(FOOTER_NS.namespacePrefix, FOOTER_NS.namespaceURL);
                }
                writer.writeNamespace(COMMON_NS.namespacePrefix, COMMON_NS.namespaceURL);
                writer.writeNamespace(MESSAGE_NS.namespacePrefix, MESSAGE_NS.namespaceURL);
                if (dataNamespace != null && dataNamespace != datasetNamespace) {
                    writer.writeNamespace(dataNamespace.namespacePrefix, dataNamespace.namespaceURL);
                }
                if (dataFormat == BASE_DATA_FORMAT.GENERIC) {
                    writer.writeNamespace(GENERIC_NS.namespacePrefix, GENERIC_NS.namespaceURL);
                }
                writer.writeNamespace("xsi", XSI_NS);
                writer.writeNamespace("xml", XML_NS);

                if (datasetNamespace != MESSAGE_NS) {
                    writer.writeNamespace(datasetNamespace.namespacePrefix, datasetNamespace.namespaceURL);
                }
                if (!isTwoPointOne()) {
                    if (constrainable instanceof DataflowBean) {
                        //Version 2.0 and 1.0 have dataflowAgencyID and dataflowID on the Dataset as attributes
                        datasetAttributes.put("dataflowAgencyID", constrainable.getAgencyId());
                        datasetAttributes.put("dataflowID", constrainable.getId());
                    } else if (constrainable instanceof ProvisionAgreementBean) {
                        ProvisionAgreementBean prov = (ProvisionAgreementBean) constrainable;
                        //Version 2.0 and 1.0 have dataflowAgencyID and dataflowID on the Dataset as attributes
                        datasetAttributes.put("dataflowAgencyID", prov.getStructureUseage().getAgencyId());
                        datasetAttributes.put("dataflowID", prov.getStructureUseage().getMaintainableId());
                        datasetAttributes.put("dataProviderID", prov.getDataproviderRef().getIdentifiableIds()[0]);
                        datasetAttributes.put("dataProviderSchemeAgencyId", prov.getDataproviderRef().getAgencyId());
                        datasetAttributes.put("dataProviderSchemeId", prov.getDataproviderRef().getMaintainableId());
                    }
                }

                return writer;
            } catch (XMLStreamException e) {
                throw new RuntimeException(e);
            }
        }

        /**
         * Writes out the namespace
         *
         * @param bean         either a dataflow, data strucutre, or a provision agreement
         * @param writerNumber
         * @return
         */
        private Namespace generateStructureNameSpace(MaintainableBean bean, int writerNumber) {
            String ssNamespaceUrl = bean.getUrn() + ":ObsLevelDim:" + getDimensionAtObservation();

            Namespace dataNamespace = getNamespaceIfKnown(knownNamespaces, ssNamespaceUrl);
            if (dataNamespace == null) {
                dataNamespace = new Namespace(ssNamespaceUrl, "ns" + writerNumber);
            }
            return dataNamespace;
        }
    }


}
