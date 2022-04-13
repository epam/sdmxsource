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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sdmxsource.sdmx.api.constants.*;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.beans.base.ComponentBean;
import org.sdmxsource.sdmx.api.model.beans.base.ContactBean;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.beans.base.TextTypeWrapper;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataflowBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ProvisionAgreementBean;
import org.sdmxsource.sdmx.api.model.data.Keyable;
import org.sdmxsource.sdmx.api.model.data.Observation;
import org.sdmxsource.sdmx.api.model.header.DatasetStructureReferenceBean;
import org.sdmxsource.sdmx.api.model.header.HeaderBean;
import org.sdmxsource.sdmx.api.model.header.PartyBean;
import org.sdmxsource.sdmx.api.util.ReadableDataLocation;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.ContactBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.TextTypeWrapperImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.header.DatasetStructureReferenceBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.header.HeaderBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.header.PartyBeanImpl;
import org.sdmxsource.sdmx.util.beans.reference.StructureReferenceBeanImpl;
import org.sdmxsource.sdmx.util.date.DateUtil;
import org.sdmxsource.sdmx.util.sdmx.SdmxMessageUtil;
import org.sdmxsource.util.ObjectUtil;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.util.*;


/**
 * The type Abstract sdmx data reader engine.
 */
public abstract class AbstractSdmxDataReaderEngine extends AbstractDataReaderEngine {
    /**
     * The constant XML_NS.
     */
    protected static final String XML_NS = "http://www.w3.org/XML/1998/namespace";
    private static final long serialVersionUID = 2653866522651246489L;
    private static final Logger LOG = LogManager.getLogger(AbstractSdmxDataReaderEngine.class);

    /**
     * The Parser.
     */
    protected transient XMLStreamReader parser;
    /**
     * The Run ahead parser.
     */
    protected transient XMLStreamReader runAheadParser;
    /**
     * The Is two point one.
     */
    protected boolean isTwoPointOne;
    /**
     * The Group id.
     */
    protected String groupId;
    /**
     * The No series.
     */
    protected boolean noSeries;
    private transient InputStream parserInputStream;
    private transient InputStream runAheadParserInputStream;
    private Observation flatObs;


    //A mapping of concept id, to component id
    private Map<String, String> conceptToComponentId = new HashMap<String, String>();

    private LAST_CALL lastCall;

    /**
     * Creates a reader engine based on the data location, the location of available data structures that can be used to retrieve dsds, and the default dsd to use
     *
     * @param dataLocation       the location of the data
     * @param beanRetrieval      giving the ability to retrieve dsds for the datasets this reader engine is reading.  This can be null if there is only one relevent dsd - in which case the default dsd should be provided
     * @param defaultDsd         the default dsd to use if the beanRetrieval is null, or if the bean retrieval does not return the dsd for the given dataset
     * @param dataflowBean       the dataflow bean
     * @param provisionAgreement the provision agreement
     */
    public AbstractSdmxDataReaderEngine(ReadableDataLocation dataLocation, SdmxBeanRetrievalManager beanRetrieval, DataStructureBean defaultDsd, DataflowBean dataflowBean, ProvisionAgreementBean provisionAgreement) {
        super(dataLocation, beanRetrieval, defaultDsd, dataflowBean, provisionAgreement);
        isTwoPointOne = SdmxMessageUtil.getSchemaVersion(dataLocation) == SDMX_SCHEMA.VERSION_TWO_POINT_ONE;
    }

    @Override
    protected void setCurrentDsd(DataStructureBean currentDsd) {
        super.setCurrentDsd(currentDsd);
        for (ComponentBean component : currentDsd.getComponents()) {
            conceptToComponentId.put(component.getConceptRef().getFullId(), component.getId());
        }
    }

    /**
     * Returns the id that should be output for a component Id.  This will be the id of the dimension
     * For a 2.1 data message, this is the id of the component (and will just be returned unchanged).
     *
     * @param componentId the component id
     * @return component id
     */
    protected String getComponentId(String componentId) {
        if (isTwoPointOne) {
            return componentId;
        }
        if (conceptToComponentId.containsKey(componentId)) {
            return conceptToComponentId.get(componentId);
        }
        return componentId;
    }

