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
package org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v21.rid;

import org.sdmx.resources.sdmxml.schemas.v21.common.ActionType;
import org.sdmx.resources.sdmxml.schemas.v21.message.RegistryInterfaceDocument;
import org.sdmx.resources.sdmxml.schemas.v21.message.RegistryInterfaceType;
import org.sdmx.resources.sdmxml.schemas.v21.registry.SubmitStructureRequestType;
import org.sdmxsource.sdmx.api.constants.DATASET_ACTION;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.registry.response.impl.v21.V2_1Helper;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v21.assemblers.StructureXmlBeanAssembler;


/**
 * The type Submit structure builder.
 */
@Deprecated
/**
 * Use RegistrySubmitXmlBeanBuilder instead
 */
public class SubmitStructureBuilder {

    private final StructureXmlBeanAssembler structureXmlBeanAssembler = new StructureXmlBeanAssembler();

    /**
     * Build registry interface document registry interface document.
     *
     * @param buildFrom the build from
     * @param action    the action
     * @return the registry interface document
     * @throws SdmxException the sdmx exception
     */
    public RegistryInterfaceDocument buildRegistryInterfaceDocument(SdmxBeans buildFrom, DATASET_ACTION action) throws SdmxException {
        RegistryInterfaceDocument rid = RegistryInterfaceDocument.Factory.newInstance();
        RegistryInterfaceType rit = rid.addNewRegistryInterface();
        V2_1Helper.setHeader(rit);
        SubmitStructureRequestType structureRequestType = rit.addNewSubmitStructureRequest();
        switch (action) {
            case APPEND:
                structureRequestType.setAction(ActionType.APPEND);
                break;
            case REPLACE:
                structureRequestType.setAction(ActionType.REPLACE);
                break;
            case DELETE:
                structureRequestType.setAction(ActionType.DELETE);
                break;
            case INFORMATION:
                structureRequestType.setAction(ActionType.INFORMATION);
                break;
        }
        structureXmlBeanAssembler.assemble(structureRequestType.addNewStructures(), buildFrom);

        return rid;
    }
}
