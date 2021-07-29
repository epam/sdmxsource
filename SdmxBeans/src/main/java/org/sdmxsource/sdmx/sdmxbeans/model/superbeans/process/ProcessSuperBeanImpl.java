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
import org.sdmxsource.sdmx.api.model.beans.process.ProcessBean;
import org.sdmxsource.sdmx.api.model.superbeans.process.ProcessStepSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.process.ProcessSuperBean;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.base.MaintainableSuperBeanImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * The type Process super bean.
 */
public class ProcessSuperBeanImpl extends MaintainableSuperBeanImpl implements ProcessSuperBean {

    private static final long serialVersionUID = -8679939983498220784L;

    private ProcessBean process;
    private List<ProcessStepSuperBean> processSteps;

    /**
     * Instantiates a new Process super bean.
     *
     * @param process      the process
     * @param processSteps the process steps
     */
    public ProcessSuperBeanImpl(ProcessBean process,
                                List<ProcessStepSuperBean> processSteps) {
        super(process);
        this.process = process;
        this.processSteps = processSteps;
    }

    @Override
    public List<ProcessStepSuperBean> getProcessSteps() {
        return new ArrayList<ProcessStepSuperBean>(this.processSteps);
    }

    @Override
    public ProcessBean getProcess() {
        return this.process;
    }

    @Override
    public Set<MaintainableBean> getCompositeBeans() {
        Set<MaintainableBean> returnSet = super.getCompositeBeans();
        returnSet.add(process);
        addToSet(returnSet, processSteps);
        return returnSet;
    }
}