    /**
     * Closes all the streams and recreates them from the input locations
     */
    @Override
    public void reset() {
        super.reset();
        // Close all of the streams and parsers.
        closeStreams();

        lastCall = null;
        groupId = null;
        flatObs = null;

        XMLInputFactory factory = XMLInputFactory.newInstance();
        try {
            parserInputStream = dataLocation.getInputStream();
            parser = factory.createXMLStreamReader(parserInputStream, "UTF-8");
            runAheadParserInputStream = dataLocation.getInputStream();
            runAheadParser = factory.createXMLStreamReader(runAheadParserInputStream, "UTF-8");
            this.headerBean = processHeader();
        } catch (XMLStreamException e) {
            close();
            throw new RuntimeException(e);
        }
    }

    @Override
    public HeaderBean getHeader() {
        return headerBean;
    }

    private HeaderBean processHeader() throws XMLStreamException {
        //All the attributes required to build a header
        Map<String, String> additionalAttributes = new HashMap<String, String>();
        StructureReferenceBean dataProviderReference = null;
        List<DatasetStructureReferenceBean> structure = new ArrayList<DatasetStructureReferenceBean>();
        DATASET_ACTION datasetAction = null;
        String id = null;
        String datasetId = null;
        Date embargoDate = null;
        Date extracted = null;
        Date prepared = null;
        Date reportingBegin = null;
        Date reportingEnd = null;
        List<TextTypeWrapper> name = new ArrayList<TextTypeWrapper>();
        List<TextTypeWrapper> source = new ArrayList<TextTypeWrapper>();
        List<PartyBean> receiver = new ArrayList<PartyBean>();
        PartyBean sender = null;
        boolean test = false;
        String dsdId = null;
        String dsdAgency = null;
        while (parser.hasNext()) {
            int event = parser.next();
            if (event == XMLStreamConstants.START_ELEMENT) {
                String nodeName = parser.getLocalName();
                if (nodeName.equals("ID")) {
                    id = parser.getElementText();
                } else if (nodeName.equals("Test")) {
                    test = Boolean.valueOf(parser.getElementText());
                } else if (nodeName.equals("Prepared")) {
                    prepared = DateUtil.formatDate(parser.getElementText(), true);
                } else if (nodeName.equals("Sender")) {
                    sender = processParty(nodeName);
                } else if (nodeName.equals("Receiver")) {
                    receiver.add(processParty(nodeName));
                } else if (nodeName.equals("Name")) {
                    addItemToLangMap(name);
                } else if (nodeName.equals("Structure")) {
                    structure.add(processStructure());
                } else if (nodeName.equals("DataProvider")) {
                    dataProviderReference = parseStructureReference(nodeName, SDMX_STRUCTURE_TYPE.DATA_PROVIDER);
                } else if (nodeName.equals("DataSetAction")) {
                    datasetAction = DATASET_ACTION.getAction(parser.getElementText());
                } else if (nodeName.equals("DataSetID")) {
                    datasetId = parser.getElementText();
                } else if (nodeName.equals("Extracted")) {
                    extracted = DateUtil.formatDate(parser.getElementText(), true);
                } else if (nodeName.equals("ReportingBegin")) {
                    reportingBegin = DateUtil.formatDate(parser.getElementText(), true);
                } else if (nodeName.equals("ReportingEnd")) {
                    reportingEnd = DateUtil.formatDate(parser.getElementText(), true);
                } else if (nodeName.equals("EmbargoDate")) {
                    embargoDate = DateUtil.formatDate(parser.getElementText(), true);
                } else if (nodeName.equals("Source")) {
                    addItemToLangMap(source);
                } else if (nodeName.equals("KeyFamilyAgency")) {
                    dsdAgency = parser.getElementText();
                } else if (nodeName.equals("KeyFamilyRef")) {
                    dsdId = parser.getElementText();
                }
            } else if (event == XMLStreamConstants.END_ELEMENT) {
                String nodeName = parser.getLocalName();
                if (nodeName.equals("Header")) {
                    if (dsdId != null) {
                        if (defaultDsd != null && dsdId.equals(defaultDsd.getId())) {
                            structure.add(new DatasetStructureReferenceBeanImpl(defaultDsd.asReference()));
                        } else {
                            structure.add(new DatasetStructureReferenceBeanImpl(new StructureReferenceBeanImpl(dsdAgency, dsdId, MaintainableBean.DEFAULT_VERSION, SDMX_STRUCTURE_TYPE.DSD)));
                        }
                    }

                    return new HeaderBeanImpl(additionalAttributes,
                            structure,
                            dataProviderReference,
                            datasetAction,
                            id,
                            datasetId,
                            embargoDate,
                            extracted,
                            prepared,
                            reportingBegin,
                            reportingEnd,
                            name,
                            source,
                            receiver,
                            sender,
                            test);
                }
            }
        }
        throw new IllegalArgumentException("Dataset does not contain a header");
    }

