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
package org.sdmxsource.sdmx.sdmxbeans.model.mutablesuperbeans.codelist;

import org.sdmxsource.sdmx.api.model.beans.reference.MaintainableRefBean;
import org.sdmxsource.sdmx.api.model.mutable.codelist.CodeMutableBean;
import org.sdmxsource.sdmx.api.model.mutablesuperbeans.codelist.CodeRefMutableSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.codelist.HierarchicalCodeSuperBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.codelist.CodeMutableBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutablesuperbeans.base.IdentifiableMutableSuperBeanImpl;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Code ref mutable super bean.
 */
public class CodeRefMutableSuperBeanImpl extends IdentifiableMutableSuperBeanImpl implements CodeRefMutableSuperBean {
    private static final long serialVersionUID = -1845045383400597647L;
    private CodeMutableBean code;
    private MaintainableRefBean codelistRef;
    private List<CodeRefMutableSuperBean> codeRefs = new ArrayList<CodeRefMutableSuperBean>();

    /**
     * Instantiates a new Code ref mutable super bean.
     */
    public CodeRefMutableSuperBeanImpl() {
    }

    /**
     * Instantiates a new Code ref mutable super bean.
     *
     * @param codeRefSuperBean the code ref super bean
     */
    public CodeRefMutableSuperBeanImpl(HierarchicalCodeSuperBean codeRefSuperBean) {
        super(codeRefSuperBean);
        code = new CodeMutableBeanImpl(codeRefSuperBean.getCode());
        codelistRef = codeRefSuperBean.getCode().getMaintainableParent().asReference().getMaintainableReference();
        for (HierarchicalCodeSuperBean currentCodeRef : codeRefSuperBean.getCodeRefs()) {
            codeRefs.add(new CodeRefMutableSuperBeanImpl(currentCodeRef));
        }
    }

    /**
     * Gets codelist ref.
     *
     * @return the codelist ref
     */
    public MaintainableRefBean getCodelistRef() {
        return codelistRef;
    }

    /**
     * Sets codelist ref.
     *
     * @param codelistRef the codelist ref
     */
    public void setCodelistRef(MaintainableRefBean codelistRef) {
        this.codelistRef = codelistRef;
    }

    @Override
    public CodeMutableBean getCode() {
        return code;
    }

    @Override
    public void setCode(CodeMutableBean code) {
        this.code = code;
    }

    @Override
    public List<CodeRefMutableSuperBean> getCodeRefs() {
        return codeRefs;
    }

    @Override
    public void setCodeRefs(List<CodeRefMutableSuperBean> codeRefs) {
        this.codeRefs = codeRefs;
    }
}
