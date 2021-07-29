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
package org.sdmxsource.sdmx.util.stax;

import org.sdmxsource.sdmx.util.date.DateUtil;
import org.sdmxsource.util.ObjectUtil;

import javax.xml.stream.*;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Date;


/**
 * The type Stax util.
 */
public class StaxUtil {
    private static final String XML_NS = "http://www.w3.org/XML/1998/namespace";

    /**
     * Write header.
     *
     * @param writer   the writer
     * @param id       the id
     * @param senderId the sender id
     * @throws XMLStreamException the xml stream exception
     */
    public static void writeHeader(XMLStreamWriter writer, String id, String senderId) throws XMLStreamException {
        writer.writeStartElement("Header");
        //WRITE ID
        writer.writeStartElement("ID");
        writer.writeCharacters(id);
        writer.writeEndElement();
        //WRITE TEST
        writer.writeStartElement("Test");
        writer.writeCharacters("false");
        writer.writeEndElement();
        //WRITE PREPARED
        writer.writeStartElement("Prepared");
        writer.writeCharacters(DateUtil.formatDate(new Date()));
        writer.writeEndElement();
        //WRITE SENDER
        writer.writeStartElement("Sender");
        writer.writeAttribute("id", senderId);
        writer.writeEndElement();

        //END HEADER
        writer.writeEndElement();
    }

    /**
     * Copies the current node, namespaces and all attributes.  Does not copy text if deepCopy is false.
     *
     * @param parser             the parser
     * @param deepCopy           - copies all the children of the current node, copies all text
     * @param includeCurrentNode - if false will skip the current node and copy all children (only relevent if a deep copy - if false on a shallow copy, nothing will be copied)
     * @param includeNamespaces  the include namespaces
     * @param writers            the writers
     * @throws XMLStreamException - if the parser is not at position XMLStreamConstants.START_ELEMENT
     */
    public static void copyNode(XMLStreamReader parser, boolean deepCopy, boolean includeCurrentNode, boolean includeNamespaces, XMLStreamWriter... writers) throws XMLStreamException {
        if (writers == null || writers.length == 0) {
            throw new IllegalArgumentException("StxUtil.copyNode expects at least one XMLStreamWriter to copy to");
        }
        if (includeCurrentNode) {
            if (ObjectUtil.validString(parser.getNamespaceURI())) {
                for (XMLStreamWriter writer : writers) {
                    writer.writeStartElement(parser.getNamespaceURI(), parser.getLocalName());
                }
            } else {
                for (XMLStreamWriter writer : writers) {
                    writer.writeStartElement(parser.getLocalName());
                }
            }
            if (includeNamespaces) {
                for (int i = 0; i < parser.getNamespaceCount(); i++) {
                    String nsPrefix = parser.getNamespacePrefix(i);
                    String nsUri = parser.getNamespaceURI(i);
                    for (XMLStreamWriter writer : writers) {
                        writer.writeNamespace(nsPrefix, nsUri);
                    }
                }
            }
            for (int i = 0; i < parser.getAttributeCount(); i++) {
                String attNs = parser.getAttributeNamespace(i);
                String attName = parser.getAttributeLocalName(i);
                String attVal = parser.getAttributeValue(i);
                if (ObjectUtil.validString(attNs)) {
                    if (attNs.equals("http://www.w3.org/XML/1998/namespace")) {
                        for (XMLStreamWriter writer : writers) {
                            writer.writeAttribute("xml", attNs, attName, attVal);
                        }
                    } else {
                        for (XMLStreamWriter writer : writers) {
                            writer.writeAttribute(attNs, attName, attVal);
                        }
                    }
                } else {
                    for (XMLStreamWriter writer : writers) {
                        writer.writeAttribute(attName, attVal);
                    }
                }
            }
        }
        if (deepCopy) {
            while (parser.hasNext()) {
                int event = parser.next();
                if (event == XMLStreamConstants.CHARACTERS) {
                    for (XMLStreamWriter writer : writers) {
                        writer.writeCharacters(parser.getText());
                    }
                } else if (event == XMLStreamConstants.START_ELEMENT) {
                    copyNode(parser, true, true, includeNamespaces, writers);
                } else if (event == XMLStreamConstants.END_ELEMENT) {
                    for (XMLStreamWriter writer : writers) {
                        writer.writeEndElement();
                    }
                    return;
                }
            }
        }
    }

