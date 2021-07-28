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
import org.sdmxsource.sdmx.api.model.beans.categoryscheme.CategorisationBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.mutable.categoryscheme.CategorisationMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.categoryscheme.CategorisationBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.MaintainableMutableBeanImpl;


/**
 * The type Categorisation mutable bean.
 */
public class CategorisationMutableBeanImpl extends MaintainableMutableBeanImpl implements CategorisationMutableBean {
    private static final long serialVersionUID = 129560498625554240L;
    private StructureReferenceBean categoryRefBean;
    private StructureReferenceBean structureReference;

    /**
     * Instantiates a new Categorisation mutable bean.
     */
    public CategorisationMutableBeanImpl() {
        super(SDMX_STRUCTURE_TYPE.CATEGORISATION);
    }

    /**
     * Instantiates a new Categorisation mutable bean.
     *
     * @param bean the bean
     */
    public CategorisationMutableBeanImpl(CategorisationBean bean) {
        super(bean);

        if (bean.getCategoryReference() != null) {
            this.categoryRefBean = bean.getCategoryReference().createMutableInstance();
        }
        if (bean.getStructureReference() != null) {
            this.structureReference = bean.getStructureReference().createMutableInstance();
        }
    }

    @Override
    public StructureReferenceBean getCategoryReference() {
        return categoryRefBean;
    }

    @Override
    public void setCategoryReference(StructureReferenceBean categoryRef) {
        this.categoryRefBean = categoryRef;
    }

    @Override
    public StructureReferenceBean getStructureReference() {
        return structureReference;
    }

    @Override
    public void setStructureReference(StructureReferenceBean structureReference) {
        this.structureReference = structureReference;
    }

    @Override
    public CategorisationBean getImmutableInstance() {
        return new CategorisationBeanImpl(this);
    }

}
