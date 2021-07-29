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
package org.sdmxsource.sdmx.sdmxbeans.model.superbeans.categoryscheme;

import org.sdmxsource.sdmx.api.exception.CrossReferenceException;
import org.sdmxsource.sdmx.api.manager.retrieval.IdentifiableRetrievalManager;
import org.sdmxsource.sdmx.api.model.beans.base.IdentifiableBean;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.beans.categoryscheme.CategorisationBean;
import org.sdmxsource.sdmx.api.model.beans.categoryscheme.CategoryBean;
import org.sdmxsource.sdmx.api.model.superbeans.categoryscheme.CategorisationSuperBean;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.base.MaintainableSuperBeanImpl;

import java.util.Set;


/**
 * The type Categorisation super bean.
 */
public class CategorisationSuperBeanImpl extends MaintainableSuperBeanImpl implements CategorisationSuperBean {
    private static final long serialVersionUID = 1L;

    private CategorisationBean categorisation;
    private IdentifiableBean structure;
    private CategoryBean category;

    /**
     * Instantiates a new Categorisation super bean.
     *
     * @param categorisation the categorisation
     * @param retMan         the ret man
     */
    public CategorisationSuperBeanImpl(CategorisationBean categorisation, IdentifiableRetrievalManager retMan) {
        super(categorisation);
        category = retMan.getIdentifiableBean(categorisation.getCategoryReference(), CategoryBean.class);
        structure = retMan.getIdentifiableBean(categorisation.getStructureReference(), IdentifiableBean.class);

        if (structure == null) {
            throw new CrossReferenceException(categorisation.getStructureReference());
        }
        if (category == null) {
            throw new CrossReferenceException(categorisation.getCategoryReference());
        }
    }

    @Override
    public IdentifiableBean getStructure() {
        return structure;
    }

    @Override
    public CategoryBean getCategory() {
        return category;
    }

    @Override
    public Set<MaintainableBean> getCompositeBeans() {
        Set<MaintainableBean> returnSet = super.getCompositeBeans();
        returnSet.add(categorisation);
        returnSet.add(structure.getMaintainableParent());
        returnSet.add(category.getMaintainableParent());
        return returnSet;
    }

    @Override
    public CategorisationBean getBuiltFrom() {
        return categorisation;
    }
}
