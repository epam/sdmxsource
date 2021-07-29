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
package org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v21.assemblers;

import org.sdmx.resources.sdmxml.schemas.v21.common.LocalProcessStepReferenceType;
import org.sdmx.resources.sdmxml.schemas.v21.common.RefBaseType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.ComputationType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.InputOutputType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.ProcessStepType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.TransitionType;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.beans.process.ComputationBean;
import org.sdmxsource.sdmx.api.model.beans.process.InputOutputBean;
import org.sdmxsource.sdmx.api.model.beans.process.ProcessStepBean;
import org.sdmxsource.sdmx.api.model.beans.process.TransitionBean;
import org.sdmxsource.util.ObjectUtil;


/**
 * Assembles a ProcessStepBean to facilitate Builder&lt;K,V&gt; for the top level bean.
 * v2.1 SDMX uses inheritance, so thus can we, unlike in previous versions.
 * Builder&lt;K,V&gt; doesn't lend itself to inheritance.
 */
public class ProcessStepBeanAssembler extends NameableBeanAssembler implements Assembler<ProcessStepType, ProcessStepBean> {

    @Override
    public void assemble(ProcessStepType assembleInto, ProcessStepBean assembleFrom) throws SdmxException {
        assembleNameable(assembleInto, assembleFrom);
        for (InputOutputBean currentInput : assembleFrom.getInput()) {
            assembleInputOutput(currentInput, assembleInto.addNewInput());
        }
        for (InputOutputBean currentOutput : assembleFrom.getOutput()) {
            assembleInputOutput(currentOutput, assembleInto.addNewOutput());
        }
        if (assembleFrom.getComputation() != null) {
            assembleComputation(assembleFrom.getComputation(), assembleInto.addNewComputation());

        }
        for (TransitionBean currentTranistion : assembleFrom.getTransitions()) {
            assembleTransition(currentTranistion, assembleInto.addNewTransition());
        }
        for (ProcessStepBean eachProcessStepBean : assembleFrom.getProcessSteps()) {
            ProcessStepType eachProcessStep = assembleInto.addNewProcessStep();
            this.assemble(eachProcessStep, eachProcessStepBean);
        }
    }

    private void assembleInputOutput(InputOutputBean currentInput, InputOutputType ioType) {
        if (ObjectUtil.validString(currentInput.getLocalId())) {
            ioType.setLocalID(currentInput.getLocalId());
        }
        if (hasAnnotations(currentInput)) {
            ioType.setAnnotations(getAnnotationsType(currentInput));
        }
        if (currentInput.getStructureReference() != null) {
            super.setReference(ioType.addNewObjectReference().addNewRef(), currentInput.getStructureReference());
        }
    }

    private void assembleComputation(ComputationBean computationBean, ComputationType computationType) {
        if (ObjectUtil.validString(computationBean.getLocalId())) {
            computationType.setLocalID(computationBean.getLocalId());
        }
        if (ObjectUtil.validString(computationBean.getSoftwarePackage())) {
            computationType.setSoftwarePackage(computationBean.getSoftwarePackage());
        }
        if (ObjectUtil.validString(computationBean.getSoftwareLanguage())) {
            computationType.setSoftwareLanguage(computationBean.getSoftwareLanguage());
        }
        if (ObjectUtil.validString(computationBean.getSoftwareVersion())) {
            computationType.setSoftwareVersion(computationBean.getSoftwareVersion());
        }
        if (hasAnnotations(computationBean)) {
            computationType.setAnnotations(getAnnotationsType(computationBean));
        }
        if (validCollection(computationBean.getDescription())) {
            computationType.setDescriptionArray(getTextType(computationBean.getDescription()));
        }
    }

    private void assembleTransition(TransitionBean transitionBean, TransitionType transitionType) {
        if (ObjectUtil.validString(transitionBean.getLocalId())) {
            transitionType.setLocalID(transitionBean.getLocalId());
        }
        super.assembleIdentifiable(transitionType, transitionBean);
        if (transitionBean.getTargetStep() != null) {
            LocalProcessStepReferenceType procRef = transitionType.addNewTargetStep();
            RefBaseType ref = procRef.addNewRef();
            ref.setId(transitionBean.getTargetStep().getFullIdPath(false));
        }
        if (ObjectUtil.validCollection(transitionBean.getCondition())) {
            transitionType.setConditionArray(getTextType(transitionBean.getCondition()));
        } else {
            transitionType.addNewCondition();
        }
    }
}
