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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.CodeBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.CodelistBean;
import org.sdmxsource.sdmx.api.model.superbeans.codelist.CodeSuperBean;
import org.sdmxsource.sdmx.api.model.superbeans.codelist.CodelistSuperBean;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.base.MaintainableSuperBeanImpl;
import org.sdmxsource.util.ObjectUtil;

import java.util.*;


/**
 * The type Codelist super bean.
 *
 * @author Matt Nelson
 */
public class CodelistSuperBeanImpl extends MaintainableSuperBeanImpl implements CodelistSuperBean {
    private static final transient Logger LOG = LogManager.getLogger(CodelistSuperBeanImpl.class);
    private static final long serialVersionUID = 1L;

    private List<CodeSuperBean> codes = new ArrayList<CodeSuperBean>();
    private Map<String, CodeSuperBean> codeValueMap = new HashMap<String, CodeSuperBean>();
    private CodelistBean codelistBean;

    /**
     * Instantiates a new Codelist super bean.
     *
     * @param codelist the codelist
     */
    public CodelistSuperBeanImpl(CodelistBean codelist) {
        super(codelist);
        LOG.debug("Create Codelist Super Bean");
        this.codelistBean = codelist;
        List<CodeBean> allCodes = codelist.getItems();
        if (allCodes != null) {
            Map<CodeBean, List<CodeBean>> codeChildMap = new HashMap<CodeBean, List<CodeBean>>();

            LOG.debug("Create code Child Map");
            for (CodeBean currentCode : allCodes) {
                if (ObjectUtil.validString(currentCode.getParentCode())) {
                    CodeBean parent = codelist.getCodeById(currentCode.getParentCode());

                    //Add the child to the parent to the key, and the child to the value
                    List<CodeBean> children = codeChildMap.get(parent);
                    if (children == null) {
                        children = new ArrayList<CodeBean>();
                        codeChildMap.put(parent, children);
                    }
                    children.add(currentCode);
                }
            }
            LOG.debug("Map Created");
            for (CodeBean currentCode : allCodes) {
                if (!ObjectUtil.validString(currentCode.getParentCode())) {
                    LOG.debug("Create Top Level Code Super Bean: " + currentCode.getId());
                    CodeSuperBean code = new CodeSuperBeanImpl(this, currentCode, codeChildMap, null);
                    LOG.debug("Create Top Level Code Super Bean Created: " + currentCode.getId());
                    codes.add(code);
                }
            }
        }
        buildCodeValueMap(codes);
    }

    /* (non-Javadoc)
     * @see org.sdmxsource.dataweb.external.model.Codelist#getCodes()
     */
    @Override
    public List<CodeSuperBean> getCodes() {
        return new ArrayList<CodeSuperBean>(codes);
    }

    @Override
    public CodeSuperBean getCodeByValue(String value) {
        return codeValueMap.get(value);
    }

    @Override
    public CodelistBean getBuiltFrom() {
        return codelistBean;
    }

    @Override
    public Set<MaintainableBean> getCompositeBeans() {
        Set<MaintainableBean> returnSet = super.getCompositeBeans();
        returnSet.add(codelistBean);
        return returnSet;
    }


    private void buildCodeValueMap(List<CodeSuperBean> codes) {
        for (CodeSuperBean currentCode : codes) {
            if (currentCode.hasChildren()) {
                buildCodeValueMap(currentCode.getChildren());
            }
            codeValueMap.put(currentCode.getId(), currentCode);
        }
    }
}
