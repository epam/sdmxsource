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
package org.sdmxsource.sdmx.api.model.mutable.process;

import org.sdmxsource.sdmx.api.model.beans.process.ProcessBean;
import org.sdmxsource.sdmx.api.model.mutable.base.MaintainableMutableBean;

import java.util.List;


/**
 * The interface Process mutable bean.
 */
public interface ProcessMutableBean extends MaintainableMutableBean {

    /**
     * Gets process steps.
     *
     * @return the process steps
     */
    List<ProcessStepMutableBean> getProcessSteps();

    /**
     * Sets process steps.
     *
     * @param processSteps the process steps
     */
    void setProcessSteps(List<ProcessStepMutableBean> processSteps);

    /**
     * Add process step.
     *
     * @param processStep the process step
     */
    void addProcessStep(ProcessStepMutableBean processStep);

    /**
     * Returns a representation of itself in a bean which can not be modified, modifications to the mutable bean
     * are not reflected in the returned instance of the MaintainableBean.
     *
     * @return the immutable instance
     */
    @Override
    ProcessBean getImmutableInstance();

}