    private void addItemToLangMap(List<TextTypeWrapper> tts) throws XMLStreamException {
        String lang = "en";
        try {
            lang = parser.getAttributeValue(XML_NS, "lang");
        } catch (Throwable th) {

        }
        String value = parser.getElementText();
        tts.add(new TextTypeWrapperImpl(lang, value, null));
    }

    /**
     * parser expects at position and reading a Structure node in the header
     *
     * @return
     * @throws XMLStreamException
     */
    private DatasetStructureReferenceBean processStructure() throws XMLStreamException {
        String id = null;
        StructureReferenceBean structureReference = null;
        String serviceURL = null;
        String structureURL = null;
        String dimensionAtObservation = null;

        id = parser.getAttributeValue(null, "structureID");
        serviceURL = parser.getAttributeValue(null, "serviceURL");
        structureURL = parser.getAttributeValue(null, "structureURL");
        dimensionAtObservation = parser.getAttributeValue(null, "dimensionAtObservation");

        while (parser.hasNext()) {
            int event = parser.next();
            if (event == XMLStreamConstants.START_ELEMENT) {
                String nodeName = parser.getLocalName();
                if (nodeName.equals("ProvisionAgrement") || nodeName.equals("ProvisionAgreement")) {  //This accounts for the
                    //typo in the SDMX 2.1 Schema
                    structureReference = parseStructureReference(nodeName, SDMX_STRUCTURE_TYPE.PROVISION_AGREEMENT);
                } else if (nodeName.equals("StructureUsage")) {
                    structureReference = parseStructureReference(nodeName, SDMX_STRUCTURE_TYPE.DATAFLOW);
                } else if (nodeName.equals("Structure")) {
                    structureReference = parseStructureReference(nodeName, SDMX_STRUCTURE_TYPE.DSD);
                }
            } else if (event == XMLStreamConstants.END_ELEMENT) {
                String nodeName = parser.getLocalName();
                if (nodeName.equals("Structure")) {
                    break;
                }
            }
        }
        if (structureReference == null) {
            throw new RuntimeException("Dataset Header.Structure expected to have one of the following nodes present (ProvisionAgreement|StructureUsage|Structure) to reference either a provision agreement, dataflow, or data structure definition");
        }
        if (dimensionAtObservation.equals(DIMENSION_AT_OBSERVATION.ALL.getVal())) {
            noSeries = true;
        }
        return new DatasetStructureReferenceBeanImpl(id, structureReference, serviceURL, structureURL, dimensionAtObservation);
    }

    private StructureReferenceBean parseStructureReference(String parseNodeName, SDMX_STRUCTURE_TYPE structureType) throws XMLStreamException {
        while (parser.hasNext()) {
            int event = parser.next();
            if (event == XMLStreamConstants.START_ELEMENT) {
                String nodeName = parser.getLocalName();
                if (nodeName.equals("Ref")) {
                    String agencyId = parser.getAttributeValue(null, "agencyID");
                    String id = parser.getAttributeValue(null, "id");
                    String maintainableId = parser.getAttributeValue(null, "maintainableParentId");
                    String version = parser.getAttributeValue(null, "version");
                    if (!ObjectUtil.validString(agencyId)) {
                        throw new RuntimeException("Dataset structure reference incomplete, missing agencyId");
                    }
                    if (!ObjectUtil.validString(id)) {
                        throw new RuntimeException("Dataset structure reference incomplete, missing id");
                    }
                    if (!structureType.isMaintainable()) {
                        if (!ObjectUtil.validString(maintainableId)) {
                            throw new RuntimeException("Dataset structure reference incomplete, missing maintainableParentId");
                        }
                        return new StructureReferenceBeanImpl(agencyId, maintainableId, version, structureType, id);  //Identifiable Reference
                    }
                    return new StructureReferenceBeanImpl(agencyId, id, version, structureType);  //Maintainable Reference
                } else if (nodeName.equals("URN")) {
                    String urn = parser.getElementText();
                    if (ObjectUtil.validString(urn)) {
                        StructureReferenceBean sRef = new StructureReferenceBeanImpl(urn);
                        if (sRef.getTargetReference() != structureType) {
                            throw new RuntimeException("Dataset Structure reference invalid '" + urn + "' , expecting a reference to '" + structureType.getType() + "' but got '" + sRef.getTargetReference().getType() + "'");
                        }
                        return sRef;
                    }
                }
            } else if (event == XMLStreamConstants.END_ELEMENT) {
                String nodeName = parser.getLocalName();
                if (nodeName.equals(parseNodeName)) {
                    break;
                }
            }
        }
        throw new RuntimeException("Dataset structure reference invalid, could not process reference, no Ref node or URN node found");
    }

