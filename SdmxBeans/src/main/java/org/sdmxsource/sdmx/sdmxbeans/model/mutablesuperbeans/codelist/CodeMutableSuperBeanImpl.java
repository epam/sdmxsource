/**
 * Copyright (c) 2013 Metadata Technology Ltd.
 * <p>
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the GNU Lesser General Public License v 3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * This file is part of the SDMX Component Library.
 * <p>
 * The SDMX Component Library is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 * <p>
 * The SDMX Component Library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser
 * General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License
 * along with The SDMX Component Library If not, see
 * http://www.gnu.org/licenses/lgpl.
 * <p>
 * Contributors:
 * Metadata Technology - initial API and implementation
 */
/**
 *
 */
package org.sdmxsource.sdmx.sdmxbeans.model.mutablesuperbeans.codelist;

import org.sdmxsource.sdmx.api.model.mutablesuperbeans.codelist.CodeMutableSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.codelist.CodeSuperBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutablesuperbeans.base.ItemMutableSuperBeanImpl;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Code mutable super bean.
 *
 * @author Matt Nelson
 */
public class CodeMutableSuperBeanImpl extends ItemMutableSuperBeanImpl<CodelistMutableSuperBeanImpl> implements CodeMutableSuperBean {

    private static final long serialVersionUID = 1L;

    private List<CodeMutableSuperBean> children;
    private CodeMutableSuperBean parent;
    private String value;

    /**
     * Instantiates a new Code mutable super bean.
     *
     * @param codelist the codelist
     * @param code     the code
     * @param parent   the parent
     */
    public CodeMutableSuperBeanImpl(CodelistMutableSuperBeanImpl codelist, CodeSuperBean code, CodeMutableSuperBeanImpl parent) {
        super(codelist, code);
        if (code.hasChildren()) {
            children = new ArrayList<CodeMutableSuperBean>();
            for (CodeSuperBean currentBean : code.getChildren()) {
                children.add(new CodeMutableSuperBeanImpl(codelist, currentBean, this));
            }
        }
        this.parent = parent;
    }

    /**
     * Instantiates a new Code mutable super bean.
     */
    public CodeMutableSuperBeanImpl() {
    }

    @Override
    public List<CodeMutableSuperBean> getChildren() {
        return children;
    }

    @Override
    public void setChildren(List<CodeMutableSuperBean> children) {
        this.children = children;
    }

    @Override
    public CodeMutableSuperBean getParent() {
        return parent;
    }

    @Override
    public void setParent(CodeMutableSuperBean parent) {
        this.parent = parent;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }
}
