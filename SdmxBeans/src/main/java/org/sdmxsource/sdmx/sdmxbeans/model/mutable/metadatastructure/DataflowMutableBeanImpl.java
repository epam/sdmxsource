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
package org.sdmxsource.sdmx.sdmxbeans.model.mutable.metadatastructure;

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataflowBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.mutable.datastructure.DataflowMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.datastructure.DataflowBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.MaintainableMutableBeanImpl;


/**
 * The type Dataflow mutable bean.
 */
public class DataflowMutableBeanImpl extends MaintainableMutableBeanImpl implements DataflowMutableBean {
    private static final long serialVersionUID = 1L;

    private StructureReferenceBean dataStructureRef;

    /**
     * Instantiates a new Dataflow mutable bean.
     */
    public DataflowMutableBeanImpl() {
        super(SDMX_STRUCTURE_TYPE.DATAFLOW);
    }

    /**
     * Instantiates a new Dataflow mutable bean.
     *
     * @param bean the bean
     */
    public DataflowMutableBeanImpl(DataflowBean bean) {
        super(bean);
        if (bean.getDataStructureRef() != null) {
            this.dataStructureRef = bean.getDataStructureRef().createMutableInstance();
        }
    }

    /**
     * Constructs a dataflow from a datastrucutre, the agency id, id, version, name and descriptions are copied.
     * The datastructure reference is set to the datastructure.
     *
     * @param dataStructureBean the data structure bean
     */
    public DataflowMutableBeanImpl(DataStructureBean dataStructureBean) {
        super(dataStructureBean);
        super.structureType = SDMX_STRUCTURE_TYPE.DATAFLOW;
        this.dataStructureRef = dataStructureBean.asReference();
    }

    @Override
    public DataflowBean getImmutableInstance() {
        return new DataflowBeanImpl(this);
    }

    @Override
    public StructureReferenceBean getDataStructureRef() {
        return dataStructureRef;
    }

    @Override
    public void setDataStructureRef(StructureReferenceBean dataStructureRef) {
        this.dataStructureRef = dataStructureRef;
    }
}
