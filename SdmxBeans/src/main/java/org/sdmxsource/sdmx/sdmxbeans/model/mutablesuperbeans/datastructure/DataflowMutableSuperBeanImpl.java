/**
 * Copyright (c) 2013 Metadata Technology Ltd.
 * <p>
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the GNU Lesser General Public License v 3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * This file is part of the SDMX Component Library.
 * <p>
 * The SDMX Component Library is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 * <p>
 * The SDMX Component Library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser
 * General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License
 * along with The SDMX Component Library If not, see
 * http://www.gnu.org/licenses/lgpl.
 * <p>
 * Contributors:
 * Metadata Technology - initial API and implementation
 */
/**
 *
 */
package org.sdmxsource.sdmx.sdmxbeans.model.mutablesuperbeans.datastructure;

import org.sdmxsource.sdmx.api.model.mutablesuperbeans.datastructure.DataStructureMutableSuperBean;
import org.sdmxsource.sdmx.api.model.mutablesuperbeans.datastructure.DataflowMutableSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.DataflowSuperBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutablesuperbeans.base.MaintainableMutableSuperBeanImpl;


/**
 * The type Dataflow mutable super bean.
 *
 * @author Matt Nelson
 */
public class DataflowMutableSuperBeanImpl extends MaintainableMutableSuperBeanImpl implements DataflowMutableSuperBean {

    private static final long serialVersionUID = 1L;

    private DataStructureMutableSuperBean keyFamily;


    /**
     * Instantiates a new Dataflow mutable super bean.
     *
     * @param dataflow the dataflow
     */
    public DataflowMutableSuperBeanImpl(DataflowSuperBean dataflow) {
        super(dataflow);

        if (dataflow.getDataStructure() != null) {
            this.keyFamily = new DataStructureMutableSuperBeanImpl(dataflow.getDataStructure());
        }
    }

    /**
     * Instantiates a new Dataflow mutable super bean.
     */
    public DataflowMutableSuperBeanImpl() {
    }

    @Override
    public DataStructureMutableSuperBean getDataStructure() {
        return keyFamily;
    }

    @Override
    public void setDataStructure(DataStructureMutableSuperBean keyFamily) {
        this.keyFamily = keyFamily;
    }
}
