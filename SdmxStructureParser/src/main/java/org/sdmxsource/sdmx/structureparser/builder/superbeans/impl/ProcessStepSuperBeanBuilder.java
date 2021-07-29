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
import org.sdmxsource.sdmx.api.model.beans.process.InputOutputBean;
import org.sdmxsource.sdmx.api.model.beans.process.ProcessStepBean;
import org.sdmxsource.sdmx.api.model.beans.process.TransitionBean;
import org.sdmxsource.sdmx.api.model.superbeans.process.InputOutputSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.process.ProcessStepSuperBean;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.process.ProcessStepSuperBeanImpl;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Process step super bean builder.
 */
public class ProcessStepSuperBeanBuilder {

    private final InputOutputSuperBeanBuilder inputOutputSuperBeanBuilder;

    /**
     * Instantiates a new Process step super bean builder.
     */
    public ProcessStepSuperBeanBuilder() {
        this(new InputOutputSuperBeanBuilder());
    }

    /**
     * Instantiates a new Process step super bean builder.
     *
     * @param inputOutputSuperBeanBuilder the input output super bean builder
     */
    public ProcessStepSuperBeanBuilder(final InputOutputSuperBeanBuilder inputOutputSuperBeanBuilder) {
        this.inputOutputSuperBeanBuilder = inputOutputSuperBeanBuilder;
    }

    /**
     * Build process step super bean.
     *
     * @param buildFrom        the build from
     * @param retrievalManager the retrieval manager
     * @return the process step super bean
     */
    public ProcessStepSuperBean build(ProcessStepBean buildFrom,
                                      IdentifiableRetrievalManager retrievalManager) {

        List<InputOutputSuperBean> input = new ArrayList<InputOutputSuperBean>();
        List<InputOutputSuperBean> output = new ArrayList<InputOutputSuperBean>();
        List<TransitionBean> transitions = new ArrayList<TransitionBean>();
        List<ProcessStepSuperBean> processSteps = new ArrayList<ProcessStepSuperBean>();

        for (InputOutputBean eachInput : buildFrom.getInput()) {
            input.add(inputOutputSuperBeanBuilder.build(eachInput, retrievalManager));
        }

        for (InputOutputBean eachOutput : buildFrom.getOutput()) {
            output.add(inputOutputSuperBeanBuilder.build(eachOutput, retrievalManager));
        }

        for (TransitionBean eachTrans : buildFrom.getTransitions()) {
            transitions.add(eachTrans);
        }

        for (ProcessStepBean childStep : buildFrom.getProcessSteps()) {
            processSteps.add(build(childStep, retrievalManager));
        }

        return new ProcessStepSuperBeanImpl(buildFrom, input, output, buildFrom.getComputation(), transitions, processSteps);
    }


}