    private PartyBean processParty(String partyNodeName) throws XMLStreamException {
        String id = parser.getAttributeValue(null, "id");
        String timeZone = null;
        List<TextTypeWrapper> nameMap = new ArrayList<TextTypeWrapper>();
        List<ContactBean> contacts = new ArrayList<ContactBean>();

        while (parser.hasNext()) {
            int event = parser.next();
            if (event == XMLStreamConstants.START_ELEMENT) {
                String nodeName = parser.getLocalName();
                if (nodeName.equals("Name")) {
                    addItemToLangMap(nameMap);
                } else if (nodeName.equals("TimeZone")) {
                    timeZone = parser.getElementText();
                } else if (nodeName.equals("Contact")) {
                    contacts.add(processContact());
                }
            } else if (event == XMLStreamConstants.END_ELEMENT) {
                String nodeName = parser.getLocalName();
                if (nodeName.equals(partyNodeName)) {
                    break;
                }
            }
        }
        return new PartyBeanImpl(nameMap, id, contacts, timeZone);
    }

    private ContactBean processContact() throws XMLStreamException {
        List<TextTypeWrapper> name = new ArrayList<TextTypeWrapper>();
        List<TextTypeWrapper> role = new ArrayList<TextTypeWrapper>();
        List<TextTypeWrapper> departments = new ArrayList<TextTypeWrapper>();
        List<String> email = new ArrayList<String>();
        List<String> fax = new ArrayList<String>();
        List<String> telephone = new ArrayList<String>();
        List<String> uri = new ArrayList<String>();
        List<String> x400 = new ArrayList<String>();

        while (parser.hasNext()) {
            int event = parser.next();
            if (event == XMLStreamConstants.START_ELEMENT) {
                String nodeName = parser.getLocalName();
                if (nodeName.equals("Name")) {
                    addItemToLangMap(name);
                } else if (nodeName.equals("Role")) {
                    addItemToLangMap(role);
                } else if (nodeName.equals("Department")) {
                    addItemToLangMap(departments);
                } else if (nodeName.equals("Telephone")) {
                    telephone.add(parser.getElementText());
                } else if (nodeName.equals("Fax")) {
                    fax.add(parser.getElementText());
                } else if (nodeName.equals("X400")) {
                    x400.add(parser.getElementText());
                } else if (nodeName.equals("URI")) {
                    uri.add(parser.getElementText());
                } else if (nodeName.equals("Email")) {
                    email.add(parser.getElementText());
                }
            } else if (event == XMLStreamConstants.END_ELEMENT) {
                String nodeName = parser.getLocalName();
                if (nodeName.equals("Contact")) {
                    break;
                }
            }
        }
        return new ContactBeanImpl(name, role, departments, email, fax, telephone, uri, x400);
    }

