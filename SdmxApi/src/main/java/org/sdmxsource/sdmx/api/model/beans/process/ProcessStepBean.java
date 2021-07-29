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
package org.sdmxsource.sdmx.api.model.beans.process;

import org.sdmxsource.sdmx.api.model.beans.base.NameableBean;

import java.util.List;


/**
 * Represents an SDMX Process Step
 *
 * @author Matt Nelson
 */
public interface ProcessStepBean extends NameableBean {

    /**
     * Returns the inputs of this Process Step as a copy of the underlying list.
     * <p>
     * Returns an empty list if no inputs exist
     *
     * @return input
     */
    List<InputOutputBean> getInput();

    /**
     * Returns the outputs of this process step as a copy of the underlying list.
     * <p>
     * Returns an empty list if no outputs exist
     *
     * @return output
     */
    List<InputOutputBean> getOutput();


    /**
     * Gets computation.
     *
     * @return the computation
     */
    ComputationBean getComputation();

    /**
     * Returns TransitionBeans as a copy of the underlying list.
     * <p>
     * Returns an empty list if no TransitionBean exist
     *
     * @return transitions
     */
    List<TransitionBean> getTransitions();

    /**
     * Returns the child Process Steps as a copy of the underlying list.
     * <p>
     * Returns an empty list if no child process steps exist
     *
     * @return process steps
     */
    List<ProcessStepBean> getProcessSteps();

}
