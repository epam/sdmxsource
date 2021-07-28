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
package org.sdmxsource.sdmx.sdmxbeans.model.superbeans.process;

import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.beans.process.ComputationBean;
import org.sdmxsource.sdmx.api.model.beans.process.ProcessStepBean;
import org.sdmxsource.sdmx.api.model.beans.process.TransitionBean;
import org.sdmxsource.sdmx.api.model.superbeans.process.InputOutputSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.process.ProcessStepSuperBean;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.base.NameableSuperBeanImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * The type Process step super bean.
 */
public class ProcessStepSuperBeanImpl extends NameableSuperBeanImpl implements ProcessStepSuperBean {

    private static final long serialVersionUID = 4815826565240989163L;

    private ProcessStepBean processStep;
    private List<InputOutputSuperBean> inputs;
    private List<InputOutputSuperBean> outputs;
    private ComputationBean computation;
    private List<TransitionBean> transitions;
    private List<ProcessStepSuperBean> processSteps;

    /**
     * Instantiates a new Process step super bean.
     *
     * @param processStep  the process step
     * @param inputs       the inputs
     * @param outputs      the outputs
     * @param computation  the computation
     * @param transitions  the transitions
     * @param processSteps the process steps
     */
    public ProcessStepSuperBeanImpl(ProcessStepBean processStep,
                                    List<InputOutputSuperBean> inputs,
                                    List<InputOutputSuperBean> outputs,
                                    ComputationBean computation,
                                    List<TransitionBean> transitions,
                                    List<ProcessStepSuperBean> processSteps) {

        super(processStep);
        this.processStep = processStep;
        this.inputs = inputs;
        this.outputs = outputs;
        this.computation = computation;
        this.transitions = transitions;
        this.processSteps = processSteps;
    }

    @Override
    public ProcessStepBean getBuiltFrom() {
        return this.processStep;
    }

    @Override
    public List<InputOutputSuperBean> getInput() {
        return new ArrayList<InputOutputSuperBean>(this.inputs);
    }

    @Override
    public List<InputOutputSuperBean> getOutput() {
        return new ArrayList<InputOutputSuperBean>(this.outputs);
    }

    @Override
    public ComputationBean getComputation() {
        return this.computation;
    }

    @Override
    public List<TransitionBean> getTransitions() {
        return new ArrayList<TransitionBean>(this.transitions);
    }

    @Override
    public List<ProcessStepSuperBean> getProcessSteps() {
        return new ArrayList<ProcessStepSuperBean>(this.processSteps);
    }

    @Override
    public Set<MaintainableBean> getCompositeBeans() {
        Set<MaintainableBean> returnSet = super.getCompositeBeans();
        returnSet.add(getBuiltFrom().getMaintainableParent());

        addToSet(returnSet, inputs);
        addToSet(returnSet, outputs);
        addToSet(returnSet, processSteps);
        return returnSet;
    }
}
