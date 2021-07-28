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
package org.sdmxsource.sdmx.sdmxbeans.model.mutable.conceptscheme;

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.conceptscheme.ConceptBean;
import org.sdmxsource.sdmx.api.model.beans.conceptscheme.ConceptSchemeBean;
import org.sdmxsource.sdmx.api.model.mutable.conceptscheme.ConceptMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.conceptscheme.ConceptSchemeMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.conceptscheme.ConceptSchemeBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.ItemSchemeMutableBeanImpl;


/**
 * The type Concept scheme mutable bean.
 */
public class ConceptSchemeMutableBeanImpl extends ItemSchemeMutableBeanImpl<ConceptMutableBean>
        implements ConceptSchemeMutableBean {

    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new Concept scheme mutable bean.
     */
    public ConceptSchemeMutableBeanImpl() {
        super(SDMX_STRUCTURE_TYPE.CONCEPT_SCHEME);
    }

    /**
     * Instantiates a new Concept scheme mutable bean.
     *
     * @param bean the bean
     */
    public ConceptSchemeMutableBeanImpl(ConceptSchemeBean bean) {
        super(bean);

        if (bean.getItems() != null) {
            for (ConceptBean currentBean : bean.getItems()) {
                this.addItem(new ConceptMutableBeanImpl(currentBean));
            }
        }
    }


    @Override
    public ConceptMutableBean createItem(String id, String name) {
        ConceptMutableBean concept = new ConceptMutableBeanImpl();
        concept.setId(id);
        concept.addName("en", name);
        addItem(concept);
        return concept;
    }

    @Override
    public ConceptSchemeBean getImmutableInstance() {
        return new ConceptSchemeBeanImpl(this);
    }
}
