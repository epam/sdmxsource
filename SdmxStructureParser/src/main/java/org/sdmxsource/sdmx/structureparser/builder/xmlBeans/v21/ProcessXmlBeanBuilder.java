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
package org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v21;

import org.sdmx.resources.sdmxml.schemas.v21.structure.ProcessStepType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.ProcessType;
import org.sdmxsource.sdmx.api.builder.Builder;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.beans.process.ProcessBean;
import org.sdmxsource.sdmx.api.model.beans.process.ProcessStepBean;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v21.assemblers.MaintainableBeanAssembler;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v21.assemblers.ProcessStepBeanAssembler;


/**
 * The type Process xml bean builder.
 */
public class ProcessXmlBeanBuilder extends MaintainableBeanAssembler implements Builder<ProcessType, ProcessBean> {

    private final ProcessStepBeanAssembler processStepBeanAssemblerBean = new ProcessStepBeanAssembler();

    @Override
    public ProcessType build(ProcessBean buildFrom) throws SdmxException {
        // Create outgoing build
        ProcessType builtObj = ProcessType.Factory.newInstance();
        // Populate it from inherited super
        assembleMaintainable(builtObj, buildFrom);
        // Populate it using this class's specifics
        for (ProcessStepBean eachProcessStepBean : buildFrom.getProcessSteps()) {
            ProcessStepType newProcessStepType = builtObj.addNewProcessStep();
            processStepBeanAssemblerBean.assemble(newProcessStepType, eachProcessStepBean);
        }
        return builtObj;
    }
}
