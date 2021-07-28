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
package org.sdmxsource.sdmx.sdmxbeans.model.mutable.reference;

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.codelist.CodelistRefBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.mutable.reference.CodelistRefMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.MutableBeanImpl;


/**
 * The type Codelist ref mutable bean.
 */
public class CodelistRefMutableBeanImpl extends MutableBeanImpl implements CodelistRefMutableBean {
    private static final long serialVersionUID = 1L;

    private String alias;
    private StructureReferenceBean structureReference;

    /**
     * Instantiates a new Codelist ref mutable bean.
     */
    public CodelistRefMutableBeanImpl() {
        super(SDMX_STRUCTURE_TYPE.CODE_LIST_REF);
    }

    /**
     * Instantiates a new Codelist ref mutable bean.
     *
     * @param bean the bean
     */
    public CodelistRefMutableBeanImpl(CodelistRefBean bean) {
        super(bean);
        this.alias = bean.getAlias();
        this.structureReference = bean.getCodelistReference().createMutableInstance();
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public StructureReferenceBean getCodelistReference() {
        return structureReference;
    }

    @Override
    public void setCodelistReference(StructureReferenceBean structureReference) {
        this.structureReference = structureReference;
    }
}
