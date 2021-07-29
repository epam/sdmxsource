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

import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.*;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.superbeans.codelist.HierarchicalCodeSuperBean;
import org.sdmxsource.sdmx.util.beans.MaintainableUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * The type Code ref super bean.
 */
public class CodeRefSuperBeanImpl extends IdentifiableSuperBeanImpl implements HierarchicalCodeSuperBean {
    private static final long serialVersionUID = 328066827956232466L;
    private CodeBean codeBean;
    private List<HierarchicalCodeSuperBean> codeRefs;

    /**
     * Instantiates a new Code ref super bean.
     *
     * @param hierarchyBean the hierarchy bean
     * @param codeRef       the code ref
     * @param codelists     the codelists
     */
    public CodeRefSuperBeanImpl(HierarchyBean hierarchyBean, HierarchicalCodeBean codeRef, List<CodelistBean> codelists) {
        super(codeRef);
        codeBean = getCodeBean(hierarchyBean, codeRef, codelists);
        if (codeRef.getCodeRefs() != null) {
            codeRefs = new ArrayList<HierarchicalCodeSuperBean>();
            for (HierarchicalCodeBean currentCodeRef : codeRef.getCodeRefs())
                codeRefs.add(new CodeRefSuperBeanImpl(hierarchyBean, currentCodeRef, codelists));
        }
    }

    private CodeBean getCodeBean(HierarchyBean hierarchyBean, HierarchicalCodeBean codeRef, List<CodelistBean> codelists) {
        StructureReferenceBean sRef = null;
        if (codeRef.getCodeReference() != null) {
            sRef = codeRef.getCodeReference();
        } else {
            HierarchicalCodelistBean hcl = hierarchyBean.getMaintainableParent();
            CodelistRefBean codelistRef = getCodelistRef(hcl.getCodelistRef(), codeRef.getCodelistAliasRef());
            sRef = codelistRef.getCodelistReference();
        }

        CodelistBean codelist = (CodelistBean) MaintainableUtil.resolveReference(codelists, sRef);
        if (codelist == null) {
            throw new IllegalArgumentException("Codelist " + sRef.getMaintainableUrn() + " Not found");
        }
        return getCode(codelist, sRef.getChildReference().getId());
    }


    private CodeBean getCode(CodelistBean codelist, String codeId) {
        for (CodeBean currentCode : codelist.getItems()) {
            if (currentCode.getId().equals(codeId)) {
                return currentCode;
            }
        }
        throw new IllegalArgumentException("Code " + codeId + " Not found in codelist : " + codelist.getUrn());
    }

    private CodelistRefBean getCodelistRef(List<CodelistRefBean> codelistRefs, String codelistaliasRef) {
        for (CodelistRefBean codelistref : codelistRefs) {
            if (codelistref.getAlias().equals(codelistaliasRef)) {
                return codelistref;
            }
        }
        throw new IllegalArgumentException("Codelist Ref Not found with Alias : " + codelistaliasRef);
    }

    @Override
    public CodeBean getCode() {
        return codeBean;
    }

    @Override
    public Set<MaintainableBean> getCompositeBeans() {
        Set<MaintainableBean> returnSet = super.getCompositeBeans();
        returnSet.add(codeBean.getMaintainableParent());
        if (codeRefs != null) {
            for (HierarchicalCodeSuperBean currentRef : codeRefs) {
                returnSet.addAll(currentRef.getCompositeBeans());
            }
        }
        return returnSet;
    }

    @Override
    public List<HierarchicalCodeSuperBean> getCodeRefs() {
        if (codeRefs == null) {
            return null;
        }
        return new ArrayList<HierarchicalCodeSuperBean>(codeRefs);
    }

    @Override
    public HierarchicalCodeBean getBuiltFrom() {
        return (HierarchicalCodeBean) super.getBuiltFrom();
    }
}
