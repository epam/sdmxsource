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
package org.sdmxsource.sdmx.sdmxbeans.model.mutable.process;

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.process.InputOutputBean;
import org.sdmxsource.sdmx.api.model.beans.process.ProcessStepBean;
import org.sdmxsource.sdmx.api.model.beans.process.TransitionBean;
import org.sdmxsource.sdmx.api.model.mutable.base.TransitionMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.process.ComputationMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.process.InputOutputMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.process.ProcessStepMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.NameableMutableBeanImpl;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Process step mutable bean.
 */
public class ProcessStepMutableBeanImpl extends NameableMutableBeanImpl implements ProcessStepMutableBean {
    private static final long serialVersionUID = 1L;

    private List<InputOutputMutableBean> input = new ArrayList<InputOutputMutableBean>();
    private List<InputOutputMutableBean> output = new ArrayList<InputOutputMutableBean>();
    private List<TransitionMutableBean> transitions = new ArrayList<TransitionMutableBean>();
    private List<ProcessStepMutableBean> processSteps = new ArrayList<ProcessStepMutableBean>();
    private ComputationMutableBean computation;

    /**
     * Instantiates a new Process step mutable bean.
     */
    public ProcessStepMutableBeanImpl() {
        super(SDMX_STRUCTURE_TYPE.PROCESS_STEP);
    }

    /**
     * Instantiates a new Process step mutable bean.
     *
     * @param bean the bean
     */
    public ProcessStepMutableBeanImpl(ProcessStepBean bean) {
        super(bean);

        if (bean.getInput() != null) {
            for (InputOutputBean currentIo : bean.getInput()) {
                this.input.add(new InputOutputMutableBeanImpl(currentIo));
            }
        }
        if (bean.getOutput() != null) {
            for (InputOutputBean currentIo : bean.getOutput()) {
                this.output.add(new InputOutputMutableBeanImpl(currentIo));
            }
        }

        // make into mutable bean lists
        if (bean.getProcessSteps() != null) {
            for (ProcessStepBean currentBean : bean.getProcessSteps()) {
                this.addProcessStep(new ProcessStepMutableBeanImpl(currentBean));
            }
        }
        if (bean.getTransitions() != null) {
            for (TransitionBean currentBean : bean.getTransitions()) {
                this.addTransition(new TransitionMutableBeanImpl(currentBean));
            }
        }
        if (bean.getComputation() != null) {
            this.computation = new ComputationMutableBeanImpl(bean.getComputation());
        }
    }

    @Override
    public List<InputOutputMutableBean> getInput() {
        return input;
    }

    @Override
    public void setInput(List<InputOutputMutableBean> input) {
        this.input = input;
    }

    @Override
    public List<InputOutputMutableBean> getOutput() {
        return output;
    }

    @Override
    public void setOutput(List<InputOutputMutableBean> output) {
        this.output = output;
    }

    @Override
    public ComputationMutableBean getComputation() {
        return computation;
    }

    @Override
    public void setComputation(ComputationMutableBean computation) {
        this.computation = computation;
    }

    @Override
    public List<TransitionMutableBean> getTransitions() {
        return transitions;
    }

    @Override
    public void setTransitions(List<TransitionMutableBean> transitions) {
        this.transitions = transitions;
    }

    @Override
    public List<ProcessStepMutableBean> getProcessSteps() {
        return processSteps;
    }

    @Override
    public void setProcessSteps(List<ProcessStepMutableBean> processSteps) {
        this.processSteps = processSteps;
    }

    @Override
    public void addTransition(TransitionMutableBean transition) {
        this.transitions.add(transition);
    }

    @Override
    public void addProcessStep(ProcessStepMutableBean processStep) {
        this.processSteps.add(processStep);
    }
}