    /**
     * Implementation of Move next, does not actually set the currentObs value, as it is lazy loaded on demand - instead this operation moves the pointer in the file
     * to the next observation to be read (if there is one) and reports true if there is a next observation to be read.
     */
    @Override
    protected boolean moveNextObservationInternal() {
        try {
            currentObs = null;  //Set the current observation to null, this is so when the user reads the observation it can generate it on demand

            //If we are at the end of the file, then return false, there is no point in processing anything
            if (!hasNext) {
                return false;
            }
            //If the dataset position is an observation that is also acting as a series, then the following rules apply;
            //1. The user has made a call on hasNextSeries, this has returned true, but they have not processed the series, in this case, we need to process the series to extract the observation
            //2. The user's last call was readNextSeries, in which case there will be an observation, as the series IS also the observation
            //3. The user's last call was hasNextObservation, or readNextObs in both cases we return false, as there is only one observation here
            if (datasetPosition == DATASET_POSITION.OBSERAVTION_AS_SERIES) {
                if (lastCall == LAST_CALL.HAS_NEXT_SERIES) {
                    processSeriesNode();
                    return true;
                }
                if (lastCall == LAST_CALL.NEXT_SERIES) {
                    return true;
                }
                //We have read the next obs, or already made this call, set the position to null and return false
                datasetPosition = null;
                return false;
            }

            if (next(true)) {
                return datasetPosition == DATASET_POSITION.OBSERVATION;
            }
            return false;
        } catch (XMLStreamException e) {
            throw new SdmxException(e, "Unrecoverable error while reading SDMX data");
        } catch (SdmxException e) {
            throw new SdmxException(e, "Error while attempting to read observation");
        } finally {
            lastCall = LAST_CALL.HAS_NEXT_OBS;
        }
    }

    @Override
    protected Observation lazyLoadObservation() {
        try {
            //There are no more observations, or even series return null
            if (!hasNext) {
                return null;
            }
            //Series and Observation are on the same node (2.1
            if (datasetPosition == DATASET_POSITION.OBSERAVTION_AS_SERIES) {
                //The current node is a series and obs node as one, the last call was to get the next observation, there is only one so this time we return null
                if (lastCall == LAST_CALL.NEXT_OBS) {
                    return null;
                }

                //The current node is a series and obs node as one, the last call was not to process the next series but to move onto it, so we will process it now, to get the next observation
                if (lastCall == LAST_CALL.HAS_NEXT_SERIES) {
                    processSeriesNode();
                }
                //The series has now been processed, return the observation that resulted from that reading
                return flatObs;
            }

            //If the user has not yet called has next observation, then make a call now to check and return the next obs, if there is one
            if (lastCall != LAST_CALL.HAS_NEXT_OBS) {
                if (moveNextObservation()) {
                    return processObsNode(parser);
                }
                return null;
            }
            //The last call was has next observation, so make sure, the dataset position is on the observation node (i.e - there was a next one), and process it
            if (datasetPosition == DATASET_POSITION.OBSERVATION) {
                return processObsNode(parser);
            }

            //The last call was has next obs, and it returned false, so this call returns null
            return null;
        } catch (XMLStreamException e) {
            throw new RuntimeException("Unrecoverable error while reading SDMX data", e);
        } finally {
            lastCall = LAST_CALL.NEXT_OBS;
        }
    }

    @Override
    protected boolean moveNextDatasetInternal() {
        try {
            //If a check was made to hasNextSeries, the parser may have moved forward to the next dataset,
            if (lastCall != LAST_CALL.HAS_NEXT_DATASET && datasetPosition == DATASET_POSITION.DATASET) {
                return true;
            }
            //Set the dataset to null at this point, as it may be recreated
            datasetHeaderBean = null;
            while (next(false)) {
                if (datasetPosition == DATASET_POSITION.DATASET) {
                    return true;
                }
            }
            return false;

        } catch (XMLStreamException e) {
            throw new RuntimeException("Unrecoverable error while reading SDMX data", e);
        } finally {
            lastCall = LAST_CALL.HAS_NEXT_DATASET;
        }
    }

    @Override
    protected boolean moveNextKeyableInternal() {
        try {
            if (datasetPosition == DATASET_POSITION.DATASET
                    && lastCall != LAST_CALL.HAS_NEXT_DATASET) {
                return false;
            }

            //If a check was made to hasNextObservation, the parser may have moved forward to the next series as obs,
            //and this may not yet have been processed, in which case check to see if a call has been made to hasNextKeyable since the last
            //call to hasNextObservation
            if (lastCall == LAST_CALL.HAS_NEXT_OBS
                    && (datasetPosition == DATASET_POSITION.OBSERAVTION_AS_SERIES
                    || datasetPosition == DATASET_POSITION.SERIES
                    || datasetPosition == DATASET_POSITION.GROUP)) {
                return true;
            }
            while (next(false)) {
                if (datasetPosition == DATASET_POSITION.SERIES
                        || datasetPosition == DATASET_POSITION.GROUP
                        || datasetPosition == DATASET_POSITION.OBSERAVTION_AS_SERIES) {
                    return true;
                }
                if (datasetPosition == DATASET_POSITION.DATASET) {
                    return false;
                }
            }
            return false;
        } catch (XMLStreamException e) {
            throw new SdmxException(e, "Unrecoverable error while reading SDMX data");
        } catch (SdmxException e) {
            throw new SdmxException(e, "Error while attempting to read key");
        } finally {
            lastCall = LAST_CALL.HAS_NEXT_SERIES;
        }
    }

