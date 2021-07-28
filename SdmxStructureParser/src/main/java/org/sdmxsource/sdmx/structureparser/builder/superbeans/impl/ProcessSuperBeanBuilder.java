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
package org.sdmxsource.sdmx.structureparser.builder.superbeans.impl;

import org.sdmxsource.sdmx.api.manager.retrieval.IdentifiableRetrievalManager;
import org.sdmxsource.sdmx.api.model.beans.process.ProcessBean;
import org.sdmxsource.sdmx.api.model.beans.process.ProcessStepBean;
import org.sdmxsource.sdmx.api.model.superbeans.process.ProcessStepSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.process.ProcessSuperBean;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.process.ProcessSuperBeanImpl;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Process super bean builder.
 */
public class ProcessSuperBeanBuilder {

    private final ProcessStepSuperBeanBuilder processStepSuperBeanBuilder;

    /**
     * Instantiates a new Process super bean builder.
     */
    public ProcessSuperBeanBuilder() {
        this(new ProcessStepSuperBeanBuilder());
    }

    /**
     * Instantiates a new Process super bean builder.
     *
     * @param processStepSuperBeanBuilder the process step super bean builder
     */
    public ProcessSuperBeanBuilder(final ProcessStepSuperBeanBuilder processStepSuperBeanBuilder) {
        this.processStepSuperBeanBuilder = processStepSuperBeanBuilder;
    }

    /**
     * Build the Process from a Process XML Bean
     *
     * @param buildFrom        the build from
     * @param retrievalManager the retrieval manager
     * @return the process super bean
     */
    public ProcessSuperBean build(ProcessBean buildFrom,
                                  IdentifiableRetrievalManager retrievalManager) {

        List<ProcessStepSuperBean> steps = new ArrayList<ProcessStepSuperBean>();

        for (ProcessStepBean eachStep : buildFrom.getProcessSteps()) {
            steps.add(processStepSuperBeanBuilder.build(eachStep, retrievalManager));
        }

        return new ProcessSuperBeanImpl(buildFrom, steps);
    }


}
