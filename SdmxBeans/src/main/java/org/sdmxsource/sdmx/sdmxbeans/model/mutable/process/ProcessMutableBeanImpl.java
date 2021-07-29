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
import org.sdmxsource.sdmx.api.model.beans.process.ProcessBean;
import org.sdmxsource.sdmx.api.model.beans.process.ProcessStepBean;
import org.sdmxsource.sdmx.api.model.mutable.process.ProcessMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.process.ProcessStepMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.process.ProcessBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.MaintainableMutableBeanImpl;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Process mutable bean.
 */
public class ProcessMutableBeanImpl extends MaintainableMutableBeanImpl implements ProcessMutableBean {
    private static final long serialVersionUID = 1L;

    private List<ProcessStepMutableBean> processSteps = new ArrayList<ProcessStepMutableBean>();

    /**
     * Instantiates a new Process mutable bean.
     */
    public ProcessMutableBeanImpl() {
        super(SDMX_STRUCTURE_TYPE.PROCESS);
    }

    /**
     * Instantiates a new Process mutable bean.
     *
     * @param bean the bean
     */
    public ProcessMutableBeanImpl(ProcessBean bean) {
        super(bean);

        // make into mutable bean list
        if (bean.getProcessSteps() != null) {
            for (ProcessStepBean currentBean : bean.getProcessSteps()) {
                this.addProcessStep(new ProcessStepMutableBeanImpl(currentBean));
            }
        }
    }

    @Override
    public ProcessBean getImmutableInstance() {
        return new ProcessBeanImpl(this);
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
    public void addProcessStep(ProcessStepMutableBean processStep) {
        this.processSteps.add(processStep);
    }

}
