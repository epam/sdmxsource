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
package org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v2;

import org.sdmx.resources.sdmxml.schemas.v20.structure.ProcessStepType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.ProcessType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.TransitionType;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.beans.process.InputOutputBean;
import org.sdmxsource.sdmx.api.model.beans.process.ProcessBean;
import org.sdmxsource.sdmx.api.model.beans.process.ProcessStepBean;
import org.sdmxsource.sdmx.api.model.beans.process.TransitionBean;
import org.sdmxsource.sdmx.sdmxbeans.util.XmlBeansEnumUtil;
import org.sdmxsource.util.ObjectUtil;


/**
 * The type Process xml bean builder.
 */
public class ProcessXmlBeanBuilder extends AbstractBuilder {

    /**
     * Build process type.
     *
     * @param buildFrom the build from
     * @return the process type
     * @throws SdmxException the sdmx exception
     */
    public ProcessType build(ProcessBean buildFrom) throws SdmxException {
        ProcessType builtObj = ProcessType.Factory.newInstance();

        //MAINTAINABLE ATTRIBUTES
        if (validString(buildFrom.getAgencyId())) {
            builtObj.setAgencyID(buildFrom.getAgencyId());
        }
        if (validString(buildFrom.getId())) {
            builtObj.setId(buildFrom.getId());
        }
        if (buildFrom.getUri() != null) {
            builtObj.setUri(buildFrom.getUri().toString());
        } else if (buildFrom.getStructureURL() != null) {
            builtObj.setUri(buildFrom.getStructureURL().toString());
        } else if (buildFrom.getServiceURL() != null) {
            builtObj.setUri(buildFrom.getStructureURL().toString());
        }
        if (validString(buildFrom.getUrn())) {
            builtObj.setUrn(buildFrom.getUrn());
        }
        if (validString(buildFrom.getVersion())) {
            builtObj.setVersion(buildFrom.getVersion());
        }
        if (buildFrom.getStartDate() != null) {
            builtObj.setValidFrom(buildFrom.getStartDate());
        }
        if (buildFrom.getEndDate() != null) {
            builtObj.setValidTo(buildFrom.getEndDate());
        }
        if (validCollection(buildFrom.getNames())) {
            builtObj.setNameArray(getTextType(buildFrom.getNames()));
        }
        if (validCollection(buildFrom.getDescriptions())) {
            builtObj.setDescriptionArray(getTextType(buildFrom.getDescriptions()));
        }
        if (hasAnnotations(buildFrom)) {
            builtObj.setAnnotations(getAnnotationsType(buildFrom));
        }
        if (buildFrom.isExternalReference().isSet()) {
            builtObj.setIsExternalReference(buildFrom.isExternalReference().isTrue());
        }
        if (buildFrom.isFinal().isSet()) {
            builtObj.setIsFinal(buildFrom.isFinal().isTrue());
        }

        for (ProcessStepBean processStep : buildFrom.getProcessSteps()) {
            ProcessStepType processStepType = builtObj.addNewProcessStep();
            processProcessStep(processStep, processStepType);
        }

        return builtObj;
    }

    private void processProcessStep(ProcessStepBean buildFrom, ProcessStepType builtObj) {
        if (validString(buildFrom.getId())) {
            builtObj.setId(buildFrom.getId());
        }

        if (validCollection(buildFrom.getNames())) {
            builtObj.setNameArray(getTextType(buildFrom.getNames()));
        }

        if (validCollection(buildFrom.getInput())) {
            for (InputOutputBean ioBean : buildFrom.getInput()) {
                if (ioBean.getStructureReference() != null) {
                    builtObj.addInput(XmlBeansEnumUtil.getSdmxObjectIdType(ioBean.getStructureReference().getTargetReference()));
                }
            }
        }
        if (validCollection(buildFrom.getOutput())) {
            for (InputOutputBean ioBean : buildFrom.getOutput()) {
                if (ioBean.getStructureReference() != null) {
                    builtObj.addOutput(XmlBeansEnumUtil.getSdmxObjectIdType(ioBean.getStructureReference().getTargetReference()));
                }
            }
        }
        if (buildFrom.getComputation() != null) {
            builtObj.setComputationArray(getTextType(buildFrom.getComputation().getDescription()));
        }
        if (validCollection(buildFrom.getTransitions())) {
            for (TransitionBean transition : buildFrom.getTransitions()) {
                TransitionType transitionType = builtObj.addNewTransition();
                if (transition.getTargetStep() != null) {
                    transitionType.setTargetStep(transition.getTargetStep().getId());
                }
                if (ObjectUtil.validCollection(transition.getCondition())) {
                    transitionType.setCondition(getTextType(transition.getCondition().get(0)));
                }
            }
        }
        if (validCollection(buildFrom.getProcessSteps())) {
            for (ProcessStepBean processStep : buildFrom.getProcessSteps()) {
                ProcessStepType newProcessStepType = builtObj.addNewProcessStep();
                processProcessStep(processStep, newProcessStepType);
            }
        }
        if (hasAnnotations(buildFrom)) {
            builtObj.setAnnotations(getAnnotationsType(buildFrom));
        }
    }
}
