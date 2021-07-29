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
package org.sdmxsource.sdmx.dataparser.manager.impl;

import org.sdmxsource.sdmx.api.constants.*;
import org.sdmxsource.sdmx.api.engine.DataReaderEngine;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.exception.SdmxSyntaxException;
import org.sdmxsource.sdmx.api.model.data.DataFormat;
import org.sdmxsource.sdmx.api.model.data.KeyValue;
import org.sdmxsource.sdmx.api.util.ReadableDataLocation;
import org.sdmxsource.sdmx.dataparser.engine.FixedConceptEngine;
import org.sdmxsource.sdmx.dataparser.engine.ReportedDateEngine;
import org.sdmxsource.sdmx.dataparser.engine.impl.FixedConceptEngineImpl;
import org.sdmxsource.sdmx.dataparser.engine.impl.ReportedDateEngineImpl;
import org.sdmxsource.sdmx.dataparser.engine.reader.csv.StreamCsvDataReaderEngine;
import org.sdmxsource.sdmx.dataparser.manager.DataInformationManager;
import org.sdmxsource.sdmx.dataparser.model.DataInformation;
import org.sdmxsource.sdmx.dataparser.model.JsonReader;
import org.sdmxsource.sdmx.sdmxbeans.model.data.SdmxDataFormat;
import org.sdmxsource.sdmx.util.sdmx.SdmxMessageUtil;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;


/**
 * The type Data information manager.
 */
public class DataInformationManagerImpl implements DataInformationManager {

    private final ReportedDateEngine reportedDateEngine;
    private final FixedConceptEngine fixedConceptEngine;

    /**
     * Instantiates a new Data information manager.
     */
    public DataInformationManagerImpl() {
        this(null, null);
    }

    /**
     * Instantiates a new Data information manager.
     *
     * @param reportedDateEngine the reported date engine
     * @param fixedConceptEngine the fixed concept engine
     */
    public DataInformationManagerImpl(
            ReportedDateEngine reportedDateEngine,
            FixedConceptEngine fixedConceptEngine) {
        this.reportedDateEngine = reportedDateEngine != null ? reportedDateEngine : new ReportedDateEngineImpl();
        this.fixedConceptEngine = fixedConceptEngine != null ? fixedConceptEngine : new FixedConceptEngineImpl();
    }

    @Override
    public Map<TIME_FORMAT, List<String>> getAllReportedDates(DataReaderEngine dataReaderEngine) {
        return reportedDateEngine.getAllReportedDates(dataReaderEngine);
    }


    @Override
    public List<KeyValue> getFixedConcepts(DataReaderEngine dre, boolean includeObs, boolean includeAttributes) {
        return fixedConceptEngine.getFixedConcepts(dre, includeObs, includeAttributes);
    }

    @Override
    public DataInformation getDataInformation(DataReaderEngine dre) {
        return new DataInformation(dre);
    }