    /**
     * Moves the parser position forward to the point after this node and all children of this node
     *
     * @param parser the parser
     * @throws XMLStreamException the xml stream exception
     */
    public static void skipNode(XMLStreamReader parser) throws XMLStreamException {
        while (parser.hasNext()) {
            int event = parser.next();
            if (event == XMLStreamConstants.START_ELEMENT) {
                skipNode(parser);
            } else if (event == XMLStreamConstants.END_ELEMENT) {
                return;
            }
        }
    }

    /**
     * Moves the parser position forward to the next instance of the node with the given name
     *
     * @param parser   the parser
     * @param nodeName the node name
     * @return the boolean
     * @throws XMLStreamException the xml stream exception
     */
    public static boolean skipToNode(XMLStreamReader parser, String nodeName) throws XMLStreamException {
        while (parser.hasNext()) {
            int event = parser.next();
            if (event == XMLStreamConstants.START_ELEMENT) {
                if (parser.getLocalName().equals(nodeName)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Jump to node boolean.
     *
     * @param parser                   the parser
     * @param findNodeName             the find node name
     * @param doNotProcessPastNodeName the do not process past node name
     * @return the boolean
     * @throws XMLStreamException the xml stream exception
     */
    public static boolean jumpToNode(XMLStreamReader parser, String findNodeName, String doNotProcessPastNodeName) throws XMLStreamException {
        String nodeName;
        while (parser.hasNext()) {
            int event = parser.next();
            if (event == XMLStreamConstants.START_ELEMENT) {
                nodeName = parser.getLocalName();
                if (nodeName.equals(findNodeName)) {
                    return true;
                }
            }
            if (event == XMLStreamConstants.END_ELEMENT) {
                nodeName = parser.getLocalName();
                if (doNotProcessPastNodeName != null) {
                    if (nodeName.equals(doNotProcessPastNodeName)) {
                        return false;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Moves the parser position forward to the next instance of the end node given name
     *
     * @param parser   the parser
     * @param nodeName the node name
     * @return the boolean
     * @throws XMLStreamException the xml stream exception
     */
    public static boolean skipToEndNode(XMLStreamReader parser, String nodeName) throws XMLStreamException {
        while (parser.hasNext()) {
            int event = parser.next();
            if (event == XMLStreamConstants.END_ELEMENT) {
                if (parser.getLocalName().equals(nodeName)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns the current node, all attributes and children as a string
     *
     * @param parser the parser
     * @return string string
     * @throws XMLStreamException the xml stream exception
     */
    public static String parseString(XMLStreamReader parser) throws XMLStreamException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        XMLOutputFactory xmlOutputfactory = XMLOutputFactory.newInstance();
        XMLStreamWriter writer = xmlOutputfactory.createXMLStreamWriter(out);
        writer.writeStartDocument();
        boolean started = false;
        int event = XMLStreamConstants.START_ELEMENT;
        String endElement = parser.getLocalName();
        int level = 0;
        while (true) {
            switch (event) {
                case XMLStreamConstants.START_ELEMENT:
                    if (parser.getLocalName().equals(endElement)) {
                        level++;
                    }
                    writer.writeStartElement(parser.getNamespaceURI(), parser.getLocalName());
                    if (!started) {
                        writer.writeNamespace("xml", XML_NS);
                        started = true;
                    }
                    for (int i = 0; i < parser.getAttributeCount(); i++) {
                        if (parser.getAttributeNamespace(i) == null) {
                            writer.writeAttribute(parser.getAttributeLocalName(i), parser.getAttributeValue(i));
                        } else {
                            writer.writeAttribute(parser.getAttributeNamespace(i), parser.getAttributeLocalName(i), parser.getAttributeValue(i));
                        }
                    }
                    break;

                case XMLStreamConstants.CHARACTERS:
                    writer.writeCharacters(parser.getText());
                    break;

                case XMLStreamConstants.END_ELEMENT:
                    writer.writeEndElement();
                    if (parser.getLocalName().equals(endElement)) {
                        if (level == 1) {
                            writer.flush();
                            writer.close();
                            return out.toString();
                        }
                        level--;
                    }
                    break;
            }
            event = parser.next();
        }
    }

    /**
     * Checks if the XML in the first input stream is the same as the XML in the second input stream, throws an exception if they differ
     *
     * @param xmlOne          an inputStream
     * @param xmlTwo          an inputStream
     * @param ignoreAttrValue if specified any attributes with this value will not be tested against the other stream
     * @param ignoreNodes     an optional list of nodes which shouldn't be compared.
     * @throws Exception the exception
     */
    public static void isSameXML(InputStream xmlOne, InputStream xmlTwo, String ignoreAttrValue, String... ignoreNodes) throws Exception {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader parser1 = factory.createXMLStreamReader(xmlOne, "UTF-8");

        XMLStreamReader parser2 = factory.createXMLStreamReader(xmlTwo, "UTF-8");
        String nodeA = null;
        String nodeB = null;

        int element = 0;
        int a = -1;
        int b = -1;

        outer:
        while (parser1.hasNext()) {
            a = parser1.next();
            b = parser2.next();
            while (a == XMLStreamConstants.SPACE) {
                a = parser1.next();
            }
            while (b == XMLStreamConstants.SPACE) {
                b = parser2.next();
            }
            if (parser1.isCharacters()) {
                if (ObjectUtil.validString(parser1.getText())) {
                    if (!parser2.isCharacters()) {
                        throw new IllegalArgumentException("Text Differs on Node:" + nodeA);
                    }
                    if (!parser1.getText().equals(parser2.getText())) {
                        throw new IllegalArgumentException("Text Differs on Node:" + nodeA + "\nExpected:" + parser1.getText() + "\nActual:" + parser2.getText());
                    }
                } else {
                    a = parser1.next();
                    if (parser2.isCharacters() && !ObjectUtil.validString(parser2.getText())) {
                        b = parser2.next();
                    }
                }
            } else if (parser2.isCharacters()) {

                if (ObjectUtil.validString(parser2.getText())) {
                    throw new IllegalArgumentException("Text Differs on Node:" + nodeA + " : " + parser2.getText());
                }
                b = parser2.next();
            }

            if (a == XMLStreamConstants.END_ELEMENT) {
                if (b != a) {
                    nodeA = parser1.getLocalName();
                    throw new IllegalArgumentException("Input A is ending XML element: " + nodeA + " whilst input B is not");
                }
            } else if (a == XMLStreamConstants.START_ELEMENT) {
                element++;
                nodeA = parser1.getLocalName();
                if (b != XMLStreamConstants.START_ELEMENT) {
                    while (parser2.hasNext()) {
                        b = parser2.next();
                        if (b == XMLStreamConstants.START_ELEMENT) {
                            nodeB = parser2.getLocalName();
                            break;
                        }
                    }
                }
                if (b != XMLStreamConstants.START_ELEMENT) {
                    throw new IllegalArgumentException("Parser B end of document, Parser A on node: " + nodeA);
                }
                nodeB = parser2.getLocalName();


                if (!nodeA.equals(nodeB)) {
                    throw new IllegalArgumentException("XML NODES DIFFER: " + nodeA + "," + nodeB);
                }
                for (String ignoreNode : ignoreNodes) {
                    if (nodeA.equals(ignoreNode)) {
                        StaxUtil.skipNode(parser1);
                        StaxUtil.skipNode(parser2);
                        continue outer;
                    }
                }


                if (parser1.getNamespaceCount() != parser2.getNamespaceCount()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Namespace count for parser 1 : " + parser1.getNamespaceCount() + "\n");
                    for (int i = 0; i < parser1.getNamespaceCount(); i++) {
                        sb.append("Parser 1 Namespace #" + (i + 1) + ":  " + parser1.getNamespaceURI(i) + "\n");
                    }
                    sb.append("Namespace count for parser 2 : " + parser2.getNamespaceCount() + "\n");
                    for (int i = 0; i < parser2.getNamespaceCount(); i++) {
                        sb.append("Parser 2 Namespace #" + (i + 1) + ":  " + parser2.getNamespaceURI(i) + "\n");
                    }
                    throw new IllegalArgumentException("Namespace count differs on node:" + nodeA + "\n" + sb.toString());
                }

                for (int i = 0; i < parser1.getNamespaceCount(); i++) {
                    boolean found = false;
                    String nameSpaceURI = parser1.getNamespaceURI(i);
                    for (int j = 0; j < parser2.getNamespaceCount(); j++) {
                        if (parser2.getNamespaceURI(j).equals(nameSpaceURI)) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        throw new IllegalArgumentException("Namespace not found " + nameSpaceURI);
                    }
                }
                if (parser1.getAttributeCount() != parser2.getAttributeCount()) {
                    throw new IllegalArgumentException("Attribute count differs on node:" + nodeA);
                }

                for (int i = 0; i < parser1.getAttributeCount(); i++) {
                    boolean found = false;
                    String attributeLocalName = parser1.getAttributeLocalName(i);
                    String attributeNameSpace = parser1.getAttributeNamespace(i);
                    String attributeValue = parser1.getAttributeValue(i);
                    int j;
                    for (j = 0; j < parser2.getAttributeCount(); j++) {
                        if (parser2.getAttributeLocalName(j).equals(attributeLocalName)) {
                            if (attributeNameSpace == null && parser2.getAttributeNamespace(j) == null) {
                                //THIS IS OKAY
                            } else {
                                if (attributeNameSpace == null && parser2.getAttributeNamespace(j) != null) {
                                    throw new IllegalArgumentException("ATTRIBUTE NAMESPACE DIFFERS " + parser2.getAttributeNamespace(i) + "," + attributeNameSpace);
                                }
                                if (attributeNameSpace != null && parser2.getAttributeNamespace(j) == null) {
                                    throw new IllegalArgumentException("ATTRIBUTE NAMESPACE DIFFERS " + parser2.getAttributeNamespace(i) + "," + attributeNameSpace);
                                }
                                if (!parser2.getAttributeNamespace(j).equals(attributeNameSpace)) {
                                    throw new IllegalArgumentException("ATTRIBUTE NAMESPACE DIFFERS " + parser2.getAttributeNamespace(i) + "," + attributeNameSpace);
                                }
                            }


                            found = true;
                            break;
                        }

                    }
                    if (!found) {
                        throw new IllegalArgumentException("ATTRIBUTE NOT FOUND ON NODE '" + nodeA + "' : " + attributeLocalName);
                    }

                    // Ensure the attribute to be tested is not an ignore value then test it
                    if (!attributeValue.equals(ignoreAttrValue)) {
                        if (!parser2.getAttributeValue(j).equals(attributeValue)) {
                            String line_separator = System.getProperty("line.separator");
                            throw new IllegalArgumentException("ATTRIBUTE VALUE DIFFERS on: " + attributeLocalName + " - Node: " + element + line_separator
                                    + attributeValue + line_separator
                                    + parser2.getAttributeValue(i));
                        }
                    }
                }
            }
        }
    }

}
