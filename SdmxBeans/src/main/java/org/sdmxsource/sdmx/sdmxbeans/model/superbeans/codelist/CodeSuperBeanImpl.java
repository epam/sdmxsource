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
package org.sdmxsource.sdmx.sdmxbeans.model.superbeans.codelist;

import org.sdmxsource.sdmx.api.model.beans.codelist.CodeBean;
import org.sdmxsource.sdmx.api.model.superbeans.codelist.CodeSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.codelist.CodelistSuperBean;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.base.ItemSuperBeanImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * The type Code super bean.
 *
 * @author Matt Nelson
 */
public class CodeSuperBeanImpl extends ItemSuperBeanImpl<CodelistSuperBean> implements CodeSuperBean {
    private static final long serialVersionUID = 1L;

    private List<CodeSuperBean> children = new ArrayList<CodeSuperBean>();
    private CodeSuperBean parent;

    /**
     * Instantiates a new Code super bean.
     *
     * @param codelist     the codelist
     * @param code         the code
     * @param codeChildMap the code child map
     * @param parent       the parent
     */
    public CodeSuperBeanImpl(CodelistSuperBean codelist,
                             CodeBean code,
                             Map<CodeBean, List<CodeBean>> codeChildMap,
                             CodeSuperBean parent) {
        super(code, codelist);

        List<CodeBean> childCodes = codeChildMap.get(code);
        if (childCodes != null) {
            for (CodeBean currentChild : childCodes) {
                CodeSuperBean child = new CodeSuperBeanImpl(codelist, currentChild, codeChildMap, this);
                children.add(child);
            }
        }
        this.parent = parent;
    }

    /* (non-Javadoc)
     * @see org.sdmxsource.dataweb.external.model.Hierarchical#getChildren()
     */
    @Override
    public List<CodeSuperBean> getChildren() {
        return new ArrayList<CodeSuperBean>(children);
    }

    /* (non-Javadoc)
     * @see org.sdmxsource.dataweb.external.model.Hierarchical#getParent()
     */
    @Override
    public CodeSuperBean getParent() {
        return this.parent;
    }


    /* (non-Javadoc)
     * @see org.sdmxsource.dataweb.external.model.Hierarchical#hasChildren()
     */
    @Override
    public boolean hasChildren() {
        return this.children != null && this.children.size() > 0;
    }

    /* (non-Javadoc)
     * @see org.sdmxsource.dataweb.external.model.Hierarchical#hasParent()
     */
    @Override
    public boolean hasParent() {
        return this.parent != null;
    }

    @Override
    public CodeBean getBuiltFrom() {
        return (CodeBean) super.getBuiltFrom();
    }
}
