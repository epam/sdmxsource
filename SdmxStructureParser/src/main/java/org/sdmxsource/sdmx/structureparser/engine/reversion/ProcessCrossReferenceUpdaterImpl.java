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
package org.sdmxsource.sdmx.structureparser.engine.reversion;

import org.sdmxsource.sdmx.api.model.beans.process.ProcessBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.mutable.process.InputOutputMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.process.ProcessMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.process.ProcessStepMutableBean;
import org.sdmxsource.sdmx.structureparser.engine.ProcessCrossReferenceUpdater;

import java.util.List;
import java.util.Map;


/**
 * The type Process cross reference updater.
 */
public class ProcessCrossReferenceUpdaterImpl implements ProcessCrossReferenceUpdater {

    @Override
    public ProcessBean updateReferences(ProcessBean maintianable, Map<StructureReferenceBean, StructureReferenceBean> updateReferences, String newVersionNumber) {
        ProcessMutableBean processMutableBean = maintianable.getMutableInstance();
        processMutableBean.setVersion(newVersionNumber);
        updateProcessSteps(processMutableBean.getProcessSteps(), updateReferences);
        return processMutableBean.getImmutableInstance();
    }

    private void updateProcessSteps(List<ProcessStepMutableBean> processSteps, Map<StructureReferenceBean, StructureReferenceBean> updateReferences) {
        if (processSteps != null) {
            for (ProcessStepMutableBean currentProcessStep : processSteps) {
                updateProcessSteps(currentProcessStep.getProcessSteps(), updateReferences);
                updateInputOutput(currentProcessStep.getInput(), updateReferences);
                updateInputOutput(currentProcessStep.getOutput(), updateReferences);
            }
        }
    }

    private void updateInputOutput(List<InputOutputMutableBean> inputOutput, Map<StructureReferenceBean, StructureReferenceBean> updateReferences) {
        if (inputOutput != null) {
            for (InputOutputMutableBean currentInputOutput : inputOutput) {
                StructureReferenceBean updateTo = updateReferences.get(currentInputOutput.getStructureReference());
                if (updateTo != null) {
                    currentInputOutput.setStructureReference(updateTo);
                }
            }
        }
    }

}
