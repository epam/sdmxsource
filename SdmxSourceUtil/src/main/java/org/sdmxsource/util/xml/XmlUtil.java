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
package org.sdmxsource.util.xml;

import org.dom4j.io.SAXReader;
import org.sdmxsource.sdmx.api.util.ReadableDataLocation;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringReader;
import java.io.StringWriter;


/**
 * Utility class providing helper methods to aid with XML.
 */
public class XmlUtil {

    /**
     * Returns true if the specified ReadableDataLocation contains valid XML.
     *
     * @param sourceData The ReadableDataLocation to test
     * @return true if the specified  ReadableDataLocation contains valid XML.
     */
    public static boolean isXML(ReadableDataLocation sourceData) {
        SAXReader reader = new SAXReader();
        reader.setValidation(false);
        try {
            reader.read(sourceData.getInputStream());
        } catch (Throwable th) {
            return false;
        }
        return true;
    }

    /**
     * Formats the specified XML String so it has indentation
     *
     * @param unformattedXml A string representation of some XML
     * @return a formatted version of the input XML.
     */
    public static String formatXml(String unformattedXml) {
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            StreamResult result = new StreamResult(new StringWriter());

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(unformattedXml));
            Document doc = db.parse(is);

            DOMSource source = new DOMSource(doc);
            transformer.transform(source, result);
            return result.getWriter().toString();
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }
}
