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
package org.sdmxsource.sdmx.sdmxbeans.model.mutablesuperbeans.base;

import org.sdmxsource.sdmx.api.model.mutablesuperbeans.base.ComponentMutableSuperBean;
import org.sdmxsource.sdmx.api.model.mutablesuperbeans.codelist.CodelistMutableSuperBean;
import org.sdmxsource.sdmx.api.model.mutablesuperbeans.conceptscheme.ConceptMutableSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.base.ComponentSuperBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutablesuperbeans.codelist.CodelistMutableSuperBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutablesuperbeans.conceptscheme.ConceptMutableSuperBeanImpl;


/**
 * The type Component mutable super bean.
 */
public abstract class ComponentMutableSuperBeanImpl extends IdentifiableMutableSuperBeanImpl implements ComponentMutableSuperBean {

    private static final long serialVersionUID = 1L;
    private CodelistMutableSuperBean codelistBean;
    private ConceptMutableSuperBean conceptSuperBean;

    /**
     * Instantiates a new Component mutable super bean.
     *
     * @param componentBean the component bean
     */
    public ComponentMutableSuperBeanImpl(ComponentSuperBean componentBean) {
        super(componentBean);

        if (componentBean.getCodelist(false) != null) {
            this.codelistBean = new CodelistMutableSuperBeanImpl(componentBean.getCodelist(false));
        }
        if (componentBean.getConcept() != null) {
            this.conceptSuperBean = new ConceptMutableSuperBeanImpl(componentBean.getConcept());
        }
    }

    /**
     * Instantiates a new Component mutable super bean.
     */
    public ComponentMutableSuperBeanImpl() {
    }

    @Override
    public CodelistMutableSuperBean getCodelistBean() {
        return codelistBean;
    }

    @Override
    public void setCodelistBean(CodelistMutableSuperBean codelistBean) {
        this.codelistBean = codelistBean;
    }

    @Override
    public ConceptMutableSuperBean getConceptSuperBean() {
        return conceptSuperBean;
    }

    @Override
    public void setConceptSuperBean(ConceptMutableSuperBean conceptSuperBean) {
        this.conceptSuperBean = conceptSuperBean;
    }
}
