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
import org.sdmxsource.sdmx.api.model.beans.codelist.HierarchicalCodeBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.mutable.reference.CodeRefMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.IdentifiableMutableBeanImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * The type Code ref mutable bean.
 */
public class CodeRefMutableBeanImpl extends IdentifiableMutableBeanImpl implements CodeRefMutableBean {
    private static final long serialVersionUID = 1L;

    private List<CodeRefMutableBean> codeRefs = new ArrayList<CodeRefMutableBean>();
    private StructureReferenceBean codeReference;
    private String codelistAliasRef;
    private String codeId;
    private Date validFrom;
    private Date validTo;
    private String levelReference;


    /**
     * Instantiates a new Code ref mutable bean.
     */
    public CodeRefMutableBeanImpl() {
        super(SDMX_STRUCTURE_TYPE.HIERARCHICAL_CODE);
    }

    /**
     * Instantiates a new Code ref mutable bean.
     *
     * @param codeRef the code ref
     */
    public CodeRefMutableBeanImpl(HierarchicalCodeBean codeRef) {
        super(codeRef);
        this.codeReference = codeRef.getCodeReference().createMutableInstance();
        this.codelistAliasRef = codeRef.getCodelistAliasRef();
        this.codeId = codeRef.getCodeId();

        // change list to Mutable CodeRefBeans
        if (codeRef.getCodeRefs() != null) {
            for (HierarchicalCodeBean currentCodeRef : codeRef.getCodeRefs()) {
                this.codeRefs.add(new CodeRefMutableBeanImpl(currentCodeRef));
            }
        }
        if (codeRef.getLevel(false) != null) {
            levelReference = codeRef.getLevel(false).getFullIdPath(false);
        }
    }

    @Override
    public String getLevelReference() {
        return levelReference;
    }

    @Override
    public void setLevelReference(String levelReference) {
        this.levelReference = levelReference;
    }

    @Override
    public Date getValidFrom() {
        return validFrom;
    }

    @Override
    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    @Override
    public Date getValidTo() {
        return validTo;
    }

    @Override
    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    @Override
    public void addCodeRef(CodeRefMutableBean codeRef) {
        this.codeRefs.add(codeRef);
    }

    @Override
    public List<CodeRefMutableBean> getCodeRefs() {
        return codeRefs;
    }

    @Override
    public void setCodeRefs(List<CodeRefMutableBean> codeRefs) {
        this.codeRefs = codeRefs;
    }

    @Override
    public StructureReferenceBean getCodeReference() {
        return codeReference;
    }

    @Override
    public void setCodeReference(StructureReferenceBean codeReference) {
        this.codeReference = codeReference;
    }

    @Override
    public String getCodelistAliasRef() {
        return codelistAliasRef;
    }

    @Override
    public void setCodelistAliasRef(String codelistAliasRef) {
        this.codelistAliasRef = codelistAliasRef;
    }

    @Override
    public String getCodeId() {
        return codeId;
    }

    @Override
    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }
}
