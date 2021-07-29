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
package org.sdmxsource.sdmx.sdmxbeans.model.mutable.codelist;

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.SdmxReader;
import org.sdmxsource.sdmx.api.model.beans.codelist.CodeBean;
import org.sdmxsource.sdmx.api.model.mutable.codelist.CodeMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.ItemMutableBeanImpl;

/**
 * The type Code mutable bean.
 */
public class CodeMutableBeanImpl extends ItemMutableBeanImpl implements CodeMutableBean {
    private static final long serialVersionUID = 1L;

    private String parentCode;

    /**
     * Instantiates a new Code mutable bean.
     */
    public CodeMutableBeanImpl() {
        super(SDMX_STRUCTURE_TYPE.CODE);
    }

    /**
     * Instantiates a new Code mutable bean.
     *
     * @param bean the bean
     */
    public CodeMutableBeanImpl(CodeBean bean) {
        super(bean);
        this.parentCode = bean.getParentCode();
    }

    /**
     * Instantiates a new Code mutable bean.
     *
     * @param reader the reader
     */
    public CodeMutableBeanImpl(SdmxReader reader) {
        super(SDMX_STRUCTURE_TYPE.CODE);
        buildIdentifiableAttributes(reader);

        reader.moveNextElement();
        while (processReader(reader)) {
            reader.moveNextElement();
        }
    }

    @Override
    protected boolean processReader(SdmxReader reader) {
        if (super.processReader(reader)) {
            return true;
        }
        if (reader.getCurrentElement().equals("Parent")) {
            reader.moveNextElement();
            if (!reader.getCurrentElement().equals("Ref")) {
                throw new SdmxSemmanticException("Expected 'Ref' as a child node of Parent");
            }
            parentCode = reader.getAttributeValue("id", true);
            return true;
        }
        return false;
    }

    @Override
    public String getParentCode() {
        return parentCode;
    }

    @Override
    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }
}
