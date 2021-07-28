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
package org.sdmxsource.sdmx.sdmxbeans.model.beans.codelist;

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.base.SdmxStructureBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.CodelistRefBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.mutable.reference.CodelistRefMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.SdmxStructureBeanImpl;
import org.sdmxsource.sdmx.util.beans.reference.CrossReferenceBeanImpl;
import org.sdmxsource.util.ObjectUtil;

import java.util.HashSet;
import java.util.Set;


/**
 * The type Codelist ref bean.
 */
public class CodelistRefBeanImpl extends SdmxStructureBeanImpl implements CodelistRefBean {
    private static final long serialVersionUID = 1L;
    private String alias;
    private CrossReferenceBean codelistReference;

    /**
     * Instantiates a new Codelist ref bean.
     *
     * @param bean   the bean
     * @param parent the parent
     */
    public CodelistRefBeanImpl(CodelistRefMutableBean bean, SdmxStructureBean parent) {
        super(bean, parent);
        this.alias = bean.getAlias();
        if (bean.getCodelistReference() != null) {
            this.codelistReference = new CrossReferenceBeanImpl(this, bean.getCodelistReference());
        }
        validate();
    }

    /**
     * Instantiates a new Codelist ref bean.
     *
     * @param agencyId       the agency id
     * @param maintainableId the maintainable id
     * @param version        the version
     * @param alias          the alias
     * @param parent         the parent
     */
    public CodelistRefBeanImpl(
            String agencyId,
            String maintainableId,
            String version,
            String alias, SdmxStructureBean parent) {
        super(SDMX_STRUCTURE_TYPE.CODE_LIST_REF, parent);
        this.alias = alias;
        this.codelistReference = new CrossReferenceBeanImpl(this, agencyId, maintainableId, version, SDMX_STRUCTURE_TYPE.CODE_LIST);
        validate();
    }

    /**
     * Instantiates a new Codelist ref bean.
     *
     * @param urn    the urn
     * @param alias  the alias
     * @param parent the parent
     */
    public CodelistRefBeanImpl(String urn, String alias, SdmxStructureBean parent) {
        super(SDMX_STRUCTURE_TYPE.CODE_LIST_REF, parent);
        this.alias = alias;
        this.codelistReference = new CrossReferenceBeanImpl(this, urn);
        validate();
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public CrossReferenceBean getCodelistReference() {
        return codelistReference;
    }

    @Override
    protected Set<CrossReferenceBean> getCrossReferenceInternal() {
        Set<CrossReferenceBean> references = new HashSet<CrossReferenceBean>();
        if (codelistReference != null) {
            references.add(codelistReference);
        }
        return references;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////DEEP VALIDATION						 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean deepEquals(SDMXBean bean, boolean includeFinalProperties) {
        if (bean == null) {
            return false;
        }
        if (bean.getStructureType() == this.getStructureType()) {
            CodelistRefBean that = (CodelistRefBean) bean;
            if (!ObjectUtil.equivalent(alias, that.getAlias())) {
                return false;
            }
            if (!super.equivalent(codelistReference, that.getCodelistReference())) {
                return false;
            }
            return true;
        }
        return false;
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////VALIDATION							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private void validate() throws SdmxSemmanticException {
        if (codelistReference == null) {
            throw new SdmxSemmanticException("HierarchicalCodelist, Codelist Reference Missing a Reference");
        }
        if (codelistReference.getTargetReference() != SDMX_STRUCTURE_TYPE.CODE_LIST) {
            throw new SdmxSemmanticException("Referenced structure should be " + SDMX_STRUCTURE_TYPE.CODE_LIST.getType() + " but is declared as " + codelistReference.getTargetReference().getType());
        }
    }
}
