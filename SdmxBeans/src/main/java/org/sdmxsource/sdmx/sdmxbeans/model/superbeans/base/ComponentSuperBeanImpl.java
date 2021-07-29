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
package org.sdmxsource.sdmx.sdmxbeans.model.superbeans.base;

import org.sdmxsource.sdmx.api.manager.retrieval.SdmxBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.beans.base.ComponentBean;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.beans.base.TextFormatBean;
import org.sdmxsource.sdmx.api.model.superbeans.SuperBeans;
import org.sdmxsource.sdmx.api.model.superbeans.base.ComponentSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.codelist.CodelistSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.conceptscheme.ConceptSuperBean;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.SuperBeansUtil;

import java.util.Set;

/**
 * The type Component super bean.
 */
public abstract class ComponentSuperBeanImpl extends IdentifiableSuperBeanImpl implements ComponentSuperBean {
    private static final long serialVersionUID = 1L;
    private CodelistSuperBean codelistBean;
    private ConceptSuperBean conceptSuperBean;
    private ComponentBean componentBean;


    /**
     * Instantiates a new Component super bean.
     *
     * @param componentBean    the component bean
     * @param retrievalManager the retrieval manager
     * @param existingBeans    the existing beans
     */
    public ComponentSuperBeanImpl(ComponentBean componentBean,
                                  SdmxBeanRetrievalManager retrievalManager,
                                  SuperBeans existingBeans) {
        super(componentBean);
        this.componentBean = componentBean;
        if (componentBean.getRepresentation() != null) {
            this.codelistBean = SuperBeansUtil.buildCodelist(componentBean.getRepresentation().getRepresentation(), retrievalManager, existingBeans);
        }
        this.conceptSuperBean = SuperBeansUtil.buildConcept(componentBean.getConceptRef(), retrievalManager, existingBeans);
        if (codelistBean == null) {
            this.codelistBean = conceptSuperBean.getCoreRepresentation();
        }
    }

    /**
     * Instantiates a new Component super bean.
     *
     * @param componentBean    the component bean
     * @param codelistBean     the codelist bean
     * @param conceptSuperBean the concept super bean
     */
    public ComponentSuperBeanImpl(ComponentBean componentBean, CodelistSuperBean codelistBean, ConceptSuperBean conceptSuperBean) {
        super(componentBean);
        this.componentBean = componentBean;
        this.codelistBean = codelistBean;
        if (codelistBean == null) {
            this.codelistBean = conceptSuperBean.getCoreRepresentation();
        }
        this.conceptSuperBean = conceptSuperBean;
    }


    /**
     * Gets codelist.
     *
     * @return the codelist
     */
    public CodelistSuperBean getCodelist() {
        return getCodelist(true);
    }

    @Override
    public CodelistSuperBean getCodelist(boolean defaultIfRequired) {
        if (codelistBean == null) {
            return conceptSuperBean.getCoreRepresentation();
        }
        return codelistBean;
    }

    @Override
    public ConceptSuperBean getConcept() {
        return conceptSuperBean;
    }

    @Override
    public TextFormatBean getTextFormat() {
        if (componentBean.getRepresentation() != null) {
            return componentBean.getRepresentation().getTextFormat();
        }
        if (conceptSuperBean.getTextFormat() != null) {
            return conceptSuperBean.getTextFormat();
        }
        return null;
    }

    @Override
    public Set<MaintainableBean> getCompositeBeans() {
        Set<MaintainableBean> returnSet = super.getCompositeBeans();
        returnSet.add(componentBean.getMaintainableParent());
        if (codelistBean != null) {
            returnSet.addAll(codelistBean.getCompositeBeans());
        }
        if (conceptSuperBean != null) {
            returnSet.addAll(conceptSuperBean.getCompositeBeans());
        }
        return returnSet;
    }

    @Override
    public ComponentBean getBuiltFrom() {
        return componentBean;
    }
}
