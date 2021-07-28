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
package org.sdmxsource.sdmx.ediparser.constants;

/**
 * The enum Message position.
 */
public enum MESSAGE_POSITION {
    /**
     * Message identification message position.
     */
    MESSAGE_IDENTIFICATION,
    /**
     * Message function message position.
     */
    MESSAGE_FUNCTION,
    /**
     * Codelist maintenance agency message position.
     */
    CODELIST_MAINTENANCE_AGENCY,
    /**
     * Receiver identification message position.
     */
    RECEIVER_IDENTIFICATION,
    /**
     * Sender identification message position.
     */
    SENDER_IDENTIFICATION,
    /**
     * Concept identifier message position.
     */
    CONCEPT_IDENTIFIER,
    /**
     * Concept name message position.
     */
    CONCEPT_NAME,
    /**
     * Codelist identifier message position.
     */
    CODELIST_IDENTIFIER,
    /**
     * Code value message position.
     */
    CODE_VALUE,
    /**
     * Code description message position.
     */
    CODE_DESCRIPTION,
    /**
     * Key family identifier message position.
     */
    KEY_FAMILY_IDENTIFIER,
    /**
     * Key family name message position.
     */
    KEY_FAMILY_NAME,
    /**
     * Dimension message position.
     */
    DIMENSION,
    /**
     * Attribute message position.
     */
    ATTRIBUTE,
    /**
     * Codelist reference message position.
     */
    CODELIST_REFERENCE;
}
