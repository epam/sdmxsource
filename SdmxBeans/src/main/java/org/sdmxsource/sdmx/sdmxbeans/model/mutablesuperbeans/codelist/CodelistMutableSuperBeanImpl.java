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
import org.sdmxsource.sdmx.api.model.mutablesuperbeans.codelist.CodelistMutableSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.codelist.CodeSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.codelist.CodelistSuperBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutablesuperbeans.base.MaintainableMutableSuperBeanImpl;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Codelist mutable super bean.
 *
 * @author Matt Nelson
 */
public class CodelistMutableSuperBeanImpl extends MaintainableMutableSuperBeanImpl implements CodelistMutableSuperBean {

    private static final long serialVersionUID = 1L;

    private List<CodeMutableSuperBean> codes = new ArrayList<CodeMutableSuperBean>();


    /**
     * Instantiates a new Codelist mutable super bean.
     *
     * @param codelist the codelist
     */
    public CodelistMutableSuperBeanImpl(CodelistSuperBean codelist) {
        super(codelist);
        if (codelist.getCodes() != null) {
            for (CodeSuperBean currentBean : codelist.getCodes()) {
                codes.add(new CodeMutableSuperBeanImpl(this, currentBean, null));
            }
        }
    }

    /**
     * Instantiates a new Codelist mutable super bean.
     */
    public CodelistMutableSuperBeanImpl() {
    }

    @Override
    public List<CodeMutableSuperBean> getCodes() {
        return codes;
    }

    @Override
    public void setCodes(List<CodeMutableSuperBean> codes) {
        this.codes = codes;
    }

}
