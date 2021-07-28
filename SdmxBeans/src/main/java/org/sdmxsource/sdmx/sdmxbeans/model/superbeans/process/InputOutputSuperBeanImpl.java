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

import org.sdmxsource.sdmx.api.model.beans.base.IdentifiableBean;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.beans.process.InputOutputBean;
import org.sdmxsource.sdmx.api.model.superbeans.process.InputOutputSuperBean;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.base.AnnotableSuperBeanImpl;

import java.util.Set;


/**
 * The type Input output super bean.
 */
public class InputOutputSuperBeanImpl extends AnnotableSuperBeanImpl implements InputOutputSuperBean {

    private static final long serialVersionUID = -7130852167819727964L;

    private InputOutputBean inputOutputBean;
    private String localId;
    private IdentifiableBean structure;

    /**
     * Instantiates a new Input output super bean.
     *
     * @param inputOutputBean the input output bean
     * @param structure       the structure
     */
    public InputOutputSuperBeanImpl(InputOutputBean inputOutputBean, IdentifiableBean structure) {
        super(inputOutputBean);
        this.inputOutputBean = inputOutputBean;
        this.localId = inputOutputBean.getLocalId();
        this.structure = structure;
    }


    @Override
    public InputOutputBean getBuiltFrom() {
        return this.inputOutputBean;
    }

    @Override
    public String getLocalId() {
        return this.localId;
    }

    @Override
    public IdentifiableBean getStructure() {
        return this.structure;
    }

    @Override
    public Set<MaintainableBean> getCompositeBeans() {
        Set<MaintainableBean> returnSet = super.getCompositeBeans();
        if (structure != null) {
            returnSet.add(structure.getMaintainableParent());
        }
        return returnSet;
    }
}
