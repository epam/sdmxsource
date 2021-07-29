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
package org.sdmxsource.sdmx.sdmxbeans.model.superbeans.datastructure;

import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataflowBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.DataStructureSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.datastructure.DataflowSuperBean;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.base.MaintainableSuperBeanImpl;

import java.util.Set;


/**
 * The type Dataflow super bean.
 *
 * @author Matt Nelson
 */
public class DataflowSuperBeanImpl extends MaintainableSuperBeanImpl implements DataflowSuperBean {
    private static final long serialVersionUID = 1L;

    private DataStructureSuperBean dataStructure;
    private DataflowBean dataflowBean;

    /**
     * Instantiates a new Dataflow super bean.
     *
     * @param dataflow  the dataflow
     * @param keyFamily the key family
     */
    public DataflowSuperBeanImpl(DataflowBean dataflow, DataStructureSuperBean keyFamily) {
        super(dataflow);
        if (keyFamily == null) {
            throw new SdmxSemmanticException("DataflowSuperBeanImpl requires DataStructureSuperBean");
        }
        this.dataStructure = keyFamily;
        this.dataflowBean = dataflow;

    }

    /* (non-Javadoc)
     * @see org.sdmxsource.dataweb.external.model.Dataflow#getKeyFamilyRef()
     */
    @Override
    public DataStructureSuperBean getDataStructure() {
        return this.dataStructure;
    }

    @Override
    public DataflowBean getBuiltFrom() {
        return dataflowBean;
    }

    @Override
    public Set<MaintainableBean> getCompositeBeans() {
        Set<MaintainableBean> returnSet = super.getCompositeBeans();
        returnSet.add(dataflowBean);
        returnSet.addAll(dataStructure.getCompositeBeans());
        return returnSet;
    }
}
