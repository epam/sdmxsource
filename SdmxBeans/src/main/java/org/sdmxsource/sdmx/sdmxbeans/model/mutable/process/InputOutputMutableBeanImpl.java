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
import org.sdmxsource.sdmx.api.model.beans.process.InputOutputBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.mutable.process.InputOutputMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.AnnotableMutableBeanImpl;


/**
 * The type Input output mutable bean.
 */
public class InputOutputMutableBeanImpl extends AnnotableMutableBeanImpl implements InputOutputMutableBean {
    private static final long serialVersionUID = 1L;

    private String localId;
    private StructureReferenceBean structureReference;

    /**
     * Instantiates a new Input output mutable bean.
     */
    public InputOutputMutableBeanImpl() {
        super(SDMX_STRUCTURE_TYPE.INPUT_OUTPUT);
    }

    /**
     * Instantiates a new Input output mutable bean.
     *
     * @param bean the bean
     */
    public InputOutputMutableBeanImpl(InputOutputBean bean) {
        super(bean);
        this.localId = bean.getLocalId();
        if (bean.getStructureReference() != null) {
            this.structureReference = bean.getStructureReference().createMutableInstance();
        }
    }

    @Override
    public String getLocalId() {
        return localId;
    }

    @Override
    public void setLocalId(String localId) {
        this.localId = localId;
    }

    @Override
    public StructureReferenceBean getStructureReference() {
        return structureReference;
    }

    @Override
    public void setStructureReference(StructureReferenceBean structureReference) {
        this.structureReference = structureReference;
    }
}