    @Override
    protected Keyable lazyLoadKey() {
        try {
            //If the last call was to read the next series, then we are reading the one after that, so move on,
            //Otherwise, if we're not on a series, group, or obs as series, move on
            if (lastCall == LAST_CALL.NEXT_SERIES ||
                    (datasetPosition != DATASET_POSITION.SERIES
                            && datasetPosition != DATASET_POSITION.GROUP
                            && datasetPosition != DATASET_POSITION.OBSERAVTION_AS_SERIES)) {
                if (!moveNextKeyable()) {
                    return null;
                }
            }

            if (datasetPosition == DATASET_POSITION.GROUP) {
                Keyable key = processGroupNode();
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Read Key " + key.toString());
                }
                return key;
            } else if (datasetPosition == DATASET_POSITION.SERIES || datasetPosition == DATASET_POSITION.OBSERAVTION_AS_SERIES) {
                Keyable key = processSeriesNode();
                flatObs = currentObs;   //STORE THE OBSERVATION LOCALLY IN THIS CLASS THAT WAS CREATED AS PART OF GENERATING THE SERIES
                if (LOG.isDebugEnabled()) {
                    LOG.debug(key.toString());
                }
                return key;
            }
            return null;
        } catch (XMLStreamException e) {
            throw new RuntimeException("Unrecoverable error while reading SDMX data", e);
        } finally {
            lastCall = LAST_CALL.NEXT_SERIES;
        }
    }

    @Override
    public void close() {
        closeStreams();
        if (dataLocation != null) {
            dataLocation.close();
            dataLocation = null;
        }
    }

    private void closeStreams() {
        if (parser != null) {
            try {
                parser.close();
            } catch (XMLStreamException e) {
                throw new RuntimeException("Error trying to close parser : " + e);
            }
        }

        if (parserInputStream != null) {
            try {
                parserInputStream.close();
            } catch (Exception e) {
                throw new RuntimeException("Error trying to close parser InputStream : " + e);
            }
        }

        if (runAheadParser != null) {
            try {
                runAheadParser.close();
            } catch (XMLStreamException e) {
                throw new RuntimeException("Error trying to close runAheadParser : " + e);
            }
        }

        if (runAheadParserInputStream != null) {
            try {
                runAheadParserInputStream.close();
            } catch (Exception e) {
                throw new RuntimeException("Error trying to close runAheadParserInputStream InputStream : " + e);
            }
        }
    }

    /**
     * Process group node keyable.
     *
     * @return the keyable
     * @throws XMLStreamException the xml stream exception
     */
    protected abstract Keyable processGroupNode() throws XMLStreamException;

    /**
     * Process series node keyable.
     *
     * @return the keyable
     * @throws XMLStreamException the xml stream exception
     */
    protected abstract Keyable processSeriesNode() throws XMLStreamException;

    /**
     * Process obs node observation.
     *
     * @param parser the parser
     * @return the observation
     * @throws XMLStreamException the xml stream exception
     */
    protected abstract Observation processObsNode(XMLStreamReader parser) throws XMLStreamException;

    /**
     * Next boolean.
     *
     * @param includeObs the include obs
     * @return the boolean
     * @throws XMLStreamException the xml stream exception
     */
    protected abstract boolean next(boolean includeObs) throws XMLStreamException;

    /**
     * The enum Last call.
     */
    enum LAST_CALL {
        /**
         * Has next dataset last call.
         */
        HAS_NEXT_DATASET,
        /**
         * Has next obs last call.
         */
        HAS_NEXT_OBS,
        /**
         * Has next series last call.
         */
        HAS_NEXT_SERIES,
        /**
         * Next obs last call.
         */
        NEXT_OBS,
        /**
         * Next series last call.
         */
        NEXT_SERIES;
    }

}
