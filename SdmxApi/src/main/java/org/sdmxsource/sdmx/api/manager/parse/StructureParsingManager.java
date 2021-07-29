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
package org.sdmxsource.sdmx.api.manager.parse;

import org.sdmxsource.sdmx.api.exception.CrossReferenceException;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.exception.SdmxSyntaxException;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.ResolutionSettings;
import org.sdmxsource.sdmx.api.model.StructureWorkspace;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.api.util.ReadableDataLocation;

/**
 * Parses and validates input SDMX-ML data into other data formats
 */
public interface StructureParsingManager {

    /**
     * Parses a structure document OR a Registry document that contains structures.
     * <p>
     * Validates the SDMX-ML document against the correct schema. It also validates the structure according to the SDMX standards,
     * using rules which can not be specified by the schema. Uses the supplied settings to perform any extra operations.
     *
     * @param dataLocation     the document to be parsed.
     * @param settings         additional settings to perform when parsing.
     * @param retrievalManager An optional parameter which if supplied, will be used to resolve any references  that do not exist in the supplied SDMX-ML document.
     * @return a StructureWorkspace from which structures can be retrieved in any format required.
     * @throws SdmxSyntaxException     if the structure message is syntactically invalid
     * @throws CrossReferenceException if the structure document references structures that can not be resolved
     * @throws SdmxSemmanticException  if the structure message is syntactically correct, but the content is illegal
     */
    StructureWorkspace parseStructures(ReadableDataLocation dataLocation,
                                       ResolutionSettings settings,
                                       SdmxBeanRetrievalManager retrievalManager) throws SdmxSyntaxException, CrossReferenceException, SdmxSemmanticException;

    /**
     * Parses a structure document OR a Registry document that contains structures.
     * <p>
     * Validates the SDMX-ML document against the correct schema. It also validates the structure according to the SDMX standards,
     * using rules which can not be specified by schema.
     * <p>
     * Uses the default parsing settings, which is to not validate cross references, and therefore no <code>SdmxBeanRetrievalManager</code> is
     * required.
     *
     * @param dataLocation the document to be parsed.
     * @return a StructureWorkspace from which structures can be retrieved in any format required.
     * @throws SdmxSyntaxException     if the structure message is syntactically invalid
     * @throws CrossReferenceException if the structure document references structures that can not be resolved
     * @throws SdmxSemmanticException  if the structure message is syntactically correct, but the content is illegal
     */
    StructureWorkspace parseStructures(ReadableDataLocation dataLocation) throws SdmxSyntaxException, CrossReferenceException, SdmxSemmanticException;

    /**
     * Creates a StructureWorkspace object from the supplied parameters.
     *
     * @param beans            the beans
     * @param settings         the settings
     * @param retrievalManager the retrieval manager
     * @return a StructureWorkspace from which structures can be retrieved in any format required.
     */
    StructureWorkspace buildWorkspace(SdmxBeans beans, ResolutionSettings settings, SdmxBeanRetrievalManager retrievalManager);
}