    @Override
    public DataFormat getDataType(ReadableDataLocation sourceData) {
        try {
            SdmxException.disableExceptionTrace(true);
            try (BufferedReader br = new BufferedReader(new InputStreamReader(sourceData.getInputStream()))) {
                char[] firstPortion = new char[StreamCsvDataReaderEngine.DATAFLOW_KEY.length() * 2 + 2];
                br.read(firstPortion, 0, firstPortion.length);
                String str = new String(firstPortion);
                //Check CSV format
                if (str.contains(StreamCsvDataReaderEngine.DATAFLOW_KEY)) {
                    return new SdmxDataFormat(DATA_TYPE.CSV);
                }
            } catch (Throwable th) {
            }

            try {
                //Try and read it as a JSON dataset
                JsonReader reader = new JsonReader(sourceData);
                try {
                    reader.moveNext();
                    return new SdmxDataFormat(DATA_TYPE.SDMXJSON);
                } finally {
                    reader.closeParser();
                }
            } catch (Throwable th) {
            }

            if (SdmxMessageUtil.getMessageType(sourceData) == MESSAGE_TYPE.SDMX_EDI) {
                return new SdmxDataFormat(DATA_TYPE.EDI_TS);
            }
            if (SdmxMessageUtil.getMessageType(sourceData) == MESSAGE_TYPE.ERROR) {
                SdmxMessageUtil.parseSdmxErrorMessage(sourceData);
            }
            InputStream stream = sourceData.getInputStream();
            try {
                XMLInputFactory factory = XMLInputFactory.newInstance();
                XMLStreamReader parser = factory.createXMLStreamReader(stream, "UTF-8");
                String rootNode = null;
                while (parser.hasNext()) {
                    int event = parser.next();
                    if (event == XMLStreamConstants.START_ELEMENT) {
                        rootNode = parser.getLocalName();
                        for (int i = 0; i < parser.getNamespaceCount(); i++) {
                            String ns = parser.getNamespaceURI(i);
                            if (SdmxConstants.getNamespacesV1().contains(ns)) {
                                if (rootNode.equals(SdmxConstants.GENERIC_DATA_ROOT_NODE)) {
                                    return new SdmxDataFormat(DATA_TYPE.GENERIC_1_0);
                                } else if (rootNode.equals(SdmxConstants.UTILITY_DATA_ROOT_NODE)) {
                                    return new SdmxDataFormat(DATA_TYPE.UTILITY_1_0);
                                } else if (rootNode.equals(SdmxConstants.COMPACT_DATA_ROOT_NODE)) {
                                    return new SdmxDataFormat(DATA_TYPE.COMPACT_1_0);
                                } else if (rootNode.equals(SdmxConstants.CROSS_SECTIONAL_DATA_ROOT_NODE)) {
                                    return new SdmxDataFormat(DATA_TYPE.CROSS_SECTIONAL_1_0);
                                } else if (rootNode.equals(SdmxConstants.MESSAGE_GROUP_ROOT_NODE)) {
                                    BASE_DATA_FORMAT dataForamt = getMessageGroupDataFormat(parser);
                                    if (dataForamt != null) {
                                        switch (dataForamt) {
                                            case COMPACT:
                                                return new SdmxDataFormat(DATA_TYPE.MESSAGE_GROUP_1_0_COMPACT);
                                            case GENERIC:
                                                return new SdmxDataFormat(DATA_TYPE.MESSAGE_GROUP_1_0_GENERIC);
                                            case UTILITY:
                                                return new SdmxDataFormat(DATA_TYPE.MESSAGE_GROUP_1_0_UTILITY);
                                        }
                                    }
                                    throw new SdmxSyntaxException("Unknown Message Group Format");
                                } else {
                                    throw new SdmxSyntaxException("Unexpected root node '" + rootNode + "'");
                                }

                            }
                            if (SdmxConstants.getNamespacesV2().contains(ns)) {
                                if (rootNode.equals(SdmxConstants.GENERIC_DATA_ROOT_NODE)) {
                                    return new SdmxDataFormat(DATA_TYPE.GENERIC_2_0);
                                } else if (rootNode.equals(SdmxConstants.UTILITY_DATA_ROOT_NODE)) {
                                    return new SdmxDataFormat(DATA_TYPE.UTILITY_2_0);
                                } else if (rootNode.equals(SdmxConstants.COMPACT_DATA_ROOT_NODE)) {
                                    return new SdmxDataFormat(DATA_TYPE.COMPACT_2_0);
                                } else if (rootNode.equals(SdmxConstants.CROSS_SECTIONAL_DATA_ROOT_NODE)) {
                                    return new SdmxDataFormat(DATA_TYPE.CROSS_SECTIONAL_2_0);
                                } else if (rootNode.equals(SdmxConstants.MESSAGE_GROUP_ROOT_NODE)) {
                                    BASE_DATA_FORMAT dataForamt = getMessageGroupDataFormat(parser);
                                    if (dataForamt != null) {
                                        switch (dataForamt) {
                                            case COMPACT:
                                                return new SdmxDataFormat(DATA_TYPE.MESSAGE_GROUP_2_0_COMPACT);
                                            case GENERIC:
                                                return new SdmxDataFormat(DATA_TYPE.MESSAGE_GROUP_2_0_GENERIC);
                                            case UTILITY:
                                                return new SdmxDataFormat(DATA_TYPE.MESSAGE_GROUP_2_0_UTILITY);
                                        }
                                    }
                                    throw new SdmxSyntaxException("Unknown Message Group Format");
                                } else {
                                    throw new SdmxSyntaxException("Unexpected root node '" + rootNode + "'");
                                }
                            }
                            if (SdmxConstants.getNamespacesV2_1().contains(ns)) {
                                if (rootNode.equals(SdmxConstants.STRUCTURE_SPECIFIC_TIME_SERIES_DATA)
                                        || rootNode.equals(SdmxConstants.STRUCTURE_SPECIFIC_DATA)) {
                                    return new SdmxDataFormat(DATA_TYPE.COMPACT_2_1);
                                } else if (rootNode.equals(SdmxConstants.GENERIC_TIME_SERIES_DATA_ROOT_NODE) ||
                                        rootNode.equals(SdmxConstants.GENERIC_DATA_ROOT_NODE)) {
                                    return new SdmxDataFormat(DATA_TYPE.GENERIC_2_1);
                                } else {
                                    throw new SdmxSyntaxException("Unexpected root node '" + rootNode + "'");
                                }
                            }
                        }
                        throw new SdmxSyntaxException(ExceptionCode.XML_PARSE_EXCEPTION, "Can not determine data type " +
                                "unknown namespaces defined");
                    }
                }
                throw new SdmxSyntaxException(ExceptionCode.XML_PARSE_EXCEPTION,
                        "Unknown Data type with root node :" + rootNode);
            } catch (javax.xml.stream.XMLStreamException e) {
                throw new SdmxSyntaxException(e, ExceptionCode.XML_PARSE_EXCEPTION);
            } finally {
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        } finally {
            SdmxException.disableExceptionTrace(false);
        }
    }

    private BASE_DATA_FORMAT getMessageGroupDataFormat(XMLStreamReader parser) throws XMLStreamException {
        while (parser.hasNext()) {
            int event = parser.next();
            if (event == XMLStreamConstants.START_ELEMENT) {
                String rootNode = parser.getLocalName();
                if (rootNode.equals("DataSet")) {
                    String namespaceURI = parser.getNamespaceURI();
                    if (SdmxConstants.GENERIC_NS_1_0.equals(namespaceURI) || SdmxConstants.GENERIC_NS_2_0.equals(namespaceURI)) {
                        return BASE_DATA_FORMAT.GENERIC;
                    }
                    if (SdmxConstants.UTILITY_NS_1_0.equals(namespaceURI) || SdmxConstants.UTILITY_NS_2_0.equals(namespaceURI)) {
                        return BASE_DATA_FORMAT.UTILITY;
                    }
                    //The Compact Messages start with Namespace URN, but to be lienient also check for compact namespace
                    if (SdmxConstants.COMPACT_NS_1_0.equals(namespaceURI) || SdmxConstants.COMPACT_NS_2_0.equals(namespaceURI) || namespaceURI.startsWith("urn")) {
                        return BASE_DATA_FORMAT.COMPACT;
                    }
                    return null;
                }
            }
        }
        return null;
    }

    @Override
    public String getTargetNamespace(ReadableDataLocation sourceData) {
        InputStream stream = sourceData.getInputStream();
        try {
            return jumpToNode(stream, "DataSet", null, true).getNamespaceURI();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


    private XMLStreamReader jumpToNode(InputStream stream, String findNodeName, String doNotProcessPastNodeName, boolean throwException) {
        String nodeName;
        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLStreamReader parser = factory.createXMLStreamReader(stream, "UTF-8");
            String rootNode = null;
            while (parser.hasNext()) {
                int event = parser.next();
                if (event == XMLStreamConstants.START_ELEMENT) {
                    rootNode = parser.getLocalName();
                    if (rootNode.equals(findNodeName)) {
                        return parser;
                    }
                }
                if (event == XMLStreamConstants.END_ELEMENT) {
                    nodeName = parser.getLocalName();
                    if (doNotProcessPastNodeName != null) {
                        if (nodeName.equals(doNotProcessPastNodeName)) {
                            if (throwException) {
                                throw new SdmxSyntaxException("Could not find element: " + nodeName);
                            }
                            return null;
                        }
                    }
                }
            }
            throw new SdmxSyntaxException("Could not find element: " + findNodeName);
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }
}
