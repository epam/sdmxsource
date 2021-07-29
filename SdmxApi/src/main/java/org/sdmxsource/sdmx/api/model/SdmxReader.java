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
package org.sdmxsource.sdmx.api.model;

import java.util.Map;


/**
 * The SdmxReader is a technology independent definition of a reader for SDMX-ML files
 */
public interface SdmxReader {

//    /**
//     * Reads the next element enforcing it is one of the following elements
//     * @param expectedElement this can not be null
//     * @return will return a value that matches one of the expected values, response can never be null
//     * @throws ValidationException if the next element does not match the expected element
//     */
//    String readNextElement(String...expectedElement) throws ValidationException;


//    /**
//     * Takes a peek at the next element without moving the parser forward
//     * @return
//     */
//	String peek();

    /**
     * Reads the next element returning the value.  The response is false if there is no more elements to read
     *
     * @return false if there is no more elements to read
     */
    boolean moveNextElement();


    /**
     * Returns the name of the currentElement
     *
     * @return current element
     */
    String getCurrentElement();

    /**
     * Returns the value in the current element - returns null if there is no value
     *
     * @return current element value
     */
    String getCurrentElementValue();

    /**
     * Moves the parser back to the element with the given name.
     *
     * @param element the element
     * @return true if the move was successful, false if no element was found
     */
    boolean moveBackToElement(String element);

    /**
     * Moves to the position in the file with the element name, returns true if the move was successful, false if no element was found
     *
     * @param element the element
     * @return true if the move was successful, false if no element was found
     */
    boolean moveToElement(String element);

    /**
     * Move to element boolean.
     *
     * @param element      the element
     * @param doNoMovePast the do no move past
     * @return true if the move was successful, false if no element was found
     */
    boolean moveToElement(String element, String doNoMovePast);

    /**
     * Returns a map of all the attributes present on the current node
     *
     * @return the attributes map
     */
    Map<String, String> getAttributes();


    /**
     * Returns an attribute value with the given name, if mandatory is true then will enforce the response is not null
     *
     * @param attributeName the attribute name
     * @param mandatory     the mandatory
     * @return attribute value //	 * @throws SchemaValidationException if mandatory is true and the attribute is not present
     */
    String getAttributeValue(String attributeName, boolean mandatory);


    /**
     * Closes any underlying resource
     */
    void close();

}
