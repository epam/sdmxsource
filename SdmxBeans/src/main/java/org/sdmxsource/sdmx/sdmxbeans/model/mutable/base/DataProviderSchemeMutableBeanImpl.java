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
import org.sdmxsource.sdmx.api.model.beans.base.DataProviderBean;
import org.sdmxsource.sdmx.api.model.beans.base.DataProviderSchemeBean;
import org.sdmxsource.sdmx.api.model.mutable.base.DataProviderMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.base.DataProviderSchemeMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.DataProviderSchemeBeanImpl;


/**
 * The type Data provider scheme mutable bean.
 */
public class DataProviderSchemeMutableBeanImpl extends ItemSchemeMutableBeanImpl<DataProviderMutableBean> implements DataProviderSchemeMutableBean {
    private static final long serialVersionUID = 1L;


    /**
     * Instantiates a new Data provider scheme mutable bean.
     *
     * @param bean the bean
     */
    public DataProviderSchemeMutableBeanImpl(DataProviderSchemeBean bean) {
        super(bean);
        for (DataProviderBean dataProviderBean : bean.getItems()) {
            this.addItem(new DataProviderMutableBeanImpl(dataProviderBean));
        }
    }


    /**
     * Instantiates a new Data provider scheme mutable bean.
     */
    public DataProviderSchemeMutableBeanImpl() {
        super(SDMX_STRUCTURE_TYPE.DATA_PROVIDER_SCHEME);
    }

    @Override
    public DataProviderMutableBean createItem(String id, String name) {
        DataProviderMutableBean dpBean = new DataProviderMutableBeanImpl();
        dpBean.setId(id);
        dpBean.addName("en", name);
        addItem(dpBean);
        return dpBean;
    }

    @Override
    public DataProviderSchemeBean getImmutableInstance() {
        return new DataProviderSchemeBeanImpl(this);
    }
}
