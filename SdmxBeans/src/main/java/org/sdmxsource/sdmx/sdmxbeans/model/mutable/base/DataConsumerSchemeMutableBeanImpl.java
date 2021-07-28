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
package org.sdmxsource.sdmx.sdmxbeans.model.mutable.base;

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.base.DataConsumerBean;
import org.sdmxsource.sdmx.api.model.beans.base.DataConsumerSchemeBean;
import org.sdmxsource.sdmx.api.model.mutable.base.DataConsumerMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.base.DataConsumerSchemeMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.DataConsumerSchemeBeanImpl;


/**
 * The type Data consumer scheme mutable bean.
 */
public class DataConsumerSchemeMutableBeanImpl extends ItemSchemeMutableBeanImpl<DataConsumerMutableBean> implements DataConsumerSchemeMutableBean {

    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new Data consumer scheme mutable bean.
     *
     * @param bean the bean
     */
    public DataConsumerSchemeMutableBeanImpl(DataConsumerSchemeBean bean) {
        super(bean);
        for (DataConsumerBean dataConsumerBean : bean.getItems()) {
            this.addItem(new DataConsumerMutableBeanImpl(dataConsumerBean));
        }
    }


    /**
     * Instantiates a new Data consumer scheme mutable bean.
     */
    public DataConsumerSchemeMutableBeanImpl() {
        super(SDMX_STRUCTURE_TYPE.DATA_CONSUMER_SCHEME);
    }

    @Override
    public DataConsumerMutableBean createItem(String id, String name) {
        DataConsumerMutableBean dcBean = new DataConsumerMutableBeanImpl();
        dcBean.setId(id);
        dcBean.addName("en", name);
        addItem(dcBean);
        return dcBean;
    }

    @Override
    public DataConsumerSchemeBean getImmutableInstance() {
        return new DataConsumerSchemeBeanImpl(this);
    }
}
