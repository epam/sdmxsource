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
import org.sdmxsource.sdmx.api.model.beans.codelist.CodelistBean;
import org.sdmxsource.sdmx.api.model.mutable.codelist.CodeMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.codelist.CodelistMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.codelist.CodelistBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.ItemSchemeMutableBeanImpl;
import org.sdmxsource.util.ObjectUtil;


/**
 * The type Codelist mutable bean.
 */
public class CodelistMutableBeanImpl extends ItemSchemeMutableBeanImpl<CodeMutableBean> implements CodelistMutableBean {

    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new Codelist mutable bean.
     */
    public CodelistMutableBeanImpl() {
        super(SDMX_STRUCTURE_TYPE.CODE_LIST);
    }

    /**
     * Instantiates a new Codelist mutable bean.
     *
     * @param reader the reader
     */
    public CodelistMutableBeanImpl(SdmxReader reader) {
        super(SDMX_STRUCTURE_TYPE.CODE_LIST);
        validateRootElement(reader);
        buildMaintainableAttributes(reader);

        reader.moveNextElement();
        while (processReader(reader)) {
            reader.moveNextElement();
        }
    }

    /**
     * Instantiates a new Codelist mutable bean.
     *
     * @param structureType the structure type
     */
    public CodelistMutableBeanImpl(SDMX_STRUCTURE_TYPE structureType) {
        super(structureType);
    }

    /**
     * Instantiates a new Codelist mutable bean.
     *
     * @param bean the bean
     */
    public CodelistMutableBeanImpl(CodelistBean bean) {
        super(bean);

        // change Codelist beans in Mutable Codelist beans
        if (bean.getItems() != null) {
            for (CodeBean currentBean : bean.getItems()) {
                this.addItem(new CodeMutableBeanImpl(currentBean));
            }
        }
    }

    @Override
    public CodeMutableBean createItem(String id, String name) {
        CodeMutableBean code = new CodeMutableBeanImpl();
        code.setId(id);
        code.addName("en", name);
        addItem(code);
        return code;
    }

    @Override
    protected boolean processReader(SdmxReader reader) {
        if (super.processReader(reader)) {
            return true;
        }
        if (reader.getCurrentElement().equals("Code")) {
            processCodes(reader);
        }
        return false;
    }

    private void processCodes(SdmxReader reader) {
        while (reader.getCurrentElement().equals("Code")) {
            CodeMutableBean newCode = new CodeMutableBeanImpl(reader);
            if (newCode.getNames() == null || newCode.getNames().size() == 0 || !ObjectUtil.validString(newCode.getNames().get(0).getValue())) {
            }
            addItem(newCode);
        }
    }

    private SDMX_STRUCTURE_TYPE validateRootElement(SdmxReader reader) {
        if (!reader.getCurrentElement().equals("Codelist")) {
            throw new SdmxSemmanticException("Can not construct codelist - expecting 'Codelist' Element in SDMX, actual element:" + reader.getCurrentElementValue());
        }
        return SDMX_STRUCTURE_TYPE.CODE_LIST;
    }

    @Override
    public CodeMutableBean getCodeById(String id) {
        for (CodeMutableBean currentCode : getItems()) {
            if (currentCode.getId().equals(id)) {
                return currentCode;
            }
        }
        return null;
    }

    @Override
    public CodelistBean getImmutableInstance() {
        return new CodelistBeanImpl(this);
    }

    @Override
    public void setIsPartial(boolean isPartial) {
        super.setPartial(isPartial);
    }

}
