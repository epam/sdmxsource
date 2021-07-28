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
package org.sdmxsource.sdmx.sdmxbeans.model.beans.process;

import org.apache.log4j.Logger;
import org.sdmx.resources.sdmxml.schemas.v20.structure.ProcessStepType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.TransitionType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.InputOutputType;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.IdentifiableBean;
import org.sdmxsource.sdmx.api.model.beans.base.NameableBean;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.base.TextTypeWrapper;
import org.sdmxsource.sdmx.api.model.beans.process.ComputationBean;
import org.sdmxsource.sdmx.api.model.beans.process.InputOutputBean;
import org.sdmxsource.sdmx.api.model.beans.process.ProcessStepBean;
import org.sdmxsource.sdmx.api.model.beans.process.TransitionBean;
import org.sdmxsource.sdmx.api.model.mutable.base.TransitionMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.process.InputOutputMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.process.ProcessStepMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.NameableBeanImpl;

import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * The type Process step bean.
 */
public class ProcessStepBeanImpl extends NameableBeanImpl implements ProcessStepBean {
    private static final long serialVersionUID = 1L;

    private static Logger LOG = Logger.getLogger(ProcessStepBeanImpl.class);

    private List<InputOutputBean> input = new ArrayList<InputOutputBean>();
    private List<InputOutputBean> output = new ArrayList<InputOutputBean>();
    private List<TransitionBean> transitions = new ArrayList<TransitionBean>();
    private List<ProcessStepBean> processSteps = new ArrayList<ProcessStepBean>();
    private ComputationBean computation;

