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
package org.sdmxsource.sdmx.util.sdmx;

import org.sdmxsource.sdmx.api.constants.*;
import org.sdmxsource.sdmx.api.exception.*;
import org.sdmxsource.sdmx.api.util.ReadableDataLocation;
import org.sdmxsource.sdmx.util.exception.ParseException;
import org.sdmxsource.sdmx.util.stax.StaxUtil;
import org.sdmxsource.util.io.StreamUtil;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


/**
 * The type Sdmx message util.
 */
public class SdmxMessageUtil {


    /**
     * Parse sdmx error message.
     *
     * @param dataLocation the data location
     */
    public static void parseSdmxErrorMessage(ReadableDataLocation dataLocation) {
        InputStream stream = dataLocation.getInputStream();
        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLStreamReader parser = factory.createXMLStreamReader(stream);
            String nodeName;
            String code = null;
            SDMX_ERROR_CODE errorCode = null;
            while (parser.hasNext()) {
                int event = parser.next();
                if (event == XMLStreamConstants.START_ELEMENT) {
                    nodeName = parser.getLocalName();
                    if (nodeName.equals("ErrorMessage")) {
                        code = parser.getAttributeValue(null, "code");
                        errorCode = SDMX_ERROR_CODE.parseClientCode(Integer.parseInt(code));
                    } else if (nodeName.equals("Text")) {
                        if (errorCode == null) {
                            errorCode = SDMX_ERROR_CODE.INTERNAL_SERVER_ERROR;
                        }
                        switch (errorCode) {
                            case NO_RESULTS_FOUND:
                                throw new SdmxNoResultsException(parser.getElementText());
                            case NOT_IMPLEMENTED:
                                throw new SdmxNotImplementedException(parser.getElementText());
                            case SEMANTIC_ERROR:
                                throw new SdmxSemmanticException(parser.getElementText());
                            case UNAUTHORISED:
                                throw new SdmxUnauthorisedException(parser.getElementText());
                            case SERVICE_UNAVAILABLE:
                                throw new SdmxServiceUnavailableException(parser.getElementText());
                            case SYNTAX_ERROR:
                                throw new SdmxSyntaxException(parser.getElementText());
                            case RESPONSE_SIZE_EXCEEDS_SERVICE_LIMIT:
                                throw new SdmxResponseSizeExceedsLimitException(parser.getElementText());
                            case RESPONSE_TOO_LARGE:
                                throw new SdmxResponseTooLargeException(parser.getElementText());
                            case INTERNAL_SERVER_ERROR:
                                throw new SdmxInternalServerException(parser.getElementText());
                            default:
                                throw new SdmxException(parser.getElementText(), errorCode);
                        }
                    }
                }
            }
        } catch (XMLStreamException e) {
            throw new SdmxSyntaxException(ExceptionCode.XML_PARSE_EXCEPTION, e);
        } finally {
            StreamUtil.closeStream(stream);
        }
    }

    /**
     * Returns the version of the schema that the document stored at this URI points to.
     * <br>
     * The URI is inferred by the namespaces declared in the root element of the document.
     * <br>
     * This will throw an error if there is no way of determining the schema version
     *
     * @param sourceData the source data
     * @return schema version
     * @throws ParseException the parse exception
     */
    public static SDMX_SCHEMA getSchemaVersion(ReadableDataLocation sourceData) throws ParseException {
        if (isEDI(sourceData)) {
            return SDMX_SCHEMA.EDI;
        }
        if (isECV(sourceData)) {
            return SDMX_SCHEMA.ECV;
        }
        //		if(!XmlUtil.isXML(uri)) {
        //			throw new ParseException(ExceptionCode.PARSE_ERROR_NOT_XML_OR_EDI);
        //		}
        InputStream stream = sourceData.getInputStream();
        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLStreamReader parser = factory.createXMLStreamReader(stream);
            while (parser.hasNext()) {
                int event = parser.next();
                if (event == XMLStreamConstants.START_ELEMENT) {
                    for (int i = 0; i < parser.getNamespaceCount(); i++) {
                        String ns = parser.getNamespaceURI(i);
                        if (SdmxConstants.getNamespacesV1().contains(ns)) {
                            return SDMX_SCHEMA.VERSION_ONE;
                        }
                        if (SdmxConstants.getNamespacesV2().contains(ns)) {
                            return SDMX_SCHEMA.VERSION_TWO;
                        }
                        if (SdmxConstants.getNamespacesV2_1().contains(ns)) {
                            return SDMX_SCHEMA.VERSION_TWO_POINT_ONE;
                        }
                    }
                    throw new SdmxSyntaxException("Can not get Scheme Version from SDMX message.  Unable to determine structure type from Namespaces- please ensure this is a valid SDMX document");
                }
            }
            throw new SdmxSyntaxException(ExceptionCode.XML_PARSE_EXCEPTION, "No root node found");
        } catch (XMLStreamException e) {
            throw new SdmxSyntaxException(ExceptionCode.XML_PARSE_EXCEPTION, e);
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


    /**
     * Is ecv delete boolean.
     *
     * @param dataLocation the data location
     * @return the boolean
     */
    public static boolean isECVDelete(ReadableDataLocation dataLocation) {
        InputStream stream = dataLocation.getInputStream();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(stream));
            char[] firstPortion = new char[100];
            br.read(firstPortion, 0, 10);
            String str = new String(firstPortion);
            return str.toUpperCase().startsWith("ECV-");
        } catch (IOException e) {
            throw new IllegalArgumentException("Error while trying to read dataLocation:" + dataLocation);
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

    private static boolean isEDI(ReadableDataLocation sourceData) {
        InputStream stream = sourceData.getInputStream();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(stream));
            char[] firstPortion = new char[100];
            br.read(firstPortion, 0, 100);
            String str = new String(firstPortion);
            return str.toUpperCase().startsWith("UNA");
        } catch (IOException e) {
            throw new IllegalArgumentException("Error while trying to read source:" + sourceData);
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

    private static boolean isECV(ReadableDataLocation sourceData) {
        InputStream stream = sourceData.getInputStream();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(stream));
            char[] firstPortion = new char[100];
            br.read(firstPortion, 0, 100);
            String str = new String(firstPortion);
            return str.toUpperCase().startsWith("ECV");
        } catch (IOException e) {
            throw new IllegalArgumentException("Error while trying to read source:" + sourceData);
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

    /**
     * Returns MESSAGE_TYPE for the URI.  This is determined from the root node.
     *
     * @param sourceData the source data
     * @return MESSAGE_TYPE message type
     */
    public static MESSAGE_TYPE getMessageType(ReadableDataLocation sourceData) {
        if (isEDI(sourceData)) {
            return MESSAGE_TYPE.SDMX_EDI;
        }
        //		if(!XmlUtil.isXML(uri)) {
        //			throw new ParseException(ExceptionCode.PARSE_ERROR_NOT_XML_OR_EDI);
        //		}
        InputStream stream = sourceData.getInputStream();
        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLStreamReader parser = factory.createXMLStreamReader(stream);
            String rootNode = null;
            while (parser.hasNext()) {
                int event = parser.next();
                if (event == XMLStreamConstants.START_ELEMENT) {
                    rootNode = parser.getLocalName();
                    try {
                        return MESSAGE_TYPE.parseString(rootNode);
                    } catch (Throwable th) {
                        throw new SdmxSyntaxException(th, ExceptionCode.PARSE_ERROR_NOT_SDMX);
                    }
                }
            }
            throw new IllegalArgumentException("No root node found");
        } catch (XMLStreamException th) {
            throw new RuntimeException(th);
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

    /**
     * Returns the registry message type - this will ony work if the XML at the
     * URI is a RegistryInterface message.
     * <p>
     * The registry message type may be one of the following:
     * <ul>
     *  <li>SUBMIT STRUCTURE REQUEST</li>
     * 	<li>SUBMIT PROVISION REQUEST</li>
     * 	<li>SUBMIT REGISTRATION REQUEST</li>
     * 	<li>SUBMIT SUBSCRIPTION REQUEST</li>
     * 	<li>QUERY STRUCTURE REQUEST</li>
     * 	<li>QUERY PROVISION REQUEST</li>
     * 	<li>QUERY REGISTRATION REQUEST</li>
     * 	<li>SUBMIT STRUCTURE RESPONSE</li>
     * 	<li>SUBMIT PROVISION RESPONSE</li>
     * 	<li>SUBMIT REGISTRATION RESPONSE</li>
     * 	<li>QUERY STRUCTURE RESPONSE</li>
     * 	<li>QUERY PROVISION RESPONSE</li>
     * 	<li>QUERY REGISTRATION RESPONSE</li>
     * 	<li>SUBMIT SUBSCRIPTION RESPONSE</li>
     * 	<li>NOTIFY REGISTRY EVENT</li>
     * </ul>
     *
     * @param sourceData the source data
     * @return registry message type
     */
    public static REGISTRY_MESSAGE_TYPE getRegistryMessageType(ReadableDataLocation sourceData) {
        //		if(!XmlUtil.isXML(uri)) {
        //			throw new ParseException(ExceptionCode.PARSE_ERROR_NOT_XML);
        //		}
        InputStream stream = sourceData.getInputStream();
        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLStreamReader parser = factory.createXMLStreamReader(stream);
            boolean skippedHeader = StaxUtil.skipToEndNode(parser, "Header");
            if (!skippedHeader) {
                throw new IllegalArgumentException("Header not found");
            }
            String rootNode = null;
            while (parser.hasNext()) {
                int event = parser.next();
                if (event == XMLStreamConstants.START_ELEMENT) {
                    rootNode = parser.getLocalName();
                    return REGISTRY_MESSAGE_TYPE.getMessageType(rootNode);
                }
            }
            throw new IllegalArgumentException("No nodes found after header");
        } catch (XMLStreamException th) {
            throw new RuntimeException(th);
        } finally {
            StreamUtil.closeStream(stream);
        }
    }

    /**
     * Determines the query message types for an input SDMX-ML query message
     *
     * @param sourceData the source data
     * @return for v2.1 it will be always one query message due to schema constraints.
     */
    public static List<QUERY_MESSAGE_TYPE> getQueryMessageTypes(ReadableDataLocation sourceData) {
        MESSAGE_TYPE messageType = getMessageType(sourceData);
        if (messageType != MESSAGE_TYPE.QUERY) {
            throw new IllegalArgumentException("Can not determine query type, as message is of type : " + messageType.toString());
        }
        List<QUERY_MESSAGE_TYPE> returnList = new ArrayList<QUERY_MESSAGE_TYPE>();

        InputStream stream = sourceData.getInputStream();
        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLStreamReader parser = factory.createXMLStreamReader(stream);

            // Determine by the root node if it is a v2.1 DataQuery
            int rootStartEvent = parser.nextTag();
            if (rootStartEvent != XMLStreamConstants.START_ELEMENT) {
                throw new IllegalArgumentException("Could not get root start element!");
            }
            String rootNode = parser.getLocalName();

            if (rootNode.equals(QUERY_MESSAGE_TYPE.GENERIC_DATA_QUERY.getNodeName()) || rootNode.equals(QUERY_MESSAGE_TYPE.STRUCTURE_SPECIFIC_DATA_QUERY.getNodeName()) ||
                    rootNode.equals(QUERY_MESSAGE_TYPE.GENERIC_TIME_SERIES_DATA_QUERY.getNodeName()) || rootNode.equals(QUERY_MESSAGE_TYPE.STRUCTURE_SPECIFIC_TIME_SERIES_DATA_QUERY.getNodeName())
                    || rootNode.equals(QUERY_MESSAGE_TYPE.GENERIC_METADATA_QUERY.getNodeName()) || rootNode.equals(QUERY_MESSAGE_TYPE.STRUCTURE_SPECIFIC_METADATA_QUERY.getNodeName())) { // don't like but checking with parsingString exception is worse. May be boolean isQueryMessageType() in enum

                returnList.add(QUERY_MESSAGE_TYPE.parseString(rootNode));
                return returnList;
            }

            // continue determine type by  Where elements after Query
            StaxUtil.skipToNode(parser, "Query");

            String nodeName = null;

            while (parser.hasNext()) {
                int event = parser.next();
                if (event == XMLStreamConstants.START_ELEMENT) {
                    //Child of Query
                    nodeName = parser.getLocalName();

                    if (nodeName.equals("ReturnDetails")) { //Skip details of 2.1 queries
                        StaxUtil.skipToEndNode(parser, "ReturnDetails");
                    } else {
                        returnList.add(QUERY_MESSAGE_TYPE.parseString(nodeName));
                        StaxUtil.skipToEndNode(parser, nodeName);
                    }
                } else if (event == XMLStreamConstants.END_ELEMENT) {
                    nodeName = parser.getLocalName();
                    if (nodeName.equals("Query")) {
                        break;
                    }
                }
            }
            return returnList;
        } catch (XMLStreamException th) {
            throw new RuntimeException(th);
        }
    }

    /**
     * Retruns the dataset action
     *
     * @param sourceData the source data
     * @return data set action
     */
    public static DATASET_ACTION getDataSetAction(ReadableDataLocation sourceData) {
        //		if(!XmlUtil.isXML(uri)) {
        //			throw new ParseException(ExceptionCode.PARSE_ERROR_NOT_XML);
        //		}
        MESSAGE_TYPE message = getMessageType(sourceData);
        boolean continueToSubmitStructureRequest = false;
        if (message == MESSAGE_TYPE.REGISTRY_INTERFACE) {
            REGISTRY_MESSAGE_TYPE registryMessageType = getRegistryMessageType(sourceData);
            if (registryMessageType == REGISTRY_MESSAGE_TYPE.SUBMIT_STRUCTURE_REQUEST) {
                continueToSubmitStructureRequest = true;
            }
        }
        InputStream stream = sourceData.getInputStream();
        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLStreamReader parser = factory.createXMLStreamReader(stream);
            String currentNode = null;
            while (parser.hasNext()) {
                int event = parser.next();
                if (event == XMLStreamConstants.START_ELEMENT) {
                    currentNode = parser.getLocalName();
                    if (currentNode.equals("DataSetAction")) {
                        String action = parser.getElementText();
                        if (action.equals("Append") || action.equals("Update")) {
                            return DATASET_ACTION.APPEND;
                        }
                        if (action.equals("Replace")) {
                            return DATASET_ACTION.REPLACE;
                        }
                        if (action.equals("Delete")) {
                            return DATASET_ACTION.DELETE;
                        }
                        if (action.equals("Information")) {
                            return DATASET_ACTION.INFORMATION;
                        }
                    }
                    if (currentNode.equals("SubmitStructureRequest")) {
                        String action = parser.getAttributeValue(null, "action");
                        if (action != null) {
                            return DATASET_ACTION.getAction(action);
                        }
                        return DATASET_ACTION.APPEND;
                    }
                } else if (event == XMLStreamConstants.END_ELEMENT) {
                    currentNode = parser.getLocalName();
                    if (currentNode.equals("Header")) {
                        if (!continueToSubmitStructureRequest) {
                            return DATASET_ACTION.APPEND;
                        }
                    }
                }
            }
            return DATASET_ACTION.APPEND;
        } catch (XMLStreamException th) {
            throw new RuntimeException(th);
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
}
