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
package org.sdmxsource.sdmx.sdmxbeans.model.mutable.categoryscheme;

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.categoryscheme.ReportingCategoryBean;
import org.sdmxsource.sdmx.api.model.beans.categoryscheme.ReportingTaxonomyBean;
import org.sdmxsource.sdmx.api.model.mutable.categoryscheme.ReportingCategoryMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.categoryscheme.ReportingTaxonomyMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.categoryscheme.ReportingTaxonomyBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.ItemSchemeMutableBeanImpl;


/**
 * The type Reporting taxonomy mutable bean.
 */
public class ReportingTaxonomyMutableBeanImpl extends ItemSchemeMutableBeanImpl<ReportingCategoryMutableBean> implements ReportingTaxonomyMutableBean {

    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new Reporting taxonomy mutable bean.
     */
    public ReportingTaxonomyMutableBeanImpl() {
        super(SDMX_STRUCTURE_TYPE.REPORTING_TAXONOMY);
    }

    /**
     * Instantiates a new Reporting taxonomy mutable bean.
     *
     * @param bean the bean
     */
    public ReportingTaxonomyMutableBeanImpl(ReportingTaxonomyBean bean) {
        super(bean);

        // make into a Category mutable list
        if (bean.getItems() != null) {
            for (ReportingCategoryBean currentBean : bean.getItems()) {
                this.addReportingCategory(new ReportingCategoryMutableBeanImpl(currentBean));
            }
        }
    }


    @Override
    public ReportingCategoryMutableBean createItem(String id, String name) {
        ReportingCategoryMutableBean rcMut = new ReportingCategoryMutableBeanImpl();
        rcMut.setId(id);
        rcMut.addName("en", name);
        addItem(rcMut);
        return rcMut;
    }

    @Override
    public boolean removeItem(String id) {
        if (id.contains(".")) {
            String[] idArray = id.split("\\.");
            for (ReportingCategoryMutableBean cat : getItems()) {
                if (cat.getId().equals(idArray[0])) {
                    return removeItem(cat, idArray, 1);
                }
            }
            return false;
        }
        return super.removeItem(id);

    }

    private boolean removeItem(ReportingCategoryMutableBean parent, String[] idArray, int pos) {
        String searchId = idArray[pos];
        ReportingCategoryMutableBean removeCat = null;
        for (ReportingCategoryMutableBean cat : parent.getItems()) {
            if (cat.getId().equals(searchId)) {
                if (idArray.length > ++pos) {
                    return removeItem(cat, idArray, ++pos);
                } else {
                    removeCat = cat;
                    break;
                }
            }
        }
        if (removeCat != null) {
            parent.getItems().remove(removeCat);
        }
        return removeCat != null;
    }

    @Override
    public ReportingTaxonomyBean getImmutableInstance() {
        return new ReportingTaxonomyBeanImpl(this);
    }

    /**
     * Add reporting category.
     *
     * @param category the category
     */
    public void addReportingCategory(ReportingCategoryMutableBean category) {
        super.addItem(category);
    }
}
