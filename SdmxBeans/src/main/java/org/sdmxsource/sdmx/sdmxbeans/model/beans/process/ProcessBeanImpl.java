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

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.sdmx.resources.sdmxml.schemas.v20.structure.ProcessStepType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.ProcessType;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.process.ProcessBean;
import org.sdmxsource.sdmx.api.model.beans.process.ProcessStepBean;
import org.sdmxsource.sdmx.api.model.beans.process.TransitionBean;
import org.sdmxsource.sdmx.api.model.mutable.process.ProcessMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.process.ProcessStepMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.MaintainableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.process.ProcessMutableBeanImpl;

import javax.xml.bind.ValidationException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * The type Process bean.
 */
public class ProcessBeanImpl extends MaintainableBeanImpl implements ProcessBean {
    private static final long serialVersionUID = 1L;
    private static Logger LOG = LoggerFactory.getLogger(ProcessBeanImpl.class);
    private List<ProcessStepBean> processSteps = new ArrayList<ProcessStepBean>();


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM ITSELF, CREATES STUB BEAN //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private ProcessBeanImpl(ProcessBean bean, URL actualLocation, boolean isServiceUrl) {
        super(bean, actualLocation, isServiceUrl);
        LOG.debug("Stub ProcessBean Built");
    }

    /**
     * Instantiates a new Process bean.
     *
     * @param processBean the process bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEANS			 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ProcessBeanImpl(ProcessMutableBean processBean) {
        super(processBean);
        LOG.debug("Building ProcessBean from Mutable Bean");
        try {
            if (processBean.getProcessSteps() != null) {
                for (ProcessStepMutableBean processStep : processBean.getProcessSteps()) {
                    processSteps.add(new ProcessStepBeanImpl(this, processStep));
                }
            }
        } catch (Throwable th) {
            throw new SdmxSemmanticException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this);
        }
        try {
            validate();
        } catch (ValidationException e) {
            throw new SdmxSemmanticException(e, ExceptionCode.FAIL_VALIDATION, this);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("ProcessBean Built " + this.getUrn());
        }
    }


    /**
     * Instantiates a new Process bean.
     *
     * @param processBean the process bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ProcessBeanImpl(org.sdmx.resources.sdmxml.schemas.v21.structure.ProcessType processBean) {
        super(processBean, SDMX_STRUCTURE_TYPE.PROCESS);
        LOG.debug("Building ProcessBean from 2.1 SDMX");
        try {
            if (processBean.getProcessStepList() != null) {
                for (org.sdmx.resources.sdmxml.schemas.v21.structure.ProcessStepType processStep : processBean.getProcessStepList()) {
                    processSteps.add(new ProcessStepBeanImpl(this, processStep));
                }
            }
        } catch (Throwable th) {
            throw new SdmxSemmanticException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this);
        }
        try {
            validate();
        } catch (ValidationException e) {
            throw new SdmxSemmanticException(e, ExceptionCode.FAIL_VALIDATION, this);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("ProcessBean Built " + this.getUrn());
        }
    }


    /**
     * Instantiates a new Process bean.
     *
     * @param bean the bean
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ProcessBeanImpl(ProcessType bean) {
        super(bean, SDMX_STRUCTURE_TYPE.PROCESS,
                bean.getValidTo(),
                bean.getValidFrom(),
                bean.getVersion(),
                createTertiary(bean.isSetIsFinal(), bean.getIsFinal()),
                bean.getAgencyID(),
                bean.getId(),
                bean.getUri(),
                bean.getNameList(),
                bean.getDescriptionList(),
                createTertiary(bean.isSetIsExternalReference(), bean.getIsExternalReference()),
                bean.getAnnotations());
        LOG.debug("Building ProcessBean from 2.0 SDMX");
        try {
            if (bean.getProcessStepList() != null) {
                for (ProcessStepType processStep : bean.getProcessStepList()) {
                    processSteps.add(new ProcessStepBeanImpl(this, processStep));
                }
            }
        } catch (SdmxSemmanticException ex) {
            throw new SdmxSemmanticException(ex, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        } catch (Throwable th) {
            throw new SdmxException(th, ExceptionCode.BEAN_STRUCTURE_CONSTRUCTION_ERROR, this.getUrn());
        }
        try {
            validate();
        } catch (ValidationException e) {
            throw new SdmxSemmanticException(e, ExceptionCode.FAIL_VALIDATION, this);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("ProcessBean Built " + this.getUrn());
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
            ProcessBean that = (ProcessBean) bean;
            if (!super.equivalent(processSteps, that.getProcessSteps(), includeFinalProperties)) {
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
        Set<String> processStepReferences = new HashSet<String>();  //Process Step References
        Set<String> processStepIds = new HashSet<String>();            //Process Step Ids

        populateProcessStepSets(processSteps, processStepIds, processStepReferences);
        if (!processStepIds.containsAll(processStepReferences)) {
            for (String currentReference : processStepReferences) {
                if (!processStepIds.contains(currentReference)) {
                    throw new SdmxSemmanticException("Transition references non existent process step '" + currentReference + "'");
                }
            }
        }
    }

    private void populateProcessStepSets(List<ProcessStepBean> processSteps, Set<String> processStepIds, Set<String> processStepReferences) {
        for (ProcessStepBean processStep : processSteps) {
            for (TransitionBean transition : processStep.getTransitions()) {
                processStepReferences.add(transition.getTargetStep().getId());
            }
            processStepIds.add(processStep.getFullIdPath(false));
            populateProcessStepSets(processStep.getProcessSteps(), processStepIds, processStepReferences);
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public ProcessBean getStub(URL actualLocation, boolean isServiceUrl) {
        return new ProcessBeanImpl(this, actualLocation, isServiceUrl);
    }


    @Override
    public ProcessMutableBean getMutableInstance() {
        return new ProcessMutableBeanImpl(this);
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
        super.addToCompositeSet(processSteps, composites);
        return composites;
    }
}
