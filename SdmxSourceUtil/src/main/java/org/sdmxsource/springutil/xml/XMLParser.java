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
package org.sdmxsource.springutil.xml;

import org.apache.log4j.Logger;
import org.sdmxsource.sdmx.api.constants.SDMX_SCHEMA;
import org.sdmxsource.sdmx.api.exception.SdmxSyntaxException;
import org.sdmxsource.sdmx.api.util.ReadableDataLocation;
import org.sdmxsource.sdmx.util.exception.SchemaValidationException;
import org.sdmxsource.util.io.StreamUtil;
import org.springframework.core.io.ClassPathResource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The type Xml parser.
 */
public class XMLParser {
    private static final Logger log = Logger.getLogger(XMLParser.class);
    private static final SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
    private static boolean enableValidation = true;
    private static Map<SDMX_SCHEMA, URI> schemaLocations = new HashMap<SDMX_SCHEMA, URI>();
    private static Map<SDMX_SCHEMA, Schema> schemas = new HashMap<SDMX_SCHEMA, Schema>();

    static {
        try {
            schemaLocations.put(SDMX_SCHEMA.VERSION_ONE, new ClassPathResource("/xsd/1_0/SDMXMessage.xsd").getURI());
            schemaLocations.put(SDMX_SCHEMA.VERSION_TWO, new ClassPathResource("/xsd/2_0/SDMXMessage.xsd").getURI());
            schemaLocations.put(SDMX_SCHEMA.VERSION_TWO_POINT_ONE, new ClassPathResource("/xsd/2_1/SDMXMessage.xsd").getURI());

            for (SDMX_SCHEMA schemaVersion : schemaLocations.keySet()) {
                URI schemaUri = schemaLocations.get(schemaVersion);
                StreamSource source = new StreamSource(schemaUri.toString());
                schemas.put(schemaVersion, schemaFactory.newSchema(source));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Validates the XML against the schema version.  Throws a validation exception if any schema errors are found.
     * <p>
     * <b>NOTE : </b>In order for this method to work, the schema location must be set, and be accessible via a uri.
     *
     * @param sourceData     the source data
     * @param schemaVersion  - the schema version to validate against
     * @param extraLocations - any extra schemas required for validation
     * @throws SdmxSyntaxException the sdmx syntax exception
     */
    public static void validateXML(ReadableDataLocation sourceData, SDMX_SCHEMA schemaVersion, ReadableDataLocation... extraLocations) throws SdmxSyntaxException {
        validateXML(sourceData.getInputStream(), schemaVersion, extraLocations);
    }

    /**
     * Validate xml.
     *
     * @param xml            the xml
     * @param schemaVersion  the schema version
     * @param extraLocations the extra locations
     * @throws SdmxSyntaxException the sdmx syntax exception
     */
    public static void validateXML(InputStream xml, SDMX_SCHEMA schemaVersion, ReadableDataLocation... extraLocations) throws SdmxSyntaxException {
        log.debug("Validate XML Enabled :" + enableValidation);
        if (!enableValidation) {
            log.warn("Validation disabled");
            return;
        }
        if (!schemaLocations.containsKey(schemaVersion)) {
            throw new IllegalArgumentException("Schema location has not been set for schema : " + schemaVersion);
        }

        if (extraLocations == null) {
            URI schema = schemaLocations.get(schemaVersion);
            validateXML(xml, schema, extraLocations);
        } else {
            validateXML(xml, schemaLocations.get(schemaVersion));
        }
    }

    /**
     * Validate xml.
     *
     * @param xmlLocation    the xml location
     * @param schemaURI      the schema uri
     * @param schemaLocation the schema location
     * @throws SdmxSyntaxException the sdmx syntax exception
     */
    public static void validateXML(InputStream xmlLocation, URI schemaURI, ReadableDataLocation... schemaLocation) throws SdmxSyntaxException {
        log.debug("Validate XML Enabled :" + enableValidation);
        if (!enableValidation) {
            log.warn("Validation disabled");
            return;
        }


        Source[] schemaSource = new Source[schemaLocation.length + 1];
        log.debug("Create Schema" + schemaURI.toString());
        schemaSource[0] = new StreamSource(schemaURI.toString());
        if (schemaLocation != null) {
            for (int i = 0; i < schemaLocation.length; i++) {
                log.debug("Create Stream Source" + schemaLocation[i]);
                schemaSource[i + 1] = new StreamSource(schemaLocation[i].getInputStream());
            }
        }

        log.debug("Create Schema from Source[]");
        Schema schema;
        //Note: any parse called is no thread safe, if this is called by two threads then the following error will occur:
        //      org.xml.sax.SAXException: FWK005 parse may not be called while parsing.
        synchronized (schemaFactory) {
            try {
                schema = schemaFactory.newSchema(schemaSource);
            } catch (SAXException e) {
                throw new RuntimeException(e);
            }
        }
        validateXML(xmlLocation, schema);
    }


    private static void validateXML(InputStream xmlLocation, Schema schema) throws SdmxSyntaxException {
        log.debug("Validate XML Enabled :" + enableValidation);
        if (!enableValidation) {
            log.warn("Validation disabled");
            return;
        }

        StreamSource source = null;

        try {
            Validator schemaValidator = schema.newValidator();

            ErrorHandler eh = new ErrorHandler();
            schemaValidator.setErrorHandler(eh);

            source = new StreamSource(xmlLocation);

            log.debug("Validate XML");
            schemaValidator.validate(source);
            source.getInputStream().close();
            log.debug("Validation complete");
            if (eh.getErrors().size() > 0) {
                // Create a "report" for all of the errors that occurred
                List<String> validationErrors = new ArrayList<String>();
                for (SAXParseException ex : eh.getErrors()) {
                    String message = ex.getMessage() + "line/column: " + ex.getLineNumber() + "/" + ex.getColumnNumber();
                    validationErrors.add(message);
                }
                throw new SchemaValidationException(validationErrors);
            }
            log.debug("XML is Valid");
        } catch (Throwable th) {
            if (th instanceof SchemaValidationException) {
                throw (SchemaValidationException) th;
            }
            throw new SchemaValidationException(th);
        } finally {
            if (source != null) {
                StreamUtil.closeStream(source.getInputStream());
            }
        }
    }

    /**
     * Sets enable validation.
     *
     * @param enableValidation the enable validation
     */
    public void setEnableValidation(boolean enableValidation) {
        XMLParser.enableValidation = enableValidation;
    }

    /**
     * The type Error handler.
     */
    /*
     * This class is a bit of a work-around for a possible bug that exists within the validate routine of the Validator class.
     * When validating an XML file against a schema the problem is that if the validation of the file fails,
     * then the file becomes locked until the application terminates.
     * If the input file was valid then the file does not become locked and everything is fine.
     * So, when an error is encountered, it is simply stored in a List and validation continues successfully.
     * Once the validation has completed, this class must be asked if there were any errors and act accordingly.
     */
    protected static class ErrorHandler extends DefaultHandler {
        private List<SAXParseException> errors = new ArrayList<SAXParseException>();

        /**
         * Gets errors.
         *
         * @return the errors
         */
        public List<SAXParseException> getErrors() {
            return errors;
        }

        @Override
        public void error(SAXParseException parseException) throws SAXException {
            log.error(parseException);
            errors.add(parseException);
        }

        @Override
        public void fatalError(SAXParseException parseException) throws SAXException {
            log.error(parseException);
            errors.add(parseException);
        }

        @Override
        public void warning(SAXParseException parseException) throws SAXException {
            log.error(parseException);
            errors.add(parseException);
        }
    }
}
