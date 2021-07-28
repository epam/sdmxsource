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

import org.sdmx.resources.sdmxml.schemas.v21.structure.TransitionType;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.base.SdmxStructureBean;
import org.sdmxsource.sdmx.api.model.beans.base.TextTypeWrapper;
import org.sdmxsource.sdmx.api.model.beans.process.ProcessBean;
import org.sdmxsource.sdmx.api.model.beans.process.ProcessStepBean;
import org.sdmxsource.sdmx.api.model.beans.process.TransitionBean;
import org.sdmxsource.sdmx.api.model.mutable.base.TextTypeWrapperMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.base.TransitionMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.IdentifiableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.TextTypeWrapperImpl;
import org.sdmxsource.sdmx.sdmxbeans.util.RefUtil;
import org.sdmxsource.sdmx.sdmxbeans.util.TextTypeUtil;
import org.sdmxsource.util.ObjectUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;


/**
 * The type Transition bean.
 */
public class TransitionBeanImpl extends IdentifiableBeanImpl implements TransitionBean {
    private static final long serialVersionUID = 1L;
    private String targetStep;
    private List<TextTypeWrapper> condition = new ArrayList<TextTypeWrapper>();
    private String localId;
    private transient ProcessStepBean processStep;  //Referenced from targetStep

    /**
     * Instantiates a new Transition bean.
     *
     * @param bean   the bean
     * @param parent the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEANS 			 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public TransitionBeanImpl(TransitionMutableBean bean, SdmxStructureBean parent) {
        super(bean, parent);
        this.targetStep = bean.getTargetStep();
        if (bean.getConditions() != null) {
            for (TextTypeWrapperMutableBean ttMut : bean.getConditions()) {
                if (ObjectUtil.validString(ttMut.getValue())) {
                    condition.add(new TextTypeWrapperImpl(ttMut, this));
                }
            }
        }
        this.localId = bean.getLocalId();
        validate();
    }

    /**
     * Instantiates a new Transition bean.
     *
     * @param transition the transition
     * @param parent     the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public TransitionBeanImpl(TransitionType transition, SdmxStructureBean parent) {
        super(transition, SDMX_STRUCTURE_TYPE.TRANSITION, parent);
        if (transition.getTargetStep() != null) {
            targetStep = RefUtil.createLocalIdReference(transition.getTargetStep());
        }
        if (transition.getConditionList() != null) {
            this.condition = TextTypeUtil.wrapTextTypeV21(transition.getConditionList(), this);
        }
        this.localId = transition.getLocalID();
        validate();
    }


    /**
     * Instantiates a new Transition bean.
     *
     * @param trans  the trans
     * @param parent the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public TransitionBeanImpl(org.sdmx.resources.sdmxml.schemas.v20.structure.TransitionType trans, SdmxStructureBean parent) {
        super(generateId(), SDMX_STRUCTURE_TYPE.TRANSITION, parent);
        this.targetStep = trans.getTargetStep();
        if (trans.getCondition() != null) {
            this.condition.add(new TextTypeWrapperImpl(trans.getCondition(), this));
        }
    }

    private static String generateId() {
        return UUID.randomUUID().toString();
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
            TransitionBean that = (TransitionBean) bean;
            if (!super.equivalent(condition, that.getCondition(), includeFinalProperties)) {
                return false;
            }
            if (!ObjectUtil.equivalent(targetStep, that.getTargetStep())) {
                return false;
            }
            if (!ObjectUtil.equivalent(localId, that.getLocalId())) {
                return false;
            }
            return super.deepEqualsInternal(that, includeFinalProperties);
        }
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATE				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private void validate() throws SdmxSemmanticException {
        if (!ObjectUtil.validString(targetStep)) {
            throw new SdmxSemmanticException("Transition is missing mandatory 'Target Step'");
        }
    }

    /**
     * Method to be called after construction of Process, as a Transition may be referencing a Process Step that has not yet been built due to nesting
     */
    protected void verifyProcessSteps() {
        ProcessBean parentProcess = (ProcessBean) getMaintainableParent();
        setTargetStep(parentProcess.getProcessSteps(), targetStep.split("\\."), 0);
    }

    /**
     * Walks through Processes and sub processes to set the ProcessStepBean referenced from the targetStep
     *
     * @param processSteps
     */
    private void setTargetStep(List<ProcessStepBean> processSteps, String[] targetStepSplit, int currentPosition) {
        for (ProcessStepBean currentProcessStep : processSteps) {
            if (currentProcessStep.getId().equals(targetStepSplit[currentPosition])) {
                int nextPos = currentPosition + 1;
                if (targetStepSplit.length > nextPos) {
                    setTargetStep(currentProcessStep.getProcessSteps(), targetStepSplit, nextPos);
                    return;
                } else {
                    this.processStep = currentProcessStep;
                    return;
                }
            }
        }
        throw new SdmxSemmanticException("Can not resolve reference to ProcessStep with reference '" + this.targetStep + "'");
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public ProcessStepBean getTargetStep() {
        if (targetStep != null && processStep == null) {
            verifyProcessSteps();
        }
        return processStep;
    }

    @Override
    public List<TextTypeWrapper> getCondition() {
        return new ArrayList<TextTypeWrapper>(condition);
    }

    @Override
    public List<TextTypeWrapper> getAllTextTypes() {
        List<TextTypeWrapper> returnList = super.getAllTextTypes();
        returnList.addAll(condition);
        return returnList;
    }

    @Override
    public String getLocalId() {
        return localId;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////COMPOSITES		                     //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected Set<SDMXBean> getCompositesInternal() {
        Set<SDMXBean> composites = super.getCompositesInternal();
        super.addToCompositeSet(condition, composites);
        return composites;
    }
}