    /**
     * Instantiates a new Process step bean.
     *
     * @param parent      the parent
     * @param processBean the process bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEANS			 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ProcessStepBeanImpl(IdentifiableBean parent, ProcessStepMutableBean processBean) {
        super(processBean, parent);
        if (processBean.getInput() != null) {
            for (InputOutputMutableBean currentIo : processBean.getInput()) {
                this.input.add(new InputOutputBeanImpl(this, currentIo));
            }
        }
        if (processBean.getOutput() != null) {
            for (InputOutputMutableBean currentIo : processBean.getOutput()) {
                this.output.add(new InputOutputBeanImpl(this, currentIo));
            }
        }
        if (processBean.getComputation() != null) {
            computation = new ComputationBeanImpl(this, processBean.getComputation());
        }
        if (processBean.getTransitions() != null) {
            for (TransitionMutableBean mutable : processBean.getTransitions()) {
                transitions.add(new TransitionBeanImpl(mutable, this));
            }
        }
        if (processBean.getProcessSteps() != null) {
            for (ProcessStepMutableBean mutable : processBean.getProcessSteps()) {
                processSteps.add(new ProcessStepBeanImpl(this, mutable));
            }
        }
        try {
            validate();
        } catch (ValidationException e) {
            throw new SdmxSemmanticException(e, ExceptionCode.FAIL_VALIDATION, this);
        }
    }


    /**
     * Instantiates a new Process step bean.
     *
     * @param parent      the parent
     * @param processBean the process bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ProcessStepBeanImpl(NameableBean parent, org.sdmx.resources.sdmxml.schemas.v21.structure.ProcessStepType processBean) {
        super(processBean, SDMX_STRUCTURE_TYPE.PROCESS_STEP, parent);

        if (processBean.getInputList() != null) {
            for (InputOutputType currentIo : processBean.getInputList()) {
                this.input.add(new InputOutputBeanImpl(this, currentIo));
            }
        }
        if (processBean.getOutputList() != null) {
            for (InputOutputType currentIo : processBean.getOutputList()) {
                this.output.add(new InputOutputBeanImpl(this, currentIo));
            }
        }

        if (processBean.getComputation() != null) {
            computation = new ComputationBeanImpl(this, processBean.getComputation());
        }

        if (processBean.getProcessStepList() != null) {
            for (org.sdmx.resources.sdmxml.schemas.v21.structure.ProcessStepType processStep : processBean.getProcessStepList()) {
                processSteps.add(new ProcessStepBeanImpl(this, processStep));
            }
        }

        if (processBean.getTransitionList() != null) {
            for (org.sdmx.resources.sdmxml.schemas.v21.structure.TransitionType trans : processBean.getTransitionList()) {
                TransitionBean tRef = new TransitionBeanImpl(trans, this);
                transitions.add(tRef);
            }
        }
        try {
            validate();
        } catch (ValidationException e) {
            throw new SdmxSemmanticException(e, ExceptionCode.FAIL_VALIDATION, this);
        }
    }

    /**
     * Instantiates a new Process step bean.
     *
     * @param parent      the parent
     * @param processBean the process bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ProcessStepBeanImpl(NameableBean parent, ProcessStepType processBean) {
        super(processBean, SDMX_STRUCTURE_TYPE.PROCESS_STEP, processBean.getId(), "", processBean.getNameList(), processBean.getDescriptionList(), processBean.getAnnotations(), parent);

        if (processBean.getInputList() != null) {
            LOG.warn("Input items not supported for SDMX V2.0. These items will be discarded");
        }

        if (processBean.getOutputList() != null) {
            LOG.warn("Input items not supported for SDMX V2.0. These items will be discarded");
        }

        if (processBean.getComputationList() != null) {
            computation = new ComputationBeanImpl(this, processBean);
        }

        if (processBean.getProcessStepList() != null) {
            for (ProcessStepType processStep : processBean.getProcessStepList()) {
                processSteps.add(new ProcessStepBeanImpl(this, processStep));
            }
        }

        if (processBean.getTransitionList() != null) {
            for (TransitionType trans : processBean.getTransitionList()) {
                TransitionBean tRef = new TransitionBeanImpl(trans, this);
                transitions.add(tRef);
            }
        }
        try {
            validate();
        } catch (ValidationException e) {
            throw new SdmxSemmanticException(e, ExceptionCode.FAIL_VALIDATION, this);
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////DEEP EQUALS							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean deepEquals(SDMXBean bean, boolean includeFinalProperties) {
        if (bean == null) {
            return false;
        }
        if (bean.getStructureType() == this.getStructureType()) {
            ProcessStepBean that = (ProcessStepBean) bean;
            if (!super.equivalent(input, that.getInput(), includeFinalProperties)) {
                return false;
            }
            if (!super.equivalent(output, that.getOutput(), includeFinalProperties)) {
                return false;
            }
            if (!super.equivalent(transitions, that.getTransitions(), includeFinalProperties)) {
                return false;
            }
            if (!super.equivalent(processSteps, that.getProcessSteps(), includeFinalProperties)) {
                return false;
            }
            if (!super.equivalent(computation, that.getComputation(), includeFinalProperties)) {
                return false;
            }
            return super.deepEqualsInternal(that, includeFinalProperties);
        }
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATION							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private void validate() throws ValidationException {
        //NO VALIDATION REQUIRED
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public ComputationBean getComputation() {
        return computation;
    }


    @Override
    public List<TextTypeWrapper> getAllTextTypes() {
        List<TextTypeWrapper> allTextTypes = super.getAllTextTypes();
        if (computation != null) {
            allTextTypes.addAll(computation.getDescription());
        }
        return allTextTypes;
    }


    @Override
    public List<InputOutputBean> getInput() {
        return new ArrayList<InputOutputBean>(input);
    }

    @Override
    public List<InputOutputBean> getOutput() {
        return new ArrayList<InputOutputBean>(output);
    }

    @Override
    public List<TransitionBean> getTransitions() {
        return new ArrayList<TransitionBean>(transitions);
    }

    @Override
    public List<ProcessStepBean> getProcessSteps() {
        return new ArrayList<ProcessStepBean>(processSteps);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////COMPOSITES		                     //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected Set<SDMXBean> getCompositesInternal() {
        Set<SDMXBean> composites = super.getCompositesInternal();
        super.addToCompositeSet(input, composites);
        super.addToCompositeSet(output, composites);
        super.addToCompositeSet(transitions, composites);
        super.addToCompositeSet(processSteps, composites);
        super.addToCompositeSet(computation, composites);
        return composites;
    }
}
